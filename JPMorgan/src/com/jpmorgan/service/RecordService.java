package com.jpmorgan.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jpmorgan.bean.Record;
import com.jpmorgan.util.MapUtil;

public class RecordService {

	// this is List for the Currencies have weekend on Friday and Saturday -
	// this can be placed in config file or DB
	final ArrayList<String> currList = new ArrayList<String>(Arrays.asList(new String[] { "AED", "SAR" }));

	/**
	 * @param entity
	 * @param type
	 * @param agreedFX
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 * @param unit
	 * @param pricePerUnit
	 * @return the added record if valid , null if the inserted record not in
	 *         valid format
	 */
	public Record addRecord(String entity, String type, float agreedFX, String currency, String instructionDate,
			String settlementDate, int unit, double pricePerUnit) {

		if (!validateRecord(entity, type, agreedFX, currency, instructionDate, settlementDate, unit, pricePerUnit)) {
			return null;

		}

		Record rec = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		try {
			Date instDate = formatter.parse(instructionDate);
			Date setDate = formatter.parse(settlementDate);

			Calendar c = Calendar.getInstance();
			c.setTime(setDate);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

			if (currList.contains(currency)) {
				if (dayOfWeek == Calendar.FRIDAY) { // If it's Friday so skip to
													// Sunday
					c.add(Calendar.DATE, 2);
				} else if (dayOfWeek == Calendar.SATURDAY) { // If it's Saturday
																// skip to
																// sunday
					c.add(Calendar.DATE, 1);
				}
			} else {
				if (dayOfWeek == Calendar.SATURDAY) { // If it's Friday so skip
														// to Monday
					c.add(Calendar.DATE, 2);
				} else if (dayOfWeek == Calendar.SUNDAY) { // If it's Saturday
															// skip to Monday
					c.add(Calendar.DATE, 1);
				}
			}

			// there is no requirement to round the double value
			// so i kept the original value without rounding
			//
			double priceInUS = pricePerUnit * unit * agreedFX;

			rec = new Record(entity, type, agreedFX, currency, instDate, setDate, unit, pricePerUnit, priceInUS,
					c.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return rec;
	}

	/**
	 * @param entity
	 * @param type
	 * @param agreedFX
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 * @param unit
	 * @param pricePerUnit
	 * @return true of the record is valid
	 */
	private boolean validateRecord(String entity, String type, float agreedFX, String currency, String instructionDate,
			String settlementDate, int unit, double pricePerUnit) {
		if (entity == null || entity.equalsIgnoreCase("")) {
			return false;
		}
		if (type == null || type.equalsIgnoreCase("") || !type.equals("B") || !type.equals("S")) {
			return false;
		}
		if (agreedFX <= 0) {
			return false;
		}
		if (currency == null || currency.equalsIgnoreCase("")) {
			return false;
		}
		if (instructionDate == null || instructionDate.equalsIgnoreCase("") || !isValidDate(instructionDate)) {
			return false;
		}
		if (settlementDate == null || settlementDate.equalsIgnoreCase("") || !isValidDate(settlementDate)) {
			return false;
		}
		if(!isValidsettlemenAndinstruction(instructionDate,settlementDate)){
			return false;
		}
		if (unit <= 0) {
			return false;
		}
		if (pricePerUnit <= 0) {
			return false;
		}
		return true;
	}

	private boolean isValidsettlemenAndinstruction(String instructionDate, String settlementDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		try {
			Date instructionDateObj = formatter.parse(instructionDate);
			Date settlementDateObj = formatter.parse(settlementDate);
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c.setTime(instructionDateObj);
			c2.setTime(settlementDateObj);

			// Assume that instructionDate can't be Greater than settlementDate 
			if (c.getTimeInMillis() > c2.getTimeInMillis()) {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param date
	 * @return false if Date String is not valid format
	 */
	private boolean isValidDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		try {
			Date dateObj = formatter.parse(date);
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c2.setTime(dateObj);

			// Assume that instructionDate and settlementDate can't be in past -
			// this is based on business requirement
			if (c.getTimeInMillis() > c2.getTimeInMillis()) {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param records
	 * @return sum all Incoming for everydate
	 */
	public String generateReportForEveryDay(List<Record> records) {
		HashMap<Date, Double> hm = new HashMap<Date, Double>();
		for (Record record : records) {
			if (hm.containsKey(record.getAccSetDate())) {
				Double value = hm.get(record.getAccSetDate());
				value += record.getPreiceForUSA();
				hm.put(record.getAccSetDate(), value);
			} else {
				hm.put(record.getAccSetDate(), record.getPreiceForUSA());
			}
		}
		return printValues(hm);
	}

	/**
	 * @param map
	 * @return String value for the Sum if amount per every day
	 */
	private String printValues(HashMap<Date, Double> map) {
		StringBuilder sb = new StringBuilder();
		if (map.size() >= 1) {
			Iterator<Entry<Date, Double>> it = map.entrySet().iterator();
			DateFormat df = new SimpleDateFormat("dd MMM yyyy");

			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				sb.append(df.format(pair.getKey()) + " = " + pair.getValue() + "\n");
				it.remove(); // avoids a ConcurrentModificationException
			}
		} else {
			sb.append("No Records Found \n");
		}
		return sb.toString();
	}

	/**
	 * @param records
	 * @return List of the Incoming records
	 */
	public List<Record> getIncomingRecords(List<Record> records) {
		List<Record> inComingList = new ArrayList<Record>();
		for (Record rec : records) {
			if (rec.getType().equals("S")) {
				inComingList.add(rec);
			}
		}
		return inComingList;
	}

	/**
	 * @param records
	 * @return List of the outgoing records
	 */
	public List<Record> getOutgoingRecords(List<Record> records) {
		List<Record> outGoingList = new ArrayList<Record>();
		for (Record rec : records) {
			if (rec.getType().equals("B")) {
				outGoingList.add(rec);
			}
		}
		return outGoingList;
	}

	/**
	 * @param records
	 * @return String of the ranked records from the highest to the lowest
	 */
	public String printRanking(List<Record> records) {

		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (Record record : records) {
			if (hm.containsKey(record.getEntity())) {
				Double value = hm.get(record.getEntity());
				value += record.getPreiceForUSA();
				hm.put(record.getEntity(), value);
			} else {
				hm.put(record.getEntity(), record.getPreiceForUSA());
			}
		}

		Map<String, Double> map = MapUtil.sortByValue(hm);
		return printSortedValues(map);
	}

	/**
	 * @param map
	 * @return String of the ranked records from the highest to the lowest
	 */
	private String printSortedValues(Map<String, Double> map) {
		StringBuilder sb = new StringBuilder();
		if (map.size() >= 1) {
			Iterator<Entry<String, Double>> it = map.entrySet().iterator();

			int i = 1;
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				sb.append("Rank : " + i++ + " " + pair.getKey() + " = " + pair.getValue() + "\n");
				it.remove(); // avoids a ConcurrentModificationException
			}

		} else {
			sb.append("No Records Found \n");
		}

		return sb.toString();
	}

}
