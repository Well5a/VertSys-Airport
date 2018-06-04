package com.airport.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.airport.model.*;
import com.airport.session.AirportEJB;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	private Runway runway;
	
	private ParkingPosition parkingPosition;
	
	private int i;
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
		runway = new Runway();
	}
	
	@PostConstruct
	private void init() {
		airplane = new Airplane();
		
		storeRunway(new Runway(1, false, "north"));
		storeRunway(new Runway(2, false, "west"));
		storeRunway(new Runway(3, false, "south"));
		storeRunway(new Runway(4, false, "east"));
		
		storeParkingPosition(new ParkingPosition(10, false, "a"));
		storeParkingPosition(new ParkingPosition(20, false, "b"));
		storeParkingPosition(new ParkingPosition(30, false, "c"));
		storeParkingPosition(new ParkingPosition(40, false, "d"));
		storeParkingPosition(new ParkingPosition(50, false, "e"));
		storeParkingPosition(new ParkingPosition(60, false, "f"));
		storeParkingPosition(new ParkingPosition(70, false, "g"));
		storeParkingPosition(new ParkingPosition(80, false, "h"));
	}
	
	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
		// if runway != null
		
		System.out.println("Hallo, Welt!");
	}public void update() {
		System.out.println("test");
	}
	

	public List<Airplane> getAirplanes() {
		return airportEJB.getAirplanes();
	}
	
	public List<Runway> getRunways() {
		return airportEJB.getRunways();
	}
	
	public List<Runway> getFreeRunways() {
		return airportEJB.getFreeRunways();
	}
	
	public List<ParkingPosition> getParkingPositions() {
		return airportEJB.getParkingPositions();
	}
	
	public List<ParkingPosition> getFreeParkingPositions() {
		return airportEJB.getFreeParkingPositions();
	}
	
	public Airplane getAirplane() {
		return airplane;
	}
	
	public void store() {
		airportEJB.store(airplane);
		System.out.println("Store: " + airplane.getName());
		
		airplane = new Airplane();
	}
	
	public void storeRunway(Runway runway) {
		airportEJB.storeRunway(runway);
		System.out.println("Store: " + runway.getName());
	}
	
	public void storeParkingPosition(ParkingPosition parkingPosition) {
		airportEJB.storeParkingPosition(parkingPosition);
		System.out.println("Store: " + parkingPosition.getName());
	}
	

	public void update(Airplane airplane) {
		System.out.println("ID:" + airplane.getId() + " Name:" + airplane.getName() + " Runway:" + airplane.getRunway() + " ETA:" + airplane.getEstimatedArrivalTime());
		System.out.println(this.airplane.getEstimatedArrivalTime());
		
		airportEJB.store(airplane);
		
		airplane = new Airplane();
	}
	
	
}
