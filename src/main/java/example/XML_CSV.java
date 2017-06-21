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
        String outputCSV_file = "data/XML-CSV/outputCSV.csv";
        String inputXML = new String(readInputMessage(inputXML_file));
        System.out.println("========================Input XML=============================");
        System.out.println(inputXML);
        System.out.println("==============================================================");
        
        File stylesheet = new File(XSLT_file);
        File xmlSource = new File(inputXML_file);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlSource);

        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = TransformerFactory.newInstance() .newTransformer(stylesource);

        Source source = new DOMSource(document);
        Result outputTarget = new StreamResult(new File(outputCSV_file));
        transformer.transform(source, outputTarget);
        
        String outputCSV = new String(readInputMessage(outputCSV_file));
        System.out.println("========================Output CSV=============================");
        System.out.println(outputCSV);
        System.out.println("==============================================================");
        
    }
    private static byte[] readInputMessage(String fileName) {
        try {
            return StreamUtils.readStream(new FileInputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message />".getBytes();
        }
    }  
}
