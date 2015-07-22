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

import java.util.Vector;

public class Experiment {

	private Net net;
	private GeneticAlgorithm ga;
	private int expTag;

	private Viewer v1;
 	private double[] results1;
	private double[] results2;
	private double[] results3;
 	private double[] results4;

	//GA variables
	private int generations;
	private int elites;
	private int children;
	private int random;

	//NN variables
	private int inputLayerSize;
	private int hiddenLayerSize;
	private int outputLayerSize;
	private int trialType;
	private int trialNumber;
	private int avgOver;
	private double mutationChance;
	private int criterium;

	public static void main(String[] args) {
		System.out.println("initializing setup...");
		Experiment e  = new Experiment();
		System.out.println("starting experiment...");
		System.out.println("");

		//e.startViewer();

		//e.runTest();

		//e.runA();
		//e.runC1();
		//e.runB();
		//e.runC2();
		e.runD();
	}

	public void startViewer(){
		v1 = new Viewer();
	}

	public void setThresholds(double d){
		this.net.getInputLayer().setThresholds(d);
		this.net.getHiddenLayer().setThresholds(d);
		this.net.getOutputLayer().setThresholds(d);
	}

	public Experiment(){

		//parameters (default settings):
		inputLayerSize = 10;
		hiddenLayerSize = 10;
		outputLayerSize = 10;
		generations = 30000;
	 	elites = 10;
	 	children = 50;
 		random = 0;
 		trialType = 1;
 		trialNumber = 8;//max is in-/outputlayerSize-2;
 		avgOver = 100;
	 	mutationChance = 0.01;
	 	criterium = 1;

	}

	public void updateT(int trialNumber, int totalT, int avgOverNumber, int totalA, double d){
		int i = (int)Math.round(d);
		if(trialNumber == 1){//first
			System.out.print("Run "+avgOverNumber+"/"+totalA+":  "+ i);
		}
		else if(trialNumber == totalT){//last
			System.out.println(" "+i);
		}
		else{//middle
			System.out.print(" "+i);
		}
	}

	/*
	 * runA(): initial demonstration of how a network can learn an index.
	 * The system is shown an object, and it has to learn to respond with
	 * the corresponding lexigram:
     *
     * B --> LB, 1000000 --> 1000000
     *
     * each time a new object is added, the time to learn this object takes
     * longer - because the working memory of the network is increasingly
     * overloaded
     *
	 */

	public void runA(){

		trialType = 1;
		expTag = 1;
		printParameters(expTag);

		net = new Net(inputLayerSize,hiddenLayerSize,outputLayerSize); // resize network according to new parameters
		setThresholds(0.4);

		double[][] results = new double[avgOver][trialNumber];
		for (int i=0;i<avgOver;i++){

			results1 = new double[generations];

			ga = new GeneticAlgorithm(this);
			ga.updateParameters();

			for(int j=0;j<trialNumber;j++){
				results[i][j] = ga.runTime(expTag, (j+1));
				updateT((j+1), trialNumber, (i+1), avgOver, (results[i][j]-criterium));
			}
		}
		System.out.println("");
		showResults(avgOver(results));
	}

	/*
	 * runB(): In this configuration, the systems learns holophrases, that is, stimulus-
	 * action pairs. Although often referred to as symbolic learning, SSR argue that the
	 * apes performing this behavior only show indexical learning skills.
     *
     * B --> LBLG, 1000000 --> 1000010
     *
     * again, each time a new object is added, the time to learn this object takes
     * longer - because the working memory of the network is increasingly
     * overloaded (due to the non-symbolic use of memory which is unefficient)
     *
	 */

	public void runB(){

		trialType = 2;
		expTag = 2;
		printParameters(expTag);

		net = new Net(inputLayerSize,hiddenLayerSize,outputLayerSize); // resize network according to new paramters
		setThresholds(0.85);

		double[][] results = new double[avgOver][trialNumber];
		for (int i=0;i<avgOver;i++){

			results2 = new double[generations];

			ga = new GeneticAlgorithm(this);
			ga.updateParameters();

			for(int j=0;j<trialNumber;j++){
				results[i][j] = ga.runTime(expTag, (j+1));
				updateT((j+1), trialNumber, (i+1), avgOver, results[i][j]-criterium);
			}
		}
		System.out.println("");
		showResults(avgOver(results));
	}

	public void runC1(){
		trialType = 3;
		expTag = 3;
		printParameters(expTag);

		net = new Net(inputLayerSize,hiddenLayerSize,outputLayerSize); // resize network according to new paramters
		setThresholds(0.85);

		double[][] results = new double[avgOver][trialNumber];
		for (int i=0;i<avgOver;i++){

			results3 = new double[generations];

			ga = new GeneticAlgorithm(this);
			ga.updateParameters();

			for(int j=0;j<trialNumber;j++){
				results[i][j] = ga.runTime(expTag, (j+1));
				updateT((j+1), trialNumber, (i+1), avgOver, results[i][j]-criterium);
			}
		}
		System.out.println("");
		showResults(avgOver(results));

	}

	/*
	public void runC2(){
		trialType = 4;
		expTag = 4;
		printParameters(expTag);

		net = new Net(inputLayerSize,hiddenLayerSize,outputLayerSize); // resize network according to new parameters
		setThresholds(0.85);

		double[][] results = new double[avgOver][trialNumber];
		for (int i=0;i<avgOver;i++){

			results4 = new double[generations];

			dga = new DoubleGeneticAlgorithm(this);
			dga.updateParameters();

			for(int j=0;j<trialNumber;j++){
				results[i][j] = dga.runTime(expTag, (j+1));
				updateT((j+1), trialNumber, (i+1), avgOver, results[i][j]);
				//System.out.println("experiment: "+i+", object #: "+j+", foundAt: "+results[i][j]);
			}
			//System.out.print("|");//System.out.println("Experiment "+(i+1)+" of "+avgOver);
		}
		System.out.println("");
		showResults(avgOver(results));
	}
	*/

	//Sort of Hack, used expTag 2 instead of 5
	public void runD(){

		trialType = 5;
		expTag = 2;
		printParameters(expTag);

		net = new Net(inputLayerSize,hiddenLayerSize,outputLayerSize); // resize network according to new paramters
		setThresholds(0.85);

		double[][] results = new double[avgOver][trialNumber];
		for (int i=0;i<avgOver;i++){

			results2 = new double[generations];

			ga = new GeneticAlgorithm(this);
			ga.updateParameters();

			for(int j=0;j<trialNumber;j++){
				results[i][j] = ga.runTime(expTag, (j+1));
				updateT((j+1), trialNumber, (i+1), avgOver, results[i][j]-criterium);
			}
		}
		System.out.println("");
		showResults(avgOver(results));
	}


	public void runTest(){

		TrialPair t;
		for(int i=0;i<100;i++){
			t = new TrialPair(5, ((i%8)+1), 10, 10);
			out("TrialPair "+i+" = ");
			t.printPair();
		}

	}


	public void update(int g, double e, int tag){
		if(tag == 1){
			results1[g] = e;
			//v1.update(g, results1, tag);
		}
		else if(tag == 2){
			results2[g] = e;
			//v1.update(g, results2, tag);
		}
		else if(tag == 3){
			results3[g] = e;
			//v1.update(g, results3, tag);
		}
		else if(tag == 4){
			results4[g] = e;
			//v1.update(g, results4, tag);
		}
		else{
			out("Error Experiment.update: tag not found");
		}
	}

	public boolean solutionFound(int generation, int tag){
		boolean found = true;
		if(generation > criterium){
			switch(tag){
			case(1):{
				for(int i=(generation-(criterium-1));i<(generation+1);i++){
					if(results1[i] != 0){
						found = false;
					}
				}
				break;
			}
			case(2):{
				for(int i=(generation-(criterium-1));i<(generation+1);i++){
					if(results2[i] != 0){
						found = false;
					}
				}
				break;
			}
			case(3):{
				for(int i=(generation-(criterium-1));i<(generation+1);i++){
					if(results3[i] != 0){
						found = false;
					}
				}
				break;
			}
			case(4):{
				for(int i=(generation-(criterium-1));i<(generation+1);i++){
					if(results4[i] != 0){
						found = false;
					}
				}
				break;
			}
			default:{
				found = false;
				break;
			}
			}
		}
		else{//generation < criterium
			found = false;
		}
		return found;
	}






	/*******************/
	/***** GET/SET *****/
	/*******************/

	public Net getNet(){
		return net;
	}

	public void out(String s){
		System.out.println(s);
	}

	public void printParameters(int expTag){
		out("");
		out("--- Exp "+expTag+" Parameters ---");
		out("inputLayerSize  = "+inputLayerSize);
		out("hiddenLayerSize = "+hiddenLayerSize);
		out("outputLayerSize = "+outputLayerSize);
		out("generations     = "+generations);
		out("elites          = "+elites);
		out("children        = "+children);
		out("random          = "+random);
		out("trialType       = "+trialType);
		out("trialNumber     = "+trialNumber);
		out("mutationChance  = "+mutationChance);
		out("average over    = "+avgOver);
		out("criterium       = "+criterium);
		out("------------------------");
		out("");
	}

	public void showResults(double[] r){
		System.out.println("RESULTS: ");
		for(int i=0;i<r.length;i++){
			System.out.print(r[i]+" ");
		}
		System.out.println("");
	}

	public double[] avgOver(double[][] r){
		double[] avgResults = new double[r[0].length];
		//for each object number...
		for(int i=0;i<r[0].length;i++){
			//... add all experiment results
			for(int j=0;j<r.length;j++){
				avgResults[i] = avgResults[i] + r[j][i];
			}
			//... and divide them by the number of experiments
			avgResults[i] = avgResults[i]/r.length;
		}
		return avgResults;
	}

	public int getGenerations(){
		return generations;
	}

	public int getChildren(){
		return children;
	}

	public int getElites(){
		return elites;
	}

	public int getRandom(){
		return random;
	}

	public int getTrialType(){
		return trialType;
	}

	public int getTrialNumber(){
		return trialNumber;
	}

	public double getMutationChance(){
		return mutationChance;
	}

	public int getInputLayerSize(){
		return inputLayerSize;
	}

	public int getHiddenLayerSize(){
		return hiddenLayerSize;
	}

	public int getOutputLayerSize(){
		return outputLayerSize;
	}

	public void setChildren(int c){
		children = c;
	}

	public void setRandom(int r){
		random = r;
	}

	public void setElites(int e){
		elites = e;
	}

	public void setMutationChance(double m){
		mutationChance = m;
	}

	public void setTrialType(int t){
		trialType = t;
	}

	public void setTrialNumber(int n){
		trialNumber = n;
	}

	public void setGenerations(int g){
		generations = g;
	}

	public void setInputLayerSize(int i){
		inputLayerSize = i;
	}

	public void setHiddenLayerSize(int h){
		hiddenLayerSize = h;
	}

	public void setOutputLayerSize(int o){
		outputLayerSize = o;
	}
}
