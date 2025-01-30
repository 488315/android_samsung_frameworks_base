package com.android.keyguard.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardSecSecurityContainer;
import com.android.systemui.R;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.keyguard.dagger.KeyguardBouncerModule_ProvidesKeyguardSecSecurityContainerFactory */
/* loaded from: classes.dex */
public final class C0859xfbd8c533 implements Provider {
    public final Provider layoutInflaterProvider;
    public final Provider rootViewProvider;

    public C0859xfbd8c533(Provider provider, Provider provider2) {
        this.rootViewProvider = provider;
        this.layoutInflaterProvider = provider2;
    }

    public static KeyguardSecSecurityContainer providesKeyguardSecSecurityContainer(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        KeyguardSecSecurityContainer keyguardSecSecurityContainer = (KeyguardSecSecurityContainer) layoutInflater.inflate(R.layout.keyguard_sec_security_container_view, viewGroup, false);
        viewGroup.addView(keyguardSecSecurityContainer);
        Preconditions.checkNotNullFromProvides(keyguardSecSecurityContainer);
        return keyguardSecSecurityContainer;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesKeyguardSecSecurityContainer((LayoutInflater) this.layoutInflaterProvider.get(), (ViewGroup) this.rootViewProvider.get());
    }
}
