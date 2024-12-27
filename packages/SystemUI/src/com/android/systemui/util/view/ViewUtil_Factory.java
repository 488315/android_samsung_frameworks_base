package com.android.systemui.util.view;

import dagger.internal.Provider;

public final class ViewUtil_Factory implements Provider {

    final class InstanceHolder {
        private static final ViewUtil_Factory INSTANCE = new ViewUtil_Factory();

        private InstanceHolder() {
        }
    }

    public static ViewUtil_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ViewUtil newInstance() {
        return new ViewUtil();
    }

    @Override // javax.inject.Provider
    public ViewUtil get() {
        return newInstance();
    }
}
