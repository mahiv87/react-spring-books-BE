package com.reactbooks.spring_boot_library.service;

import com.reactbooks.spring_boot_library.model.Book;
import com.reactbooks.spring_boot_library.model.Checkout;
import com.reactbooks.spring_boot_library.model.History;
import com.reactbooks.spring_boot_library.repository.BookRepository;
import com.reactbooks.spring_boot_library.repository.CheckoutRepository;
import com.reactbooks.spring_boot_library.repository.HistoryRepository;
import com.reactbooks.spring_boot_library.responsemodels.ShelfCurrentLoansResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.Duration;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private HistoryRepository historyRepository;


    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository, HistoryRepository historyRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
    }

//    Checks out a book to the user and updates the book's available copies and creates a new checkout record
    public Book checkoutBook (String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail,bookId);
        if (book.isEmpty() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("book does not exist or is already checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );

        checkoutRepository.save(checkout);

        return book.get();
    }

//    Checks if a specific book is checked out by a given user
    public Boolean isBookCheckedOutByUser (String userEmail, Long bookId) {
        return checkoutRepository.findByUserEmailAndBookId(userEmail, bookId) != null;
    }

//    Gets the count of books currently checked out by a user
    public int currentLoansCount (String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

//    Gets a list of currently checked-out books for a user, including the number of days left before return
    public List<ShelfCurrentLoansResponse> currentLoans (String userEmail) throws Exception {
        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>();

        for (Checkout i : checkoutList) {
            bookIdList.add(i.getBookId());
        }

        List<Book> books = bookRepository.findBookById(bookIdList);

        for (Book book : books) {
            Optional<Checkout> checkout = checkoutList.stream().filter(x -> x.getBookId() == book.getId()).findFirst();

            if (checkout.isPresent()) {
                LocalDate returnDate = LocalDate.parse(checkout.get().getReturnDate());
                LocalDate now = LocalDate.now();

                Duration duration = Duration.between(now.atStartOfDay(), returnDate.atStartOfDay());
                long daysLeft = duration.toDays();

                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book,(int) daysLeft));
            }
        }
        return shelfCurrentLoansResponses;
    }

//    Returns a book that was checked out by the user and updates the book's available copies,
//    deletes the checkout record, and saves the transaction to history
    public void returnBook (String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (book.isEmpty() || validateCheckout == null) {
            throw new Exception("Book doesnt exist or not checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());
        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().getImg()
        );

        historyRepository.save(history);

    }

//    Renews the loan period for a book checked out by the user
    public void renewLoan (String userEmail, Long bookId) throws Exception {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout == null) {
            throw new Exception("Book does not exist or is not checked out by user");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate returnDate = LocalDate.parse(validateCheckout.getReturnDate(), formatter);
        LocalDate now = LocalDate.now();

        if (!returnDate.isBefore(now)) {
            validateCheckout.setReturnDate(now.plusDays(7).format(formatter));
            checkoutRepository.save(validateCheckout);
        }
    }






}
