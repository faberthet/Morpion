import morpions.kit.test.SpecifModeleMorpions;

public class MorpionsImp implements SpecifModeleMorpions {

	
	int[][] grille= new int[TAILLE][TAILLE];
	
	
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
				if(grille[i][j]==1) {j1++;}
				else if(grille[i][j]==2) {j2++;}
			}
		}
		if (j1>j2) {return 2;}
		return 1;
	}

	@Override
	public int getVainqueur() {
		
		int ligne1=grille[0][0]*grille[0][1]*grille[0][2];
		int ligne2=grille[1][0]*grille[1][1]*grille[1][2];
		int ligne3=grille[2][0]*grille[2][1]*grille[2][2];
		int colonne1=grille[0][0]*grille[1][0]*grille[2][0];
		int colonne2=grille[0][1]*grille[1][1]*grille[2][1];
		int colonne3=grille[0][2]*grille[1][2]*grille[2][2];
		int diagonale1=grille[0][0]*grille[1][1]*grille[2][2];
		int diagonale2=grille[2][0]*grille[1][1]*grille[0][2];
		boolean v1= ligne1==1||ligne2==1||ligne3==1||colonne1==1||colonne2==1||colonne3==1||diagonale1==1||diagonale2==1;
		boolean v2= ligne1==8||ligne2==8||ligne3==8||colonne1==8||colonne2==8||colonne3==8||diagonale1==8||diagonale2==8;
      if(v1) {return 1;}else if(v2) {return 2;}else {return 0;}
	}

	@Override
	public int getNombreCoups() {
		int res=0;
		for (int i=0; i<TAILLE; i++) {
			for (int j=0; j<TAILLE;j++) {
				if(grille[i][j]!=0) {res++;}	
			}
		}
		return res;
	}

	@Override
	public boolean estFinie() {
		return getVainqueur()!=0||getNombreCoups()==9;
	}

	@Override
	public boolean estCoupAutorise(int ligne, int colonne) {
		return ligne-1>=0&&ligne-1<TAILLE&&colonne-1>=0&&colonne-1<TAILLE&&grille[ligne-1][colonne-1]==0;
	}

	@Override
	public void jouerCoup(int ligne, int colonne) {
		assert !estFinie() : "partie terminé";
		assert estCoupAutorise(ligne,colonne) : "coup non-autorisé";
		
		if (getJoueur()==1) {grille[ligne-1][colonne-1]=1;}
		else {grille[ligne-1][colonne-1]=2;}
		
	}

}
