package com.example.finder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyItemListAdapter extends FirebaseRecyclerAdapter<recyclerViewModelClass, MyItemListAdapter.myViewHoldler> {


    public MyItemListAdapter(@NonNull FirebaseRecyclerOptions<recyclerViewModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyItemListAdapter.myViewHoldler holder, final int position, @NonNull final recyclerViewModelClass model) {


           // final recyclerViewModelClass example = data[position];

        holder.tittle.setText(model.getTitle());
        Glide.with(holder.imageView.getContext()).load(model.getPicture()).into(holder.imageView);
        holder.imageViewDelete.setVisibility(View.VISIBLE);

        final String deleteThisID = model.getPostKey();

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete "+model.getTitle())
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel", null)
                        .show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteItem(deleteThisID);

                        Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


            }
        });


    }

    private void deleteItem(String postKey) {

        DatabaseReference databaseReferenceDetail = FirebaseDatabase.getInstance().getReference("Posts").child(postKey);
        databaseReferenceDetail.removeValue();

    }

    @NonNull
    @Override
    public MyItemListAdapter.myViewHoldler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false);
        return new MyItemListAdapter.myViewHoldler(view);

    }

    class myViewHoldler extends RecyclerView.ViewHolder {

        ImageView imageView,imageViewDelete;
        TextView tittle;
        TextView id;
        ConstraintLayout layout;
        //  TextView discription;

        public myViewHoldler(@NonNull View itemView) {
            super(itemView);



            imageView = itemView.findViewById(R.id.row_post_img);
            tittle = itemView.findViewById(R.id.row_post_title);
            layout = itemView.findViewById(R.id.layout);
            imageViewDelete = itemView.findViewById(R.id.deleteBtn);

        }
    }
}