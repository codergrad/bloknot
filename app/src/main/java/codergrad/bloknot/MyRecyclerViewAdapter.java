package codergrad.bloknot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Note> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Загружаем данныые в адаптер
    MyRecyclerViewAdapter(Context context, ArrayList<Note> data, ItemClickListener onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onItemClick;
    }
/*

Тест коммит 1

 */
    // "Надуваем" ячейку из XML-макета
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // Цепляем данные к TextView каждой ячейке
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardviewTitle.setText(mData.get(position).getTitle());
        holder.cardviewContent.setText(mData.get(position).getContent());
    }

    // Всего ячеек
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardviewTitle;
        TextView cardviewContent;

        ViewHolder(View itemView) {
            super(itemView);
            cardviewTitle = itemView.findViewById(R.id.cardviewTitle);
            cardviewContent = itemView.findViewById(R.id.cardviewContent);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Удобный метод получения номера ячейки
    long getItem(int id) {
        return mData.get(id).getId();
    }

    // Позволяет реагировать на нажатия
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // родительское activity должно реализовывать этот метод для обработки нажатий
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}