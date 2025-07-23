package com.android.systemui.highlight.database.table;

import com.android.systemui.highlight.database.Columns;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.sec.ims.settings.ImsSettings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class InteractionHistoryTable {
    public static final Columns[] COLUMNS = {Columns.create("id", "INTEGER", "PRIMARY KEY AUTOINCREMENT"), Columns.create("action", "INTEGER", "NON NULL"), Columns.create("package_name", ImsSettings.TYPE_TEXT, "NON NULL"), Columns.create("noti_key", ImsSettings.TYPE_TEXT, "NON NULL"), Columns.create("post_time", "LONG", "NON NULL"), Columns.create("action_time", "LONG", "NON NULL"), Columns.create(UniversalCredentialUtil.AGENT_TITLE, ImsSettings.TYPE_TEXT), Columns.create("text", ImsSettings.TYPE_TEXT)};
}
