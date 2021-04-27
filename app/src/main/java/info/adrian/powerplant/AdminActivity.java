package info.adrian.powerplant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    String users[];
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recycler_view_users);
        users = getResources().getStringArray(R.array.users); //R.array.users is coming from values -> strings.xml

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
//        query.countInBackground(new CountCallback() {
//            @Override
//            public void done(int count, ParseException e) {
//                if (e == null){
//                    Log.d( "count", "." + count +" .");
//                } else {
//                    Log.d("count", "failed");
//                }
//            }
//        });
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    Log.d("TEST", "Retrieved " + userList.size() + " users");
                } else {
                    Log.d("TEST", "Error: " + e.getMessage());
                }
            }
        });

        AdminRecyclerViewAdapter adapter = new AdminRecyclerViewAdapter(this, users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}