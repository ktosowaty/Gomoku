package utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadXML {

    private NodeList nodeList;
    public String pol = "pol";
    public String eng = "eng";
    public String esp = "esp";

    private static final Logger LOGGER = Logger.getLogger(ReadXML.class.getName());

    public void readXML() {
        try {
            File inputFile = new File("src/main/resources/spanishWords.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName("word");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void getRandomWord() {
        Random random = new Random();
        int nodeNumber = random.nextInt(nodeList.getLength());
        Node node = nodeList.item(nodeNumber);
        if(node.getNodeType()==Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            pol = eElement.getElementsByTagName("polishWord").item(0).getTextContent();
            eng = eElement.getElementsByTagName("englishWord").item(0).getTextContent();
            esp = eElement.getElementsByTagName("spanishWord").item(0).getTextContent();
        }
    }

}
