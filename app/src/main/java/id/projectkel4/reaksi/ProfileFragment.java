package id.projectkel4.reaksi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText etNama, etGoldar, etAlergi, etKontak;
    private TextView tvDispNama, tvDispGoldar, tvDispAlergi, tvDispKontak;
    private Button btnSave;

    public ProfileFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi dengan pengecekan aman
        try {
            etNama = view.findViewById(R.id.etNama);
            etGoldar = view.findViewById(R.id.etGoldar);
            etAlergi = view.findViewById(R.id.etAlergi);
            etKontak = view.findViewById(R.id.etKontak);

            tvDispNama = view.findViewById(R.id.tvDispNama);
            tvDispGoldar = view.findViewById(R.id.tvDispGoldar);
            tvDispAlergi = view.findViewById(R.id.tvDispAlergi);
            tvDispKontak = view.findViewById(R.id.tvDispKontak);

            btnSave = view.findViewById(R.id.btnSave);

            if (btnSave != null) {
                btnSave.setOnClickListener(v -> updateMedicalCard());
            }
        } catch (Exception e) {
            // Jika terjadi error saat inisialisasi, aplikasi tidak langsung force close
            e.printStackTrace();
        }

        return view;
    }

    private void updateMedicalCard() {
        // Cek null untuk menghindari NullPointerException
        if (etNama == null || etGoldar == null || etAlergi == null || etKontak == null) return;

        String nama = etNama.getText().toString().trim();
        String goldar = etGoldar.getText().toString().trim();
        String alergi = etAlergi.getText().toString().trim();
        String kontak = etKontak.getText().toString().trim();

        if (nama.isEmpty()) {
            etNama.setError("Nama tidak boleh kosong");
            return;
        }

        // Update Tampilan Preview
        if (tvDispNama != null) tvDispNama.setText("" + nama.toUpperCase());
        if (tvDispGoldar != null) tvDispGoldar.setText(goldar.isEmpty() ? "-" : goldar.toUpperCase());
        if (tvDispAlergi != null) tvDispAlergi.setText(alergi.isEmpty() ? "-" : alergi);
        if (tvDispKontak != null) tvDispKontak.setText("Emergency Call: " + (kontak.isEmpty() ? "-" : kontak));

        if (getContext() != null) {
            Toast.makeText(getContext(), "Medical Card Berhasil Diperbarui!", Toast.LENGTH_SHORT).show();
        }
    }
}