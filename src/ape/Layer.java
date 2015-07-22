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
import java.util.*;

public class Layer {

	private Vector neurons;

	public Layer(int s) {

		neurons = new Vector();
		for(int i=0;i<s;i++){
			neurons.addElement(new Neuron());
		}
	}

	public void activate(){
		//for each neuron, compute activation values
		for(int i=0;i<neurons.size();i++){
			((Neuron)neurons.elementAt(i)).activate();
		}
	}

	public void fire(){
		//for each neuron, fire if active
		for(int i=0;i<neurons.size();i++){
			((Neuron)neurons.elementAt(i)).fire();
		}
	}

	public void setThresholds(double d){
		for(int i=0;i<neurons.size();i++){
			((Neuron)neurons.elementAt(i)).setThreshold(d);
		}
	}

	public Vector getNeurons(){
		return neurons;
	}

	public void setNeurons(Vector n){
		neurons = n;
	}

	public Neuron getNeuron(int i){
		return ((Neuron)neurons.elementAt(i));
	}

	public int size(){
		return neurons.size();
	}

	public void printActivation(){
		for(int i=0;i<neurons.size();i++){
			System.out.print(((Neuron)neurons.elementAt(i)).getActivity()+" ");
		}
		System.out.println("");

	}
}
