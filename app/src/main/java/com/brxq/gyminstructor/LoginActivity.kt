package com.brxq.gyminstructor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.brxq.gyminstructor.databinding.ActivityLoginBinding
import com.brxq.gyminstructor.ui.programs.ProgramFragmentDirections

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.button?.setOnClickListener {

            val login = binding?.login?.text
            val password = binding?.password?.text
            Log.i("LOGIN + PASSWORD", "$login -> $password");
            if (login.contentEquals("+79219172989") && password.contentEquals("mytestpassword")) {
                selectProgram(context = this)
            } else {
                Toast.makeText(this, "Неправильный номер телефона или пароль", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding?.textView4?.setOnClickListener {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun selectProgram(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(
            "Вся информация, представленная в приложении, носит исключительно информационный характер и не является призывом к действию. Повторяйте на свой страх и риск!"
        )
            .setNegativeButton(
                "НЕТ"
            ) { _, _ ->
                Toast.makeText(
                    this,
                    "Для продолжения необходимо согласиться с информацией!",
                    Toast.LENGTH_SHORT
                ).show()
                closeOptionsMenu()
            }
            .setPositiveButton(
                "ПРИНИМАЮ"
            ) { _, _ ->
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        // Create the AlertDialog object and return it
        builder.create().show()
    }

}