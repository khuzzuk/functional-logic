package pl.khuzzuk.functions

import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger

class MultiGateSpec extends Specification {
    def "check initialization validation exception"() {
        when:
        MultiGate.of(invalidNumber, action1, action2)

        then:
        thrown IllegalArgumentException

        where:
        invalidNumber | action1 | action2
        -1            | null    | null
        0             | null    | null
        1             | null    | null
    }

    def "set on with double gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        MultiGate multiGate = MultiGate.of(2, {atomicInteger.set(1)}, {atomicInteger.set(0)})

        when:
        multiGate.on(0)
        multiGate.on(1)

        then:
        atomicInteger.get() == 1
    }

    def "set on with triple gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        MultiGate multiGate = MultiGate.of(3, {atomicInteger.set(1)}, {atomicInteger.set(0)})

        when:
        multiGate.on(0)
        multiGate.on(1)
        multiGate.on(2)

        then:
        atomicInteger.get() == 1
    }

    def "set on with big multi gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        MultiGate multiGate = MultiGate.of(10, {atomicInteger.set(1)}, {atomicInteger.set(0)})

        when:
        multiGate.on(0)
        multiGate.on(1)
        multiGate.on(2)
        multiGate.on(3)
        multiGate.on(4)
        multiGate.on(5)
        multiGate.on(6)
        multiGate.on(7)
        multiGate.on(8)
        multiGate.on(9)

        then:
        atomicInteger.get() == 1
    }

    def "set on and off with triple gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        MultiGate multiGate = MultiGate.of(3, {atomicInteger.set(1)}, {atomicInteger.set(0)})

        when:
        multiGate.on(0)
        multiGate.on(1)
        multiGate.on(2)
        multiGate.off(1)

        then:
        atomicInteger.get() == 0
    }

    def "set on and off with big multi gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        MultiGate multiGate = MultiGate.of(10, {atomicInteger.set(1)}, {atomicInteger.set(0)})

        when:
        multiGate.on(0)
        multiGate.on(1)
        multiGate.on(2)
        multiGate.on(3)
        multiGate.on(4)
        multiGate.on(5)
        multiGate.on(6)
        multiGate.on(7)
        multiGate.on(8)
        multiGate.on(9)
        multiGate.off(1)

        then:
        atomicInteger.get() == 0
    }
}
