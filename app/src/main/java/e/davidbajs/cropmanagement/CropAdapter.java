package e.davidbajs.cropmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import e.davidbajs.lib_crop.Crop;
import e.davidbajs.lib_crop.CropList;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {
    CropList cl;
    Activity activity;

    public CropAdapter(CropList cl, Activity activity) {
        this.cl = cl;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cropName;
        public TextView fieldName;
        public ImageView img;
        public View row;

        public ViewHolder(View view) {
            super(view);
            row = view.findViewById(R.id.rvRow);
            cropName = (TextView) view.findViewById(R.id.cropName);
            fieldName = (TextView) view.findViewById(R.id.fieldName);
            img = (ImageView) view.findViewById(R.id.imgCrop);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_adapter, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Crop crop = cl.getPosition(i);

        viewHolder.cropName.setText(crop.getName());
        viewHolder.fieldName.setText(crop.getMyLocation().getFieldName());

        viewHolder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropAdapter.startDView(crop.getIdCrop(), activity);
            }
        });

        viewHolder.row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == DialogInterface.BUTTON_POSITIVE) {
                            cl.RemoveCrop(crop.getIdCrop());
                            CropAdapter.this.notifyDataSetChanged();
                        } else {
                        }
                    }
                };
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setMessage("Do you want to remove this crop?").
                        setPositiveButton("Yes", dialogListener).
                        setNegativeButton("No", dialogListener).show();

                return true;
            }
        });

        if (crop.getName().equals("Corn")) {
            viewHolder.img.setImageResource(R.drawable.corn);
            viewHolder.row.setBackgroundColor(Color.parseColor("#FBEC5D"));
        }
        if (crop.getName().equals("Potato")) {
            viewHolder.img.setImageResource(R.drawable.potato);
            viewHolder.row.setBackgroundColor(Color.parseColor("#f4c575"));
        }
        if (crop.getName().equals("Wheat")) {
            viewHolder.img.setImageResource(R.drawable.wheat);
            viewHolder.row.setBackgroundColor(Color.parseColor("#F5DEB3"));
        }
    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    private static void startDView(String id, Activity ac) {
        System.out.println("id" + ":" + id);
        Intent i = new Intent(ac.getBaseContext(), ActivityCrop.class);
        i.putExtra(CropList.ID, id);
        ac.startActivity(i);
    }
}

