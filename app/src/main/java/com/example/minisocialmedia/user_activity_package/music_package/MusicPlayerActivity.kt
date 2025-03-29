package com.example.minisocialmedia.user_activity_package.music_package

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.database_package.AppDatabase
import com.example.minisocialmedia.R

/**
 * Activity для воспроизведения музыкальных треков.
 */
class MusicPlayerActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_PERMISSION_CODE = 1000
        private const val REQUEST_CODE_PICK_AUDIO = 1001
    }

    /**
     * RecyclerView для отображения списка музыкальных треков.
     */
    private lateinit var rvMusicList: RecyclerView

    /**
     * Кнопка для остановки воспроизведения.
     */
    private lateinit var btnStopMusic: Button

    /**
     * Кнопка для выбора музыкального файла.
     */
    private lateinit var btnChooseMusic: Button

    /**
     * Объект MediaPlayer для воспроизведения музыки.
     */
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Адаптер для списка музыкальных треков.
     */
    private lateinit var musicAdapter: MusicAdapter

    /**
     * Список музыкальных треков.
     */
    private var musicList = mutableListOf<Music>()

    /**
     * Вызывается при создании Activity.
     *
     * Инициализирует UI, проверяет разрешения, загружает список музыки и устанавливает обработчики событий.
     *
     * @param savedInstanceState Если Activity пересоздается после предыдущего завершения,
     * этот Bundle содержит данные, которые он ранее сохранил.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        rvMusicList = findViewById(R.id.musicListView)
        btnStopMusic = findViewById(R.id.btnStopMusic)
        btnChooseMusic = findViewById(R.id.btnChooseMusic)

        checkPermissions()
        musicList = loadMusicFiles().toMutableList()

        musicAdapter = MusicAdapter(this, musicList) { music ->
            playMusic(music)
        }
        rvMusicList.layoutManager = LinearLayoutManager(this)
        rvMusicList.adapter = musicAdapter

        btnStopMusic.setOnClickListener { stopMusic() }
        btnChooseMusic.setOnClickListener { openFilePicker() }
    }

    /**
     * Проверяет и запрашивает разрешения на чтение внешнего хранилища.
     */
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_CODE)
        }
    }

    /**
     * Воспроизводит музыкальный трек.
     *
     * @param music Музыкальный трек для воспроизведения.
     */
    private fun playMusic(music: Music) {
        stopMusic()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(music.filePath)
            prepare()
            start()
        }
    }

    /**
     * Останавливает воспроизведение музыкального трека.
     */
    private fun stopMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
                release()
            }
        }
        mediaPlayer = null
    }

    /**
     * Открывает файловый менеджер для выбора музыкального файла.
     */
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/*"
        }
        startActivityForResult(intent, REQUEST_CODE_PICK_AUDIO)
    }

    /**
     * Обрабатывает результат выбора музыкального файла.
     *
     * @param requestCode Код запроса.
     * @param resultCode Результат операции.
     * @param data Intent с данными.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_AUDIO && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                val filePath = getFilePathFromUri(uri)
                val fileName = getFileName(uri)

                if (filePath != null && fileName != null) {
                    saveMusicToDatabase(fileName, filePath)
                    musicList.add(Music(title = fileName, filePath = filePath, duration = 0L))
                    musicAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    /**
     * Получает путь к файлу из URI.
     *
     * @param uri URI файла.
     * @return Путь к файлу или null, если не удалось получить.
     */
    private fun getFilePathFromUri(uri: Uri): String? {
        val cursor = contentResolver.query(uri, arrayOf(MediaStore.Audio.Media.DATA), null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(MediaStore.Audio.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }

    /**
     * Получает имя файла из URI.
     *
     * @param uri URI файла.
     * @return Имя файла или null, если не удалось получить.
     */
    private fun getFileName(uri: Uri): String? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                return it.getString(columnIndex)
            }
        }
        return null
    }

    /**
     * Сохраняет информацию о музыкальном треке в базу данных.
     *
     * @param title Название музыкального трека.
     * @param filePath Путь к файлу музыкального трека.
     */
    private fun saveMusicToDatabase(title: String, filePath: String) {
        val db = AppDatabase.getDatabase(this)
        val musicDao = db.musicDao()

        val newMusic = Music(title = title, filePath = filePath, duration = 0L)
        musicDao.insertMusic(newMusic)
    }

    /**
     * Загружает список музыкальных треков из внешнего хранилища.
     *
     * @return Список музыкальных треков.
     */
    private fun loadMusicFiles(): List<Music> {
        val musicList = mutableListOf<Music>()
        val musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

        if (musicDir.exists() && musicDir.isDirectory) {
            val musicFiles = musicDir.listFiles { file -> file.extension == "mp3" }

            musicFiles?.forEach { file ->
                val music = Music(
                    title = file.nameWithoutExtension,
                    filePath = file.absolutePath,
                    duration = getMusicDuration(file.absolutePath)
                )
                musicList.add(music)
            }
        }
        return musicList
    }

    /**
     * Получает длительность музыкального трека.
     *
     * @param filePath Путь к файлу музыкального трека.
     * @return Длительность музыкального трека в миллисекундах.
     */
    private fun getMusicDuration(filePath: String): Long {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(filePath)
        mediaPlayer.prepare()
        return mediaPlayer.duration.toLong()
    }

    /**
     * Останавливает воспроизведение музыки при уничтожении Activity.
     */
    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }
}