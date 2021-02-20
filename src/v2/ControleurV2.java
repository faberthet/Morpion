package v2;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.ModeleMorpionFX;
//import morpions.kit.test.SpecifModeleMorpions.Etat;

public class ControleurV2 {
	
	private ModeleMorpionFX morpion;
	
	@FXML // pour rendre la variable visible depuis SceneBuilder
	private GridPane grille;
	
	@FXML
	private Label labelNbCoups;
	@FXML
	private Label labelEtatJeu;
	
	@FXML
	private Label labelJoueur;

	@FXML // pour rendre la méthode visible depuis SceneBuilder
	private void initialize() {
		
		morpion= new ModeleMorpionFX();
		for (Node n : grille.getChildren())
		{ n.setOnMouseClicked(e -> this.clicBouton(e)); }
		
		morpion.nbCoupsProperty().addListener
		(
		(obsValue, oldValue, newValue) ->
		majNbCoups(newValue.intValue())
		);

		labelJoueur.textProperty()
		.bind(morpion.symboleJoueurCourantProperty());
		
		for (Node enfant : grille.getChildren()){
			// enfants de "grille": des Labels, mais pas que...
			if (enfant instanceof Label){
				Label l = (Label) enfant;
				int ligne = (int) l.getProperties().get("gridpane-row")+1 ;
				int colonne = (int) l.getProperties().get("gridpane-column")+1 ;
				morpion.casePlateauProperty(ligne, colonne)
				.addListener
				( (obs, oldV, newV)-> l.setText(morpion.symboleJoueur(newV.intValue())));
			}
		}
		 

	}

		private void clicBouton(MouseEvent e){ 
			Node n = (Node) e.getSource() ;
			Integer ligne = ((Integer) n.getProperties().get("gridpane-row"))+1;
			Integer colonne = ((Integer) n.getProperties().get("gridpane-column"))+1;
			if(morpion.estCoupAutorise(ligne, colonne)&&!morpion.estFinie()) { 
				morpion.jouerCoup(ligne, colonne) ;recalculerLabelEtatJeu(); 
				System.out.println("Coup joué : " + ligne + "/" + colonne) ;
				System.out.println("résultat: " + morpion.getEtatJeu()) ;
			}
		}
	
	private void recalculerLabelEtatJeu() {
		
		//if(morpion.getEtatJeu()== Etat.J1_JOUE) { labelEtatJeu.setText("C'est au tour de ..."); }
		switch(morpion.getEtatJeu()) {

			case J1_JOUE : labelEtatJeu.setText("C'est au tour du joueur 1"); break;
			case J2_JOUE : labelEtatJeu.setText("C'est au tour du joueur 2"); break;
			case MATCH_NUL : labelEtatJeu.setText("Match nul"); break;
			case J1_VAINQUEUR : labelEtatJeu.setText("joueur 1 vainqueur"); break;
			case J2_VAINQUEUR : labelEtatJeu.setText("joueur 2 vaiqueur"); break;
		}

	}
	private void majNbCoups(int nb){ 
		if (nb == 0){
			labelNbCoups.setText("aucun coup joué");
		}
		else{
			String ch ;
			if (nb == 1) ch = " coup joué" ;
			else ch = " coups joués" ;
			labelNbCoups.setText(Integer.toString(nb) + ch);
		}
	}

	
}
