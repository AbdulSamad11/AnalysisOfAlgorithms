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

import java.util.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Acer
 */
public class NodosHandler extends DefaultHandler{
    
    
  static Set<String> c= new HashSet<String>();
  
  public void startElement(String uri, String localName, String qName, Attributes attrs)throws SAXException {
    if (qName.equals("edge")) {//etiqueta
       
      String name = attrs.getValue("source"); //atributo
      c.add(name.trim());
      name = attrs.getValue("target"); //atributo
      c.add(name.trim());
    }
  }
        
}

