package practica5;

public class Edge {

    private int inicio;
    private int fin;
    private double dist;

    public Edge(int inicio, int fin, double dist) {
        this.inicio = inicio;
        this.fin = fin;
        this.dist = dist;
    }

    public int getStart() {
        return inicio;
    }

    public int getEnd() {
        return fin;
    }

    public double getDistance() {
        return dist;
    }

    public boolean equals(Edge e) {
        if (e != null && inicio == e.getStart() && fin == e.getEnd()) {
            return true;
        } else {
            return false;
        }
    }
}
