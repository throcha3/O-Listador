package br.com.thiagopgr.olistador.business

import br.com.thiagopgr.olistador.entities.ClassificationEntity

class ClassificationBusiness {

    private val mListClassifications: List<ClassificationEntity> = listOf(
            ClassificationEntity(1, "Filme"),
            ClassificationEntity(2, "Anime"),
            ClassificationEntity(3, "Série"),
            ClassificationEntity(4, "Documentário"),
            ClassificationEntity(5, "Livro"),
            ClassificationEntity(6, "Outros")
    )

    fun getList(): List<ClassificationEntity>{
        return mListClassifications
    }
}