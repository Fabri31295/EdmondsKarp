import java.util.*

private const val delimiter = " "

    fun main(args: Array<String>) {

        var n: Int // cantidad de nodos (1..n)
        var m: Int // cantidad de arcos
        var s: Int // nodo source
        var t: Int // nodo sink
        var line : String

        val sc = Scanner(System.`in`)
        println("Input: ")
        line = sc.nextLine()
        n = line.split(delimiter)[0].toInt()
        m = line.split(delimiter)[1].toInt()
        line = sc.nextLine()
        s = line.split(delimiter)[0].toInt()
        t = line.split(delimiter)[1].toInt()



    }

    private fun getList(str: String): List<String> {
        val list = str.split(delimiter)
        return list
    }

    private fun bfs(s: Int, t: Int) {
        var queue: Queue<Int>
        
    }

