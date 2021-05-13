package info.adrian.powerplant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class AdminEditUserActivity extends AppCompatActivity {

    public boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_user);

        TextInputEditText password = findViewById(R.id.password);
        Button change_password = findViewById(R.id.change_pass);
        Button delete_account = findViewById(R.id.delete_account);
        Bundle b = getIntent().getExtras();
        String user_to_change = b.getString("user");

        Log.d("TEST", "User to change is " + user_to_change);
//        Log.d("TEST", "Password to change is " + password_to_change);
        change_password.setOnClickListener(v -> {

            String password_to_change = password.getText().toString();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User"); //will tap into database
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> userList, ParseException e) {
                    if (e == null) {
                        for(int i = 0; i < userList.size(); i++){
                            String temp_username = userList.get(i).getString("username");
                            if(temp_username.equals(user_to_change)){
                                Log.d("INSIDE LOOP", "User to change is " + user_to_change);
                                Log.d("INSIDE LOOP", "Password to change is " + password_to_change);
                                userList.get(i).put("password", password_to_change);
                                userList.get(i).saveInBackground();
                                Log.d("TEST", "Password succesfully changed.");
                                break;
                            } else {
                                Log.d("ERROR", "Password not changed.");
                            }
                        }
                    } else {
                        Log.d("TEST", "Nothing happned.");
                    }
                }
            });

        });
    }
}