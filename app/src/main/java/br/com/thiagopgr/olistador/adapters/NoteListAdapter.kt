package br.com.thiagopgr.olistador.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.entities.NoteEntity
import br.com.thiagopgr.olistador.entities.listeners.OnNoteListInteractionListener
import br.com.thiagopgr.olistador.viewholders.NoteViewHolder

class NoteListAdapter (noteList: List<NoteEntity>, listener: OnNoteListInteractionListener): RecyclerView.Adapter<NoteViewHolder>() {
    private val mListNoteEntities: List<NoteEntity> = noteList
    private val mListener: OnNoteListInteractionListener = listener

    override fun getItemCount(): Int {
        return mListNoteEntities.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // Obtém item da lista
        val note: NoteEntity = mListNoteEntities[position]
        holder.bindData(note, mListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val context = parent.context

        // Infla o layout da linha e faz uso na listagem
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_note_list, parent, false)
        //val view = LayoutInflater.from(context).inflate(R.layout.row_thing_list, parent, false)

        return NoteViewHolder(view, context)
    }
}