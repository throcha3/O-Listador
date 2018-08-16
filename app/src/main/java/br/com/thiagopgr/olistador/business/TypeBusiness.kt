package br.com.thiagopgr.olistador.business

import br.com.thiagopgr.olistador.entities.TypeEntity

class TypeBusiness{


    private val mListTypes: List<TypeEntity> = listOf(
            TypeEntity(1,"Em andamento"),
            TypeEntity(2,"Finalizado"),
            TypeEntity(3,"Lista de desejos")
    )

    fun getList(): List<TypeEntity>{
        return mListTypes
    }

}