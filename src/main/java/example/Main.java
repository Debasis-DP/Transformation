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
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    
    public static void main(String[] args) throws IOException,SAXException,Exception{
        String inputJSON_file;  //Stores location of input file of JSON format
        String XSLT_file;       //Stores location of XSLT file 
        String inputXML_file;   //Stores location of input file of XML format
        Scanner sc = new Scanner(System.in);    //To read user input
        System.out.println("Choose from following transformations."
                + "\n1. JSON-JSON"
                + "\n2. JSON-CSV"
                + "\n3. JSON-XML"
                + "\n4. XML-JSON"
                + "\n5. XML-CSV"
        );
        System.out.println("Enter the format");
        String type = sc.next();    //Read user input for the type of transformation
        System.out.println("Type entered is : "+type);
        
        //Switch case corresponding to the choice of transformation
        switch(type){
            case "1":
                inputJSON_file = "data/JSON-JSON/inputJSON.jsn";
                XSLT_file = "data/JSON-JSON/XSLT.xsl";
                JSON_JSON.main(inputJSON_file,XSLT_file);   //JSON-JSON transformation function
                // Format - JSON_JSON.main(String inputJSON_file,String XSLT_file)
                break;
            case "2":
                inputJSON_file = "data/JSON-CSV/inputJSON.jsn";
                XSLT_file = "data/JSON-CSV/XSLT.xsl";
                JSON_CSV.main(inputJSON_file,XSLT_file);    //JSON-CSV transformation function
                // Format - JSON_CSV.main(String inputJSON_file,String XSLT_file)
                break;
            case "3" :
                inputJSON_file = "data/JSON-XML/inputJSON.jsn";
                XSLT_file = "data/JSON-XML/XSLT.xsl";
                JSON_XML.main(inputJSON_file,XSLT_file);    //JSON-XML transformation function
                // Format - JSON_XML.main(String inputJSON_file,String XSLT_file)
                break;
            case "4" :
                inputXML_file = "data/XML-JSON/inputXML.xml";
                XSLT_file = "data/XML-JSON/XSLT.xsl";
                XML_JSON.main(inputXML_file,XSLT_file);     //XML-JSON transformation function
                //Format - XML_JSON.main(String inputXML_file, String XSLT_file)
                break;
            case "5" :
                inputXML_file = "data/XML-CSV/inputXML.xml";   
                XSLT_file = "data/XML-CSV/XSLT.xsl";
                XML_CSV.main(inputXML_file,XSLT_file);      //XML-CSV transformation function
                //Format - XML_CSV.main(String inputXML_file, String XSLT_file)
                break;
            default:
                System.out.println("\n=============================\nError type!!\n=============================\n");
        }        
    }
    
}
