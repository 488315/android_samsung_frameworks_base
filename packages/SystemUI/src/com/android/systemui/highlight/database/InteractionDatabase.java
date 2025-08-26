package com.android.systemui.highlight.database;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.highlight.database.table.InteractionAverageTimeTable;
import com.android.systemui.highlight.database.table.InteractionHistoryTable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class InteractionDatabase extends HighlightDbHelper {
    public InteractionDatabase(Context context) {
        super(context, "user_interaction_database", 1);
        new Handler();
        new InteractionCache();
    }

    @Override // com.android.systemui.highlight.database.HighlightDbHelper
    public final void init(Map map) {
        HashMap map2 = (HashMap) map;
        map2.put("user_interaction_history", InteractionHistoryTable.COLUMNS);
        map2.put("user_interaction_time", InteractionAverageTimeTable.COLUMNS);
    }
}
