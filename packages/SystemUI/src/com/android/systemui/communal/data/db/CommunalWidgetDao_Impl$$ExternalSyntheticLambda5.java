package com.android.systemui.communal.data.db;

import androidx.room.util.SQLiteConnectionUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda5 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ CommunalWidgetDao_Impl$$ExternalSyntheticLambda5(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SQLiteStatement prepare;
        int i = this.$r8$classId;
        int i2 = this.f$0;
        SQLiteConnection sQLiteConnection = (SQLiteConnection) obj;
        switch (i) {
            case 0:
                prepare = sQLiteConnection.prepare("SELECT * FROM communal_widget_table WHERE widget_id = ?");
                try {
                    prepare.bindLong(1, i2);
                    int columnIndexOrThrow = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, NetworkAnalyticsConstants.DataPoints.UID);
                    int columnIndexOrThrow2 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "widget_id");
                    int columnIndexOrThrow3 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "component_name");
                    int columnIndexOrThrow4 = SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "item_id");
                    if (prepare.step()) {
                        r4 = new CommunalWidgetItem(prepare.getLong(columnIndexOrThrow), (int) prepare.getLong(columnIndexOrThrow2), prepare.isNull(columnIndexOrThrow3) ? null : prepare.getText(columnIndexOrThrow3), prepare.getLong(columnIndexOrThrow4));
                    }
                    return r4;
                } finally {
                }
            default:
                prepare = sQLiteConnection.prepare("INSERT INTO communal_item_rank_table(rank) VALUES(?)");
                try {
                    prepare.bindLong(1, i2);
                    prepare.step();
                    return Long.valueOf(SQLiteConnectionUtil.getLastInsertedRowId(sQLiteConnection));
                } finally {
                }
        }
    }
}
