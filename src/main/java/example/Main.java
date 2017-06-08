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
                String inputJSON_file = "data/JSON-JSON/inputJSON.jsn";
                String XSLT_file = "data/JSON-JSON/XSLT.xsl";
                        
                JSON_JSON.main(inputJSON_file,XSLT_file);
                break;
            default:
                System.out.println("Error type!");
        }        
    }
    
}
