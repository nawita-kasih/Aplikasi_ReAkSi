package id.projectkel4.reaksi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class KatalogFragment extends Fragment {

    private RecyclerView rvKatalog;
    private KatalogAdapter adapter;
    private List<String> listAsli;
    private List<String> listFilter;
    private EditText etSearch;
    private LinearLayout layoutEmpty; // Tambahan untuk layout kosong

    public KatalogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_katalog, container, false);

        // Inisialisasi View (Materi P3)
        etSearch = view.findViewById(R.id.etSearch);
        rvKatalog = view.findViewById(R.id.rvKatalog);
        layoutEmpty = view.findViewById(R.id.layoutEmpty); // Inisialisasi layout kosong

        rvKatalog.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inisialisasi Data Lengkap Panduan (Daftar 8 video)
        listAsli = new ArrayList<>();
        listAsli.add("Luka Bakar");
        listAsli.add("Kecelakaan");
        listAsli.add("Kesetrum");
        listAsli.add("Kram Otot");
        listAsli.add("Luka Iris");
        listAsli.add("Membersihkan Luka");
        listAsli.add("Pingsan");
        listAsli.add("Tersedak");

        // List yang akan ditampilkan (awalnya sama dengan asli)
        listFilter = new ArrayList<>(listAsli);

        // Pasang Adapter
        adapter = new KatalogAdapter(listFilter);
        rvKatalog.setAdapter(adapter);

        // --- Logika Fitur Search (TextWatcher) ---
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Panggil fungsi filter setiap kali teks berubah
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    // Fungsi untuk memfilter data berdasarkan input user
    private void filterData(String query) {
        listFilter.clear();
        for (String item : listAsli) {
            // Cek apakah item mengandung kata kunci (tanpa memperdulikan huruf besar/kecil)
            if (item.toLowerCase().contains(query.toLowerCase())) {
                listFilter.add(item);
            }
        }
        // Beritahu adapter bahwa data berubah (P9)
        adapter.notifyDataSetChanged();

        // LOGIKA PENGECEKAN DATA KOSONG
        if (listFilter.isEmpty()) {
            rvKatalog.setVisibility(View.GONE); // Sembunyikan list
            layoutEmpty.setVisibility(View.VISIBLE); // Tampilkan peringatan
        } else {
            rvKatalog.setVisibility(View.VISIBLE); // Tampilkan list
            layoutEmpty.setVisibility(View.GONE); // Sembunyikan peringatan
        }
    }

    /**
     * ADAPTER INTERNAL (Materi P9 - RecyclerView)
     */
    private class KatalogAdapter extends RecyclerView.Adapter<KatalogAdapter.ViewHolder> {
        private List<String> data;

        KatalogAdapter(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // MENGGUNAKAN LAYOUT ITEM KUSTOM (item_katalog_neo)
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_katalog_neo, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String judul = data.get(position);
            holder.tvJudul.setText(judul.toUpperCase()); // Teks kapital agar tegas

            // Logika Warna Garis Samping (Merah - Biru bergantian)
            if (position % 2 == 0) {
                holder.viewAccent.setBackgroundColor(Color.parseColor("#B71C1C")); // Merah
            } else {
                holder.viewAccent.setBackgroundColor(Color.parseColor("#1976D2")); // Biru (ambil dari skema logo)
            }

            // Intent ke DetailActivity (P5 - Explicit Intent)
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("JUDUL", judul);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvJudul;
            View viewAccent;
            ViewHolder(View v) {
                super(v);
                tvJudul = v.findViewById(R.id.tvJudulKatalogNeo);
                viewAccent = v.findViewById(R.id.viewAccentNeo);
            }
        }
    }
}