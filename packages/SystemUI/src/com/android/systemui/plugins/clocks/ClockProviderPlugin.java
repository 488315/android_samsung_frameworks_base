package com.android.systemui.plugins.clocks;

import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER", version = 1)
public interface ClockProviderPlugin extends Plugin, ClockProvider {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
        public static final int VERSION = 1;

        private Companion() {
        }
    }
}
