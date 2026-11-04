package library;

import java.time.LocalDate;

public class Loan {

    private final Member member;
    private final Copy copy;
    private final LocalDate startDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Member member, Copy copy, LocalDate startDate) {
        this.member = member;
        this.copy = copy;
        this.startDate = startDate;
        this.dueDate = startDate.plusDays(14);
        this.returnDate = null;
    }

    public Member getMember() {
        return member;
    }

    public Copy getCopy() {
        return copy;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isOpen() {
        return returnDate == null;
    }

    public boolean isLate(LocalDate today) {
        return isOpen() && today.isAfter(dueDate);
    }
}