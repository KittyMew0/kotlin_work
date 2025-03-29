package com.example.minisocialmedia.user_activity_package.post_package

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minisocialmedia.database_package.AppDatabase
import com.example.minisocialmedia.R

/**
 * Activity для создания нового поста.
 */
class CreatePostActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * DAO для работы с постами.
     */
    private lateinit var postDao: PostDao

    /**
     * SharedPreferences для хранения данных пользователя.
     */
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Поле ввода для содержания поста.
     */
    private lateinit var etPostContent: EditText

    /**
     * ImageView для отображения выбранного изображения поста.
     */
    private lateinit var ivPostImage: ImageView

    /**
     * Кнопка для выбора изображения поста.
     */
    private lateinit var btnSelectImage: Button

    /**
     * Кнопка для публикации поста.
     */
    private lateinit var btnPost: Button

    /**
     * URI выбранного изображения поста.
     */
    private var imageUri: Uri? = null

    /**
     * Код запроса для выбора изображения.
     */
    private val PICK_IMAGE_REQUEST = 1

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует UI, получает DAO и SharedPreferences, устанавливает обработчики событий.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        db = AppDatabase.getDatabase(this)
        postDao = db.postDao()
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        etPostContent = findViewById(R.id.etPostContent)
        ivPostImage = findViewById(R.id.ivPostImage)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnPost = findViewById(R.id.btnPost)

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnPost.setOnClickListener {
            val content = etPostContent.text.toString()
            val author = sharedPreferences.getString("loggedInUser", "") ?: ""

            if (content.isNotEmpty() && author.isNotEmpty()) {
                val post = Post(author = author, content = content, imageUri = imageUri?.toString() ?: "")
                postDao.insertPost(post)
                Toast.makeText(this, "Posted!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Enter some text", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Обрабатывает результат выбора изображения.
     *
     * @param requestCode Код запроса.
     * @param resultCode Результат операции.
     * @param data Intent с данными.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                imageUri = uri
                ivPostImage.visibility = View.VISIBLE
                ivPostImage.setImageURI(uri)
            }
        }
    }
}