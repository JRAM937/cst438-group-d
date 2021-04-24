package info.adrian.powerplant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedPageActivity extends AppCompatActivity {

    public static final String TAG = "FeedPageActivity";
    private ImageView image;
    private TextView test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);

        Button addPostButton = (Button) findViewById(R.id.add_post);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postActivity = new Intent(getApplicationContext(), AddPost.class);
                startActivity(postActivity);
            }
        });
    }
}