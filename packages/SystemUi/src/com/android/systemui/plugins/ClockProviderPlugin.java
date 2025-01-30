package com.android.systemui.plugins;

import com.android.systemui.plugins.ClockProvider;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER", version = 1)
/* loaded from: classes2.dex */
public interface ClockProviderPlugin extends Plugin, ClockProvider {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
        public static final int VERSION = 1;

        private Companion() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DefaultImpls {
        public static ClockController createClock(ClockProviderPlugin clockProviderPlugin, String str) {
            return ClockProvider.DefaultImpls.createClock(clockProviderPlugin, str);
        }
    }
}
