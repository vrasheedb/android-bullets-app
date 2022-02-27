package univeral.oya.bullets;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class RealmMigrations implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();
// TODO Need to confirm that schema updates do not destroy existing data, if so, how to resolve it??
        if (oldVersion == 3) {
            final RealmObjectSchema taskSchema = schema.get("TaskDB");
            taskSchema.addField("Completed", Boolean.class);
        }
    }
}
