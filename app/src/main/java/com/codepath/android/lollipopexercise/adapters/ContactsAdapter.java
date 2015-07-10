package com.codepath.android.lollipopexercise.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.activities.DetailsActivity;
import com.codepath.android.lollipopexercise.models.Contact;
import com.squareup.picasso.Picasso;

import java.util.List;

// Provide the underlying view for an individual list item.
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.VH> {
    private Activity mContext;
    private List<Contact> mContacts;

    public ContactsAdapter(Activity context, List<Contact> contacts) {
        mContext = context;
        if (contacts == null) {
            throw new IllegalArgumentException("contacts must not be null");
        }
        mContacts = contacts;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(VH holder, int position) {
        Contact contact = mContacts.get(position);
        holder.rootView.setTag(contact);
        holder.tvName.setText(contact.getName());
        Picasso.with(mContext).load(contact.getThumbnailDrawable()).into(holder.ivProfile);

        if(position == 3) {
            holder.cvRoot.setCardElevation(15);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    // Provide a reference to the views for each contact item
    public final static class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvName;
        final View vPalette;
        final CardView cvRoot;
        private final Activity context;

        public VH(final View itemView, final Activity context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            vPalette = itemView.findViewById(R.id.vPalette);
            cvRoot = (CardView) itemView.findViewById(R.id.cvRoot);

            this.context = context;

            // Navigate to contact details activity on click of card view.
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*final Contact contact = (Contact)v.getTag();
                    if (contact != null) {

                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact);
                        context.startActivity(intent);
                        // Fire an intent when a contact is selected
                        // Pass contact object in the bundle and populate details activity.
                    }*/

                    // previously visible view
                    final View myView = ivProfile;

                    if(myView.getVisibility() == View.VISIBLE) {

                        // get the center for the clipping circle
                        int cx = (myView.getLeft() + myView.getRight()) / 2;
                        int cy = (myView.getTop() + myView.getBottom()) / 2;

                        // get the initial radius for the clipping circle
                        int initialRadius = myView.getWidth();

                        // create the animation (the final radius is zero)
                        if(Build.VERSION.SDK_INT >= 21) {

                            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

                            // make the view invisible when the animation is done
                            anim.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    myView.setVisibility(View.INVISIBLE);
                                }
                            });

                            // start the animation

                            anim.setDuration(1000);
                            anim.start();

                        } else {

                            myView.setVisibility(View.INVISIBLE);
                        }

                    } else {

                        // get the center for the clipping circle
                        int cx = (myView.getLeft() + myView.getRight()) / 2;
                        int cy = (myView.getTop() + myView.getBottom()) / 2;

                        cx = cx/2;
                        cy = 0;

                        // get the final radius for the clipping circle
                        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

                        if(Build.VERSION.SDK_INT >= 21) {

                            // create the animator for this view (the start radius is zero)
                            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

                            anim.setDuration(1000);

                            // make the view visible and start the animation
                            myView.setVisibility(View.VISIBLE);
                            anim.start();

                        } else {

                            myView.setVisibility(View.VISIBLE);
                        }

                    }

                }
            });

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Contact contact = (Contact)v.getTag();
                    if (contact != null) {

                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact);
                        //context.startActivity(intent);

                        if(Build.VERSION.SDK_INT >= 21) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, itemView, "robot");
                            context.startActivity(intent, options.toBundle());
                        } else {
                            context.startActivity(intent);
                        }
                        // start the new activity
                        //context.startActivity(intent);
                        // Fire an intent when a contact is selected
                        // Pass contact object in the bundle and populate details activity.
                    }
                }
            });
        }
    }
}
