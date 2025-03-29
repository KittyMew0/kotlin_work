package com.example.minisocialmedia.user_activity_package.messages_package

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO для работы с сообщениями.
 */
@Dao
interface MessageDao {

    /**
     * Вставляет новое сообщение в базу данных.
     *
     * @param message Сообщение для вставки.
     */
    @Insert
    fun insertMessage(message: Message)

    /**
     * Получает список сообщений между двумя пользователями, отсортированных по времени отправки.
     *
     * @param user1 Идентификатор первого пользователя.
     * @param user2 Идентификатор второго пользователя.
     * @return Список сообщений между пользователями.
     */
    @Query("SELECT * FROM messages WHERE (senderId = :user1 AND receiverId = :user2) OR (senderId = :user2 AND receiverId = :user1) ORDER BY timestamp ASC")
    fun getChatMessages(user1: Int, user2: Int): List<Message>
}