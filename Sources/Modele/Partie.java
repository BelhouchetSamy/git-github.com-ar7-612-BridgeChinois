package Modele;

import java.util.ArrayList;

import Patterns.Observable;

import Controleur.IA;

public class Partie extends Observable {
    private Historique<Coup> histo;

    ArrayList<Manche> Manches;
    ArrayList<Carte> cartepiochevisible;
    Manche manchecourante;
    Joueur j1, j2;
    String Modedejeu;
    boolean manchefin;
    public boolean debutpartie, finpartie;
    int phase, phasetour, nbmanche, nMax, score1, score2;
    int donneurdebut;
    boolean J1EstIA;
    boolean J2EstIA;
    IA joueur1IA;
    IA joueur2IA;

    public Partie() {
        j1 = new Joueur();
        j2 = new Joueur();
        donneurdebut = 1;
        manchecourante = new Manche(j1, j2, donneurdebut);
        phase = 4;
        phasetour = 0;
        nbmanche = 1;
        score1 = 0;
        score2 = 0;
        debutpartie = true;
        finpartie = false;
        histo = new Historique<Coup>();

        Manches = new ArrayList<Manche>();
        Manches.add(manchecourante);
    }
    
    public Carte[] CartevisiblePile() {
    	return manchecourante.CartevisiblePile();
    }
    
    public int atout() {
        return manchecourante.atout;
    }

    public Boolean JouableSec(int arg) {
        if (manchecourante.donneur == 1) {

            if (manchecourante.j2.jouables(manchecourante.cartePremier().couleur) > 0)
                return manchecourante.j2.main.carte(arg).couleur == manchecourante.cartePremier().couleur;
            else
                return true;
        } else {
            if (manchecourante.j1.jouables(manchecourante.cartePremier().couleur) > 0)
                return manchecourante.j1.main.carte(arg).couleur == manchecourante.cartePremier().couleur;

            else
                return true;
        }
    }

    public int scoremanchej1() {
        return manchecourante.j1.scoreManche;
    }

    public int scoremanchej2() {
        return manchecourante.j2.scoreManche;
    }

    public int nbmanche() {
        return nbmanche;
    }

    public void jouerCoup(Coup cp) {

        cp.fixerpartie(this);
        histo.nouveau(cp);
        miseAJour();
    }

    void nouvellemanche() {
        if (donneurdebut == 1)
            donneurdebut = 2;
        manchecourante = new Manche(j1, j2, donneurdebut);
        Manches.add(manchecourante);
        phasetour = 0;
        phase = 4;
    }

    public Carte[] cartevisiblepioche() {
        return manchecourante.CartevisiblePile();
    }

    public Joueur joueurDonneur() {
        if (manchecourante.quiDonne() == 1)

            return manchecourante.j1;
        else
            return manchecourante.j2;
    }

    public Joueur joueurReceveur() {
        if (manchecourante.quiRecois() == 1)

            return manchecourante.j1;
        else
            return manchecourante.j2;
    }

    public void testFinPartie() {
        if (nbmanche == nMax + 1 && Modedejeu.equals("m")) {
            finpartie = true;
        } else if ((manchecourante.j1.scorePartie == nMax || manchecourante.j2.scorePartie == nMax)
                && Modedejeu.equals("p")) {
            finpartie = true;
        } else {
            finpartie = false;
        }

    }

    public Coup determinerCoup(int codecoup, int arg) {
        Coup resultat = new Coup(codecoup, arg);
        return resultat;
    }

    public int phasetour() {
        return phasetour;
    }

    public int phase() {
        return phase;
    }

    public int quiDonne() {
        return manchecourante.quiDonne();
    }

    public int quiRecois() {
        return manchecourante.quiRecois();
    }

    public Boolean pilevide(int arg) {
        return manchecourante.pilevide(arg);
    }

    public boolean manchefini() {
        return manchecourante.Manchefini();
    }

    public boolean tourfini() {
        return phasetour % phase == 0;
    }

    public String cartePremCouleur() {
        String s = null;
        switch (manchecourante.cartePremier().couleur()) {
            case 1:
                s = "TREFLE";
                return s;
            case 2:
                s = "CARREAU";
                return s;
            case 3:
                s = "COEUR";
                return s;
            case 4:
                s = "PIQUE";
                return s;
            default:
                return s;
        }
    }

    public Carte cartePrem() {
        return manchecourante.cartePremier();
    }

    public Carte carteSec() {
        return manchecourante.carteSeconde();
    }

    public void ModeV(String mode, int nombre) {
        Modedejeu = mode;
        nMax = nombre;
        testFinPartie();
    }

    public int quiJoue() {
        switch (phasetour()) {
            case 0:
                return quiDonne();
            case 1:
                return quiRecois();
            case 2:
                return quiGagnetour();
            case 3:
                if (quiGagnetour() == 1)
                    return 2;
                else
                    return 1;
            default:
                return -1;
        }
    }

    public void ModeJoueur(int joueur, String mode) {
    	if(mode.equals("non")) {
    		if(joueur==1) {
        		J1EstIA=false;
        	}else {
        		J2EstIA=false;
        	}
    	} else {
    		if(joueur==1) {
        		J1EstIA=true;
        		joueur1IA = IA.creerIA(this,j1,j2,1,mode);
        	}else {
        		J2EstIA=true;
        		joueur2IA = IA.creerIA(this,j1,j2,2,mode);
        	}
    	}
    }
    

    public boolean estIA(int joueur) {
        if (joueur == 1) {
            return J1EstIA;
        } else {
            return J2EstIA;
        }
    }

    public int getCoupIA(int joueur) {
        if (joueur == 1) {
            return joueur1IA.jouerCoup();
        } else {
            return joueur2IA.jouerCoup();
        }
    }

    public boolean partifini() {
        return finpartie;
    }

    public int scorePartiej1() {
        return manchecourante.j1.scorePartie;
    }

    public int phasetourterm() {

        return phasetour % phase;
    }
  
    public int scorePartiej2() {
        return manchecourante.j2.scorePartie;
    }

    public int quiGagnePartie() {
        if (Modedejeu.equals("m")) {
            if (manchecourante.j1.manchesGagnees > manchecourante.j2.manchesGagnees)
                return 1;
            else if (manchecourante.j1.manchesGagnees < manchecourante.j2.manchesGagnees) {
                return 2;
            }

        } else {
            if (manchecourante.j1.scorePartie > manchecourante.j2.scorePartie)
                return 1;
            else {
                return 2;
            }
        }
        return 0;
    }

    public int quiGagneManche() {
        if (manchecourante.j1.scoreManche > manchecourante.j2.scoreManche)
            return 1;
        else if (manchecourante.j1.scoreManche < manchecourante.j2.scoreManche) {
            return 2;
        }
        return 0;
    }

    public void annulleCoup(Coup cp) {
        cp.fixerpartie(this);
        histo.annuler();
        miseAJour();
    }

    public void refaireCoup(Coup cp) {
        cp.fixerpartie(this);
        histo.refaire();
        miseAJour();
    }

    public int quiGagnetour() {
        return manchecourante.quigagnetour();
    }

    public int nbmanchegagnej1() {
        return manchecourante.nbmanchegagnej1();
    }

    public int nbmanchegagnej2() {
        return manchecourante.nbmanchegagnej2();
    }
    public int quiGagnetourav() {
        return manchecourante.quigagnetourav();
    }
    public Carte cartePremav() {
        return manchecourante.cartePremierav();
    }
    public Carte carteSecav() {
        return manchecourante.carteSecondeav();
    }
}
