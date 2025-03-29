package com.example.minisocialmedia.user_activity_package.music_package

import android.app.Activity
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minisocialmedia.R
import com.example.minisocialmedia.database_package.AppDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Activity для выбора и сохранения музыкальных файлов.
 */
class MusicActivity : AppCompatActivity() {

    /**
     * Экземпляр базы данных приложения.
     */
    private lateinit var db: AppDatabase

    /**
     * Кнопка для выбора музыкального файла.
     */
    private lateinit var btnSelectMusic: Button

    /**
     * Код запроса для выбора аудиофайла.
     */
    private val PICK_AUDIO_REQUEST = 1

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует базу данных, устанавливает обработчик нажатия на кнопку выбора музыки.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        db = AppDatabase.getDatabase(this)

        btnSelectMusic = findViewById(R.id.btnSelectMusic)
        btnSelectMusic.setOnClickListener {
            selectAudioFile()
        }
    }

    /**
     * Открывает Intent для выбора аудиофайла.
     */
    private fun selectAudioFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "audio/*"
        startActivityForResult(intent, PICK_AUDIO_REQUEST)
    }

    /**
     * Обрабатывает результат выбора аудиофайла.
     *
     * @param requestCode Код запроса.
     * @param resultCode Результат операции.
     * @param data Intent с данными.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                saveMusicFile(uri)
            }
        }
    }

    /**
     * Сохраняет выбранный музыкальный файл в локальное хранилище и добавляет информацию о нем в базу данных.
     *
     * @param uri URI выбранного аудиофайла.
     */
    private fun saveMusicFile(uri: Uri) {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        if (inputStream != null) {
            val fileName = getFileName(uri)
            val file = File(filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            val duration = getAudioDuration(file.absolutePath)

            val music = Music(title = fileName, filePath = file.absolutePath, duration = duration)
            db.musicDao().insertMusic(music)

            Toast.makeText(this, "Music saved!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Получает имя файла из URI.
     *
     * @param uri URI файла.
     * @return Имя файла.
     */
    private fun getFileName(uri: Uri): String {
        var name = "audio_file.mp3"
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return name
    }

    /**
     * Получает длительность аудиофайла.
     *
     * @param filePath Путь к аудиофайлу.
     * @return Длительность аудиофайла в миллисекундах.
     */
    private fun getAudioDuration(filePath: String): Long {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(filePath)
        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        retriever.release()
        return durationStr?.toLong() ?: 0
    }
}