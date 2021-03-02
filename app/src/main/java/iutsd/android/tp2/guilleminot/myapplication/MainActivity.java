package iutsd.android.tp2.guilleminot.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BooksListFragment.BooksListProvider, BooksListFragment.OnListFragmentInteractionListener{

    static BooksListFragment booksListFragment;

    private List<Book> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        books = Book.GET_TEST_LIST();
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickBookDetails(Book book) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("book", book);

        startActivity(intent);
    }

    @Override
    public void onClickBookModify(Book book) {
        View parentLayout = findViewById(android.R.id.content);

        Snackbar.make(parentLayout , "Modifier le livre " + book.getBookName(), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onClickBookActions(Book book) {
        View parentLayout = findViewById(android.R.id.content);

        Snackbar.make(parentLayout , "Actions du livre " + book.getBookName(), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public List<Book> getBooksList() {
        return books;
    }
}