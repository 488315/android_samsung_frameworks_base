package com.android.systemui.biometrics;

import android.content.res.Resources;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import dagger.internal.Provider;

/* loaded from: classes.dex */
public final class BiometricNotificationDialogFactory_Factory implements Provider {
    public final Provider faceManagerProvider;
    public final Provider fingerprintManagerProvider;
    public final Provider resourcesProvider;
    public final Provider shadeDialogContextInteractorProvider;
    public final Provider systemUIDialogFactoryProvider;

    public BiometricNotificationDialogFactory_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.resourcesProvider = provider;
        this.systemUIDialogFactoryProvider = provider2;
        this.shadeDialogContextInteractorProvider = provider3;
        this.fingerprintManagerProvider = provider4;
        this.faceManagerProvider = provider5;
    }

    public static BiometricNotificationDialogFactory newInstance(Resources resources, SystemUIDialog.Factory factory, ShadeDialogContextInteractor shadeDialogContextInteractor, FingerprintManager fingerprintManager, FaceManager faceManager) {
        return new BiometricNotificationDialogFactory(resources, factory, shadeDialogContextInteractor, fingerprintManager, faceManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BiometricNotificationDialogFactory((Resources) this.resourcesProvider.get(), (SystemUIDialog.Factory) this.systemUIDialogFactoryProvider.get(), (ShadeDialogContextInteractor) this.shadeDialogContextInteractorProvider.get(), (FingerprintManager) this.fingerprintManagerProvider.get(), (FaceManager) this.faceManagerProvider.get());
    }
}
