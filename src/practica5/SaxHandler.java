/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica5;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    
  static List<String> lista = new ArrayList<String>();
  
  public void startElement(String uri, String localName, String qName, Attributes attrs)throws SAXException {
    if (qName.equals("edge")) {//etiqueta
       
      String name = attrs.getValue("source"); //atributo
      lista.add(name);
      name = attrs.getValue("target"); //atributo
      lista.add(name);
      name = attrs.getValue("weight"); //atributo
      lista.add(name);            
    }
  }
        
}
