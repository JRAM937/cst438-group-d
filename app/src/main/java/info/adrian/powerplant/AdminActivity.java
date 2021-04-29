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

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    List<String> users = new ArrayList<String>(); //empty List which will get populated with database users.
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recycler_view_users);
        AdminRecyclerViewAdapter adapter = new AdminRecyclerViewAdapter(this, users);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User"); //will tap into database
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    Log.d("TEST", "Retrieved " + userList.get(0).getString("username") + " user");
                    for(int i = 0; i < userList.size(); i++){
                        Log.d("TEST", userList.get(i).getString("username") + " is being added.");
                        String username_test = userList.get(i).getString("username"); //extracts just the username from the object in the list
                        adapter.addUser(username_test); //adds the username to the list as well as updates dataset in adapter.
                        Log.d("TEST", "Added succesfully.");
                    }
                } else {
                    Log.d("TEST", "Error: " + e.getMessage());
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}