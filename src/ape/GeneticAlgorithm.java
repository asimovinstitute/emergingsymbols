/***************************************************************\
 * Java code used for language training experiments with
 * deep neural networks. This research was modeled after
 * Savage-Rumbaugh's research on chimpanzees crossing the
 * symbolic threshold. It shows the advantages in (terms of
 * learning) of using semiotic symbols over pure indexical
 * (i.e. associative) signs.

 * For more background info see
 * http://www.leijnen.com/docs/Leijnen.Emerging.Symbols.2008.pdf

 * Copyright (c) 2015 Leijnen Technology
 * Author: Stefan Leijnen (stefan@leijnen.com)
 * All Rights Reserved
\***************************************************************/


package ape;

/***
 *
 * GeneticAlgorithm: trains the network using a genetic algorithm
 *
 */

import java.util.*;

public class GeneticAlgorithm{

	private Experiment exp;
	private Random generator;

	private Vector population;
	private Vector elite;

	private int populationSize;
	private int encodedNetSize;

	//parameters:
	private int generations;
	private int elites;
	private int children;
	private int random;
	private int trials;
	private int trialType;
	private int trialNumber;
	private double mutationChance;

	public GeneticAlgorithm(Experiment e){
		exp = e;
		generator = new Random();

		population = new Vector();
		elite = new Vector();
	}

	public int runTime(int tag, int tn){

		boolean found = false;
		int foundAt = generations;
		trialNumber = tn;
		for(int g=0;g<generations;g++){
			if(found){
				exp.update(g, 0, tag);
			}
			else{
				//generate a new population, clear elites
				generatePopulation();

				//compute error of all individuals in population
				simulate();

				//select the best elites and clear population
				selectElite();

				//update time, the *error* so far and viewer
				exp.update(g, ((EncodedNet)elite.firstElement()).getError(), tag);


				//check if a solution has been found;
				if(exp.solutionFound(g, tag)){
					found = true;
					foundAt = g;
				}
			}
		}
		return foundAt;
	}

	/***
	 * generatePopulation():
	 * generate a new population from existing elites, using
	 *   - existing elites
	 *   - random new nets
	 *   - crossover and mutation
	 */
	public void generatePopulation(){
		//random population on first generation
		if(elite.isEmpty()){
			for(int i=0;i<populationSize;i++){
				population.addElement(new EncodedNet(encodedNetSize));
			}
		}
		else{

			//add elite to population
			for(int e=0;e<elites;e++){
				population.addElement((elite.elementAt(e)));
			}
			//crossover and mutation
			for(int c=0;c<children;c++){
				population.addElement(mutate(crossOver()));
			}
			//random individuals
			for(int r=0;r<random;r++){
				population.addElement(new EncodedNet(encodedNetSize));
			}

			//throw away old elites, that are now in the population
			elite.clear();
		}
	}

	/***
	 * simulate():
	 * generate error scores for each individual in population
	 */
	public void simulate(){
		for(int j=0;j<populationSize;j++){

			//determine the specific trials
			TrialVector t = new TrialVector(trialNumber, trialType, exp.getInputLayerSize(), exp.getOutputLayerSize());

			//set the weights according to the encoding for this individual
			double[] weights = ((EncodedNet)population.elementAt(j)).getFenotype();
			exp.getNet().setWeights(weights);

			//for each trial, input a pattern, do a feedforward and compute the error
			for(int k=0;k<trialNumber;k++){

				exp.getNet().input(t.getInput(k));
				exp.getNet().propagate();
				t.getError()[k] = exp.getNet().computeError(t.getOutput(k));
			}

			//set the error for the individual
			((EncodedNet)population.elementAt(j)).setError(t.getAverageError());
		}
	}


	/***
	 * selectElite():
	 * select lowest error producing individuals of the current population
	 */

	//select the individuals with the lowest error values
	public void selectElite(){

		//change: make sure elite is randomly added (so if more than elite.size
		//have the same score, use random and don't just pick the first ones

		sortPopulation(); //lowest error values first
		copyBestPop(); //to the elites array
		Vector sameScore = new Vector();
		int sameScoreCounter = 0;
		double worstEliteError = ((EncodedNet)elite.lastElement()).getError();
		//get all with same score together
		for(int i=0;i<elite.size();i++){
			EncodedNet e = (EncodedNet)elite.elementAt(i);
			if(e.getError() == worstEliteError){
				sameScore.add(e);
				sameScoreCounter++;
			}
		}
		for(int i=0;i<population.size();i++){
			EncodedNet e = (EncodedNet)population.elementAt(i);
			if(e.getError() == worstEliteError){
				sameScore.add(e);
			}
		}
		//replace with random elements
		for(int i=0;i<sameScoreCounter;i++){
			elite.removeElementAt((elite.size()-1));
		}
		for(int i=0;i<sameScoreCounter;i++){
			int r = generator.nextInt(sameScore.size());
			elite.add(sameScore.elementAt(r));
			sameScore.removeElementAt(r);
		}
		population.clear();
	}

	//Bubblesort
	public void sortPopulation(){
	  EncodedNet temp;

	  for (int position = population.size() - 1; position >= 0; position--) {
	    for (int scan = 0; scan <= position - 1; scan++) {
	      if (((EncodedNet)population.elementAt(scan)).getError() > ((EncodedNet)population.elementAt(scan+1)).getError()) {
	        temp = (EncodedNet)population.elementAt(scan);
	        population.setElementAt(population.elementAt(scan+1), scan);
	        population.setElementAt(temp, (scan+1));
	      }
	    }
	  }
	}

	public void copyBestPop(){
		for(int i=0;i<elites;i++){
			elite.add(population.elementAt(i));
		}
	}

	/*****GA functions*****/

	public EncodedNet crossOver(){
		EncodedNet e1 = getRandomElite();
		EncodedNet e2 = getRandomElite();
		int[] weights = new int[e1.getWeights().length];

		for(int i=0; i<weights.length;i++){
			//select a random weight
			if(Math.random() >= 0.5){
				weights[i] = e1.getWeights()[i];
			}
			else{
				weights[i] = e2.getWeights()[i];
			}
		}
		EncodedNet en = new EncodedNet(encodedNetSize);
		en.setWeights(weights);
		return en;
	}

	public EncodedNet mutate(EncodedNet e){
		EncodedNet em = e;
		//under a certain probability mutationChance, flip a bit
		for(int i=0; i<em.getWeights().length;i++){
			if(Math.random() < mutationChance){
				em.getWeights()[i] = Math.abs(em.getWeights()[i] - 1);
			}
		}
		return em;
	}

	//returns a random EncodedNet from the elite vector
	public EncodedNet getRandomElite(){
		double stepsize = 1.0/(double)elite.size();
		double index = Math.random();
		EncodedNet e = new EncodedNet(encodedNetSize);
		for(int i=0;i<elite.size();i++){
			if(index >= ((double)i)*stepsize  &&  index < (((double)i)+1)*stepsize){
				e = (EncodedNet)elite.elementAt(i);
			}
		}
		return (e);
	}



	public void printPop(){
		//output population
		for(int l=0;l<population.size();l++){
			System.out.println("pop element "+l+": "+((EncodedNet)population.elementAt(l)).weightsToString());
		}
		System.out.println("");
	}

	public void printElites(){
		//output elites
		for(int l=0;l<elite.size();l++){
			System.out.println("elite element "+l+": "+((EncodedNet)elite.elementAt(l)).weightsToString());
		}
	}

	public int getBinaryRandom(){
		if(Math.random() >= 0.5)
			return 1;
		else
			return 0;
	}

	public void updateParameters(){
		generations = exp.getGenerations();
		elites = exp.getElites();
		children = exp.getChildren();
		random = exp.getRandom();
		trialType = exp.getTrialType();
		trialNumber = exp.getTrialNumber();
		mutationChance = exp.getMutationChance();

		populationSize = elites + children + random;
		//based on the assumption of full connectivity:
		encodedNetSize = 3*((exp.getInputLayerSize() * exp.getHiddenLayerSize())
					    + (exp.getHiddenLayerSize() * exp.getOutputLayerSize()));
	}

	public void printArray(int[] array){
		for(int i=0;i<array.length;i++){
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}

	public void printArray(double[] array){
		for(int i=0;i<array.length;i++){
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}

	public void setGenerations(int g){
		generations = g;
	}

	public void setElitesSize(int e){
		elites = e;
	}

	public void setRandomSize(int r){
		random = r;
	}

	public void setPopulationSize(int p){
		populationSize = p;
	}

	public void setTrials(int t){
		trials = t;
	}

	public void setTrialType(int t){
		trialType = t;
	}

	public void setTrialNumber(int n){
		trialNumber = n;
	}

	public void setMutationChance(double m){
		mutationChance = m;
	}

	public void out(String s){
		System.out.println(s);
	}

	public void setPopulation(Vector v){
		population = v;
	}

	public Vector getElite(){
		return elite;
	}
}
