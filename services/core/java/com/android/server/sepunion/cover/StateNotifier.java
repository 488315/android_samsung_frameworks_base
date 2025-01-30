package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManagerCallback;
import com.samsung.android.cover.ICoverStateListenerCallback;
import com.samsung.android.cover.ICoverWindowStateListenerCallback;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class StateNotifier {
    public static final String TAG = "CoverManager_" + StateNotifier.class.getSimpleName();
    public Context mContext;
    public final HandlerC2566H mHandler;
    public boolean mLcdOffDisabledByApp;
    public LcdOffDisabledByAppListener mLcdOffDisabledByAppListener;
    public final PowerManager mPowerManager;
    public PowerManager.WakeLock mSendCoverAttachStateWakeLock;
    public PowerManager.WakeLock mSendCoverSwitchStateWakeLock;
    public final ArrayList mHighPriorityListeners = new ArrayList();
    public final ArrayList mListeners = new ArrayList();
    public final ArrayList mLcdOffDisableListeners = new ArrayList();
    public int mCoverType = 2;
    public boolean mCoverOpen = false;
    public boolean mBootComplete = false;
    public Runnable mGoToSleepRunnable = null;

    public interface LcdOffDisabledByAppListener {
        void onLcdOffByCoverEnabled();
    }

    public StateNotifier(Looper looper, Context context) {
        this.mContext = context;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mHandler = new HandlerC2566H(looper);
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "CoverSwitchState");
        this.mSendCoverSwitchStateWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = powerManager.newWakeLock(1, "CoverAttachState");
        this.mSendCoverAttachStateWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
    }

    /* renamed from: com.android.server.sepunion.cover.StateNotifier$H */
    public final class HandlerC2566H extends Handler {
        public HandlerC2566H(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                StateNotifier.this.handleSendCoverSwitchState((CoverState) message.obj, message.arg1 == 1);
            } else {
                if (i != 1) {
                    return;
                }
                StateNotifier.this.handleSendCoverAttachState(message.arg1 == 1);
            }
        }
    }

    public class CoverStateListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int type;
        public final int uid;

        public CoverStateListenerInfo(IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
            this.type = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.v(StateNotifier.TAG, "binderDied : binder = " + this.token);
            synchronized (StateNotifier.this.mListeners) {
                StateNotifier.this.mListeners.remove(this);
            }
            synchronized (StateNotifier.this.mHighPriorityListeners) {
                StateNotifier.this.mHighPriorityListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public void onCoverSwitchStateChanged(CoverState coverState) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w(StateNotifier.TAG, "onCoverSwitchStateChanged : token is null");
                return;
            }
            if (this.type == 1) {
                try {
                    ICoverManagerCallback asInterface = ICoverManagerCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        asInterface.coverCallback(coverState);
                    }
                } catch (RemoteException e) {
                    Log.e(StateNotifier.TAG, "Failed onCoverStateChanged coverCallback", e);
                }
            }
        }

        public void onCoverSwitchStateChanged(boolean z) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w(StateNotifier.TAG, "onCoverSwitchStateChanged : token is null");
                return;
            }
            if ((this.type & 2) != 0) {
                try {
                    ICoverStateListenerCallback asInterface = ICoverStateListenerCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        asInterface.onCoverSwitchStateChanged(z);
                    }
                } catch (RemoteException e) {
                    Log.e(StateNotifier.TAG, "Failed onCoverSwitchStateChanged callback", e);
                }
            }
        }

        public void onCoverAttachStateChanged(boolean z) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w(StateNotifier.TAG, "onCoverAttachStateChanged : token is null");
                return;
            }
            if ((this.type & 2) != 0) {
                try {
                    ICoverStateListenerCallback asInterface = ICoverStateListenerCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        asInterface.onCoverAttachStateChanged(z);
                    }
                } catch (RemoteException e) {
                    Log.e(StateNotifier.TAG, "Failed onCoverAttachStateChanged callback", e);
                }
            }
        }

        public void onCoverAppStateChanged(boolean z) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w(StateNotifier.TAG, "onCoverAppStateChanged : token is null");
                return;
            }
            if ((this.type & 4) != 0) {
                try {
                    ICoverWindowStateListenerCallback asInterface = ICoverWindowStateListenerCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        asInterface.onCoverAppCovered(z);
                    }
                } catch (RemoteException e) {
                    Log.e(StateNotifier.TAG, "Failed onCoverAppCovered callback", e);
                }
            }
        }
    }

    public void onBootComplete() {
        if (this.mBootComplete) {
            return;
        }
        Log.w(TAG, "onBootComplete");
        this.mBootComplete = true;
        Runnable runnable = this.mGoToSleepRunnable;
        if (runnable != null) {
            runnable.run();
            this.mGoToSleepRunnable = null;
        }
    }

    public void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) {
        if (componentName != null && "com.samsung.android.incallui".equals(componentName.getPackageName())) {
            synchronized (this.mHighPriorityListeners) {
                addListenerToListLocked(iBinder, componentName, i, this.mHighPriorityListeners);
            }
        } else {
            synchronized (this.mListeners) {
                addListenerToListLocked(iBinder, componentName, i, this.mListeners);
            }
        }
    }

    public final void addListenerToListLocked(IBinder iBinder, ComponentName componentName, int i, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CoverStateListenerInfo coverStateListenerInfo = (CoverStateListenerInfo) it.next();
            if (coverStateListenerInfo != null && iBinder.equals(coverStateListenerInfo.token)) {
                Log.e(TAG, "addListenerToListLocked : duplicated listener handle");
                return;
            }
        }
        CoverStateListenerInfo coverStateListenerInfo2 = new CoverStateListenerInfo(iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid(), i);
        try {
            iBinder.linkToDeath(coverStateListenerInfo2, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        list.add(coverStateListenerInfo2);
    }

    public boolean unregisterCallback(IBinder iBinder) {
        synchronized (this.mHighPriorityListeners) {
            CoverStateListenerInfo findListenerOnListLocked = findListenerOnListLocked(iBinder, this.mHighPriorityListeners);
            if (findListenerOnListLocked != null) {
                return removeListenerFromListLocked(iBinder, findListenerOnListLocked, this.mHighPriorityListeners);
            }
            synchronized (this.mListeners) {
                CoverStateListenerInfo findListenerOnListLocked2 = findListenerOnListLocked(iBinder, this.mListeners);
                if (findListenerOnListLocked2 == null) {
                    return false;
                }
                return removeListenerFromListLocked(iBinder, findListenerOnListLocked2, this.mListeners);
            }
        }
    }

    public final CoverStateListenerInfo findListenerOnListLocked(IBinder iBinder, ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CoverStateListenerInfo coverStateListenerInfo = (CoverStateListenerInfo) it.next();
            if (coverStateListenerInfo != null && iBinder.equals(coverStateListenerInfo.token)) {
                return coverStateListenerInfo;
            }
        }
        return null;
    }

    public final boolean removeListenerFromListLocked(IBinder iBinder, CoverStateListenerInfo coverStateListenerInfo, ArrayList arrayList) {
        if (coverStateListenerInfo == null) {
            Log.e(TAG, "removeListenerFromListLocked : listener is null");
            return false;
        }
        if (!arrayList.isEmpty()) {
            arrayList.remove(coverStateListenerInfo);
        }
        iBinder.unlinkToDeath(coverStateListenerInfo, 0);
        arrayList.notify();
        return true;
    }

    public void updateCoverSwitchState(CoverState coverState, boolean z) {
        this.mCoverOpen = coverState.getSwitchState();
        synchronized (this.mHighPriorityListeners) {
            Iterator it = this.mHighPriorityListeners.iterator();
            while (it.hasNext()) {
                CoverStateListenerInfo coverStateListenerInfo = (CoverStateListenerInfo) it.next();
                coverStateListenerInfo.onCoverSwitchStateChanged(coverState);
                coverStateListenerInfo.onCoverSwitchStateChanged(coverState.getSwitchState());
            }
        }
        sendCoverSwitchState(coverState);
    }

    public final void sendCoverSwitchState(CoverState coverState) {
        if (!this.mSendCoverSwitchStateWakeLock.isHeld()) {
            this.mSendCoverSwitchStateWakeLock.acquire();
        }
        this.mHandler.obtainMessage(0, coverState.getSwitchState() ? 1 : 0, 0, coverState).sendToTarget();
    }

    public final void handleSendCoverSwitchState(CoverState coverState, boolean z) {
        synchronized (this.mListeners) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                CoverStateListenerInfo coverStateListenerInfo = (CoverStateListenerInfo) it.next();
                coverStateListenerInfo.onCoverSwitchStateChanged(coverState);
                coverStateListenerInfo.onCoverSwitchStateChanged(z);
            }
        }
        Log.addLogString("CoverManager_", "send cover switch : " + z);
        if (this.mSendCoverSwitchStateWakeLock.isHeld()) {
            this.mSendCoverSwitchStateWakeLock.release();
        }
    }

    public void updateCoverAttachState(boolean z, int i, boolean z2) {
        this.mCoverType = i;
        this.mCoverOpen = z2;
        if (Feature.getInstance(this.mContext).isSupportSecureCover() || checkSystemFeatureForCoverType(i)) {
            sendCoverAttachState(z);
        }
    }

    public final void sendCoverAttachState(boolean z) {
        if (!this.mSendCoverAttachStateWakeLock.isHeld()) {
            this.mSendCoverAttachStateWakeLock.acquire();
        }
        if (CoverManagerUtils.isSupportWirelessCharge) {
            if (CoverManagerUtils.isCoverTypeForWirelessCharger(this.mCoverType) && z) {
                CoverManagerUtils.fileWriteInt("/sys/class/power_supply/battery/led_cover", 1);
            } else {
                CoverManagerUtils.fileWriteInt("/sys/class/power_supply/battery/led_cover", 0);
            }
        }
        this.mHandler.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
    }

    public final void handleSendCoverAttachState(boolean z) {
        synchronized (this.mHighPriorityListeners) {
            Iterator it = this.mHighPriorityListeners.iterator();
            while (it.hasNext()) {
                ((CoverStateListenerInfo) it.next()).onCoverAttachStateChanged(z);
            }
        }
        synchronized (this.mListeners) {
            Iterator it2 = this.mListeners.iterator();
            while (it2.hasNext()) {
                ((CoverStateListenerInfo) it2.next()).onCoverAttachStateChanged(z);
            }
        }
        Log.addLogString("CoverManager_", "send cover attach : " + z);
        if (this.mSendCoverAttachStateWakeLock.isHeld()) {
            this.mSendCoverAttachStateWakeLock.release();
        }
    }

    public void updatePowerState(int i, boolean z) {
        Log.d(TAG, "updatePowerState : isOpen=" + z + ", type=" + i);
        this.mPowerManager.updateCoverState(z ^ true);
        if (i != 0) {
            if (i == 11) {
                if (Feature.getInstance(this.mContext).isSupportNeonCover() && this.mPowerManager.isInteractive()) {
                    this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                    return;
                }
                return;
            }
            if (i != 100) {
                if (i != 7) {
                    if (i == 8 && Feature.getInstance(this.mContext).isSupportClearCover() && this.mPowerManager.isInteractive()) {
                        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                        return;
                    }
                    return;
                }
                if (Feature.getInstance(this.mContext).isSupportNfcLedCover()) {
                    if (z) {
                        if (this.mPowerManager.isInteractive()) {
                            this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                            return;
                        } else {
                            this.mPowerManager.semWakeUp(SystemClock.uptimeMillis(), 103, "updatePowerState");
                            return;
                        }
                    }
                    if (this.mLcdOffDisabledByApp) {
                        synchronized (this.mLcdOffDisableListeners) {
                            if (this.mLcdOffDisableListeners.isEmpty()) {
                                enableLcdOffByCoverIfPossibleLocked();
                            }
                        }
                        return;
                    }
                    return;
                }
                return;
            }
        }
        if (!Feature.getInstance(this.mContext).isSupportFlipCover() || FactoryTest.isFactoryMode() || FactoryTest.isAutomaticTestMode(this.mContext) || FactoryTest.isRunningFactoryApp()) {
            return;
        }
        if (z) {
            if (this.mPowerManager.isInteractive()) {
                this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
            } else {
                this.mPowerManager.semWakeUp(SystemClock.uptimeMillis(), 103, "updatePowerState");
            }
            if (this.mGoToSleepRunnable != null) {
                this.mGoToSleepRunnable = null;
                return;
            }
            return;
        }
        if (this.mLcdOffDisabledByApp) {
            synchronized (this.mLcdOffDisableListeners) {
                if (!this.mLcdOffDisableListeners.isEmpty()) {
                    Iterator it = this.mLcdOffDisableListeners.iterator();
                    while (it.hasNext()) {
                        LcdOffDisableListenerInfo lcdOffDisableListenerInfo = (LcdOffDisableListenerInfo) it.next();
                        Log.d(TAG, "cover close: goToSleep disabled by: PID:" + lcdOffDisableListenerInfo.pid + " UID:" + lcdOffDisableListenerInfo.uid);
                    }
                } else {
                    this.mLcdOffDisabledByApp = false;
                    Log.addLogString("CoverManager_", "goToSleep by cover close : mLcdOffDisableListeners is empty");
                    this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
                }
            }
            return;
        }
        if (this.mBootComplete) {
            Log.addLogString("CoverManager_", "goToSleep by cover close : mLcdOffDisabledByApp is false");
            this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
        } else {
            this.mGoToSleepRunnable = new Runnable() { // from class: com.android.server.sepunion.cover.StateNotifier$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StateNotifier.this.lambda$updatePowerState$0();
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePowerState$0() {
        Log.addLogString("CoverManager_", "goToSleep by cover close after boot complete : mLcdOffDisabledByApp is false");
        this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
    }

    public int onCoverAppStateChanged(boolean z) {
        synchronized (this.mListeners) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((CoverStateListenerInfo) it.next()).onCoverAppStateChanged(z);
            }
        }
        return 0;
    }

    public final boolean checkSystemFeatureForCoverType(int i) {
        if (i == 0) {
            return Feature.getInstance(this.mContext).isSupportFlipCover();
        }
        if (i == 11) {
            return Feature.getInstance(this.mContext).isSupportNeonCover();
        }
        if (i == 7) {
            return Feature.getInstance(this.mContext).isSupportNfcLedCover();
        }
        if (i == 8) {
            return Feature.getInstance(this.mContext).isSupportClearCover();
        }
        switch (i) {
            case 14:
                return Feature.getInstance(this.mContext).isSupportLEDBackCover();
            case 15:
                return Feature.getInstance(this.mContext).isSupportClearSideViewCover();
            case 16:
                return Feature.getInstance(this.mContext).isSupportMiniSviewWalletCover();
            case 17:
                return Feature.getInstance(this.mContext).isSupportClearCameraViewCover();
            default:
                return false;
        }
    }

    public class LcdOffDisableListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int uid;

        public LcdOffDisableListenerInfo(IBinder iBinder, ComponentName componentName, int i, int i2) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.v(StateNotifier.TAG, "binderDied : binder = " + this.token);
            synchronized (StateNotifier.this.mLcdOffDisableListeners) {
                StateNotifier.this.mLcdOffDisableListeners.remove(this);
                StateNotifier.this.enableLcdOffByCoverIfPossibleLocked();
            }
            this.token.unlinkToDeath(this, 0);
        }
    }

    public boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        Log.d(TAG, "disableLcdOffByCover");
        synchronized (this.mLcdOffDisableListeners) {
            Iterator it = this.mLcdOffDisableListeners.iterator();
            while (it.hasNext()) {
                LcdOffDisableListenerInfo lcdOffDisableListenerInfo = (LcdOffDisableListenerInfo) it.next();
                if (lcdOffDisableListenerInfo != null && iBinder.equals(lcdOffDisableListenerInfo.token)) {
                    Log.e(TAG, "disableLcdOffByCover : LCD off already called by app");
                    return false;
                }
            }
            LcdOffDisableListenerInfo lcdOffDisableListenerInfo2 = new LcdOffDisableListenerInfo(iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid());
            iBinder.linkToDeath(lcdOffDisableListenerInfo2, 0);
            this.mLcdOffDisableListeners.add(lcdOffDisableListenerInfo2);
            this.mLcdOffDisabledByApp = true;
            return true;
        }
    }

    public boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        LcdOffDisableListenerInfo lcdOffDisableListenerInfo;
        Log.d(TAG, "enableLcdOffByCover");
        synchronized (this.mLcdOffDisableListeners) {
            Iterator it = this.mLcdOffDisableListeners.iterator();
            while (true) {
                if (!it.hasNext()) {
                    lcdOffDisableListenerInfo = null;
                    break;
                }
                lcdOffDisableListenerInfo = (LcdOffDisableListenerInfo) it.next();
                if (lcdOffDisableListenerInfo != null && iBinder.equals(lcdOffDisableListenerInfo.token)) {
                    break;
                }
            }
            if (lcdOffDisableListenerInfo == null) {
                Log.e(TAG, "enableLcdOffByCover: matching listener does not exist.");
                return false;
            }
            this.mLcdOffDisableListeners.remove(lcdOffDisableListenerInfo);
            iBinder.unlinkToDeath(lcdOffDisableListenerInfo, 0);
            enableLcdOffByCoverIfPossibleLocked();
            return true;
        }
    }

    public final void enableLcdOffByCoverIfPossibleLocked() {
        if (this.mLcdOffDisableListeners.isEmpty()) {
            this.mLcdOffDisabledByApp = false;
            if (!this.mCoverOpen && this.mCoverType == 0) {
                Log.addLogString("CoverManager_", "goToSleep by cover close : enableLcdOff");
                this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
            }
            LcdOffDisabledByAppListener lcdOffDisabledByAppListener = this.mLcdOffDisabledByAppListener;
            if (lcdOffDisabledByAppListener != null) {
                int i = this.mCoverType;
                if (i == 7 || i == 11 || i == 14) {
                    lcdOffDisabledByAppListener.onLcdOffByCoverEnabled();
                }
            }
        }
    }

    public void registerLcdOffDisabledByAppListener(LcdOffDisabledByAppListener lcdOffDisabledByAppListener) {
        this.mLcdOffDisabledByAppListener = lcdOffDisabledByAppListener;
    }

    public void unregisterLcdOffDisabledByAppListener(LcdOffDisabledByAppListener lcdOffDisabledByAppListener) {
        this.mLcdOffDisabledByAppListener = null;
    }

    public boolean isLcdOffByDisabledByApp() {
        return this.mLcdOffDisabledByApp;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current StateNotifier state:");
        synchronized (this.mListeners) {
            printWriter.println("  Live listeners (" + this.mListeners.size() + "):");
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                CoverStateListenerInfo coverStateListenerInfo = (CoverStateListenerInfo) it.next();
                if (coverStateListenerInfo != null) {
                    printWriter.println("    " + coverStateListenerInfo.component + " (pid=" + coverStateListenerInfo.pid + " uid=" + coverStateListenerInfo.uid + ")");
                }
            }
            printWriter.println("  ");
        }
        synchronized (this.mHighPriorityListeners) {
            printWriter.println("  Live high priority listeners (" + this.mHighPriorityListeners.size() + "):");
            Iterator it2 = this.mHighPriorityListeners.iterator();
            while (it2.hasNext()) {
                CoverStateListenerInfo coverStateListenerInfo2 = (CoverStateListenerInfo) it2.next();
                if (coverStateListenerInfo2 != null) {
                    printWriter.println("    " + coverStateListenerInfo2.component + " (pid=" + coverStateListenerInfo2.pid + " uid=" + coverStateListenerInfo2.uid + ")");
                }
            }
            printWriter.println("  ");
        }
        synchronized (this.mLcdOffDisableListeners) {
            printWriter.println(" LCD Off disabled by app: " + this.mLcdOffDisabledByApp);
            printWriter.println(" LCD Off listeners (" + this.mLcdOffDisableListeners.size() + "):");
            Iterator it3 = this.mLcdOffDisableListeners.iterator();
            while (it3.hasNext()) {
                LcdOffDisableListenerInfo lcdOffDisableListenerInfo = (LcdOffDisableListenerInfo) it3.next();
                if (lcdOffDisableListenerInfo != null) {
                    printWriter.println("    " + lcdOffDisableListenerInfo.component + " (pid=" + lcdOffDisableListenerInfo.pid + " uid=" + lcdOffDisableListenerInfo.uid + ")");
                }
            }
            printWriter.println("  ");
        }
    }
}
