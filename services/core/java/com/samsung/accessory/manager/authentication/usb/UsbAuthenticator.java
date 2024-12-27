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

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.accessory.manager.authentication.cover.CoverInfo;
import com.samsung.android.sepunion.SemUnionManagerLocal;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsbAuthenticator extends LocalAuthenticator
        implements SAccessoryManager.AuthenticationResultCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SAccessoryManager.AnonymousClass1 mAuthenticationTask;
    public final Context mContext;
    public PowerManager mPowerManager;
    public PowerManager.WakeLock mSafetyDetachTimeoutWakeLock;
    public SemUnionManagerLocal mUnionManagerLocal;
    public final UsbAuthHandler mUsbAuthHandler;
    public PowerManager.WakeLock mUsbAuthWakeLock;
    public long mLastAttachTime = 0;
    public long mLastDetachTime = 0;
    public long mUsbAttachedWhenNanos = 0;
    public int mRetryCounterWhenBusy = 0;
    public int mFailuresCount = 0;
    public byte[] mUriData = null;
    public boolean mSystemReady = false;
    public volatile boolean mPreparing = false;
    public boolean mIsFactoryBinary = false;
    public int usbState = 1;
    public int mCoverType = 2;
    public final AtomicReference mResult = new AtomicReference();
    public final LinkedList mAuthenticationHistory = new LinkedList();
    public AuthenticationSession mCurrentSession = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.accessory.manager.authentication.usb.UsbAuthenticator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UsbAuthenticator this$0;

        public /* synthetic */ AnonymousClass1(UsbAuthenticator usbAuthenticator, int i) {
            this.$r8$classId = i;
            this.this$0 = usbAuthenticator;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    int i = UsbAuthenticator.$r8$clinit;
                    Log.i("SAccessoryManager_UsbAuthenticator", "onAuthenticationStarted");
                    if (this.this$0.mPreparing && this.this$0.mUsbAuthHandler.hasMessages(5)) {
                        this.this$0.mPreparing = false;
                        UsbAuthenticator usbAuthenticator = this.this$0;
                        usbAuthenticator.removeAuthenticationTimeOuts();
                        usbAuthenticator.mUsbAuthHandler.sendEmptyMessageDelayed(5, 10000L);
                        break;
                    }
                    break;
                default:
                    int i2 = UsbAuthenticator.$r8$clinit;
                    Log.i("SAccessoryManager_UsbAuthenticator", "onAuthenticationStopped");
                    UsbAuthenticator usbAuthenticator2 = this.this$0;
                    usbAuthenticator2.mCurrentSession = null;
                    usbAuthenticator2.mRetryCounterWhenBusy = 0;
                    if (usbAuthenticator2.mUsbAuthHandler.hasMessages(5)) {
                        this.this$0.startAuthentication$1(0L, false);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UsbAuthHandler extends Handler {
        public UsbAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            int i = UsbAuthenticator.$r8$clinit;
            StringBuilder sb = new StringBuilder("handleMessage = ");
            int i2 = message.what;
            UsbAuthenticator usbAuthenticator = UsbAuthenticator.this;
            usbAuthenticator.getClass();
            sb.append(LocalAuthenticator.convertMsg(i2));
            Log.i("SAccessoryManager_UsbAuthenticator", sb.toString());
            boolean z2 = false;
            boolean z3 = true;
            switch (message.what) {
                case 1:
                    Log.i("SAccessoryManager_UsbAuthenticator", "Initialize usb authenticator");
                    usbAuthenticator.mIsFactoryBinary = FactoryTest.isFactoryBinary();
                    PowerManager powerManager =
                            (PowerManager) usbAuthenticator.mContext.getSystemService("power");
                    usbAuthenticator.mPowerManager = powerManager;
                    PowerManager.WakeLock newWakeLock =
                            powerManager.newWakeLock(1, "SAccessoryManager_UsbAuthenticator");
                    usbAuthenticator.mUsbAuthWakeLock = newWakeLock;
                    newWakeLock.setReferenceCounted(false);
                    PowerManager.WakeLock newWakeLock2 =
                            usbAuthenticator.mPowerManager.newWakeLock(
                                    1, "SAccessoryManager_UsbAuthenticatorDetachTimeoutWakeLock");
                    usbAuthenticator.mSafetyDetachTimeoutWakeLock = newWakeLock2;
                    newWakeLock2.setReferenceCounted(false);
                    usbAuthenticator.mSystemReady = true;
                    usbAuthenticator.mAuthenticationTask.authenticationReady();
                    return;
                case 2:
                    Log.i("SAccessoryManager_UsbAuthenticator", "handleAuthStart");
                    usbAuthenticator.mUsbAuthWakeLock.acquire();
                    AuthenticationSession authenticationSession = usbAuthenticator.mCurrentSession;
                    SAccessoryManager.AnonymousClass1 anonymousClass1 =
                            usbAuthenticator.mAuthenticationTask;
                    int sessionState = anonymousClass1.getSessionState(authenticationSession);
                    if (sessionState != 7 && sessionState != 1) {
                        Log.e("SAccessoryManager_UsbAuthenticator", "session is busy");
                        usbAuthenticator.mUsbAuthWakeLock.release();
                        return;
                    }
                    Message message2 = new Message();
                    if (anonymousClass1.getSessionState(usbAuthenticator.mCurrentSession) == 7) {
                        Bundle m =
                                SystemUpdateManagerService$$ExternalSyntheticOutline0.m(
                                        3, "connectivity_type");
                        message2.obj = usbAuthenticator;
                        message2.setData(m);
                        anonymousClass1.start(message2);
                    } else {
                        message2.obj = usbAuthenticator.mCurrentSession;
                        anonymousClass1.start(message2);
                    }
                    usbAuthenticator.mUsbAuthWakeLock.release();
                    return;
                case 3:
                    Log.i("SAccessoryManager_UsbAuthenticator", "handleAuthResponse");
                    usbAuthenticator.mUsbAuthWakeLock.acquire();
                    AuthenticationResult authenticationResult = (AuthenticationResult) message.obj;
                    usbAuthenticator.mResult.set(authenticationResult);
                    int i3 = authenticationResult.getResultBundle().getInt("reason");
                    Log.i("SAccessoryManager_UsbAuthenticator", "auth reason = " + i3);
                    CoverInfo coverInfo = new CoverInfo(authenticationResult.mExtraID);
                    if (i3 == 0) {
                        z2 = true;
                    } else {
                        if (i3 != 1) {
                            if (i3 == 11 || i3 == 30) {
                                Log.i("SAccessoryManager_UsbAuthenticator", "do nothing..");
                            } else {
                                UsbAuthHandler usbAuthHandler = usbAuthenticator.mUsbAuthHandler;
                                if (i3 == 31) {
                                    usbAuthenticator.removeAuthenticationTimeOuts();
                                    usbAuthHandler.removeMessages(2);
                                } else if (i3 == 12) {
                                    usbAuthenticator.removeAuthenticationTimeOuts();
                                    usbAuthHandler.removeMessages(2);
                                } else if (i3 == 13) {
                                    if (usbAuthenticator.mRetryCounterWhenBusy >= 2
                                            || !usbAuthHandler.hasMessages(5)) {
                                        usbAuthenticator.mRetryCounterWhenBusy = 0;
                                        usbAuthenticator.removeAuthenticationTimeOuts();
                                        usbAuthHandler.removeMessages(2);
                                    } else {
                                        usbAuthenticator.mRetryCounterWhenBusy++;
                                        if (usbAuthenticator.mSystemReady) {
                                            usbAuthHandler.removeMessages(4);
                                            usbAuthenticator.removeAuthenticationTimeOuts();
                                            usbAuthenticator.mUsbAuthHandler
                                                    .sendEmptyMessageDelayed(5, 10000L);
                                            usbAuthHandler.sendEmptyMessageDelayed(2, 5000L);
                                        }
                                    }
                                } else if (i3 == 90 && usbAuthHandler.hasMessages(5)) {
                                    usbAuthenticator.startAuthentication$1(300L, false);
                                }
                            }
                            z3 = false;
                        }
                        z3 = false;
                        z2 = true;
                    }
                    if (usbAuthenticator.mAuthenticationHistory.size() > 60) {
                        usbAuthenticator.mAuthenticationHistory.removeFirst();
                    }
                    usbAuthenticator.mAuthenticationHistory.add(
                            String.valueOf(i3)
                                    + "/"
                                    + TimeUtils.logTimeOfDay(System.currentTimeMillis()));
                    if (z2) {
                        usbAuthenticator.setUsbVerified(z3, coverInfo, authenticationResult);
                        usbAuthenticator.removeAuthenticationTimeOuts();
                        UsbAuthHandler usbAuthHandler2 = usbAuthenticator.mUsbAuthHandler;
                        usbAuthHandler2.removeMessages(2);
                        usbAuthHandler2.sendEmptyMessageDelayed(4, 0L);
                    }
                    usbAuthenticator.mUsbAuthWakeLock.release();
                    return;
                case 4:
                    break;
                case 5:
                case 6:
                    usbAuthenticator.mUsbAuthHandler.removeMessages(2);
                    if (message.what == 5) {
                        usbAuthenticator.mFailuresCount++;
                        Log.e("SAccessoryManager_UsbAuthenticator", "authentication is timed out!");
                        break;
                    }
                    break;
                default:
                    return;
            }
            usbAuthenticator.mAuthenticationTask.stop(usbAuthenticator.mCurrentSession);
            if (message.what == 6) {
                usbAuthenticator.removeAuthenticationTimeOuts();
                z = true;
            } else {
                z = false;
            }
            if (usbAuthenticator.usbState == 1
                    || ((message.what == 5 && usbAuthenticator.mFailuresCount > 1) || z)) {
                usbAuthenticator.setUsbVerified(false, null, null);
                usbAuthenticator.mResult.set(null);
            }
        }
    }

    static {
        Debug.semIsProductDev();
    }

    public UsbAuthenticator(
            Context context, Looper looper, SAccessoryManager.AnonymousClass1 anonymousClass1) {
        this.mContext = context;
        this.mAuthenticationTask = anonymousClass1;
        this.mUsbAuthHandler = new UsbAuthHandler(looper);
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current UsbAuthenticator state:");
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

    public final void notifyFriendsStateChanged$1(boolean z, byte[] bArr, byte[] bArr2) {
        if (this.mUnionManagerLocal == null) {
            this.mUnionManagerLocal =
                    (SemUnionManagerLocal) LocalServices.getService(SemUnionManagerLocal.class);
        }
        SemUnionManagerLocal semUnionManagerLocal = this.mUnionManagerLocal;
        if (semUnionManagerLocal != null) {
            semUnionManagerLocal.accessoryStateChanged(z, bArr, bArr2);
            try {
                Intent component =
                        new Intent()
                                .setComponent(
                                        new ComponentName(
                                                "com.sec.enterprise.knox.cloudmdm.smdms",
                                                "com.sec.enterprise.knox.cloudmdm.smdms.core.AccessoryStateChangeReceiver"));
                component.putExtra("accessoryType", this.mCoverType);
                component.putExtra("accessoryState", z ? 1001 : 1002);
                component.putExtra("accessoryUid", bArr);
                component.putExtra("from", "com.samsung.accessory.manager.authentication");
                this.mContext.sendBroadcast(component);
            } catch (Exception unused) {
                Log.d("SAccessoryManager_UsbAuthenticator", "error during starting KES service");
            }
        }
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationComplted(AuthenticationResult authenticationResult) {
        UsbAuthHandler usbAuthHandler = this.mUsbAuthHandler;
        Message obtainMessage = usbAuthHandler.obtainMessage(3);
        obtainMessage.obj = authenticationResult;
        usbAuthHandler.sendMessage(obtainMessage);
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStarted() {
        this.mUsbAuthHandler.post(new AnonymousClass1(this, 0));
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStarting(final AuthenticationSession authenticationSession) {
        this.mUsbAuthHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.accessory.manager.authentication.usb.UsbAuthenticator.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i = UsbAuthenticator.$r8$clinit;
                        Log.i("SAccessoryManager_UsbAuthenticator", "onAuthenticationStarting");
                        UsbAuthenticator.this.mCurrentSession = authenticationSession;
                    }
                });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStopped() {
        this.mUsbAuthHandler.post(new AnonymousClass1(this, 1));
    }

    public final void onUsbAttached(boolean z) {
        if (this.mIsFactoryBinary || !this.mSystemReady) {
            return;
        }
        long j = 0;
        if (z) {
            this.usbState = 0;
            this.mUsbAttachedWhenNanos = 0L;
            this.mLastAttachTime = System.currentTimeMillis();
            startAuthentication$1(0L, true);
            return;
        }
        this.usbState = 1;
        long j2 = this.mUsbAttachedWhenNanos;
        if (j2 != 0 && 0 - j2 < 500000000) {
            j = 500;
        }
        this.mSafetyDetachTimeoutWakeLock.acquire(1000L);
        this.mLastDetachTime = System.currentTimeMillis();
        this.mFailuresCount = 0;
        removeAuthenticationTimeOuts();
        UsbAuthHandler usbAuthHandler = this.mUsbAuthHandler;
        usbAuthHandler.removeMessages(2);
        usbAuthHandler.sendEmptyMessageDelayed(4, j);
    }

    public final void removeAuthenticationTimeOuts() {
        this.mPreparing = false;
        this.mUsbAuthHandler.removeMessages(5);
    }

    public final void setUsbVerified(
            boolean z, CoverInfo coverInfo, AuthenticationResult authenticationResult) {
        byte b;
        Log.i("SAccessoryManager_UsbAuthenticator", "setUsbVerified");
        if (coverInfo != null) {
            this.mCoverType = coverInfo.type;
        }
        if (!z) {
            notifyFriendsStateChanged$1(false, this.mUriData, null);
            this.mUriData = null;
            return;
        }
        byte[] bArr = authenticationResult.mByteArrayManagerURI;
        this.mUriData = bArr;
        if (coverInfo == null
                || coverInfo.url == 0
                || bArr == null
                || bArr.length < 2
                || (b = bArr[1]) < 17
                || b >= 32) {
            return;
        }
        notifyFriendsStateChanged$1(true, bArr, coverInfo.chip_id);
    }

    public final void startAuthentication$1(long j, boolean z) {
        if (this.mSystemReady) {
            this.mUsbAuthHandler.removeMessages(4);
            if (z) {
                removeAuthenticationTimeOuts();
                this.mUsbAuthHandler.sendEmptyMessageDelayed(5, 27000L);
                this.mPreparing = true;
            }
            this.mUsbAuthHandler.sendEmptyMessageDelayed(2, j);
        }
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public final void systemReady() {
        this.mUsbAuthHandler.sendEmptyMessage(1);
    }
}
