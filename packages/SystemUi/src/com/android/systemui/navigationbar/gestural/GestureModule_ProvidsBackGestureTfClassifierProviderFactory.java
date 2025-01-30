package com.android.systemui.navigationbar.gestural;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GestureModule_ProvidsBackGestureTfClassifierProviderFactory implements Provider {
    public static BackGestureTfClassifierProvider providsBackGestureTfClassifierProvider() {
        return new BackGestureTfClassifierProvider();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BackGestureTfClassifierProvider();
    }
}
