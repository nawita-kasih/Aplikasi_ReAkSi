package id.projectkel4.reaksi;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController; // Import ini
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class DetailActivity extends AppCompatActivity {

    private ViewPager2 viewPagerSteps;
    private VideoView videoViewTutorial;
    private String[] steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String judulKecelakaan = getIntent().getStringExtra("JUDUL");
        if (judulKecelakaan == null) judulKecelakaan = "Panduan Umum";

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        tvTitle.setText("Panduan: " + judulKecelakaan);

        // --- 1. LOGIKA TEKS LANGKAH-LANGKAH ---
        // (Tetap sama seperti kode kamu sebelumnya...)
        setupSteps(judulKecelakaan);

        viewPagerSteps = findViewById(R.id.viewPagerSteps);
        StepAdapter adapter = new StepAdapter(steps);
        viewPagerSteps.setAdapter(adapter);

        // --- 2. LOGIKA VIDEO DENGAN KONTROL DURASI ---
        videoViewTutorial = findViewById(R.id.videoViewTutorial);
        Button btnPlay = findViewById(R.id.btnPlayVideo);

        // --- TAMBAHKAN KODE INI ---
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoViewTutorial); // Menempelkan kontrol ke VideoView
        videoViewTutorial.setMediaController(mediaController); // Aktifkan slider durasi
        // --------------------------

        int videoResId;
        switch (judulKecelakaan) {
            case "Kecelakaan": videoResId = R.raw.kecelakaan; break;
            case "Kesetrum": videoResId = R.raw.kesetrum; break;
            case "Kram Otot": videoResId = R.raw.kram_otot; break;
            case "Luka Bakar": videoResId = R.raw.luka_bakar; break;
            case "Luka Iris": videoResId = R.raw.luka_iris; break;
            case "Membersihkan Luka": videoResId = R.raw.membersihkan_luka; break;
            case "Pingsan": videoResId = R.raw.pingsan; break;
            case "Tersedak": videoResId = R.raw.tersedak; break;
            default: videoResId = R.raw.membersihkan_luka; break;
        }

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
        videoViewTutorial.setVideoURI(videoUri);

        btnPlay.setOnClickListener(v -> {
            if (!videoViewTutorial.isPlaying()) {
                videoViewTutorial.start();
                btnPlay.setText("Pause Video");
            } else {
                videoViewTutorial.pause();
                btnPlay.setText("Putar Video");
            }
        });
    }

    // Helper untuk merapikan kode steps
    private void setupSteps(String judul) {
        if (judul.equals("Luka Bakar")) {
            steps = new String[]{ "Siram luka dengan air mengalir.", "Lepaskan perhiasan.", "Tutup dengan kassa.", "Jangan pecah lepuhan." };
        } else if (judul.equals("Tersedak")) {
            steps = new String[]{ "Berdiri di belakang korban.", "5 tepukan punggung.", "5 tekanan perut.", "Ulangi sampai keluar." };
        } else if (judul.equals("Pingsan")) {
            steps = new String[]{ "Baringkan telentang.", "Angkat kaki (30cm).", "Longgarkan pakaian.", "Berikan ruang udara." };
        } else {
            steps = new String[]{ "Amankan lokasi.", "Periksa kesadaran.", "Hubungi 119.", "Ikuti instruksi video." };
        }
    }

    // --- ADAPTER RECYCLERVIEW (Tetap sama seperti sebelumnya) ---
    private class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
        private String[] data;
        StepAdapter(String[] data) { this.data = data; }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvNum.setText("Langkah " + (position + 1));
            holder.tvDesc.setText(data[position]);
        }

        @Override
        public int getItemCount() { return data.length; }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNum, tvDesc;
            ViewHolder(View v) {
                super(v);
                tvNum = v.findViewById(R.id.tvStepNumber);
                tvDesc = v.findViewById(R.id.tvStepDescription);
            }
        }
    }
}