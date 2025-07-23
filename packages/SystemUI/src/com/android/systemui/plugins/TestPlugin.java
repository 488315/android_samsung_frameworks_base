package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = "testAction", version = 1)
/* loaded from: classes2.dex */
public interface TestPlugin extends Plugin {
    public static final String ACTION = "testAction";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "testAction";
        public static final int VERSION = 1;

        private Companion() {
        }
    }

    Object methodThrowsError();
}
