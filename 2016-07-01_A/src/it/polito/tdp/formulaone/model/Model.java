package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private Map<Integer, Driver> mapDriver;
	private FormulaOneDAO dao;
	private Graph<Driver, DefaultWeightedEdge> grafo;
	
	public Model() {
		mapDriver = new HashMap<>();
		dao = new FormulaOneDAO();
		dao.getAllDrivers(mapDriver);
	}
	
	public void creaGrafo(int stagione) {
		grafo = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
		for(Adiacenza a : dao.getAdiacenze(stagione, mapDriver)) {
			Graphs.addEdgeWithVertices(grafo, a.getD1(), a.getD2(), a.getPeso());
		}
				
	}

	public Graph<Driver, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<Driver, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public List<Season> getAnni(){
		return dao.getAllSeasons();
	}
}
