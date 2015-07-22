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

/***
 *
 * A neuron has a vector of incoming synapses, a vector of outgoing synapses,
 * and a threshold to determine the firing rate
 *
 */

public class Neuron {

	private double threshold;
	private double activation;

	//incoming dendrites, Vector of Synapses
	private Vector in;

	//outgoing axons, Vector of Synapses
	private Vector out;

	public Neuron() {

		in = new Vector();
		out = new Vector();

		activation = 0.0;
		threshold = 0.85;//0.65//0.5	1.0 is too much, only 1/(8*7*2) first layer solutions are correct (and most likely discarded in the next generation)
	}

	//make the neuron active or inactive, based on the incoming synapses
	public void activate(){
		double sum = 0.0;
		for(int i=0;i<in.size();i++){
			if(((Synapse)in.elementAt(i)).active()){
				double fromNeuronActivation = ((Neuron)((Synapse)in.elementAt(i)).getFrom()).activation;
				sum += ((Synapse)in.elementAt(i)).getWeight() * fromNeuronActivation;
			}
		}
		activation = sum;
		//System.out.println(""+activation);
	}

	//fire if active, else make outgoing synapses inactive
	public void fire(){
		for(int i=0;i<out.size();i++){
			((Synapse)out.elementAt(i)).setActive(this.active());
		}
	}

	public boolean active(){
		return (activation>=threshold);
	}

	public void setActivation(double a){
		activation = a;
	}

	public double getThreshold(){
		return threshold;
	}

	public int getActivity(){
		return(active()?1:0);
	}

	public void setThreshold(double t){
		threshold = t;
	}

	public void addInConnection(Synapse s){
		in.addElement(s);
	}

	public void addOutConnection(Synapse s){
		out.addElement(s);
	}

	public Vector getInConnections(){
		return in;
	}

	public Vector getOutConnections(){
		return out;
	}
}
