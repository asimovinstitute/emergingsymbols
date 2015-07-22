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
 * ResultDisplay showns the lowest average error after each generation in a graph
 */
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ResultDisplay extends JPanel{

	private double[] results;
	private int generation;

	public ResultDisplay(){
	}

	public void paintComponent(Graphics g){

		//paint Background;
		g.setColor(Color.WHITE);
		g.fillRect(0,0,this.getWidth(),this.getHeight());

		int xOffset = Math.round(this.getWidth()/10);
		int yOffset = Math.round(this.getHeight()/10);
		double xaxis = (this.getWidth()-(2*xOffset));
		double yaxis = (this.getHeight()-(2*yOffset));


		try {

			int xaxis1 = xOffset;
			int xaxis2 = (int)Math.round(xOffset+(results.length)*(xaxis/results.length));

			//draw coordinates system
			g.setColor(Color.BLACK);
			g.drawLine(xOffset, (this.getHeight()-yOffset), xOffset, yOffset);

			g.setColor(Color.BLACK);
			g.drawString("gen",this.getWidth()-xOffset, this.getHeight()-10);
			g.drawLine(xaxis1, (this.getHeight()-yOffset), xaxis2, (this.getHeight()-yOffset));

			g.setColor(Color.RED);
			g.drawString("0.5",Math.round(xOffset/4),(int)Math.round(this.getHeight()/2));
			g.drawLine(xaxis1, Math.round(this.getHeight()/2),xaxis2, Math.round(this.getHeight()/2));

			g.setColor(Color.RED);
			g.drawString("0.25",Math.round(xOffset/4),(int)Math.round(yOffset+(yaxis*3/4)));
			g.drawLine(xaxis1, (int)Math.round(yOffset+(yaxis*3/4)),xaxis2, (int)Math.round(yOffset+(yaxis*3/4)));

			g.setColor(Color.RED);
			g.drawString("0.75",Math.round(xOffset/4),(int)Math.round(yOffset+(yaxis/4)));
			g.drawLine(xaxis1, (int)Math.round(yOffset+(yaxis/4)),xaxis2, (int)Math.round(yOffset+(yaxis/4)));

			//Results: blue line
			g.setColor(Color.BLUE);

				int scale = 1;
				while(results.length/scale > 1){
					scale = scale * 10;
				}
				scale = scale/100;
				for(int i=0;i<generation - scale;i = i + scale){

					int x1 = (int)Math.round(xOffset+i*(xaxis/results.length));
					int x2 = (int)Math.round(xOffset+(i+scale)*(xaxis/results.length));

					int y1 = (int)Math.round((this.getHeight() - yOffset) - (yaxis * results[i]));
					int y2 = (int)Math.round((this.getHeight() - yOffset) - (yaxis * results[i+scale]));

					g.drawLine(x1, y1, x2, y2);
			}
		}
		catch (Exception e) {

		}
	}

	public void update(int g, double[] r){
		generation = g;
		results = r;
		this.repaint();
	}

	public void printArray(double[] array){
		for(int i=0;i<array.length;i++){
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}

	public void setResults(double[] r){
		results = r;
	}
}
