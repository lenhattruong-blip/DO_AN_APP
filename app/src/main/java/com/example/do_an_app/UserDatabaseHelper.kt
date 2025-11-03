package com.example.do_an_app

// File: UserDatabaseHelper.kt
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, "app_users.db", null, 1) {

    companion object {
        private const val TABLE_USERS = "users"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PHONE = "phone"
        private const val KEY_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_NAME TEXT,
                $KEY_EMAIL TEXT UNIQUE, 
                $KEY_PHONE TEXT,
                $KEY_PASSWORD TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    /**
     * Hàm dùng cho Đăng Ký
     * Thêm một user mới vào bảng
     */
    fun insertUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, user.name)
            put(KEY_EMAIL, user.email)
            put(KEY_PHONE, user.phone)
            put(KEY_PASSWORD, user.password)
        }
        // Chèn hàng mới, trả về -1L nếu lỗi (ví dụ: email đã tồn tại)
        val result = db.insert(TABLE_USERS, null, values)
        return result
    }

    /**
     * Hàm dùng cho Đăng Ký
     * Kiểm tra xem email đã tồn tại trong CSDL chưa
     */
    fun checkEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val emailExists = cursor.count > 0
        cursor.close()
        return emailExists
    }

    /**
     * Hàm dùng cho Đăng Nhập
     * Kiểm tra email và mật khẩu có khớp không
     */
    fun checkUserLogin(email: String, password: String): Boolean {
        val db = this.readableDatabase
        // Dùng rawQuery giống như file Lab 7
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_EMAIL = ? AND $KEY_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val userExists = cursor.count > 0 // Nếu > 0 tức là tìm thấy user
        cursor.close()
    //    db.close()
        return userExists
    }
}