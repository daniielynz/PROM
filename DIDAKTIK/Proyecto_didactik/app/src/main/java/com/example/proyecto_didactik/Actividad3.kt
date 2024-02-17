package com.example.proyecto_didactik

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import java.util.Random

class Actividad3 : AppCompatActivity(), View.OnTouchListener {

    private var initX = -1f
    private var initY = -1f
    private var diffX = -1f
    private var diffY = -1f
    private var prevDiffX = -1f
    private var prevDiffY = -1f
    private var cellWidth = 0

    private lateinit var game_board: GridLayout

    private var acertadas: Int = 0

    private var nombreActividad: String? = null

    enum class SwipeDirection {
        Undefined,
        Vertical,
        Horizontal }

    private var direction = SwipeDirection.Undefined

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initX = event.x
                initY = event.y
                v.setBackgroundResource(R.drawable.selected_background)
            }

            MotionEvent.ACTION_MOVE -> {
                if(initX != -1f && initY != -1f){

                    val tag = v.tag.toString()
                    val tagInt = tag.toInt()
                    diffX = initX - event.x
                    diffY = initY - event.y

                    if(direction == SwipeDirection.Undefined || direction == SwipeDirection.Horizontal){
                        when {
                            diffX > cellWidth -> {
                                // left dir
                                if(prevDiffX == -1f || prevDiffX < diffX){
                                    selectLetter((tagInt - (diffX / cellWidth).toInt()).toString())
                                    direction = SwipeDirection.Horizontal
                                }

                                else if ( prevDiffX != -1f && prevDiffX > diffX){
                                    unselectLetter((tagInt - (prevDiffX / cellWidth).toInt()).toString())
                                }
                            }
                            (-1) * diffX > cellWidth -> {
                                // right dir
                                if(prevDiffX == -1f || prevDiffX > diffX){
                                    selectLetter((tagInt + -1 * (diffX / cellWidth).toInt()).toString())
                                    direction = SwipeDirection.Horizontal
                                }

                                else if ( prevDiffX != -1f && prevDiffX < diffX){
                                    unselectLetter((tagInt - (prevDiffX / cellWidth).toInt()).toString())
                                }
                            }
                        }
                    }

                    if(direction == SwipeDirection.Undefined || direction == SwipeDirection.Vertical){
                        when {
                            diffY > cellWidth -> {
                                if(prevDiffY == -1f || prevDiffY < diffY){
                                    selectLetter((tagInt - 10 * (diffY / cellWidth).toInt()).toString())
                                    direction = SwipeDirection.Vertical
                                }

                                else if (prevDiffY != -1f && prevDiffY > diffY){
                                    unselectLetter((tagInt - 10 * (diffY / cellWidth).toInt()).toString())
                                }
                            }
                            (-1) * diffY > cellWidth -> {
                                if(prevDiffY == -1f || prevDiffY > diffY){
                                    selectLetter((tagInt + -10 * (diffY / cellWidth).toInt()).toString())
                                    direction = SwipeDirection.Vertical
                                }

                                else if (prevDiffY != -1f && prevDiffY < diffY){
                                    unselectLetter((tagInt - 10 * (diffY / cellWidth).toInt()).toString())
                                }
                            }
                        }
                    }

                    prevDiffX = diffX
                    prevDiffY = diffY
                }
            }

            MotionEvent.ACTION_UP -> {
                val tag = v.tag.toString()
                val tagInt = tag.toInt()
                var finalTag = tag

                if(direction == SwipeDirection.Horizontal){
                    finalTag = when {
                        diffX > cellWidth -> {
                            (tagInt - (diffX / cellWidth).toInt()).toString()
                        }
                        -1 * diffX > cellWidth -> {
                            (tagInt + -1 * (diffX / cellWidth).toInt()).toString()
                        }

                        else -> tag
                    }
                }

                else if(direction == SwipeDirection.Vertical){
                    finalTag = when {
                        diffY > cellWidth -> {
                            (tagInt - 10 * (diffY / cellWidth).toInt()).toString()
                        }
                        -1 * diffY > cellWidth -> {
                            (tagInt + -10 * (diffY / cellWidth).toInt()).toString()
                        }

                        else -> tag
                    }
                }
                checkAnswer(v.tag.toString(), finalTag, v)
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad3)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        acertadas = 0
        cellWidth = resources.displayMetrics.widthPixels / 10

        game_board = findViewById(R.id.game_board)

        for (i in 0 until game_board.childCount){
            val row = game_board.getChildAt(i) as LinearLayout
            for(j in 0 until row.childCount){
                row.getChildAt(j).setOnTouchListener(this)
            }
        }

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")

        generateBoardHelper()
    }

    private fun generateBoardHelper(){
        // reset all flags to false
        letterFlag = Array(dim) {Array(dim) { false } }
        answerFlag = Array(dim) { Array(dim) { false } }
        var flag: Boolean = Random().nextInt(2) != 0

        for (i in 0 until dim){
            for (j in 0 until dim){
                // set each cell to a random letter
                boardValues[i][j] = letters[(letters.indices).random()].toString()
            }
        }

        val rand = Random()
        for (answer in 0 until answers.size){
            var isFound = false

            while(!isFound){
                var r = 0
                if(answers[answer].wordVal.length < dim){
                    r = rand.nextInt(dim - (answers[answer].wordVal.length))
                } else if (answers[answer].wordVal.length > dim){
                    // invalid word
                    break
                }

                var start = rand.nextInt(dim - 1)

                for (n in 0 until dim){
                    var _n = (n + start) % dim
                    for (i in r until r + answers[answer].wordVal.length ) {
                        if(flag){
                            if(letterFlag[_n][i] && boardValues[_n][i] != answers[answer].wordVal[i-r].toString()) {
                                break
                            } else if (i == r + answers[answer].wordVal.length - 1) {
                                isFound = true
                            }
                        } else {
                            if(letterFlag[i][_n]&& boardValues[i][_n] != answers[answer].wordVal[i-r].toString()) {
                                break
                            } else if (i == r + answers[answer].wordVal.length - 1) {
                                isFound = true
                            }
                        }
                    }
                    if(isFound) {
                        if(flag){
                            answers[answer].setLoc(_n * 10 + r, flag)
                        } else {
                            answers[answer].setLoc(r * 10 + _n, flag)
                        }

                        for (i in r until r + answers[answer].wordVal.length ) {
                            if(flag){
                                boardValues[_n][i] = answers[answer].wordVal[i-r].toString()
                                letterFlag[_n][i] = true
                            } else {
                                boardValues[i][_n] = answers[answer].wordVal[i-r].toString()
                                letterFlag[i][_n] = true
                            }
                        }
                        break
                    }
                }
                flag = !flag
            }
        }

        val childCount = game_board.childCount
        for (i in 0 until childCount){
            val row = game_board.getChildAt(i) as LinearLayout
            for (j in 0 until row.childCount){
                (row.getChildAt(j) as TextView).text = boardValues[i][j]
            }
        }

    }

    private fun selectLetter(tag: String){
        for (i in 0 until game_board.childCount){
            val row = game_board.getChildAt(i) as LinearLayout
            for (j in 0 until row.childCount){
                if(row.getChildAt(j).tag == tag){
                    row.getChildAt(j).setBackgroundResource(R.drawable.selected_background)
                    return
                }
            }
        }
    }

    private fun unselectLetter(tag: String){
        var tagInt = tag.toInt()
        for (i in 0 until game_board.childCount){
            val row = game_board.getChildAt(i) as LinearLayout
            for (t in 0 until row.childCount){
                if(row.getChildAt(t).tag == tag){
                    if(!answerFlag[tagInt / 10][tagInt % 10]){
                        row.getChildAt(t).setBackgroundResource(R.drawable.unselected_background)
                    }
                    return
                }
            }
        }
    }

    private fun unselectLetters(start: Int, end: Int, isHorizontal: Boolean){
        var _start = start
        var _end = end

        if (end < start){
            _start = end
            _end = start
        }
        if (isHorizontal){
            for (i in _start.._end){
                unselectLetter(i.toString())
            }
        }
        else{
            // step 10 to increment 10 cells horizontal since traversing vertically
            for (i in _start.._end step 10){
                unselectLetter(i.toString())
            }
        }
    }

    private fun checkAnswer(start: String, end: String, view:View){
        var isFound = false
        var foundWord = ""

        for(answer in answers){
            if(answer.checkLoc(start.toInt(), end.toInt(), direction == SwipeDirection.Horizontal)){
                if(answer.isFound){
                    initX = -1f
                    initY = -1f
                    diffX = -1f
                    diffY = -1f
                    prevDiffX = -1f
                    prevDiffY = -1f
                    direction = SwipeDirection.Undefined
                    return
                }
                flagHelper(start.toInt(), end.toInt(), direction == SwipeDirection.Horizontal)
                answer.isFound = true
                isFound = true
                foundWord = answer.wordVal
                break
            }
        }
        if (isFound){
            findViewById<TextView>(dictionary.getValue(foundWord)).setTextColor(resources.getColor(R.color.masClaro))
            if (++acertadas == dictionary.size) {
                //Finalizar juego
                //Mostrar diálogo de acierto
                val letra = resources.getStringArray(R.array.arrayLetrasActividades).getOrNull(2)?.trim()?.toString() ?: ""
                val puntos = resources.getStringArray(R.array.arrayPuntosActividades).getOrNull(2)?.trim()?.toString() ?: ""

                val mensaje = getString(R.string.felicitacion_actividad)+"\n-"+getString(R.string.letra)+" "+letra+"\n-"+puntos+" "+getString(R.string.puntos)

                generarDialogo(view, getString(R.string.tituloGenerarDialogo), mensaje) {
                    //Otorgar puntos si la actividad no se ha completado
                    val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
                    val valorVariable = sharedPreferences.getInt("ACTIVIDAD_3", 0)
                    if (valorVariable == 0) {
                        //Poner ACTIVIDAD_3 con valor 1
                        val editor = sharedPreferences.edit()
                        editor.putInt("ACTIVIDAD_3", 1)
                        editor.apply()

                        //Encontrar el ID del usuario y sumarle X puntos
                        val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", -1)

                        if (idUsuarioActual != -1L) {
                            val dbOperations = DbOperations(this)
                            val usuarioActual = dbOperations.getUserById(idUsuarioActual)

                            if (usuarioActual != null) {
                                val puntosActuales = usuarioActual.puntos
                                val nuevosPuntos = puntosActuales + puntos.toInt()

                                // Actualizar puntos del usuario en la base de datos
                                dbOperations.updateUserPoints(idUsuarioActual, nuevosPuntos)

                                //Actualizar valor de la actividad
                                dbOperations.updateActividad(idUsuarioActual,3)

                                //Actualizar la variable PUNTOS_USUARIO_ACTUAL en SharedPreferences
                                editor.putInt("PUNTOS_USUARIO_ACTUAL", nuevosPuntos)
                                editor.apply()
                            }
                        }
                    }

                    // Este bloque de código se ejecutará después de cerrar el diálogo
                    val intent = Intent(this, Mapa::class.java)
                    intent.putExtra("letraActividadCompletada" , "E");
                    startActivity(intent)
                }
            }
        } else {
            unselectLetters(start.toInt(), end.toInt(), direction == SwipeDirection.Horizontal)
        }

        initX = -1f
        initY = -1f
        diffX = -1f
        diffY = -1f
        direction = SwipeDirection.Undefined
    }

    private fun flagHelper(start: Int, end: Int, isHorizontal: Boolean){
        var _start = start
        var _end = end

        if (end < start){
            _start = end
            _end = start
        }
        if (isHorizontal){
            for (i in _start.._end){
                answerFlag[i / 10][i % 10] = true
            }
        }
        else{
            // step 10 to increment 10 cells horizontal since traversing vertically
            for (i in _start.._end step 10){
                answerFlag[i / 10][i % 10] = true
            }
        }
    }

    companion object {
        val answers = arrayOf(Word("ETXETXU"), Word("TXALUPA"), Word("ARRANTZA"), Word("PERIZENA"), Word("ETXEZARRA"), Word("MANUELENA"))
        const val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        const val dim = 10
        val dictionary = mapOf("ETXETXU" to R.id.etxetxu, "TXALUPA" to R.id.txalupa, "ARRANTZA" to R.id.arrantza, "PERIZENA" to R.id.perizena, "ETXEZARRA" to R.id.etxezarra, "MANUELENA" to R.id.manuelena)

        // A 10x10 Array of String, all set to V
        var boardValues = Array(dim) {Array(dim) {"V"} }

        // A 10x10 Array of Boolean, all set to false, representing if the letter has been found already
        var letterFlag = Array(dim) {Array(dim) { false } }

        // A 10x10 Array of Boolean, all set to false, representing if the answer has been found already
        var answerFlag = Array(dim) { Array(dim) { false } }
    }

    //GENERAR DIÁLOGO MODAL
    fun generarDialogo(
        view: View,
        titulo: String,
        contenido: String,
        callback: () -> Unit
    ) {
        val builder = AlertDialog.Builder(view.context, R.style.AlertDialogTheme)
        val inflater = LayoutInflater.from(view.context)
        val dialogView: View = inflater.inflate(R.layout.modal_layout, null)

        val titleTextView = dialogView.findViewById<TextView>(R.id.titleTextView)
        val contentTextView = dialogView.findViewById<TextView>(R.id.contentTextView)
        val acceptButton = dialogView.findViewById<Button>(R.id.acceptButton)

        titleTextView.text = titulo
        contentTextView.text = contenido

        val alertDialog = builder.setView(dialogView).create()
        alertDialog.setCanceledOnTouchOutside(false)

        acceptButton.setOnClickListener {
            // Cierra la ventana modal al hacer clic en el botón Aceptar
            alertDialog.dismiss()
            // Ejecutar la devolución de llamada
            callback.invoke()
        }

        alertDialog.show()
    }
}