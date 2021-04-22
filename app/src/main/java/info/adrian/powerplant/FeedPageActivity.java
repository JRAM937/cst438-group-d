package info.adrian.powerplant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }
}