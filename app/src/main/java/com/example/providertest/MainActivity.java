package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnDelete = findViewById(R.id.btn_delete);
        Button btnQuery = findViewById(R.id.btn_query);
        Button btnUpdate = findViewById(R.id.btn_update);

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.contentproviderapplication.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.i("TAG", "Book name is " + name + ",author is " + author + ",pages is " + pages + ",prices is " + price);
                    }
                    cursor.close();
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.contentproviderapplication.provider/book");
                ContentValues values =new ContentValues();
                values.put("name","A Clash Of Kings");
                values.put("author","ZhangSan");
                values.put("pages",502);
                values.put("price",22.54);
                Uri newUri =getContentResolver().insert(uri,values);
                bookId=newUri.getPathSegments().get(1);

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.contentproviderapplication.provider/book/"+bookId);
                getContentResolver().delete(uri,null,null);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.contentproviderapplication.provider/book/"+bookId);
                ContentValues values =new ContentValues();
                values.put("name","A Storm of Swords");
                getContentResolver().update(uri,values,null,null);
            }
        });

    }
}