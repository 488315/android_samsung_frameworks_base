package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class EventLogImpl_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final EventLogImpl_Factory INSTANCE = new EventLogImpl_Factory();

        private InstanceHolder() {
        }
    }

    public static EventLogImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static EventLogImpl newInstance() {
        return new EventLogImpl();
    }

    @Override // javax.inject.Provider
    public EventLogImpl get() {
        return newInstance();
    }
}
