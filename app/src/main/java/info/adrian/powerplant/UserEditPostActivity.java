package info.adrian.powerplant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class UserEditPostActivity extends AppCompatActivity {

    public static final String TAG = "EditPostActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;

    private EditText etDescription;
    private ImageView postImage;
    private File photoFile;
    public String photoFileName = "photo.jpg";

    private Post currentPost;
    private String postID;
    private final ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_post);

        etDescription = findViewById(R.id.description);
        Button captureImageButton = findViewById(R.id.buttonCaptureImage);
        postImage = findViewById(R.id.ivPostImage);
        Button submitButton = findViewById(R.id.submitButton);
        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        postID = getIntent().getExtras().getString("id");

        Button deleteButton = findViewById(R.id.delete_button);

        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });


        //handles the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Description can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (photoFile == null || postImage.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "There is no image!", Toast.LENGTH_SHORT).show();
                    return;
                }
                queryPosts();
                savePost(description, currentUser, photoFile);
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(), UploadImageAndAddPost.class);
                startActivity(newIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost();
                Intent newIntent = new Intent(getApplicationContext(), FeedPageActivity.class);
                startActivity(newIntent);
            }
        });

    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
                }

                //For testing purposes, this for loop gets the first post that belongs to the current user and edits it
                for(int i = posts.size() - 1; i >= 0; i--){
                    if(posts.get(i).getObjectId().equals(postID)){
                        currentPost = posts.get(i);
                    }
                }

            }
        });
    }

    //launches the phone camera
    private void launchCamera() {
        Log.i("Camera", "lauchCamera() entered");
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i("Camera", "85");
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);
        Log.i("Camera", "88");

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(UserEditPostActivity.this, "info.adrian.fileprovider", photoFile);
        Log.i("Camera", "94");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        Log.i("Camera", "96");

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.

        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            Log.i("Camera", "intent.resolveActivity(getPackageManager())");
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                postImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //method to save a post
    private void savePost(String description, ParseUser currentUser, File photoFile) {
        if (!description.equals(currentPost.getDescription())) {
            currentPost.setDescription(description);
        }
            currentPost.setImage(new ParseFile(photoFile));
            currentPost.setUser(currentUser);
            currentPost.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while saving", e);
                        Toast.makeText(getApplicationContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "Post save was successful!!!");
                    etDescription.setText("");
                    postImage.setImageResource(0);
                }
            });
            Intent i = new Intent(getApplicationContext(), FeedPageActivity.class);
            startActivity(i);
    }

    private void deletePost(){
        ParseUser user = ParseUser.getCurrentUser();

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username:" + post.getUser().getUsername());
                }
                Log.d("In userEditActivity:", "post id is "  + postID);
                for(int i = posts.size() - 1; i >= 0; i--){
                    Log.d("Current iteration: ", posts.get(i).getObjectId());
                    if(postID.equals(posts.get(i).getObjectId())) {
                        posts.get(i).deleteInBackground();
                        Log.d("Post Deleted", "title: " + posts.get(i).getDescription());
                    }
                }
            }
        });
    }
}
