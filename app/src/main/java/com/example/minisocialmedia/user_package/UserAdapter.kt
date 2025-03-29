package com.example.minisocialmedia.user_package

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.R

/**
 * Адаптер для отображения списка пользователей в RecyclerView.
 *
 * @param users Список пользователей для отображения.
 */
class UserAdapter(private val users: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    /**
     * ViewHolder для элементов списка пользователей.
     *
     * @param view Представление элемента списка пользователей.
     */
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName: TextView = view.findViewById(R.id.tvUserName)
    }

    /**
     * Создает новый ViewHolder для элемента списка пользователей.
     *
     * @param parent ViewGroup, в который будет добавлен ViewHolder.
     * @param viewType Тип представления нового представления.
     * @return Новый ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    /**
     * Привязывает данные пользователя к ViewHolder.
     *
     * @param holder ViewHolder, к которому нужно привязать данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.tvUserName.text = user.nickname
    }

    /**
     * Возвращает количество элементов в списке пользователей.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount() = users.size
}