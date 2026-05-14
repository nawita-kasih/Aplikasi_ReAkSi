package id.projectkel4.reaksi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvQuestionNumber;
    private Button[] btnOptions = new Button[4];
    private Button btnBack, btnNext, btnHome;
    private LinearLayout layoutNavigation;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;

    // Array untuk menyimpan jawaban yang dipilih pengguna (-1 berarti belum dijawab)
    private int[] selectedAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Inisialisasi View
        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        btnOptions[0] = findViewById(R.id.btnOption0);
        btnOptions[1] = findViewById(R.id.btnOption1);
        btnOptions[2] = findViewById(R.id.btnOption2);
        btnOptions[3] = findViewById(R.id.btnOption3);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnHome = findViewById(R.id.btnHome);
        layoutNavigation = findViewById(R.id.layoutNavigation);

        loadQuestions();

        // Siapkan array penyimpan jawaban
        selectedAnswers = new int[questionList.size()];
        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1; // Default -1
        }

        displayQuestion();

        // Listener Pilihan Jawaban
        for (int i = 0; i < btnOptions.length; i++) {
            final int selectedOption = i;
            btnOptions[i].setOnClickListener(v -> selectOption(selectedOption));
        }

        // Navigasi Kembali
        btnBack.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
            }
        });

        // Navigasi Selanjutnya / Selesai
        btnNext.setOnClickListener(v -> {
            if (selectedAnswers[currentQuestionIndex] == -1) {
                Toast.makeText(QuizActivity.this, "Pilih jawaban terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentQuestionIndex < questionList.size() - 1) {
                currentQuestionIndex++;
                displayQuestion();
            } else {
                calculateAndShowFinalScore();
            }
        });

        // Tombol Kembali ke Beranda
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    // --- 10 PERTANYAAN KUIS ---
    private void loadQuestions() {
        questionList = new ArrayList<>();

        // Soal 1
        questionList.add(new Question("Apa yang TIDAK boleh dilakukan saat menangani luka bakar ringan?",
                new String[]{"Membasuh dengan air mengalir", "Memecahkan lepuhan kulit", "Menutup dengan kassa steril", "Melepaskan perhiasan"}, 1));

        // Soal 2
        questionList.add(new Question("Bagaimana posisi kaki yang benar saat menangani orang yang baru saja pingsan?",
                new String[]{"Ditekuk ke perut", "Sejajar dengan kepala", "Diangkat sekitar 30 cm", "Disilangkan"}, 2));

        // Soal 3
        questionList.add(new Question("Jika seseorang tersedak tetapi masih bisa batuk dengan kuat, apa tindakan Anda?",
                new String[]{"Lakukan Heimlich maneuver", "Beri minum air yang banyak", "Dorong ia untuk terus batuk", "Tepuk punggungnya sekeras mungkin"}, 2));

        // Soal 4
        questionList.add(new Question("Cara yang benar untuk menghentikan mimisan (hidung berdarah) adalah...",
                new String[]{"Mendongakkan kepala ke atas", "Menunduk ke depan dan pencet cuping hidung", "Berbaring telentang", "Memasukkan tisu dalam-dalam"}, 1));

        // Soal 5
        questionList.add(new Question("Langkah paling awal yang harus dilakukan jika melihat korban kecelakaan tidak sadarkan diri adalah?",
                new String[]{"Langsung beri napas buatan", "Amankan lokasi / pastikan lingkungan aman (Danger)", "Beri minum air putih", "Tarik korban menjauh dari lokasi"}, 1));

        // Soal 6
        questionList.add(new Question("Jika mata terkena cipratan bahan kimia atau debu berbahaya, apa yang harus segera dilakukan?",
                new String[]{"Kucek mata sekuatnya", "Bilas dengan air bersih mengalir 15-20 menit", "Teteskan obat mata warung", "Tutup mata dengan kain kering rapat-rapat"}, 1));

        // Soal 7
        questionList.add(new Question("Tindakan pertama jika melihat seseorang tersengat arus listrik (kesetrum) adalah?",
                new String[]{"Tarik bajunya segera", "Siram dengan air", "Matikan sumber listrik/sekring utama", "Pegang tangannya untuk menarik"}, 2));

        // Soal 8
        questionList.add(new Question("Berapa rasio (perbandingan) antara kompresi dada dan napas buatan pada RJP (Resusitasi Jantung Paru) untuk dewasa?",
                new String[]{"15 kompresi : 2 napas", "30 kompresi : 2 napas", "10 kompresi : 1 napas", "50 kompresi : 5 napas"}, 1));

        // Soal 9
        questionList.add(new Question("Bagaimana penanganan pertama yang tepat untuk kram otot saat berolahraga?",
                new String[]{"Pijat dengan sangat keras", "Hentikan aktivitas dan regangkan otot perlahan", "Lanjutkan olahraga agar otot lemas", "Kompres dengan es batu langsung ke kulit"}, 1));

        // Soal 10
        questionList.add(new Question("Apa pertolongan pertama yang benar jika tergigit ular berbisa?",
                new String[]{"Ikat kuat-kuat di atas luka (turniket)", "Hisap bisa ular dari luka", "Imobilisasi area gigitan dan segera ke RS", "Sayat luka menggunakan pisau"}, 2));
    }

    private void displayQuestion() {
        Question q = questionList.get(currentQuestionIndex);

        // Tampilkan Nomor Soal
        tvQuestionNumber.setText("Soal " + (currentQuestionIndex + 1) + " dari " + questionList.size());
        tvQuestion.setText(q.getPertanyaan());

        String[] prefix = {"A. ", "B. ", "C. ", "D. "};

        // Atur teks pilihan ganda
        for (int i = 0; i < btnOptions.length; i++) {
            btnOptions[i].setText(prefix[i] + q.getPilihan()[i]);
            btnOptions[i].setVisibility(View.VISIBLE);
        }

        updateOptionStyles();

        // Atur navigasi
        btnBack.setVisibility(currentQuestionIndex == 0 ? View.INVISIBLE : View.VISIBLE);
        btnNext.setText(currentQuestionIndex == questionList.size() - 1 ? "SELESAI" : "SELANJUTNYA");
        btnHome.setVisibility(View.GONE);
    }

    private void selectOption(int selectedOption) {
        selectedAnswers[currentQuestionIndex] = selectedOption;
        updateOptionStyles();
    }

    private void updateOptionStyles() {
        for (int i = 0; i < btnOptions.length; i++) {
            if (selectedAnswers[currentQuestionIndex] == i) {
                btnOptions[i].setBackgroundResource(R.drawable.neo_brutal_yellow);
            } else {
                btnOptions[i].setBackgroundResource(R.drawable.neo_brutal_white);
            }
        }
    }

    private void calculateAndShowFinalScore() {
        int score = 0;
        StringBuilder reviewBuilder = new StringBuilder();
        reviewBuilder.append("REVIEW JAWABAN BENAR:\n\n");

        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);

            // Cek kebenaran jawaban
            if (selectedAnswers[i] == q.getIndexJawabanBenar()) {
                score++;
            }

            // Susun teks review untuk semua pertanyaan (jawaban yang benar saja yang ditampilkan)
            reviewBuilder.append("Soal ").append(i + 1).append(":\n")
                    .append(q.getPertanyaan()).append("\n")
                    .append("✔ Kunci Jawaban: ").append(q.getPilihan()[q.getIndexJawabanBenar()])
                    .append("\n\n");
        }

        int nilaiSkala100 = (score * 100) / questionList.size();

        // Tampilkan Hasil Akhir di Layar
        tvQuestionNumber.setText("HASIL & REVIEW");
        tvQuestion.setText("KUIS SELESAI!\n\nSkor Anda: " + nilaiSkala100 + "\n(Benar: " + score + ", Salah: " + (questionList.size() - score) + ")\n\n" + reviewBuilder.toString());

        // Sembunyikan tombol pilihan ganda dan navigasi
        for (Button btn : btnOptions) {
            btn.setVisibility(View.GONE);
        }
        layoutNavigation.setVisibility(View.GONE);

        // Munculkan tombol kembali ke Home
        btnHome.setVisibility(View.VISIBLE);
    }
}