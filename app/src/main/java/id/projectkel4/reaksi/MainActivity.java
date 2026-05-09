package id.projectkel4.reaksi;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        // Menampilkan HomeFragment sebagai halaman awal saat aplikasi dibuka
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Listener untuk perpindahan Menu Navigasi Bawah (Materi P7)
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_list) {
                selectedFragment = new KatalogFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            } else if (id == R.id.nav_about) {
                selectedFragment = new AboutFragment();
            }

            return loadFragment(selectedFragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // Menggunakan animasi fade in/out agar perpindahan terasa lebih halus (Interaktif)
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}