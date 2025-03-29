package com.example.minisocialmedia.log_activity_package

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.minisocialmedia.R

/**
 * Activity для экрана регистрации пользователя.
 *
 * Устанавливает макет для регистрации и обрабатывает вставки системных окон.
 */
class SignupActivity : AppCompatActivity() {

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует макет регистрации и устанавливает обработчик для обработки
     * вставок системных окон, чтобы контент не перекрывался системными элементами.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Включает отображение контента до краев экрана
        setContentView(R.layout.activity_signup)

        // Устанавливает слушатель для обработки вставок системных окон (например, статус-бар, навигационная панель)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Устанавливает отступы для корневого вида, чтобы контент не перекрывался системными элементами
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Возвращает исходные вставки
        }
    }
}