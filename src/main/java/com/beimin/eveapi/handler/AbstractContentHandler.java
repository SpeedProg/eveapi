package com.beimin.eveapi.handler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.beimin.eveapi.response.ApiResponse;
import com.beimin.eveapi.utils.DateUtils;

public abstract class AbstractContentHandler extends DefaultHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractContentHandler.class);

	protected StringBuffer accumulator = new StringBuffer(); // Accumulate parsed text
	private ApiError error;
	private DateTimeFormatter dtFormatter;
	
	public AbstractContentHandler() {
		DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
		builder.appendPattern("uuuu-MM-dd kk:mm:ss");
		dtFormatter = builder.toFormatter();
	}

	@Override
	public void characters(char[] buffer, int start, int length) {
		accumulator.append(buffer, start, length);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
		if (qName.equals("eveapi")) {
			getResponse().setVersion(getInt(attrs, "version"));
		} else if (qName.equals("error")) {
			error = new ApiError();
			error.setCode(getInt(attrs, "code"));
			getResponse().setError(error);
		}
		accumulator.setLength(0);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("currentTime"))
			getResponse().setCurrentTime(getOffsetDateTime());
		else if (qName.equals("cachedUntil"))
			getResponse().setCachedUntil(getOffsetDateTime());
		else if (qName.equals("error"))
			error.setError(getString());
	}

	protected String getString() {
		return accumulator.toString().trim();
	}

	protected String getString(Attributes attrs, String qName) {
		return attrs.getValue(qName);
	}

	protected Date getDate() {
		return getDate(getString());
	}

	protected Date getDate(String string) {
		return DateUtils.getGMTConverter().convert(Date.class, string);
	}

	protected Date getDate(Attributes attrs, String qName) {
		return getDate(getString(attrs, qName));
	}

	protected Integer getInt() {
		return parseInteger(getString());
	}

	protected Integer getInt(Attributes attrs, String qName) {
		return parseInteger(getString(attrs, qName));
	}

	protected Integer parseInteger(String value) {
		Integer result = null;
		if(value != null && !value.trim().isEmpty()) {
			try {
				result = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				LOGGER.error("Couldn't parse number", e);
			} catch (NullPointerException e) {
				LOGGER.error("Couldn't parse number", e);
			}
		}
		return result;
	}
	
	protected Long getLong() {
		return parseLong(getString());
	}

	protected Long getLong(Attributes attrs, String qName) {
		return parseLong(getString(attrs, qName));
	}

	protected Long parseLong(String value) {
		Long result = null;
		if(value != null && !value.trim().isEmpty()) {
			try {
				result = Long.parseLong(value);
			} catch (NumberFormatException e) {
				LOGGER.error("Couldn't parse number", e);
			} catch (NullPointerException e) {
				LOGGER.error("Couldn't parse number", e);
			}
		}
		return result;
	}
	
	protected Double getDouble() {
		return parseDouble(getString());
	}

	protected Double getDouble(Attributes attrs, String qName) {
		return parseDouble(getString(attrs, qName));
	}

	protected Double parseDouble(String value) {
		Double result = null;
		if(value != null && !value.trim().isEmpty()) {
			try {
				result = Double.parseDouble(value);
			} catch (NumberFormatException e) {
				LOGGER.error("Couldn't parse number", e);
			} catch (NullPointerException e) {
				LOGGER.error("Couldn't parse number", e);
			}
		}
		return result;
	}
	
	protected boolean getBoolean() {
		return "1".equals(getString()) || "true".equalsIgnoreCase(getString());
	}

	protected boolean getBoolean(Attributes attrs, String qName) {
		return "1".equals(getString(attrs, qName)) || "true".equalsIgnoreCase(getString(attrs, qName));
	}
	
	protected BigDecimal getBigDecimal() {
		String s = getString();
		if (s.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(s);
	}

	protected BigDecimal getBigDecimal(Attributes attrs, String qName) {
		String s = getString(attrs, qName);
		if (s.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(getString(attrs, qName));
	}
	
	protected OffsetDateTime getOffsetDateTime() {
		String s = getString();
		// evetime is utc through we don't get offset data
		OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.parse(s, dtFormatter), ZoneOffset.UTC); 
		return odt;
	}
	
	protected OffsetDateTime getOffsetDateTime(Attributes attrs, String qName) {
		String s = getString(attrs, qName);
		// evetime is utc through we don't get offset data
		OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.parse(s, dtFormatter), ZoneOffset.UTC); 
		return odt;
	}
	
	public abstract ApiResponse getResponse();
}