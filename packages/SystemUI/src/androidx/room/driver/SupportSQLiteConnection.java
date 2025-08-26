package androidx.room.driver;

import androidx.room.driver.SupportSQLiteStatement;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.io.IOException;
import java.util.Locale;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class SupportSQLiteConnection implements SQLiteConnection {
    public final SupportSQLiteDatabase db;

    public SupportSQLiteConnection(SupportSQLiteDatabase supportSQLiteDatabase) {
        this.db = supportSQLiteDatabase;
    }

    @Override // java.lang.AutoCloseable
    public final void close() throws IOException {
        this.db.close();
    }

    @Override // androidx.sqlite.SQLiteConnection
    public final SupportSQLiteStatement prepare(String str) {
        String upperCase;
        int iHashCode;
        SupportSQLiteStatement.Companion companion = SupportSQLiteStatement.Companion;
        SupportSQLiteDatabase supportSQLiteDatabase = this.db;
        companion.getClass();
        String string = StringsKt__StringsKt.trim(str).toString();
        return (string.length() >= 3 && ((iHashCode = (upperCase = string.substring(0, 3).toUpperCase(Locale.ROOT)).hashCode()) == 79487 ? upperCase.equals("PRA") : !(iHashCode == 81978 ? !upperCase.equals("SEL") : !(iHashCode == 85954 && upperCase.equals("WIT"))))) ? new SupportSQLiteStatement.SupportAndroidSQLiteStatement(supportSQLiteDatabase, str) : new SupportSQLiteStatement.SupportOtherAndroidSQLiteStatement(supportSQLiteDatabase, str);
    }
}
