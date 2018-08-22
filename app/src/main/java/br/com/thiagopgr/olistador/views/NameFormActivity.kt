package br.com.thiagopgr.olistador.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.constants.OListadorConstants
import br.com.thiagopgr.olistador.util.SecPrefs
import kotlinx.android.synthetic.main.activity_name_form.*

class NameFormActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var mSecPrefs: SecPrefs
    private var mUserName : String = ""
    private var mUserGender : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_form)

        mSecPrefs = SecPrefs(this)

        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btnSave -> {
                handleSave()
            }
        }
    }

    private fun handleSave(){
        mUserName = edtName.text.toString()
        val id: Int = radioGroupGender.checkedRadioButtonId

        //Se tem valor em tudo, continua.
        if (id != -1 && mUserName != ""){
            val radio: RadioButton = findViewById(id)
            mUserGender = radio.text.toString()
            mSecPrefs.storeString(OListadorConstants.KEY.USER_NAME, mUserName)
            mSecPrefs.storeString(OListadorConstants.KEY.USER_GENDER, mUserGender)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{ //Senão não acontece nada.
            Toast.makeText(applicationContext,"É necessário preencher todos os campos!",
                    Toast.LENGTH_SHORT).show()
        }

    }


}
