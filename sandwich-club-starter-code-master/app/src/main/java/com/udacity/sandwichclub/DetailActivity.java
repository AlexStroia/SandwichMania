package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
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

    ImageView ingredientsIv;
    TextView mDetailDescTextView;
    TextView mPlaceOfOriginTextView;
    TextView mAlsoKnownAsTextView;
    TextView mIngredientsTextView;
    TextView mDescriptionData;
    TextView mPlaceOfOriginData;
    TextView mAlsoKnownData;
    TextView mIngredientsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mDetailDescTextView = findViewById(R.id.tv_detail_desc);
        mPlaceOfOriginTextView = findViewById(R.id.tv_placeOfOrigin);
        mAlsoKnownAsTextView = findViewById(R.id.tv_alsoKnownAs);
        mIngredientsTextView = findViewById(R.id.tv_ingredients);

        mDescriptionData = findViewById(R.id.tv_description_data);
        mPlaceOfOriginData = findViewById(R.id.tv_place_of_origin_data);
        mAlsoKnownData = findViewById(R.id.tv_alsoKnowAs_data);
        mIngredientsData = findViewById(R.id.tv_ingredients_data);

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
        Sandwich sandwich = null;

        sandwich = JsonUtils.parseSandwichJson(json);

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

    private void populateUI(final Sandwich sandwich) {
        mDescriptionData.setText(sandwich.getDescription());
        mPlaceOfOriginData.setText(sandwich.getPlaceOfOrigin());
        final String alsoKnownAs = formatArray(sandwich.getAlsoKnownAs());
        mAlsoKnownData.setText(alsoKnownAs);
        final String ingredients = formatArray(sandwich.getIngredients());
        mIngredientsData.setText(ingredients);
    }

    private String formatArray(List<String> list) {
        String formatedString;
        if (list.isEmpty()) {
            formatedString = "Not defined";
            return formatedString;
        }
        formatedString = TextUtils.join(", ", list);
        return formatedString;
    }
}
