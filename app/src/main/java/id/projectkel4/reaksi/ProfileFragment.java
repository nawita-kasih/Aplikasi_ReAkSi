package id.projectkel4.reaksi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView tvCardName, tvCardAgeGen, tvCardBlood, tvCardPhysical, tvCardDisease, tvCardAllergy, tvCardMeds, tvCardInsurance, tvCardContact1, tvCardContact2;
    private Button btnGoToEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvCardName = view.findViewById(R.id.tvCardName);
        tvCardAgeGen = view.findViewById(R.id.tvCardAgeGen);
        tvCardBlood = view.findViewById(R.id.tvCardBlood);
        tvCardPhysical = view.findViewById(R.id.tvCardPhysical);
        tvCardDisease = view.findViewById(R.id.tvCardDisease);
        tvCardAllergy = view.findViewById(R.id.tvCardAllergy);
        tvCardMeds = view.findViewById(R.id.tvCardMeds);
        tvCardInsurance = view.findViewById(R.id.tvCardInsurance);
        tvCardContact1 = view.findViewById(R.id.tvCardContact1);
        tvCardContact2 = view.findViewById(R.id.tvCardContact2);
        btnGoToEdit = view.findViewById(R.id.btnGoToEdit);

        loadProfileData();

        btnGoToEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfileData();
    }

    private void loadProfileData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("ReAksiProfile", Context.MODE_PRIVATE);

        tvCardName.setText(prefs.getString("NAME", "-").toUpperCase());
        tvCardAgeGen.setText("USIA & GENDER: " + prefs.getString("AGEGEN", "-").toUpperCase());
        tvCardBlood.setText("GOLONGAN DARAH: " + prefs.getString("BLOOD", "-").toUpperCase());
        tvCardPhysical.setText("TB / BB: " + prefs.getString("PHYSICAL", "-").toUpperCase());
        tvCardDisease.setText("PENYAKIT BAWAAN: " + prefs.getString("DISEASE", "-").toUpperCase());
        tvCardAllergy.setText("ALERGI: " + prefs.getString("ALLERGY", "-").toUpperCase());
        tvCardMeds.setText("OBAT RUTIN: " + prefs.getString("MEDS", "-").toUpperCase());
        tvCardInsurance.setText("NO BPJS/ASURANSI: " + prefs.getString("INSURANCE", "-").toUpperCase());
        tvCardContact1.setText("1. " + prefs.getString("CONTACT1", "-"));
        tvCardContact2.setText("2. " + prefs.getString("CONTACT2", "-"));
    }
}