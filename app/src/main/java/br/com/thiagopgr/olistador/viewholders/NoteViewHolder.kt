package br.com.thiagopgr.olistador.viewholders

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.entities.NoteEntity
import br.com.thiagopgr.olistador.entities.listeners.OnNoteListInteractionListener
import kotlinx.android.synthetic.main.row_note_list.view.*

class NoteViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView)  {


    private val mDesc: TextView = itemView.findViewById(R.id.txtNoteDesc)
    //private val mDate: TextView = itemView.findViewById(R.id.textDesc)


    fun bindData(noteEntity: NoteEntity, listener: OnNoteListInteractionListener) {

        mDesc.text = noteEntity.description

        // Atribui evento de click de detalhes
        itemView.setOnClickListener {
            listener.onListClick(noteEntity.id)
        }

        // Atribui evento de remoção
        /* itemView.setOnLongClickListener {
             showDialogConfirmation(noteEntity, listener)
             true
         }*/

    }

    /**
     * Confirma remoção
     */
    private fun showDialogConfirmation(noteEntity: NoteEntity, listener: OnNoteListInteractionListener) {
        AlertDialog.Builder(context)
                .setTitle("Deseja realmente remover?")
                .setMessage("Deseja realmente remover esta nota?")
                .setIcon(R.drawable.ic_remove)
                .setPositiveButton(context.getString(R.string.sim), { dialogInterface: DialogInterface, i: Int -> listener.onDeleteClick(noteEntity.id) })
                .setNegativeButton(context.getString(R.string.cancel), null).show()
    }
}