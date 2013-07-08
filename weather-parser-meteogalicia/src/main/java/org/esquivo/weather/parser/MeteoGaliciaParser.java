package org.esquivo.weather.parser;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.esquivo.weather.entities.MeteoGaliciaData;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MeteoGaliciaParser implements Parser<List<MeteoGaliciaData>> {

    /* (non-Javadoc)
     * @see org.esquivo.weather.Parser#parse(java.io.InputStream)
     */
    @Override
    public List<MeteoGaliciaData> parse(InputStream is) {
        
        List<MeteoGaliciaData> data = new ArrayList<MeteoGaliciaData>();
        CleanerProperties props = new CleanerProperties();
        HtmlCleaner htmlCleaner = new HtmlCleaner(props);
        TagNode root = null;

        //Refactor threadsafe class
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        
        try {
            root = htmlCleaner.clean(is);
            root.setDocType(null);
            Iterator<String> atrs = root.getAttributes().keySet().iterator();
            while(atrs.hasNext()) {
                atrs.next();
                atrs.remove();
            }
            
            Document doc = new DomSerializer(props).createDOM(root);
            // new SimpleXmlSerializer(props).writeToStream(root, System.out);
           
            XPathExpression exprDate = xpath.compile("//div[@id='masEnlaces']/div[@class='linkPDF']/span[@class='peMapaAcl toCapit']");
            
            XPathExpression expr = xpath.compile("//table[@class='temperatrasTabla']/tbody/tr");
            XPathExpression exprTown = xpath.compile("td/a");
            XPathExpression exprTemp = xpath.compile("td/span");
            
            
            String sDate = (String) exprDate.evaluate(doc, XPathConstants.STRING);
            
            // Split para obtener el string de fecha (eliminando "Ultima Actualizacion: "
            sDate = splitDate(sDate);
            
            // Convertir el string fecha a tipo date
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(sDate);
            
            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            
            for (int i = 1; i < nl.getLength()-3; i++) {
                Node node = nl.item(i);
                
                String town = ((String) exprTown.evaluate(node, XPathConstants.STRING)).trim();
                NodeList temps = (NodeList) exprTemp.evaluate(node, XPathConstants.NODESET);
                String min = StringUtils.trim(filter(temps.item(0).getTextContent()));
                String max = StringUtils.trim(filter(temps.item(1).getTextContent()));

                data.add(new MeteoGaliciaData(date, town, Integer.parseInt(min), Integer.parseInt(max)));          
            }
            
            return data;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return null;
    }
    
    public static String filter(String str) {
        StringBuilder filtered = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (current >= 0x20 && current <= 0x7e) {
                filtered.append(current);
            }
        }

        return filtered.toString();
    }
    
    public static String splitDate(String sDate){
        String[] temp;
        String delimiter = ": ";
        temp = sDate.split(delimiter);
        return temp[1].trim(); 
    }
}
