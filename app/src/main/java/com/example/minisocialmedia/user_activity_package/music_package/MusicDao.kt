package com.example.minisocialmedia.user_activity_package.music_package

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO для работы с музыкальными треками.
 */
@Dao
interface MusicDao {

    /**
     * Вставляет новый музыкальный трек в базу данных.
     *
     * @param music Музыкальный трек для вставки.
     */
    @Insert
    fun insertMusic(music: Music)

    /**
     * Получает список всех музыкальных треков, отсортированных по названию.
     *
     * @return Список всех музыкальных треков.
     */
    @Query("SELECT * FROM music ORDER BY title ASC")
    fun getAllMusic(): List<Music>
}