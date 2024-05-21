package at.htl.todo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import javax.inject.Inject;

import at.htl.todo.model.TodoService;
import at.htl.todo.ui.layout.MainViewBuilder;
import at.htl.todo.util.config.Config;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends ComponentActivity {
    static final String TAG  = MainActivity.class.getSimpleName();

    @Inject
    MainViewBuilder mainView;

    @Inject
    TodoService todoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.load(this);
        var base_url = Config.getProperty("json.placeholder.baseurl");
        Log.i(TAG, "onCreate: " + base_url);
        todoService.getAll();
        mainView.setContentOfActivity(this);
    }
}
