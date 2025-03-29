package com.example.minisocialmedia.user_activity_package.comment_package

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.database_package.AppDatabase
import com.example.minisocialmedia.R

/**
 * Activity для отображения и добавления комментариев к посту.
 *
 * Позволяет пользователям просматривать комментарии к посту и добавлять новые.
 */
class CommentActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * DAO для работы с комментариями.
     */
    private lateinit var commentDao: CommentDao

    /**
     * Поле ввода для добавления нового комментария.
     */
    private lateinit var etComment: EditText

    /**
     * Кнопка для отправки нового комментария.
     */
    private lateinit var btnSubmitComment: Button

    /**
     * RecyclerView для отображения списка комментариев.
     */
    private lateinit var rvComments: RecyclerView

    /**
     * Идентификатор поста, к которому относятся комментарии.
     */
    private var postId: Int = -1

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует базу данных, получает DAO для работы с комментариями,
     * устанавливает обработчики событий и загружает комментарии.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        db = AppDatabase.getDatabase(this)
        commentDao = db.commentDao()

        etComment = findViewById(R.id.etComment)
        btnSubmitComment = findViewById(R.id.btnSubmitComment)
        rvComments = findViewById(R.id.rvComments)

        postId = intent.getIntExtra("postId", -1)

        btnSubmitComment.setOnClickListener {
            val commentText = etComment.text.toString()
            if (commentText.isNotEmpty()) {
                val comment = Comment(postId = postId, author = "User", content = commentText)
                commentDao.insertComment(comment)
                etComment.text.clear()
                loadComments()
            }
        }

        loadComments()
    }

    /**
     * Загружает комментарии для заданного поста и отображает их в RecyclerView.
     */
    private fun loadComments() {
        val comments = commentDao.getCommentsForPost(postId)
        rvComments.layoutManager = LinearLayoutManager(this)
        rvComments.adapter = CommentAdapter(comments)
    }
}