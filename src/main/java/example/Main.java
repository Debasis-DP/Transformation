/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.util.Scanner; 
/**
 *
 * @author Debasis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException,SAXException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the format");
        String type = sc.next();
        System.out.println("Type entered is : "+type);
        switch(type){
            case "xml":
                JSON_JSON.main("data/JSON-JSON/inputJSON.jsn","data/JSON-JSON/intermediateXML.xml","data/JSON-JSON/XSLT.xsl","data/JSON-JSON/smooks-config.xml","data/JSON-JSON/outputJSON");
                break;
            default:
                System.out.println("Error type!");
        }        
    }
    
}
