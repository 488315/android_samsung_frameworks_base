package com.samsung.accessory.manager.authentication.usb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.util.TimeUtils;
import com.android.server.LocalServices;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.accessory.manager.authentication.cover.CoverInfo;
import com.samsung.android.sepunion.SemUnionManagerLocal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class UsbAuthenticator extends LocalAuthenticator implements SAccessoryManager.AuthenticationResultCallback {
    public SAccessoryManager.AuthenticationTask mAuthenticationTask;
    public Context mContext;
    public PowerManager mPowerManager;
    public PowerManager.WakeLock mSafetyDetachTimeoutWakeLock;
    public SemUnionManagerLocal mUnionManagerLocal;
    public UsbAuthHandler mUsbAuthHandler;
    public PowerManager.WakeLock mUsbAuthWakeLock;
    public static final String TAG = "SAccessoryManager_" + UsbAuthenticator.class.getSimpleName();
    public static final boolean DBG = Debug.semIsProductDev();
    public volatile long mLastAuthenticationTime = 0;
    public long mLastAttachTime = 0;
    public long mLastDetachTime = 0;
    public long mUsbAttachedWhenNanos = 0;
    public int mRetryCounterWhenBusy = 0;
    public int mRetryCounterFactoryTest = 0;
    public int mFailuresCount = 0;
    public byte[] mUriData = null;
    public int USB_ATTACHED = 0;
    public int USB_DETACHED = 1;
    public boolean mSystemReady = false;
    public volatile boolean mIsShutingdown = false;
    public volatile boolean mPreparing = false;
    public boolean mIsFactoryBinary = false;
    public int usbState = this.USB_DETACHED;
    public int mCoverType = 2;
    public final AtomicReference mResult = new AtomicReference();
    public final LinkedList mAuthenticationHistory = new LinkedList();
    public AuthenticationSession mCurrentSession = null;

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationComplted(AuthenticationResult authenticationResult) {
        Message obtainMessage = this.mUsbAuthHandler.obtainMessage(3);
        obtainMessage.obj = authenticationResult;
        this.mUsbAuthHandler.sendMessage(obtainMessage);
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStarted() {
        this.mUsbAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.usb.UsbAuthenticator.1
            @Override // java.lang.Runnable
            public void run() {
                Log.i(UsbAuthenticator.TAG, "onAuthenticationStarted");
                if (UsbAuthenticator.this.mPreparing && UsbAuthenticator.this.mUsbAuthHandler.hasMessages(5)) {
                    UsbAuthenticator.this.mPreparing = false;
                    UsbAuthenticator.this.scheduleAuthenticationTimeOuts(10000L);
                }
            }
        });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStopped(AuthenticationSession authenticationSession) {
        this.mUsbAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.usb.UsbAuthenticator.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i(UsbAuthenticator.TAG, "onAuthenticationStopped");
                UsbAuthenticator usbAuthenticator = UsbAuthenticator.this;
                usbAuthenticator.mCurrentSession = null;
                usbAuthenticator.mRetryCounterWhenBusy = 0;
                if (UsbAuthenticator.this.mUsbAuthHandler.hasMessages(5)) {
                    UsbAuthenticator.this.startAuthentication(false, 0L);
                }
            }
        });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStarting(final AuthenticationSession authenticationSession) {
        this.mUsbAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.usb.UsbAuthenticator.3
            @Override // java.lang.Runnable
            public void run() {
                Log.i(UsbAuthenticator.TAG, "onAuthenticationStarting");
                UsbAuthenticator.this.mCurrentSession = authenticationSession;
            }
        });
    }

    public final class UsbAuthHandler extends Handler {
        public UsbAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            Log.i(UsbAuthenticator.TAG, "handleMessage = " + UsbAuthenticator.this.convertMsg(message.what));
            switch (message.what) {
                case 1:
                    UsbAuthenticator.this.initialize();
                    return;
                case 2:
                    UsbAuthenticator.this.handleAuthStart();
                    return;
                case 3:
                    UsbAuthenticator.this.handleAuthResponse(message);
                    return;
                case 4:
                    break;
                case 5:
                case 6:
                    UsbAuthenticator.this.mUsbAuthHandler.removeMessages(2);
                    if (message.what == 5) {
                        UsbAuthenticator.this.mFailuresCount++;
                        Log.e(UsbAuthenticator.TAG, "authentication is timed out!");
                        break;
                    }
                    break;
                default:
                    return;
            }
            UsbAuthenticator.this.mAuthenticationTask.stop(UsbAuthenticator.this.mCurrentSession);
            if (message.what == 6) {
                UsbAuthenticator.this.removeAuthenticationTimeOuts();
                z = true;
            } else {
                z = false;
            }
            UsbAuthenticator usbAuthenticator = UsbAuthenticator.this;
            if (usbAuthenticator.usbState == usbAuthenticator.USB_DETACHED || ((message.what == 5 && usbAuthenticator.mFailuresCount > 1) || z)) {
                UsbAuthenticator.this.setUsbVerified(false, null, null);
                UsbAuthenticator.this.mResult.set(null);
            }
        }
    }

    public final void handleAuthResponse(Message message) {
        boolean z;
        String str = TAG;
        Log.i(str, "handleAuthResponse");
        this.mUsbAuthWakeLock.acquire();
        AuthenticationResult authenticationResult = (AuthenticationResult) message.obj;
        this.mResult.set(authenticationResult);
        int i = authenticationResult.getResultBundle().getInt("reason");
        Log.i(str, "auth reason = " + i);
        CoverInfo coverInfo = new CoverInfo(authenticationResult.getExtraId());
        boolean z2 = true;
        if (i == 0) {
            z = true;
        } else {
            z = false;
            if (i != 1) {
                if (i == 11 || i == 30) {
                    Log.i(str, "do nothing..");
                } else if (i == 31) {
                    removeAuthenticationTimeOuts();
                    this.mUsbAuthHandler.removeMessages(2);
                } else if (i == 12) {
                    removeAuthenticationTimeOuts();
                    this.mUsbAuthHandler.removeMessages(2);
                } else if (i == 13) {
                    if (this.mRetryCounterWhenBusy < 2 && this.mUsbAuthHandler.hasMessages(5)) {
                        this.mRetryCounterWhenBusy++;
                        startAuthentication(true, 10000L, 5000L);
                    } else {
                        this.mRetryCounterWhenBusy = 0;
                        removeAuthenticationTimeOuts();
                        this.mUsbAuthHandler.removeMessages(2);
                    }
                } else if (i == 90 && this.mUsbAuthHandler.hasMessages(5)) {
                    startAuthentication(false, 300L);
                }
                z2 = false;
            }
        }
        addRecord(i, authenticationResult.getApiState());
        if (z2) {
            setUsbVerified(z, coverInfo, authenticationResult);
            stopAuthentication(0L);
        }
        this.mUsbAuthWakeLock.release();
    }

    public final void handleAuthStart() {
        String str = TAG;
        Log.i(str, "handleAuthStart");
        this.mUsbAuthWakeLock.acquire();
        int sessionState = this.mAuthenticationTask.getSessionState(this.mCurrentSession);
        if (sessionState != 7 && sessionState != 1) {
            Log.e(str, "session is busy");
            this.mUsbAuthWakeLock.release();
            return;
        }
        Message message = new Message();
        if (this.mAuthenticationTask.getSessionState(this.mCurrentSession) == 7) {
            Bundle bundle = new Bundle();
            bundle.putInt("connectivity_type", 3);
            message.obj = this;
            message.setData(bundle);
            this.mAuthenticationTask.start(message, true);
        } else {
            message.obj = this.mCurrentSession;
            this.mAuthenticationTask.start(message, true);
        }
        this.mUsbAuthWakeLock.release();
    }

    public UsbAuthenticator(Context context, Looper looper, SAccessoryManager.AuthenticationTask authenticationTask) {
        this.mContext = context;
        this.mAuthenticationTask = authenticationTask;
        this.mUsbAuthHandler = new UsbAuthHandler(looper);
    }

    public final void initialize() {
        String str = TAG;
        Log.i(str, "Initialize usb authenticator");
        this.mIsFactoryBinary = FactoryTest.isFactoryBinary();
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mPowerManager = powerManager;
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, str);
        this.mUsbAuthWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = this.mPowerManager.newWakeLock(1, str + "DetachTimeoutWakeLock");
        this.mSafetyDetachTimeoutWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
        this.mSystemReady = true;
        this.mAuthenticationTask.authenticationReady();
    }

    public void onUsbAttached(long j, boolean z) {
        if (this.mIsFactoryBinary || !isAuthenticationReady()) {
            return;
        }
        long j2 = 0;
        if (z) {
            this.usbState = this.USB_ATTACHED;
            this.mUsbAttachedWhenNanos = j;
            this.mLastAttachTime = System.currentTimeMillis();
            startAuthentication(true, 0L);
            return;
        }
        this.usbState = this.USB_DETACHED;
        long j3 = this.mUsbAttachedWhenNanos;
        if (j3 != 0 && j - j3 < 500000000) {
            j2 = 500;
        }
        this.mSafetyDetachTimeoutWakeLock.acquire(1000L);
        this.mLastDetachTime = System.currentTimeMillis();
        this.mFailuresCount = 0;
        stopAuthentication(j2);
    }

    public final void startAuthentication(boolean z, long j) {
        if (isAuthenticationReady()) {
            this.mUsbAuthHandler.removeMessages(4);
            if (z) {
                scheduleAuthenticationTimeOuts(27000L);
                this.mPreparing = true;
            }
            this.mUsbAuthHandler.sendEmptyMessageDelayed(2, j);
        }
    }

    public final void startAuthentication(boolean z, long j, long j2) {
        if (isAuthenticationReady()) {
            this.mUsbAuthHandler.removeMessages(4);
            if (z) {
                scheduleAuthenticationTimeOuts(j);
            }
            this.mUsbAuthHandler.sendEmptyMessageDelayed(2, j2);
        }
    }

    public final void scheduleAuthenticationTimeOuts(long j) {
        removeAuthenticationTimeOuts();
        this.mUsbAuthHandler.sendEmptyMessageDelayed(5, j);
    }

    public void removeAuthenticationTimeOuts() {
        this.mPreparing = false;
        this.mUsbAuthHandler.removeMessages(5);
    }

    public final void stopAuthentication(long j) {
        removeAuthenticationTimeOuts();
        this.mUsbAuthHandler.removeMessages(2);
        this.mUsbAuthHandler.sendEmptyMessageDelayed(4, j);
    }

    public final void setUsbVerified(boolean z, CoverInfo coverInfo, AuthenticationResult authenticationResult) {
        byte[] bArr;
        byte b;
        Log.i(TAG, "setUsbVerified");
        if (coverInfo != null) {
            this.mCoverType = coverInfo.getType();
        }
        if (z) {
            this.mUriData = authenticationResult.getByteArrayManagerURI();
            if (coverInfo == null || coverInfo.getUrl() == 0 || (bArr = this.mUriData) == null || bArr.length < 2 || (b = bArr[1]) < 17 || b >= 32) {
                return;
            }
            notifyFriendsStateChanged(true, bArr, coverInfo.getId());
            return;
        }
        notifyFriendsStateChanged(false, this.mUriData, null);
        this.mUriData = null;
    }

    public final boolean notifyFriendsStateChanged(boolean z, byte[] bArr, byte[] bArr2) {
        if (this.mUnionManagerLocal == null) {
            this.mUnionManagerLocal = (SemUnionManagerLocal) LocalServices.getService(SemUnionManagerLocal.class);
        }
        SemUnionManagerLocal semUnionManagerLocal = this.mUnionManagerLocal;
        if (semUnionManagerLocal == null) {
            return true;
        }
        semUnionManagerLocal.accessoryStateChanged(z, bArr, bArr2);
        try {
            Intent component = new Intent().setComponent(new ComponentName("com.sec.enterprise.knox.cloudmdm.smdms", "com.sec.enterprise.knox.cloudmdm.smdms.core.AccessoryStateChangeReceiver"));
            component.putExtra("accessoryType", this.mCoverType);
            component.putExtra("accessoryState", z ? 1001 : 1002);
            component.putExtra("accessoryUid", bArr);
            component.putExtra("from", "com.samsung.accessory.manager.authentication");
            this.mContext.sendBroadcast(component);
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "error during starting KES service");
            return true;
        }
    }

    public final boolean isAuthenticationReady() {
        return this.mSystemReady && !this.mIsShutingdown;
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public void systemReady() {
        this.mUsbAuthHandler.sendEmptyMessage(1);
    }

    public void addRecord(int i, int i2) {
        if (this.mAuthenticationHistory.size() > 60) {
            this.mAuthenticationHistory.removeFirst();
        }
        this.mAuthenticationHistory.add(String.valueOf(i) + "/" + TimeUtils.logTimeOfDay(System.currentTimeMillis()));
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current UsbAuthenticator state:");
        AuthenticationResult authenticationResult = (AuthenticationResult) this.mResult.get();
        if (authenticationResult != null) {
            printWriter.println("  auth reason = " + authenticationResult.getReason());
        }
        printWriter.println("  Historical authentication: ");
        for (int i = 0; i < this.mAuthenticationHistory.size(); i++) {
            printWriter.println("    " + ((String) this.mAuthenticationHistory.get(i)));
        }
        printWriter.println("  mLastAuthenticationTime = " + this.mLastAuthenticationTime);
        printWriter.println("  mLastAttachTime = " + TimeUtils.logTimeOfDay(this.mLastAttachTime));
        printWriter.println("  mLastDetachTime = " + TimeUtils.logTimeOfDay(this.mLastDetachTime));
    }
}
