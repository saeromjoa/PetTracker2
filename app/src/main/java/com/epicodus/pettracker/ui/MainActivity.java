package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.parceler.Parcels;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.weightButton) Button mWeightButton;
    @Bind(R.id.medicationButton) Button mMedicationButton;
    @Bind(R.id.addPet) ImageView mAddPet;
    @Bind(R.id.petName) TextView mPetName;
    @Bind(R.id.birthDate) TextView mBirthDate;
    @Bind(R.id.vetButton) Button mVetButton;

    private Pet mPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPet = Parcels.unwrap(getIntent().getParcelableExtra("pet"));

        if (mPet != null){
            DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
            Date birthDate = mPet.getBirthdate();
            String stringDate = df.format(birthDate);


            mPetName.setText(mPet.getName() + " | " + mPet.getGender());
            mBirthDate.setText(stringDate);
        }


        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mPetName.setTypeface(rampung);
        mBirthDate.setTypeface(rampung);
        mWeightButton.setTypeface(rampung);
        mMedicationButton.setTypeface(rampung);
        mVetButton.setTypeface(rampung);

        mWeightButton.setOnClickListener(this);
        mMedicationButton.setOnClickListener(this);
        mAddPet.setClickable(true);
        mAddPet.setOnClickListener(this);
        mVetButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        if(v == mWeightButton) {
            Intent intent = new Intent(MainActivity.this, WeightActivity.class);
            startActivity(intent);
        } else if(v == mMedicationButton){
            Intent intent = new Intent(MainActivity.this, MedicationActivity.class);

            startActivity(intent);
        } else if (v == mAddPet){
            Intent intent = new Intent(MainActivity.this, PetListActivity.class);
            startActivity(intent);
        } else if (v == mVetButton){
            Intent intent = new Intent(MainActivity.this, VetActivity.class);
            startActivity(intent);
        }
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}