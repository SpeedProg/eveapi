package com.beimin.eveapi.character.wallet.journal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.model.shared.JournalEntry;
import com.beimin.eveapi.model.shared.RefType;
import com.beimin.eveapi.parser.ApiPage;
import com.beimin.eveapi.parser.ApiPath;
import com.beimin.eveapi.parser.pilot.WalletJournalParser;
import com.beimin.eveapi.parser.shared.AbstractWalletJournalParser;
import com.beimin.eveapi.response.shared.WalletJournalResponse;
import com.beimin.eveapi.utils.FullAuthParserTest;

public class JournalParserTest extends FullAuthParserTest {
	public JournalParserTest() {
		super(ApiPath.CHARACTER, ApiPage.WALLET_JOURNAL);
	}

	@Test
	public void getResponse() throws ApiException {
		OffsetDateTime expectedDateTime = OffsetDateTime.of(2008, 8, 20, 13, 10, 0, 0, ZoneOffset.UTC);
		BigDecimal expectedAmount = new BigDecimal("135000.00");
		BigDecimal expectedBalance = new BigDecimal("609292267.52");
		BigDecimal expectedTaxAmount = new BigDecimal("15000.00");
		AbstractWalletJournalParser parser = new WalletJournalParser();
		WalletJournalResponse response = parser.getResponse(auth, 1000);
		assertNotNull(response);
		Collection<JournalEntry> entries = response.getAll();
		assertEquals(10, entries.size());
		boolean found = false;
		for (JournalEntry journalEntry : entries) {
			if (journalEntry.getRefID() == 1575178032L) {
				found = true;
				assertTrue(expectedDateTime.equals(journalEntry.getDate()));
				assertEquals(RefType.BOUNTY_PRIZES, journalEntry.getRefType());
				assertEquals(1000125, journalEntry.getOwnerID1());
				assertEquals("CONCORD", journalEntry.getOwnerName1());
				assertEquals(173993711, journalEntry.getOwnerID2());
				assertEquals("anonymous", journalEntry.getOwnerName2());
				assertEquals(30001660L, journalEntry.getArgID1());
				assertEquals("Jita", journalEntry.getArgName1());
				assertTrue(expectedAmount.equals(journalEntry.getAmount()));
				assertTrue(expectedBalance.equals(journalEntry.getBalance()));
				assertEquals("29200:15,", journalEntry.getReason());
				assertEquals(1734917694L, journalEntry.getTaxReceiverID().longValue());
				assertTrue(expectedTaxAmount.equals(journalEntry.getTaxAmount()));
			}
		}
		assertTrue("test journal entry wasn't found.", found);
	}

	@Override
	public void extraAsserts(Map<String, String> req) {
		super.extraAsserts(req);
		assertEquals("1000", req.get("accountKey"));
	}
}