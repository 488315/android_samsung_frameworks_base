package com.samsung.accessory.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
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
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.input.InputManagerService;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.accessory.manager.authentication.cover.CoverAuthenticator;
import com.samsung.accessory.manager.authentication.usb.UsbAuthenticator;
import com.samsung.accessory.manager.authentication.wirelesscharger.WirelessChargerAuthenticator;
import com.samsung.accessory.manager.connectivity.Connectivity;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SAccessoryManager extends SAccessoryManagerInternal implements InputManagerService.SecAccessoryManagerCallbacks {
    public Handler mAuthHandler;
    public final BroadcastReceiver mAuthenticationRequsetReceiver;
    public final AuthenticationTask mAuthenticationTask;
    public final BroadcastReceiver mBatteryEventReceiver;
    public Context mContext;
    public DetachCheck mDetachCheck;
    public HandlerThread mHandlerThread;
    public InputManagerService mInputManager;
    public boolean mIsFactory;
    public final AuthenticationSession.SessionEventListener mSessionEventListener;
    public final UEventObserver mUEventObserver;
    public String mUsbpdIds;
    public final PowerManager.WakeLock mWakeLock;
    public String modelName;
    public static final String TAG = "SAccessoryManager_" + SAccessoryManager.class.getSimpleName();
    public static final boolean DBG = Debug.semIsProductDev();
    public int auth_state = 0;
    public boolean authState = false;
    public HashMap mSessions = new HashMap();
    public ArrayList mLocalAuthenticator = new ArrayList();
    public int authenticatedHall = -1;
    public boolean usbState = false;
    public boolean wirelesschargerState = false;
    public boolean isUsbReady = false;

    public interface AuthenticationResultCallback {
        void onAuthenticationComplted(AuthenticationResult authenticationResult);

        void onAuthenticationStarted();

        void onAuthenticationStarting(AuthenticationSession authenticationSession);

        void onAuthenticationStopped(AuthenticationSession authenticationSession);
    }

    public interface AuthenticationTask {
        void authenticationReady();

        int getSessionState(AuthenticationSession authenticationSession);

        void setAuthenticatedHall(int i);

        void start(Message message, boolean z);

        void stop(AuthenticationSession authenticationSession);
    }

    public final String convertAuthMsg(int i) {
        if (i == 1) {
            return "MSG_AUTH_START_REQUEST";
        }
        if (i == 2) {
            return "MSG_AUTH_START_REQUEST_INTERNAL";
        }
        if (i == 3) {
            return "MSG_AUTH_RESTART";
        }
        if (i == 4) {
            return "MSG_AUTH_STOP_REQUEST";
        }
        switch (i) {
            case 10:
                return "MSG_AUTH_SESSION_STARTING";
            case 11:
                return "MSG_AUTH_SESSION_STARTED";
            case 12:
                return "MSG_AUTH_SESSION_COMPLETE";
            case 13:
                return "MSG_AUTH_SESSION_STOPPED";
            case 14:
                return "MSG_DETACHCHECK";
            default:
                return null;
        }
    }

    @Override // com.android.server.input.InputManagerService.SecAccessoryManagerCallbacks
    public void notifyCoverSwitchStateChanged(long j, boolean z) {
    }

    public final void initUsbState() {
        byte b;
        FileReader fileReader;
        int i = 1024;
        try {
            char[] cArr = new char[1024];
            fileReader = new FileReader("/sys/class/sec/ccic/usbpd_ids");
            try {
                this.mUsbpdIds = new String(cArr, 0, fileReader.read(cArr, 0, 1024)).trim();
            } finally {
                fileReader.close();
            }
        } catch (FileNotFoundException unused) {
            i = 0;
        } catch (Exception unused2) {
            i = 0;
        }
        try {
            fileReader.close();
            String str = TAG;
            Log.i(str, "Service start and check pdids: " + this.mUsbpdIds);
            char[] cArr2 = new char[1024];
            fileReader = new FileReader("/sys/class/sec/ccic/usbpd_type");
            try {
                i = Integer.valueOf(new String(cArr2, 0, fileReader.read(cArr2, 0, 1024)).trim()).intValue();
                try {
                    Log.i(str, "init dock state = " + i);
                    fileReader.close();
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException unused3) {
            Log.e(TAG, "This kernel does not have ccic dock support");
        } catch (Exception unused4) {
        }
        String str2 = this.mUsbpdIds;
        String[] split = str2 != null ? str2.split(XmlUtils.STRING_ARRAY_SEPARATOR) : null;
        byte[] stringToByte = stringToByte("b001b7ff");
        if (split != null && split.length == 2 && split[0].equals("04e8")) {
            byte[] stringToByte2 = stringToByte(split[1]);
            if (stringToByte2.length == 4 && stringToByte2[0] == stringToByte[0] && (b = stringToByte2[1]) >= stringToByte[1] && b <= stringToByte[5]) {
                attachUsbTypeC(i);
            } else {
                Log.e(TAG, "This device is not support usb authentication usb_pdids: " + this.mUsbpdIds);
            }
        }
        this.isUsbReady = true;
    }

    public static byte[] stringToByte(String str) {
        byte[] bArr = new byte[str.length()];
        return str.getBytes();
    }

    public final int getRunningSessionsLocked(int i) {
        int i2;
        synchronized (this.mSessions) {
            Iterator it = this.mSessions.values().iterator();
            i2 = 0;
            while (it.hasNext()) {
                if (((Integer) it.next()).intValue() == i) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public final void handleAuthStopRequest(Message message) {
        AuthenticationSession authenticationSession = (AuthenticationSession) message.obj;
        synchronized (this.mSessions) {
            if (this.mSessions.containsKey(authenticationSession)) {
                authenticationSession.stopSession();
            } else {
                Log.e(TAG, "The session does not exist! so can't stop the session!");
            }
        }
    }

    public final void handleAuthReStartRequest(Message message) {
        AuthenticationSession authenticationSession = (AuthenticationSession) message.obj;
        synchronized (this.mSessions) {
            if (this.mSessions.containsKey(authenticationSession)) {
                authenticationSession.startSession();
            } else {
                Log.e(TAG, "The session does not exist! so can't restart the session!");
            }
        }
    }

    public final void handleAuthStartRequest(Message message, boolean z) {
        Bundle data = message.getData();
        synchronized (this.mSessions) {
            String string = data.getString("package_name", "");
            int i = data.getInt("connectivity_type", -1);
            if (getRunningSessionsLocked(i) < Connectivity.getMaxConnection(i)) {
                this.mWakeLock.acquire();
                AuthenticationSession createNewSession = AuthenticationSession.createNewSession(this.mContext, string, i);
                if (z) {
                    createNewSession.setAuthenticationResultCallback((AuthenticationResultCallback) message.obj);
                }
                createNewSession.setSessionCallback(this.mSessionEventListener);
                createNewSession.startSession();
                this.mSessions.put(createNewSession, Integer.valueOf(i));
                return;
            }
            AuthenticationResult authenticationResult = new AuthenticationResult();
            authenticationResult.setReason(11);
            if (z) {
                AuthenticationResultCallback authenticationResultCallback = (AuthenticationResultCallback) message.obj;
                if (authenticationResultCallback != null) {
                    authenticationResultCallback.onAuthenticationComplted(authenticationResult);
                } else {
                    Log.e(TAG, "handleAuthStartRequest, callback is null!");
                }
            }
        }
    }

    public final void handleAuthResponse(Message message) {
        AuthenticationSession authenticationSession = (AuthenticationSession) message.obj;
        if (authenticationSession.getAuthenticationCallback() == null) {
            sendIntentToReceiver(authenticationSession);
        } else {
            authenticationSession.getAuthenticationCallback().onAuthenticationComplted(authenticationSession.getAuthenticationResult());
        }
    }

    public final void sendIntentToReceiver(AuthenticationSession authenticationSession) {
        Log.d(TAG, "sendIntentToReceiver");
        AuthenticationResult authenticationResult = authenticationSession.getAuthenticationResult();
        Intent intent = new Intent("com.samsung.accessory.authentication.action.TEST_AUTHENTICATION_REPLY");
        intent.putExtras(authenticationResult.getResultBundle());
        intent.setPackage(authenticationResult.getRequestPackage());
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    public final void processAuthMessage(Message message) {
        String str = TAG;
        Log.d(str, "processAuthMessage = " + convertAuthMsg(message.what));
        int i = message.what;
        if (i == 1) {
            handleAuthStartRequest(message, false);
            return;
        }
        if (i == 2) {
            handleAuthStartRequest(message, true);
            return;
        }
        if (i == 3) {
            handleAuthReStartRequest(message);
            return;
        }
        if (i == 4) {
            handleAuthStopRequest(message);
            return;
        }
        switch (i) {
            case 10:
                AuthenticationSession authenticationSession = (AuthenticationSession) message.obj;
                if (authenticationSession.getAuthenticationCallback() != null) {
                    authenticationSession.getAuthenticationCallback().onAuthenticationStarting(authenticationSession);
                    return;
                }
                return;
            case 11:
                AuthenticationSession authenticationSession2 = (AuthenticationSession) message.obj;
                if (authenticationSession2.getAuthenticationCallback() != null) {
                    authenticationSession2.getAuthenticationCallback().onAuthenticationStarted();
                    return;
                }
                return;
            case 12:
                handleAuthResponse(message);
                return;
            case 13:
                AuthenticationSession authenticationSession3 = (AuthenticationSession) message.obj;
                synchronized (this.mSessions) {
                    if (this.mSessions.remove(authenticationSession3) != null) {
                        Log.d(str, "session removed");
                    }
                    if (this.mSessions.size() == 0 && this.mWakeLock.isHeld()) {
                        this.mWakeLock.release();
                    }
                    if (authenticationSession3.getAuthenticationCallback() != null) {
                        authenticationSession3.getAuthenticationCallback().onAuthenticationStopped(null);
                    }
                }
                return;
            case 14:
                Log.i(str, "Check auth chip again. Skip detach process if there is no auth chip in this time");
                if (this.mDetachCheck.isAuthChipExist()) {
                    return;
                }
                Iterator it = this.mLocalAuthenticator.iterator();
                while (it.hasNext()) {
                    LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
                    if (localAuthenticator instanceof CoverAuthenticator) {
                        Log.i(TAG, "there is no authentication chip, so do detach process");
                        ((CoverAuthenticator) localAuthenticator).onCoverAttached(System.nanoTime(), false, 0);
                    }
                }
                return;
            default:
                return;
        }
    }

    public final class AuthHandler extends Handler {
        public AuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SAccessoryManager.this.processAuthMessage(message);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.String] */
    public final int WPCRead() {
        FileReader fileReader;
        char[] cArr = new char[1024];
        int i = 0;
        FileReader fileReader2 = null;
        FileReader fileReader3 = null;
        try {
            try {
                try {
                    fileReader = new FileReader("sys/class/power_supply/battery/wpc_auth_mode");
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Exception e) {
                e = e;
            }
        } catch (IOException unused) {
        }
        try {
            i = Integer.parseInt(new String(cArr, 0, fileReader.read(cArr, 0, 1024)).trim());
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            ?? r2 = "wpc auth  mode = ";
            sb.append("wpc auth  mode = ");
            sb.append(i);
            Log.i(str, sb.toString());
            fileReader.close();
            fileReader2 = r2;
        } catch (Exception e2) {
            e = e2;
            fileReader3 = fileReader;
            Log.i(TAG, "File read fail " + e);
            fileReader2 = fileReader3;
            if (fileReader3 != null) {
                fileReader3.close();
                fileReader2 = fileReader3;
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
            fileReader2 = fileReader;
            if (fileReader2 != null) {
                try {
                    fileReader2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
        return i;
    }

    public SAccessoryManager(Context context, InputManagerService inputManagerService) {
        this.mIsFactory = false;
        this.modelName = "Samsung Mobile";
        AuthenticationTask authenticationTask = new AuthenticationTask() { // from class: com.samsung.accessory.manager.SAccessoryManager.1
            @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationTask
            public void start(Message message, boolean z) {
                SAccessoryManager.this.auth_state = 1;
                if (message.obj instanceof AuthenticationSession) {
                    SAccessoryManager.this.mAuthHandler.obtainMessage(3, message.obj).sendToTarget();
                    return;
                }
                Message obtainMessage = SAccessoryManager.this.mAuthHandler.obtainMessage(z ? 2 : 1);
                obtainMessage.obj = message.obj;
                obtainMessage.setData(message.getData());
                SAccessoryManager.this.mAuthHandler.sendMessage(obtainMessage);
            }

            @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationTask
            public void stop(AuthenticationSession authenticationSession) {
                AuthenticationResult authenticationResult;
                SAccessoryManager.this.auth_state = 0;
                if (SAccessoryManager.this.modelName.contains("SM-F900") && authenticationSession != null && (authenticationResult = authenticationSession.mResult) != null && authenticationResult.getReason() == 0) {
                    Log.i(SAccessoryManager.TAG, "F900, set auth state true");
                    SAccessoryManager.this.authState = true;
                } else if (SAccessoryManager.this.modelName.contains("SM-F900")) {
                    Log.i(SAccessoryManager.TAG, "F900, set aute state false");
                    SAccessoryManager.this.authState = false;
                }
                SAccessoryManager.this.mAuthHandler.obtainMessage(4, authenticationSession).sendToTarget();
            }

            @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationTask
            public int getSessionState(AuthenticationSession authenticationSession) {
                synchronized (SAccessoryManager.this.mSessions) {
                    if (!SAccessoryManager.this.mSessions.containsKey(authenticationSession)) {
                        return 7;
                    }
                    return authenticationSession.getSessionState();
                }
            }

            @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationTask
            public void setAuthenticatedHall(int i) {
                SAccessoryManager.this.authenticatedHall = i;
            }

            @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationTask
            public void authenticationReady() {
                SAccessoryManager.this.initUsbState();
            }
        };
        this.mAuthenticationTask = authenticationTask;
        this.mSessionEventListener = new AuthenticationSession.SessionEventListener() { // from class: com.samsung.accessory.manager.SAccessoryManager.2
            @Override // com.samsung.accessory.manager.authentication.AuthenticationSession.SessionEventListener
            public void onSessionEvent(int i, AuthenticationSession authenticationSession) {
                SAccessoryManager.this.mAuthHandler.obtainMessage(i != 1 ? i != 2 ? i != 3 ? i != 4 ? -1 : 13 : 12 : 11 : 10, authenticationSession).sendToTarget();
            }
        };
        UEventObserver uEventObserver = new UEventObserver() { // from class: com.samsung.accessory.manager.SAccessoryManager.3
            public void onUEvent(UEventObserver.UEvent uEvent) {
                byte b;
                try {
                    Log.i(SAccessoryManager.TAG, "UEventObserver, event : " + uEvent);
                    if (!SAccessoryManager.this.mIsFactory && (Integer.parseInt(uEvent.get("SWITCH_STATE")) == 200 || Integer.parseInt(uEvent.get("SWITCH_STATE")) == 0)) {
                        if (Integer.parseInt(uEvent.get("SWITCH_STATE")) == 0) {
                            SAccessoryManager.this.attachUsbTypeC(Integer.parseInt(uEvent.get("SWITCH_STATE")));
                            return;
                        }
                        String[] split = uEvent.get("USBPD_IDS") != null ? uEvent.get("USBPD_IDS").split(XmlUtils.STRING_ARRAY_SEPARATOR) : null;
                        byte[] stringToByte = SAccessoryManager.stringToByte("b001b7ff");
                        if (SAccessoryManager.this.isUsbReady && split != null && split.length == 2 && split[0].equals("04e8")) {
                            byte[] stringToByte2 = SAccessoryManager.stringToByte(split[1]);
                            if (stringToByte2.length == 4 && stringToByte2[0] == stringToByte[0] && (b = stringToByte2[1]) >= stringToByte[1] && b <= stringToByte[5]) {
                                SAccessoryManager.this.attachUsbTypeC(Integer.parseInt(uEvent.get("SWITCH_STATE")));
                                return;
                            } else {
                                Log.i(SAccessoryManager.TAG, "UEventObserver, event : ");
                                return;
                            }
                        }
                        return;
                    }
                    Log.d(SAccessoryManager.TAG, "uEvent switch state is not related with usb auth or Factory binary");
                } catch (NumberFormatException unused) {
                    Log.e(SAccessoryManager.TAG, "Could not parse switch state from event " + uEvent);
                }
            }
        };
        this.mUEventObserver = uEventObserver;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.accessory.manager.SAccessoryManager.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.samsung.accessory.authentication.action.TEST_AUTHENTICATION_REQUEST".equals(intent.getAction())) {
                    Bundle extras = intent.getExtras();
                    Message obtainMessage = SAccessoryManager.this.mAuthHandler.obtainMessage(1);
                    obtainMessage.setData(extras);
                    SAccessoryManager.this.mAuthHandler.sendMessage(obtainMessage);
                }
            }
        };
        this.mAuthenticationRequsetReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.samsung.accessory.manager.SAccessoryManager.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("misc_event", 0);
                    int intExtra2 = intent.getIntExtra("sec_plug_type", 0);
                    boolean z = (intent.getIntExtra("misc_event", 0) & 64) == 64;
                    Log.i(SAccessoryManager.TAG, "onReceive: " + intent.getAction() + ", misc_event: " + intExtra + ", plug event: " + intExtra2);
                    SAccessoryManager.this.batteryChanged(intExtra2);
                    int WPCRead = SAccessoryManager.this.WPCRead();
                    if (WPCRead == 0 || WPCRead == 2) {
                        if ((intent.getIntExtra("misc_event", 0) & 512) == 512) {
                            SAccessoryManager.this.wirelessChargerConnected(1);
                            return;
                        } else {
                            if (z || !SAccessoryManager.this.wirelesschargerState) {
                                return;
                            }
                            SAccessoryManager.this.wirelessChargerConnected(2);
                            return;
                        }
                    }
                    Log.i(SAccessoryManager.TAG, "do not auth wireless charging");
                }
            }
        };
        this.mBatteryEventReceiver = broadcastReceiver2;
        String str = TAG;
        Log.i(str, "SAccessoryManager starting up");
        this.mContext = context;
        this.mInputManager = inputManagerService;
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, str);
        this.mWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        HandlerThread handlerThread = new HandlerThread(str);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mAuthHandler = new AuthHandler(this.mHandlerThread.getLooper());
        String str2 = Build.MODEL;
        if (str2 != null) {
            this.modelName = str2;
        }
        this.mIsFactory = FactoryTest.isFactoryBinary();
        if (DBG) {
            this.mContext.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.accessory.authentication.action.TEST_AUTHENTICATION_REQUEST"));
        }
        this.mContext.registerReceiver(broadcastReceiver2, new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT"));
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                if (packageManager.hasSystemFeature("com.sec.feature.usb_authentication")) {
                    this.mLocalAuthenticator.add(new UsbAuthenticator(this.mContext, this.mHandlerThread.getLooper(), authenticationTask));
                    uEventObserver.startObserving("DEVPATH=/devices/virtual/sec/ccic");
                }
                if (packageManager.hasSystemFeature("com.sec.feature.nfc_authentication") || packageManager.hasSystemFeature("com.sec.feature.nfc_authentication_cover")) {
                    this.mLocalAuthenticator.add(new CoverAuthenticator(this.mContext, inputManagerService, this.mHandlerThread.getLooper(), authenticationTask));
                }
                if (packageManager.hasSystemFeature("com.sec.feature.wirelesscharger_authentication")) {
                    this.mLocalAuthenticator.add(new WirelessChargerAuthenticator(this.mContext, this.mHandlerThread.getLooper(), authenticationTask));
                }
                Log.d(str, "feature check nfc: " + packageManager.hasSystemFeature("com.sec.feature.nfc_authentication") + ", usb: " + packageManager.hasSystemFeature("com.sec.feature.usb_authentication"));
            }
        } catch (Throwable unused) {
            Log.e(TAG, "package manager error for check feature");
        }
        this.mDetachCheck = new DetachCheck(this.mContext);
    }

    public void systemReady() {
        ArrayList arrayList = this.mLocalAuthenticator;
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((LocalAuthenticator) it.next()).systemReady();
        }
    }

    @Override // com.android.server.input.InputManagerService.SecAccessoryManagerCallbacks
    public void notifyUnverifiedCoverAttachChanged(long j, boolean z) {
        String str = TAG;
        Log.d(str, "notifyUnverifiedCoverAttachChanged whenNanos = " + j + ", attached = " + z);
        if (!z && this.modelName.contains("SM-F900")) {
            if (this.mDetachCheck.isAuthChipExist()) {
                Log.i(str, "auth chip exists. will retry to check after TIME_OUT_DETACH_RETRY");
                this.mAuthHandler.sendEmptyMessageDelayed(14, 1000L);
                return;
            }
        } else if (this.modelName.contains("SM-F900") && z && this.authState) {
            Log.i(str, "F900, current auth state is true, so skip authentication");
            return;
        }
        Iterator it = this.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
            if (localAuthenticator instanceof CoverAuthenticator) {
                ((CoverAuthenticator) localAuthenticator).onCoverAttached(j, z, 0);
            }
        }
    }

    public final void attachUsbTypeC(int i) {
        Iterator it = this.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
            if (localAuthenticator instanceof UsbAuthenticator) {
                String str = TAG;
                Log.d(str, "USB Type C attached, attached = " + i);
                Log.d(str, "USB State " + this.usbState);
                UsbAuthenticator usbAuthenticator = (UsbAuthenticator) localAuthenticator;
                if (i == 200) {
                    if (!this.usbState) {
                        this.usbState = true;
                        usbAuthenticator.onUsbAttached(0L, true);
                    }
                } else if (i == 0) {
                    this.usbState = false;
                    usbAuthenticator.onUsbAttached(0L, false);
                }
            }
        }
    }

    public final void wirelessChargerConnected(int i) {
        Iterator it = this.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
            if (localAuthenticator instanceof WirelessChargerAuthenticator) {
                Log.d(TAG, "Wireless Charger Connected = " + i);
                WirelessChargerAuthenticator wirelessChargerAuthenticator = (WirelessChargerAuthenticator) localAuthenticator;
                if (i == 1) {
                    this.wirelesschargerState = true;
                    wirelessChargerAuthenticator.onWirelessChargerConnected(0L, true);
                } else if (i == 2) {
                    this.wirelesschargerState = false;
                    wirelessChargerAuthenticator.onWirelessChargerConnected(0L, false);
                }
            }
        }
    }

    public final void batteryChanged(int i) {
        Iterator it = this.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            LocalAuthenticator localAuthenticator = (LocalAuthenticator) it.next();
            if (localAuthenticator instanceof CoverAuthenticator) {
                Log.d(TAG, "batteryChanged: " + i);
                ((CoverAuthenticator) localAuthenticator).onBatteryChanged(i);
            }
        }
    }

    @Override // com.samsung.accessory.manager.SAccessoryManagerInternal
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump SAccessoryManager from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        Iterator it = this.mLocalAuthenticator.iterator();
        while (it.hasNext()) {
            ((LocalAuthenticator) it.next()).dump(fileDescriptor, printWriter, strArr);
        }
        synchronized (this.mSessions) {
            Iterator it2 = this.mSessions.keySet().iterator();
            while (it2.hasNext()) {
                ((AuthenticationSession) it2.next()).dump(fileDescriptor, printWriter, strArr);
            }
        }
    }
}
