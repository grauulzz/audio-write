package org.grauulzz

@main def Main(args: String*): Unit =
    println(Console.GREEN)

        object Calculator:
        // instance variable
        private var _calculatorsCreated: Int = 0
        // getter
        def calculatorsCreated: Int = _calculatorsCreated
        // setter
        private def calculatorsCreated_=(newValue: Int) =
        _calculatorsCreated = newValue
        
    class Calculator(a: Int = 0):
        // counter for how many instances created
        Calculator.calculatorsCreated += 1
        def add(b: Int): Int =
        a + b
        def sub(b: Int): Int = 
        a - b  

    val c1 = Calculator(1)
    val c2 = Calculator(1)
    
    println(c1.sub(1))

    println(Calculator.calculatorsCreated)    
    
    println(Console.RESET)
