package com.android.systemui.communal.data.db;

import androidx.room.AmbiguousColumnResolver;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class CommunalWidgetDao_Impl$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SQLiteStatement prepare;
        switch (this.$r8$classId) {
            case 0:
                prepare = ((SQLiteConnection) obj).prepare("SELECT * FROM communal_widget_table JOIN communal_item_rank_table ON communal_item_rank_table.uid = communal_widget_table.item_id ORDER BY communal_item_rank_table.rank DESC");
                try {
                    int columnCount = prepare.getColumnCount();
                    ArrayList arrayList = new ArrayList(columnCount);
                    for (int i = 0; i < columnCount; i++) {
                        arrayList.add(prepare.getColumnName(i));
                    }
                    int[][] resolve = AmbiguousColumnResolver.resolve(arrayList, new String[][]{new String[]{NetworkAnalyticsConstants.DataPoints.UID, "rank"}, new String[]{NetworkAnalyticsConstants.DataPoints.UID, "widget_id", "component_name", "item_id"}});
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    while (prepare.step()) {
                        CommunalItemRank communalItemRank = new CommunalItemRank(prepare.getLong(resolve[0][0]), (int) prepare.getLong(resolve[0][1]));
                        if (prepare.isNull(resolve[1][0]) && prepare.isNull(resolve[1][1]) && prepare.isNull(resolve[1][2]) && prepare.isNull(resolve[1][3])) {
                            linkedHashMap.put(communalItemRank, null);
                        } else {
                            CommunalWidgetItem communalWidgetItem = new CommunalWidgetItem(prepare.getLong(resolve[1][0]), (int) prepare.getLong(resolve[1][1]), prepare.isNull(resolve[1][2]) ? null : prepare.getText(resolve[1][2]), prepare.getLong(resolve[1][3]));
                            if (!linkedHashMap.containsKey(communalItemRank)) {
                                linkedHashMap.put(communalItemRank, communalWidgetItem);
                            }
                        }
                    }
                    return linkedHashMap;
                } catch (Throwable th) {
                    throw th;
                }
            case 1:
                prepare = ((SQLiteConnection) obj).prepare("DELETE FROM communal_widget_table");
                try {
                    prepare.step();
                    prepare.close();
                    return null;
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
