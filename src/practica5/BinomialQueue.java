package practica5;

public class BinomialQueue {

    private Qnode min;
    private int size;

    public static class Qnode {

        private int indice;
        private int grado;
        private double dist;
        private Qnode padre;
        private Qnode hijo;
        private Qnode sIzq;
        private Qnode sDer;
        private boolean hijoCut;

        public Qnode(int indice, double dist) {
            this.indice = indice;
            this.dist = dist;
            this.grado = 0;
            this.hijoCut = false;
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

        public void setPadre(Qnode p) {
            padre = p;
        }

        public Qnode getPadre() {
            return padre;
        }

        public void setHijo(Qnode c) {
            hijo = c;
        }

        public Qnode getHijo() {
            return hijo;
        }

        public void setLeftSibling(Qnode ls) {
            sIzq = ls;
        }

        public Qnode getIzqS() {
            return sIzq;
        }

        public void setDerS(Qnode rs) {
            sDer = rs;
        }

        public Qnode getDerS() {
            return sDer;
        }

        public void incrementarGrado() {
            grado++;
        }

        public void decrementaGrado() {
            grado--;
        }

        public int getDegree() {
            return grado;
        }

        public void setHijoC(boolean b) {
            hijoCut = b;
        }

        public boolean getHijoC() {
            return hijoCut;
        }
    }

    public BinomialQueue() {
        min = null;
        size = 0;
    }

    public BinomialQueue(Qnode x) {
        min = x;
        x.setHijo(null);
        x.setPadre(null);
        x.setLeftSibling(x);
        x.setDerS(x);
        size = 1;
    }

    public Qnode getMin() {
        return min;
    }

    public int getTam() {
        return size;
    }

    public void link(Qnode y, Qnode x) {
        Qnode l = y.getIzqS();
        Qnode r = y.getDerS();

        l.setDerS(r);
        r.setLeftSibling(l);
        y.setPadre(x);
        y.setHijoC(false);

        if (x.getDegree() == 0) {
            y.setLeftSibling(y);
            y.setDerS(y);
        } else {
            y.setDerS(x.getHijo());
            y.setLeftSibling(x.getHijo().getIzqS());
            x.getHijo().getIzqS().setDerS(y);
            x.getHijo().setLeftSibling(y);
        }

        x.setHijo(y);
        x.incrementarGrado();

    }

    public void union(BinomialQueue h) {
        Qnode min2 = h.getMin();
        size += h.getTam();

        //if(min2 == null) do nothing

        if (min == null && min2 != null) {
            min = min2;
        }

        if (min != null && min2 != null) {
            Qnode l = min.getIzqS();

            min2.getIzqS().setDerS(min);
            l.setDerS(min2);
            min.setLeftSibling(min2.getIzqS());
            min2.setLeftSibling(l);

            if (min.getDist() > min2.getDist()) {
                min = min2;
            }
        }
    }

    public void inserta(int indice, double dist) {
        Qnode fnode = new Qnode(indice, dist);
        BinomialQueue h = new BinomialQueue(fnode);
        this.union(h);
    }

    public void cons() {
        int b = (int) (Math.log(size) / Math.log(2)) + 2;

        Qnode[] gradoMap = new Qnode[b];
        for (int i = 0; i < b; i++) {
            gradoMap[i] = null;
        }

        int d;
        Qnode x = min;
        Qnode start = min;
        Qnode y, next;
        do {
            d = x.getDegree();
            next = x.getDerS();
            while (gradoMap[d] != null) {
                y = gradoMap[d];
                if (x.getDist() > y.getDist()) {
                    Qnode temp = y;
                    y = x;
                    x = temp;
                }
                if (y == start) {
                    start = start.getDerS();
                }

                if (y == next) {
                    next = next.getDerS();
                }
                this.link(y, x);
                gradoMap[d] = null;
                d++;
            }
            gradoMap[d] = x;
            x = next;
        } while (x != start);


        min = null;
        for (int i = 0; i < b; i++) {
            if (min == null || (gradoMap[i] != null && min.getDist() > gradoMap[i].getDist())) {
                min = gradoMap[i];
            }
        }
    }

    public Qnode extraeMin() {
        Qnode z = min;
        if (z != null) {
            Qnode x = z.getHijo();
            if (x != null) {
                x.setPadre(null);
                for (x = x.getDerS(); x != z.getHijo(); x = x.getDerS()) {
                    x.setPadre(null);
                }


                Qnode l = z.getIzqS();
                x = z.getHijo();

                l.setDerS(x);
                x.getIzqS().setDerS(z);
                z.setLeftSibling(x.getIzqS());
                x.setLeftSibling(l);
            }


            z.getIzqS().setDerS(z.getDerS());
            z.getDerS().setLeftSibling(z.getIzqS());


            if (z == z.getDerS()) {
                min = null;
            } else {
                min = z.getDerS();
                this.cons();
            }
            size--;
        }
        return z;
    }

    public void decrementaVal(Qnode x, double k) {
        if (k > x.getDist()) {
            System.out.println("Error!");
            System.exit(1);
        }

        x.setDist(k);
        Qnode y = x.getPadre();
        if (y != null && x.getDist() < y.getDist()) {
            cu(x, y);
            c(y);
        }

        if (x.getDist() < min.getDist()) {
            min = x;
        }
    }

    public void cu(Qnode x, Qnode y) {
        Qnode z = y.getHijo();
        if (z == x && x.getDerS() == x) {
            y.setHijo(null);
        }

        if (z == x && x.getDerS() != x) {
            Qnode l = x.getIzqS();
            Qnode r = x.getDerS();
            l.setDerS(r);
            r.setLeftSibling(l);
            y.setHijo(r);
        }

        if (z != x) {
            Qnode l = x.getIzqS();
            Qnode r = x.getDerS();

            l.setDerS(r);
            r.setLeftSibling(l);
        }

        y.decrementaGrado();

        x.setDerS(min);
        min.getIzqS().setDerS(x);
        x.setLeftSibling(min.getIzqS());
        min.setLeftSibling(x);

        x.setPadre(null);
        x.setHijoC(false);
    }

    public void c(Qnode y) {
        Qnode z = y.getPadre();
        if (z != null) {
            if (y.getHijoC() == false) {
                y.setHijoC(true);
            } else {
                this.cu(y, z);
                this.c(z);
            }
        }
    }

    public void delete(Qnode x) {
        this.decrementaVal(x, Double.NEGATIVE_INFINITY);
        this.extraeMin();
    }

    public Qnode buscaNodo(int indice, Qnode x) {
        Qnode wantedNode = null;
        if (x != null) {
            Qnode y = x;
            do {
                if (y.getIndice() == indice) {
                    return y;
                }
                if ((wantedNode = buscaNodo(indice, y.getHijo())) != null) {
                    return wantedNode;
                }
                y = y.getDerS();

            } while (y != x);
        }
        return wantedNode;
    }

    public void print(Qnode x) {
        Qnode y = x;
        if (x != null) {
            do {
                System.out.print(y.getIndice() + "\t");
                print(y.getHijo());

                System.out.println();

                y = y.getDerS();
            } while (y != x);
        }
    }
}
