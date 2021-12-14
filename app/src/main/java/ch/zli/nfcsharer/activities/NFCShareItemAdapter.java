package ch.zli.nfcsharer.activities;
import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.zli.nfcsharer.R;
import ch.zli.nfcsharer.domain.NFCShareItem;


public class NFCShareItemAdapter extends RecyclerView.Adapter<NFCShareItemAdapter.ViewHolder> {

    private final MainActivity activity;
    List<NFCShareItem> nfcShareItems;
    Context context;

    public NFCShareItemAdapter(List<NFCShareItem> nfcShareItems, MainActivity activity)
    {
        this.nfcShareItems = nfcShareItems;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        NFCShareItem nfcShareItem = nfcShareItems.get(position);

        holder.itemTitle.setText(nfcShareItem.getName());
        holder.description.setText(nfcShareItem.getDescription());
        holder.cv.setCardBackgroundColor(nfcShareItem.isEnabled()? R.color.teal_200 : R.color.teal_700);
        System.out.println("nfcShareItem.isEnabled()"+nfcShareItem.isEnabled());
        holder.cv.setOnClickListener(listener ->{
            //Toast.makeText(context,"The position is:"+position,Toast.LENGTH_SHORT).show();//TODO open view

            nfcShareItems.forEach(item ->{
                if(item.isEnabled()){
                    item.setEnabled(false);
                }
            });
            nfcShareItem.setEnabled(true);
            activity.reloadRecyclerViewAdapter();
        });
        holder.deleteButton.setOnClickListener(listener ->{
            activity.getStorageService().removeItem(nfcShareItem);
            activity.reloadRecyclerViewAdapter();
        });
        holder.editButton.setOnClickListener(listener ->{
            activity.editItem(nfcShareItem);
        });
    }



    @Override
    public int getItemCount() {
        return nfcShareItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemTitle;
        TextView description;
        Button editButton;
        Button deleteButton;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.itemTitle);
            description = (TextView)itemView.findViewById(R.id.description);
            editButton = (Button)itemView.findViewById(R.id.editButton);
            deleteButton = (Button)itemView.findViewById(R.id.deleteButton);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}