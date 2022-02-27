package univeral.oya.bullets;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<TaskDB, RecyclerViewAdapter.MyViewHolder> implements NewTaskDialog.NewTaskListener {

    ItemLongClickListener mListener;

    // This automatically updates the view with the data if boolean is "True"
    public RecyclerViewAdapter(@Nullable OrderedRealmCollection<TaskDB> data) {
        super(data, true);

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        // This is the mListener initialization as copied from "NewTaskDialog". Instead of putting it within onAttach, it is applicable here in onCreateViewHolder
        try {
            mListener=(ItemLongClickListener) parent.getContext();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(parent.getClass().toString()+ " must implement ItemLongClickListener");
        }
        return new MyViewHolder(itemView);
    }
    // Here, we can change the behavior of each of the individual items.
    // TODO VERY IMPORTANT PER ITEM!! All changes to each line item should happen here
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        final TaskDB taskDB = getItem(position);
        // This is a test to grab a variable from MainActivity
        final String prizetest = new MainActivity().prize;
        final NewTaskDialog dialogMgr;
        final String getBoolean = Boolean.toString(taskDB.getCompleted());
        final String getBody = taskDB.getBody();
        final String getTask = taskDB.getTask();
        final int getID = taskDB.getId();
        final Boolean getCompleted = taskDB.getCompleted();

        holder.txtTask.setText(taskDB.getTask());
        // This is

        if (taskDB.getCompleted() ==  Boolean.TRUE){
            holder.itemView.setBackgroundColor(Color.GREEN);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {

                //TODO set up a datahelper that reads data - use holder.getItemID()
                mListener.editDialog(getID, getTask, getBody, getCompleted);
                Log.d("tester",Integer.toString(pos));

            }

            @Override
            public void editDialog(int id, String task, String body, Boolean completed) {

            }
        });






    }

    @Override
    public void onAddTask(int id, String task, String body, Boolean state) {

    }

    @Override
    public void onCancel(DialogFragment dialogFragment) {

    }


    // This is created to facilitate long click listener
    public interface ItemLongClickListener {

        void onItemLongClick(View v,int pos);
        void editDialog(int id, String task, String body, Boolean state);
    }



    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }


    //This class is responsible for temporarily holding widgets for adapter to recycle views
    //Here we also set up Long press actions as well as any other item specific actions
    //TODO Confirm whether or not this is a item by (individual)item basis
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView txtTask;
        ItemLongClickListener itemLongClickListener;

        MyViewHolder(View view){
            super(view);
            txtTask=view.findViewById(R.id.txt_row_task);

            view.setOnLongClickListener(this);
        }

        public void setItemLongClickListener(ItemLongClickListener ic){
            this.itemLongClickListener=ic;
        }

        @Override
        public boolean onLongClick(View view) {
            this.itemLongClickListener.onItemLongClick(view, getLayoutPosition());

            return false;
        }
    }

}
