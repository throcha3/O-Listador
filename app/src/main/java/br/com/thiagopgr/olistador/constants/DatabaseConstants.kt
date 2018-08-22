package br.com.thiagopgr.olistador.constants

class DatabaseConstants {
    object THINGS {
        val TABLE_NAME = "things"

        object COLUMNS {
            val ID = "_id"
            val NAME = "name"
            val DESCRIPTION = "description"
            val TYPE = "type"
            val CLASSIFICATION = "classification"
            val SEASONS = "seasons"
            val EPISODES = "episodes"
            val RELEASE = "release"
            val STARTED = "started"
            val COMPLETED = "complete"
            val RATE = "rate"
            val IMAGE_URL = "image_url"
            val CURRENT_EP = "current_ep"
            val CURRENT_SEASON = "current_season"
        }
    }

    object NOTES {
        val TABLE_NAME = "notes"

        object COLUMNS {
            val ID = "_id"
            val THINGID = "thing_id"
            val DESCRIPTION = "description"
            val DATE = "date"
        }
    }
}