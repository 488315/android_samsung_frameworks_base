package com.android.wm.shell.common;

import android.content.Context;
import android.content.IntentFilter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DockStateReader {
    public static final IntentFilter DOCK_INTENT_FILTER = new IntentFilter("android.intent.action.DOCK_EVENT");
    public final Context mContext;

    public DockStateReader(Context context) {
        this.mContext = context;
    }
}
