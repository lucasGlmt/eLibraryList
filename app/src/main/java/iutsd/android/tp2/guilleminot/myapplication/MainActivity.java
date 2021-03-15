package iutsd.android.tp2.caputo.elibraryfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BooksListFragment.BooksListProvider, BooksListFragment.OnListFragmentInteractionListener {

    private List<Book> booksList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        booksList = Book.GET_TEST_LIST();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.info_button) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("AndroLib v1.0");
            builder.setMessage("Thank you for using our Library management application.");
            builder.setIcon(R.drawable.androidbook);
            builder.setNegativeButton("Close", (dialog, which) -> dialog.cancel());

            builder.create().show();
        }

        if (id == R.id.add_button) {
            Intent i = new Intent(this, BookAddModify.class);
            startActivityForResult(i, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data.getExtras() != null) {
                String type = data.getExtras().getString("type");
                Book book = (Book) data.getExtras().getSerializable("book");

                if (type.equals("edit")) {
                    getBooksList().remove(book);
                }

                getBooksList().add(book);

                BooksListFragment a = (BooksListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                a.update();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClickBookDetails(Book book) {
        Intent i = new Intent(this, BookDetails.class);
        i.putExtra("book", book);
        startActivity(i);
    }

    @Override
    public void onClickBookModify(Book book) {
        Intent i = new Intent(this, BookAddModify.class);
        i.putExtra("book", book);
        startActivityForResult(i, 1);
    }

    @Override
    public void onClickBookActions(Book book) {
        Snackbar.make(
                findViewById(android.R.id.content),
                String.format("Actions pour le livre : [%d] %s", book.getBookId(), book.getBookName()),
                Snackbar.LENGTH_LONG)
        .show();
    }

    @Override
    public List<Book> getBooksList() {
        return booksList;
    }
}