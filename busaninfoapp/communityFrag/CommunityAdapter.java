package com.example.busaninfoapp.communityFrag;

import android.app.Fragment;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busaninfoapp.Community;
import com.example.busaninfoapp.R;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>{
    ArrayList<Community> communities = new ArrayList<>();

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommunityAdapter.CommunityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_community, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {
        Community community = communities.get(position);

        holder.msgText.setText(community.message);
        holder.image.setImageURI(Uri.parse(community.getImageUri()));
        //holder.commentCnt.setText(community.heartCnt);
        //holder.heartCnt.setText(community.commentCnt);

        /*
        holder.heartImage.setOnClickListener(new View.OnClickListener() {
            public boolean heartTrans = true;
            MainCommFragment mainCommFragment = new MainCommFragment();

            @Override
            public void onClick(View v) {
                if (heartTrans = true) {
                    holder.heartImage.setImageResource(R.drawable.fullheart);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getFragmentManager().beginTransaction().replace(R.id.commFragment, mainCommFragment).addToBackStack(null).commit();
                    heartTrans = false;

                } else {
                    holder.heartImage.setImageResource(R.drawable.blankheart);
                    heartTrans = true;
                }

            }
        });
        */
    }

    @Override
    public int getItemCount() { return communities.size(); }

    public void addData(Community community) { communities.add(community); }

    class CommunityViewHolder extends RecyclerView.ViewHolder {

        TextView msgText, heartCnt, commentCnt;
        ImageView image, heartImage, commentImage;

        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            heartCnt = itemView.findViewById(R.id.heartCount);
            commentCnt = itemView.findViewById(R.id.commentCount);
            msgText = itemView.findViewById(R.id.msgText);
            heartImage = itemView.findViewById(R.id.heartImage);
            commentImage = itemView.findViewById(R.id.commentImage);
        }
    }
}
