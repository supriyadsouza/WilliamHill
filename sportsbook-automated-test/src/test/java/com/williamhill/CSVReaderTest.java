package com.williamhill;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A test to verify is headers and rows are properly read from a CSV file.
 * Class under test: com.williamhill.CSVReader
 */
public class CSVReaderTest extends TestCase {

	@Test
	public void testReadFromDatabank() throws Exception {
		FileInputStream fis = new FileInputStream(new File("./databank.csv"));
		CSVReader reader = new CSVReader(new InputStreamReader(fis, "UTF-8"));
		reader.readHeaders();
		
		String[] expectedHeaders = new String[] {"SPORT", "EVENT"};
		Assert.assertArrayEquals(expectedHeaders, reader.getHeaders());
		
		List<String> actualSports = new ArrayList<String>();
		List<String> actualEvents = new ArrayList<String>();
		while (reader.readRecord()) {
			String sport = reader.get("SPORT");
			String event = reader.get("EVENT");
			actualSports.add (sport);
			actualEvents.add(event);
		}
		reader.close();
		fis.close();
		
		String expectedSports[] = new String[] {"Football", "Cricket", "Football"};
		String expectedEvents[] = new String[] {"Chelsea   v   Man Utd", "Mumbai   v   Chennai", "Reading   v   Arsenal"};
		Assert.assertArrayEquals(expectedSports, actualSports.toArray());
		Assert.assertArrayEquals(expectedEvents, actualEvents.toArray());		
	}
}
