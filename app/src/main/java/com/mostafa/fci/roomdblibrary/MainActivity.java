package com.mostafa.fci.roomdblibrary;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.util.StringUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name , author , search;
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        author = findViewById(R.id.author);
        details = findViewById(R.id.details);
        search  = findViewById(R.id.search);

    }

    long num = System.currentTimeMillis() + (int )(Math.random() * 100 + 1);

    public void savebtn_Clicked(View view) {

        Book book = new Book(++num , name.getText().toString(),author.getText().toString());
        BookRoomDatabase db = BookRoomDatabase.getDatabase(MainActivity.this);
        BookDao mBookDao = db.BookDao();
        mBookDao.insertOnlySingleBook(book);
        Toast.makeText(MainActivity.this , "Inserted " ,Toast.LENGTH_LONG).show();


    }

    public void getDatabtn_Clicked(View view) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(MainActivity.this);
        BookDao mBookDao = db.BookDao();
        details.setText("Details : \n");

        String text = search.getText().toString().trim();
        if ( !text.isEmpty() ) {
            long id = Long.valueOf(search.getText().toString()).longValue();
            Book book = mBookDao.fetchOneBooksbyBookId(id);
            if (book != null)
                details.setText(details.getText() + "\nID : "+book.getId()+"  , name : "+book.getName()+" " +
                        ", author : "+book.getAuthor() +"\n");
            else
                Toast.makeText(MainActivity.this , "not Found " ,Toast.LENGTH_LONG).show();
        }else {
            List<Book> books = mBookDao.fetchAllBooks();
            for (Book book : books) {
                details.setText(details.getText() +"\nID : "+book.getId()+"  , name : "+book.getName()+" " +
                        ", author : "+book.getAuthor() +"\n");
            }
        }
    }


    public void deleteDatabtn_Clicked(View view) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(MainActivity.this);
        BookDao mBookDao = db.BookDao();

        String text = search.getText().toString().trim();
        if ( !text.isEmpty() ) {
            long id = Long.valueOf(search.getText().toString()).longValue();
            Book book = mBookDao.fetchOneBooksbyBookId(id);
            if (book!= null) {
                mBookDao.deleteBook(book);
                Toast.makeText(MainActivity.this, "Deleted ", Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(MainActivity.this , "Not Found " ,Toast.LENGTH_LONG).show();
        }else {
            mBookDao.deleteAllBook();
            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();
        }
    }

    public void updateDatabtn_Clicked(View view) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(MainActivity.this);
        BookDao mBookDao = db.BookDao();

        String text = search.getText().toString().trim();
        if ( !text.isEmpty() ) {
            long id = Long.valueOf(search.getText().toString()).longValue();
            Book book = mBookDao.fetchOneBooksbyBookId(id);
            if (book!= null) {
                book.setName(name.getText().toString());
                book.setAuthor(author.getText().toString());
                mBookDao.updateBook(book);
                Toast.makeText(MainActivity.this, "Updated ", Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(MainActivity.this , "Not Found " ,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this, "Set ID", Toast.LENGTH_LONG).show();
        }
    }

}
