package com.syedmuzani.umbrella.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


/**
 * Created by muz on 2019-02-15.
 */


class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null
        const val TODO_TABLE = "Todo"
        const val COL_NAME = "name"
        const val COL_ID = "_id"

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(TODO_TABLE, true,
               COL_ID to TEXT + PRIMARY_KEY + UNIQUE,
                COL_NAME to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // do nothing
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)