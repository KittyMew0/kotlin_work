package com.example.minisocialmedia.user_activity_package.messages_package

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.R
import com.example.minisocialmedia.database_package.AppDatabase

/**
 * Activity для отображения чата между двумя пользователями.
 */
class ChatActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * DAO для работы с сообщениями.
     */
    private lateinit var messageDao: MessageDao

    /**
     * RecyclerView для отображения сообщений.
     */
    private lateinit var rvMessages: RecyclerView

    /**
     * Поле ввода для отправки нового сообщения.
     */
    private lateinit var etMessage: EditText

    /**
     * Кнопка для отправки сообщения.
     */
    private lateinit var btnSend: Button

    /**
     * Адаптер для отображения сообщений.
     */
    private lateinit var adapter: MessageAdapter

    /**
     * Идентификатор отправителя сообщений.
     */
    private var senderId: Int = 0

    /**
     * Идентификатор получателя сообщений.
     */
    private var receiverId: Int = 0

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует базу данных, получает DAO для работы с сообщениями,
     * устанавливает RecyclerView, EditText, Button и загружает сообщения.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        db = AppDatabase.getDatabase(this)
        messageDao = db.messageDao()

        rvMessages = findViewById(R.id.rvMessages)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)

        senderId = intent.getIntExtra("SENDER_ID", 0)
        receiverId = intent.getIntExtra("RECEIVER_ID", 0)

        loadMessages()

        btnSend.setOnClickListener {
            val text = etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                val message = Message(0, senderId, receiverId, text, System.currentTimeMillis())
                messageDao.insertMessage(message)
                etMessage.text.clear()
                loadMessages()
            }
        }
    }

    /**
     * Загружает сообщения между отправителем и получателем и отображает их в RecyclerView.
     */
    private fun loadMessages() {
        val messages = messageDao.getChatMessages(senderId, receiverId)
        adapter = MessageAdapter(messages)
        rvMessages.layoutManager = LinearLayoutManager(this)
        rvMessages.adapter = adapter
    }
}