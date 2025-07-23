package androidx.room.util;

import androidx.room.DatabaseConfiguration;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
