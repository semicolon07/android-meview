package com.unigainfo.android.meview.sample.viewholderfactory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unigainfo.android.meview.adapter.ItemViewHolder;
import com.unigainfo.android.meview.adapter.ViewHolderFactory;
import com.unigainfo.android.meview.sample.R;
import com.unigainfo.android.meview.sample.model.ImageWithLabelViewModel;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public class ImageWithLabelViewHolderFactory extends ViewHolderFactory<ImageWithLabelViewModel,ImageWithLabelViewHolderFactory.ViewHolder> {

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_item_image_with_label;
    }

    @Override
    public void bindData(ViewHolder viewHolder, ImageWithLabelViewModel item, int position) {
        viewHolder.labelTextView.setText(item.getLabel());
        Glide.with(context)
                .load(item.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_broken_image)
                .centerCrop()
                .animate(android.R.anim.fade_in)
                .into(viewHolder.imageView);
    }

    class ViewHolder extends ItemViewHolder{
        ImageView imageView;
        TextView labelTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) findViewById(R.id.label_textView);
            imageView = (ImageView) findViewById(R.id.icon_imageView);
        }
    }
}
