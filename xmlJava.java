/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Gabry
 */

public class xmlJava {
    
   
    private static final String FILENAME = "C://Users//20102//IdeaProjects//SOA_1//src//main//java//app//Books.xml";
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        int cases = 0 ;
		System.out.println("------------------------------");
		System.out.println("Welcome to XML Application");
		System.out.println("1. Add new Book");
		System.out.println("2. Search for a book");
                System.out.println("3. Delete Book by Id");
		Scanner in = new Scanner(System.in);
		cases = in.nextInt();
		switch (cases) {
			case 1: {
				add_book();
				break;
			}
			case 2:{
				search_books();
				break;
			}
			case 3:{
				delete_book();
				break;
			}
		}
    
    
    
    }
    public static int is_new = 0;
    public static void add_book() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException  {
    
    System.out.println("Enter number of books : ");
    Scanner in = new Scanner(System.in);
    int num= in.nextInt();
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    
    
    Document xmlDoc=null;
    
    Element root=null;
    
    if(is_new==0){
        xmlDoc=docBuilder.newDocument();
        root=xmlDoc.createElement("Catalogue");
        xmlDoc.appendChild(root);
        is_new=1;
    }
    else
    {
        xmlDoc=docBuilder.parse("FILENAME");
        root=xmlDoc.getDocumentElement();
    }
    for(int i=0;i<num;i++)
    {
         Element book = xmlDoc.createElement("Book");		      
         Element Author = xmlDoc.createElement("Author");                                   
         Element Title = xmlDoc.createElement("Title");
         Element Genre = xmlDoc.createElement("Genre");
         Element Price = xmlDoc.createElement("Price");
         Element Publish_Date = xmlDoc.createElement("Publish_Date");
         Element Description = xmlDoc.createElement("Description");
         root.appendChild(book);
         
         System.out.println("Adding the Book #"+(i+1));
         System.out.println("Add Book ID:");
         String s=in.nextLine();
         book.setAttribute("id", in.nextLine());
         
         
         System.out.println("Add the Author: ");
	 Author.setTextContent(in.nextLine());
	 book.appendChild(Author);
         
         System.out.println("Add the Title: ");
	 Title.setTextContent(in.nextLine());
	 book.appendChild(Title);
         
         System.out.println("Add the Genre: ");
	 Genre.setTextContent(in.nextLine());
	 book.appendChild(Genre);
         
         System.out.println("Add the Price: ");
	 Price.setTextContent(in.nextLine());
	 book.appendChild(Price);
         
         System.out.println("Add the Publish_Date: ");
	 Publish_Date.setTextContent(in.nextLine());
	 book.appendChild(Publish_Date);
         
         System.out.println("Add the Description: ");
	 Description.setTextContent(in.nextLine());
	 book.appendChild(Description);
         
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 Transformer transformer = transformerFactory.newTransformer();
         
	 transformer.setOutputProperty(OutputKeys.INDENT, "yes");

         DOMSource source = new DOMSource(xmlDoc);
         
         FileOutputStream output = new FileOutputStream("C://Users//20102//IdeaProjects//SOA_1//src//main//java//app//Books.xml");
	 StreamResult result = new StreamResult(output);
         transformer.transform(source, result); 
        }   
    }
    public static void search_books() throws ParserConfigurationException, SAXException, IOException{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Title OR Author to search : ");
        String search = in.nextLine();

        boolean found = false;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = docFactory.newDocumentBuilder();
        Document doc = db.parse(new File(FILENAME));
        doc.getDocumentElement().normalize();
                  
        NodeList list = doc.getElementsByTagName("Book");
        for (int i = 0; i < list.getLength(); i++){
           Node node=list.item(i);
           if (node.getNodeType() == Node.ELEMENT_NODE){
              Element element = (Element) node;
              
              String id = element.getAttribute("id");
              
              String Author = element.getElementsByTagName("Author").item(0).getTextContent();
              String Title = element.getElementsByTagName("Title").item(0).getTextContent();
              String Genre = element.getElementsByTagName("Genre").item(0).getTextContent();
              String Price = element.getElementsByTagName("Price").item(0).getTextContent();
              String Publish_Date = element.getElementsByTagName("Publish_Date").item(0).getTextContent();
              String Description = element.getElementsByTagName("Description").item(0).getTextContent();
              if(Author.equalsIgnoreCase(search) || Title.equalsIgnoreCase(search)){
                  System.out.println("The Author is: "+Author);
                  System.out.println("The Title is: "+Title);
                  System.out.println("The Genre is: "+Genre);
                  System.out.println("The Price is: "+Price);
                  System.out.println("The Publish_Date is: "+Publish_Date);
                  System.out.println("The Description is: "+Description);
                }
            }   
        }
    }
    public static void delete_book() throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException{
            Scanner in = new Scanner(System.in);
            System.out.println("Enter ID of the Book: ");
            String search = in.nextLine();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(FILENAME));

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer tFormer = tFactory.newTransformer();

            NodeList list = doc.getElementsByTagName("Book");
            for (int i = 0; i < list.getLength(); i++){
               Node node=list.item(i);
               if (node.getNodeType() == Node.ELEMENT_NODE){
                  Element element = (Element) node;
                  
                  String id = element.getAttribute("id");
                  
                  if(id.equalsIgnoreCase(search)){
                     element.getParentNode().removeChild(element);
                     
                     doc.normalize();
                     Source source = new DOMSource(doc);
                     
                     tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
                     FileOutputStream output = new FileOutputStream("C://Users//20102//IdeaProjects//SOA_1//src//main//java//app//Books.xml");
                     StreamResult result = new StreamResult(output);
                     tFormer.transform(source, result);
                    }
                }
            }
    }   
}