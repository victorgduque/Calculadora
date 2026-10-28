package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView pantalla;
    String num = "";
    String op = "";
    double a = 0;
    boolean fin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);
        pantalla = findViewById(R.id.tvPantalla);
    }

    public void numero(View v) {
        String t = ((Button) v).getText().toString();
        if (fin) {
            num = "";
            fin = false;
        }
        if (t.equals(",")) {
            if (num.contains(",")) return;
            if (num.isEmpty()) num = "0";
        }
        num += t;
        actualizar();
    }

    public void operador(View v) {
        if (num.isEmpty() && op.isEmpty()) return;
        if (!num.isEmpty()) {
            if (op.isEmpty()) a = leer(num);
            else if (!calcular()) return;
        }
        op = ((Button) v).getText().toString();
        num = "";
        fin = false;
        actualizar();
    }

    public void igual(View v) {
        if (num.isEmpty() || op.isEmpty()) return;
        if (calcular()) {
            op = "";
            num = mostrar(a);
            fin = true;
            actualizar();
        }
    }

    public void c(View v) {
        if (fin) {
            ac(v);
            return;
        }
        if (!num.isEmpty()) num = num.substring(0, num.length() - 1);
        actualizar();
    }

    public void ac(View v) {
        num = "";
        op = "";
        a = 0;
        fin = false;
        actualizar();
    }

    boolean calcular() {
        double b = leer(num);
        if (op.equals("+")) a += b;
        else if (op.equals("-")) a -= b;
        else if (op.equals("×")) a *= b;
        else {
            if (b == 0) {
                ac(null);
                pantalla.setText("Error: división entre 0");
                return false;
            }
            a /= b;
        }
        return true;
    }

    void actualizar() {
        String t = op.isEmpty() ? num : mostrar(a) + " " + op + " " + num;
        pantalla.setText(t.isEmpty() ? "0" : t);
    }

    double leer(String s) {
        return Double.parseDouble(s.replace(',', '.'));
    }

    String mostrar(double n) {
        if (n == (long) n) return String.valueOf((long) n);
        return String.valueOf(n).replace('.', ',');
    }
}