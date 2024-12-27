package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
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

import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.sepunion.Log;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public final class DreamyNfcLedCoverController extends BaseNfcLedCoverController {
    public final ArrayList mCoverAuthCallbacks;
    public final ArrayDeque mEnquedFactoryCommands;
    public final Queue mFotaInProgressCallbacks;
    public final NfcLedCoverControllerHandler mHandler;
    public final ArrayList mListeners;
    public final PowerManager.WakeLock mSendLedDataWakeLock;
    public final PowerManager.WakeLock mTouchResponseWakeLock;

    public final class AuthCallbackInfo extends NfcLedTouchListenerInfo {
        public AuthCallbackInfo(
                IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            super(iBinder, componentName, i, i2, i3);
        }

        @Override // com.android.server.sepunion.cover.DreamyNfcLedCoverController.NfcLedTouchListenerInfo, android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.v("DreamyNfcLedCoverController", "binderDied : binder = " + this.token);
            synchronized (DreamyNfcLedCoverController.this.mCoverAuthCallbacks) {
                DreamyNfcLedCoverController.this.mCoverAuthCallbacks.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }
    }

    public final class FotaInProgressCallbackInfo extends NfcLedTouchListenerInfo {
        public FotaInProgressCallbackInfo(
                IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            super(iBinder, componentName, i, i2, i3);
        }

        @Override // com.android.server.sepunion.cover.DreamyNfcLedCoverController.NfcLedTouchListenerInfo, android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.v("DreamyNfcLedCoverController", "binderDied : binder = " + this.token);
            synchronized (DreamyNfcLedCoverController.this.mFotaInProgressCallbacks) {
                ((LinkedList) DreamyNfcLedCoverController.this.mFotaInProgressCallbacks)
                        .remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }
    }

    public final class NfcLedCoverControllerHandler extends Handler {
        public NfcLedCoverControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    DreamyNfcLedCoverController.m880$$Nest$mhandleSendDataToNfcLedCover(
                            DreamyNfcLedCoverController.this, message.arg1, (byte[]) message.obj);
                    return;
                case 1:
                case 2:
                case 8:
                default:
                    return;
                case 3:
                    DreamyNfcLedCoverController.m878$$Nest$mhandleEventResponse(
                            DreamyNfcLedCoverController.this, message.arg1, message.arg2);
                    return;
                case 4:
                    DreamyNfcLedCoverController.m881$$Nest$mhandleSendPowerKeyToCover(
                            DreamyNfcLedCoverController.this);
                    return;
                case 5:
                    if (message.arg1 == 1) {
                        return;
                    }
                    DreamyNfcLedCoverController dreamyNfcLedCoverController =
                            DreamyNfcLedCoverController.this;
                    dreamyNfcLedCoverController.getClass();
                    Log.d("DreamyNfcLedCoverController", "handleCoverDetached()");
                    dreamyNfcLedCoverController.mHandler.removeCallbacksAndMessages(null);
                    BaseNfcLedCoverController.releaseWakeLockWithPermission(
                            dreamyNfcLedCoverController.mSendLedDataWakeLock);
                    BaseNfcLedCoverController.releaseWakeLockWithPermission(
                            dreamyNfcLedCoverController.mTouchResponseWakeLock);
                    return;
                case 6:
                    DreamyNfcLedCoverController.m877$$Nest$mhandleAddLedNotification(
                            DreamyNfcLedCoverController.this, (Bundle) message.obj);
                    return;
                case 7:
                    DreamyNfcLedCoverController.m879$$Nest$mhandleRemoveLedNotification(
                            DreamyNfcLedCoverController.this, (Bundle) message.obj);
                    return;
                case 9:
                    DreamyNfcLedCoverController dreamyNfcLedCoverController2 =
                            DreamyNfcLedCoverController.this;
                    Bundle bundle = (Bundle) message.obj;
                    synchronized (dreamyNfcLedCoverController2.mListeners) {
                        try {
                            Iterator it = dreamyNfcLedCoverController2.mListeners.iterator();
                            while (it.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                                        (NfcLedTouchListenerInfo) it.next();
                                if (nfcLedTouchListenerInfo.type == 4) {
                                    nfcLedTouchListenerInfo.onSystemCoverEvent(4, bundle);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                case 10:
                    ((Long) message.obj).getClass();
                    DreamyNfcLedCoverController.this.getClass();
                    return;
                case 11:
                    DreamyNfcLedCoverController dreamyNfcLedCoverController3 =
                            DreamyNfcLedCoverController.this;
                    synchronized (dreamyNfcLedCoverController3.mCoverAuthCallbacks) {
                        try {
                            Iterator it2 =
                                    dreamyNfcLedCoverController3.mCoverAuthCallbacks.iterator();
                            while (it2.hasNext()) {
                                AuthCallbackInfo authCallbackInfo = (AuthCallbackInfo) it2.next();
                                authCallbackInfo.onSystemCoverEvent(6, null);
                                authCallbackInfo.token.unlinkToDeath(authCallbackInfo, 0);
                                it2.remove();
                            }
                        } finally {
                        }
                    }
                    return;
                case 12:
                    DreamyNfcLedCoverController dreamyNfcLedCoverController4 =
                            DreamyNfcLedCoverController.this;
                    boolean z = message.arg1 == 1;
                    dreamyNfcLedCoverController4.getClass();
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("fota_in_progress", z);
                    synchronized (dreamyNfcLedCoverController4.mListeners) {
                        try {
                            Iterator it3 = dreamyNfcLedCoverController4.mListeners.iterator();
                            while (it3.hasNext()) {
                                NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
                                        (NfcLedTouchListenerInfo) it3.next();
                                if (nfcLedTouchListenerInfo2.type == 4) {
                                    nfcLedTouchListenerInfo2.onSystemCoverEvent(7, bundle2);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                case 13:
                    DreamyNfcLedCoverController dreamyNfcLedCoverController5 =
                            DreamyNfcLedCoverController.this;
                    boolean z2 = message.arg1 == 1;
                    dreamyNfcLedCoverController5.getClass();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("fota_in_progress", z2);
                    synchronized (dreamyNfcLedCoverController5.mFotaInProgressCallbacks) {
                        try {
                            FotaInProgressCallbackInfo fotaInProgressCallbackInfo =
                                    (FotaInProgressCallbackInfo)
                                            ((LinkedList)
                                                            dreamyNfcLedCoverController5
                                                                    .mFotaInProgressCallbacks)
                                                    .poll();
                            if (fotaInProgressCallbackInfo != null) {
                                fotaInProgressCallbackInfo.onSystemCoverEvent(8, bundle3);
                                fotaInProgressCallbackInfo.token.unlinkToDeath(
                                        fotaInProgressCallbackInfo, 0);
                            }
                        } finally {
                        }
                    }
                    return;
            }
        }
    }

    public class NfcLedTouchListenerInfo implements IBinder.DeathRecipient {
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
        public void binderDied() {
            Log.v("DreamyNfcLedCoverController", "binderDied : binder = " + this.token);
            synchronized (DreamyNfcLedCoverController.this.mListeners) {
                DreamyNfcLedCoverController.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final void onSystemCoverEvent(int i, Bundle bundle) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w("DreamyNfcLedCoverController", "null listener received onSystemCoverEvent!");
                return;
            }
            try {
                INfcLedCoverTouchListenerCallback asInterface =
                        INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    asInterface.onSystemCoverEvent(i, bundle);
                }
            } catch (RemoteException e) {
                Log.e("DreamyNfcLedCoverController", "Failed onSystemCoverEvent callback", e);
            }
        }
    }

    /* renamed from: -$$Nest$mhandleAddLedNotification, reason: not valid java name */
    public static void m877$$Nest$mhandleAddLedNotification(
            DreamyNfcLedCoverController dreamyNfcLedCoverController, Bundle bundle) {
        synchronized (dreamyNfcLedCoverController.mListeners) {
            try {
                Iterator it = dreamyNfcLedCoverController.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo.type == 4) {
                        nfcLedTouchListenerInfo.onSystemCoverEvent(2, bundle);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mhandleEventResponse, reason: not valid java name */
    public static void m878$$Nest$mhandleEventResponse(
            DreamyNfcLedCoverController dreamyNfcLedCoverController, int i, int i2) {
        Iterator it;
        Iterator it2;
        Iterator it3;
        Iterator it4;
        Iterator it5;
        dreamyNfcLedCoverController.getClass();
        Log.d("DreamyNfcLedCoverController", "HandleEventResponse: type: " + i + " action: " + i2);
        if (i2 == 1) {
            Log.d("DreamyNfcLedCoverController", "Event touch: accept");
            synchronized (dreamyNfcLedCoverController.mListeners) {
                try {
                    it = dreamyNfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e) {
                    Log.e("DreamyNfcLedCoverController", "Failed onCoverTouchAccept callback", e);
                } finally {
                }
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (i == nfcLedTouchListenerInfo.type) {
                        IBinder iBinder = nfcLedTouchListenerInfo.token;
                        if (iBinder == null) {
                            Log.w(
                                    "DreamyNfcLedCoverController",
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
            Log.d("DreamyNfcLedCoverController", "Event touch: reject");
            synchronized (dreamyNfcLedCoverController.mListeners) {
                try {
                    it2 = dreamyNfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e2) {
                    Log.e("DreamyNfcLedCoverController", "Failed onCoverTouchReject callback", e2);
                } finally {
                }
                while (it2.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
                            (NfcLedTouchListenerInfo) it2.next();
                    if (i == nfcLedTouchListenerInfo2.type) {
                        IBinder iBinder2 = nfcLedTouchListenerInfo2.token;
                        if (iBinder2 == null) {
                            Log.w(
                                    "DreamyNfcLedCoverController",
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
        } else if (i2 == 3) {
            Log.d("DreamyNfcLedCoverController", "Event touch: tap left");
            synchronized (dreamyNfcLedCoverController.mListeners) {
                try {
                    it3 = dreamyNfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e3) {
                    Log.e("DreamyNfcLedCoverController", "Failed onCoverTapLeft callback", e3);
                } finally {
                }
                while (it3.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo3 =
                            (NfcLedTouchListenerInfo) it3.next();
                    if (i == nfcLedTouchListenerInfo3.type) {
                        IBinder iBinder3 = nfcLedTouchListenerInfo3.token;
                        if (iBinder3 == null) {
                            Log.w(
                                    "DreamyNfcLedCoverController",
                                    "null listener received onCoverTapLeft!");
                        } else {
                            INfcLedCoverTouchListenerCallback asInterface3 =
                                    INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder3);
                            if (asInterface3 != null) {
                                asInterface3.onCoverTapLeft();
                            }
                        }
                    }
                }
            }
        } else if (i2 == 4) {
            Log.d("DreamyNfcLedCoverController", "Event touch: tap middle");
            synchronized (dreamyNfcLedCoverController.mListeners) {
                try {
                    it4 = dreamyNfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e4) {
                    Log.e("DreamyNfcLedCoverController", "Failed onCoverTapMid callback", e4);
                } finally {
                }
                while (it4.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo4 =
                            (NfcLedTouchListenerInfo) it4.next();
                    if (i == nfcLedTouchListenerInfo4.type) {
                        IBinder iBinder4 = nfcLedTouchListenerInfo4.token;
                        if (iBinder4 == null) {
                            Log.w(
                                    "DreamyNfcLedCoverController",
                                    "null listener received onCoverTapMid!");
                        } else {
                            INfcLedCoverTouchListenerCallback asInterface4 =
                                    INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder4);
                            if (asInterface4 != null) {
                                asInterface4.onCoverTapMid();
                            }
                        }
                    }
                }
            }
        } else if (i2 != 5) {
            Log.d("DreamyNfcLedCoverController", "Unknown event action: " + i2);
        } else {
            Log.d("DreamyNfcLedCoverController", "Event touch: tap right");
            synchronized (dreamyNfcLedCoverController.mListeners) {
                try {
                    it5 = dreamyNfcLedCoverController.mListeners.iterator();
                } catch (RemoteException e5) {
                    Log.e("DreamyNfcLedCoverController", "Failed onCoverTapRight callback", e5);
                } finally {
                }
                while (it5.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo5 =
                            (NfcLedTouchListenerInfo) it5.next();
                    if (i == nfcLedTouchListenerInfo5.type) {
                        IBinder iBinder5 = nfcLedTouchListenerInfo5.token;
                        if (iBinder5 == null) {
                            Log.w(
                                    "DreamyNfcLedCoverController",
                                    "null listener received onCoverTapRight!");
                        } else {
                            INfcLedCoverTouchListenerCallback asInterface5 =
                                    INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder5);
                            if (asInterface5 != null) {
                                asInterface5.onCoverTapRight();
                            }
                        }
                    }
                }
            }
        }
        if (dreamyNfcLedCoverController.mHandler.hasMessages(3)) {
            return;
        }
        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                dreamyNfcLedCoverController.mTouchResponseWakeLock);
    }

    /* renamed from: -$$Nest$mhandleRemoveLedNotification, reason: not valid java name */
    public static void m879$$Nest$mhandleRemoveLedNotification(
            DreamyNfcLedCoverController dreamyNfcLedCoverController, Bundle bundle) {
        synchronized (dreamyNfcLedCoverController.mListeners) {
            try {
                Iterator it = dreamyNfcLedCoverController.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo.type == 4) {
                        nfcLedTouchListenerInfo.onSystemCoverEvent(3, bundle);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mhandleSendDataToNfcLedCover, reason: not valid java name */
    public static void m880$$Nest$mhandleSendDataToNfcLedCover(
            DreamyNfcLedCoverController dreamyNfcLedCoverController, int i, byte[] bArr) {
        boolean z;
        dreamyNfcLedCoverController.getClass();
        Bundle bundle = new Bundle();
        bundle.putInt("send_command_id", i);
        bundle.putByteArray("send_command_content", bArr);
        synchronized (dreamyNfcLedCoverController.mListeners) {
            try {
                Iterator it = dreamyNfcLedCoverController.mListeners.iterator();
                z = false;
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo.type == 4) {
                        nfcLedTouchListenerInfo.onSystemCoverEvent(5, bundle);
                        z = true;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z) {
            Log.w(
                    "DreamyNfcLedCoverController",
                    "LedCoverService did not finished initalizing, enqueue command " + i);
            if (dreamyNfcLedCoverController.mEnquedFactoryCommands.size() >= 3) {
                dreamyNfcLedCoverController.mEnquedFactoryCommands.poll();
            }
            dreamyNfcLedCoverController.mEnquedFactoryCommands.add(bundle);
        }
        if (dreamyNfcLedCoverController.mHandler.hasMessages(0)) {
            return;
        }
        BaseNfcLedCoverController.releaseWakeLockWithPermission(
                dreamyNfcLedCoverController.mSendLedDataWakeLock);
    }

    /* renamed from: -$$Nest$mhandleSendPowerKeyToCover, reason: not valid java name */
    public static void m881$$Nest$mhandleSendPowerKeyToCover(
            DreamyNfcLedCoverController dreamyNfcLedCoverController) {
        synchronized (dreamyNfcLedCoverController.mListeners) {
            try {
                Iterator it = dreamyNfcLedCoverController.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    int i = nfcLedTouchListenerInfo.type;
                    if (i == 4 || i == 10) {
                        nfcLedTouchListenerInfo.onSystemCoverEvent(1, null);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public DreamyNfcLedCoverController(Looper looper, Context context) {
        super(context);
        this.mListeners = new ArrayList();
        this.mCoverAuthCallbacks = new ArrayList();
        this.mEnquedFactoryCommands = new ArrayDeque();
        this.mFotaInProgressCallbacks = new LinkedList();
        this.mHandler = new NfcLedCoverControllerHandler(looper);
        PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "send leddata");
        this.mSendLedDataWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 =
                this.mPowerManager.newWakeLock(1, "touchResponse ledcover");
        this.mTouchResponseWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void addLedNotification(Bundle bundle) {
        Log.d("DreamyNfcLedCoverController", "addLedNotification");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(6);
        obtainMessage.obj = bundle;
        nfcLedCoverControllerHandler.sendMessage(obtainMessage);
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

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void notifyAuthenticationResponse() {
        this.mHandler.sendEmptyMessage(11);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void registerNfcTouchListenerCallback(
            int i, IBinder iBinder, ComponentName componentName) {
        synchronized (this.mListeners) {
            try {
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    NfcLedTouchListenerInfo nfcLedTouchListenerInfo =
                            (NfcLedTouchListenerInfo) it.next();
                    if (nfcLedTouchListenerInfo != null
                            && iBinder.equals(nfcLedTouchListenerInfo.token)) {
                        Log.e(
                                "DreamyNfcLedCoverController",
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
                if (i == 4) {
                    Iterator it2 = this.mEnquedFactoryCommands.iterator();
                    while (it2.hasNext()) {
                        nfcLedTouchListenerInfo2.onSystemCoverEvent(5, (Bundle) it2.next());
                    }
                    this.mEnquedFactoryCommands.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void removeLedNotification(Bundle bundle) {
        Log.d("DreamyNfcLedCoverController", "removeLedNotification");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(7);
        obtainMessage.obj = bundle;
        nfcLedCoverControllerHandler.sendMessage(obtainMessage);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final boolean requestCoverAuthentication(
            long j, IBinder iBinder, ComponentName componentName) {
        synchronized (this.mCoverAuthCallbacks) {
            try {
                Iterator it = this.mCoverAuthCallbacks.iterator();
                while (it.hasNext()) {
                    AuthCallbackInfo authCallbackInfo = (AuthCallbackInfo) it.next();
                    if (authCallbackInfo != null && iBinder.equals(authCallbackInfo.token)) {
                        Log.e(
                                "DreamyNfcLedCoverController",
                                "requestCoverAuthentication : duplicated listener handle");
                        return false;
                    }
                }
                AuthCallbackInfo authCallbackInfo2 =
                        new AuthCallbackInfo(
                                iBinder,
                                componentName,
                                Binder.getCallingPid(),
                                Binder.getCallingUid(),
                                4);
                iBinder.linkToDeath(authCallbackInfo2, 0);
                this.mCoverAuthCallbacks.add(authCallbackInfo2);
                Message obtainMessage = this.mHandler.obtainMessage(10);
                obtainMessage.obj = Long.valueOf(j);
                obtainMessage.sendToTarget();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendDataToNfcLedCover(int i, byte[] bArr) {
        if (!this.mIsLedCoverAttached && !FactoryTest.isFactoryBinary()) {
            Log.d("DreamyNfcLedCoverController", "sendDataToLedCover : Not attached LED Cover");
            return;
        }
        NfcAdapter nfcAdapter = this.mNfcAdapter;
        if (nfcAdapter == null) {
            if (nfcAdapter == null) {
                this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
            }
            if (this.mNfcAdapter == null) {
                Log.e("CoverManager_BaseNfcLedCoverController", "Could not get NfcAdapter");
            }
            if (this.mNfcAdapter == null) {
                Log.d(
                        "DreamyNfcLedCoverController",
                        "sendDataToLedCover : Nfc Service not available");
                return;
            }
        }
        if (i == -1 || i == -2) {
            Log.d(
                    "DreamyNfcLedCoverController",
                    "sendDataToLedCover : command: -1 should not be sent, ignore");
            return;
        }
        BaseNfcLedCoverController.acquireWakeLockWithPermission(this.mSendLedDataWakeLock);
        Message obtain = Message.obtain();
        obtain.arg1 = i;
        obtain.obj = bArr;
        obtain.what = 0;
        this.mHandler.sendMessage(obtain);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendPowerKeyToCover() {
        this.mHandler.sendEmptyMessage(4);
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final void sendSystemEvent(Bundle bundle) {
        int i = bundle.getInt("event_type");
        NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
        if (i == 0) {
            BaseNfcLedCoverController.acquireWakeLockWithPermission(this.mTouchResponseWakeLock);
            int i2 = bundle.getInt("lcd_touch_listener_type", -1);
            int i3 = bundle.getInt("lcd_touch_listener_respone", -1);
            Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(3);
            obtainMessage.arg1 = i2;
            obtainMessage.arg2 = i3;
            obtainMessage.sendToTarget();
            return;
        }
        if (i == 1) {
            Message obtainMessage2 = nfcLedCoverControllerHandler.obtainMessage(9);
            obtainMessage2.obj = bundle;
            obtainMessage2.sendToTarget();
        } else {
            if (i != 8) {
                return;
            }
            boolean z = bundle.getBoolean("fota_in_progress", false);
            Message obtainMessage3 = nfcLedCoverControllerHandler.obtainMessage(13);
            obtainMessage3.arg1 = z ? 1 : 0;
            obtainMessage3.sendToTarget();
        }
    }

    @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
    public final boolean setFotaInProgress(
            boolean z, IBinder iBinder, ComponentName componentName) {
        synchronized (this.mFotaInProgressCallbacks) {
            try {
                for (FotaInProgressCallbackInfo fotaInProgressCallbackInfo :
                        this.mFotaInProgressCallbacks) {
                    if (fotaInProgressCallbackInfo != null
                            && iBinder.equals(fotaInProgressCallbackInfo.token)) {
                        Log.e(
                                "DreamyNfcLedCoverController",
                                "setFotaInProgress : duplicated listener handle");
                        return false;
                    }
                }
                FotaInProgressCallbackInfo fotaInProgressCallbackInfo2 =
                        new FotaInProgressCallbackInfo(
                                iBinder,
                                componentName,
                                Binder.getCallingPid(),
                                Binder.getCallingUid(),
                                4);
                iBinder.linkToDeath(fotaInProgressCallbackInfo2, 0);
                ((LinkedList) this.mFotaInProgressCallbacks).offer(fotaInProgressCallbackInfo2);
                Message obtainMessage = this.mHandler.obtainMessage(12);
                obtainMessage.arg1 = z ? 1 : 0;
                obtainMessage.sendToTarget();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
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
                                "DreamyNfcLedCoverController",
                                "remove listener: " + nfcLedTouchListenerInfo.pid);
                        this.mListeners.remove(nfcLedTouchListenerInfo);
                        iBinder.unlinkToDeath(nfcLedTouchListenerInfo, 0);
                        return true;
                    }
                }
                Log.e(
                        "DreamyNfcLedCoverController",
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
            if (z2) {
                return;
            }
            Log.d(
                    "DreamyNfcLedCoverController",
                    "NfcLedCover detached, start clearing all flags, messages, wakelocks");
            NfcLedCoverControllerHandler nfcLedCoverControllerHandler = this.mHandler;
            Message obtainMessage = nfcLedCoverControllerHandler.obtainMessage(5);
            obtainMessage.arg1 = z ? 1 : 0;
            nfcLedCoverControllerHandler.sendMessageAtFrontOfQueue(obtainMessage);
        }
    }
}
