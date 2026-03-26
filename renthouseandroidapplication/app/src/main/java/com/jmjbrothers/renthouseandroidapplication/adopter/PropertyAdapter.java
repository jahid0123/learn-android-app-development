package com.jmjbrothers.renthouseandroidapplication.adopter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.jmjbrothers.renthouseandroidapplication.model.GetPostedProperty;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private List<GetPostedProperty> propertyList;
    private Context context;

    public PropertyAdapter(Context context, List<GetPostedProperty> propertyList) {
        this.context = context;
        this.propertyList = propertyList;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.item_property, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        GetPostedProperty property = propertyList.get(position);

        holder.titleText.setText(property.getTitle());
        holder.rentText.setText("Rent: " + property.getRentAmount() + " BDT");
        holder.locationText.setText(property.getThana() + ", " + property.getSection());

        if (property.getImageUrls() != null && !property.getImageUrls().isEmpty()) {
            Glide.with(context).load(property.getImageUrls().get(0)).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public static class PropertyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText, rentText, locationText;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleText = itemView.findViewById(R.id.titleText);
            rentText = itemView.findViewById(R.id.rentText);
            locationText = itemView.findViewById(R.id.locationText);
        }
    }
}

