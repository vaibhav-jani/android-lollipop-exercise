package com.codepath.android.lollipopexercise.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.models.Contact;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private Contact mContact;
    private ImageView ivProfile;
    private TextView tvName;
    private TextView tvPhone;
    private View vPalette;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        vPalette = findViewById(R.id.vPalette);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mContact.getNumber()));
                startActivity(intent);
            }
        });

        // Extract contact from bundle
        mContact = (Contact)getIntent().getExtras().getParcelable(EXTRA_CONTACT);

        // Fill views with data
        Picasso.with(DetailsActivity.this).load(mContact.getThumbnailDrawable()).into(ivProfile);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mContact.getThumbnailDrawable());
        // Asynchronous
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                vPalette.setBackgroundColor(p.getLightMutedColor(Color.WHITE));
            }
        });

        /*Palette p = Palette.from(bitmap).generate();
        vPalette.setBackgroundColor(p.getLightMutedColor(Color.WHITE));*/

        tvName.setText(mContact.getName());
        tvPhone.setText(mContact.getNumber());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
