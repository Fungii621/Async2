package com.example.async2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class Sort extends View {

    private int[] tablica;
    private Paint pedzel = new Paint();

    public Sort(Context context, AttributeSet attrs) {
        super(context, attrs);
        pedzel.setColor(Color.BLUE);
    }

    public void ustawTablice(int[] tablica) {
        this.tablica = tablica;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (tablica != null) {
            int szerokosc = getWidth();
            int wysokosc = getHeight();
            float szerokoscSlupka = (float) szerokosc / tablica.length;

            Random random = new Random();
            for (int i = 0; i < tablica.length; i++) {
                float wysokoscSlupka = ((float) tablica[i] / tablica.length) * wysokosc;

                pedzel.setColor(Color.BLUE);


                canvas.drawRect(i * szerokoscSlupka, wysokosc - wysokoscSlupka, (i + 1) * szerokoscSlupka, wysokosc, pedzel);
            }
        }
    }
}
