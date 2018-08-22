package br.com.thiagopgr.olistador.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.entities.ThingEntity
import br.com.thiagopgr.olistador.entities.listeners.OnThingListFragmentInteractionListener
import br.com.thiagopgr.olistador.viewholders.ThingViewHolder

class ThingListAdapter (taskList: List<ThingEntity>, listener: OnThingListFragmentInteractionListener): RecyclerView.Adapter<ThingViewHolder>() {
    private val mListTaskEntities: List<ThingEntity> = taskList
    private val mListener: OnThingListFragmentInteractionListener = listener

    override fun getItemCount(): Int {
        if (mListTaskEntities != null) {
            return mListTaskEntities.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ThingViewHolder, position: Int) {
        // Obtém item da lista
        val task: ThingEntity = mListTaskEntities[position]
        holder.bindData(task, mListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingViewHolder {
        val context = parent.context

        // Infla o layout da linha e faz uso na listagem
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_thing_list, parent, false)

        return ThingViewHolder(view, context)
    }
}