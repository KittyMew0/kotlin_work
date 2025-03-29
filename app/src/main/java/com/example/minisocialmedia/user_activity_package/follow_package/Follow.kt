package com.example.minisocialmedia.user_activity_package.follow_package

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс данных, представляющий связь подписки между пользователями.
 *
 * @property id Уникальный идентификатор подписки.
 * @property userId Идентификатор пользователя, который подписался.
 * @property followedUserId Идентификатор пользователя, на которого подписались.
 */
@Entity(tableName = "follows")
data class Follow(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val followedUserId: Int
)