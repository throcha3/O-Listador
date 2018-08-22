package br.com.thiagopgr.olistador.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.com.thiagopgr.olistador.constants.DatabaseConstants
import br.com.thiagopgr.olistador.entities.ThingEntity
import br.com.thiagopgr.olistador.repositories.helpers.OListadorDatabaseHelper

class ThingRepository private constructor(context: Context) {

    private val mOListadorDatabaseHelper: OListadorDatabaseHelper = OListadorDatabaseHelper(context)

    companion object {
        fun getInstance(context: Context): ThingRepository {
            if (INSTANCE == null) {
                INSTANCE = ThingRepository(context)
            }
            return INSTANCE as ThingRepository
        }
        private var INSTANCE: ThingRepository? = null
    }

    fun insert(thing: ThingEntity){
        try {
            val db = mOListadorDatabaseHelper.writableDatabase

            //val complete: Int = if (task.complete) 1 else 0

            val insertValues = ContentValues()
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.NAME, thing.name)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.DESCRIPTION, thing.description)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.TYPE, thing.type)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION, thing.classification)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.SEASONS, thing.seasons)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.EPISODES, thing.episodes)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.RELEASE, thing.release)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.STARTED, thing.started)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.COMPLETED, thing.completed)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.RATE, thing.rate)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.IMAGE_URL, thing.image_url)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.CURRENT_EP, thing.curr_ep)
            insertValues.put(DatabaseConstants.THINGS.COLUMNS.CURRENT_SEASON, thing.curr_se)

            db.insertOrThrow("things", null, insertValues)
        } catch (e: Exception) {
            throw e
        }
    }

    fun update(thing: ThingEntity){
        try {
            val db = mOListadorDatabaseHelper.writableDatabase

            val updateValues = ContentValues()
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.NAME, thing.name)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.DESCRIPTION, thing.description)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.TYPE, thing.type)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION, thing.classification)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.SEASONS, thing.seasons)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.EPISODES, thing.episodes)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.RELEASE, thing.release)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.STARTED, thing.started)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.COMPLETED, thing.completed)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.RATE, thing.rate)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.IMAGE_URL, thing.image_url)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.CURRENT_EP, thing.curr_ep)
            updateValues.put(DatabaseConstants.THINGS.COLUMNS.CURRENT_SEASON, thing.curr_se)

            // Critério de seleção
            val selection = DatabaseConstants.THINGS.COLUMNS.ID + " = ?"
            val selectionArgs = arrayOf(thing.id.toString())

            db.update(DatabaseConstants.THINGS.TABLE_NAME, updateValues, selection, selectionArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(id: Int){
        try {

            // Para fazer escrita de dados
            val db = mOListadorDatabaseHelper.writableDatabase

            val whereClause = "${DatabaseConstants.THINGS.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(id.toString())
            db.delete(DatabaseConstants.THINGS.TABLE_NAME, whereClause, whereArgs)

            //single line delete
            //db.delete(DatabaseConstants.THINGS.TABLE_NAME, "${DatabaseConstants.TASK.COLUMNS.ID} = $id", null)

        } catch (e: Exception) {
            throw e
        }
    }

    fun getList(typeFilter: Int, classiFilter: Int): MutableList<ThingEntity> {
        val list = mutableListOf<ThingEntity>()

        try {
            val cursor: Cursor
            val db = this.mOListadorDatabaseHelper.readableDatabase

            // Lista de taerfas filtradas de acordo com parâmetro

            //Tem tipo mas não tem classificação
            if (typeFilter != 0 && classiFilter == 0) {
                cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.THINGS.TABLE_NAME} WHERE " +
                        "${DatabaseConstants.THINGS.COLUMNS.TYPE} = $typeFilter",  null)
            }
            else if(classiFilter != 0 && typeFilter == 0) { //Tem classificação mas não tem tipo
                cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.THINGS.TABLE_NAME} WHERE " +
                        "${DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION} = $classiFilter",  null)
            }
            else if(classiFilter != 0 && typeFilter != 0) { //Tem ambos
                cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.THINGS.TABLE_NAME} WHERE " +
                        " ${DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION} = $classiFilter AND " +
                        " ${DatabaseConstants.THINGS.COLUMNS.TYPE} = $typeFilter",  null)
            }
            else {
                cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.THINGS.TABLE_NAME}", null)
            }

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val thingId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.NAME))
                    val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.DESCRIPTION))
                    val type = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.TYPE))
                    val classification = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION))
                    val seasons = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.SEASONS))
                    val episodes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.EPISODES))
                    val release = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.RELEASE))
                    val started = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.STARTED))
                    val completed = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.COMPLETED))
                    val rate = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.RATE))
                    val image_url = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.IMAGE_URL))
                    val currEp = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.CURRENT_EP))
                    val currSe = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.CURRENT_SEASON))

                    // Adiciona item a lista
                    list.add(ThingEntity(thingId, name, description, type, classification, seasons, episodes, release, started, completed, rate, image_url,currEp, currSe))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }

        // Retorno objeto com dados
        return list
    }

    fun get(thingId: Int): ThingEntity? {

        var thingEntity: ThingEntity? = null

        try {
            val cursor: Cursor
            val db = this.mOListadorDatabaseHelper.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(DatabaseConstants.THINGS.COLUMNS.NAME
                    , DatabaseConstants.THINGS.COLUMNS.DESCRIPTION
                    , DatabaseConstants.THINGS.COLUMNS.TYPE
                    , DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION
                    , DatabaseConstants.THINGS.COLUMNS.SEASONS
                    , DatabaseConstants.THINGS.COLUMNS.EPISODES
                    , DatabaseConstants.THINGS.COLUMNS.RELEASE
                    , DatabaseConstants.THINGS.COLUMNS.STARTED
                    , DatabaseConstants.THINGS.COLUMNS.COMPLETED
                    , DatabaseConstants.THINGS.COLUMNS.RATE
                    , DatabaseConstants.THINGS.COLUMNS.IMAGE_URL
                    , DatabaseConstants.THINGS.COLUMNS.CURRENT_EP
                    , DatabaseConstants.THINGS.COLUMNS.CURRENT_SEASON)

            // Filtro
            val selection = "${DatabaseConstants.THINGS.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(thingId.toString())

            // Carrega thing
            cursor = db.query(DatabaseConstants.THINGS.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            // Verifica se existe retorno
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.NAME))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.DESCRIPTION))
                val type = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.TYPE))
                val classification = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.CLASSIFICATION))
                val seasons = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.SEASONS))
                val episodes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.EPISODES))
                val release = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.RELEASE))
                val started = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.STARTED))
                val completed = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.COMPLETED))
                val rate = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.RATE))
                val imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.IMAGE_URL))
                val currEp = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.CURRENT_EP))
                val currSe = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.THINGS.COLUMNS.CURRENT_SEASON))


                // Atribui valor a variável do usuário
                thingEntity = ThingEntity(thingId, name, description, type, classification, seasons, episodes, release, started, completed, rate, imageUrl, currEp, currSe)
            }

            // Fecha cursor
            cursor.close()

        } catch (e: Exception) {
            return thingEntity
        }

        // Retorno objeto com dados
        return thingEntity
    }
}