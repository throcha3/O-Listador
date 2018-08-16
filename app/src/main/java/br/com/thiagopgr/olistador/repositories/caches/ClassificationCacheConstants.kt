package br.com.thiagopgr.olistador.repositories.caches

import br.com.thiagopgr.olistador.entities.ClassificationEntity

class ClassificationCacheConstants private constructor(){

    companion object {
        fun setCache(list: List<ClassificationEntity>) {
            for (item in list) {
                mClassificationCache.put(item.id, item.description)
            }
        }

        fun getClassificationDescription(id: Int): String {
            return if (mClassificationCache[id] == null) {
                ""
            } else
                mClassificationCache[id].toString()
        }

        private val mClassificationCache = hashMapOf<Int, String>()
    }

}