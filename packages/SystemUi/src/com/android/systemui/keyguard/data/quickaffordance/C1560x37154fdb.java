package com.android.systemui.keyguard.data.quickaffordance;

import dagger.internal.Preconditions;
import java.util.Set;
import javax.inject.Provider;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory */
/* loaded from: classes.dex */
public final class C1560x37154fdb implements Provider {
    public final Provider cameraProvider;
    public final Provider doNotDisturbProvider;
    public final Provider flashlightProvider;
    public final Provider homeProvider;
    public final Provider muteProvider;
    public final Provider qrCodeScannerProvider;
    public final Provider quickAccessWalletProvider;
    public final Provider videoCameraProvider;

    public C1560x37154fdb(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.cameraProvider = provider;
        this.doNotDisturbProvider = provider2;
        this.flashlightProvider = provider3;
        this.homeProvider = provider4;
        this.muteProvider = provider5;
        this.quickAccessWalletProvider = provider6;
        this.qrCodeScannerProvider = provider7;
        this.videoCameraProvider = provider8;
    }

    public static Set quickAffordanceConfigs(CameraQuickAffordanceConfig cameraQuickAffordanceConfig, DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig, HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, MuteQuickAffordanceConfig muteQuickAffordanceConfig, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig, VideoCameraQuickAffordanceConfig videoCameraQuickAffordanceConfig) {
        KeyguardDataQuickAffordanceModule.Companion.getClass();
        Set of = SetsKt__SetsKt.setOf(cameraQuickAffordanceConfig, doNotDisturbQuickAffordanceConfig, flashlightQuickAffordanceConfig, homeControlsKeyguardQuickAffordanceConfig, muteQuickAffordanceConfig, quickAccessWalletKeyguardQuickAffordanceConfig, qrCodeScannerKeyguardQuickAffordanceConfig, videoCameraQuickAffordanceConfig);
        Preconditions.checkNotNullFromProvides(of);
        return of;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return quickAffordanceConfigs((CameraQuickAffordanceConfig) this.cameraProvider.get(), (DoNotDisturbQuickAffordanceConfig) this.doNotDisturbProvider.get(), (FlashlightQuickAffordanceConfig) this.flashlightProvider.get(), (HomeControlsKeyguardQuickAffordanceConfig) this.homeProvider.get(), (MuteQuickAffordanceConfig) this.muteProvider.get(), (QuickAccessWalletKeyguardQuickAffordanceConfig) this.quickAccessWalletProvider.get(), (QrCodeScannerKeyguardQuickAffordanceConfig) this.qrCodeScannerProvider.get(), (VideoCameraQuickAffordanceConfig) this.videoCameraProvider.get());
    }
}
