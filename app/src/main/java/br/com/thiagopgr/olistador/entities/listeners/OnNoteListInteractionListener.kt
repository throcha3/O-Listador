package br.com.thiagopgr.olistador.entities.listeners

interface OnNoteListInteractionListener {
    /**
     * Click para edição
     */
    fun onListClick(thingId: Int)

    /**
     * Remoção
     */
    fun onDeleteClick(thingId: Int)
}