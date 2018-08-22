package br.com.thiagopgr.olistador.business

import android.content.Context
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.entities.ThingEntity
import br.com.thiagopgr.olistador.repositories.ThingRepository
import br.com.thiagopgr.olistador.util.exception.ValidationException

class ThingBusiness(val context: Context) {

    private val mThingRepository: ThingRepository = ThingRepository.getInstance(context)

    fun get(id: Int): ThingEntity? = mThingRepository.get(id)


    fun getList(typeFilter: Int, typeClassi: Int): MutableList<ThingEntity> = mThingRepository.getList(typeFilter, typeClassi)

    fun insert(thing: ThingEntity) {

        try {
            // Faz a validação dos campos
            if (thing.description == "" || thing.name == "" || thing.classification == 0 || thing.type == 0) {
                throw ValidationException(context.getString(R.string.fill_all_fields))
            }

            // Faz a inserção da tarefa
            mThingRepository.insert(thing)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Faz a atualização da tarefa
     * */
    fun update(thing: ThingEntity) {

        try {
            // Faz a validação dos campos
            if (thing.description == "" || thing.name == "" || thing.classification == 0 || thing.type == 0) {
                throw ValidationException(context.getString(R.string.fill_all_fields))
            }

            // Faz a atualização da tarefa
            mThingRepository.update(thing)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Faz a remoção da tarefa
     * */
    fun delete(id: Int) = mThingRepository.delete(id)

}