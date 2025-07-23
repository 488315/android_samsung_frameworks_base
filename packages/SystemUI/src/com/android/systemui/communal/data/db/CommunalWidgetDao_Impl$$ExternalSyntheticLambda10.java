package com.android.systemui.communal.data.db;

import androidx.room.util.SQLiteConnectionUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object mo779invoke(Object obj) {
        SQLiteStatement prepare;
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                SQLiteConnection sQLiteConnection = (SQLiteConnection) obj;
                prepare = sQLiteConnection.prepare("INSERT INTO communal_item_rank_table(rank) VALUES(?)");
                try {
                    prepare.bindLong(1, i2);
                    prepare.step();
                    return Long.valueOf(SQLiteConnectionUtil.getLastInsertedRowId(sQLiteConnection));
                } finally {
                }
            default:
                prepare = ((SQLiteConnection) obj).prepare("SELECT * FROM communal_widget_table WHERE widget_id = ?");
                try {
                    prepare.bindLong(1, i2);
                    int columnIndexOrThrow = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, NetworkAnalyticsConstants.DataPoints.UID);
                    int columnIndexOrThrow2 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "widget_id");
                    int columnIndexOrThrow3 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "component_name");
                    int columnIndexOrThrow4 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "item_id");
                    int columnIndexOrThrow5 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "user_serial_number");
                    int columnIndexOrThrow6 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "span_y");
                    int columnIndexOrThrow7 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "span_y_new");
                    if (prepare.step()) {
                        r9 = new CommunalWidgetItem(prepare.getLong(columnIndexOrThrow), (int) prepare.getLong(columnIndexOrThrow2), prepare.isNull(columnIndexOrThrow3) ? null : prepare.getText(columnIndexOrThrow3), prepare.getLong(columnIndexOrThrow4), (int) prepare.getLong(columnIndexOrThrow5), (int) prepare.getLong(columnIndexOrThrow6), (int) prepare.getLong(columnIndexOrThrow7));
                    }
                    return r9;
                } finally {
                }
        }
    }
}
