package com.jpmorgan.controller;

import java.util.List;

import com.jpmorgan.bean.Record;
import com.jpmorgan.service.RecordService;

public class RecordController {

	RecordService recordService = new RecordService();
	
	public Record addNewRecord(String entity, String type, float agreedFX,String currency, String instructionDate, String settlementDate, 
			int unit, double pricePerUnit) {
		return recordService.addRecord(entity,type,agreedFX,currency,instructionDate,settlementDate,unit,pricePerUnit);
	}

	
	
	public List<Record> getIncomingRecords(List<Record> records) {
		return	recordService.getIncomingRecords(records);
	}
	public List<Record> getOutgoingRecords(List<Record> records) {
		return	recordService.getOutgoingRecords(records);
	}
	

	public String generateIncoming(List<Record> records) {
		return recordService.generateReportForEveryDay(records);
		
	}
	
	public String generateOutgoing(List<Record> records) {
		return recordService.generateReportForEveryDay(records);
		
	}
	
	
	public String printIncomingRanking(List<Record> inComingRecords) {
		return recordService.printRanking(inComingRecords);
		
	}
	public String printOutgoingRanking(List<Record> outGoingRecords) {
		return recordService.printRanking(outGoingRecords);
		
	}
	
}
