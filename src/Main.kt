import java.util.*

private const val DELIMITER = " "
private var cantidadNodos = 0
fun main(args: Array<String>) {

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
        val listaDeArcos: MutableList<String> = mutableListOf()
        for(i in 1..cantidadArcos){
            sc.nextLine()?.let { listaDeArcos.add(it) }
        }
        println("${edmondsKarp(formarGrafoMatriz(listaDeArcos, cantidadNodos),nodoSource-1,nodoSink-1)}")
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

    val rGraph = Array(cantidadNodos) { IntArray(cantidadNodos) }
    u = 0
    while (u < cantidadNodos) {
        v = 0
        while (v < cantidadNodos) {
            rGraph[u][v] = graph[u][v]
            v++
        }
        u++
    }

    // This array is filled by BFS and to store path
    val parent = IntArray(cantidadNodos)
    var maxFlow = 0 // There is no flow initially

    // Augment the flow while tere is path from source
    // to sink
    while (bfs(rGraph, s, t, parent)) {
        // Find minimum residual capacity of the edhes
        // along the path filled by BFS. Or we can say
        // find the maximum flow through the path found.
        var pathFlow = Int.MAX_VALUE
        v = t
        while (v != s) {
            u = parent[v]
            pathFlow = pathFlow.coerceAtMost(rGraph[u][v])
            v = parent[v]
        }

        // update residual capacities of the edges and
        // reverse edges along the path
        v = t
        while (v != s) {
            u = parent[v]
            rGraph[u][v] -= pathFlow
            rGraph[v][u] += pathFlow
            v = parent[v]
        }

        // Add path flow to overall flow
        maxFlow += pathFlow
    }

    // Return the overall flow
    return maxFlow
}

fun bfs(rGraph: Array<IntArray>, s: Int, t: Int, parent: IntArray): Boolean {
    // Create a visited array and mark all vertices as
    // not visited
    val visited = BooleanArray(cantidadNodos)
    for (i in 0 until cantidadNodos) visited[i] = false

    // Create a queue, enqueue source vertex and mark
    // source vertex as visited
    val queue = LinkedList<Int>()
    queue.add(s)
    visited[s] = true
    parent[s] = -1

    // Standard BFS Loop
    while (queue.size != 0) {
        val u = queue.poll()
        for (v in 0 until cantidadNodos) {
            if (!visited[v] && rGraph[u][v] > 0) {
                // If we find a connection to the sink
                // node, then there is no point in BFS
                // anymore We just have to set its parent
                // and can return true
                if (v == t) {
                    parent[v] = u
                    return true
                }
                queue.add(v)
                parent[v] = u
                visited[v] = true
            }
        }
    }
    return false
}

