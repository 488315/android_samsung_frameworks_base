package com.android.systemui.communal.data.db;

import androidx.room.AmbiguousColumnResolver;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.LinkedHashMap;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) throws Exception {
        SQLiteStatement sQLiteStatementPrepare;
        switch (this.$r8$classId) {
            case 0:
                sQLiteStatementPrepare = ((SQLiteConnection) obj).prepare("SELECT * FROM communal_widget_table JOIN communal_item_rank_table ON communal_item_rank_table.uid = communal_widget_table.item_id ORDER BY communal_item_rank_table.rank ASC");
                try {
                    int[][] iArrResolve = AmbiguousColumnResolver.resolve(sQLiteStatementPrepare.getColumnNames(), new String[][]{new String[]{NetworkAnalyticsConstants.DataPoints.UID, "rank"}, new String[]{NetworkAnalyticsConstants.DataPoints.UID, "widget_id", "component_name", "item_id", "user_serial_number", "span_y", "span_y_new"}});
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    while (sQLiteStatementPrepare.step()) {
                        CommunalItemRank communalItemRank = new CommunalItemRank(sQLiteStatementPrepare.getLong(iArrResolve[0][0]), (int) sQLiteStatementPrepare.getLong(iArrResolve[0][1]));
                        if (sQLiteStatementPrepare.isNull(iArrResolve[1][0]) && sQLiteStatementPrepare.isNull(iArrResolve[1][1]) && sQLiteStatementPrepare.isNull(iArrResolve[1][2]) && sQLiteStatementPrepare.isNull(iArrResolve[1][3]) && sQLiteStatementPrepare.isNull(iArrResolve[1][4]) && sQLiteStatementPrepare.isNull(iArrResolve[1][5]) && sQLiteStatementPrepare.isNull(iArrResolve[1][6])) {
                            linkedHashMap.put(communalItemRank, null);
                        } else {
                            CommunalWidgetItem communalWidgetItem = new CommunalWidgetItem(sQLiteStatementPrepare.getLong(iArrResolve[1][0]), (int) sQLiteStatementPrepare.getLong(iArrResolve[1][1]), sQLiteStatementPrepare.isNull(iArrResolve[1][2]) ? null : sQLiteStatementPrepare.getText(iArrResolve[1][2]), sQLiteStatementPrepare.getLong(iArrResolve[1][3]), (int) sQLiteStatementPrepare.getLong(iArrResolve[1][4]), (int) sQLiteStatementPrepare.getLong(iArrResolve[1][5]), (int) sQLiteStatementPrepare.getLong(iArrResolve[1][6]));
                            if (!linkedHashMap.containsKey(communalItemRank)) {
                                linkedHashMap.put(communalItemRank, communalWidgetItem);
                            }
                        }
                    }
                    return linkedHashMap;
                } finally {
                }
            case 1:
                sQLiteStatementPrepare = ((SQLiteConnection) obj).prepare("DELETE FROM communal_widget_table");
                try {
                    sQLiteStatementPrepare.step();
                    sQLiteStatementPrepare.close();
                    return null;
                } finally {
                }
            case 2:
                sQLiteStatementPrepare = ((SQLiteConnection) obj).prepare("SELECT * FROM communal_widget_table JOIN communal_item_rank_table ON communal_item_rank_table.uid = communal_widget_table.item_id ORDER BY communal_item_rank_table.rank ASC");
                try {
                    int[][] iArrResolve2 = AmbiguousColumnResolver.resolve(sQLiteStatementPrepare.getColumnNames(), new String[][]{new String[]{NetworkAnalyticsConstants.DataPoints.UID, "rank"}, new String[]{NetworkAnalyticsConstants.DataPoints.UID, "widget_id", "component_name", "item_id", "user_serial_number", "span_y", "span_y_new"}});
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    while (sQLiteStatementPrepare.step()) {
                        CommunalItemRank communalItemRank2 = new CommunalItemRank(sQLiteStatementPrepare.getLong(iArrResolve2[0][0]), (int) sQLiteStatementPrepare.getLong(iArrResolve2[0][1]));
                        if (sQLiteStatementPrepare.isNull(iArrResolve2[1][0]) && sQLiteStatementPrepare.isNull(iArrResolve2[1][1]) && sQLiteStatementPrepare.isNull(iArrResolve2[1][2]) && sQLiteStatementPrepare.isNull(iArrResolve2[1][3]) && sQLiteStatementPrepare.isNull(iArrResolve2[1][4]) && sQLiteStatementPrepare.isNull(iArrResolve2[1][5]) && sQLiteStatementPrepare.isNull(iArrResolve2[1][6])) {
                            linkedHashMap2.put(communalItemRank2, null);
                        } else {
                            CommunalWidgetItem communalWidgetItem2 = new CommunalWidgetItem(sQLiteStatementPrepare.getLong(iArrResolve2[1][0]), (int) sQLiteStatementPrepare.getLong(iArrResolve2[1][1]), sQLiteStatementPrepare.isNull(iArrResolve2[1][2]) ? null : sQLiteStatementPrepare.getText(iArrResolve2[1][2]), sQLiteStatementPrepare.getLong(iArrResolve2[1][3]), (int) sQLiteStatementPrepare.getLong(iArrResolve2[1][4]), (int) sQLiteStatementPrepare.getLong(iArrResolve2[1][5]), (int) sQLiteStatementPrepare.getLong(iArrResolve2[1][6]));
                            if (!linkedHashMap2.containsKey(communalItemRank2)) {
                                linkedHashMap2.put(communalItemRank2, communalWidgetItem2);
                            }
                        }
                    }
                    return linkedHashMap2;
                } finally {
                }
            default:
                sQLiteStatementPrepare = ((SQLiteConnection) obj).prepare("DELETE FROM communal_item_rank_table");
                try {
                    sQLiteStatementPrepare.step();
                    sQLiteStatementPrepare.close();
                    return null;
                } finally {
                }
        }
    }
}
