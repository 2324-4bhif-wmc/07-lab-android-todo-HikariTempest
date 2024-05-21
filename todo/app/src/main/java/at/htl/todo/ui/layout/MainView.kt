package at.htl.todo.ui.layout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.htl.todo.model.Model
import at.htl.todo.model.Store
import at.htl.todo.model.Todo
import at.htl.todo.ui.theme.TodoTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView @Inject constructor() {

    @Inject
    lateinit var store: Store

    fun buildContent(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        activity.setContent {
            val viewModel = store
                .pipe
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model())
                .value
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ApplicationStructure(viewModel = viewModel)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ApplicationStructure(viewModel: Model, modifier: Modifier = Modifier) {
        Scaffold (
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(text = "Todo App")
                    })
            }
        ) {
                innerPadding ->
            Surface (
                modifier = modifier
                    .padding(8.dp)
                    .clickable { store.unsetTodoView() }
            ){
                if (viewModel.todoView.showTodo && viewModel.todoView.todo != null) {
                    TodoView(todo = viewModel.todoView.todo, modifier = Modifier.padding(innerPadding))
                } else {
                    Todos(model = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun Todos(model: Model, modifier: Modifier = Modifier) {
        val todos = model.todos
        LazyColumn(
            modifier = modifier
        ) {
            items(todos.size) { index ->
                TodoRow(todo  = todos[index])
            }
        }
    }

    @Composable
    fun TodoRow(todo: Todo) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                onClick = { store.setTodoViewTodo(todo) },
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = todo.title,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TodoView(todo: Todo, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .padding(8.dp)
        ) {
            Card(
                onClick = {
                    store.unsetTodoView()
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
                                onCheckedChange = { store.updateTodoChecked(todo.id) }
                            )
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                        Button(onClick = { store.unsetTodoView() }) {
                            Text(text = "Back")
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TodoPreview() {
        val model = Model()
        val todo = Todo()
        todo.id = 1
        todo.title = "First Todo"
        model.todos = arrayOf(todo)

        TodoTheme {
            Todos(model)
        }
    }
}