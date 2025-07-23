package com.android.systemui.animation.back;

import android.window.BackEvent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface BackAnimationSpec {
    public static final Companion Companion = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
