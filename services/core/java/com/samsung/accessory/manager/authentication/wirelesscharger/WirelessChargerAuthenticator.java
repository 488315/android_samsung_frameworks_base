package com.samsung.accessory.manager.authentication.wirelesscharger;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.util.TimeUtils;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class WirelessChargerAuthenticator extends LocalAuthenticator implements SAccessoryManager.AuthenticationResultCallback {
    public boolean mAuthenticationStarted;
    public SAccessoryManager.AuthenticationTask mAuthenticationTask;
    public Context mContext;
    public MsgHelper mMsgHelper;
    public PowerManager mPowerManager;
    public PowerManager.WakeLock mSafetyDetachTimeoutWakeLock;
    public WirelessChargerAuthHandler mWirelessChargerAuthHandler;
    public PowerManager.WakeLock mWirelessChargerAuthWakeLock;
    public static final String TAG = "SAccessoryManager_" + WirelessChargerAuthenticator.class.getSimpleName();
    public static final boolean DBG = Debug.semIsProductDev();
    public volatile long mLastAuthenticationTime = 0;
    public long mLastAttachTime = 0;
    public long mLastDetachTime = 0;
    public long mWirelessChargerAttachedWhenNanos = 0;
    public int mRetryCounterWhenBusy = 0;
    public int mRetryCounterFactoryTest = 0;
    public int mFailuresCount = 0;
    public byte[] mUriData = null;
    public int WIRELESSCHARGER_CONNECTED = 0;
    public int WIRELESSCHARGER_DISCONNECTED = 1;
    public int auth_result = -2;
    public boolean mSystemReady = false;
    public volatile boolean mIsShutingdown = false;
    public volatile boolean mPreparing = false;
    public boolean mIsFactoryBinary = false;
    public int wirelessChargerState = this.WIRELESSCHARGER_DISCONNECTED;
    public final AtomicReference mResult = new AtomicReference();
    public final LinkedList mAuthenticationHistory = new LinkedList();
    public AuthenticationSession mCurrentSession = null;

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationComplted(AuthenticationResult authenticationResult) {
        Message obtainMessage = this.mWirelessChargerAuthHandler.obtainMessage(3);
        obtainMessage.obj = authenticationResult;
        this.mWirelessChargerAuthHandler.sendMessage(obtainMessage);
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStarted() {
        this.mWirelessChargerAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.wirelesscharger.WirelessChargerAuthenticator.1
            @Override // java.lang.Runnable
            public void run() {
                Log.i(WirelessChargerAuthenticator.TAG, "onAuthenticationStarted");
                if (WirelessChargerAuthenticator.this.mPreparing && WirelessChargerAuthenticator.this.mWirelessChargerAuthHandler.hasMessages(5)) {
                    WirelessChargerAuthenticator.this.mPreparing = false;
                    WirelessChargerAuthenticator.this.scheduleAuthenticationTimeOuts(60000L);
                }
            }
        });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStopped(AuthenticationSession authenticationSession) {
        this.mWirelessChargerAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.wirelesscharger.WirelessChargerAuthenticator.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i(WirelessChargerAuthenticator.TAG, "onAuthenticationStopped");
                WirelessChargerAuthenticator wirelessChargerAuthenticator = WirelessChargerAuthenticator.this;
                wirelessChargerAuthenticator.mCurrentSession = null;
                wirelessChargerAuthenticator.mRetryCounterWhenBusy = 0;
                if (WirelessChargerAuthenticator.this.mWirelessChargerAuthHandler.hasMessages(5)) {
                    WirelessChargerAuthenticator.this.startAuthentication(false, 0L);
                }
            }
        });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStarting(final AuthenticationSession authenticationSession) {
        this.mWirelessChargerAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.wirelesscharger.WirelessChargerAuthenticator.3
            @Override // java.lang.Runnable
            public void run() {
                Log.i(WirelessChargerAuthenticator.TAG, "onAuthenticationStarting");
                WirelessChargerAuthenticator.this.mCurrentSession = authenticationSession;
            }
        });
    }

    public final class WirelessChargerAuthHandler extends Handler {
        public WirelessChargerAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            Log.i(WirelessChargerAuthenticator.TAG, "handleMessage: " + WirelessChargerAuthenticator.this.convertMsg(message.what));
            switch (message.what) {
                case 1:
                    WirelessChargerAuthenticator.this.initialize();
                    return;
                case 2:
                    WirelessChargerAuthenticator.this.handleAuthStart(((Boolean) message.obj).booleanValue());
                    return;
                case 3:
                    WirelessChargerAuthenticator.this.handleAuthResponse(message);
                    return;
                case 4:
                    break;
                case 5:
                case 6:
                    WirelessChargerAuthenticator.this.mWirelessChargerAuthHandler.removeMessages(2);
                    if (message.what == 5) {
                        WirelessChargerAuthenticator.this.mFailuresCount++;
                        Log.e(WirelessChargerAuthenticator.TAG, "authentication is timed out!");
                        break;
                    }
                    break;
                default:
                    return;
            }
            WirelessChargerAuthenticator.this.mAuthenticationTask.stop(WirelessChargerAuthenticator.this.mCurrentSession);
            if (message.what == 6) {
                WirelessChargerAuthenticator.this.removeAuthenticationTimeOuts();
                z = true;
            } else {
                z = false;
            }
            WirelessChargerAuthenticator wirelessChargerAuthenticator = WirelessChargerAuthenticator.this;
            if (wirelessChargerAuthenticator.wirelessChargerState == wirelessChargerAuthenticator.WIRELESSCHARGER_DISCONNECTED) {
                wirelessChargerAuthenticator.setWirelessChargerVerified(101, null);
            } else if ((message.what == 5 && wirelessChargerAuthenticator.mFailuresCount > 1) || z) {
                WirelessChargerAuthenticator wirelessChargerAuthenticator2 = WirelessChargerAuthenticator.this;
                int i = wirelessChargerAuthenticator2.auth_result;
                if (i != -2 || i != 101) {
                    wirelessChargerAuthenticator2.setWirelessChargerVerified(i, null);
                } else {
                    wirelessChargerAuthenticator2.setWirelessChargerVerified(102, null);
                }
            }
            WirelessChargerAuthenticator.this.mResult.set(null);
        }
    }

    public final void handleAuthResponse(Message message) {
        String str = TAG;
        Log.i(str, "handleAuthResponse");
        this.mWirelessChargerAuthWakeLock.acquire();
        AuthenticationResult authenticationResult = (AuthenticationResult) message.obj;
        this.mResult.set(authenticationResult);
        int i = authenticationResult.getResultBundle().getInt("reason");
        this.auth_result = i;
        Log.i(str, "auth reason = " + i);
        boolean z = true;
        if (i != 0 && i != 1) {
            if (i == 11 || i == 30) {
                Log.d(str, "do nothing..");
            } else if (i == 31) {
                removeAuthenticationTimeOuts();
                this.mWirelessChargerAuthHandler.removeMessages(2);
            } else if (i == 12) {
                removeAuthenticationTimeOuts();
                this.mWirelessChargerAuthHandler.removeMessages(2);
            } else if (i == 13) {
                if (this.mRetryCounterWhenBusy < 2 && this.mWirelessChargerAuthHandler.hasMessages(5)) {
                    this.mRetryCounterWhenBusy++;
                    startAuthentication(true, 60000L, 5000L);
                } else {
                    this.mRetryCounterWhenBusy = 0;
                    removeAuthenticationTimeOuts();
                    this.mWirelessChargerAuthHandler.removeMessages(2);
                }
            } else if (i == 90 && this.mWirelessChargerAuthHandler.hasMessages(5)) {
                startAuthentication(false, 300L);
            }
            z = false;
        }
        if (z) {
            setWirelessChargerVerified(i, authenticationResult);
            stopAuthentication(0L);
        }
        this.mWirelessChargerAuthWakeLock.release();
    }

    public final void handleAuthStart(boolean z) {
        String str = TAG;
        Log.i(str, "handleAuthStart");
        this.mWirelessChargerAuthWakeLock.acquire();
        int sessionState = this.mAuthenticationTask.getSessionState(this.mCurrentSession);
        if (sessionState != 7 && sessionState != 1) {
            Log.e(str, "session is busy");
            this.mWirelessChargerAuthWakeLock.release();
            return;
        }
        if (z) {
            scheduleAuthenticationTimeOuts(27000L);
            this.mPreparing = true;
        }
        Message message = new Message();
        if (this.mAuthenticationTask.getSessionState(this.mCurrentSession) == 7) {
            Bundle bundle = new Bundle();
            bundle.putInt("connectivity_type", 4);
            message.obj = this;
            message.setData(bundle);
            this.mAuthenticationTask.start(message, true);
        } else {
            message.obj = this.mCurrentSession;
            this.mAuthenticationTask.start(message, true);
        }
        this.mWirelessChargerAuthWakeLock.release();
    }

    public WirelessChargerAuthenticator(Context context, Looper looper, SAccessoryManager.AuthenticationTask authenticationTask) {
        this.mContext = context;
        this.mAuthenticationTask = authenticationTask;
        this.mWirelessChargerAuthHandler = new WirelessChargerAuthHandler(looper);
    }

    public final void initialize() {
        String str = TAG;
        Log.d(str, "Initialize wirelesscharger authenticator");
        this.mIsFactoryBinary = FactoryTest.isFactoryBinary();
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mPowerManager = powerManager;
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, str);
        this.mWirelessChargerAuthWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = this.mPowerManager.newWakeLock(1, str + "DetachTimeoutWakeLock");
        this.mSafetyDetachTimeoutWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
        this.mMsgHelper = new MsgHelper();
        this.mSystemReady = true;
        this.mAuthenticationTask.authenticationReady();
    }

    public void onWirelessChargerConnected(long j, boolean z) {
        long j2 = 0;
        if (z) {
            if (this.mAuthenticationStarted) {
                Log.w(TAG, "Authentication already starts");
                return;
            }
            this.mAuthenticationStarted = true;
            this.wirelessChargerState = this.WIRELESSCHARGER_CONNECTED;
            this.mWirelessChargerAttachedWhenNanos = System.currentTimeMillis();
            this.mLastAttachTime = System.currentTimeMillis();
            startAuthentication(true, 0L);
            return;
        }
        this.wirelessChargerState = this.WIRELESSCHARGER_DISCONNECTED;
        long j3 = this.mWirelessChargerAttachedWhenNanos;
        if (j3 != 0 && j - j3 < 500000000) {
            j2 = 500;
        }
        this.mSafetyDetachTimeoutWakeLock.acquire(1000L);
        this.mLastDetachTime = System.currentTimeMillis();
        this.mFailuresCount = 0;
        this.mAuthenticationStarted = false;
        stopAuthentication(j2);
    }

    public final void startAuthentication(boolean z, long j) {
        if (isAuthenticationReady()) {
            this.mWirelessChargerAuthHandler.removeMessages(4);
            Message message = new Message();
            message.obj = Boolean.valueOf(z);
            message.what = 2;
            this.mWirelessChargerAuthHandler.sendMessageDelayed(message, j);
        }
    }

    public final void startAuthentication(boolean z, long j, long j2) {
        if (isAuthenticationReady()) {
            this.mWirelessChargerAuthHandler.removeMessages(4);
            if (z) {
                scheduleAuthenticationTimeOuts(j);
            }
            this.mWirelessChargerAuthHandler.sendEmptyMessageDelayed(2, j2);
        }
    }

    public final void scheduleAuthenticationTimeOuts(long j) {
        removeAuthenticationTimeOuts();
        this.mWirelessChargerAuthHandler.sendEmptyMessageDelayed(5, j);
    }

    public void removeAuthenticationTimeOuts() {
        this.mPreparing = false;
        this.mWirelessChargerAuthHandler.removeMessages(5);
    }

    public final void stopAuthentication(long j) {
        removeAuthenticationTimeOuts();
        this.mWirelessChargerAuthHandler.removeMessages(2);
        this.mWirelessChargerAuthHandler.sendEmptyMessageDelayed(4, j);
    }

    public final void setWirelessChargerVerified(int i, AuthenticationResult authenticationResult) {
        Log.i(TAG, "setWirelessChargerVerified");
        if (i != 28) {
            int i2 = 5;
            for (boolean z = false; i2 >= 0 && !z; z = true) {
                int wirelesscharger_open = this.mMsgHelper.wirelesscharger_open();
                byte[] bArr = {1};
                byte[] bArr2 = {2};
                String str = TAG;
                Log.i(str, "reason = " + i + ", open batt_misc ret = " + wirelesscharger_open);
                if (wirelesscharger_open < 0) {
                    Log.e(str, "open fail");
                    i = 102;
                }
                if (i == 0) {
                    Log.i(str, "write success");
                    this.mMsgHelper.ioctl_longDataWrite_batt(bArr);
                } else if (i != 101) {
                    Log.i(str, "write fail");
                    this.mMsgHelper.ioctl_longDataWrite_batt(bArr2);
                }
                Log.d(str, "close batt_misc");
                this.mMsgHelper.ccic_close();
                i2--;
            }
        }
        addRecord(i, 0);
        this.auth_result = -2;
        this.mAuthenticationStarted = false;
    }

    public final boolean isAuthenticationReady() {
        return this.mSystemReady && !this.mIsShutingdown;
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public void systemReady() {
        this.mWirelessChargerAuthHandler.sendEmptyMessage(1);
    }

    public void addRecord(int i, int i2) {
        if (this.mAuthenticationHistory.size() > 60) {
            this.mAuthenticationHistory.removeFirst();
        }
        this.mAuthenticationHistory.add(String.valueOf(i) + "/" + TimeUtils.logTimeOfDay(System.currentTimeMillis()));
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current WirelessChargerAuthenticator state:");
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
