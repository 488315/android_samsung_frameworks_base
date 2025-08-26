package androidx.room;

import androidx.sqlite.SQLiteConnection;

/* loaded from: classes.dex */
public abstract class RoomOpenDelegate {
    public final String identityHash;
    public final String legacyIdentityHash;
    public final int version;

    public final class ValidationResult {
        public final String expectedFoundMsg;
        public final boolean isValid;

        public ValidationResult(boolean z, String str) {
            this.isValid = z;
            this.expectedFoundMsg = str;
        }
    }

    public RoomOpenDelegate(int i, String str, String str2) {
        this.version = i;
        this.identityHash = str;
        this.legacyIdentityHash = str2;
    }

    public abstract void createAllTables(SQLiteConnection sQLiteConnection);

    public abstract void dropAllTables(SQLiteConnection sQLiteConnection);

    public abstract void onCreate();

    public abstract void onOpen(SQLiteConnection sQLiteConnection);

    public abstract void onPostMigrate();

    public abstract void onPreMigrate(SQLiteConnection sQLiteConnection);

    public abstract ValidationResult onValidateSchema(SQLiteConnection sQLiteConnection);
}
