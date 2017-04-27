import org.junit.Test;

/**
 * Testklasse für Mediaservice.
 *
 * @author Juliane Seidl
 * @author Carolin Direnberger
 */

public class ServiceTest {

    // -----------------------------------------
    // ----------------- Books -----------------
    // -----------------------------------------

    /**
     * Test zum Hinzufügen eines validen Buchs.
     */
    @Test
    public void addingValidBook() {

    }

    /**
     * Test, ob ein invalides Buch (leeres Feld) erkannt und NICHT hinzugefügt wird.
     */
    @Test
    public void noEmptyFieldsInBookAccepted() {
    }

    /**
     * Test, ob das richtige Buch geladen wird, wenn mit ISBN gesucht wird.
     */
    @Test
    public void getBookByExistingISBN() {
    }

    /**
     * Test, ob die richtige Meldung kommt wenn ein Buch mit der gesuchten ISBN nicht in der Liste ist.
     */
    @Test
    public void getBookByUnexistingISBN() {
    }

    /**
     * Test, ob alle Bücher in gültigem JSON-Format geladen werden.
     */
    @Test
    public void getBooks() {
    }

    /**
     * Test, ob die richtige Meldung kommt wenn die Bücherliste leer ist.
     */
    @Test
    public void getBooksEmptyList() {
    }


    // -----------------------------------------
    // ----------------- Discs -----------------
    // -----------------------------------------

    /**
     * Test zum Hinzufügen einer validen Disc.
     */
    @Test
    public void addingValidDisc() {

    }

    /**
     * Test, ob eine invalide Disc (leeres Feld) erkannt und NICHT hinzugefügt wird.
     */
    @Test
    public void noEmptyFieldsInDiscAccepted() {
    }

    /**
     * Test, ob die richtige Disc geladen wird, wenn mit Barcode gesucht wird.
     */
    @Test
    public void getDiscByExistingBarcode() {
    }

    /**
     * Test, ob die richtige Meldung kommt wenn eine Disc mit der gesuchten Barcode nicht in der Liste ist.
     */
    @Test
    public void getDiscByUnexistingBarcode() {
    }

    /**
     * Test, ob alle Discs in gültigem JSON-Format geladen werden.
     */
    @Test
    public void getDiscs() {
    }

    /**
     * Test, ob die richtige Meldung kommt wenn die Discliste leer ist.
     */
    @Test
    public void getDiscsEmptyList() {
    }

}