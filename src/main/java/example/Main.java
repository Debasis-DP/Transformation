/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;
import java.io.FileInputStream;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.util.Scanner; 
import org.milyn.io.StreamUtils;
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
                byte[] inputJSON = readInputMessage("data/JSON-JSON/inputJSON.jsn");
                JSON_JSON.main(inputJSON,"data/JSON-JSON/intermediateXML.xml","data/JSON-JSON/XSLT.xsl","data/JSON-JSON/smooks-config.xml","data/JSON-JSON/outputJSON");
                break;
            default:
                System.out.println("Error type!");
        }        
    }
    private static byte[] readInputMessage(String JSON_input_file) {
        try {
            return StreamUtils.readStream(new FileInputStream(JSON_input_file));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message />".getBytes();
        }
    }
    
}
