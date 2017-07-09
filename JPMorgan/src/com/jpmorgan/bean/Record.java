package com.jpmorgan.bean;

import java.util.Date;

public class Record {

	String entity;
	String type;
	float agreedFX;
	String currency;
	Date instructionDate;
	Date settlementDate;
	Date accSetDate;
	int units;
	double pricePerUint;
	double preiceForUSA;


	public Record(String entity, String type, float agreedFX, String currency, Date instructionDate,
			Date settlementDate, int units, double pricePerUint ,double preiceForUSA,Date accSetDate) {
		super();
		this.entity = entity;
		this.type = type;
		this.agreedFX = agreedFX;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUint = pricePerUint;
		this.preiceForUSA = preiceForUSA;
		this.accSetDate = accSetDate;
	}

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the agreedFX
	 */
	public float getAgreedFX() {
		return agreedFX;
	}

	/**
	 * @param agreedFX
	 *            the agreedFX to set
	 */
	public void setAgreedFX(float agreedFX) {
		this.agreedFX = agreedFX;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the instructionDate
	 */
	public Date getInstructionDate() {
		return instructionDate;
	}

	/**
	 * @param instructionDate
	 *            the instructionDate to set
	 */
	public void setInstructionDate(Date instructionDate) {
		this.instructionDate = instructionDate;
	}

	/**
	 * @return the settlementDate
	 */
	public Date getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate
	 *            the settlementDate to set
	 */
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * @return the accSetDate
	 */
	public Date getAccSetDate() {
		return accSetDate;
	}

	/**
	 * @param accSetDate
	 *            the accSetDate to set
	 */
	public void setAccSetDate(Date accSetDate) {
		this.accSetDate = accSetDate;
	}

	/**
	 * @return the units
	 */
	public int getUnits() {
		return units;
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(int units) {
		this.units = units;
	}

	/**
	 * @return the pricePerUint
	 */
	public double getPricePerUint() {
		return pricePerUint;
	}

	/**
	 * @param pricePerUint
	 *            the pricePerUint to set
	 */
	public void setPricePerUint(double pricePerUint) {
		this.pricePerUint = pricePerUint;
	}

	/**
	 * @return the preiceForUSA
	 */
	public double getPreiceForUSA() {
		return preiceForUSA;
	}

	/**
	 * @param preiceForUSA
	 *            the preiceForUSA to set
	 */
	public void setPreiceForUSA(double preiceForUSA) {
		this.preiceForUSA = preiceForUSA;
	}

}
