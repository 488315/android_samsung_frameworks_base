package androidx.room.driver;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
