package at.htl.todo.ui.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.Store
import at.htl.todo.model.Todo

@Composable
fun Todos(model: Model, store: Store?) {
    val todos = model.todos

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(todos.size) { index ->
            TodoRow(
                todo = todos[index],
                store = store
            )
        }
    }
}

@Composable
fun TodoRow(todo: Todo, store: Store?) {
    Card(
        modifier = Modifier.padding(4.dp),
        onClick = {
            store?.setTodoViewTodo(todo)
        }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = todo.id.toString(),
                modifier = Modifier.padding(horizontal = 6.dp)
            )
            Text(
                text = todo.title,
                overflow = TextOverflow.Ellipsis, maxLines = 1
            )
        }
    }
}
