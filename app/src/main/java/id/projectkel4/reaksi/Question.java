package id.projectkel4.reaksi;

public class Question {
    private String pertanyaan;
    private String[] pilihan;
    private int indexJawabanBenar; // 0 untuk A, 1 untuk B, 2 untuk C, 3 untuk D

    public Question(String pertanyaan, String[] pilihan, int indexJawabanBenar) {
        this.pertanyaan = pertanyaan;
        this.pilihan = pilihan;
        this.indexJawabanBenar = indexJawabanBenar;
    }

    public String getPertanyaan() { return pertanyaan; }
    public String[] getPilihan() { return pilihan; }
    public int getIndexJawabanBenar() { return indexJawabanBenar; }
}