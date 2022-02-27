package univeral.oya.bullets;


import android.util.Log;

import io.realm.Realm;

public class DataHelper {

    public String prize2 = "nope.";
    private String test = "test";
    private Boolean swtch;

    public static void newTask(Realm realm, int taskID, String task, String body, Boolean completed) {

        TaskDB taskData = new TaskDB(taskID, task, body, completed);

        realm.beginTransaction();
        realm.copyToRealm(taskData);
        realm.commitTransaction();

    }

    public static void deleteTask(Realm realm, final long id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskDB taskDBItem= realm.where(TaskDB.class).equalTo("id", id).findFirst();
                if (taskDBItem != null){
                    taskDBItem.deleteFromRealm();
                }

            }
        });
    }

    public static void updateCompletedTask(Realm realm, final long id, final Boolean bool){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskDB taskDBItem= realm.where(TaskDB.class).equalTo("id", id).findFirst();
                if (bool == Boolean.TRUE){
                    taskDBItem.setCompleted(Boolean.TRUE);

                } else {
                    taskDBItem.setCompleted(Boolean.FALSE);
//                    this.getClass().

                }

            }
        });
    }

    public static void updateTask(Realm realm, final long id, String task, String body, final Boolean completed){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskDB taskDBItem= realm.where(TaskDB.class).equalTo("id", id).findFirst();
                if (taskDBItem != null){
                    taskDBItem.setTask(task);
                    taskDBItem.setBody(body);
                    taskDBItem.setCompleted(completed);
                }

            }
        });
    }

//    public class Boolean dataMan {(
//        Boolean testData)
//
//        {
//            this.swtch = test;
//            return this.swtch;
//        }
//    }

//    public String getData(Realm realm, final long id, final String getData){
//        final String data;
////        final String data;
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                TaskDB taskDBItem= realm.where(TaskDB.class).equalTo("id", id).findFirst();
//                if (taskDBItem != null){
//                    taskDBItem.getTask();
//                }
//
//            }
//        });
//        return data;
//    }

//    public static void updateCompletedTask(Realm realm, final long id, Boolean bool){
//        if(bool == Boolean.TRUE){
//
//        }
//    }

    //        RealmModelExample user=new RealmModelExample("Peter", 28);
//        realm.beginTransaction();
//        realm.copyToRealm(user);
//        realm.commitTransaction();

//    public static void newTask(Realm realm, int taskID, String task) {
//        realm.beginTransaction();
//
//        TaskDB taskDB = realm.createObject(TaskDB.class, taskID);
//
//        taskDB.setTask(task);
//        realm.commitTransaction();
//
//    }


}
