/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import org.w3c.dom.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.milyn.io.StreamUtils;
/**
 *
 * @author Debasis
 */
public class XML_CSV {
    public static void main(String inputXML_file,String XSLT_file) throws Exception {
        String outputCSV_file = "data/XML-CSV/outputCSV.csv"; //Ouput CSV file location
        String inputXML = new String(readInputMessage(inputXML_file));  
            //readInputMessage() function to read input XML file contents
        System.out.println("========================Input XML=============================");
        System.out.println(inputXML); //Display file contents
        System.out.println("==============================================================");
        
        File stylesheet = new File(XSLT_file);  //XSLT file
        File xmlSource = new File(inputXML_file);   //XML file

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        //DocumentBuilderFactory to create new instance of DocumentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder(); 
        //DocumentBuilder to parse XML file
        Document document = builder.parse(xmlSource);   //Parse XML file

        StreamSource stylesource = new StreamSource(stylesheet);    
        Transformer transformer = TransformerFactory.newInstance() .newTransformer(stylesource);    
                                                            //Parse XSLT file
        Source source = new DOMSource(document);
        Result outputTarget = new StreamResult(new File(outputCSV_file));
        transformer.transform(source, outputTarget);    //Tranform function and write to file
        
        String outputCSV = new String(readInputMessage(outputCSV_file)); //read from output file
        System.out.println("========================Output CSV=============================");
        System.out.println(outputCSV);  //Display output CSV contents
        System.out.println("==============================================================");
        
    }
    private static byte[] readInputMessage(String fileName) {   //Function to read from file
        try {
            return StreamUtils.readStream(new FileInputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message />".getBytes();
        }
    }  
}
