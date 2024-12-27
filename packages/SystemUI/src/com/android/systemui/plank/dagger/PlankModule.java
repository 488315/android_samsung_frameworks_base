package com.android.systemui.plank.dagger;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class PlankModule {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
