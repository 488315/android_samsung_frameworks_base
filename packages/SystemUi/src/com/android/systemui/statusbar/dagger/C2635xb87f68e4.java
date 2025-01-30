package com.android.systemui.statusbar.dagger;

import android.content.Context;
import com.android.systemui.statusbar.phone.StatusBarIconList;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideStatusBarIconListFactory */
/* loaded from: classes2.dex */
public final class C2635xb87f68e4 implements Provider {
    public final Provider contextProvider;

    public C2635xb87f68e4(Provider provider) {
        this.contextProvider = provider;
    }

    public static StatusBarIconList provideStatusBarIconList(Context context) {
        return new StatusBarIconList(context.getResources().getStringArray(17236309));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStatusBarIconList((Context) this.contextProvider.get());
    }
}
