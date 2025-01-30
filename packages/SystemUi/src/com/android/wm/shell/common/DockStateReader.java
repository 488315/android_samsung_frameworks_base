package com.android.wm.shell.common;

import android.content.Context;
import android.content.IntentFilter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DockStateReader {
    public static final IntentFilter DOCK_INTENT_FILTER = new IntentFilter("android.intent.action.DOCK_EVENT");
    public final Context mContext;

    public DockStateReader(Context context) {
        this.mContext = context;
    }
}
