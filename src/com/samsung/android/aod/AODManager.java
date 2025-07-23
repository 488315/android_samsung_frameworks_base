package com.samsung.android.aod;

import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.Display;
import com.samsung.android.aod.AODManager;
import com.samsung.android.aod.IAODCallback;
import com.samsung.android.aod.IAODDozeCallback;
import com.samsung.android.aod.IAODManager;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes6.dex */
public class AODManager {
    public static final int AOD_MANAGER_VERSION = 4;
    public static final String AOD_PACKAGE_NAME = "com.samsung.android.app.aodservice";
    public static final int INTERVAL_100 = 0;
    public static final int INTERVAL_1000 = 3;
    public static final int INTERVAL_200 = 1;
    public static final int INTERVAL_500 = 2;
    public static final int INTERVAL_DEBUG = 999;
    public static final int ROTATE_0 = 0;
    public static final int ROTATE_180 = 2;
    public static final int ROTATE_270 = 3;
    public static final int ROTATE_90 = 1;
    private static final String TAG = "AODManager";
    public static final int TYPE_ACTIVE_ANALOG_IMAGE = 2;
    public static final int TYPE_ACTIVE_DIGITAL_IMAGE = 3;
    public static final int TYPE_ACTIVE_ICON_IMAGE = 1;
    private static AODManager sInstance;
    private AODDozeCallbackDelegate mAODDozeCallbackDelegate;
    Context mContext;
    private IAODManager mService;
    private final Object mAODCallbackLock = new Object();
    private CopyOnWriteArrayList<AODCallbackDelegate> mAODCallbackDelegates = new CopyOnWriteArrayList<>();

    public interface AODChangeListener {
        void readyToScreenTurningOn();
    }

    public interface AODDozeCallback {
        void onAODToastRequested(AODToast aODToast);

        void onDozeAcquired();

        void onDozeReleased();
    }

    public static AODManager getInstance(Context context) {
        AODManager aODManager = sInstance;
        if (aODManager != null) {
            return aODManager;
        }
        AODManager aODManager2 = new AODManager(context);
        sInstance = aODManager2;
        return aODManager2;
    }

    public AODManager(Context context) {
        this.mContext = context;
    }

    private IAODManager getService() {
        if (this.mService == null) {
            this.mService = IAODManager.Stub.asInterface(ServiceManager.getService("AODManagerService"));
        }
        if (this.mService == null) {
            Log.wtf(TAG, "getService fail!");
        }
        return this.mService;
    }

    public boolean isAODState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isAODState();
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            return false;
        }
    }

    public void updateAODTspRect(int i, int i2, int i3, int i4) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateAODTspRect(i, i2, i3, i4, AOD_PACKAGE_NAME);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public void updateAODTspRect(int i, int i2, int i3, int i4, String str) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateAODTspRect(i, i2, i3, i4, str);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public void updateAODNotiTspRect(int i, int i2, int i3, int i4) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateAODNotiTspRect(i, i2, i3, i4, AOD_PACKAGE_NAME);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public void setGripData(String str) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setGripData(str);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public void updateAODNotiTspRect(int i, int i2, int i3, int i4, String str) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateAODNotiTspRect(i, i2, i3, i4, str);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public boolean isSViewCoverBrightnessHigh() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSViewCoverBrightnessHigh();
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            return true;
        }
    }

    public void addLogText(List<String> list) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.addLogText(list);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public void writeAODCommand(String str, String str2, String str3, String str4, String str5) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.writeAODCommand(str, str2, str3, str4, str5);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setLiveClockInfo(i, j, j2, j3, j4, j5, j6, j7, j8);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            return -1;
        }
    }

    public void setLiveClockNeedle(byte[] bArr) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setLiveClockNeedle(bArr);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public String getActiveImageInfo() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getActiveImageInfo();
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            return null;
        }
    }

    public int setLiveClockImage(int i, int i2, byte[] bArr, String str) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setLiveClockImage(i, i2, bArr, str);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            return -1;
        }
    }

    public int setLiveClockCommand(int i, int i2, int i3, int[] iArr) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setLiveClockCommand(i, i2, i3, iArr);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            return -1;
        }
    }

    public String getAodActiveArea(boolean z) {
        if (getService() == null) {
            return "NG";
        }
        try {
            return this.mService.getAodActiveArea(z);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            return "NG";
        }
    }

    public void readyToScreenTurningOn() {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.readyToScreenTurningOn();
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public void registerAODListener(AODListener aODListener) {
        AODCallbackDelegate aODCallbackDelegate;
        if (getService() == null) {
            return;
        }
        if (aODListener == null) {
            Log.w(TAG, "registerAODListener : listener is null");
            return;
        }
        synchronized (this.mAODCallbackLock) {
            Iterator<AODCallbackDelegate> it = this.mAODCallbackDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    aODCallbackDelegate = null;
                    break;
                }
                aODCallbackDelegate = it.next();
                if (aODCallbackDelegate.getListener() != null && aODCallbackDelegate.getListener().equals(aODListener)) {
                    break;
                }
            }
            if (aODCallbackDelegate == null) {
                AODCallbackDelegate aODCallbackDelegate2 = new AODCallbackDelegate(this, aODListener);
                this.mAODCallbackDelegates.add(aODCallbackDelegate2);
                try {
                    this.mService.registerAODListener(aODCallbackDelegate2);
                } catch (RemoteException e) {
                    Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
                }
                return;
            }
            Log.w(TAG, "registerAODListener : listener already registered");
        }
    }

    public void unregisterAODListener(AODListener aODListener) {
        AODCallbackDelegate aODCallbackDelegate;
        if (getService() == null) {
            return;
        }
        if (aODListener == null) {
            Log.w(TAG, "unregisterAODListener : listener is null");
            return;
        }
        synchronized (this.mAODCallbackLock) {
            Iterator<AODCallbackDelegate> it = this.mAODCallbackDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    aODCallbackDelegate = null;
                    break;
                }
                aODCallbackDelegate = it.next();
                if (aODCallbackDelegate.getListener() != null && aODCallbackDelegate.getListener().equals(aODListener)) {
                    break;
                }
            }
            if (aODCallbackDelegate == null) {
                Log.w(TAG, "unregisterAODListener : cannot find the listener");
                return;
            }
            try {
                this.mService.unregisterAODListener(aODCallbackDelegate);
                this.mAODCallbackDelegates.remove(aODCallbackDelegate);
            } catch (RemoteException e) {
                Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class AODCallbackDelegate extends IAODCallback.Stub {
        private Handler mHandler;
        private AODListener mListener;

        public AODCallbackDelegate(AODManager aODManager, AODListener aODListener) {
            this.mListener = aODListener;
            this.mHandler = new Handler(aODManager.mContext.getMainLooper());
        }

        @Override // com.samsung.android.aod.IAODCallback
        public void onScreenTurningOn() {
            this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.samsung.android.aod.AODManager$AODCallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AODManager.AODCallbackDelegate.this.lambda$onScreenTurningOn$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScreenTurningOn$0() {
            AODListener aODListener = this.mListener;
            if (aODListener != null) {
                aODListener.onScreenTurningOn();
            }
        }

        AODListener getListener() {
            return this.mListener;
        }
    }

    public void registerAODDozeCallback(AODDozeCallback aODDozeCallback) {
        if (getService() == null) {
            return;
        }
        if (aODDozeCallback == null) {
            Log.w(TAG, "registerAODDozeCallback: callback is null");
            return;
        }
        if (this.mAODDozeCallbackDelegate != null) {
            Log.w(TAG, "registerAODDozeCallback: listener already registered");
            return;
        }
        AODDozeCallbackDelegate aODDozeCallbackDelegate = new AODDozeCallbackDelegate(this, aODDozeCallback);
        this.mAODDozeCallbackDelegate = aODDozeCallbackDelegate;
        try {
            this.mService.registerAODDozeCallback(aODDozeCallbackDelegate);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public void unregisterAODDozeCallback(AODDozeCallback aODDozeCallback) {
        if (getService() == null) {
            return;
        }
        if (aODDozeCallback == null) {
            Log.w(TAG, "unregisterAODDozeCallback: callback is null");
            return;
        }
        AODDozeCallbackDelegate aODDozeCallbackDelegate = this.mAODDozeCallbackDelegate;
        if (aODDozeCallbackDelegate == null) {
            Log.w(TAG, "unregisterAODDozeCallback: not registered yet");
            return;
        }
        try {
            this.mService.unregisterAODDozeCallback(aODDozeCallbackDelegate);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
        this.mAODDozeCallbackDelegate = null;
    }

    public void requestAODToast(AODToast aODToast) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.requestAODToast(this.mContext.getPackageName(), aODToast);
        } catch (RemoteException e) {
            Log.w(TAG, "AODManagerService RuntimeException?\n" + Log.getStackTraceString(e));
        }
    }

    public final class AODDozeLock {
        private boolean mHeld;
        private final String mPackageName;
        private String mTag;
        private final IBinder mToken = new Binder();

        private AODDozeLock(String str, String str2) {
            this.mTag = str;
            this.mPackageName = str2;
        }

        public void acquire() {
            synchronized (this.mToken) {
                Display display = AODManager.this.mContext.getDisplay();
                if (display == null) {
                    Log.d(AODManager.TAG, "acquireDoze: display is null");
                    return;
                }
                int state = display.getState();
                int state2 = display.getState();
                if (state2 == 1 || state2 == 3 || state2 == 4) {
                    try {
                        AODManager.this.mService.acquireDoze(this.mToken, this.mTag, this.mPackageName);
                    } catch (RemoteException e) {
                        Log.w(AODManager.TAG, "AODDozeLock RuntimeException?\n" + Log.getStackTraceString(e));
                    }
                    this.mHeld = true;
                } else {
                    Log.d(AODManager.TAG, "acquireDoze: skip due to state = " + state);
                }
            }
        }

        public void release() {
            synchronized (this.mToken) {
                Display display = AODManager.this.mContext.getDisplay();
                if (display == null) {
                    Log.d(AODManager.TAG, "release: display is null");
                    return;
                }
                int state = display.getState();
                int state2 = display.getState();
                if (state2 == 3 || state2 == 4) {
                    if (this.mHeld) {
                        try {
                            AODManager.this.mService.releaseDoze(this.mToken);
                        } catch (RemoteException e) {
                            Log.w(AODManager.TAG, "AODDozeLock RuntimeException?\n" + Log.getStackTraceString(e));
                        }
                        this.mHeld = false;
                    }
                } else {
                    Log.d(AODManager.TAG, "releaseDoze: skip due to state = " + state);
                }
            }
        }

        public boolean isHeld() {
            boolean z;
            synchronized (this.mToken) {
                z = this.mHeld;
            }
            return z;
        }

        public AODDozeLock newAODDozeLock(String str) {
            AODManager aODManager = AODManager.this;
            return aODManager.new AODDozeLock(str, aODManager.mContext.getOpPackageName());
        }
    }

    private class AODDozeCallbackDelegate extends IAODDozeCallback.Stub {
        private WeakReference<AODDozeCallback> mCallback;
        private Handler mHandler;

        AODDozeCallbackDelegate(AODManager aODManager, AODDozeCallback aODDozeCallback) {
            this.mHandler = new Handler(aODManager.mContext.getMainLooper());
            this.mCallback = new WeakReference<>(aODDozeCallback);
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onDozeAcquired() throws RemoteException {
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.aod.AODManager.AODDozeCallbackDelegate.1
                @Override // java.lang.Runnable
                public void run() {
                    AODDozeCallback aODDozeCallback = (AODDozeCallback) AODDozeCallbackDelegate.this.mCallback.get();
                    if (aODDozeCallback != null) {
                        aODDozeCallback.onDozeAcquired();
                    }
                }
            });
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onDozeReleased() throws RemoteException {
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.aod.AODManager.AODDozeCallbackDelegate.2
                @Override // java.lang.Runnable
                public void run() {
                    AODDozeCallback aODDozeCallback = (AODDozeCallback) AODDozeCallbackDelegate.this.mCallback.get();
                    if (aODDozeCallback != null) {
                        aODDozeCallback.onDozeReleased();
                    }
                }
            });
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onAODToastRequested(final AODToast aODToast) throws RemoteException {
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.aod.AODManager.AODDozeCallbackDelegate.3
                @Override // java.lang.Runnable
                public void run() {
                    AODDozeCallback aODDozeCallback = (AODDozeCallback) AODDozeCallbackDelegate.this.mCallback.get();
                    if (aODDozeCallback != null) {
                        aODDozeCallback.onAODToastRequested(aODToast);
                    }
                }
            });
        }
    }
}
