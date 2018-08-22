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

import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.adapters.ThingListAdapter
import br.com.thiagopgr.olistador.business.ThingBusiness
import br.com.thiagopgr.olistador.entities.ThingEntity
import br.com.thiagopgr.olistador.entities.listeners.OnThingListFragmentInteractionListener
import android.view.*
import android.widget.Toast
import br.com.thiagopgr.olistador.constants.OListadorConstants
import kotlinx.android.synthetic.main.fragment_things_list.*


class ThingsListFragment : Fragment(), View.OnClickListener,SearchView.OnQueryTextListener, BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.filter_all ->{
                mFilterType = 0
                loadThings()
            }
            R.id.filter_on_the_move ->{
                mFilterType = 1
                loadThings()
            }
            R.id.filter_finished ->{
                mFilterType = 2
                loadThings()
            }
            R.id.filter_wishlist ->{
                mFilterType = 3
                loadThings()
            }
        }

        return true
    }

    private var mUserSearch: String = ""

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        mUserSearch = newText
        loadThings()
        return true
    }

    private var mFilterType: Int = 0
    private var mFilterClassi: Int = 0
    private lateinit var mContext: Context
    private lateinit var mThingBusiness: ThingBusiness
    private lateinit var mOnThingListFragmentInteractionListener: OnThingListFragmentInteractionListener
    private lateinit var mRecyclerThingList: RecyclerView


    override fun onClick(v: View) {
        when(v.id){
            R.id.floatAddThing ->{
                startActivity(Intent(mContext, ThingFormActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            aqui o mFilterType vai receber o valor passado pelo bundle!
        }*/



    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Digite um nome.."

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_things_list, container, false)

        val rootView: View = inflater.inflate(R.layout.fragment_things_list, container, false)

        // Incializa as variáveis
        mContext = rootView.context
        mThingBusiness = ThingBusiness(mContext)

        // Necessário buscar os elementos de interface através do findViewById. Não funciona Kotlin-Extensions
        rootView.findViewById<FloatingActionButton>(R.id.floatAddThing).setOnClickListener(this)

        rootView.findViewById<BottomNavigationView>(R.id.bottomNav).setOnNavigationItemSelectedListener(this)

        // Inicializa listener
        //txtView.text = "OI jovem"
        createInteractionListener()
        // 1 - Obter a recyclerView
        mRecyclerThingList = rootView.findViewById(R.id.recViewThings)

        // 2 - Definir adapter passando listagem de itens
        val taskListAdapter = ThingListAdapter(mutableListOf(), mOnThingListFragmentInteractionListener)
        mRecyclerThingList.adapter = taskListAdapter

        // 3 - Definir um layout
        mRecyclerThingList.layoutManager = LinearLayoutManager(mContext)

        // Retorna View
        return rootView
    }

      override fun onResume() {
        super.onResume()
        loadThings()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
                ThingsListFragment().apply {
                    arguments = Bundle().apply {
                        /*putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)*/
                        //putInt(IntDoFiltro aqui)
                    }
                }
    }

    private fun createInteractionListener() {
        //Configurar pra quando clicar ir pra tela de cadastro
        mOnThingListFragmentInteractionListener = object : OnThingListFragmentInteractionListener {
            override fun onListClick(taskId: Int) {
                val bundle = Bundle()
                bundle.putInt(OListadorConstants.BUNDLE.THING_ID, taskId)

                val intent = Intent(mContext, ThingFormActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(taskId: Int) {
                /*mTaskBusiness.delete(taskId)
                Toast.makeText(mContext, getString(R.string.tarefa_removida_com_sucesso), Toast.LENGTH_LONG).show()
                loadTasks()*/
            }


        }
    }

    private fun loadThings() {

        // Carrega lista de tarefas
        val listThingEntity: MutableList<ThingEntity> = mThingBusiness.getList(mFilterType, mFilterClassi)

        if (mUserSearch != ""){
            var listFiltered: MutableList<ThingEntity> = mutableListOf()
            for (item in listThingEntity){
                if (item.name.contains(mUserSearch)){
                    listFiltered.add(item)
                }
            }
            mRecyclerThingList.adapter = ThingListAdapter(listFiltered, mOnThingListFragmentInteractionListener)
        } else{
            // Inicializa o adapter com registros atualizados
            mRecyclerThingList.adapter = ThingListAdapter(listThingEntity, mOnThingListFragmentInteractionListener)
        }

    }

}
