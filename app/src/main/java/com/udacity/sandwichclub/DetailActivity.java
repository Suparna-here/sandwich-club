package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /** Populate UI with Sandwitch details */
    private void populateUI(Sandwich sandwich) {
        TextView also_known_tv = findViewById(R.id.also_known_tv);
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        TextView placeOfOrigin_tv = findViewById(R.id.placeOfOrigin_tv);
        TextView description_tv = findViewById(R.id.description_tv);
        if (sandwich.getAlsoKnownAs() != null) {
            List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                if (i != (alsoKnownAsList.size() - 1)) {
                    also_known_tv.append(alsoKnownAsList.get(i) + ", ");
                } else also_known_tv.append(alsoKnownAsList.get(i));
            }
        } else also_known_tv.append(getString(R.string.detail_also_known_as_text));

        if (sandwich.getIngredients() != null) {
            List<String> ingredientList = sandwich.getIngredients();
            for (int i = 0; i < ingredientList.size(); i++) {
                if (i != (ingredientList.size() - 1)) {
                    ingredients_tv.append(ingredientList.get(i) + ", ");
                } else ingredients_tv.append(ingredientList.get(i));
            }
        }
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin()))placeOfOrigin_tv.setText(getString(R.string.detail_place_of_origin_text));
        else placeOfOrigin_tv.setText(sandwich.getPlaceOfOrigin());

        description_tv.setText(sandwich.getDescription());
    }
}
