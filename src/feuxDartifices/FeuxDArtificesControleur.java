package feuxDartifices;

import java.util.ArrayList;

import processing.core.*;

public class FeuxDArtificesControleur {

	ArrayList<FeuDartifice> feux;
	PVector pVector;
	PApplet parent;

	public FeuxDArtificesControleur(ArrayList<FeuDartifice> listeFeux, PVector pv, PApplet parent) {
		// TODO Auto-generated constructor stub
		this.feux = listeFeux;
		this.pVector = pv;
		this.parent = parent;
	}
	
	public FeuxDArtificesControleur(PApplet parent) {
		// TODO Auto-generated constructor stub
		this.feux = new ArrayList<>();
		this.pVector = new PVector();
		this.parent = parent;
	}

	// public void settings() {
	// size(displayWidth / 2, displayHeight / 2);
	// feux = new ArrayList<FeuDartifice>();
	// }

	public void display() {
		// frameRate(10);
		// background(0);
		parent.loadPixels();
		for (int i = 0; i < parent.width * parent.height; i++)
			if (i > 0 && i % parent.width > 0)
				parent.pixels[i - 1] = parent.pixels[i];
		parent.updatePixels();
		for (int i = 0; i < feux.size(); i++) {
			if (feux.get(i).getListe().isEmpty()) {
				feux.remove(i);
			} else
				feux.get(i).display();
		}
		// System.out.println(feux.size());

	}

	public void addFeu() {
		feux.add(new FeuDartifice(this.pVector, this.parent));
	}

}
