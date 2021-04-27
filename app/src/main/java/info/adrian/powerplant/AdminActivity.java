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


//    String users[];
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recycler_view_users);

        List<String> users = getUsers();
//        users = getResources().getStringArray(R.array.users); //R.array.users is coming from values -> strings.xml


//        query.countInBackground(new CountCallback() {
//            @Override
//            public void done(int count, ParseException e) {
//                if (e == null){
//                    Log.d( "count", "." + count +" .");
//                } else {
//                    Log.d("count", "failed");
//                }
//            }
//        })
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> userList, ParseException e) {
//                if (e == null) {
//                    Log.d("TEST", "Retrieved " + userList.get(0).getString("username") + " user");
//                    for(int i = 0; i < userList.size(); i++){
//                        Log.d("TEST", "Retrieved " + userList.get(i).getString("username") + " user");
//                        String username_test = userList.get(i).getString("username");
//                        user_test.add(username_test);
//                        Log.d("TEST", "Added succesfully.");
//                    }
//                } else {
//                    Log.d("TEST", "Error: " + e.getMessage());
//                }
//                Log.d("TEST", "INSIDE: String users[] size: " + user_test.size() + " users");
//
//            }
//        });
        Log.d("TEST", "OUTSIDE: String users[] size: " + users.size() + " users");

        AdminRecyclerViewAdapter adapter = new AdminRecyclerViewAdapter(this, users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private List<String> getUsers() {
        List<String> user_test = new ArrayList<String>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    Log.d("TEST", "Retrieved " + userList.get(0).getString("username") + " user");
                    for(int i = 0; i < userList.size(); i++){
                        Log.d("TEST", "Retrieved " + userList.get(i).getString("username") + " user");
                        String username_test = userList.get(i).getString("username");
                        user_test.add(username_test);
                        Log.d("TEST", "Added succesfully.");
                    }
                } else {
                    Log.d("TEST", "Error: " + e.getMessage());
                }
                Log.d("TEST", "INSIDE: String users[] size: " + user_test.size() + " users");

            }
        });


        return user_test;
    }
}