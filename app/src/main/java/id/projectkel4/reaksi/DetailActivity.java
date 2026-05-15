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
        switch (judul) {
            case "Kecelakaan":
                steps = new String[]{
                        "Amankan lokasi kejadian dan pasang tanda bahaya.",
                        "Jangan pindahkan korban kecuali ada ancaman nyawa (api/ledakan).",
                        "Segera hubungi layanan gawat darurat (119).",
                        "Hentikan perdarahan parah dengan menekan luka menggunakan kain bersih."
                };
                break;
            case "Kesetrum":
                steps = new String[]{
                        "JANGAN sentuh korban!\n Matikan sumber listrik utama segera.",
                        "Jauhkan sumber listrik menggunakan benda non-konduktor \n(kayu/karet kering).",
                        "Hubungi layanan medis (119).",
                        "Cek napas dan denyut nadi korban,\n lakukan CPR jika terhenti."
                };
                break;
            case "Kram Otot":
                steps = new String[]{
                        "Hentikan aktivitas fisik dan istirahatkan area yang kram.",
                        "Regangkan otot yang kram secara perlahan dan tahan.",
                        "Pijat lembut area tersebut untuk merelaksasi otot.",
                        "Minum air putih atau cairan elektrolit untuk mencegah dehidrasi."
                };
                break;
            case "Luka Bakar":
                steps = new String[]{
                        "Siram luka dengan air biasa yang mengalir selama 10-20 menit.",
                        "Lepaskan perhiasan atau pakaian ketat di sekitar luka sebelum membengkak.",
                        "Tutup area luka dengan kassa steril atau kain bersih yang longgar.",
                        "JANGAN memecahkan lepuhan atau mengoleskan odol/mentega."
                };
                break;
            case "Luka Iris":
                steps = new String[]{
                        "Cuci tangan Anda dengan sabun sebelum menangani luka.",
                        "Tekan luka dengan kain bersih selama beberapa menit untuk menghentikan darah.",
                        "Bilas luka di bawah air mengalir untuk membersihkan kotoran.",
                        "Tutup dengan plester atau perban steril agar tidak infeksi."
                };
                break;
            case "Membersihkan Luka":
                steps = new String[]{
                        "Cuci tangan Anda dengan sabun dan air mengalir.",
                        "Siram area luka dengan air bersih untuk menghilangkan debu/kotoran.",
                        "Gunakan pinset steril jika masih ada kerikil yang menempel.",
                        "Keringkan dengan lembut, oleskan antiseptik, lalu tutup dengan perban."
                };
                break;
            case "Pingsan":
                steps = new String[]{
                        "Baringkan korban di tempat yang teduh dan datar.",
                        "Angkat kakinya sedikit lebih tinggi dari dada (sekitar 30 cm).",
                        "Longgarkan pakaian yang ketat (sabuk, kerah baju, dasi).",
                        "Berikan ruang udara yang cukup dan jangan biarkan orang berkerumun."
                };
                break;
            case "Tersedak":
                steps = new String[]{
                        "Berdiri di belakang korban dan peluk pinggangnya.",
                        "Berikan 5 tepukan kuat di punggung \n(di antara tulang belikat).",
                        "Lakukan 5 tarikan hentakan di perut (Heimlich Maneuver).",
                        "Ulangi terus sampai benda yang menyumbat berhasil keluar."
                };
                break;
            default:
                steps = new String[]{
                        "Tetap tenang dan amankan lokasi kejadian.",
                        "Periksa tingkat kesadaran korban.",
                        "Hubungi bantuan darurat medis (119).",
                        "Ikuti instruksi video di bawah ini."
                };
                break;
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