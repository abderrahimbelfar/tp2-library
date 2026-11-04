package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Copy> copies = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void addCopy(String isbn, String inventoryNumber) {
        Book book = books.get(isbn);

        if (book == null) {
            throw new IllegalArgumentException("Unknown ISBN");
        }

        copies.put(inventoryNumber, new Copy(inventoryNumber, book));
    }

    public void registerMember(Member member) {
        members.put(member.getNumber(), member);
    }

    public Copy findCopy(String inventoryNumber) {
        return copies.get(inventoryNumber);
    }

    public Loan lend(String memberNumber, String inventoryNumber,
                     LocalDate today) {

        Member member = members.get(memberNumber);
        Copy copy = copies.get(inventoryNumber);

        if (member == null || copy == null) {
            throw new IllegalArgumentException("Unknown member or copy");
        }

        if (!copy.isAvailable()) {
            throw new IllegalStateException("Copy is not available");
        }

        long openLoans = loans.stream()
                .filter(loan -> loan.getMember().getNumber()
                        .equals(memberNumber))
                .filter(Loan::isOpen)
                .count();

        if (openLoans >= 5) {
            throw new IllegalStateException(
                    "Member already has 5 open loans");
        }

        copy.setAvailable(false);

        Loan loan = new Loan(member, copy, today);
        loans.add(loan);

        return loan;
    }

    public void returnCopy(String inventoryNumber, LocalDate today) {

        Copy copy = copies.get(inventoryNumber);

        if (copy == null) {
            throw new IllegalArgumentException("Unknown copy");
        }

        Loan openLoan = loans.stream()
                .filter(loan -> loan.getCopy() == copy)
                .filter(Loan::isOpen)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Copy is not on loan"));

        openLoan.setReturnDate(today);
        copy.setAvailable(true);
    }
    public List<Book> searchByTitle(String keyword) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }
}
