package com.example.minisocialmedia.log_activity_package

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minisocialmedia.database_package.AppDatabase
import com.example.minisocialmedia.MainActivity
import com.example.minisocialmedia.R
import com.example.minisocialmedia.user_package.UserDao

/**
 * Activity для экрана входа в приложение.
 *
 * Позволяет пользователям вводить имя пользователя и пароль для входа в приложение.
 * Проверяет учетные данные в базе данных и перенаправляет на MainActivity при успешном входе.
 */
class LoginActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * DAO для доступа к данным пользователей.
     */
    private lateinit var userDao: UserDao

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует базу данных, получает DAO для работы с пользователями,
     * устанавливает обработчик нажатия на кнопку входа.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        val etNickname = findViewById<EditText>(R.id.etLoginNickname)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val nickname = etNickname.text.toString()
            val password = etPassword.text.toString()

            val user = userDao.getUser(nickname, password)
            if (user != null) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}