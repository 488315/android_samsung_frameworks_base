package com.android.systemui.communal.data.db;

import androidx.room.util.SQLiteConnectionUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda10 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda10(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.communal.data.db.CommunalWidgetItem] */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) throws Exception {
        SQLiteStatement sQLiteStatementPrepare;
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                SQLiteConnection sQLiteConnection = (SQLiteConnection) obj;
                sQLiteStatementPrepare = sQLiteConnection.prepare("INSERT INTO communal_item_rank_table(rank) VALUES(?)");
                try {
                    sQLiteStatementPrepare.bindLong(1, i2);
                    sQLiteStatementPrepare.step();
                    return Long.valueOf(SQLiteConnectionUtil.getLastInsertedRowId(sQLiteConnection));
                } finally {
                }
            default:
                sQLiteStatementPrepare = ((SQLiteConnection) obj).prepare("SELECT * FROM communal_widget_table WHERE widget_id = ?");
                try {
                    sQLiteStatementPrepare.bindLong(1, i2);
                    int columnIndexOrThrow = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, NetworkAnalyticsConstants.DataPoints.UID);
                    int columnIndexOrThrow2 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "widget_id");
                    int columnIndexOrThrow3 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "component_name");
                    int columnIndexOrThrow4 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "item_id");
                    int columnIndexOrThrow5 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "user_serial_number");
                    int columnIndexOrThrow6 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "span_y");
                    int columnIndexOrThrow7 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "span_y_new");
                    if (sQLiteStatementPrepare.step()) {
                        communalWidgetItem = new CommunalWidgetItem(sQLiteStatementPrepare.getLong(columnIndexOrThrow), (int) sQLiteStatementPrepare.getLong(columnIndexOrThrow2), sQLiteStatementPrepare.isNull(columnIndexOrThrow3) ? null : sQLiteStatementPrepare.getText(columnIndexOrThrow3), sQLiteStatementPrepare.getLong(columnIndexOrThrow4), (int) sQLiteStatementPrepare.getLong(columnIndexOrThrow5), (int) sQLiteStatementPrepare.getLong(columnIndexOrThrow6), (int) sQLiteStatementPrepare.getLong(columnIndexOrThrow7));
                    }
                    return communalWidgetItem;
                } finally {
                }
        }
    }
}
