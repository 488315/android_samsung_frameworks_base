package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.provider.Settings;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.nfc.adapter.SamsungNfcAdapter;
import com.samsung.android.sepunion.Log;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GracefulNfcLedCoverController extends BaseNfcLedCoverController {
    public static int TEST;
    public static final byte[] VERSION_CHECK_COMMAND = {0, -94, 0, 0, 7, 7, 113, 0, 0, 0, -1, -1};
    public final Object mCallTimerLock;
    public String mFirmwareVersion;
    public final NfcLedCoverControllerHandler mHandler;
    public boolean mIsLedOn;
    public long mLastWcControlResetTime;
    public int mLedCoverStartRetryCount;
    public int mLedCoverTransceiveRetryCount;
    public final PowerManager.WakeLock mLedVersionCheckWakeLock;
    public final ArrayList mListeners;
    public final PowerManager.WakeLock mPollTouchWakeLock;
    public boolean mPollingTouchEvents;
    public final PowerManager.WakeLock mSendLedDataWakeLock;
    public final PowerManager.WakeLock mTouchResponseWakeLock;
    public int testCount;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NfcLedCoverControllerHandler extends Handler {
        public NfcLedCoverControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            byte b;
            byte[] bArr = null;
            boolean z = false;
            switch (message.what) {
                case 0:
                    GracefulNfcLedCoverController.this.clearRetryCountDelayedMsg();
                    break;
                case 1:
                case 5:
                default:
                    return;
                case 2:
                    GracefulNfcLedCoverController gracefulNfcLedCoverController =
                            GracefulNfcLedCoverController.this;
                    int i = message.arg1;
                    if (!gracefulNfcLedCoverController.mPollingTouchEvents) {
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                gracefulNfcLedCoverController.mPollTouchWakeLock);
                        return;
                    }
                    if (GracefulNfcLedCoverController.TEST > 0) {
                        gracefulNfcLedCoverController.testCount++;
                    }
                    byte[] buildNfcCoverLedData =
                            GracefulNfcLedCoverController.buildNfcCoverLedData(
                                    17, new byte[] {0, 0});
                    if (buildNfcCoverLedData == null) {
                        Log.e("CoverManager_GracefulNfcLedCoverController", "Invalid data");
                        return;
                    }
                    try {
                        if (GracefulNfcLedCoverController.TEST == 0) {
                            long currentTimeMillis = System.currentTimeMillis();
                            if (currentTimeMillis
                                            - gracefulNfcLedCoverController.mLastWcControlResetTime
                                    > 30000) {
                                gracefulNfcLedCoverController.mSamsungNfcAdapter.getClass();
                                SamsungNfcAdapter.setWirelessChargeEnabled(false);
                                gracefulNfcLedCoverController.mLastWcControlResetTime =
                                        currentTimeMillis;
                            }
                            gracefulNfcLedCoverController.mSamsungNfcAdapter.getClass();
                            bArr =
                                    SamsungNfcAdapter.transceiveDataWithLedCover(
                                            buildNfcCoverLedData);
                        }
                    } catch (Exception e) {
                        Log.e(
                                "CoverManager_GracefulNfcLedCoverController",
                                "Error sending data to NFC",
                                e);
                    }
                    int i2 = GracefulNfcLedCoverController.TEST;
                    if (i2 > 0 && gracefulNfcLedCoverController.testCount > 19) {
                        bArr = new byte[6];
                        bArr[0] = 6;
                        bArr[1] = 17;
                        bArr[2] = 0;
                        if (i2 == 2) {
                            bArr[3] = 1;
                        } else if (i2 == 1) {
                            bArr[3] = 2;
                        } else {
                            Log.e(
                                    "CoverManager_GracefulNfcLedCoverController",
                                    "Unknown test value: "
                                            + GracefulNfcLedCoverController.TEST
                                            + ", reject by default");
                            bArr[3] = 2;
                        }
                        bArr[4] = -1;
                        bArr[5] = -1;
                    }
                    NfcLedCoverControllerHandler nfcLedCoverControllerHandler =
                            gracefulNfcLedCoverController.mHandler;
                    if (bArr == null
                            || bArr.length < 6
                            || bArr[1] != 17
                            || !((b = bArr[3]) == 1 || b == 2)) {
                        Log.d(
                                "CoverManager_GracefulNfcLedCoverController",
                                "No touch event from LED cover, keep listening");
                        Message obtain = Message.obtain();
                        obtain.what = 2;
                        obtain.arg1 = i;
                        nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 100L);
                        return;
                    }
                    BaseNfcLedCoverController.acquireWakeLockWithPermission(
                            gracefulNfcLedCoverController.mTouchResponseWakeLock);
                    Message obtain2 = Message.obtain();
                    obtain2.what = 3;
                    obtain2.arg1 = i;
                    obtain2.arg2 = bArr[3];
                    nfcLedCoverControllerHandler.sendMessage(obtain2);
                    gracefulNfcLedCoverController.mPollingTouchEvents = false;
                    BaseNfcLedCoverController.releaseWakeLockWithPermission(
                            gracefulNfcLedCoverController.mPollTouchWakeLock);
                    return;
                case 3:
                    GracefulNfcLedCoverController.m882$$Nest$mhandleEventResponse(
                            GracefulNfcLedCoverController.this, message.arg1, message.arg2);
                    return;
                case 4:
                    break;
                case 6:
                    GracefulNfcLedCoverController.m884$$Nest$mhandleSendPowerKeyToCover(
                            GracefulNfcLedCoverController.this);
                    return;
                case 7:
                    if (message.arg1 == 1) {
                        return;
                    }
                    GracefulNfcLedCoverController gracefulNfcLedCoverController2 =
                            GracefulNfcLedCoverController.this;
                    gracefulNfcLedCoverController2.getClass();
                    Log.d("CoverManager_GracefulNfcLedCoverController", "handleCoverDetached()");
                    synchronized (gracefulNfcLedCoverController2.mCallTimerLock) {
                        try {
                            gracefulNfcLedCoverController2.mHandler.removeCallbacksAndMessages(
                                    null);
                            BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                    gracefulNfcLedCoverController2.mSendLedDataWakeLock);
                            BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                    gracefulNfcLedCoverController2.mPollTouchWakeLock);
                            BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                    gracefulNfcLedCoverController2.mTouchResponseWakeLock);
                            gracefulNfcLedCoverController2.mPollingTouchEvents = false;
                            gracefulNfcLedCoverController2.clearRetryCountDelayedMsg();
                            if (gracefulNfcLedCoverController2.mNfcAdapter == null) {
                                gracefulNfcLedCoverController2.mNfcAdapter =
                                        NfcAdapter.getDefaultAdapter(
                                                gracefulNfcLedCoverController2.mContext);
                            }
                            if (gracefulNfcLedCoverController2.mNfcAdapter == null) {
                                Log.e(
                                        "CoverManager_BaseNfcLedCoverController",
                                        "Could not get NfcAdapter");
                            }
                            if (gracefulNfcLedCoverController2.mNfcAdapter != null) {
                                gracefulNfcLedCoverController2.stopLedCover();
                            }
                            gracefulNfcLedCoverController2.mFirmwareVersion = null;
                        } finally {
                        }
                    }
                    return;
                case 8:
                    GracefulNfcLedCoverController gracefulNfcLedCoverController3 =
                            GracefulNfcLedCoverController.this;
                    Bundle bundle = (Bundle) message.obj;
                    synchronized (gracefulNfcLedCoverController3.mListeners) {
                        try {
                            Iterator it = gracefulNfcLedCoverController3.mListeners.iterator();
                            while (it.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                                        (NfcLedTouchListenerInfo) it.next();
                                if (nfcLedTouchListenerInfo.type == 4) {
                                    nfcLedTouchListenerInfo.onSystemCoverEvent(2, bundle);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                case 9:
                    GracefulNfcLedCoverController gracefulNfcLedCoverController4 =
                            GracefulNfcLedCoverController.this;
                    Bundle bundle2 = (Bundle) message.obj;
                    synchronized (gracefulNfcLedCoverController4.mListeners) {
                        try {
                            Iterator it2 = gracefulNfcLedCoverController4.mListeners.iterator();
                            while (it2.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
                                        (NfcLedTouchListenerInfo) it2.next();
                                if (nfcLedTouchListenerInfo2.type == 4) {
                                    nfcLedTouchListenerInfo2.onSystemCoverEvent(3, bundle2);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                case 10:
                    GracefulNfcLedCoverController gracefulNfcLedCoverController5 =
                            GracefulNfcLedCoverController.this;
                    if (gracefulNfcLedCoverController5.mFirmwareVersion != null) {
                        Log.e(
                                "CoverManager_GracefulNfcLedCoverController",
                                "Firmware version already retrieved: "
                                        + gracefulNfcLedCoverController5.mFirmwareVersion);
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                gracefulNfcLedCoverController5.mLedVersionCheckWakeLock);
                        return;
                    }
                    if (gracefulNfcLedCoverController5.mIsLedOn) {
                        Log.d(
                                "CoverManager_GracefulNfcLedCoverController",
                                "Led is on, try checking version later");
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                gracefulNfcLedCoverController5.mLedVersionCheckWakeLock);
                        return;
                    }
                    gracefulNfcLedCoverController5.mSamsungNfcAdapter.getClass();
                    byte[] startLedCover = SamsungNfcAdapter.startLedCover();
                    boolean z2 = startLedCover != null && startLedCover.length > 1;
                    NfcLedCoverControllerHandler nfcLedCoverControllerHandler2 =
                            gracefulNfcLedCoverController5.mHandler;
                    if (!z2) {
                        gracefulNfcLedCoverController5.mSamsungNfcAdapter.getClass();
                        SamsungNfcAdapter.stopLedCover();
                        if (!nfcLedCoverControllerHandler2.hasMessages(4)) {
                            gracefulNfcLedCoverController5.mSamsungNfcAdapter.getClass();
                            SamsungNfcAdapter.setWirelessChargeEnabled(true);
                        }
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                gracefulNfcLedCoverController5.mLedVersionCheckWakeLock);
                        return;
                    }
                    SamsungNfcAdapter samsungNfcAdapter =
                            gracefulNfcLedCoverController5.mSamsungNfcAdapter;
                    byte[] bArr2 = GracefulNfcLedCoverController.VERSION_CHECK_COMMAND;
                    samsungNfcAdapter.getClass();
                    byte[] transceiveDataWithLedCover =
                            SamsungNfcAdapter.transceiveDataWithLedCover(bArr2);
                    Log.d(
                            "CoverManager_GracefulNfcLedCoverController",
                            "Verison check response: "
                                    + BaseNfcLedCoverController.getByteDataString(
                                            transceiveDataWithLedCover));
                    if (transceiveDataWithLedCover != null
                            && transceiveDataWithLedCover.length >= 7
                            && transceiveDataWithLedCover[0] == bArr2[5]
                            && transceiveDataWithLedCover[1] == bArr2[6]
                            && transceiveDataWithLedCover[2] == bArr2[7]
                            && transceiveDataWithLedCover[5] == bArr2[10]
                            && transceiveDataWithLedCover[6] == bArr2[11]) {
                        z = true;
                    }
                    if (z) {
                        gracefulNfcLedCoverController5.mFirmwareVersion =
                                String.format(
                                        "%02X %02X",
                                        Byte.valueOf(transceiveDataWithLedCover[3]),
                                        Byte.valueOf(transceiveDataWithLedCover[4]));
                    }
                    if (z) {
                        Log.e(
                                "CoverManager_GracefulNfcLedCoverController",
                                "Firmware version retrieved: "
                                        + gracefulNfcLedCoverController5.mFirmwareVersion);
                        Settings.Secure.putString(
                                gracefulNfcLedCoverController5.mContext.getContentResolver(),
                                "led_cover_firmware_version",
                                gracefulNfcLedCoverController5.mFirmwareVersion);
                    }
                    gracefulNfcLedCoverController5.mSamsungNfcAdapter.getClass();
                    SamsungNfcAdapter.stopLedCover();
                    if (!nfcLedCoverControllerHandler2.hasMessages(4)) {
                        gracefulNfcLedCoverController5.mSamsungNfcAdapter.getClass();
                        SamsungNfcAdapter.setWirelessChargeEnabled(true);
                    }
                    BaseNfcLedCoverController.releaseWakeLockWithPermission(
                            gracefulNfcLedCoverController5.mLedVersionCheckWakeLock);
                    return;
                case 11:
                    GracefulNfcLedCoverController gracefulNfcLedCoverController6 =
                            GracefulNfcLedCoverController.this;
                    Bundle bundle3 = (Bundle) message.obj;
                    synchronized (gracefulNfcLedCoverController6.mListeners) {
                        try {
                            Iterator it3 = gracefulNfcLedCoverController6.mListeners.iterator();
                            while (it3.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo3 =
                                        (NfcLedTouchListenerInfo) it3.next();
                                if (nfcLedTouchListenerInfo3.type == 4) {
                                    nfcLedTouchListenerInfo3.onSystemCoverEvent(4, bundle3);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
            }
            GracefulNfcLedCoverController.m883$$Nest$mhandleSendDataToNfcLedCover(
                    GracefulNfcLedCoverController.this, message.arg1, (byte[]) message.obj);
            if (GracefulNfcLedCoverController.this.mHandler.hasMessages(0)
                    || GracefulNfcLedCoverController.this.mHandler.hasMessages(4)) {
                return;
            }
            BaseNfcLedCoverController.releaseWakeLockWithPermission(
                    GracefulNfcLedCoverController.this.mSendLedDataWakeLock);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NfcLedTouchListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int type;
        public final int uid;

        public NfcLedTouchListenerInfo(
                IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
            this.type = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.v(
                    "CoverManager_GracefulNfcLedCoverController",
                    "binderDied : binder = " + this.token);
            synchronized (GracefulNfcLedCoverController.this.mListeners) {
                GracefulNfcLedCoverController.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final void onSystemCoverEvent(int i, Bundle bundle) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w(
                        "CoverManager_GracefulNfcLedCoverController",
                        "null listener received TouchReject!");
                return;
            }
            try {
                INfcLedCoverTouchListenerCallback asInterface =
                        INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    asInterface.onSystemCoverEvent(i, bundle);
                }
            } catch (RemoteException e) {
                Log.e(
                        "CoverManager_GracefulNfcLedCoverController",
                        "Failed onSystemCoverEvent callback",
                        e);
            }
        }
    }

    /* renamed from: -$$Nest$mhandleEventResponse, reason: not valid java name */
    public static void m882$$Nest$mhandleEventResponse(
            GracefulNfcLedCoverController gracefulNfcLedCoverController, int i, int i2) {
        Iterator it;
        Iterator it2;
        gracefulNfcLedCoverController.getClass();
        Log.d(
                "CoverManager_GracefulNfcLedCoverController",
                "HandleEventResponse: type: " + i + " action: " + i2);
        if (i2 == 1) {
            Log.d("CoverManager_GracefulNfcLedCoverController", "Event touch: accept");
            synchronized (gracefulNfcLedCoverController.mListeners) {
                try {
                    it2 = gracefulNfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e) {
                    Log.e(
                            "CoverManager_GracefulNfcLedCoverController",
                            "Failed onCoverTouchAccept callback",
                            e);
                } finally {
                }
                while (it2.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it2.next();
                    if (i == nfcLedTouchListenerInfo.type) {
                        IBinder iBinder = nfcLedTouchListenerInfo.token;
                        if (iBinder == null) {
                            Log.w(
                                    "CoverManager_GracefulNfcLedCoverController",
                                    "null listener received TouchAccept!");
                        } else {
                            INfcLedCoverTouchListenerCallback asInterface =
                                    INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
                            if (asInterface != null) {
                                asInterface.onCoverTouchAccept();
                            }
                        }
                    }
                }
            }
        } else if (i2 == 2) {
            Log.d("CoverManager_GracefulNfcLedCoverController", "Event touch: reject");
            synchronized (gracefulNfcLedCoverController.mListeners) {
                try {
                    it = gracefulNfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e2) {
                    Log.e(
                            "CoverManager_GracefulNfcLedCoverController",
                            "Failed onCoverTouchReject callback",
                            e2);
                } finally {
                }
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
                            (NfcLedTouchListenerInfo) it.next();
                    if (i == nfcLedTouchListenerInfo2.type) {
                        IBinder iBinder2 = nfcLedTouchListenerInfo2.token;
                        if (iBinder2 == null) {
                            Log.w(
                                    "CoverManager_GracefulNfcLedCoverController",
                                    "null listener received TouchReject!");
                        } else {
                            INfcLedCoverTouchListenerCallback asInterface2 =
                                    INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder2);
                            if (asInterface2 != null) {
                                asInterface2.onCoverTouchReject();
                            }
                        }
                    }
                }
            }
        } else {
            Log.d("CoverManager_GracefulNfcLedCoverController", "Unknown event action: " + i2);
        }
        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                gracefulNfcLedCoverController.mTouchResponseWakeLock);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x015f A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:4:0x000b, B:6:0x0018, B:7:0x001f, B:13:0x002a, B:15:0x002e, B:16:0x003d, B:19:0x0040, B:21:0x0059, B:23:0x005f, B:24:0x009e, B:25:0x00a5, B:28:0x0092, B:29:0x00a8, B:31:0x00aa, B:33:0x00b8, B:34:0x00c2, B:35:0x00d5, B:38:0x00f3, B:40:0x0129, B:43:0x012c, B:44:0x0144, B:50:0x01c5, B:51:0x01d1, B:54:0x01c2, B:55:0x015b, B:57:0x015f, B:58:0x0161, B:66:0x0190, B:68:0x019b, B:69:0x01aa, B:70:0x01a8, B:72:0x01b4, B:74:0x01b7, B:76:0x01bb, B:77:0x01d3, B:78:0x01ee, B:80:0x0171, B:82:0x0174, B:86:0x017d, B:88:0x0180, B:96:0x00de, B:100:0x00e6, B:102:0x00eb, B:104:0x0134, B:107:0x0137, B:110:0x00cd), top: B:3:0x000b, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0190 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:4:0x000b, B:6:0x0018, B:7:0x001f, B:13:0x002a, B:15:0x002e, B:16:0x003d, B:19:0x0040, B:21:0x0059, B:23:0x005f, B:24:0x009e, B:25:0x00a5, B:28:0x0092, B:29:0x00a8, B:31:0x00aa, B:33:0x00b8, B:34:0x00c2, B:35:0x00d5, B:38:0x00f3, B:40:0x0129, B:43:0x012c, B:44:0x0144, B:50:0x01c5, B:51:0x01d1, B:54:0x01c2, B:55:0x015b, B:57:0x015f, B:58:0x0161, B:66:0x0190, B:68:0x019b, B:69:0x01aa, B:70:0x01a8, B:72:0x01b4, B:74:0x01b7, B:76:0x01bb, B:77:0x01d3, B:78:0x01ee, B:80:0x0171, B:82:0x0174, B:86:0x017d, B:88:0x0180, B:96:0x00de, B:100:0x00e6, B:102:0x00eb, B:104:0x0134, B:107:0x0137, B:110:0x00cd), top: B:3:0x000b, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01b2  */
    /* renamed from: -$$Nest$mhandleSendDataToNfcLedCover, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m883$$Nest$mhandleSendDataToNfcLedCover(
            com.android.server.sepunion.cover.GracefulNfcLedCoverController r17,
            int r18,
            byte[] r19) {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.sepunion.cover.GracefulNfcLedCoverController.m883$$Nest$mhandleSendDataToNfcLedCover(com.android.server.sepunion.cover.GracefulNfcLedCoverController,"
                    + " int, byte[]):void");
    }

    /* renamed from: -$$Nest$mhandleSendPowerKeyToCover, reason: not valid java name */
    public static void m884$$Nest$mhandleSendPowerKeyToCover(
            GracefulNfcLedCoverController gracefulNfcLedCoverController) {
        synchronized (gracefulNfcLedCoverController.mListeners) {
            try {
                Iterator it = gracefulNfcLedCoverController.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo.type == 4) {
                        nfcLedTouchListenerInfo.onSystemCoverEvent(1, null);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public GracefulNfcLedCoverController(Looper looper, Context context) {
        super(context);
        this.mIsLedOn = false;
        this.mCallTimerLock = new Object();
        this.mLedCoverStartRetryCount = 0;
        this.mLedCoverTransceiveRetryCount = 0;
        this.mListeners = new ArrayList();
        this.mHandler = new NfcLedCoverControllerHandler(looper);
        PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "send leddata");
        this.mSendLedDataWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 =
                this.mPowerManager.newWakeLock(1, "pollTouch ledcover");
        this.mPollTouchWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock3 =
                this.mPowerManager.newWakeLock(1, "touchResponse ledcover");
        this.mTouchResponseWakeLock = newWakeLock3;
        newWakeLock3.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock4 =
                this.mPowerManager.newWakeLock(1, "version check ledcover");
        this.mLedVersionCheckWakeLock = newWakeLock4;
        newWakeLock4.setReferenceCounted(false);
        TEST = Settings.Secure.getInt(context.getContentResolver(), "nfc_led_cover_test", 0);
    }

    public static byte[] buildNfcCoverLedData(int i, byte[] bArr) {
        if (bArr == null) {
            bArr = new byte[] {0, 0};
        }
        if (bArr.length < 2) {
            bArr = new byte[] {0, bArr[0]};
        }
        boolean z = bArr.length > 3;
        int length = bArr.length + 4;
        if (z) {
            length = bArr[0] & 255;
        }
        int i2 = 5 + length;
        byte[] bArr2 = new byte[i2];
        if (i2 < bArr.length + (z ? 5 : 7)) {
            Log.e(
                    "CoverManager_GracefulNfcLedCoverController",
                    "buildNfcCoverLedData : Invalid genData length data.length="
                            + String.valueOf(bArr.length)
                            + " genData.length="
                            + String.valueOf(i2));
            return null;
        }
        bArr2[0] = 0;
        bArr2[1] = -94;
        bArr2[2] = 0;
        bArr2[3] = 0;
        byte b = (byte) length;
        bArr2[4] = b;
        if (z) {
            System.arraycopy(bArr, 0, bArr2, 5, bArr.length);
        } else {
            bArr2[5] = b;
            bArr2[6] = (byte) i;
            System.arraycopy(bArr, 0, bArr2, 7, bArr.length);
            bArr2[length + 3] = -1;
            bArr2[length + 4] = -1;
        }
        return bArr2;
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void addLedNotification(Bundle bundle) {
        Log.d("CoverManager_GracefulNfcLedCoverController", "addLedNotification");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(8);
        obtainMessage.obj = bundle;
        nfcLedCoverControllerHandler.sendMessage(obtainMessage);
    }

    public final void clearRetryCountDelayedMsg() {
        this.mLedCoverStartRetryCount = 0;
        this.mLedCoverTransceiveRetryCount = 0;
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (nfcLedCoverControllerHandler.hasMessages(4)) {
            nfcLedCoverControllerHandler.removeMessages(4);
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current NfcLedCoverController state:");
        printWriter.print("  mIsLEDCoverAttached=");
        printWriter.println(this.mIsLedCoverAttached);
        printWriter.print("  TEST=");
        printWriter.println(TEST);
        printWriter.println("  ");
        printWriter.println(" Current NFC Callback state:");
        synchronized (this.mListeners) {
            try {
                printWriter.println("  Live callbacks (" + this.mListeners.size() + "):");
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo != null) {
                        printWriter.println(
                                "    "
                                        + nfcLedTouchListenerInfo.component
                                        + " (pid="
                                        + nfcLedTouchListenerInfo.pid
                                        + " uid="
                                        + nfcLedTouchListenerInfo.uid
                                        + " type="
                                        + nfcLedTouchListenerInfo.type
                                        + ")");
                    }
                }
                printWriter.println("  ");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleInvalidCommand(int i, byte[] bArr, byte[] bArr2) {
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (bArr2 != null && i != 18 && bArr2.length == 1) {
            byte b = bArr2[0];
            if (b != -32 && b != 1 && b != 3 && b != 5 && b != 6) {
                switch (b) {
                    case -80:
                    case -79:
                    case -78:
                        break;
                    default:
                        Log.e(
                                "CoverManager_GracefulNfcLedCoverController",
                                "Transceive error - unknown error value: " + ((int) bArr2[0]));
                        break;
                }
            }
            if (this.mLedCoverTransceiveRetryCount < 13) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "Repeat command ", " count: ");
                m.append(this.mLedCoverTransceiveRetryCount);
                Log.e("CoverManager_GracefulNfcLedCoverController", m.toString());
                this.mIsLedOn = false;
                this.mSamsungNfcAdapter.getClass();
                SamsungNfcAdapter.stopLedCover();
                Message obtain = Message.obtain();
                obtain.what = 4;
                obtain.arg1 = i;
                obtain.obj = bArr;
                nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 500L);
                this.mLedCoverTransceiveRetryCount++;
                return;
            }
            this.mLedCoverTransceiveRetryCount = 0;
            Log.e(
                    "CoverManager_GracefulNfcLedCoverController",
                    "Could not transceive command to cover so turn off led cover");
        }
        Log.d("CoverManager_GracefulNfcLedCoverController", "Sent done intent, fail transceive");
        stopLedCover();
        this.mPollingTouchEvents = false;
        nfcLedCoverControllerHandler.removeMessages(2);
        BaseNfcLedCoverController.releaseWakeLockWithPermission(this.mPollTouchWakeLock);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void registerNfcTouchListenerCallback(
            int i, IBinder iBinder, ComponentName componentName) {
        if (i < 0 || i > 5) {
            Log.e(
                    "CoverManager_GracefulNfcLedCoverController",
                    "Unsupported touch listener type: " + i);
            return;
        }
        synchronized (this.mListeners) {
            try {
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo != null
                            && iBinder.equals(nfcLedTouchListenerInfo.token)) {
                        Log.e(
                                "CoverManager_GracefulNfcLedCoverController",
                                "sendDataToNfcLedCover : duplicated listener handle");
                        return;
                    }
                }
                NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
                        new NfcLedTouchListenerInfo(
                                iBinder,
                                componentName,
                                Binder.getCallingPid(),
                                Binder.getCallingUid(),
                                i);
                iBinder.linkToDeath(nfcLedTouchListenerInfo2, 0);
                this.mListeners.add(nfcLedTouchListenerInfo2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void removeLedNotification(Bundle bundle) {
        Log.d("CoverManager_GracefulNfcLedCoverController", "removeLedNotification");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(9);
        obtainMessage.obj = bundle;
        nfcLedCoverControllerHandler.sendMessage(obtainMessage);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendDataToNfcLedCover(int i, byte[] bArr) {
        if (!this.mIsLedCoverAttached && !FactoryTest.isFactoryBinary()) {
            Log.d(
                    "CoverManager_GracefulNfcLedCoverController",
                    "sendDataToLedCover : Not attached LED Cover");
            return;
        }
        if (this.mSamsungNfcAdapter == null && getSamsungNfcAdapter() == null) {
            Log.d(
                    "CoverManager_GracefulNfcLedCoverController",
                    "sendDataToLedCover : Nfc Service not available");
            return;
        }
        BaseNfcLedCoverController.acquireWakeLockWithPermission(this.mSendLedDataWakeLock);
        Message obtain = Message.obtain();
        obtain.arg1 = i;
        obtain.obj = bArr;
        obtain.what = 0;
        clearRetryCountDelayedMsg();
        this.mHandler.sendMessage(obtain);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendPowerKeyToCover() {
        this.mHandler.sendEmptyMessage(6);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendSystemEvent(Bundle bundle) {
        int i = bundle.getInt("event_type");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (i != 0) {
            if (i != 1) {
                return;
            }
            Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(11);
            obtainMessage.obj = bundle;
            nfcLedCoverControllerHandler.sendMessage(obtainMessage);
            return;
        }
        int i2 = bundle.getInt("lcd_touch_listener_type", -1);
        int i3 = bundle.getInt("lcd_touch_listener_respone", -1);
        Message obtain = Message.obtain();
        obtain.what = 3;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        nfcLedCoverControllerHandler.sendMessage(obtain);
    }

    public final void stopLedCover() {
        this.mSamsungNfcAdapter.getClass();
        SamsungNfcAdapter.stopLedCover();
        this.mIsLedOn = false;
        this.mSamsungNfcAdapter.getClass();
        SamsungNfcAdapter.setWirelessChargeEnabled(true);
        if (FactoryTest.isFactoryBinary()) {
            BaseNfcLedCoverController.acquireWakeLockWithPermission(this.mLedVersionCheckWakeLock);
            this.mHandler.sendEmptyMessageDelayed(10, 500L);
        }
    }

    public final boolean tryStartLedCover(Intent intent) {
        Log.d(
                "CoverManager_GracefulNfcLedCoverController",
                "Trying to start NFC LED Cover mIsLedOn=" + String.valueOf(this.mIsLedOn));
        if (this.mIsLedOn) {
            Log.d("CoverManager_GracefulNfcLedCoverController", "NFC LED Cover already started");
        } else {
            if (this.mLedCoverStartRetryCount == 0) {
                this.mLastWcControlResetTime = System.currentTimeMillis();
                this.mSamsungNfcAdapter.getClass();
                SamsungNfcAdapter.setWirelessChargeEnabled(false);
            }
            this.mSamsungNfcAdapter.getClass();
            byte[] startLedCover = SamsungNfcAdapter.startLedCover();
            intent.putExtra("start_result", startLedCover);
            if (startLedCover == null || startLedCover.length <= 1) {
                Log.w(
                        "CoverManager_GracefulNfcLedCoverController",
                        "Failed to start NFC LED Cover");
                return false;
            }
            Log.d("CoverManager_GracefulNfcLedCoverController", "Started NFC LED Cover");
            this.mLedCoverStartRetryCount = 0;
            this.mIsLedOn = true;
        }
        return true;
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final boolean unRegisterNfcTouchListenerCallback(IBinder iBinder) {
        synchronized (this.mListeners) {
            try {
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo != null
                            && iBinder.equals(nfcLedTouchListenerInfo.token)) {
                        Log.e(
                                "CoverManager_GracefulNfcLedCoverController",
                                "remove listener: " + nfcLedTouchListenerInfo.pid);
                        this.mListeners.remove(nfcLedTouchListenerInfo);
                        iBinder.unlinkToDeath(nfcLedTouchListenerInfo, 0);
                        return true;
                    }
                }
                Log.e(
                        "CoverManager_GracefulNfcLedCoverController",
                        "UnregisterNfcTouchListener: listener does not exist");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void updateNfcLedCoverAttachStateLocked(int i, boolean z) {
        boolean z2 = z && i == 7;
        if (this.mIsLedCoverAttached != z2) {
            this.mIsLedCoverAttached = z2;
            NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
            if (!z2) {
                Log.d(
                        "CoverManager_GracefulNfcLedCoverController",
                        "NfcLedCover detached, start clearing all flags, messages, wakelocks");
                Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(7);
                obtainMessage.arg1 = z ? 1 : 0;
                nfcLedCoverControllerHandler.sendMessageAtFrontOfQueue(obtainMessage);
            } else if (FactoryTest.isFactoryBinary() && getSamsungNfcAdapter() != null) {
                BaseNfcLedCoverController.acquireWakeLockWithPermission(
                        this.mLedVersionCheckWakeLock);
                nfcLedCoverControllerHandler.sendEmptyMessage(10);
            }
        }
        TEST = Settings.Secure.getInt(this.mContext.getContentResolver(), "nfc_led_cover_test", 0);
    }
}
