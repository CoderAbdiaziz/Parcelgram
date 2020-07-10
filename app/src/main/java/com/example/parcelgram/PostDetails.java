package com.example.parcelgram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostDetails extends AppCompatActivity {

    Post post;
    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvUsername2;
    private TextView tvTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        // unwrap the movie passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        Log.d("PostDetails", String.format("Showing details for '%s'", post.getUser().getUsername()));

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvUsername2 = findViewById(R.id.tvUsername2);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);

        tvTimeStamp.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));
        tvUsername.setText(post.getUser().getUsername());
        tvUsername2.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        Glide.with(getApplicationContext())
                .load(post.getImage().getUrl()).into(ivImage);
    }
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}