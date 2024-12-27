package com.android.server.sepunion.cover;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.format.DateFormat;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.nfc.adapter.SamsungNfcAdapter;
import com.samsung.android.sepunion.Log;

import java.io.PrintWriter;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.ReentrantLock;

public final class NfcLedCoverController extends BaseNfcLedCoverController {
    public static final byte[] mResponsePattern = {
        HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, 6, -47, 0, -1, -1
    };
    public Timer mCallDurationTimer;
    public CallDurationTask mCallDurationUpdateTask;
    public boolean mCallInProgressDisplay;
    public long mCallStartTime;
    public final ReentrantLock mCallTimerLock;
    public boolean mCoverEventsDisabledForSamsungPay;
    public boolean mFactoryTransceiveResponseIntentSent;
    public final NfcLedCoverControllerHandler mHandler;
    public boolean mIsLedOn;
    public long mLedCoverRetryPostTime;
    public int mLedCoverStartRetryCount;
    public int mLedCoverTransceiveRetryCount;
    public final PowerManager.WakeLock mLedOnOffWakeLock;
    public final ArrayList mListeners;
    public Command mOngoingEvent;
    public final PowerManager.WakeLock mPollTouchWakeLock;
    public boolean mPollingTouchEvents;
    public int mPrevCommand;
    public final PowerManager.WakeLock mSendLedDataWakeLock;
    public int mTestCount;
    public int mTestMode;
    public final PowerManager.WakeLock mTouchResponseWakeLock;

    public final class CallDurationTask extends TimerTask {
        public byte prevSecond = -1;

        public CallDurationTask() {}

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            NfcLedCoverController.this.mCallTimerLock.lock();
            try {
                NfcLedCoverController nfcLedCoverController = NfcLedCoverController.this;
                if (nfcLedCoverController.mCallInProgressDisplay) {
                    byte[] m885$$Nest$mgetCallDuration =
                            NfcLedCoverController.m885$$Nest$mgetCallDuration(
                                    nfcLedCoverController);
                    if (m885$$Nest$mgetCallDuration[3] != this.prevSecond) {
                        NfcLedCoverController.m887$$Nest$mhandleSendDataToNfcLedCover(
                                NfcLedCoverController.this, 3, m885$$Nest$mgetCallDuration);
                        if (!NfcLedCoverController.this.mHandler.hasMessages(4)) {
                            this.prevSecond = m885$$Nest$mgetCallDuration[3];
                        }
                        NfcLedCoverController.this.clearRetryCountDelayedMsg$1();
                    }
                }
                NfcLedCoverController.this.mCallTimerLock.unlock();
            } catch (Throwable th) {
                NfcLedCoverController.this.mCallTimerLock.unlock();
                throw th;
            }
        }
    }

    public final class Command {
        public int code;
        public byte[] data;

        public Command(int i, byte[] bArr) {
            this.code = i;
            this.data = bArr;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Command [code=");
            sb.append(this.code);
            sb.append(", data=");
            byte[] bArr = this.data;
            NfcLedCoverController.this.getClass();
            sb.append(BaseNfcLedCoverController.getByteDataString(bArr));
            sb.append("]");
            return sb.toString();
        }
    }

    public final class NfcLedCoverControllerHandler extends Handler {
        public NfcLedCoverControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            byte b;
            Command command;
            NfcLedCoverController nfcLedCoverController;
            byte[] bArr = null;
            switch (message.what) {
                case 0:
                    NfcLedCoverController.this.clearRetryCountDelayedMsg$1();
                    break;
                case 1:
                    NfcLedCoverController.this.clearRetryCountDelayedMsg$1();
                    NfcLedCoverController nfcLedCoverController2 = NfcLedCoverController.this;
                    if (nfcLedCoverController2.mPrevCommand == 12) {
                        nfcLedCoverController2.sendDataToNfcLedCover(15, null);
                    } else {
                        NfcLedCoverController.m887$$Nest$mhandleSendDataToNfcLedCover(
                                nfcLedCoverController2, 18, null);
                    }
                    BaseNfcLedCoverController.releaseWakeLockWithPermission(
                            NfcLedCoverController.this.mLedOnOffWakeLock);
                    return;
                case 2:
                    NfcLedCoverController nfcLedCoverController3 = NfcLedCoverController.this;
                    int i = message.arg1;
                    if (!nfcLedCoverController3.mPollingTouchEvents) {
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                nfcLedCoverController3.mPollTouchWakeLock);
                        return;
                    }
                    if (nfcLedCoverController3.mTestMode > 0) {
                        nfcLedCoverController3.mTestCount++;
                    }
                    byte[] buildNfcCoverLedData$1 =
                            NfcLedCoverController.buildNfcCoverLedData$1(17, new byte[] {0});
                    try {
                        if (nfcLedCoverController3.mTestMode == 0) {
                            nfcLedCoverController3.mSamsungNfcAdapter.getClass();
                            bArr =
                                    SamsungNfcAdapter.transceiveDataWithLedCover(
                                            buildNfcCoverLedData$1);
                        }
                    } catch (Exception e) {
                        Log.e("CoverManager_NfcLedCoverController", "Error sending data to NFC", e);
                    }
                    int i2 = nfcLedCoverController3.mTestMode;
                    if (i2 > 0 && nfcLedCoverController3.mTestCount > 19) {
                        bArr = new byte[5];
                        bArr[1] = 0;
                        bArr[0] = 0;
                        bArr[2] = -47;
                        bArr[3] = 17;
                        if (i2 == 2) {
                            bArr[4] = 1;
                        } else if (i2 == 1) {
                            bArr[4] = 2;
                        } else {
                            Log.e(
                                    "CoverManager_NfcLedCoverController",
                                    "Unknown test value: "
                                            + nfcLedCoverController3.mTestMode
                                            + ", reject by default");
                            bArr[4] = 2;
                        }
                    }
                    NfcLedCoverControllerHandler nfcLedCoverControllerHandler =
                            nfcLedCoverController3.mHandler;
                    if (bArr == null
                            || bArr.length < 5
                            || bArr[2] != -47
                            || bArr[3] != 17
                            || ((b = bArr[4]) != 1 && b != 2)) {
                        Log.d(
                                "CoverManager_NfcLedCoverController",
                                "No touch event from LED cover, keep listening");
                        Message obtain = Message.obtain();
                        obtain.what = 2;
                        obtain.arg1 = i;
                        nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 100L);
                        return;
                    }
                    BaseNfcLedCoverController.acquireWakeLockWithPermission(
                            nfcLedCoverController3.mTouchResponseWakeLock);
                    Message obtain2 = Message.obtain();
                    obtain2.what = 3;
                    obtain2.arg1 = i;
                    obtain2.arg2 = bArr[4];
                    nfcLedCoverControllerHandler.sendMessage(obtain2);
                    nfcLedCoverController3.mPollingTouchEvents = false;
                    BaseNfcLedCoverController.releaseWakeLockWithPermission(
                            nfcLedCoverController3.mPollTouchWakeLock);
                    return;
                case 3:
                    NfcLedCoverController.m886$$Nest$mhandleEventResponse(
                            NfcLedCoverController.this, message.arg1, message.arg2);
                    return;
                case 4:
                    break;
                case 5:
                    NfcLedCoverController nfcLedCoverController4 = NfcLedCoverController.this;
                    int i3 = message.arg1;
                    nfcLedCoverController4.getClass();
                    int i4 = i3 != 65534 ? i3 : 3;
                    if (i4 == 0
                            || ((command = nfcLedCoverController4.mOngoingEvent) != null
                                    && command.code == i4)) {
                        nfcLedCoverController4.mOngoingEvent = null;
                    }
                    if (NfcLedCoverController.this.mHandler.hasMessages(0)
                            || NfcLedCoverController.this.mHandler.hasMessages(4)
                            || NfcLedCoverController.this.mHandler.hasMessages(5)) {
                        return;
                    }
                    BaseNfcLedCoverController.releaseWakeLockWithPermission(
                            NfcLedCoverController.this.mSendLedDataWakeLock);
                    return;
                case 6:
                    nfcLedCoverController = NfcLedCoverController.this;
                    synchronized (nfcLedCoverController.mListeners) {
                        try {
                            Iterator it = nfcLedCoverController.mListeners.iterator();
                            while (it.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                                        (NfcLedTouchListenerInfo) it.next();
                                if (nfcLedTouchListenerInfo.type == 4) {
                                    nfcLedTouchListenerInfo.onSystemCoverEvent(1, null);
                                }
                            }
                        } finally {
                        }
                    }
                    byte[] bArr2 = {0};
                    if (nfcLedCoverController.mIsLedOn) {
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                nfcLedCoverController.mLedOnOffWakeLock);
                        nfcLedCoverController.sendDataToNfcLedCover(18, bArr2);
                        return;
                    }
                    nfcLedCoverController.mCallTimerLock.lock();
                    try {
                        Command command2 = nfcLedCoverController.mOngoingEvent;
                        if (command2 != null) {
                            int i5 = command2.code;
                            if (i5 == 3) {
                                if (nfcLedCoverController.mCallDurationTimer != null
                                        && nfcLedCoverController.mCallDurationUpdateTask != null) {
                                    nfcLedCoverController.mCallInProgressDisplay = true;
                                }
                                Log.e(
                                        "CoverManager_NfcLedCoverController",
                                        "There is no time update task but we've got call duration"
                                            + " ongoing event... displaying clock instead");
                                nfcLedCoverController.mOngoingEvent = null;
                                nfcLedCoverController.sendDataToNfcLedCover(15, null);
                            } else {
                                nfcLedCoverController.sendDataToNfcLedCover(i5, command2.data);
                            }
                        } else {
                            nfcLedCoverController.sendDataToNfcLedCover(15, null);
                        }
                        return;
                    } catch (Throwable th) {
                        throw th;
                    }
                case 7:
                    if (message.arg1 == 1) {
                        return;
                    }
                    nfcLedCoverController = NfcLedCoverController.this;
                    nfcLedCoverController.getClass();
                    Log.d("CoverManager_NfcLedCoverController", "handleCoverDetached()");
                    nfcLedCoverController.mCallTimerLock.lock();
                    try {
                        nfcLedCoverController.mCallInProgressDisplay = false;
                        Timer timer = nfcLedCoverController.mCallDurationTimer;
                        if (timer != null) {
                            timer.cancel();
                            nfcLedCoverController.mCallDurationTimer = null;
                            nfcLedCoverController.mCallDurationUpdateTask = null;
                        } else {
                            Log.e(
                                    "CoverManager_NfcLedCoverController",
                                    "Call duration should not be null in stop or was already"
                                        + " stopped");
                        }
                        nfcLedCoverController.mCallStartTime = -1L;
                        nfcLedCoverController.mHandler.removeCallbacksAndMessages(null);
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                nfcLedCoverController.mSendLedDataWakeLock);
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                nfcLedCoverController.mPollTouchWakeLock);
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                nfcLedCoverController.mTouchResponseWakeLock);
                        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                                nfcLedCoverController.mLedOnOffWakeLock);
                        nfcLedCoverController.mPollingTouchEvents = false;
                        nfcLedCoverController.mPrevCommand = 0;
                        nfcLedCoverController.mOngoingEvent = null;
                        nfcLedCoverController.clearRetryCountDelayedMsg$1();
                        if (nfcLedCoverController.getSamsungNfcAdapter() != null) {
                            nfcLedCoverController.stopLedCover$1();
                        }
                        return;
                    } finally {
                        nfcLedCoverController.mCallTimerLock.unlock();
                    }
                case 8:
                    NfcLedCoverController nfcLedCoverController5 = NfcLedCoverController.this;
                    Bundle bundle = (Bundle) message.obj;
                    synchronized (nfcLedCoverController5.mListeners) {
                        try {
                            Iterator it2 = nfcLedCoverController5.mListeners.iterator();
                            while (it2.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
                                        (NfcLedTouchListenerInfo) it2.next();
                                if (nfcLedTouchListenerInfo2.type == 4) {
                                    nfcLedTouchListenerInfo2.onSystemCoverEvent(2, bundle);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                case 9:
                    NfcLedCoverController nfcLedCoverController6 = NfcLedCoverController.this;
                    Bundle bundle2 = (Bundle) message.obj;
                    synchronized (nfcLedCoverController6.mListeners) {
                        try {
                            Iterator it3 = nfcLedCoverController6.mListeners.iterator();
                            while (it3.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo3 =
                                        (NfcLedTouchListenerInfo) it3.next();
                                if (nfcLedTouchListenerInfo3.type == 4) {
                                    nfcLedTouchListenerInfo3.onSystemCoverEvent(3, bundle2);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                case 10:
                    NfcLedCoverController nfcLedCoverController7 = NfcLedCoverController.this;
                    Bundle bundle3 = (Bundle) message.obj;
                    nfcLedCoverController7.getClass();
                    boolean z = bundle3.getBoolean("lcd_off_disabled_by_cover");
                    if (nfcLedCoverController7.mCoverEventsDisabledForSamsungPay != z) {
                        nfcLedCoverController7.mCoverEventsDisabledForSamsungPay = z;
                        if (z && nfcLedCoverController7.mIsLedOn) {
                            nfcLedCoverController7.sendDataToNfcLedCover(18, null);
                        }
                    }
                    synchronized (nfcLedCoverController7.mListeners) {
                        try {
                            Iterator it4 = nfcLedCoverController7.mListeners.iterator();
                            while (it4.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo4 =
                                        (NfcLedTouchListenerInfo) it4.next();
                                if (nfcLedTouchListenerInfo4.type == 4) {
                                    nfcLedTouchListenerInfo4.onSystemCoverEvent(4, bundle3);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
            NfcLedCoverController.m887$$Nest$mhandleSendDataToNfcLedCover(
                    NfcLedCoverController.this, message.arg1, (byte[]) message.obj);
            if (NfcLedCoverController.this.mHandler.hasMessages(0)
                    || NfcLedCoverController.this.mHandler.hasMessages(4)
                    || NfcLedCoverController.this.mHandler.hasMessages(5)) {
                return;
            }
            BaseNfcLedCoverController.releaseWakeLockWithPermission(
                    NfcLedCoverController.this.mSendLedDataWakeLock);
        }
    }

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
            Log.v("CoverManager_NfcLedCoverController", "binderDied : binder = " + this.token);
            synchronized (NfcLedCoverController.this.mListeners) {
                NfcLedCoverController.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final void onSystemCoverEvent(int i, Bundle bundle) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w("CoverManager_NfcLedCoverController", "null listener received TouchReject!");
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
                        "CoverManager_NfcLedCoverController",
                        "Failed onSystemCoverEvent callback",
                        e);
            }
        }
    }

    /* renamed from: -$$Nest$mgetCallDuration, reason: not valid java name */
    public static byte[] m885$$Nest$mgetCallDuration(NfcLedCoverController nfcLedCoverController) {
        nfcLedCoverController.getClass();
        byte[] bArr = {0, 0, 0, 0};
        if (nfcLedCoverController.mCallStartTime != -1) {
            long elapsedRealtime =
                    (SystemClock.elapsedRealtime() - nfcLedCoverController.mCallStartTime) / 1000;
            byte[] bytes =
                    String.format(null, "%02d", Long.valueOf((elapsedRealtime / 60) % 100))
                            .getBytes();
            byte[] bytes2 =
                    String.format(null, "%02d", Long.valueOf(elapsedRealtime % 60)).getBytes();
            bArr[0] = bytes[0];
            bArr[1] = bytes[1];
            bArr[2] = bytes2[0];
            bArr[3] = bytes2[1];
        }
        return bArr;
    }

    /* renamed from: -$$Nest$mhandleEventResponse, reason: not valid java name */
    public static void m886$$Nest$mhandleEventResponse(
            NfcLedCoverController nfcLedCoverController, int i, int i2) {
        Iterator it;
        Iterator it2;
        nfcLedCoverController.getClass();
        Log.d(
                "CoverManager_NfcLedCoverController",
                "HandleEventResponse: type: " + i + " action: " + i2);
        if (i2 == 1) {
            Log.d("CoverManager_NfcLedCoverController", "Event touch: accept");
            synchronized (nfcLedCoverController.mListeners) {
                try {
                    it2 = nfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e) {
                    Log.e(
                            "CoverManager_NfcLedCoverController",
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
                                    "CoverManager_NfcLedCoverController",
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
            Log.d("CoverManager_NfcLedCoverController", "Event touch: reject");
            synchronized (nfcLedCoverController.mListeners) {
                try {
                    it = nfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e2) {
                    Log.e(
                            "CoverManager_NfcLedCoverController",
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
                                    "CoverManager_NfcLedCoverController",
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
            Log.d("CoverManager_NfcLedCoverController", "Unknown event action: " + i2);
        }
        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                nfcLedCoverController.mTouchResponseWakeLock);
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x0162, code lost:

       if (r5 == 3) goto L64;
    */
    /* renamed from: -$$Nest$mhandleSendDataToNfcLedCover, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m887$$Nest$mhandleSendDataToNfcLedCover(
            com.android.server.sepunion.cover.NfcLedCoverController r16, int r17, byte[] r18) {
        /*
            Method dump skipped, instructions count: 522
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.sepunion.cover.NfcLedCoverController.m887$$Nest$mhandleSendDataToNfcLedCover(com.android.server.sepunion.cover.NfcLedCoverController,"
                    + " int, byte[]):void");
    }

    public NfcLedCoverController(Looper looper, Context context) {
        super(context);
        this.mIsLedOn = false;
        this.mCallTimerLock = new ReentrantLock(true);
        this.mCallStartTime = -1L;
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
        PowerManager.WakeLock newWakeLock4 = this.mPowerManager.newWakeLock(1, "onoff ledcover");
        this.mLedOnOffWakeLock = newWakeLock4;
        newWakeLock4.setReferenceCounted(false);
        this.mTestMode =
                Settings.Secure.getInt(context.getContentResolver(), "nfc_led_cover_test", 0);
    }

    public static byte[] buildNfcCoverLedData$1(int i, byte[] bArr) {
        if (bArr == null) {
            bArr = new byte[] {0};
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 10];
        bArr2[0] = 0;
        bArr2[1] = -94;
        bArr2[2] = 0;
        bArr2[3] = 0;
        byte b = (byte) (length + 5);
        bArr2[4] = b;
        bArr2[5] = HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED;
        bArr2[6] = b;
        bArr2[7] = (byte) i;
        System.arraycopy(bArr, 0, bArr2, 8, bArr.length);
        bArr2[length + 8] = -1;
        bArr2[length + 9] = -1;
        return bArr2;
    }

    public static boolean isValidResponse(int i, byte[] bArr) {
        if (bArr == null || bArr.length < 6) {
            return false;
        }
        byte[] bArr2 = mResponsePattern;
        bArr2[3] = (byte) i;
        int i2 = 0;
        for (byte b : bArr) {
            if (b == bArr2[i2] || i2 == 1) {
                i2++;
                if (i2 == 6) {
                    return true;
                }
            } else if (i2 != 0) {
                return false;
            }
        }
        return false;
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void addLedNotification(Bundle bundle) {
        Log.d("CoverManager_NfcLedCoverController", "addLedNotification");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(8);
        obtainMessage.obj = bundle;
        nfcLedCoverControllerHandler.sendMessage(obtainMessage);
    }

    public final void clearRetryCountDelayedMsg$1() {
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

    public final void handleInvalidCommand$1(int i, byte[] bArr, byte[] bArr2) {
        boolean z;
        this.mPrevCommand = i;
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (bArr2 != null && i != 18 && bArr2.length >= 1) {
            byte b = bArr2[0];
            if (b != -32 && b != 1 && b != 3 && b != 5 && b != 6) {
                switch (b) {
                    case -80:
                    case -79:
                    case -78:
                        break;
                    default:
                        Log.e(
                                "CoverManager_NfcLedCoverController",
                                "Transceive error - unknown error value: " + ((int) bArr2[0]));
                        z = false;
                        break;
                }
                if (!z && bArr2.length > 2 && bArr2[2] == -48) {
                    byte[] bArr3 = new byte[bArr2.length];
                    System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
                    bArr3[2] = -47;
                    z = isValidResponse(i, bArr3);
                }
                if (!z && this.mLedCoverTransceiveRetryCount < 13) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i, "Repeat command ", " count: ");
                    m.append(this.mLedCoverTransceiveRetryCount);
                    Log.e("CoverManager_NfcLedCoverController", m.toString());
                    this.mIsLedOn = false;
                    this.mSamsungNfcAdapter.getClass();
                    SamsungNfcAdapter.stopLedCover();
                    Message obtain = Message.obtain();
                    obtain.what = 4;
                    obtain.arg1 = i;
                    obtain.obj = bArr;
                    nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 500L);
                    this.mLedCoverRetryPostTime = System.currentTimeMillis();
                    this.mLedCoverTransceiveRetryCount++;
                    return;
                }
                this.mLedCoverTransceiveRetryCount = 0;
                Log.e(
                        "CoverManager_NfcLedCoverController",
                        "Could not transceive command to cover so turn off led cover");
                this.mFactoryTransceiveResponseIntentSent = false;
            }
            z = true;
            if (!z) {
                byte[] bArr32 = new byte[bArr2.length];
                System.arraycopy(bArr2, 0, bArr32, 0, bArr2.length);
                bArr32[2] = -47;
                z = isValidResponse(i, bArr32);
            }
            if (!z) {}
            this.mLedCoverTransceiveRetryCount = 0;
            Log.e(
                    "CoverManager_NfcLedCoverController",
                    "Could not transceive command to cover so turn off led cover");
            this.mFactoryTransceiveResponseIntentSent = false;
        }
        this.mContext.sendBroadcastAsUser(
                new Intent("android.intent.action.NFC_LED_COVER_MAX_RETRY_DONE"), UserHandle.ALL);
        Log.d("CoverManager_NfcLedCoverController", "Sent done intent, fail transceive");
        if ((i == 2 || i == 18) && this.mCallInProgressDisplay) {
            this.mCallInProgressDisplay = false;
        }
        stopLedCover$1();
        this.mPollingTouchEvents = false;
        nfcLedCoverControllerHandler.removeMessages(2);
        BaseNfcLedCoverController.releaseWakeLockWithPermission(this.mPollTouchWakeLock);
    }

    public final void notifyLedOffListeners(int i) {
        Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, "led_off_command");
        synchronized (this.mListeners) {
            try {
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo.type == 4) {
                        nfcLedTouchListenerInfo.onSystemCoverEvent(0, m);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void registerNfcTouchListenerCallback(
            int i, IBinder iBinder, ComponentName componentName) {
        if (i < 0 || i > 5) {
            Log.e("CoverManager_NfcLedCoverController", "Unsupported touch listener type: " + i);
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
                                "CoverManager_NfcLedCoverController",
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
        Log.d("CoverManager_NfcLedCoverController", "removeLedNotification");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(9);
        obtainMessage.obj = bundle;
        nfcLedCoverControllerHandler.sendMessage(obtainMessage);
    }

    public final void scheduleLedOffTimeout(int i, byte[] bArr) {
        Log.d("CoverManager_NfcLedCoverController", "scheduleLedOffTimerout, command: " + i);
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (i == 1 || i == 2 || i == 18 || i == 20 || i == 224) {
            if (nfcLedCoverControllerHandler.hasMessages(1)) {
                nfcLedCoverControllerHandler.removeMessages(1);
            }
            BaseNfcLedCoverController.releaseWakeLockWithPermission(this.mLedOnOffWakeLock);
            return;
        }
        if (this.mIsLedOn
                && ((i == 15 || i == 3)
                        && this.mPrevCommand == i
                        && nfcLedCoverControllerHandler.hasMessages(1))) {
            if (i == 15) {
                Log.d("CoverManager_NfcLedCoverController", "Time update");
                return;
            } else {
                Log.d("CoverManager_NfcLedCoverController", "Call InProgress duration time update");
                return;
            }
        }
        BaseNfcLedCoverController.acquireWakeLockWithPermission(this.mLedOnOffWakeLock);
        if (nfcLedCoverControllerHandler.hasMessages(1)) {
            nfcLedCoverControllerHandler.removeMessages(1);
        }
        Message.obtain();
        Message obtain = Message.obtain();
        obtain.what = 1;
        if (i == 6) {
            nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 60000L);
            return;
        }
        if (i != 9) {
            switch (i) {
                case 12:
                    nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 4600L);
                    break;
                case 13:
                case 14:
                    nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 9000L);
                    break;
                default:
                    nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 3700L);
                    break;
            }
            return;
        }
        if (bArr == null
                || bArr.length < 5
                || !((bArr[1] == 48 && bArr[2] == 48) || (bArr[3] == 48 && bArr[4] == 48))) {
            nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 5100L);
        } else {
            nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 3700L);
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendDataToNfcLedCover(int i, byte[] bArr) {
        String format;
        if (!this.mIsLedCoverAttached
                && !FactoryTest.isFactoryBinary()
                && this.mCoverEventsDisabledForSamsungPay) {
            Log.d(
                    "CoverManager_NfcLedCoverController",
                    "sendDataToLedCover : Not attached LED Cover or Disabled by SamsungPay");
            return;
        }
        if (this.mSamsungNfcAdapter == null && getSamsungNfcAdapter() == null) {
            Log.d(
                    "CoverManager_NfcLedCoverController",
                    "sendDataToLedCover : Nfc Service not available");
            return;
        }
        if (i == 65535) {
            if (this.mPrevCommand != 15 || !this.mIsLedOn) {
                Log.d(
                        "CoverManager_NfcLedCoverController",
                        "Time tick: clock not displayed, ignore");
                return;
            }
            i = 15;
        }
        if (i == 15) {
            boolean is24HourFormat =
                    DateFormat.is24HourFormat(this.mContext, ActivityManager.getCurrentUser());
            long currentTimeMillis = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTimeMillis);
            int i2 = calendar.get(11);
            int i3 = calendar.get(12);
            if (is24HourFormat) {
                format = String.format(null, "%02d%02d", Integer.valueOf(i2), Integer.valueOf(i3));
            } else {
                int i4 = i2 % 12;
                if (i4 == 0) {
                    i4 = 12;
                }
                format = String.format(null, "%2d%02d", Integer.valueOf(i4), Integer.valueOf(i3));
            }
            bArr = format.getBytes();
        }
        if (i == 12 && this.mPrevCommand == 11) {
            Log.e(
                    "CoverManager_NfcLedCoverController",
                    "Ignore battery chargin, battery low already shown");
            return;
        }
        BaseNfcLedCoverController.acquireWakeLockWithPermission(this.mSendLedDataWakeLock);
        Message obtain = Message.obtain();
        obtain.arg1 = i;
        obtain.obj = bArr;
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (i == 65520) {
            if (bArr == null || bArr.length <= 0) {
                return;
            }
            obtain.what = 5;
            obtain.arg1 = bArr[0];
            obtain.obj = null;
            nfcLedCoverControllerHandler.sendMessage(obtain);
            return;
        }
        if (i == 19) {
            obtain.what = 4;
            nfcLedCoverControllerHandler.removeMessages(4);
            nfcLedCoverControllerHandler.sendMessageDelayed(obtain, 150L);
            return;
        }
        obtain.what = 0;
        if (nfcLedCoverControllerHandler.hasMessages(4)) {
            nfcLedCoverControllerHandler.removeMessages(0);
            long currentTimeMillis2 =
                    500 - (System.currentTimeMillis() - this.mLedCoverRetryPostTime);
            r6 = currentTimeMillis2 >= 0 ? currentTimeMillis2 : 0L;
            Log.w(
                    "CoverManager_NfcLedCoverController",
                    "There is pending DELAYED message due to Retry mechanism send this command with"
                        + " proper delay:"
                            + r6);
        }
        clearRetryCountDelayedMsg$1();
        nfcLedCoverControllerHandler.sendMessageDelayed(obtain, r6);
    }

    public final void sendNfcFailIntentForFactoryMode(byte[] bArr) {
        if ((FactoryTest.isFactoryBinary()
                        || FactoryTest.isRunningFactoryApp()
                        || this.mTestMode == 42)
                && !this.mFactoryTransceiveResponseIntentSent
                && bArr != null
                && bArr.length > 2
                && bArr[0] == 105
                && bArr[1] == -123
                && bArr[2] == 0) {
            this.mFactoryTransceiveResponseIntentSent = true;
            this.mContext.sendBroadcastAsUser(
                    new Intent("android.intent.action.NFC_LED_COVER_FPCB_DISCONNECT"),
                    UserHandle.ALL);
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendPowerKeyToCover() {
        if (this.mCoverEventsDisabledForSamsungPay) {
            Log.d(
                    "CoverManager_NfcLedCoverController",
                    "handleMessage MSG_EVENT_POWER_BUTTON: SamsungPay active - ignore power button"
                        + " events");
        } else {
            this.mHandler.sendEmptyMessage(6);
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendSystemEvent(Bundle bundle) {
        int i = bundle.getInt("event_type");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (i != 0) {
            if (i != 1) {
                return;
            }
            Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(10);
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

    public final void startCallInProgressDisplayTimer(byte[] bArr) {
        ByteBuffer allocate = ByteBuffer.allocate(64);
        try {
            allocate.put(bArr);
            allocate.flip();
            this.mCallStartTime = allocate.getLong();
            Log.d("CoverManager_NfcLedCoverController", "CallStartTime: " + this.mCallStartTime);
            Timer timer = this.mCallDurationTimer;
            if (timer != null) {
                timer.cancel();
                this.mCallDurationTimer = null;
                this.mCallDurationUpdateTask = null;
            }
            this.mCallInProgressDisplay = true;
            this.mCallDurationTimer = new Timer(true);
            CallDurationTask callDurationTask = new CallDurationTask();
            this.mCallDurationUpdateTask = callDurationTask;
            try {
                this.mCallDurationTimer.scheduleAtFixedRate(callDurationTask, 0L, 500L);
            } catch (RejectedExecutionException e) {
                Log.e("CoverManager_NfcLedCoverController", "Error while scheduling TimerTask", e);
                this.mCallInProgressDisplay = false;
                this.mCallDurationTimer = null;
                this.mCallDurationUpdateTask = null;
            }
        } catch (BufferOverflowException e2) {
            Log.e("CoverManager_NfcLedCoverController", "CallStartTime incorrect", e2);
        } catch (BufferUnderflowException e3) {
            Log.e("CoverManager_NfcLedCoverController", "CallStartTime incorrect", e3);
        }
    }

    public final void stopLedCover$1() {
        this.mSamsungNfcAdapter.getClass();
        SamsungNfcAdapter.stopLedCover();
        this.mIsLedOn = false;
        this.mSamsungNfcAdapter.getClass();
        SamsungNfcAdapter.setWirelessChargeEnabled(true);
    }

    public final boolean tryStartLedCover() {
        Log.d(
                "CoverManager_NfcLedCoverController",
                "Trying to start NFC LED Cover mIsLedOn=" + String.valueOf(this.mIsLedOn));
        if (this.mIsLedOn) {
            Log.d("CoverManager_NfcLedCoverController", "NFC LED Cover already started");
        } else {
            if (this.mLedCoverStartRetryCount == 0) {
                this.mSamsungNfcAdapter.getClass();
                SamsungNfcAdapter.setWirelessChargeEnabled(false);
            }
            this.mSamsungNfcAdapter.getClass();
            byte[] startLedCover = SamsungNfcAdapter.startLedCover();
            if (startLedCover == null || startLedCover.length <= 1) {
                Log.w("CoverManager_NfcLedCoverController", "Failed to start NFC LED Cover");
                sendNfcFailIntentForFactoryMode(startLedCover);
                return false;
            }
            Log.d("CoverManager_NfcLedCoverController", "Started NFC LED Cover");
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
                                "CoverManager_NfcLedCoverController",
                                "remove listener: " + nfcLedTouchListenerInfo.pid);
                        this.mListeners.remove(nfcLedTouchListenerInfo);
                        iBinder.unlinkToDeath(nfcLedTouchListenerInfo, 0);
                        return true;
                    }
                }
                Log.e(
                        "CoverManager_NfcLedCoverController",
                        "UnregisterNfcTouchListener: listener does not exist");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void updateNfcLedCoverAttachStateLocked(int i, boolean z) {
        boolean z2 = z && (i == 7 || i == 14);
        if (this.mIsLedCoverAttached != z2) {
            this.mIsLedCoverAttached = z2;
            if (!z2) {
                Log.d(
                        "CoverManager_NfcLedCoverController",
                        "NfcLedCover detached, start clearing all flags, messages, wakelocks");
                NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
                Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(7);
                obtainMessage.arg1 = z ? 1 : 0;
                nfcLedCoverControllerHandler.sendMessageAtFrontOfQueue(obtainMessage);
            }
        }
        this.mTestMode =
                Settings.Secure.getInt(this.mContext.getContentResolver(), "nfc_led_cover_test", 0);
    }
}
