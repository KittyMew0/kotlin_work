package com.example.minisocialmedia.user_activity_package.music_package

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.R

/**
 * Адаптер для отображения списка музыкальных треков в RecyclerView.
 *
 * @param context Контекст приложения.
 * @param musicList Список музыкальных треков для отображения.
 * @param onItemClick Функция, вызываемая при нажатии на элемент списка.
 */
class MusicAdapter(
    private val context: Context,
    private val musicList: List<Music>,
    private val onItemClick: (Music) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    /**
     * ViewHolder для элементов списка музыкальных треков.
     *
     * @param view Представление элемента списка музыкальных треков.
     */
    class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvMusicTitle)
        val tvDuration: TextView = view.findViewById(R.id.tvMusicDuration)
    }

    /**
     * Создает новый ViewHolder для элемента списка музыкальных треков.
     *
     * @param parent ViewGroup, в который будет добавлен ViewHolder.
     * @param viewType Тип представления нового представления.
     * @return Новый ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    /**
     * Привязывает данные музыкального трека к ViewHolder.
     *
     * @param holder ViewHolder, к которому нужно привязать данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]
        holder.tvTitle.text = music.title
        holder.tvDuration.text = formatDuration(music.duration)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Playing: ${music.title}", Toast.LENGTH_SHORT).show()
            onItemClick(music)
        }
    }

    /**
     * Возвращает количество элементов в списке музыкальных треков.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount() = musicList.size

    /**
     * Форматирует длительность музыкального трека в строку "MM:SS".
     *
     * @param duration Длительность музыкального трека в миллисекундах.
     * @return Форматированная строка длительности.
     */
    private fun formatDuration(duration: Long): String {
        val minutes = duration / 60000
        val seconds = (duration % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }
}