package com.android.wm.shell.common;

import android.content.Context;
import android.content.IntentFilter;

/* loaded from: classes3.dex */
public class DockStateReader {
    public static final IntentFilter DOCK_INTENT_FILTER = new IntentFilter("android.intent.action.DOCK_EVENT");
    public final Context mContext;

    public DockStateReader(Context context) {
        this.mContext = context;
    }
}
