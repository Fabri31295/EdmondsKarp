import java.util.*

private const val DELIMITER = " "
private var cantidadNodos = 0
fun main(args: Array<String>) {

        val cantidadArcos: Int
        val origen: Int
        val destino: Int
        var line : String
        val sc = Scanner(System.`in`)
        line = sc.nextLine()
        cantidadNodos = line.split(DELIMITER)[0].toInt()
        cantidadArcos = line.split(DELIMITER)[1].toInt()
        line = sc.nextLine()
        origen = line.split(DELIMITER)[0].toInt()
        destino = line.split(DELIMITER)[1].toInt()
        val listaDeArcos: MutableList<String> = mutableListOf()
        for(i in 1..cantidadArcos){
            sc.nextLine()?.let { listaDeArcos.add(it) }
        }
        println("${edmondsKarp(formarGrafoMatriz(listaDeArcos, cantidadNodos),origen-1,destino-1)}")
    }

    private fun formarGrafoMatriz(listaDeArcos : MutableList<String>,cantidadNodos: Int) : Array<IntArray>{
        val grafo = Array(cantidadNodos) { IntArray(cantidadNodos) }
        var u = 0
        var v: Int
        while(u < cantidadNodos){
            v = 0
            while(v < cantidadNodos){
                grafo[u][v] = 0
                v++
            }
            u++
        }
        for(linea in listaDeArcos){
            val nodoOrigen = linea.split(DELIMITER)[0].toInt()
            val nodoDestino = linea.split(DELIMITER)[1].toInt()
            val capacidad = linea.split(DELIMITER)[2].toInt()
            grafo[nodoOrigen-1][nodoDestino-1] = capacidad
        }
        return grafo
    }

fun edmondsKarp(graph: Array<IntArray>, s: Int, t: Int): Int {
    var u: Int
    var v: Int

    val grafoResidual = Array(cantidadNodos) { IntArray(cantidadNodos) }
    u = 0
    while (u < cantidadNodos) {
        v = 0
        while (v < cantidadNodos) {
            grafoResidual[u][v] = graph[u][v]
            v++
        }
        u++
    }

    val camino = IntArray(cantidadNodos)
    var maxFlow = 0

    while (bfs(grafoResidual, s, t, camino)) {
        var caminoDeFlujo = Int.MAX_VALUE
        v = t
        while (v != s) {
            u = camino[v]
            caminoDeFlujo = caminoDeFlujo.coerceAtMost(grafoResidual[u][v])
            v = camino[v]
        }

        v = t
        while (v != s) {
            u = camino[v]
            grafoResidual[u][v] -= caminoDeFlujo
            grafoResidual[v][u] += caminoDeFlujo
            v = camino[v]
        }

        maxFlow += caminoDeFlujo
    }

    return maxFlow
}

fun bfs(grafoResidual: Array<IntArray>, s: Int, t: Int, camino: IntArray): Boolean {
    val visitado = BooleanArray(cantidadNodos)
    for (i in 0 until cantidadNodos) visitado[i] = false

    val cola = LinkedList<Int>()
    cola.add(s)
    visitado[s] = true
    camino[s] = -1

    while (cola.size != 0) {
        val u = cola.poll()
        for (v in 0 until cantidadNodos) {
            if (!visitado[v] && grafoResidual[u][v] > 0) {
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

