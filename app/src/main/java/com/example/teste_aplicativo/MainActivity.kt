package com.example.teste_aplicativo

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.teste_aplicativo.databinding.ActivityMainBinding
import com.example.teste_aplicativo.model.CalculateIntakeDaily
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calculateIntakeDaily:CalculateIntakeDaily

    private var resultMl = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendar: Calendar

    var currentTime = 0
    var currentMinutes = 0

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        calculateIntakeDaily = CalculateIntakeDaily()

        binding.buttonCalc.setOnClickListener {
            if (binding.peso.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_weight_information, Toast.LENGTH_SHORT).show()
            } else if (binding.idade.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_age_information, Toast.LENGTH_SHORT).show()
            } else {
                val weight = binding.peso.text.toString().toDouble()
                val age = binding.idade.text.toString().toInt()
                calculateIntakeDaily.calculateTotalMl(weight, age)
                resultMl = calculateIntakeDaily.resultTotalMl()
                val format = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                format.isGroupingUsed = false
                binding.txtResultMl.text = format.format(resultMl) + " " + "ml"
            }
        }

        binding.refresh.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialogTitle)
                .setMessage(R.string.dialogMessage)
                .setPositiveButton("OK", {dialogInterface, i ->
                    binding.peso.setText("")
                    binding.idade.setText("")
                    binding.txtResultMl.setText("")
                    binding.txtHora.setText(R.string.text_hora)
                    binding.txtMinutos.setText(R.string.text_minutos)
                })
            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i ->

            })
            val dialog = alertDialog.create()
            dialog.show()
        }

        binding.btnLembrete.setOnClickListener {
            calendar = Calendar.getInstance()
            currentTime = calendar.get(Calendar.HOUR_OF_DAY)
            currentMinutes = calendar.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this, {timePicker:TimePicker, hourOfDay:Int, minutes: Int ->
                binding.txtHora.text = String.format("%02d", hourOfDay)
                binding.txtMinutos.text = String.format("%02d", minutes)
            }, currentTime, currentMinutes, true)
            timePickerDialog.show()
        }

        binding.btnAlarm.setOnClickListener {

            val txtHora = binding.txtHora
            val txtMinutes = binding.txtMinutos

            if (!txtHora.toString().isEmpty() && !txtMinutes.toString().isEmpty()) {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, txtHora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txtMinutes.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarmMessage))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

        }
    }

}