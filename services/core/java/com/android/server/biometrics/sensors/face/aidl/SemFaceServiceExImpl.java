package com.android.server.biometrics.sensors.face.aidl;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorEvent;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.common.Ashmem;
import android.hardware.common.NativeHandle;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.MemoryFile;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.OrientationEventListener;
import android.view.Surface;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricBoostingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.face.FaceService;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.SemFaceBrightManager;
import com.android.server.biometrics.sensors.face.SemFaceMainThread;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.SemProximitySensorObserver;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl;
import com.android.server.display.DisplayPowerController2;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import vendor.samsung.hardware.biometrics.face.ISehFace;
import vendor.samsung.hardware.biometrics.face.ISehSession;

/* loaded from: classes.dex */
public class SemFaceServiceExImpl {
    public static SemFaceServiceExImpl mSemFaceServiceExImpl;
    public AppOpsManager mAppOpsManager;
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
    public OrientationEventListener mOrientationEventListener;
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
    public SemProximitySensorObserver mProximitySensorMgr = null;
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
    public Surface mPreviewSurface = null;
    public boolean mIsAuthenticationExtOperation = false;
    public MemoryFile mMemoryFile = null;
    public Handler mHandlerMain = new Handler(SemFaceMainThread.get().getLooper()) { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Slog.d("SemFace", "handleMessage : MSG_INACTIVITY_TIMER_EXPIRED(TIMEOUT)");
                SemFaceServiceExImpl.this.mIsTimeout = true;
                SemFaceServiceExImpl.this.onTimeout();
            } else if (i == 4) {
                Slog.i("SemFace", "handleMessage: MSG_PROXIMITY_SENSOR_ERROR");
                SemFaceServiceExImpl.this.sendAcquired(22, 1001);
            } else {
                Slog.w("SemFace", "Unknown message:" + message.what);
            }
        }
    };

    public final int getRotationValue(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 90;
        }
        if (i != 3) {
            return FrameworkStatsLog.f392x1636cc72;
        }
        return 180;
    }

    public static SemFaceServiceExImpl getInstance() {
        if (mSemFaceServiceExImpl == null) {
            mSemFaceServiceExImpl = new SemFaceServiceExImpl();
        }
        return mSemFaceServiceExImpl;
    }

    public static SemFaceServiceExImpl createInstance(Context context, int i, Sensor sensor, FaceProvider faceProvider) {
        if (mSemFaceServiceExImpl == null) {
            mSemFaceServiceExImpl = new SemFaceServiceExImpl();
        }
        SemFaceServiceExImpl semFaceServiceExImpl = mSemFaceServiceExImpl;
        semFaceServiceExImpl.mContext = context;
        semFaceServiceExImpl.mSensorId = i;
        semFaceServiceExImpl.mSensor = sensor;
        semFaceServiceExImpl.mScheduler = sensor.getScheduler();
        SemFaceServiceExImpl semFaceServiceExImpl2 = mSemFaceServiceExImpl;
        semFaceServiceExImpl2.mProvider = faceProvider;
        semFaceServiceExImpl2.init();
        return mSemFaceServiceExImpl;
    }

    public ISession getISession() {
        return this.mISession;
    }

    public void setISession(ISession iSession) {
        this.mISession = iSession;
    }

    public void setISessionCallback(ISessionCallback iSessionCallback) {
        this.mISessionCallback = iSessionCallback;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public int getSensorId() {
        return this.mSensorId;
    }

    public void semConnectSession(IFace iFace) {
        if (iFace == null) {
            Slog.e("SemFace", "semConnectSession daemon is NULL!!");
            return;
        }
        if (this.mISessionCallback == null) {
            Slog.e("SemFace", "semConnectSession mISessionCallback is NULL!! not set yet");
            return;
        }
        try {
            Slog.d("SemFace", "semConnectSession IFace = " + iFace + ", " + iFace.getInterfaceVersion());
            this.mISehFace = ISehFace.Stub.asInterface(iFace.asBinder().getExtension());
            Slog.d("SemFace", "semConnectSession ISehFace = " + this.mISehFace + ", " + this.mISehFace.getInterfaceVersion());
            this.mISehSession = this.mISehFace.createSession(this.mSensorId, this.mUserId, this.mISessionCallback);
            StringBuilder sb = new StringBuilder();
            sb.append("semConnectSession ISehSession = ");
            sb.append(this.mISehSession);
            Slog.d("SemFace", sb.toString());
            this.mISession = ISession.Stub.asInterface(this.mISehSession.asBinder().getExtension());
            Slog.d("SemFace", "semConnectSession ISession = " + this.mISession);
            this.mISehFace.asBinder().linkToDeath(this.mProvider, 0);
            this.mISehSession.asBinder().linkToDeath(this.mProvider, 0);
            this.mISession.asBinder().linkToDeath(this.mProvider, 0);
            Slog.d("SemFace", "semConnectSession end");
        } catch (Exception e) {
            Slog.e("SemFace", "semConnectSession Exception : ", e);
            semResetConnection();
        }
    }

    public void semResetConnection() {
        this.mIsResetting = true;
        this.mProvider.binderDied();
        this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SemFaceServiceExImpl.this.lambda$semResetConnection$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$semResetConnection$0() {
        this.mISehFace = null;
        ISehSession iSehSession = this.mISehSession;
        if (iSehSession != null) {
            try {
                iSehSession.close();
            } catch (Exception e) {
                Slog.e("SemFace", "semResetConnection Exception :", e);
            }
        }
        this.mISehSession = null;
        ISession iSession = this.mISession;
        if (iSession != null) {
            try {
                iSession.close();
            } catch (Exception e2) {
                Slog.e("SemFace", "semResetConnection Exception : ", e2);
            }
        }
        this.mISession = null;
        this.mIsResetting = false;
        stopOperation();
    }

    public final void init() {
        this.mPowerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
        this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        synchronized (this) {
            this.mWakeLock = this.mPowerManager.newWakeLock(268435466, "SemFace");
        }
        this.mOrientationEventListener = new C08652(this.mContext, 3);
        if (Utils.isTablet()) {
            this.mProximitySensorMgr = new SemProximitySensorObserver(this.mContext) { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl.3
                @Override // android.hardware.SensorEventListener
                public void onSensorChanged(SensorEvent sensorEvent) {
                    if (!SemFaceServiceExImpl.this.mSensor.getTestHalEnabled() && sensorEvent.sensor.getType() == 8) {
                        float[] fArr = (float[]) sensorEvent.values.clone();
                        if (fArr[0] == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                            SemFaceServiceExImpl.this.mHandlerMain.removeMessages(4);
                            SemFaceServiceExImpl.this.mHandlerMain.sendEmptyMessage(4);
                        }
                        Slog.i("SemFace", "[PROXIMITY] onSensorChanged : " + fArr[0]);
                    }
                }
            };
        } else {
            this.mProximitySensorMgr = null;
        }
        this.mFaceUtils = FaceUtils.getLegacyInstance(this.mSensorId);
        registerBroadcastEvents();
        this.mSecurityLevel = Utils.propertyStrengthToOemStrength(this.mSensor.getSensorProperties().sensorStrength);
        Slog.w("SemFace", "SL :" + this.mSecurityLevel);
    }

    /* renamed from: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$2 */
    public class C08652 extends OrientationEventListener {
        public C08652(Context context, int i) {
            super(context, i);
        }

        @Override // android.view.OrientationEventListener
        public void onOrientationChanged(int i) {
            if (SemFaceServiceExImpl.this.mContext == null) {
                return;
            }
            try {
                int rotation = SemFaceServiceExImpl.this.mContext.getDisplay().getRotation();
                if (rotation != SemFaceServiceExImpl.this.mLastRotation) {
                    SemFaceServiceExImpl.this.mLastRotation = rotation;
                    if (SemFaceServiceExImpl.this.mIsOperationStarted) {
                        SemFaceServiceExImpl.this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$2$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemFaceServiceExImpl.C08652.this.lambda$onOrientationChanged$0();
                            }
                        });
                    }
                }
            } catch (UnsupportedOperationException e) {
                Slog.w("SemFace", "onOrientationChanged: " + e.getMessage());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOrientationChanged$0() {
            SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.this;
            semFaceServiceExImpl.daemonSetRotation(semFaceServiceExImpl.mLastRotation);
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void onBootPhase(int i) {
        if (i == 600) {
            this.mSemAnalyticsManager = SemBioAnalyticsManager.getInstance();
        }
    }

    public final void startInactivityTimer(int i) {
        Slog.i("SemFace", "startInactivityTimer : " + i);
        this.mHandlerMain.removeMessages(1);
        this.mHandlerMain.sendEmptyMessageDelayed(1, (long) i);
    }

    public final void resumeEnrollExt(int i) {
        Slog.i("SemFace", "resumeEnrollExt : " + i);
        this.mHandlerMain.removeMessages(1);
        this.mHandlerMain.sendEmptyMessageDelayed(1, (long) i);
        acquireDVFS(i, 1);
    }

    public final void pauseEnrollExt() {
        Slog.i("SemFace", "pauseEnrollExt");
        this.mHandlerMain.removeMessages(1);
        releaseDVFS();
    }

    public boolean resetAuthenticationTimeout(int i) {
        Slog.i("SemFace", "resetAuthenticationTimeout");
        if (i <= 0) {
            return false;
        }
        startInactivityTimer(i);
        releaseDVFS();
        acquireDVFS(i, 2);
        return true;
    }

    public synchronized void startOperation(int i) {
        Slog.i("SemFace", "startOperation S");
        this.mIsOperationStarted = true;
        this.mOperationType = i;
        this.mStartOperationTime = System.currentTimeMillis();
        int i2 = 60000;
        if (i == 1) {
            this.mEnrollStartTime = System.currentTimeMillis();
            if (!Utils.isTalkBackEnabled(this.mContext)) {
                i2 = 30000;
            }
            Slog.d("SemFace", "enroll timeout set as : " + i2);
            startInactivityTimer(i2);
            this.mAppOpsManager.startOp(26, Process.myUid(), this.mContext.getOpPackageName(), false, "Biometrics_FaceService", null);
            SemBioLoggingManager.get().faceStart(this.mScheduler.getCurrentClient().getHashID(), "E", this.mScheduler.getCurrentClient().getOwnerString());
        } else if (i == 2) {
            int i3 = SemFaceBrightManager.getInstance(this.mContext).isNeededSetBrightness() ? 5000 : 4000;
            if (SemFaceUtils.needToAuthenticateExt()) {
                this.mPreviewSurface = SemFaceUtils.getSurface();
                SemFaceUtils.resetSurface();
                this.mIsAuthenticationExtOperation = true;
                obtainSurfaceHandlesIfNeeded();
                i3 = 600000;
            }
            if (!this.mSensor.getTestHalEnabled()) {
                i2 = i3;
            }
            startInactivityTimer(i2);
            startBrightness();
            byte[] bArr = new byte[1];
            bArr[0] = isBrightnessEnable() ? (byte) 1 : (byte) 0;
            daemonSetFaceTag(1, bArr);
            SemBioLoggingManager.get().faceStart(this.mScheduler.getCurrentClient().getHashID(), "A", this.mScheduler.getCurrentClient().getOwnerString());
        } else {
            i2 = 6000;
        }
        acquireDVFS(i2, i);
        setWakeLock(isCurrentClientKeyguard(), i2 + 3000);
        try {
            this.mLastRotation = this.mContext.getDisplay().getRotation();
        } catch (UnsupportedOperationException e) {
            Slog.w("SemFace", "startOperation: failed to get display, " + e.getMessage());
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
        this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SemFaceServiceExImpl.this.lambda$startOperation$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOperation$1() {
        if (this.mOrientationEventListener.canDetectOrientation()) {
            this.mOrientationEventListener.enable();
        }
        SemProximitySensorObserver semProximitySensorObserver = this.mProximitySensorMgr;
        if (semProximitySensorObserver != null) {
            semProximitySensorObserver.registerListener();
        }
        SystemProperties.set("service.bioface.authenticating", "1");
    }

    public final synchronized void stopOperation() {
        Slog.i("SemFace", "stopOperation S");
        if (!this.mIsOperationStarted) {
            Slog.i("SemFace", "stopOperation E : skip");
            return;
        }
        int i = this.mOperationType;
        if (i == 1) {
            this.mAppOpsManager.finishOp(26, Process.myUid(), this.mContext.getOpPackageName(), "Biometrics_FaceService");
            releaseWakeLock(true);
        } else if (i == 2) {
            releaseWakeLock(false);
            restoreBrightness();
        }
        releaseDVFS();
        this.mHandlerMain.removeMessages(1);
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
        MemoryFile memoryFile = this.mMemoryFile;
        if (memoryFile != null) {
            memoryFile.close();
            this.mMemoryFile = null;
        }
        Slog.i("SemFace", "stopOperation E");
        this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SemFaceServiceExImpl.this.lambda$stopOperation$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopOperation$2() {
        this.mOrientationEventListener.disable();
        SemProximitySensorObserver semProximitySensorObserver = this.mProximitySensorMgr;
        if (semProximitySensorObserver != null) {
            semProximitySensorObserver.unregisterListener();
        }
        SystemProperties.set("service.bioface.authenticating", "0");
    }

    public final void acquireDVFS(int i, int i2) {
        Slog.i("SemFace", "acquireDVFS");
        SemBiometricBoostingManager.getInstance().acquireDvfs(getContext(), i2 == 1 ? 3511 : 3512, 8, "GFACE_SERVICE", i);
    }

    public final void releaseDVFS() {
        Slog.i("SemFace", "releaseDVFS");
        SemBiometricBoostingManager.getInstance().release(getContext(), 8);
    }

    public final boolean isBrightnessEnable() {
        return (getCurrentClientUserID() == -10000 || Utils.isFlipFolded(this.mContext) || Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "face_brighten_screen", 1, getCurrentClientUserID()) != 1) ? false : true;
    }

    public final void startBrightness() {
        if (isBrightnessEnable()) {
            SemFaceBrightManager.getInstance(this.mContext).startBrightness(this.mUserId);
        }
    }

    public final void restoreBrightness() {
        if (isBrightnessEnable()) {
            SemFaceBrightManager.getInstance(this.mContext).restoreBrightness();
        }
    }

    public final void upBrightnessMax() {
        SemFaceBrightManager.getInstance(this.mContext).setBrightnessMax();
    }

    public final void userActivity() {
        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
    }

    public final synchronized void setWakeLock(boolean z, int i) {
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
    }

    public final synchronized void releaseWakeLock(boolean z) {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            Slog.i("SemFace", "releaseWakeLock : " + z);
            if (z) {
                userActivity();
            }
            this.mWakeLock.release();
        }
    }

    public int getSecurityLevel(boolean z) {
        int i = this.mSecurityLevel;
        if (!z && i == 1) {
            i = 2;
        }
        Slog.i("SemFace", "getSL : " + this.mSecurityLevel + " (" + i + "), " + Binder.getCallingPid());
        return i;
    }

    public void dump(PrintWriter printWriter) {
        try {
            printWriter.println(" current User : " + getCurrentClientUserID());
            StringBuilder sb = new StringBuilder();
            sb.append(" SL : ");
            sb.append(this.mSecurityLevel);
            sb.append(" , ");
            sb.append(getSecurityLevel(false));
            sb.append(SemBiometricFeature.FEATURE_JDM_HAL ? " , J" : " , S");
            printWriter.println(sb.toString());
            printWriter.println(" FALI : " + this.mFALI + ", FABK : " + this.mFABK + ", FAMO : " + this.mFAMO + ", FALQ : " + this.mFALQ + ", FANM : " + this.mFANM);
            SemBioLoggingManager.get().faceDump(printWriter);
        } catch (Exception e) {
            Slog.w("SemFace", "dump: " + e.getMessage());
        }
    }

    public final void registerBroadcastEvents() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(new C08674(), intentFilter);
    }

    /* renamed from: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$4 */
    public class C08674 extends BroadcastReceiver {
        public C08674() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("SemFace", "onBroadCastReceive : " + action);
            action.hashCode();
            if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                SemFaceServiceExImpl.this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemFaceServiceExImpl.C08674.this.lambda$onReceive$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0() {
            SemFaceServiceExImpl.this.daemonCancelInternal();
        }
    }

    public final void sendBroadcast(String str, int i, int i2) {
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
            Slog.e("SemFace", "sendBroadcast failed :" + e);
        }
    }

    public final void onExtended(int i) {
        int i2;
        long currentTimeMillis = System.currentTimeMillis() - this.mStartOperationTime;
        if (i == 1) {
            SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
            if (semBioAnalyticsManager != null) {
                semBioAnalyticsManager.faceOnAuthenticatedSuccess(getCurrentClientOwnerString());
            }
            SemBioLoggingManager.get().faceStop(getCurrentClientHashID(), "M", currentTimeMillis, 0);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            SemBioAnalyticsManager semBioAnalyticsManager2 = this.mSemAnalyticsManager;
            if (semBioAnalyticsManager2 != null) {
                i2 = semBioAnalyticsManager2.getFaceTimeoutDetailCondition();
                this.mSemAnalyticsManager.faceOnTimeout(getCurrentClientOwnerString());
            } else {
                i2 = 0;
            }
            int i3 = i2;
            if (this.mOperationType == 2) {
                sendAcquired(22, 100002);
            }
            SemBioLoggingManager.get().faceTimeout(this.mContext, getCurrentClientHashID(), currentTimeMillis, i3, 0);
            SemBioLoggingManager.get().faceStop(getCurrentClientHashID(), "T", currentTimeMillis, 0);
            return;
        }
        boolean z = SemBiometricFeature.FEATURE_JDM_HAL;
        int noMatchReason = z ? -1 : SemFaceUtils.getNoMatchReason(this.mBrightnessUp, this.mBacklight, this.mMotion, this.mInsufficient, this.mNoMatchMaxCountNum);
        SemBioAnalyticsManager semBioAnalyticsManager3 = this.mSemAnalyticsManager;
        if (semBioAnalyticsManager3 != null) {
            if (z) {
                semBioAnalyticsManager3.faceOnAuthenticatedFailure(getCurrentClientOwnerString());
            } else {
                semBioAnalyticsManager3.faceOnAuthenticatedFailure(getCurrentClientOwnerString(), noMatchReason);
                switch (noMatchReason) {
                    case 100002:
                        this.mFALI++;
                        break;
                    case 100003:
                        this.mFALQ++;
                        break;
                    case 100004:
                    default:
                        this.mFANM++;
                        break;
                    case 100005:
                        this.mFABK++;
                        break;
                    case 100006:
                        this.mFAMO++;
                        break;
                }
                sendAcquired(22, noMatchReason);
            }
        }
        SemBioLoggingManager.get().faceNoMatch(this.mContext, getCurrentClientHashID(), currentTimeMillis, noMatchReason, 0);
        SemBioLoggingManager.get().faceStop(getCurrentClientHashID(), "N", currentTimeMillis, 0);
    }

    public void onRemovedExt(String str, int i) {
        if (this.mFaceUtils.getBiometricsForUser(this.mContext, getCurrentClientUserID()).size() > 0) {
            sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_REMOVED", i, getCurrentClientUserID());
        } else {
            sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_RESET", -1, getCurrentClientUserID());
        }
        SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
        if (semBioAnalyticsManager != null) {
            semBioAnalyticsManager.faceOnRemoved(String.valueOf(i));
        }
        SemBioLoggingManager.get().faceRemoved(str, -1);
    }

    public void onErrorExt(int i, int i2) {
        SemBioLoggingManager.get().faceError(this.mContext, getCurrentClientHashID(), System.currentTimeMillis() - this.mStartOperationTime, i, i2);
        if (i != 5 || i != 3) {
            SemBioLoggingManager.get().faceStop(getCurrentClientHashID(), "E", System.currentTimeMillis() - this.mStartOperationTime, i == 8 ? i2 : i);
        }
        SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
        if (semBioAnalyticsManager != null) {
            semBioAnalyticsManager.faceOnError(getCurrentClientOwnerString(), i, i2);
        }
        if (i == 8 && i2 == 1006 && this.mOperationType == 2) {
            sendAcquired(22, FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED);
        }
    }

    public boolean onPreError(int i, int i2) {
        if (this.mSensor.getTestHalEnabled()) {
            return false;
        }
        if (i == 5) {
            Slog.d("SemFace", "onError: skip error (5:cancel) from daemon");
            return true;
        }
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if ((!(currentClient instanceof FaceAuthenticationClient) && !(currentClient instanceof FaceEnrollClient)) || this.mIsOperationStarted) {
            return false;
        }
        Slog.d("SemFace", "onError: skip (" + i + ") after stop()");
        return true;
    }

    public void onError(int i, int i2) {
        if (i == 8 && i2 == 1001) {
            Slog.e("SemFace", "onError : TEMPLATE_CORRUPTED");
            daemonEnumerateUser();
        }
        stopOperation();
    }

    public int onPreAcquired(int i, int i2, boolean z) {
        String str;
        if (this.mSensor.getTestHalEnabled()) {
            return 0;
        }
        if (i == 1) {
            this.mInsufficient++;
        } else {
            if (i == 22 && i2 == 1018) {
                Slog.d("SemFace", "onPreAcquired: early stop");
                this.mIsEarlyStop = true;
                return 1;
            }
            if (i == 22 && i2 == 1019) {
                Slog.d("SemFace", "onPreAcquired: no match max count");
                if (this.mIsAuthenticationExtOperation) {
                    sendFailed();
                    return 1;
                }
                this.mNoMatchMaxCountNum++;
                this.mIsAuthenticateResult = true;
                return 1;
            }
            if (i == 22 && i2 == 1022) {
                this.mBacklight++;
                return 1;
            }
            if (i == 22 && i2 == 1023) {
                this.mMotion++;
                return 1;
            }
        }
        if (z && this.mEnrollStartTime != -1 && SemFaceUtils.isNoFaceGuideEvents(i, i2)) {
            if (this.mEnrollStartTime + 3000 > System.currentTimeMillis()) {
                Slog.d("SemFace", "onPreAcquired: no face guide event skip (" + i + ", " + i2 + ")");
                return 1;
            }
            this.mEnrollStartTime = -1L;
        }
        int i3 = this.mOperationType;
        if (i3 == 2 && i == 22 && i2 == 1015) {
            upBrightnessMax();
            Slog.d("SemFace", "onAcquired: upBrightnessMax");
            this.mBrightnessUp++;
        } else if (i3 == 1 && ((i == 22 && i2 == 1015) || i == 3)) {
            this.mIsCheckedTooDark = true;
            this.mBrightnessUp++;
            Slog.d("SemFace", "onAcquired: upBrightnessMax");
        }
        if ((i != 22 && i == this.mPrevAcquiredInfo) || (i == 22 && i2 == this.mPrevAcquiredVendorInfo)) {
            int i4 = this.mSkipAcquiredEventCount + 1;
            this.mSkipAcquiredEventCount = i4;
            if (i4 < 30) {
                return 1;
            }
        }
        this.mSkipAcquiredEventCount = 0;
        this.mPrevAcquiredInfo = i;
        this.mPrevAcquiredVendorInfo = i2;
        if (!this.mIsOperationStarted) {
            Slog.d("SemFace", "onPreAcquired: skip (" + i + ", " + i2 + ") after stop()");
            return 1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("onAcquired: ");
        sb.append(i);
        boolean z2 = Utils.DEBUG;
        String str2 = "";
        if (z2) {
            str = "(" + FaceManager.getAcquiredName(i) + ")";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(", ");
        sb.append(i2);
        if (z2) {
            str2 = "(" + FaceManager.getAcquiredName(i2) + ")";
        }
        sb.append(str2);
        Slog.d("SemFace", sb.toString());
        return 0;
    }

    public void onAcquired(int i, int i2) {
        SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
        if (semBioAnalyticsManager != null) {
            semBioAnalyticsManager.faceCountHelpEvent(i, i2);
        }
    }

    public void onEnrollResult(int i, int i2) {
        if (this.mIsEnrollPausing && i2 == 30) {
            pauseEnrollExt();
        }
        if (i2 == 0) {
            sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_ADDED", i, getCurrentClientUserID());
            SemBioAnalyticsManager semBioAnalyticsManager = this.mSemAnalyticsManager;
            if (semBioAnalyticsManager != null) {
                semBioAnalyticsManager.faceOnEnrollmentSuccess(String.valueOf(i));
            }
            SemFaceUtils.saveEnrolledFacePostion(this.mContext, i, getCurrentClientUserID());
            SemBioLoggingManager.get().faceStop(getCurrentClientHashID(), "S", System.currentTimeMillis() - this.mStartOperationTime, 0);
            stopOperation();
        }
    }

    public boolean removeSavedFaceIdIfCancelled(int[] iArr) {
        if (!this.mDaemonIsCancelled) {
            return false;
        }
        stopOperation();
        try {
            daemonRemove(iArr);
        } catch (Exception e) {
            Slog.e("SemFace", "removeSavedFaceIdIfCancelled: " + e.getMessage());
        }
        Slog.i("SemFace", "removeSavedFaceIdIfCancelled: remove registered face as enrollment is being cancelled");
        return true;
    }

    public final void onTimeout() {
        int i = this.mOperationType;
        if (i == 1) {
            onExtended(3);
            daemonCancelInternal();
            if (this.mIsCheckedTooDark) {
                sendError(8, 100002);
                return;
            } else {
                sendError(3, 0);
                return;
            }
        }
        if (i == 2) {
            if (this.mIsAuthenticateResult) {
                Slog.i("SemFace", "biofs : no match (timeout)");
                this.mSensor.getSessionForUser(getCurrentClientUserID()).getHalSessionCallback().onAuthenticationFailed();
            } else {
                Slog.i("SemFace", "biofs : no face");
                onExtended(3);
                daemonCancelInternal();
                sendError(3, 0);
            }
        }
    }

    public boolean onPreAuthenticationFailed() {
        Slog.d("SemFace", "e=" + this.mIsEarlyStop + ", n=" + this.mNoMatchMaxCountNum + ", t=" + this.mIsTimeout);
        if (this.mSensor.getTestHalEnabled()) {
            return false;
        }
        if (!this.mIsOperationStarted) {
            Slog.d("SemFace", "onAuthenticated: skip events after stop()");
            return true;
        }
        this.mIsAuthenticateResult = true;
        return (this.mIsEarlyStop || this.mNoMatchMaxCountNum > 0 || this.mIsTimeout) ? false : true;
    }

    public void onAuthenticationFailed() {
        onExtended(2);
        daemonCancelInternal();
    }

    public boolean onPreAuthenticationSucceeded() {
        if (this.mIsAuthenticationExtOperation) {
            sendSucceeded(daemonGetWrappedDataFromMemory());
            return true;
        }
        daemonGetWrappedData();
        return false;
    }

    public void onAuthenticationSucceeded() {
        onExtended(1);
        stopOperation();
    }

    public void doTemplateSyncForUser(int[] iArr) {
        boolean z;
        if (iArr == null) {
            iArr = new int[0];
        }
        final int currentClientUserID = getCurrentClientUserID();
        List<Face> biometricsForUser = this.mFaceUtils.getBiometricsForUser(this.mContext, currentClientUserID);
        int length = iArr.length;
        try {
            Slog.i("SemFace", "doTemplateSyncForUser: FW=" + biometricsForUser.size() + ", HAL=" + length);
            if (SemBiometricFeature.FEATURE_JDM_HAL) {
                z = false;
            } else {
                if (length > 0) {
                    z = true;
                    for (int i : iArr) {
                        if (i == 1) {
                            z = false;
                        }
                    }
                } else {
                    z = false;
                }
                if (z) {
                    Slog.w("SemFace", "Main face ID(1) was removed!!!");
                    daemonRemove(new int[]{0});
                }
            }
            if (length == 0 || z) {
                if (biometricsForUser.size() > 0) {
                    for (Face face : biometricsForUser) {
                        this.mFaceUtils.removeBiometricForUser(this.mContext, currentClientUserID, face.getBiometricId());
                        Slog.i("SemFace", "removing unknown template from fw, " + face.getBiometricId());
                    }
                    this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFaceServiceExImpl.this.lambda$doTemplateSyncForUser$3(currentClientUserID);
                        }
                    });
                    SemBioLoggingManager.get().faceRemoved("doTemplateSyncForUser", -1);
                    return;
                }
                return;
            }
            if (length > 0) {
                for (int i2 = 0; i2 < length; i2++) {
                    int size = biometricsForUser.size();
                    Slog.d("SemFace", "enumerate: HAL ID=" + iArr[i2]);
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            i3 = -1;
                            break;
                        } else if (((Face) biometricsForUser.get(i3)).getBiometricId() == iArr[i2]) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i3 >= 0) {
                        biometricsForUser.remove(i3);
                    } else {
                        final Face face2 = new Face(this.mFaceUtils.getUniqueName(this.mContext, currentClientUserID), iArr[i2], this.mSensorId);
                        this.mFaceUtils.addBiometricForUser(this.mContext, currentClientUserID, face2);
                        Slog.i("SemFace", "enumerate: adding unknown template, " + face2.getBiometricId());
                        this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemFaceServiceExImpl.this.lambda$doTemplateSyncForUser$4(face2, currentClientUserID);
                            }
                        });
                    }
                }
                for (final Face face3 : biometricsForUser) {
                    this.mFaceUtils.removeBiometricForUser(this.mContext, currentClientUserID, face3.getBiometricId());
                    Slog.i("SemFace", "removing unknown template from fw, " + face3.getBiometricId());
                    SemBioLoggingManager.get().faceRemoved("doTemplateSyncForUser", -1);
                    this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFaceServiceExImpl.this.lambda$doTemplateSyncForUser$5(currentClientUserID, face3);
                        }
                    });
                }
            }
        } catch (Exception e) {
            Slog.w("SemFace", "enumerate: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doTemplateSyncForUser$3(int i) {
        sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_RESET", -1, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doTemplateSyncForUser$4(Face face, int i) {
        sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_ADDED", face.getBiometricId(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doTemplateSyncForUser$5(int i, Face face) {
        if (this.mFaceUtils.getBiometricsForUser(this.mContext, i).size() > 0) {
            sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_REMOVED", face.getBiometricId(), i);
        } else {
            sendBroadcast("com.samsung.android.bio.face.intent.action.FACE_RESET", -1, i);
        }
    }

    public final boolean isCurrentClientKeyguard() {
        String currentClientOwnerString = getCurrentClientOwnerString();
        return currentClientOwnerString != null && Utils.isKeyguard(this.mContext, currentClientOwnerString);
    }

    public final String getCurrentClientOwnerString() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient == null) {
            Slog.e("SemFace", "getCurrentClientOwnerString : client is null");
            return null;
        }
        return currentClient.getOwnerString();
    }

    public ICancellationSignal daemonAuthenticate(long j) {
        ISession iSession = this.mISession;
        if (iSession == null) {
            Slog.e("SemFace", "authenticate(): no ISession!");
            return null;
        }
        ISehSession iSehSession = this.mISehSession;
        if (iSehSession == null) {
            this.mCancellationSignal = iSession.authenticate(j);
        } else if (this.mIsAuthenticationExtOperation) {
            this.mCancellationSignal = iSehSession.authenticateForIssuance(j, SemFaceUtils.getSecurityMode(getContext()), SemFaceUtils.getFidoRequestData(), true, this.mHwPreviewHandle);
        } else {
            this.mCancellationSignal = iSehSession.authenticateExtension(j, SemFaceUtils.getSecurityMode(getContext()), SemFaceUtils.getFidoRequestData());
        }
        return this.mCancellationSignal;
    }

    public ICancellationSignal daemonEnroll(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle) {
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonEnroll: no face HAL!");
            throw new IllegalArgumentException();
        }
        if (hardwareAuthToken == null) {
            Slog.w("SemFace", "daemonEnroll: hardwareAuthToken is null");
            throw new IllegalArgumentException();
        }
        if (hardwareAuthToken.challenge == 0) {
            Slog.w("SemFace", "daemonEnroll: hardwareAuthToken mac.length = " + hardwareAuthToken.mac.length + ", id=" + hardwareAuthToken.authenticatorId + ", challenge=" + hardwareAuthToken.challenge + ", type=" + hardwareAuthToken.authenticatorType);
            throw new IllegalArgumentException();
        }
        if (Utils.DEBUG) {
            Slog.d("SemFace", "hardwareAuthToken  id = " + hardwareAuthToken.authenticatorId);
        }
        ICancellationSignal enroll = this.mISession.enroll(hardwareAuthToken, b, bArr, nativeHandle);
        this.mCancellationSignal = enroll;
        return enroll;
    }

    public synchronized void daemonCancel(ICancellationSignal iCancellationSignal, boolean z) {
        stopOperation();
        if (iCancellationSignal == null) {
            Slog.w("SemFace", "cancellationSignal is null");
            return;
        }
        if (!z) {
            this.mHandlerMain.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemFaceServiceExImpl.this.lambda$daemonCancel$6();
                }
            });
        }
        this.mDaemonIsCancelled = true;
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "daemonCancel START");
            iCancellationSignal.cancel();
            Slog.w("SemFace", "daemonCancel FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: ");
        } catch (RemoteException e) {
            Slog.e("SemFace", "Failed to get biometric interface", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$daemonCancel$6() {
        sendError(5, 0);
    }

    public final synchronized void daemonCancelInternal() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (!(currentClient instanceof FaceAuthenticationClient) && !(currentClient instanceof FaceEnrollClient)) {
            Slog.d("SemFace", "daemonCancelInternal not auth(enroll) client");
        } else {
            Slog.d("SemFace", "daemonCancelInternal");
        }
        daemonCancel(this.mCancellationSignal, true);
    }

    public final void daemonSetRotation(int i) {
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonSetRotation(): no face seh HAL!");
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "SetRotation START");
            Slog.w("SemFace", "SetRotation FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mISehSession.setRotation(getRotationValue(i)));
        } catch (Exception e) {
            Slog.w("SemFace", "daemonSetRotation: " + e.getMessage());
        }
    }

    public void daemonRemove(int[] iArr) {
        long j;
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonRemove: no face HAL!");
            return;
        }
        try {
            j = System.currentTimeMillis();
            try {
                Slog.w("SemFace", "removeEnrollments START");
                this.mISession.removeEnrollments(iArr);
            } catch (RemoteException e) {
                e = e;
                Slog.e("SemFace", "daemonRemove : " + e);
                Slog.w("SemFace", "removeEnrollments FINISH (" + (System.currentTimeMillis() - j) + "ms) RESULT: ");
            }
        } catch (RemoteException e2) {
            e = e2;
            j = 0;
        }
        Slog.w("SemFace", "removeEnrollments FINISH (" + (System.currentTimeMillis() - j) + "ms) RESULT: ");
    }

    public void daemonPauseEnroll() {
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonPauseEnroll(): no face seh HAL!");
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "pause FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mISehSession.pause());
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonPauseEnroll: ", e);
        }
        this.mIsEnrollPausing = true;
    }

    public void daemonResumeEnroll() {
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonResumeEnroll(): no face seh HAL!");
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "resume FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mISehSession.resume());
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonResumeEnroll: ", e);
        }
        this.mIsEnrollPausing = false;
        resumeEnrollExt(Utils.isTalkBackEnabled(this.mContext) ? 60000 : 30000);
    }

    public void daemonPauseAuth() {
        if (this.mScheduler.getCurrentClient() instanceof FaceAuthenticationClient) {
            if (this.mISehSession == null) {
                Slog.w("SemFace", "daemonPauseAuth(): no face seh HAL!");
                return;
            }
            try {
                long currentTimeMillis = System.currentTimeMillis();
                Slog.w("SemFace", "pause FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mISehSession.pause());
                return;
            } catch (RemoteException e) {
                Slog.e("SemFace", "daemonPauseAuth: ", e);
                return;
            }
        }
        Slog.w("SemFace", "daemonPauseAuth skipped");
    }

    public void daemonResumeAuth() {
        if (this.mScheduler.getCurrentClient() instanceof FaceAuthenticationClient) {
            if (this.mISehSession == null) {
                Slog.w("SemFace", "daemonResumeAuth(): no face seh HAL!");
                return;
            }
            try {
                long currentTimeMillis = System.currentTimeMillis();
                Slog.w("SemFace", "resume FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mISehSession.resume());
                return;
            } catch (RemoteException e) {
                Slog.e("SemFace", "daemonResumeAuth(auth): ", e);
                return;
            }
        }
        Slog.w("SemFace", "daemonResumeAuth skipped");
    }

    public String daemonGetInfo(int i) {
        String str = null;
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonGetInfo(): no face seh HAL!");
            return null;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            if (i != 1) {
                return null;
            }
            str = this.mISehSession.getTaInfo();
            Slog.w("SemFace", "getEngineVersion FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + str);
            return str;
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonGetInfo: ", e);
            return str;
        }
    }

    public void daemonSessionOpen() {
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonSessionOpen(): no face seh HAL!");
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "sehOpenTaSession FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mISehSession.loadTA());
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonSessionOpen: ", e);
        }
    }

    public void daemonSessionClose() {
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonSessionClose(): no face seh HAL!");
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Slog.w("SemFace", "sehCloseTaSession FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mISehSession.unloadTA());
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonSessionClose: ", e);
        }
    }

    public boolean daemonIsSessionClose() {
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonIsSessionClose(): no face seh HAL!");
            return false;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            boolean isTAUnloaded = this.mISehSession.isTAUnloaded();
            Slog.w("SemFace", "sehIsTaSessionClosed FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + isTAUnloaded);
            return isTAUnloaded;
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonIsSessionClose: ", e);
            return false;
        }
    }

    public void daemonEnumerateUser() {
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonEnumerateUser(): no face HAL!");
            return;
        }
        try {
            Slog.i("SemFace", "daemonEnumerateUser START");
            long currentTimeMillis = System.currentTimeMillis();
            this.mISession.enumerateEnrollments();
            Slog.w("SemFace", "daemonEnumerateUser FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonEnumerateUser: ", e);
        }
    }

    public void daemonGenerateChallenge() {
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonGenerateChallenge(): no face HAL!");
            return;
        }
        try {
            Slog.i("SemFace", "daemonGenerateChallenge START");
            long currentTimeMillis = System.currentTimeMillis();
            this.mISession.generateChallenge();
            Slog.w("SemFace", "daemonGenerateChallenge FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonGenerateChallenge: ", e);
        }
    }

    public void daemonRevokeChallenge(long j) {
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonRevokeChallenge(): no face HAL!");
            return;
        }
        try {
            Slog.i("SemFace", "daemonRevokeChallenge START");
            long currentTimeMillis = System.currentTimeMillis();
            this.mISession.revokeChallenge(j);
            Slog.w("SemFace", "daemonRevokeChallenge FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonRevokeChallenge: ", e);
        }
    }

    public void daemonGetFeatures() {
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonGetFeatures(): no face HAL!");
            return;
        }
        try {
            Slog.i("SemFace", "daemonGetFeatures START");
            long currentTimeMillis = System.currentTimeMillis();
            this.mISession.getFeatures();
            Slog.w("SemFace", "daemonGetFeatures FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonGetFeatures: ", e);
        }
    }

    public void daemonGetAuthenticatorId() {
        if (this.mISession == null) {
            Slog.w("SemFace", "daemonGetAuthenticatorId(): no face HAL!");
            return;
        }
        try {
            Slog.i("SemFace", "daemonGetAuthenticatorId START");
            long currentTimeMillis = System.currentTimeMillis();
            this.mISession.getAuthenticatorId();
            Slog.w("SemFace", "daemonGetAuthenticatorId FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
        } catch (RemoteException e) {
            Slog.e("SemFace", "daemonGetAuthenticatorId: ", e);
        }
    }

    public void daemonGetWrappedData() {
        String str;
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonGetWrappedData(): no seh face HAL!");
            return;
        }
        try {
            Slog.i("SemFace", "getWrappedData START");
            long currentTimeMillis = System.currentTimeMillis();
            byte[] wrappedData = this.mISehSession.getWrappedData();
            Slog.i("SemFace", "getWrappedData FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
            if (wrappedData != null && wrappedData.length > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("getWrappedData size: ");
                sb.append(wrappedData.length);
                if (Utils.DEBUG) {
                    str = ", data: " + SemFaceUtils.byteArrayToHex(wrappedData);
                } else {
                    str = "";
                }
                sb.append(str);
                Slog.d("SemFace", sb.toString());
                SemFaceUtils.setFidoResultData(wrappedData);
                return;
            }
            Slog.w("SemFace", "getWrappedData : data is null or 0");
        } catch (RemoteException e) {
            Slog.e("SemFace", "getWrappedData: ", e);
        }
    }

    public Bundle daemonGetWrappedDataFromMemory() {
        ParcelFileDescriptor parcelFileDescriptor;
        long currentTimeMillis;
        Ashmem wrappedDataFromMemory;
        Bundle bundle = new Bundle();
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonGetWrappedDataFromMemory(): no seh face HAL!");
            return bundle;
        }
        try {
            Slog.i("SemFace", "getWrappedDataFromMemory START");
            currentTimeMillis = System.currentTimeMillis();
            wrappedDataFromMemory = this.mISehSession.getWrappedDataFromMemory();
        } catch (Exception e) {
            Log.w("SemFace", "Unable to read statistics stream", e);
            parcelFileDescriptor = null;
        }
        if (wrappedDataFromMemory == null) {
            Slog.e("SemFace", "getWrappedDataFromMemory: ash is null");
            return bundle;
        }
        Slog.i("SemFace", "getWrappedDataFromMemory FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
        ByteBuffer mapReadOnly = SharedMemory.fromFileDescriptor(wrappedDataFromMemory.fd).mapReadOnly();
        if (mapReadOnly == null) {
            Slog.e("SemFace", "getWrappedDataFromMemory: dataBuffer is null");
            return bundle;
        }
        int remaining = mapReadOnly.remaining();
        byte[] bArr = new byte[remaining];
        mapReadOnly.get(bArr, 0, (int) wrappedDataFromMemory.size);
        Slog.i("SemFace", "getWrappedDataFromMemory read " + remaining);
        if (Utils.DEBUG) {
            int i = 128;
            if (remaining <= 128) {
                i = remaining;
            }
            Slog.i("SemFace", "data = " + Arrays.toString(Arrays.copyOf(bArr, i)));
        }
        if (this.mMemoryFile == null) {
            this.mMemoryFile = new MemoryFile("auth_preview", remaining);
        }
        this.mMemoryFile.writeBytes(bArr, 0, 0, remaining);
        parcelFileDescriptor = ParcelFileDescriptor.dup((FileDescriptor) MemoryFile.class.getDeclaredMethod("getFileDescriptor", new Class[0]).invoke(this.mMemoryFile, new Object[0]));
        Slog.i("SemFace", "getWrappedDataFromMemory save");
        bundle.putParcelable("memoryfile_descriptor", parcelFileDescriptor);
        return bundle;
    }

    public final int daemonSetFaceTag(int i, byte[] bArr) {
        String str;
        if (this.mISehSession == null) {
            Slog.w("SemFace", "daemonSetFaceTag(): no seh face HAL!");
            return 0;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("setFaceTag START type = ");
            sb.append(i);
            if (Utils.DEBUG) {
                str = ", data: " + SemFaceUtils.byteArrayToHex(bArr);
            } else {
                str = "";
            }
            sb.append(str);
            Slog.i("SemFace", sb.toString());
            long currentTimeMillis = System.currentTimeMillis();
            this.mISehSession.setFaceTag(i, bArr);
            Slog.i("SemFace", "setFaceTag FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
            return 1;
        } catch (RemoteException e) {
            Slog.w("SemFace", "setFaceTag: " + e.getMessage());
            return 0;
        }
    }

    public void daemonClose() {
        Slog.d("SemFace", "daemonClose");
        try {
            if (this.mISession == null) {
                Slog.e("SemFace", "daemonClose: no face HAL!");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            this.mISession.close();
            Slog.w("SemFace", "ISession.close: FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
            this.mISession = null;
            if (this.mISehSession == null) {
                Slog.e("SemFace", "daemonClose: no seh face HAL!");
                return;
            }
            long currentTimeMillis2 = System.currentTimeMillis();
            this.mISehSession.close();
            Slog.w("SemFace", "IsehSession.close: FINISH (" + (System.currentTimeMillis() - currentTimeMillis2) + "ms)");
            this.mISehSession = null;
        } catch (RemoteException e) {
            Slog.e("SemFace", "IsehSession.close: ", e);
        }
    }

    public boolean isDaemonConnectionClosed() {
        return this.mISehSession == null || this.mISession == null;
    }

    public final void sendAcquired(int i, int i2) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (!(currentClient instanceof AcquisitionClient)) {
            Slog.e("SemFace", "sendAcquired - not AcquisitionClient: " + Utils.getClientName(currentClient));
            return;
        }
        ((AcquisitionClient) currentClient).onAcquired(i, i2);
    }

    public final void sendError(int i, int i2) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (!(currentClient instanceof AcquisitionClient)) {
            Slog.e("SemFace", "sendError - not AcquisitionClient: " + Utils.getClientName(currentClient));
            return;
        }
        ((AcquisitionClient) currentClient).onError(i, i2);
    }

    public final void sendSucceeded(Bundle bundle) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (!(currentClient instanceof AcquisitionClient)) {
            Slog.e("SemFace", "sendSuccess - not AcquisitionClient: " + Utils.getClientName(currentClient));
            return;
        }
        try {
            currentClient.getListener().onSemAuthenticationSucceededWithBundle(this.mUserId, bundle);
        } catch (RemoteException e) {
            Slog.e("SemFace", "sendSucceeded : Unable to notify listener, finishing", e);
        }
    }

    public final void sendFailed() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (!(currentClient instanceof AcquisitionClient)) {
            Slog.e("SemFace", "sendFailed - not AcquisitionClient: " + Utils.getClientName(currentClient));
            return;
        }
        try {
            currentClient.getListener().onSemAuthenticationFailed();
        } catch (RemoteException e) {
            Slog.e("SemFace", "sendFailed : Unable to notify listener, finishing", e);
        }
    }

    public final int getCurrentClientHashID() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient == null) {
            Slog.e("SemFace", "getCurrentClientHashID : client is null");
            return 0;
        }
        return currentClient.getHashID();
    }

    public final int getCurrentClientUserID() {
        return this.mUserId;
    }

    public boolean isTpaSehTestHalEnabled() {
        return (Build.IS_USERDEBUG || Build.IS_ENG) && this.mTpaHalModeEnabled;
    }

    public boolean isUsingSehAPI() {
        return (this.mSensor.getTestHalEnabled() || isTpaSehTestHalEnabled() || SemBiometricFeature.FEATURE_JDM_HAL) ? false : true;
    }

    public void setTpaHalEnabled(boolean z) {
        if (this.mTpaHalModeEnabled == z) {
            return;
        }
        this.mTpaHalModeEnabled = z;
        this.mSensor.setTestHalEnabled(true);
        this.mSensor.setTestHalEnabled(false);
        int intDb = Utils.getIntDb(this.mContext, "biometric_tpa_mode", true, 0);
        Utils.putIntDb(this.mContext, "biometric_tpa_mode", true, z ? intDb | 8 : intDb & (-9));
    }

    public final void obtainSurfaceHandlesIfNeeded() {
        Surface surface = this.mPreviewSurface;
        if (surface != null) {
            android.os.NativeHandle acquireSurfaceHandle = FaceService.acquireSurfaceHandle(surface);
            this.mOsPreviewHandle = acquireSurfaceHandle;
            try {
                this.mHwPreviewHandle = AidlNativeHandleUtils.dup(acquireSurfaceHandle);
                Slog.v("SemFace", "Obtained handles for the preview surface.");
            } catch (IOException e) {
                this.mHwPreviewHandle = null;
                Slog.e("SemFace", "Failed to dup mOsPreviewHandle", e);
            }
        }
    }

    public final void releaseSurfaceHandlesIfNeeded() {
        if (this.mPreviewSurface != null && this.mHwPreviewHandle == null) {
            Slog.w("SemFace", "mHwPreviewHandle is null even though mPreviewSurface is not null.");
        }
        if (this.mHwPreviewHandle != null) {
            try {
                Slog.v("SemFace", "Closing mHwPreviewHandle");
                AidlNativeHandleUtils.close(this.mHwPreviewHandle);
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
}
