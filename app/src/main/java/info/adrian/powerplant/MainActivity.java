package info.adrian.powerplant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;



public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Testing connection to Parse-->
        ParseObject firstObject = new  ParseObject("FirstClass");
        firstObject.put("message","Hey ! First message from android. Parse is now connected");
        firstObject.saveInBackground(e -> {
            if (e != null){
                Log.e("MainActivity", e.getLocalizedMessage());
            }else{
                Log.d("MainActivity","Object saved.");
            }
        });
        //<--Testing connection to Parse



        //testing

        //onClick to go to user login page
        Button admin_button = (Button) findViewById(R.id.admin_button);
        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(loginActivity);
            }
        });

        //onClick for Administrator login page
        Button feed_page_button = (Button) findViewById(R.id.feed_page_button);
        feed_page_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(), FeedPageActivity.class);
                startActivity(loginActivity);
            }
        });

        //onClick for create account page
        Button edit_account_button = (Button) findViewById(R.id.edit_account_button);
        edit_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(), UserEditAccountActivity.class);
                startActivity(loginActivity);
            }
        });

        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(v -> {
            progressDialog.show();
            // logging out of Parse
            ParseUser.logOutInBackground(e -> {
                progressDialog.dismiss();
                if (e == null)
                    showAlert("So, you're going...", "Ok...Bye-bye then");
            });
        });

        Button add_post_button = (Button) findViewById(R.id.add_post_button);
        add_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(), AddPostActivity.class);
                startActivity(loginActivity);
            }
        });
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}