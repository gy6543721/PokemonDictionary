package levilin.pokemon.dictionary.ui.screen.chat.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import levilin.pokemon.dictionary.R
import levilin.pokemon.dictionary.model.remote.chat.ChatMessage
import levilin.pokemon.dictionary.model.remote.chat.MessageType

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
            ChatMessageItem(message)
        }
    }
}

@Composable
fun ChatMessageItem(
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