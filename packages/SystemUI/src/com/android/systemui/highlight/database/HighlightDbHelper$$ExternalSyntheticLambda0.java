package com.android.systemui.highlight.database;

import android.database.sqlite.SQLiteDatabase;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class HighlightDbHelper$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SQLiteDatabase f$0;

    public /* synthetic */ HighlightDbHelper$$ExternalSyntheticLambda0(SQLiteDatabase sQLiteDatabase, int i) {
        this.$r8$classId = i;
        this.f$0 = sQLiteDatabase;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        SQLiteDatabase sQLiteDatabase = this.f$0;
        String str = (String) obj;
        Columns[] columnsArr = (Columns[]) obj2;
        switch (i) {
            case 0:
                int i2 = HighlightDbHelper.$r8$clinit;
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str);
                break;
            default:
                int i3 = HighlightDbHelper.$r8$clinit;
                StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("CREATE TABLE ", str, " (");
                StringBuilder sb = new StringBuilder();
                for (int i4 = 0; i4 < columnsArr.length; i4++) {
                    sb.append(columnsArr[i4].toString());
                    if (i4 != columnsArr.length - 1) {
                        sb.append(", ");
                    }
                }
                m.append(sb.toString());
                m.append(");");
                sQLiteDatabase.execSQL(m.toString());
                break;
        }
    }
}
