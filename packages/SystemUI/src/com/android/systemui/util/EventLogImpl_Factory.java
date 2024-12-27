package com.android.systemui.util;

import dagger.internal.Provider;

public final class EventLogImpl_Factory implements Provider {

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
