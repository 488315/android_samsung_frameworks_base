package androidx.room.util;

import androidx.room.DatabaseConfiguration;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class MigrationUtil {
    public static final boolean isMigrationRequired(DatabaseConfiguration databaseConfiguration, int i, int i2) {
        if (i > i2 && databaseConfiguration.allowDestructiveMigrationOnDowngrade) {
            return false;
        }
        Set set = databaseConfiguration.migrationNotRequiredFrom;
        return databaseConfiguration.requireMigration && (set == null || !set.contains(Integer.valueOf(i)));
    }
}
