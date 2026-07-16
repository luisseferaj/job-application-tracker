package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import static com.example.demo.Status.INTERVIEW;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static com.example.demo.Status.APPLIED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JobApplicationServiceTest {
    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    @Test
    public void getAll_returnsList(){

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("luis");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setId(1);
        user.setUsername("luis");

        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(1);
        jobApplication.setCompany("Intesa");
        jobApplication.setPosition("Developer");
        jobApplication.setStatus(Status.APPLIED);
        jobApplication.setNotes(null);
        jobApplication.setDateApplied(LocalDate.of(2026, 7,16));
        jobApplication.setUser(user);

        when(userRepository.findByUsername("luis")).thenReturn(Optional.of(user));
        when(jobApplicationRepository.findByUser(user)).thenReturn(List.of(jobApplication));

        List<JobApplicationDTO> result = jobApplicationService.getAll(jobApplication.getStatus());

        assertEquals(1, result.size());
        assertEquals("Intesa", result.get(0).getCompany());
        assertEquals("Developer", result.get(0).getPosition());
        assertEquals(APPLIED, result.get(0).getStatus());
        assertEquals(null, result.get(0).getNotes());
        assertEquals(LocalDate.of(2026, 7, 16), result.get(0).getDateApplied());
    }

    @Test
    public void delete_deleteByid(){
        jobApplicationService.delete(1);
        verify(jobApplicationRepository, times(1)).deleteById(1);
    }

    @Test
    public void update_Byid(){
        JobApplication existing = new JobApplication();

        User user = new User();
        user.setId(1);
        user.setUsername("luis");

        existing.setId(1);
        existing.setCompany("OldComp");
        existing.setStatus(APPLIED);
        existing.setPosition("OldPos");
        existing.setNotes(null);
        existing.setDateApplied(LocalDate.of(2026, 1, 24));
        existing.setUser(user);

        JobApplication updated = new JobApplication();
        updated.setStatus(INTERVIEW);
        updated.setDateApplied(LocalDate.of(2026, 7, 24));

        when(jobApplicationRepository.findById(1)).thenReturn(Optional.of(existing));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(existing);

        JobApplication result=jobApplicationService.update(1, updated);

        assertEquals(INTERVIEW, result.getStatus());
        assertEquals(LocalDate.of(2026, 7, 24), result.getDateApplied());
    }
}
