package com.example.minisocialmedia.user_package

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO для работы с пользователями.
 */
@Dao
interface UserDao {

    /**
     * Вставляет нового пользователя в базу данных или заменяет существующего, если никнейм совпадает.
     *
     * @param user Пользователь для вставки или замены.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    /**
     * Получает пользователя по никнейму и паролю.
     *
     * @param nickname Никнейм пользователя.
     * @param password Пароль пользователя.
     * @return Пользователь, если найден, иначе null.
     */
    @Query("SELECT * FROM users WHERE nickname = :nickname AND password = :password")
    fun getUser(nickname: String, password: String): User?

    /**
     * Получает пользователя по никнейму.
     *
     * @param nickname Никнейм пользователя.
     * @return Пользователь, если найден, иначе null.
     */
    @Query("SELECT * FROM users WHERE nickname = :nickname")
    fun getUserByNickname(nickname: String): User?

    /**
     * Обновляет профиль пользователя.
     *
     * @param nickname Никнейм пользователя.
     * @param bio Биография пользователя.
     * @param avatarUri URI аватара пользователя.
     */
    @Query("UPDATE users SET bio = :bio, avatarUri = :avatarUri WHERE nickname = :nickname")
    fun updateProfile(nickname: String, bio: String, avatarUri: String)

    /**
     * Получает список пользователей по их идентификаторам.
     *
     * @param userIds Список идентификаторов пользователей.
     * @return Список пользователей.
     */
    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun getUsersByIds(userIds: List<Int>): List<User>
}