UC-07 – Register a Return

Use Case Name

Register a Return

Scope

Library System

Level

User Goal

Primary Actor

Librarian

Secondary Actor

Mail server

Stakeholders and Interests

- Member: Wants to return a borrowed copy successfully.
- Librarian: Wants to register the returned copy correctly.
- Library: Wants loan records and book availability to remain up to date.
- Member who reserved the book: Wants to be notified when the reserved copy becomes available.
- Mail server: Sends notifications to members.

Preconditions

- The librarian is authenticated.
- The member has borrowed the copy.
- The loan is registered in the library system.

Success Guarantee

- The loan is closed in the system.
- The returned copy is registered.
- If the copy is reserved, it is set aside and the reserving member is notified.
- If the return is late, the member suspension procedure is activated.

Trigger

The member brings a borrowed copy back to the library.

Main Success Scenario

1. The member gives the borrowed copy to the librarian.
2. The librarian identifies the copy and its loan.
3. The system retrieves the loan information.
4. The system checks whether the return is late.
5. The system closes the loan.
6. The system checks whether the copy is reserved.
7. If it is reserved, the librarian sets the copy aside.
8. The system notifies the member who reserved the copy.
9. The librarian completes the return process.
10. The system prints the receipt.

Extensions

- 1a. Barcode cannot be read: The librarian manually enters the copy number.
- 2a. Copy is not on loan: The system displays an error message and the use case ends.
- 4a. Late return: The system activates the extension point "late return" and executes the use case "Suspend the member".
- 6a. Copy is reserved: The copy is set aside and the member who reserved it is notified.