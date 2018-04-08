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

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAs;
    private TextView mIngridients;
    private TextView mOrigin;
    private TextView mDescription;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mIngridients = findViewById(R.id.ingredients_tv);
        mOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        for (String alsoName : sandwich.getAlsoKnownAs()) {
                mAlsoKnownAs.append(alsoName + " ");
        }
        setTVInvisibleIfEmpty(mAlsoKnownAs, (TextView) findViewById(R.id.also_known_lbl_tv));

        for (String ingridient : sandwich.getIngredients()) {
                mIngridients.append(ingridient);
        }
        setTVInvisibleIfEmpty(mIngridients, (TextView) findViewById(R.id.ingredients_lbl_tv));

        mOrigin.setText(sandwich.getPlaceOfOrigin());
        setTVInvisibleIfEmpty(mOrigin, (TextView) findViewById(R.id.origin_lbl_tv));

        mDescription.setText(sandwich.getDescription());
        setTVInvisibleIfEmpty(mDescription, (TextView) findViewById(R.id.description_lbl_tv));
    }

    private void setTVInvisibleIfEmpty(TextView view, TextView label) {
        if (view.getText().toString().trim().isEmpty()) {
            view.setVisibility(View.INVISIBLE);
            label.setVisibility(View.INVISIBLE);
        }
    }

}
