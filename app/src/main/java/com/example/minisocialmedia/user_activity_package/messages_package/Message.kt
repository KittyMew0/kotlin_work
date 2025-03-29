package com.example.minisocialmedia.user_activity_package.messages_package

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс данных, представляющий сообщение между пользователями.
 *
 * @property id Уникальный идентификатор сообщения.
 * @property senderId Идентификатор отправителя сообщения.
 * @property receiverId Идентификатор получателя сообщения.
 * @property content Содержание сообщения.
 * @property timestamp Время отправки сообщения в миллисекундах с начала эпохи Unix.
 */
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val senderId: Int,
    val receiverId: Int,
    val content: String,
    val timestamp: Long
)