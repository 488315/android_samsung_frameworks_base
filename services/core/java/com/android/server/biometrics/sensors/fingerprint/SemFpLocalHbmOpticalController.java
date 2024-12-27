package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.util.Pair;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda1;
import com.android.server.biometrics.SemBiometricDisplayStateMonitor;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.SemBioSysFsProvider;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.function.IntConsumer;

public final class SemFpLocalHbmOpticalController
        implements SemBiometricDisplayStateMonitor.DisplayStateCallback {
    static final String LOCAL_HBM_PATH_OF_IN_HOUSE = "/sys/class/lcd/panel/local_hbm";
    static final String LOCAL_HBM_PATH_OF_JDM = "/sys/class/display/display_ctrl/lhbm_mode_set";
    public final SemBiometricDisplayStateMonitor mDisplayStateMonitor;
    public SemFpLocalHbmOpticalController$$ExternalSyntheticLambda1
            mLocalHbmModeChangeAfterScreenOn;
    public final SemBioSysFsProvider mSysFsProvider;
    public SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2 mTouchDownDeliverAfterLhbmOn;
    public final boolean mUseInHouseSolution;
    public int mStartPhysicalDisplayState = 2;
    public LocalHbmStatus mCurrentLocalHbmStatus = LocalHbmStatus.LOCAL_HBM_MODE_OFF;

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.SemFpLocalHbmOpticalController$1, reason: invalid class name */
    public final class AnonymousClass1 implements SemBioSysFsProvider {}

    enum LocalHbmStatus {
        LOCAL_HBM_MODE_OFF("0"),
        LOCAL_HBM_MODE_ON_SOURCE_OFF("1"),
        LOCAL_HBM_MODE_ON_SOURCE_ON("2");

        private final String value;

        LocalHbmStatus(String str) {
            this.value = str;
        }

        public final String getString() {
            return this.value;
        }
    }

    public SemFpLocalHbmOpticalController(
            Handler handler,
            SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor,
            SemBioSysFsProvider semBioSysFsProvider,
            boolean z) {
        this.mSysFsProvider = semBioSysFsProvider;
        this.mUseInHouseSolution = z;
        this.mDisplayStateMonitor = semBiometricDisplayStateMonitor;
        semBiometricDisplayStateMonitor.registerStateCallback(this);
        IntConsumer intConsumer =
                new IntConsumer() { // from class:
                                    // com.android.server.biometrics.sensors.fingerprint.SemFpLocalHbmOpticalController$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        SemFpLocalHbmOpticalController semFpLocalHbmOpticalController =
                                SemFpLocalHbmOpticalController.this;
                        semFpLocalHbmOpticalController.mStartPhysicalDisplayState = i;
                        if (i != 2) {
                            semFpLocalHbmOpticalController.handleLocalHbm(0);
                        }
                    }
                };
        if (semBiometricDisplayStateMonitor.mBlockingTasksWhenStartPhysicalState.contains(
                intConsumer)) {
            return;
        }
        semBiometricDisplayStateMonitor.mBlockingTasksWhenStartPhysicalState.add(intConsumer);
    }

    public final void changeToNextState(LocalHbmStatus localHbmStatus) {
        LocalHbmStatus localHbmStatus2 = LocalHbmStatus.LOCAL_HBM_MODE_OFF;
        LocalHbmStatus localHbmStatus3 = LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF;
        LocalHbmStatus localHbmStatus4 = LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON;
        boolean z = this.mUseInHouseSolution;
        if (!z) {
            localHbmStatus = localHbmStatus == localHbmStatus4 ? localHbmStatus3 : localHbmStatus2;
        }
        int ordinal = this.mCurrentLocalHbmStatus.ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    return;
                }
                if (localHbmStatus == localHbmStatus3) {
                    writeLocalHbmStatus(localHbmStatus3);
                } else {
                    if (localHbmStatus != localHbmStatus2) {
                        return;
                    }
                    writeLocalHbmStatus(localHbmStatus3);
                    writeLocalHbmStatus(localHbmStatus2);
                }
            } else if (localHbmStatus == localHbmStatus2) {
                writeLocalHbmStatus(localHbmStatus2);
            } else if (localHbmStatus != localHbmStatus4) {
                return;
            } else {
                writeLocalHbmStatus(localHbmStatus4);
            }
        } else if (localHbmStatus == localHbmStatus3) {
            writeLocalHbmStatus(localHbmStatus3);
        } else {
            if (localHbmStatus != localHbmStatus4) {
                return;
            }
            writeLocalHbmStatus(localHbmStatus3);
            writeLocalHbmStatus(localHbmStatus4);
        }
        Slog.i(
                "FingerprintService.SemFpLhbmOpticalController",
                "LocalHbmStatus change from : "
                        + this.mCurrentLocalHbmStatus
                        + " to : "
                        + localHbmStatus);
        this.mCurrentLocalHbmStatus = localHbmStatus;
        if (z) {
            if (localHbmStatus != localHbmStatus4) {
                return;
            }
        } else if (localHbmStatus != localHbmStatus3) {
            return;
        }
        SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2
                semFpLocalHbmOpticalController$$ExternalSyntheticLambda2 =
                        this.mTouchDownDeliverAfterLhbmOn;
        if (semFpLocalHbmOpticalController$$ExternalSyntheticLambda2 != null) {
            semFpLocalHbmOpticalController$$ExternalSyntheticLambda2.run();
            this.mTouchDownDeliverAfterLhbmOn = null;
        }
    }

    public final synchronized void handleLocalHbm(int i) {
        try {
            if (i == 0) {
                changeToNextState(LocalHbmStatus.LOCAL_HBM_MODE_OFF);
            } else if (this.mStartPhysicalDisplayState == 2
                    && this.mDisplayStateMonitor.mPhysicalDisplayState == 2) {
                if (i == 1) {
                    changeToNextState(LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF);
                } else if (i == 2) {
                    changeToNextState(LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON);
                }
                this.mLocalHbmModeChangeAfterScreenOn = null;
            } else {
                this.mLocalHbmModeChangeAfterScreenOn =
                        new SemFpLocalHbmOpticalController$$ExternalSyntheticLambda1(this, i);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void handleTouchEventInLhbm(Pair pair, int i, long j) {
        DisplayManagerInternal displayManagerInternal;
        int i2 = 0;
        boolean z = this.mUseInHouseSolution;
        if (i == 2) {
            this.mTouchDownDeliverAfterLhbmOn = null;
            if (!z
                    ? this.mCurrentLocalHbmStatus != LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF
                    : this.mCurrentLocalHbmStatus != LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON) {
                this.mTouchDownDeliverAfterLhbmOn =
                        new SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2(
                                this, pair, i, j);
                return;
            }
        }
        byte[] bArr = {(byte) i};
        if (z) {
            ServiceProvider serviceProvider = (ServiceProvider) pair.second;
            int intValue = ((Integer) pair.first).intValue();
            boolean z2 = i == 2;
            if (z
                    && (displayManagerInternal =
                                    (DisplayManagerInternal)
                                            LocalServices.getService(DisplayManagerInternal.class))
                            != null) {
                i2 = displayManagerInternal.setFreezeBrightnessMode(z2);
            }
            ((FingerprintProvider) serviceProvider).semRequest(intValue, 43, i2, bArr, null);
        } else {
            ((FingerprintProvider) ((ServiceProvider) pair.second))
                    .semRequest(((Integer) pair.first).intValue(), 22, i, null, null);
        }
        BaseClientMonitor semGetCurrentClient =
                ((FingerprintProvider) ((ServiceProvider) pair.second)).semGetCurrentClient();
        long j2 = semGetCurrentClient == null ? 0L : semGetCurrentClient.mRequestId;
        if (i != 2) {
            if (i == 1) {
                ((FingerprintProvider) ((ServiceProvider) pair.second))
                        .onPointerUp(j2, ((Integer) pair.first).intValue(), new PointerContext());
            }
        } else {
            ((FingerprintProvider) ((ServiceProvider) pair.second))
                    .onPointerDown(j2, ((Integer) pair.first).intValue(), new PointerContext());
            if (semGetCurrentClient instanceof AuthenticationClient) {
                SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
                semBioLoggingManager
                        .getFpHandler()
                        .post(
                                new SemBioLoggingManager$$ExternalSyntheticLambda1(
                                        semBioLoggingManager,
                                        (int) j2,
                                        (int) (j >> 16),
                                        (int) (j & 65535)));
            }
        }
    }

    @Override // com.android.server.biometrics.SemBiometricDisplayStateMonitor.DisplayStateCallback
    public final void onFinishDisplayState(int i, int i2, int i3) {
        SemFpLocalHbmOpticalController$$ExternalSyntheticLambda1
                semFpLocalHbmOpticalController$$ExternalSyntheticLambda1;
        if (this.mUseInHouseSolution
                && i2 == 2
                && (semFpLocalHbmOpticalController$$ExternalSyntheticLambda1 =
                                this.mLocalHbmModeChangeAfterScreenOn)
                        != null) {
            semFpLocalHbmOpticalController$$ExternalSyntheticLambda1.run();
            this.mLocalHbmModeChangeAfterScreenOn = null;
        }
    }

    public final void writeLocalHbmStatus(LocalHbmStatus localHbmStatus) {
        boolean z = this.mUseInHouseSolution;
        SemBioSysFsProvider semBioSysFsProvider = this.mSysFsProvider;
        if (z) {
            String string = localHbmStatus.getString();
            semBioSysFsProvider.getClass();
            Utils.writeFile(
                    new File(LOCAL_HBM_PATH_OF_IN_HOUSE), string.getBytes(StandardCharsets.UTF_8));
        } else {
            String string2 = localHbmStatus.getString();
            semBioSysFsProvider.getClass();
            Utils.writeFile(
                    new File(LOCAL_HBM_PATH_OF_JDM), string2.getBytes(StandardCharsets.UTF_8));
        }
    }
}
