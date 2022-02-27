package univeral.oya.bullets;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        Realm realm=Realm.getDefaultInstance();

        //query looking at all users
        RealmQuery<RealmModelExample> users=realm.where(RealmModelExample.class);

        //query conditions to filters query
        users.equalTo("name", "John");
        users.or().equalTo("name", "James");

        //Execute query

        RealmResults<RealmModelExample> result= users.findAll();
        result.get(0).getAge();
        result.size();
        //Insert method 1
//        realm.beginTransaction();
//        RealmModelExample user=realm.createObject(RealmModelExample.class);
//        user.setName("John");
//        user.setAge(28);
//        realm.commitTransaction();

        //Insert method 2 - better
//        RealmModelExample user=new RealmModelExample("Peter", 28);
//        realm.beginTransaction();
//        realm.copyToRealm(user);
//        realm.commitTransaction();

        //Insert multiple objects - Good one
//        List<RealmModelExample> users= Arrays.asList(new RealmModelExample("James", 23), new RealmModelExample("Abi", 33));
//        realm.beginTransaction();
//        realm.insert(users);
//        realm.commitTransaction();

    }
}
