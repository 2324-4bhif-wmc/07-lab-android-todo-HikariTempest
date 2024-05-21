package at.htl.todo.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.htl.todo.model.Store
import at.htl.todo.model.Todo

@Composable
fun TodoCard(todo: Todo, store: Store?) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Card(
            onClick = {
                store?.unsetTodoView()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = todo.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column (
                            modifier = Modifier.width(300.dp)
                        ) {

                            Text(
                                text = "ID: ${todo.id}",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "User-ID: ${todo.userId}",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Checkbox(
                            checked = todo.completed,
                            onCheckedChange = { store?.updateTodoChecked(todo.id) }
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(onClick = { store?.unsetTodoView() }) {
                        Text(text = "Back")
                    }
                }
            }
        }
    }
}
