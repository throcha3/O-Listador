package br.com.thiagopgr.olistador.views

import android.app.DatePickerDialog
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.SeekBar
import android.widget.Toast
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.business.ClassificationBusiness
import br.com.thiagopgr.olistador.business.ThingBusiness
import br.com.thiagopgr.olistador.business.TypeBusiness
import br.com.thiagopgr.olistador.constants.OListadorConstants
import br.com.thiagopgr.olistador.entities.ClassificationEntity
import br.com.thiagopgr.olistador.entities.ThingEntity
import br.com.thiagopgr.olistador.entities.TypeEntity
import br.com.thiagopgr.olistador.util.exception.ValidationException
import kotlinx.android.synthetic.main.activity_thing_form.*
import java.text.SimpleDateFormat
import java.util.*

class ThingFormActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (seekBar.id == R.id.seekRate) {
            txtRateValue.text = progress.toString()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    private var SIMPLE_DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")
    private var mThingId: Int = 0
    private lateinit var mThingBusiness: ThingBusiness
    private var mClassificationBusiness: ClassificationBusiness = ClassificationBusiness()
    private var mTypeBusiness: TypeBusiness = TypeBusiness()

    private var mTypeEntityList: MutableList<TypeEntity> = mutableListOf()
    private var mTypeEntityListId: MutableList<Int> = mutableListOf()

    private var mClassiEntityList: MutableList<ClassificationEntity> = mutableListOf()
    private var mClassiEntityListId: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thing_form)

        mThingBusiness = ThingBusiness(this)
        setListeners()
        setSpinners()
        loadDataFromActivity()

        //mClassificationBusiness = ClassificationBusiness(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSave -> {
                handleSave()
            }
            R.id.btnCompleted -> {
                showDateDialogCompleted()
            }
            R.id.btnRelease -> {
                showDateDialogRelease()
            }
            R.id.btnStarted -> {
                showDateDialogStarted()
            }
        }
    }

    private fun setSpinners() {
        // Lista de prioridades do banco de dados local
        mTypeEntityList = mTypeBusiness.getList() as MutableList<TypeEntity>
        // Cria uma lista de strings para preenchimento do spinner
        val listTypesStr = mTypeEntityList.map { it.description }
        // Mapeia a lista de Ids das prioridades
        mTypeEntityListId = mTypeEntityList.map { it.id }.toMutableList()
        val adapterType = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listTypesStr)
        spinnerType.adapter = adapterType


        mClassiEntityList = mClassificationBusiness.getList() as MutableList<ClassificationEntity>
        val listClassisStr = mClassiEntityList.map { it.description }
        mClassiEntityListId = mClassiEntityList.map { it.id }.toMutableList()
        val adapterClassi = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listClassisStr)
        spinnerClassification.adapter = adapterClassi
    }

    private fun setListeners() {
        btnSave.setOnClickListener(this)
        btnCompleted.setOnClickListener(this)
        btnStarted.setOnClickListener(this)
        btnRelease.setOnClickListener(this)
        seekRate.setOnSeekBarChangeListener(this)
    }

    private fun handleSave() {

        if (edtCurrEpisode.text == null) edtCurrEpisode.setText(0)
        if (edtCurrSeason.text == null)  edtCurrSeason.setText(0)
        if (edtEpisodes.text == null)    edtEpisodes.setText(0)
        if (edtSeasons.text == null)     edtSeasons.setText(0)



        try {
            val name = edtName.text.toString()
            val description = edtDesc.text.toString()
            val type = mTypeEntityListId[spinnerType.selectedItemPosition]
            //val priorityId = mPriorityEntityListId[spinnerPriority.selectedItemPosition]
            val classification = mClassiEntityListId[spinnerClassification.selectedItemPosition]
            val seasons: Int = edtSeasons.text.toString().toInt()
            val episodes: Int = edtEpisodes.text.toString().toInt()
            val release = btnRelease.text.toString()
            val started = btnStarted.text.toString()
            val completed = btnCompleted.text.toString()
            val rate = seekRate.progress
            val imageUrl = ""
            val currEp = edtCurrEpisode.text.toString().toInt()
            val currSe = edtCurrSeason.text.toString().toInt()

            var thing = ThingEntity(mThingId, name, description, type, classification, seasons, episodes, release, started, completed, rate, imageUrl, currEp, currSe)

            if (mThingId == 0) {
                mThingBusiness.insert(thing)
                Toast.makeText(this, "Cadastro efetuado com sucesso!!", Toast.LENGTH_LONG).show()
            } else {
                mThingBusiness.update(thing)
                Toast.makeText(this, "Cadastro atualizado com sucesso!!", Toast.LENGTH_LONG).show()
            }

            finish()
        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    } // End fun handleSave()

    private fun getIndexType(typeId: Int): Int {
        var index = 0
        for (i in 0..mTypeEntityList.size) {
            if (mTypeEntityList[i].id == typeId) {
                index = i
                break
            }
        }
        return index
    }

    private fun getIndexClassi(classiId: Int): Int {
        var index = 0
        for (i in 0..mClassiEntityList.size) {
            if (mClassiEntityList[i].id == classiId) {
                index = i
                break
            }
        }
        return index
    }

    /**
     * Carrega dados de edição
     */
    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            mThingId = bundle.getInt(OListadorConstants.BUNDLE.THINGID, 0)

            // Carrega tarefa
            if (mThingId != 0) {
                //textToolbar.setText(R.string.atualizar_tarefa)
                btnSave.text = getString(R.string.edit)

                // Carrega tarefa
                val thing = mThingBusiness.get(mThingId)
                if (thing != null) {
                    // Atribui valores as propriedades
                    edtName.setText(thing.name)
                    edtDesc.setText(thing.description)
                    edtCurrEpisode.setText(thing.curr_ep.toString())
                    edtCurrSeason.setText(thing.curr_se.toString())
                    edtSeasons.setText(thing.seasons.toString())
                    edtEpisodes.setText(thing.episodes.toString())

                    txtRateValue.text = thing.rate.toString()
                    seekRate.progress = thing.rate

                    btnRelease.text = thing.release
                    btnCompleted.text = thing.completed
                    btnStarted.text = thing.started

                    spinnerType.setSelection(getIndexType(thing.type))
                    spinnerClassification.setSelection(getIndexClassi(thing.classification))
                }
            }

        }
    }

    /**
     * Mostra datepicker de seleção
     */
    private fun showDateDialogRelease() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //DatePickerDialog(this, this, year, month, day).show()
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val strDate = SIMPLE_DATE_FORMAT.format(calendar.time)
            btnRelease.text = strDate

        }, year, month, day)
        dpd.show()
    }

    private fun showDateDialogStarted() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //DatePickerDialog(this, this, year, month, day).show()
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val strDate = SIMPLE_DATE_FORMAT.format(calendar.time)
            btnStarted.text = strDate

        }, year, month, day)
        dpd.show()
    }

    private fun showDateDialogCompleted() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //DatePickerDialog(this, this, year, month, day).show()
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val strDate = SIMPLE_DATE_FORMAT.format(calendar.time)
            btnCompleted.text = strDate

        }, year, month, day)
        dpd.show()
    }
}
