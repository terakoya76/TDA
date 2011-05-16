package com.tda.model.itinerary;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.Size;
import org.springframework.format.annotation.DateTimeFormat;

import com.tda.model.applicationuser.ApplicationUser;

@Entity
@Table(name = "ITINERARY")
public class Itinerary {
	private Long id;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date beginningDate;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;

	@Size(max = 200)
	private String description;

	private List<Place> places;

	private List<ApplicationUser> personnel;

	@Size(max = 200)
	private String additionalInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITINERARY_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	public List<ApplicationUser> getPersonnel() {
		return personnel;
	}

	public void setPersonnel(List<ApplicationUser> personnel) {
		this.personnel = personnel;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String toString() {
		return "Itinerary [id=" + id + ", beginningDate=" + beginningDate
				+ ", endDate=" + endDate + ", description=" + description
				+ ", places=" + places + ", personnel=" + personnel
				+ ", additionalInfo=" + additionalInfo + "]";
	}

}
