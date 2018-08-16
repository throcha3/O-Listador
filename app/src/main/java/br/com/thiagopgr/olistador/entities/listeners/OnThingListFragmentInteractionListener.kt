package br.com.thiagopgr.olistador.entities.listeners

interface OnThingListFragmentInteractionListener {
    /**
     * Click para edição
     */
    fun onListClick(taskId: Int)

    /**
     * Remoção
     */
    fun onDeleteClick(taskId: Int)

    /**
     * Completa tarefa
     */
    fun onCompleteClick(taskId: Int)

    /**
     * Descompleta tarefa
     */
    fun onUncompleteClick(taskId: Int)
}