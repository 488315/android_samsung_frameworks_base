package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = DozeServicePlugin.ACTION, version = 1)
public interface DozeServicePlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_DOZE";
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface RequestDoze {
        void onRequestHideDoze();

        void onRequestShowDoze();
    }

    void onDreamingStarted();

    void onDreamingStopped();

    void setDozeRequester(RequestDoze requestDoze);
}
