package modele;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import morpions.kit.test.SpecifModeleMorpions;

public class ModeleMorpionFX implements SpecifModeleMorpions {

	//int[][] grille= new int[TAILLE][TAILLE];
	
	private ReadOnlyIntegerWrapper nbCoups;
	
	private StringProperty symboleJoueurCourant;// Symbole désignant le joueur courant
	
	public ModeleMorpionFX() {
		nbCoups= new ReadOnlyIntegerWrapper(0);
		symboleJoueurCourant = new ReadOnlyStringWrapper("x");//symbole joueur courant 1 au départ
		plateau= new ReadOnlyIntegerWrapper[TAILLE][TAILLE];
		for (int i=0; i<TAILLE; i++) {
			for (int j=0; j<TAILLE;j++) {
				plateau[i][j]=new ReadOnlyIntegerWrapper(0);
			}
		}
		
	}
	
	public ReadOnlyIntegerProperty nbCoupsProperty(){
	return nbCoups.getReadOnlyProperty() ; 
	}
	
	public StringProperty symboleJoueurCourantProperty(){
	return this.symboleJoueurCourant;
	}
	// Accesseurs "Java Bean" sur la valeur encapsulée
	public String getSymboleJoueurCourant(){
	return symboleJoueurCourant.getValue();
	}
	@SuppressWarnings("unused")
	private void setSymboleJoueurCourant(String ch){
	symboleJoueurCourant.setValue(ch);
	}
	
	private ReadOnlyIntegerWrapper[][] plateau;

	public ReadOnlyIntegerProperty casePlateauProperty(int ligne, int colonne){
		return plateau[ligne-1][colonne-1].getReadOnlyProperty() ;
	}

	private int getVal(int ligne, int colonne){
		return plateau[ligne][colonne].getValue() ;
	}
	
	private void setVal(int ligne, int colonne, int val){
		plateau[ligne-1][colonne-1].setValue(val);
	}

	@Override
	public Etat getEtatJeu() {
		if(!estFinie()) {
			if(getJoueur()==1) {return Etat.J1_JOUE;}
			return Etat.J2_JOUE;
		}else{
			int v=getVainqueur();
			if (v==0) {return Etat.MATCH_NUL;}
			else if(v==1) {return Etat.J1_VAINQUEUR;}
			else {return Etat.J2_VAINQUEUR;}
		}
	}

	@Override
	public int getJoueur() {
		if (estFinie()) {return 0;}
		int j1=0;
		int j2=0;
		for (int i=0; i<TAILLE; i++) {
			for (int j=0; j<TAILLE;j++) {
				if(getVal(i,j)==1) {j1++;}
				else if(getVal(i,j)==2) {j2++;}
			}
		}
		if (j1>j2) {return 2;}
		return 1;
	}

	@Override
	public int getVainqueur() {
		
		int ligne1=getVal(0,0)*getVal(0,1)*getVal(0,2);
		int ligne2=getVal(1,0)*getVal(1,1)*getVal(1,2);
		int ligne3=getVal(2,0)*getVal(2,1)*getVal(2,2);
		int colonne1=getVal(0,0)*getVal(1,0)*getVal(2,0);
		int colonne2=getVal(0,1)*getVal(1,1)*getVal(2,1);
		int colonne3=getVal(0,2)*getVal(1,2)*getVal(2,2);
		int diagonale1=getVal(0,0)*getVal(1,1)*getVal(2,2);
		int diagonale2=getVal(2,0)*getVal(1,1)*getVal(0,2);
		boolean v1= ligne1==1||ligne2==1||ligne3==1||colonne1==1||colonne2==1||colonne3==1||diagonale1==1||diagonale2==1;
		boolean v2= ligne1==8||ligne2==8||ligne3==8||colonne1==8||colonne2==8||colonne3==8||diagonale1==8||diagonale2==8;
      if(v1) {return 1;}else if(v2) {return 2;}else {return 0;}
	}

	@Override
	public int getNombreCoups() {

		return nbCoups.intValue();
	}
	
	@Override
	public boolean estFinie() {
		return getVainqueur()!=0||getNombreCoups()==9;
	}

	@Override
	public boolean estCoupAutorise(int ligne, int colonne) {
		return ligne-1>=0&&ligne-1<TAILLE&&colonne-1>=0&&colonne-1<TAILLE&&getVal(ligne-1,colonne-1)==0;
	}

	@Override
	public void jouerCoup(int ligne, int colonne) {
		//assert !estFinie() : "partie terminé";
		//assert estCoupAutorise(ligne,colonne) : "coup non-autorisé";
		if(!estFinie()&&estCoupAutorise(ligne,colonne)) {
		if (getJoueur()==1) {
			setVal(ligne,colonne, 1);
			symboleJoueurCourant.setValue(symboleJoueur(2));}
		else {
			setVal(ligne,colonne, 2);
			symboleJoueurCourant.setValue(symboleJoueur(1));}
		nbCoups.setValue(getNombreCoups()+1);
		
		}
	}
	
	public String symboleJoueur(int val){
		switch (val){
		case 1 : return "x" ;
		case 2 : return "o" ;
		default : return " " ;
		}
	}


}
