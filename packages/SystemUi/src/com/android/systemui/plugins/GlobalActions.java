package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = GlobalActions.ACTION, version = 1)
@DependsOn(target = GlobalActionsManager.class)
/* loaded from: classes2.dex */
public interface GlobalActions extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_GLOBAL_ACTIONS";
    public static final int VERSION = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @ProvidesInterface(version = 1)
    public interface GlobalActionsManager {
        public static final int VERSION = 1;

        boolean isFOTAAvailableForGlobalActions();

        void onGlobalActionsHidden();

        void onGlobalActionsShown();

        void reboot(boolean z);

        void shutdown();
    }

    void showGlobalActions(GlobalActionsManager globalActionsManager);

    void showGlobalActions(GlobalActionsManager globalActionsManager, int i);

    default void destroy() {
    }

    default void showShutdownUi(boolean z, String str) {
    }
}
