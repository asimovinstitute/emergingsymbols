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
 * A synapse connects two neurons to eachother, with a certain weight (also called strength)
 *
 */
public class Synapse {

	private Neuron from;
	private Neuron to;

	private boolean active;

	// the strength of the connection [0..1]
	private double weight;

	public Synapse(Neuron f, Neuron t) {
		from = f;
		to = t;

		//update neuron connections
		f.addOutConnection(this);
		t.addInConnection(this);

		weight = Math.random();//0;
		active = false;
	}

	//default constructor: only used for avoiding errors
	public Synapse(){
	}

	public double getWeight(){
		return weight;
	}

	public void setWeight(double w){
		weight = w;
	}

	public boolean active(){
		return active;
	}

	public void setActive(boolean a){
		active = a;
	}

	public Neuron getFrom(){
		return from;
	}

	public Neuron getTo(){
		return to;
	}
}
