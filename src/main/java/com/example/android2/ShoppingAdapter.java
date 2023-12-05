package com.example.android2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private final List<String> shoppingList;

    public ShoppingAdapter(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = shoppingList.get(position);
        holder.editTextItem.setText(item);
        holder.editButton.setOnClickListener(v -> onEditClick(holder));
        holder.saveButton.setOnClickListener(v -> onSaveClick(holder));
        holder.deleteButton.setOnClickListener(v -> onDeleteClick(holder));

    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    private void setEditingMode(ViewHolder holder, boolean isEditing) {
        holder.editTextItem.setEnabled(isEditing);
        holder.editButton.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        holder.saveButton.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }

    private void onEditClick(ViewHolder holder) {
        boolean isEditing = !holder.editTextItem.isEnabled();
        setEditingMode(holder, isEditing);

        if (isEditing) {
            holder.editTextItem.setSelection(holder.editTextItem.getText().length());
            holder.editTextItem.requestFocus();

            InputMethodManager imm = (InputMethodManager) holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(holder.editTextItem, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onSaveClick(ViewHolder holder) {
        String editedItem = holder.editTextItem.getText().toString();
        shoppingList.set(holder.getAdapterPosition(), editedItem);
        setEditingMode(holder, false);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onDeleteClick(ViewHolder holder) {
        shoppingList.remove(holder.getAdapterPosition());
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public EditText editTextItem;
        public ImageView editButton;
        public ImageView saveButton;
        public ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextItem = itemView.findViewById(R.id.editTextItem);
            editButton = itemView.findViewById(R.id.editButton);
            saveButton = itemView.findViewById(R.id.saveButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            itemView.setBackgroundResource(R.drawable.item_border);
        }
    }
    public static class ItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        public ItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
