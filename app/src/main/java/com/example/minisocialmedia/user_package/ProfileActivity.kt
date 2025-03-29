package com.example.minisocialmedia.user_package

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minisocialmedia.database_package.AppDatabase
import com.example.minisocialmedia.R
import com.example.minisocialmedia.user_activity_package.follow_package.Follow
import com.example.minisocialmedia.user_activity_package.follow_package.FollowDao

/**
 * Activity для отображения и редактирования профиля пользователя.
 */
class ProfileActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * DAO для работы с пользователями.
     */
    private lateinit var userDao: UserDao

    /**
     * SharedPreferences для хранения данных пользователя.
     */
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * ImageView для отображения аватара пользователя.
     */
    private lateinit var ivAvatar: ImageView

    /**
     * EditText для ввода био пользователя.
     */
    private lateinit var etBio: EditText

    /**
     * Кнопка для изменения аватара.
     */
    private lateinit var btnChangeAvatar: Button

    /**
     * Кнопка для сохранения изменений профиля.
     */
    private lateinit var btnSaveProfile: Button

    /**
     * URI выбранного аватара.
     */
    private var avatarUri: Uri? = null

    /**
     * Код запроса для выбора изображения.
     */
    private val PICK_IMAGE_REQUEST = 1

    /**
     * DAO для работы с подписками.
     */
    private lateinit var followDao: FollowDao

    /**
     * Кнопка для подписки/отписки.
     */
    private lateinit var btnFollowUnfollow: Button

    /**
     * Идентификатор текущего пользователя.
     */
    private var currentUserId = 1

    /**
     * Идентификатор просматриваемого пользователя.
     */
    private var viewedUserId = 2

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует UI, получает DAO и SharedPreferences, загружает данные пользователя,
     * устанавливает обработчики событий.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        ivAvatar = findViewById(R.id.ivAvatar)
        etBio = findViewById(R.id.etBio)
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar)
        btnSaveProfile = findViewById(R.id.btnSaveProfile)

        val nickname = sharedPreferences.getString("loggedInUser", "") ?: ""

        if (nickname.isNotEmpty()) {
            val user = userDao.getUserByNickname(nickname)
            user?.let {
                etBio.setText(it.bio)
                if (it.avatarUri.isNotEmpty()) {
                    ivAvatar.setImageURI(Uri.parse(it.avatarUri))
                }
            }
        }

        btnChangeAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnSaveProfile.setOnClickListener {
            val bio = etBio.text.toString()
            val avatarUriString = avatarUri?.toString() ?: ""

            if (nickname.isNotEmpty()) {
                userDao.updateProfile(nickname, bio, avatarUriString)
                Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
            }
        }

        db = AppDatabase.getDatabase(this)
        followDao = db.followDao()

        btnFollowUnfollow = findViewById(R.id.btnFollowUnfollow)

        checkFollowStatus()

        btnFollowUnfollow.setOnClickListener {
            toggleFollowStatus()
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
                avatarUri = uri
                ivAvatar.setImageURI(uri)
            }
        }
    }

    /**
     * Проверяет статус подписки и устанавливает текст кнопки.
     */
    private fun checkFollowStatus() {
        val follows = followDao.getFollowing(currentUserId)
        val isFollowing = follows.any { it.followedUserId == viewedUserId }

        if (isFollowing) {
            btnFollowUnfollow.text = "Unfollow"
        } else {
            btnFollowUnfollow.text = "Follow"
        }
    }

    /**
     * Переключает статус подписки и обновляет текст кнопки.
     */
    private fun toggleFollowStatus() {
        val follows = followDao.getFollowing(currentUserId)
        val isFollowing = follows.any { it.followedUserId == viewedUserId }

        if (isFollowing) {
            val follow = follows.find { it.followedUserId == viewedUserId }
            follow?.let { followDao.unfollowUser(it) }
            btnFollowUnfollow.text = "Follow"
        } else {
            val follow = Follow(userId = currentUserId, followedUserId = viewedUserId)
            followDao.followUser(follow)
            btnFollowUnfollow.text = "Unfollow"
        }
    }
}