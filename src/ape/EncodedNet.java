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
 *  EncodedNet: the genotype of a network, consisting of an array of binary
 *  			bits, that can be mapped onto a real network
 *
 */


public class EncodedNet {

	private int[] weights;
	private double error;

	public EncodedNet(int s){
		int size = s;
		weights = new int[size];
		for(int i=0;i<size;i++){
			weights[i] = getBinaryRandom();
		}
	}

	//change the (binary) genotype to the (decimal) fenotype of the weights
	//using Gray encoding (only when multiple bits encode one weight)
	public double[] getFenotype(){
		double[] fenotype = new double[weights.length/3];
		for(int i=0;i<weights.length;i=i+3){
			if     (weights[i] == 0 && weights[i+1] == 0 && weights[i+2] == 0) {fenotype[(i/3)] = 0.000;}
			else if(weights[i] == 0 && weights[i+1] == 0 && weights[i+2] == 1) {fenotype[(i/3)] = 0.072;}
			else if(weights[i] == 0 && weights[i+1] == 1 && weights[i+2] == 1) {fenotype[(i/3)] = 0.144;}
			else if(weights[i] == 0 && weights[i+1] == 1 && weights[i+2] == 0) {fenotype[(i/3)] = 0.216;}
			else if(weights[i] == 1 && weights[i+1] == 1 && weights[i+2] == 0) {fenotype[(i/3)] = 0.288;}
			else if(weights[i] == 1 && weights[i+1] == 1 && weights[i+2] == 1) {fenotype[(i/3)] = 0.360;}
			else if(weights[i] == 1 && weights[i+1] == 0 && weights[i+2] == 1) {fenotype[(i/3)] = 0.432;}
			else if(weights[i] == 1 && weights[i+1] == 0 && weights[i+2] == 0) {fenotype[(i/3)] = 0.504;}

			else{//default case, error
				fenotype[(i/3)] = 0;
				System.out.println("convertBinary failed");
			}
		}
		return fenotype;
	}

	public int getBinaryRandom(){
		if(Math.random() >= 0.5)
			return 1;
		else
			return 0;
	}

	public void setError(double e){
		error = e;
	}

	public double getError(){
		return error;
	}

	public int[] getWeights(){
		return weights;
	}

	public void setWeights(int[] w){
		weights = w;
	}

	public String weightsToString(){
		String s = "";
		for (int i=0;i<weights.length;i++){
			s = s + weights[i] + " ";
		}
		return s;
	}

	public void printWeights(){
		System.out.println(weightsToString());
	}
}
