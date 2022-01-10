package com.eanam.practice.state_widget

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * 页面状态
 */
class PageState<T>(state: PageData<T>) {

    //内部交互的状态: 状态单向流动，不让外部随意更改
    internal var interactionState by mutableStateOf(state)

    /**
     * 供外部获取的页面状态
     */
    val state: PageData<T>
        get() = interactionState

    val isLoading: Boolean
        get() = interactionState is PageData.Loading
}