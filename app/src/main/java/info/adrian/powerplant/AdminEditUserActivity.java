package info.adrian.powerplant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

    String TAG = "DELETE ACCOUNT";
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
                        for (int i = 0; i < userList.size(); i++) {
                            String temp_username = userList.get(i).getString("username");
                            if (temp_username.equals(user_to_change)) {
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

        delete_account.setOnClickListener(v -> {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User"); //will tap into database
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> userList, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < userList.size(); i++) {
                            String temp_username = userList.get(i).getString("username");
                            if (temp_username.equals(user_to_change)) {
                                deleteAcc((ParseUser) userList.get(i));
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


    private void deleteAcc(ParseUser user) {

        //look for posts that belong to the current user and delete them
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, user);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
                }

                for (int i = posts.size() - 1; i >= 0; i--) {
                    posts.get(i).deleteInBackground();
                    Log.d("Post Deleted", "title: " + posts.get(i).getDescription());
                }
            }
        });

        //Delete the user from the database
        user.deleteInBackground();
        showAlert("Account has been deleted", "Thanks for using Power Plants!");
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminEditUserActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(AdminEditUserActivity.this, AdminActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}