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
package org.apache.stanbol.enhancer.engine.disambiguation.mlt;

import static org.apache.commons.lang.StringUtils.getLevenshteinDistance;
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
import org.apache.commons.lang.StringUtils;
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
import org.apache.stanbol.entityhub.servicesapi.query.SimilarityConstraint;
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
 * 
 * @author Kritarth
 */
@Component(immediate = true, metatype = true)
@Service
@Properties(value = {@Property(name = EnhancementEngine.PROPERTY_NAME, value = "entity-disambiguator")})
public class DisambiguatorEngine extends AbstractEnhancementEngine<IOException,RuntimeException> implements
        EnhancementEngine, ServiceProperties {

    private static Logger LOG = LoggerFactory.getLogger(DisambiguatorEngine.class);

   
    /**
     * Service URL
     */
    private String serviceURL;

    /**
     * The default value for the Execution of this Engine. Currently set to
     * {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT} + 17. It should run after Metaxa and LangId.
     */
    public static final Integer defaultOrder = ServiceProperties.ORDERING_EXTRACTION_ENHANCEMENT - 10;
    public static final String PLAIN_TEXT_MIMETYPE = "text/plain";

    @Reference
    protected ReferencedSiteManager siteManager;
    /**
     * Contains the only supported mime type {@link #PLAIN_TEXT_MIMETYPE}
     */

    public static final Set<String> SUPPORTED_MIMETYPES = Collections.singleton(PLAIN_TEXT_MIMETYPE);


    public Map<String,Object> getServiceProperties() {
        return Collections.unmodifiableMap(Collections.singletonMap(ENHANCEMENT_ENGINE_ORDERING,
            (Object) defaultOrder));
    }

    public int canEnhance(ContentItem ci) throws EngineException {
        // check if content is present
        try {
            if ((ContentItemHelper.getText(ci.getBlob()) == null)
                || (ContentItemHelper.getText(ci.getBlob()).trim().isEmpty())) {
                return CANNOT_ENHANCE;
            }
        } catch (IOException e) {
            LOG.error("Failed to get the text for " + "enhancement of content: " + ci.getUri(), e);
            throw new InvalidContentException(this, ci, e);
        }
        // default enhancement is synchronous enhancement
        return ENHANCE_SYNCHRONOUS;
    }

	/*
	  This function first evaluates all the possible ambiguations of each text annotation detected. the text of all entities detected is used for making a Dbpedia query with all string for MLT that contain all the other entities. The results obtained are used to calcualte new confidence values which are updated in the metadata.
	 */	

    public void computeEnhancements(ContentItem ci) throws EngineException {

        MGraph graph = ci.getMetadata();
        LiteralFactory literalFactory = LiteralFactory.getInstance();

        List<String> allEntities = new ArrayList<String>();
        Map<SavedEntity,List<UriRef>> textAnnotations = new HashMap<SavedEntity,List<UriRef>>();
        String contentLangauge = null;

	
        List<Triple> loseConfidence = new ArrayList<Triple>();//List to contain old confidence values that are to removed
        List<Triple> gainConfidence = new ArrayList<Triple>();//List to contain new confidence values to be added to metadata
        ci.getLock().readLock().lock();
        try {
            contentLangauge = EnhancementEngineHelper.getLanguage(ci);
            readEntities(loseConfidence, allEntities, textAnnotations, graph);
        } catch (Exception e) {
            LOG.info(" readEntities" + e.getMessage());
        }
        ci.getLock().readLock().unlock();
        ReferencedSite dbpediaReferencedSite = null;
        try {
            dbpediaReferencedSite = siteManager.getReferencedSite("dbpedia");

            for (Entry<SavedEntity,List<UriRef>> entry : textAnnotations.entrySet()) {

                SavedEntity savedEntity = entry.getKey();
                String label = savedEntity.getName();// the selected text of the TextAnnotation to
                                                     // disambiguate
                Collection<String> types = null; // potential types of entities
                String language = contentLangauge; // the language of the analyzed text
                List<UriRef> subsumed = entry.getValue();
                boolean casesensitive = false;
                String savedEntityLabel = casesensitive ? label : label.toLowerCase();

                if (subsumed.size() <= 1) {
                    continue;
                }
                String extractionContext = findContext(label, allEntities); // the surrounding text of the
                                                                            // extraction
                QueryResultList<Entity> results = queryDbpedia(dbpediaReferencedSite, savedEntityLabel,
                    language, extractionContext);
                LOG.info(" - {} results returned by query {}", results.size(), results.getQuery());
                List<Suggestion> matches = rankResults(results, casesensitive, language, savedEntityLabel);
                Collections.sort(matches);

                ci.getLock().readLock().lock();
                try {
                    if (intersectionCheck(matches, subsumed, graph, contentLangauge)) {
                        gainConfidence = intersection(matches, subsumed, graph, gainConfidence,
                            contentLangauge);
                    } else {
                        loseConfidence = unchangedConfidences(subsumed, graph, loseConfidence);
                    }
                } finally {
                    ci.getLock().readLock().unlock();
                }
            }

            ci.getLock().writeLock().lock();
            try {
                removeOldConfidenceFromGraph(graph, loseConfidence);
                addNewConfidenceToGraph(graph, gainConfidence);
            } finally {
                ci.getLock().writeLock().unlock();
            }

        }

        catch (Exception e) {
            LOG.info("Error");
        }
    }

    /* We create  a data structure that stores the mapping of text annotation to List of Uri of all possible amiguations of the Text. Also it fills the list loseconfidence with confidence values of all the ambiguations for all entities (which will be removed eventually)*/

    protected void readEntities(List<Triple> loseConfidence,
                                List<String> allEntities,
                                Map<SavedEntity,List<UriRef>> textAnnotations,
                                MGraph graph) {

        for (Iterator<Triple> it = graph.filter(null, RDF_TYPE, TechnicalClasses.ENHANCER_TEXTANNOTATION); it
                .hasNext();) {
            UriRef uri = (UriRef) it.next().getSubject();
            String selectText = EnhancementEngineHelper.getString(graph, uri, ENHANCER_SELECTED_TEXT);
            if (graph.filter(uri, new UriRef(NamespaceEnum.dc + "relation"), null).hasNext()) {
                continue;
            }
            SavedEntity savedEntity = SavedEntity.createFromTextAnnotation(graph, uri);
            if (savedEntity != null) {
                allEntities.add(selectText);
                List<UriRef> confidenceUriList = new ArrayList<UriRef>();
                for (Iterator<Triple> it2 = graph
                        .filter(null, new UriRef(NamespaceEnum.dc + "relation"), uri); it2.hasNext();) {
                    UriRef uriAmbiguations = (UriRef) it2.next().getSubject();
                    Iterator<Triple> confidenceTriple = graph.filter(uriAmbiguations, ENHANCER_CONFIDENCE,
                        null);
                    while (confidenceTriple.hasNext()) {
                        loseConfidence.add(confidenceTriple.next());
                    }
                    String name1 = EnhancementEngineHelper.getString(graph, uriAmbiguations,
                        ENHANCER_CONFIDENCE);
                    confidenceUriList.add(uriAmbiguations);
                    
                }
textAnnotations.put(savedEntity, confidenceUriList);
            }
        }
        return;
    }

/*Is used to query the Dbpedia with a entity as main constraint and then add string of all other entities detected as similarity constraints*/

    protected QueryResultList<Entity> queryDbpedia(ReferencedSite dbpediaReferencedSite,
                                                   String savedEntityLabel,
                                                   String language,
                                                   String extractionContext) throws ReferencedSiteException {

        FieldQuery query = dbpediaReferencedSite.getQueryFactory().createFieldQuery();
        Constraint labelConstraint;
        if (language != null) {
            labelConstraint = new TextConstraint(savedEntityLabel, false, language, null);
        } else {
            labelConstraint = new TextConstraint(savedEntityLabel, false);
        }

        query.setConstraint(RDFS_LABEL.getUnicodeString(), labelConstraint);
        LOG.info("Init SavedEntityTaggingEngine instance for the Entityhub");
        query.setConstraint("http://stanbol.apache.org/ontology/entityhub/query#fullText",
            new SimilarityConstraint(extractionContext));
        // query.setLimit(Math.max(20,9));

        return dbpediaReferencedSite.findEntities(query);
    }

/*If for an entity the Dbpedia query results in suggestion none of which match the already present ambiguations, we go with the ambiguations found earlier that is the ones we have with.*/

    protected List<Triple> unchangedConfidences(List<UriRef> subsumed,
                                                MGraph graph,
                                                List<Triple> loseConfidence) {
        for (int i = 0; i < subsumed.size(); i++) {
            UriRef uri = subsumed.get(i);
            Iterator<Triple> confidenceTriple = graph.filter(uri, ENHANCER_CONFIDENCE, null);
            while (confidenceTriple.hasNext()) {
                loseConfidence.remove(confidenceTriple.next());
            }
        }
        return loseConfidence;
    }
/*To convert the values from Dbpedia into score values. */
    protected List<Suggestion> rankResults(QueryResultList<Entity> results,
                                           boolean casesensitive,
                                           String language,
                                           String savedEntityLabel) {
        List<Suggestion> matches = new ArrayList<Suggestion>(results.size());
        Float maxScore = null;
        Float maxExactScore = null;

        for (Iterator<Entity> guesses = results.iterator(); guesses.hasNext();) {
            Suggestion match = new Suggestion(guesses.next());
            Representation rep = match.getEntity().getRepresentation();
            Float score = rep.getFirst(RdfResourceEnum.resultScore.getUri(), Float.class);
            if (maxScore == null) {
                maxScore = score;
            }
            Iterator<Text> labels = rep.getText(RDFS_LABEL.getUnicodeString());
            while (labels.hasNext() && match.getLevenshtein() < 1.0) {
                Text label1 = labels.next();
                if (language == null || // if the content language is unknown -> accept all labels
                    label1.getLanguage() == null || // accept labels with no language
                    // and labels in the same language as the content
                    (language != null && label1.getLanguage().startsWith(language))) {
                    double actMatch = levenshtein(
                        casesensitive ? label1.getText().toLowerCase() : label1.getText(), savedEntityLabel);
                    if (actMatch > match.getLevenshtein()) {
                        match.setLevenshtein(actMatch);
                        match.setMatchedLabel(label1);
                    }
                }
            }
            if (match.getMatchedLabel() != null) {
                if (match.getLevenshtein() == 1.0) {
                    if (maxExactScore == null) {
                        maxExactScore = score;
                    }
                    // normalise exact matches against the best exact score
                    match.setScore(score.doubleValue() / maxExactScore.doubleValue());
                } else {
                    // normalise partial matches against the best match and the
                    // Levenshtein similarity with the label
                    match.setScore(score.doubleValue() * match.getLevenshtein() / maxScore.doubleValue());
                }
                matches.add(match);
            } else {
                LOG.info("No value of {} for Entity {}!", RDFS_LABEL.getUnicodeString(), match.getEntity()
                        .getId());
            }
        }
        return matches;
    }

/*Checks if there is any common elements amongst the ambiguations amongst latest dbpedia query and intial ambiguations*/

    protected boolean intersectionCheck(List<Suggestion> matches,
                                        List<UriRef> subsumed,
                                        MGraph graph,
                                        String contentLangauge) {
        for (int i = 0; i < subsumed.size(); i++) {
            UriRef uri = subsumed.get(i);
            String selectedText = EnhancementEngineHelper.getString(graph, uri, ENHANCER_ENTITY_LABEL);

            if (selectedText == null) {
                continue;
            }

            for (int j = 0; j < matches.size(); j++) {
                Suggestion suggestion = matches.get(j);
                String suggestName = suggestion.getMatchedLabel().toString();
                if (suggestName.compareToIgnoreCase(selectedText + "@" + contentLangauge) == 0) return true;
            }
        }
        return false;
    }


/*Finds values the lie in intersection of both the set of disambiguations( the one intially suggested and the one from dpedia). Update the confidence values of those and make the confidence values of others as 0 in gainconfidence list*/
    protected List<Triple> intersection(List<Suggestion> matches,
                                        List<UriRef> subsumed,
                                        MGraph graph,
                                        List<Triple> gainConfidence,
                                        String contentLangauge) {
        for (int i = 0; i < subsumed.size(); i++) {
            boolean matchFound = false;
            UriRef uri = subsumed.get(i);
            String selectedText = EnhancementEngineHelper.getString(graph, uri, ENHANCER_ENTITY_LABEL);
            // int c=0;
            for (int j = 0; j < matches.size(); j++) {
                Suggestion suggestion = matches.get(j);
                String suggestName = suggestion.getMatchedLabel().toString();
                if (selectedText == null) {
                    continue;
                }
                if (suggestName.compareToIgnoreCase(selectedText + "@" + contentLangauge) == 0) {
                    Triple confidenceTriple = new TripleImpl(uri, ENHANCER_CONFIDENCE, LiteralFactory
                            .getInstance().createTypedLiteral(suggestion.getScore()));
                    Triple contributorTriple = new TripleImpl((UriRef) confidenceTriple.getSubject(),
                            new UriRef(NamespaceEnum.dc + "contributor"), LiteralFactory.getInstance()
                                    .createTypedLiteral(this.getClass().getName()));
                    gainConfidence.add(confidenceTriple);
                    gainConfidence.add(contributorTriple);
                    matchFound = true;
                }
            }

            if (!matchFound) {
                Triple confidenceTriple = new TripleImpl(uri, ENHANCER_CONFIDENCE, LiteralFactory
                        .getInstance().createTypedLiteral(0.0));
                Triple contributorTriple = new TripleImpl((UriRef) confidenceTriple.getSubject(), new UriRef(
                        NamespaceEnum.dc + "contributor"), LiteralFactory.getInstance().createTypedLiteral(
                    this.getClass().getName()));
                gainConfidence.add(confidenceTriple);
                gainConfidence.add(contributorTriple);
            }
        }
        return gainConfidence;
    }
/*Removes the value in lose confidence from the graph*/
    protected void removeOldConfidenceFromGraph(MGraph graph, List<Triple> loseConfidence) {
        for (int i = 0; i < loseConfidence.size(); i++) {
            Triple elementToRemove = loseConfidence.get(i);
            graph.remove(elementToRemove);
        }
    }
/* adds the confidence values from gain confidence list to graph*/
    protected void addNewConfidenceToGraph(MGraph graph, List<Triple> gainConfidence) {
        for (int i = 0; i < gainConfidence.size(); i++) {
            Triple elementToAdd = gainConfidence.get(i);
            graph.add(elementToAdd);
        }
    }
/*Returns a string on appended text annotations seperated by spaces*/
    protected String findContext(String label, List<String> allEntities) {
        String allEntityString = "";
        for (int i = 0; i < allEntities.size(); i++) {

            if (label.compareToIgnoreCase(allEntities.get(i)) != 0) {
                allEntityString = allEntityString + allEntities.get(i);
            }
        }
        return allEntityString;
    }

    /**
     * Activate and read the properties
     * 
     * @param ce
     *            the {@link ComponentContext}
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
        Dictionary<String,Object> properties = ce.getProperties();
        // update the service URL if it is defined
       // if (properties.get(FORMCEPT_SERVICE_URL) != null) {
         //   this.serviceURL = (String) properties.get(FORMCEPT_SERVICE_URL);
        //}
    }

    /**
     * Deactivate
     * 
     * @param ce
     *            the {@link ComponentContext}
     */
    @Deactivate
    protected void deactivate(ComponentContext ce) {
        super.deactivate(ce);
    }

    /**
     * Gets the Service URL
     * 
     * @return
     */
    public String getServiceURL() {
        return serviceURL;
    }

    private static double levenshtein(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("NONE of the parsed String MUST BE NULL!");
        }
        s1 = StringUtils.trim(s1);
        s2 = StringUtils.trim(s2);
        return s1.isEmpty() || s2.isEmpty() ? 0
                : 1.0 - (((double) getLevenshteinDistance(s1, s2)) / ((double) (Math.max(s1.length(),
                    s2.length()))));
    }

}