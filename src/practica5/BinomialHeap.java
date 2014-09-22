package practica5;

public class BinomialHeap {

    private Bnodo raiz;

    public BinomialHeap() {
        raiz = null;
    }

    public BinomialHeap(Bnodo node) {
        raiz = node;
    }

    public static class Bnodo {

        private int indice;
        private int grado;
        private double dist;
        private Bnodo padre;
        private Bnodo hijo;
        private Bnodo s;

        public Bnodo(int indice, double dist) {
            this.indice = indice;
            this.dist = dist;
            this.grado = 0;
        }

        public void setIndice(int i) {
            indice = i;
        }

        public int getIndice() {
            return indice;
        }

        public void setDist(double d) {
            dist = d;
        }

        public double getDist() {
            return dist;
        }

        public void setParent(Bnodo p) {
            padre = p;
        }

        public Bnodo getParent() {
            return padre;
        }

        public void setHijo(Bnodo c) {
            hijo = c;
        }

        public Bnodo getHijo() {
            return hijo;
        }

        public void setS(Bnodo s) {
            s = s;
        }

        public Bnodo getS() {
            return s;
        }

        public void incrementaGrado() {
            grado++;
        }

        public void decrementaGrado() {
            grado--;
        }

        public int getGrado() {
            return grado;
        }
    }

    public void arista(Bnodo y, Bnodo z) {
        y.setParent(z);
        y.setS(z.getHijo());
        z.setHijo(y);
        z.incrementaGrado();
    }

    public Bnodo getMin() {
        Bnodo y = null;
        Bnodo x = raiz;
        while (x != null) {
            if (y == null || x.getDist() < y.getDist()) {
                y = x;
            }
            x = x.getS();
        }
        return y;
    }

    public Bnodo getRaiz() {
        return raiz;
    }

    public void pega(BinomialHeap h) {
        Bnodo a = raiz;
        Bnodo b = h.getRaiz();

        if (a == null && b != null) {
            raiz = b;
        }

        if (a != null && b != null) {
            if (a.getGrado() > b.getGrado()) {
                raiz = b;
                b = a;
                a = raiz;
            }

            while (b != null) {
                if (a.getS() == null) {
                    a.setS(b);
                    break;
                } else {
                    if (a.getS().getGrado() < b.getGrado()) {
                        a = a.getS();
                    } else {
                        Bnodo c = b.getS();
                        b.setS(a.getS());
                        a.setS(b);
                        a = a.getS();
                        b = c;
                    }
                }
            }
        }
    }

    public void union(BinomialHeap h) {
        this.pega(h);

        if (raiz != null) {
            Bnodo previous = null;
            Bnodo x = raiz;
            Bnodo next = x.getS();

            while (next != null) {
                if ((x.getGrado() != next.getGrado())
                        || (next.getS() != null && next.getS().getGrado() == x.getGrado())) {
                    previous = x;
                    x = next;
                } else {
                    if (x.getDist() <= next.getDist()) {
                        x.setS(next.getS());
                        this.arista(next, x);
                    } else {
                        if (previous == null) {
                            raiz = next;
                        } else {
                            previous.setS(next);
                        }

                        this.arista(x, next);
                        x = next;
                    }

                }
                next = x.getS();
            }
        }
    }

    public void insert(int indice, double dist) {
        Bnodo bnode = new Bnodo(indice, dist);
        BinomialHeap h = new BinomialHeap(bnode);
        this.union(h);
    }

    public Bnodo reverseList(Bnodo x) {
        Bnodo y = x;
        if (x != null) {
            while (y.getS() != null) {
                y.setParent(null);
                y = y.getS();
            }
            y.setParent(null);

            if (x.getS() != null) {
                reverseList(x.getS());
                x.getS().setS(x);
            }

            x.setS(null);
        }
        return y;
    }

    public Bnodo extraeMin() {
        Bnodo min = this.getMin();

        if (raiz == min) {
            raiz = raiz.getS();
        } else {
            Bnodo x = raiz;
            while (x.getS() != min) {
                x = x.getS();
            }

            x.setS(min.getS());
        }

        Bnodo hijo = min.getHijo();
        if (hijo != null) {
            hijo = this.reverseList(hijo);
            BinomialHeap h = new BinomialHeap(hijo);
            this.union(h);
        }

        return min;
    }

    public void decrementaVal(Bnodo x, double d) {
        if (d > x.getDist()) {
            System.out.println("Error!");
            System.exit(1);
        }

        x.setDist(d);

        Bnodo y = x;
        Bnodo z = y.getParent();
        while (z != null && y.getDist() < z.getDist()) {
            double dist = y.getDist();
            int indice = y.getIndice();
            y.setIndice(z.getIndice());
            y.setDist(z.getDist());
            z.setIndice(indice);
            z.setDist(dist);

            y = z;
            z = y.getParent();
        }
    }

    public Bnodo buscaNodo(int indice, Bnodo x) {
        Bnodo wantedNode = null;

        if (x.getIndice() == indice) {
            return x;
        } else {
            if (x.getHijo() != null && wantedNode == null) {
                wantedNode = buscaNodo(indice, x.getHijo());
            }

            if (x.getS() != null && wantedNode == null) {
                wantedNode = buscaNodo(indice, x.getS());
            }

            return wantedNode;
        }

    }

    public void delete(Bnodo x) {
        this.decrementaVal(x, Double.NEGATIVE_INFINITY);
        this.extraeMin();
    }
}
