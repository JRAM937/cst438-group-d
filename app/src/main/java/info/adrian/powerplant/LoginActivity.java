package info.adrian.powerplant;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseUser;



public class LoginActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText password;
    private Button login;
    private Button signUp;
    private ProgressBar loginProg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginProg = findViewById(R.id.loginProg);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);

        login.setOnClickListener(v -> login(username.getText().toString(), password.getText().toString()));

        signUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

    }

    private void login(String username, String password) {
        login.setVisibility(View.INVISIBLE);
        loginProg.setVisibility(View.VISIBLE);
        //progressDialog.show();
        ParseUser.logInInBackground(username, password, (parseUser, e) -> {
            //progressDialog.dismiss();
            loginProg.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            if (parseUser != null) {
                Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);//change LogoutActivity to FeedActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                ParseUser.logOut();
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}