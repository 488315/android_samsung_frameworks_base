package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Binder;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;

import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import com.samsung.android.biometrics.app.setting.fingerprint.HbmListener;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.hardware.display.IRefreshRateToken;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class DisplayStateManager implements DisplayManager.DisplayListener, Handler.Callback {
    static final int AOD_DEFAULT_HIGH_BRIGHTNESS_NIT = 60;
    static final int AOD_DEFAULT_LOW_BRIGHTNESS_NIT = 2;
    static final String RESOURCES_NAME_AOD_BRIGHTNESS_VALUES = "config_aodBrightnessValues";
    public final int[] mAodBrightnessValues;
    public final Handler mBgHandler;
    final ISemBiometricSysUiDisplayStateCallback mBioSysUiDisplayStateCallback;
    public final List mCallbacks;
    public final Context mContext;
    public int mCurrentDisplayState;
    public int mCurrentRotation;
    public int mCurrentStateLogical;
    public final SemDisplaySolutionManager mDisplaySolutionManager;
    public int mDisplayStateFromKeyguard;
    public final Set mDozeRequesters;
    FileObserver mFileObserve;
    public final Handler mHandler;
    public final List mHbmListeners;
    public final Injector mInjector;
    public final AtomicBoolean mIsLimitedDisplayInProgress;
    public final boolean mIsOpticalUdfps;
    public final List mLimitDisplayCallbacks;
    public IRefreshRateToken mPassiveModeToken;
    public int mPrevRotation;
    public IRefreshRateToken mRefreshRateToken;
    public Runnable mRunnableReleaseRefreshRate;
    public final IBinder mTokenForPassiveMode;
    public final IBinder mTokenForRefreshRate;
    public final AtomicBoolean mVirtualHbmNode;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.DisplayStateManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends ISemBiometricSysUiDisplayStateCallback.Stub {
        public AnonymousClass1() {}

        public final void onFinish(int i, int i2, int i3) {
            DisplayStateManager.this.mHandler.post(
                    new DisplayStateManager$1$$ExternalSyntheticLambda0(this, i, 0));
        }

        public final void onStart(int i, int i2, int i3) {
            DisplayStateManager.this.mHandler.post(
                    new DisplayStateManager$1$$ExternalSyntheticLambda0(this, i, 1));
        }
    }

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.DisplayStateManager$2, reason: invalid class name */
    public final class AnonymousClass2 extends FileObserver {
        public AnonymousClass2(File file) {
            super(file, DisplayStateManager.AOD_DEFAULT_LOW_BRIGHTNESS_NIT);
        }

        @Override // android.os.FileObserver
        public final void onEvent(int i, String str) {
            if (i != DisplayStateManager.AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
                return;
            }
            final boolean isEnabledHbm = DisplayStateManager.this.isEnabledHbm();
            DisplayStateManager.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.biometrics.app.setting.DisplayStateManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Runnable runnable;
                            DisplayStateManager.AnonymousClass2 anonymousClass2 =
                                    DisplayStateManager.AnonymousClass2.this;
                            boolean z = isEnabledHbm;
                            Iterator it =
                                    ((ArrayList) DisplayStateManager.this.mHbmListeners).iterator();
                            while (it.hasNext()) {
                                ((HbmListener) it.next()).onHbmChanged(z);
                            }
                            if (z
                                    || (runnable =
                                                    DisplayStateManager.this
                                                            .mRunnableReleaseRefreshRate)
                                            == null) {
                                return;
                            }
                            runnable.run();
                            DisplayStateManager.this.mRunnableReleaseRefreshRate = null;
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public class Injector {
        public final Context mContext;
        public DisplayManager mDisplayManager;
        public IFingerprintService mIFingerprintService;
        public PowerManager.WakeLock mPartialWakeLock;

        public Injector(Context context) {
            this.mContext = context;
        }

        public final IFingerprintService getIFingerprintService() {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService =
                        IFingerprintService.Stub.asInterface(
                                ServiceManager.getService("fingerprint"));
            }
            return this.mIFingerprintService;
        }

        public final void requestDisplayLimitState(boolean z) {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService = getIFingerprintService();
            }
            IFingerprintService iFingerprintService = this.mIFingerprintService;
            if (iFingerprintService == null) {
                Log.w("BSS_DisplayStateManager", "IFingerprintService is NULL");
                return;
            }
            try {
                iFingerprintService.semBioSysUiRequest(1, z ? 1 : 0, 0L, (String) null);
            } catch (RemoteException e) {
                Log.w("BSS_DisplayStateManager", "enableDisplayLimitState: " + e.getMessage());
            }
        }
    }

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public interface LimitDisplayStateCallback {
        void onLimitDisplayStateChanged(boolean z);
    }

    public DisplayStateManager(Context context) {
        this(
                context,
                new Handler(context.getMainLooper()),
                BackgroundThread.get().getLooper(),
                new Injector(context));
    }

    public static String stateToString(int i) {
        return i != 0
                ? i != 1
                        ? i != AOD_DEFAULT_LOW_BRIGHTNESS_NIT
                                ? i != 3
                                        ? i != 4
                                                ? i != 1001
                                                        ? i != 1002
                                                                ? Integer.toString(i)
                                                                : "GOING_TO_ON"
                                                        : "GOING_TO_OFF"
                                                : "DOZE_SUSPEND"
                                        : "DOZE"
                                : "ON"
                        : "OFF"
                : "UNKNOWN";
    }

    public void handleDisplayStateChanged(int i) {
        Log.i(
                "BSS_DisplayStateManager",
                "handleDisplayStateChanged: "
                        + stateToString(this.mCurrentDisplayState)
                        + " -> "
                        + stateToString(i));
        if (i == 1002) {
            i = AOD_DEFAULT_LOW_BRIGHTNESS_NIT;
        }
        if (this.mCurrentDisplayState == i) {
            return;
        }
        this.mCurrentDisplayState = i;
        if (i == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            ((ArraySet) this.mDozeRequesters).clear();
        }
        Iterator it = ((ArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((SysUiManager) it.next()).onDisplayStateChanged(this.mCurrentDisplayState);
        }
    }

    public final void handleDisplayStateLimit(boolean z) {
        if (z) {
            try {
                if (this.mCurrentDisplayState == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
                    return;
                }
            } finally {
                PowerManager.WakeLock wakeLock = this.mInjector.mPartialWakeLock;
                if (wakeLock != null) {
                    wakeLock.release();
                }
                this.mIsLimitedDisplayInProgress.set(false);
            }
        }
        Log.i("BSS_DisplayStateManager", "setDisplayStateLimit: -> ".concat(z ? "ON" : "OFF"));
        this.mInjector.requestDisplayLimitState(z);
        Iterator it = ((ArrayList) this.mLimitDisplayCallbacks).iterator();
        while (it.hasNext()) {
            ((LimitDisplayStateCallback) it.next()).onLimitDisplayStateChanged(z);
        }
        PowerManager.WakeLock wakeLock2 = this.mInjector.mPartialWakeLock;
        if (wakeLock2 != null) {
            wakeLock2.release();
        }
        this.mIsLimitedDisplayInProgress.set(false);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.d("BSS_DisplayStateManager", Utils.getLogFormat(message));
        int i = message.what;
        if (i == 1) {
            handleDisplayStateLimit(true);
        } else if (i == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            handleDisplayStateLimit(false);
        }
        return true;
    }

    public final boolean isEnabledHbm() {
        if (Utils.Config.FP_FEATURE_LOCAL_HBM) {
            return this.mVirtualHbmNode.get();
        }
        byte[] readFile = Utils.readFile(new File("/sys/class/lcd/panel/actual_mask_brightness"));
        if (readFile == null) {
            return false;
        }
        String trim = new String(readFile, StandardCharsets.UTF_8).trim();
        Log.i("BSS_DisplayStateManager", "HBM=" + trim);
        return trim.length() > 1 || trim.charAt(0) != '0';
    }

    public final boolean isOnState() {
        return this.mCurrentDisplayState == AOD_DEFAULT_LOW_BRIGHTNESS_NIT;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
        int rotation;
        int i2;
        if (i == 0
                && (rotation = Utils.getRotation(this.mInjector.mContext))
                        != (i2 = this.mCurrentRotation)
                && (rotation ^ i2) == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            this.mPrevRotation = i2;
            this.mCurrentRotation = rotation;
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((SysUiManager) it.next()).onRotationStateChanged(rotation);
            }
        }
    }

    public final void registerHbmListener(HbmListener hbmListener) {
        if (hbmListener == null) {
            return;
        }
        Log.i("BSS_DisplayStateManager", "registerHbmListener: " + hbmListener);
        if (((ArrayList) this.mHbmListeners).contains(hbmListener)) {
            return;
        }
        ((ArrayList) this.mHbmListeners).add(hbmListener);
    }

    public final void releaseRefreshRateForSeamlessMode() {
        if (isEnabledHbm()) {
            this.mRunnableReleaseRefreshRate =
                    new DisplayStateManager$$ExternalSyntheticLambda0(this, 1);
            return;
        }
        try {
            if (this.mPassiveModeToken != null) {
                Log.i("BSS_DisplayStateManager", "releasePassiveModeToken");
                this.mPassiveModeToken.release();
                this.mPassiveModeToken = null;
            }
            if (this.mRefreshRateToken != null) {
                Log.i("BSS_DisplayStateManager", "releaseRefreshRateMinLimitToken");
                this.mRefreshRateToken.release();
                this.mRefreshRateToken = null;
            }
        } catch (RemoteException unused) {
            Log.w("BSS_DisplayStateManager", "Error : releaseRefreshRateForSeamlessMode");
        }
    }

    public final void turnOffDoze(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.d("BSS_DisplayStateManager", "turnOffDoze from ".concat(str));
        ((ArraySet) this.mDozeRequesters).remove(str);
        if (((ArraySet) this.mDozeRequesters).isEmpty()) {
            try {
                this.mInjector
                        .getIFingerprintService()
                        .semBioSysUiRequest(AOD_DEFAULT_LOW_BRIGHTNESS_NIT, 0, 0L, (String) null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public final void turnOnDoze(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.d("BSS_DisplayStateManager", "turnOnDoze from ".concat(str));
        ((ArraySet) this.mDozeRequesters).add(str);
        try {
            this.mInjector
                    .getIFingerprintService()
                    .semBioSysUiRequest(AOD_DEFAULT_LOW_BRIGHTNESS_NIT, 1, 0L, (String) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void updateDisplayState() {
        if (this.mCurrentDisplayState == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            Injector injector = this.mInjector;
            Context context = this.mContext;
            injector.getClass();
            if (!((PowerManager) context.getSystemService(PowerManager.class)).isInteractive()) {
                this.mCurrentDisplayState = 1001;
            }
        }
        if (Utils.DEBUG) {
            Log.i(
                    "BSS_DisplayStateManager",
                    "DisplayStateManager#updateDisplayState: state="
                            + stateToString(this.mCurrentDisplayState));
        }
    }

    public DisplayStateManager(Context context, Handler handler, Looper looper, Injector injector) {
        boolean z;
        int i = 0;
        this.mDisplayStateFromKeyguard = 0;
        this.mIsLimitedDisplayInProgress = new AtomicBoolean(false);
        this.mTokenForPassiveMode = new Binder();
        this.mTokenForRefreshRate = new Binder();
        this.mVirtualHbmNode = new AtomicBoolean(false);
        this.mBioSysUiDisplayStateCallback = new AnonymousClass1();
        this.mContext = context;
        this.mHandler = handler;
        this.mBgHandler = new Handler(looper, this);
        this.mInjector = injector;
        IFingerprintService iFingerprintService = injector.getIFingerprintService();
        if (iFingerprintService != null) {
            try {
                for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal :
                        iFingerprintService.getSensorPropertiesInternal(
                                injector.mContext.getOpPackageName())) {
                    if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()
                            && fingerprintSensorPropertiesInternal.isOpticalType()) {
                        z = true;
                        break;
                    }
                }
            } catch (RemoteException e) {
                Log.w("BSS_DisplayStateManager", "failed to get sensor prop", e);
            }
        }
        z = false;
        this.mIsOpticalUdfps = z;
        int rotation = Utils.getRotation(injector.mContext);
        this.mPrevRotation = rotation;
        this.mCurrentRotation = rotation;
        this.mCallbacks = new ArrayList();
        this.mLimitDisplayCallbacks = new ArrayList();
        this.mHbmListeners = new ArrayList();
        this.mDozeRequesters = new ArraySet(AOD_DEFAULT_LOW_BRIGHTNESS_NIT);
        this.mDisplaySolutionManager =
                (SemDisplaySolutionManager)
                        this.mContext.getSystemService(SemDisplaySolutionManager.class);
        try {
            int[] intArray =
                    this.mContext
                            .getResources()
                            .getIntArray(
                                    this.mContext
                                            .getResources()
                                            .getIdentifier(
                                                    RESOURCES_NAME_AOD_BRIGHTNESS_VALUES,
                                                    "array",
                                                    "android"));
            this.mAodBrightnessValues = intArray;
            if (intArray.length >= AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
                if (Utils.DEBUG) {
                    StringBuilder sb = new StringBuilder();
                    int[] iArr = this.mAodBrightnessValues;
                    int length = iArr.length;
                    while (i < length) {
                        sb.append(iArr[i]);
                        sb.append(",");
                        i++;
                    }
                    Log.d("BSS_DisplayStateManager", "setAodBrightnessValues: " + sb.toString());
                    return;
                }
                return;
            }
            Log.e("BSS_DisplayStateManager", "setAodBrightnessValues: not matched!");
            if (Utils.DEBUG) {
                StringBuilder sb2 = new StringBuilder();
                int[] iArr2 = this.mAodBrightnessValues;
                int length2 = iArr2.length;
                while (i < length2) {
                    sb2.append(iArr2[i]);
                    sb2.append(",");
                    i++;
                }
                Log.d("BSS_DisplayStateManager", "setAodBrightnessValues: " + sb2.toString());
            }
            this.mAodBrightnessValues = null;
        } catch (Exception unused) {
            Log.e("BSS_DisplayStateManager", "Fail to get service array");
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {}
}
