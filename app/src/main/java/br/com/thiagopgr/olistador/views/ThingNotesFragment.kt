package br.com.thiagopgr.olistador.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.transition.TransitionManager
import android.view.*
import android.widget.ImageView
import android.widget.TextView

import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.adapters.NoteListAdapter
import br.com.thiagopgr.olistador.business.NoteBusiness
import br.com.thiagopgr.olistador.entities.NoteEntity
import br.com.thiagopgr.olistador.entities.listeners.OnNoteListInteractionListener
import kotlinx.android.synthetic.main.fragment_thing_notes.*


class ThingNotesFragment : Fragment(),View.OnClickListener {
    private var mFilterThing: Int = 0

    private lateinit var mContext: Context
    //private lateinit var mThingBusiness: ThingBusiness
    private lateinit var mNoteBusiness: NoteBusiness
    private lateinit var mOnNoteListFragmentInteractionListener: OnNoteListInteractionListener
    private lateinit var mRecyclerNotes: RecyclerView

    private var mCollapsed : Int = 0


    override fun onClick(v: View) {
        when (v.id){
            R.id.viewExpand -> {
                if (mCollapsed == 0){
                    TransitionManager.beginDelayedTransition(cardThing)
                    txtDescThing.visibility = View.VISIBLE
                    txtRelease.visibility = View.VISIBLE
                    mCollapsed = 1
                } else {
                    TransitionManager.beginDelayedTransition(cardThing)
                    txtDescThing.visibility = View.GONE
                    txtRelease.visibility = View.GONE
                    mCollapsed = 0
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            aqui o mFilterThing vai receber o valor passado pelo bundle!
        }*/



    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        //inflar menu da toolbar caso precise.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView: View = inflater.inflate(R.layout.fragment_thing_notes, container, false)

        // Incializa as variáveis
        mContext = rootView.context
        mNoteBusiness = NoteBusiness(mContext)


        createInteractionListener()
        // 1 - Obter a recyclerView
        mRecyclerNotes = rootView.findViewById(R.id.recViewNotes)
        val expand: View  = rootView.findViewById(R.id.viewExpand)
        expand.setOnClickListener(this)

        // 2 - Definir adapter passando listagem de itens
        val noteListAdapter = NoteListAdapter(mutableListOf(), mOnNoteListFragmentInteractionListener)
        mRecyclerNotes.adapter = noteListAdapter

        // 3 - Definir um layout
        mRecyclerNotes.layoutManager = LinearLayoutManager(mContext)

        // Retorna View
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadThings()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
                ThingNotesFragment().apply {
                    arguments = Bundle().apply {
                        /*putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)*/
                        //putInt(IntDoFiltro aqui)
                    }
                }
    }

    private fun createInteractionListener() {
        //Configurar pra quando clicar ir pra tela de cadastro
        mOnNoteListFragmentInteractionListener = object : OnNoteListInteractionListener {
            override fun onListClick(taskId: Int) {
                //val bundle = Bundle()
                //bundle.putInt(OListadorConstants.BUNDLE.THINGID, taskId)

                //val intent = Intent(mContext, ThingFormActivity::class.java)
                //intent.putExtras(bundle)
                //startActivity(intent)
            }

            override fun onDeleteClick(taskId: Int) {
                /*mTaskBusiness.delete(taskId)
                Toast.makeText(mContext, getString(R.string.tarefa_removida_com_sucesso), Toast.LENGTH_LONG).show()
                loadTasks()*/
            }


        }
    }

    private fun loadThings() {
        //Carregar Thing pro cabeçario

        // Carrega lista de Notas
        val listThingEntity: MutableList<NoteEntity> = mNoteBusiness.getList(mFilterThing)
        //normal
        //mRecyclerNotes.adapter = NoteListAdapter(listThingEntity, mOnNoteListFragmentInteractionListener)

        // side by side itens
        mRecyclerNotes.adapter = NoteListAdapter(listThingEntity, mOnNoteListFragmentInteractionListener)
        val layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerNotes.layoutManager = layoutManager
    }
}
