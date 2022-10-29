package hamidasonia.com.androidcallit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hamidasonia.com.androidcallit.Interface.ItemClickListener;
import hamidasonia.com.androidcallit.R;

/**
 * Created by Mbahman on 1/2/2018.
 */

public class MenusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView menus_name;
    public ImageView menus_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MenusViewHolder(View itemView) {
        super(itemView);

        menus_name = (TextView) itemView.findViewById(R.id.menus_name);
        menus_image = (ImageView) itemView.findViewById(R.id.menus_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}
