package at.htl.todo.model;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.todo.util.store.StoreBase;

@Singleton
public class Store extends StoreBase<Model> {

    @Inject
    Store() {
        super(Model.class, new Model());
    }

    public void setTodos(Todo[] todos) {
        apply(model -> model.todos = todos);
    }

    public void setTodoViewTodo(Todo todo) {
        apply(model -> {
            model.uiState.todo = todo;
            model.uiState.showTodo = true;
        });
    }

    public void unsetTodoView() {
        apply(model -> {
            model.uiState.todo = null;
            model.uiState.showTodo = false;
        });
    }

    public void updateTodoChecked(Long id) {
        if (id != null) {
            apply(model -> {
                for (Todo todo : model.todos) {
                    if (todo.id.equals(id)) {
                        todo.completed = !todo.completed;
                    }
                }

                if (model.uiState.todo.id.equals(id)) {
                    model.uiState.todo.completed = !model.uiState.todo.completed;
                }
            });
        }
    }

    public void selectTab(int tabIndex) {
        apply(model -> model.uiState.selectedTab = tabIndex);
    }
}
