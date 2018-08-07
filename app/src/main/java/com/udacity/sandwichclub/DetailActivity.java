package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    TextView originTextView;
    TextView originDesc;

    TextView akaTextView;
    TextView akaDescription;

    TextView ingredientsTextView;
    TextView ingredientsDescription;

    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTextView = findViewById(R.id.origin_tv);
        originDesc = findViewById(R.id.origin_desc);

        akaTextView = findViewById(R.id.also_known_tv);
        akaDescription = findViewById(R.id.aka_desc);

        ingredientsTextView = findViewById(R.id.ingredients_tv);
        ingredientsDescription = findViewById(R.id.ingredients_desc);

        descriptionTextView = findViewById(R.id.description_tv);


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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTextView.setVisibility(View.GONE);
            originDesc.setVisibility(View.GONE);
        } else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }


        String akaStr = listToString(sandwich.getAlsoKnownAs());

        if (akaStr == null || akaStr.isEmpty()) {
            akaTextView.setVisibility(View.GONE);
            akaDescription.setVisibility(View.GONE);
        } else {
            akaTextView.setText(akaStr);
        }


        String ingredientsStr = listToString(sandwich.getIngredients());

        if (ingredientsStr == null || ingredientsStr.isEmpty()) {
            ingredientsTextView.setVisibility(View.GONE);
            ingredientsDescription.setVisibility(View.GONE);
        } else {
            ingredientsTextView.setText(ingredientsStr);
        }

        descriptionTextView.setText(sandwich.getDescription());
    }

    private String listToString(List<String> list) {
        if (list == null) {
            return null;
        }

        StringBuilder compString = new StringBuilder();

        for (int s = 0; s < list.size(); s++) {

            compString.append(list.get(s));

            if (s < list.size() - 1) {
                compString.append(", \n");
            }
        }

        return compString.toString();
    }
}
