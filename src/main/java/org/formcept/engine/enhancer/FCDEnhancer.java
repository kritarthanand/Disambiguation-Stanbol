/*
 * Copyright 2012, FORMCEPT [http://www.formcept.com]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.formcept.engine.enhancer;

import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_ENTITY_LABEL;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.RDF_TYPE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.RDFS_LABEL;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.DC_TYPE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_SELECTED_TEXT;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_ENTITY_TYPE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_CONFIDENCE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_SELECTION_CONTEXT;
import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.Map.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.swing.*;

import org.apache.clerezza.rdf.core.LiteralFactory;
import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.NonLiteral;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;

import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.core.impl.PlainLiteralImpl;
import org.apache.clerezza.rdf.core.impl.TripleImpl;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.InvalidContentException;
import org.apache.stanbol.enhancer.servicesapi.ServiceProperties;
import org.apache.stanbol.enhancer.servicesapi.helper.ContentItemHelper;
import org.apache.stanbol.enhancer.servicesapi.helper.EnhancementEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.impl.AbstractEnhancementEngine;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.stanbol.enhancer.servicesapi.Blob;
import org.apache.stanbol.enhancer.servicesapi.Chain;
import org.apache.stanbol.enhancer.servicesapi.ChainException;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.InvalidContentException;
import org.apache.stanbol.enhancer.servicesapi.ServiceProperties;
import org.apache.stanbol.enhancer.servicesapi.helper.ContentItemHelper;
import org.apache.stanbol.enhancer.servicesapi.helper.EnhancementEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.rdf.NamespaceEnum;
import org.apache.stanbol.enhancer.servicesapi.rdf.OntologicalClasses;
import org.apache.stanbol.enhancer.servicesapi.rdf.TechnicalClasses;

import org.apache.clerezza.rdf.core.MGraph;
import org.apache.stanbol.entityhub.servicesapi.site.ReferencedSite;
import org.apache.stanbol.entityhub.servicesapi.site.ReferencedSiteException;
import org.apache.stanbol.entityhub.servicesapi.site.ReferencedSiteManager;
import org.apache.stanbol.entityhub.servicesapi.query.Constraint;
import org.apache.stanbol.entityhub.servicesapi.query.FieldQuery;
import org.apache.stanbol.entityhub.servicesapi.query.QueryResultList;
import org.apache.stanbol.entityhub.servicesapi.query.ReferenceConstraint;
import org.apache.stanbol.entityhub.servicesapi.query.TextConstraint;
import org.apache.stanbol.entityhub.servicesapi.model.Entity;
import org.apache.stanbol.entityhub.servicesapi.model.Representation;
import org.apache.stanbol.entityhub.servicesapi.model.Text;
import org.apache.stanbol.entityhub.servicesapi.model.rdf.RdfResourceEnum;
import org.apache.stanbol.entityhub.servicesapi.Entityhub;
import org.apache.stanbol.entityhub.servicesapi.EntityhubException;
//import org.apache.stanbol.entityhub.servicesapi.defaults;


import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferenceStrategy;
import org.apache.felix.scr.annotations.Service;

/**
 * Simple Enhancer
 * @author Anuj
 */
@Component(immediate = true, metatype = true)
@Service
@Properties(value={
    @Property(name=EnhancementEngine.PROPERTY_NAME,value="formcept-disambiguator")
})
public class FCDEnhancer extends AbstractEnhancementEngine<IOException,RuntimeException>
    implements EnhancementEngine, ServiceProperties {
    
    private static Logger LOG = LoggerFactory.getLogger(FCDEnhancer.class);
    
    @Property(value = "https://www.formcept.com/analyze")
    public static final String FORMCEPT_SERVICE_URL = "org.formcept.engine.enhancer.url";
    
    /**
     * Service URL
     */
    private String serviceURL;

    /**
     * The default value for the Execution of this Engine. Currently set to
     * {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT} + 17. It should run after Metaxa and LangId.
     */
    public static final Integer defaultOrder = ServiceProperties.ORDERING_EXTRACTION_ENHANCEMENT -10;
    public static final String PLAIN_TEXT_MIMETYPE = "text/plain";

   @Reference
    protected ReferencedSiteManager siteManager;
    /**
     * Contains the only supported mime type {@link #PLAIN_TEXT_MIMETYPE}
     */

 @Reference
    protected Entityhub entityhub;
    public static final Set<String> SUPPORTED_MIMETYPES = Collections.singleton(PLAIN_TEXT_MIMETYPE);

    
    public Map<String,Object> getServiceProperties() {
        return Collections.unmodifiableMap(Collections.singletonMap(
            ENHANCEMENT_ENGINE_ORDERING, (Object) defaultOrder));
    }

    public int canEnhance(ContentItem ci) throws EngineException {
        // check if content is present
        try {
            if((ContentItemHelper.getText(ci.getBlob()) == null) || 
                    (ContentItemHelper.getText(ci.getBlob()).trim().isEmpty())){
                return CANNOT_ENHANCE;
            }
        } catch (IOException e) {
            LOG.error("Failed to get the text for " +
            		"enhancement of content: " + ci.getUri(), e);
            throw new InvalidContentException(this, ci, e);
        }
        // default enhancement is synchronous enhancement
        return ENHANCE_SYNCHRONOUS;
    }

    public void computeEnhancements(ContentItem ci) throws EngineException {
        // write results (requires a write lock)
        // not required as we are enhancing synchronously
        //ci.getLock().writeLock().lock();
        
        
        
       /* final ReferencedSite site;
        if(referencedSiteID != null) 
        { //lookup the referenced site
            site = siteManager.getReferencedSite(referencedSiteID);
            //ensure that it is present
            if (site == null) {
                String msg = String.format(
                    "Unable to enhance %s because Referenced Site %s is currently not active!", 
                    ci.getUri().getUnicodeString(), referencedSiteID);
                log.warn(msg);
                // TODO: throwing Exceptions is currently deactivated. We need a more clear
                // policy what do to in such situations
                // throw new EngineException(msg);
                return;
            }
            //and that it supports offline mode if required
            if (isOfflineMode() && !site.supportsLocalMode()) {
                log.warn("Unable to enhance ci {} because OfflineMode is not supported by ReferencedSite {}.",
                    ci.getUri().getUnicodeString(), site.getId());
                return;
            }
        } else { // null indicates to use the Entityhub to lookup Entities
            site = null;
        }*/
        
        MGraph graph = ci.getMetadata();
        LiteralFactory literalFactory = LiteralFactory.getInstance();
        // Retrieve the existing text annotations (requires read lock)
       //Map<NamedEntity,List<UriRef>> textAnnotations = new HashMap<NamedEntity,List<UriRef>>();
        //the language extracted for the parsed content or NULL if not available
        String contentLangauge;
      
       UriRef DC_RELATION = new UriRef(NamespaceEnum.dc+ "relation");

       ci.getLock().readLock().lock();
        try {
            contentLangauge = EnhancementEngineHelper.getLanguage(ci);
        for (Iterator<Triple> it = graph.filter(null, RDF_TYPE, TechnicalClasses.ENHANCER_TEXTANNOTATION); it
                    .hasNext();) 
	        {
           
           	     	UriRef uri = (UriRef) it.next().getSubject();
                    String name = EnhancementEngineHelper.getString(graph, uri, ENHANCER_SELECTED_TEXT);
          if(name==null)
          {continue;}
          
          JOptionPane.showMessageDialog(null, name);  
               
                 	if (graph.filter(uri, DC_RELATION, null).hasNext()) {
                    	// this is not the most specific occurrence of this name: skip
                       	      continue;
                      	}
               		// NamedEntity namedEntity = NamedEntity.createFromTextAnnotation(graph, uri);
                	//if(namedEntity != null){
                    // This is a first occurrence, collect any subsumed annotations
                    List<UriRef> subsumed = new ArrayList<UriRef>();

                    for (Iterator<Triple> it2 = graph.filter(null, DC_RELATION, uri); it2.hasNext();) 
                    	{
                      		UriRef uri1 = (UriRef) it2.next().getSubject();
                    	String name1 = EnhancementEngineHelper.getString(graph, uri1, DC_TYPE);
				          JOptionPane.showMessageDialog(null, "++"+name1);   

                      		subsumed.add(uri1);

                    	}
                    	
                    //textAnnotations.put(namedEntity, subsumed);
                //}
            }
        } finally {
            ci.getLock().readLock().unlock();
        }
        ReferencedSite dbpediaReferencedSite=null;
        try{
     // ReferencedSiteManager siteManager=new ReferencedSiteManager();
        dbpediaReferencedSite =siteManager.getReferencedSite("dbpedia");
        	String label="Paris"; //the selected text of the TextAnnotation to disambiguate
			    Collection<String> types=null; //potential types of entities
   				 String language=""; //the language of the analyzed text
    			String extractionContext; //the surrounding text of the extraction
        
        	FieldQuery query =entityhub.getQueryFactory().createFieldQuery();


        Constraint labelConstraint;
        //TODO: make case sensitivity configurable
        boolean casesensitive = false;
        String namedEntityLabel = casesensitive ? label : label.toLowerCase();
       if(language != null){
            //search labels in the language and without language
            labelConstraint = new TextConstraint(namedEntityLabel,casesensitive,language,null);
        } else {
            labelConstraint = new TextConstraint(namedEntityLabel,casesensitive);
        }
        query.setConstraint("rdfs:label", labelConstraint);
        
          LOG.info("Init NamedEntityTaggingEngine instance for the Entityhub");
        //add the type constraint
  //  if(types != null && !types.isEmpty()) {
    //    query.setConstraint(RDF_TYPE.getUnicodeString(), new ReferenceConstraint(types));
    //}
     //  query.setConstraint(SpecialFieldEnum.fullText.getUri(), new
//SimilarityConstraint(extractionContext));

      query.setLimit(Math.max(20,9));
       QueryResultList<Entity> results = entityhub.findEntities(query);
       
         JOptionPane.showMessageDialog(null, results.size());  
        
        }
        catch(Exception e){}
		
      /*  log.info(" - {} results returned by query {}", results.size(), results.getQuery());
        if(results.isEmpty()){ //no results nothing to do
            return Collections.emptyList();
        }
        */

        
        //search the suggestions
      /*  Map<NamedEntity,List<Suggestion>> suggestions = new HashMap<NamedEntity,List<Suggestion>>(textAnnotations.size());
        for (Entry<NamedEntity,List<UriRef>> entry : textAnnotations.entrySet()) {
            try {
                List<Suggestion> entitySuggestions = computeEntityRecommentations(
                    site, entry.getKey(),entry.getValue(),contentLangauge);
                if(entitySuggestions != null && !entitySuggestions.isEmpty()){
                    suggestions.put(entry.getKey(), entitySuggestions);
                }
            } catch (EntityhubException e) {
                throw new EngineException(this, ci, e);
            }
        }
        //now write the results (requires write lock)
        ci.getLock().writeLock().lock();
        try {
            RdfValueFactory factory = RdfValueFactory.getInstance();
            Map<String, Representation> entityData = new HashMap<String,Representation>();
            for(Entry<NamedEntity,List<Suggestion>> entitySuggestions : suggestions.entrySet()){
                List<UriRef> subsumed = textAnnotations.get(entitySuggestions.getKey());
                List<NonLiteral> annotationsToRelate = new ArrayList<NonLiteral>(subsumed);
                annotationsToRelate.add(entitySuggestions.getKey().getEntity());
                for(Suggestion suggestion : entitySuggestions.getValue()){
                    log.debug("Add Suggestion {} for {}", suggestion.getEntity().getId(), entitySuggestions.getKey());
                    EnhancementRDFUtils.writeEntityAnnotation(this, literalFactory, graph, ci.getUri(),
                        annotationsToRelate, suggestion, nameField,
                        //TODO: maybe we want labels in a different language than the
                        //      language of the content (e.g. Accept-Language header)?!
                        contentLangauge == null ? DEFAULT_LANGUAGE : contentLangauge);
                    if (dereferenceEntities) {
                        entityData.put(suggestion.getEntity().getId(), suggestion.getEntity().getRepresentation());
                    }
                }
            }
            //if dereferneceEntities is true the entityData will also contain all
            //Representations to add! If false entityData will be empty
            for(Representation rep : entityData.values()){
                graph.addAll(factory.toRdfRepresentation(rep).getRdfGraph());
            }
        } finally {
            ci.getLock().writeLock().unlock();
        }*/

        /*
        
        
      Entry<UriRef,Blob> contentPart = ContentItemHelper.getBlob(ci, SUPPORTED_MIMETYPES);
        String text="";
       // String kds="s";
       //text = ContentItemHelper.getText(contentPart.getValue());
        try {
            text = ContentItemHelper.getText(contentPart.getValue());
        } catch (IOException e) {
            throw new InvalidContentException(String.format(
                "Unable to extract " + " textual content from ContentPart %s of ContentItem %s!",
                contentPart.getKey(), ci.getUri()), e);
        }
        
      
         JOptionPane.showMessageDialog(null, text);   
        //JOptionPane.showMessageDialog(null,kds);
        try {
            // get the metadata graph
            MGraph metadata = ci.getMetadata();
            // update some sample data
            UriRef textAnnotation = EnhancementEngineHelper.createTextEnhancement(ci, this);
            metadata.add(new TripleImpl(textAnnotation, ENHANCER_ENTITY_LABEL, new PlainLiteralImpl("FORMCEPT")));
            LOG.info("FORMCEPT: Enhancement Succeeded");
        } finally {
            //ci.getLock().writeLock().unlock();
        }*/
    }
    
    /**
     * Activate and read the properties
     * @param ce the {@link ComponentContext}
     */
    @Activate
    protected void activate(ComponentContext ce) throws ConfigurationException {
        try {
            super.activate(ce);
        } catch (IOException e) {
            // log
            LOG.error("Failed to update the configuration", e);
        }
        @SuppressWarnings("unchecked")
        Dictionary<String, Object> properties = ce.getProperties();
        // update the service URL if it is defined
        if(properties.get(FORMCEPT_SERVICE_URL) != null){
            this.serviceURL = (String) properties.get(FORMCEPT_SERVICE_URL);
        }
    }
    
    /**
     * Deactivate
     * @param ce the {@link ComponentContext}
     */
    @Deactivate
    protected void deactivate(ComponentContext ce) {
        super.deactivate(ce);
    }

    /**
     * Gets the Service URL
     * @return
     */
    public String getServiceURL() {
        return serviceURL;
    }
            
}
