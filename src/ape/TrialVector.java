package ape;

import java.util.*;

/***
 * 
 *  TrialVector contains the correct input-output pairs
 *
 */


public class TrialVector {
	
	private Vector data;
	private double[] error;
	
	public TrialVector(int number, int type, int inputSize, int outputSize){
		
		data = new Vector();
		error = new double[number];
		
		for(int i=0;i<number;i++){
			data.addElement(new TrialPair(type, (i+1), inputSize, outputSize));
		}	
	}
	
	/*
	public TrialVector(int size, int type, int number, int inputSize, int outputSize){
		
		data = new Vector();
		error = new double[size];
		for(int i=0;i<number;i++){//make set TP's
			data.addElement(new TrialPair((i+1), type, number, inputSize, outputSize));
			System.out.println("made set TP" + (i+1));
		}
		for(int j=number;j<(size+1);j++){
			data.addElement(new TrialPair(type, number, inputSize, outputSize));
			System.out.println("made RANDOM TP" + j);
		}
	}
*/
	public double getAverageError(){
		double avgError = 0;
		if(error.length == 0){
			System.out.println("error: cannot compute average error of empty trialvector");
		}
		else{
			for(int i=0;i<error.length;i++){
				avgError += error[i];
			}
			avgError = avgError/error.length;
		}
		return avgError;
	}
	
	public Vector getData(){
		return data;
	}
	
	public void printPairs(){
		for(int i=0;i<data.size();i++){
			((TrialPair)data.elementAt(i)).printPair();
		}
	}
	
	public int[] getInput(int n){
		return ((TrialPair)data.elementAt(n)).getInput();
	}
	
	public int[] getOutput(int n){
		return ((TrialPair)data.elementAt(n)).getOutput();
	}
	
	public void setError(double[] e){
		error = e;
	}
	
	public double[] getError(){
		return error;
	}
}
