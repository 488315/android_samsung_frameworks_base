package com.android.systemui.popup.util;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PopupUIUtil_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
