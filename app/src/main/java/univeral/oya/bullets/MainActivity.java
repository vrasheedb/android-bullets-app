package univeral.oya.bullets;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NewTaskDialog.NewTaskListener, RecyclerViewAdapter.ItemLongClickListener {

    private Realm realm;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    public Context context;


    //Test target string for accessing this class from another class
    public String prize = "YEP buddy!!";
    public String prize2 = "YEP buddy!!";

    public String taskName;
    public String taskBody;
    public Boolean taskProgress;
    public FragmentManager getSupportFragmentManager = getSupportFragmentManager();


    private int removedPosition = 0;
    private String removedItem = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm=Realm.getDefaultInstance();
        recyclerView=findViewById(R.id.my_recycler_view);
        floatingActionButton=findViewById(R.id.my_fab);
        final DialogFragment addTaskDialog = new NewTaskDialog();
        setUpRecyclerView();

        floatingActionButton.setOnClickListener(v -> {
            addTaskDialog.show(getSupportFragmentManager(), "New Task");
            // we can pass data to this using "setArgument" in NewTaskDialog
//            ((NewTaskDialog) addTaskDialog).setArguments(prize);
        });

    }

    public MainActivity(){
    }


    // Objects that are shared externally
    public String resources() { return  prize; }
    public String taskDataName () { return taskName; }
    public String taskDataBody () { return taskBody; }
    public Boolean taskDataProgress () { return taskProgress; }


    //This grabs the task from the Add button input for insertion in to DB.
    // The mListener in NewTaskDialog will detect "onAddTask" when the "Add" button is pressed which triggers the following to happen
    @Override
    public void onAddTask(int id, String task, String body, Boolean state) {

        if (id > 0){
            DataHelper.updateTask(realm, id, task, body, state);
        } else {
            SecureRandom secureRandom=new SecureRandom();
            int taskID=secureRandom.nextInt(100000);
            DataHelper.newTask(realm, taskID, task, body, Boolean.FALSE);

        }

    }

    @Override
    public void onCancel(DialogFragment dialogFragment) {
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }

    private void setUpRecyclerView(){
        RecyclerViewAdapter adapter= new RecyclerViewAdapter(realm.where(TaskDB.class).findAll());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        TouchHelperCallback touchHelperCallback=new TouchHelperCallback();
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onItemLongClick(View v, int pos) {
        Log.d("tester", "Pressed it!!!");
    }

    // The mListener in RecyclerViewAdapter will detect "editDialog" when the longpress happens which triggers the following to happen
    @Override
    public void editDialog(int id, String task, String body, Boolean completed) {
        final DialogFragment addTaskDialog = new NewTaskDialog();

        Log.d("tester", task);
        Log.d("tester", body);
        Log.d("tester", Boolean.toString(completed));
        Toast.makeText(this, body, Toast.LENGTH_SHORT).show();
        addTaskDialog.show(getSupportFragmentManager(), "Edit Task");
        ((NewTaskDialog) addTaskDialog).setArguments(id, task, body, completed);


    }

    // Using this, I can set up "Swipe to remove" on the list items
    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        private AdapterView.OnItemLongClickListener setItemLongClickListener;

        TouchHelperCallback()
        {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            // we can call the TaskDB from here as well as its persistent
            final TaskDB taskDBItem = realm.where(TaskDB.class).equalTo("id", viewHolder.getItemId()).findFirst();

            // Here we cache all the data so it can be undone.
            Boolean cacheCompleted = taskDBItem.getCompleted();
            String cacheTask = taskDBItem.getTask();
            int cacheId = taskDBItem.getId();
            String cacheBody = taskDBItem.getBody();

            // This is the onclicklistener that is called by Snackbar when the user clicks on "Undo"
            View.OnClickListener cacheOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataHelper.newTask(realm, cacheId, cacheTask, cacheBody, cacheCompleted);

                }
            };

                viewHolder.getItemId();
            //TODO - Need to set up Left swipe to change color of view when swiped also indicating a completed task. This will require a schema update to add boolean for "Completed"

            if (i == 4){
                final String testToast = Boolean.toString(taskDBItem.getCompleted());
                final Boolean swtch = !(taskDBItem.getCompleted());
                Toast.makeText(MainActivity.super.getApplicationContext(), "Swiped Left: " + testToast, Toast.LENGTH_SHORT).show();
                //TODO - This should change the Boolean based on what it already is in DB - it should only be a switch and not explicit
                DataHelper.updateCompletedTask(realm, viewHolder.getItemId(), swtch);
            }
            if (i == 8) {
                DataHelper.deleteTask(realm, viewHolder.getItemId());
//                Toast.makeText(MainActivity.super.getApplicationContext(), "Removedd", Toast.LENGTH_SHORT).show();
                Snackbar.make(viewHolder.itemView, "itworks", Snackbar.LENGTH_LONG).setAction("UNDO", cacheOnClickListener).show();
                // TODO I used the following "MainActivity.super.getApplicationContext" in order to access "this" in the context of the main class. I am not sure if there is a better way of doing this or if this may cause future bugs




                Log.d("tester","deleted success");
                Log.d("tester", "Swipe direction" + Integer.toString(i));
            }
            else {
                Log.d("Error: Swiped to: ", Integer.toString(i));
            }
        }

    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
