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
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.Objects;

public class UserEditAccountActivity extends AppCompatActivity {
    private TextInputEditText new_username;
    private TextInputEditText new_password;
    private boolean success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_account);

        Button changePassButton = findViewById(R.id.change_pass);
        Button changeUserButton = findViewById(R.id.change_user);
        Button deleteAccButton = findViewById(R.id.delete);
        Button admin = findViewById(R.id.admin);
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

        deleteAccButton.setOnClickListener(v -> {
            deleteAcc();
        });
        ParseUser user = ParseUser.getCurrentUser();
        if(user.getBoolean("admin")){
            admin.setVisibility(View.VISIBLE);
            admin.setOnClickListener(view -> {
                Intent intent = new Intent(UserEditAccountActivity.this, AdminActivity.class);
                startActivity(intent);
            });
        }
    }

    private void changeUsername(String username) {
        ParseUser user = ParseUser.getCurrentUser();
        //Run some checks to see if username is valid before making a query

        //Case 1: New username is blank

        if(username.equals("")){
            Toast.makeText(this, "New Username Cannot Be Blank", Toast.LENGTH_SHORT).show();
            success = false;
            //test boolean value
            Log.d("Test", "Value: " + success);
        }

        //Case 2: Username already exists
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> users, ParseException e) {
                for(int i = 0; i < users.size(); i++){
                    if(users.get(i).getString("username").equals(username)){
                        success = false;
                        //test boolean value
                        Log.d("Test", "Value: " + success);
                        break;
                    }
                }
            }
        });

        //Set the username if the above cases pass
        if(success) {
            query.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject user, ParseException e) {
                    if (e == null) {
                        user.put("username", username);
                        user.saveInBackground();
                    }
                }
            });
            Toast.makeText(this, "Change Successful!", Toast.LENGTH_SHORT).show();
        } else{
            //I couldn't put this failure toast inside the query so here it is instead
            Toast.makeText(this, "New Username Must Be Different From Current Username.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changePassword(String password) {
        ParseUser user = ParseUser.getCurrentUser();
        //Run some checks to see if password is valid before making a query
        //Note: Parse won't let me get the password, so all I can do si check if it is empty

        //Case 1: New password is blank
        if(password.equals("")){
            Toast.makeText(this, "New Password Cannot Be Blank", Toast.LENGTH_SHORT).show();
            success = false;
            //test boolean value
            Log.d("Test", "Value: " + success);
        }

        //Change Password if the above checks pass
        if(success) {
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
                    }
                }
            });
            Toast.makeText(this, "Change Successful!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAcc(){
        ParseUser user = ParseUser.getCurrentUser();
        user.deleteInBackground();
        showAlert("Account has been deleted", "Thanks for using Power Plants!");
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
                        Intent intent = new Intent(UserEditAccountActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

}
