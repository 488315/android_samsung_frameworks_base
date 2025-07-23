package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = DozeServicePlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface DozeServicePlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_DOZE";
    public static final int VERSION = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface RequestDoze {
        void onRequestHideDoze();

        void onRequestShowDoze();
    }

    void onDreamingStarted();

    void onDreamingStopped();

    void setDozeRequester(RequestDoze requestDoze);
}
