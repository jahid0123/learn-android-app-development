import 'package:calculator_app_using_flutter/button_values.dart';
import 'package:flutter/material.dart';
class CalculatorScreen extends StatefulWidget {
  const CalculatorScreen({super.key});

  @override
  State<CalculatorScreen> createState() => _CalculatorScreenState();
}

class _CalculatorScreenState extends State<CalculatorScreen> {
  String number1 = ""; //. 0-9
  String operand = ""; //+ - * /
  String number2 = ""; //0-9
  @override
  Widget build(BuildContext context) {
    final screenSize = MediaQuery
        .of(context)
        .size;
    return Scaffold(
      extendBody: true,
      extendBodyBehindAppBar: true,
      body: SafeArea(
          bottom: false,
          child: Column(
            children: [
              //output

              Expanded(
                child: SingleChildScrollView(
                  reverse: true,
                  child: Container(
                    alignment: Alignment.bottomRight,
                    padding: const EdgeInsets.all(16),
                    child: Text(
                      "$number1$operand$number2".isEmpty
                          ? "0"
                          : "$number1$operand$number2",
                      style: const TextStyle(
                        fontSize: 48,
                        fontWeight: FontWeight.bold,
                      ),
                      textAlign: TextAlign.end,
                    ),
                  ),
                ),
              ),

              //input
              Wrap(
                  children:
                  Btn.buttonValues.map(
                          (value) =>
                          SizedBox(
                            width: value == Btn.n0
                                ? screenSize.width / 2
                                : screenSize.width / 4,
                            height: screenSize.width / 5,
                            child: buildButton(value),
                          )
                  ).toList()
              )
            ],
          )
      ),
    );
  }

  Widget buildButton(value) {
    return Padding(
      padding: const EdgeInsets.all(4.0),
      child: Material(
        color: getBtnColor(value),
        clipBehavior: Clip.hardEdge,
        shape: OutlineInputBorder(
          borderSide: const BorderSide(
            color: Colors.white24,
          ),
          borderRadius: BorderRadius.circular(100),
        ),

        child: InkWell(
          onTap: () => onBtnTap(value),
          child: Center(
            child: Text(
              value,
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 30,),),
          ),
        ),
      ),
    );
  }

  //###
  void appendValue (String value){
    if (value != Btn.dot && int.tryParse(value) == null) {
      if (operand.isNotEmpty && number2.isNotEmpty) {

      }
      operand = value;
    } else if (number1.isEmpty || operand.isEmpty) {
      if (value == Btn.dot && number1.contains(Btn.dot)) return;
      if (value == Btn.dot && number1.isEmpty || number1 == Btn.dot) {
        value = "0.";
      }
      number1 += value;
    } else if (number2.isEmpty || operand.isNotEmpty) {
      if (value == Btn.dot && number2.contains(Btn.dot)) return;
      if (value == Btn.dot && number2.isEmpty || number2 == Btn.dot) {
        value = "0.";
      }
      number2 += value;
    }
    setState(() {});
  }

  //####
  void onBtnTap(String value) {
    if(value == Btn.del){
      delete();
      return;
    }

    if(value==Btn.clr){
      clearAll();
      return;
    }

    if(value == Btn.per){
      convertPercentage();
      return;
    }

    if(value == Btn.calculate){
      calculate();
      return;
    }

   appendValue(value);
  }

  //convert to percent
  void convertPercentage(){

    if(number1.isNotEmpty && number2.isNotEmpty){
      calculate();
    }
    if(operand.isNotEmpty){
      return;
    }
    final number = double.parse(number1);
    setState(() {
      number1 = "${(number/100)}";
      operand = "";
      number2 = "";
    });
  }

  //calculate two number
  void calculate(){
    if(number1.isEmpty) return;
    if(operand.isEmpty) return;
    if(number2.isEmpty) return;

    final double num1 = double.parse(number1);
    final double num2 = double.parse(number2);

    var result = 0.0;
    switch(operand){
      case Btn.add:
        result = num1 + num2;
        break;
      case Btn.subtract:
        result = num1 - num2;
        break;
      case Btn.multiply:
        result = num1 * num2;
        break;
      case Btn.divide:
        result = num1 / num2;
        break;
      default:
    }
    setState(() {
      number1 = "$result";
      if(number1.endsWith(".0")){
        number1 = number1.substring(0, number1.length-2);
      }
      operand = "";
      number2 = "";
    });
  }

  //clear all output
  void clearAll(){
    setState(() {
      number1 = "";
      operand = "";
      number2 = "";
    });
  }


  //delete ontap to last element
  void delete(){
    if(number2.isNotEmpty){
      number2 = number2.substring(0, number2.length - 1);
    }else if(operand.isNotEmpty){
      operand = "";
    }else if(number1.isNotEmpty){
      number1 = number1.substring(0, number1.length - 1);
    }
    setState(() {});
  }

//Btn color method
  Color getBtnColor(value) {
    return [Btn.del, Btn.clr].contains(value)
        ? Colors.blueGrey
        : [
      Btn.per,
      Btn.multiply,
      Btn.add,
      Btn.divide,
      Btn.subtract,
      Btn.calculate].contains(value)
        ? Colors.orange
        : Colors.black87;
  }
}

