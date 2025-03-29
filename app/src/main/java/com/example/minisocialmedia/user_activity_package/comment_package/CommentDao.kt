package com.example.minisocialmedia.user_activity_package.comment_package

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO для работы с комментариями.
 */
@Dao
interface CommentDao {

    /**
     * Вставляет новый комментарий в базу данных.
     *
     * @param comment Комментарий для вставки.
     */
    @Insert
    fun insertComment(comment: Comment)

    /**
     * Получает список комментариев для заданного поста, отсортированных по времени создания.
     *
     * @param postId Идентификатор поста, для которого нужно получить комментарии.
     * @return Список комментариев для поста.
     */
    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY timestamp ASC")
    fun getCommentsForPost(postId: Int): List<Comment>
}