package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = "testAction", version = 1)
/* loaded from: classes2.dex */
public interface TestPlugin extends Plugin {
    public static final String ACTION = "testAction";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "testAction";
        public static final int VERSION = 1;

        private Companion() {
        }
    }

    Object methodThrowsError();
}
