package br.com.thiagopgr.olistador.repositories.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.thiagopgr.olistador.constants.DatabaseConstants

class OListadorDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Usado para criação do banco de dados
    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "olistador.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableThings)
        db.execSQL(createTableNotes)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(delTableNotes)
        db.execSQL(delTableThings)

        db.execSQL(createTableNotes)
        db.execSQL(createTableThings)
    }

    private val createTableThings = ( "CREATE TABLE ${DatabaseConstants.THINGS.TABLE_NAME} (" +
            " ${DatabaseConstants.THINGS.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.NAME} TEXT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.DESCRIPTION} TEXT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.TYPE} TEXT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION} INTEGER, " +
            " ${DatabaseConstants.THINGS.COLUMNS.SEASONS} INTEGER, " +
            " ${DatabaseConstants.THINGS.COLUMNS.EPISODES} INTEGER, " +
            " ${DatabaseConstants.THINGS.COLUMNS.RELEASE} TEXT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.STARTED} TEXT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.COMPLETED} TEXT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.RATE} INTEGER, " +
            " ${DatabaseConstants.THINGS.COLUMNS.IMAGE_URL} TEXT, " +
            " ${DatabaseConstants.THINGS.COLUMNS.CURRENT_EP} INTEGER, " +
            " ${DatabaseConstants.THINGS.COLUMNS.CURRENT_SEASON} INTEGER " +
            ");" )

    private val delTableThings = "DROP TABLE IF EXISTS ${DatabaseConstants.THINGS.TABLE_NAME}"

    private val createTableNotes = ( "CREATE TABLE ${DatabaseConstants.NOTES.TABLE_NAME} (" +
            " ${DatabaseConstants.NOTES.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " ${DatabaseConstants.NOTES.COLUMNS.THINGID} INTEGER, " +
            " ${DatabaseConstants.NOTES.COLUMNS.DATE} TEXT, " +
            " ${DatabaseConstants.NOTES.COLUMNS.DESCRIPTION} TEXT " +
            ");" )

    private val delTableNotes = "DROP TABLE IF EXISTS ${DatabaseConstants.NOTES.TABLE_NAME}"
}