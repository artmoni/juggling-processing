package feuxDartifices;
import java.util.ArrayList;
import java.util.Random;

import processing.core.*;

public class FeuDartifice {

	int SIZE_LIST = 10;

	ArrayList<Particule> listParticule;
	PVector pVector;
	PApplet parent;

	public FeuDartifice(PApplet parent) {
		this.listParticule = new ArrayList<Particule>();
		this.pVector = new PVector();
		this.parent = parent;
	}

	public FeuDartifice(PVector pv, PApplet parent) {
		this.listParticule = new ArrayList<Particule>();
		this.pVector = pv;
		this.parent = parent;
		creerListeParticule(pv);
	}

	Particule creerParticule(PVector pv) {
		Particule particule;
		Random random = new Random();
		int color = parent.color(random.nextInt(255) + 1, random.nextInt(255) + 1, random.nextInt(255) + 1);
		int form = random.nextInt(3);
		float vX = (float) (random.nextInt(10) - 10);
		float vY = (float) (random.nextInt(10) - 10);

		switch (form) {
		case 0:
			particule = new ParticuleRonde(pv, color, this.parent, vX, vY);
			break;
		case 1:
			particule = new ParticuleCarre(pv, color, this.parent, vX, vY);
			break;
		default:
			particule = new ParticuleChelou(pv, color, parent, vX, vY);
		}
		return particule;
	}

	public void creerListeParticule(PVector pv) {
		for (int i = 0; i < SIZE_LIST; i++) {
			pv.set(pv.x + (i * 2), pv.y + (i * 2));
			Particule particule = creerParticule(pv);
			listParticule.add(particule);
		}
	}

	public void display() {
		for (int i = 0; i < listParticule.size(); i++) {
			if (listParticule.get(i).deplacer()) {
				listParticule.get(i).display();
				listParticule.get(i).gravity();
			} else
				listParticule.remove(i);

//			System.out.println(listParticule.size());
		}
	}

	public ArrayList<Particule> getListe() {
		return this.listParticule;
	}

}
