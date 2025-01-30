package com.android.keyguard.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.systemui.R;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory implements Provider {
    public final Provider layoutInflaterProvider;
    public final Provider rootViewProvider;

    public KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory(Provider provider, Provider provider2) {
        this.rootViewProvider = provider;
        this.layoutInflaterProvider = provider2;
    }

    public static KeyguardSecurityContainer providesKeyguardSecurityContainer(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) layoutInflater.inflate(R.layout.keyguard_security_container_view, viewGroup, false);
        Preconditions.checkNotNullFromProvides(keyguardSecurityContainer);
        return keyguardSecurityContainer;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesKeyguardSecurityContainer((LayoutInflater) this.layoutInflaterProvider.get(), (ViewGroup) this.rootViewProvider.get());
    }
}
