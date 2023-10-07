package com.example.prueban2miguelangeliturra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Spinner spinner;
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private boolean enPosicionVertical = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> laboratorios = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        laboratorios.addAll(Arrays.asList("C2", "LINF", "LEICA", "LNET", "LTEL"));
        spinner.setAdapter(laboratorios);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] valores = event.values;
            float x = valores[0];
            float y = valores[1];
            float z = valores[2];

            // Determina si el dispositivo está en posición vertical.
            enPosicionVertical = (Math.abs(x) < 2 && Math.abs(y) < 2 && z > 9.5);

            if (enPosicionVertical) {
                // Muestra el mensaje Toast cuando el dispositivo está en posición vertical.
                Toast.makeText(this, "Datos Grabados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Maneja cambios en la precisión del sensor si es necesario.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
