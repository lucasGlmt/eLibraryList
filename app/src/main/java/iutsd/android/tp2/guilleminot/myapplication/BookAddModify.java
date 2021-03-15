package iutsd.android.tp2.caputo.elibraryfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookAddModify extends AppCompatActivity {

    Book book;
    TextView bookName, bookAuthor, bookDesc;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Add/Modify a book");
        setContentView(R.layout.activity_book_add_modify);

        bookName = findViewById(R.id.edit_bookName);
        bookAuthor = findViewById(R.id.edit_bookAuthor);
        bookDesc = findViewById(R.id.edit_bookDesc);
        submit = findViewById(R.id.buttonSubmit);

        if (getIntent().getExtras() != null) {
            book = (Book) getIntent().getSerializableExtra("book");

            bookName.setText(book.getBookName());
            bookAuthor.setText(book.getAuthor());
            bookDesc.setText(book.getDescription());

            submit.setText("Modify this book");
        } else {
            book = null;
            submit.setText("Add this book");
        }

        submit.setOnClickListener(v ->
                finish()
        );
    }

    @Override
    public void finish() {
        Intent i = new Intent();

        if (book == null) {
            book = new Book();
            i.putExtra("type", "add");
        } else {
            i.putExtra("type", "edit");
        }

        book.setBookName(bookName.getText().toString());
        book.setAuthor(bookAuthor.getText().toString());
        book.setDescription(bookDesc.getText().toString());

        i.putExtra("book", book);
        setResult(RESULT_OK, i);
        super.finish();
    }

}
