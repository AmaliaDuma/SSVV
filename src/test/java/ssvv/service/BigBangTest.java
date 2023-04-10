package ssvv.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ssvv.domain.Nota;
import ssvv.domain.Student;
import ssvv.domain.Tema;
import ssvv.repository.NotaXMLRepo;
import ssvv.repository.StudentXMLRepo;
import ssvv.repository.TemaXMLRepo;
import ssvv.validation.NotaValidator;
import ssvv.validation.StudentValidator;
import ssvv.validation.TemaValidator;
import ssvv.validation.ValidationException;

import java.time.LocalDate;

public class BigBangTest {
    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();

    StudentXMLRepo studentRepo = new StudentXMLRepo("fisiere/Studenti.xml");
    TemaXMLRepo temaRepo = new TemaXMLRepo("fisiere/Teme.xml");

    NotaValidator notaValidator = new NotaValidator(studentRepo, temaRepo);
    NotaXMLRepo noteRepo = new NotaXMLRepo("fisiere/Note.xml");

    Service service = new Service(studentRepo, studentValidator, temaRepo, temaValidator, noteRepo, notaValidator);

    @Test
    void saveStudent() {
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", null, 205, "test@email.com"));
        });
    }

    @Test
    void saveAssignment() {
        Assertions.assertNull(service.addTema(new Tema("A5", "Description", 6, 4)));

        Assertions.assertNotNull(service.deleteTema("A5").getID());
    }

    @Test
    void saveGrade() {
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addNota(new Nota("N1", "632", "2", 7, LocalDate.now()), "Good");
        });
    }

    @Test
    void saveAll() {
        Assertions.assertThrows(ValidationException.class, () -> {
            service.addStudent(new Student("123", null, 205, "test@email.com"));
        });

        Assertions.assertNull(service.addTema(new Tema("A5", "Description", 6, 4)));

        Assertions.assertNotNull(service.deleteTema("A5").getID());

        Assertions.assertThrows(ValidationException.class, () -> {
            service.addNota(new Nota("N1", "632", "2", 7, LocalDate.now()), "Good");
        });
    }
}
