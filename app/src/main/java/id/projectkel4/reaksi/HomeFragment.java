package id.projectkel4.reaksi;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private MediaPlayer alarmPlayer, cprPlayer;
    private boolean isAlarmPlaying = false, isCprPlaying = false, isFlashOn = false;

    // Tambahkan btnQuiz di sini
    private View btnPanic, btnFlashlight, btnCPR, btnCall, btnMaps, btnQuiz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // INISIALISASI VIEW - Cek ID di fragment_home.xml harus sama persis!
        btnPanic = view.findViewById(R.id.btnPanic);
        btnFlashlight = view.findViewById(R.id.btnFlashlight);
        btnCPR = view.findViewById(R.id.btnCPR);
        btnCall = view.findViewById(R.id.btnCall);
        btnMaps = view.findViewById(R.id.btnMaps);
        btnQuiz = view.findViewById(R.id.btnQuiz); // Inisialisasi tombol kuis

        // PROTEKSI: Jika salah satu ID salah, aplikasi tidak akan crash saat dibuka
        if (btnPanic == null) return view;

        // Inisialisasi Player di sini (Gunakan try-catch agar jika file raw hilang tidak crash)
        try {
            alarmPlayer = MediaPlayer.create(getContext(), R.raw.sirine);
            cprPlayer = MediaPlayer.create(getContext(), R.raw.metronom);
        } catch (Exception e) {
            Toast.makeText(getContext(), "File suara tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        btnPanic.setOnClickListener(v -> toggleAlarm());
        btnFlashlight.setOnClickListener(v -> toggleFlashlight());
        btnCPR.setOnClickListener(v -> toggleCPR());

        btnCall.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:119"));
            startActivity(i);
        });

        btnMaps.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=rumah+sakit+terdekat");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Maps tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        // LOGIKA UNTUK KLIK TOMBOL KUIS
        if (btnQuiz != null) {
            btnQuiz.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                startActivity(intent);
            });
        }

        return view;
    }

    private void toggleAlarm() {
        if (alarmPlayer == null) return;
        if (!isAlarmPlaying) {
            alarmPlayer.start();
            isAlarmPlaying = true;
        } else {
            alarmPlayer.pause();
            alarmPlayer.seekTo(0);
            isAlarmPlaying = false;
        }
    }

    private void toggleCPR() {
        if (cprPlayer == null) return;
        if (!isCprPlaying) {
            cprPlayer.start();
            isCprPlaying = true;
        } else {
            cprPlayer.pause();
            cprPlayer.seekTo(0);
            isCprPlaying = false;
        }
    }

    private void toggleFlashlight() {
        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, !isFlashOn);
            isFlashOn = !isFlashOn;
        } catch (Exception e) {
            Toast.makeText(getContext(), "Senter Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (alarmPlayer != null && alarmPlayer.isPlaying()) alarmPlayer.pause();
        if (cprPlayer != null && cprPlayer.isPlaying()) cprPlayer.pause();
    }
}