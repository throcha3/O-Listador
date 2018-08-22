package br.com.thiagopgr.olistador.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.constants.OListadorConstants
import br.com.thiagopgr.olistador.util.SecPrefs

class SplashActivity : AppCompatActivity() {

    private lateinit var mSecPrefs: SecPrefs
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000 //1 seg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mSecPrefs = SecPrefs(this)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }



    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            //val intent = Intent(applicationContext, MainActivity::class.java)
            //startActivity(intent)
            //finish()
            verifyExistingUser()
        }
    }

    private fun verifyExistingUser() {
        val userName = mSecPrefs.getStoredString(OListadorConstants.KEY.USER_NAME)
        val userGender = mSecPrefs.getStoredString(OListadorConstants.KEY.USER_GENDER)

        if (userName != "" && userGender != "") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, NameFormActivity::class.java))
            finish()
        }
    }
}
