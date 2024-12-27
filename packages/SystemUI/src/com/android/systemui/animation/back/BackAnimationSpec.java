package com.android.systemui.animation.back;

import android.window.BackEvent;

public interface BackAnimationSpec {
    public static final Companion Companion = null;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    static {
        Companion companion = Companion.$$INSTANCE;
    }

    void getBackTransformation(BackEvent backEvent, float f, BackTransformation backTransformation);
}
