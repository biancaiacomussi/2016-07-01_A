package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.LinkedList;
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
	private List<Driver> ottima;
	private int best;
	
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
	
	public Driver migliore() {
		Driver migliore = null;
		int best = Integer.MIN_VALUE;
		
		for(Driver d: grafo.vertexSet()) {
			int sum = 0;
			
			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(d)) {
				sum += grafo.getEdgeWeight(e);
			}
			
			// Itero sugli archi entranti
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(d)) {
				sum -= grafo.getEdgeWeight(e);
			}
			
			if(sum>best || migliore==null) {
				migliore = d;
				best = sum;
			}
		}
		
		return migliore;
	}
	
	public Driver getBestDriver() {
		if (grafo == null) {
			new RuntimeException("Creare il grafo!");
		}
		
		// Inizializzazione
		Driver bestDriver = null;
		int best = Integer.MIN_VALUE;
		
		for (Driver d : grafo.vertexSet()) {
			int sum = 0;
			
			// Itero sugli archi uscenti
			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(d)) {
				sum += grafo.getEdgeWeight(e);
			}
			
			// Itero sugli archi entranti
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(d)) {
				sum -= grafo.getEdgeWeight(e);
			}
			
			if (sum > best || bestDriver == null) {
				bestDriver = d;
				best = sum;
			}
		}
		
		if (bestDriver == null) {
			new RuntimeException("BestDriver not found!");
		}
		
		return bestDriver;
	}
	
	public List<Driver> getDreamTeam(int k){
		this.ottima = new LinkedList<Driver>();
		best = Integer.MAX_VALUE;
		List<Driver> parziale = new LinkedList<Driver>();
		
	
		cerca(parziale, k);
		
		return this.ottima;
	}

	private void cerca(List<Driver> parziale, int k) {
		
		
		//ottengo tutti i candidati
		List<Driver> candidati = new LinkedList<>(grafo.vertexSet());
		for(Driver candidato : candidati) {
			
			if(parziale.size()>=k) {
				if(parziale.size()==k && best>this.tassoSconfitta(parziale)) {
					this.ottima = new LinkedList<>(parziale);
					best = this.tassoSconfitta(parziale);
				}
				return;
			}
				
			
			if(!parziale.contains(candidato)) {
				//è un candidato che non ho ancora considerato
				parziale.add(candidato);
				this.cerca(parziale, k);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
		//vedere se la soluzione corrente è migliore della ottima corrente
		
		
		
	}
	
	public int tassoSconfitta(List<Driver> parziale) {
		int tasso = 0;
		for(Driver d : parziale) {
			for(DefaultWeightedEdge e : grafo.incomingEdgesOf(d)) {
				tasso += grafo.getEdgeWeight(e);
			}
		}
		return tasso;
	}
}
