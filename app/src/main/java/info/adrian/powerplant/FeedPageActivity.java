package info.adrian.powerplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static info.adrian.powerplant.R.id.app_bar_home;
import static info.adrian.powerplant.R.id.app_bar_search;

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case app_bar_home:
                Intent editAccountActivity = new Intent(getApplicationContext(), UserEditAccountActivity.class);
                startActivity(editAccountActivity);
                return true;

//            case app_bar_search:
//                Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivity(searchActivity);
//                return true;

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
}