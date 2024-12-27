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

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;

import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WirelessChargerAuthenticator extends LocalAuthenticator
        implements SAccessoryManager.AuthenticationResultCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAuthenticationStarted;
    public final SAccessoryManager.AnonymousClass1 mAuthenticationTask;
    public final Context mContext;
    public MsgHelper mMsgHelper;
    public PowerManager mPowerManager;
    public PowerManager.WakeLock mSafetyDetachTimeoutWakeLock;
    public final WirelessChargerAuthHandler mWirelessChargerAuthHandler;
    public PowerManager.WakeLock mWirelessChargerAuthWakeLock;
    public long mLastAttachTime = 0;
    public long mLastDetachTime = 0;
    public long mWirelessChargerAttachedWhenNanos = 0;
    public int mRetryCounterWhenBusy = 0;
    public int mFailuresCount = 0;
    public int auth_result = -2;
    public boolean mSystemReady = false;
    public volatile boolean mPreparing = false;
    public int wirelessChargerState = 1;
    public final AtomicReference mResult = new AtomicReference();
    public final LinkedList mAuthenticationHistory = new LinkedList();
    public AuthenticationSession mCurrentSession = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.accessory.manager.authentication.wirelesscharger.WirelessChargerAuthenticator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WirelessChargerAuthenticator this$0;

        public /* synthetic */ AnonymousClass1(
                WirelessChargerAuthenticator wirelessChargerAuthenticator, int i) {
            this.$r8$classId = i;
            this.this$0 = wirelessChargerAuthenticator;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    int i = WirelessChargerAuthenticator.$r8$clinit;
                    Log.i(
                            "SAccessoryManager_WirelessChargerAuthenticator",
                            "onAuthenticationStarted");
                    if (this.this$0.mPreparing
                            && this.this$0.mWirelessChargerAuthHandler.hasMessages(5)) {
                        this.this$0.mPreparing = false;
                        WirelessChargerAuthenticator wirelessChargerAuthenticator = this.this$0;
                        wirelessChargerAuthenticator.removeAuthenticationTimeOuts();
                        wirelessChargerAuthenticator.mWirelessChargerAuthHandler
                                .sendEmptyMessageDelayed(5, 60000L);
                        break;
                    }
                    break;
                default:
                    int i2 = WirelessChargerAuthenticator.$r8$clinit;
                    Log.i(
                            "SAccessoryManager_WirelessChargerAuthenticator",
                            "onAuthenticationStopped");
                    WirelessChargerAuthenticator wirelessChargerAuthenticator2 = this.this$0;
                    wirelessChargerAuthenticator2.mCurrentSession = null;
                    wirelessChargerAuthenticator2.mRetryCounterWhenBusy = 0;
                    if (wirelessChargerAuthenticator2.mWirelessChargerAuthHandler.hasMessages(5)) {
                        this.this$0.startAuthentication$2(0L, false);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WirelessChargerAuthHandler extends Handler {
        public WirelessChargerAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = WirelessChargerAuthenticator.$r8$clinit;
            StringBuilder sb = new StringBuilder("handleMessage: ");
            WirelessChargerAuthenticator wirelessChargerAuthenticator =
                    WirelessChargerAuthenticator.this;
            int i2 = message.what;
            wirelessChargerAuthenticator.getClass();
            sb.append(LocalAuthenticator.convertMsg(i2));
            Log.i("SAccessoryManager_WirelessChargerAuthenticator", sb.toString());
            boolean z = false;
            switch (message.what) {
                case 1:
                    WirelessChargerAuthenticator wirelessChargerAuthenticator2 =
                            WirelessChargerAuthenticator.this;
                    wirelessChargerAuthenticator2.getClass();
                    Log.d(
                            "SAccessoryManager_WirelessChargerAuthenticator",
                            "Initialize wirelesscharger authenticator");
                    FactoryTest.isFactoryBinary();
                    PowerManager powerManager =
                            (PowerManager)
                                    wirelessChargerAuthenticator2.mContext.getSystemService(
                                            "power");
                    wirelessChargerAuthenticator2.mPowerManager = powerManager;
                    PowerManager.WakeLock newWakeLock =
                            powerManager.newWakeLock(
                                    1, "SAccessoryManager_WirelessChargerAuthenticator");
                    wirelessChargerAuthenticator2.mWirelessChargerAuthWakeLock = newWakeLock;
                    newWakeLock.setReferenceCounted(false);
                    PowerManager.WakeLock newWakeLock2 =
                            wirelessChargerAuthenticator2.mPowerManager.newWakeLock(
                                    1,
                                    "SAccessoryManager_WirelessChargerAuthenticatorDetachTimeoutWakeLock");
                    wirelessChargerAuthenticator2.mSafetyDetachTimeoutWakeLock = newWakeLock2;
                    newWakeLock2.setReferenceCounted(false);
                    wirelessChargerAuthenticator2.mMsgHelper = new MsgHelper();
                    wirelessChargerAuthenticator2.mSystemReady = true;
                    wirelessChargerAuthenticator2.mAuthenticationTask.authenticationReady();
                    return;
                case 2:
                    WirelessChargerAuthenticator wirelessChargerAuthenticator3 =
                            WirelessChargerAuthenticator.this;
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    wirelessChargerAuthenticator3.getClass();
                    Log.i("SAccessoryManager_WirelessChargerAuthenticator", "handleAuthStart");
                    wirelessChargerAuthenticator3.mWirelessChargerAuthWakeLock.acquire();
                    int sessionState =
                            wirelessChargerAuthenticator3.mAuthenticationTask.getSessionState(
                                    wirelessChargerAuthenticator3.mCurrentSession);
                    if (sessionState != 7 && sessionState != 1) {
                        Log.e("SAccessoryManager_WirelessChargerAuthenticator", "session is busy");
                        wirelessChargerAuthenticator3.mWirelessChargerAuthWakeLock.release();
                        return;
                    }
                    if (booleanValue) {
                        wirelessChargerAuthenticator3.removeAuthenticationTimeOuts();
                        wirelessChargerAuthenticator3.mWirelessChargerAuthHandler
                                .sendEmptyMessageDelayed(5, 27000L);
                        wirelessChargerAuthenticator3.mPreparing = true;
                    }
                    Message message2 = new Message();
                    if (wirelessChargerAuthenticator3.mAuthenticationTask.getSessionState(
                                    wirelessChargerAuthenticator3.mCurrentSession)
                            == 7) {
                        Bundle m =
                                SystemUpdateManagerService$$ExternalSyntheticOutline0.m(
                                        4, "connectivity_type");
                        message2.obj = wirelessChargerAuthenticator3;
                        message2.setData(m);
                        wirelessChargerAuthenticator3.mAuthenticationTask.start(message2);
                    } else {
                        message2.obj = wirelessChargerAuthenticator3.mCurrentSession;
                        wirelessChargerAuthenticator3.mAuthenticationTask.start(message2);
                    }
                    wirelessChargerAuthenticator3.mWirelessChargerAuthWakeLock.release();
                    return;
                case 3:
                    WirelessChargerAuthenticator wirelessChargerAuthenticator4 =
                            WirelessChargerAuthenticator.this;
                    wirelessChargerAuthenticator4.getClass();
                    Log.i("SAccessoryManager_WirelessChargerAuthenticator", "handleAuthResponse");
                    wirelessChargerAuthenticator4.mWirelessChargerAuthWakeLock.acquire();
                    AuthenticationResult authenticationResult = (AuthenticationResult) message.obj;
                    wirelessChargerAuthenticator4.mResult.set(authenticationResult);
                    int i3 = authenticationResult.getResultBundle().getInt("reason");
                    wirelessChargerAuthenticator4.auth_result = i3;
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i3, "auth reason = ", "SAccessoryManager_WirelessChargerAuthenticator");
                    if (i3 != 0 && i3 != 1) {
                        if (i3 == 11 || i3 == 30) {
                            Log.d("SAccessoryManager_WirelessChargerAuthenticator", "do nothing..");
                        } else {
                            WirelessChargerAuthHandler wirelessChargerAuthHandler =
                                    wirelessChargerAuthenticator4.mWirelessChargerAuthHandler;
                            if (i3 == 31) {
                                wirelessChargerAuthenticator4.removeAuthenticationTimeOuts();
                                wirelessChargerAuthHandler.removeMessages(2);
                            } else if (i3 == 12) {
                                wirelessChargerAuthenticator4.removeAuthenticationTimeOuts();
                                wirelessChargerAuthHandler.removeMessages(2);
                            } else if (i3 == 13) {
                                if (wirelessChargerAuthenticator4.mRetryCounterWhenBusy >= 2
                                        || !wirelessChargerAuthHandler.hasMessages(5)) {
                                    wirelessChargerAuthenticator4.mRetryCounterWhenBusy = 0;
                                    wirelessChargerAuthenticator4.removeAuthenticationTimeOuts();
                                    wirelessChargerAuthHandler.removeMessages(2);
                                } else {
                                    wirelessChargerAuthenticator4.mRetryCounterWhenBusy++;
                                    if (wirelessChargerAuthenticator4.mSystemReady) {
                                        wirelessChargerAuthHandler.removeMessages(4);
                                        wirelessChargerAuthenticator4
                                                .removeAuthenticationTimeOuts();
                                        wirelessChargerAuthenticator4.mWirelessChargerAuthHandler
                                                .sendEmptyMessageDelayed(5, 60000L);
                                        wirelessChargerAuthHandler.sendEmptyMessageDelayed(
                                                2, 5000L);
                                    }
                                }
                            } else if (i3 == 90 && wirelessChargerAuthHandler.hasMessages(5)) {
                                wirelessChargerAuthenticator4.startAuthentication$2(300L, false);
                            }
                        }
                        wirelessChargerAuthenticator4.mWirelessChargerAuthWakeLock.release();
                        return;
                    }
                    wirelessChargerAuthenticator4.setWirelessChargerVerified(i3);
                    wirelessChargerAuthenticator4.removeAuthenticationTimeOuts();
                    WirelessChargerAuthHandler wirelessChargerAuthHandler2 =
                            wirelessChargerAuthenticator4.mWirelessChargerAuthHandler;
                    wirelessChargerAuthHandler2.removeMessages(2);
                    wirelessChargerAuthHandler2.sendEmptyMessageDelayed(4, 0L);
                    wirelessChargerAuthenticator4.mWirelessChargerAuthWakeLock.release();
                    return;
                case 4:
                    break;
                case 5:
                case 6:
                    WirelessChargerAuthenticator.this.mWirelessChargerAuthHandler.removeMessages(2);
                    if (message.what == 5) {
                        WirelessChargerAuthenticator.this.mFailuresCount++;
                        Log.e(
                                "SAccessoryManager_WirelessChargerAuthenticator",
                                "authentication is timed out!");
                        break;
                    }
                    break;
                default:
                    return;
            }
            WirelessChargerAuthenticator wirelessChargerAuthenticator5 =
                    WirelessChargerAuthenticator.this;
            wirelessChargerAuthenticator5.mAuthenticationTask.stop(
                    wirelessChargerAuthenticator5.mCurrentSession);
            if (message.what == 6) {
                WirelessChargerAuthenticator.this.removeAuthenticationTimeOuts();
                z = true;
            }
            WirelessChargerAuthenticator wirelessChargerAuthenticator6 =
                    WirelessChargerAuthenticator.this;
            if (wirelessChargerAuthenticator6.wirelessChargerState == 1) {
                wirelessChargerAuthenticator6.setWirelessChargerVerified(101);
            } else if ((message.what == 5 && wirelessChargerAuthenticator6.mFailuresCount > 1)
                    || z) {
                int i4 = wirelessChargerAuthenticator6.auth_result;
                if (i4 == -2 && i4 == 101) {
                    wirelessChargerAuthenticator6.setWirelessChargerVerified(102);
                } else {
                    wirelessChargerAuthenticator6.setWirelessChargerVerified(i4);
                }
            }
            WirelessChargerAuthenticator.this.mResult.set(null);
        }
    }

    static {
        Debug.semIsProductDev();
    }

    public WirelessChargerAuthenticator(
            Context context, Looper looper, SAccessoryManager.AnonymousClass1 anonymousClass1) {
        this.mContext = context;
        this.mAuthenticationTask = anonymousClass1;
        this.mWirelessChargerAuthHandler = new WirelessChargerAuthHandler(looper);
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current WirelessChargerAuthenticator state:");
        AuthenticationResult authenticationResult = (AuthenticationResult) this.mResult.get();
        if (authenticationResult != null) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  auth reason = "),
                    authenticationResult.mReason,
                    printWriter);
        }
        printWriter.println("  Historical authentication: ");
        for (int i = 0; i < this.mAuthenticationHistory.size(); i++) {
            printWriter.println("    " + ((String) this.mAuthenticationHistory.get(i)));
        }
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter, "  mLastAuthenticationTime = 0", "  mLastAttachTime = ");
        m$1.append(TimeUtils.logTimeOfDay(this.mLastAttachTime));
        printWriter.println(m$1.toString());
        printWriter.println("  mLastDetachTime = " + TimeUtils.logTimeOfDay(this.mLastDetachTime));
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationComplted(AuthenticationResult authenticationResult) {
        WirelessChargerAuthHandler wirelessChargerAuthHandler = this.mWirelessChargerAuthHandler;
        Message obtainMessage = wirelessChargerAuthHandler.obtainMessage(3);
        obtainMessage.obj = authenticationResult;
        wirelessChargerAuthHandler.sendMessage(obtainMessage);
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStarted() {
        this.mWirelessChargerAuthHandler.post(new AnonymousClass1(this, 0));
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStarting(final AuthenticationSession authenticationSession) {
        this.mWirelessChargerAuthHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.accessory.manager.authentication.wirelesscharger.WirelessChargerAuthenticator.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i = WirelessChargerAuthenticator.$r8$clinit;
                        Log.i(
                                "SAccessoryManager_WirelessChargerAuthenticator",
                                "onAuthenticationStarting");
                        WirelessChargerAuthenticator.this.mCurrentSession = authenticationSession;
                    }
                });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStopped() {
        this.mWirelessChargerAuthHandler.post(new AnonymousClass1(this, 1));
    }

    public final void onWirelessChargerConnected(boolean z) {
        long j = 0;
        if (z) {
            if (this.mAuthenticationStarted) {
                Log.w(
                        "SAccessoryManager_WirelessChargerAuthenticator",
                        "Authentication already starts");
                return;
            }
            this.mAuthenticationStarted = true;
            this.wirelessChargerState = 0;
            this.mWirelessChargerAttachedWhenNanos = System.currentTimeMillis();
            this.mLastAttachTime = System.currentTimeMillis();
            startAuthentication$2(0L, true);
            return;
        }
        this.wirelessChargerState = 1;
        long j2 = this.mWirelessChargerAttachedWhenNanos;
        if (j2 != 0 && 0 - j2 < 500000000) {
            j = 500;
        }
        this.mSafetyDetachTimeoutWakeLock.acquire(1000L);
        this.mLastDetachTime = System.currentTimeMillis();
        this.mFailuresCount = 0;
        this.mAuthenticationStarted = false;
        removeAuthenticationTimeOuts();
        WirelessChargerAuthHandler wirelessChargerAuthHandler = this.mWirelessChargerAuthHandler;
        wirelessChargerAuthHandler.removeMessages(2);
        wirelessChargerAuthHandler.sendEmptyMessageDelayed(4, j);
    }

    public final void removeAuthenticationTimeOuts() {
        this.mPreparing = false;
        this.mWirelessChargerAuthHandler.removeMessages(5);
    }

    public final void setWirelessChargerVerified(int i) {
        Log.i("SAccessoryManager_WirelessChargerAuthenticator", "setWirelessChargerVerified");
        if (i != 28) {
            int i2 = 5;
            for (boolean z = false; i2 >= 0 && !z; z = true) {
                int wirelesscharger_open = this.mMsgHelper.wirelesscharger_open();
                byte[] bArr = {1};
                byte[] bArr2 = {2};
                AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                        i,
                        wirelesscharger_open,
                        "reason = ",
                        ", open batt_misc ret = ",
                        "SAccessoryManager_WirelessChargerAuthenticator");
                if (wirelesscharger_open < 0) {
                    Log.e("SAccessoryManager_WirelessChargerAuthenticator", "open fail");
                    i = 102;
                }
                if (i == 0) {
                    Log.i("SAccessoryManager_WirelessChargerAuthenticator", "write success");
                    this.mMsgHelper.ioctl_longDataWrite_batt(bArr);
                } else if (i != 101) {
                    Log.i("SAccessoryManager_WirelessChargerAuthenticator", "write fail");
                    this.mMsgHelper.ioctl_longDataWrite_batt(bArr2);
                }
                Log.d("SAccessoryManager_WirelessChargerAuthenticator", "close batt_misc");
                this.mMsgHelper.ccic_close();
                i2--;
            }
        }
        if (this.mAuthenticationHistory.size() > 60) {
            this.mAuthenticationHistory.removeFirst();
        }
        this.mAuthenticationHistory.add(
                String.valueOf(i) + "/" + TimeUtils.logTimeOfDay(System.currentTimeMillis()));
        this.auth_result = -2;
        this.mAuthenticationStarted = false;
    }

    public final void startAuthentication$2(long j, boolean z) {
        if (this.mSystemReady) {
            WirelessChargerAuthHandler wirelessChargerAuthHandler =
                    this.mWirelessChargerAuthHandler;
            wirelessChargerAuthHandler.removeMessages(4);
            Message message = new Message();
            message.obj = Boolean.valueOf(z);
            message.what = 2;
            wirelessChargerAuthHandler.sendMessageDelayed(message, j);
        }
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public final void systemReady() {
        this.mWirelessChargerAuthHandler.sendEmptyMessage(1);
    }
}
