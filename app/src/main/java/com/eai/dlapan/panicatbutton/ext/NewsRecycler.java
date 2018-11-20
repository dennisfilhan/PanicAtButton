package com.eai.dlapan.panicatbutton.ext;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eai.dlapan.panicatbutton.R;
import com.eai.dlapan.panicatbutton.domain.NewsModel.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecycler extends RecyclerView.Adapter<NewsRecycler.VH> {

    private List<News> list;

    public NewsRecycler(List<News> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewsRecycler.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_news, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecycler.VH vh, int i) {
        News item = this.list.get(i);
        vh.bind(item);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView lblHeading, lblDesc, lblSource;
        private ImageView lblIcon;

        public VH(@NonNull View itemView) {
            super(itemView);
            lblHeading = (TextView) itemView.findViewById(R.id.lblNewsItemHeading);
            lblDesc = (TextView) itemView.findViewById(R.id.lblNewsItemDesc);
            lblSource = (TextView) itemView.findViewById(R.id.lblNewsItemSourceName);
            lblIcon = (ImageView) itemView.findViewById(R.id.lblNewsItemImage);
        }

        public void bind(final News model) {
            lblHeading.setText((model.getTitle() != null && model.getTitle().length() > 1) ? model.getTitle() : "No Title");
            lblDesc.setText((model.getDescription() != null && model.getDescription().length() > 1) ? model.getDescription() : "No Description");
            lblSource.setText((model.getSource() != null) ? (model.getSource().get("name")).toString().toLowerCase() : "~");
            Picasso.get()
                    .load(model.getUrlToImage())
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_smile_32_black)
                    .centerCrop().fit().into(lblIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl())));
                }
            });
        }
    }
}
