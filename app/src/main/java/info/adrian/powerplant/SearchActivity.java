package info.adrian.powerplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static info.adrian.powerplant.R.id.app_bar_home;
import static info.adrian.powerplant.R.id.app_bar_search;

public class SearchActivity extends AppCompatActivity {

    private EditText username;
    private String searchUserName;
    private Button searchButton;
    private BottomAppBar mBottomAppBar;

    private RecyclerView rvPosts;

    public static final String TAG = "SearchActivity";

    private PostsAdapter adapter;
    private List<Post> allPosts;
    //testing


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        username = findViewById(R.id.userName);
        rvPosts = findViewById(R.id.rvPosts);

        mBottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(mBottomAppBar);

        searchUserName = username.getText().toString();
        Log.i(TAG, "name: " + searchUserName);

        //searchUserName = username.toString();

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //searchUserName = username.toString();

                searchUserName = username.getText().toString();
                Log.i(TAG, "name: " + searchUserName);
                queryPosts(searchUserName);
                allPosts.clear();
            }
        });

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getApplicationContext(), allPosts);

        rvPosts.setAdapter(adapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        queryPosts();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case app_bar_home:
                Intent userProfileActivity = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(userProfileActivity);
                return true;

            case app_bar_search:
                Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(searchActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
        return true;
    }

    private void queryPosts(String userName) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
                    Log.i(TAG, "User: " + userName);
                }

                for(int i = posts.size() - 1; i >= 0; i--){ //will add the posts in reverse order which is New first, old bottom.
                    if(posts.get(i).getUser().getUsername().equals(userName)){
                        allPosts.add(posts.get(i));
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}