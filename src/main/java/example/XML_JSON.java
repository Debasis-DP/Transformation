/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

// For write operation
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import org.milyn.io.StreamUtils;

import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Debasis
 */
public class XML_JSON {
    static Document document;
    public static void main(String inputXML_file, String XSLT_file) {
        try {
            File stylesheet = new File(XSLT_file);      //XSLT file
            File datafile = new File(inputXML_file);    //XML file            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
            //DocumentBuilderFactory to create new instance of DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder(); 
            //DocumentBuilder to parse XML file
            document = builder.parse(datafile);     //Parse XML file            
            String inputXML = new String(readInputMessage(inputXML_file)); 
            //readInputMessage() function reads from a given file location
            System.out.println("========================Input XML=============================");
            System.out.println(inputXML); //Display of input file contents
            System.out.println("==============================================================");            
            // Use a Transformer for output
            TransformerFactory tFactory = TransformerFactory.newInstance();
            StreamSource stylesource = new StreamSource(stylesheet); //Parse XSLT file
            Transformer transformer = tFactory.newTransformer(stylesource); 
            //Create a transformer for the XSLT rule
            
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result); //Transformation function
            StringBuffer sb = writer.getBuffer(); //Write to buffer
            String finalString = sb.toString(); //Final output JSON string
                        
           //Indent the output JSON
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(finalString, Object.class);
            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json); //Indented JSON
            
            System.out.println("=====================Final JSON===============================");
            System.out.println(indented); //Display indented JSON string
            System.out.println("==============================================================");
            
            //Write to the output file
            BufferedWriter bufferedWriter_out = new BufferedWriter( new FileWriter ("data/XML-JSON/outputJSON.jsn"));
            bufferedWriter_out.write(indented);
            bufferedWriter_out.close();     

        } catch (TransformerConfigurationException tce) {
            // Error generated by the parser
            System.out.println("\n** Transformer Factory error");
            System.out.println("   " + tce.getMessage());

            // Use the contained exception, if any
            Throwable x = tce;

            if (tce.getException() != null) {
                x = tce.getException();
            }

            x.printStackTrace();
        } catch (TransformerException te) {
            // Error generated by the parser
            System.out.println("\n** Transformation error");
            System.out.println("   " + te.getMessage());

            // Use the contained exception, if any
            Throwable x = te;

            if (te.getException() != null) {
                x = te.getException();
            }

            x.printStackTrace();
        } catch (SAXException sxe) {
            // Error generated by this application
            // (or a parser-initialization error)
            Exception x = sxe;

            if (sxe.getException() != null) {
                x = sxe.getException();
            }

            x.printStackTrace();
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        
        }
    } // main
    private static byte[] readInputMessage(String fileName) {   //Read from file
        try {
            return StreamUtils.readStream(new FileInputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message />".getBytes();
        }
    }  
}
