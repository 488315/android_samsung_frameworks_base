package com.android.systemui.plugins.clocks;

import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER", version = 1)
/* loaded from: classes2.dex */
public interface ClockProviderPlugin extends Plugin, ClockProvider {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
        public static final int VERSION = 1;

        private Companion() {
        }
    }
}
