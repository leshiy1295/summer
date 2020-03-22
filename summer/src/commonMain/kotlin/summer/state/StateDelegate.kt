package summer.state

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StateDelegate<T, TOwner>(
    private val owner: TOwner,
    private val property: KProperty<*>,
    private val onSet: (T) -> Unit,
    private val initial: T,
    private val strategy: SummerStateStrategy<TOwner, T>
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val isInit = strategy.isInit(owner, property)
        return if (isInit) {
            strategy.get(owner, property)
        } else {
            initial
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        strategy.set(owner, property, value)
        onSet(value)
    }

    fun restore() {
        val isInit = strategy.isInit(owner, property)
        if (isInit) {
            val value = strategy.get(owner, property)
            onSet(value)
        } else {
            onSet(initial)
        }
    }
}

class StateDelegateProvider<T, TOwner>(
    private val owner: TOwner,
    private val onSet: (T) -> Unit,
    private val initial: T,
    private val strategy: SummerStateStrategy<TOwner, T>,
    private val delegateCreated: (StateDelegate<T, TOwner>) -> Unit
) {
    operator fun provideDelegate(
        thisRef: Any?,
        prop: KProperty<*>
    ): StateDelegate<T, TOwner> {
        val delegate = StateDelegate(owner, prop, onSet, initial, strategy)
        delegateCreated(delegate)
        return delegate
    }
}