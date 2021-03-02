package iutsd.android.tp2.guilleminot.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Book book;
        if (getIntent().getExtras() != null) {
            book = (Book) getIntent().getSerializableExtra("book");
        }else book = null;

        TextView textViewName = findViewById(R.id.textBookName);
        TextView textViewAuthor = findViewById(R.id.textBookAuthor);
        TextView textViewDesc = findViewById(R.id.textDesc);

        Button btnClose = findViewById(R.id.buttonClose);

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        if (book != null) {
            textViewName.setText(book.getAuthor());
            textViewAuthor.setText(book.getBookName());
            textViewDesc.setText(book.getDescription());
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.action_help:
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("BLABLAHBLAH")
                    .setMessage("diozaefoaznefonzeofnaozek,fpzae,fpozaenfpozeafze")
                    .show();

    }
        return(super.onOptionsItemSelected(item));
    }
}