package com.android.systemui.popup.util;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PopupUIUtil_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final PopupUIUtil_Factory INSTANCE = new PopupUIUtil_Factory();

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
