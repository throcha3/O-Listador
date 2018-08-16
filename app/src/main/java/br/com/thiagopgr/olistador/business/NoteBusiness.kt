package br.com.thiagopgr.olistador.business

import android.content.Context
import br.com.thiagopgr.olistador.repositories.ThingRepository
import br.com.thiagopgr.olistador.repositories.helpers.OListadorDatabaseHelper

class NoteBusiness(val context: Context) {
    private val mThingRepository: ThingRepository = ThingRepository.getInstance(context)
}