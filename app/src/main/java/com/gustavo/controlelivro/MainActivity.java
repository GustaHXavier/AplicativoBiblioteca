package com.gustavo.controlelivro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gustavo.controlelivro.Helper.MyDataBaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDataBaseHelper my;
    ArrayList<String> livro_id, livro_title, livro_author, livro_pages;
    ClienteAdapter clienteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        my = new MyDataBaseHelper(MainActivity.this);
        livro_id = new ArrayList<>();
        livro_title = new ArrayList<>();
        livro_author = new ArrayList<>();
        livro_pages = new ArrayList<>();

        displayData();

        clienteAdapter = new ClienteAdapter(MainActivity.this, MainActivity.this, livro_id, livro_title, livro_author, livro_pages);
        recyclerView.setAdapter(clienteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void displayData (){
        Cursor cursor = my.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "Nenhum livro encontrado", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                livro_id.add(cursor.getString(0));
                livro_title.add(cursor.getString(1));
                livro_author.add(cursor.getString(2));
                livro_pages.add(cursor.getString(3));
            }

        }

    }
}