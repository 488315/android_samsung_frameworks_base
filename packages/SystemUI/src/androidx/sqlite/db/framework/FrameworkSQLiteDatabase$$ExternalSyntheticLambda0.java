package androidx.sqlite.db.framework;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.framework.FrameworkSQLiteDatabase;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class FrameworkSQLiteDatabase$$ExternalSyntheticLambda0 implements Function4 {
    public final /* synthetic */ SupportSQLiteQuery f$0;

    public /* synthetic */ FrameworkSQLiteDatabase$$ExternalSyntheticLambda0(SupportSQLiteQuery supportSQLiteQuery) {
        this.f$0 = supportSQLiteQuery;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        SQLiteQuery sQLiteQuery = (SQLiteQuery) obj4;
        FrameworkSQLiteDatabase.Companion companion = FrameworkSQLiteDatabase.Companion;
        sQLiteQuery.getClass();
        this.f$0.bindTo(new FrameworkSQLiteProgram(sQLiteQuery));
        return new SQLiteCursor((SQLiteCursorDriver) obj2, (String) obj3, sQLiteQuery);
    }
}
