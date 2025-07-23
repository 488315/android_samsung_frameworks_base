package com.android.systemui.communal.data.db;

import androidx.room.AmbiguousColumnResolver;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.LinkedHashMap;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        SQLiteStatement prepare;
        switch (this.$r8$classId) {
            case 0:
                prepare = ((SQLiteConnection) obj).prepare("SELECT * FROM communal_widget_table JOIN communal_item_rank_table ON communal_item_rank_table.uid = communal_widget_table.item_id ORDER BY communal_item_rank_table.rank ASC");
                try {
                    int[][] resolve = AmbiguousColumnResolver.resolve(prepare.getColumnNames(), new String[][]{new String[]{NetworkAnalyticsConstants.DataPoints.UID, "rank"}, new String[]{NetworkAnalyticsConstants.DataPoints.UID, "widget_id", "component_name", "item_id", "user_serial_number", "span_y", "span_y_new"}});
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    while (prepare.step()) {
                        CommunalItemRank communalItemRank = new CommunalItemRank(prepare.getLong(resolve[0][0]), (int) prepare.getLong(resolve[0][1]));
                        if (prepare.isNull(resolve[1][0]) && prepare.isNull(resolve[1][1]) && prepare.isNull(resolve[1][2]) && prepare.isNull(resolve[1][3]) && prepare.isNull(resolve[1][4]) && prepare.isNull(resolve[1][5]) && prepare.isNull(resolve[1][6])) {
                            linkedHashMap.put(communalItemRank, null);
                        } else {
                            CommunalWidgetItem communalWidgetItem = new CommunalWidgetItem(prepare.getLong(resolve[1][0]), (int) prepare.getLong(resolve[1][1]), prepare.isNull(resolve[1][2]) ? null : prepare.getText(resolve[1][2]), prepare.getLong(resolve[1][3]), (int) prepare.getLong(resolve[1][4]), (int) prepare.getLong(resolve[1][5]), (int) prepare.getLong(resolve[1][6]));
                            if (!linkedHashMap.containsKey(communalItemRank)) {
                                linkedHashMap.put(communalItemRank, communalWidgetItem);
                            }
                        }
                    }
                    return linkedHashMap;
                } finally {
                }
            case 1:
                prepare = ((SQLiteConnection) obj).prepare("DELETE FROM communal_widget_table");
                try {
                    prepare.step();
                    prepare.close();
                    return null;
                } finally {
                }
            case 2:
                prepare = ((SQLiteConnection) obj).prepare("SELECT * FROM communal_widget_table JOIN communal_item_rank_table ON communal_item_rank_table.uid = communal_widget_table.item_id ORDER BY communal_item_rank_table.rank ASC");
                try {
                    int[][] resolve2 = AmbiguousColumnResolver.resolve(prepare.getColumnNames(), new String[][]{new String[]{NetworkAnalyticsConstants.DataPoints.UID, "rank"}, new String[]{NetworkAnalyticsConstants.DataPoints.UID, "widget_id", "component_name", "item_id", "user_serial_number", "span_y", "span_y_new"}});
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    while (prepare.step()) {
                        CommunalItemRank communalItemRank2 = new CommunalItemRank(prepare.getLong(resolve2[0][0]), (int) prepare.getLong(resolve2[0][1]));
                        if (prepare.isNull(resolve2[1][0]) && prepare.isNull(resolve2[1][1]) && prepare.isNull(resolve2[1][2]) && prepare.isNull(resolve2[1][3]) && prepare.isNull(resolve2[1][4]) && prepare.isNull(resolve2[1][5]) && prepare.isNull(resolve2[1][6])) {
                            linkedHashMap2.put(communalItemRank2, null);
                        } else {
                            CommunalWidgetItem communalWidgetItem2 = new CommunalWidgetItem(prepare.getLong(resolve2[1][0]), (int) prepare.getLong(resolve2[1][1]), prepare.isNull(resolve2[1][2]) ? null : prepare.getText(resolve2[1][2]), prepare.getLong(resolve2[1][3]), (int) prepare.getLong(resolve2[1][4]), (int) prepare.getLong(resolve2[1][5]), (int) prepare.getLong(resolve2[1][6]));
                            if (!linkedHashMap2.containsKey(communalItemRank2)) {
                                linkedHashMap2.put(communalItemRank2, communalWidgetItem2);
                            }
                        }
                    }
                    return linkedHashMap2;
                } finally {
                }
            default:
                prepare = ((SQLiteConnection) obj).prepare("DELETE FROM communal_item_rank_table");
                try {
                    prepare.step();
                    prepare.close();
                    return null;
                } finally {
                }
        }
    }
}
