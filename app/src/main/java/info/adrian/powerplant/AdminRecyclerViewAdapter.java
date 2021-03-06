package info.adrian.powerplant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecyclerViewAdapter.MyViewHolder> {

    List<String> user_data = new ArrayList<String>(); //this is to bring in the list that is in AdminActivity
    Context context;
    Bundle b = new Bundle();

    public AdminRecyclerViewAdapter (Context ct, List<String> users){
        context = ct;
        user_data = users;
    }

    public void addUser(String username){ //stupid piece of code that fixed the issue i was having for three days.
        user_data.add(username); //adds the username to the list
        notifyDataSetChanged(); //allows the data to be saved.
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_users_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.users_username.setText(user_data.get(position));
    }

    @Override
    public int getItemCount() {
        return user_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView users_username;
        Button users_view;
        String username_to_change;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            users_username = itemView.findViewById(R.id.users_username);
            users_view = itemView.findViewById(R.id.users_view);


//            Log.d("IN ADAPTER", "User is " + users_username.getText().toString());

//            b.putString("user", users_username.getText().toString());

            users_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), AdminEditUserActivity.class);
                    intent.putExtra("user", users_username.getText().toString());
                    context.startActivity(intent);
                }
            });

        }
    }
}
