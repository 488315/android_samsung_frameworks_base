package androidx.room.driver;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/* loaded from: classes.dex */
public final class SupportSQLiteDriver implements SQLiteDriver {
    public final SupportSQLiteOpenHelper openHelper;

    public SupportSQLiteDriver(SupportSQLiteOpenHelper supportSQLiteOpenHelper) {
        this.openHelper = supportSQLiteOpenHelper;
    }

    @Override // androidx.sqlite.SQLiteDriver
    public final SQLiteConnection open(String str) {
        return new SupportSQLiteConnection(this.openHelper.getWritableDatabase());
    }
}
