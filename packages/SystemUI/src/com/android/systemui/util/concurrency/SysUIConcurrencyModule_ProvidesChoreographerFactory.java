package com.android.systemui.util.concurrency;

import android.view.Choreographer;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvidesChoreographerFactory implements Provider {

    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvidesChoreographerFactory INSTANCE = new SysUIConcurrencyModule_ProvidesChoreographerFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvidesChoreographerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Choreographer providesChoreographer() {
        Choreographer choreographerProvidesChoreographer = SysUIConcurrencyModule.INSTANCE.providesChoreographer();
        choreographerProvidesChoreographer.getClass();
        return choreographerProvidesChoreographer;
    }

    @Override // javax.inject.Provider
    public Choreographer get() {
        return providesChoreographer();
    }
}
