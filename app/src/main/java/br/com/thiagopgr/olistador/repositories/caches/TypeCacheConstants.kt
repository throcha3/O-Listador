package br.com.thiagopgr.olistador.repositories.caches

import br.com.thiagopgr.olistador.entities.TypeEntity

class TypeCacheConstants private constructor(){

    companion object {
        fun setCache(list: List<TypeEntity>) {
            for (item in list) {
                mTypeCache.put(item.id, item.description)
            }
        }

        fun getTypeDescription(id: Int): String {
            return if (mTypeCache[id] == null) {
                ""
            } else
                mTypeCache[id].toString()
        }

        private val mTypeCache = hashMapOf<Int, String>()
    }

}