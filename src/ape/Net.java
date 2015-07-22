package ape;


/***
 * defines the neural network
 */


public class Net {
	
	private Layer inputLayer;
	private Layer hiddenLayer;
	private Layer outputLayer;
	
	private Connection ih;
	private Connection ho;
	
		
	public Net(int i, int h, int o){
		
		inputLayer = new Layer(i);
		hiddenLayer = new Layer(h);
		outputLayer = new Layer(o);
		
		ih = new Connection(inputLayer, hiddenLayer);
		ho = new Connection(hiddenLayer, outputLayer);
			
	}
	
	public void propagate(){
		inputLayer.fire();
		hiddenLayer.activate();
		hiddenLayer.fire();
		outputLayer.activate();
	}
	
	//sets the weights of the synapses equal to a weights array
	public void setWeights(double[] weights){
		int layer1Size = inputLayer.size()*hiddenLayer.size();
		int layer2Size = hiddenLayer.size()*outputLayer.size();
		if(weights.length != (layer1Size+layer2Size)){
			System.out.println("SetWeights: size of weights vector doesn't match number of synapses"+weights.length+" "+(ih.size()+ho.size()));
		}	
		double[] weightsIH = new double[layer1Size];
		
		for(int i=0;i<layer1Size;i++){
			weightsIH[i] = weights[i];
		}
		double[] weightsHO = new double[layer2Size]; 
		for(int i=0;i<layer2Size;i++){
			weightsHO[i] = weights[i+layer1Size];
		}
		
		ih.setWeights(weightsIH);
		ho.setWeights(weightsHO);
	}
	
	public void input(int[] a){
		if(a.length != inputLayer.size()){
			System.out.println("error: net.input");
		}
		else{
			for(int i=0;i<a.length;i++){
				if(a[i] == 1){
					inputLayer.getNeuron(i).setActivation(1.0);
				}
				else{
					inputLayer.getNeuron(i).setActivation(0.0);
				}
			}
		}
	}	
	
	/* the correct array is a, the function returns the error e
	 * 
	 */
	
	public int computeError(int[] a){
		//printLayers();
		int e = 0;
		int i = 0;
		while(e==0 && i<a.length){
			if(outputLayer.getNeuron(i).active()){
				if(a[i] == 0){
					e = 1;
				}
			}
			else{
				if(a[i] == 1){
					e = 1;
				}
			}
			i++;
		}
		return e;
	}
		
		
	/*****get-set*****/
	
	public int[] getOutput(){
		
		int[] a = new int[outputLayer.size()];
		for(int i=0;i<a.length;i++){
			a[i] = outputLayer.getNeuron(i).getActivity();
		}
		
		return a;
	}
	
	public Layer getInputLayer(){
		return inputLayer;
	}
	
	public Layer getHiddenLayer(){
		return hiddenLayer;
	}
	
	public Layer getOutputLayer(){
		return outputLayer;
	}
	
	public Connection getIH(){
		return ih;
	}
	
	public Connection getHO(){
		return ho;
	}
	
	public void printLayers(){
		System.out.println("input layer: ");
		inputLayer.printActivation();
		System.out.println("hidden layer: ");
		hiddenLayer.printActivation();
		System.out.println("output layer: ");
		outputLayer.printActivation();
		System.out.println("");
	}
}


