package com.example.minisocialmedia.user_activity_package.comment_package

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс данных, представляющий комментарий к посту.
 *
 * @property id Уникальный идентификатор комментария.
 * @property postId Идентификатор поста, к которому относится комментарий.
 * @property author Имя автора комментария.
 * @property content Содержание комментария.
 * @property timestamp Время создания комментария в миллисекундах с начала эпохи Unix.
 */
@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val author: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)