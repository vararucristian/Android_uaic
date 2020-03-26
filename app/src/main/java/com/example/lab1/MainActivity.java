package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView titleTextView;
    private String[] ProductsNames;
    private String[] ProductsImages;
    private Double[] ProductsPrices;
    private String[] ProductsDescriptions;
    private ListView productsListView;
    private ItemsListAdapter itemsListAdapter;
    private String TAG = "MainActivity";
    private String username = "";
    private String password = "";
    private String wrongDataText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ProductsNames = new String[]{"product1","product1","product1","product1"};
        this.ProductsImages = new String[]{"/storage/emulated/0/lab1/index.png","Internal storage/lab1/index.png","Internal storage/lab1/index.png","Internal storage/lab1/index.png"};
        this.ProductsPrices = new Double[]{24.99,24.99,24.99,24.99};
        this.ProductsDescriptions = new String[]{"a description","a description","a description","a description"};
        this.productsListView = findViewById( R.id.ProductListView);
        this.titleTextView = findViewById(R.id.titleTextView);
        this.titleTextView.setText("OnlineShop");
        Log.i("ceva", String.valueOf(Environment.getDataDirectory()));
        this.productsListView.setAdapter(createListAdaptor(this.ProductsNames,this.ProductsImages));

        this.productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("MainActivity image", ProductsImages[position]);
                Intent viewProductIntent = new Intent(getApplicationContext(),ViewProductActivity.class);
                viewProductIntent.putExtra("ProductName",ProductsNames[position]);
                viewProductIntent.putExtra("ProductImage", ProductsImages[position]);
                viewProductIntent.putExtra("ProductPrice",ProductsPrices[position].toString());
                viewProductIntent.putExtra("ProductDescription",ProductsDescriptions[position]);
                startActivity(viewProductIntent);

            }
        });


        checkPreferences();
        getUsernameAndPassword();

    }

    private void getUsernameAndPassword() {
        File file = new File(getApplicationContext().getFilesDir(),"userCredentials.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line = reader.readLine();

            this.username = line;
            Log.i("username",username);
            line = reader.readLine();
            this.password = line;
            Log.i("password",password);

        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }

    }

    private void checkPreferences() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        boolean boldPref = sharedPreferences.getBoolean("TitleBold",false);
        boolean italicPref = sharedPreferences.getBoolean("TitleItalic",false);
        boolean normalPref = sharedPreferences.getBoolean("TitleNormal",false);
        if(boldPref)
            this.titleTextView.setTypeface(Typeface.DEFAULT_BOLD);
        if(italicPref)
            this.titleTextView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        if(normalPref)
            this.titleTextView.setTypeface(Typeface.DEFAULT);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Connect:
                Connect();
                return true;
            case R.id.MyAccount:
                MyAccount();

                return true;
            case R.id.prefs:
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.sensors:
                Intent intent1 = new Intent(getApplicationContext(),SensorsActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void MyAccount() {
        if(username.isEmpty() || password.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("You are not authenticated");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            Intent intent = new Intent(getApplicationContext(),MyAccountActivity.class);
            intent.putExtra("username",this.username);
            intent.putExtra("password",this.password);
            startActivity(intent);
        }

    }

    private void Connect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connect");
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.connect_alert,null);
        TextView textView = view.findViewById(R.id.wrongDataId);
        textView.setText(wrongDataText);
        builder.setView(view);
        builder.setPositiveButton("oK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText usernameEdit = view.findViewById(R.id.editUsername);
                EditText passwordEdit = view.findViewById(R.id.editPassword);
                username = String.valueOf(usernameEdit.getText());
                password = passwordEdit.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    wrongDataText = "The fields should not be empty!";
                    Connect();
                } else {
                    saveCredentials();
                    wrongDataText = "";
                }
            }}
        );

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private boolean checkStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }


    private void saveCredentials(){
        if (checkStoragePermission()) {
            File file = new File(getApplicationContext().getFilesDir(),"userCredentials.txt");
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.print("");
            writer.close();
            Log.i("path",file.getAbsolutePath());
            try {
                FileWriter fileWriter = new FileWriter(file, true);
                fileWriter.append(this.username);
                fileWriter.append("\n");
                fileWriter.append(this.password);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast toast = Toast.makeText(this,"MainActivity started!",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop Called!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy called!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPreferences();
        Toast toast = Toast.makeText(this,"MainActivity resumed",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkPreferences();
        Toast toast = Toast.makeText(this,"MainActivity restarted!",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }


    private ItemsListAdapter createListAdaptor(String[] productsNames, String[] productImages){

        this.itemsListAdapter = new ItemsListAdapter(this,productsNames,productImages);
        return itemsListAdapter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String text = (String) this.titleTextView.getText();
        outState.putString("title",text);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        this.titleTextView.setText(savedInstanceState.getString("title"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
