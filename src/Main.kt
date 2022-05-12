class Main {

    // fonction binaire qui prend deux arg
    // k : unit -> A
    // f : A -> (unit -> B)
    // f : unit -> B
    fun <B, A> unfold(b: B, f: (B) -> Pair<A, B>?): List<A> =
    f(b)?.let { (a, b) -> listOf(a) + unfold(b, f) } ?: emptyList()

    fun <A, B> bind(effectful: (Unit) -> A, pure2Effect: (A) -> ((Unit) -> B) ): (Unit) -> (Unit) -> B {
        return fun(Unit) :  (Unit) -> B {
            var result = effectful(Unit)
            return pure2Effect(result)
        }
    }

    fun <A> run(effect: (Unit) -> A) : (Unit) -> A = effect


    fun main() {
        val effect_A: (Unit) -> (Unit) = println("A")
        val effect_B: Unit = println("B")

        val effect_C: Unit = println("C")
        run ( bind(effect_A, effect_B ))
    }


}