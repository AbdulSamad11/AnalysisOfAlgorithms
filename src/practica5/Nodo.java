package practica5;


public class Nodo 
{
  private int indice;
	private double dist;
        private String nombre;	
	
	public Nodo(int i, double d, String nombre)
	{
		indice = i;
		dist = d;
                this.nombre = nombre;
	}
	
	public int getIndex()
	{
		return indice;
	}

	public double getDistance()
	{
		return dist;
	}
        
        public String getNombre(){
            return nombre;
        }
        
        public String toString(){
            return "nombre=" + nombre + " peso="+dist;
        }
}
