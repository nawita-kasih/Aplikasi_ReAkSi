package id.projectkel4.reaksi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etAgeGen, etBlood, etPhysical, etDisease, etAllergy, etMeds, etInsurance, etContact1, etContact2;
    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etAgeGen = findViewById(R.id.etAgeGen);
        etBlood = findViewById(R.id.etBlood);
        etPhysical = findViewById(R.id.etPhysical);
        etDisease = findViewById(R.id.etDisease);
        etAllergy = findViewById(R.id.etAllergy);
        etMeds = findViewById(R.id.etMeds);
        etInsurance = findViewById(R.id.etInsurance);
        etContact1 = findViewById(R.id.etContact1);
        etContact2 = findViewById(R.id.etContact2);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        loadExistingData();

        btnSaveProfile.setOnClickListener(v -> saveProfileData());
    }

    private void loadExistingData() {
        SharedPreferences prefs = getSharedPreferences("ReAksiProfile", Context.MODE_PRIVATE);
        etName.setText(prefs.getString("NAME", ""));
        etAgeGen.setText(prefs.getString("AGEGEN", ""));
        etBlood.setText(prefs.getString("BLOOD", ""));
        etPhysical.setText(prefs.getString("PHYSICAL", ""));
        etDisease.setText(prefs.getString("DISEASE", ""));
        etAllergy.setText(prefs.getString("ALLERGY", ""));
        etMeds.setText(prefs.getString("MEDS", ""));
        etInsurance.setText(prefs.getString("INSURANCE", ""));
        etContact1.setText(prefs.getString("CONTACT1", ""));
        etContact2.setText(prefs.getString("CONTACT2", ""));
    }

    private void saveProfileData() {
        SharedPreferences prefs = getSharedPreferences("ReAksiProfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("NAME", etName.getText().toString());
        editor.putString("AGEGEN", etAgeGen.getText().toString());
        editor.putString("BLOOD", etBlood.getText().toString());
        editor.putString("PHYSICAL", etPhysical.getText().toString());
        editor.putString("DISEASE", etDisease.getText().toString());
        editor.putString("ALLERGY", etAllergy.getText().toString());
        editor.putString("MEDS", etMeds.getText().toString());
        editor.putString("INSURANCE", etInsurance.getText().toString());
        editor.putString("CONTACT1", etContact1.getText().toString());
        editor.putString("CONTACT2", etContact2.getText().toString());
        editor.apply();

        Toast.makeText(this, "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        finish();
    }
}