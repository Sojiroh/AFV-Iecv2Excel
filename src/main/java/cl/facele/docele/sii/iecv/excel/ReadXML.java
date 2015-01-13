package cl.facele.docele.sii.iecv.excel;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class ReadXML {
	Logger logger = Logger.getLogger(getClass().getCanonicalName());
	private final String startTR = "<tr>";
	private final String endTR = "</tr>";
	private final String startTD = "<td>";
	private final String endTD = "</td>";

	@SuppressWarnings("unchecked")
	public Bean read(Path file) throws Exception {
		Bean b = new Bean();
		try {
//			logger.debug("file: " + new String(Files.readAllBytes(file)));
			SAXReader reader = new SAXReader();
			Document XMLdom = reader.read(new ByteArrayInputStream(Files.readAllBytes(file)));

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("sii", "http://www.sii.cl/SiiDte");

			String contenido = new String();
			contenido += "<table>" + startTR + startTD + "tipoDocumento" + endTD;
			contenido += startTD + "folioDocumento" + endTD;
			contenido += startTD + "fechaEmision" + endTD;
			contenido += startTD + "rutContraparte" + endTD;
			contenido += startTD + "RazonSocial" + endTD;
			contenido += startTD + "montoExento" + endTD;
			contenido += startTD + "montoNeto" + endTD;
			contenido += startTD + "montoIVA" + endTD;
                        contenido += startTD + "CodIVANoRec" + endTD;
                        contenido += startTD + "MntIVANoRec" + endTD;
			contenido += startTD + "montoTOTAL" + endTD;
			contenido += endTR + "\n";

			XPath xPath;

			xPath = DocumentHelper.createXPath("//sii:Caratula//sii:RutEmisorLibro");
			xPath.setNamespaceURIs(map);
			b.setRut(xPath.selectSingleNode(XMLdom).getText());
			
			xPath = DocumentHelper.createXPath("//sii:Caratula//sii:PeriodoTributario");
			xPath.setNamespaceURIs(map);
			b.setPeriodo(xPath.selectSingleNode(XMLdom).getText());
			
			xPath = DocumentHelper.createXPath("//sii:Caratula//sii:TipoOperacion");
			xPath.setNamespaceURIs(map);
			b.setOperacion(xPath.selectSingleNode(XMLdom).getText());
			
			
			xPath = DocumentHelper.createXPath("//sii:Detalle");
			xPath.setNamespaceURIs(map);
			List<Node> detalles = xPath.selectNodes(XMLdom);
			
			for (Node detalle : detalles) {
				contenido += startTR;
				Document element = reader.read(new StringReader(detalle.asXML()));
				element.setXMLEncoding("ISO-8859-1");

				xPath = DocumentHelper.createXPath("//sii:TpoDoc");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:NroDoc");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:FchDoc");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:RUTDoc");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:RznSoc");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:MntExe");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:MntNeto");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:MntIVA");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}
                                
                                xPath = DocumentHelper.createXPath("//sii:IVANoRec//sii:CodIVANoRec");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}
                                
                                xPath = DocumentHelper.createXPath("//sii:IVANoRec//sii:MntIVANoRec");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				xPath = DocumentHelper.createXPath("//sii:MntTotal");
				xPath.setNamespaceURIs(map);
				try {
					contenido += startTD + xPath.selectSingleNode(element).getText() + endTD;					
				} catch (NullPointerException npe) {
					contenido += startTD + "" + endTD;
				}

				
				contenido += endTR + "\n";
				
//				logger.debug("COntenido: " + contenido);
			}
			
			b.setContenido(contenido + "</table>" );
		} catch (Exception e) {
			logger.error(e, e);
			throw new Exception("Error leyendo xml: " + e.getMessage());
		}
		return b;
	}

}
