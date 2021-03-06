package com.tda.model.patient;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Null;

import com.tda.model.applicationuser.ApplicationUser;
import com.tda.model.dentist.DentistForm;
import com.tda.model.nurse.NurseForm;
import com.tda.model.pediatrician.PediatricianForm;
import com.tda.model.socialworker.SocialWorkerForm;

@Entity
public class PatientInTrain implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Patient patient;
	private SocialWorkerForm socialworkerform;
	private PediatricianForm padiatricianform;
	private NurseForm nurseform;
	private DentistForm dentistform;
	private ApplicationUser user;

	@Null
	@OneToOne
	public DentistForm getDentistform() {
		return dentistform;
	}

	public void setDentistform(DentistForm dentistform) {
		this.dentistform = dentistform;
	}

	@Null
	@OneToOne(fetch = FetchType.EAGER)
	public ApplicationUser getUser() {
		return user;
	}

	public void setUser(ApplicationUser user) {
		this.user = user;
	}

	@Null
	@OneToOne
	public SocialWorkerForm getSocialworkerform() {
		return socialworkerform;
	}

	public void setSocialworkerform(SocialWorkerForm socialworkerform) {
		this.socialworkerform = socialworkerform;
	}

	@Null
	@OneToOne
	public PediatricianForm getPadiatricianform() {
		return padiatricianform;
	}

	public void setPadiatricianform(PediatricianForm padiatricianform) {
		this.padiatricianform = padiatricianform;
	}

	@Null
	@OneToOne
	public NurseForm getNurseform() {
		return nurseform;
	}

	public void setNurseform(NurseForm nurseform) {
		this.nurseform = nurseform;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@OneToOne
	public Patient getPatient() {
		return patient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientInTrain other = (PatientInTrain) obj;
		if (patient.getId() == other.getPatient().getId()) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return id.toString();
	}

}
