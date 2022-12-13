package org.wit.a20090170_assignment2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.wit.a20090170_assignment2.databinding.ActivitySignInBinding
import timber.log.Timber.i

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        binding.emailSignIn.setText("")
        binding.passwordSignIn.setText("")
        binding.emailRegister.setText("")
        binding.passwordRegister.setText("")

        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonSignIn.setOnClickListener {
            try {
                var email = binding.emailSignIn.text.toString()
                var password = binding.passwordSignIn.text.toString()
                if(email.isNotEmpty() && password.isNotEmpty()) {
                    signIn(email, password)

                    binding.emailSignIn.setText("")
                    binding.passwordSignIn.setText("")
                    binding.emailRegister.setText("")
                    binding.passwordRegister.setText("")
                }
                else {
                    Toast.makeText(baseContext, "Please fill in necessary fields",
                        Toast.LENGTH_SHORT).show()
                }
            }
            catch(e : Exception) {
                i(e)
            }
        }

        binding.buttonRegister.setOnClickListener {
            try {
                var email = binding.emailRegister.text.toString()
                var password = binding.passwordRegister.text.toString()
                if(email.isNotEmpty() && password.isNotEmpty()) {
                    createAccount(email, password)

                    binding.emailSignIn.setText("")
                    binding.passwordSignIn.setText("")
                    binding.emailRegister.setText("")
                    binding.passwordRegister.setText("")
                }
                else {
                    Toast.makeText(baseContext, "Please fill in necessary fields",
                        Toast.LENGTH_SHORT).show()
                }
            }
            catch(e : Exception) {
                i(e)
            }
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                    val launcherIntent = Intent(this, RentalCarListActivity::class.java)
                    listIntentLauncher.launch(launcherIntent)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    val launcherIntent = Intent(this, RentalCarListActivity::class.java)
                    listIntentLauncher.launch(launcherIntent)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    i(task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    private val listIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { }

}