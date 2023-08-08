fun main() {
    val grid = StringBuilder()
    repeat(9) { grid.append(' ') }
    printGrid(grid.toString())

    var input = 'X'
    do {
        do {
            val valid = play(grid, input)
        } while(!valid)

        printGrid(grid.toString())
        input = if(input == 'X') 'O' else 'X'

    } while(!gameOver((grid)))
}

fun printGrid(grid: String){
    println("---------")
    for(i in 0 until grid.length step 3){
        println("| ${grid[i]} ${grid[i+1]} ${grid[i+2]} |")
    }
    println("---------")
}

fun play(grid: StringBuilder, input: Char): Boolean {
    val xy = readln()

    if(!xy[0].isDigit() || !xy[2].isDigit())
        println("You should enter numbers!")
    else {
        val i = getIndex(xy.filter { it.isDigit() })

        if(i == -1)
            println("Coordinates should be from 1 to 3!")
        else if(grid[i] != ' ')
            println("This cell is occupied! Choose another one!")
        else {
            grid[i] = input
            return true
        }
    }
    return false
}

fun getIndex(coordinate: String): Int = when(coordinate) {
    "11" -> 0
    "12" -> 1
    "13" -> 2
    "21" -> 3
    "22" -> 4
    "23" -> 5
    "31" -> 6
    "32" -> 7
    "33" -> 8
    else -> -1
}

data class Winner(var winX: Int, var winO: Int, var noWin: Int)

fun gameOver(grid: StringBuilder): Boolean {
    val sequence = StringBuilder()
    val winner = Winner(0,0,0)

    // 3 x vertical check
    for(i in 0..2){
        arrayOf(0,3,6).forEach { sequence.append(grid[i+it]) }
        countWinner(sequence, winner)
        sequence.clear()
    }

    // 3 x horizontal check
    for(i in 0..6 step 3){
        arrayOf(0,1,2).forEach { sequence.append(grid[i+it]) }
        countWinner(sequence, winner)
        sequence.clear()
    }

    // 1 x left diagonal check
    arrayOf(0,4,8).forEach { sequence.append(grid[it]) }
    countWinner(sequence, winner)
    sequence.clear()

    // 1 x right diagonal check
    arrayOf(2,4,6).forEach { sequence.append(grid[it]) }
    countWinner(sequence, winner)

    return when {
        winner.winX >= 1 -> { println("X wins"); true }
        winner.winO >= 1 -> { println("O wins"); true }
        winner.noWin > 0 -> false
        else -> { println("Draw"); true }
    }
}

fun countWinner(sequence: StringBuilder, winner: Winner) {
    if(sequence.count {it == 'X'} == 3)
        winner.winX += 1
    else if(sequence.count {it == 'O'} == 3)
        winner.winO += 1
    else if(sequence.count {it != 'X' && it != 'O'} > 0)
        winner.noWin += 1
}
