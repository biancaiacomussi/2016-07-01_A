package it.polito.tdp.formulaone.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo(2013);
		System.out.println("Grafo creato con "+ model.getGrafo().vertexSet().size()+" vertici e "+model.getGrafo().edgeSet().size()+" archi\n");
	}

}
