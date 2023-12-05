package com.example.android2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShoppingAdapter adapter;
    private EditText newItemEditText;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ShoppingAdapter.ItemDecoration(getResources().getDimensionPixelSize(R.dimen.item_margin))); // Ресурс для размера промежутка
        newItemEditText = findViewById(R.id.newItemEditText);
        ArrayList<String> shoppingList = new ArrayList<>();
        adapter = new ShoppingAdapter(shoppingList);
        recyclerView.setAdapter(adapter);

        ImageView addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            String newItem = newItemEditText.getText().toString().trim();
            if (!newItem.isEmpty()) {
                shoppingList.add(0, newItem);
                adapter.notifyDataSetChanged();
                newItemEditText.getText().clear();
                runLayoutAnimation(recyclerView);
            } else {
                Toast.makeText(MainActivity.this, "Введите текст перед добавлением", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_from_left);

        recyclerView.setLayoutAnimation(controller);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
