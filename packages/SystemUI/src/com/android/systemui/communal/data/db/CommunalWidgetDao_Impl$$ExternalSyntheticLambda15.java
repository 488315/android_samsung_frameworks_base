package com.android.systemui.communal.data.db;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda15 implements Function1 {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda15(int i, long j) {
        this.f$0 = i;
        this.f$1 = j;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i = this.f$0;
        long j = this.f$1;
        SQLiteStatement prepare = ((SQLiteConnection) obj).prepare("UPDATE communal_item_rank_table SET rank = ? WHERE uid = ?");
        try {
            prepare.bindLong(1, i);
            prepare.bindLong(2, j);
            prepare.step();
            prepare.close();
            return null;
        } catch (Throwable th) {
            prepare.close();
            throw th;
        }
    }
}
