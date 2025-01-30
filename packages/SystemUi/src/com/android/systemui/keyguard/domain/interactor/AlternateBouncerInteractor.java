package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AlternateBouncerInteractor {
    public final KeyguardBouncerRepository bouncerRepository;
    public final StateFlow isVisible;
    public boolean receivedDownTouch;
    public final StatusBarStateController statusBarStateController;
    public final SystemClock systemClock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AlternateBouncerInteractor(StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, KeyguardBouncerRepository keyguardBouncerRepository, BiometricSettingsRepository biometricSettingsRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, SystemClock systemClock) {
        this.statusBarStateController = statusBarStateController;
        this.bouncerRepository = keyguardBouncerRepository;
        this.systemClock = systemClock;
        this.isVisible = ((KeyguardBouncerRepositoryImpl) keyguardBouncerRepository).alternateBouncerVisible;
    }

    public final boolean hide() {
        this.receivedDownTouch = false;
        boolean isVisibleState = isVisibleState();
        ((KeyguardBouncerRepositoryImpl) this.bouncerRepository).setAlternateVisible();
        return isVisibleState && !isVisibleState();
    }

    public final boolean isVisibleState() {
        return ((Boolean) ((KeyguardBouncerRepositoryImpl) this.bouncerRepository).alternateBouncerVisible.getValue()).booleanValue();
    }
}
