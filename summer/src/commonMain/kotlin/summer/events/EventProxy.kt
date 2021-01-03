package summer.events

import summer.GetViewProvider

/**
 * Proxy that calls action following defined [EventProxyStrategy].
 * Can proxy actions with 0-12 arguments. Have child to each supported arity.
 *
 * Implementations are generated by SummerEventGenerator.kts
 */
class EventProxy<TView, in TOwner>(
    private val getViewEvent: (TView) -> Function<Unit>,
    private val owner: TOwner,
    private val getViewProvider: GetViewProvider<TView>,
    private val listener: EventProxyListener<TView, TOwner>?,
    private val strategy: EventProxyStrategy<TView, TOwner>,
) : () -> Unit,
        (Any?) -> Unit,
        (Any?, Any?) -> Unit,
        (Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit,
        (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit {

    override fun invoke() {
        executeEvent()
    }

    override fun invoke(p1: Any?) {
        executeEvent(p1)
    }

    override fun invoke(p1: Any?, p2: Any?) {
        executeEvent(p1, p2)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?) {
        executeEvent(p1, p2, p3)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?) {
        executeEvent(p1, p2, p3, p4)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?) {
        executeEvent(p1, p2, p3, p4, p5)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?, p6: Any?) {
        executeEvent(p1, p2, p3, p4, p5, p6)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?, p6: Any?, p7: Any?) {
        executeEvent(p1, p2, p3, p4, p5, p6, p7)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?, p6: Any?, p7: Any?, p8: Any?) {
        executeEvent(p1, p2, p3, p4, p5, p6, p7, p8)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?, p6: Any?, p7: Any?, p8: Any?, p9: Any?) {
        executeEvent(p1, p2, p3, p4, p5, p6, p7, p8, p9)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?, p6: Any?, p7: Any?, p8: Any?, p9: Any?, p10: Any?) {
        executeEvent(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?, p6: Any?, p7: Any?, p8: Any?, p9: Any?, p10: Any?, p11: Any?) {
        executeEvent(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)
    }

    override fun invoke(p1: Any?, p2: Any?, p3: Any?, p4: Any?, p5: Any?, p6: Any?, p7: Any?, p8: Any?, p9: Any?, p10: Any?, p11: Any?, p12: Any?) {
        executeEvent(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)
    }

    private fun executeEvent(vararg params: Any?) {
        val execution = ViewEventExecution(getViewEvent, params, listener, owner, strategy)
        strategy.proxyInvoked(execution, owner, getViewProvider)
        listener?.proxyInvoked(strategy, execution, owner)
    }

    fun viewCreated() {
        strategy.viewCreated(owner, getViewProvider)
    }
}

