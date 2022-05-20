package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var screen_view: TextView? = null;
    var lastNumericFlag:Boolean = false;
    var lastDotFlag:Boolean = false;
    var expEvaluated = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screen_view = findViewById<TextView>(R.id.screen);
    }
    fun onDigit(view: View){

        if(expEvaluated){
            screen_view?.text = "";
            expEvaluated = false;
        }
        screen_view?.append((view as Button).text);
        lastNumericFlag = true;
        lastDotFlag = false;
    }
    fun onOperator(view:View){
        screen_view?.text?.let{
            if(lastNumericFlag && !operatorAdded(it.toString())){
                screen_view?.append((view as Button).text);
                lastNumericFlag = false;
                lastDotFlag = false;
            }
        }
    }
    fun onDecimalPoint(view:View){
        if(lastNumericFlag && !lastDotFlag){
            screen_view?.append(".");
            lastNumericFlag = false;
            lastDotFlag = true;
        }
    }
    fun onEqual(view:View){
        if(lastNumericFlag){
            var exp = screen_view?.text.toString();
            screen_view?.append((view as Button).text);
            try {
                if(exp.contains("/")){
                    var tokens = exp.split("/");
                    var num1 = tokens[0];
                    var num2 = tokens[1];

                    screen_view?.append( (num1.toDouble()/num2.toDouble()).toString());
                }else if(exp.contains("*")){
                    var tokens = exp.split("*");
                    var num1 = tokens[0];
                    var num2 = tokens[1];

                    screen_view?.append( (num1.toDouble()*num2.toDouble()).toString());
                }else if(exp.contains("+")){
                    var tokens = exp.split("+");
                    var num1 = tokens[0];
                    var num2 = tokens[1];

                    screen_view?.append( (num1.toDouble()+num2.toDouble()).toString());
                }else if(exp.contains("-")){
                    var tokens = exp.split("-");
                    var num1 = tokens[0];
                    var num2 = tokens[1];

                    screen_view?.append( (num1.toDouble()-num2.toDouble()).toString());
                }
                lastNumericFlag = false;
                lastDotFlag = false;
                expEvaluated = true;
            }catch (e:ArithmeticException){
                e.printStackTrace()
            }

        }
    }
    fun onClear(view:View){
        screen_view?.text = "";
        lastNumericFlag = false;
        lastDotFlag = false;
    }
    fun operatorAdded(value:String): Boolean {
        return if(value.startsWith("-")){
            false;
        }else{
            value.contains("/") || value.contains("*") ||value.contains("+") ||value.contains("-");
        }
    }
}