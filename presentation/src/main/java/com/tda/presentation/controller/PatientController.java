package com.tda.presentation.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tda.model.patient.Patient;
import com.tda.model.patient.Sex;
import com.tda.persistence.paginator.Paginator;
import com.tda.presentation.params.ParamContainer;
import com.tda.service.api.PatientService;

@Controller
@RequestMapping(value = "/patient")
@SessionAttributes("patient")
public class PatientController {
	private static final String FORM_DELETE_ERROR = "form.deleteError";
	private static final String FORM_NOT_FOUND = "form.notFound";
	private static final String FORM_MESSAGE = "message";
	private static final String FORM_ADD_SUCCESSFUL = "form.addSuccessful";
	private static final String REDIRECT_TO_LIST = "redirect:/patient/";
	private static final String CREATE_FORM = "patient/createForm";
	private static final String LIST = "patient/list";
	private static final String LIST_SEARCH = "patient/search";

	private PatientService patientService;
	private Paginator paginator;
	private ParamContainer params;

	// TODO should be localized?
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");

	public PatientController() {
		params = new ParamContainer();
	}

	@ModelAttribute("sex")
	public Sex[] populateCategories() {
		return Sex.values();
	}

	@Autowired
	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}

	@Autowired
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
		paginator.setOrderAscending(true);
		paginator.setOrderField("id");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getList(
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "orderField", required = false) String orderField,
			@RequestParam(value = "orderAscending", required = false) Boolean orderAscending) {
		ModelAndView modelAndView = new ModelAndView(LIST);

		modelAndView = processRequest(modelAndView, new Patient(), pageNumber,
				orderField, orderAscending);

		return modelAndView;
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ModelAndView getList(
			@ModelAttribute Patient aPatient,
			BindingResult result,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "orderField", required = false) String orderField,
			@RequestParam(value = "orderAscending", required = false) Boolean orderAscending) {

		ModelAndView modelAndView = new ModelAndView(LIST);

		// set first page paginator
		paginator.setPageIndex(1);

		if (aPatient.getFirstName() != null)
			params.setParam("firstName", aPatient.getFirstName());
		if (aPatient.getLastName() != null)
			params.setParam("lastName", aPatient.getLastName());
		if (aPatient.getDni() != null)
			params.setParam("dni", aPatient.getDni());
		// TODO: birdhday format?
		if (aPatient.getBirthdate() != null)
			params.setParam("birthday", aPatient.getBirthdate().toString());
		if (aPatient.getSex() != null)
			params.setParam("sex", aPatient.getSex().toString());

		modelAndView = processRequest(modelAndView, aPatient, pageNumber,
				orderField, orderAscending);

		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("patient", new Patient());

		return CREATE_FORM;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(Model model,
			@Valid @ModelAttribute Patient aPatient, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();

		// TODO if we're editing and not adding a new item the message
		// seems somewhat... misleading, CHANGE IT :D
		if (result.hasErrors()) {
			modelAndView.setViewName(CREATE_FORM);
		} else {
			modelAndView.setViewName(REDIRECT_TO_LIST);
			modelAndView.addObject(FORM_MESSAGE, FORM_ADD_SUCCESSFUL);
			patientService.save(aPatient);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getUpdateForm(@PathVariable Long id, Model model) {
		Patient patient = patientService.findById(id);
		model.addAttribute("patient", patient);

		return CREATE_FORM;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ModelAndView deleteItem(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView(REDIRECT_TO_LIST);
		Patient aPatient = patientService.findById(id);

		if (aPatient == null) {
			modelAndView.addObject(FORM_MESSAGE, FORM_NOT_FOUND);
		} else {

			try {
				patientService.delete(aPatient);
			} catch (Exception e) {
				modelAndView.addObject(FORM_MESSAGE, FORM_DELETE_ERROR);
			}
		}
		return modelAndView;
	}

	private ModelAndView processRequest(ModelAndView modelAndView,
			Patient aPatient, Integer pageNumber, String orderField,
			Boolean orderAscending) {
		List<Patient> patientList = null;

		// Pagination
		if (pageNumber != null) {
			paginator.setPageIndex(pageNumber);
		}

		// Order
		if (orderField == null || orderAscending == null) {
			orderField = "firstName";
			orderAscending = true;
		}

		paginator.setOrderAscending(orderAscending);
		paginator.setOrderField(orderField);

		patientList = patientService.findByExamplePaged(aPatient, paginator);

		modelAndView.addObject("patient", new Patient());
		modelAndView.addObject("patientList", patientList);
		modelAndView.addObject("paginator", paginator);
		modelAndView.addObject("params", params);
		modelAndView.addObject("orderField", orderField);
		modelAndView.addObject("orderAscending", orderAscending.toString());

		return modelAndView;
	}

	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(Date.class, new DateEditor());
	}

	private class DateEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {

			try {
				setValue(simpleDateFormat.parse(text));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public String getAsText() {
			// TODO why its entering here when getValue() == null?
			if (getValue() == null)
				return null;

			return simpleDateFormat.format((Date) getValue());
		}
	}

}
