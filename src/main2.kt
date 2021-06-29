import java.util.*

private const val DELIMITER = " "
private var cantNodos = 0
private lateinit var arcos: MutableList<Arco>

fun main(args: Array<String>) {

    var cantArcos: Int
    var s: Int
    var t: Int
    var line: String
    val sc = Scanner(System.`in`)

    println("Input: ")
    line = sc.nextLine()
    cantNodos = line.split(DELIMITER)[0].toInt()
    cantArcos = line.split(DELIMITER)[1].toInt()
    line = sc.nextLine()
    s = line.split(DELIMITER)[0].toInt()
    t = line.split(DELIMITER)[1].toInt()

    arcos = mutableListOf<Arco>()
    var nodoU: Int
    var nodoV: Int
    var edge: Arco

    for (i in 1..cantArcos) {
        line = sc.nextLine()
        nodoU = line.split(DELIMITER)[0].toInt()
        nodoV = line.split(DELIMITER)[1].toInt()
        edge = Arco(nodoU, nodoV, line.split(DELIMITER)[2].toInt())
        arcos.add(arcos.size, edge)
    }

    println("${edmondsKarp(arcos,s,t)}")

}

class Arco(private val u: Int, private val v: Int, private var n: Int) {
    fun getNum(): Int = n
    fun setNum(nx : Int) {
        n += nx
    }
    fun getOrigen(): Int = u
    fun getDestino(): Int = v
}

fun edmondsKarp(graph: MutableList<Arco>, s: Int, t: Int): Int {
    var u: Int
    var v: Int

    val grafoResidual : MutableList<Arco> = mutableListOf()

    var size = arcos.size-1
    for(i in 0..size){
        grafoResidual.add(grafoResidual.size, graph[i])
    }

    val camino = IntArray(cantNodos)
    var maxFlow = 0

    while (bfs(grafoResidual, s, t, camino)) {
        var caminoDeFlujo = Int.MAX_VALUE
        v = t
        while (v != s) {
            u = camino[v]
            caminoDeFlujo = caminoDeFlujo.coerceAtMost(getArco(grafoResidual,u,v).getNum())
            v = camino[v]
        }

        v = t
        while (v != s) {
            u = camino[v]
            getArco(grafoResidual,u,v).setNum(-caminoDeFlujo)
            getArco(grafoResidual,v,u).setNum(caminoDeFlujo)
            v = camino[v]
        }

        maxFlow += caminoDeFlujo
    }

    return maxFlow
}

fun bfs(grafoResidual: MutableList<Arco>, s: Int, t: Int, camino: IntArray): Boolean {
    val visitado = BooleanArray(cantNodos)
    for (i in 0 until cantNodos) visitado[i] = false

    val cola = LinkedList<Int>()
    cola.add(s)
    visitado[s] = true
    camino[s] = -1

    while (cola.size != 0) {
        val u = cola.poll()
        for (v in 0 until cantNodos) {
            if (!visitado[v] && hayArco(grafoResidual,u,v)) {
                if (v == t) {
                    camino[v] = u
                    return true
                }
                cola.add(v)
                camino[v] = u
                visitado[v] = true
            }
        }
    }
    return false
}

fun hayArco(grafo : MutableList<Arco>, u: Int, v: Int): Boolean {
    var size = grafo.size - 1
    for (i in 0..size) {
        if (grafo.get(i).getOrigen().equals(u) && grafo.get(i).getDestino().equals(v))
            return true
    }
    return false
}

fun getArco(grafo : MutableList<Arco>, u: Int, v:Int) : Arco {
    var arcoreturn = Arco(0,0,0)
    var size = grafo.size-1
    for (i in 0..size) {
        if (grafo.get(i).getOrigen().equals(u) && grafo.get(i).getDestino().equals(v))
            arcoreturn =  grafo.get(i)
    }
    return arcoreturn
}