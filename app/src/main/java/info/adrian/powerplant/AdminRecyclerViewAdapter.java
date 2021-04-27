package info.adrian.powerplant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecyclerViewAdapter.MyViewHolder> {

//    String user_data[];
    List<String> user_data = new ArrayList<String>();
    Context context;

    public AdminRecyclerViewAdapter (Context ct, List<String> users){
        context = ct;
        user_data = users;
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            users_username = itemView.findViewById(R.id.users_username);
        }
    }
}
