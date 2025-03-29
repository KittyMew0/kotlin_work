package com.example.minisocialmedia.user_activity_package.messages_package

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.R

/**
 * Адаптер для отображения списка сообщений в RecyclerView.
 *
 * @param messages Список сообщений для отображения.
 */
class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    /**
     * ViewHolder для элементов списка сообщений.
     *
     * @param view Представление элемента списка сообщений.
     */
    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
    }

    /**
     * Создает новый ViewHolder для элемента списка сообщений.
     *
     * @param parent ViewGroup, в который будет добавлен ViewHolder.
     * @param viewType Тип представления нового представления.
     * @return Новый ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    /**
     * Привязывает данные сообщения к ViewHolder.
     *
     * @param holder ViewHolder, к которому нужно привязать данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.tvMessage.text = message.content
    }

    /**
     * Возвращает количество элементов в списке сообщений.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount() = messages.size
}