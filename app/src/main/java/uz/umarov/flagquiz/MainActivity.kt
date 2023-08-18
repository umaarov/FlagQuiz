package uz.umarov.flagquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import uz.umarov.flagquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {
            if (binding.name.text!!.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
            } else if (binding.name.text!!.length < 3 || binding.name.text!!.length > 20) {
                Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_LONG).show()
            } else if (binding.name.text!!.isDigitsOnly()) {
                Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, binding.name.text.toString())
                startActivity(intent)
                finish()
            }

        }
    }
}