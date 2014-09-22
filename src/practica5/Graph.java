package practica5;

import java.util.ArrayList;

public class Graph {

    private int indice;
    private String nombre;
    private ArrayList<Nodo> adjNodos;
    private int n = 0;

    public Graph(int indice) {
        this.indice = indice;
        adjNodos = new ArrayList<Nodo>();
    }

    public void agregarAdyacencia(int i, double d, String nombre) {
        adjNodos.add(new Nodo(i, d, nombre));
    }

    public ArrayList<Nodo> getAdjnodes() {
        return adjNodos;
    }

    public int getInd() {
        return indice;
    }

    public int getN() {
        return n;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Graph{" + "indice=" + indice + ", nombre=" + nombre + ", adjNodos=" + adjNodos + '}';
    }
}
