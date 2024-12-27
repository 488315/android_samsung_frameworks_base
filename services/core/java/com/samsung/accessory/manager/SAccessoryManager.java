package com.samsung.accessory.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.accessory.manager.authentication.cover.CoverAuthenticator;
import com.samsung.accessory.manager.authentication.usb.UsbAuthenticator;
import com.samsung.accessory.manager.authentication.wirelesscharger.WirelessChargerAuthenticator;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SAccessoryManager extends SAccessoryManagerInternal implements InputManagerService.SecAccessoryManagerCallbacks {
    public static final boolean DBG = Debug.semIsProductDev();
    public boolean isUsbReady;
    public final AuthHandler mAuthHandler;
    public final AnonymousClass4 mAuthenticationRequsetReceiver;
    public final AnonymousClass4 mBatteryEventReceiver;
    public final Context mContext;
    public final DetachCheck mDetachCheck;
    public final boolean mIsFactory;
    public final ArrayList mLocalAuthenticator;
    public final AnonymousClass1 mSessionEventListener;
    public final HashMap mSessions = new HashMap();
    public final AnonymousClass3 mUEventObserver;
    public String mUsbpdIds;
    public final PowerManager.WakeLock mWakeLock;
    public final String modelName;
    public boolean usbState;
    public boolean wirelesschargerState;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.accessory.manager.SAccessoryManager$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public /* synthetic */ AnonymousClass1() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v13, types: [int] */
        /* JADX WARN: Type inference failed for: r2v2 */
        /* JADX WARN: Type inference failed for: r2v3 */
        public void authenticationReady() {
            byte b;
            char[] cArr;
            FileReader fileReader;
            SAccessoryManager sAccessoryManager = SAccessoryManager.this;
            sAccessoryManager.getClass();
            int i = "Service start and check pdids: ";
            try {
                cArr = new char[1024];
                fileReader = new FileReader("/sys/class/sec/ccic/usbpd_ids");
            } catch (FileNotFoundException unused) {
                i = 0;
            } catch (Exception unused2) {
                i = 0;
            }
            try {
                sAccessoryManager.mUsbpdIds = new String(cArr, 0, fileReader.read(cArr, 0, 1024)).trim();
                try {
                    fileReader.close();
                    Log.i("SAccessoryManager_SAccessoryManager", "Service start and check pdids: " + sAccessoryManager.mUsbpdIds);
                    char[] cArr2 = new char[1024];
                    fileReader = new FileReader("/sys/class/sec/ccic/usbpd_type");
                    try {
                        i = Integer.valueOf(new String(cArr2, 0, fileReader.read(cArr2, 0, 1024)).trim()).intValue();
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (FileNotFoundException unused3) {
                    Log.e("SAccessoryManager_SAccessoryManager", "This kernel does not have ccic dock support");
                } catch (Exception unused4) {
                }
                try {
                    Log.i("SAccessoryManager_SAccessoryManager", "init dock state = " + ((int) i));
                    fileReader.close();
                    String str = sAccessoryManager.mUsbpdIds;
                    String[] split = str != null ? str.split(":") : null;
                    byte[] bytes = "b001b7ff".getBytes();
                    if (split != null && split.length == 2 && split[0].equals("04e8")) {
                        String str2 = split[1];
                        byte[] bArr = new byte[str2.length()];
                        byte[] bytes2 = str2.getBytes();
                        if (bytes2.length != 4 || bytes2[0] != bytes[0] || (b = bytes2[1]) < bytes[1] || b > bytes[5]) {
                            Log.e("SAccessoryManager_SAccessoryManager", "This device is not support usb authentication usb_pdids: " + sAccessoryManager.mUsbpdIds);
                        } else {
                            sAccessoryManager.attachUsbTypeC(i);
                        }
                    }
                    sAccessoryManager.isUsbReady = true;
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            } finally {
                fileReader.close();
            }
        }

        public int getSessionState(AuthenticationSession authenticationSession) {
            synchronized (SAccessoryManager.this.mSessions) {
                try {
                    if (!SAccessoryManager.this.mSessions.containsKey(authenticationSession)) {
                        return 7;
                    }
                    return authenticationSession.getSessionState();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void onSessionEvent(int i, AuthenticationSession authenticationSession) {
            SAccessoryManager.this.mAuthHandler.obtainMessage(i != 1 ? i != 2 ? i != 3 ? i != 4 ? -1 : 13 : 12 : 11 : 10, authenticationSession).sendToTarget();
        }

        public void start(Message message) {
            SAccessoryManager sAccessoryManager = SAccessoryManager.this;
            sAccessoryManager.getClass();
            Object obj = message.obj;
            if (obj instanceof AuthenticationSession) {
                sAccessoryManager.mAuthHandler.obtainMessage(3, obj).sendToTarget();
                return;
            }
            Message obtainMessage = sAccessoryManager.mAuthHandler.obtainMessage(2);
            obtainMessage.obj = message.obj;
            obtainMessage.setData(message.getData());
            sAccessoryManager.mAuthHandler.sendMessage(obtainMessage);
        }

        public void stop(AuthenticationSession authenticationSession) {
            AuthenticationResult authenticationResult;
            SAccessoryManager sAccessoryManager = SAccessoryManager.this;
            sAccessoryManager.getClass();
            if (sAccessoryManager.modelName.contains("SM-F900") && authenticationSession != null && (authenticationResult = authenticationSession.mResult) != null && authenticationResult.mReason == 0) {
                boolean z = SAccessoryManager.DBG;
                Log.i("SAccessoryManager_SAccessoryManager", "F900, set auth state true");
            } else if (sAccessoryManager.modelName.contains("SM-F900")) {
                boolean z2 = SAccessoryManager.DBG;
                Log.i("SAccessoryManager_SAccessoryManager", "F900, set aute state false");
            }
            sAccessoryManager.mAuthHandler.obtainMessage(4, authenticationSession).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthHandler extends Handler {
        public AuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            SAccessoryManager sAccessoryManager = SAccessoryManager.this;
            sAccessoryManager.getClass();
            StringBuilder sb = new StringBuilder("processAuthMessage = ");
            int i = message.what;
            if (i == 1) {
                str = "MSG_AUTH_START_REQUEST";
            } else if (i == 2) {
                str = "MSG_AUTH_START_REQUEST_INTERNAL";
            } else if (i == 3) {
                str = "MSG_AUTH_RESTART";
            } else if (i != 4) {
                switch (i) {
                    case 10:
                        str = "MSG_AUTH_SESSION_STARTING";
                        break;
                    case 11:
                        str = "MSG_AUTH_SESSION_STARTED";
                        break;
                    case 12:
                        str = "MSG_AUTH_SESSION_COMPLETE";
                        break;
                    case 13:
                        str = "MSG_AUTH_SESSION_STOPPED";
                        break;
                    case 14:
                        str = "MSG_DETACHCHECK";
                        break;
                    default:
                        str = null;
                        break;
                }
            } else {
                str = "MSG_AUTH_STOP_REQUEST";
            }
            VpnManagerService$$ExternalSyntheticOutline0.m(sb, str, "SAccessoryManager_SAccessoryManager");
            int i2 = message.what;
            if (i2 == 1) {
                sAccessoryManager.handleAuthStartRequest(message, false);
                return;
            }
            if (i2 == 2) {
                sAccessoryManager.handleAuthStartRequest(message, true);
                return;
            }
            if (i2 == 3) {
                AuthenticationSession authenticationSession = (AuthenticationSession) message.obj;
                synchronized (sAccessoryManager.mSessions) {
                    try {
                        if (sAccessoryManager.mSessions.containsKey(authenticationSession)) {
                            authenticationSession.startSession();
                        } else {
                            Log.e("SAccessoryManager_SAccessoryManager", "The session does not exist! so can't restart the session!");
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i2 == 4) {
                AuthenticationSession authenticationSession2 = (AuthenticationSession) message.obj;
                synchronized (sAccessoryManager.mSessions) {
                    if (sAccessoryManager.mSessions.containsKey(authenticationSession2)) {
                        synchronized (authenticationSession2) {
                            Log.i("SAccessoryManager_AuthenticationSession", "stopSession");
                            authenticationSession2.mSessionHandler.removeMessages(11);
                            authenticationSession2.mSessionHandler.removeMessages(12);
                            authenticationSession2.mSessionHandler.sendEmptyMessage(12);
                        }
                    } else {
                        Log.e("SAccessoryManager_SAccessoryManager", "The session does not exist! so can't stop the session!");
                    }
                }
                return;
            }
            switch (i2) {
                case 10:
                    AuthenticationSession authenticationSession3 = (AuthenticationSession) message.obj;
                    AuthenticationResultCallback authenticationResultCallback = authenticationSession3.mAuthResultCallback;
                    if (authenticationResultCallback != null) {
                        authenticationResultCallback.onAuthenticationStarting(authenticationSession3);
                        return;
                    }
                    return;
                case 11:
                    AuthenticationResultCallback authenticationResultCallback2 = ((AuthenticationSession) message.obj).mAuthResultCallback;
                    if (authenticationResultCallback2 != null) {
                        authenticationResultCallback2.onAuthenticationStarted();
                        return;
                    }
                    return;
                case 12:
                    AuthenticationSession authenticationSession4 = (AuthenticationSession) message.obj;
                    AuthenticationResultCallback authenticationResultCallback3 = authenticationSession4.mAuthResultCallback;
                    if (authenticationResultCallback3 != null) {
                        authenticationResultCallback3.onAuthenticationComplted(authenticationSession4.mResult);
                        return;
                    }
                    Log.d("SAccessoryManager_SAccessoryManager", "sendIntentToReceiver");
                    AuthenticationResult authenticationResult = authenticationSession4.mResult;
                    Intent intent = new Intent("com.samsung.accessory.authentication.action.TEST_AUTHENTICATION_REPLY");
                    intent.putExtras(authenticationResult.getResultBundle());
                    intent.setPackage(authenticationResult.mRequestPkg);
                    sAccessoryManager.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
                    return;
                case 13:
                    AuthenticationSession authenticationSession5 = (AuthenticationSession) message.obj;
                    synchronized (sAccessoryManager.mSessions) {
                        try {
                            if (sAccessoryManager.mSessions.remove(authenticationSession5) != null) {
                                Log.d("SAccessoryManager_SAccessoryManager", "session removed");
                            }
                            if (sAccessoryManager.mSessions.size() == 0 && sAccessoryManager.mWakeLock.isHeld()) {
                                sAccessoryManager.mWakeLock.release();
                            }
                            AuthenticationResultCallback authenticationResultCallback4 = authenticationSession5.mAuthResultCallback;
                            if (authenticationResultCallback4 != null) {
                                authenticationResultCallback4.onAuthenticationStopped();
                            }
                        } finally {
                        }
                    }
                    return;
                case 14:
                    Log.i("SAccessoryManager_SAccessoryManager", "Check auth chip again. Skip detach process if there is no auth chip in this time");
                    DetachCheck detachCheck = sAccessoryManager.mDetachCheck;
                    byte[] requestStartTypeS = detachCheck.requestStartTypeS();
                    if (requestStartTypeS != null && requestStartTypeS.length == 16) {
                        detachCheck.requestStopTypeS();
                        return;
                    }
                    detachCheck.requestStopTypeS();
                    Iterator it = sAccessoryManager.mLocalAuthenticator.iterator();
                    while (it.hasNext()) {
                        LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
                        if (localAuthenticator instanceof CoverAuthenticator) {
                            Log.i("SAccessoryManager_SAccessoryManager", "there is no authentication chip, so do detach process");
                            ((CoverAuthenticator) localAuthenticator).onCoverAttached(System.nanoTime(), false, false);
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AuthenticationResultCallback {
        void onAuthenticationComplted(AuthenticationResult authenticationResult);

        void onAuthenticationStarted();

        void onAuthenticationStarting(AuthenticationSession authenticationSession);

        void onAuthenticationStopped();
    }

    /* renamed from: -$$Nest$mwirelessChargerConnected, reason: not valid java name */
    public static void m1129$$Nest$mwirelessChargerConnected(SAccessoryManager sAccessoryManager, int i) {
        Iterator it = sAccessoryManager.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
            if (localAuthenticator instanceof WirelessChargerAuthenticator) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Wireless Charger Connected = ", "SAccessoryManager_SAccessoryManager");
                WirelessChargerAuthenticator wirelessChargerAuthenticator = (WirelessChargerAuthenticator) localAuthenticator;
                if (i == 1) {
                    sAccessoryManager.wirelesschargerState = true;
                    wirelessChargerAuthenticator.onWirelessChargerConnected(true);
                } else if (i == 2) {
                    sAccessoryManager.wirelesschargerState = false;
                    wirelessChargerAuthenticator.onWirelessChargerConnected(false);
                }
            }
        }
    }

    public SAccessoryManager(Context context, InputManagerService inputManagerService) {
        ArrayList arrayList = new ArrayList();
        this.mLocalAuthenticator = arrayList;
        this.mIsFactory = false;
        this.usbState = false;
        this.wirelesschargerState = false;
        this.isUsbReady = false;
        this.modelName = "Samsung Mobile";
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mSessionEventListener = new AnonymousClass1();
        UEventObserver uEventObserver = new UEventObserver() { // from class: com.samsung.accessory.manager.SAccessoryManager.3
            public final void onUEvent(UEventObserver.UEvent uEvent) {
                byte b;
                try {
                    boolean z = SAccessoryManager.DBG;
                    Log.i("SAccessoryManager_SAccessoryManager", "UEventObserver, event : " + uEvent);
                    if (!SAccessoryManager.this.mIsFactory && (Integer.parseInt(uEvent.get("SWITCH_STATE")) == 200 || Integer.parseInt(uEvent.get("SWITCH_STATE")) == 0)) {
                        if (Integer.parseInt(uEvent.get("SWITCH_STATE")) == 0) {
                            SAccessoryManager.this.attachUsbTypeC(Integer.parseInt(uEvent.get("SWITCH_STATE")));
                            return;
                        }
                        String[] split = uEvent.get("USBPD_IDS") != null ? uEvent.get("USBPD_IDS").split(":") : null;
                        byte[] bytes = "b001b7ff".getBytes();
                        if (SAccessoryManager.this.isUsbReady && split != null && split.length == 2 && split[0].equals("04e8")) {
                            String str = split[1];
                            byte[] bArr = new byte[str.length()];
                            byte[] bytes2 = str.getBytes();
                            if (bytes2.length != 4 || bytes2[0] != bytes[0] || (b = bytes2[1]) < bytes[1] || b > bytes[5]) {
                                Log.i("SAccessoryManager_SAccessoryManager", "UEventObserver, event : ");
                                return;
                            } else {
                                SAccessoryManager.this.attachUsbTypeC(Integer.parseInt(uEvent.get("SWITCH_STATE")));
                                return;
                            }
                        }
                        return;
                    }
                    Log.d("SAccessoryManager_SAccessoryManager", "uEvent switch state is not related with usb auth or Factory binary");
                } catch (NumberFormatException unused) {
                    boolean z2 = SAccessoryManager.DBG;
                    Log.e("SAccessoryManager_SAccessoryManager", "Could not parse switch state from event " + uEvent);
                }
            }
        };
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.samsung.accessory.manager.SAccessoryManager.4
            public final /* synthetic */ SAccessoryManager this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Code restructure failed: missing block: B:29:0x00b1, code lost:
            
                if (r13 > com.android.server.backup.BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) goto L34;
             */
            /* JADX WARN: Code restructure failed: missing block: B:51:0x00cc, code lost:
            
                if (r4.getAttachState() != false) goto L44;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:68:0x015c  */
            /* JADX WARN: Removed duplicated region for block: B:76:0x0172  */
            /* JADX WARN: Removed duplicated region for block: B:78:0x0179  */
            /* JADX WARN: Removed duplicated region for block: B:95:0x0156 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Type inference failed for: r4v8, types: [java.lang.StringBuilder] */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [int] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v2 */
            /* JADX WARN: Type inference failed for: r7v3 */
            /* JADX WARN: Type inference failed for: r7v5, types: [java.io.FileReader] */
            /* JADX WARN: Type inference failed for: r7v7 */
            /* JADX WARN: Type inference failed for: r7v8 */
            /* JADX WARN: Type inference failed for: r7v9 */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r20, android.content.Intent r21) {
                /*
                    Method dump skipped, instructions count: 440
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.manager.SAccessoryManager.AnonymousClass4.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.samsung.accessory.manager.SAccessoryManager.4
            public final /* synthetic */ SAccessoryManager this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                /*
                    Method dump skipped, instructions count: 440
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.manager.SAccessoryManager.AnonymousClass4.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        Log.i("SAccessoryManager_SAccessoryManager", "SAccessoryManager starting up");
        this.mContext = context;
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "SAccessoryManager_SAccessoryManager");
        this.mWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        HandlerThread handlerThread = new HandlerThread("SAccessoryManager_SAccessoryManager");
        handlerThread.start();
        this.mAuthHandler = new AuthHandler(handlerThread.getLooper());
        String str = Build.MODEL;
        if (str != null) {
            this.modelName = str;
        }
        this.mIsFactory = FactoryTest.isFactoryBinary();
        if (DBG) {
            context.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.accessory.authentication.action.TEST_AUTHENTICATION_REQUEST"));
        }
        context.registerReceiver(broadcastReceiver2, new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT"));
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                if (packageManager.hasSystemFeature("com.sec.feature.usb_authentication")) {
                    arrayList.add(new UsbAuthenticator(context, handlerThread.getLooper(), anonymousClass1));
                    uEventObserver.startObserving("DEVPATH=/devices/virtual/sec/ccic");
                }
                if (packageManager.hasSystemFeature("com.sec.feature.nfc_authentication") || packageManager.hasSystemFeature("com.sec.feature.nfc_authentication_cover")) {
                    arrayList.add(new CoverAuthenticator(context, inputManagerService, handlerThread.getLooper(), anonymousClass1));
                }
                if (packageManager.hasSystemFeature("com.sec.feature.wirelesscharger_authentication")) {
                    arrayList.add(new WirelessChargerAuthenticator(context, handlerThread.getLooper(), anonymousClass1));
                }
                Log.d("SAccessoryManager_SAccessoryManager", "feature check nfc: " + packageManager.hasSystemFeature("com.sec.feature.nfc_authentication") + ", usb: " + packageManager.hasSystemFeature("com.sec.feature.usb_authentication"));
            }
        } catch (Throwable unused) {
            Log.e("SAccessoryManager_SAccessoryManager", "package manager error for check feature");
        }
        this.mDetachCheck = new DetachCheck(this.mContext);
    }

    public final void attachUsbTypeC(int i) {
        Iterator it = this.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
            if (localAuthenticator instanceof UsbAuthenticator) {
                Log.d("SAccessoryManager_SAccessoryManager", "USB Type C attached, attached = " + i);
                RCPManagerService$$ExternalSyntheticOutline0.m("SAccessoryManager_SAccessoryManager", new StringBuilder("USB State "), this.usbState);
                UsbAuthenticator usbAuthenticator = (UsbAuthenticator) localAuthenticator;
                if (i == 200) {
                    if (!this.usbState) {
                        this.usbState = true;
                        usbAuthenticator.onUsbAttached(true);
                    }
                } else if (i == 0) {
                    this.usbState = false;
                    usbAuthenticator.onUsbAttached(false);
                }
            }
        }
    }

    @Override // com.samsung.accessory.manager.SAccessoryManagerInternal
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump SAccessoryManager from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        Iterator it = this.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            ((LocalAuthenticator) it.next()).dump(printWriter);
        }
        synchronized (this.mSessions) {
            try {
                Iterator it2 = this.mSessions.keySet().iterator();
                while (it2.hasNext()) {
                    ((AuthenticationSession) it2.next()).dump(fileDescriptor, printWriter, strArr);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleAuthStartRequest(android.os.Message r9, boolean r10) {
        /*
            r8 = this;
            android.os.Bundle r0 = r9.getData()
            java.util.HashMap r1 = r8.mSessions
            monitor-enter(r1)
            java.lang.String r2 = "package_name"
            java.lang.String r3 = ""
            java.lang.String r2 = r0.getString(r2, r3)     // Catch: java.lang.Throwable -> L67
            java.lang.String r3 = "connectivity_type"
            r4 = -1
            int r0 = r0.getInt(r3, r4)     // Catch: java.lang.Throwable -> L67
            r3 = 1
            if (r0 != r3) goto L1c
        L1a:
            r4 = r3
            goto L2a
        L1c:
            r5 = 2
            if (r0 != r5) goto L22
            r4 = 8
            goto L2a
        L22:
            r5 = 3
            if (r0 != r5) goto L26
            goto L1a
        L26:
            r5 = 4
            if (r0 != r5) goto L2a
            goto L1a
        L2a:
            java.util.HashMap r3 = r8.mSessions     // Catch: java.lang.Throwable -> L67
            monitor-enter(r3)     // Catch: java.lang.Throwable -> L67
            java.util.HashMap r5 = r8.mSessions     // Catch: java.lang.Throwable -> L4d
            java.util.Collection r5 = r5.values()     // Catch: java.lang.Throwable -> L4d
            java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Throwable -> L4d
            r6 = 0
        L38:
            boolean r7 = r5.hasNext()     // Catch: java.lang.Throwable -> L4d
            if (r7 == 0) goto L4f
            java.lang.Object r7 = r5.next()     // Catch: java.lang.Throwable -> L4d
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch: java.lang.Throwable -> L4d
            int r7 = r7.intValue()     // Catch: java.lang.Throwable -> L4d
            if (r7 != r0) goto L38
            int r6 = r6 + 1
            goto L38
        L4d:
            r8 = move-exception
            goto L9a
        L4f:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4d
            if (r6 >= r4) goto L7b
            android.os.PowerManager$WakeLock r3 = r8.mWakeLock     // Catch: java.lang.Throwable -> L67
            r3.acquire()     // Catch: java.lang.Throwable -> L67
            android.content.Context r3 = r8.mContext     // Catch: java.lang.Throwable -> L67
            com.samsung.accessory.manager.authentication.AuthenticationSession r4 = new com.samsung.accessory.manager.authentication.AuthenticationSession     // Catch: java.lang.Throwable -> L67
            r4.<init>(r3, r2, r0)     // Catch: java.lang.Throwable -> L67
            if (r10 == 0) goto L69
            java.lang.Object r9 = r9.obj     // Catch: java.lang.Throwable -> L67
            com.samsung.accessory.manager.SAccessoryManager$AuthenticationResultCallback r9 = (com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback) r9     // Catch: java.lang.Throwable -> L67
            r4.mAuthResultCallback = r9     // Catch: java.lang.Throwable -> L67
            goto L69
        L67:
            r8 = move-exception
            goto L9c
        L69:
            com.samsung.accessory.manager.SAccessoryManager$1 r9 = r8.mSessionEventListener     // Catch: java.lang.Throwable -> L67
            r4.mSessionEventListener = r9     // Catch: java.lang.Throwable -> L67
            r4.startSession()     // Catch: java.lang.Throwable -> L67
            java.util.HashMap r8 = r8.mSessions     // Catch: java.lang.Throwable -> L67
            java.lang.Integer r9 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> L67
            r8.put(r4, r9)     // Catch: java.lang.Throwable -> L67
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L67
            return
        L7b:
            com.samsung.accessory.manager.authentication.AuthenticationResult r8 = new com.samsung.accessory.manager.authentication.AuthenticationResult     // Catch: java.lang.Throwable -> L67
            r8.<init>()     // Catch: java.lang.Throwable -> L67
            r0 = 11
            r8.setReason(r0)     // Catch: java.lang.Throwable -> L67
            if (r10 == 0) goto L98
            java.lang.Object r9 = r9.obj     // Catch: java.lang.Throwable -> L67
            com.samsung.accessory.manager.SAccessoryManager$AuthenticationResultCallback r9 = (com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback) r9     // Catch: java.lang.Throwable -> L67
            if (r9 == 0) goto L91
            r9.onAuthenticationComplted(r8)     // Catch: java.lang.Throwable -> L67
            goto L98
        L91:
            java.lang.String r8 = "SAccessoryManager_SAccessoryManager"
            java.lang.String r9 = "handleAuthStartRequest, callback is null!"
            android.util.Log.e(r8, r9)     // Catch: java.lang.Throwable -> L67
        L98:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L67
            return
        L9a:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4d
            throw r8     // Catch: java.lang.Throwable -> L67
        L9c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L67
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.manager.SAccessoryManager.handleAuthStartRequest(android.os.Message, boolean):void");
    }
}
