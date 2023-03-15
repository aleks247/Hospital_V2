package com.project.Hospital_V2;
import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.entities.Doctor;
import com.project.Hospital_V2.repositories.DoctorRepository;
import com.project.Hospital_V2.services.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    private HttpSession session;

    @Mock
    private Iterable<Appointment> appointmentList;


    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    DoctorService doctorService;

    @Mock
    private Model model;


    @Captor
    private ArgumentCaptor<String> sqlCaptor;

    @Captor
    private ArgumentCaptor<Object[]> paramsCaptor;

    @Test
    void testInsertDoctors() {


        String sql = "INSERT IGNORE INTO doctor (id, username, first_name, last_name) " +
                "SELECT user_id, username, first_name, last_name FROM users WHERE role = ?";
        Object[] params = {"DOCTOR"};
        int rowsAffected = 5;

        when(jdbcTemplate.update(sql, params)).thenReturn(rowsAffected);

        doctorService.insertDoctors();

        verify(jdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());
        assertEquals(sql, sqlCaptor.getValue());
    }
}