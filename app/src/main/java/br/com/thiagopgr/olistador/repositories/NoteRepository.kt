package br.com.thiagopgr.olistador.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.com.thiagopgr.olistador.constants.DatabaseConstants
import br.com.thiagopgr.olistador.entities.NoteEntity
import br.com.thiagopgr.olistador.repositories.helpers.OListadorDatabaseHelper

class NoteRepository private constructor(context: Context) {

    private val mOListadorDatabaseHelper: OListadorDatabaseHelper = OListadorDatabaseHelper(context)

    companion object {
        fun getInstance(context: Context): NoteRepository {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
            return INSTANCE as NoteRepository
        }
        private var INSTANCE: NoteRepository? = null
    }

    fun insert(note: NoteEntity){
        try {
            val db = mOListadorDatabaseHelper.writableDatabase

            //val complete: Int = if (task.complete) 1 else 0

            val insertValues = ContentValues()
            insertValues.put(DatabaseConstants.NOTES.COLUMNS.THINGID, note.thingId)
            insertValues.put(DatabaseConstants.NOTES.COLUMNS.DESCRIPTION, note.description)
            insertValues.put(DatabaseConstants.NOTES.COLUMNS.DATE, note.date)

            db.insertOrThrow(DatabaseConstants.NOTES.TABLE_NAME, null, insertValues)
        } catch (e: Exception) {
            throw e
        }
    }

    fun update(note: NoteEntity){
        try {
            val db = mOListadorDatabaseHelper.writableDatabase

            val updateValues = ContentValues()
            updateValues.put(DatabaseConstants.NOTES.COLUMNS.THINGID, note.thingId)
            updateValues.put(DatabaseConstants.NOTES.COLUMNS.DESCRIPTION, note.description)
            updateValues.put(DatabaseConstants.NOTES.COLUMNS.DATE, note.date)

            // Critério de seleção
            val selection = DatabaseConstants.NOTES.COLUMNS.ID + " = ?"
            val selectionArgs = arrayOf(note.id.toString())

            db.update(DatabaseConstants.NOTES.TABLE_NAME, updateValues, selection, selectionArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(id: Int){
        try {

            // Para fazer escrita de dados
            val db = mOListadorDatabaseHelper.writableDatabase

            val whereClause = "${DatabaseConstants.NOTES.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(id.toString())
            db.delete(DatabaseConstants.NOTES.TABLE_NAME, whereClause, whereArgs)

            //single line delete
            //db.delete(DatabaseConstants.THINGS.TABLE_NAME, "${DatabaseConstants.NOTES.COLUMNS.ID} = $id", null)

        } catch (e: Exception) {
            throw e
        }
    }

    fun getList(thingFilter: Int): MutableList<NoteEntity> {
        val list = mutableListOf<NoteEntity>()

        try {
            val cursor: Cursor
            val db = this.mOListadorDatabaseHelper.readableDatabase

            // Lista de taerfas filtradas de acordo com parâmetro

            //Tem tipo mas não tem classificação
            if (thingFilter != 0 ) {
                cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.NOTES.TABLE_NAME} WHERE " +
                        "${DatabaseConstants.NOTES.COLUMNS.THINGID} = $thingFilter", null)
            }
            else {
                cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.NOTES.TABLE_NAME}", null)
            }

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val noteId =      cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.NOTES.COLUMNS.ID))
                    val thingId =     cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.NOTES.COLUMNS.THINGID))
                    val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.NOTES.COLUMNS.DESCRIPTION))
                    val date =        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.NOTES.COLUMNS.DATE))

                    // Adiciona item a lista
                    list.add(NoteEntity(noteId, thingId, description,date))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }

        // Retorno objeto com dados
        return list
    }

    fun get(noteId: Int): NoteEntity? {

        var noteEntity: NoteEntity? = null

        try {
            val cursor: Cursor
            val db = this.mOListadorDatabaseHelper.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(DatabaseConstants.NOTES.COLUMNS.THINGID
                    , DatabaseConstants.NOTES.COLUMNS.DESCRIPTION
                    , DatabaseConstants.NOTES.COLUMNS.DATE
                    )

            // Filtro
            val selection = "${DatabaseConstants.NOTES.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(noteId.toString())

            // Carrega note
            cursor = db.query(DatabaseConstants.NOTES.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            // Verifica se existe retorno
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val thingId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.NOTES.COLUMNS.THINGID))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.NOTES.COLUMNS.DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.NOTES.COLUMNS.DATE))


                noteEntity = NoteEntity(noteId, thingId, description, date)
            }

            // Fecha cursor
            cursor.close()

        } catch (e: Exception) {
            return noteEntity
        }

        // Retorno objeto com dados
        return noteEntity
    }

}