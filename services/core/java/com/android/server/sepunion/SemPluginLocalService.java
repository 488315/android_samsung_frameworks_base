package com.android.server.sepunion;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import com.android.server.sepunion.cover.BaseNfcLedCoverController;
import com.android.server.sepunion.cover.CoverDisabler;
import com.android.server.sepunion.cover.CoverManagerAllowLists;
import com.android.server.sepunion.cover.CoverManagerServiceImpl;
import com.android.server.sepunion.cover.CoverServiceManager;
import com.android.server.sepunion.cover.CoverTestModeUtils;
import com.android.server.sepunion.cover.GenericCoverServiceController;
import com.android.server.sepunion.cover.SleepTokenAcquirer;
import com.android.server.sepunion.cover.SleepTokenAcquirer$$ExternalSyntheticLambda0;
import com.android.server.sepunion.cover.StateNotifier;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverWindowStateListenerCallback;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemPluginManagerLocal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemPluginLocalService extends SemPluginManagerLocal {
    public CoverManagerServiceImpl mCoverManagerServiceImpl = getCoverManagerServiceImpl();

    public SemPluginLocalService(Context context) {
    }

    public final void addLedNotification(Bundle bundle) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (Binder.getCallingUid() != Process.myUid()) {
            if (!coverManagerServiceImpl.mCoverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
                Log.w("CoverManager_CoverManagerServiceImpl", "addLedNotification : caller is invalid");
                return;
            }
        }
        Log.d("CoverManager_CoverManagerServiceImpl", "addLedNotification");
        BaseNfcLedCoverController baseNfcLedCoverController = coverManagerServiceImpl.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.addLedNotification(bundle);
        }
    }

    public final void disableCoverManager(boolean z, IBinder iBinder, String str) {
        boolean disableCoverManagerLocked;
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        final CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid() && !coverManagerServiceImpl.mCoverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w("CoverManager_CoverManagerServiceImpl", "disableCoverManager : caller is invalid");
            return;
        }
        CoverDisabler coverDisabler = coverManagerServiceImpl.mCoverDisabler;
        synchronized (coverDisabler.mLock) {
            disableCoverManagerLocked = coverDisabler.disableCoverManagerLocked(z, iBinder, str);
        }
        if (disableCoverManagerLocked) {
            boolean z2 = coverManagerServiceImpl.mCoverDisabler.mCoverManagerDisabled;
            boolean z3 = coverManagerServiceImpl.getCoverSwitchStateFromInputManager() != 1;
            final boolean z4 = z2 || z3;
            coverManagerServiceImpl.mCoverDisabler.mRealCoverSwitchState = z3;
            Log.addLogString("CoverManager_", "disable CoverManager : " + z + ", pkg : " + str);
            coverManagerServiceImpl.mHandler.post(new Runnable() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.10
                public final /* synthetic */ boolean val$coverSwitchState;

                public AnonymousClass10(final boolean z42) {
                    r2 = z42;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                        CoverManagerServiceImpl.this.sendCoverSwitchStateLocked(r2, false, false, true);
                    }
                }
            });
        }
    }

    public final boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        try {
            return this.mCoverManagerServiceImpl.disableLcdOffByCover(iBinder, componentName);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, String str) {
        if (str == null || !str.equals("cover")) {
            return;
        }
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        if (coverManagerServiceImpl != null) {
            coverManagerServiceImpl.dump(fileDescriptor, printWriter, strArr);
        }
    }

    public final boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        try {
            return this.mCoverManagerServiceImpl.enableLcdOffByCover(iBinder, componentName);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final CoverManagerServiceImpl getCoverManagerServiceImpl() {
        SemPluginManagerService semPluginManagerService = (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService();
        if (semPluginManagerService != null) {
            return semPluginManagerService.mCoverManagerServiceImpl;
        }
        return null;
    }

    public final CoverState getCoverState() {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        return this.mCoverManagerServiceImpl.getCoverState();
    }

    public final CoverState getCoverStateForExternal() {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        this.mCoverManagerServiceImpl.getClass();
        Log.e("CoverManager_CoverManagerServiceImpl", "deprecated getCoverStateForExternal");
        return null;
    }

    public final boolean getCoverSwitchState() {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            if (!coverManagerServiceImpl.mCoverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
                Log.w("CoverManager_CoverManagerServiceImpl", "getCoverSwitchState : caller is invalid");
                return true;
            }
        }
        CoverDisabler coverDisabler = coverManagerServiceImpl.mCoverDisabler;
        return coverDisabler.mCoverManagerDisabled ? coverDisabler.mRealCoverSwitchState : coverManagerServiceImpl.mCoverState.getSwitchState();
    }

    public final int getVersion() {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        this.mCoverManagerServiceImpl.getClass();
        return R.interpolator.accelerate_quad;
    }

    public final boolean isCoverManagerDisabled() {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (CoverTestModeUtils.isTestMode() || Binder.getCallingUid() == Process.myUid()) {
            return coverManagerServiceImpl.mCoverDisabler.mCoverManagerDisabled;
        }
        throw new SecurityException("Caller is not SYSTEM_PROCESS");
    }

    public final int onCoverAppCovered(boolean z) {
        int i;
        boolean z2;
        Iterator it;
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        Log.d("CoverManager_CoverManagerServiceImpl", "onCoverAppCovered : " + z);
        coverManagerServiceImpl.mIsCoverAppCovered = z;
        SleepTokenAcquirer sleepTokenAcquirer = coverManagerServiceImpl.mSleepTokenAcquirer;
        sleepTokenAcquirer.mSwitchState = coverManagerServiceImpl.mCoverState.switchState;
        sleepTokenAcquirer.mIsCoverAppCovered = z;
        Handler handler = sleepTokenAcquirer.mHandler;
        SleepTokenAcquirer$$ExternalSyntheticLambda0 sleepTokenAcquirer$$ExternalSyntheticLambda0 = sleepTokenAcquirer.mSleepTokenTask;
        handler.removeCallbacks(sleepTokenAcquirer$$ExternalSyntheticLambda0);
        handler.post(sleepTokenAcquirer$$ExternalSyntheticLambda0);
        CoverServiceManager coverServiceManager = coverManagerServiceImpl.mCoverServiceManager;
        synchronized (coverServiceManager.mLock) {
            i = 0;
            z2 = coverServiceManager.mActiveServices.size() > 0;
        }
        if (z2) {
            CoverServiceManager coverServiceManager2 = coverManagerServiceImpl.mCoverServiceManager;
            synchronized (coverServiceManager2.mLock) {
                try {
                    if (coverServiceManager2.mActiveServices.size() != 0) {
                        Iterator it2 = coverServiceManager2.mActiveServices.iterator();
                        while (it2.hasNext()) {
                            CoverServiceManager.CoverServiceInfo coverServiceInfo = (CoverServiceManager.CoverServiceInfo) it2.next();
                            if (CoverServiceManager.SYSTEM_UI_COVER.equals(coverServiceInfo.component)) {
                                i = coverServiceInfo.onCoverAppStateChanged(z);
                            } else {
                                coverServiceInfo.onCoverAppStateChanged(z);
                            }
                        }
                    }
                } finally {
                }
            }
        } else {
            StateNotifier stateNotifier = coverManagerServiceImpl.mStateNotifier;
            synchronized (stateNotifier.mListeners) {
                try {
                    it = stateNotifier.mListeners.iterator();
                } catch (RemoteException e) {
                    Log.e("CoverManager_StateNotifier", "Failed onCoverAppCovered callback", e);
                } finally {
                }
                while (it.hasNext()) {
                    StateNotifier.CoverStateListenerInfo coverStateListenerInfo = (StateNotifier.CoverStateListenerInfo) it.next();
                    IBinder iBinder = coverStateListenerInfo.token;
                    if (iBinder == null) {
                        Log.w("CoverManager_StateNotifier", "onCoverAppStateChanged : token is null");
                    } else if ((coverStateListenerInfo.type & 4) != 0) {
                        ICoverWindowStateListenerCallback asInterface = ICoverWindowStateListenerCallback.Stub.asInterface(iBinder);
                        if (asInterface != null) {
                            asInterface.onCoverAppCovered(z);
                        }
                    }
                }
            }
        }
        return i;
    }

    public final void registerCallback(IBinder iBinder, ComponentName componentName) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        this.mCoverManagerServiceImpl.registerListenerCallbackInternal(1, iBinder, componentName);
    }

    public final void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        this.mCoverManagerServiceImpl.registerListenerCallbackInternal(i, iBinder, componentName);
    }

    public final void registerListenerCallbackForExternal(IBinder iBinder, ComponentName componentName, int i) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        Log.e("CoverManager_CoverManagerServiceImpl", "deprecated registerListenerCallbackForExternal " + this.mCoverManagerServiceImpl.mPackageManager.getNameForUid(Binder.getCallingUid()));
    }

    public final void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        try {
            this.mCoverManagerServiceImpl.registerNfcTouchListenerCallback(i, iBinder, componentName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void removeLedNotification(Bundle bundle) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (Binder.getCallingUid() != Process.myUid()) {
            if (!coverManagerServiceImpl.mCoverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
                Log.w("CoverManager_CoverManagerServiceImpl", "removeLedNotification : caller is invalid");
                return;
            }
        }
        Log.d("CoverManager_CoverManagerServiceImpl", "removeLedNotification");
        BaseNfcLedCoverController baseNfcLedCoverController = coverManagerServiceImpl.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.removeLedNotification(bundle);
        }
    }

    public final boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        try {
            return this.mCoverManagerServiceImpl.requestCoverAuthentication(iBinder, componentName);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void sendDataToCover(int i, byte[] bArr) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        int callingUid = Binder.getCallingUid();
        int myUid = Process.myUid();
        CoverManagerAllowLists coverManagerAllowLists = coverManagerServiceImpl.mCoverManagerAllowLists;
        if (callingUid != myUid && !coverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w("CoverManager_CoverManagerServiceImpl", "sendDataToCover : caller is invalid");
            return;
        }
        Context context = coverManagerServiceImpl.mContext;
        int callingPid = Binder.getCallingPid();
        coverManagerAllowLists.getClass();
        if (Constants.SYSTEMUI_PACKAGE_NAME.equals(CoverManagerAllowLists.getPackageForPid(context, callingPid))) {
            Log.w("CoverManager_CoverManagerServiceImpl", "sendDataToCover : ignoring call from SystemUI");
        }
    }

    public final void sendDataToNfcLedCover(int i, byte[] bArr) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        int callingUid = Binder.getCallingUid();
        int myUid = Process.myUid();
        CoverManagerAllowLists coverManagerAllowLists = coverManagerServiceImpl.mCoverManagerAllowLists;
        if (callingUid != myUid && !coverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w("CoverManager_CoverManagerServiceImpl", "sendStateDataToCover : caller is invalid");
            return;
        }
        Context context = coverManagerServiceImpl.mContext;
        int callingPid = Binder.getCallingPid();
        coverManagerAllowLists.getClass();
        if (Constants.SYSTEMUI_PACKAGE_NAME.equals(CoverManagerAllowLists.getPackageForPid(context, callingPid))) {
            Log.w("CoverManager_CoverManagerServiceImpl", "sendDataToCover : ignoring call from SystemUI");
            return;
        }
        if (!FactoryTest.isFactoryBinary()) {
            BaseNfcLedCoverController baseNfcLedCoverController = coverManagerServiceImpl.mNfcLedCoverController;
            if (baseNfcLedCoverController != null) {
                baseNfcLedCoverController.sendDataToNfcLedCover(i, bArr);
                return;
            }
            return;
        }
        if (i != -1 && i != -2) {
            if (coverManagerServiceImpl.mNfcLedCoverController == null) {
                coverManagerServiceImpl.initializeLedCoverController();
            }
            BaseNfcLedCoverController baseNfcLedCoverController2 = coverManagerServiceImpl.mNfcLedCoverController;
            if (baseNfcLedCoverController2 != null) {
                baseNfcLedCoverController2.sendDataToNfcLedCover(i, bArr);
                return;
            }
            return;
        }
        CoverServiceManager coverServiceManager = coverManagerServiceImpl.mCoverServiceManager;
        if (i == -1) {
            coverServiceManager.bindCoverService(7, true);
        } else if (i == -2) {
            coverServiceManager.bindCoverService(14, true);
        }
        if (coverManagerServiceImpl.mNfcLedCoverController == null) {
            coverManagerServiceImpl.initializeLedCoverController();
        }
    }

    public final void sendPowerKeyToCover() {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        Log.d("CoverManager_CoverManagerServiceImpl", "sendPowerKeyToCover");
        BaseNfcLedCoverController baseNfcLedCoverController = coverManagerServiceImpl.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.sendPowerKeyToCover();
        }
        GenericCoverServiceController genericCoverServiceController = coverManagerServiceImpl.mGenericCoverServiceController;
        if (genericCoverServiceController != null) {
            Log.d("CoverManager_GenericCoverServiceController", "sendPowerKeyToCover");
            PowerManager.WakeLock wakeLock = genericCoverServiceController.mSendPowerKeyWakeLock;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!wakeLock.isHeld()) {
                    wakeLock.acquire();
                }
            } catch (IllegalStateException e) {
                Log.e("CoverManager_GenericCoverServiceController", "Shouldn't happen", e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            genericCoverServiceController.mHandler.obtainMessage(0).sendToTarget();
        }
    }

    public final void sendSystemEvent(Bundle bundle) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (Binder.getCallingUid() != Process.myUid()) {
            if (!coverManagerServiceImpl.mCoverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
                Log.w("CoverManager_CoverManagerServiceImpl", "sendSystemEvent : caller is invalid");
                return;
            }
        }
        BaseNfcLedCoverController baseNfcLedCoverController = coverManagerServiceImpl.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.sendSystemEvent(bundle);
        }
    }

    public final boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        try {
            return this.mCoverManagerServiceImpl.setFotaInProgress(z, iBinder, componentName);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean unregisterCallback(IBinder iBinder) {
        IBinder.DeathRecipient deathRecipient;
        StateNotifier.CoverStateListenerInfo coverStateListenerInfo;
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (Binder.getCallingUid() != Process.myUid() && !coverManagerServiceImpl.mCoverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            return false;
        }
        StateNotifier stateNotifier = coverManagerServiceImpl.mStateNotifier;
        synchronized (stateNotifier.mHighPriorityListeners) {
            try {
                Iterator it = stateNotifier.mHighPriorityListeners.iterator();
                while (true) {
                    deathRecipient = null;
                    if (!it.hasNext()) {
                        coverStateListenerInfo = null;
                        break;
                    }
                    coverStateListenerInfo = (StateNotifier.CoverStateListenerInfo) it.next();
                    if (coverStateListenerInfo != null && iBinder.equals(coverStateListenerInfo.token)) {
                        break;
                    }
                }
                if (coverStateListenerInfo != null) {
                    ArrayList arrayList = stateNotifier.mHighPriorityListeners;
                    if (!arrayList.isEmpty()) {
                        arrayList.remove(coverStateListenerInfo);
                    }
                    iBinder.unlinkToDeath(coverStateListenerInfo, 0);
                    arrayList.notify();
                } else {
                    synchronized (stateNotifier.mListeners) {
                        try {
                            Iterator it2 = stateNotifier.mListeners.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                StateNotifier.CoverStateListenerInfo coverStateListenerInfo2 = (StateNotifier.CoverStateListenerInfo) it2.next();
                                if (coverStateListenerInfo2 != null && iBinder.equals(coverStateListenerInfo2.token)) {
                                    deathRecipient = coverStateListenerInfo2;
                                    break;
                                }
                            }
                            if (deathRecipient == null) {
                                return false;
                            }
                            ArrayList arrayList2 = stateNotifier.mListeners;
                            if (!arrayList2.isEmpty()) {
                                arrayList2.remove(deathRecipient);
                            }
                            iBinder.unlinkToDeath(deathRecipient, 0);
                            arrayList2.notify();
                        } finally {
                        }
                    }
                }
            } finally {
            }
        }
        return true;
    }

    public final boolean unregisterCallbackForExternal(IBinder iBinder) {
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        Log.e("CoverManager_CoverManagerServiceImpl", "deprecated unregisterCallbackForExternal " + this.mCoverManagerServiceImpl.mPackageManager.getNameForUid(Binder.getCallingUid()));
        return false;
    }

    public final boolean unregisterNfcTouchListenerCallback(IBinder iBinder) {
        boolean z;
        if (this.mCoverManagerServiceImpl == null) {
            this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
        }
        CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
        coverManagerServiceImpl.getClass();
        if (Binder.getCallingUid() != Process.myUid() && !coverManagerServiceImpl.mCoverManagerAllowLists.isAllowedToUse(coverManagerServiceImpl.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w("CoverManager_CoverManagerServiceImpl", "unregisterNfcTouchListenerCallback : caller is invalid");
            return false;
        }
        BaseNfcLedCoverController baseNfcLedCoverController = coverManagerServiceImpl.mNfcLedCoverController;
        boolean unRegisterNfcTouchListenerCallback = baseNfcLedCoverController != null ? baseNfcLedCoverController.unRegisterNfcTouchListenerCallback(iBinder) : false;
        GenericCoverServiceController genericCoverServiceController = coverManagerServiceImpl.mGenericCoverServiceController;
        if (genericCoverServiceController != null) {
            Log.d("CoverManager_GenericCoverServiceController", "unRegisterNfcTouchListenerCallback: binder = " + iBinder + ", pid : " + Binder.getCallingPid() + ", uid : " + Binder.getCallingUid());
            synchronized (genericCoverServiceController.mListeners) {
                try {
                    Iterator it = genericCoverServiceController.mListeners.iterator();
                    while (it.hasNext()) {
                        GenericCoverServiceController.GenericPressEventListenerInfo genericPressEventListenerInfo = (GenericCoverServiceController.GenericPressEventListenerInfo) it.next();
                        if (genericPressEventListenerInfo != null && iBinder.equals(genericPressEventListenerInfo.token)) {
                            Log.e("CoverManager_GenericCoverServiceController", "remove listener: " + genericPressEventListenerInfo.pid);
                            genericCoverServiceController.mListeners.remove(genericPressEventListenerInfo);
                            iBinder.unlinkToDeath(genericPressEventListenerInfo, 0);
                            z = true;
                            break;
                        }
                    }
                    Log.e("CoverManager_GenericCoverServiceController", "UnregisterNfcTouchListener: listener does not exist");
                } finally {
                }
            }
            return !unRegisterNfcTouchListenerCallback || z;
        }
        z = false;
        if (unRegisterNfcTouchListenerCallback) {
        }
    }
}
