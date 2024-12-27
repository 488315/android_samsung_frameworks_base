package com.android.server.adb;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.debug.AdbManagerInternal;
import android.debug.FingerprintAndPairDevice;
import android.debug.IAdbCallback;
import android.debug.IAdbManager;
import android.debug.IAdbTransport;
import android.debug.PairDevice;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.sysprop.AdbProperties;
import android.util.ArrayMap;
import android.util.sysfwutil.Slog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.adb.AdbDebuggingManager;
import com.android.server.adb.AdbDebuggingManager.AdbKeyStore;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdbService extends IAdbManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AdbDebuggingManager.AdbConnectionPortPoller mConnectionPortPoller;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final AdbDebuggingManager mDebuggingManager;
    public boolean mIsAdbUsbEnabled;
    public boolean mIsAdbWifiEnabled;
    public final AdbSettingsObserver mObserver;
    public final AtomicInteger mConnectionPort = new AtomicInteger(-1);
    public final AdbConnectionPortListener mPortListener = new AdbConnectionPortListener();
    public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
    public final ArrayMap mTransports = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdbConnectionPortListener implements AdbDebuggingManager.AdbConnectionPortListener {
        public AdbConnectionPortListener() {
        }

        @Override // com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener
        public final void onPortReceived(int i) {
            AdbService adbService = AdbService.this;
            if (i <= 0 || i > 65535) {
                adbService.mConnectionPort.set(-1);
                try {
                    Settings.Global.putInt(adbService.mContentResolver, "adb_wifi_enabled", 0);
                } catch (SecurityException unused) {
                    Slog.d("AdbService", "ADB_ENABLED is restricted.");
                }
            } else {
                adbService.mConnectionPort.set(i);
            }
            int i2 = adbService.mConnectionPort.get();
            Intent intent = new Intent("com.android.server.adb.WIRELESS_DEBUG_STATUS");
            intent.putExtra(Constants.JSON_CLIENT_DATA_STATUS, i2 >= 0 ? 4 : 5);
            intent.putExtra("adb_port", i2);
            AdbDebuggingManager.sendBroadcastWithDebugPermission(adbService.mContext, intent, UserHandle.ALL);
            Slog.i("AdbService", "sent port broadcast port=" + i2);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdbManagerInternalImpl extends AdbManagerInternal {
        public AdbManagerInternalImpl() {
        }

        public final File getAdbKeysFile() {
            AdbDebuggingManager adbDebuggingManager = AdbService.this.mDebuggingManager;
            if (adbDebuggingManager == null) {
                return null;
            }
            return adbDebuggingManager.mUserKeyFile;
        }

        public final File getAdbTempKeysFile() {
            AdbDebuggingManager adbDebuggingManager = AdbService.this.mDebuggingManager;
            if (adbDebuggingManager == null) {
                return null;
            }
            return adbDebuggingManager.mTempKeysFile;
        }

        public final boolean isAdbEnabled(byte b) {
            if (b == 0) {
                return AdbService.this.mIsAdbUsbEnabled;
            }
            if (b == 1) {
                return AdbService.this.mIsAdbWifiEnabled;
            }
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(b, "isAdbEnabled called with unimplemented transport type="));
        }

        public final void notifyKeyFilesUpdated() {
            AdbDebuggingManager adbDebuggingManager = AdbService.this.mDebuggingManager;
            if (adbDebuggingManager == null) {
                return;
            }
            adbDebuggingManager.mHandler.sendEmptyMessage(28);
        }

        public final void registerTransport(IAdbTransport iAdbTransport) {
            AdbService.this.mTransports.put(iAdbTransport.asBinder(), iAdbTransport);
        }

        public final void startAdbdForTransport(byte b) {
            FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0(1), AdbService.this, Boolean.TRUE, Byte.valueOf(b)));
        }

        public final void stopAdbdForTransport(byte b) {
            FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0(1), AdbService.this, Boolean.FALSE, Byte.valueOf(b)));
        }

        public final void unregisterTransport(IAdbTransport iAdbTransport) {
            AdbService.this.mTransports.remove(iAdbTransport.asBinder());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdbSettingsObserver extends ContentObserver {
        public final Uri mAdbUsbUri;
        public final Uri mAdbWifiUri;

        /* renamed from: $r8$lambda$QsA9Am4S85-6tzWrx_VQgRFMz-I, reason: not valid java name */
        public static void m148$r8$lambda$QsA9Am4S856tzWrx_VQgRFMzI(AdbService adbService, final boolean z, final byte b) {
            int i = AdbService.$r8$clinit;
            adbService.getClass();
            Slog.d("AdbService", "setAdbEnabled(" + z + "), mIsAdbUsbEnabled=" + adbService.mIsAdbUsbEnabled + ", mIsAdbWifiEnabled=" + adbService.mIsAdbWifiEnabled + ", transportType=" + ((int) b));
            if (b == 0 && z != adbService.mIsAdbUsbEnabled) {
                adbService.mIsAdbUsbEnabled = z;
            } else {
                if (b != 1 || z == adbService.mIsAdbWifiEnabled) {
                    return;
                }
                adbService.mIsAdbWifiEnabled = z;
                if (!z) {
                    SystemProperties.set("persist.adb.tls_server.enable", "0");
                    AdbDebuggingManager.AdbConnectionPortPoller adbConnectionPortPoller = adbService.mConnectionPortPoller;
                    if (adbConnectionPortPoller != null) {
                        adbConnectionPortPoller.cancelAndWait();
                        adbService.mConnectionPortPoller = null;
                    }
                } else if (!((Boolean) AdbProperties.secure().orElse(Boolean.FALSE)).booleanValue() && adbService.mDebuggingManager == null) {
                    SystemProperties.set("persist.adb.tls_server.enable", "1");
                    AdbDebuggingManager.AdbConnectionPortPoller adbConnectionPortPoller2 = new AdbDebuggingManager.AdbConnectionPortPoller(adbService.mPortListener);
                    adbService.mConnectionPortPoller = adbConnectionPortPoller2;
                    adbConnectionPortPoller2.start();
                }
            }
            if (z) {
                SystemProperties.set("ctl.start", "adbd");
            } else if (!adbService.mIsAdbUsbEnabled && !adbService.mIsAdbWifiEnabled) {
                SystemProperties.set("ctl.stop", "adbd");
            }
            for (IAdbTransport iAdbTransport : adbService.mTransports.values()) {
                try {
                    iAdbTransport.onAdbEnabled(z, b);
                } catch (RemoteException unused) {
                    Slog.w("AdbService", "Unable to send onAdbEnabled to transport " + iAdbTransport.toString());
                }
            }
            AdbDebuggingManager adbDebuggingManager = adbService.mDebuggingManager;
            if (adbDebuggingManager != null) {
                adbDebuggingManager.setAdbEnabled(z, b);
            }
            Slog.d("AdbService", "Broadcasting enable = " + z + ", type = " + ((int) b));
            adbService.mCallbacks.broadcast(new Consumer() { // from class: com.android.server.adb.AdbService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    boolean z2 = z;
                    byte b2 = b;
                    IAdbCallback iAdbCallback = (IAdbCallback) obj;
                    StringBuilder m = AccessibilityManagerService$$ExternalSyntheticOutline0.m(b2, "Sending enable = ", ", type = ", " to ", z2);
                    m.append(iAdbCallback);
                    Slog.d("AdbService", m.toString());
                    try {
                        iAdbCallback.onDebuggingChanged(z2, b2);
                    } catch (RemoteException e) {
                        Slog.d("AdbService", "Unable to send onDebuggingChanged:", e);
                    }
                }
            });
        }

        public AdbSettingsObserver() {
            super(null);
            this.mAdbUsbUri = Settings.Global.getUriFor("adb_enabled");
            this.mAdbWifiUri = Settings.Global.getUriFor("adb_wifi_enabled");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            if (Settings.Secure.getInt(AdbService.this.mContext.getContentResolver(), "rampart_blocked_adb_cmd", 0) != 1) {
                if (this.mAdbUsbUri.equals(uri)) {
                    FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0(0), AdbService.this, Boolean.valueOf(Settings.Global.getInt(AdbService.this.mContentResolver, "adb_enabled", 0) > 0), (byte) 0));
                    return;
                } else {
                    if (this.mAdbWifiUri.equals(uri)) {
                        FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0(0), AdbService.this, Boolean.valueOf(Settings.Global.getInt(AdbService.this.mContentResolver, "adb_wifi_enabled", 0) > 0), (byte) 1));
                        return;
                    }
                    return;
                }
            }
            Slog.d("AdbService", "onChange : ADB is blocked by Auto Blocker");
            Handler handler = FgThread.getHandler();
            AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0 adbService$AdbSettingsObserver$$ExternalSyntheticLambda0 = new AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0(0);
            AdbService adbService = AdbService.this;
            Boolean bool = Boolean.FALSE;
            handler.sendMessage(PooledLambda.obtainMessage(adbService$AdbSettingsObserver$$ExternalSyntheticLambda0, adbService, bool, (byte) 1));
            FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0(0), AdbService.this, bool, (byte) 0));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public AdbService mAdbService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            int i2;
            if (i != 550) {
                if (i == 1000) {
                    FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$Lifecycle$$ExternalSyntheticLambda0(), this.mAdbService));
                    return;
                }
                return;
            }
            AdbService adbService = this.mAdbService;
            adbService.getClass();
            Slog.d("AdbService", "systemReady");
            String str = SystemProperties.get("persist.sys.usb.config", "");
            int indexOf = str.indexOf("adb");
            int i3 = 1;
            boolean z = indexOf >= 0 && (indexOf <= 0 || str.charAt(indexOf + (-1)) == ',') && ((i2 = 3 + indexOf) >= str.length() || str.charAt(i2) == ',');
            adbService.mIsAdbUsbEnabled = z;
            if (!z && !SystemProperties.getBoolean("persist.sys.test_harness", false)) {
                i3 = 0;
            }
            adbService.mIsAdbWifiEnabled = "1".equals(SystemProperties.get("persist.adb.tls_server.enable", "0"));
            try {
                Settings.Global.putInt(adbService.mContentResolver, "adb_enabled", i3);
                Settings.Global.putInt(adbService.mContentResolver, "adb_wifi_enabled", adbService.mIsAdbWifiEnabled ? 1 : 0);
            } catch (SecurityException unused) {
                Slog.d("AdbService", "ADB_ENABLED is restricted.");
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.adb.AdbService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? adbService = new AdbService(getContext());
            this.mAdbService = adbService;
            publishBinderService("adb", adbService);
        }
    }

    public AdbService(Context context) {
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.mContentResolver = contentResolver;
        this.mDebuggingManager = new AdbDebuggingManager(context, null, AdbDebuggingManager.getAdbFile("adb_keys"), AdbDebuggingManager.getAdbFile("adb_temp_keys.xml"), null, AdbDebuggingManager.SYSTEM_TICKER);
        try {
            this.mObserver = new AdbSettingsObserver();
            contentResolver.registerContentObserver(Settings.Global.getUriFor("adb_enabled"), false, this.mObserver);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("adb_wifi_enabled"), false, this.mObserver);
        } catch (Exception e) {
            Slog.e("AdbService", "Error in registerContentObservers", e);
        }
        LocalServices.addService(AdbManagerInternal.class, new AdbManagerInternalImpl());
    }

    public final void allowDebugging(boolean z, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            Message obtainMessage = adbDebuggingManager.mHandler.obtainMessage(3);
            obtainMessage.arg1 = z ? 1 : 0;
            obtainMessage.obj = str;
            adbDebuggingManager.mHandler.sendMessage(obtainMessage);
        }
    }

    public final void allowWirelessDebugging(boolean z, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            Message obtainMessage = adbDebuggingManager.mHandler.obtainMessage(18);
            obtainMessage.arg1 = z ? 1 : 0;
            obtainMessage.obj = str;
            adbDebuggingManager.mHandler.sendMessage(obtainMessage);
        }
    }

    public final void clearDebuggingKeys() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager == null) {
            throw new RuntimeException("Cannot clear ADB debugging keys, AdbDebuggingManager not enabled");
        }
        adbDebuggingManager.mHandler.sendEmptyMessage(6);
    }

    public final void denyDebugging() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.mHandler.sendEmptyMessage(4);
        }
    }

    public final void denyWirelessDebugging() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.mHandler.sendEmptyMessage(19);
        }
    }

    public final void disablePairing() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.mHandler.sendEmptyMessage(14);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0044, code lost:
    
        r7 = new com.android.internal.util.dump.DualDumpOutputStream(new android.util.proto.ProtoOutputStream(r6));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.FileDescriptor r6, java.io.PrintWriter r7, java.lang.String[] r8) {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            java.lang.String r1 = "AdbService"
            boolean r0 = com.android.internal.util.DumpUtils.checkDumpPermission(r0, r1, r7)
            if (r0 != 0) goto Lb
            return
        Lb:
            com.android.internal.util.IndentingPrintWriter r0 = new com.android.internal.util.IndentingPrintWriter
            java.lang.String r1 = "  "
            r0.<init>(r7, r1)
            long r2 = android.os.Binder.clearCallingIdentity()
            android.util.ArraySet r7 = new android.util.ArraySet     // Catch: java.lang.Throwable -> L40
            r7.<init>()     // Catch: java.lang.Throwable -> L40
            java.util.Collections.addAll(r7, r8)     // Catch: java.lang.Throwable -> L40
            java.lang.String r8 = "--proto"
            boolean r8 = r7.contains(r8)     // Catch: java.lang.Throwable -> L40
            int r4 = r7.size()     // Catch: java.lang.Throwable -> L40
            if (r4 == 0) goto L42
            java.lang.String r4 = "-a"
            boolean r7 = r7.contains(r4)     // Catch: java.lang.Throwable -> L40
            if (r7 != 0) goto L42
            if (r8 == 0) goto L35
            goto L42
        L35:
            java.lang.String r5 = "Dump current ADB state"
            r0.println(r5)     // Catch: java.lang.Throwable -> L40
            java.lang.String r5 = "  No commands available"
            r0.println(r5)     // Catch: java.lang.Throwable -> L40
            goto L68
        L40:
            r5 = move-exception
            goto L6c
        L42:
            if (r8 == 0) goto L4f
            com.android.internal.util.dump.DualDumpOutputStream r7 = new com.android.internal.util.dump.DualDumpOutputStream     // Catch: java.lang.Throwable -> L40
            android.util.proto.ProtoOutputStream r8 = new android.util.proto.ProtoOutputStream     // Catch: java.lang.Throwable -> L40
            r8.<init>(r6)     // Catch: java.lang.Throwable -> L40
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L40
            goto L5e
        L4f:
            java.lang.String r6 = "ADB MANAGER STATE (dumpsys adb):"
            r0.println(r6)     // Catch: java.lang.Throwable -> L40
            com.android.internal.util.dump.DualDumpOutputStream r7 = new com.android.internal.util.dump.DualDumpOutputStream     // Catch: java.lang.Throwable -> L40
            com.android.internal.util.IndentingPrintWriter r6 = new com.android.internal.util.IndentingPrintWriter     // Catch: java.lang.Throwable -> L40
            r6.<init>(r0, r1)     // Catch: java.lang.Throwable -> L40
            r7.<init>(r6)     // Catch: java.lang.Throwable -> L40
        L5e:
            com.android.server.adb.AdbDebuggingManager r5 = r5.mDebuggingManager     // Catch: java.lang.Throwable -> L40
            if (r5 == 0) goto L65
            r5.dump(r7)     // Catch: java.lang.Throwable -> L40
        L65:
            r7.flush()     // Catch: java.lang.Throwable -> L40
        L68:
            android.os.Binder.restoreCallingIdentity(r2)
            return
        L6c:
            android.os.Binder.restoreCallingIdentity(r2)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.adb.AdbService.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void enablePairingByPairingCode() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.mHandler.sendEmptyMessage(15);
        }
    }

    public final void enablePairingByQrCode(String str, String str2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        Preconditions.checkStringNotEmpty(str2);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.getClass();
            Bundle bundle = new Bundle();
            bundle.putString("serviceName", str);
            bundle.putString("password", str2);
            adbDebuggingManager.mHandler.sendMessage(Message.obtain(adbDebuggingManager.mHandler, 16, bundle));
        }
    }

    public final int getAdbWirelessPort() {
        int i;
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager == null) {
            return this.mConnectionPort.get();
        }
        synchronized (adbDebuggingManager.mAdbConnectionInfo) {
            AdbDebuggingManager.AdbConnectionInfo adbConnectionInfo = adbDebuggingManager.mAdbConnectionInfo;
            String str = adbConnectionInfo.mBssid;
            i = adbConnectionInfo.mPort;
        }
        return i;
    }

    public final FingerprintAndPairDevice[] getPairedDevices() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager == null) {
            return null;
        }
        adbDebuggingManager.getClass();
        HashMap hashMap = (HashMap) adbDebuggingManager.new AdbKeyStore().getPairedDevices();
        FingerprintAndPairDevice[] fingerprintAndPairDeviceArr = new FingerprintAndPairDevice[hashMap.size()];
        int i = 0;
        for (Map.Entry entry : hashMap.entrySet()) {
            FingerprintAndPairDevice fingerprintAndPairDevice = new FingerprintAndPairDevice();
            fingerprintAndPairDeviceArr[i] = fingerprintAndPairDevice;
            fingerprintAndPairDevice.keyFingerprint = (String) entry.getKey();
            fingerprintAndPairDeviceArr[i].device = (PairDevice) entry.getValue();
            i++;
        }
        return fingerprintAndPairDeviceArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
        return new AdbShellCommand(this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
    }

    public final boolean isAdbWifiQrSupported() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_DEBUGGING", "AdbService");
        return isAdbWifiSupported() && this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera.any");
    }

    public final boolean isAdbWifiSupported() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_DEBUGGING", "AdbService");
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi") || this.mContext.getPackageManager().hasSystemFeature("android.hardware.ethernet");
    }

    public final void registerCallback(IAdbCallback iAdbCallback) {
        Slog.d("AdbService", "Registering callback " + iAdbCallback);
        this.mCallbacks.register(iAdbCallback);
    }

    public final void unpairDevice(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.mHandler.sendMessage(Message.obtain(adbDebuggingManager.mHandler, 17, str));
        }
    }

    public final void unregisterCallback(IAdbCallback iAdbCallback) {
        Slog.d("AdbService", "Unregistering callback " + iAdbCallback);
        this.mCallbacks.unregister(iAdbCallback);
    }
}
