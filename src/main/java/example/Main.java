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
    
    public static void main(String[] args) throws IOException,SAXException,Exception{
        String inputJSON_file,XSLT_file,inputXML_file;
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose from following transformations."
                + "\n1. JSON-JSON"
                + "\n2. JSON-CSV"
                + "\n3. JSON-XML"
                + "\n4. XML-JSON"
                + "\n5. XML-CSV"
        );
        System.out.println("Enter the format");
        String type = sc.next();
        System.out.println("Type entered is : "+type);
        
        switch(type){
            case "1":
                inputJSON_file = "data/JSON-JSON/inputJSON.jsn";
                XSLT_file = "data/JSON-JSON/XSLT.xsl";
                JSON_JSON.main(inputJSON_file,XSLT_file);
                break;
            case "2":
                inputJSON_file = "data/JSON-CSV/inputJSON.jsn";
                XSLT_file = "data/JSON-CSV/XSLT.xsl";
                JSON_CSV.main(inputJSON_file,XSLT_file);
                break;
            case "3" :
                inputJSON_file = "data/JSON-XML/inputJSON.jsn";
                XSLT_file = "data/JSON-XML/XSLT.xsl";
                JSON_XML.main(inputJSON_file,XSLT_file);
                break;
            case "4" :
                inputXML_file = "data/XML-JSON/inputXML.xml";
                XSLT_file = "data/XML-JSON/XSLT.xsl";
                XML_JSON.main(inputXML_file,XSLT_file);
                break;
            case "5" :
                inputXML_file = "data/XML-CSV/inputXML.xml";
                XSLT_file = "data/XML-CSV/XSLT.xsl";
                XML_CSV.main(inputXML_file,XSLT_file);
                break;
            default:
                System.out.println("\n=============================\nError type!!\n=============================\n");
        }        
    }
    
}
