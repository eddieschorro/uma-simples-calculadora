package com.example.minhacalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var lastCharIsDot = false
    var lastCharIsNumber = false

    // pass a view to the fun. this view is assigned as the Button (which is a view)
    fun onDigit(view: View) {
        tv_input.append((view as Button).text)
        lastCharIsNumber = true
    }

    // same as above
    fun onClear(view: View) {
        tv_input.text = ""
        var lastCharIsDot = false
        var lastCharIsNumber = false
    }

    fun onDot(view: View) {
        if (!lastCharIsDot && lastCharIsNumber) {
            tv_input.append(".")
            lastCharIsDot = true
            lastCharIsNumber = false
        }
    }

    fun onOperator(view: View) {
        if (lastCharIsNumber && !isOperatorAdded(tv_input.text.toString())) {
            tv_input.append((view as Button).text)
            lastCharIsNumber = false
            lastCharIsDot = false
        }
    }

    fun onEqual(view: View) {
        if (lastCharIsNumber) {

            var conteudoTvText =
                tv_input.text.toString()   // stores the value of the whole content shown on screen
            var prefix = "" // apenas para fazer um esquema abaixo no startsWith

            try {
                // validação apenas para garantir que o - no começo do texto não interfira na operação
                if (conteudoTvText.startsWith("-")) {
                    prefix = "-"
                    conteudoTvText = conteudoTvText.substring(1)

                }

                if (tv_input.text.contains("-")) {

                    val split = conteudoTvText.split("-")

                    var ladoEsquerdoDaConta = split[0]
                    var ladoDireiroDaConta = split[1]

                    if (!prefix.isEmpty()) {
                        ladoEsquerdoDaConta = prefix + ladoEsquerdoDaConta
                    }

                    tv_input.text =
                        (ladoEsquerdoDaConta.toInt() - ladoDireiroDaConta.toInt()).toString()

                }
            } catch (e: ArithmeticException) {
                e.stackTrace
            }
        }
    }

    // validation for before adding an operator to the tv_input
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("*") || value.contains("/") || value.contains("+ ") || value.contains(
                "-"
            )
        }
    }
}