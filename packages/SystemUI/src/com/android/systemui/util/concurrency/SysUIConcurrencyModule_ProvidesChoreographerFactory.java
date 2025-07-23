package com.android.systemui.util.concurrency;

import android.view.Choreographer;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvidesChoreographerFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvidesChoreographerFactory INSTANCE = new SysUIConcurrencyModule_ProvidesChoreographerFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvidesChoreographerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Choreographer providesChoreographer() {
        Choreographer providesChoreographer = SysUIConcurrencyModule.INSTANCE.providesChoreographer();
        providesChoreographer.getClass();
        return providesChoreographer;
    }

    @Override // javax.inject.Provider
    public Choreographer get() {
        return providesChoreographer();
    }
}
