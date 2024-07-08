package com.reactbooks.spring_boot_library.responsemodels;

import com.reactbooks.spring_boot_library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelfCurrentLoansResponse {

    private Book book;
    private int daysLeft;

}
