package univeral.oya.bullets;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

//        final RealmConfiguration configuration = new RealmConfiguration.Builder().name("bullets.realm").schemaVersion(3).build();


        // Here we can change the configuration of the realm DB using the "RealmMigrations.class" - See: https://medium.com/@budioktaviyans/android-realm-migration-schema-4fcef6c61e82
        final RealmConfiguration configuration = new RealmConfiguration.Builder().name("bullets.realm").schemaVersion(4).migration(new RealmMigrations()).build();
        Realm.setDefaultConfiguration(configuration);
        Realm.getInstance(configuration);
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}
