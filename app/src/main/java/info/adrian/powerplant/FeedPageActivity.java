package info.adrian.powerplant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedPageActivity extends AppCompatActivity {

    public static final String TAG = "FeedPageActivity";
    private ImageView image;
    private TextView test;
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton mAddPost;
    private RecyclerView rvPosts;

    private PostsAdapter adapter;
    private List<Post> allPosts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);
        rvPosts = findViewById(R.id.rvPosts);

        mAddPost = findViewById(R.id.fab_add);

        mBottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(mBottomAppBar);

        mAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postActivity = new Intent(getApplicationContext(), AddPostActivity.class);
                startActivity(postActivity);
            }
        });


        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getApplicationContext(), allPosts);

        rvPosts.setAdapter(adapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        queryPosts();
    }

    private void queryPosts() {
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
                }

                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
        return true;
    }
}