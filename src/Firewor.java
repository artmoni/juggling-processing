
import java.util.ArrayList;
import java.util.Random;

import javax.xml.stream.events.ProcessingInstruction;

import processing.core.*;
import processing.serial.Serial;

class Firewor // Particle
{
	PVector[] lim = new PVector[2];
	int FrameRate = 60;

	PApplet parent;
	PVector pos; // (~10m)
	PVector vit; // (~10m/s = 36km/h)
	PVector acc; // (~10g)

	float mass; // (~100g);
	float ray;
	float ray_ecran;
	float dt;
	int rgb;

	int date_naissance;
	int date_mort;
	int niveau;
	boolean explosee;
	int[] rgbs = { parent.color(200, 0, 50), parent.color(0, 120, 200), parent.color(0, 5, 230),
			parent.color(0, 230, 160), parent.color(80, 160, 90), parent.color(250, 230, 50) };

	Firewor(float masse, PVector pos_i, PVector vit_i, PVector acc_i, int niveau_parent, int couleur) {
		mass = masse;
		pos = pos_i;
		vit = vit_i;
		acc = acc_i;

		ray = (float) (mass / 1000.0);
		ray_ecran = Math.abs(parent.map(ray, (float)0.0, lim[1].x,(float) 0.0, (float)(parent.displayWidth / 2.0)));
		dt = 1;
		rgb = parent.color(255, 0, 100);

		niveau = niveau_parent + 1;
		date_naissance = parent.frameCount;
		Random random = new Random();
		int rdm = random.nextInt(FrameRate / 2) -FrameRate / 2;
		date_mort = (int) (date_naissance + FrameRate * (1 * niveau + 1) - FrameRate / 4.0
				+ rdm);
		explosee = false;
		rgb = couleur;
	}

	Firewor(float masse, PVector pos_i, PVector vit_i, int niveau_parent, int couleur) {
		this(masse, pos_i, vit_i, new PVector(0, (float) (-0.02)), niveau_parent, couleur);
	}

	void dessine() {
		PVector pos_ecran = new PVector(parent.map(pos.x, lim[0].x, lim[1].x, 0, parent.width),
				parent.map(pos.y, lim[0].y, lim[1].y, parent.height, 0));
		if (ray_ecran == 0) {
			ray_ecran = 2;
		}
		parent.ellipseMode(parent.CENTER);
		parent.noStroke();
		parent.fill(rgb);
		parent.ellipse(pos_ecran.x, pos_ecran.y, 2 * ray_ecran, 2 * ray_ecran);
	}

	void actualise() {
		// Aléa
		float beta = parent.atan2(vit.y, vit.x);
		Random random = new Random();
		float rdm = (float)random.nextInt((int)(-parent.PI/512))+(parent.PI/512);
		float alph = rdm;
		float norme = parent.sqrt(parent.sq(vit.x) + parent.sq(vit.y));
		vit = new PVector((int)(norme * Math.cos(beta + alph)),(int)( norme * Math.sin(beta + alph)));

		// F° vitales
		vit = PVector.add(vit, PVector.mult(acc, dt));
		pos = PVector.add(pos, PVector.mult(vit, dt));
	}

	void explose() {
		explosee = true;
		Random random = new Random();
		int rdm = random.nextInt(20)+10;
		int Nfragments = (int) rdm - 10 * niveau;

		float beta = parent.atan2(vit.y, vit.x);
		float norme = parent.sqrt(parent.sq(vit.x) + parent.sq(vit.y));

		for (int i = 0; i < Nfragments; i++) {
			PVector pos_fragment = pos;
			rdm = (int)(random.nextInt((int)-parent.PI)+parent.PI);
			float alph = rdm;
			PVector vit_fragment = new PVector((int)(norme * Math.cos(beta + alph) / 10),(int)( norme * Math.sin(beta + alph) / 10));
			vit_fragment.mult(Nfragments / 5);
			int masse_fragment = (int) (mass / 2.0);
			Firewor b_inter = new Firewor(masse_fragment, pos_fragment, vit_fragment, niveau, rgb);
			firewors.add(b_inter);
		}
	}

	void display() {

//		parent.fill(00, 10);
//		parent.rect(0, 0, parent.width, parent.height);

		for (int i = 0; i < firewors.size(); i++) {
			Firewor f = (Firewor) firewors.get(i);
			f.actualise();
			f.dessine();
		}

		// Rajout durée de vie de la particule;
		for (int i = 0; i < firewors.size(); i++) {
			Firewor firewor = (Firewor) firewors.get(i);
			if (parent.frameCount > firewor.date_mort) {
				if (firewor.niveau < 2) {
					firewor.explose();
				}
				firewors.remove(i);
			}
		}
		if (pos.z < 0 || pos.z > 3) {
			Random random = new Random();
			int rdm = (int) (random.nextInt((int)(parent.PI / 2.0 - parent.PI / 32.0))+(parent.PI / 2.0 + parent.PI / 32.0));
			float beta = (float) rdm;
			rdm = random.nextInt(rgbs.length);
			int rgb1 = rgbs[(int) rdm];
			Firewor b_tiree = new Firewor(4000,
					new PVector(parent.map(pos.x, 0, parent.width, lim[0].x, lim[1].x), parent.map(pos.y, parent.height, 0, lim[0].y, lim[1].y)),
					new PVector((int)(10 * Math.cos(beta)),(int)( 10 * Math.sin(beta))), -1, rgb1); // ball_defaut;
			firewors.add(b_tiree);
		}
	}
	
}
