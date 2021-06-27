import java.util.*

private const val DELIMITER = " "

    fun main(args: Array<String>) {

        val cantidadNodos: Int
        val cantidadArcos: Int
        val nodoSource: Int
        val nodoSink: Int
        var line : String

        val sc = Scanner(System.`in`)
        line = sc.nextLine()
        cantidadNodos = line.split(DELIMITER)[0].toInt()
        cantidadArcos = line.split(DELIMITER)[1].toInt()
        line = sc.nextLine()
        nodoSource = line.split(DELIMITER)[0].toInt()
        nodoSink = line.split(DELIMITER)[1].toInt()
        val edgeList: MutableList<String> = mutableListOf()
        for(i in 1..cantidadArcos){
            sc.nextLine()?.let { edgeList.add(it) }
        }
    }

    private fun edmondsKarp(s: Int, t: Int, edgeList: MutableList<String>) : Int {
        var flujoMaximo = 0


        return flujoMaximo
    }

    private fun getList(str: String): List<String> {
        val list = str.split(DELIMITER)
        return list
    }

    private fun bfs(s: Int, t: Int, edgeList: MutableList<String>) {
        var queue: Queue<Int>

    }


