package com.eanam.practice.state_widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch


//用于缓存页面状态
@Composable
fun<T> rememberPageState(state: PageData<T> = PageData.Loading): PageState<T> {
    return rememberSaveable {
        PageState(state)
    }
}

/**
 * 状态组件
 */
@Composable
fun<T> ComposeState(
    modifier: Modifier,
    pageState: PageState<T>,
    loading: suspend () -> Unit,
    loadingComponentBlock: @Composable (BoxScope.() -> Unit)?,
    emptyComponentBlock: @Composable (BoxScope.(PageData.Empty) -> Unit)?,
    errorComponentBlock: @Composable (BoxScope.(PageData.Error) -> Unit)?,
    contentComponentBlock: @Composable (BoxScope.(PageData.Success<T>) -> Unit)
){
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        when (pageState.interactionState) {
            is PageData.Success -> contentComponentBlock(pageState.interactionState as PageData.Success<T>)
            is PageData.Loading -> {
                loadingComponentBlock?.invoke(this)
                scope.launch {
                    loading.invoke()
                }
            }
            is PageData.Empty -> emptyComponentBlock?.invoke(
                this,
                pageState.interactionState as PageData.Empty
            )
            is PageData.Error -> StateBoxCompose(
                { pageState.interactionState = PageData.Loading }
            ) {
                errorComponentBlock?.invoke(
                    this,
                    pageState.interactionState as PageData.Error
                )
            }
        }
    }
}

@Composable
fun StateBoxCompose(block: () -> Unit, content: @Composable BoxScope.() -> Unit) {
    Box(
        Modifier.clickable {
            block.invoke()
        },
        content = content
    )
}