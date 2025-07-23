package com.android.systemui.highlight.database.table;

import com.android.systemui.highlight.database.Columns;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.settings.ImsSettings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class InteractionAverageTimeTable {
    public static final Columns[] COLUMNS = {Columns.create("id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"), Columns.create("key", ImsSettings.TYPE_TEXT, "NON NULL UNIQUE"), Columns.create("time", "LONG", "NON NULL"), Columns.create(SystemUIAnalytics.QPNE_KEY_COUNT, "INTEGER", "NON NULL"), Columns.create("last_title", ImsSettings.TYPE_TEXT), Columns.create("last_text", ImsSettings.TYPE_TEXT), Columns.create("last_when", "LONG")};
}
