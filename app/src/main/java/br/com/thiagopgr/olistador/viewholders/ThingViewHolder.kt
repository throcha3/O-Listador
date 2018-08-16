package br.com.thiagopgr.olistador.viewholders

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.entities.ThingEntity
import br.com.thiagopgr.olistador.entities.listeners.OnThingListFragmentInteractionListener
import br.com.thiagopgr.olistador.repositories.caches.ClassificationCacheConstants
import br.com.thiagopgr.olistador.repositories.caches.TypeCacheConstants
import android.graphics.drawable.Drawable



class ThingViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView)  {
    private val mDescription: TextView = itemView.findViewById(R.id.textDesc)
    private val mName: TextView = itemView.findViewById(R.id.textName)
    private val mCurrEpisode: TextView = itemView.findViewById(R.id.textCEpi)
    private val mCurrSeason: TextView = itemView.findViewById(R.id.textCSea)
    private val mClassifi: TextView = itemView.findViewById(R.id.textClassification)
    private val mType: TextView = itemView.findViewById(R.id.textType)

    fun bindData(thingEntity: ThingEntity, listener: OnThingListFragmentInteractionListener) {

        mDescription.text = thingEntity.description
        mName.text = thingEntity.name
        mCurrEpisode.text = "Ep:" + thingEntity.curr_ep.toString()
        mCurrSeason.text = "Temporada:" + thingEntity.curr_se.toString()
        mClassifi.text = ClassificationCacheConstants.getClassificationDescription(thingEntity.classification)
        mType.text = TypeCacheConstants.getTypeDescription(thingEntity.type)
        //mTextPriority.text = PriorityCacheConstants.getPriorityDescription(taskEntity.priorityId)
        var img = context.resources.getDrawable(R.drawable.ic_completed, null)

        when(thingEntity.type){
            1 -> img = context.resources.getDrawable(R.drawable.ic_on_the_move,null)
            2 -> img = context.resources.getDrawable(R.drawable.ic_completed,null)
            3 -> img = context.resources.getDrawable(R.drawable.ic_wishlist,null)
        }

        mName.setCompoundDrawablesWithIntrinsicBounds(img, null,null,null)




        // Atribui evento de click de detalhes
        mDescription.setOnClickListener {
            listener.onListClick(thingEntity.id)
        }

        // Atribui evento de remoção
        mDescription.setOnLongClickListener {
            showDialogConfirmation(thingEntity, listener)
            true
        }

        /*mImageTask.setOnClickListener({
            if (taskEntity.complete) {
                listener.onUncompleteClick(taskEntity.id)
            } else {
                listener.onCompleteClick(taskEntity.id)
            }
        })*/

        // Faz o tratamento para tarefas já completas
        /*if (taskEntity.complete) {
            this.mTextDescription.setTextColor(Color.GRAY)
            this.mImageTask.setImageResource(R.drawable.ic_done)
        }*/

    }

    /**
     * Confirma remoção
     */
    private fun showDialogConfirmation(thingEntity: ThingEntity, listener: OnThingListFragmentInteractionListener) {
        AlertDialog.Builder(context)
                .setTitle("Deseja realmente remover?")
                .setMessage("Deseja realmente remover ${thingEntity.name}?")
                .setIcon(R.drawable.ic_remove)
                .setPositiveButton(context.getString(R.string.sim), { dialogInterface: DialogInterface, i: Int -> listener.onDeleteClick(thingEntity.id) })
                .setNegativeButton(context.getString(R.string.cancel), null).show()
    }
}