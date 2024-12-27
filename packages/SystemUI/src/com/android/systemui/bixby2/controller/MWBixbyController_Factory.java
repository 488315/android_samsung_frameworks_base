package com.android.systemui.bixby2.controller;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class MWBixbyController_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final MWBixbyController_Factory INSTANCE = new MWBixbyController_Factory();

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
