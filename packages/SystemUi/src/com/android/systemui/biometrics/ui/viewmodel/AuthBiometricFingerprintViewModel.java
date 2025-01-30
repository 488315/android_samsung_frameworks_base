package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthBiometricFingerprintViewModel {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 iconAsset;
    public final DisplayStateInteractor interactor;
    public int rotation;

    public AuthBiometricFingerprintViewModel(DisplayStateInteractor displayStateInteractor) {
        this.interactor = displayStateInteractor;
        DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) displayStateInteractor;
        this.iconAsset = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayStateInteractorImpl.isFolded, displayStateInteractorImpl.isInRearDisplayMode, new AuthBiometricFingerprintViewModel$iconAsset$1(this, null));
    }
}
