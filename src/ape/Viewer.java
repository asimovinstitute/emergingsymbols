package ape;

/***
 * 
 * Viewer sets up the interface, assigns panels and handles input.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Viewer {
	
	private JFrame frame;
	ResultDisplay rdisplay = new ResultDisplay(); 
		
	public Viewer(){
	
		//look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exception) {
			System.out.println("Program failed on look and feel");
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		rdisplay = new ResultDisplay();
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Results");
		rdisplay.setBorder(title);
	
        frame = new JFrame("APEViewer");
		frame.setSize(800,500);
		frame.setLocation(100,100);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	
		//specify component layout
		//top panel
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(800,500));
				
		top.setLayout(new GridLayout(1,0));
		top.add(rdisplay);
		
		//frame
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(top, BorderLayout.CENTER);
			
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update(int generation, double[] results, int tag){
		frame.setTitle("APEViewer -- Experiment "+tag+" @ generation: "+(generation+1)+" error: "+results[generation]);
		rdisplay.update(generation, results);
	}

	public void updateT(int trialNumber, int totalT, int avgOverNumber, int totalA, double d){
		int i = (int)Math.round(d);
		if(trialNumber == 1){//first
			System.out.print("Run "+avgOverNumber+"/"+totalA+":  "+ i);
		}
		else if(trialNumber == totalT){//last
			System.out.println(" "+i);
		}
		else{//middle
			System.out.print(" "+i);
		}
	}
}
