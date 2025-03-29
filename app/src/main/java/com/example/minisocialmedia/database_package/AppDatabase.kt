package com.example.minisocialmedia.database_package

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.minisocialmedia.user_activity_package.comment_package.Comment
import com.example.minisocialmedia.user_activity_package.comment_package.CommentDao
import com.example.minisocialmedia.user_activity_package.follow_package.Follow
import com.example.minisocialmedia.user_activity_package.follow_package.FollowDao
import com.example.minisocialmedia.user_activity_package.messages_package.Message
import com.example.minisocialmedia.user_activity_package.messages_package.MessageDao
import com.example.minisocialmedia.user_activity_package.music_package.Music
import com.example.minisocialmedia.user_activity_package.music_package.MusicDao
import com.example.minisocialmedia.user_activity_package.post_package.Post
import com.example.minisocialmedia.user_activity_package.post_package.PostDao
import com.example.minisocialmedia.user_package.UserDao
import com.example.minisocialmedia.user_package.User

/**
 * Основной класс базы данных приложения, использующий Room.
 *
 * Определяет сущности, используемые в базе данных, и предоставляет методы для доступа к DAO (Data Access Objects).
 *
 * @property userDao DAO для доступа к данным пользователей.
 * @property postDao DAO для доступа к данным постов.
 * @property commentDao DAO для доступа к данным комментариев.
 * @property followDao DAO для доступа к данным подписок.
 * @property messageDao DAO для доступа к данным сообщений.
 * @property musicDao DAO для доступа к данным музыки.
 */
@Database(entities = [User::class, Post::class, Comment::class, Follow::class, Message::class, Music::class], version = 8)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Получает DAO для доступа к данным пользователей.
     *
     * @return UserDao для работы с таблицей пользователей.
     */
    abstract fun userDao(): UserDao

    /**
     * Получает DAO для доступа к данным постов.
     *
     * @return PostDao для работы с таблицей постов.
     */
    abstract fun postDao(): PostDao

    /**
     * Получает DAO для доступа к данным комментариев.
     *
     * @return CommentDao для работы с таблицей комментариев.
     */
    abstract fun commentDao(): CommentDao

    /**
     * Получает DAO для доступа к данным подписок.
     *
     * @return FollowDao для работы с таблицей подписок.
     */
    abstract fun followDao(): FollowDao

    /**
     * Получает DAO для доступа к данным сообщений.
     *
     * @return MessageDao для работы с таблицей сообщений.
     */
    abstract fun messageDao(): MessageDao

    /**
     * Получает DAO для доступа к данным музыки.
     *
     * @return MusicDao для работы с таблицей музыки.
     */
    abstract fun musicDao(): MusicDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Получает экземпляр базы данных приложения.
         *
         * Если экземпляр базы данных не существует, создает его.
         *
         * @param context Контекст приложения.
         * @return Экземпляр AppDatabase.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build() // Внимание: allowMainThreadQueries() только для разработки.
                INSTANCE = instance
                instance
            }
        }
    }
}