package br.com.thiagopgr.olistador.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.business.NoteBusiness
import br.com.thiagopgr.olistador.business.TypeBusiness
import br.com.thiagopgr.olistador.entities.NoteEntity
import kotlinx.android.synthetic.main.activity_note_form.*

class NoteFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mNoteBusiness: NoteBusiness


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_form)

        btnSave.setOnClickListener(this)
        mNoteBusiness = NoteBusiness(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnSave -> {
                handleSave()
            }
        }
    }

    private fun handleSave(){
        val thingId = edtThingId.text.toString().toInt()
        val desc = edtDesc.text.toString()

        val note = NoteEntity(0, thingId, desc, "data")

        try {
            mNoteBusiness.insert(note)
            Toast.makeText(this, "deu bom", Toast.LENGTH_SHORT).show()
            finish()
        } catch (e: Exception){
            throw e
        }

    }
}
