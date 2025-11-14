package com.example.do_an_app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "FlightApp.db", null, 1) {
    companion object {
        private const val TABLE_USERS = "users"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PHONE = "phone"
        private const val KEY_PASSWORD = "password"
        private const val TABLE_DATVE = "lich_su_dat_ve"
        private const val COL_DATVE_MADATVE = "maDatVe"
        private const val COL_DATVE_USER_ID = "nguoiDungId"
        private const val COL_DATVE_FROM = "chuyenBayTu"
        private const val COL_DATVE_TO = "chuyenBayDen"
        private const val COL_DATVE_DATE = "ngayDi"
        private const val COL_DATVE_DEPTIME = "gioDi"
        private const val COL_DATVE_AIRLINE = "hangBay"
        private const val COL_DATVE_FLIGHTCODE = "maChuyenBay"
        private const val COL_DATVE_GIAVE = "giaVe"
        private const val COL_DATVE_STATUS = "trangThai"
        private const val TABLE_HANHKHACH = "hanh_khach"
        private const val COL_HK_ID = "hanhKhachId"
        private const val COL_HK_USER_ID = "nguoiDungId"
        private const val COL_HK_HO = "ho"
        private const val COL_HK_TEN = "ten"
        private const val COL_HK_NGAYSINH = "ngaySinh"
        private const val COL_HK_QUOCTICH = "quocTich"
        private const val COL_HK_CHUCDANH = "chucDanh"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("PRAGMA foreign_keys=ON;")
        val CREATE_TABLE_USER = """
            CREATE TABLE $TABLE_USERS (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_NAME TEXT,
                $KEY_EMAIL TEXT UNIQUE, 
                $KEY_PHONE TEXT,
                $KEY_PASSWORD TEXT
            )
        """.trimIndent()
        val CREATE_TABLE_DATVE = """
            CREATE TABLE $TABLE_DATVE (
                $COL_DATVE_MADATVE TEXT PRIMARY KEY,
                $COL_DATVE_USER_ID INTEGER NOT NULL,
                $COL_DATVE_FROM TEXT,
                $COL_DATVE_TO TEXT,
                $COL_DATVE_DATE TEXT,
                $COL_DATVE_DEPTIME TEXT,
                $COL_DATVE_AIRLINE TEXT,
                $COL_DATVE_FLIGHTCODE TEXT,
                $COL_DATVE_GIAVE REAL,
                $COL_DATVE_STATUS TEXT,
                FOREIGN KEY($COL_DATVE_USER_ID) REFERENCES $TABLE_USERS($KEY_ID) ON DELETE CASCADE
            )
        """.trimIndent()
        val CREATE_TABLE_HANHKHACH = """
            CREATE TABLE $TABLE_HANHKHACH (
                $COL_HK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_HK_USER_ID INTEGER NOT NULL,
                $COL_HK_HO TEXT,
                $COL_HK_TEN TEXT,
                $COL_HK_NGAYSINH TEXT,
                $COL_HK_QUOCTICH TEXT,
                $COL_HK_CHUCDANH TEXT,
                FOREIGN KEY($COL_HK_USER_ID) REFERENCES $TABLE_USERS($KEY_ID) ON DELETE CASCADE
            )
        """.trimIndent()
        db?.execSQL(CREATE_TABLE_USER)
        db?.execSQL(CREATE_TABLE_DATVE)
        db?.execSQL(CREATE_TABLE_HANHKHACH)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DATVE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HANHKHACH")
        onCreate(db)
    }

    fun insertUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, user.name)
            put(KEY_EMAIL, user.email)
            put(KEY_PHONE, user.phone)
            put(KEY_PASSWORD, user.password)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result
    }
    fun insertHanhKhach(hanhKhach: HanhKhach): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_HK_USER_ID, hanhKhach.userId)
            put(COL_HK_HO, hanhKhach.ho)
            put(COL_HK_TEN, hanhKhach.ten)
            put(COL_HK_NGAYSINH, hanhKhach.ngaySinh)
            put(COL_HK_QUOCTICH, hanhKhach.quocTich)
            put(COL_HK_CHUCDANH, hanhKhach.chucDanh)
        }
        val result = db.insert(TABLE_HANHKHACH, null, values)
        db.close()
        return result
    }
    fun checkEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val emailExists = cursor.count > 0
        cursor.close()
        db.close()
        return emailExists
    }
    fun checkUserLoginAndGetId(email: String, password: String): Int {
        val db = this.readableDatabase
        val query = "SELECT $KEY_ID FROM $TABLE_USERS WHERE $KEY_EMAIL = ? AND $KEY_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        var userId = -1

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
        }

        cursor.close()
        db.close()
        return userId
    }
    fun addDatVe(datVe: DatVe, userId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_DATVE_MADATVE, datVe.maDatVe)
            put(COL_DATVE_USER_ID, userId)
            put(COL_DATVE_FROM, datVe.from)
            put(COL_DATVE_TO, datVe.to)
            put(COL_DATVE_DATE, datVe.date)
            put(COL_DATVE_DEPTIME, datVe.departureTime)
            put(COL_DATVE_AIRLINE, datVe.airline)
            put(COL_DATVE_FLIGHTCODE, datVe.flightCode)
            put(COL_DATVE_GIAVE, datVe.giaVe)
            put(COL_DATVE_STATUS, datVe.status)
        }
        val id = db.insert(TABLE_DATVE, null, values)
        db.close()
        return id
    }

    fun getAllDatVe(userId: Int): List<DatVe> {
        val bookingList = mutableListOf<DatVe>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_DATVE WHERE $COL_DATVE_USER_ID = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val datVe = DatVe(
                    maDatVe = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_MADATVE)),
                    from = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_FROM)),
                    to = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_TO)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_DATE)),
                    departureTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_DEPTIME)),
                    airline = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_AIRLINE)),
                    flightCode = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_FLIGHTCODE)),
                    tenHanhKhach = "Hành khách (Cần JOIN)",
                    giaVe = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_DATVE_GIAVE)),
                    status = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATVE_STATUS))
                )
                bookingList.add(datVe)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return bookingList
    }
    /**
     * HÀM MỚI 1: Lấy thông tin User bằng ID
     * (Dùng cho trang Cá nhân và Thông tin liên hệ)
     */
    fun getUserById(userId: Int): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                password = "" // Không cần lấy pass ở đây
            )
        }
        cursor.close()
        db.close()
        return user
    }

    /**
     * HÀM MỚI 2: Cập nhật thông tin User
     * (Dùng khi bấm "Lưu" ở trang Thông tin liên hệ)
     */
    fun updateUser(userId: Int, name: String, phone: String, email: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, name)
            put(KEY_PHONE, phone)
            put(KEY_EMAIL, email)
        }
        // Cập nhật hàng (row) dựa trên ID
        val result = db.update(TABLE_USERS, values, "$KEY_ID = ?", arrayOf(userId.toString()))
        db.close()
        return result // Trả về số hàng bị ảnh hưởng (should be 1)
    }
}