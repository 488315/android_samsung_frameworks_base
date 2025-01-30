package com.android.server.sepunion.cover;

import android.R;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.input.IInputManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserManager;
import android.view.IDisplayFoldListener;
import android.view.IWindowManager;
import com.android.internal.util.DumpUtils;
import com.android.server.LocalServices;
import com.android.server.input.InputManagerService;
import com.android.server.wm.WindowManagerService;
import com.android.server.sepunion.cover.CoverServiceManager;
import com.android.server.sepunion.cover.CoverTestModeUtils;
import com.android.server.sepunion.cover.StateNotifier;
import com.samsung.accessory.manager.SAccessoryManagerInternal;
import com.samsung.android.cover.CoverState;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class CoverManagerServiceImpl implements CoverServiceManager.OnCoverStateProvider, StateNotifier.LcdOffDisabledByAppListener {
    public static final String TAG = "CoverManager_" + CoverManagerServiceImpl.class.getSimpleName();
    public final AutoScreenOn mAutoScreenOn;
    public Context mContext;
    public final CoverDisabler mCoverDisabler;
    public final CoverHideAnimator mCoverHideAnimator;
    public final CoverManagerAllowLists mCoverManagerAllowLists;
    public final CoverServiceManager mCoverServiceManager;
    public final CoverTestModeUtils mCoverTestModeUtils;
    public final CoverVerifier mCoverVerifier;
    public GenericCoverServiceController mGenericCoverServiceController;
    public SensorEventListener mHallicSensorEventListener;
    public final CoverManagerHandler mHandler;
    public SemInputDeviceManager mInputDeviceManager;
    public InputManagerService mInputManagerService;
    public BaseNfcLedCoverController mNfcLedCoverController;
    public PackageManager mPackageManager;
    public final PowerManager mPowerManager;
    public final ResolutionMonitor mResolutionMonitor;
    public ContentResolver mResolver;
    public SensorManager mSensorManager;
    public final SleepTokenAcquirer mSleepTokenAcquirer;
    public final StateNotifier mStateNotifier;
    public boolean mSupportHallIcSensor;
    public boolean mSupportSubDisplay;
    public final HandlerThread mThread;
    public final UserManager mUserManager;
    public WindowManagerService mWindowManagerService;
    public final CoverState mCoverState = new CoverState();
    public final Object mCoverStateLock = new Object();
    public boolean mSystemReady = false;
    public boolean mIsAttachStateChanged = false;
    public boolean mIsCoverAppCovered = false;
    public Runnable mAnimationStartCallback = new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                CoverState cloneCoverStateLocked = CoverManagerServiceImpl.this.cloneCoverStateLocked();
                CoverManagerServiceImpl.this.updateCoverStateToWindowManagerLocked(cloneCoverStateLocked);
                CoverManagerServiceImpl.this.mCoverServiceManager.updateCoverState(cloneCoverStateLocked);
                CoverManagerServiceImpl.this.mStateNotifier.updateCoverSwitchState(cloneCoverStateLocked, false);
            }
        }
    };
    public Runnable mResolutionMonitorCallback = new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                CoverManagerServiceImpl.this.mCoverState.updateVisibleRect(CoverManagerServiceImpl.this.mCoverState.getType());
                CoverState cloneCoverStateLocked = CoverManagerServiceImpl.this.cloneCoverStateLocked();
                CoverManagerServiceImpl.this.updateCoverStateToWindowManagerLocked(cloneCoverStateLocked);
                CoverManagerServiceImpl.this.mCoverServiceManager.updateCoverState(cloneCoverStateLocked);
                CoverManagerServiceImpl.this.mStateNotifier.updateCoverSwitchState(cloneCoverStateLocked, false);
            }
        }
    };
    public final IDisplayFoldListener.Stub mDisplayFoldListener = new IDisplayFoldListener.Stub() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.3
        public void onDisplayFoldChanged(int i, boolean z) {
            Log.i(CoverManagerServiceImpl.TAG, "onDisplayFoldChanged: displayId = " + i + ", folded = " + z);
            if (i == 0) {
                if (z) {
                    CoverManagerServiceImpl.this.notifyCoverSwitchStateChanged(System.nanoTime(), CoverManagerServiceImpl.this.getCoverSwitchStateFromInputManager() != 1);
                } else {
                    CoverManagerServiceImpl.this.notifyCoverSwitchStateChanged(System.nanoTime(), true);
                }
            }
        }
    };

    public int getVersion() {
        return R.interpolator.accelerate_quad;
    }

    public CoverManagerServiceImpl(Context context) {
        this.mSupportSubDisplay = false;
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mResolver = context.getContentResolver();
        HandlerThread handlerThread = new HandlerThread("cover");
        this.mThread = handlerThread;
        handlerThread.start();
        CoverManagerHandler coverManagerHandler = new CoverManagerHandler(handlerThread.getLooper());
        this.mHandler = coverManagerHandler;
        coverManagerHandler.post(new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.4
            @Override // java.lang.Runnable
            public void run() {
                Process.setThreadPriority(-4);
                Process.setCanSelfBackground(false);
            }
        });
        this.mCoverVerifier = new CoverVerifier(this.mContext);
        this.mCoverServiceManager = new CoverServiceManager(this.mContext, coverManagerHandler.getLooper(), this);
        this.mStateNotifier = new StateNotifier(handlerThread.getLooper(), this.mContext);
        this.mCoverDisabler = new CoverDisabler(handlerThread.getLooper(), this.mContext);
        this.mCoverHideAnimator = new CoverHideAnimator(this.mContext, coverManagerHandler.getLooper());
        this.mCoverManagerAllowLists = new CoverManagerAllowLists();
        this.mAutoScreenOn = new AutoScreenOn(handlerThread.getLooper(), this.mContext);
        this.mResolutionMonitor = new ResolutionMonitor(this.mContext, handlerThread.getLooper(), this.mResolutionMonitorCallback);
        this.mSleepTokenAcquirer = new SleepTokenAcquirer(this.mContext, handlerThread.getLooper());
        this.mCoverTestModeUtils = new CoverTestModeUtils(this.mContext, new CoverTestModeUtils.OnCoverTestModeChanged() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.5
            @Override // com.android.server.sepunion.cover.CoverTestModeUtils.OnCoverTestModeChanged
            public void onCoverTestModeChanged(int i, boolean z) {
                if (CoverManagerServiceImpl.this.mSystemReady) {
                    if (i == 255) {
                        CoverManagerServiceImpl.this.notifySmartCoverAttachStateChangedInternal(z, new CoverState(true, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, 0, 0, 0, z));
                    } else {
                        CoverManagerServiceImpl.this.updateCoverAttachState(z, false, null);
                    }
                }
            }
        });
        PackageManager packageManager = this.mContext.getPackageManager();
        this.mPackageManager = packageManager;
        this.mSupportSubDisplay = false;
        this.mSupportHallIcSensor = packageManager.hasSystemFeature("com.sec.feature.cover.hallic");
    }

    public void systemRunning() {
        if (this.mSystemReady) {
            return;
        }
        this.mSystemReady = true;
        initialize();
    }

    public void onBootComplete() {
        this.mStateNotifier.onBootComplete();
        this.mInputDeviceManager = (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
    }

    public final void initialize() {
        if (CoverTestModeUtils.isTestMode()) {
            new Handler().postDelayed(new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.6
                @Override // java.lang.Runnable
                public void run() {
                    CoverManagerServiceImpl.this.updateCoverAttachState(true, false, null);
                }
            }, 5000L);
            return;
        }
        if (this.mAutoScreenOn.support()) {
            updateCoverAttachState(getCoverAttachStateFromInputManager() == 1, true, null);
            boolean z = getCoverSwitchStateFromInputManager() != 1;
            if (this.mCoverDisabler.isCoverManagerDisabled() || FactoryTest.isFactoryBinary() || this.mAutoScreenOn.off()) {
                z = true;
            }
            updateCoverSwitchState(z, true);
            return;
        }
        Log.d(TAG, "Nfc authentication supported, skipping creating first coverState");
    }

    public void onUserUnlocked(int i) {
        String str = TAG;
        Log.d(str, "onUserUnlocked : " + i);
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            Log.w(str, "User " + i + " is no longer unlocked - exiting");
            return;
        }
        synchronized (this.mCoverStateLock) {
            if (this.mCoverState.getAttachState()) {
                this.mCoverServiceManager.bindCoverService(this.mCoverState.getType(), false);
            }
        }
    }

    public void onSwitchUser(int i) {
        String str = TAG;
        Log.d(str, "onSwitchUser : " + i);
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            Log.w(str, "User " + i + " is no longer unlocked - exiting");
            synchronized (this.mCoverStateLock) {
                if (this.mCoverState.getAttachState()) {
                    this.mCoverServiceManager.unbindActiveCoverService(this.mCoverState.getType());
                }
            }
            return;
        }
        synchronized (this.mCoverStateLock) {
            if (this.mCoverState.getAttachState()) {
                this.mCoverServiceManager.switchCoverService(this.mCoverState.getType(), i);
            }
        }
        if (this.mAutoScreenOn.support()) {
            this.mAutoScreenOn.update();
        }
    }

    public final CoverState cloneCoverStateLocked() {
        CoverState coverState = new CoverState();
        coverState.copyFrom(this.mCoverState);
        return coverState;
    }

    public final WindowManagerService getWindowManagerService() {
        if (this.mWindowManagerService == null) {
            this.mWindowManagerService = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        }
        return this.mWindowManagerService;
    }

    public final InputManagerService getInputManagerService() {
        if (this.mInputManagerService == null) {
            this.mInputManagerService = IInputManager.Stub.asInterface(ServiceManager.getService("input"));
        }
        return this.mInputManagerService;
    }

    public final SensorEventListener getHallicSensorEventListener() {
        if (this.mHallicSensorEventListener == null) {
            this.mHallicSensorEventListener = new SensorEventListener() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.7
                @Override // android.hardware.SensorEventListener
                public void onAccuracyChanged(Sensor sensor, int i) {
                }

                @Override // android.hardware.SensorEventListener
                public void onSensorChanged(SensorEvent sensorEvent) {
                    int i = (int) sensorEvent.values[0];
                    Log.i(CoverManagerServiceImpl.TAG, "onSensorChanged: hallic = " + i);
                    if (i == 0) {
                        CoverManagerServiceImpl.this.notifyCoverSwitchStateChanged(System.nanoTime(), false);
                    } else if (i == 1) {
                        CoverManagerServiceImpl.this.notifyCoverSwitchStateChanged(System.nanoTime(), true);
                    }
                }
            };
        }
        return this.mHallicSensorEventListener;
    }

    public void registerCallback(IBinder iBinder, ComponentName componentName) {
        registerListenerCallbackInternal(iBinder, componentName, 1, true);
    }

    public void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) {
        registerListenerCallbackInternal(iBinder, componentName, i, true);
    }

    public void registerListenerCallbackForExternal(IBinder iBinder, ComponentName componentName, int i) {
        String nameForUid = this.mPackageManager.getNameForUid(Binder.getCallingUid());
        Log.e(TAG, "deprecated registerListenerCallbackForExternal " + nameForUid);
    }

    public final void registerListenerCallbackInternal(IBinder iBinder, ComponentName componentName, int i, boolean z) {
        if (z && Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "registerListenerCallbackInternal : caller is invalid");
        } else {
            this.mStateNotifier.registerListenerCallback(iBinder, componentName, i);
        }
    }

    public boolean unregisterCallback(IBinder iBinder) {
        return unregisterCallbackInternal(iBinder, true);
    }

    public boolean unregisterCallbackForExternal(IBinder iBinder) {
        String nameForUid = this.mPackageManager.getNameForUid(Binder.getCallingUid());
        Log.e(TAG, "deprecated unregisterCallbackForExternal " + nameForUid);
        return false;
    }

    public final boolean unregisterCallbackInternal(IBinder iBinder, boolean z) {
        if (!z || Binder.getCallingUid() == Process.myUid() || this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            return this.mStateNotifier.unregisterCallback(iBinder);
        }
        return false;
    }

    public void notifySmartCoverAttachStateChanged(long j, boolean z, CoverState coverState) {
        if (!this.mSystemReady) {
            Log.d(TAG, "notifySmartCoverAttachStateChanged : return because system is not yet ready");
            return;
        }
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        if (coverState != null && coverState.getType() != 9 && coverState.getType() != 10 && !CoverServiceManager.verifySystemFeature(this.mContext, coverState.getType())) {
            Log.d(TAG, "notifySmartCoverAttachStateChanged : not supported cover type = " + coverState.getType());
            Log.addLogString("CoverManager_", "cover attach is failed - not supported cover type = " + coverState.getType());
            return;
        }
        Log.d(TAG, "notifySmartCoverAttachStateChanged : attach = " + z);
        if (coverState != null) {
            Log.addLogString("CoverManager_", "cover attach : " + z + ", cover type : " + coverState.getType());
        }
        notifySmartCoverAttachStateChangedInternal(z, coverState);
    }

    public final void notifySmartCoverAttachStateChangedInternal(boolean z, CoverState coverState) {
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.notifyAuthenticationResponse();
        }
        updateCoverAttachState(z, false, coverState);
    }

    public final void updateCoverAttachState(boolean z, boolean z2, CoverState coverState) {
        String str = TAG;
        Log.d(str, "updateCoverAttachState : attach=" + z + ", isBoot=" + z2);
        if (!z2) {
            CoverManagerUtils.performCPUBoostCover(this.mContext);
        }
        if (this.mCoverVerifier.updateCoverAttachState(z)) {
            synchronized (this.mCoverStateLock) {
                sendCoverAttachStateLocked(z2, coverState);
            }
            return;
        }
        Log.d(str, "updateCoverAttachState : Returning attach state - it is same");
    }

    public final void sendCoverAttachStateLocked(boolean z, CoverState coverState) {
        boolean isCoverAttached = this.mCoverVerifier.isCoverAttached();
        boolean z2 = (isCoverAttached && getCoverSwitchStateFromInputManager() == 1) ? false : true;
        if (FactoryTest.isFactoryBinary() || (this.mAutoScreenOn.support() && this.mAutoScreenOn.off())) {
            z2 = true;
        }
        this.mCoverVerifier.updateCoverPropertiesLocked(this.mCoverState, coverState);
        int type = this.mCoverState.getType();
        this.mCoverState.getColor();
        if (Feature.getInstance(this.mContext).isSupportSecureCover()) {
            this.mStateNotifier.updateCoverAttachState(isCoverAttached, type, this.mCoverState.getSwitchState());
            return;
        }
        initializeCoverController(isCoverAttached, type, this.mCoverState.getColor());
        this.mIsAttachStateChanged = true;
        if (isCoverAttached) {
            this.mCoverServiceManager.bindCoverService(type, false);
            this.mStateNotifier.updateCoverAttachState(true, type, this.mCoverState.getSwitchState());
            if (!this.mCoverDisabler.isCoverManagerDisabled()) {
                sendCoverSwitchStateLocked(z2, z, true, false);
            } else {
                this.mCoverDisabler.setRealCoverSwitchState(z2);
            }
            if (CoverManagerUtils.isSamsungCover(this.mCoverState)) {
                sendCoverInformationToAgentLocked(z);
            }
            if (this.mSupportSubDisplay) {
                getWindowManagerService().registerDisplayFoldListener(this.mDisplayFoldListener);
            }
            if (this.mSupportHallIcSensor) {
                if (this.mSensorManager == null) {
                    this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
                }
                Sensor defaultSensor = this.mSensorManager.getDefaultSensor(65600);
                if (defaultSensor != null) {
                    Log.i(TAG, "sendCoverAttachStateLocked : register Sensor ");
                    this.mSensorManager.registerListener(getHallicSensorEventListener(), defaultSensor, 3);
                }
            }
        } else {
            sendCoverSwitchStateLocked(true, z, true, false);
            this.mStateNotifier.updateCoverAttachState(false, type, this.mCoverState.getSwitchState());
            this.mCoverServiceManager.unbindCoverService(type);
            this.mCoverDisabler.setRealCoverSwitchState(true);
            if (this.mNfcLedCoverController != null) {
                this.mNfcLedCoverController = null;
            } else if (this.mGenericCoverServiceController != null) {
                this.mGenericCoverServiceController = null;
            }
            if (this.mSupportSubDisplay) {
                getWindowManagerService().unregisterDisplayFoldListener(this.mDisplayFoldListener);
            }
            if (this.mSupportHallIcSensor && this.mHallicSensorEventListener != null) {
                if (this.mSensorManager == null) {
                    this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
                }
                Log.i(TAG, "sendCoverAttachStateLocked : unregister Sensor ");
                this.mSensorManager.unregisterListener(getHallicSensorEventListener());
            }
            this.mCoverState.type = 2;
            dispatchInputManager(true, 2);
        }
        Log.d(TAG, "sendCoverAttachStateLocked : coverAttached = " + isCoverAttached + ", switchState=" + z2);
    }

    public final void sendCoverInformationToAgentLocked(boolean z) {
        if (Feature.getInstance(this.mContext).isNfcAuthEnabled()) {
            CoverManagerUtils.sendCoverInformationToAgent(this.mContext, this.mCoverState.getSerialNumber(), z);
        }
    }

    public void notifyCoverSwitchStateChanged(long j, boolean z) {
        if (!this.mSystemReady) {
            Log.d(TAG, "notifyCoverSwitchStateChanged : return because system is not yet ready");
            return;
        }
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            Log.d(TAG, "getCallingUid() = " + Binder.getCallingUid() + ", myUid() == " + Process.myUid());
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        if (this.mCoverDisabler.isCoverManagerDisabled()) {
            boolean z2 = !this.mCoverVerifier.isCoverAttached() || z;
            this.mCoverDisabler.setRealCoverSwitchState(z2);
            this.mCoverDisabler.sendCoverSwitchIntent(this.mContext, z2);
            Log.addLogString("CoverManager_", "cover switch fail because CoverManager is disabled");
            return;
        }
        if (FactoryTest.isRunningFactoryApp()) {
            Log.addLogString("CoverManager_", "cover switch fail because factory app is running.");
            return;
        }
        if (this.mSupportSubDisplay && !z && !getWindowManagerService().isFolded()) {
            Log.addLogString("CoverManager_", "folder is open.");
            synchronized (this.mCoverStateLock) {
                if (this.mCoverState.getSwitchState()) {
                    return;
                } else {
                    Log.addLogString("CoverManager_", "AutoScreenOn changed in cover closed state");
                }
            }
        }
        try {
            String str = TAG;
            Log.d(str, "notifyCoverSwitchStateChanged : switchState = " + z + ", type = " + this.mCoverState.getType());
            SemInputDeviceManager semInputDeviceManager = this.mInputDeviceManager;
            if (semInputDeviceManager != null) {
                semInputDeviceManager.setCoverMode(z, this.mCoverState.getType());
            } else {
                Log.d(str, "InputDeviceManager is null");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (this.mAutoScreenOn.support() && this.mAutoScreenOn.off()) {
            Log.addLogString("CoverManager_", "AutoScreenOn is disabled.");
            synchronized (this.mCoverStateLock) {
                if (this.mCoverState.getSwitchState()) {
                    return;
                } else {
                    Log.addLogString("CoverManager_", "AutoScreenOn changed in cover closed state");
                }
            }
        }
        Log.addLogString("CoverManager_", "cover switch : " + z);
        updateCoverSwitchState(z, false);
    }

    public final void dispatchInputManager(boolean z, int i) {
        try {
            String str = TAG;
            Log.d(str, "dispatchInputManager : switchState = " + z + ", type = " + i);
            SemInputDeviceManager semInputDeviceManager = this.mInputDeviceManager;
            if (semInputDeviceManager != null) {
                semInputDeviceManager.setCoverMode(z, i);
            } else {
                Log.d(str, "InputDeviceManager is null");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public final void updateCoverSwitchState(boolean z, boolean z2) {
        synchronized (this.mCoverStateLock) {
            String str = TAG;
            Log.d(str, "updateCoverSwitchState : switchState = " + z + ", isBoot = " + z2);
            if (Feature.getInstance(this.mContext).isSupportSecureCover()) {
                Log.d(str, "updateCoverSwitchState : return because this cover is secure cover");
                return;
            }
            if (CoverManagerUtils.isBackCover(this.mCoverState)) {
                Log.d(str, "updateCoverSwitchState : return because this cover is back cover");
                return;
            }
            if (!z2 && (z || CoverManagerUtils.needsCPUBoostCover(this.mCoverState.getType()))) {
                CoverManagerUtils.performCPUBoostCover(this.mContext);
            }
            if (!Feature.getInstance(this.mContext).isNfcAuthEnabled() && !Feature.getInstance(this.mContext).isSupportDetectCover()) {
                if (this.mCoverVerifier.updateCoverVerification()) {
                    sendCoverAttachStateLocked(z2, null);
                } else {
                    this.mCoverVerifier.updateCoverPropertiesLocked(this.mCoverState, null);
                }
            }
            boolean z3 = true;
            if (!this.mCoverVerifier.isCoverAttached()) {
                PowerManager powerManager = this.mPowerManager;
                if (z) {
                    z3 = false;
                }
                powerManager.updateCoverState(z3);
                return;
            }
            boolean sendCoverSwitchStateLocked = sendCoverSwitchStateLocked(z, z2, false, true);
            if (z && this.mPowerManager != null && sendCoverSwitchStateLocked) {
                Log.addLogString("CoverManager_", "wake up by cover open");
                this.mPowerManager.semWakeUp(SystemClock.uptimeMillis(), 103, "updateCoverSwitchState");
            }
        }
    }

    public final boolean sendCoverSwitchStateLocked(boolean z, boolean z2, boolean z3, boolean z4) {
        if (!z3 && z == this.mCoverState.getSwitchState()) {
            Log.addLogString("CoverManager_", "cover switch fail because switch state is same");
            return false;
        }
        int type = this.mCoverState.getType();
        if (!CoverServiceManager.verifySystemFeature(this.mContext, type)) {
            Log.w(TAG, "sendCoverSwitchStateLocked : return false because type(" + type + ") is not supported");
            Log.addLogString("CoverManager_", "cover switch fail because type(" + type + ") is not supported");
            return false;
        }
        if (CoverManagerUtils.isBackCover(this.mCoverState)) {
            boolean z5 = this.mIsAttachStateChanged;
            if (!z5) {
                Log.d(TAG, "sendCoverSwitchStateLocked : return because this cover is back cover " + z);
                return false;
            }
            if (z5 && !z) {
                Log.d(TAG, "sendCoverSwitchStateLocked : return because attach state is changed, but switch is false " + z);
                return false;
            }
        } else {
            this.mIsAttachStateChanged = false;
        }
        this.mCoverState.setSwitchState(z);
        if (CoverManagerUtils.isClearCover(this.mCoverState) || type == 11) {
            this.mSleepTokenAcquirer.update(this.mCoverState.switchState, this.mIsCoverAppCovered);
            this.mCoverHideAnimator.cancelHideAnimation();
        }
        if ((CoverManagerUtils.isClearCover(this.mCoverState) || (type == 11 && !this.mStateNotifier.isLcdOffByDisabledByApp())) && z4 && z) {
            if (this.mPowerManager.isInteractive()) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.8
                    @Override // java.lang.Runnable
                    public void run() {
                        CoverManagerServiceImpl.this.mCoverHideAnimator.playCoverHideAnimation(CoverManagerServiceImpl.this.mAnimationStartCallback);
                        synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                            CoverManagerServiceImpl.this.mStateNotifier.updatePowerState(CoverManagerServiceImpl.this.mCoverState.getType(), CoverManagerServiceImpl.this.mCoverState.getSwitchState());
                        }
                    }
                });
            } else {
                this.mStateNotifier.updatePowerState(type, true);
                CoverState cloneCoverStateLocked = cloneCoverStateLocked();
                updateCoverStateToWindowManagerLocked(cloneCoverStateLocked);
                this.mCoverServiceManager.updateCoverState(cloneCoverStateLocked);
                this.mStateNotifier.updateCoverSwitchState(cloneCoverStateLocked, z2);
            }
        } else if (type == 0 || type == 7) {
            CoverState cloneCoverStateLocked2 = cloneCoverStateLocked();
            updateCoverStateToWindowManagerLocked(cloneCoverStateLocked2);
            this.mCoverServiceManager.updateCoverState(cloneCoverStateLocked2);
            this.mStateNotifier.updateCoverSwitchState(cloneCoverStateLocked2, z2);
            this.mStateNotifier.updatePowerState(type, z);
        } else {
            CoverState cloneCoverStateLocked3 = cloneCoverStateLocked();
            updateCoverStateToWindowManagerLocked(cloneCoverStateLocked3);
            this.mCoverServiceManager.updateCoverState(cloneCoverStateLocked3);
            this.mStateNotifier.updateCoverSwitchState(cloneCoverStateLocked3, z2);
            this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.9
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                        CoverManagerServiceImpl.this.mStateNotifier.updatePowerState(CoverManagerServiceImpl.this.mCoverState.getType(), CoverManagerServiceImpl.this.mCoverState.getSwitchState());
                    }
                }
            });
        }
        Log.d(TAG, "sendCoverSwitchStateLocked : switchState = " + this.mCoverState.switchState + ", type = " + this.mCoverState.type + ", color = " + this.mCoverState.color + ", widthPixel = " + this.mCoverState.widthPixel + ", heightPixel = " + this.mCoverState.heightPixel);
        return true;
    }

    public final void initializeCoverController(boolean z, int i, int i2) {
        if (i != 0) {
            if (i != 7) {
                if (i == 11) {
                    if (Feature.getInstance(this.mContext).isSupportNeonCover() && this.mGenericCoverServiceController == null) {
                        this.mGenericCoverServiceController = new GenericCoverServiceController(this.mThread.getLooper(), this.mContext);
                        return;
                    }
                    return;
                }
                if (i != 14) {
                    return;
                }
            }
            if (Feature.getInstance(this.mContext).isNfcAuthEnabled()) {
                if (this.mNfcLedCoverController == null) {
                    initializeLedCoverController();
                }
                BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
                if (baseNfcLedCoverController != null) {
                    baseNfcLedCoverController.updateNfcLedCoverAttachStateLocked(z, i);
                    return;
                }
                return;
            }
            return;
        }
        if (Feature.getInstance(this.mContext).isSupportFlipCover() && this.mGenericCoverServiceController == null) {
            this.mGenericCoverServiceController = new GenericCoverServiceController(this.mThread.getLooper(), this.mContext);
        }
    }

    public final void initializeLedCoverController() {
        int supportNfcLedCoverLevel = Feature.getInstance(this.mContext).getSupportNfcLedCoverLevel();
        if (supportNfcLedCoverLevel == 10) {
            this.mNfcLedCoverController = new NfcLedCoverController(this.mThread.getLooper(), this.mContext);
            return;
        }
        if (supportNfcLedCoverLevel == 20) {
            this.mNfcLedCoverController = new GracefulNfcLedCoverController(this.mThread.getLooper(), this.mContext);
            return;
        }
        if (supportNfcLedCoverLevel == 30 || supportNfcLedCoverLevel == 40 || supportNfcLedCoverLevel == 50 || supportNfcLedCoverLevel == 60 || supportNfcLedCoverLevel == 70 || supportNfcLedCoverLevel == 80 || supportNfcLedCoverLevel == 90 || supportNfcLedCoverLevel == 100) {
            this.mNfcLedCoverController = new DreamyNfcLedCoverController(this.mThread.getLooper(), this.mContext);
            return;
        }
        Log.e(TAG, "initializeLedCoverController : unsupported NfcLedCover level " + supportNfcLedCoverLevel);
    }

    public final int getCoverAttachStateFromInputManager() {
        InputManagerService inputManagerService = getInputManagerService();
        if (inputManagerService != null) {
            try {
                int switchState = inputManagerService.getSwitchState(-1, -256, 26);
                if (switchState > 0) {
                    return 1;
                }
                return switchState == 0 ? 0 : -1;
            } catch (Exception unused) {
                Log.e(TAG, "getCoverAttachStateFromInputManager : Can't get cover attach state");
            }
        } else {
            Log.d(TAG, "getCoverAttachStateFromInputManager : InputManager is null");
        }
        return -1;
    }

    public final int getCoverSwitchStateFromInputManager() {
        InputManagerService inputManagerService = getInputManagerService();
        if (inputManagerService != null) {
            try {
                int switchState = inputManagerService.getSwitchState(-1, -256, 21);
                if (switchState > 0) {
                    return 1;
                }
                return switchState == 0 ? 0 : -1;
            } catch (Exception unused) {
                Log.e(TAG, "getCoverSwitchStateFromInputManager : Can't get cover switch state");
                return -1;
            }
        }
        Log.d(TAG, "getCoverSwitchStateFromInputManager : InputManager is null");
        return -1;
    }

    public CoverState getCoverState() {
        return getCoverStateInternal(true);
    }

    public CoverState getCoverStateForExternal() {
        Log.e(TAG, "deprecated getCoverStateForExternal");
        return null;
    }

    public final CoverState getCoverStateInternal(boolean z) {
        if (!CoverTestModeUtils.isTestMode() && z && Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "getCoverStateInternal : caller is invalid");
            return null;
        }
        return this.mCoverState;
    }

    public boolean getCoverSwitchState() {
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "getCoverSwitchState : caller is invalid");
            return true;
        }
        if (this.mCoverDisabler.isCoverManagerDisabled()) {
            return this.mCoverDisabler.getRealCoverSwitchState();
        }
        return this.mCoverState.getSwitchState();
    }

    public boolean isCoverManagerDisabled() {
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        return this.mCoverDisabler.isCoverManagerDisabled();
    }

    public void disableCoverManager(boolean z, IBinder iBinder, String str) {
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "disableCoverManager : caller is invalid");
            return;
        }
        if (this.mCoverDisabler.disableCoverManager(z, iBinder, str)) {
            boolean isCoverManagerDisabled = this.mCoverDisabler.isCoverManagerDisabled();
            boolean z2 = getCoverSwitchStateFromInputManager() != 1;
            final boolean z3 = isCoverManagerDisabled || z2;
            this.mCoverDisabler.setRealCoverSwitchState(z2);
            Log.addLogString("CoverManager_", "disable CoverManager : " + z + ", pkg : " + str);
            this.mHandler.post(new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.10
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                        CoverManagerServiceImpl.this.sendCoverSwitchStateLocked(z3, false, false, true);
                    }
                }
            });
        }
    }

    public void sendDataToCover(int i, byte[] bArr) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "sendDataToCover : caller is invalid");
        } else if ("com.android.systemui".equals(this.mCoverManagerAllowLists.getPackageForPid(this.mContext, Binder.getCallingPid()))) {
            Log.w(TAG, "sendDataToCover : ignoring call from SystemUI");
        }
    }

    public void sendPowerKeyToCover() {
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        Log.d(TAG, "sendPowerKeyToCover");
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.sendPowerKeyToCover();
        }
        GenericCoverServiceController genericCoverServiceController = this.mGenericCoverServiceController;
        if (genericCoverServiceController != null) {
            genericCoverServiceController.sendPowerKeyToCover();
        }
    }

    public void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "registerNfcTouchListenerCallback : caller is invalid");
            return;
        }
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.registerNfcTouchListenerCallback(i, iBinder, componentName);
        }
        GenericCoverServiceController genericCoverServiceController = this.mGenericCoverServiceController;
        if (genericCoverServiceController != null) {
            genericCoverServiceController.registerNfcTouchListenerCallback(i, iBinder, componentName);
        }
    }

    public boolean unregisterNfcTouchListenerCallback(IBinder iBinder) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "unregisterNfcTouchListenerCallback : caller is invalid");
            return false;
        }
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        boolean unRegisterNfcTouchListenerCallback = baseNfcLedCoverController != null ? baseNfcLedCoverController.unRegisterNfcTouchListenerCallback(iBinder) : false;
        GenericCoverServiceController genericCoverServiceController = this.mGenericCoverServiceController;
        return unRegisterNfcTouchListenerCallback || (genericCoverServiceController != null ? genericCoverServiceController.unRegisterNfcTouchListenerCallback(iBinder) : false);
    }

    public void sendDataToNfcLedCover(int i, byte[] bArr) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "sendStateDataToCover : caller is invalid");
            return;
        }
        if ("com.android.systemui".equals(this.mCoverManagerAllowLists.getPackageForPid(this.mContext, Binder.getCallingPid()))) {
            Log.w(TAG, "sendDataToCover : ignoring call from SystemUI");
            return;
        }
        if (!FactoryTest.isFactoryBinary()) {
            BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
            if (baseNfcLedCoverController != null) {
                baseNfcLedCoverController.sendDataToNfcLedCover(i, bArr);
                return;
            }
            return;
        }
        if (i == -1 || i == -2) {
            if (i == -1) {
                this.mCoverServiceManager.bindCoverService(7, true);
            } else if (i == -2) {
                this.mCoverServiceManager.bindCoverService(14, true);
            }
            if (this.mNfcLedCoverController == null) {
                initializeLedCoverController();
                return;
            }
            return;
        }
        if (this.mNfcLedCoverController == null) {
            initializeLedCoverController();
        }
        BaseNfcLedCoverController baseNfcLedCoverController2 = this.mNfcLedCoverController;
        if (baseNfcLedCoverController2 != null) {
            baseNfcLedCoverController2.sendDataToNfcLedCover(i, bArr);
        }
    }

    public void addLedNotification(Bundle bundle) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "addLedNotification : caller is invalid");
            return;
        }
        Log.d(TAG, "addLedNotification");
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.addLedNotification(bundle);
        }
    }

    public void removeLedNotification(Bundle bundle) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "removeLedNotification : caller is invalid");
            return;
        }
        Log.d(TAG, "removeLedNotification");
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.removeLedNotification(bundle);
        }
    }

    public void sendSystemEvent(Bundle bundle) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "sendSystemEvent : caller is invalid");
            return;
        }
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.sendSystemEvent(bundle);
        }
    }

    public boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "disableLcdOffByCover : caller is invalid");
            return false;
        }
        Log.addLogString("CoverManager_", "disable LCD OFF : " + componentName);
        boolean disableLcdOffByCover = this.mStateNotifier.disableLcdOffByCover(iBinder, componentName);
        if (disableLcdOffByCover) {
            BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
            if (baseNfcLedCoverController != null) {
                baseNfcLedCoverController.setLcdOffDisabledByCover(true);
                this.mStateNotifier.registerLcdOffDisabledByAppListener(this);
            }
            GenericCoverServiceController genericCoverServiceController = this.mGenericCoverServiceController;
            if (genericCoverServiceController != null) {
                genericCoverServiceController.setLcdOffDisabledByCover(true);
                this.mStateNotifier.registerLcdOffDisabledByAppListener(this);
            }
        }
        return disableLcdOffByCover;
    }

    public boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "enableLcdOffByCover : caller is invalid");
            return false;
        }
        Log.addLogString("CoverManager_", "enable LCD OFF : " + componentName);
        return this.mStateNotifier.enableLcdOffByCover(iBinder, componentName);
    }

    public boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "requestCoverAuthentication : caller is invalid");
            return false;
        }
        long nanoTime = System.nanoTime();
        Log.d(TAG, "requestCoverAuthentication : whenNanos=" + nanoTime);
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        return baseNfcLedCoverController != null && baseNfcLedCoverController.requestCoverAuthentication(nanoTime, iBinder, componentName);
    }

    public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid() && !this.mCoverManagerAllowLists.isAllowedToUse(this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(TAG, "setFotaInProgress : caller is invalid");
            return false;
        }
        Log.d(TAG, "setFotaInProgress : inProgress = " + z);
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        return baseNfcLedCoverController != null && baseNfcLedCoverController.setFotaInProgress(z, iBinder, componentName);
    }

    public int onCoverAppCovered(boolean z) {
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        Log.d(TAG, "onCoverAppCovered : " + z);
        this.mIsCoverAppCovered = z;
        this.mSleepTokenAcquirer.update(this.mCoverState.switchState, z);
        if (this.mCoverServiceManager.isBindingCoverService()) {
            return this.mCoverServiceManager.onCoverAppStateChanged(z);
        }
        return this.mStateNotifier.onCoverAppStateChanged(z);
    }

    @Override // com.android.server.sepunion.cover.StateNotifier.LcdOffDisabledByAppListener
    public void onLcdOffByCoverEnabled() {
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.setLcdOffDisabledByCover(false);
            this.mStateNotifier.unregisterLcdOffDisabledByAppListener(this);
        }
        GenericCoverServiceController genericCoverServiceController = this.mGenericCoverServiceController;
        if (genericCoverServiceController != null) {
            genericCoverServiceController.setLcdOffDisabledByCover(false);
            this.mStateNotifier.unregisterLcdOffDisabledByAppListener(this);
        }
    }

    public final void updateCoverStateToWindowManagerLocked(CoverState coverState) {
        WindowManagerService windowManagerService = getWindowManagerService();
        if (windowManagerService != null) {
            windowManagerService.mExt.updateCoverState(coverState);
        } else {
            Log.e(TAG, "updateCoverStateToWindowManagerLocked : wms is null");
        }
    }

    public final void updateCoverWindowSize() {
        synchronized (this.mCoverStateLock) {
            this.mCoverVerifier.initializeDefaultCoverState();
            int type = this.mCoverState.getType();
            if (type == 7 || type == 8 || type == 11) {
                Log.d(TAG, "updateCoverWindowSize: updating cover window for type: " + this.mCoverState.getType());
                this.mCoverVerifier.updateCoverWindowSize(this.mCoverState);
                CoverState cloneCoverStateLocked = cloneCoverStateLocked();
                updateCoverStateToWindowManagerLocked(cloneCoverStateLocked);
                this.mCoverServiceManager.updateCoverState(cloneCoverStateLocked);
                this.mStateNotifier.updateCoverSwitchState(cloneCoverStateLocked, false);
            } else {
                Log.d(TAG, "updateCoverWindowSize: no need to update cover window for type: " + this.mCoverState.getType());
            }
        }
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.OnCoverStateProvider
    public CoverState getCoverStateFromCoverServiceManager() {
        CoverState cloneCoverStateLocked;
        synchronized (this.mCoverStateLock) {
            cloneCoverStateLocked = cloneCoverStateLocked();
        }
        return cloneCoverStateLocked;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0111, code lost:
    
        if (r11.equals("l") == false) goto L43;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        int i2;
        int i3;
        if (DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            if (!CoverTestModeUtils.SHIPPED && strArr != null && strArr.length > 0) {
                int i4 = 0;
                String str = strArr[0];
                char c = 4;
                if ("on".equals(str) || "off".equals(str)) {
                    boolean equals = "on".equals(str);
                    if (equals && strArr.length > 1) {
                        String str2 = strArr[1];
                        str2.hashCode();
                        switch (str2.hashCode()) {
                            case 98:
                                if (str2.equals("b")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 99:
                                if (str2.equals("c")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 102:
                                if (str2.equals(KnoxAnalyticsDataConverter.FEATURE)) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 103:
                                if (str2.equals("g")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 108:
                                break;
                            case 109:
                                if (str2.equals("m")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 110:
                                if (str2.equals("n")) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 3168:
                                if (str2.equals("cc")) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 3184:
                                if (str2.equals("cs")) {
                                    c = '\b';
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 14)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(14);
                                    printWriter.println("TEST MODE: LED BACK COVER " + str);
                                    return;
                                }
                                break;
                            case 1:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 8)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(8);
                                    printWriter.println("TEST MODE: CLEAR_COVER " + str);
                                    return;
                                }
                                break;
                            case 2:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 0)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(0);
                                    printWriter.println("TEST MODE: FLIP_COVER " + str);
                                    return;
                                }
                                break;
                            case 3:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 13)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(13);
                                    printWriter.println("TEST MODE: GAMEPACK_COVER " + str);
                                    return;
                                }
                                break;
                            case 4:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 7)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(7);
                                    printWriter.println("TEST MODE: LED_COVER " + str);
                                    return;
                                }
                                break;
                            case 5:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 16)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(16);
                                    printWriter.println("TEST MODE: MINI SVIEW WALLET COVER " + str);
                                    return;
                                }
                                break;
                            case 6:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 11)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(11);
                                    printWriter.println("TEST MODE: NEON_COVER " + str);
                                    return;
                                }
                                break;
                            case 7:
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 17)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(17);
                                    printWriter.println("TEST MODE: CLEAR CAMERA VIEW COVER " + str);
                                    return;
                                }
                                break;
                            case '\b':
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 15)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(15);
                                    printWriter.println("TEST MODE: CLEAR SIDE VIEW COVER " + str);
                                    return;
                                }
                                break;
                        }
                    } else if (!equals) {
                        this.mCoverTestModeUtils.setTestModeToSetting(-1);
                        printWriter.println("TEST MODE: " + str);
                        return;
                    }
                    printCoverDebugModeGuide(printWriter);
                    return;
                }
                if ("open".equals(str) || "close".equals(str)) {
                    if (!CoverTestModeUtils.isTestMode()) {
                        printWriter.println("NOT TEST MODE");
                        return;
                    }
                    boolean z = !"close".equals(str);
                    StringBuilder sb = new StringBuilder();
                    sb.append("TEST MODE: ");
                    sb.append(z ? "OPEN" : "CLOSE");
                    printWriter.println(sb.toString());
                    notifyCoverSwitchStateChanged(System.nanoTime(), z);
                    return;
                }
                if ("vr".equals(str)) {
                    try {
                        int parseInt = Integer.parseInt(strArr[1]);
                        i2 = Integer.parseInt(strArr[2]);
                        i3 = Integer.parseInt(strArr[3]);
                        i4 = parseInt;
                        i = Integer.parseInt(strArr[4]);
                    } catch (Exception unused) {
                        i = 0;
                        i2 = 0;
                        i3 = 0;
                    }
                    Rect rect = new Rect(i4, i2, i3, i);
                    this.mCoverTestModeUtils.setTestVisibleRectToSetting(rect);
                    printWriter.println("TEST MODE: CLEAR VIEW COVER VISIBLE AREA " + rect);
                    return;
                }
            }
            printWriter.println("COVER MANAGER SERVICE (dumpsys cover)");
            printWriter.println(" Current Cover state");
            synchronized (this.mCoverStateLock) {
                printWriter.println("  " + this.mCoverState);
                printWriter.println("  ");
                this.mCoverVerifier.dump(fileDescriptor, printWriter, strArr);
            }
            Feature.getInstance(this.mContext).dump(fileDescriptor, printWriter, strArr);
            this.mCoverServiceManager.dump(fileDescriptor, printWriter, strArr);
            this.mStateNotifier.dump(fileDescriptor, printWriter, strArr);
            this.mCoverDisabler.dump(fileDescriptor, printWriter, strArr);
            BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
            if (baseNfcLedCoverController != null) {
                baseNfcLedCoverController.dump(fileDescriptor, printWriter, strArr);
            }
            GenericCoverServiceController genericCoverServiceController = this.mGenericCoverServiceController;
            if (genericCoverServiceController != null) {
                genericCoverServiceController.dump(fileDescriptor, printWriter, strArr);
            }
            if (this.mAutoScreenOn.support()) {
                this.mAutoScreenOn.dump(fileDescriptor, printWriter, strArr);
            }
            this.mCoverTestModeUtils.dump(fileDescriptor, printWriter, strArr);
            Log.dump("CoverManager_", fileDescriptor, printWriter, strArr);
            SAccessoryManagerInternal sAccessoryManagerInternal = (SAccessoryManagerInternal) LocalServices.getService(SAccessoryManagerInternal.class);
            if (sAccessoryManagerInternal != null) {
                sAccessoryManagerInternal.dump(fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void printCoverDebugModeGuide(PrintWriter printWriter) {
        printWriter.println("Cover Test Mode options:");
        printWriter.println("  [cmd] [type]");
        printWriter.println("  cmd may be one of:");
        printWriter.println("    on: cover attached");
        printWriter.println("    off: cover detached");
        printWriter.println("    open: cover opened");
        printWriter.println("    close: cover closed");
        printWriter.println("    vr: set clear view visible rect");
        printWriter.println("  type may be one of:");
        printWriter.println("    f[lip cover]: flip cover");
        printWriter.println("    s[view cover]: sview cover");
        printWriter.println("    c[lear cover]: clear cover");
        printWriter.println("    l[ed cover]: led cover");
        printWriter.println("    n[eon cover]: neon cover");
        printWriter.println("    g[amepack cover]: gamepack cover");
        printWriter.println("    b: led back cover");
        printWriter.println("    cs: clear side view cover");
        printWriter.println("    m: mini sview wallet cover");
        printWriter.println("    cc: clear camera view cover");
    }

    public final class CoverManagerHandler extends Handler {
        public CoverManagerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 300) {
                return;
            }
            CoverManagerServiceImpl.this.updateCoverWindowSize();
        }
    }
}
