package com.android.server.biometrics;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.common.OperationContext;
import android.os.IBinder;
import android.os.RemoteException;
import android.security.KeyStoreAuthorization;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricService;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricFrameworkStatsLogger;
import com.android.server.biometrics.log.OperationContextExt;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AuthSession implements IBinder.DeathRecipient {
    public long mAuthenticatedTimeMs;
    public final SparseArray mAuthenticationResults;
    public final BiometricContext mBiometricContext;
    final BiometricFrameworkStatsLogger mBiometricFrameworkStatsLogger;
    public final BiometricManager mBiometricManager;
    public boolean mCancelled;
    public final ClientDeathReceiver mClientDeathReceiver;
    public final IBiometricServiceReceiver mClientReceiver;
    public final Context mContext;
    public BiometricSensor mCurrentSensor;
    public final boolean mDebugEnabled;
    public int mErrorEscrow;
    public final KeyStoreAuthorization mKeyStoreAuthorization;
    public final String mOpPackageName;
    public final OperationContextExt mOperationContext;
    public final long mOperationId;
    public final PreAuthInfo mPreAuthInfo;
    final PromptInfo mPromptInfo;
    public final Random mRandom;
    public final long mRequestId;
    final IBiometricSensorReceiver mSensorReceiver;
    public int[] mSensors;
    public final List mSfpsSensorIds;
    public long mStartTimeMs;
    public final SemBiometricSysUiWrapper mStatusBarService;
    final IBiometricSysuiReceiver mSysuiReceiver;
    final IBinder mToken;
    public byte[] mTokenEscrow;
    public final int mUserId;
    public int mVendorCodeEscrow;
    public int mState = 0;
    public int mAuthenticatedSensorId = -1;
    public boolean mUseSwitchingMode = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthenticationResult {
        public byte[] challengeToken;
        public int id;
        public String name;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ClientDeathReceiver {
    }

    public AuthSession(Context context, BiometricContext biometricContext, SemBiometricSysUiWrapper semBiometricSysUiWrapper, IBiometricSysuiReceiver iBiometricSysuiReceiver, KeyStoreAuthorization keyStoreAuthorization, Random random, ClientDeathReceiver clientDeathReceiver, PreAuthInfo preAuthInfo, IBinder iBinder, long j, long j2, int i, IBiometricSensorReceiver iBiometricSensorReceiver, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo, boolean z, List list, BiometricFrameworkStatsLogger biometricFrameworkStatsLogger) {
        Slog.d("BiometricService/AuthSession", "Creating AuthSession with: " + preAuthInfo);
        this.mContext = context;
        this.mBiometricContext = biometricContext;
        this.mStatusBarService = semBiometricSysUiWrapper;
        this.mSysuiReceiver = iBiometricSysuiReceiver;
        this.mKeyStoreAuthorization = keyStoreAuthorization;
        this.mRandom = random;
        this.mClientDeathReceiver = clientDeathReceiver;
        this.mPreAuthInfo = preAuthInfo;
        this.mToken = iBinder;
        this.mRequestId = j;
        this.mOperationId = j2;
        this.mUserId = i;
        this.mSensorReceiver = iBiometricSensorReceiver;
        this.mClientReceiver = iBiometricServiceReceiver;
        this.mOpPackageName = str;
        this.mPromptInfo = promptInfo;
        this.mDebugEnabled = z;
        this.mCancelled = false;
        this.mBiometricFrameworkStatsLogger = biometricFrameworkStatsLogger;
        this.mOperationContext = new OperationContextExt(new OperationContext(), true, 0, preAuthInfo.mIsMandatoryBiometricsAuthentication);
        this.mBiometricManager = (BiometricManager) context.getSystemService(BiometricManager.class);
        this.mSfpsSensorIds = list.stream().filter(new AuthSession$$ExternalSyntheticLambda0()).map(new AuthSession$$ExternalSyntheticLambda1(0)).toList();
        try {
            iBiometricServiceReceiver.asBinder().linkToDeath(this, 0);
        } catch (RemoteException unused) {
            Slog.w("BiometricService/AuthSession", "Unable to link to death");
        }
        for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(new StringBuilder("set to unknown state sensor: "), biometricSensor.id, "BiometricService/AuthSession");
            biometricSensor.mSensorState = 0;
            biometricSensor.mCookie = 0;
        }
        this.mAuthenticationResults = new SparseArray(1);
        SemBiometricSysUiWrapper semBiometricSysUiWrapper2 = this.mStatusBarService;
        semBiometricSysUiWrapper2.getClass();
        if (iBiometricSysuiReceiver instanceof BiometricService.AnonymousClass2) {
            BiometricService.AnonymousClass2 anonymousClass2 = (BiometricService.AnonymousClass2) iBiometricSysuiReceiver;
            ((HashMap) semBiometricSysUiWrapper2.mSessions).put(Long.valueOf(j), new Pair(Integer.valueOf(semBiometricSysUiWrapper2.mSysUiManager.openSession(iBinder.toString() + ":" + j, anonymousClass2.mSysUiListener)), anonymousClass2));
        }
        SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
        semBioLoggingManager.getClass();
        SemBioLoggingManager.LoggingInfo loggingInfo = new SemBioLoggingManager.LoggingInfo();
        loggingInfo.mStartTime = System.currentTimeMillis();
        loggingInfo.mType = "AP";
        loggingInfo.mPackageName = str;
        semBioLoggingManager.mBpLoggingInfo.append((int) j, loggingInfo);
        if (semBioLoggingManager.mBpLoggingInfo.size() >= SemBioLoggingManager.LOG_ARRAY_SIZE) {
            semBioLoggingManager.mBpLoggingInfo.removeAt(0);
        }
    }

    public boolean allCookiesReceived() {
        int i = 0;
        for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (biometricSensor.mSensorState == 1) {
                Slog.d("BiometricService/PreAuthInfo", "Sensor ID: " + biometricSensor.id + " Waiting for cookie: " + biometricSensor.mCookie);
                i++;
            }
        }
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Remaining cookies: ", "BiometricService/AuthSession");
        return i == 0;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.e("BiometricService/AuthSession", "Binder died, session: " + this);
        BiometricService$$ExternalSyntheticLambda3 biometricService$$ExternalSyntheticLambda3 = (BiometricService$$ExternalSyntheticLambda3) this.mClientDeathReceiver;
        BiometricService biometricService = biometricService$$ExternalSyntheticLambda3.f$0;
        biometricService.getClass();
        biometricService.mHandler.post(new BiometricService$$ExternalSyntheticLambda4(0, biometricService$$ExternalSyntheticLambda3.f$1, biometricService));
    }

    public final void cancelAllSensors() {
        cancelAllSensors(new AuthSession$$ExternalSyntheticLambda1(2));
    }

    public final void cancelAllSensors(Function function) {
        for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            try {
                if (((Boolean) function.apply(biometricSensor)).booleanValue()) {
                    Slog.d("BiometricService/AuthSession", "Cancelling sensorId: " + biometricSensor.id);
                    IBinder iBinder = this.mToken;
                    String str = this.mOpPackageName;
                    long j = this.mRequestId;
                    if (biometricSensor.mSensorState != 4) {
                        biometricSensor.impl.cancelAuthenticationFromService(iBinder, str, j);
                        biometricSensor.mSensorState = 4;
                    }
                }
            } catch (RemoteException unused) {
                Slog.e("BiometricService/AuthSession", "Unable to cancel authentication");
            }
        }
    }

    public final void destroy() {
        SemBiometricSysUiWrapper semBiometricSysUiWrapper = this.mStatusBarService;
        long j = this.mRequestId;
        semBiometricSysUiWrapper.mHandler.removeCallbacks(semBiometricSysUiWrapper.mShowDialogWatchdog);
        Pair pair = (Pair) ((HashMap) semBiometricSysUiWrapper.mSessions).get(Long.valueOf(j));
        if (pair == null) {
            Slog.w("SemBiometricSysUiWrapper", "closeSession: no session info with " + j);
        } else {
            ((HashMap) semBiometricSysUiWrapper.mSessions).remove(Long.valueOf(j));
            semBiometricSysUiWrapper.mSysUiManager.closeSession(((Integer) pair.first).intValue(), 1000L);
        }
        SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
        int i = (int) this.mRequestId;
        int i2 = this.mErrorEscrow;
        SemBioLoggingManager.LoggingInfo loggingInfo = (SemBioLoggingManager.LoggingInfo) semBioLoggingManager.mBpLoggingInfo.get(i);
        if (loggingInfo != null) {
            loggingInfo.mResultTime = System.currentTimeMillis();
            loggingInfo.mResult = "E";
            loggingInfo.mExtra = i2;
            synchronized (semBioLoggingManager) {
                try {
                    semBioLoggingManager.mBpAuthLogList.add(loggingInfo.toDumpFormat());
                    if (semBioLoggingManager.mBpAuthLogList.size() > SemBioLoggingManager.LOG_ARRAY_SIZE) {
                        semBioLoggingManager.mBpAuthLogList.remove(0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            semBioLoggingManager.mBpLoggingInfo.delete(i);
        }
    }

    public final void goToInitialState() {
        BiometricSensor biometricSensor;
        PreAuthInfo preAuthInfo = this.mPreAuthInfo;
        if (preAuthInfo.credentialAvailable && preAuthInfo.eligibleSensors.isEmpty()) {
            this.mState = 9;
            int[] iArr = new int[0];
            this.mSensors = iArr;
            this.mStatusBarService.showAuthenticationDialog(this.mPromptInfo, iArr, true, false, this.mUserId, this.mOperationId, this.mOpPackageName, this.mRequestId);
            return;
        }
        if (this.mPreAuthInfo.eligibleSensors.isEmpty()) {
            throw new IllegalStateException("No authenticators requested");
        }
        if (this.mUseSwitchingMode) {
            List list = this.mPreAuthInfo.eligibleSensors;
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    biometricSensor = (BiometricSensor) list.get(0);
                    break;
                } else {
                    biometricSensor = (BiometricSensor) it.next();
                    if (biometricSensor.modality == 2) {
                        break;
                    }
                }
            }
            setSensorsToStateWaitingForCookie(biometricSensor, false);
        } else {
            setSensorsToStateWaitingForCookie(false);
        }
        this.mState = 1;
    }

    public final boolean hasAuthenticatedAndConfirmed() {
        if (((this.mPromptInfo.semGetPrivilegedFlag() & 1) == 0 && (this.mPromptInfo.semGetPrivilegedFlag() & 64) == 0) ? false : true) {
            return false;
        }
        return this.mAuthenticatedSensorId != -1 && this.mState == 7;
    }

    public final boolean isConfirmationRequired(BiometricSensor biometricSensor) {
        boolean booleanValue;
        BiometricService.BiometricServiceWrapper.AnonymousClass1 anonymousClass1 = (BiometricService.BiometricServiceWrapper.AnonymousClass1) biometricSensor;
        anonymousClass1.getClass();
        boolean z = Utils.DEBUG;
        int i = anonymousClass1.modality;
        if (i == 4 || i == 8) {
            int i2 = this.mUserId;
            BiometricService.BiometricServiceWrapper.AnonymousClass1 anonymousClass12 = (BiometricService.BiometricServiceWrapper.AnonymousClass1) biometricSensor;
            BiometricService.SettingObserver settingObserver = BiometricService.this.mSettingObserver;
            if (anonymousClass12.modality != 8) {
                settingObserver.getClass();
                booleanValue = false;
            } else {
                if (!((HashMap) settingObserver.mFaceAlwaysRequireConfirmation).containsKey(Integer.valueOf(i2))) {
                    settingObserver.onChange(true, settingObserver.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION, i2);
                }
                booleanValue = ((Boolean) ((HashMap) settingObserver.mFaceAlwaysRequireConfirmation).get(Integer.valueOf(i2))).booleanValue();
            }
            if (booleanValue || this.mPreAuthInfo.confirmationRequested) {
                return true;
            }
        }
        return false;
    }

    public final boolean isCrypto() {
        return this.mOperationId != 0;
    }

    public final boolean onCancelAuthSession(boolean z) {
        if (hasAuthenticatedAndConfirmed()) {
            Slog.d("BiometricService/AuthSession", "onCancelAuthSession after successful auth");
            return true;
        }
        this.mCancelled = true;
        int i = this.mState;
        boolean z2 = i == 1 || i == 2 || i == 3;
        cancelAllSensors();
        if (z2 && !z) {
            return false;
        }
        try {
            this.mClientReceiver.onError(this.mPreAuthInfo.getEligibleModalities(), 5, 0);
            this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
            return true;
        } catch (RemoteException e) {
            Slog.e("BiometricService/AuthSession", "Remote exception", e);
            return false;
        }
    }

    public final boolean onErrorReceived(int i, int i2, int i3, int i4) {
        boolean z;
        ASKSManagerService$$ExternalSyntheticOutline0.m(i, i3, "onErrorReceived sensor: ", " error: ", "BiometricService/AuthSession");
        Iterator it = this.mPreAuthInfo.eligibleSensors.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (((BiometricSensor) it.next()).mCookie == i2) {
                z = true;
                break;
            }
        }
        if (!z) {
            NandswapManager$$ExternalSyntheticOutline0.m(i2, "Unknown/expired cookie: ", "BiometricService/AuthSession");
            return false;
        }
        for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (biometricSensor.mSensorState == 3 && i2 == biometricSensor.mCookie) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Sensor("), biometricSensor.id, ") now in STATE_STOPPED", "BiometricService/Sensor");
                biometricSensor.mSensorState = 5;
            }
        }
        if (this.mAuthenticatedSensorId != -1) {
            Slog.d("BiometricService/AuthSession", "onErrorReceived after successful auth (ignoring)");
            return false;
        }
        boolean z2 = i3 == 7 || i3 == 9;
        if (z2) {
            cancelAllSensors(new AuthSession$$ExternalSyntheticLambda6(this, i, 0));
        }
        this.mErrorEscrow = i3;
        this.mVendorCodeEscrow = i4;
        int sensorIdToModality = sensorIdToModality(i);
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("onErrorReceived: "), this.mState, "BiometricService/AuthSession");
        int i5 = this.mState;
        if (i5 != 1) {
            if (i5 != 2 && i5 != 3) {
                if (i5 == 4) {
                    this.mClientReceiver.onError(sensorIdToModality, i3, i4);
                    this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
                    return true;
                }
                if (i5 != 6) {
                    if (i5 == 9) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Biometric canceled, ignoring from state: "), this.mState, "BiometricService/AuthSession");
                    } else {
                        if (i5 == 10) {
                            this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
                            return true;
                        }
                        VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("Unhandled error state, mState: "), this.mState, "BiometricService/AuthSession");
                    }
                }
            }
            PromptInfo promptInfo = this.mPromptInfo;
            boolean z3 = Utils.DEBUG;
            if (Utils.isCredentialRequested(promptInfo.getAuthenticators()) && z2) {
                this.mState = 9;
                this.mStatusBarService.onBiometricError(sensorIdToModality, i3, i4);
            } else {
                if (i3 == 5) {
                    this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
                    this.mClientReceiver.onError(sensorIdToModality, i3, i4);
                    return true;
                }
                this.mState = 8;
                this.mStatusBarService.onBiometricError(sensorIdToModality, i3, i4);
            }
        } else {
            PromptInfo promptInfo2 = this.mPromptInfo;
            boolean z4 = Utils.DEBUG;
            if (!Utils.isCredentialRequested(promptInfo2.getAuthenticators())) {
                this.mClientReceiver.onError(sensorIdToModality, i3, i4);
                return true;
            }
            this.mPromptInfo.setAuthenticators(this.mPromptInfo.getAuthenticators() & (-32768));
            this.mState = 9;
            int[] iArr = new int[0];
            this.mSensors = iArr;
            this.mStatusBarService.showAuthenticationDialog(this.mPromptInfo, iArr, true, false, this.mUserId, this.mOperationId, this.mOpPackageName, this.mRequestId);
        }
        return false;
    }

    public final boolean pauseSensorIfSupported(int i) {
        int i2;
        Iterator it = this.mPreAuthInfo.eligibleSensors.iterator();
        while (true) {
            if (!it.hasNext()) {
                NandswapManager$$ExternalSyntheticOutline0.m(i, "Unknown sensor: ", "BiometricService/AuthSession");
                i2 = 0;
                break;
            }
            BiometricSensor biometricSensor = (BiometricSensor) it.next();
            if (i == biometricSensor.id) {
                i2 = biometricSensor.mSensorState;
                break;
            }
        }
        boolean z = i2 == 4;
        if (sensorIdToModality(i) != 8 || z) {
            return false;
        }
        cancelAllSensors(new AuthSession$$ExternalSyntheticLambda5(i, 0));
        return true;
    }

    public final int sensorIdToModality(int i) {
        for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (i == biometricSensor.id) {
                return biometricSensor.modality;
            }
        }
        NandswapManager$$ExternalSyntheticOutline0.m(i, "Unknown sensor: ", "BiometricService/AuthSession");
        return 0;
    }

    public final void setSensorsToStateWaitingForCookie(BiometricSensor biometricSensor, boolean z) {
        int i = biometricSensor.mSensorState;
        if (z && i != 5 && i != 4) {
            Slog.d("BiometricService/AuthSession", "Skip retry because sensor: " + biometricSensor.id + " is: " + i);
            return;
        }
        if (z) {
            this.mState = 5;
        }
        int nextInt = this.mRandom.nextInt(2147483646) + 1;
        boolean isConfirmationRequired = isConfirmationRequired(biometricSensor);
        IBinder iBinder = this.mToken;
        long j = this.mOperationId;
        int i2 = this.mUserId;
        IBiometricSensorReceiver iBiometricSensorReceiver = this.mSensorReceiver;
        String str = this.mOpPackageName;
        long j2 = this.mRequestId;
        boolean isAllowBackgroundAuthentication = this.mPromptInfo.isAllowBackgroundAuthentication();
        boolean isForLegacyFingerprintManager = this.mPromptInfo.isForLegacyFingerprintManager();
        boolean z2 = this.mOperationContext.mIsMandatoryBiometrics;
        biometricSensor.mCookie = nextInt;
        biometricSensor.impl.prepareForAuthentication(isConfirmationRequired, iBinder, j, i2, iBiometricSensorReceiver, str, j2, nextInt, isAllowBackgroundAuthentication, isForLegacyFingerprintManager, z2);
        biometricSensor.mSensorState = 1;
        this.mCurrentSensor = biometricSensor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002e, code lost:
    
        r21.mState = 5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setSensorsToStateWaitingForCookie(boolean r22) {
        /*
            r21 = this;
            r0 = r21
            com.android.server.biometrics.PreAuthInfo r1 = r0.mPreAuthInfo
            java.util.List r1 = r1.eligibleSensors
            java.util.Iterator r1 = r1.iterator()
        La:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L74
            java.lang.Object r2 = r1.next()
            com.android.server.biometrics.BiometricSensor r2 = (com.android.server.biometrics.BiometricSensor) r2
            int r3 = r2.mSensorState
            java.lang.String r4 = "BiometricService/AuthSession"
            r5 = 5
            int r6 = r2.id
            if (r22 == 0) goto L2c
            if (r3 == r5) goto L2c
            r7 = 4
            if (r3 == r7) goto L2c
            java.lang.String r2 = "Skip retry because sensor: "
            java.lang.String r5 = " is: "
            com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0.m(r6, r3, r2, r5, r4)
            goto La
        L2c:
            if (r22 == 0) goto L30
            r0.mState = r5
        L30:
            java.util.Random r3 = r0.mRandom
            r5 = 2147483646(0x7ffffffe, float:NaN)
            int r3 = r3.nextInt(r5)
            r5 = 1
            int r3 = r3 + r5
            boolean r8 = r0.isConfirmationRequired(r2)
            java.lang.String r7 = "waiting for cooking for sensor: "
            com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0.m(r6, r7, r4)
            android.os.IBinder r9 = r0.mToken
            long r10 = r0.mOperationId
            int r12 = r0.mUserId
            android.hardware.biometrics.IBiometricSensorReceiver r13 = r0.mSensorReceiver
            java.lang.String r14 = r0.mOpPackageName
            long r6 = r0.mRequestId
            android.hardware.biometrics.PromptInfo r4 = r0.mPromptInfo
            boolean r18 = r4.isAllowBackgroundAuthentication()
            android.hardware.biometrics.PromptInfo r4 = r0.mPromptInfo
            boolean r19 = r4.isForLegacyFingerprintManager()
            com.android.server.biometrics.log.OperationContextExt r4 = r0.mOperationContext
            boolean r4 = r4.mIsMandatoryBiometrics
            r2.mCookie = r3
            android.hardware.biometrics.IBiometricAuthenticator r15 = r2.impl
            r16 = r6
            r7 = r15
            r15 = r16
            r17 = r3
            r20 = r4
            r7.prepareForAuthentication(r8, r9, r10, r12, r13, r14, r15, r17, r18, r19, r20)
            r2.mSensorState = r5
            goto La
        L74:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.AuthSession.setSensorsToStateWaitingForCookie(boolean):void");
    }

    public final void startAllPreparedSensors(Function function) {
        for (BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (((Boolean) function.apply(biometricSensor)).booleanValue()) {
                try {
                    Slog.v("BiometricService/AuthSession", "Starting sensor: " + biometricSensor.id);
                    int i = biometricSensor.mCookie;
                    if (i != 0) {
                        biometricSensor.impl.startPreparedClient(i);
                        biometricSensor.mSensorState = 3;
                    }
                } catch (RemoteException e) {
                    Slog.e("BiometricService/AuthSession", "Unable to start prepared client, sensor: " + biometricSensor, e);
                }
            }
        }
    }

    public final int statsModality() {
        Iterator it = this.mPreAuthInfo.eligibleSensors.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = ((BiometricSensor) it.next()).modality;
            if ((i2 & 2) != 0) {
                i |= 1;
            }
            if ((i2 & 4) != 0) {
                i |= 2;
            }
            if ((i2 & 8) != 0) {
                i |= 4;
            }
        }
        return i;
    }

    public final String toString() {
        return "State: " + this.mState + ", cancelled: " + this.mCancelled + ", isCrypto: " + isCrypto() + ", PreAuthInfo: " + this.mPreAuthInfo + ", requestId: " + this.mRequestId;
    }
}
