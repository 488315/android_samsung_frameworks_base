package com.android.systemui.bouncer.log;

import android.os.Build;
import com.android.systemui.CoreStartable;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricSettingsInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.log.BouncerLogger;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BouncerLoggerStartable implements CoreStartable {
    public final CoroutineScope applicationScope;
    public final DeviceEntryBiometricSettingsInteractor biometricSettingsInteractor;
    public final BouncerLogger bouncerLogger;
    public final DeviceEntryFaceAuthInteractor faceAuthInteractor;
    public final DeviceEntryFingerprintAuthInteractor fingerprintAuthInteractor;

    public BouncerLoggerStartable(CoroutineScope coroutineScope, DeviceEntryBiometricSettingsInteractor deviceEntryBiometricSettingsInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, BouncerLogger bouncerLogger) {
        this.applicationScope = coroutineScope;
        this.biometricSettingsInteractor = deviceEntryBiometricSettingsInteractor;
        this.faceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.fingerprintAuthInteractor = deviceEntryFingerprintAuthInteractor;
        this.bouncerLogger = bouncerLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Build.isDebuggable()) {
            BouncerLoggerStartable$start$1 bouncerLoggerStartable$start$1 = new BouncerLoggerStartable$start$1(this, null);
            CoroutineScope coroutineScope = this.applicationScope;
            BuildersKt.launch$default(coroutineScope, null, null, bouncerLoggerStartable$start$1, 3);
            BuildersKt.launch$default(coroutineScope, null, null, new BouncerLoggerStartable$start$2(this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new BouncerLoggerStartable$start$3(this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new BouncerLoggerStartable$start$4(this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new BouncerLoggerStartable$start$5(this, null), 3);
        }
    }
}
