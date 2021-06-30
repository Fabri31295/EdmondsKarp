import java.util.*

private const val DELIMITER = " "
private var cantidadNodos = 0

data class Pair<A, B> (var first: A, var second: B)
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
    val map = HashMap<Int, MutableSet<Pair<Int,Int>>>()
    for(i in 1..cantidadArcos){
        sc.nextLine()?.let {
            val informacion = it.split(DELIMITER)
            map.getOrPut(informacion[0].toInt()) { mutableSetOf() }.add(Pair(informacion[1].toInt(),informacion[2].toInt()))
        }
    }
    println("${edmondsKarp(map,origen,destino)}")
}

private fun edmondsKarp(graph: HashMap<Int, MutableSet<Pair<Int, Int>>>, s: Int, t: Int): Int {
    var u: Int
    var v: Int

    val camino = IntArray(cantidadNodos + 1)
    var maxFlow = 0
    var newFlow = bfs(graph,s,t,camino)
    while (newFlow > 0) {
        v = t
        while (v != s) {
            u = camino[v]
            val adyacentes = graph[u]
            if (adyacentes != null) {
                for(par in adyacentes){
                    if(par.first == v){
                        par.second -= newFlow
                        break
                    }
                }
            }
            val adyacentesV = graph[v]
            var encontre = false
            if(adyacentesV != null){
                for(par in adyacentesV){
                    if(par.first == u){
                        encontre = true
                        par.second+= newFlow
                        break
                    }
                }
                if(!encontre)
                    graph.getOrPut(v) {mutableSetOf()}.add(Pair(u,newFlow))
            }else
                graph.getOrPut(v) {mutableSetOf()}.add(Pair(u,newFlow))
            v = camino[v]
        }

        maxFlow += newFlow
        newFlow = bfs(graph,s,t,camino)
    }

    return maxFlow
}

private fun bfs(grafo: HashMap<Int, MutableSet<Pair<Int, Int>>>, s: Int, t: Int, camino: IntArray): Int {
    val visitado = BooleanArray(cantidadNodos+1)
    for (i in 0 until cantidadNodos+1) visitado[i] = false

    val cola = LinkedList<Pair<Int,Int>>()
    cola.add(Pair(s,Int.MAX_VALUE))
    visitado[s] = true
    camino[s] = -1

    while (cola.size != 0) {
        val pair = cola.poll()
        val u = pair.first
        val flow = pair.second
        for (par in grafo[u]!!) {
            val v = par.first
            if (!visitado[v] && par.second > 0) {
                val newFlow = flow.coerceAtMost(par.second)
                if (v == t) {
                    camino[v] = u
                    return newFlow
                }
                cola.add(Pair(v,newFlow))
                camino[v] = u
                visitado[v] = true
            }
        }
    }
    return 0
}

