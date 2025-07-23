package androidx.room.driver;

import androidx.room.driver.SupportSQLiteStatement;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.Locale;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SupportSQLiteConnection implements SQLiteConnection {
    public final SupportSQLiteDatabase db;

    public SupportSQLiteConnection(SupportSQLiteDatabase supportSQLiteDatabase) {
        this.db = supportSQLiteDatabase;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.db.close();
    }

    @Override // androidx.sqlite.SQLiteConnection
    public final SupportSQLiteStatement prepare(String str) {
        String upperCase;
        int hashCode;
        SupportSQLiteStatement.Companion companion = SupportSQLiteStatement.Companion;
        SupportSQLiteDatabase supportSQLiteDatabase = this.db;
        companion.getClass();
        String obj = StringsKt__StringsKt.trim(str).toString();
        return (obj.length() >= 3 && ((hashCode = (upperCase = obj.substring(0, 3).toUpperCase(Locale.ROOT)).hashCode()) == 79487 ? upperCase.equals("PRA") : !(hashCode == 81978 ? !upperCase.equals("SEL") : !(hashCode == 85954 && upperCase.equals("WIT"))))) ? new SupportSQLiteStatement.SupportAndroidSQLiteStatement(supportSQLiteDatabase, str) : new SupportSQLiteStatement.SupportOtherAndroidSQLiteStatement(supportSQLiteDatabase, str);
    }
}
