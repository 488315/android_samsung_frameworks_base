package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = DozeServicePlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface DozeServicePlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_DOZE";
    public static final int VERSION = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RequestDoze {
        void onRequestHideDoze();

        void onRequestShowDoze();
    }

    void onDreamingStarted();

    void onDreamingStopped();

    void setDozeRequester(RequestDoze requestDoze);
}
