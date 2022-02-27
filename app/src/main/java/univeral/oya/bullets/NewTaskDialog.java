package univeral.oya.bullets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NewTaskDialog extends DialogFragment {

    NewTaskListener mListener;
    String taskGrabbr;
    String bodyGrabbr;
    int idGrabbr;
    Boolean stateGrabber;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener=(NewTaskListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()+ " must implement NewTaskListener");
        }
    }

    public NewTaskDialog(){

    }

    public void setArguments(int id, String task, String body, Boolean completed) {
        this.taskGrabbr = task;
        this.bodyGrabbr = body;
        this.stateGrabber = completed;
        this.idGrabbr = id;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater= getActivity().getLayoutInflater();
        final View dialogLayout= inflater.inflate(R.layout.new_task_dialog2, null);
        final EditText myTaskTxt=dialogLayout.findViewById(R.id.txt_dialog_task);
        final EditText myBodyTxt=dialogLayout.findViewById(R.id.txt_dialog_body);
        final Boolean existence;

        if (idGrabbr > 0){
            myTaskTxt.setText(taskGrabbr);
            myBodyTxt.setText(bodyGrabbr);
        } else {


        }

        // This is the input method that appears when the button is pressed to add a task
        builder.setView(dialogLayout)
                .setPositiveButton("Add/Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String task = myTaskTxt.getText().toString();
                        String body = myBodyTxt.getText().toString();
                        mListener.onAddTask(idGrabbr, task, body, stateGrabber);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCancel(NewTaskDialog.this);
                NewTaskDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
//The following is used when we "implement" this class. It is specifically used in the MainActivity.class
    public interface NewTaskListener{
        void onAddTask(int id, String task, String body, Boolean state);
        void onCancel(DialogFragment dialogFragment);
//        void onGetTask(String task, String body);
    }
}
