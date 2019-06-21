package it.polito.tdp.formulaone.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo(2013);
		System.out.println("Grafo creato con "+ model.getGrafo().vertexSet().size()+" vertici e "+model.getGrafo().edgeSet().size()+" archi\n");
		System.out.println(model.migliore());
		System.out.println(model.getBestDriver());
		System.out.println(model.getDreamTeam(2));
	}

}
