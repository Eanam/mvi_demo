package com.eanam.practice.state_widget

/**
 * 状态页面的几种State
 */
sealed class PageData<out T> {
    data class Success<T>(val data: T? = null): PageData<T>()

    data class Error(
        val throwable: Throwable? = null,
        val value: Any? = null
    ): PageData<Nothing>()

    object Loading: PageData<Nothing>()

    data class Empty(val value: Any? = null): PageData<Nothing>()
}