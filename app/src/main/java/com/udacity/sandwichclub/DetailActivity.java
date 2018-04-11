package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final String TAG = "DetailActivity";
    private static final int DEFAULT_POSITION = -1;
    private TextView tvAlsoKnown, tvOrigin, tvDescription, tvIngredients;
    private ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        tvAlsoKnown = findViewById(R.id.also_known_tv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvDescription = findViewById(R.id.description_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError("Null intent");
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError("Couldn't get position");
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError("JSON parsing failed");
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError(String error) {
        Log.e(TAG, error);
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        String alsoKnownAs = listToString(sandwich.getAlsoKnownAs());

        String origin = sandwich.getPlaceOfOrigin();
        if (origin.equals("")) {
            origin = getString(R.string.unavailable);
        }

        String description = sandwich.getDescription();
        if (description.equals("")) {
            description = getString(R.string.unavailable);
        }

        String ingredients = listToString(sandwich.getIngredients());

        tvAlsoKnown.setText(alsoKnownAs);
        tvOrigin.setText(origin);
        tvDescription.setText(description);
        tvIngredients.setText(ingredients);
    }

    private String listToString(List<String> list) {
        StringBuilder stringBuilder;
        if (list == null) {
            stringBuilder = new StringBuilder(getString(R.string.unavailable));
        } else {
            stringBuilder = new StringBuilder();
            int size = list.size();
            for (int i = 0; i < size; ++i) {
                stringBuilder.append(list.get(i));
                if (i == size - 2) {
                    stringBuilder.append(" & ");
                } else if (i < size - 2) {
                    stringBuilder.append(", ");
                }
            }
        }
        return stringBuilder.toString();
    }
}
