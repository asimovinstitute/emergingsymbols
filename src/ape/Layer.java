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