package com.visioture.model.dto;

import java.util.Date;

public class Actor {
	
	private int id;
	private String firstName;
	private String lastName;
	private Date lastUpdate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	@Override
	public String toString() {
		return "Actor id: " + id + "\nfirstName = " + firstName + "\nlastName = " + lastName + "\nlastUpdate = " + lastUpdate + "\n\n";
	}
	
	
	
}
