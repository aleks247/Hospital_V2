package com.project.Hospital_V2;

import com.project.Hospital_V2.controllers.PatientController;
import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.entities.Doctor;
import com.project.Hospital_V2.entities.Patient;
import com.project.Hospital_V2.enums.KindOfReview;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.repositories.DoctorRepository;
import com.project.Hospital_V2.repositories.PatientRepository;
import com.project.Hospital_V2.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private PatientService patientService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientController patientController;

    @Mock
    private Model model;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testInsertPatients() {
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(3);
        patientService.insertPatients();
        verify(jdbcTemplate).update(eq("INSERT IGNORE  INTO patient (id, username, age, first_name, last_name) " +
                "SELECT user_id, username, age, first_name, last_name FROM users WHERE role = ?"), eq(new Object[]{"PATIENT"}));
    }

    @Test
    void appointmentCreation_whenBindingResultHasErrors_thenRedirectToCreate() {
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView result = patientService.appointmentCreation(new Appointment(), bindingResult);

        assertEquals("redirect:/patients/create", result.getViewName());
    }

    @Test
    void appointmentCreation_whenValidAppointmentAndDoctorHasNoAppointments_thenSaveAppointmentAndRedirectToMenu() {
        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();
        doctor.setId(1);
        appointment.setDoctor(doctor);
        appointment.setDateTime(LocalDateTime.now().plusDays(1));
        when(bindingResult.hasErrors()).thenReturn(false);
        when(securityContext.getAuthentication()).thenReturn(mock(Authentication.class));
        when(patientRepository.findByUsername(anyString())).thenReturn(new Patient());
        when(appointmentRepository.findByDoctorId(anyInt())).thenReturn(new ArrayList<>());

        ModelAndView result = patientService.appointmentCreation(appointment, bindingResult);

        verify(appointmentRepository).save(appointment);
        assertEquals("redirect:/patients/menu", result.getViewName());
    }

    @Test
    void appointmentCreation_whenValidAppointmentAndDoctorHasAppointments_thenRedirectToCreate() {
        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();
        doctor.setId(1);
        appointment.setDoctor(doctor);
        appointment.setDateTime(LocalDateTime.now().plusDays(1));
        when(bindingResult.hasErrors()).thenReturn(false);
        when(securityContext.getAuthentication()).thenReturn(mock(Authentication.class));
        when(patientRepository.findByUsername(anyString())).thenReturn(new Patient());
        List<Appointment> doctorAppointments = new ArrayList<>();
        Appointment existingAppointment = new Appointment();
        existingAppointment.setDateTime(LocalDateTime.now().plusDays(1).plusMinutes(15));
        doctorAppointments.add(existingAppointment);
        when(appointmentRepository.findByDoctorId(anyInt())).thenReturn(doctorAppointments);

        ModelAndView result = patientService.appointmentCreation(appointment, bindingResult);

        verify(appointmentRepository, never()).save(any());
        assertEquals("redirect:/patients/create", result.getViewName());
    }

    @BeforeEach
    public void setupCreateApp() {
        doctorRepository = mock(DoctorRepository.class);
        patientController = new PatientController();
        model = mock(Model.class);
    }

    @Test
    public void testCreateAppointment() {
        Iterable<Doctor> doctors = doctorRepository.findAll();
        when(doctorRepository.findAll()).thenReturn(doctors);

        String viewName = patientService.createAppointment(model);

        assertEquals("/patients/create", viewName);
        verify(model).addAttribute(eq("appointment"), any(Appointment.class));
        verify(model).addAttribute(eq("doctors"), eq(doctors));
        verify(model).addAttribute(eq("KindOfReview"), eq(KindOfReview.values()));
    }

    @BeforeEach
    public void setupEdit() {
        appointmentRepository = mock(AppointmentRepository.class);
        patientService = new PatientService(jdbcTemplate);
        model = mock(Model.class);
    }

    @Test
    public void testEditAppointment_existingAppointment() {
        int appointmentId = 1;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        Optional<Appointment> optionalAppointment = Optional.of(appointment);
        when(appointmentRepository.findById(appointmentId)).thenReturn(optionalAppointment);

        String viewName = patientService.editAppointment(appointmentId, model);

        assertEquals("/patients/edit", viewName);
        verify(model).addAttribute(eq("appointment"), eq(appointment));
        verify(model, never()).addAttribute(eq("appointment"), eq("Error!"));
        verify(model, never()).addAttribute(eq("errorMsg"), anyString());
    }

    @Test
    public void testEditAppointment_nonExistingAppointment() {
        int appointmentId = 1;
        Optional<Appointment> optionalAppointment = Optional.empty();
        when(appointmentRepository.findById(appointmentId)).thenReturn(optionalAppointment);

        String viewName = patientService.editAppointment(appointmentId, model);

        assertEquals("/patients/edit", viewName);
        verify(model).addAttribute(eq("appointment"), eq("Error!"));
        verify(model).addAttribute(eq("errorMsg"), eq(" Not existing appointment with id = " + appointmentId));
    }
}
