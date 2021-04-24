package info.adrian.powerplant;

import androidx.appcompat.app.AppCompatActivity;

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
        Button loginButton = (Button) findViewById(R.id.user_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
        });

        //onClick for Administrator login page
        Button administratorLoginButton = (Button) findViewById(R.id.administrator_login);
        administratorLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //onClick for create account page
        Button createAccountButton = (Button) findViewById(R.id.create_account);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpActivity);
            }
        });
    }
}