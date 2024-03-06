package levilin.pokemon.dictionary.ui.screen.chat

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.R
import levilin.pokemon.dictionary.model.remote.chat.ChatMessage
import levilin.pokemon.dictionary.model.remote.chat.MessageType
import levilin.pokemon.dictionary.repository.remote.chat.GenerativeViewModelFactory
import levilin.pokemon.dictionary.viewmodel.chat.ChatRoomViewModel

@Composable
fun ChatRoomScreen(
    chatRoomViewModel: ChatRoomViewModel = viewModel(factory = GenerativeViewModelFactory)
) {
    val chatState by chatRoomViewModel.chatState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            MessageInput(
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
                chatMessages = chatState.messages,
                listState = listState
            )
        }
    }
}

@Composable
fun ChatMessageList(
    chatMessages: List<ChatMessage>,
    listState: LazyListState
) {
    LazyColumn(
        reverseLayout = true,
        state = listState
    ) {
        items(chatMessages.reversed()) { message ->
            MessageBubbleItem(message)
        }
    }
}

@Composable
fun MessageBubbleItem(
    chatMessage: ChatMessage
) {
    val isModelMessage = chatMessage.messageType == MessageType.MODEL ||
            chatMessage.messageType == MessageType.ERROR

    val backgroundColor = when (chatMessage.messageType) {
        MessageType.MODEL -> MaterialTheme.colors.onPrimary
        MessageType.USER -> MaterialTheme.colors.onSecondary
        MessageType.ERROR -> MaterialTheme.colors.error
    }

    val bubbleShape = if (isModelMessage) {
        RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
    } else {
        RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
    }

    val horizontalAlignment = if (isModelMessage) {
        Alignment.Start
    } else {
        Alignment.End
    }

    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = stringResource(id =
                when (chatMessage.messageType) {
                    MessageType.MODEL -> R.string.professor_label
                    MessageType.USER -> R.string.user_label
                    MessageType.ERROR -> R.string.error_label
                }
            ),
            style = MaterialTheme.typography.body1,
            color = when (chatMessage.messageType) {
                MessageType.ERROR -> MaterialTheme.colors.error
                else -> MaterialTheme.colors.onBackground
            }
        )
        Row {
            if (chatMessage.isPending) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(all = 8.dp)
                )
            }
            BoxWithConstraints {
                Card(
                    modifier = Modifier.widthIn(0.dp, maxWidth * 0.9f),
                    backgroundColor = backgroundColor,
                    shape = bubbleShape
                ) {
                    SelectionContainer {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = chatMessage.text
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit,
    resetScroll: () -> Unit = {}
) {
    var userMessage by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.85f),
                value = userMessage,
                label = { Text(stringResource(R.string.chat_label)) },
                onValueChange = { userMessage = it },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onBackground,
                    focusedLabelColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = MaterialTheme.colors.onBackground,
                    unfocusedLabelColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onBackground
                )
            )
            IconButton(
                onClick = {
                    if (userMessage.isNotBlank()) {
                        onSendMessage(userMessage)
                        userMessage = ""
                        resetScroll()
                    }
                },
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.15f)
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "send_icon"
                )
            }
        }
    }
}