package v1;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.ModeleMorpionFX;

public class ControleurV1 {
	
	private ModeleMorpionFX morpion;
	
	@FXML // pour rendre la variable visible depuis SceneBuilder
	private GridPane grille;
	
	@FXML // pour rendre la méthode visible depuis SceneBuilder
	private void initialize() {
		
		morpion= new ModeleMorpionFX();
		for (Node n : grille.getChildren())
		{ n.setOnMouseClicked(e -> this.clicBouton(e)); }
	}

	private void clicBouton(MouseEvent e)
	{ Node n = (Node) e.getSource() ;
	 Integer ligne
	 = ((Integer) n.getProperties().get("gridpane-row"))+1;
	 Integer colonne
	 = ((Integer) n.getProperties().get("gridpane-column"))+1;
	 morpion.jouerCoup(ligne, colonne) ;
	 System.out.println("Coup joué : " + ligne + "/" + colonne) ;
	 System.out.println("résultat: " + morpion.getEtatJeu()) ;
	}
	
}
