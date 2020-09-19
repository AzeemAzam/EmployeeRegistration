package com.test.testsat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button proman_btn,composer_btn,writer_btn,designer_btn,books_btn,helpingbooks_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proman_btn= findViewById(R.id.button);
        proman_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_proman();
            }
        });
        composer_btn= findViewById(R.id.button2);
        composer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_composer();
            }
        });
        writer_btn=findViewById(R.id.button3);
        writer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_wtiter();
            }
        });
        designer_btn=findViewById(R.id.button4);
        designer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_designer();
            }
        });
        books_btn=findViewById(R.id.button5);
        books_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_books();
            }
        });
        helpingbooks_btn=findViewById(R.id.button6);
        helpingbooks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_helpingbooks();
            }
        });

    }
    public void open_proman(){
        Intent intent=new Intent(this,proman.class);
        startActivity(intent);
    }
    public void  open_composer(){
        Intent intent =new Intent(this,ComposerActivity.class);
        startActivity(intent);
    }
    public void open_wtiter(){
        Intent intent=new Intent(this,Writer.class);
        startActivity(intent);
    }
    public void open_designer(){
        Intent intent=new Intent(this,Designer.class);
        startActivity(intent);
    }
    public void open_books(){
        Intent intent=new Intent(this,Books.class);
        startActivity(intent);
    }
    public void open_helpingbooks(){
        Intent intent=new Intent(this,HelpingBooks.class);
        startActivity(intent);
    }

}