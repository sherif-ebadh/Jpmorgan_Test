package test.com.jpmorgan;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.jpmorgan.bean.Record;
import com.jpmorgan.controller.RecordController;

public class RecordControllerTest {

	RecordController rc = new RecordController();

	@Test
	public void TestInvalidInstructionDate() {
		// Test invalid Date instructionDate in the past

		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Jan 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);

		rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestInvalidSettlementDate() {
		// Test invalid Date settlementDate in the past
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Jan 2017", "", 200, 100.25);
		assertNull(rec);

		rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Jan 2017", "08 Sep 2016", 200, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestInvalidDates() {
		// Test invalid Date settlementDateis before instructionDate
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Oct 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestInvalidUnitRecord() {

		Record rec = rc.addNewRecord("foo", "", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", -1, 100.25);
		assertNull(rec);
		rec = rc.addNewRecord("foo", null, 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 0, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestInvalidEntity() {

		Record rec = rc.addNewRecord("", "B", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
		rec = rc.addNewRecord(null, "B", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestInvalidUnitPrice() {

		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 0);
		assertNull(rec);
		rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, -1);
		assertNull(rec);
	}

	@Test
	public void TestInvalidAgreedFX() {

		Record rec = rc.addNewRecord("foo", "B", 0, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
		rec = rc.addNewRecord("foo", "B", -1, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestInvalidCurrency() {

		Record rec = rc.addNewRecord("foo", "B", 0.50f, null, "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
		rec = rc.addNewRecord("foo", "B", 0.50f, "", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestInvalidType() {

		Record rec = rc.addNewRecord("foo", "", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
		rec = rc.addNewRecord("foo", null, 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNull(rec);
	}

	@Test
	public void TestAddValidRecord() {

		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNotNull(rec);
		rec = rc.addNewRecord("Boo", "S", 0.50f, "SGP", "01 Sep 2017", "08 Sep 2017", 200, 100.25);
		assertNotNull(rec);
	}

	@Test
	public void TestAddWeekEndRecordForFridayAndSaturday() throws ParseException {
		List<Record> records = new ArrayList<Record>();
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "AED", "01 Sep 2017", "01 Sep 2017", 200, 100.25);
		records.add(rec);
		Record rec2 = rc.addNewRecord("foo", "B", 0.50f, "AED", "01 Sep 2017", "02 Sep 2017", 200, 100.25);
		records.add(rec2);

		List<Record> outGoingList = rc.getOutgoingRecords(records);
		assertEquals(0, (rc.getIncomingRecords(records)).size());
		assertEquals(2, outGoingList.size());

		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Date dateObj = formatter.parse("03 Sep 2017");

		assertEquals(dateObj, outGoingList.get(0).getAccSetDate());
		assertEquals(dateObj, outGoingList.get(1).getAccSetDate());

	}

	@Test
	public void TestAddWeekEndRecordForSaturdayAndSunday() throws ParseException {
		List<Record> records = new ArrayList<Record>();
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "02 Sep 2017", 200, 100.25);
		records.add(rec);
		Record rec2 = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec2);

		List<Record> outGoingList = rc.getOutgoingRecords(records);
		assertEquals(0, (rc.getIncomingRecords(records)).size());
		assertEquals(2, outGoingList.size());

		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Date dateObj = formatter.parse("04 Sep 2017");

		assertEquals(dateObj, outGoingList.get(0).getAccSetDate());
		assertEquals(dateObj, outGoingList.get(1).getAccSetDate());

	}

	@Test
	public void TestPrintingIncomingRepoert() throws ParseException {
		List<Record> records = new ArrayList<Record>();
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "04 Sep 2017", 200, 100.25);
		records.add(rec);
		Record rec2 = rc.addNewRecord("foo", "B", 0.50f, "AED", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec2);
		Record rec3 = rc.addNewRecord("Boo", "B", 0.50f, "SGP", "01 Sep 2017", "06 Sep 2017", 200, 100.25);
		records.add(rec3);
		Record rec4 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec4);
		Record rec5 = rc.addNewRecord("BPP", "S", 0.50f, "AED", "01 Sep 2017", "05 Sep 2017", 200, 100.25);
		records.add(rec5);
		Record rec6 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec6);

		List<Record> outGoingList = rc.getOutgoingRecords(records);
		List<Record> incomingList = rc.getIncomingRecords(records);

		assertEquals(3, incomingList.size());
		assertEquals(3, outGoingList.size());

		String incomingReport = rc.generateIncoming(incomingList);
		assertThat(incomingReport, containsString("05 Sep 2017 = 10025.0"));
		assertThat(incomingReport, containsString("04 Sep 2017 = 20050.0"));

	}
	
	@Test
	public void TestPrintingoutgoingRepoert() throws ParseException {
		List<Record> records = new ArrayList<Record>();
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "04 Sep 2017", 200, 100.25);
		records.add(rec);
		Record rec2 = rc.addNewRecord("foo", "B", 0.50f, "AED", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec2);
		Record rec3 = rc.addNewRecord("Boo", "B", 0.50f, "SGP", "01 Sep 2017", "06 Sep 2017", 200, 100.25);
		records.add(rec3);
		Record rec4 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec4);
		Record rec5 = rc.addNewRecord("BPP", "S", 0.50f, "AED", "01 Sep 2017", "05 Sep 2017", 200, 100.25);
		records.add(rec5);
		Record rec6 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec6);

		List<Record> outGoingList = rc.getOutgoingRecords(records);
		List<Record> incomingList = rc.getIncomingRecords(records);

		assertEquals(3, incomingList.size());
		assertEquals(3, outGoingList.size());

		String outgoingReport = rc.generateOutgoing(outGoingList);
		assertThat(outgoingReport, containsString("06 Sep 2017 = 10025.0"));
		assertThat(outgoingReport, containsString("03 Sep 2017 = 10025.0"));
		assertThat(outgoingReport, containsString("04 Sep 2017 = 10025.0"));


	}
	
	@Test
	public void TestPrintingIncomingRanking() throws ParseException {
		List<Record> records = new ArrayList<Record>();
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "04 Sep 2017", 200, 100.25);
		records.add(rec);
		Record rec2 = rc.addNewRecord("foo", "B", 0.50f, "AED", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec2);
		Record rec3 = rc.addNewRecord("Boo", "B", 0.50f, "SGP", "01 Sep 2017", "06 Sep 2017", 200, 100.25);
		records.add(rec3);
		Record rec4 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec4);
		Record rec5 = rc.addNewRecord("BPP", "S", 0.50f, "AED", "01 Sep 2017", "05 Sep 2017", 200, 100.25);
		records.add(rec5);
		Record rec6 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec6);

		List<Record> outGoingList = rc.getOutgoingRecords(records);
		List<Record> incomingList = rc.getIncomingRecords(records);

		assertEquals(3, incomingList.size());
		assertEquals(3, outGoingList.size());


		String incomingReport = rc.printIncomingRanking(incomingList);
		assertThat(incomingReport, containsString("Rank : 1 foo = 20050.0"));
		assertThat(incomingReport, containsString("Rank : 2 BPP = 10025.0"));

	}
	
	@Test
	public void TestPrintingOutgoingRepoert() throws ParseException {
		List<Record> records = new ArrayList<Record>();
		Record rec = rc.addNewRecord("foo", "B", 0.50f, "SGP", "01 Sep 2017", "04 Sep 2017", 200, 100.25);
		records.add(rec);
		Record rec2 = rc.addNewRecord("foo", "B", 0.50f, "AED", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec2);
		Record rec3 = rc.addNewRecord("Boo", "B", 0.50f, "SGP", "01 Sep 2017", "06 Sep 2017", 200, 100.25);
		records.add(rec3);
		Record rec4 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec4);
		Record rec5 = rc.addNewRecord("BPP", "S", 0.50f, "AED", "01 Sep 2017", "05 Sep 2017", 200, 100.25);
		records.add(rec5);
		Record rec6 = rc.addNewRecord("foo", "S", 0.50f, "SGP", "01 Sep 2017", "03 Sep 2017", 200, 100.25);
		records.add(rec6);

		List<Record> outGoingList = rc.getOutgoingRecords(records);
		List<Record> incomingList = rc.getIncomingRecords(records);

		assertEquals(3, incomingList.size());
		assertEquals(3, outGoingList.size());

		String outgoingReport = rc.printOutgoingRanking(outGoingList);
		assertThat(outgoingReport, containsString("Rank : 1 foo = 20050.0"));
		assertThat(outgoingReport, containsString("Rank : 2 Boo = 10025.0"));
	}

}
