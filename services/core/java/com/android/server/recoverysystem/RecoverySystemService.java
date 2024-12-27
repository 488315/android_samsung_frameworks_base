package com.android.server.recoverysystem;

import android.apex.CompressedApexInfo;
import android.apex.CompressedApexInfoList;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.boot.IBootControl;
import android.hardware.security.secretkeeper.ISecretkeeper;
import android.hardware.security.secretkeeper.ISecretkeeper$Stub$Proxy;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRecoverySystem;
import android.os.IRecoverySystemProgressListener;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.ota.nano.OtaPackageMetadata;
import android.provider.DeviceConfig;
import android.security.AndroidKeyStoreMaintenance;
import android.security.KeyStoreException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.FastImmutableArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.RebootEscrowListener;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;
import com.android.server.utils.Slogf;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import libcore.io.IoUtils;

public final class RecoverySystemService extends IRecoverySystem.Stub implements RebootEscrowListener {
    static final String AB_UPDATE = "ro.build.ab_update";
    static final String INIT_SERVICE_CLEAR_BCB = "init.svc.clear-bcb";
    static final String INIT_SERVICE_SETUP_BCB = "init.svc.setup-bcb";
    static final String INIT_SERVICE_UNCRYPT = "init.svc.uncrypt";
    public final ArrayMap mCallerPendingRequest = new ArrayMap();
    public final ArraySet mCallerPreparedForReboot = new ArraySet();
    public final Context mContext;
    public final Injector mInjector;
    public static final Object sRequestLock = new Object();
    public static final FastImmutableArraySet FATAL_ARM_ESCROW_ERRORS = new FastImmutableArraySet(new Integer[]{2, 3, 4, 5, 6});

    public final class Injector {
        public final Context mContext;
        public final PreferencesManager mPrefs;

        public Injector(Context context) {
            this.mContext = context;
            this.mPrefs = new PreferencesManager(context);
        }

        public static UncryptSocket connectService() {
            UncryptSocket uncryptSocket = new UncryptSocket();
            uncryptSocket.mLocalSocket = new LocalSocket();
            int i = 0;
            while (true) {
                if (i >= 30) {
                    Slog.e("RecoverySystemService", "Timed out connecting to uncrypt socket");
                    uncryptSocket.close();
                    break;
                }
                try {
                    uncryptSocket.mLocalSocket.connect(new LocalSocketAddress("uncrypt", LocalSocketAddress.Namespace.RESERVED));
                    try {
                        uncryptSocket.mInputStream = new DataInputStream(uncryptSocket.mLocalSocket.getInputStream());
                        uncryptSocket.mOutputStream = new DataOutputStream(uncryptSocket.mLocalSocket.getOutputStream());
                        return uncryptSocket;
                    } catch (IOException unused) {
                        uncryptSocket.close();
                        uncryptSocket.close();
                        return null;
                    }
                } catch (IOException unused2) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        Slog.w("RecoverySystemService", "Interrupted:", e);
                    }
                    i++;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0087  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0089  */
        /* JADX WARN: Type inference failed for: r5v1, types: [android.hardware.boot.V1_1.IBootControl$Proxy, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r5v2, types: [android.hardware.boot.V1_1.IBootControl] */
        /* JADX WARN: Type inference failed for: r5v3, types: [android.hardware.boot.V1_1.IBootControl] */
        /* JADX WARN: Type inference failed for: r5v5 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static android.hardware.boot.IBootControl getBootControl() {
            /*
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = android.hardware.boot.IBootControl.DESCRIPTOR
                java.lang.String r2 = "/default"
                java.lang.String r0 = android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0.m(r0, r1, r2)
                boolean r2 = android.os.ServiceManager.isDeclared(r0)
                java.lang.String r3 = "RecoverySystemService"
                r4 = 0
                if (r2 == 0) goto L46
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r5 = "AIDL version of BootControl HAL present, using instance "
                r2.<init>(r5)
                r2.append(r0)
                java.lang.String r2 = r2.toString()
                android.util.Slog.i(r3, r2)
                android.os.IBinder r0 = android.os.ServiceManager.waitForDeclaredService(r0)
                int r2 = android.hardware.boot.IBootControl.Stub.$r8$clinit
                if (r0 != 0) goto L30
                goto L45
            L30:
                android.os.IInterface r1 = r0.queryLocalInterface(r1)
                if (r1 == 0) goto L3e
                boolean r2 = r1 instanceof android.hardware.boot.IBootControl
                if (r2 == 0) goto L3e
                r4 = r1
                android.hardware.boot.IBootControl r4 = (android.hardware.boot.IBootControl) r4
                goto L45
            L3e:
                android.hardware.boot.IBootControl$Stub$Proxy r4 = new android.hardware.boot.IBootControl$Stub$Proxy
                r4.<init>()
                r4.mRemote = r0
            L45:
                return r4
            L46:
                android.hardware.boot.V1_0.IBootControl r0 = android.hardware.boot.V1_0.IBootControl.getService()
                if (r0 != 0) goto L4e
            L4c:
                r5 = r4
                goto L85
            L4e:
                android.os.IHwBinder r1 = r0.asBinder()
                if (r1 != 0) goto L55
                goto L4c
            L55:
                java.lang.String r2 = "android.hardware.boot@1.1::IBootControl"
                android.os.IHwInterface r5 = r1.queryLocalInterface(r2)
                if (r5 == 0) goto L64
                boolean r6 = r5 instanceof android.hardware.boot.V1_1.IBootControl
                if (r6 == 0) goto L64
                android.hardware.boot.V1_1.IBootControl r5 = (android.hardware.boot.V1_1.IBootControl) r5
                goto L85
            L64:
                android.hardware.boot.V1_1.IBootControl$Proxy r5 = new android.hardware.boot.V1_1.IBootControl$Proxy
                r5.<init>()
                r5.mRemote = r1
                java.util.ArrayList r1 = r5.interfaceChain()     // Catch: android.os.RemoteException -> L4c
                java.util.Iterator r1 = r1.iterator()     // Catch: android.os.RemoteException -> L4c
            L73:
                boolean r6 = r1.hasNext()     // Catch: android.os.RemoteException -> L4c
                if (r6 == 0) goto L4c
                java.lang.Object r6 = r1.next()     // Catch: android.os.RemoteException -> L4c
                java.lang.String r6 = (java.lang.String) r6     // Catch: android.os.RemoteException -> L4c
                boolean r6 = r6.equals(r2)     // Catch: android.os.RemoteException -> L4c
                if (r6 == 0) goto L73
            L85:
                if (r0 != 0) goto L89
                r1 = r4
                goto L91
            L89:
                android.os.IHwBinder r1 = r0.asBinder()
                android.hardware.boot.V1_2.IBootControl$Proxy r1 = android.hardware.boot.V1_2.IBootControl$Proxy.asInterface(r1)
            L91:
                com.android.server.recoverysystem.hal.BootControlHIDL r2 = new com.android.server.recoverysystem.hal.BootControlHIDL
                r2.<init>(r0, r5, r1)
                android.hardware.boot.V1_0.IBootControl.getService()     // Catch: java.lang.Throwable -> Lac
                java.lang.String r0 = "android.hardware.boot@1.2::IBootControl"
                java.lang.String r1 = "default"
                r5 = 1
                android.os.IHwBinder r0 = android.os.HwBinder.getService(r0, r1, r5)     // Catch: java.lang.Throwable -> La6
                android.hardware.boot.V1_2.IBootControl$Proxy.asInterface(r0)     // Catch: java.lang.Throwable -> La6
                return r2
            La6:
                java.lang.String r0 = "Device doesn't implement boot control HAL V1_2."
                android.util.Slog.w(r3, r0)
                return r4
            Lac:
                java.lang.String r0 = "Neither AIDL nor HIDL version of the BootControl HAL is present."
                android.util.Slog.e(r3, r0)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.recoverysystem.RecoverySystemService.Injector.getBootControl():android.hardware.boot.IBootControl");
        }

        public final int getUidFromPackageName(String str) {
            try {
                return this.mContext.getPackageManager().getPackageUidAsUser(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Failed to find uid for ", str, "RecoverySystemService");
                return -1;
            }
        }
    }

    public final class Lifecycle extends SystemService {
        public RecoverySystemService mRecoverySystemService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 500) {
                this.mRecoverySystemService.onSystemServicesReady();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.recoverysystem.RecoverySystemService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? recoverySystemService = new RecoverySystemService(new Injector(getContext()));
            this.mRecoverySystemService = recoverySystemService;
            publishBinderService("recovery", recoverySystemService);
        }
    }

    public final class PreferencesManager {
        public final File mMetricsPrefsFile;
        public final SharedPreferences mSharedPreferences;

        public PreferencesManager(Context context) {
            File file = new File(new File(Environment.getDataSystemCeDirectory(0), "recovery_system"), "RecoverySystemMetricsPrefs.xml");
            this.mMetricsPrefsFile = file;
            this.mSharedPreferences = context.getSharedPreferences(file, 0);
        }

        public final synchronized void incrementIntKey(String str) {
            this.mSharedPreferences.edit().putInt(str, this.mSharedPreferences.getInt(str, 0) + 1).commit();
        }
    }

    public final class RebootPreparationError {
        public final int mProviderErrorCode;
        public final int mRebootErrorCode;

        public RebootPreparationError(int i, int i2) {
            this.mRebootErrorCode = i;
            this.mProviderErrorCode = i2;
        }
    }

    public final class UncryptSocket {
        public DataInputStream mInputStream;
        public LocalSocket mLocalSocket;
        public DataOutputStream mOutputStream;

        public final void close() {
            IoUtils.closeQuietly(this.mInputStream);
            IoUtils.closeQuietly(this.mOutputStream);
            IoUtils.closeQuietly(this.mLocalSocket);
        }

        public final void sendCommand(String str) {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            this.mOutputStream.writeInt(bytes.length);
            this.mOutputStream.write(bytes, 0, bytes.length);
        }
    }

    public RecoverySystemService(Injector injector) {
        this.mInjector = injector;
        this.mContext = injector.mContext;
    }

    public static CompressedApexInfoList getCompressedApexInfoList(String str) {
        ZipFile zipFile = new ZipFile(str);
        try {
            ZipEntry entry = zipFile.getEntry("apex_info.pb");
            if (entry == null) {
                zipFile.close();
                return null;
            }
            if (entry.getSize() >= 2457600) {
                throw new IllegalArgumentException("apex_info.pb has size " + entry.getSize() + " which is larger than the permitted limit2457600");
            }
            if (entry.getSize() == 0) {
                CompressedApexInfoList compressedApexInfoList = new CompressedApexInfoList();
                compressedApexInfoList.apexInfos = new CompressedApexInfo[0];
                zipFile.close();
                return compressedApexInfoList;
            }
            Log.i("RecoverySystemService", "Allocating " + entry.getSize() + " bytes of memory to store OTA Metadata");
            int size = (int) entry.getSize();
            byte[] bArr = new byte[size];
            InputStream inputStream = zipFile.getInputStream(entry);
            try {
                int read = inputStream.read(bArr);
                String str2 = "Read " + read + " when expecting " + size;
                Log.e("RecoverySystemService", str2);
                if (read != size) {
                    throw new IOException(str2);
                }
                inputStream.close();
                OtaPackageMetadata.ApexMetadata parseFrom = OtaPackageMetadata.ApexMetadata.parseFrom(bArr);
                CompressedApexInfoList compressedApexInfoList2 = new CompressedApexInfoList();
                compressedApexInfoList2.apexInfos = (CompressedApexInfo[]) Arrays.stream(parseFrom.apexInfo).filter(new RecoverySystemService$$ExternalSyntheticLambda0()).map(new RecoverySystemService$$ExternalSyntheticLambda1()).toArray(new RecoverySystemService$$ExternalSyntheticLambda2());
                zipFile.close();
                return compressedApexInfoList2;
            } finally {
            }
        } catch (Throwable th) {
            try {
                zipFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.hardware.security.secretkeeper.ISecretkeeper] */
    public static ISecretkeeper getSecretKeeper() {
        ISecretkeeper$Stub$Proxy iSecretkeeper$Stub$Proxy;
        try {
            StringBuilder sb = new StringBuilder();
            String str = ISecretkeeper.DESCRIPTOR;
            sb.append(str);
            sb.append("/default");
            IBinder waitForDeclaredService = ServiceManager.waitForDeclaredService(sb.toString());
            if (waitForDeclaredService == null) {
                return null;
            }
            IInterface queryLocalInterface = waitForDeclaredService.queryLocalInterface(str);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ISecretkeeper)) {
                ISecretkeeper$Stub$Proxy iSecretkeeper$Stub$Proxy2 = new ISecretkeeper$Stub$Proxy();
                iSecretkeeper$Stub$Proxy2.mRemote = waitForDeclaredService;
                iSecretkeeper$Stub$Proxy = iSecretkeeper$Stub$Proxy2;
            } else {
                iSecretkeeper$Stub$Proxy = (ISecretkeeper) queryLocalInterface;
            }
            return iSecretkeeper$Stub$Proxy;
        } catch (SecurityException unused) {
            Slog.w("RecoverySystemService", "Does not have permissions to get AIDL secretkeeper service");
            return null;
        }
    }

    public static void sendPreparedForRebootIntentIfNeeded(IntentSender intentSender) {
        if (intentSender != null) {
            try {
                intentSender.sendIntent(null, 0, null, null, null);
            } catch (IntentSender.SendIntentException e) {
                Slog.w("RecoverySystemService", "Could not send intent for prepared reboot: " + e.getMessage());
            }
        }
    }

    public final boolean allocateSpaceForUpdate(String str) {
        allocateSpaceForUpdate_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    CompressedApexInfoList compressedApexInfoList = getCompressedApexInfoList(str);
                    if (compressedApexInfoList == null) {
                        Log.i("RecoverySystemService", "apex_info.pb not present in OTA package. Assuming device doesn't support compressedAPEX, continueing without allocating space.");
                        return true;
                    }
                    ((ApexManager.ApexManagerImpl) ApexManager.getInstance()).waitForApexService().reserveSpaceForCompressedApex(compressedApexInfoList);
                    return true;
                } catch (IOException | UnsupportedOperationException e) {
                    Slog.e("RecoverySystemService", "Failed to reserve space for compressed apex: ", e);
                    return false;
                }
            } catch (RemoteException e2) {
                e2.rethrowAsRuntimeException();
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean checkAndWaitForUncryptService() {
        for (int i = 0; i < 30; i++) {
            this.mInjector.getClass();
            String str = SystemProperties.get(INIT_SERVICE_UNCRYPT);
            this.mInjector.getClass();
            String str2 = SystemProperties.get(INIT_SERVICE_SETUP_BCB);
            this.mInjector.getClass();
            String str3 = SystemProperties.get(INIT_SERVICE_CLEAR_BCB);
            if (!INetd.IF_FLAG_RUNNING.equals(str) && !INetd.IF_FLAG_RUNNING.equals(str2) && !INetd.IF_FLAG_RUNNING.equals(str3)) {
                return true;
            }
            try {
                this.mInjector.getClass();
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Slog.w("RecoverySystemService", "Interrupted:", e);
            }
        }
        return false;
    }

    public final boolean clearBcb() {
        boolean z;
        synchronized (sRequestLock) {
            z = setupOrClearBcb(null, false);
        }
        return z;
    }

    public final boolean clearLskf(String str) {
        int i;
        enforcePermissionForResumeOnReboot();
        if (str == null) {
            Slog.w("RecoverySystemService", "Missing packageName when clearing lskf.");
            return false;
        }
        synchronized (this) {
            if (this.mCallerPreparedForReboot.contains(str) || this.mCallerPendingRequest.containsKey(str)) {
                this.mCallerPendingRequest.remove(str);
                this.mCallerPreparedForReboot.remove(str);
                i = (this.mCallerPendingRequest.isEmpty() && this.mCallerPreparedForReboot.isEmpty()) ? 1 : 2;
            } else {
                Slog.w("RecoverySystemService", str.concat(" hasn't prepared for resume on reboot"));
                i = 0;
            }
        }
        if (i == 0) {
            Slog.w("RecoverySystemService", "RoR clear called before preparation for caller ".concat(str));
            return true;
        }
        if (i != 1) {
            if (i == 2) {
                return true;
            }
            throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unsupported action type on clear "));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mInjector.getClass();
            LockSettingsInternal lockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
            if (lockSettingsInternal != null) {
                return lockSettingsInternal.clearRebootEscrow();
            }
            Slog.e("RecoverySystemService", "Failed to get lock settings service, skipping clearRebootEscrow");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void enforcePermissionForResumeOnReboot() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.RECOVERY") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.REBOOT") != 0) {
            throw new SecurityException("Caller must have android.permission.RECOVERY or android.permission.REBOOT for resume on reboot.");
        }
    }

    public final boolean isLskfCaptured(String str) {
        boolean contains;
        enforcePermissionForResumeOnReboot();
        synchronized (this) {
            contains = this.mCallerPreparedForReboot.contains(str);
        }
        if (contains) {
            return true;
        }
        Slog.i("RecoverySystemService", "Reboot requested before prepare completed for caller " + str);
        return false;
    }

    public final void onPreparedForReboot(boolean z) {
        ArrayList arrayList;
        if (z) {
            synchronized (this) {
                try {
                    if (!this.mCallerPreparedForReboot.isEmpty()) {
                        Slog.w("RecoverySystemService", "onPreparedForReboot called when some clients have prepared.");
                    }
                    if (this.mCallerPendingRequest.isEmpty()) {
                        Slog.w("RecoverySystemService", "onPreparedForReboot called but no client has requested.");
                    }
                    for (int i = 0; i < this.mCallerPendingRequest.size(); i++) {
                        sendPreparedForRebootIntentIfNeeded((IntentSender) this.mCallerPendingRequest.valueAt(i));
                        this.mCallerPreparedForReboot.add((String) this.mCallerPendingRequest.keyAt(i));
                    }
                    this.mCallerPendingRequest.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mInjector.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            synchronized (this) {
                arrayList = new ArrayList(this.mCallerPreparedForReboot);
            }
            PreferencesManager preferencesManager = this.mInjector.mPrefs;
            preferencesManager.mSharedPreferences.edit().putLong("lskf_captured_timestamp", currentTimeMillis).commit();
            preferencesManager.incrementIntKey("lskf_captured_count");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                int uidFromPackageName = this.mInjector.getUidFromPackageName(str);
                long j = preferencesManager.mSharedPreferences.getLong(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_request_lskf_timestamp"), -1L);
                int i2 = (j == -1 || currentTimeMillis <= j) ? -1 : ((int) (currentTimeMillis - j)) / 1000;
                Slog.i("RecoverySystemService", String.format("Reporting lskf captured, lskf capture takes %d seconds for package %s", Integer.valueOf(i2), str));
                Injector injector = this.mInjector;
                int size = arrayList.size();
                injector.getClass();
                FrameworkStatsLog.write(FrameworkStatsLog.REBOOT_ESCROW_LSKF_CAPTURE_REPORTED, uidFromPackageName, size, i2);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new SecurityException("Caller must be shell");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            new RecoverySystemShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onSystemServicesReady() {
        this.mInjector.getClass();
        LockSettingsInternal lockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
        if (lockSettingsInternal == null) {
            Slog.e("RecoverySystemService", "Failed to get lock settings service, skipping set RebootEscrowListener");
        } else {
            lockSettingsInternal.setRebootEscrowListener(this);
        }
    }

    public final void rebootRecoveryWithCommand(String str) {
        FileWriter fileWriter;
        boolean z = str != null && str.contains("--wipe_data");
        synchronized (sRequestLock) {
            try {
                if (!setupOrClearBcb(str, true)) {
                    Slog.e("RecoverySystemService", "rebootRecoveryWithCommand failed to setup BCB");
                    return;
                }
                if (z) {
                    Slogf.w("RecoverySystemService", "deleteSecrets");
                    try {
                        AndroidKeyStoreMaintenance.deleteAllKeys();
                    } catch (KeyStoreException e) {
                        Log.wtf("RecoverySystemService", "Failed to delete all keys from keystore.", e);
                    }
                    try {
                        ISecretkeeper secretKeeper = getSecretKeeper();
                        if (secretKeeper != null) {
                            Slogf.i("RecoverySystemService", "ISecretkeeper.deleteAll();");
                            ((ISecretkeeper$Stub$Proxy) secretKeeper).deleteAll();
                        }
                    } catch (RemoteException e2) {
                        Log.wtf("RecoverySystemService", "Failed to delete all secrets from secretkeeper.", e2);
                    }
                }
                PowerManager powerManager = (PowerManager) this.mInjector.mContext.getSystemService("power");
                Slog.d("RecoverySystemService", "!@[RecoverySystemService] rebootRecoveryWithCommand: [reset tracking] write to recovery_cause");
                try {
                    fileWriter = new FileWriter("/sys/class/sec/sec_debug/recovery_cause");
                } catch (IOException e3) {
                    Slog.e("RecoverySystemService", "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e3);
                }
                try {
                    fileWriter.write("RecoverySystemService rebootRecoveryWithCommand: " + str);
                    fileWriter.close();
                    powerManager.reboot("recovery");
                } catch (Throwable th) {
                    try {
                        fileWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final int rebootWithLskf(String str, String str2, boolean z) {
        enforcePermissionForResumeOnReboot();
        return rebootWithLskfImpl(str, str2, z);
    }

    public final int rebootWithLskfAssumeSlotSwitch(String str, String str2) {
        rebootWithLskfAssumeSlotSwitch_enforcePermission();
        return rebootWithLskfImpl(str, str2, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int rebootWithLskfImpl(String str, String str2, boolean z) {
        RebootPreparationError rebootPreparationError;
        IBootControl bootControl;
        int currentSlot;
        int activeBootSlot;
        int size;
        if (str == null) {
            Slog.w("RecoverySystemService", "Missing packageName when rebooting with lskf.");
            rebootPreparationError = new RebootPreparationError(2000, 0);
        } else if (isLskfCaptured(str)) {
            this.mInjector.getClass();
            if ("true".equalsIgnoreCase(SystemProperties.get(AB_UPDATE))) {
                try {
                    this.mInjector.getClass();
                    bootControl = Injector.getBootControl();
                } catch (RemoteException e) {
                    AccountManagerService$$ExternalSyntheticOutline0.m("Failed to get the boot control HAL ", e, "RecoverySystemService");
                }
                if (bootControl == null) {
                    Slog.w("RecoverySystemService", "Cannot get the boot control HAL, skipping slot verification.");
                } else {
                    try {
                        currentSlot = bootControl.getCurrentSlot();
                        if (currentSlot != 0 && currentSlot != 1) {
                            throw new IllegalStateException("Current boot slot should be 0 or 1, got " + currentSlot);
                        }
                        activeBootSlot = bootControl.getActiveBootSlot();
                        if (z) {
                            currentSlot = currentSlot == 0 ? 1 : 0;
                        }
                    } catch (RemoteException e2) {
                        Slog.w("RecoverySystemService", "Failed to query the active slots", e2);
                    }
                    if (activeBootSlot != currentSlot) {
                        PendingIntentController$$ExternalSyntheticOutline0.m(currentSlot, activeBootSlot, "The next active boot slot doesn't match the expected value, expected ", ", got ", "RecoverySystemService");
                        rebootPreparationError = new RebootPreparationError(4000, 0);
                    }
                }
            } else {
                Slog.w("RecoverySystemService", "Device isn't a/b, skipping slot verification.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mInjector.getClass();
                LockSettingsInternal lockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
                if (lockSettingsInternal == null) {
                    Slog.e("RecoverySystemService", "Failed to get lock settings service, skipping armRebootEscrow");
                    rebootPreparationError = new RebootPreparationError(5000, 3);
                } else {
                    int armRebootEscrow = lockSettingsInternal.armRebootEscrow();
                    if (armRebootEscrow != 0) {
                        String str3 = "Failure to escrow key for reboot, providerErrorCode: " + armRebootEscrow;
                        Slog.w("RecoverySystemService", str3);
                        rebootPreparationError = new RebootPreparationError(5000, armRebootEscrow);
                        clearCallingIdentity = str3;
                    } else {
                        rebootPreparationError = new RebootPreparationError(0, 0);
                        clearCallingIdentity = clearCallingIdentity;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            rebootPreparationError = new RebootPreparationError(3000, 0);
        }
        int uidFromPackageName = this.mInjector.getUidFromPackageName(str);
        long clearCallingIdentity2 = Binder.clearCallingIdentity();
        try {
            boolean z2 = DeviceConfig.getBoolean("ota", "server_based_ror_enabled", false);
            synchronized (this) {
                size = this.mCallerPreparedForReboot.size();
            }
            this.mInjector.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            PreferencesManager preferencesManager = this.mInjector.mPrefs;
            long j = preferencesManager.mSharedPreferences.getLong("lskf_captured_timestamp", -1L);
            int i = (j == -1 || currentTimeMillis <= j) ? -1 : ((int) (currentTimeMillis - j)) / 1000;
            int i2 = preferencesManager.mSharedPreferences.getInt(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_request_lskf_count"), -1);
            int i3 = preferencesManager.mSharedPreferences.getInt("lskf_captured_count", -1);
            Slog.i("RecoverySystemService", String.format("Reporting reboot with lskf, package name %s, client count %d, request count %d, lskf captured count %d, duration since lskf captured %d seconds.", str, Integer.valueOf(size), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i)));
            Injector injector = this.mInjector;
            int i4 = rebootPreparationError.mRebootErrorCode + rebootPreparationError.mProviderErrorCode;
            injector.getClass();
            FrameworkStatsLog.write(FrameworkStatsLog.REBOOT_ESCROW_REBOOT_REPORTED, i4, uidFromPackageName, size, i2, z, z2, i, i3);
            if (FATAL_ARM_ESCROW_ERRORS.contains(Integer.valueOf(rebootPreparationError.mProviderErrorCode))) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("Clearing resume on reboot states for all clients on arm escrow error: "), rebootPreparationError.mProviderErrorCode, "RecoverySystemService");
                synchronized (this) {
                    this.mCallerPendingRequest.clear();
                    this.mCallerPreparedForReboot.clear();
                }
            }
            int i5 = rebootPreparationError.mRebootErrorCode;
            if (i5 != 0) {
                return i5;
            }
            if (!this.mInjector.mPrefs.mMetricsPrefsFile.delete()) {
                Slog.w("RecoverySystemService", "Failed to delete metrics prefs");
            }
            Watchdog.getInstance().pauseWatchingCurrentThreadFor("reboot can be slow");
            ((PowerManager) this.mInjector.mContext.getSystemService("power")).reboot(str2);
            return 1000;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity2);
        }
    }

    public final boolean requestLskf(String str, IntentSender intentSender) {
        int i;
        int size;
        enforcePermissionForResumeOnReboot();
        if (str == null) {
            Slog.w("RecoverySystemService", "Missing packageName when requesting lskf.");
            return false;
        }
        synchronized (this) {
            try {
                if (this.mCallerPreparedForReboot.isEmpty()) {
                    boolean isEmpty = this.mCallerPendingRequest.isEmpty();
                    if (this.mCallerPendingRequest.containsKey(str)) {
                        Slog.i("RecoverySystemService", "Duplicate RoR preparation request for ".concat(str));
                    }
                    this.mCallerPendingRequest.put(str, intentSender);
                    i = isEmpty ? 0 : 2;
                } else {
                    if (this.mCallerPreparedForReboot.contains(str)) {
                        Slog.i("RecoverySystemService", "RoR already has prepared for ".concat(str));
                    }
                    this.mCallerPreparedForReboot.add(str);
                    i = 1;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        int uidFromPackageName = this.mInjector.getUidFromPackageName(str);
        synchronized (this) {
            size = this.mCallerPendingRequest.size();
        }
        PreferencesManager preferencesManager = this.mInjector.mPrefs;
        String concat = str.concat("_request_lskf_timestamp");
        this.mInjector.getClass();
        preferencesManager.mSharedPreferences.edit().putLong(concat, System.currentTimeMillis()).commit();
        preferencesManager.incrementIntKey(str.concat("_request_lskf_count"));
        this.mInjector.getClass();
        FrameworkStatsLog.write(FrameworkStatsLog.REBOOT_ESCROW_PREPARATION_REPORTED, uidFromPackageName, i, size);
        if (i != 0) {
            if (i == 1) {
                sendPreparedForRebootIntentIfNeeded(intentSender);
                return true;
            }
            if (i == 2) {
                return true;
            }
            throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unsupported action type on new request "));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mInjector.getClass();
            LockSettingsInternal lockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
            if (lockSettingsInternal == null) {
                Slog.e("RecoverySystemService", "Failed to get lock settings service, skipping prepareRebootEscrow");
                return false;
            }
            if (lockSettingsInternal.prepareRebootEscrow()) {
                return true;
            }
            synchronized (this) {
                this.mCallerPendingRequest.clear();
                this.mCallerPreparedForReboot.clear();
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setupBcb(String str) {
        boolean z;
        synchronized (sRequestLock) {
            z = setupOrClearBcb(str, true);
        }
        return z;
    }

    public final boolean setupOrClearBcb(String str, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVERY", null);
        if (!checkAndWaitForUncryptService()) {
            Slog.e("RecoverySystemService", "uncrypt service is unavailable.");
            return false;
        }
        if (z) {
            this.mInjector.getClass();
            SystemProperties.set("ctl.start", "setup-bcb");
        } else {
            this.mInjector.getClass();
            SystemProperties.set("ctl.start", "clear-bcb");
        }
        this.mInjector.getClass();
        UncryptSocket connectService = Injector.connectService();
        if (connectService == null) {
            Slog.e("RecoverySystemService", "Failed to connect to uncrypt socket");
            return false;
        }
        try {
            if (z) {
                try {
                    connectService.sendCommand(str);
                } catch (IOException e) {
                    Slog.e("RecoverySystemService", "IOException when communicating with uncrypt:", e);
                    connectService.close();
                    return false;
                }
            }
            int readInt = connectService.mInputStream.readInt();
            connectService.mOutputStream.writeInt(0);
            if (readInt != 100) {
                Slog.e("RecoverySystemService", "uncrypt failed with status: " + readInt);
                connectService.close();
                return false;
            }
            StringBuilder sb = new StringBuilder("uncrypt ");
            sb.append(z ? "setup" : "clear");
            sb.append(" bcb successfully finished.");
            Slog.i("RecoverySystemService", sb.toString());
            connectService.close();
            return true;
        } catch (Throwable th) {
            connectService.close();
            throw th;
        }
    }

    public final boolean uncrypt(String str, IRecoverySystemProgressListener iRecoverySystemProgressListener) {
        int readInt;
        synchronized (sRequestLock) {
            try {
                this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVERY", null);
                if (!checkAndWaitForUncryptService()) {
                    Slog.e("RecoverySystemService", "uncrypt service is unavailable.");
                    return false;
                }
                this.mInjector.getClass();
                File file = RecoverySystem.UNCRYPT_PACKAGE_FILE;
                file.delete();
                try {
                    this.mInjector.getClass();
                    FileWriter fileWriter = new FileWriter(file);
                    try {
                        fileWriter.write(str + "\n");
                        fileWriter.close();
                        this.mInjector.getClass();
                        SystemProperties.set("ctl.start", "uncrypt");
                        this.mInjector.getClass();
                        UncryptSocket connectService = Injector.connectService();
                        if (connectService == null) {
                            Slog.e("RecoverySystemService", "Failed to connect to uncrypt socket");
                            return false;
                        }
                        int i = Integer.MIN_VALUE;
                        while (true) {
                            try {
                                readInt = connectService.mInputStream.readInt();
                                if (readInt != i || i == Integer.MIN_VALUE) {
                                    if (readInt < 0 || readInt > 100) {
                                        break;
                                    }
                                    Slog.i("RecoverySystemService", "uncrypt read status: " + readInt);
                                    if (iRecoverySystemProgressListener != null) {
                                        try {
                                            iRecoverySystemProgressListener.onProgress(readInt);
                                        } catch (RemoteException unused) {
                                            Slog.w("RecoverySystemService", "RemoteException when posting progress");
                                        }
                                    }
                                    if (readInt == 100) {
                                        Slog.i("RecoverySystemService", "uncrypt successfully finished.");
                                        connectService.mOutputStream.writeInt(0);
                                        return true;
                                    }
                                    i = readInt;
                                }
                            } catch (IOException e) {
                                Slog.e("RecoverySystemService", "IOException when reading status: ", e);
                                return false;
                            } finally {
                                connectService.close();
                            }
                        }
                        Slog.e("RecoverySystemService", "uncrypt failed with status: " + readInt);
                        connectService.mOutputStream.writeInt(0);
                        return false;
                    } catch (Throwable th) {
                        try {
                            fileWriter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    StringBuilder sb = new StringBuilder("IOException when writing \"");
                    this.mInjector.getClass();
                    sb.append(RecoverySystem.UNCRYPT_PACKAGE_FILE.getName());
                    sb.append("\":");
                    Slog.e("RecoverySystemService", sb.toString(), e2);
                    return false;
                }
            } finally {
            }
        }
    }
}
