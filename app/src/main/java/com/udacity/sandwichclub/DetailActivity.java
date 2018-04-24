package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

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
        Log.e("Amit", "Position: " + position);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.e("Amit", "Sandwitch:: " + sandwich.toString());
        if (sandwich == null) {
            Log.e("Amit", "Failed Position: " + position);
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // description
        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        // also known as
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        if (sandwich.getAlsoKnownAs().size() == 0)
        {
            alsoKnownAs.setVisibility(View.INVISIBLE);
        }
        else
        {
            alsoKnownAs.setVisibility(View.VISIBLE);
            String str = "";
            for (int i = 0; i<sandwich.getAlsoKnownAs().size(); i++)
            {
                if (i==0) {
                    str = str + sandwich.getAlsoKnownAs().get(i);
                } else {
                    str = str + ", " + sandwich.getAlsoKnownAs().get(i);
                }
            }
            alsoKnownAs.setText(str);
        }

        // place of origin
        TextView placeOfOrigin = findViewById(R.id.placeOfOrigin_tv);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        // ingredients
        TextView ingredients = findViewById(R.id.ingredients_tv);
        String str = "";
        for (int i = 0; i<sandwich.getIngredients().size(); i++)
        {
            if (i==0) {
                str = str + sandwich.getIngredients().get(i);
            } else {
                str = str + ", " + sandwich.getIngredients().get(i);
            }
        }
        ingredients.setText(str);
    }
}
