package com.petarstoykov.xmlsecureprocessor.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;

@Service
public class XmlProcessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlProcessorService.class);

    @Value("${xml.files.path}/books.xml")
    private Resource xmlFile;

    @Value("${xsl.files.path}/books.xsl")
    private Resource xsltFile;

    /**
     * Transforms the XML file to an HTML string using the specified XSLT file.
     *
     * @return the transformed HTML string, or null if an error occurs
     */
    public String transformXmlToHtml() {
        try {
            Source xslt = new StreamSource(xsltFile.getInputStream());
            Source xml = new StreamSource(xmlFile.getInputStream());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(xslt);

            StringWriter writer = new StringWriter();
            transformer.transform(xml, new StreamResult(writer));

            return writer.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }
}
