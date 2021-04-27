package info.adrian.powerplant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FeedPageActivity extends AppCompatActivity {

    public static final String TAG = "FeedPageActivity";
    private ImageView image;
    private TextView test;
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton mAddPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
        return true;
    }
}