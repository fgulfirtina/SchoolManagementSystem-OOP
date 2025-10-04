import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SchoolManagementSystemTest {

    @Test
    public void testIsValidTCKN_ValidTCKN() {
        SchoolManagementSystem sms = new SchoolManagementSystem();
        assertTrue(sms.isValidTCKN("37326692176"));
    }

    @Test
    public void testIsValidTCKN_InvalidTCKN() {
        SchoolManagementSystem sms = new SchoolManagementSystem();
        assertFalse(sms.isValidTCKN("12345678900")); // Last digit changed to 0
    }


    @Test
    public void testIsValidDate_ValidDate() {
        SchoolManagementSystem sms = new SchoolManagementSystem();
        assertTrue(sms.isValidDate("31/12/2023"));
    }

    @Test
    public void testIsValidDate_InvalidDate() {
        SchoolManagementSystem sms = new SchoolManagementSystem();
        assertFalse(sms.isValidDate("32/12/2023")); // Day out of range
    }

    @Test
    public void testFixString() {
        assertEquals("John", SchoolManagementSystem.FixString("john"));
    }

    @Test
    public void testFixString_EmptyString() {
        assertEquals("", SchoolManagementSystem.FixString(""));
    }

    @Test
    public void testFixString_SingleLetter() {
        assertEquals("A", SchoolManagementSystem.FixString("a"));
    }

    @Test
    public void testFixString_MultipleWords() {
        assertEquals("John Doe Smith", SchoolManagementSystem.FixString("john doe smith"));
    }

    @Test
    public void testFixString_Apostrophe() {
        assertEquals("O'Connell", SchoolManagementSystem.FixString("o'connell"));
    }

    @Test
    public void validEmailCheck() {
        assertTrue(SchoolManagementSystem.isValidEmail("emreakkaya@ogr.deu.edu.tr"));
    }

    @Test
    public void invalidEmailCheck() {
        assertFalse(SchoolManagementSystem.isValidEmail("emreakkayaogr.deu.edu.tr"));
        assertFalse(SchoolManagementSystem.isValidEmail("emreakkaya@"));
        assertFalse(SchoolManagementSystem.isValidEmail("emreakkaya@ogr.deu.education")); // TLD too long
        assertFalse(SchoolManagementSystem.isValidEmail("emreakkaya@ogr!deu.edu.tr"));

    }

}





