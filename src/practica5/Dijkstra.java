/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica5;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Acer
 */
public class Dijkstra {

    /**
     * @param args the command line arguments
     */
    public static final double INFINITY = Double.POSITIVE_INFINITY;
    
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        // TODO code application logic here-
        BinomialHeap obj = new BinomialHeap();
        Scanner leer = new Scanner(System.in);
        System.out.println("Introduzca el nombre de su archivo xml con extensión: ");
        String ruta = leer.next();
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
       
        NodosHandler handler = new NodosHandler();
        parser.parse(ruta, handler);
        Graph [] nodos = new Graph [NodosHandler.c.size()];
        
        Iterator<String> it = NodosHandler.c.iterator();
        int i = 0;
        while(it.hasNext()){
            nodos[i] = new Graph(i);
            nodos[i].setNombre(it.next().trim());
            i++;           
        }
                        
        SaxHandler handler2 = new SaxHandler();
        parser.parse(ruta, handler2);
        
        i = 0;
        String source=null,target=null,weight="0";        
        boolean incremento = true;
        for(String e:SaxHandler.lista){            
            switch(i){
                case 0:
                    source = e;                    
                break;
                case 1:
                    target = e;
                break;
                case 2:
                    weight = e;
                break;                    
            }        
            if(i==2){                
                Integer d = Integer.valueOf(weight);
                for(Graph g:nodos){
                   if(g.getNombre().equals(source.trim())){
                       for(Graph g2:nodos){
                           if(g2.getNombre().equals(target.trim())){
                               g.agregarAdyacencia(g2.getInd(), d, g2.getNombre());
                           }
                       }
                   }
                }
                i=0;
                incremento = false;
            }
            
            if(incremento){
                i++;
            }
            
            incremento = true;
            
        }
        
        
        double[][] resultado = bheapScheme(nodos);
        
        for(int k=0;k<resultado.length;k++){
            for(int j=0;j<resultado[i].length;j++){
                if(resultado[k][j]!=INFINITY && resultado[k][j] != 0.0)
                //System.out.print(resultado[k][j] + " ");
                out:
                for(Graph n:nodos){
                    for(Nodo a: n.getAdjnodes()){
                        if(resultado[k][j]==a.getDistance()){
                            System.out.println("<Vertice>" + n.getNombre() + " <Vertice>" + a.getNombre() + " peso " + a.getDistance());
                            break out;
                        }
                    }
                }
            }
        }
        
        
    }
    
    
	public static double[][] bheapScheme(Graph[] g)
	{
		BinomialHeap bheap = new BinomialHeap();
		int n = g.length;
                String[] nombres = new String[n];
		double[][] distVec = new double[n][n];
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				distVec[i][j] = INFINITY;
		int minIndex = 0;
		Set<Graph> V = new HashSet<Graph>();
		
                
		for(int source=0; source<n; source++)
		{
			distVec[source][source] = 0;
			for(Graph gnode : g)
			{
				V.add(gnode);                                
			}                        
			
                        
			bheap.insert(source, 0);
			V.remove(g[source]);
			
			while(bheap.getRaiz() != null)
			{                           
				minIndex = bheap.extraeMin().getIndice();	
				for(Nodo an : g[minIndex].getAdjnodes())
				{
					if(distVec[source][an.getIndex()] > distVec[source][minIndex] + an.getDistance())
					{
						distVec[source][an.getIndex()] = distVec[source][minIndex] + an.getDistance();                                                
                                                
						if(V.contains(g[an.getIndex()]))
						{
							bheap.insert(an.getIndex(), distVec[source][an.getIndex()]);
							V.remove(g[an.getIndex()]);
						}
						
						else bheap.decrementaVal(bheap.buscaNodo(an.getIndex(), bheap.getRaiz()), distVec[source][an.getIndex()]);
					}
				}	
			}
		}
		return distVec;
	}
}
