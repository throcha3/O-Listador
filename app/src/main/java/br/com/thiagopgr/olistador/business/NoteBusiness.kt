package br.com.thiagopgr.olistador.business

import android.content.Context
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.entities.NoteEntity
import br.com.thiagopgr.olistador.repositories.NoteRepository
import br.com.thiagopgr.olistador.util.exception.ValidationException

class NoteBusiness(val context: Context) {
    private val mNoteRepository: NoteRepository = NoteRepository.getInstance(context)

    fun get(id: Int): NoteEntity? = mNoteRepository.get(id)
    
    fun getList(thingFilter: Int): MutableList<NoteEntity> = mNoteRepository.getList(thingFilter)

    fun insert(note: NoteEntity) {

        try {
            // Faz a validação dos campos
            if (note.description == "" ) {
                throw ValidationException(context.getString(R.string.fill_all_fields))
            }
            if (note.thingId == 0 ) {
                throw ValidationException(context.getString(R.string.invalid_parameter_insert))
            }

            // Faz a inserção da tarefa
            mNoteRepository.insert(note)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Faz a atualização da tarefa
     * */
    fun update(note: NoteEntity) {

        try {
            // Faz a validação dos campos
            if (note.description == "" ) {
                throw ValidationException(context.getString(R.string.fill_all_fields))
            }
            if (note.thingId == 0 ) {
                throw ValidationException(context.getString(R.string.invalid_parameter_insert))
            }

            // Faz a atualização da tarefa
            mNoteRepository.update(note)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Faz a remoção da tarefa
     * */
    fun delete(id: Int) = mNoteRepository.delete(id)
}