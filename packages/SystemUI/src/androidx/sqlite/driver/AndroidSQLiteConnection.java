package androidx.sqlite.driver;

import android.database.sqlite.SQLiteDatabase;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.driver.AndroidSQLiteStatement;
import java.util.Locale;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidSQLiteConnection implements SQLiteConnection {
    public final SQLiteDatabase db;

    public AndroidSQLiteConnection(SQLiteDatabase sQLiteDatabase) {
        this.db = sQLiteDatabase;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.db.close();
    }

    @Override // androidx.sqlite.SQLiteConnection
    public final SQLiteStatement prepare(String str) {
        String upperCase;
        int hashCode;
        if (!this.db.isOpen()) {
            SQLite.throwSQLiteException(21, "connection is closed");
            throw null;
        }
        AndroidSQLiteStatement.Companion companion = AndroidSQLiteStatement.Companion;
        SQLiteDatabase sQLiteDatabase = this.db;
        companion.getClass();
        String obj = StringsKt__StringsKt.trim(str).toString();
        return (obj.length() >= 3 && ((hashCode = (upperCase = obj.substring(0, 3).toUpperCase(Locale.ROOT)).hashCode()) == 79487 ? upperCase.equals("PRA") : !(hashCode == 81978 ? !upperCase.equals("SEL") : !(hashCode == 85954 && upperCase.equals("WIT"))))) ? new AndroidSQLiteStatement.SelectAndroidSQLiteStatement(sQLiteDatabase, str) : new AndroidSQLiteStatement.OtherAndroidSQLiteStatement(sQLiteDatabase, str);
    }
}
