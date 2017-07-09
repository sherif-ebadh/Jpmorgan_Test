package com.jpmorgan;

import java.util.ArrayList;
import java.util.List;

import com.jpmorgan.bean.Record;
import com.jpmorgan.controller.RecordController;

public class Application {

	public static void main(String[] args) {
		
		List<Record> records =  new ArrayList<Record>();
		RecordController rc = new RecordController();
		
//		this is a repeated task for adding the data - 
//		it can be used from command o just as sample from the main method as i do

		
		// Insert new Data
		
		Record rec = null;
//		
//		rec = rc.addNewRecord("foo","B",0.50f,"SGP","01 Jan 2017","08 Jul 2016",200, 100.25);
//		if(rec!=null){
//			records.add(rec);
//		}
//		rec = rc.addNewRecord("foo","B",0.50f,"SGP","08 Jul 2017","09 Jul 2017",200, 100.25);
//		if(rec!=null){
//			records.add(rec);
//		}
//		rec = rc.addNewRecord("TEST","B",0.50f,"EGP","01 Jan 2018","05 Jan 2018",200, 100.25);
//		if(rec!=null){
//		records.add(rec);
//	}
//		
//		rec = rc.addNewRecord("bar","S",0.22f,"AED","05 Jan 2018","07 Jan 2018",450, 150.5);
//		if(rec!=null){
//		records.add(rec);
//	}
//		
//		rec = rc.addNewRecord("foo","S",0.50f,"SGP","01 Jan 2018","02 Jan 2018",200, 100.25);
//		if(rec!=null){
//		records.add(rec);
//	}
//		
//		rec = rc.addNewRecord("bar","B",0.22f,"AED","05 Jan 2018","07 Jan 2018",150, 150.5);
//		if(rec!=null){
//		records.add(rec);
//	}
//				
//		rec = rc.addNewRecord("bar","B",0.22f,"AED","05 Jan 2018","12 Jan 2018",100, 150.5);
//		if(rec!=null){
//		records.add(rec);
//	}
//				
//		
//		rec = rc.addNewRecord("bar","B",0.22f,"AED","05 Jan 2018","12 Jan 2018",100, 150.5);
//		if(rec!=null){
//			records.add(rec);
//		}
//		
//		rec = rc.addNewRecord("bar","B",0.22f,"AED","05 Jan 2018","07 Jul 2018",100, 150.5);
//		if(rec!=null){
//			records.add(rec);
//		}
//		rec = rc.addNewRecord("bar","B",0.22f,"AED","05 Jan 2018","08 Jul 2018",100, 150.5);
//		if(rec!=null){
//			records.add(rec);
//		}
		
		rec = rc.addNewRecord("bar","B",0.22f,"AED","05 Jan 2018","08 Jul 2017",100, 150.5);
		if(rec!=null){
			records.add(rec);
		}

		
		
		// divide the list for INcoming and Outgoing
		List<Record> inComingRecords = rc.getIncomingRecords(records);
		List<Record> outGoingRecords = rc.getOutgoingRecords(records);
		
			
		// Report Amount in USD settled incoming everyday 
		System.out.println("############################################");
		System.out.println("Report Amount in USD settled incoming everyday ");
		System.out.println(rc.generateIncoming(inComingRecords));
		
		
		
		// Amount in USD settled outgoing everyday
		System.out.println("############################################");
		System.out.println("Report Amount in USD settled outgoing everyday ");
		System.out.println(rc.generateIncoming(outGoingRecords));
		
		// Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest
		// amount for a buy instruction, then foo is rank 1 for outgoing
		
		System.out.println("############################################");
		System.out.println("Ranking of entities based on incoming amount ");
		System.out.println(rc.printIncomingRanking(inComingRecords));
		
		System.out.println("############################################");
		System.out.println("Ranking of entities based on outgoing amount ");
		System.out.println(rc.printOutgoingRanking(outGoingRecords));
		
	}

	

}
