package pl.khuzzuk.functions

import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger

class ForceGateSpec extends Specification {
    def "check initialization validation exception"() {
        when:
        ForceGate.of(invalidNumber, action1, action2)

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
        ForceGate multiGate = ForceGate.of(2, {atomicInteger.set(1)}, {atomicInteger.set(0)}, true)

        when:
        multiGate.on()
        multiGate.on()

        then:
        atomicInteger.get() == 1
    }

    def "set on with triple gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        ForceGate multiGate = ForceGate.of(3, {atomicInteger.set(1)}, {atomicInteger.set(0)}, true)

        when:
        multiGate.on()
        multiGate.on()
        multiGate.on()

        then:
        atomicInteger.get() == 1
    }

    def "set on with big multi gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        ForceGate multiGate = ForceGate.of(10, {atomicInteger.set(1)}, {atomicInteger.set(0)}, true)

        when:
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()

        then:
        atomicInteger.get() == 1
    }

    def "set on and off with triple gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        ForceGate multiGate = ForceGate.of(3, {atomicInteger.set(1)}, {atomicInteger.set(0)}, true)

        when:
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.off()

        then:
        atomicInteger.get() == 0
    }

    def "set on and off with big multi gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        ForceGate multiGate = ForceGate.of(10, {atomicInteger.set(1)}, {atomicInteger.set(0)}, true)

        when:
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.on()
        multiGate.off()

        then:
        atomicInteger.get() == 0
    }

    def "set repeatable on and off with double gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        ForceGate multiGate = ForceGate.of(2, {atomicInteger.addAndGet(1)}, MultiGate.EMPTY_ACTION, true)

        when:
        multiGate.on()
        multiGate.on()
        multiGate.off()
        multiGate.on()

        then:
        atomicInteger.get() == 2
    }

    def "set nonRepeatable on and off with double gate"() {
        given:
        AtomicInteger atomicInteger = new AtomicInteger(0)
        ForceGate multiGate = ForceGate.of(2, {atomicInteger.addAndGet(1)}, MultiGate.EMPTY_ACTION, false)

        when:
        multiGate.on()
        multiGate.on()
        multiGate.off()
        multiGate.on()

        then:
        atomicInteger.get() == 1
    }
}
