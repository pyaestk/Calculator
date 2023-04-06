package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var expressionBuilder = StringBuilder()
    var addOperation = true
    var addDecimal = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            //number
            zero.setOnClickListener{
                numAdd("0")
                showExpression()
            }
            onw.setOnClickListener{
                numAdd("1")
                showExpression()
            }
            two.setOnClickListener{
                numAdd("2")
                showExpression()
            }
            three.setOnClickListener{
                numAdd("3")
                showExpression()
            }
            four.setOnClickListener{
                numAdd("4")
                showExpression()
            }
            five.setOnClickListener{
                numAdd("5")
                showExpression()
            }
            six.setOnClickListener{
                numAdd("6")
                showExpression()
            }
            seven.setOnClickListener{
                numAdd("7")
                showExpression()
            }
            eight.setOnClickListener{
                numAdd("8")
                showExpression()
            }
            nine.setOnClickListener{
                numAdd("9")
                showExpression()
            }
            decimalpoint.setOnClickListener{
                numAdd(".")
                showExpression()
            }

            //operator
            plus.setOnClickListener{
                opAdd("+")
                showExpression()
            }
            minus.setOnClickListener{
                opAdd("-")
                showExpression()
            }
            div.setOnClickListener{
                opAdd("/")
                showExpression()
            }
            multi.setOnClickListener{
                opAdd("x")
                showExpression()
            }

            equal.setOnClickListener{
                calculate()
            }


            //features
            del.setOnClickListener{
                if(binding.Expression.length() > 0){
                    expressionBuilder.delete(expressionBuilder.length-1, expressionBuilder.length)
                    binding.Expression.text = expressionBuilder.toString()
                }
            }
            allClear.setOnClickListener{
                expressionBuilder.clear()
                addOperation = true
                addDecimal = true
                binding.Result.text = ""
                binding.Expression.text = ""
            }
        }
    }

    fun numAdd(num: String){
        if (num == "."){
            if (addDecimal){
                expressionBuilder.append(num)
            }
            addDecimal = false
        }
        else{
            expressionBuilder.append(num)
            addOperation = true
        }
    }

    fun opAdd(num: String){

        if (num=="+"||num=="-"||num=="x"||num=="/"){

            if (addOperation && expressionBuilder.isNotBlank()){
                expressionBuilder.append(num)
            }
            addOperation = false
            addDecimal = true
        }
    }
    fun showExpression(){
        binding.Expression.text = expressionBuilder.toString()
    }

    fun calculate(){
        var finalResult = addSubAction(multiSubAction())
        binding.Result.text = finalResult
    }

    fun multiSubAction(): MutableList<Any>{

        var temExp = StringBuilder()
        var array = mutableListOf<Any>()

        for (i in expressionBuilder.indices){
            if (expressionBuilder[i].isDigit() || expressionBuilder[i]=='.'){
                temExp.append(expressionBuilder[i])
            }
            else{
                array.add(temExp.toString())
                temExp.clear()
                array.add(expressionBuilder[i])
            }
        }
        array.add(temExp.toString().toDouble())


        var multiSubArray = mutableListOf<Any>()
        var temp = 0.0
        var canSkip = true

        for (i in array.indices){
            if (canSkip){
                if (array[i].equals('x')||array[i].equals('/')){
                    var firstNum = multiSubArray[multiSubArray.lastIndex].toString().toDouble()
                    var secNum = array[i+1].toString().toDouble()
                    when(array[i]){
                        'x'->temp = firstNum * secNum
                        '/'->temp = firstNum / secNum
                    }
                    multiSubArray.removeAt(multiSubArray.lastIndex)
                    multiSubArray.add(temp)
                    canSkip = false

                }else{
                    multiSubArray.add(array[i])
                }
                continue
            }else{
                canSkip= true
            }
        }

        return multiSubArray
    }

    fun addSubAction(array: MutableList<Any>): String{

        var addSubArray = mutableListOf<Any>()
        var temp = 0.0
        var canSkip = true
        var finalString =""

        for (i in array.indices){
            if (canSkip){
                if (array[i].equals('+')||array[i].equals('-')){
                    var firstNum = addSubArray[addSubArray.lastIndex].toString().toDouble()
                    var secNum = array[i+1].toString().toDouble()
                    when(array[i]){
                        '+'->temp = firstNum + secNum
                        '-'->temp = firstNum - secNum
                    }
                    addSubArray.removeAt(addSubArray.lastIndex)
                    addSubArray.add(temp)
                    canSkip = false

                }else{
                    addSubArray.add(array[i])
                }
                continue
            }else{
                canSkip= true
            }
        }
        for(i in addSubArray.indices){
            finalString += addSubArray[i].toString()
        }
        return finalString
    }
}