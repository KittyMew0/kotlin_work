package com.example.minisocialmedia.user_activity_package.comment_package

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.R

/**
 * Адаптер для отображения списка комментариев в RecyclerView.
 *
 * @param comments Список комментариев для отображения.
 */
class CommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    /**
     * ViewHolder для элементов списка комментариев.
     *
     * @param view Представление элемента списка комментариев.
     */
    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val tvComment: TextView = view.findViewById(R.id.tvComment)
    }

    /**
     * Создает новый ViewHolder для элемента списка комментариев.
     *
     * @param parent ViewGroup, в который будет добавлен ViewHolder.
     * @param viewType Тип представления нового представления.
     * @return Новый ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    /**
     * Привязывает данные комментария к ViewHolder.
     *
     * @param holder ViewHolder, к которому нужно привязать данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.tvAuthor.text = comment.author
        holder.tvComment.text = comment.content
    }

    /**
     * Возвращает количество элементов в списке комментариев.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount() = comments.size
}