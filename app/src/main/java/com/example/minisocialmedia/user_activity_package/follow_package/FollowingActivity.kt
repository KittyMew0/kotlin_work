package com.example.minisocialmedia.user_activity_package.follow_package

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.database_package.AppDatabase
import com.example.minisocialmedia.R
import com.example.minisocialmedia.user_package.UserAdapter

/**
 * Activity для отображения списка пользователей, на которых подписан текущий пользователь.
 */
class FollowingActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * DAO для работы с подписками.
     */
    private lateinit var followDao: FollowDao

    /**
     * RecyclerView для отображения списка пользователей, на которых подписан текущий пользователь.
     */
    private lateinit var rvFollowing: RecyclerView

    /**
     * Идентификатор текущего пользователя.
     */
    private var currentUserId = 1

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует базу данных, получает DAO для работы с подписками,
     * устанавливает RecyclerView и загружает список подписок.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)

        db = AppDatabase.getDatabase(this)
        followDao = db.followDao()

        rvFollowing = findViewById(R.id.rvFollowing)

        loadFollowing()
    }

    /**
     * Загружает список пользователей, на которых подписан текущий пользователь, и отображает их в RecyclerView.
     */
    private fun loadFollowing() {
        val follows = followDao.getFollowing(currentUserId)
        val followedUserIds = follows.map { it.followedUserId }

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        val users = userDao.getUsersByIds(followedUserIds)

        rvFollowing.layoutManager = LinearLayoutManager(this)
        rvFollowing.adapter = UserAdapter(users)
    }
}