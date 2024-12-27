package com.samsung.android.biometrics.app.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.SysUiUdfpsManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsFodHandler;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconOptionMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconVisibilityNotifier;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconVisibilityNotifier$$ExternalSyntheticLambda0;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthSensorWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsUiCustom;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils;
import java.io.FileDescriptor;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class SysUiUdfpsManager extends SysUiManager implements AodStatusMonitor.Callback {
    public final AodStatusMonitor mAodStatusMonitor;
    public UdfpsFodHandler mFodHandler;
    public boolean mHasPrepareRequest;
    public final UdfpsIconOptionMonitor mIconOptionMonitor;
    public final UdfpsIconVisibilityNotifier mUdfpsIconNotifier;
    public UdfpsSensorWindow mUdfpsSensorWindow;

    /* renamed from: com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public SysUiUdfpsManager(Context context, DisplayStateManager displayStateManager, PowerServiceProviderImpl powerServiceProviderImpl, TaskStackObserver taskStackObserver, FpServiceProviderImpl fpServiceProviderImpl, AodStatusMonitor aodStatusMonitor, UdfpsIconOptionMonitor udfpsIconOptionMonitor, UdfpsIconVisibilityNotifier udfpsIconVisibilityNotifier) {
        super(context, displayStateManager, powerServiceProviderImpl, taskStackObserver, fpServiceProviderImpl);
        this.mAodStatusMonitor = aodStatusMonitor;
        this.mIconOptionMonitor = udfpsIconOptionMonitor;
        this.mUdfpsIconNotifier = udfpsIconVisibilityNotifier;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public SysUiClient createClient(SysUiClientOptions sysUiClientOptions, ISemBiometricSysUiCallback iSemBiometricSysUiCallback) {
        SysUiClient createClient = super.createClient(sysUiClientOptions, iSemBiometricSysUiCallback);
        if (createClient instanceof UdfpsKeyguardClient) {
            UdfpsKeyguardClient udfpsKeyguardClient = (UdfpsKeyguardClient) createClient;
            udfpsKeyguardClient.setAodStatusMonitor(this.mAodStatusMonitor);
            udfpsKeyguardClient.setUdfpsIconOptionMonitor(this.mIconOptionMonitor);
        } else if (createClient instanceof BiometricPromptClient) {
            BiometricPromptClient biometricPromptClient = (BiometricPromptClient) createClient;
            if (biometricPromptClient.mPromptConfig.canUseFingerprint() && (!UCMUtils.isUCMKeyguardEnabled(biometricPromptClient.mPromptConfig.mUserId) || UCMUtils.isSupportBiometricForUCM(biometricPromptClient.mPromptConfig.mUserId))) {
                Log.i("BSS_SysUiManager", "createClient: setUdfpsForBpClient ");
                setUdfpsForBpClient(biometricPromptClient);
            }
        }
        return createClient;
    }

    /* JADX WARN: Type inference failed for: r8v0, types: [com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda4] */
    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final SysUiClientFactoryImpl createClientFactory() {
        Context context = this.mContext;
        Handler handler = this.mH;
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        SysUiUdfpsManager$$ExternalSyntheticLambda0 sysUiUdfpsManager$$ExternalSyntheticLambda0 = new SysUiUdfpsManager$$ExternalSyntheticLambda0(this, 0);
        SysUiUdfpsManager$$ExternalSyntheticLambda0 sysUiUdfpsManager$$ExternalSyntheticLambda02 = new SysUiUdfpsManager$$ExternalSyntheticLambda0(this, 1);
        SysUiUdfpsManager$$ExternalSyntheticLambda0 sysUiUdfpsManager$$ExternalSyntheticLambda03 = new SysUiUdfpsManager$$ExternalSyntheticLambda0(this, 2);
        SysUiUdfpsManager$$ExternalSyntheticLambda0 sysUiUdfpsManager$$ExternalSyntheticLambda04 = new SysUiUdfpsManager$$ExternalSyntheticLambda0(this, 3);
        final UdfpsIconVisibilityNotifier udfpsIconVisibilityNotifier = this.mUdfpsIconNotifier;
        Objects.requireNonNull(udfpsIconVisibilityNotifier);
        return new SysUiClientFactoryImpl(context, handler, displayStateManager, sysUiUdfpsManager$$ExternalSyntheticLambda0, sysUiUdfpsManager$$ExternalSyntheticLambda02, sysUiUdfpsManager$$ExternalSyntheticLambda03, sysUiUdfpsManager$$ExternalSyntheticLambda04, new Consumer() { // from class: com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UdfpsIconVisibilityNotifier udfpsIconVisibilityNotifier2 = UdfpsIconVisibilityNotifier.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                udfpsIconVisibilityNotifier2.getClass();
                BackgroundThread.get().getClass();
                BackgroundThread.sHandler.post(new UdfpsIconVisibilityNotifier$$ExternalSyntheticLambda0(udfpsIconVisibilityNotifier2, booleanValue));
            }
        });
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public void destroy() {
        UdfpsFodHandler udfpsFodHandler = this.mFodHandler;
        if (udfpsFodHandler != null) {
            UdfpsFodHandler.AnonymousClass1 anonymousClass1 = udfpsFodHandler.mBroadCastReceiverForTSP;
            if (anonymousClass1 != null) {
                try {
                    udfpsFodHandler.mContext.unregisterReceiver(anonymousClass1);
                    udfpsFodHandler.mBroadCastReceiverForTSP = null;
                } catch (Exception e) {
                    FocusableWindow$$ExternalSyntheticOutline0.m(e, new StringBuilder("unregisterBroadcastReceiver: "), "BSS_UdfpsFodHandler");
                }
            }
            udfpsFodHandler.mMainHandler.removeCallbacks(udfpsFodHandler.mRunnableTimeoutTouchUp);
            BackgroundThread.get().getClass();
            BackgroundThread.sHandler.removeCallbacks(udfpsFodHandler.mRunnablePrintTouchInfo);
            ((CopyOnWriteArrayList) udfpsFodHandler.mFodConsumers).clear();
        }
        this.mIconOptionMonitor.stop();
        this.mAodStatusMonitor.removeCallback(this);
        this.mAodStatusMonitor.stop();
        UdfpsSensorWindow udfpsSensorWindow = this.mUdfpsSensorWindow;
        if (udfpsSensorWindow != null) {
            udfpsSensorWindow.removeView();
        }
        super.destroy();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleBiometricTheme(int i, String str, FileDescriptor fileDescriptor) {
        if (i == 10000) {
            Context context = this.mContext;
            StringBuilder sb = new StringBuilder("custom Touch Effect (");
            sb.append(str);
            sb.append(", ");
            sb.append(fileDescriptor != null ? fileDescriptor.toString() : "null");
            sb.append(")");
            Log.i("BSS_InDisplayCustom", sb.toString());
            UdfpsUiCustom.applyCustomResource(context, str, fileDescriptor, "user_fingerprint_touch_effect.json", "user_fingerprint_touch_effect.png");
            return;
        }
        if (i == 10001) {
            Context context2 = this.mContext;
            StringBuilder sb2 = new StringBuilder("custom Finger Icon (");
            sb2.append(str);
            sb2.append(", ");
            sb2.append(fileDescriptor != null ? fileDescriptor.toString() : "null");
            sb2.append(")");
            Log.i("BSS_InDisplayCustom", sb2.toString());
            UdfpsUiCustom.applyCustomResource(context2, str, fileDescriptor, "user_fingerprint_icon.json", "user_fingerprint_icon.png");
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public void handleCaptureCompleted(int i) {
        UdfpsFodHandler udfpsFodHandler;
        SysUiClient sysUiClient = this.mCurrentClient;
        if ((sysUiClient instanceof UdfpsAuthClient) && i == sysUiClient.mSessionId) {
            if (this.mFingerprintSensorInfo.mIsAnyUdfps && (udfpsFodHandler = this.mFodHandler) != null) {
                udfpsFodHandler.mMainHandler.postDelayed(udfpsFodHandler.mRunnableTimeoutTouchUp, 1000L);
            }
            ((UdfpsAuthClient) this.mCurrentClient).onCaptureComplete();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleCaptureStarted(int i) {
        SysUiClient sysUiClient = this.mCurrentClient;
        if ((sysUiClient instanceof UdfpsAuthClient) && i == sysUiClient.mSessionId) {
            ((UdfpsAuthClient) sysUiClient).onCaptureStart();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public void handleOnClientFinished(SysUiClient sysUiClient) {
        super.handleOnClientFinished(sysUiClient);
        if (sysUiClient instanceof UdfpsKeyguardClient) {
            DisplayStateManager displayStateManager = this.mDisplayStateManager;
            displayStateManager.getClass();
            if (Utils.DEBUG) {
                Log.d("BSS_DisplayStateManager", "onScreenUnknownFromKeyguard");
            }
            displayStateManager.mDisplayStateFromKeyguard = 0;
        }
        UdfpsFodHandler udfpsFodHandler = this.mFodHandler;
        if (udfpsFodHandler != null) {
            udfpsFodHandler.mIsClientRunning = false;
            udfpsFodHandler.mMainHandler.post(udfpsFodHandler.mRunnableTimeoutTouchUp);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleOnClientStarted() {
        UdfpsFodHandler udfpsFodHandler = this.mFodHandler;
        if (udfpsFodHandler != null) {
            udfpsFodHandler.mIsClientRunning = true;
        }
    }

    public void handleOnFodSingleTap() {
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient instanceof UdfpsKeyguardClient) {
            ((UdfpsKeyguardClient) sysUiClient).handleSingleTapEvent();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleShow(SysUiClientOptions sysUiClientOptions, ISemBiometricSysUiCallback iSemBiometricSysUiCallback) {
        super.handleShow(sysUiClientOptions, iSemBiometricSysUiCallback);
        BackgroundThread backgroundThread = BackgroundThread.get();
        SysUiUdfpsManager$$ExternalSyntheticLambda8 sysUiUdfpsManager$$ExternalSyntheticLambda8 = new SysUiUdfpsManager$$ExternalSyntheticLambda8(this, false);
        backgroundThread.getClass();
        BackgroundThread.sHandler.post(sysUiUdfpsManager$$ExternalSyntheticLambda8);
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.samsung.android.biometrics.app.setting.fingerprint.UdfpsFodHandler$1] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda5] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda5] */
    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public void init() {
        super.init();
        this.mAodStatusMonitor.start();
        this.mAodStatusMonitor.addCallback(this);
        this.mIconOptionMonitor.start();
        Context context = this.mContext;
        Handler handler = this.mH;
        final int i = 0;
        ?? r4 = new BooleanSupplier(this) { // from class: com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda5
            public final /* synthetic */ SysUiUdfpsManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i2 = i;
                SysUiUdfpsManager sysUiUdfpsManager = this.f$0;
                switch (i2) {
                    case 0:
                        FingerprintSensorInfo fingerprintSensorInfo = sysUiUdfpsManager.mFingerprintSensorInfo;
                        if (fingerprintSensorInfo.mIsUltrasonic || (fingerprintSensorInfo.mIsOptical && !sysUiUdfpsManager.mAodStatusMonitor.mIsEnabledAod)) {
                        }
                        break;
                    default:
                        if (!sysUiUdfpsManager.mDisplayStateManager.isOnState()) {
                            UdfpsIconOptionMonitor udfpsIconOptionMonitor = sysUiUdfpsManager.mIconOptionMonitor;
                            if (udfpsIconOptionMonitor.mIconOptionWhenScreenOff != 1 && !udfpsIconOptionMonitor.isEnabledOnAod()) {
                            }
                        }
                        break;
                }
                return true;
            }
        };
        final int i2 = 1;
        final UdfpsFodHandler udfpsFodHandler = new UdfpsFodHandler(context, handler, r4, new BooleanSupplier(this) { // from class: com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda5
            public final /* synthetic */ SysUiUdfpsManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i22 = i2;
                SysUiUdfpsManager sysUiUdfpsManager = this.f$0;
                switch (i22) {
                    case 0:
                        FingerprintSensorInfo fingerprintSensorInfo = sysUiUdfpsManager.mFingerprintSensorInfo;
                        if (fingerprintSensorInfo.mIsUltrasonic || (fingerprintSensorInfo.mIsOptical && !sysUiUdfpsManager.mAodStatusMonitor.mIsEnabledAod)) {
                        }
                        break;
                    default:
                        if (!sysUiUdfpsManager.mDisplayStateManager.isOnState()) {
                            UdfpsIconOptionMonitor udfpsIconOptionMonitor = sysUiUdfpsManager.mIconOptionMonitor;
                            if (udfpsIconOptionMonitor.mIconOptionWhenScreenOff != 1 && !udfpsIconOptionMonitor.isEnabledOnAod()) {
                            }
                        }
                        break;
                }
                return true;
            }
        }, new SysUiUdfpsManager$$ExternalSyntheticLambda0(this, 4));
        this.mFodHandler = udfpsFodHandler;
        if (udfpsFodHandler.mBroadCastReceiverForTSP == null) {
            udfpsFodHandler.mBroadCastReceiverForTSP = new BroadcastReceiver() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsFodHandler.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, final Intent intent) {
                    final UdfpsFodHandler udfpsFodHandler2 = UdfpsFodHandler.this;
                    udfpsFodHandler2.getClass();
                    udfpsFodHandler2.mMainHandler.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsFodHandler$$ExternalSyntheticLambda1
                        /* JADX WARN: Type inference failed for: r1v11, types: [com.samsung.android.biometrics.app.setting.fingerprint.UdfpsFodHandler$$ExternalSyntheticLambda3] */
                        @Override // java.lang.Runnable
                        public final void run() {
                            final float f;
                            final float f2;
                            final UdfpsFodHandler udfpsFodHandler3 = UdfpsFodHandler.this;
                            Intent intent2 = intent;
                            udfpsFodHandler3.getClass();
                            String action = intent2.getAction();
                            if ("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE".equals(action) || "com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY".equals(action)) {
                                int intExtra = intent2.getIntExtra("info", -1);
                                float[] floatArrayExtra = intent2.getFloatArrayExtra("location");
                                Log.i("BSS_UdfpsFodHandler", action + ", " + intExtra + ", " + udfpsFodHandler3.mIsClientRunning);
                                if (floatArrayExtra == null || floatArrayExtra.length != 2) {
                                    f = RecyclerView.DECELERATION_RATE;
                                    f2 = 0.0f;
                                } else {
                                    f = floatArrayExtra[0];
                                    f2 = floatArrayExtra[1];
                                }
                                Handler handler2 = udfpsFodHandler3.mMainHandler;
                                if (intExtra == 8) {
                                    if (Utils.Config.FEATURE_SUPPORT_AOD) {
                                        UdfpsFodHandler$$ExternalSyntheticLambda0 udfpsFodHandler$$ExternalSyntheticLambda0 = new UdfpsFodHandler$$ExternalSyntheticLambda0(udfpsFodHandler3, 1);
                                        if (udfpsFodHandler3.mShouldUseDelayOfSingleTap.getAsBoolean()) {
                                            handler2.postDelayed(udfpsFodHandler$$ExternalSyntheticLambda0, 300L);
                                            return;
                                        } else {
                                            udfpsFodHandler$$ExternalSyntheticLambda0.run();
                                        }
                                    }
                                    return;
                                }
                                switch (intExtra) {
                                    case 15:
                                        if (udfpsFodHandler3.mIsClientRunning) {
                                            handler2.removeCallbacks(udfpsFodHandler3.mRunnableTimeoutTouchUp);
                                            udfpsFodHandler3.mIsTouchDown = true;
                                            Iterator it = ((CopyOnWriteArrayList) udfpsFodHandler3.mFodConsumers).iterator();
                                            while (it.hasNext()) {
                                                SysUiUdfpsManager.this.handleOnFodTouchDown();
                                            }
                                            Log.d("BSS_UdfpsFodHandler", "printTouchInfoOnBgThread() called with: x = [" + f + "], y = [" + f2 + "]");
                                            udfpsFodHandler3.mRunnablePrintTouchInfo = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsFodHandler$$ExternalSyntheticLambda3
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsFodHandler udfpsFodHandler4 = UdfpsFodHandler.this;
                                                    float f3 = f;
                                                    float f4 = f2;
                                                    udfpsFodHandler4.getClass();
                                                    if (f3 == RecyclerView.DECELERATION_RATE || f4 == RecyclerView.DECELERATION_RATE) {
                                                        return;
                                                    }
                                                    Point point = (Point) udfpsFodHandler4.mGetUdfpsCenterPointInPortraitMode.get();
                                                    int i3 = point.x - ((int) f3);
                                                    int i4 = point.y - ((int) f4);
                                                    StringBuilder sb = new StringBuilder("onFodTouchEvent: distance = ");
                                                    float f5 = point.x;
                                                    float f6 = point.y;
                                                    sb.append((((float) Math.sqrt(Math.pow(f6 - f4, 2.0d) + Math.pow(f5 - f3, 2.0d))) / Utils.getDisplayMetrics(udfpsFodHandler4.mContext).xdpi) * 25.4f);
                                                    sb.append(", diff = ");
                                                    sb.append(i3);
                                                    sb.append(", ");
                                                    sb.append(i4);
                                                    Log.i("BSS_UdfpsFodHandler", sb.toString());
                                                }
                                            };
                                            BackgroundThread.get().getClass();
                                            BackgroundThread.sHandler.post(udfpsFodHandler3.mRunnablePrintTouchInfo);
                                            break;
                                        }
                                        break;
                                    case 16:
                                    case 17:
                                        udfpsFodHandler3.handleTouchUp(f, f2);
                                        break;
                                }
                            }
                        }
                    });
                }
            };
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
                UdfpsFodHandler.AnonymousClass1 anonymousClass1 = udfpsFodHandler.mBroadCastReceiverForTSP;
                UserHandle userHandle = UserHandle.ALL;
                BackgroundThread.get().getClass();
                context.registerReceiverAsUser(anonymousClass1, userHandle, intentFilter, "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER", BackgroundThread.sHandler);
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY");
                UdfpsFodHandler.AnonymousClass1 anonymousClass12 = udfpsFodHandler.mBroadCastReceiverForTSP;
                BackgroundThread.get().getClass();
                context.registerReceiverAsUser(anonymousClass12, userHandle, intentFilter2, "com.samsung.android.permission.BROADCAST_QUICKACCESS", BackgroundThread.sHandler);
            } catch (Exception e) {
                FocusableWindow$$ExternalSyntheticOutline0.m(e, new StringBuilder("registerBroadcastReceiver: "), "BSS_UdfpsFodHandler");
            }
        }
        UdfpsFodHandler udfpsFodHandler2 = this.mFodHandler;
        ((CopyOnWriteArrayList) udfpsFodHandler2.mFodConsumers).add(new AnonymousClass1());
        if (this.mUdfpsSensorWindow == null) {
            Context context2 = this.mContext;
            UdfpsSensorWindow udfpsSensorWindow = new UdfpsSensorWindow(context2, null, this.mFingerprintSensorInfo, this.mDisplayStateManager);
            this.mUdfpsSensorWindow = udfpsSensorWindow;
            udfpsSensorWindow.initLayout((FrameLayout) LayoutInflater.from(context2).inflate(R.layout.sem_fingerprint_view, (ViewGroup) null));
            this.mUdfpsSensorWindow.setVisibility(4);
            this.mUdfpsSensorWindow.addView();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStart() {
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager.mCurrentDisplayState == 1001) {
            displayStateManager.handleDisplayStateChanged(1);
        }
    }

    public void setUdfpsForBpClient(final BiometricPromptClient biometricPromptClient) {
        UdfpsPrivilegedAuthSensorWindow udfpsPrivilegedAuthSensorWindow = new UdfpsPrivilegedAuthSensorWindow(this.mContext, new UdfpsWindowCallback() { // from class: com.samsung.android.biometrics.app.setting.SysUiUdfpsManager.2
            @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
            public final void onSensorIconVisibilityChanged(int i) {
                UdfpsIconVisibilityNotifier udfpsIconVisibilityNotifier = SysUiUdfpsManager.this.mUdfpsIconNotifier;
                boolean z = i == 0;
                udfpsIconVisibilityNotifier.getClass();
                BackgroundThread.get().getClass();
                BackgroundThread.sHandler.post(new UdfpsIconVisibilityNotifier$$ExternalSyntheticLambda0(udfpsIconVisibilityNotifier, z));
            }

            @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
            public final void onUserCancel(int i) {
                biometricPromptClient.onUserCancel(i);
            }
        }, this.mFingerprintSensorInfo, this.mDisplayStateManager, null, null, false, null);
        udfpsPrivilegedAuthSensorWindow.initFromBaseWindow(this.mUdfpsSensorWindow);
        biometricPromptClient.mUdfpsAuthSensorWindow = udfpsPrivilegedAuthSensorWindow;
        biometricPromptClient.mFingerprintSensorInfo = this.mFingerprintSensorInfo;
    }

    public void handleOnFodTouchDown() {
    }

    public void handleOnFodTouchUp() {
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.Callback
    public final void onAodStop() {
    }
}
