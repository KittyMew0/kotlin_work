package com.example.minisocialmedia.user_activity_package.follow_package

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO для работы с подписками.
 */
@Dao
interface FollowDao {

    /**
     * Добавляет новую подписку.
     *
     * @param follow Объект подписки для добавления.
     */
    @Insert
    fun followUser(follow: Follow)

    /**
     * Удаляет подписку.
     *
     * @param follow Объект подписки для удаления.
     */
    @Delete
    fun unfollowUser(follow: Follow)

    /**
     * Получает список подписок пользователя.
     *
     * @param userId Идентификатор пользователя, чьи подписки нужно получить.
     * @return Список подписок пользователя.
     */
    @Query("SELECT * FROM follows WHERE userId = :userId")
    fun getFollowing(userId: Int): List<Follow>
}