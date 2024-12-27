package com.android.systemui.popup.util;

import dagger.internal.Provider;

public final class PopupUIUtil_Factory implements Provider {

    final class InstanceHolder {
        private static final PopupUIUtil_Factory INSTANCE = new PopupUIUtil_Factory();

        private InstanceHolder() {
        }
    }

    public static PopupUIUtil_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static PopupUIUtil newInstance() {
        return new PopupUIUtil();
    }

    @Override // javax.inject.Provider
    public PopupUIUtil get() {
        return newInstance();
    }
}
