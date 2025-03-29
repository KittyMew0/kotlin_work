package com.example.minisocialmedia.user_activity_package.post_package

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO для работы с постами.
 */
@Dao
interface PostDao {

    /**
     * Вставляет новый пост в базу данных.
     *
     * @param post Пост для вставки.
     */
    @Insert
    fun insertPost(post: Post)

    /**
     * Получает список всех постов, отсортированных по времени создания в обратном порядке.
     *
     * @return Список всех постов.
     */
    @Query("SELECT * FROM posts ORDER BY timestamp DESC")
    fun getAllPosts(): List<Post>
}