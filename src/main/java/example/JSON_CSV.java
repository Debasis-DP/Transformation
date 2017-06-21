/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import org.milyn.Smooks;
import org.milyn.SmooksException;
import org.milyn.container.ExecutionContext;
import org.milyn.io.StreamUtils;

import org.milyn.payload.StringResult;
import org.xml.sax.InputSource;


import java.io.BufferedWriter;


//XML-JSON
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

// For write operation
import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Debasis
 */
public class JSON_CSV {
    private final Smooks smooks;
    static Document document;
    
    protected JSON_CSV(String smooksFile) throws IOException, SAXException, SmooksException {
        smooks = new Smooks(smooksFile);
    }
    
    protected String runSmooksTransform(ExecutionContext executionContext,byte[] JSON_input) throws IOException,SAXException, SmooksException{
        try{
            StringResult result = new StringResult();
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(JSON_input)), result);
            return result.toString();
        } finally {
            smooks.close();
        }
        
    }

    
     public static void main(String inputJSON_file, String XSLT_file) throws IOException, SAXException, SmooksException ,Exception{
       
        /*============= JSON to XML============= */
        String intermediateXML_file = "data/JSON-CSV/intermediateXML.xml";
        String smooks_file = "data/JSON-CSV/smooks-config.xml";
        String outputCSV_file = "data/JSON-CSV/outputCSV.csv";
        byte[] JSON_input = readInputMessage(inputJSON_file);

        System.out.println("\n=====================Original JSON file=============================");
        System.out.println(new String(JSON_input));
        System.out.println("\n====================================================================");

        JSON_CSV mainSmooks = new JSON_CSV(smooks_file);
        ExecutionContext executionContext = mainSmooks.smooks.createExecutionContext();
        String outXML = mainSmooks.runSmooksTransform(executionContext,JSON_input); 

        String indentedXML = format(outXML);//Indented intermediate XML

        System.out.println("\n========================Intermediate XML===========================");
        System.out.println(indentedXML);
        System.out.println("====================================================================");

        try {
             BufferedWriter bufferedWriter_out = new BufferedWriter( new FileWriter (intermediateXML_file));
             bufferedWriter_out.write(indentedXML);
             bufferedWriter_out.close();       
         } catch (IOException e) {
             e.printStackTrace();
         }      

        /*=============== XML to CSV============== */
     
        File stylesheet = new File(XSLT_file);
        File xmlSource = new File(intermediateXML_file);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlSource);

        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = TransformerFactory.newInstance() .newTransformer(stylesource);

        Source source = new DOMSource(document);
        Result outputTarget = new StreamResult(new File(outputCSV_file));
        transformer.transform(source, outputTarget);
        byte[] output = readInputMessage(outputCSV_file);
        String outputCSV = new String(output);
        
        System.out.println("\n===========================Final CSV==========================");
        System.out.println(outputCSV);
        System.out.println("================================================================");
  }// End main
     

    
    private static byte[] readInputMessage(String file_name) {
        try {
            return StreamUtils.readStream(new FileInputStream(file_name));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message />".getBytes();
        }
    }
    
  
    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String format(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);
 
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
 
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
 
    }
}
