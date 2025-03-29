package com.example.minisocialmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.database_package.AppDatabase
import com.example.minisocialmedia.log_activity_package.LoginActivity
import com.example.minisocialmedia.user_activity_package.post_package.CreatePostActivity
import com.example.minisocialmedia.user_activity_package.post_package.PostAdapter
import com.example.minisocialmedia.user_activity_package.post_package.PostDao
import com.example.minisocialmedia.user_package.ProfileActivity

/**
 * Activity для отображения основной ленты постов.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * DAO для работы с постами.
     */
    private lateinit var postDao: PostDao

    /**
     * RecyclerView для отображения списка постов.
     */
    private lateinit var rvPosts: RecyclerView

    /**
     * Кнопка для создания нового поста.
     */
    private lateinit var btnCreatePost: Button

    /**
     * Кнопка для выхода из аккаунта.
     */
    private lateinit var btnLogout: Button

    /**
     * Кнопка для перехода в профиль.
     */
    private lateinit var btnProfile: Button

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует UI, получает DAO, устанавливает обработчики событий и загружает посты.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)
        postDao = db.postDao()

        rvPosts = findViewById(R.id.rvPosts)
        btnCreatePost = findViewById(R.id.btnCreatePost)
        btnLogout = findViewById(R.id.btnLogout)
        btnProfile = findViewById(R.id.btnProfile)

        btnCreatePost.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        loadPosts()
    }

    /**
     * Вызывается при возобновлении Activity.
     *
     * Обновляет список постов.
     */
    override fun onResume() {
        super.onResume()
        loadPosts()
    }

    /**
     * Загружает и отображает список постов.
     */
    private fun loadPosts() {
        val posts = postDao.getAllPosts()
        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = PostAdapter(posts, postDao)
    }
}