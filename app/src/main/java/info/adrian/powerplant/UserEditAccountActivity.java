package info.adrian.powerplant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
public class UserEditAccountActivity extends AppCompatActivity {
    private TextInputEditText new_username;
    private TextInputEditText new_password;
    private Button changeUserButton;
    private Button changePassButton;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_account);

        changePassButton = findViewById(R.id.change_pass);
        changeUserButton = findViewById(R.id.change_user);
        new_password = findViewById(R.id.password);
        new_username = findViewById(R.id.username);

        changePassButton.setOnClickListener(v -> {
            changePassword(new_password.getText().toString());
            success = false;
        });

        changeUserButton.setOnClickListener(v -> {
            changeUsername(new_username.getText().toString());
            success = false;
        });
    }

    private void changeUsername(String username) {
        ParseUser user = ParseUser.getCurrentUser();
        //Change Username
            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
            query.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject user, ParseException e) {
                    if (e == null) {
                        user.put("username", username);
                        user.saveInBackground();
                        success = true;
                        //test boolean value
                        Log.d("Test", "Value: " + success);
                    } else {
                        //test boolean value
                        Log.d("Test", "Value: " + success);
                    }
                }
            });

        if(success) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Change failed. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changePassword(String password) {
        ParseUser user = ParseUser.getCurrentUser();
        //Change Password
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject user, ParseException e) {
                if (e == null) {
                    user.put("password", password);
                    user.saveInBackground();
                    success = true;
                    //test boolean value
                    Log.d("Test", "Value: " + success);
                } else {
                    //test boolean value
                    Log.d("Test", "Value: " + success);
                }
            }
        });

        if(success) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Change failed. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserEditAccountActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(UserEditAccountActivity.this, LogoutActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

}
