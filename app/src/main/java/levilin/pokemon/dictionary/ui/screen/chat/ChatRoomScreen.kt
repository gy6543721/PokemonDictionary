package levilin.pokemon.dictionary.ui.screen.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.ui.screen.chat.component.ChatMessageInputBar
import levilin.pokemon.dictionary.ui.screen.chat.component.ChatMessageList

@Composable
fun ChatRoomScreen(
    chatRoomViewModel: ChatRoomViewModel = hiltViewModel()
) {
    val chatState by chatRoomViewModel.chatState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            ChatMessageInputBar(
                onSendMessage = { inputText ->
                    chatRoomViewModel.sendMessage(userMessage = inputText)
                },
                resetScroll = {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Messages List
            ChatMessageList(
                chatMessages = chatState.chatMessages,
                listState = listState
            )
        }
    }
}