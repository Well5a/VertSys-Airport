package com.airport.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.airport.model.*;
import com.airport.model.Airplane.Status;
import com.airport.session.AirportEJB;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	private Airline airline;
	
	private Runway runway;

	private ParkingPosition parkingPosition;

	
	private long selectedRunway;
	
	private long selectedAirplane;
	
	private long selectedParkingAirplane;


	private long landingAirplane;
	
	private long parkingAirplane;
	
	private long selectedParkingPosition;
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
		runway = new Runway();
	}

	
	
//	System.out.println("ID:" + airplane.getId() + " Name:" + airplane.getName() + " Runway:" + airplane.getRunway() + " ETA:" + airplane.getEstimatedArrivalTime());
//	System.out.println(this.airplane.getEstimatedArrivalTime());
	@PostConstruct
	private void init() {
		airplane = new Airplane();
//		runwayName= "";
		
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
		
		storeAirline(new Airline(100, "Air Berlin"));
		storeAirline(new Airline(200, "Niki"));
		storeAirline(new Airline(300, "Monarch Airlines"));
		storeAirline(new Airline(400, "Alitalia"));
	}
	
	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}
	
	public Airline getAirline() {
		return airline;
	}
	
	public List<Airline> getAirlines() {
		return airportEJB.getAirlines();
	}
	
	public void setAirline(Airline airline) {
		this.airline = airline;
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
	

	public long getSelectedParkingAirplane() {
		return selectedParkingAirplane;
	}



	public void setSelectedParkingAirplane(long selectedParkingAirplane) {
		this.selectedParkingAirplane = selectedParkingAirplane;
	}
	
	public void store() {
		String name = airplane.getAirlineName();
		System.out.println(name);
		List<Airline> airlines = getAirlines();
		Iterator<Airline> airlineIter = airlines.iterator();
		Airline airline = new Airline();
		while(airlineIter.hasNext()) {
			airline = airlineIter.next();
			System.out.println(airline.getName());
			if(airline.getName().equals(name)) 
			{
				System.out.println("true");
				break;
			}
		}
		airplane.setAirline(airline);
				
		airportEJB.store(airplane);
		System.out.println("Store Airplane: " + airplane.getName());
		
		airplane = new Airplane();
	}
	
	public void storeRunway(Runway runway) {
		airportEJB.storeRunway(runway);
		System.out.println("Store Runway: " + runway.getName());
	}
	
	public void storeParkingPosition(ParkingPosition parkingPosition) {
		airportEJB.storeParkingPosition(parkingPosition);
		System.out.println("Store ParkingPosition: " + parkingPosition.getName());
	}
	
	public void storeAirline(Airline airline) {
		airportEJB.storeAirline(airline);
		System.out.println("Store Airline: " + airline.getName());
	}
	

	public void assignRunway() {
		Runway runway = runwayById(selectedRunway);
		runway.setAirplane(airplaneById(selectedAirplane));
		runway.setIsLocked(true);
		
		airportEJB.storeRunway(runway);
	}
	private Runway runwayById(long runwayId) {
		List<Runway> runways = getFreeRunways();
		Iterator<Runway> runwayIter = runways.iterator();
		Runway runway = new Runway();
		while(runwayIter.hasNext()) {
			runway = runwayIter.next();
			if(runway.getId() == runwayId) 
			{
				return runway;
			}
		}
		return null;
	}
	
	private ParkingPosition parkingPositionById(long pId) {
		List<ParkingPosition> parkingPositions = getFreeParkingPositions();
		Iterator<ParkingPosition> parkingPositionIter = parkingPositions.iterator();
		ParkingPosition parkingPosition = new ParkingPosition();
		while(parkingPositionIter.hasNext()) {
			parkingPosition = parkingPositionIter.next();
			if(parkingPosition.getId() == pId) 
			{
				return parkingPosition;
			}
		}
		return null;
	}
	
	private Airplane airplaneById(long planeId) {
		List<Airplane> airplanes = getAirplanes();
		Iterator<Airplane> airplaneIter = airplanes.iterator();
		Airplane airplane = new Airplane();
		while(airplaneIter.hasNext())
		{
			airplane = airplaneIter.next();
			if(airplane.getId() == planeId)
			{
				return airplane;
			}
		}
		return null;
	}
	
	public void land() {
		Airplane airplane = airplaneById(landingAirplane);
		airplane.setStatus(Status.LANDING);
		airportEJB.store(airplane);
	}
	
	public void park() {
		Airplane airplane = airplaneById(parkingAirplane);
		List<Runway> runways = airportEJB.getRunways();
		Runway runway = new Runway();
		Iterator<Runway> runwayIter = runways.iterator();
		
		while (runwayIter.hasNext()) {
			runway = runwayIter.next();
			if(runway.getAirplane() != null && 
					runway.getAirplane().getId() == parkingAirplane)
				break;
		}
		runway.setIsLocked(false);
		runway.setAirplane(null);
		airportEJB.storeRunway(runway);
		
		airplane.setStatus(Status.PARKING);
		airportEJB.store(airplane);
	}
	/*
	public void releaseParkingPosition() {
		ParkingPosition pPos = parkingPositonById(releasingParkingPosition);
		pPos.setIsLocked(false);
		airportEJB.storeParkingPosition(pPos);
	}*/
	
	public void assignParkingPosition() {
		
		ParkingPosition parkingPosition = parkingPositionById(selectedParkingPosition);
		
		parkingPosition.setIsLocked(true);
		parkingPosition.setAirplane(airplaneById(selectedParkingAirplane));
				
		airportEJB.storeParkingPosition(parkingPosition);
	}
	
	

	public long getSelectedAirplane() {
		return selectedAirplane;
	}

	public void setSelectedAirplane(long selectedAirplane) {
		this.selectedAirplane = selectedAirplane;
	}
	
	public long getSelectedRunway() {
		return selectedRunway;
	}

	public void setSelectedRunway(long selectedRunway) {
		this.selectedRunway = selectedRunway;
	}


	public long getSelectedParkingPosition() {
		return selectedParkingPosition;
	}
	public void setSelectedParkingPosition(long selectedParkingPosition) {
		this.selectedParkingPosition = selectedParkingPosition;
	}



	public long getLandingAirplane() {
		return landingAirplane;
	}



	public void setLandingAirplane(long landingAirplane) {
		this.landingAirplane = landingAirplane;
	}



	public long getParkingAirplane() {
		return parkingAirplane;
	}



	public void setParkingAirplane(long parkingAirplane) {
		this.parkingAirplane = parkingAirplane;
	}
}
