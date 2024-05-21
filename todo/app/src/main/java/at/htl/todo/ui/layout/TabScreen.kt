package at.htl.todo.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.htl.todo.model.Model
import at.htl.todo.model.Store
import at.htl.todo.model.Todo
import at.htl.todo.model.TodoService
import at.htl.todo.ui.theme.TodoTheme

@Composable
fun TabScreen(model: Model, store: Store?, todoSvc: TodoService?) {
    var uiState = model.uiState
    val tabIndex = uiState.selectedTab
    val tabs = listOf("Home", "Todos")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = uiState.selectedTab
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { store?.selectTab(index)},
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
                            1 -> BadgedBox(
                                badge = {
                                    Badge {
                                        Text(text = "${model.todos.size}")
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Favorite, contentDescription = "ToDos")
                            }
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> HomeScreen(model, todoSvc, store)
            //TODO: 1 -> ToDos(model)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabScreenPreview() {
    fun todo(id: Long, title: String): Todo {
        val todo = Todo()
        todo.id = id
        todo.title = title
        return todo
    }

    val model = Model()
    model.uiState.selectedTab = 0
    model.todos = arrayOf(todo(1, "homework"), todo(2, "this is a todo with a very long text which should be truncated"))

    TodoTheme {
        TabScreen(model = model, store = null, todoSvc = null)
    }
}

