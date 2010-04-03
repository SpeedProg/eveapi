package com.beimin.eveapi.standings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collection;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ParserTest {

	@Test
	public void testStandingsParserCorp() throws IOException, SAXException, ParseException {
		StandingsParser parser = StandingsParser.getInstance();
		InputStream input = ParserTest.class.getResourceAsStream("/CorpStandings.xml");
		StandingsResponse response = parser.getResponse(input);
		assertNotNull(response);
		
		Collection<StandingsList> corporationStandingsTo = response.getCorporationStandingsTo();
		assertNotNull(corporationStandingsTo);
		assertEquals(3, corporationStandingsTo.size());
		for (StandingsList standingsList : corporationStandingsTo) {
			assertNotNull(standingsList);
			String name = standingsList.getName();
			if(name.equals("characters")) {
				assertEquals(3, standingsList.size());
			} else if(name.equals("corporations")) {
				assertEquals(4, standingsList.size());
			} else if(name.equals("alliances")) {
				assertEquals(7, standingsList.size());
			} 
		}
		
		Collection<StandingsList> corporationStandingsFrom = response.getCorporationStandingsFrom();
		assertNotNull(corporationStandingsFrom);
		assertEquals(3, corporationStandingsFrom.size());
		for (StandingsList standingsList : corporationStandingsTo) {
			assertNotNull(standingsList);
			String name = standingsList.getName();
			if(name.equals("agents")) {
				assertEquals(8, standingsList.size());
			} else if(name.equals("NPCCorporations")) {
				assertEquals(9, standingsList.size());
			} else if(name.equals("factions")) {
				assertEquals(20, standingsList.size());
			} 
		}
		
		Collection<StandingsList> allianceStandingsTo = response.getAllianceStandingsTo();
		assertNotNull(allianceStandingsTo);
		assertEquals(2, allianceStandingsTo.size());
		for (StandingsList standingsList : allianceStandingsTo) {
			assertNotNull(standingsList);
			String name = standingsList.getName();
			if(name.equals("corporations")) {
				assertEquals(9, standingsList.size());
			} else if(name.equals("alliances")) {
				assertEquals(27, standingsList.size());
			}
		}
	}
	
	@Test
	public void testStandingsParserChar() throws IOException, SAXException, ParseException {
		StandingsParser parser = StandingsParser.getInstance();
		InputStream input = ParserTest.class.getResourceAsStream("/CharStandings.xml");
		StandingsResponse response = parser.getResponse(input);
		assertNotNull(response);
		
		Collection<StandingsList> characterStandingsTo = response.getCharacterStandingsTo();
		assertNotNull(characterStandingsTo);
		assertEquals(2, characterStandingsTo.size());
		for (StandingsList standingsList : characterStandingsTo) {
			assertNotNull(standingsList);
			String name = standingsList.getName();
			if(name.equals("characters")) {
				assertEquals(0, standingsList.size());
			} else if(name.equals("corporations")) {
				assertEquals(2, standingsList.size());
			}
		}
		
		Collection<StandingsList> characterStandingsFrom = response.getCharacterStandingsFrom();
		assertNotNull(characterStandingsFrom);
		assertEquals(3, characterStandingsFrom.size());
		for (StandingsList standingsList : characterStandingsFrom) {
			assertNotNull(standingsList);
			String name = standingsList.getName();
			if(name.equals("agents")) {
				assertEquals(2, standingsList.size());
			} else if(name.equals("NPCCorporations")) {
				assertEquals(3, standingsList.size());
			} else if(name.equals("factions")) {
				assertEquals(16, standingsList.size());
			} 
		}
	}
}