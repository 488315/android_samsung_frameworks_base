package com.android.systemui.communal.data.db;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import kotlin.jvm.functions.Function1;

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
    public final Object mo781invoke(Object obj) throws Exception {
        int i = this.f$0;
        long j = this.f$1;
        SQLiteStatement sQLiteStatementPrepare = ((SQLiteConnection) obj).prepare("UPDATE communal_item_rank_table SET rank = ? WHERE uid = ?");
        try {
            sQLiteStatementPrepare.bindLong(1, i);
            sQLiteStatementPrepare.bindLong(2, j);
            sQLiteStatementPrepare.step();
            sQLiteStatementPrepare.close();
            return null;
        } catch (Throwable th) {
            sQLiteStatementPrepare.close();
            throw th;
        }
    }
}
