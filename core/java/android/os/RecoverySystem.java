package android.os;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IRecoverySystemProgressListener;
import android.os.IVold;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.RestrictionPolicy;
import android.sec.enterprise.auditlog.AuditLog;
import android.system.ErrnoException;
import android.system.Os;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

/* loaded from: classes3.dex */
public class RecoverySystem {
    private static final String ACTION_EUICC_FACTORY_RESET = "com.android.internal.action.EUICC_FACTORY_RESET";
    private static final String ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS = "com.android.internal.action.EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS";
    private static final long DEFAULT_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 30000;
    private static final long DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 45000;
    private static final String LAST_CACHE_SUDDEN_RESET_LOG_PATH = "/data/log/recovery_last_sudden_reset.log";
    private static final String LAST_INSTALL_PATH = "last_install";
    private static final String LAST_PREFIX = "last_";
    private static final String LAST_RECOVERY_MODE = "dev.lastrecoverymode";
    private static final int LOG_FILE_MAX_LENGTH = 65536;
    private static final long MAX_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 60000;
    private static final long MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 90000;
    private static final long MIN_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 5000;
    private static final long MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 15000;
    private static final String PACKAGE_NAME_EUICC_DATA_MANAGEMENT_CALLBACK = "android";
    private static final long PUBLISH_PROGRESS_INTERVAL_MS = 500;
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
    private final IRecoverySystem mService;
    private static final File DEFAULT_KEYSTORE = new File("/system/etc/security/otacerts.zip");
    private static final File RECOVERY_DIR = new File("/cache/recovery");
    private static final File LOG_FILE = new File(RECOVERY_DIR, "log");
    private static File COMMAND_FILE = new File(RECOVERY_DIR, "command");
    private static final Object mShutdownIsInProgressLock = new Object();
    private static Boolean mShutdownIsInProgress = false;
    public static final File RECOVERY_RESCUEPARTY_FILE = new File(RECOVERY_DIR, "rescueparty_log");
    public static final File BLOCK_MAP_FILE = new File(RECOVERY_DIR, "block.map");
    public static final File UNCRYPT_PACKAGE_FILE = new File(RECOVERY_DIR, "uncrypt_file");
    public static final File UNCRYPT_STATUS_FILE = new File(RECOVERY_DIR, "uncrypt_status");
    private static final Object sRequestLock = new Object();

    public interface ProgressListener {
        void onProgress(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResumeOnRebootRebootErrorCode {
    }

    private static HashSet<X509Certificate> getTrustedCerts(File keystore) throws IOException, GeneralSecurityException {
        HashSet<X509Certificate> trusted = new HashSet<>();
        if (keystore == null) {
            keystore = DEFAULT_KEYSTORE;
        }
        ZipFile zip = new ZipFile(keystore);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream is = zip.getInputStream(entry);
                try {
                    trusted.add((X509Certificate) cf.generateCertificate(is));
                    is.close();
                } finally {
                }
            }
            return trusted;
        } finally {
            zip.close();
        }
    }

    private static long parseSuperUsedSize(File packageFile) throws IOException {
        try {
            ZipFile zip = new ZipFile(packageFile);
            try {
                ZipEntry entry = zip.getEntry("super_used_size.txt");
                if (entry == null) {
                    Log.e("RecoverySystem", "!@RecoverySystem failed to get zipEntry");
                } else {
                    InputStream inputStream = zip.getInputStream(entry);
                    if (inputStream == null) {
                        Log.e("RecoverySystem", "!@RecoverySystem failed to get inputStream");
                    } else {
                        byte[] bytes = new byte[8];
                        int len = inputStream.read(bytes);
                        if (len <= 0) {
                            Log.e("RecoverySystem", "!@RecoverySystem failed to read super_used_size");
                            inputStream.close();
                        } else {
                            String str = new String(bytes);
                            Log.i("RecoverySystem", "!@RecoverySystem super_used_size: " + str);
                            long parseLong = Long.parseLong(str);
                            zip.close();
                            return parseLong;
                        }
                    }
                }
                zip.close();
                zip.close();
                return 0L;
            } finally {
            }
        } catch (Exception e) {
            Log.e("RecoverySystem", "!@RecoverySystem IOException when reading package files", e);
            return 0L;
        }
    }

    public static void verifyPackage(File packageFile, final ProgressListener listener, File deviceCertsZipFile) throws IOException, GeneralSecurityException {
        byte[] eocd;
        final long fileLen = packageFile.length();
        final RandomAccessFile raf = new RandomAccessFile(packageFile, "r");
        try {
            final long startTimeMillis = System.currentTimeMillis();
            if (listener != null) {
                listener.onProgress(0);
            }
            raf.seek(fileLen - 6);
            byte[] footer = new byte[6];
            raf.readFully(footer);
            if (footer[2] != -1 || footer[3] != -1) {
                throw new SignatureException("no signature in file (no footer)");
            }
            final int commentSize = (footer[4] & 255) | ((footer[5] & 255) << 8);
            int signatureStart = (footer[0] & 255) | ((footer[1] & 255) << 8);
            byte[] eocd2 = new byte[commentSize + 22];
            raf.seek(fileLen - (commentSize + 22));
            raf.readFully(eocd2);
            byte b = 80;
            if (eocd2[0] != 80 || eocd2[1] != 75 || eocd2[2] != 5 || eocd2[3] != 6) {
                throw new SignatureException("no signature in file (bad footer)");
            }
            int i = 4;
            while (i < eocd2.length - 3) {
                if (eocd2[i] == b && eocd2[i + 1] == 75 && eocd2[i + 2] == 5) {
                    if (eocd2[i + 3] == 6) {
                        throw new SignatureException("EOCD marker found after start of EOCD");
                    }
                }
                i++;
                b = 80;
            }
            PKCS7 block = new PKCS7(new ByteArrayInputStream(eocd2, (commentSize + 22) - signatureStart, signatureStart));
            X509Certificate[] certificates = block.getCertificates();
            if (certificates == null || certificates.length == 0) {
                throw new SignatureException("signature contains no certificates");
            }
            X509Certificate cert = certificates[0];
            PublicKey signatureKey = cert.getPublicKey();
            SignerInfo[] signerInfos = block.getSignerInfos();
            if (signerInfos == null || signerInfos.length == 0) {
                throw new SignatureException("signature contains no signedData");
            }
            SignerInfo signerInfo = signerInfos[0];
            boolean verified = false;
            HashSet<X509Certificate> trusted = getTrustedCerts(deviceCertsZipFile == null ? DEFAULT_KEYSTORE : deviceCertsZipFile);
            Iterator<X509Certificate> it = trusted.iterator();
            while (true) {
                if (!it.hasNext()) {
                    eocd = eocd2;
                    break;
                }
                X509Certificate c = it.next();
                eocd = eocd2;
                if (c.getPublicKey().equals(signatureKey)) {
                    verified = true;
                    break;
                }
                eocd2 = eocd;
            }
            if (!verified) {
                throw new SignatureException("signature doesn't match any trusted key");
            }
            raf.seek(0L);
            SignerInfo verifyResult = block.verify(signerInfo, new InputStream() { // from class: android.os.RecoverySystem.1
                long lastPublishTime;
                long toRead;
                long soFar = 0;
                int lastPercent = 0;

                {
                    this.toRead = (fileLen - commentSize) - 2;
                    this.lastPublishTime = startTimeMillis;
                }

                @Override // java.io.InputStream
                public int read() throws IOException {
                    throw new UnsupportedOperationException();
                }

                @Override // java.io.InputStream
                public int read(byte[] b2, int off, int len) throws IOException {
                    if (this.soFar >= this.toRead || Thread.currentThread().isInterrupted()) {
                        return -1;
                    }
                    int size = len;
                    if (this.soFar + size > this.toRead) {
                        size = (int) (this.toRead - this.soFar);
                    }
                    int read = raf.read(b2, off, size);
                    this.soFar += read;
                    if (listener != null) {
                        long now = System.currentTimeMillis();
                        int p = (int) ((this.soFar * 100) / this.toRead);
                        if (p > this.lastPercent && now - this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                            this.lastPercent = p;
                            this.lastPublishTime = now;
                            listener.onProgress(this.lastPercent);
                        }
                    }
                    return read;
                }
            });
            boolean interrupted = Thread.interrupted();
            if (listener != null) {
                listener.onProgress(100);
            }
            if (interrupted) {
                throw new SignatureException("verification was interrupted");
            }
            if (verifyResult == null) {
                throw new SignatureException("signature digest verification failed");
            }
        } finally {
            raf.close();
        }
    }

    @Deprecated
    private static boolean verifyPackageCompatibility(InputStream inputStream) throws IOException {
        return true;
    }

    @SystemApi
    @Deprecated
    public static boolean verifyPackageCompatibility(File compatibilityFile) throws IOException {
        return true;
    }

    @SystemApi
    public static void processPackage(Context context, File packageFile, ProgressListener listener, Handler handler) throws IOException {
        Handler progressHandler;
        String filename = packageFile.getCanonicalPath();
        if (!filename.startsWith("/data/")) {
            return;
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        IRecoverySystemProgressListener progressListener = null;
        if (listener != null) {
            if (handler != null) {
                progressHandler = handler;
            } else {
                progressHandler = new Handler(context.getMainLooper());
            }
            progressListener = new AnonymousClass2(progressHandler, listener);
        }
        if (!rs.uncrypt(filename, progressListener)) {
            throw new IOException("process package failed");
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
        public void onProgress(final int progress) {
            final long now = System.currentTimeMillis();
            this.val$progressHandler.post(new Runnable() { // from class: android.os.RecoverySystem.2.1
                @Override // java.lang.Runnable
                public void run() {
                    if (progress > AnonymousClass2.this.lastProgress && now - AnonymousClass2.this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                        AnonymousClass2.this.lastProgress = progress;
                        AnonymousClass2.this.lastPublishTime = now;
                        AnonymousClass2.this.val$listener.onProgress(progress);
                    }
                }
            });
        }
    }

    @SystemApi
    public static void processPackage(Context context, File packageFile, ProgressListener listener) throws IOException {
        processPackage(context, packageFile, listener, null);
    }

    public static void installPackage(Context context, File packageFile) throws IOException {
        installPackage(context, packageFile, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0296 A[Catch: all -> 0x0306, TryCatch #2 {, blocks: (B:4:0x0005, B:6:0x0064, B:8:0x0072, B:9:0x007d, B:10:0x008b, B:15:0x0092, B:19:0x009b, B:20:0x00a9, B:21:0x00aa, B:24:0x00c7, B:26:0x00d3, B:28:0x00f5, B:29:0x00db, B:32:0x00ff, B:33:0x0103, B:34:0x0104, B:36:0x0153, B:37:0x0167, B:39:0x0173, B:41:0x01bf, B:42:0x01d1, B:45:0x01d7, B:49:0x01fa, B:51:0x0200, B:100:0x0208, B:57:0x022a, B:59:0x0233, B:61:0x023a, B:67:0x0258, B:69:0x027e, B:71:0x0296, B:73:0x02aa, B:74:0x02be, B:75:0x02c8, B:79:0x0277, B:91:0x0271, B:90:0x026e, B:97:0x02c9, B:98:0x02d9, B:53:0x0210, B:112:0x02f1, B:111:0x02ee, B:115:0x02f7, B:116:0x0305, B:120:0x0188, B:122:0x0194, B:123:0x01a9, B:23:0x00b1), top: B:3:0x0005, inners: #10 }] */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void installPackage(android.content.Context r17, java.io.File r18, boolean r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 777
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.installPackage(android.content.Context, java.io.File, boolean):void");
    }

    @SystemApi
    public static void prepareForUnattendedUpdate(Context context, String updateToken, IntentSender intentSender) throws IOException {
        if (updateToken == null) {
            throw new NullPointerException("updateToken == null");
        }
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        if (keyguardManager == null || !keyguardManager.isDeviceSecure()) {
            throw new IOException("Failed to request LSKF because the device doesn't have a lock screen. ");
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.requestLskf(context.getPackageName(), intentSender)) {
            throw new IOException("preparation for update failed");
        }
    }

    @SystemApi
    public static void clearPrepareForUnattendedUpdate(Context context) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.clearLskf(context.getPackageName())) {
            throw new IOException("could not reset unattended update state");
        }
    }

    @SystemApi
    public static void rebootAndApply(Context context, String updateToken, String reason) throws IOException {
        if (updateToken == null) {
            throw new NullPointerException("updateToken == null");
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (rs.rebootWithLskfAssumeSlotSwitch(context.getPackageName(), reason) != 0) {
            throw new IOException("system not prepared to apply update");
        }
    }

    @SystemApi
    public static boolean isPreparedForUnattendedUpdate(Context context) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService(RecoverySystem.class);
        return rs.isLskfCaptured(context.getPackageName());
    }

    @SystemApi
    public static int rebootAndApply(Context context, String reason, boolean slotSwitch) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService(RecoverySystem.class);
        return rs.rebootWithLskf(context.getPackageName(), reason, slotSwitch);
    }

    @SystemApi
    public static void scheduleUpdateOnBoot(Context context, File packageFile) throws IOException {
        String filename = packageFile.getCanonicalPath();
        boolean securityUpdate = filename.endsWith("_s.zip");
        if (filename.startsWith("/data/")) {
            filename = "@/cache/recovery/block.map";
        }
        String filenameArg = "--update_package=" + filename + "\n";
        String localeArg = "--locale=" + Locale.getDefault().toLanguageTag() + "\n";
        String command = filenameArg + localeArg;
        if (securityUpdate) {
            command = command + "--security\n";
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.setupBcb(command)) {
            throw new IOException("schedule update on boot failed");
        }
    }

    @SystemApi
    public static void cancelScheduledUpdate(Context context) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.clearBcb()) {
            throw new IOException("cancel scheduled update failed");
        }
    }

    public static void rebootWipeUserData(Context context) throws IOException {
        rebootWipeUserData(context, false, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, String reason) throws IOException {
        rebootWipeUserData(context, false, reason, false, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown) throws IOException {
        rebootWipeUserData(context, shutdown, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force) throws IOException {
        rebootWipeUserData(context, shutdown, reason, force, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force, boolean wipeEuicc) throws IOException {
        rebootWipeUserData(context, shutdown, reason, force, wipeEuicc, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force, boolean wipeEuicc, boolean keepMemtagMode) throws IOException {
        rebootWipeUserData(context, shutdown, reason, force, wipeEuicc, keepMemtagMode, null);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force, boolean wipeEuicc, boolean keepMemtagMode, String extraCmd) throws IOException {
        String shutdownArg;
        String reasonArg;
        String memtagArg;
        String extraCmdArg;
        RestrictionPolicy restrPol;
        Log.i("RecoverySystem", "rebootWipeUserData++");
        if (force || (restrPol = EnterpriseDeviceManager.getInstance().getRestrictionPolicy()) == null || restrPol.isFactoryResetAllowed()) {
            UserManager um = (UserManager) context.getSystemService("user");
            if (!force && um.hasUserRestriction(UserManager.DISALLOW_FACTORY_RESET)) {
                AuditLog.logEvent(43, new Object[0]);
                throw new SecurityException("Wiping data is not allowed for this user.");
            }
            final ConditionVariable condition = new ConditionVariable();
            HandlerThread hthread = new HandlerThread("RecoverySystem");
            Log.i("RecoverySystem", "rebootWipeUserData: run handler " + hthread);
            hthread.start();
            Log.i("RecoverySystem", "rebootWipeUserData: sendOrderedBroadcastAsUser");
            Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR_NOTIFICATION);
            intent.addFlags(285212672);
            context.sendOrderedBroadcastAsUser(intent, UserHandle.SYSTEM, Manifest.permission.MASTER_CLEAR, new BroadcastReceiver() { // from class: android.os.RecoverySystem.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent2) {
                    Log.i("RecoverySystem", "rebootWipeUserData: onReceive");
                    ConditionVariable.this.open();
                }
            }, new Handler(hthread.getLooper()), 0, null, null);
            Log.i("RecoverySystem", "rebootWipeUserData: wait intent to complete");
            condition.block();
            Log.i("RecoverySystem", "rebootWipeUserData: continue..");
            hthread.quitSafely();
            EuiccManager euiccManager = (EuiccManager) context.getSystemService(EuiccManager.class);
            if (wipeEuicc) {
                wipeEuiccData(context, "android");
            } else {
                removeEuiccInvisibleSubs(context, euiccManager);
            }
            if (!shutdown) {
                shutdownArg = null;
            } else {
                shutdownArg = "--shutdown_after";
            }
            if (TextUtils.isEmpty(reason)) {
                reasonArg = null;
            } else {
                String timeStamp = DateFormat.format("yyyy-MM-ddTHH:mm:ssZ", System.currentTimeMillis()).toString();
                String reasonArg2 = "--reason=" + sanitizeArg(reason + "," + timeStamp);
                reasonArg = reasonArg2;
            }
            if (!keepMemtagMode) {
                memtagArg = null;
            } else {
                memtagArg = "--keep_memtag_mode";
            }
            String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
            if (TextUtils.isEmpty(extraCmd)) {
                extraCmdArg = "";
            } else {
                String extraCmdArg2 = "--" + sanitizeArg(extraCmd);
                extraCmdArg = extraCmdArg2;
            }
            try {
                Log.d("RecoverySystem", "!@[RecoverySystem] rebootWipeUserData: wipeDataArg:[--wipe_data], extraCmdArg:[" + extraCmdArg + NavigationBarInflaterView.SIZE_MOD_END);
                String wipeDataArg = memtagArg;
                try {
                    bootCommand(context, shutdownArg, "--wipe_data", extraCmdArg, reasonArg, localeArg, wipeDataArg);
                } catch (IOException e) {
                    ioE = e;
                    AuditLog.logEvent(42, ioE.getMessage());
                    throw ioE;
                }
            } catch (IOException e2) {
                ioE = e2;
            }
        } else {
            AuditLog.logEventForComponent("RecoverySystem", 43, new Object[0]);
            throw new SecurityException("Wiping data is not allowed due to restriction policy.");
        }
    }

    public static boolean wipeEuiccData(Context context, String packageName) {
        long waitingTimeMillis;
        ContentResolver cr = context.getContentResolver();
        if (Settings.Global.getInt(cr, Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            Log.d("RecoverySystem", "Skipping eUICC wipe/retain as it is not provisioned");
            return true;
        }
        EuiccManager euiccManager = (EuiccManager) context.getSystemService(Context.EUICC_SERVICE);
        if (euiccManager == null || !euiccManager.isEnabled()) {
            return false;
        }
        final CountDownLatch euiccFactoryResetLatch = new CountDownLatch(1);
        final AtomicBoolean wipingSucceeded = new AtomicBoolean(false);
        BroadcastReceiver euiccWipeFinishReceiver = new BroadcastReceiver() { // from class: android.os.RecoverySystem.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
                    if (getResultCode() != 0) {
                        int detailedCode = intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                        Log.e("RecoverySystem", "Error wiping euicc data, Detailed code = " + detailedCode);
                    } else {
                        Log.d("RecoverySystem", "Successfully wiped euicc data.");
                        wipingSucceeded.set(true);
                    }
                    euiccFactoryResetLatch.countDown();
                }
            }
        };
        Intent intent = new Intent(ACTION_EUICC_FACTORY_RESET);
        intent.setPackage(packageName);
        PendingIntent callbackIntent = PendingIntent.getBroadcastAsUser(context, 0, intent, 201326592, UserHandle.SYSTEM);
        IntentFilter filterConsent = new IntentFilter();
        filterConsent.addAction(ACTION_EUICC_FACTORY_RESET);
        HandlerThread euiccHandlerThread = new HandlerThread("euiccWipeFinishReceiverThread");
        euiccHandlerThread.start();
        Handler euiccHandler = new Handler(euiccHandlerThread.getLooper());
        context.getApplicationContext().registerReceiver(euiccWipeFinishReceiver, filterConsent, null, euiccHandler);
        euiccManager.eraseSubscriptions(callbackIntent);
        try {
        } catch (InterruptedException e) {
            e = e;
        } catch (Throwable th) {
            e = th;
        }
        try {
            waitingTimeMillis = Settings.Global.getLong(context.getContentResolver(), Settings.Global.EUICC_FACTORY_RESET_TIMEOUT_MILLIS, 30000L);
            if (waitingTimeMillis < 5000) {
                waitingTimeMillis = 5000;
            } else if (waitingTimeMillis > 60000) {
                waitingTimeMillis = 60000;
            }
        } catch (InterruptedException e2) {
            e = e2;
        } catch (Throwable th2) {
            e = th2;
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            throw e;
        }
        try {
            try {
                if (euiccFactoryResetLatch.await(waitingTimeMillis, TimeUnit.MILLISECONDS)) {
                    context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                    return wipingSucceeded.get();
                }
                Log.e("RecoverySystem", "Timeout wiping eUICC data.");
                context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                return false;
            } catch (Throwable th3) {
                e = th3;
                context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                throw e;
            }
        } catch (InterruptedException e3) {
            e = e3;
            Thread.currentThread().interrupt();
            Log.e("RecoverySystem", "Wiping eUICC data interrupted", e);
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            return false;
        }
    }

    private static void removeEuiccInvisibleSubs(Context context, EuiccManager euiccManager) {
        ContentResolver cr = context.getContentResolver();
        if (Settings.Global.getInt(cr, Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            Log.i("RecoverySystem", "Skip removing eUICC invisible profiles as it is not provisioned.");
            return;
        }
        if (euiccManager == null || !euiccManager.isEnabled()) {
            Log.i("RecoverySystem", "Skip removing eUICC invisible profiles as eUICC manager is not available.");
            return;
        }
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        List<SubscriptionInfo> availableSubs = subscriptionManager.getAvailableSubscriptionInfoList();
        if (availableSubs == null || availableSubs.isEmpty()) {
            Log.i("RecoverySystem", "Skip removing eUICC invisible profiles as no available profiles found.");
            return;
        }
        List<SubscriptionInfo> invisibleSubs = new ArrayList<>();
        for (SubscriptionInfo sub : availableSubs) {
            if (sub.isEmbedded() && sub.getGroupUuid() != null && sub.isOpportunistic()) {
                invisibleSubs.add(sub);
            }
        }
        removeEuiccInvisibleSubs(context, invisibleSubs, euiccManager);
    }

    private static boolean removeEuiccInvisibleSubs(Context context, List<SubscriptionInfo> subscriptionInfos, EuiccManager euiccManager) {
        if (subscriptionInfos != null && !subscriptionInfos.isEmpty()) {
            final CountDownLatch removeSubsLatch = new CountDownLatch(subscriptionInfos.size());
            final AtomicInteger removedSubsCount = new AtomicInteger(0);
            BroadcastReceiver removeEuiccSubsReceiver = new BroadcastReceiver() { // from class: android.os.RecoverySystem.5
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if (RecoverySystem.ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS.equals(intent.getAction())) {
                        if (getResultCode() != 0) {
                            int detailedCode = intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                            Log.e("RecoverySystem", "Error removing euicc opportunistic profile, Detailed code = " + detailedCode);
                        } else {
                            Log.e("RecoverySystem", "Successfully remove euicc opportunistic profile.");
                            removedSubsCount.incrementAndGet();
                        }
                        removeSubsLatch.countDown();
                    }
                }
            };
            Intent intent = new Intent(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
            intent.setPackage("android");
            PendingIntent callbackIntent = PendingIntent.getBroadcastAsUser(context, 0, intent, 201326592, UserHandle.SYSTEM);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
            HandlerThread euiccHandlerThread = new HandlerThread("euiccRemovingSubsReceiverThread");
            euiccHandlerThread.start();
            Handler euiccHandler = new Handler(euiccHandlerThread.getLooper());
            context.getApplicationContext().registerReceiver(removeEuiccSubsReceiver, intentFilter, null, euiccHandler);
            for (SubscriptionInfo subscriptionInfo : subscriptionInfos) {
                Log.i("RecoverySystem", "Remove invisible subscription " + subscriptionInfo.getSubscriptionId() + " from card " + subscriptionInfo.getCardId());
                euiccManager.createForCardId(subscriptionInfo.getCardId()).deleteSubscription(subscriptionInfo.getSubscriptionId(), callbackIntent);
            }
            try {
                long waitingTimeMillis = Settings.Global.getLong(context.getContentResolver(), Settings.Global.EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS, DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS);
                if (waitingTimeMillis < MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
                    waitingTimeMillis = MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
                } else if (waitingTimeMillis > MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
                    waitingTimeMillis = MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
                }
                if (!removeSubsLatch.await(waitingTimeMillis, TimeUnit.MILLISECONDS)) {
                    Log.e("RecoverySystem", "Timeout removing invisible euicc profiles.");
                    return false;
                }
                context.getApplicationContext().unregisterReceiver(removeEuiccSubsReceiver);
                euiccHandlerThread.quit();
                return removedSubsCount.get() == subscriptionInfos.size();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.e("RecoverySystem", "Removing invisible euicc profiles interrupted", e);
                return false;
            } finally {
                context.getApplicationContext().unregisterReceiver(removeEuiccSubsReceiver);
                euiccHandlerThread.quit();
            }
        }
        Log.i("RecoverySystem", "There are no eUICC invisible profiles needed to be removed.");
        return true;
    }

    public static void rebootPromptAndWipeUserData(Context context, String reason) throws IOException {
        boolean checkpointing = false;
        IVold vold = null;
        try {
            vold = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
            if (vold != null) {
                checkpointing = vold.needsCheckpoint();
            } else {
                Log.w("RecoverySystem", "Failed to get vold");
            }
        } catch (Exception e) {
            Log.w("RecoverySystem", "Failed to check for checkpointing");
        }
        if (checkpointing) {
            try {
                vold.abortChanges("rescueparty", false);
                Log.i("RecoverySystem", "Rescue Party requested wipe. Aborting update");
                return;
            } catch (Exception e2) {
                Log.i("RecoverySystem", "Rescue Party requested wipe. Rebooting instead.");
                PowerManager pm = (PowerManager) context.getSystemService("power");
                pm.reboot("rescueparty");
                return;
            }
        }
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String localeArg = "--locale=" + Locale.getDefault().toString();
        bootCommand(context, null, "--prompt_and_wipe_data", reasonArg, localeArg);
    }

    public static void rebootPromptAndWipeAppData(Context context, String reason) throws IOException {
        boolean checkpointing = false;
        IVold vold = null;
        try {
            vold = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
            if (vold != null) {
                checkpointing = vold.needsCheckpoint();
            } else {
                Log.w("RecoverySystem", "Failed to get vold");
            }
        } catch (Exception e) {
            Log.w("RecoverySystem", "Failed to check for checkpointing");
        }
        if (checkpointing) {
            try {
                vold.abortChanges("rescueparty", false);
                Log.i("RecoverySystem", "Rescue Party requested wipe. Aborting update");
                return;
            } catch (Exception e2) {
                Log.i("RecoverySystem", "Rescue Party requested wipe. Rebooting instead.");
                PowerManager pm = (PowerManager) context.getSystemService("power");
                pm.reboot("rescueparty");
                return;
            }
        }
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String localeArg = "--locale=" + Locale.getDefault().toString();
        bootCommand(context, null, "--prompt_and_wipe_app_data", reasonArg, localeArg);
    }

    public static void rebootWipeCache(Context context) throws IOException {
        rebootWipeCache(context, context.getPackageName());
    }

    public static void rebootWipeCache(Context context, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
        bootCommand(context, "--wipe_cache", reasonArg, localeArg);
    }

    @SystemApi
    public static void rebootWipeAb(Context context, File packageFile, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String filename = packageFile.getCanonicalPath();
        String filenameArg = "--wipe_package=" + filename;
        String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
        bootCommand(context, "--wipe_ab", filenameArg, reasonArg, localeArg);
    }

    public void wipePartitionToExt4() throws IOException {
        rebootRecoveryWithCommand("--wipe_data\n--reformat_data=ext4");
    }

    public static void rebootWipeCustomerPartition(Context context, String arg, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        bootCommand(context, arg, reasonArg);
    }

    private static String getRecoveryReason(String arg) {
        int idx = arg.indexOf("=");
        try {
            String reason = arg.substring(idx + 1);
            return reason;
        } catch (StringIndexOutOfBoundsException e) {
            Log.e("RecoverySystem", "StringIndexOutOfBoundsException when splitting recovery cause:", e);
            return null;
        }
    }

    private static void bootCommand(Context context, String... args) throws IOException {
        synchronized (mShutdownIsInProgressLock) {
            if (mShutdownIsInProgress.booleanValue()) {
                return;
            }
            mShutdownIsInProgress = true;
            Log.i("RecoverySystem", "!@[RecoverySystem] bootCommand: " + Arrays.toString(args));
            synchronized (sRequestLock) {
                StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                StringBuilder sb = new StringBuilder();
                sb.append("!@[RecoverySystem] ");
                for (StackTraceElement st : stackTraceElements) {
                    sb.append(st.toString() + "\n");
                }
                Log.i("RecoverySystem", sb.toString());
                RECOVERY_DIR.mkdirs();
                COMMAND_FILE.delete();
                String recovery_cause = null;
                LOG_FILE.delete();
                int retryCount = 3;
                while (true) {
                    RandomAccessFile command = new RandomAccessFile(COMMAND_FILE, "rwd");
                    try {
                        for (String arg : args) {
                            if (!TextUtils.isEmpty(arg)) {
                                command.writeBytes(arg);
                                command.writeBytes("\n");
                                if (arg.startsWith("--reason=")) {
                                    recovery_cause = getRecoveryReason(arg);
                                }
                            }
                        }
                        Log.i("RecoverySystem", "!@[RecoverySystem] bootCommand: before fsync syscall!!");
                        command.getFD().sync();
                        Log.i("RecoverySystem", "!@[RecoverySystem] bootCommand: after fsync syscall!!");
                        command.close();
                        retryCount--;
                        if (COMMAND_FILE.exists()) {
                            Log.i("RecoverySystem", "COMMAND_FILE is created!!");
                            break;
                        } else {
                            Log.i("RecoverySystem", "retryCount : " + retryCount);
                            if (retryCount == 0) {
                                break;
                            }
                        }
                    } catch (Throwable th) {
                        command.close();
                        throw th;
                    }
                }
                if (!COMMAND_FILE.exists()) {
                    Log.i("RecoverySystem", "!@[RecoverySystem] bootCommand: command file absent, throw exception");
                    throw new IOException("Reboot failed (unable to create command file)");
                }
                PowerManager pm = (PowerManager) context.getSystemService("power");
                String reason = SystemProperties.get("persist.sys.reboot.reason");
                if ("nvrecovery".equals(reason)) {
                    Log.i("RecoverySystem", "FactoryTest ->nvrecovery ");
                    pm.reboot("nvrecovery");
                } else if (Context.DOWNLOAD_SERVICE.equals(reason)) {
                    Log.i("RecoverySystem", "FactoryTest ->download ");
                    pm.reboot(Context.DOWNLOAD_SERVICE);
                } else {
                    Log.d("RecoverySystem", "calling pm.reboot");
                    if (recovery_cause == null) {
                        recovery_cause = "bootCommand()";
                    }
                    Log.d("RecoverySystem", "!@[RecoverySystem] bootCommand: [reset tracking] write to recovery_cause : " + recovery_cause);
                    try {
                        FileOutputStream fos = new FileOutputStream("/sys/class/sec/sec_debug/recovery_cause");
                        try {
                            String content = "RecoverySystem " + recovery_cause;
                            fos.write(content.getBytes(StandardCharsets.UTF_8));
                            fos.close();
                        } catch (Throwable th2) {
                            try {
                                fos.close();
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                            }
                            throw th2;
                        }
                    } catch (IOException e) {
                        Log.e("RecoverySystem", "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e);
                    }
                    pm.reboot("recovery");
                }
                throw new IOException("Reboot failed (no permissions?)");
            }
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:114:0x008e -> B:26:0x00b5). Please report as a decompilation issue!!! */
    public static String handleAftermath(Context context) {
        synchronized (mShutdownIsInProgressLock) {
            if (mShutdownIsInProgress.booleanValue()) {
                Log.i("RecoverySystem", "!@[RecoverySystem] handleAftermath: disabled, as shutdown in progress");
                return null;
            }
            Log.i("RecoverySystem", "!@[RecoverySystem] handleAftermath");
            String log = null;
            try {
                log = FileUtils.readTextFile(LOG_FILE, -65536, "...\n");
            } catch (FileNotFoundException e) {
                Log.i("RecoverySystem", "No recovery log file");
            } catch (IOException e2) {
                Log.e("RecoverySystem", "Error reading recovery log", e2);
            }
            FileInputStream reFis = null;
            try {
            } catch (IOException e3) {
                Log.e("RecoverySystem", "IOException when close last_recovery_mode file:", e3);
            }
            try {
                try {
                    File reFile = new File("/cache/recovery/last_recovery_mode");
                    reFis = new FileInputStream(reFile);
                    byte[] mode = new byte[21];
                    int bytes = reFis.read(mode);
                    if (bytes > 0) {
                        String lastRecoveryMode = new String(mode, 0, bytes, StandardCharsets.UTF_8);
                        Log.i("RecoverySystem", "last_recovery_mode : " + lastRecoveryMode);
                        SystemProperties.set(LAST_RECOVERY_MODE, lastRecoveryMode);
                    }
                    if (!reFile.delete()) {
                        Log.i("RecoverySystem", "Failed to delete /cache/recovery/last_recovery_mode");
                    }
                    reFis.close();
                } catch (FileNotFoundException e4) {
                    Log.e("RecoverySystem", "FileNotFoundException when open /cache/recovery/last_recovery_mode:", e4);
                    if (reFis != null) {
                        reFis.close();
                    }
                } catch (IOException e5) {
                    Log.e("RecoverySystem", "IOException when read /cache/recovery/last_recovery_mode:", e5);
                    if (reFis != null) {
                        reFis.close();
                    }
                }
                copyFile(new File(RECOVERY_DIR, "last_history"), new File("/data/log/recovery_history.log"));
                copyFile(new File(RECOVERY_DIR, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
                copyFile(new File(RECOVERY_DIR, "last_recovery"), new File("/data/log/recovery.log"));
                if (RECOVERY_RESCUEPARTY_FILE.exists()) {
                    try {
                        RandomAccessFile raf = new RandomAccessFile(RECOVERY_RESCUEPARTY_FILE, "rw");
                        try {
                            if (raf.length() > 524288) {
                                raf.setLength(524288L);
                            }
                            raf.close();
                            raf.close();
                        } finally {
                        }
                    } catch (IOException e6) {
                        Log.e("RecoverySystem", "IOException with rescueparty_log :", e6);
                    }
                    copyFile(new File(RECOVERY_DIR, "rescueparty_log"), new File("/data/log/rescueparty_log"));
                }
                boolean reservePackage = BLOCK_MAP_FILE.exists();
                if (!reservePackage && UNCRYPT_PACKAGE_FILE.exists()) {
                    String filename = null;
                    try {
                        filename = FileUtils.readTextFile(UNCRYPT_PACKAGE_FILE, 0, null);
                    } catch (IOException e7) {
                        Log.e("RecoverySystem", "Error reading uncrypt file", e7);
                    }
                    if (filename != null && filename.startsWith("/data")) {
                        if (UNCRYPT_PACKAGE_FILE.delete()) {
                            Log.i("RecoverySystem", "Deleted: " + filename);
                        } else {
                            Log.e("RecoverySystem", "Can't delete: " + filename);
                        }
                    }
                }
                Log.i("RecoverySystem", "copy sudden_reset_log to /data/log/");
                File tmpSuddenResetLastKmsg = new File(RECOVERY_DIR, SUDDEN_RESET_LAST_KMSG_NAME);
                if (tmpSuddenResetLastKmsg.exists()) {
                    copyFile(tmpSuddenResetLastKmsg, new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
                }
                File tmpRecoveryLogFile = new File(TMP_RECOVERY_LOG_PATH);
                if (tmpRecoveryLogFile.exists()) {
                    copyFile(tmpRecoveryLogFile, new File(LAST_CACHE_SUDDEN_RESET_LOG_PATH));
                    copyFile(new File("/proc/last_kmsg"), new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
                    if (tmpRecoveryLogFile.delete()) {
                        Log.i("RecoverySystem", "Deleted: /efs/recovery/tmp_recovery.log");
                    } else {
                        Log.e("RecoverySystem", "Can't delete: /efs/recovery/tmp_recovery.log");
                    }
                }
                String[] names = RECOVERY_DIR.list();
                for (int i = 0; names != null && i < names.length; i++) {
                    if (!names[i].startsWith(LAST_PREFIX) && !names[i].equals(LAST_INSTALL_PATH) && ((!reservePackage || !names[i].equals(BLOCK_MAP_FILE.getName())) && ((!reservePackage || !names[i].equals(UNCRYPT_PACKAGE_FILE.getName())) && !names[i].equals(RECOVERY_RESCUEPARTY_FILE.getName()) && !names[i].equals(COMMAND_FILE.getName())))) {
                        recursiveDelete(new File(RECOVERY_DIR, names[i]));
                    }
                }
                return log;
            } catch (Throwable th) {
                if (reFis != null) {
                    try {
                        reFis.close();
                    } catch (IOException e8) {
                        Log.e("RecoverySystem", "IOException when close last_recovery_mode file:", e8);
                    }
                }
                throw th;
            }
        }
    }

    private static void recursiveDelete(File name) {
        if (name.isDirectory()) {
            String[] files = name.list();
            for (int i = 0; files != null && i < files.length; i++) {
                File f = new File(name, files[i]);
                recursiveDelete(f);
            }
        }
        if (!name.delete()) {
            Log.e("RecoverySystem", "Can't delete: " + name);
        } else {
            Log.i("RecoverySystem", "Deleted: " + name);
        }
    }

    private boolean uncrypt(String packageFile, IRecoverySystemProgressListener listener) {
        try {
            return this.mService.uncrypt(packageFile, listener);
        } catch (RemoteException e) {
            return false;
        }
    }

    private boolean setupBcb(String command) {
        try {
            return this.mService.setupBcb(command);
        } catch (RemoteException e) {
            return false;
        }
    }

    private boolean allocateSpaceForUpdate(File packageFile) throws RemoteException {
        return this.mService.allocateSpaceForUpdate(packageFile.getAbsolutePath());
    }

    private boolean clearBcb() {
        try {
            return this.mService.clearBcb();
        } catch (RemoteException e) {
            return false;
        }
    }

    private void rebootRecoveryWithCommand(String command) {
        try {
            this.mService.rebootRecoveryWithCommand(command);
        } catch (RemoteException e) {
        }
    }

    private boolean requestLskf(String packageName, IntentSender sender) throws IOException {
        Log.i("RecoverySystem", TextUtils.formatSimple("Package<%s> requesting LSKF", packageName));
        try {
            boolean validRequest = this.mService.requestLskf(packageName, sender);
            Log.i("RecoverySystem", TextUtils.formatSimple("LSKF Request isValid = %b", Boolean.valueOf(validRequest)));
            return validRequest;
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not request LSKF capture", e);
        }
    }

    private boolean clearLskf(String packageName) throws IOException {
        try {
            return this.mService.clearLskf(packageName);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not clear LSKF", e);
        }
    }

    private boolean isLskfCaptured(String packageName) throws IOException {
        try {
            return this.mService.isLskfCaptured(packageName);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not get LSKF capture state", e);
        }
    }

    private int rebootWithLskf(String packageName, String reason, boolean slotSwitch) throws IOException {
        try {
            return this.mService.rebootWithLskf(packageName, reason, slotSwitch);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not reboot for update", e);
        }
    }

    private int rebootWithLskfAssumeSlotSwitch(String packageName, String reason) throws IOException {
        try {
            return this.mService.rebootWithLskfAssumeSlotSwitch(packageName, reason);
        } catch (RemoteException | RuntimeException e) {
            throw new IOException("could not reboot for update", e);
        }
    }

    private static String sanitizeArg(String arg) {
        return arg.replace((char) 0, '?').replace('\n', '?');
    }

    public RecoverySystem() {
        this.mService = null;
    }

    public RecoverySystem(IRecoverySystem service) {
        this.mService = service;
    }

    private static void copyFile(File source, File dest) {
        String str = "copyFile: Error close FileChannel ";
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                try {
                    try {
                        inputChannel = new FileInputStream(source).getChannel();
                        outputChannel = new FileOutputStream(dest).getChannel();
                        outputChannel.transferFrom(inputChannel, 0L, inputChannel.size());
                        Os.chmod(dest.getPath(), 416);
                        Os.chown(dest.getPath(), 1000, 1007);
                        if (inputChannel != null) {
                            inputChannel.close();
                        }
                        if (outputChannel != null) {
                            outputChannel.close();
                        }
                    } catch (ErrnoException e) {
                        Log.e("RecoverySystem", "copyFile: Error chmod recovery logs", e);
                        if (inputChannel != null) {
                            inputChannel.close();
                        }
                        if (outputChannel != null) {
                            outputChannel.close();
                        }
                    }
                } catch (IOException e2) {
                    Log.e("RecoverySystem", "copyFile: Error copy recovery logs", e2);
                    if (inputChannel != null) {
                        inputChannel.close();
                    }
                    if (outputChannel != null) {
                        outputChannel.close();
                    }
                }
            } catch (IOException e3) {
                Log.e("RecoverySystem", "copyFile: Error close FileChannel ", e3);
            }
            str = "copyFile: " + source + " -> " + dest;
            Log.i("RecoverySystem", str);
        } catch (Throwable th) {
            if (inputChannel != null) {
                try {
                    inputChannel.close();
                } catch (IOException e4) {
                    Log.e("RecoverySystem", str, e4);
                    throw th;
                }
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
            throw th;
        }
    }
}
