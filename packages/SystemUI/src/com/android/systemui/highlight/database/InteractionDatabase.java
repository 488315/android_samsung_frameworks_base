package com.android.systemui.highlight.database;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.highlight.database.table.InteractionAverageTimeTable;
import com.android.systemui.highlight.database.table.InteractionHistoryTable;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class InteractionDatabase extends HighlightDbHelper {
    public InteractionDatabase(Context context) {
        super(context, "user_interaction_database", 1);
        new Handler();
        new InteractionCache();
    }

    @Override // com.android.systemui.highlight.database.HighlightDbHelper
    public final void init(Map map) {
        HashMap hashMap = (HashMap) map;
        hashMap.put("user_interaction_history", InteractionHistoryTable.COLUMNS);
        hashMap.put("user_interaction_time", InteractionAverageTimeTable.COLUMNS);
    }
}
