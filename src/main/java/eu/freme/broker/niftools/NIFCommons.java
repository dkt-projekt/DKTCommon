package eu.freme.broker.niftools;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

import eu.freme.broker.tools.NIFParameterSet;
import eu.freme.common.conversion.rdf.RDFConstants;
import eu.freme.common.conversion.rdf.RDFConversionService;


public class NIFCommons {

	@Autowired
	static RDFConversionService rdfConversionService;

	
	
	public static void main(String[] args){
		
		
	}
}
