package feuxDartifices;

import java.util.ArrayList;

import processing.core.*;

public class FeuxDArtificesControleur {

	ArrayList<FeuDartifice> feux;
	PApplet parent;

	public FeuxDArtificesControleur(PApplet parent) {
		this.feux = new ArrayList<>();
		this.parent = parent;
	}

	public void display() {
		// parent.loadPixels();
		// for (int i = 0; i < parent.width * parent.height; i++)
		// if (i > 0 && i % parent.width > 0)
		// parent.pixels[i - 1] = parent.pixels[i];
		// parent.updatePixels();
		for (int i = 0; i < feux.size(); i++) {
			if (feux.get(i).getListe().isEmpty()) {
				feux.remove(i);
			} else
				feux.get(i).display();
		}
	}

	public void addFeu(PVector startFire) {
		feux.add(new FeuDartifice(startFire, this.parent));
	}

}
