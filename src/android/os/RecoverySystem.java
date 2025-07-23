package android.os;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IRecoverySystemProgressListener;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.RestrictionPolicy;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.AndroidKeyStoreMaintenance;
import android.security.KeyStoreException;
import android.system.ErrnoException;
import android.system.Os;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

/* loaded from: classes3.dex */
public class RecoverySystem {
    private static final String ACTION_EUICC_FACTORY_RESET = "com.android.internal.action.EUICC_FACTORY_RESET";
    private static final String ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS = "com.android.internal.action.EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS";
    public static final File BLOCK_MAP_FILE;
    private static File COMMAND_FILE = null;
    private static final long DEFAULT_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 30000;
    private static final long DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 45000;
    private static final File DEFAULT_KEYSTORE = new File("/system/etc/security/otacerts.zip");
    private static final String LAST_CACHE_SUDDEN_RESET_LOG_PATH = "/data/log/recovery_last_sudden_reset.log";
    private static final String LAST_INSTALL_PATH = "last_install";
    private static final String LAST_PREFIX = "last_";
    private static final String LAST_RECOVERY_MODE = "dev.lastrecoverymode";
    private static final File LOG_FILE;
    private static final int LOG_FILE_MAX_LENGTH = 65536;
    private static final long MAX_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 60000;
    private static final long MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 90000;
    private static final long MIN_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 5000;
    private static final long MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 15000;
    private static final String PACKAGE_NAME_EUICC_DATA_MANAGEMENT_CALLBACK = "android";
    private static final long PUBLISH_PROGRESS_INTERVAL_MS = 500;
    private static final File RECOVERY_DIR;
    public static final File RECOVERY_RESCUEPARTY_FILE;
    private static final String RECOVERY_WIPE_DATA_COMMAND = "--wipe_data";
    private static final int RESCUEPARTY_LOG_MAX_LENGTH = 524288;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_INVALID_PACKAGE_NAME = 2000;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_LSKF_NOT_CAPTURED = 3000;
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_NONE = 0;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_PROVIDER_PREPARATION_FAILURE = 5000;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_SLOT_MISMATCH = 4000;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_UNSPECIFIED = 1000;
    private static final String SUDDEN_RESET_LAST_KMSG_NAME = "recovery_sudden_reset_last_kmsg.log";
    private static final String TAG = "RecoverySystem";
    private static final String TMP_RECOVERY_LOG_PATH = "/efs/recovery/tmp_recovery.log";
    public static final File UNCRYPT_PACKAGE_FILE;
    public static final File UNCRYPT_STATUS_FILE;
    private static Boolean mShutdownIsInProgress;
    private static final Object mShutdownIsInProgressLock;
    private static final Object sRequestLock;
    private final IRecoverySystem mService;

    public interface ProgressListener {
        void onProgress(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResumeOnRebootRebootErrorCode {
    }

    @SystemApi
    @Deprecated
    public static boolean verifyPackageCompatibility(File file) throws IOException {
        return true;
    }

    @Deprecated
    private static boolean verifyPackageCompatibility(InputStream inputStream) throws IOException {
        return true;
    }

    static {
        File file = new File("/cache/recovery");
        RECOVERY_DIR = file;
        LOG_FILE = new File(file, "log");
        COMMAND_FILE = new File(file, "command");
        mShutdownIsInProgressLock = new Object();
        mShutdownIsInProgress = false;
        RECOVERY_RESCUEPARTY_FILE = new File(file, "rescueparty_log");
        BLOCK_MAP_FILE = new File(file, "block.map");
        UNCRYPT_PACKAGE_FILE = new File(file, "uncrypt_file");
        UNCRYPT_STATUS_FILE = new File(file, "uncrypt_status");
        sRequestLock = new Object();
    }

    private static HashSet<X509Certificate> getTrustedCerts(File file) throws IOException, GeneralSecurityException {
        HashSet<X509Certificate> hashSet = new HashSet<>();
        if (file == null) {
            file = DEFAULT_KEYSTORE;
        }
        ZipFile zipFile = new ZipFile(file);
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                InputStream inputStream = zipFile.getInputStream(entries.nextElement());
                try {
                    hashSet.add((X509Certificate) certificateFactory.generateCertificate(inputStream));
                    inputStream.close();
                } finally {
                }
            }
            return hashSet;
        } finally {
            zipFile.close();
        }
    }

    private static long parseSuperUsedSize(File file) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(file);
            try {
                ZipEntry entry = zipFile.getEntry("super_used_size.txt");
                if (entry != null) {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    if (inputStream != null) {
                        byte[] bArr = new byte[8];
                        if (inputStream.read(bArr) > 0) {
                            String str = new String(bArr);
                            Log.i(TAG, "!@RecoverySystem super_used_size: ".concat(str));
                            long parseLong = Long.parseLong(str);
                            zipFile.close();
                            return parseLong;
                        }
                        Log.e(TAG, "!@RecoverySystem failed to read super_used_size");
                        inputStream.close();
                    } else {
                        Log.e(TAG, "!@RecoverySystem failed to get inputStream");
                    }
                } else {
                    Log.e(TAG, "!@RecoverySystem failed to get zipEntry");
                }
                zipFile.close();
                zipFile.close();
                return 0L;
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "!@RecoverySystem IOException when reading package files", e);
            return 0L;
        }
    }

    public static void verifyPackage(File file, ProgressListener progressListener, File file2) throws IOException, GeneralSecurityException {
        long length = file.length();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            long currentTimeMillis = System.currentTimeMillis();
            if (progressListener != null) {
                progressListener.onProgress(0);
            }
            randomAccessFile.seek(length - 6);
            byte[] bArr = new byte[6];
            randomAccessFile.readFully(bArr);
            if (bArr[2] != -1 || bArr[3] != -1) {
                throw new SignatureException("no signature in file (no footer)");
            }
            int i = (bArr[4] & 255) | ((bArr[5] & 255) << 8);
            int i2 = ((bArr[1] & 255) << 8) | (bArr[0] & 255);
            int i3 = i + 22;
            byte[] bArr2 = new byte[i3];
            randomAccessFile.seek(length - i3);
            randomAccessFile.readFully(bArr2);
            if (bArr2[0] != 80 || bArr2[1] != 75 || bArr2[2] != 5 || bArr2[3] != 6) {
                throw new SignatureException("no signature in file (bad footer)");
            }
            for (int i4 = 4; i4 < i + 19; i4++) {
                if (bArr2[i4] == 80 && bArr2[i4 + 1] == 75 && bArr2[i4 + 2] == 5 && bArr2[i4 + 3] == 6) {
                    throw new SignatureException("EOCD marker found after start of EOCD");
                }
            }
            PKCS7 pkcs7 = new PKCS7(new ByteArrayInputStream(bArr2, i3 - i2, i2));
            X509Certificate[] certificates = pkcs7.getCertificates();
            if (certificates == null || certificates.length == 0) {
                throw new SignatureException("signature contains no certificates");
            }
            PublicKey publicKey = certificates[0].getPublicKey();
            SignerInfo[] signerInfos = pkcs7.getSignerInfos();
            if (signerInfos == null || signerInfos.length == 0) {
                throw new SignatureException("signature contains no signedData");
            }
            SignerInfo signerInfo = signerInfos[0];
            Iterator<X509Certificate> it = getTrustedCerts(file2 == null ? DEFAULT_KEYSTORE : file2).iterator();
            while (it.hasNext()) {
                if (it.next().getPublicKey().equals(publicKey)) {
                    randomAccessFile.seek(0L);
                    SignerInfo verify = pkcs7.verify(signerInfo, new InputStream(length, i, currentTimeMillis, randomAccessFile, progressListener) { // from class: android.os.RecoverySystem.1
                        long lastPublishTime;
                        long toRead;
                        final /* synthetic */ int val$commentSize;
                        final /* synthetic */ long val$fileLen;
                        final /* synthetic */ ProgressListener val$listenerForInner;
                        final /* synthetic */ RandomAccessFile val$raf;
                        final /* synthetic */ long val$startTimeMillis;
                        long soFar = 0;
                        int lastPercent = 0;

                        {
                            this.val$fileLen = length;
                            this.val$commentSize = i;
                            this.val$startTimeMillis = currentTimeMillis;
                            this.val$raf = randomAccessFile;
                            this.val$listenerForInner = progressListener;
                            this.toRead = (length - i) - 2;
                            this.lastPublishTime = currentTimeMillis;
                        }

                        @Override // java.io.InputStream
                        public int read() throws IOException {
                            throw new UnsupportedOperationException();
                        }

                        @Override // java.io.InputStream
                        public int read(byte[] bArr3, int i5, int i6) throws IOException {
                            if (this.soFar >= this.toRead || Thread.currentThread().isInterrupted()) {
                                return -1;
                            }
                            long j = this.soFar;
                            long j2 = i6 + j;
                            long j3 = this.toRead;
                            if (j2 > j3) {
                                i6 = (int) (j3 - j);
                            }
                            int read = this.val$raf.read(bArr3, i5, i6);
                            this.soFar += read;
                            if (this.val$listenerForInner != null) {
                                long currentTimeMillis2 = System.currentTimeMillis();
                                int i7 = (int) ((this.soFar * 100) / this.toRead);
                                if (i7 > this.lastPercent && currentTimeMillis2 - this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                                    this.lastPercent = i7;
                                    this.lastPublishTime = currentTimeMillis2;
                                    this.val$listenerForInner.onProgress(i7);
                                }
                            }
                            return read;
                        }
                    });
                    boolean interrupted = Thread.interrupted();
                    if (progressListener != null) {
                        progressListener.onProgress(100);
                    }
                    if (interrupted) {
                        throw new SignatureException("verification was interrupted");
                    }
                    if (verify == null) {
                        throw new SignatureException("signature digest verification failed");
                    }
                    return;
                }
            }
            throw new SignatureException("signature doesn't match any trusted key");
        } finally {
            randomAccessFile.close();
        }
    }

    @SystemApi
    public static void processPackage(Context context, File file, ProgressListener progressListener, Handler handler) throws IOException {
        AnonymousClass2 anonymousClass2;
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath.startsWith("/data/")) {
            RecoverySystem recoverySystem = (RecoverySystem) context.getSystemService("recovery");
            if (progressListener != null) {
                if (handler == null) {
                    handler = new Handler(context.getMainLooper());
                }
                anonymousClass2 = new AnonymousClass2(handler, progressListener);
            } else {
                anonymousClass2 = null;
            }
            if (!recoverySystem.uncrypt(canonicalPath, anonymousClass2)) {
                throw new IOException("process package failed");
            }
        }
    }

    /* renamed from: android.os.RecoverySystem$2, reason: invalid class name */
    class AnonymousClass2 extends IRecoverySystemProgressListener.Stub {
        int lastProgress = 0;
        long lastPublishTime = System.currentTimeMillis();
        final /* synthetic */ ProgressListener val$listener;
        final /* synthetic */ Handler val$progressHandler;

        AnonymousClass2(Handler handler, ProgressListener progressListener) {
            this.val$progressHandler = handler;
            this.val$listener = progressListener;
        }

        @Override // android.os.IRecoverySystemProgressListener
        public void onProgress(final int i) {
            final long currentTimeMillis = System.currentTimeMillis();
            this.val$progressHandler.post(new Runnable() { // from class: android.os.RecoverySystem.2.1
                @Override // java.lang.Runnable
                public void run() {
                    if (i <= AnonymousClass2.this.lastProgress || currentTimeMillis - AnonymousClass2.this.lastPublishTime <= RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                        return;
                    }
                    AnonymousClass2.this.lastProgress = i;
                    AnonymousClass2.this.lastPublishTime = currentTimeMillis;
                    AnonymousClass2.this.val$listener.onProgress(i);
                }
            });
        }
    }

    @SystemApi
    public static void processPackage(Context context, File file, ProgressListener progressListener) throws IOException {
        processPackage(context, file, progressListener, null);
    }

    public static void installPackage(Context context, File file) throws IOException {
        installPackage(context, file, false);
    }

    @SystemApi
    public static void installPackage(Context context, File file, boolean z) throws IOException {
        String str;
        FileWriter fileWriter;
        synchronized (sRequestLock) {
            LOG_FILE.delete();
            File file2 = UNCRYPT_PACKAGE_FILE;
            file2.delete();
            String canonicalPath = file.getCanonicalPath();
            Log.w(TAG, "!!! REBOOTING TO INSTALL " + canonicalPath + " !!!");
            boolean endsWith = canonicalPath.endsWith("_s.zip");
            boolean startsWith = canonicalPath.startsWith("/data/");
            String str2 = "--update_org_package=" + canonicalPath + ShaderAssembler.NEWLINE;
            long parseSuperUsedSize = parseSuperUsedSize(file);
            if (parseSuperUsedSize > 0) {
                if (!((StorageManager) context.getSystemService(StorageManager.class)).shrinkDataDdp(parseSuperUsedSize)) {
                    Log.e(TAG, "[DDP] Failed to shrink /data to expand super partition");
                    throw new IOException("Failed to shrink /data to expand super partition");
                }
                SystemProperties.set("persist.sys.ddp.super_used_size", Long.toString(parseSuperUsedSize));
            }
            if (startsWith) {
                if (z) {
                    if (!BLOCK_MAP_FILE.exists()) {
                        Log.e(TAG, "Package claimed to have been processed but failed to find the block map file.");
                        throw new IOException("Failed to find block map file");
                    }
                } else {
                    FileWriter fileWriter2 = new FileWriter(file2);
                    try {
                        fileWriter2.write(canonicalPath + ShaderAssembler.NEWLINE);
                        fileWriter2.close();
                        if (!file2.setReadable(true, false) || !file2.setWritable(true, false)) {
                            Log.e(TAG, "Error setting permission for " + file2);
                        }
                        BLOCK_MAP_FILE.delete();
                    } catch (Throwable th) {
                        fileWriter2.close();
                        throw th;
                    }
                }
                canonicalPath = "@/cache/recovery/block.map";
            }
            String str3 = ("--update_package=" + canonicalPath + ShaderAssembler.NEWLINE) + ("--locale=" + Locale.getDefault().toLanguageTag() + ShaderAssembler.NEWLINE);
            if (endsWith) {
                str3 = str3 + "--security\n";
            }
            if ("com.ws.dm".equals(context.getPackageName())) {
                str = str3 + "--carry_out=att_fota\n";
            } else if ("com.samsung.sdm.sdmviewer".equals(context.getPackageName())) {
                str = str3 + "--carry_out=vzw_fota\n";
            } else {
                str = str3 + "--carry_out=open_fota\n";
            }
            if (startsWith) {
                str = str + str2;
            }
            COMMAND_FILE.delete();
            int i = 3;
            while (true) {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(COMMAND_FILE, "rwd");
                    try {
                        randomAccessFile.writeBytes(str);
                        Log.i(TAG, "!@RecoverySystem before fsync syscall!!");
                        randomAccessFile.getFD().sync();
                        Log.i(TAG, "!@RecoverySystem after fsync syscall!!");
                        randomAccessFile.close();
                        i--;
                        if (COMMAND_FILE.exists()) {
                            Log.i(TAG, "COMMAND_FILE is already exist!!");
                            break;
                        }
                        Log.i(TAG, "Retry_count : " + i);
                        if (i <= 0) {
                            break;
                        }
                    } finally {
                    }
                } catch (IOException e) {
                    Log.e(TAG, "IOException when writing command cause:", e);
                    throw new IOException("failed to create command file");
                }
            }
            if (!COMMAND_FILE.exists()) {
                Log.i(TAG, "!@ command file absent, throw exception");
                throw new IOException("failed to create command file");
            }
            Log.d(TAG, "!@[reset tracking] installPackage write to recovery_cause");
            try {
                fileWriter = new FileWriter("/sys/class/sec/sec_debug/recovery_cause");
            } catch (IOException e2) {
                Log.e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e2);
            }
            try {
                fileWriter.write("RecoverySystem installPackage: " + str);
                fileWriter.close();
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                String str4 = PowerManager.REBOOT_RECOVERY_UPDATE;
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK) && ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0).getState() != 2) {
                    str4 = PowerManager.REBOOT_RECOVERY_UPDATE + ",quiescent";
                }
                powerManager.reboot(str4);
                throw new IOException("Reboot failed (no permissions?)");
            } catch (Throwable th2) {
                try {
                    fileWriter.close();
                } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    @SystemApi
    public static void prepareForUnattendedUpdate(Context context, String str, IntentSender intentSender) throws IOException {
        if (str == null) {
            throw new NullPointerException("updateToken == null");
        }
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        if (keyguardManager == null || !keyguardManager.isDeviceSecure()) {
            throw new IOException("Failed to request LSKF because the device doesn't have a lock screen. ");
        }
        if (!((RecoverySystem) context.getSystemService("recovery")).requestLskf(context.getPackageName(), intentSender)) {
            throw new IOException("preparation for update failed");
        }
    }

    @SystemApi
    public static void clearPrepareForUnattendedUpdate(Context context) throws IOException {
        if (!((RecoverySystem) context.getSystemService("recovery")).clearLskf(context.getPackageName())) {
            throw new IOException("could not reset unattended update state");
        }
    }

    @SystemApi
    public static void rebootAndApply(Context context, String str, String str2) throws IOException {
        if (str == null) {
            throw new NullPointerException("updateToken == null");
        }
        if (((RecoverySystem) context.getSystemService("recovery")).rebootWithLskfAssumeSlotSwitch(context.getPackageName(), str2) != 0) {
            throw new IOException("system not prepared to apply update");
        }
    }

    @SystemApi
    public static boolean isPreparedForUnattendedUpdate(Context context) throws IOException {
        return ((RecoverySystem) context.getSystemService(RecoverySystem.class)).isLskfCaptured(context.getPackageName());
    }

    @SystemApi
    public static int rebootAndApply(Context context, String str, boolean z) throws IOException {
        return ((RecoverySystem) context.getSystemService(RecoverySystem.class)).rebootWithLskf(context.getPackageName(), str, z);
    }

    @SystemApi
    public static void scheduleUpdateOnBoot(Context context, File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        boolean endsWith = canonicalPath.endsWith("_s.zip");
        if (canonicalPath.startsWith("/data/")) {
            canonicalPath = "@/cache/recovery/block.map";
        }
        String str = ("--update_package=" + canonicalPath + ShaderAssembler.NEWLINE) + ("--locale=" + Locale.getDefault().toLanguageTag() + ShaderAssembler.NEWLINE);
        if (endsWith) {
            str = str + "--security\n";
        }
        if (!((RecoverySystem) context.getSystemService("recovery")).setupBcb(str)) {
            throw new IOException("schedule update on boot failed");
        }
    }

    @SystemApi
    public static void cancelScheduledUpdate(Context context) throws IOException {
        if (!((RecoverySystem) context.getSystemService("recovery")).clearBcb()) {
            throw new IOException("cancel scheduled update failed");
        }
    }

    public static void rebootWipeUserData(Context context) throws IOException {
        rebootWipeUserData(context, false, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, String str) throws IOException {
        rebootWipeUserData(context, false, str, false, false);
    }

    public static void rebootWipeUserData(Context context, boolean z) throws IOException {
        rebootWipeUserData(context, z, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2) throws IOException {
        rebootWipeUserData(context, z, str, z2, false);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2, boolean z3) throws IOException {
        rebootWipeUserData(context, z, str, z2, z3, false);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2, boolean z3, boolean z4) throws IOException {
        rebootWipeUserData(context, z, str, z2, z3, z4, null);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2, boolean z3, boolean z4, String str2) throws IOException {
        String str3;
        String str4;
        RestrictionPolicy restrictionPolicy;
        Log.i(TAG, "rebootWipeUserData++");
        if (!z2 && (restrictionPolicy = EnterpriseDeviceManager.getInstance().getRestrictionPolicy()) != null && !restrictionPolicy.isFactoryResetAllowed()) {
            AuditLog.logEvent(43, new Object[0]);
            throw new SecurityException("Wiping data is not allowed due to restriction policy.");
        }
        UserManager userManager = (UserManager) context.getSystemService("user");
        if (!z2 && userManager.hasUserRestriction(UserManager.DISALLOW_FACTORY_RESET)) {
            AuditLog.logEvent(43, new Object[0]);
            throw new SecurityException("Wiping data is not allowed for this user.");
        }
        final ConditionVariable conditionVariable = new ConditionVariable();
        HandlerThread handlerThread = new HandlerThread(TAG);
        Log.i(TAG, "rebootWipeUserData: run handler " + handlerThread);
        handlerThread.start();
        Log.i(TAG, "rebootWipeUserData: sendOrderedBroadcastAsUser");
        Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR_NOTIFICATION);
        intent.addFlags(285212672);
        context.sendOrderedBroadcastAsUser(intent, UserHandle.SYSTEM, Manifest.permission.MASTER_CLEAR, new BroadcastReceiver() { // from class: android.os.RecoverySystem.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent2) {
                Log.i(RecoverySystem.TAG, "rebootWipeUserData: onReceive");
                ConditionVariable.this.open();
            }
        }, new Handler(handlerThread.getLooper()), 0, null, null);
        Log.i(TAG, "rebootWipeUserData: wait intent to complete");
        conditionVariable.block();
        Log.i(TAG, "rebootWipeUserData: continue..");
        handlerThread.quitSafely();
        EuiccManager euiccManager = (EuiccManager) context.getSystemService(EuiccManager.class);
        if (z3) {
            wipeEuiccData(context, "android");
        } else {
            removeEuiccInvisibleSubs(context, euiccManager);
        }
        String str5 = z ? "--shutdown_after" : null;
        if (TextUtils.isEmpty(str)) {
            str3 = null;
        } else {
            String charSequence = DateFormat.format("yyyy-MM-ddTHH:mm:ssZ", System.currentTimeMillis()).toString();
            StringBuilder sb = new StringBuilder("--reason=");
            sb.append(sanitizeArg(str + "," + charSequence));
            str3 = sb.toString();
        }
        String str6 = z4 ? "--keep_memtag_mode" : null;
        String str7 = "--locale=" + Locale.getDefault().toLanguageTag();
        if (TextUtils.isEmpty(str2)) {
            str4 = "";
        } else {
            str4 = "--" + sanitizeArg(str2);
        }
        try {
            Log.d(TAG, "!@[RecoverySystem] rebootWipeUserData: wipeDataArg:[--wipe_data], extraCmdArg:[" + str4 + NavigationBarInflaterView.SIZE_MOD_END);
            bootCommand(context, str5, RECOVERY_WIPE_DATA_COMMAND, str4, str3, str7, str6);
        } catch (IOException e) {
            AuditLog.logEvent(42, e.getMessage());
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0091 A[Catch: all -> 0x00aa, InterruptedException -> 0x00ac, TRY_LEAVE, TryCatch #0 {InterruptedException -> 0x00ac, blocks: (B:12:0x006d, B:15:0x0089, B:17:0x0091), top: B:11:0x006d, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean wipeEuiccData(android.content.Context r10, java.lang.String r11) {
        /*
            android.content.ContentResolver r0 = r10.getContentResolver()
            java.lang.String r1 = "euicc_provisioned"
            r2 = 0
            int r0 = android.provider.Settings.Global.getInt(r0, r1, r2)
            r1 = 1
            java.lang.String r3 = "RecoverySystem"
            if (r0 != 0) goto L16
            java.lang.String r10 = "Skipping eUICC wipe/retain as it is not provisioned"
            android.util.Log.d(r3, r10)
            return r1
        L16:
            java.lang.String r0 = "euicc"
            java.lang.Object r0 = r10.getSystemService(r0)
            android.telephony.euicc.EuiccManager r0 = (android.telephony.euicc.EuiccManager) r0
            if (r0 == 0) goto Lc2
            boolean r4 = r0.isEnabled()
            if (r4 == 0) goto Lc2
            java.util.concurrent.CountDownLatch r4 = new java.util.concurrent.CountDownLatch
            r4.<init>(r1)
            java.util.concurrent.atomic.AtomicBoolean r1 = new java.util.concurrent.atomic.AtomicBoolean
            r1.<init>(r2)
            android.os.RecoverySystem$4 r5 = new android.os.RecoverySystem$4
            r5.<init>()
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r7 = "com.android.internal.action.EUICC_FACTORY_RESET"
            r6.<init>(r7)
            r6.setPackage(r11)
            r11 = 201326592(0xc000000, float:9.8607613E-32)
            android.os.UserHandle r8 = android.os.UserHandle.SYSTEM
            android.app.PendingIntent r11 = android.app.PendingIntent.getBroadcastAsUser(r10, r2, r6, r11, r8)
            android.content.IntentFilter r6 = new android.content.IntentFilter
            r6.<init>()
            r6.addAction(r7)
            android.os.HandlerThread r7 = new android.os.HandlerThread
            java.lang.String r8 = "euiccWipeFinishReceiverThread"
            r7.<init>(r8)
            r7.start()
            android.os.Handler r8 = new android.os.Handler
            android.os.Looper r7 = r7.getLooper()
            r8.<init>(r7)
            android.content.Context r7 = r10.getApplicationContext()
            r9 = 0
            r7.registerReceiver(r5, r6, r9, r8)
            r0.eraseSubscriptions(r11)
            android.content.ContentResolver r11 = r10.getContentResolver()     // Catch: java.lang.Throwable -> Laa java.lang.InterruptedException -> Lac
            java.lang.String r0 = "euicc_factory_reset_timeout_millis"
            r6 = 30000(0x7530, double:1.4822E-319)
            long r6 = android.provider.Settings.Global.getLong(r11, r0, r6)     // Catch: java.lang.Throwable -> Laa java.lang.InterruptedException -> Lac
            r8 = 5000(0x1388, double:2.4703E-320)
            int r11 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r11 >= 0) goto L81
        L7f:
            r6 = r8
            goto L89
        L81:
            r8 = 60000(0xea60, double:2.9644E-319)
            int r11 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r11 <= 0) goto L89
            goto L7f
        L89:
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.Throwable -> Laa java.lang.InterruptedException -> Lac
            boolean r11 = r4.await(r6, r11)     // Catch: java.lang.Throwable -> Laa java.lang.InterruptedException -> Lac
            if (r11 != 0) goto L9e
            java.lang.String r11 = "Timeout wiping eUICC data."
            android.util.Log.e(r3, r11)     // Catch: java.lang.Throwable -> Laa java.lang.InterruptedException -> Lac
        L96:
            android.content.Context r10 = r10.getApplicationContext()
            r10.unregisterReceiver(r5)
            return r2
        L9e:
            android.content.Context r10 = r10.getApplicationContext()
            r10.unregisterReceiver(r5)
            boolean r10 = r1.get()
            return r10
        Laa:
            r11 = move-exception
            goto Lba
        Lac:
            r11 = move-exception
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> Laa
            r0.interrupt()     // Catch: java.lang.Throwable -> Laa
            java.lang.String r0 = "Wiping eUICC data interrupted"
            android.util.Log.e(r3, r0, r11)     // Catch: java.lang.Throwable -> Laa
            goto L96
        Lba:
            android.content.Context r10 = r10.getApplicationContext()
            r10.unregisterReceiver(r5)
            throw r11
        Lc2:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.wipeEuiccData(android.content.Context, java.lang.String):boolean");
    }

    private static void removeEuiccInvisibleSubs(Context context, EuiccManager euiccManager) {
        if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            Log.i(TAG, "Skip removing eUICC invisible profiles as it is not provisioned.");
            return;
        }
        if (euiccManager == null || !euiccManager.isEnabled()) {
            Log.i(TAG, "Skip removing eUICC invisible profiles as eUICC manager is not available.");
            return;
        }
        List<SubscriptionInfo> availableSubscriptionInfoList = ((SubscriptionManager) context.getSystemService(SubscriptionManager.class)).getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList == null || availableSubscriptionInfoList.isEmpty()) {
            Log.i(TAG, "Skip removing eUICC invisible profiles as no available profiles found.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (SubscriptionInfo subscriptionInfo : availableSubscriptionInfoList) {
            if (subscriptionInfo.isEmbedded() && subscriptionInfo.getGroupUuid() != null && subscriptionInfo.isOpportunistic()) {
                arrayList.add(subscriptionInfo);
            }
        }
        removeEuiccInvisibleSubs(context, arrayList, euiccManager);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00be A[Catch: all -> 0x00e4, InterruptedException -> 0x00e6, Merged into TryCatch #1 {all -> 0x00e4, InterruptedException -> 0x00e6, blocks: (B:10:0x0099, B:13:0x00b6, B:15:0x00be, B:28:0x00e7), top: B:9:0x0099 }, TRY_LEAVE] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean removeEuiccInvisibleSubs(android.content.Context r12, java.util.List<android.telephony.SubscriptionInfo> r13, android.telephony.euicc.EuiccManager r14) {
        /*
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.removeEuiccInvisibleSubs(android.content.Context, java.util.List, android.telephony.euicc.EuiccManager):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0028 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0043  */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void rebootPromptAndWipeUserData(android.content.Context r6, java.lang.String r7) throws java.io.IOException {
        /*
            java.lang.String r0 = "rescueparty"
            java.lang.String r1 = "RecoverySystem"
            r2 = 0
            r3 = 0
            java.lang.String r4 = "vold"
            android.os.IBinder r4 = android.os.ServiceManager.checkService(r4)     // Catch: java.lang.Exception -> L1f
            android.os.IVold r4 = android.os.IVold.Stub.asInterface(r4)     // Catch: java.lang.Exception -> L1f
            if (r4 == 0) goto L19
            boolean r5 = r4.needsCheckpoint()     // Catch: java.lang.Exception -> L20
            goto L26
        L19:
            java.lang.String r5 = "Failed to get vold"
            android.util.Log.w(r1, r5)     // Catch: java.lang.Exception -> L20
            goto L25
        L1f:
            r4 = r3
        L20:
            java.lang.String r5 = "Failed to check for checkpointing"
            android.util.Log.w(r1, r5)
        L25:
            r5 = r2
        L26:
            if (r5 == 0) goto L43
            r4.abortChanges(r0, r2)     // Catch: java.lang.Exception -> L31
            java.lang.String r7 = "Rescue Party requested wipe. Aborting update"
            android.util.Log.i(r1, r7)     // Catch: java.lang.Exception -> L31
            goto L42
        L31:
            java.lang.String r7 = "Rescue Party requested wipe. Rebooting instead."
            android.util.Log.i(r1, r7)
            java.lang.String r7 = "power"
            java.lang.Object r6 = r6.getSystemService(r7)
            android.os.PowerManager r6 = (android.os.PowerManager) r6
            r6.reboot(r0)
        L42:
            return
        L43:
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 != 0) goto L5c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "--reason="
            r0.<init>(r1)
            java.lang.String r7 = sanitizeArg(r7)
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            goto L5d
        L5c:
            r7 = r3
        L5d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "--locale="
            r0.<init>(r1)
            java.util.Locale r1 = java.util.Locale.getDefault()
            java.lang.String r1 = r1.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "--prompt_and_wipe_data"
            java.lang.String[] r7 = new java.lang.String[]{r3, r1, r7, r0}
            bootCommand(r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.rebootPromptAndWipeUserData(android.content.Context, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0028 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void rebootPromptAndWipeAppData(android.content.Context r6, java.lang.String r7) throws java.io.IOException {
        /*
            java.lang.String r0 = "rescueparty"
            java.lang.String r1 = "RecoverySystem"
            r2 = 0
            r3 = 0
            java.lang.String r4 = "vold"
            android.os.IBinder r4 = android.os.ServiceManager.checkService(r4)     // Catch: java.lang.Exception -> L1f
            android.os.IVold r4 = android.os.IVold.Stub.asInterface(r4)     // Catch: java.lang.Exception -> L1f
            if (r4 == 0) goto L19
            boolean r5 = r4.needsCheckpoint()     // Catch: java.lang.Exception -> L20
            goto L26
        L19:
            java.lang.String r5 = "Failed to get vold"
            android.util.Log.w(r1, r5)     // Catch: java.lang.Exception -> L20
            goto L25
        L1f:
            r4 = r3
        L20:
            java.lang.String r5 = "Failed to check for checkpointing"
            android.util.Log.w(r1, r5)
        L25:
            r5 = r2
        L26:
            if (r5 == 0) goto L43
            r4.abortChanges(r0, r2)     // Catch: java.lang.Exception -> L31
            java.lang.String r7 = "Rescue Party requested wipe. Aborting update"
            android.util.Log.i(r1, r7)     // Catch: java.lang.Exception -> L31
            goto L42
        L31:
            java.lang.String r7 = "Rescue Party requested wipe. Rebooting instead."
            android.util.Log.i(r1, r7)
            java.lang.String r7 = "power"
            java.lang.Object r6 = r6.getSystemService(r7)
            android.os.PowerManager r6 = (android.os.PowerManager) r6
            r6.reboot(r0)
        L42:
            return
        L43:
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 != 0) goto L5c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "--reason="
            r0.<init>(r1)
            java.lang.String r7 = sanitizeArg(r7)
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            goto L5d
        L5c:
            r7 = r3
        L5d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "--locale="
            r0.<init>(r1)
            java.util.Locale r1 = java.util.Locale.getDefault()
            java.lang.String r1 = r1.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "--prompt_and_wipe_app_data"
            java.lang.String[] r7 = new java.lang.String[]{r3, r1, r7, r0}
            bootCommand(r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.rebootPromptAndWipeAppData(android.content.Context, java.lang.String):void");
    }

    public static void rebootWipeCache(Context context) throws IOException {
        rebootWipeCache(context, context.getPackageName());
    }

    public static void rebootWipeCache(Context context, String str) throws IOException {
        String str2;
        if (TextUtils.isEmpty(str)) {
            str2 = null;
        } else {
            str2 = "--reason=" + sanitizeArg(str);
        }
        bootCommand(context, "--wipe_cache", str2, "--locale=" + Locale.getDefault().toLanguageTag());
    }

    @SystemApi
    public static void rebootWipeAb(Context context, File file, String str) throws IOException {
        String str2;
        if (TextUtils.isEmpty(str)) {
            str2 = null;
        } else {
            str2 = "--reason=" + sanitizeArg(str);
        }
        bootCommand(context, "--wipe_ab", "--wipe_package=" + file.getCanonicalPath(), str2, "--locale=" + Locale.getDefault().toLanguageTag());
    }

    public void wipePartitionToExt4() throws IOException {
        rebootRecoveryWithCommand("--wipe_data\n--reformat_data=ext4");
    }

    public static void rebootWipeCustomerPartition(Context context, String str, String str2) throws IOException {
        String str3;
        if (TextUtils.isEmpty(str2)) {
            str3 = null;
        } else {
            str3 = "--reason=" + sanitizeArg(str2);
        }
        bootCommand(context, str, str3);
    }

    private static String getRecoveryReason(String str) {
        try {
            return str.substring(str.indexOf("=") + 1);
        } catch (StringIndexOutOfBoundsException e) {
            Log.e(TAG, "StringIndexOutOfBoundsException when splitting recovery cause:", e);
            return null;
        }
    }

    private static void bootCommand(Context context, String... strArr) throws IOException {
        FileOutputStream fileOutputStream;
        synchronized (mShutdownIsInProgressLock) {
            if (mShutdownIsInProgress.booleanValue()) {
                return;
            }
            mShutdownIsInProgress = true;
            Log.i(TAG, "!@[RecoverySystem] bootCommand: " + Arrays.toString(strArr));
            boolean z = Arrays.toString(strArr) != null && Arrays.toString(strArr).contains(RECOVERY_WIPE_DATA_COMMAND);
            synchronized (sRequestLock) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                StringBuilder sb = new StringBuilder();
                sb.append("!@[RecoverySystem] ");
                for (StackTraceElement stackTraceElement : stackTrace) {
                    sb.append(stackTraceElement.toString() + ShaderAssembler.NEWLINE);
                }
                Log.i(TAG, sb.toString());
                RECOVERY_DIR.mkdirs();
                COMMAND_FILE.delete();
                LOG_FILE.delete();
                String str = null;
                int i = 3;
                while (true) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(COMMAND_FILE, "rwd");
                    try {
                        for (String str2 : strArr) {
                            if (!TextUtils.isEmpty(str2)) {
                                randomAccessFile.writeBytes(str2);
                                randomAccessFile.writeBytes(ShaderAssembler.NEWLINE);
                                if (str2.startsWith("--reason=")) {
                                    str = getRecoveryReason(str2);
                                }
                            }
                        }
                        Log.i(TAG, "!@[RecoverySystem] bootCommand: before fsync syscall!!");
                        randomAccessFile.getFD().sync();
                        Log.i(TAG, "!@[RecoverySystem] bootCommand: after fsync syscall!!");
                        randomAccessFile.close();
                        i--;
                        if (COMMAND_FILE.exists()) {
                            Log.i(TAG, "COMMAND_FILE is created!!");
                            break;
                        }
                        Log.i(TAG, "retryCount : " + i);
                        if (i == 0) {
                            break;
                        }
                    } catch (Throwable th) {
                        randomAccessFile.close();
                        throw th;
                    }
                }
                if (!COMMAND_FILE.exists()) {
                    Log.i(TAG, "!@[RecoverySystem] bootCommand: command file absent, throw exception");
                    throw new IOException("Reboot failed (unable to create command file)");
                }
                if (z) {
                    deleteSecrets();
                }
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                String str3 = SystemProperties.get("persist.sys.reboot.reason");
                if ("nvrecovery".equals(str3)) {
                    Log.i(TAG, "FactoryTest ->nvrecovery ");
                    powerManager.reboot("nvrecovery");
                } else if (Context.DOWNLOAD_SERVICE.equals(str3)) {
                    Log.i(TAG, "FactoryTest ->download ");
                    powerManager.reboot(Context.DOWNLOAD_SERVICE);
                } else {
                    Log.d(TAG, "calling pm.reboot");
                    if (str == null) {
                        str = "bootCommand()";
                    }
                    Log.d(TAG, "!@[RecoverySystem] bootCommand: [reset tracking] write to recovery_cause : " + str);
                    try {
                        fileOutputStream = new FileOutputStream("/sys/class/sec/sec_debug/recovery_cause");
                    } catch (IOException e) {
                        Log.e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e);
                    }
                    try {
                        fileOutputStream.write(("RecoverySystem " + str).getBytes(StandardCharsets.UTF_8));
                        fileOutputStream.close();
                        powerManager.reboot("recovery");
                    } catch (Throwable th2) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
                throw new IOException("Reboot failed (no permissions?)");
            }
        }
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x024e: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:116:0x024e */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0251 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0052 A[Catch: IOException -> 0x0078, FileNotFoundException -> 0x007a, all -> 0x024d, TryCatch #4 {all -> 0x024d, blocks: (B:20:0x004a, B:22:0x0052, B:23:0x0067, B:25:0x006d, B:111:0x0081, B:107:0x0090), top: B:16:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006d A[Catch: IOException -> 0x0078, FileNotFoundException -> 0x007a, all -> 0x024d, TRY_LEAVE, TryCatch #4 {all -> 0x024d, blocks: (B:20:0x004a, B:22:0x0052, B:23:0x0067, B:25:0x006d, B:111:0x0081, B:107:0x0090), top: B:16:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00e2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:121:0x009e -> B:28:0x00a5). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String handleAftermath(android.content.Context r9) {
        /*
            Method dump skipped, instructions count: 609
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.handleAftermath(android.content.Context):java.lang.String");
    }

    private static void deleteSecrets() {
        Log.w(TAG, "deleteSecrets");
        try {
            AndroidKeyStoreMaintenance.deleteAllKeys();
        } catch (KeyStoreException e) {
            Log.wtf(TAG, "Failed to delete all keys from keystore.", e);
        }
    }

    private static void recursiveDelete(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            for (int i = 0; list != null && i < list.length; i++) {
                recursiveDelete(new File(file, list[i]));
            }
        }
        if (file.delete()) {
            Log.i(TAG, "Deleted: " + file);
        } else {
            Log.e(TAG, "Can't delete: " + file);
        }
    }

    private boolean uncrypt(String str, IRecoverySystemProgressListener iRecoverySystemProgressListener) {
        try {
            return this.mService.uncrypt(str, iRecoverySystemProgressListener);
        } catch (RemoteException unused) {
            return false;
        }
    }

    private boolean setupBcb(String str) {
        try {
            return this.mService.setupBcb(str);
        } catch (RemoteException unused) {
            return false;
        }
    }

    private boolean allocateSpaceForUpdate(File file) throws RemoteException {
        return this.mService.allocateSpaceForUpdate(file.getAbsolutePath());
    }

    private boolean clearBcb() {
        try {
            return this.mService.clearBcb();
        } catch (RemoteException unused) {
            return false;
        }
    }

    private void rebootRecoveryWithCommand(String str) {
        try {
            this.mService.rebootRecoveryWithCommand(str);
        } catch (RemoteException unused) {
        }
    }

    private boolean requestLskf(String str, IntentSender intentSender) throws IOException {
        Log.i(TAG, TextUtils.formatSimple("Package<%s> requesting LSKF", str));
        try {
            boolean requestLskf = this.mService.requestLskf(str, intentSender);
            Log.i(TAG, TextUtils.formatSimple("LSKF Request isValid = %b", Boolean.valueOf(requestLskf)));
            return requestLskf;
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not request LSKF capture", e);
        }
    }

    private boolean clearLskf(String str) throws IOException {
        try {
            return this.mService.clearLskf(str);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not clear LSKF", e);
        }
    }

    private boolean isLskfCaptured(String str) throws IOException {
        try {
            return this.mService.isLskfCaptured(str);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not get LSKF capture state", e);
        }
    }

    private int rebootWithLskf(String str, String str2, boolean z) throws IOException {
        try {
            return this.mService.rebootWithLskf(str, str2, z);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not reboot for update", e);
        }
    }

    private int rebootWithLskfAssumeSlotSwitch(String str, String str2) throws IOException {
        try {
            return this.mService.rebootWithLskfAssumeSlotSwitch(str, str2);
        } catch (RemoteException | RuntimeException e) {
            throw new IOException("could not reboot for update", e);
        }
    }

    private static String sanitizeArg(String str) {
        return str.replace((char) 0, '?').replace('\n', '?');
    }

    public RecoverySystem() {
        this.mService = null;
    }

    public RecoverySystem(IRecoverySystem iRecoverySystem) {
        this.mService = iRecoverySystem;
    }

    private static void copyFile(File file, File file2) {
        Throwable th;
        FileChannel fileChannel;
        FileChannel fileChannel2 = null;
        try {
            try {
                try {
                    FileChannel channel = new FileInputStream(file).getChannel();
                    try {
                        fileChannel = new FileOutputStream(file2).getChannel();
                        try {
                            fileChannel.transferFrom(channel, 0L, channel.size());
                            Os.chmod(file2.getPath(), 416);
                            Os.chown(file2.getPath(), 1000, 1007);
                            if (channel != null) {
                                channel.close();
                            }
                            if (fileChannel != null) {
                                fileChannel.close();
                            }
                        } catch (ErrnoException e) {
                            e = e;
                            fileChannel2 = channel;
                            Log.e(TAG, "copyFile: Error chmod recovery logs", e);
                            if (fileChannel2 != null) {
                                fileChannel2.close();
                            }
                            if (fileChannel != null) {
                                fileChannel.close();
                            }
                            Log.i(TAG, "copyFile: " + file + " -> " + file2);
                        } catch (IOException e2) {
                            e = e2;
                            fileChannel2 = channel;
                            Log.e(TAG, "copyFile: Error copy recovery logs", e);
                            if (fileChannel2 != null) {
                                fileChannel2.close();
                            }
                            if (fileChannel != null) {
                                fileChannel.close();
                            }
                            Log.i(TAG, "copyFile: " + file + " -> " + file2);
                        } catch (Throwable th2) {
                            th = th2;
                            fileChannel2 = channel;
                            if (fileChannel2 != null) {
                                try {
                                    fileChannel2.close();
                                } catch (IOException e3) {
                                    Log.e(TAG, "copyFile: Error close FileChannel ", e3);
                                    throw th;
                                }
                            }
                            if (fileChannel == null) {
                                throw th;
                            }
                            fileChannel.close();
                            throw th;
                        }
                    } catch (ErrnoException e4) {
                        e = e4;
                        fileChannel = null;
                    } catch (IOException e5) {
                        e = e5;
                        fileChannel = null;
                    } catch (Throwable th3) {
                        th = th3;
                        fileChannel = null;
                    }
                } catch (ErrnoException e6) {
                    e = e6;
                    fileChannel = null;
                } catch (IOException e7) {
                    e = e7;
                    fileChannel = null;
                } catch (Throwable th4) {
                    th = th4;
                    fileChannel = null;
                }
            } catch (IOException e8) {
                Log.e(TAG, "copyFile: Error close FileChannel ", e8);
            }
            Log.i(TAG, "copyFile: " + file + " -> " + file2);
        } catch (Throwable th5) {
            th = th5;
        }
    }
}
