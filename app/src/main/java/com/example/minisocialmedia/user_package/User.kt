package com.example.minisocialmedia.user_package

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

/**
 * Класс данных, представляющий пользователя приложения.
 *
 * @property id Уникальный идентификатор пользователя.
 * @property nickname Имя пользователя (никнейм).
 * @property password Пароль пользователя.
 * @property bio Биография пользователя.
 * @property avatarUri URI аватара пользователя (если есть).
 */
@Entity(
    tableName = "users",
    indices = [Index(value = ["nickname"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nickname: String,
    val password: String,
    val bio: String = "",
    val avatarUri: String = ""
)