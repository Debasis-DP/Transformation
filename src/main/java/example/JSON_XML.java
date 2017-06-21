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

import java.io.BufferedWriter;
import javax.xml.transform.*;

/**
 *
 * @author Debasis
 */
public class JSON_XML {
    private final Smooks smooks;
    static Document document;
    
    protected JSON_XML() throws IOException, SAXException, SmooksException {
        smooks = new Smooks("data/JSON-XML/smooks-config.xml");
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
    
    public static void main(String inputJSON_file, String XSLT_file)
            throws IOException, SAXException, SmooksException,TransformerConfigurationException,TransformerException,ParserConfigurationException {
       
       byte[] JSON_input = readInputMessage(inputJSON_file); 
       System.out.println("_______________________Original JSON file_____________________\n");
       System.out.println(new String(JSON_input));
       System.out.println("______________________________________________________________\n");
       
       String intermediate_file = "data/JSON-XML/intermediateXML.xml";
       
       JSON_XML mainSmooks = new JSON_XML();
       ExecutionContext executionContext = mainSmooks.smooks.createExecutionContext();
       String out = mainSmooks.runSmooksTransform(executionContext,JSON_input);
       String indented = format(out);
       System.out.println("______________________Intermediate XML______________________________\n");
       System.out.println(indented);
       System.out.println("_______________________________________________________________\n");
       
       //Write intermediate to file
       BufferedWriter bufferedWriter_intermediate = new BufferedWriter( new FileWriter (intermediate_file));
       bufferedWriter_intermediate.write(indented);
       bufferedWriter_intermediate.close();       
          
       File stylesheet = new File(XSLT_file);
       File datafile = new File(intermediate_file);
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder = factory.newDocumentBuilder();
       document = builder.parse(datafile);
       
        // Use a Transformer for output
       TransformerFactory tFactory = TransformerFactory.newInstance();
       StreamSource stylesource = new StreamSource(stylesheet);
       Transformer transformer = tFactory.newTransformer(stylesource);
       transformer.setOutputProperty(OutputKeys.INDENT, "yes");
       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
       
       DOMSource source = new DOMSource(document);
       StringWriter writer = new StringWriter();
       StreamResult result = new StreamResult(writer);
       transformer.transform(source, result);
       StringBuffer sb = writer.getBuffer(); 
       String finalString = sb.toString();
       
       System.out.println("===================Final XML======================");
       System.out.println(finalString);
       System.out.println("=====================================================");
       
       BufferedWriter bufferedWriter_out = new BufferedWriter( new FileWriter ("data/JSON-XML/outputXML.xml"));
       bufferedWriter_out.write(finalString);
       bufferedWriter_out.close();       
                    
        
    } // main
    private static byte[] readInputMessage(String fileName) {
        try {
            return StreamUtils.readStream(new FileInputStream(fileName));
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
