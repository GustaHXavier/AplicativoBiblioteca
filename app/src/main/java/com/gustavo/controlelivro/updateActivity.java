package com.gustavo.controlelivro;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gustavo.controlelivro.Helper.MyDataBaseHelper;

public class updateActivity extends AppCompatActivity {

    EditText title_input, author_input, page_input;
    Button updateButtom, delete_button;
    String id, tittle, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        page_input = findViewById(R.id.page_input2);
        updateButtom = findViewById(R.id.updateButtom);
        delete_button = findViewById(R.id.delete_button);
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(tittle);
        }

        updateButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(updateActivity.this);
                myDB.updateData(id, tittle, author, pages);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmaDialogo ();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void getAndSetIntentData () {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("tittle")
                && getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {

            id = getIntent().getStringExtra("id");
            tittle = getIntent().getStringExtra("tittle");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            //SET
            title_input.setText(tittle);
            author_input.setText(author);
            page_input.setText(pages);

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void ConfirmaDialogo () {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir " + tittle + " ?");
        builder.setMessage("Tem certeza que deseja excluir " + tittle + " ?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDataBaseHelper my = new MyDataBaseHelper(updateActivity.this);
                my.deleteOneRow(id);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}