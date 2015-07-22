package ape;
import java.util.*;

public class Connection {
	
	private Vector synapses;
	
	private Layer from;
	private Layer to;
	
	public Connection(Layer f, Layer t) {
		
		synapses = new Vector();
		from = f;
		to = t;
		
		fullConnect();
	}
	
	public void fullConnect(){
		for(int i=0;i<from.size();i++){
			for(int j=0;j<to.size();j++){
				synapses.add(new Synapse(from.getNeuron(i), to.getNeuron(j)));	
			}
		}	
	}
	
	public void setWeights(double[] weights){
		if(weights.length != synapses.size()){
			System.out.println("error: connection.setWeights" + weights.length +" "+synapses.size());
		}
		else{
			for(int i=0;i<weights.length;i++){
				((Synapse)synapses.elementAt(i)).setWeight(weights[i]);
			}
		}
	}
	
	
	
	
	public Vector getSynapses(){
		return synapses;
	}
	
	public void setSynapses(Vector s){
		synapses = s;
	}
	
	public Synapse getSynapse(int i){
		return ((Synapse)synapses.elementAt(i));
	}
	
	public int size(){
		return synapses.size();
	}
}