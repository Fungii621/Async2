package com.example.async2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText liczbaElementowEditText;
    private Button przyciskStart;
    private ProgressBar pasekPostepu;
    private Sort widokSortowania;

    private int[] tablica;
    private Handler handlerUI;
    private ExecutorService uslugaWatek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liczbaElementowEditText = findViewById(R.id.liczbaElementowEditText);
        przyciskStart = findViewById(R.id.przyciskStart);
        pasekPostepu = findViewById(R.id.pasekPostepu);
        widokSortowania = findViewById(R.id.widokSortowania);

        handlerUI = new Handler(Looper.getMainLooper());
        uslugaWatek = Executors.newSingleThreadExecutor();

        przyciskStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rozpocznijSortowanie();
            }
        });
    }

    private void rozpocznijSortowanie() {
        int liczbaElementow;

        try {
            liczbaElementow = Integer.parseInt(liczbaElementowEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Niepoprawna liczba", Toast.LENGTH_SHORT).show();
            return;
        }

        tablica = new int[liczbaElementow];
        Random random = new Random();
        for (int i = 0; i < liczbaElementow; i++) {
            tablica[i] = random.nextInt(liczbaElementow);
        }

        widokSortowania.ustawTablice(tablica);
        pasekPostepu.setProgress(0);

        uslugaWatek.execute(new Runnable() {
            @Override
            public void run() {
                sortowanieBabelkowe(tablica);
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Koniec", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void sortowanieBabelkowe(int[] tablica) {
        int n = tablica.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tablica[j] > tablica[j + 1]) {
                    int temp = tablica[j];
                    tablica[j] = tablica[j + 1];
                    tablica[j + 1] = temp;

                    final int postep = (int) (((float)(i * n + j) / (n * n)) * 100);

                    handlerUI.post(new Runnable() {
                        @Override
                        public void run() {
                            widokSortowania.ustawTablice(tablica);
                            pasekPostepu.setProgress(postep);
                        }
                    });

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
