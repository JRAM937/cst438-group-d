package info.adrian.powerplant;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class UserPostsAdapter extends RecyclerView.Adapter<UserPostsAdapter.ViewHolder> {

    private List<Post> usersPosts;
    private Context context;
    LayoutInflater inflater;

    public UserPostsAdapter(Context context, List<Post> usersPosts) {
        this.context = context;
        this.usersPosts = usersPosts;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_posts_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = usersPosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return usersPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView upImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            upImage = itemView.findViewById(R.id.upImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "GOT TO EDIT POST SCREEN", Toast.LENGTH_LONG).show();
                }
            });

        }

        public void bind(Post post) {
            ParseFile image = post.getImage();

            if(image != null){
                Glide.with(context).load(image.getUrl()).into(upImage);
            }

        }
    }
}
