package ape;

/***
 * 
 *  TrialPair contains the correct input and output for one trial
 *
 */


public class TrialPair {
	
	private int seed;
	
	private int[] input;
	private int[] output;
	
	/*
	 * type: 
	 * 1 = A (B->LB)             100000000 -> 100000000
	 * 2 = B (B->LBLG) with bias 100000001 -> 100000010
	 * 3 = C1 (BG -> LBLG)       100000010 -> 100000010
	 * 4 = C2 (BG -> outputB)    100000010 -> 100000010
	 * @12/02/09:  
	 * 5 = D (B+bias -> G+bias)  100000001 -> 100000010
	 *                           010000001 -> 100000001
	 *                           001000001 -> 100000010
	 * 
	 */
	

	public TrialPair(int type, int number, int inputSize, int outputSize){
		
		seed = (number-1);
		
		input = constructInput(type, number, inputSize);
		output = constructOutput(type, number, outputSize);
		
	}
	
	public int[] constructInput(int type, int number, int inputSize){
		
		int[] encoding = new int[inputSize];
		for(int i=0;i<encoding.length;i++){
			encoding[i] = 0;
		}
		switch(type){
			case(1):{
				encoding[seed] = 1;
				break;
			}
			case(2):{
				encoding[seed] = 1;
				encoding[(inputSize-1)] = 1; //last bit is bias unit
				break;
			}
			case(3):{
				encoding[seed] = 1;
				if(seed%2==0){
					encoding[inputSize-2] = 1; //edible
				}
				else{
					encoding[inputSize-1] = 1; //drinkable
				}
				break;
			}
			case(4):{
				encoding[seed] = 1;
				if(seed%2==0){
					encoding[inputSize-2] = 1; //edible
				}
				else{
					encoding[inputSize-1] = 1; //drinkable
				}
				break;
			}
			case(5):{
				encoding[seed] = 1;
				encoding[(inputSize-1)] = 1; //last bit is bias unit
				break;
			}
			default:{
				System.out.println("TrialPair.constructInput: error");
				break;
			}	
		}
		
		return encoding;
	}
	
	public int[] constructOutput(int type, int number, int outputSize){
		int[] encoding = new int[outputSize];
		for(int i=0;i<encoding.length;i++){
			encoding[i] = 0;
		}
		switch(type){
			case(1):{
				encoding[seed] = 1;
				break;
			}
			case(2):{
				encoding[seed] = 1;
				if(seed%2==0){
					encoding[outputSize-2] = 1; //edible
				}
				else{
					encoding[outputSize-1] = 1; //drinkable
				}
				break;
			}
			case(3):{
				encoding[seed] = 1;
				if(seed%2==0){
					encoding[outputSize-2] = 1; //edible
				}
				else{
					encoding[outputSize-1] = 1; //drinkable
				}
				break;
			}
			case(4):{//will be changed anyway to the output of B
				encoding[seed] = 1;
				if(seed%2==0){
					encoding[outputSize-2] = 1; //edible
				}
				else{
					encoding[outputSize-1] = 1; //drinkable
				}
				break;
			}	
			case(5):{
				encoding[0] = 1;//first unit is bias unit
				if(seed%2==0){
					encoding[outputSize-2] = 1; //edible
				}
				else{
					encoding[outputSize-1] = 1; //drinkable
				}
				break;
			}
			default:{
				System.out.println("Error in Trialpair.constructOutput: type not found");
				break;
			}
		}
		return encoding;
	}
	
	
	
	/***** GET/SET *****/
	
	
	public int[] getInput(){
		return input;
	}
	
	public void setInput(int[] i){
		input = i;
	}
	
	public void setOutput(int[] o){
		output = o;
	}
	
	public int[] getOutput(){
		return output;
	}
	
	public void printPair(){
		System.out.println("input:");
		printArray(input);
		System.out.println("output:");
		printArray(output);
	}
	
	public void printArray(int[] array){
		for(int i=0;i<array.length;i++){
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}
}
