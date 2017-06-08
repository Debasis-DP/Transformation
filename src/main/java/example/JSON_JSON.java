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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Debasis
 */
public class JSON_JSON {
    private static byte[] JSON_input; //Read from input JSON 
    private final Smooks smooks;
    static Document document;
    
    protected JSON_JSON(String smooksFile) throws IOException, SAXException, SmooksException {
        smooks = new Smooks(smooksFile);
    }
    
    protected String runSmooksTransform(ExecutionContext executionContext) throws IOException,SAXException, SmooksException{
        try{
            StringResult result = new StringResult();
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(JSON_input)), result);
            return result.toString();
        } finally {
            smooks.close();
        }
        
    }

    
     public static void main(String inputJSON_file,String intermediateXML_file,String XSLT_file,String smooks_file,String outputJSON_file) throws IOException, SAXException, SmooksException {
       JSON_input = readInputMessage(inputJSON_file);
       
       System.out.println("_______________________Original JSON file_____________________\n");
       System.out.println(new String(JSON_input));
       System.out.println("______________________________________________________________\n");
       
       String out_XMLfile = intermediateXML_file;
       
       JSON_JSON mainSmooks = new JSON_JSON(smooks_file);
       ExecutionContext executionContext = mainSmooks.smooks.createExecutionContext();
       String outXML = mainSmooks.runSmooksTransform(executionContext); //Indented intermediate XML
       String indentedXML = format(outXML);
       
       System.out.println("______________________Intermediate XML______________________________\n");
       System.out.println(indentedXML);
       System.out.println("_______________________________________________________________\n");
       
       try {
            BufferedWriter bufferedWriter_out = new BufferedWriter( new FileWriter (out_XMLfile));
            bufferedWriter_out.write(indentedXML);
            bufferedWriter_out.close();       
        } catch (IOException e) {
            e.printStackTrace();
        }
       JSON_JSON.main1(intermediateXML_file,outputJSON_file,XSLT_file);
       
    }
     
    public static void main1(String intermediateXML_file,String outputJSON_file,String XSLT_file) {
    
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

         try {
            File stylesheet = new File(XSLT_file);
            File datafile = new File(intermediateXML_file);
            
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(datafile);
            
            
            // Use a Transformer for output
            TransformerFactory tFactory = TransformerFactory.newInstance();
            StreamSource stylesource = new StreamSource(stylesheet);
            Transformer transformer = tFactory.newTransformer(stylesource);
            
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            StringBuffer sb = writer.getBuffer(); 
            String finalString = sb.toString();
                        
            
            //Indent the output JSON
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(finalString, Object.class);
            String final_indented_JSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json); //Indented JSON
            
            System.out.println("=======================Output JSON=============================");
            System.out.println(final_indented_JSON);
            System.out.println("===============================================================");
            try {
            BufferedWriter bufferedWriter_outJSON = new BufferedWriter( new FileWriter (outputJSON_file));
            bufferedWriter_outJSON.write(final_indented_JSON);
            bufferedWriter_outJSON.close();       
            } catch (IOException e) {
                e.printStackTrace();
            }

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
    
    private static byte[] readInputMessage(String JSON_input_file) {
        try {
            return StreamUtils.readStream(new FileInputStream(JSON_input_file));
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
