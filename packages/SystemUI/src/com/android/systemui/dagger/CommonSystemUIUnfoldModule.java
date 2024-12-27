package com.android.systemui.dagger;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class CommonSystemUIUnfoldModule {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
