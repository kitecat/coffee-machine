package machine

data class Resources(
    var water: Int,
    var milk: Int,
    var coffeeBeans: Int,
    var cups: Int,
    var money: Int
)

enum class CoffeeTypes(
    val coffeeName: String,
    val water: Int,
    val milk: Int,
    val coffeeBeans: Int,
    val cups: Int,
    val money: Int) {
        
    ESPRESSO("Espresso", 250, 0, 16, 1, 4),
    LATTE("Latte", 350, 75, 20, 1, 7),
    CAPPUCCINO("Cappuccino", 200, 100, 12, 1, 6);
}

enum class Status(val description: String) {
    ACTION("choosing an action"),
    BUY("choosing a variant of coffee"),
    REMAINING("displaying remaining ingredients"),
    FILL("filling machine with ingredients"),
    TAKE("taking money from machine"),
    EXIT("switch off machine"),
    ERROR("unknown action")
}

class CoffeeMachine(var resources: Resources) {
    var status: Status = Status.ACTION
    
    fun run() {
        while (status != Status.EXIT) {
            when (status) {
                Status.ACTION -> action()
                Status.BUY -> buy()
                Status.FILL -> fill()
                Status.REMAINING -> printResources()
                Status.TAKE -> take()
                else -> println("Invalid Input")
            }
        }
    }
    
    private fun action() {
        print("Write action (buy, fill, take, remaining, exit): ")
        val action = readLine()!!
        status = when (action) {
            "buy" -> Status.BUY
            "fill" -> Status.FILL
            "take" -> Status.TAKE
            "remaining" -> Status.REMAINING
            "exit" -> Status.EXIT
            else -> Status.ERROR
        }
    }
    
    private fun buy() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
        val choice = readLine()!!
        status = Status.ACTION
        when (choice) {
            "1" -> makeCoffee(CoffeeTypes.ESPRESSO)
            "2" -> makeCoffee(CoffeeTypes.LATTE)
            "3" -> makeCoffee(CoffeeTypes.CAPPUCCINO)
            else -> return
        }
    }
    
    private fun fill() {
        println("Write how many ml of water do you want to add: ")
        resources.water += readLine()!!.toInt()
        
        println("Write how many ml of milk do you want to add: ")
        resources.milk += readLine()!!.toInt()
        
        println("Write how many grams of coffee beans do you want to add: ")
        resources.coffeeBeans += readLine()!!.toInt()
        
        println("Write how many disposable cups of coffee do you want to add: ")
        resources.cups += readLine()!!.toInt()
        
        status = Status.ACTION
    }
    
    private fun take() {
        println("I gave you $${resources.money}")
        resources.money = 0
        this.status = Status.ACTION
    }
    
    private fun makeCoffee(coffeeType: CoffeeTypes) {
        if (resources.water < coffeeType.water) {
            println("Sorry, not enough water!")
        } else if (resources.milk < coffeeType.milk) {
            println("Sorry, not enough milk!")
        } else if (resources.coffeeBeans < coffeeType.coffeeBeans) {
            println("Sorry, not enough coffee beans!")
        } else if (resources.cups < 1) {
            println("Sorry, not enough disposable cups!")
        } else {
            resources.water -= coffeeType.water
            resources.milk -= coffeeType.milk
            resources.coffeeBeans -= coffeeType.coffeeBeans
            resources.cups--
            resources.money += coffeeType.money
            println("I have enough resources, making you a coffee!")
        }
    }
    
    private fun printResources() {
        println("The coffee machine has:")
        println("${resources.water} of water")
        println("${resources.milk} of milk")
        println("${resources.coffeeBeans} of coffee beans")
        println("${resources.cups} of disposable cups")
        println("${resources.money} of money")
        status = Status.ACTION
    }
}

fun main() {
    val resources = Resources(400, 540, 120, 9, 550)
    val coffeeMachine = CoffeeMachine(resources)
    coffeeMachine.run()
}
