package at.htl.todo.model;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.todo.util.store.StoreBase;

@Singleton
public class ModelStore extends StoreBase<Model> {

    @Inject
    ModelStore() {
        super(Model.class, new Model());
    }

    public void setTodos(Todo[] todos) {
        apply(model -> model.todos = todos);
    }

    public void setTodoViewTodo(Todo todo) {
        apply(model -> {
            model.todoView.todo = todo;
            model.todoView.showTodo = true;
        });
    }

    public void unsetTodoView() {
        apply(model -> {
            model.todoView.todo = null;
            model.todoView.showTodo = false;
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

                if (model.todoView.todo.id.equals(id)) {
                    model.todoView.todo.completed = !model.todoView.todo.completed;
                }
            });
        }
    }
}
