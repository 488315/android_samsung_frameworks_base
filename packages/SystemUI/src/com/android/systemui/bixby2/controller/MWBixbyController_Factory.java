package com.android.systemui.bixby2.controller;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MWBixbyController_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final MWBixbyController_Factory INSTANCE = new MWBixbyController_Factory();

        private InstanceHolder() {
        }
    }

    public static MWBixbyController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MWBixbyController newInstance() {
        return new MWBixbyController();
    }

    @Override // javax.inject.Provider
    public MWBixbyController get() {
        return newInstance();
    }
}
