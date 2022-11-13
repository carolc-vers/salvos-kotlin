package com.example.salvos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.salvos.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {
        val salvos = listOf(
            Salvo("Segunda via do bilhete único", "Perdi meu bilhete e não estou conseguindo pedir outro", "12 de nov"),
            Salvo("Novos livros de LGPD", "Chegou na biblioteca novos livros sobre LGPD e outras diretrizes", "12 de nov"),
            Salvo("Nova empresa parceira da SPTECH!", "A C6Bank é a mais nova empresa parceira da nossa faculdade", "11 de nov"),
            Salvo("Como usar o TOTVS?", "Dicas rápidas de como utilizar o TOTVS para os novos alunos", "12 de out"),
            Salvo("Manual do aluno disponível", "O novo manual do aluno para 2023 já está disponível no Moodle", "24 de set"),
            Salvo("Atenção a semana de provas", "Agora os professores disponibilizam a data das provas e também dos trabalhos", "13 de nov")
        )

        val recyclerContainer = binding.recyclerViewSalvos
        recyclerContainer.layoutManager = LinearLayoutManager(baseContext)

        recyclerContainer.adapter = SalvoAdapter(
            salvos
        ){ mensagem -> Toast.makeText(
            baseContext,
            mensagem,
            Toast.LENGTH_LONG
        ).show() }
    }
}

class SalvoAdapter(
    private val salvos: List<Salvo>,
    private val onclick: (mensagem: String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class SalvoHolder(
        private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun vincular (salvo: Salvo){

            val tvTitulo = itemView.findViewById<TextView>(R.id.txt_titulo_salvo)
            val tvDescricao = itemView.findViewById<TextView>(R.id.txt_desc_salvo)
            val tvData = itemView.findViewById<TextView>(R.id.txt_date)
            val cvSalvo = itemView.findViewById<CardView>(R.id.cvSalvo)

            tvTitulo.text = salvo.titulo_publicacao
            tvDescricao.text = salvo.descricao_publicacao
            tvData.text = salvo.date

            cvSalvo.setOnClickListener{
                onclick("Você clicou no card X")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutDoCard = LayoutInflater.from(parent.context).inflate(R.layout.salvo_item, parent, false)
        return SalvoHolder(layoutDoCard)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SalvoHolder).vincular(salvos[position])
    }

    override fun getItemCount(): Int {
        return salvos.size
    }
}

data class Salvo (
    val titulo_publicacao: String,
    val descricao_publicacao: String,
    val date: String,
    var selected: Boolean = false
)