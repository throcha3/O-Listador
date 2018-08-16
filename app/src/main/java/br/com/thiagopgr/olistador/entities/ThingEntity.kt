package br.com.thiagopgr.olistador.entities

data class ThingEntity(
        val id: Int,
        val name: String,
        val description: String,
        val type: Int,
        val classification: Int,
        val seasons: Int,
        val episodes: Int,
        val release: String, //Lançamento Data
        val started: String, // Comecei Data
        val completed: String, //Terminei Data
        val rate: Int, //Minha nota final, de 0 a 10
        val image_url: String,
        val curr_ep: Int,
        val curr_se: Int
)
