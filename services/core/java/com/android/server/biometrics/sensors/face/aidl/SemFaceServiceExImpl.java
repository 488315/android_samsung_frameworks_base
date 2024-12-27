package com.android.server.biometrics.sensors.face.aidl;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.face.FaceEnrollOptions;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.common.NativeHandle;
import android.hardware.face.Face;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.MemoryFile;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import android.view.OrientationEventListener;
import android.view.Surface;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricBoostingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.face.FaceService;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.SemFaceBrightManager;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter;

import vendor.samsung.hardware.biometrics.face.ISehFace;
import vendor.samsung.hardware.biometrics.face.ISehSession;

import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFaceServiceExImpl {
    public static SemFaceServiceExImpl mSemFaceServiceExImpl;
    public AppOpsManager mAppOpsManager;
    public long mAuthStartTime;
    public int mBacklight;
    public int mBrightnessUp;
    public ICancellationSignal mCancellationSignal;
    public Context mContext;
    public int mFABK;
    public int mFALI;
    public int mFALQ;
    public int mFAMO;
    public int mFANM;
    public FaceUtils mFaceUtils;
    public NativeHandle mHwPreviewHandle;
    public int mInsufficient;
    public int mLastRotation;
    public int mMotion;
    public AnonymousClass2 mOrientationEventListener;
    public android.os.NativeHandle mOsPreviewHandle;
    public PowerManager mPowerManager;
    public FaceProvider mProvider;
    public BiometricScheduler mScheduler;
    public SemBioAnalyticsManager mSemAnalyticsManager;
    public Sensor mSensor;
    public int mSensorId;
    public int mUserId;
    public PowerManager.WakeLock mWakeLock;
    public ISehFace mISehFace = null;
    public ISession mISession = null;
    public ISessionCallback mISessionCallback = null;
    public ISehSession mISehSession = null;
    public boolean mIsResetting = false;
    public boolean mIsCheckedTooDark = false;
    public int mSecurityLevel = 0;
    public AnonymousClass3 mProximitySensorMgr = null;
    public boolean mIsOperationStarted = false;
    public long mStartOperationTime = -1;
    public int mOperationType = -1;
    public boolean mDaemonIsCancelled = false;
    public int mPrevAcquiredInfo = -1;
    public int mPrevAcquiredVendorInfo = -1;
    public int mSkipAcquiredEventCount = 0;
    public boolean mIsAuthenticateResult = false;
    public boolean mIsEnrollPausing = false;
    public long mEnrollStartTime = -1;
    public boolean mIsEarlyStop = false;
    public int mNoMatchMaxCountNum = 0;
    public boolean mIsTimeout = false;
    public boolean mTpaHalModeEnabled = false;
    public boolean mIsHIDL = false;
    public Surface mPreviewSurface = null;
    public boolean mIsAuthenticationExtOperation = false;
    public MemoryFile mMemoryFileForAuthPreviewResult = null;
    public byte[] mHIDLpreviewImage = null;
    public MemoryFile mHIDLmemoryFileForPreview = null;
    public final AnonymousClass1 mHandlerMain =
            new Handler(
                    BiometricHandlerProvider.sBiometricHandlerProvider
                            .getFaceHandler()
                            .getLooper()) { // from class:
                                            // com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.this;
                    if (i != 1) {
                        if (i != 4) {
                            HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Unknown message:"), message.what, "SemFace");
                            return;
                        } else {
                            Slog.i("SemFace", "handleMessage: MSG_PROXIMITY_SENSOR_ERROR");
                            semFaceServiceExImpl.sendAcquired(1001);
                            return;
                        }
                    }
                    Slog.d("SemFace", "handleMessage : MSG_INACTIVITY_TIMER_EXPIRED(TIMEOUT)");
                    semFaceServiceExImpl.mIsTimeout = true;
                    int i2 = semFaceServiceExImpl.mOperationType;
                    if (i2 == 1) {
                        semFaceServiceExImpl.onExtended(3, -1);
                        semFaceServiceExImpl.daemonCancelInternal();
                        if (semFaceServiceExImpl.mIsCheckedTooDark) {
                            semFaceServiceExImpl.sendError(8, 100002);
                            return;
                        } else {
                            semFaceServiceExImpl.sendError(3, 0);
                            return;
                        }
                    }
                    if (i2 == 2) {
                        SemBioAnalyticsManager semBioAnalyticsManager =
                                semFaceServiceExImpl.mSemAnalyticsManager;
                        int[] iArr = {
                            semBioAnalyticsManager.mFaceQualityNoFace,
                            semBioAnalyticsManager.mFaceQualityBigFace,
                            semBioAnalyticsManager.mFaceQualitySmallFace
                        };
                        if (semFaceServiceExImpl.mIsAuthenticateResult) {
                            StringBuilder sb = new StringBuilder("no match timeout BILG ");
                            sb.append(iArr[0]);
                            sb.append(":");
                            sb.append(iArr[1]);
                            sb.append(":");
                            SystemServiceManager$$ExternalSyntheticOutline0.m(
                                    sb, iArr[2], "SemFace");
                            semFaceServiceExImpl
                                    .mSensor
                                    .getSessionForUser(semFaceServiceExImpl.mUserId)
                                    .mAidlResponseHandler
                                    .onAuthenticationFailed();
                            return;
                        }
                        StringBuilder sb2 = new StringBuilder("no face BILG ");
                        sb2.append(iArr[0]);
                        sb2.append(":");
                        sb2.append(iArr[1]);
                        sb2.append(":");
                        SystemServiceManager$$ExternalSyntheticOutline0.m(sb2, iArr[2], "SemFace");
                        semFaceServiceExImpl.onExtended(3, -1);
                        semFaceServiceExImpl.daemonCancelInternal();
                        semFaceServiceExImpl.sendError(3, 0);
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$2, reason: invalid class name */
    public final class AnonymousClass2 extends OrientationEventListener {
        public AnonymousClass2(Context context) {
            super(context, 3);
        }

        @Override // android.view.OrientationEventListener
        public final void onOrientationChanged(int i) {
            Context context = SemFaceServiceExImpl.this.mContext;
            if (context == null) {
                return;
            }
            try {
                int rotation = context.getDisplay().getRotation();
                SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.this;
                if (rotation != semFaceServiceExImpl.mLastRotation) {
                    semFaceServiceExImpl.mLastRotation = rotation;
                    if (semFaceServiceExImpl.mIsOperationStarted) {
                        semFaceServiceExImpl.mHandlerMain.post(
                                new SemFaceServiceExImpl$2$$ExternalSyntheticLambda0(0, this));
                    }
                }
            } catch (UnsupportedOperationException e) {
                Slog.w("SemFace", "onOrientationChanged: " + e.getMessage());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$3, reason: invalid class name */
    public final class AnonymousClass3 implements SensorEventListener {
        public boolean mIsRegisterListener = false;
        public final android.hardware.Sensor mPrxSensor;
        public HandlerThread mSensorThread;
        public final SensorManager mSmgr;

        public AnonymousClass3(Context context) {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            this.mSmgr = sensorManager;
            if (sensorManager != null) {
                this.mPrxSensor = sensorManager.getDefaultSensor(8);
            }
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(android.hardware.Sensor sensor, int i) {}

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            if (!SemFaceServiceExImpl.this.mSensor.mTestHalEnabled
                    && sensorEvent.sensor.getType() == 8) {
                float[] fArr = (float[]) sensorEvent.values.clone();
                if (fArr[0] == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    removeMessages(4);
                    sendEmptyMessage(4);
                }
                Slog.i("SemFace", "[PROXIMITY] onSensorChanged : " + fArr[0]);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$4, reason: invalid class name */
    public final class AnonymousClass4 extends BroadcastReceiver {
        public AnonymousClass4() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("SemFace", "onBroadCastReceive : " + action);
            action.getClass();
            if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                post(new SemFaceServiceExImpl$2$$ExternalSyntheticLambda0(1, this));
            }
        }
    }

    public static SemFaceServiceExImpl getInstance() {
        if (mSemFaceServiceExImpl == null) {
            mSemFaceServiceExImpl = new SemFaceServiceExImpl();
        }
        return mSemFaceServiceExImpl;
    }

    public final void acquireDVFS(int i, int i2) {
        Slog.i("SemFace", "acquireDVFS");
        SemBiometricBoostingManager.getInstance()
                .acquireDvfs(this.mContext, i2 == 1 ? 3511 : 3512, 8, "GFACE_SERVICE", i);
    }

    public final synchronized void daemonCancel(
            ICancellationSignal iCancellationSignal, boolean z) {
        stopOperation();
        if (iCancellationSignal == null) {
            Slog.w("SemFace", "cancellationSignal is null");
            return;
        }
        if (!z) {
            post(new SemFaceServiceExImpl$$ExternalSyntheticLambda0(this, 1));
        }
        if (z && this.mSensor.mTestHalEnabled) {
            return;
        }
        this.mDaemonIsCancelled = true;
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "daemonCancel START");
            iCancellationSignal.cancel();
            Slog.w(
                    "SemFace",
                    "daemonCancel FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms) RESULT: ");
        } catch (Exception e) {
            Slog.e("SemFace", "Failed to get biometric interface", e);
        }
    }

    public final synchronized void daemonCancelInternal() {
        try {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if ((currentClient instanceof FaceAuthenticationClient)
                    || (currentClient instanceof FaceEnrollClient)) {
                Slog.d("SemFace", "daemonCancelInternal");
            } else {
                Slog.d("SemFace", "daemonCancelInternal not auth(enroll) client");
            }
            daemonCancel(this.mCancellationSignal, true);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void daemonClose() {
        Slog.d("SemFace", "daemonClose");
        try {
            if (this.mISession == null) {
                Slog.e("SemFace", "daemonClose: no face HAL!");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            this.mISession.close();
            Slog.w(
                    "SemFace",
                    "ISession.close: FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
            this.mISession = null;
            if (this.mISehSession == null) {
                Slog.e("SemFace", "daemonClose: no seh face HAL!");
                return;
            }
            long currentTimeMillis2 = System.currentTimeMillis();
            ((ISehSession.Stub.Proxy) this.mISehSession).close();
            Slog.w(
                    "SemFace",
                    "IsehSession.close: FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis2)
                            + "ms)");
            this.mISehSession = null;
        } catch (Exception e) {
            Slog.e("SemFace", "IsehSession.close: ", e);
        }
    }

    public final ICancellationSignal daemonEnroll(
            HardwareAuthToken hardwareAuthToken,
            byte[] bArr,
            NativeHandle nativeHandle,
            Surface surface,
            Boolean bool) {
        Slog.i("SemFace", "enroll BILG ");
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonEnroll: no face HAL!");
            throw new IllegalArgumentException();
        }
        if (hardwareAuthToken.challenge == 0) {
            Slog.w(
                    "SemFace",
                    "daemonEnroll: hardwareAuthToken mac.length = "
                            + hardwareAuthToken.mac.length
                            + ", id="
                            + hardwareAuthToken.authenticatorId
                            + ", challenge="
                            + hardwareAuthToken.challenge
                            + ", type="
                            + hardwareAuthToken.authenticatorType);
            throw new IllegalArgumentException();
        }
        if (Utils.DEBUG) {
            BatteryService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("hardwareAuthToken  id = "),
                    hardwareAuthToken.authenticatorId,
                    "SemFace");
        }
        if (bool.booleanValue()) {
            FaceEnrollOptions faceEnrollOptions = new FaceEnrollOptions();
            faceEnrollOptions.hardwareAuthToken = hardwareAuthToken;
            faceEnrollOptions.enrollmentType = (byte) 0;
            faceEnrollOptions.features = bArr;
            faceEnrollOptions.nativeHandlePreview = nativeHandle;
            faceEnrollOptions.context = null;
            faceEnrollOptions.surfacePreview = surface;
            this.mCancellationSignal = this.mISession.enrollWithOptions(faceEnrollOptions);
        } else {
            this.mCancellationSignal =
                    this.mISession.enroll(hardwareAuthToken, (byte) 0, bArr, nativeHandle);
        }
        return this.mCancellationSignal;
    }

    public final void daemonEnumerateUser() {
        Slog.i("SemFace", "daemonEnumerateUser START");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (this.mIsHIDL) {
                ((HidlToAidlSessionAdapter)
                                ((AidlSession) this.mSensor.mLazySession.get()).mSession)
                        .enumerateEnrollments();
            } else {
                ISession iSession = this.mISession;
                if (iSession == null) {
                    Slog.w("SemFace", "daemonEnumerateUser(): no face HAL!");
                    return;
                }
                iSession.enumerateEnrollments();
            }
            Slog.w(
                    "SemFace",
                    "daemonEnumerateUser FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (Exception e) {
            Slog.e("SemFace", "daemonEnumerateUser: ", e);
        }
    }

    public final void daemonGenerateChallenge() {
        Slog.i("SemFace", "daemonGenerateChallenge START");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (this.mIsHIDL) {
                ((HidlToAidlSessionAdapter)
                                ((AidlSession) this.mSensor.mLazySession.get()).mSession)
                        .generateChallenge();
            } else if (this.mISession == null) {
                Slog.w("SemFace", "daemonGenerateChallenge(): no face HAL!");
                return;
            }
            this.mISession.generateChallenge();
            Slog.w(
                    "SemFace",
                    "daemonGenerateChallenge FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (Exception e) {
            Slog.e("SemFace", "daemonGenerateChallenge: ", e);
        }
    }

    public final void daemonGetAuthenticatorId() {
        Slog.i("SemFace", "daemonGetAuthenticatorId START");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (this.mIsHIDL) {
                ((HidlToAidlSessionAdapter)
                                ((AidlSession) this.mSensor.mLazySession.get()).mSession)
                        .getAuthenticatorId();
            } else {
                ISession iSession = this.mISession;
                if (iSession == null) {
                    Slog.w("SemFace", "daemonGetAuthenticatorId(): no face HAL!");
                    return;
                }
                iSession.getAuthenticatorId();
            }
            Slog.w(
                    "SemFace",
                    "daemonGetAuthenticatorId FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (Exception e) {
            Slog.e("SemFace", "daemonGetAuthenticatorId: ", e);
        }
    }

    public final void daemonGetFeatures() {
        Slog.i("SemFace", "daemonGetFeatures START");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (this.mIsHIDL) {
                ((HidlToAidlSessionAdapter)
                                ((AidlSession) this.mSensor.mLazySession.get()).mSession)
                        .getFeatures();
            } else {
                ISession iSession = this.mISession;
                if (iSession == null) {
                    Slog.w("SemFace", "daemonGetFeatures(): no face HAL!");
                    return;
                }
                iSession.getFeatures();
            }
            Slog.w(
                    "SemFace",
                    "daemonGetFeatures FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (Exception e) {
            Slog.e("SemFace", "daemonGetFeatures: ", e);
        }
    }

    public final void daemonRemove(int[] iArr) {
        long j;
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonRemove: no face HAL!");
            return;
        }
        try {
            j = System.currentTimeMillis();
            try {
                Slog.w("SemFace", "removeEnrollments START + SemFaceUtils.semGetSubTag()");
                this.mISession.removeEnrollments(iArr);
            } catch (Exception e) {
                e = e;
                BootReceiver$$ExternalSyntheticOutline0.m(e, "daemonRemove : ", "SemFace");
                Slog.w(
                        "SemFace",
                        "removeEnrollments FINISH ("
                                + (System.currentTimeMillis() - j)
                                + "ms) RESULT: ");
            }
        } catch (Exception e2) {
            e = e2;
            j = 0;
        }
        Slog.w(
                "SemFace",
                "removeEnrollments FINISH (" + (System.currentTimeMillis() - j) + "ms) RESULT: ");
    }

    public final void daemonRevokeChallenge(long j) {
        Slog.i("SemFace", "daemonRevokeChallenge START");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (this.mIsHIDL) {
                ((HidlToAidlSessionAdapter)
                                ((AidlSession) this.mSensor.mLazySession.get()).mSession)
                        .revokeChallenge(j);
            } else {
                ISession iSession = this.mISession;
                if (iSession == null) {
                    Slog.w("SemFace", "daemonRevokeChallenge(): no face HAL!");
                    return;
                }
                iSession.revokeChallenge(j);
            }
            Slog.w(
                    "SemFace",
                    "daemonRevokeChallenge FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (Exception e) {
            Slog.e("SemFace", "daemonRevokeChallenge: ", e);
        }
    }

    public final void daemonSetFaceTag(byte[] bArr) {
        String str;
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonSetFaceTag(): no seh face HAL!");
            return;
        }
        try {
            StringBuilder sb = new StringBuilder("setFaceTag START type = 1");
            if (Utils.DEBUG) {
                str = ", data: " + SemFaceUtils.byteArrayToHex(bArr);
            } else {
                str = "";
            }
            sb.append(str);
            Slog.i("SemFace", sb.toString());
            long currentTimeMillis = System.currentTimeMillis();
            ((ISehSession.Stub.Proxy) this.mISehSession).setFaceTag(bArr);
            Slog.i(
                    "SemFace",
                    "setFaceTag FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms)");
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("setFaceTag: "), "SemFace");
        }
    }

    public final void daemonSetRotation(int i) {
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonSetRotation(): no face seh HAL!");
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "SetRotation START");
            Slog.w(
                    "SemFace",
                    "SetRotation FINISH ("
                            + (System.currentTimeMillis() - currentTimeMillis)
                            + "ms) RESULT: "
                            + ((ISehSession.Stub.Proxy) this.mISehSession)
                                    .setRotation(
                                            i != 1
                                                    ? i != 2
                                                            ? i != 3
                                                                    ? FrameworkStatsLog
                                                                            .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6
                                                                    : 180
                                                            : 90
                                                    : 0));
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("daemonSetRotation: "), "SemFace");
        }
    }

    public final int getCurrentClientHashID() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient != null) {
            return currentClient.mHashID;
        }
        Slog.e("SemFace", "getCurrentClientHashID : client is null");
        return 0;
    }

    public final String getCurrentClientOwnerString() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient != null) {
            return currentClient.mOwner;
        }
        Slog.e("SemFace", "getCurrentClientOwnerString : client is null");
        return null;
    }

    public final boolean isBrightnessEnable() {
        return (this.mUserId == -10000
                        || Utils.isFlipFolded(this.mContext)
                        || Settings.Secure.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "face_brighten_screen",
                                        1,
                                        this.mUserId)
                                != 1)
                ? false
                : true;
    }

    public final boolean isTpaSehTestHalEnabled() {
        return (Build.IS_USERDEBUG || Build.IS_ENG) && this.mTpaHalModeEnabled;
    }

    public final boolean isUsingSehAPI() {
        return (this.mIsHIDL
                        || this.mSensor.mTestHalEnabled
                        || isTpaSehTestHalEnabled()
                        || SemBiometricFeature.FEATURE_JDM_HAL)
                ? false
                : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0280  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onExtended(int r13, int r14) {
        /*
            Method dump skipped, instructions count: 730
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.onExtended(int,"
                    + " int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onPreAcquired(int r13, int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.onPreAcquired(int,"
                    + " int, boolean):int");
    }

    public final void releaseDVFS() {
        Slog.i("SemFace", "releaseDVFS");
        SemBiometricBoostingManager.getInstance().release(this.mContext, 8);
    }

    public final void releaseSurfaceHandlesIfNeeded() {
        if (this.mPreviewSurface != null && this.mHwPreviewHandle == null) {
            Slog.w("SemFace", "mHwPreviewHandle is null even though mPreviewSurface is not null.");
        }
        if (this.mHwPreviewHandle != null) {
            try {
                Slog.v("SemFace", "Closing mHwPreviewHandle");
                NativeHandle nativeHandle = this.mHwPreviewHandle;
                if (nativeHandle != null) {
                    for (ParcelFileDescriptor parcelFileDescriptor : nativeHandle.fds) {
                        if (parcelFileDescriptor != null) {
                            parcelFileDescriptor.close();
                        }
                    }
                }
            } catch (IOException e) {
                Slog.e("SemFace", "Failed to close mPreviewSurface", e);
            }
            this.mHwPreviewHandle = null;
        }
        if (this.mOsPreviewHandle != null) {
            Slog.v("SemFace", "Releasing mOsPreviewHandle");
            FaceService.releaseSurfaceHandle(this.mOsPreviewHandle);
            this.mOsPreviewHandle = null;
        }
        if (this.mPreviewSurface != null) {
            Slog.v("SemFace", "Releasing mPreviewSurface");
            this.mPreviewSurface.release();
        }
    }

    public final synchronized void releaseWakeLock(boolean z) {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            Slog.i("SemFace", "releaseWakeLock : " + z);
            if (z) {
                this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
            }
            this.mWakeLock.release();
        }
    }

    public final void restoreBrightness() {
        if (isBrightnessEnable()) {
            SemFaceBrightManager semFaceBrightManager =
                    SemFaceBrightManager.getInstance(this.mContext);
            if (semFaceBrightManager.mIsSetBrightness) {
                semFaceBrightManager.mIsSetBrightness = false;
                SemFaceBrightManager.AnonymousClass1 anonymousClass1 =
                        semFaceBrightManager.mHandlerBg;
                anonymousClass1.removeMessages(2);
                anonymousClass1.removeMessages(5);
                boolean isFlipFolded = Utils.isFlipFolded(semFaceBrightManager.mContext);
                if (semFaceBrightManager.mIsScreenAutoBrightnessOn) {
                    semFaceBrightManager.mPowerManager.semSetAutoBrightnessLimit(-1, -1);
                } else {
                    semFaceBrightManager.mDisplayManager.setTemporaryBrightness(
                            isFlipFolded ? 1 : 0, -1, false);
                }
                HermesService$3$$ExternalSyntheticOutline0.m(
                        isFlipFolded ? 1 : 0, "restoreBrightness, ", "SemFaceBrightManager");
            }
        }
    }

    public final void semConnectSession(IFace iFace) {
        ISehFace iSehFace;
        if (iFace == null) {
            Slog.e("SemFace", "semConnectSession daemon is NULL!!");
            return;
        }
        if (this.mISessionCallback == null) {
            Slog.e("SemFace", "semConnectSession mISessionCallback is NULL!! not set yet");
            return;
        }
        try {
            Slog.d(
                    "SemFace",
                    "semConnectSession IFace = " + iFace + ", " + iFace.getInterfaceVersion());
            IBinder extension = iFace.asBinder().getExtension();
            int i = ISehFace.Stub.$r8$clinit;
            if (extension == null) {
                iSehFace = null;
            } else {
                IInterface queryLocalInterface = extension.queryLocalInterface(ISehFace.DESCRIPTOR);
                if (queryLocalInterface == null || !(queryLocalInterface instanceof ISehFace)) {
                    ISehFace.Stub.Proxy proxy = new ISehFace.Stub.Proxy();
                    proxy.mCachedVersion = -1;
                    proxy.mRemote = extension;
                    iSehFace = proxy;
                } else {
                    iSehFace = (ISehFace) queryLocalInterface;
                }
            }
            this.mISehFace = iSehFace;
            Slog.d(
                    "SemFace",
                    "semConnectSession ISehFace = "
                            + this.mISehFace
                            + ", "
                            + ((ISehFace.Stub.Proxy) this.mISehFace).getInterfaceVersion());
            this.mISehSession =
                    ((ISehFace.Stub.Proxy) this.mISehFace)
                            .createSession(this.mSensorId, this.mUserId, this.mISessionCallback);
            StringBuilder sb = new StringBuilder("semConnectSession ISehSession = ");
            sb.append(this.mISehSession);
            Slog.d("SemFace", sb.toString());
            this.mISession =
                    ISession.Stub.asInterface(
                            ((ISehSession.Stub.Proxy) this.mISehSession).mRemote.getExtension());
            Slog.d("SemFace", "semConnectSession ISession = " + this.mISession);
            ((ISehFace.Stub.Proxy) this.mISehFace).mRemote.linkToDeath(this.mProvider, 0);
            ((ISehSession.Stub.Proxy) this.mISehSession).mRemote.linkToDeath(this.mProvider, 0);
            this.mISession.asBinder().linkToDeath(this.mProvider, 0);
            Slog.d("SemFace", "semConnectSession end");
        } catch (Exception e) {
            Slog.e("SemFace", "semConnectSession Exception : ", e);
            this.mIsResetting = true;
            this.mProvider.binderDied();
            post(new SemFaceServiceExImpl$$ExternalSyntheticLambda0(this, 2));
        }
    }

    public final void sendAcquired(int i) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof AcquisitionClient) {
            ((AcquisitionClient) currentClient).onAcquired(22, i);
        } else {
            Slog.e(
                    "SemFace",
                    "sendAcquired - not AcquisitionClient: "
                            .concat(Utils.getClientName(currentClient)));
        }
    }

    public final void sendBroadcast(int i, int i2, String str) {
        Intent intent = new Intent(str);
        if (i > 0) {
            intent.putExtra("faceIndex", i);
            intent.putExtra("verificationType", "Device Credential");
        }
        try {
            intent.setFlags(16777216);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2));
            if (Utils.DEBUG) {
                Slog.i("SemFace", "[" + str + "] is sent with faceId " + i);
            } else {
                Slog.i("SemFace", "[" + str + "] is sent");
            }
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(e, "sendBroadcast failed :", "SemFace");
        }
    }

    public final void sendError(int i, int i2) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof AcquisitionClient) {
            ((AcquisitionClient) currentClient).onError(i, i2);
        } else {
            Slog.e(
                    "SemFace",
                    "sendError - not AcquisitionClient: "
                            .concat(Utils.getClientName(currentClient)));
        }
    }

    public final void sendFailed() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (!(currentClient instanceof AcquisitionClient)) {
            Slog.e(
                    "SemFace",
                    "sendFailed - not AcquisitionClient: "
                            .concat(Utils.getClientName(currentClient)));
            return;
        }
        try {
            IFaceServiceReceiver iFaceServiceReceiver =
                    currentClient.mListener.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onAuthenticationFailed();
            }
        } catch (Exception e) {
            Slog.e("SemFace", "sendFailed : Unable to notify listener, finishing", e);
        }
    }

    public final void sendSucceeded(Bundle bundle) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (!(currentClient instanceof AcquisitionClient)) {
            Slog.e(
                    "SemFace",
                    "sendSuccess - not AcquisitionClient: "
                            .concat(Utils.getClientName(currentClient)));
            return;
        }
        ClientMonitorCallbackConverter clientMonitorCallbackConverter = currentClient.mListener;
        try {
            int i = this.mUserId;
            IFaceServiceReceiver iFaceServiceReceiver =
                    clientMonitorCallbackConverter.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onSemAuthenticationSucceededWithBundle(
                        new Face("", 1, 0L), i, false, bundle);
            }
        } catch (Exception e) {
            Slog.e("SemFace", "sendSucceeded : Unable to notify listener, finishing", e);
        }
    }

    public void setISession(ISession iSession) {
        this.mISession = iSession;
    }

    public final synchronized void setWakeLock(int i, boolean z) {
        try {
            if (z) {
                this.mWakeLock = this.mPowerManager.newWakeLock(1, "SemFace");
            } else {
                this.mWakeLock = this.mPowerManager.newWakeLock(268435466, "SemFace");
            }
            PowerManager.WakeLock wakeLock = this.mWakeLock;
            if (wakeLock != null && !wakeLock.isHeld() && this.mPowerManager.isInteractive()) {
                Slog.i("SemFace", "setWakeLock");
                this.mWakeLock.acquire(i);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startBrightness() {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.startBrightness():void");
    }

    public final void startInactivityTimer(int i) {
        HermesService$3$$ExternalSyntheticOutline0.m(i, "startInactivityTimer : ", "SemFace");
        AnonymousClass1 anonymousClass1 = this.mHandlerMain;
        anonymousClass1.removeMessages(1);
        anonymousClass1.sendEmptyMessageDelayed(1, i);
    }

    public final synchronized void startOperation(int i) {
        boolean z = true;
        synchronized (this) {
            try {
                Slog.i("SemFace", "startOperation S");
                this.mIsOperationStarted = true;
                this.mOperationType = i;
                long currentTimeMillis = System.currentTimeMillis();
                this.mStartOperationTime = currentTimeMillis;
                this.mSemAnalyticsManager.mFaceStartTime = currentTimeMillis;
                int i2 = 60000;
                if (i == 1) {
                    this.mEnrollStartTime = System.currentTimeMillis();
                    if (!Utils.isTalkBackEnabled(this.mContext)) {
                        i2 = 30000;
                    }
                    Slog.d("SemFace", "enroll timeout set as : " + i2);
                    startInactivityTimer(i2);
                    this.mAppOpsManager.startOp(
                            26,
                            Process.myUid(),
                            this.mContext.getOpPackageName(),
                            false,
                            "Biometrics_FaceService",
                            null);
                    BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
                    if (currentClient != null) {
                        SemBioLoggingManager.get()
                                .faceStart(
                                        currentClient.mHashID,
                                        "E",
                                        this.mScheduler.getCurrentClient().mOwner);
                    }
                } else if (i == 2) {
                    SemFaceBrightManager semFaceBrightManager =
                            SemFaceBrightManager.getInstance(this.mContext);
                    int i3 =
                            semFaceBrightManager.mPowerManager.getCurrentBrightness(false)
                                            < ((float)
                                                    semFaceBrightManager
                                                            .mScreenFlashBrightnessLevelMaxCorrected)
                                    ? 5000
                                    : 4000;
                    if (SemFaceUtils.mNeedtoAuthenticateExt) {
                        Surface surface = SemFaceUtils.mSurface;
                        this.mPreviewSurface = surface;
                        SemFaceUtils.mSurface = null;
                        SemFaceUtils.mNeedtoAuthenticateExt = false;
                        this.mIsAuthenticationExtOperation = true;
                        if (surface != null) {
                            android.os.NativeHandle acquireSurfaceHandle =
                                    FaceService.acquireSurfaceHandle(surface);
                            this.mOsPreviewHandle = acquireSurfaceHandle;
                            try {
                                this.mHwPreviewHandle =
                                        AidlNativeHandleUtils.dup(acquireSurfaceHandle);
                                Slog.v("SemFace", "Obtained handles for the preview surface.");
                            } catch (IOException e) {
                                this.mHwPreviewHandle = null;
                                Slog.e("SemFace", "Failed to dup mOsPreviewHandle", e);
                            }
                        }
                        i3 = 600000;
                    }
                    if (!this.mSensor.mTestHalEnabled) {
                        i2 = i3;
                    }
                    startInactivityTimer(i2);
                    startBrightness();
                    daemonSetFaceTag(new byte[] {isBrightnessEnable()});
                    BaseClientMonitor currentClient2 = this.mScheduler.getCurrentClient();
                    if (currentClient2 != null) {
                        SemBioLoggingManager.get()
                                .faceStart(
                                        currentClient2.mHashID,
                                        "A",
                                        this.mScheduler.getCurrentClient().mOwner);
                    }
                } else {
                    i2 = 6000;
                }
                acquireDVFS(i2, i);
                String currentClientOwnerString = getCurrentClientOwnerString();
                if (currentClientOwnerString == null
                        || !Utils.isKeyguard(this.mContext, currentClientOwnerString)) {
                    z = false;
                }
                setWakeLock(i2 + 3000, z);
                try {
                    this.mLastRotation = this.mContext.getDisplay().getRotation();
                } catch (UnsupportedOperationException e2) {
                    Slog.w("SemFace", "startOperation: failed to get display, " + e2.getMessage());
                }
                daemonSetRotation(this.mLastRotation);
                this.mPrevAcquiredInfo = -1;
                this.mPrevAcquiredVendorInfo = -1;
                this.mSkipAcquiredEventCount = 0;
                this.mIsCheckedTooDark = false;
                this.mIsAuthenticateResult = false;
                this.mBrightnessUp = 0;
                this.mInsufficient = 0;
                this.mBacklight = 0;
                this.mMotion = 0;
                this.mIsEarlyStop = false;
                this.mNoMatchMaxCountNum = 0;
                this.mIsTimeout = false;
                this.mDaemonIsCancelled = false;
                Slog.i("SemFace", "startOperation E");
                post(new SemFaceServiceExImpl$$ExternalSyntheticLambda0(this, 0));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void stopOperation() {
        Slog.i("SemFace", "stopOperation S");
        if (!this.mIsOperationStarted) {
            Slog.i("SemFace", "stopOperation E : skip");
            return;
        }
        int i = this.mOperationType;
        if (i == 1) {
            this.mAppOpsManager.finishOp(
                    26,
                    Process.myUid(),
                    this.mContext.getOpPackageName(),
                    "Biometrics_FaceService");
            releaseWakeLock(true);
        } else if (i == 2) {
            releaseWakeLock(false);
            restoreBrightness();
        }
        releaseDVFS();
        removeMessages(1);
        this.mOperationType = -1;
        this.mIsOperationStarted = false;
        this.mIsEnrollPausing = false;
        this.mEnrollStartTime = -1L;
        this.mCancellationSignal = null;
        this.mIsAuthenticationExtOperation = false;
        if (this.mPreviewSurface != null) {
            releaseSurfaceHandlesIfNeeded();
            this.mPreviewSurface = null;
        }
        MemoryFile memoryFile = this.mMemoryFileForAuthPreviewResult;
        if (memoryFile != null) {
            memoryFile.close();
            this.mMemoryFileForAuthPreviewResult = null;
        }
        MemoryFile memoryFile2 = this.mHIDLmemoryFileForPreview;
        if (memoryFile2 != null) {
            memoryFile2.close();
            this.mHIDLmemoryFileForPreview = null;
        }
        Slog.i("SemFace", "stopOperation E");
        post(new SemFaceServiceExImpl$$ExternalSyntheticLambda0(this, 3));
    }
}
