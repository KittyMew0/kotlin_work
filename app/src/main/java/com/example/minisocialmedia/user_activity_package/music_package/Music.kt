package com.example.minisocialmedia.user_activity_package.music_package

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс данных, представляющий музыкальный трек.
 *
 * @property id Уникальный идентификатор трека.
 * @property title Название трека.
 * @property filePath Путь к файлу трека.
 * @property duration Длительность трека в миллисекундах.
 */
@Entity(tableName = "music")
data class Music(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val filePath: String,
    val duration: Long
)