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