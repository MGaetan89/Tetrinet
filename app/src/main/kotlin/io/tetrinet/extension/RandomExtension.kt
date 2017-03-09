package io.tetrinet.extension

import java.util.Random

fun <T> Random.pick(array: Array<T>) = array[this.nextInt(array.size)]
