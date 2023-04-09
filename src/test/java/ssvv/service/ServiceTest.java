package ssvv.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ssvv.domain.Student;
import ssvv.domain.Tema;
import ssvv.repository.NotaXMLRepo;
import ssvv.repository.StudentXMLRepo;
import ssvv.repository.TemaXMLRepo;
import ssvv.validation.NotaValidator;
import ssvv.validation.StudentValidator;
import ssvv.validation.TemaValidator;
import ssvv.validation.ValidationException;

// Lab2
public class ServiceTest {

    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();

    StudentXMLRepo studentRepo = new StudentXMLRepo("fisiere/Studenti.xml");
    TemaXMLRepo temaRepo = new TemaXMLRepo("fisiere/Teme.xml");

    NotaValidator notaValidator = new NotaValidator(studentRepo, temaRepo);
    NotaXMLRepo noteRepo = new NotaXMLRepo("fisiere/Note.xml");

    Service service = new Service(studentRepo, studentValidator, temaRepo, temaValidator, noteRepo, notaValidator);

    @Test
    public void testAddStudentIdUnique() {
        // Add successfully a student -> null will be returned - unique id
        Assertions.assertNull(service.addStudent(new Student("123", "Test", 205, "test@email.com")));

        // Add already existing student -> student will be returned - duplicate id
        Assertions.assertEquals("123", service.addStudent(new Student("123", "Test", 205, "test@email.com")).getID());
        Assertions.assertNotNull(service.deleteStudent("123").getID());
    }

    @Test
    public void testAddStudentIdNull() {
        // Add a student with a null id - validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student(null, "Test", 205, "test@email.com"));
        });
    }

    @Test
    public void testAddStudentIdEmpty() {
        // Add a student with an empty id - validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("", "Test", 205, "test@email.com"));
        });
    }

    @Test
    public void testAddStudentNameNull() {
        // Add a student with an empty name - validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", null, 205, "test@email.com"));
        });
    }

    @Test
    public void testAddStudentNameEmpty() {
        // Add a student with an empty name - validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", "", 205, "test@email.com"));
        });
    }

    @Test
    public void testAddStudentEmailNull() {
        // Add a student with a null email -> validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", "Nume", 205, null));
        });
    }

    @Test
    public void testAddStudentEmailEmpty() {
        // Add a student with an empty email -> validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", "Nume", 205, ""));
        });
    }

    @Test
    public void testAddStudentEmailWrongFormat() {
        // Add a student with an email that has a wrong format -> validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", "Nume", 205, "email/t@test.com"));
        });
    }

    @Test
    public void testAddStudentGroupNegativeNo() {
        // Add a student with a negative number for group -> validation exception is thrown
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", "Nume", -10, "email@test.com"));
        });
    }

    // Lab 3

    @Test
    public void  testAddAssignmentIdUnique() {
        // Add successfully an assignment -> null will be returned - unique id
        Assertions.assertNull(service.addTema(new Tema("A5", "Description", 6, 4)));

        // Add already existing assignment -> assignment will be returned - duplicate id
        Assertions.assertEquals("A5", service.addTema(new Tema("A5", "Description", 6, 4)).getID());
        Assertions.assertNotNull(service.deleteTema("A5").getID());
    }

    @Test
    public void testAddAssignmentIdNull() {
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addTema(new Tema(null, "Descrption1", 8, 6));
        });
    }

    @Test
    public void testAddAssignmentDescriptionEmpty() {
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addTema(new Tema("A1", "", 8, 6));
        });
    }

    @Test
    public void testAddAssignmentDeadline15() {
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addTema(new Tema("A1", "Descrption1", 15, 5));
        });
    }

    @Test
    public void testAddAssignmentReceivingWeek0() {
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addTema(new Tema("A1", "Descrption1", 7, 0));
        });
    }
}