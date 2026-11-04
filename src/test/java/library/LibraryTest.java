package library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private static final String ISBN = "978-0-00-000001-1";
    private static final LocalDate DAY = LocalDate.of(2026, 10, 1);

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
        library.addBook(new Book(ISBN, "Software Engineering", 2016));
        library.addCopy(ISBN, "C-001");
        library.registerMember(new Member("M-01", "First member"));
    }

    @Test
    void lendingMakesTheCopyUnavailable() {
        Loan loan = library.lend("M-01", "C-001", DAY);

        assertFalse(library.findCopy("C-001").isAvailable());
        assertEquals(DAY.plusDays(14), loan.getDueDate());
    }

    @Test
    void returningMakesTheCopyAvailable() {
        library.lend("M-01", "C-001", DAY);
        library.returnCopy("C-001", DAY.plusDays(2));

        assertTrue(library.findCopy("C-001").isAvailable());
    }

    @Test
    void cannotLendUnavailableCopy() {
        library.lend("M-01", "C-001", DAY);

        assertThrows(IllegalStateException.class,
                () -> library.lend("M-01", "C-001", DAY));
    }

    @Test
    void unknownMemberCausesException() {
        assertThrows(IllegalArgumentException.class,
                () -> library.lend("M-99", "C-001", DAY));
    }

    @Test
    void unknownCopyCausesException() {
        assertThrows(IllegalArgumentException.class,
                () -> library.lend("M-01", "C-999", DAY));
    }

    @Test
    void memberCannotHaveMoreThanFiveOpenLoans() {

        for (int i = 2; i <= 6; i++) {
            String number = String.format("C-%03d", i);
            library.addCopy(ISBN, number);
        }

        for (int i = 1; i <= 5; i++) {
            String number = String.format("C-%03d", i);
            library.lend("M-01", number, DAY);
        }

        assertThrows(IllegalStateException.class,
                () -> library.lend("M-01", "C-006", DAY));
    }
}