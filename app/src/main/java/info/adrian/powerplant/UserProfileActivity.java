package info.adrian.powerplant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    public static final String TAG = "UsersPostsActivity";

    RecyclerView userPosts;

    private UserPostsAdapter adapter;
    private List<Post> usersPosts;
    private TextView userName;
    private Button editUser;
    private Button logout_button;
    private Button edit_post_button;
    private String mUsername;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        queryUsersPosts();

        progressDialog = new ProgressDialog(UserProfileActivity.this);
        userPosts = findViewById(R.id.userPosts);
        editUser = findViewById(R.id.editUserButton);
        logout_button = findViewById(R.id.logout_button);
        edit_post_button = findViewById(R.id.edit_post_button);

        userName = findViewById(R.id.userProfileUsername);
        userName.setText(ParseUser.getCurrentUser().getUsername());

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editAccountActivity = new Intent(getApplicationContext(), UserEditAccountActivity.class);
                startActivity(editAccountActivity);
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                // logging out of Parse
                ParseUser.logOutInBackground(e -> {
                    progressDialog.dismiss();
                    if (e == null)
                        showAlert("You have Successfully Logged out.", "Bye-bye then");

                });
            }
        });

        edit_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

                query.include(Post.KEY_USER);
                query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
                query.addDescendingOrder(Post.KEY_CREATED_AT);
                query.findInBackground(new FindCallback<Post>() {
                    @Override
                    public void done(List<Post> posts, ParseException e) {
                        if(e != null){
                            Log.e(TAG, "Issue with getting posts", e);
                            return;
                        }

                        Intent intent = new Intent(getApplicationContext(), UserEditPostActivity.class);
                        intent.putExtra("id", posts.get(0).getObjectId());
                        startActivity(intent);
                    }
                });

            }
        });

        usersPosts = new ArrayList<>();
        adapter = new UserPostsAdapter(getApplicationContext(), usersPosts);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        userPosts.setLayoutManager(gridLayoutManager);
        userPosts.setAdapter(adapter);



    }

    private void queryUsersPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
                }

                for(int i = posts.size() - 1; i >= 0; i--){ //will add the posts in reverse order which is New first, old bottom.
                    usersPosts.add(posts.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}