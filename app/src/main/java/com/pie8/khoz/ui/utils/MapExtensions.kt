package com.pie8.khoz.ui.utils

internal fun <E> MutableSet<E>.addOrRemove(element: E) {
    if (!add(element)) {
        remove(element)
    }
}
