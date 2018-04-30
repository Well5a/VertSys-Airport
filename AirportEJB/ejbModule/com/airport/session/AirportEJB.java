package com.airport.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.airport.model.Airplane;
import com.airport.model.ParkingPosition;
import com.airport.model.Runway;


@Stateless
public class AirportEJB {

	@PersistenceContext(unitName="airport")
	private EntityManager entityManager;
	
	// -------------------------- Airplanes  ------------------------------------
	public List<Airplane> getAirplanes() {
		Query query = entityManager.createNamedQuery("airplane.findAll");
		
		@SuppressWarnings("unchecked")
		List<Airplane> airplanes = query.getResultList();
		return airplanes;
	}

	public void store(Airplane airplane) {
		entityManager.persist(airplane);
	}
	
	// -------------------------- Runways ------------------------------------
	@SuppressWarnings("unchecked")
	public List<Runway> getRunways() {
		Query query = entityManager.createNamedQuery("runway.findAll");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Runway> getFreeRunways() {
		Query query = entityManager.createNamedQuery("runway.findFree");
		return query.getResultList();	
	}
	
	
	public void lockRunway(Runway runway) {
		runway.setIsLocked(true);
		entityManager.persist(runway);
	}
	
	public void unlockRunway(Runway runway) {
		runway.setIsLocked(false);
		entityManager.persist(runway);
	}
	
	
	// -------------------------- Parking Positions ------------------------------------
	@SuppressWarnings("unchecked")
	public List<ParkingPosition> getParkingPositions() {
		Query query = entityManager.createNamedQuery("parkingposition.findAll");
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ParkingPosition> getFreeParkingPositions() {
		Query query = entityManager.createNamedQuery("parkingposition.findFree");
		return query.getResultList();	
	}
	
	public void lockParkingPosition(ParkingPosition parkingPosition) {
		parkingPosition.setIsLocked(true);
		entityManager.persist(parkingPosition);
	}
	
	public void unlockParkingPosition(ParkingPosition parkingPosition) {
		parkingPosition.setIsLocked(false);
		entityManager.persist(parkingPosition);
	}
	
	
}
