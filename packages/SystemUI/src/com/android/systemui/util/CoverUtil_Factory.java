package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CoverUtil_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final CoverUtil_Factory INSTANCE = new CoverUtil_Factory();

        private InstanceHolder() {
        }
    }

    public static CoverUtil_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static CoverUtil newInstance() {
        return new CoverUtil();
    }

    @Override // javax.inject.Provider
    public CoverUtil get() {
        return newInstance();
    }
}
