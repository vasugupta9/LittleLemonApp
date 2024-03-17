package com.littlelemon.littlelemonappcoursera

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.serialization.SerialName

@Entity
data class MenuItemDb(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val category: String,
    val imgUrl: String
)

@Dao
interface MenuItemDao{
    @Query("SELECT * FROM MenuItemDb")
    fun getAll(): LiveData<List<MenuItemDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllItems(items: List<MenuItemDb>)
}

@Database(entities = [MenuItemDb::class], version=1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun menuItemDao() : MenuItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()//.fallbackToDestructiveMigration().build() // use if required
                INSTANCE = instance
                return instance
            }
        }
    }
}