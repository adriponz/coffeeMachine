enum class Status {
    INITIAL, BUYING, FILL_WATER, FILL_MILK, FILL_COFFEE, FILL_CUPS
}

class CoffeeMachine {
    var water: Int = 400
    var milk: Int = 540
    var coffee: Int = 120
    var cups: Int = 9
    var money: Int = 550
    var status: Status = Status.INITIAL

    fun execute(value: String) {
        if (status == Status.INITIAL) {
            executeAction(value)
        } else if (status == Status.BUYING) {
            buyCoffee(value)
        } else {
            fill(value.toInt())
        }
    }

    private fun buyCoffee(value: String) {
        when (value) {
            "1" -> buyCoffee(4, 250, 0, 16) // espresso
            "2" -> buyCoffee(7, 350, 75, 20) // latte
            "3" -> buyCoffee(6, 200, 100, 12) // cappuccino
            "back" -> setInitialStatus()
        }
    }

    private fun executeAction(value: String) {
        when (value) {
            "fill" -> initFill()
            "buy" -> buy()
            "take" -> take()
            "remaining" -> showStatus()
        }
    }

    private fun setInitialStatus() {
        println()
        println("Write action (buy, fill, take, remaining, exit):")
        status = Status.INITIAL
    }

    private fun showStatus() {
        println()
        println("The coffe machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$coffee g of coffee beans")
        println("$cups disposable cups")
        println("$$money of money")

        setInitialStatus()
    }

    private fun take() {
        println("I gave you $$money")
        money = 0
        setInitialStatus()
    }

    private fun buy() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")
        status = Status.BUYING
    }

    private fun buyCoffee(price: Int, waterNeeded: Int, milkNeeded: Int, coffeeNeeded: Int) {
        when {
            waterNeeded > water -> println("Sorry, not enough water!")
            milkNeeded > milk -> println("Sorry, not enough milk!")
            coffeeNeeded > coffee -> println("Sorry, not enough coffee!")
            cups == 0 -> println("Sorry, not enough cups!")
            else -> {
                water -= waterNeeded
                milk -= milkNeeded
                coffee -= coffeeNeeded
                cups--
                money += price
                println("I have enough resources, making you a coffee!")
            }
        }
        setInitialStatus()
    }

    private fun fill(quantity: Int) {
        when (status) {
            Status.FILL_WATER -> fillWater(quantity)
            Status.FILL_MILK -> fillMilk(quantity)
            Status.FILL_COFFEE -> fillCoffee(quantity)
            Status.FILL_CUPS -> fillCups(quantity)
            else -> return
        }
    }

    private fun initFill() {
        println("Write how many ml of water you want to add:")
        status = Status.FILL_WATER
    }

    private fun fillWater(quantity: Int) {
        water += quantity
        println("Write how many ml of milk you want to add:")
        status = Status.FILL_MILK
    }

    private fun fillMilk(quantity: Int) {
        milk += quantity
        println("Write how many grams of coffee beans you want to add:")
        status = Status.FILL_COFFEE
    }

    private fun fillCoffee(quantity: Int) {
        coffee += quantity
        println("Write how many disposable cups you want to add:")
        status = Status.FILL_CUPS
    }

    private fun fillCups(quantity: Int) {
        cups += quantity
        setInitialStatus()
    }
}

fun menu(machine: CoffeeMachine) {
    var input: String
    while (true) {
        input = readln()
        if (input == "exit") {
            return
        }
        machine.execute(input)
        println()
    }
}

fun main() {
    val machine = CoffeeMachine()
    println("Write action (buy, fill, take, remaining, exit):")
    menu(machine)
}