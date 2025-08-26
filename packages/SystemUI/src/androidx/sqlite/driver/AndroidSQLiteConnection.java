package androidx.sqlite.driver;

import android.database.sqlite.SQLiteDatabase;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import androidx.sqlite.driver.AndroidSQLiteStatement;
import java.util.Locale;
import kotlin.text.StringsKt__StringsKt;

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
        int iHashCode;
        if (!this.db.isOpen()) {
            SQLite.throwSQLiteException(21, "connection is closed");
            throw null;
        }
        AndroidSQLiteStatement.Companion companion = AndroidSQLiteStatement.Companion;
        SQLiteDatabase sQLiteDatabase = this.db;
        companion.getClass();
        String string = StringsKt__StringsKt.trim(str).toString();
        return (string.length() >= 3 && ((iHashCode = (upperCase = string.substring(0, 3).toUpperCase(Locale.ROOT)).hashCode()) == 79487 ? upperCase.equals("PRA") : !(iHashCode == 81978 ? !upperCase.equals("SEL") : !(iHashCode == 85954 && upperCase.equals("WIT"))))) ? new AndroidSQLiteStatement.SelectAndroidSQLiteStatement(sQLiteDatabase, str) : new AndroidSQLiteStatement.OtherAndroidSQLiteStatement(sQLiteDatabase, str);
    }
}
