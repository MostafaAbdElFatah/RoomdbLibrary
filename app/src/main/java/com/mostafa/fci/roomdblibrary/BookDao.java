package com.mostafa.fci.roomdblibrary;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by FCI on 2018-06-22.
 */
@Dao
public interface BookDao {

    @Insert
    void insertOnlySingleBook (Book book);
    @Insert
    void insertMultipleBooks (List<Book> booksList);
    @Query ("SELECT * FROM Book WHERE id = :bookId")
    Book fetchOneBooksbyBookId (long bookId);
    @Query ("SELECT * FROM Book")
    List<Book> fetchAllBooks ();
    @Update
    void updateBook (Book book);
    @Delete
    void deleteBook (Book book);
    @Query("DELETE FROM Book")
    void deleteAllBook();

}
