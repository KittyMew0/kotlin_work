package com.example.minisocialmedia.user_activity_package.post_package

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс данных, представляющий пост пользователя.
 *
 * @property id Уникальный идентификатор поста.
 * @property author Имя автора поста.
 * @property content Содержание поста.
 * @property imageUri URI изображения, прикрепленного к посту (если есть).
 * @property timestamp Время создания поста в миллисекундах с начала эпохи Unix.
 * @property likes Количество лайков поста.
 */
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String,
    val content: String,
    val imageUri: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    var likes: Int = 0
)