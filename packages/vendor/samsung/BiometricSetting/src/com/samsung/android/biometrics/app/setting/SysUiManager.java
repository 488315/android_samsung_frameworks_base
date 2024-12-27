package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Binder;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

import com.android.internal.os.SomeArgs;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiService;
import com.samsung.android.biometrics.SemBiometricConstants;
import com.samsung.android.biometrics.app.setting.DisplayStateManager.AnonymousClass2;
import com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.FpGestureCalibrationClient;
import com.samsung.android.biometrics.app.setting.fingerprint.FpServiceProvider;
import com.samsung.android.biometrics.app.setting.fingerprint.GestureCalibrationWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsEnrollClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsEnrollSensorWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCoverClient;
import com.samsung.android.biometrics.app.setting.prompt.FpCheckToEnrollClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;
import com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderClient;
import com.samsung.android.biometrics.app.setting.prompt.knox.WorkProfileClient;
import com.samsung.android.view.SemWindowManager;

import java.io.File;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class SysUiManager implements SemBiometricConstants, Handler.Callback {
    public SysUiClientFactoryImpl mClientFactory;
    public final Context mContext;
    public SysUiClient mCurrentClient;
    public final DisplayStateManager mDisplayStateManager;
    public final FingerprintSensorInfo mFingerprintSensorInfo;
    public final FpServiceProvider mFpSvcProvider;
    public final Handler mH;
    public final PowerServiceProvider mPsProvider;
    public final SysUiServiceWrapper mSysUiServiceWrapper = new SysUiServiceWrapper();
    public final TaskStackObserver mTaskStackObserver;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public final class SysUiServiceWrapper extends ISemBiometricSysUiService.Stub {
        public SysUiServiceWrapper() {}

        public static boolean isRestricted() {
            return Binder.getCallingUid() != 1000;
        }

        public final void hideBiometricDialog(int i, int i2, int i3) {
            if (isRestricted()) {
                return;
            }
            SysUiManager.this.mH.obtainMessage(2, i, i2, Integer.valueOf(i3)).sendToTarget();
        }

        public final void onBiometricAuthenticated(int i, int i2, boolean z, String str) {
            if (isRestricted()) {
                return;
            }
            if (z) {
                SysUiManager.this.mH.obtainMessage(3, str).sendToTarget();
            } else {
                SysUiManager.this.mH.obtainMessage(4, str).sendToTarget();
            }
        }

        public final void onBiometricError(int i, int i2, int i3, int i4, String str) {
            if (isRestricted()) {
                return;
            }
            SysUiManager.this.mH.obtainMessage(6, i3, i4, str).sendToTarget();
        }

        public final void onBiometricHelp(int i, int i2, int i3, int i4, String str) {
            if (isRestricted()) {
                return;
            }
            SysUiManager.this.mH.obtainMessage(5, i3, i4, str).sendToTarget();
        }

        public final void sendCommand(int i, int i2, int i3, Bundle bundle) {
            if (isRestricted()) {
                return;
            }
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            if (bundle == null) {
                bundle = new Bundle();
            }
            obtain.arg1 = bundle;
            SysUiManager.this.mH.obtainMessage(7, i2, i3, obtain).sendToTarget();
        }

        public final void setBiometricTheme(
                int i, String str, byte[] bArr, FileDescriptor fileDescriptor) {
            SysUiManager.this.handleBiometricTheme(i, str, fileDescriptor);
        }

        public final void showBiometricDialog(
                final int i,
                int i2,
                Bundle bundle,
                final ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
                boolean z,
                int i3,
                String str,
                long j,
                PromptInfo promptInfo) {
            if (isRestricted()) {
                return;
            }
            if (i2 == 0) {
                SysUiManager.this.mH.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.biometrics.app.setting.SysUiManager$SysUiServiceWrapper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ISemBiometricSysUiCallback iSemBiometricSysUiCallback2 =
                                        iSemBiometricSysUiCallback;
                                int i4 = i;
                                if (iSemBiometricSysUiCallback2 != null) {
                                    try {
                                        iSemBiometricSysUiCallback2.onError(i4, 3, -1);
                                    } catch (RemoteException e) {
                                        Log.w(
                                                "BSS_SysUiManager",
                                                "showBiometricDialog: " + e.getMessage());
                                    }
                                }
                            }
                        });
                return;
            }
            SysUiClientOptions sysUiClientOptions =
                    new SysUiClientOptions(i2, i3, i, j, str, bundle);
            sysUiClientOptions.mRequireTouchBlock = z;
            sysUiClientOptions.mPromptInfo = promptInfo;
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = sysUiClientOptions;
            obtain.arg2 = iSemBiometricSysUiCallback;
            SysUiManager.this.mH.obtainMessage(1, i, i2, obtain).sendToTarget();
        }
    }

    public SysUiManager(
            Context context,
            DisplayStateManager displayStateManager,
            PowerServiceProviderImpl powerServiceProviderImpl,
            TaskStackObserver taskStackObserver,
            FpServiceProviderImpl fpServiceProviderImpl) {
        this.mContext = context;
        this.mPsProvider = powerServiceProviderImpl;
        this.mH = new Handler(context.getMainLooper(), this);
        this.mDisplayStateManager = displayStateManager;
        this.mTaskStackObserver = taskStackObserver;
        this.mFpSvcProvider = fpServiceProviderImpl;
        this.mFingerprintSensorInfo =
                new FingerprintSensorInfo(context, fpServiceProviderImpl.mIFingerprintService);
    }

    public SysUiClient createClient(
            SysUiClientOptions sysUiClientOptions,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback) {
        SysUiClient biometricPromptClient;
        Display display;
        if (this.mClientFactory == null) {
            this.mClientFactory = createClientFactory();
        }
        SysUiClientFactoryImpl sysUiClientFactoryImpl = this.mClientFactory;
        sysUiClientFactoryImpl.getClass();
        int i = sysUiClientOptions.mType;
        int i2 = i & 8;
        Handler handler = sysUiClientFactoryImpl.mHandler;
        if (i2 != 0 || (i & 4096) != 0 || (i & 8192) != 0 || (i & 16384) != 0 || (32768 & i) != 0) {
            PromptInfo promptInfo = sysUiClientOptions.mPromptInfo;
            if (promptInfo == null) {
                promptInfo = new PromptInfo();
            }
            PromptConfig promptConfig =
                    new PromptConfig(
                            sysUiClientFactoryImpl.mContext,
                            promptInfo,
                            sysUiClientOptions.mExtraInfo,
                            sysUiClientOptions.mType,
                            sysUiClientOptions.mUserId,
                            sysUiClientOptions.mOpId,
                            sysUiClientOptions.mPackageName);
            if (promptConfig.isCheckToEnrollMode()) {
                biometricPromptClient =
                        new FpCheckToEnrollClient(
                                sysUiClientFactoryImpl.mContext,
                                sysUiClientOptions.mSessionId,
                                iSemBiometricSysUiCallback,
                                handler.getLooper(),
                                sysUiClientOptions.mExtraInfo,
                                sysUiClientOptions.mPackageName,
                                promptConfig);
            } else {
                if (promptConfig.isKnoxProfile()) {
                    if (promptConfig.mIsKnoxManagedProfile) {
                        biometricPromptClient =
                                new WorkProfileClient(
                                        sysUiClientFactoryImpl.mContext,
                                        sysUiClientOptions.mSessionId,
                                        iSemBiometricSysUiCallback,
                                        handler.getLooper(),
                                        sysUiClientOptions.mExtraInfo,
                                        sysUiClientOptions.mPackageName,
                                        promptConfig);
                    } else if (promptConfig.mIsSecureFolder) {
                        biometricPromptClient =
                                new SecureFolderClient(
                                        sysUiClientFactoryImpl.mContext,
                                        sysUiClientOptions.mSessionId,
                                        iSemBiometricSysUiCallback,
                                        handler.getLooper(),
                                        sysUiClientOptions.mExtraInfo,
                                        sysUiClientOptions.mPackageName,
                                        promptConfig);
                    }
                }
                if (Utils.Config.FEATURE_SUPPORT_BP_IN_COVER_SCREEN
                        && SemWindowManager.getInstance().isFolded()) {
                    Context context = sysUiClientFactoryImpl.mContext;
                    DisplayManager displayManager =
                            (DisplayManager) context.getSystemService(DisplayManager.class);
                    if (displayManager != null) {
                        Display[] displays =
                                displayManager.getDisplays(
                                        "com.samsung.android.hardware.display.category.BUILTIN");
                        int length = displays.length;
                        for (int i3 = 0; i3 < length; i3++) {
                            display = displays[i3];
                            if (display.getDisplayId() == 1) {
                                break;
                            }
                        }
                    }
                    Log.w("BSS_SysUiClientFactoryImpl", "getCoverDisplay: No built in display");
                    display = sysUiClientFactoryImpl.mContext.getDisplay();
                    BiometricPromptCoverClient biometricPromptCoverClient =
                            new BiometricPromptCoverClient(
                                    context.createDisplayContext(display),
                                    sysUiClientOptions.mSessionId,
                                    iSemBiometricSysUiCallback,
                                    handler.getLooper(),
                                    sysUiClientOptions.mExtraInfo,
                                    sysUiClientOptions.mPackageName,
                                    promptConfig);
                    Log.i("BSS_BiometricPromptCoverClient", "BiometricPromptCoverClient: ");
                    return biometricPromptCoverClient;
                }
                biometricPromptClient =
                        new BiometricPromptClient(
                                sysUiClientFactoryImpl.mContext,
                                sysUiClientOptions.mSessionId,
                                iSemBiometricSysUiCallback,
                                handler.getLooper(),
                                sysUiClientOptions.mExtraInfo,
                                sysUiClientOptions.mPackageName,
                                promptConfig);
            }
            return biometricPromptClient;
        }
        if (!((FingerprintSensorInfo) sysUiClientFactoryImpl.mGetFpsSensorInfo.get()).mIsAnyUdfps
                || ((i & 1) == 0 && (i & 2) == 0 && (i & 4) == 0 && (i & 64) == 0)) {
            if ((65536 & i) != 0) {
                return new FpGestureCalibrationClient(
                        sysUiClientFactoryImpl.mContext,
                        sysUiClientOptions.mUserId,
                        sysUiClientOptions.mSessionId,
                        iSemBiometricSysUiCallback,
                        handler.getLooper(),
                        sysUiClientOptions.mExtraInfo,
                        sysUiClientOptions.mPackageName);
            }
            Log.e("BSS_SysUiClientFactoryImpl", "createClient: Unknown type " + i);
            return new SysUiClientFactoryImpl.AnonymousClass1(
                    sysUiClientFactoryImpl.mContext,
                    sysUiClientOptions.mSessionId,
                    iSemBiometricSysUiCallback,
                    handler.getLooper(),
                    sysUiClientOptions.mExtraInfo,
                    sysUiClientOptions.mPackageName);
        }
        int i4 = sysUiClientOptions.mType;
        if (i4 == 64) {
            Context context2 = sysUiClientFactoryImpl.mContext;
            Bundle bundle = sysUiClientOptions.mExtraInfo;
            UdfpsSensorWindow udfpsSensorWindow =
                    (UdfpsSensorWindow) sysUiClientFactoryImpl.mGetAddedSensorWindow.get();
            FingerprintSensorInfo fingerprintSensorInfo =
                    (FingerprintSensorInfo) sysUiClientFactoryImpl.mGetFpsSensorInfo.get();
            Consumer consumer = sysUiClientFactoryImpl.mSensorIconVisibilityHandler;
            int i5 = sysUiClientOptions.mUserId;
            int i6 = sysUiClientOptions.mSessionId;
            String str = sysUiClientOptions.mPackageName;
            DisplayStateManager displayStateManager = sysUiClientFactoryImpl.mDsm;
            UdfpsEnrollClient udfpsEnrollClient =
                    new UdfpsEnrollClient(
                            context2,
                            i5,
                            i6,
                            iSemBiometricSysUiCallback,
                            bundle,
                            str,
                            displayStateManager,
                            udfpsSensorWindow,
                            fingerprintSensorInfo,
                            consumer);
            udfpsEnrollClient.mEnrollWindow =
                    new UdfpsEnrollSensorWindow(
                            context2,
                            udfpsEnrollClient,
                            fingerprintSensorInfo,
                            displayStateManager);
            return udfpsEnrollClient;
        }
        if (i4 == 4) {
            return new UdfpsKeyguardClient(
                    sysUiClientFactoryImpl.mContext,
                    sysUiClientOptions.mUserId,
                    sysUiClientOptions.mSessionId,
                    iSemBiometricSysUiCallback,
                    sysUiClientOptions.mExtraInfo,
                    sysUiClientOptions.mPackageName,
                    sysUiClientFactoryImpl.mDsm,
                    (UdfpsSensorWindow) sysUiClientFactoryImpl.mGetAddedSensorWindow.get(),
                    (FingerprintSensorInfo) sysUiClientFactoryImpl.mGetFpsSensorInfo.get(),
                    sysUiClientFactoryImpl.mSensorIconVisibilityHandler,
                    (FpServiceProvider) sysUiClientFactoryImpl.mGetFpServiceProvider.get(),
                    (PowerServiceProvider) sysUiClientFactoryImpl.mGetPowerServiceProvider.get());
        }
        if (i4 == 2) {
            return new UdfpsPrivilegedAuthClient(
                    sysUiClientFactoryImpl.mContext,
                    sysUiClientOptions.mUserId,
                    sysUiClientOptions.mSessionId,
                    iSemBiometricSysUiCallback,
                    sysUiClientOptions.mExtraInfo,
                    sysUiClientOptions.mPackageName,
                    sysUiClientFactoryImpl.mDsm,
                    (UdfpsSensorWindow) sysUiClientFactoryImpl.mGetAddedSensorWindow.get(),
                    (FingerprintSensorInfo) sysUiClientFactoryImpl.mGetFpsSensorInfo.get(),
                    sysUiClientFactoryImpl.mSensorIconVisibilityHandler,
                    sysUiClientOptions.mRequireTouchBlock);
        }
        PromptInfo promptInfo2 = new PromptInfo();
        promptInfo2.setTitle(
                sysUiClientFactoryImpl.mContext.getString(R.string.sem_fingerprint_bg_title));
        promptInfo2.setDescription(
                sysUiClientFactoryImpl.mContext.getString(
                        R.string.sem_fingerprint_bg_ready_description));
        promptInfo2.setIsForLegacyFingerprintManager(0);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("KEY_AVAILABILITY_BIOMETRIC", 2);
        return new BiometricPromptClient(
                sysUiClientFactoryImpl.mContext,
                sysUiClientOptions.mSessionId,
                iSemBiometricSysUiCallback,
                handler.getLooper(),
                sysUiClientOptions.mExtraInfo,
                sysUiClientOptions.mPackageName,
                new PromptConfig(
                        sysUiClientFactoryImpl.mContext,
                        promptInfo2,
                        bundle2,
                        i4,
                        sysUiClientOptions.mUserId,
                        sysUiClientOptions.mOpId,
                        sysUiClientOptions.mPackageName));
    }

    public SysUiClientFactoryImpl createClientFactory() {
        Context context = this.mContext;
        Handler handler = this.mH;
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        final int i = 0;
        Supplier supplier =
                new Supplier(
                        this) { // from class:
                                // com.samsung.android.biometrics.app.setting.SysUiManager$$ExternalSyntheticLambda0
                    public final /* synthetic */ SysUiManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i2 = i;
                        SysUiManager sysUiManager = this.f$0;
                        switch (i2) {
                            case 0:
                                return sysUiManager.mPsProvider;
                            case 1:
                                return sysUiManager.mFpSvcProvider;
                            default:
                                return sysUiManager.mFingerprintSensorInfo;
                        }
                    }
                };
        final int i2 = 1;
        Supplier supplier2 =
                new Supplier(
                        this) { // from class:
                                // com.samsung.android.biometrics.app.setting.SysUiManager$$ExternalSyntheticLambda0
                    public final /* synthetic */ SysUiManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i2;
                        SysUiManager sysUiManager = this.f$0;
                        switch (i22) {
                            case 0:
                                return sysUiManager.mPsProvider;
                            case 1:
                                return sysUiManager.mFpSvcProvider;
                            default:
                                return sysUiManager.mFingerprintSensorInfo;
                        }
                    }
                };
        final int i3 = 2;
        return new SysUiClientFactoryImpl(
                context,
                handler,
                displayStateManager,
                supplier,
                supplier2,
                new Supplier(
                        this) { // from class:
                                // com.samsung.android.biometrics.app.setting.SysUiManager$$ExternalSyntheticLambda0
                    public final /* synthetic */ SysUiManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i3;
                        SysUiManager sysUiManager = this.f$0;
                        switch (i22) {
                            case 0:
                                return sysUiManager.mPsProvider;
                            case 1:
                                return sysUiManager.mFpSvcProvider;
                            default:
                                return sysUiManager.mFingerprintSensorInfo;
                        }
                    }
                },
                null,
                null);
    }

    public void destroy() {
        FileObserver fileObserver;
        BackgroundThread.get().getClass();
        BackgroundThread.sHandler.removeCallbacksAndMessages(null);
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            sysUiClient.stopImmediate();
            this.mCurrentClient = null;
        }
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        ((ArrayList) displayStateManager.mCallbacks).clear();
        ((ArrayList) displayStateManager.mLimitDisplayCallbacks).clear();
        ((ArrayList) displayStateManager.mHbmListeners).clear();
        ((ArraySet) displayStateManager.mDozeRequesters).clear();
        DisplayManager displayManager = displayStateManager.mInjector.mDisplayManager;
        if (displayManager != null) {
            displayManager.unregisterDisplayListener(displayStateManager);
        }
        DisplayStateManager.Injector injector = displayStateManager.mInjector;
        if (injector.mIFingerprintService == null) {
            injector.mIFingerprintService = injector.getIFingerprintService();
        }
        IFingerprintService iFingerprintService = injector.mIFingerprintService;
        if (iFingerprintService == null) {
            Log.w("BSS_DisplayStateManager", "IFingerprintService is NULL");
        } else {
            try {
                iFingerprintService.semUnregisterDisplayStateCallback();
            } catch (RemoteException e) {
                Log.w(
                        "BSS_DisplayStateManager",
                        "unregisterDisplayStateCallbackForOpt: " + e.getMessage());
            }
        }
        if (displayStateManager.mIsOpticalUdfps
                && (fileObserver = displayStateManager.mFileObserve) != null) {
            fileObserver.stopWatching();
            displayStateManager.mFileObserve = null;
        }
        Runnable runnable = displayStateManager.mRunnableReleaseRefreshRate;
        if (runnable != null) {
            runnable.run();
            displayStateManager.mRunnableReleaseRefreshRate = null;
        }
        this.mTaskStackObserver.observe(null);
        DisplayBrightnessMonitor displayBrightnessMonitor =
                DisplayBrightnessMonitor.sInstanceHolder.sInstance;
        ((CopyOnWriteArrayList) displayBrightnessMonitor.mListenerList).clear();
        displayBrightnessMonitor.mH.removeCallbacksAndMessages(null);
        this.mH.removeCallbacksAndMessages(null);
    }

    public void handleAuthenticationSucceeded(String str) {
        Handler.Callback callback = this.mCurrentClient;
        if (callback instanceof AuthenticationConsumer) {
            ((AuthenticationConsumer) callback).onAuthenticationSucceeded(str);
            return;
        }
        Log.w(
                "BSS_SysUiManager",
                "handleAuthenticationSucceeded: for non-authentication consumer: "
                        + this.mCurrentClient);
    }

    public void handleCommand(int i, int i2, int i3, Bundle bundle) {
        UdfpsSensorWindow udfpsSensorWindow;
        UdfpsSensorWindow udfpsSensorWindow2;
        SysUiClient sysUiClient = this.mCurrentClient;
        Object obj =
                (sysUiClient == null || i == 0 || sysUiClient.mSessionId == i) ? sysUiClient : null;
        if (i2 == 501) {
            handleCalibrationMode(i, i3, bundle);
        }
        if (i2 == 600) {
            if (obj instanceof FpGestureCalibrationClient) {
                final FpGestureCalibrationClient fpGestureCalibrationClient =
                        (FpGestureCalibrationClient) obj;
                GestureCalibrationWindow gestureCalibrationWindow =
                        fpGestureCalibrationClient.mCalWindow;
                TextView textView = gestureCalibrationWindow.mDescriptionTxtView;
                if (textView != null) {
                    textView.setText(R.string.fingerprint_gesture_calibration_good_job);
                }
                gestureCalibrationWindow.mIsCalibrationSuccess = true;
                fpGestureCalibrationClient.sendEvent(8, 0);
                fpGestureCalibrationClient.mHandler.postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.biometrics.app.setting.fingerprint.FpGestureCalibrationClient$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                FpGestureCalibrationClient fpGestureCalibrationClient2 =
                                        FpGestureCalibrationClient.this;
                                fpGestureCalibrationClient2.sendDismissedEvent(1, null);
                                fpGestureCalibrationClient2.stop();
                            }
                        },
                        1500L);
                return;
            }
            return;
        }
        switch (i2) {
            case 112:
                if ((obj instanceof UdfpsClient)
                        && (udfpsSensorWindow = ((UdfpsClient) obj).mBaseSensorWindow) != null) {
                    udfpsSensorWindow.showSensorIcon();
                    break;
                }
                break;
            case 113:
                if ((obj instanceof UdfpsClient)
                        && (udfpsSensorWindow2 = ((UdfpsClient) obj).mBaseSensorWindow) != null) {
                    udfpsSensorWindow2.hideSensorIcon(0);
                    break;
                }
                break;
            case 114:
                if (obj instanceof UdfpsKeyguardClient) {
                    ((UdfpsKeyguardClient) obj)
                            .moveSensorIcon(bundle.getInt("x"), bundle.getInt("y"));
                    break;
                }
                break;
            case 115:
                if (i3 != 1) {
                    DisplayStateManager displayStateManager = this.mDisplayStateManager;
                    displayStateManager.getClass();
                    if (Utils.DEBUG) {
                        Log.d("BSS_DisplayStateManager", "onScreenOffFromKeyguard");
                    }
                    displayStateManager.mDisplayStateFromKeyguard = 1;
                    if (displayStateManager.mCurrentStateLogical != 1) {
                        if (displayStateManager.isOnState()) {
                            displayStateManager.handleDisplayStateChanged(1001);
                            break;
                        }
                    } else {
                        displayStateManager.handleDisplayStateChanged(1);
                        break;
                    }
                } else {
                    DisplayStateManager displayStateManager2 = this.mDisplayStateManager;
                    displayStateManager2.getClass();
                    if (Utils.DEBUG) {
                        Log.d("BSS_DisplayStateManager", "onScreenOnFromKeyguard");
                    }
                    displayStateManager2.mDisplayStateFromKeyguard = 2;
                    displayStateManager2.handleDisplayStateChanged(2);
                    break;
                }
                break;
            default:
                switch (i2) {
                    case 117:
                        if (obj instanceof UdfpsKeyguardClient) {
                            ((UdfpsKeyguardClient) obj).onBouncerScreen(i3 == 1);
                            break;
                        }
                        break;
                    case 118:
                        handlePrepareSession();
                        break;
                    case 119:
                        if (obj instanceof UdfpsKeyguardClient) {
                            ((UdfpsKeyguardClient) obj).showFingerIconOnLock();
                            break;
                        }
                        break;
                    default:
                        switch (i2) {
                            case 201:
                                if (obj instanceof AuthenticationConsumer) {
                                    ((AuthenticationConsumer) obj)
                                            .onAuthenticationHelp(
                                                    i3,
                                                    FingerprintManager.getAcquiredString(
                                                            this.mContext, i3, 0));
                                    break;
                                }
                                break;
                            case 202:
                                if (i3 != 1004 && i3 != 1005) {
                                    if (i3 == 10002) {
                                        handleCaptureStarted(i);
                                        break;
                                    } else if (i3 == 10003) {
                                        handleCaptureCompleted(i);
                                        break;
                                    }
                                } else if (Utils.Config.FP_FEATURE_TSP_BLOCK
                                        && sysUiClient != null) {
                                    sysUiClient.handleTspBlock(i3 == 1004);
                                    break;
                                }
                                break;
                            case 203:
                                if (obj instanceof AuthenticationConsumer) {
                                    ((AuthenticationConsumer) obj)
                                            .onAuthenticationError(i3, 0, null);
                                    break;
                                }
                                break;
                            case 204:
                                if (obj instanceof AuthenticationConsumer) {
                                    ((AuthenticationConsumer) obj)
                                            .onAuthenticationError(8, i3, null);
                                    break;
                                }
                                break;
                        }
                }
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.i("BSS_SysUiManager", Utils.getLogFormat(message));
        switch (message.what) {
            case 1:
                SomeArgs someArgs = (SomeArgs) message.obj;
                handleShow(
                        (SysUiClientOptions) someArgs.arg1,
                        (ISemBiometricSysUiCallback) someArgs.arg2);
                someArgs.recycle();
                break;
            case 2:
                int i = message.arg1;
                SysUiClient sysUiClient = this.mCurrentClient;
                if (sysUiClient != null && sysUiClient.mSessionId == i) {
                    sysUiClient.stop();
                    break;
                }
                break;
            case 3:
                handleAuthenticationSucceeded((String) message.obj);
                break;
            case 4:
                String str = (String) message.obj;
                Handler.Callback callback = this.mCurrentClient;
                if (callback instanceof AuthenticationConsumer) {
                    ((AuthenticationConsumer) callback).onAuthenticationFailed(str);
                    break;
                }
                break;
            case 5:
                int i2 = message.arg1;
                int i3 = message.arg2;
                String str2 = (String) message.obj;
                Handler.Callback callback2 = this.mCurrentClient;
                if (callback2 instanceof AuthenticationConsumer) {
                    AuthenticationConsumer authenticationConsumer =
                            (AuthenticationConsumer) callback2;
                    if (i2 == 6) {
                        i2 = i3;
                    }
                    authenticationConsumer.onAuthenticationHelp(i2, str2);
                    break;
                }
                break;
            case 6:
                int i4 = message.arg1;
                int i5 = message.arg2;
                String str3 = (String) message.obj;
                Handler.Callback callback3 = this.mCurrentClient;
                if (callback3 instanceof AuthenticationConsumer) {
                    ((AuthenticationConsumer) callback3).onAuthenticationError(i4, i5, str3);
                    break;
                }
                break;
            case 7:
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                handleCommand(someArgs2.argi1, message.arg1, message.arg2, (Bundle) someArgs2.arg1);
                someArgs2.recycle();
                break;
            case 9:
                handleOnTaskStackListener(message.arg1, message.arg2);
                break;
        }
        return true;
    }

    public void handleOnClientFinished(SysUiClient sysUiClient) {
        if (this.mCurrentClient == sysUiClient) {
            this.mCurrentClient = null;
        }
    }

    public void handleOnTaskStackListener(int i, int i2) {
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            sysUiClient.handleOnTaskStackListener(i, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.samsung.android.biometrics.app.setting.SysUiManager$1] */
    public void handleShow(
            SysUiClientOptions sysUiClientOptions,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback) {
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            if (sysUiClient.mSessionId == sysUiClientOptions.mSessionId) {
                return;
            }
            if (sysUiClient instanceof UdfpsKeyguardClient) {
                sysUiClient.stopImmediate();
            } else {
                sysUiClient.stop();
            }
        }
        SysUiClient createClient = createClient(sysUiClientOptions, iSemBiometricSysUiCallback);
        Log.d("BSS_SysUiManager", "new Client from " + createClient.mPackageName);
        this.mCurrentClient = createClient;
        createClient.start(
                new ClientCallback() { // from class:
                                       // com.samsung.android.biometrics.app.setting.SysUiManager.1
                    @Override // com.samsung.android.biometrics.app.setting.ClientCallback
                    public final void onClientFinished(SysUiClient sysUiClient2) {
                        SysUiManager.this.handleOnClientFinished(sysUiClient2);
                    }

                    @Override // com.samsung.android.biometrics.app.setting.ClientCallback
                    public final void onClientStarted(SysUiClient sysUiClient2) {
                        SysUiManager.this.handleOnClientStarted();
                    }
                });
    }

    public void init() {
        Looper myLooper;
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        DisplayStateManager.Injector injector = displayStateManager.mInjector;
        Handler handler = displayStateManager.mHandler;
        if (injector.mDisplayManager == null) {
            injector.mDisplayManager =
                    (DisplayManager) injector.mContext.getSystemService(DisplayManager.class);
        }
        injector.mDisplayManager.registerDisplayListener(displayStateManager, handler);
        DisplayStateManager.Injector injector2 = displayStateManager.mInjector;
        ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback =
                displayStateManager.mBioSysUiDisplayStateCallback;
        if (injector2.mIFingerprintService == null) {
            injector2.mIFingerprintService = injector2.getIFingerprintService();
        }
        IFingerprintService iFingerprintService = injector2.mIFingerprintService;
        if (iFingerprintService == null) {
            Log.w("BSS_DisplayStateManager", "IFingerprintService is NULL");
        } else {
            try {
                iFingerprintService.semRegisterDisplayStateCallback(
                        iSemBiometricSysUiDisplayStateCallback);
            } catch (RemoteException e) {
                Log.w(
                        "BSS_DisplayStateManager",
                        "registerDisplayStateCallbackForOpt: " + e.getMessage());
            }
        }
        DisplayStateManager.Injector injector3 = displayStateManager.mInjector;
        if (injector3.mDisplayManager == null) {
            injector3.mDisplayManager =
                    (DisplayManager) injector3.mContext.getSystemService(DisplayManager.class);
        }
        int i = 0;
        Display display = injector3.mDisplayManager.getDisplay(0);
        if (display == null) {
            Log.w("BSS_DisplayStateManager", "default display is NULL");
        } else {
            i = display.getState();
        }
        displayStateManager.mCurrentDisplayState = i;
        displayStateManager.mCurrentStateLogical = i;
        displayStateManager.updateDisplayState();
        if (displayStateManager.mIsOpticalUdfps && !Utils.Config.FP_FEATURE_LOCAL_HBM) {
            if (displayStateManager.mFileObserve == null) {
                displayStateManager.mFileObserve =
                        displayStateManager
                        .new AnonymousClass2(
                                new File("/sys/class/lcd/panel/actual_mask_brightness"));
            }
            displayStateManager.mFileObserve.startWatching();
        }
        DisplayStateManager displayStateManager2 = this.mDisplayStateManager;
        if (!((ArrayList) displayStateManager2.mCallbacks).contains(this)) {
            ((ArrayList) displayStateManager2.mCallbacks).add(this);
        }
        this.mTaskStackObserver.observe(new SysUiManager$$ExternalSyntheticLambda3(this));
        if (!Utils.IS_DEBUG_LEVEL_MID_OR_HIGH || (myLooper = Looper.myLooper()) == null) {
            return;
        }
        myLooper.setPerfLogEnable();
        myLooper.setSlowLogThresholdMs(100L, 300L);
    }

    public void onConfigurationChanged(Configuration configuration) {
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        int rotation = Utils.getRotation(displayStateManager.mInjector.mContext);
        displayStateManager.mPrevRotation = displayStateManager.mCurrentRotation;
        displayStateManager.mCurrentRotation = rotation;
        this.mFingerprintSensorInfo.updateSensorInfo();
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            Iterator it = sysUiClient.mWindows.iterator();
            while (it.hasNext()) {
                ((SysUiWindow) it.next()).onConfigurationChanged(configuration);
            }
        }
    }

    public void onDisplayStateChanged(int i) {
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            sysUiClient.onDisplayStateChanged(i);
        }
    }

    public void onRotationStateChanged(int i) {
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            Iterator it = sysUiClient.mWindows.iterator();
            while (it.hasNext()) {
                ((SysUiWindow) it.next()).onRotationInfoChanged(i);
            }
        }
    }

    public void handleOnClientStarted() {}

    public void handlePrepareSession() {}

    public void handleCaptureCompleted(int i) {}

    public void handleCaptureStarted(int i) {}

    public void handleBiometricTheme(int i, String str, FileDescriptor fileDescriptor) {}

    public void handleCalibrationMode(int i, int i2, Bundle bundle) {}
}
