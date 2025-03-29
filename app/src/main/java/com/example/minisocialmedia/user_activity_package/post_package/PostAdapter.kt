package com.example.minisocialmedia.user_activity_package.post_package

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialmedia.R
import com.example.minisocialmedia.user_activity_package.comment_package.CommentActivity

/**
 * Адаптер для отображения списка постов в RecyclerView.
 *
 * @param posts Список постов для отображения.
 * @param postDao DAO для работы с постами.
 */
class PostAdapter(
    private val posts: List<Post>,
    private val postDao: PostDao
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    /**
     * ViewHolder для элементов списка постов.
     *
     * @param view Представление элемента списка постов.
     */
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val tvContent: TextView = view.findViewById(R.id.tvContent)
        val ivPostImage: ImageView = view.findViewById(R.id.ivPostImage)
        val tvLikes: TextView = view.findViewById(R.id.tvLikes)
        val btnLike: Button = view.findViewById(R.id.btnLike)
        val btnComment: Button = view.findViewById(R.id.btnComment)
    }

    /**
     * Создает новый ViewHolder для элемента списка постов.
     *
     * @param parent ViewGroup, в который будет добавлен ViewHolder.
     * @param viewType Тип представления нового представления.
     * @return Новый ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    /**
     * Привязывает данные поста к ViewHolder.
     *
     * @param holder ViewHolder, к которому нужно привязать данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        holder.tvAuthor.text = post.author
        holder.tvContent.text = post.content
        holder.tvLikes.text = "${post.likes} Likes"

        if (post.imageUri.isNotEmpty()) {
            holder.ivPostImage.visibility = View.VISIBLE
            holder.ivPostImage.setImageURI(Uri.parse(post.imageUri))
        } else {
            holder.ivPostImage.visibility = View.GONE
        }

        holder.btnLike.setOnClickListener {
            post.likes += 1
            postDao.insertPost(post)
            holder.tvLikes.text = "${post.likes} Likes"
        }

        holder.btnComment.setOnClickListener {
            val intent = Intent(holder.itemView.context, CommentActivity::class.java)
            intent.putExtra("postId", post.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    /**
     * Возвращает количество элементов в списке постов.
     *
     * @return Количество элементов в списке.
     */
    override fun getItemCount() = posts.size
}