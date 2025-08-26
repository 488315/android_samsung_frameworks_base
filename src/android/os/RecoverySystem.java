package android.os;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IRecoverySystemProgressListener;
import android.os.IVold;
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
import java.io.FileNotFoundException;
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

    private static HashSet<X509Certificate> getTrustedCerts(File file) throws GeneralSecurityException, IOException {
        HashSet<X509Certificate> hashSet = new HashSet<>();
        if (file == null) {
            file = DEFAULT_KEYSTORE;
        }
        ZipFile zipFile = new ZipFile(file);
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                InputStream inputStream = zipFile.getInputStream(enumerationEntries.nextElement());
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
                            long j = Long.parseLong(str);
                            zipFile.close();
                            return j;
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

    public static void verifyPackage(File file, ProgressListener progressListener, File file2) throws GeneralSecurityException, IOException {
        long length = file.length();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
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
                    SignerInfo signerInfoVerify = pkcs7.verify(signerInfo, new InputStream(length, i, jCurrentTimeMillis, randomAccessFile, progressListener) { // from class: android.os.RecoverySystem.1
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
                            this.val$startTimeMillis = jCurrentTimeMillis;
                            this.val$raf = randomAccessFile;
                            this.val$listenerForInner = progressListener;
                            this.toRead = (length - i) - 2;
                            this.lastPublishTime = jCurrentTimeMillis;
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
                            int i7 = this.val$raf.read(bArr3, i5, i6);
                            this.soFar += i7;
                            if (this.val$listenerForInner != null) {
                                long jCurrentTimeMillis2 = System.currentTimeMillis();
                                int i8 = (int) ((this.soFar * 100) / this.toRead);
                                if (i8 > this.lastPercent && jCurrentTimeMillis2 - this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                                    this.lastPercent = i8;
                                    this.lastPublishTime = jCurrentTimeMillis2;
                                    this.val$listenerForInner.onProgress(i8);
                                }
                            }
                            return i7;
                        }
                    });
                    boolean zInterrupted = Thread.interrupted();
                    if (progressListener != null) {
                        progressListener.onProgress(100);
                    }
                    if (zInterrupted) {
                        throw new SignatureException("verification was interrupted");
                    }
                    if (signerInfoVerify == null) {
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
            final long jCurrentTimeMillis = System.currentTimeMillis();
            this.val$progressHandler.post(new Runnable() { // from class: android.os.RecoverySystem.2.1
                @Override // java.lang.Runnable
                public void run() {
                    if (i <= AnonymousClass2.this.lastProgress || jCurrentTimeMillis - AnonymousClass2.this.lastPublishTime <= RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                        return;
                    }
                    AnonymousClass2.this.lastProgress = i;
                    AnonymousClass2.this.lastPublishTime = jCurrentTimeMillis;
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

    /* JADX WARN: Removed duplicated region for block: B:104:0x01c3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x011f A[Catch: all -> 0x0293, TryCatch #1 {, blocks: (B:4:0x000d, B:6:0x0058, B:8:0x0066, B:9:0x0071, B:10:0x007f, B:14:0x0086, B:17:0x008f, B:18:0x009d, B:19:0x009e, B:21:0x00b7, B:23:0x00c0, B:26:0x00d7, B:25:0x00c6, B:29:0x00e0, B:30:0x00e3, B:31:0x00e4, B:33:0x011f, B:34:0x0130, B:36:0x013c, B:42:0x017f, B:43:0x018e, B:45:0x0194, B:47:0x01b6, B:49:0x01bb, B:51:0x01c3, B:54:0x01e3, B:56:0x01eb, B:57:0x01f2, B:59:0x020d, B:69:0x0223, B:71:0x023b, B:73:0x024e, B:74:0x025f, B:75:0x0269, B:66:0x021a, B:65:0x0217, B:68:0x021c, B:76:0x026a, B:77:0x0278, B:52:0x01cb, B:85:0x0284, B:86:0x0292, B:83:0x0282, B:82:0x027f, B:37:0x014e, B:39:0x015a, B:40:0x016c, B:20:0x00a3), top: B:92:0x000d, inners: #3, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x013c A[Catch: all -> 0x0293, TryCatch #1 {, blocks: (B:4:0x000d, B:6:0x0058, B:8:0x0066, B:9:0x0071, B:10:0x007f, B:14:0x0086, B:17:0x008f, B:18:0x009d, B:19:0x009e, B:21:0x00b7, B:23:0x00c0, B:26:0x00d7, B:25:0x00c6, B:29:0x00e0, B:30:0x00e3, B:31:0x00e4, B:33:0x011f, B:34:0x0130, B:36:0x013c, B:42:0x017f, B:43:0x018e, B:45:0x0194, B:47:0x01b6, B:49:0x01bb, B:51:0x01c3, B:54:0x01e3, B:56:0x01eb, B:57:0x01f2, B:59:0x020d, B:69:0x0223, B:71:0x023b, B:73:0x024e, B:74:0x025f, B:75:0x0269, B:66:0x021a, B:65:0x0217, B:68:0x021c, B:76:0x026a, B:77:0x0278, B:52:0x01cb, B:85:0x0284, B:86:0x0292, B:83:0x0282, B:82:0x027f, B:37:0x014e, B:39:0x015a, B:40:0x016c, B:20:0x00a3), top: B:92:0x000d, inners: #3, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x014e A[Catch: all -> 0x0293, TryCatch #1 {, blocks: (B:4:0x000d, B:6:0x0058, B:8:0x0066, B:9:0x0071, B:10:0x007f, B:14:0x0086, B:17:0x008f, B:18:0x009d, B:19:0x009e, B:21:0x00b7, B:23:0x00c0, B:26:0x00d7, B:25:0x00c6, B:29:0x00e0, B:30:0x00e3, B:31:0x00e4, B:33:0x011f, B:34:0x0130, B:36:0x013c, B:42:0x017f, B:43:0x018e, B:45:0x0194, B:47:0x01b6, B:49:0x01bb, B:51:0x01c3, B:54:0x01e3, B:56:0x01eb, B:57:0x01f2, B:59:0x020d, B:69:0x0223, B:71:0x023b, B:73:0x024e, B:74:0x025f, B:75:0x0269, B:66:0x021a, B:65:0x0217, B:68:0x021c, B:76:0x026a, B:77:0x0278, B:52:0x01cb, B:85:0x0284, B:86:0x0292, B:83:0x0282, B:82:0x027f, B:37:0x014e, B:39:0x015a, B:40:0x016c, B:20:0x00a3), top: B:92:0x000d, inners: #3, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x017f A[Catch: all -> 0x0293, TryCatch #1 {, blocks: (B:4:0x000d, B:6:0x0058, B:8:0x0066, B:9:0x0071, B:10:0x007f, B:14:0x0086, B:17:0x008f, B:18:0x009d, B:19:0x009e, B:21:0x00b7, B:23:0x00c0, B:26:0x00d7, B:25:0x00c6, B:29:0x00e0, B:30:0x00e3, B:31:0x00e4, B:33:0x011f, B:34:0x0130, B:36:0x013c, B:42:0x017f, B:43:0x018e, B:45:0x0194, B:47:0x01b6, B:49:0x01bb, B:51:0x01c3, B:54:0x01e3, B:56:0x01eb, B:57:0x01f2, B:59:0x020d, B:69:0x0223, B:71:0x023b, B:73:0x024e, B:74:0x025f, B:75:0x0269, B:66:0x021a, B:65:0x0217, B:68:0x021c, B:76:0x026a, B:77:0x0278, B:52:0x01cb, B:85:0x0284, B:86:0x0292, B:83:0x0282, B:82:0x027f, B:37:0x014e, B:39:0x015a, B:40:0x016c, B:20:0x00a3), top: B:92:0x000d, inners: #3, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01cb A[Catch: all -> 0x0293, TryCatch #1 {, blocks: (B:4:0x000d, B:6:0x0058, B:8:0x0066, B:9:0x0071, B:10:0x007f, B:14:0x0086, B:17:0x008f, B:18:0x009d, B:19:0x009e, B:21:0x00b7, B:23:0x00c0, B:26:0x00d7, B:25:0x00c6, B:29:0x00e0, B:30:0x00e3, B:31:0x00e4, B:33:0x011f, B:34:0x0130, B:36:0x013c, B:42:0x017f, B:43:0x018e, B:45:0x0194, B:47:0x01b6, B:49:0x01bb, B:51:0x01c3, B:54:0x01e3, B:56:0x01eb, B:57:0x01f2, B:59:0x020d, B:69:0x0223, B:71:0x023b, B:73:0x024e, B:74:0x025f, B:75:0x0269, B:66:0x021a, B:65:0x0217, B:68:0x021c, B:76:0x026a, B:77:0x0278, B:52:0x01cb, B:85:0x0284, B:86:0x0292, B:83:0x0282, B:82:0x027f, B:37:0x014e, B:39:0x015a, B:40:0x016c, B:20:0x00a3), top: B:92:0x000d, inners: #3, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01eb A[Catch: all -> 0x0293, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x000d, B:6:0x0058, B:8:0x0066, B:9:0x0071, B:10:0x007f, B:14:0x0086, B:17:0x008f, B:18:0x009d, B:19:0x009e, B:21:0x00b7, B:23:0x00c0, B:26:0x00d7, B:25:0x00c6, B:29:0x00e0, B:30:0x00e3, B:31:0x00e4, B:33:0x011f, B:34:0x0130, B:36:0x013c, B:42:0x017f, B:43:0x018e, B:45:0x0194, B:47:0x01b6, B:49:0x01bb, B:51:0x01c3, B:54:0x01e3, B:56:0x01eb, B:57:0x01f2, B:59:0x020d, B:69:0x0223, B:71:0x023b, B:73:0x024e, B:74:0x025f, B:75:0x0269, B:66:0x021a, B:65:0x0217, B:68:0x021c, B:76:0x026a, B:77:0x0278, B:52:0x01cb, B:85:0x0284, B:86:0x0292, B:83:0x0282, B:82:0x027f, B:37:0x014e, B:39:0x015a, B:40:0x016c, B:20:0x00a3), top: B:92:0x000d, inners: #3, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x026a A[Catch: all -> 0x0293, TryCatch #1 {, blocks: (B:4:0x000d, B:6:0x0058, B:8:0x0066, B:9:0x0071, B:10:0x007f, B:14:0x0086, B:17:0x008f, B:18:0x009d, B:19:0x009e, B:21:0x00b7, B:23:0x00c0, B:26:0x00d7, B:25:0x00c6, B:29:0x00e0, B:30:0x00e3, B:31:0x00e4, B:33:0x011f, B:34:0x0130, B:36:0x013c, B:42:0x017f, B:43:0x018e, B:45:0x0194, B:47:0x01b6, B:49:0x01bb, B:51:0x01c3, B:54:0x01e3, B:56:0x01eb, B:57:0x01f2, B:59:0x020d, B:69:0x0223, B:71:0x023b, B:73:0x024e, B:74:0x025f, B:75:0x0269, B:66:0x021a, B:65:0x0217, B:68:0x021c, B:76:0x026a, B:77:0x0278, B:52:0x01cb, B:85:0x0284, B:86:0x0292, B:83:0x0282, B:82:0x027f, B:37:0x014e, B:39:0x015a, B:40:0x016c, B:20:0x00a3), top: B:92:0x000d, inners: #3, #6, #7 }] */
    @SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void installPackage(Context context, File file, boolean z) throws IOException {
        String str;
        int i;
        synchronized (sRequestLock) {
            LOG_FILE.delete();
            File file2 = UNCRYPT_PACKAGE_FILE;
            file2.delete();
            String canonicalPath = file.getCanonicalPath();
            Log.w(TAG, "!!! REBOOTING TO INSTALL " + canonicalPath + " !!!");
            boolean zEndsWith = canonicalPath.endsWith("_s.zip");
            boolean zStartsWith = canonicalPath.startsWith("/data/");
            String str2 = "--update_org_package=" + canonicalPath + ShaderAssembler.NEWLINE;
            long superUsedSize = parseSuperUsedSize(file);
            if (superUsedSize > 0) {
                if (!((StorageManager) context.getSystemService(StorageManager.class)).shrinkDataDdp(superUsedSize)) {
                    Log.e(TAG, "[DDP] Failed to shrink /data to expand super partition");
                    throw new IOException("Failed to shrink /data to expand super partition");
                }
                SystemProperties.set("persist.sys.ddp.super_used_size", Long.toString(superUsedSize));
            }
            if (zStartsWith) {
                if (z) {
                    if (!BLOCK_MAP_FILE.exists()) {
                        Log.e(TAG, "Package claimed to have been processed but failed to find the block map file.");
                        throw new IOException("Failed to find block map file");
                    }
                } else {
                    FileWriter fileWriter = new FileWriter(file2);
                    try {
                        fileWriter.write(canonicalPath + ShaderAssembler.NEWLINE);
                        fileWriter.close();
                        if (!file2.setReadable(true, false) || !file2.setWritable(true, false)) {
                            Log.e(TAG, "Error setting permission for " + file2);
                        }
                        BLOCK_MAP_FILE.delete();
                    } catch (Throwable th) {
                        fileWriter.close();
                        throw th;
                    }
                }
                canonicalPath = "@/cache/recovery/block.map";
                String str3 = ("--update_package=" + canonicalPath + ShaderAssembler.NEWLINE) + ("--locale=" + Locale.getDefault().toLanguageTag() + ShaderAssembler.NEWLINE);
                if (zEndsWith) {
                }
                if (!"com.ws.dm".equals(context.getPackageName())) {
                }
                if (zStartsWith) {
                }
                COMMAND_FILE.delete();
                i = 3;
                while (true) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(COMMAND_FILE, "rwd");
                    randomAccessFile.writeBytes(str);
                    Log.i(TAG, "!@RecoverySystem before fsync syscall!!");
                    randomAccessFile.getFD().sync();
                    Log.i(TAG, "!@RecoverySystem after fsync syscall!!");
                    randomAccessFile.close();
                    i--;
                    if (!COMMAND_FILE.exists()) {
                    }
                }
                if (COMMAND_FILE.exists()) {
                }
            } else {
                String str32 = ("--update_package=" + canonicalPath + ShaderAssembler.NEWLINE) + ("--locale=" + Locale.getDefault().toLanguageTag() + ShaderAssembler.NEWLINE);
                if (zEndsWith) {
                    str32 = str32 + "--security\n";
                }
                if (!"com.ws.dm".equals(context.getPackageName())) {
                    str = str32 + "--carry_out=att_fota\n";
                } else if ("com.samsung.sdm.sdmviewer".equals(context.getPackageName())) {
                    str = str32 + "--carry_out=vzw_fota\n";
                } else {
                    str = str32 + "--carry_out=open_fota\n";
                }
                if (zStartsWith) {
                    str = str + str2;
                }
                COMMAND_FILE.delete();
                i = 3;
                while (true) {
                    try {
                        RandomAccessFile randomAccessFile2 = new RandomAccessFile(COMMAND_FILE, "rwd");
                        try {
                            randomAccessFile2.writeBytes(str);
                            Log.i(TAG, "!@RecoverySystem before fsync syscall!!");
                            randomAccessFile2.getFD().sync();
                            Log.i(TAG, "!@RecoverySystem after fsync syscall!!");
                            randomAccessFile2.close();
                            i--;
                            if (!COMMAND_FILE.exists()) {
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
                if (COMMAND_FILE.exists()) {
                    Log.i(TAG, "!@ command file absent, throw exception");
                    throw new IOException("failed to create command file");
                }
                Log.d(TAG, "!@[reset tracking] installPackage write to recovery_cause");
                try {
                    FileWriter fileWriter2 = new FileWriter("/sys/class/sec/sec_debug/recovery_cause");
                    try {
                        fileWriter2.write("RecoverySystem installPackage: " + str);
                        fileWriter2.close();
                    } catch (Throwable th2) {
                        try {
                            fileWriter2.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                } catch (IOException e2) {
                    Log.e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e2);
                }
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                String str4 = PowerManager.REBOOT_RECOVERY_UPDATE;
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK) && ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0).getState() != 2) {
                    str4 = PowerManager.REBOOT_RECOVERY_UPDATE + ",quiescent";
                }
                powerManager.reboot(str4);
                throw new IOException("Reboot failed (no permissions?)");
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
        boolean zEndsWith = canonicalPath.endsWith("_s.zip");
        if (canonicalPath.startsWith("/data/")) {
            canonicalPath = "@/cache/recovery/block.map";
        }
        String str = ("--update_package=" + canonicalPath + ShaderAssembler.NEWLINE) + ("--locale=" + Locale.getDefault().toLanguageTag() + ShaderAssembler.NEWLINE);
        if (zEndsWith) {
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

    public static void rebootWipeUserData(Context context) throws IOException, RemoteException {
        rebootWipeUserData(context, false, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, String str) throws IOException, RemoteException {
        rebootWipeUserData(context, false, str, false, false);
    }

    public static void rebootWipeUserData(Context context, boolean z) throws IOException, RemoteException {
        rebootWipeUserData(context, z, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2) throws IOException, RemoteException {
        rebootWipeUserData(context, z, str, z2, false);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2, boolean z3) throws IOException, RemoteException {
        rebootWipeUserData(context, z, str, z2, z3, false);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2, boolean z3, boolean z4) throws IOException, RemoteException {
        rebootWipeUserData(context, z, str, z2, z3, z4, null);
    }

    public static void rebootWipeUserData(Context context, boolean z, String str, boolean z2, boolean z3, boolean z4, String str2) throws IOException, RemoteException {
        String string;
        String str3;
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
                conditionVariable.open();
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
        String str4 = z ? "--shutdown_after" : null;
        if (TextUtils.isEmpty(str)) {
            string = null;
        } else {
            String string2 = DateFormat.format("yyyy-MM-ddTHH:mm:ssZ", System.currentTimeMillis()).toString();
            StringBuilder sb = new StringBuilder("--reason=");
            sb.append(sanitizeArg(str + "," + string2));
            string = sb.toString();
        }
        String str5 = z4 ? "--keep_memtag_mode" : null;
        String str6 = "--locale=" + Locale.getDefault().toLanguageTag();
        if (TextUtils.isEmpty(str2)) {
            str3 = "";
        } else {
            str3 = "--" + sanitizeArg(str2);
        }
        try {
            Log.d(TAG, "!@[RecoverySystem] rebootWipeUserData: wipeDataArg:[--wipe_data], extraCmdArg:[" + str3 + NavigationBarInflaterView.SIZE_MOD_END);
            bootCommand(context, str4, RECOVERY_WIPE_DATA_COMMAND, str3, string, str6, str5);
        } catch (IOException e) {
            AuditLog.logEvent(42, e.getMessage());
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0091 A[Catch: all -> 0x00aa, InterruptedException -> 0x00ac, TRY_LEAVE, TryCatch #0 {InterruptedException -> 0x00ac, blocks: (B:11:0x006d, B:17:0x0089, B:19:0x0091), top: B:32:0x006d, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean wipeEuiccData(Context context, String str) {
        if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            Log.d(TAG, "Skipping eUICC wipe/retain as it is not provisioned");
            return true;
        }
        EuiccManager euiccManager = (EuiccManager) context.getSystemService(Context.EUICC_SERVICE);
        if (euiccManager == null || !euiccManager.isEnabled()) {
            return false;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: android.os.RecoverySystem.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
                    if (getResultCode() != 0) {
                        Log.e(RecoverySystem.TAG, "Error wiping euicc data, Detailed code = " + intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0));
                    } else {
                        Log.d(RecoverySystem.TAG, "Successfully wiped euicc data.");
                        atomicBoolean.set(true);
                    }
                    countDownLatch.countDown();
                }
            }
        };
        Intent intent = new Intent(ACTION_EUICC_FACTORY_RESET);
        intent.setPackage(str);
        PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(context, 0, intent, 201326592, UserHandle.SYSTEM);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_EUICC_FACTORY_RESET);
        HandlerThread handlerThread = new HandlerThread("euiccWipeFinishReceiverThread");
        handlerThread.start();
        context.getApplicationContext().registerReceiver(broadcastReceiver, intentFilter, null, new Handler(handlerThread.getLooper()));
        euiccManager.eraseSubscriptions(broadcastAsUser);
        try {
            try {
                long j = Settings.Global.getLong(context.getContentResolver(), Settings.Global.EUICC_FACTORY_RESET_TIMEOUT_MILLIS, 30000L);
                long j2 = 5000;
                if (j < 5000) {
                    j = j2;
                    if (countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                        Log.e(TAG, "Timeout wiping eUICC data.");
                    } else {
                        context.getApplicationContext().unregisterReceiver(broadcastReceiver);
                        return atomicBoolean.get();
                    }
                } else {
                    j2 = 60000;
                    if (j > 60000) {
                        j = j2;
                    }
                    if (countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.e(TAG, "Wiping eUICC data interrupted", e);
            }
            return false;
        } finally {
            context.getApplicationContext().unregisterReceiver(broadcastReceiver);
        }
    }

    private static void removeEuiccInvisibleSubs(Context context, EuiccManager euiccManager) throws RemoteException {
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

    private static boolean removeEuiccInvisibleSubs(Context context, List<SubscriptionInfo> list, EuiccManager euiccManager) {
        if (list == null || list.isEmpty()) {
            Log.i(TAG, "There are no eUICC invisible profiles needed to be removed.");
            return true;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(list.size());
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: android.os.RecoverySystem.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (RecoverySystem.ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS.equals(intent.getAction())) {
                    if (getResultCode() != 0) {
                        Log.e(RecoverySystem.TAG, "Error removing euicc opportunistic profile, Detailed code = " + intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0));
                    } else {
                        Log.e(RecoverySystem.TAG, "Successfully remove euicc opportunistic profile.");
                        atomicInteger.incrementAndGet();
                    }
                    countDownLatch.countDown();
                }
            }
        };
        Intent intent = new Intent(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
        intent.setPackage("android");
        PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(context, 0, intent, 201326592, UserHandle.SYSTEM);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
        HandlerThread handlerThread = new HandlerThread("euiccRemovingSubsReceiverThread");
        handlerThread.start();
        context.getApplicationContext().registerReceiver(broadcastReceiver, intentFilter, null, new Handler(handlerThread.getLooper()));
        for (SubscriptionInfo subscriptionInfo : list) {
            Log.i(TAG, "Remove invisible subscription " + subscriptionInfo.getSubscriptionId() + " from card " + subscriptionInfo.getCardId());
            euiccManager.createForCardId(subscriptionInfo.getCardId()).deleteSubscription(subscriptionInfo.getSubscriptionId(), broadcastAsUser);
        }
        try {
            long j = Settings.Global.getLong(context.getContentResolver(), Settings.Global.EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS, DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS);
            long j2 = MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
            if (j < MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
                j = j2;
            } else {
                j2 = MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
                if (j > MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
                    j = j2;
                }
            }
            if (!countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                Log.e(TAG, "Timeout removing invisible euicc profiles.");
                return false;
            }
            context.getApplicationContext().unregisterReceiver(broadcastReceiver);
            handlerThread.quit();
            return atomicInteger.get() == list.size();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Log.e(TAG, "Removing invisible euicc profiles interrupted", e);
            return false;
        } finally {
            context.getApplicationContext().unregisterReceiver(broadcastReceiver);
            handlerThread.quit();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0028 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void rebootPromptAndWipeUserData(Context context, String str) throws IOException {
        IVold iVoldAsInterface;
        boolean zNeedsCheckpoint;
        String str2;
        try {
            iVoldAsInterface = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
            try {
            } catch (Exception unused) {
                Log.w(TAG, "Failed to check for checkpointing");
                zNeedsCheckpoint = false;
                if (zNeedsCheckpoint) {
                }
            }
        } catch (Exception unused2) {
            iVoldAsInterface = null;
        }
        if (iVoldAsInterface != null) {
            zNeedsCheckpoint = iVoldAsInterface.needsCheckpoint();
            if (zNeedsCheckpoint) {
                try {
                    iVoldAsInterface.abortChanges("rescueparty", false);
                    Log.i(TAG, "Rescue Party requested wipe. Aborting update");
                    return;
                } catch (Exception unused3) {
                    Log.i(TAG, "Rescue Party requested wipe. Rebooting instead.");
                    ((PowerManager) context.getSystemService("power")).reboot("rescueparty");
                    return;
                }
            }
            if (TextUtils.isEmpty(str)) {
                str2 = null;
            } else {
                str2 = "--reason=" + sanitizeArg(str);
            }
            bootCommand(context, null, "--prompt_and_wipe_data", str2, "--locale=" + Locale.getDefault().toString());
            return;
        }
        Log.w(TAG, "Failed to get vold");
        zNeedsCheckpoint = false;
        if (zNeedsCheckpoint) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0028 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void rebootPromptAndWipeAppData(Context context, String str) throws IOException {
        IVold iVoldAsInterface;
        boolean zNeedsCheckpoint;
        String str2;
        try {
            iVoldAsInterface = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
            try {
            } catch (Exception unused) {
                Log.w(TAG, "Failed to check for checkpointing");
                zNeedsCheckpoint = false;
                if (zNeedsCheckpoint) {
                }
            }
        } catch (Exception unused2) {
            iVoldAsInterface = null;
        }
        if (iVoldAsInterface != null) {
            zNeedsCheckpoint = iVoldAsInterface.needsCheckpoint();
            if (zNeedsCheckpoint) {
                try {
                    iVoldAsInterface.abortChanges("rescueparty", false);
                    Log.i(TAG, "Rescue Party requested wipe. Aborting update");
                    return;
                } catch (Exception unused3) {
                    Log.i(TAG, "Rescue Party requested wipe. Rebooting instead.");
                    ((PowerManager) context.getSystemService("power")).reboot("rescueparty");
                    return;
                }
            }
            if (TextUtils.isEmpty(str)) {
                str2 = null;
            } else {
                str2 = "--reason=" + sanitizeArg(str);
            }
            bootCommand(context, null, "--prompt_and_wipe_app_data", str2, "--locale=" + Locale.getDefault().toString());
            return;
        }
        Log.w(TAG, "Failed to get vold");
        zNeedsCheckpoint = false;
        if (zNeedsCheckpoint) {
        }
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
                String recoveryReason = null;
                int i = 3;
                while (true) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(COMMAND_FILE, "rwd");
                    try {
                        for (String str : strArr) {
                            if (!TextUtils.isEmpty(str)) {
                                randomAccessFile.writeBytes(str);
                                randomAccessFile.writeBytes(ShaderAssembler.NEWLINE);
                                if (str.startsWith("--reason=")) {
                                    recoveryReason = getRecoveryReason(str);
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
                String str2 = SystemProperties.get("persist.sys.reboot.reason");
                if ("nvrecovery".equals(str2)) {
                    Log.i(TAG, "FactoryTest ->nvrecovery ");
                    powerManager.reboot("nvrecovery");
                } else if (Context.DOWNLOAD_SERVICE.equals(str2)) {
                    Log.i(TAG, "FactoryTest ->download ");
                    powerManager.reboot(Context.DOWNLOAD_SERVICE);
                } else {
                    Log.d(TAG, "calling pm.reboot");
                    if (recoveryReason == null) {
                        recoveryReason = "bootCommand()";
                    }
                    Log.d(TAG, "!@[RecoverySystem] bootCommand: [reset tracking] write to recovery_cause : " + recoveryReason);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/sec/sec_debug/recovery_cause");
                        try {
                            fileOutputStream.write(("RecoverySystem " + recoveryReason).getBytes(StandardCharsets.UTF_8));
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                            }
                            throw th2;
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e);
                    }
                    powerManager.reboot("recovery");
                }
                throw new IOException("Reboot failed (no permissions?)");
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:9|10|(2:143|11)|17|136|(9:133|18|19|141|20|(1:22)|23|(1:25)|26)|135|48|(8:134|50|139|51|(1:53)|54|55|65)|66|(2:68|(3:127|70|(2:77|(1:79)(1:80))))|81|(1:83)|84|(2:86|(1:88)(1:89))|90|(3:94|(1:154)(2:113|148)|114)|147|115) */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009d, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009e, code lost:
    
        android.util.Log.e(android.os.RecoverySystem.TAG, "IOException when close last_recovery_mode file:", r9);
     */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x024e: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:117:0x024e */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0251 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x00e2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0052 A[Catch: IOException -> 0x0078, FileNotFoundException -> 0x007a, all -> 0x024d, TryCatch #4 {all -> 0x024d, blocks: (B:20:0x004a, B:22:0x0052, B:23:0x0067, B:25:0x006d, B:36:0x0081, B:42:0x0090), top: B:133:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006d A[Catch: IOException -> 0x0078, FileNotFoundException -> 0x007a, all -> 0x024d, TRY_LEAVE, TryCatch #4 {all -> 0x024d, blocks: (B:20:0x004a, B:22:0x0052, B:23:0x0067, B:25:0x006d, B:36:0x0081, B:42:0x0090), top: B:133:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01e5  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x009e -> B:135:0x00a5). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String handleAftermath(Context context) throws Throwable {
        String textFile;
        int i;
        File file;
        RandomAccessFile randomAccessFile;
        boolean zExists;
        File file2;
        File file3;
        String[] list;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        File file4;
        int i2;
        synchronized (mShutdownIsInProgressLock) {
            FileInputStream fileInputStream3 = null;
            String textFile2 = null;
            if (mShutdownIsInProgress.booleanValue()) {
                Log.i(TAG, "!@[RecoverySystem] handleAftermath: disabled, as shutdown in progress");
                return null;
            }
            Log.i(TAG, "!@[RecoverySystem] handleAftermath");
            try {
                textFile = FileUtils.readTextFile(LOG_FILE, -65536, "...\n");
            } catch (FileNotFoundException unused) {
                Log.i(TAG, "No recovery log file");
                textFile = null;
                try {
                    file4 = new File("/cache/recovery/last_recovery_mode");
                    fileInputStream = new FileInputStream(file4);
                    try {
                        byte[] bArr = new byte[21];
                        i2 = fileInputStream.read(bArr);
                        if (i2 > 0) {
                        }
                        if (!file4.delete()) {
                        }
                        fileInputStream.close();
                    } catch (FileNotFoundException e) {
                        e = e;
                        Log.e(TAG, "FileNotFoundException when open /cache/recovery/last_recovery_mode:", e);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        File file5 = RECOVERY_DIR;
                        copyFile(new File(file5, "last_history"), new File("/data/log/recovery_history.log"));
                        copyFile(new File(file5, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
                        copyFile(new File(file5, "last_recovery"), new File("/data/log/recovery.log"));
                        file = RECOVERY_RESCUEPARTY_FILE;
                        if (file.exists()) {
                        }
                        zExists = BLOCK_MAP_FILE.exists();
                        if (!zExists) {
                        }
                        Log.i(TAG, "copy sudden_reset_log to /data/log/");
                        File file6 = RECOVERY_DIR;
                        file2 = new File(file6, SUDDEN_RESET_LAST_KMSG_NAME);
                        if (file2.exists()) {
                        }
                        file3 = new File(TMP_RECOVERY_LOG_PATH);
                        if (file3.exists()) {
                        }
                        list = file6.list();
                        while (list != null) {
                            if (list[i].startsWith(LAST_PREFIX)) {
                            }
                        }
                        return textFile;
                    } catch (IOException e2) {
                        e = e2;
                        Log.e(TAG, "IOException when read /cache/recovery/last_recovery_mode:", e);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        File file52 = RECOVERY_DIR;
                        copyFile(new File(file52, "last_history"), new File("/data/log/recovery_history.log"));
                        copyFile(new File(file52, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
                        copyFile(new File(file52, "last_recovery"), new File("/data/log/recovery.log"));
                        file = RECOVERY_RESCUEPARTY_FILE;
                        if (file.exists()) {
                        }
                        zExists = BLOCK_MAP_FILE.exists();
                        if (!zExists) {
                        }
                        Log.i(TAG, "copy sudden_reset_log to /data/log/");
                        File file62 = RECOVERY_DIR;
                        file2 = new File(file62, SUDDEN_RESET_LAST_KMSG_NAME);
                        if (file2.exists()) {
                        }
                        file3 = new File(TMP_RECOVERY_LOG_PATH);
                        if (file3.exists()) {
                        }
                        list = file62.list();
                        while (list != null) {
                        }
                        return textFile;
                    }
                    File file522 = RECOVERY_DIR;
                    copyFile(new File(file522, "last_history"), new File("/data/log/recovery_history.log"));
                    copyFile(new File(file522, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
                    copyFile(new File(file522, "last_recovery"), new File("/data/log/recovery.log"));
                    file = RECOVERY_RESCUEPARTY_FILE;
                    if (file.exists()) {
                    }
                    zExists = BLOCK_MAP_FILE.exists();
                    if (!zExists) {
                    }
                    Log.i(TAG, "copy sudden_reset_log to /data/log/");
                    File file622 = RECOVERY_DIR;
                    file2 = new File(file622, SUDDEN_RESET_LAST_KMSG_NAME);
                    if (file2.exists()) {
                    }
                    file3 = new File(TMP_RECOVERY_LOG_PATH);
                    if (file3.exists()) {
                    }
                    list = file622.list();
                    while (list != null) {
                    }
                    return textFile;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream3 = fileInputStream2;
                    if (fileInputStream3 != null) {
                        try {
                            fileInputStream3.close();
                        } catch (IOException e3) {
                            Log.e(TAG, "IOException when close last_recovery_mode file:", e3);
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                Log.e(TAG, "Error reading recovery log", e4);
                textFile = null;
                file4 = new File("/cache/recovery/last_recovery_mode");
                fileInputStream = new FileInputStream(file4);
                byte[] bArr2 = new byte[21];
                i2 = fileInputStream.read(bArr2);
                if (i2 > 0) {
                }
                if (!file4.delete()) {
                }
                fileInputStream.close();
                File file5222 = RECOVERY_DIR;
                copyFile(new File(file5222, "last_history"), new File("/data/log/recovery_history.log"));
                copyFile(new File(file5222, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
                copyFile(new File(file5222, "last_recovery"), new File("/data/log/recovery.log"));
                file = RECOVERY_RESCUEPARTY_FILE;
                if (file.exists()) {
                }
                zExists = BLOCK_MAP_FILE.exists();
                if (!zExists) {
                }
                Log.i(TAG, "copy sudden_reset_log to /data/log/");
                File file6222 = RECOVERY_DIR;
                file2 = new File(file6222, SUDDEN_RESET_LAST_KMSG_NAME);
                if (file2.exists()) {
                }
                file3 = new File(TMP_RECOVERY_LOG_PATH);
                if (file3.exists()) {
                }
                list = file6222.list();
                while (list != null) {
                }
                return textFile;
            }
            try {
                file4 = new File("/cache/recovery/last_recovery_mode");
                fileInputStream = new FileInputStream(file4);
                byte[] bArr22 = new byte[21];
                i2 = fileInputStream.read(bArr22);
                if (i2 > 0) {
                    String str = new String(bArr22, 0, i2, StandardCharsets.UTF_8);
                    Log.i(TAG, "last_recovery_mode : ".concat(str));
                    SystemProperties.set(LAST_RECOVERY_MODE, str);
                }
                if (!file4.delete()) {
                    Log.i(TAG, "Failed to delete /cache/recovery/last_recovery_mode");
                }
                fileInputStream.close();
            } catch (FileNotFoundException e5) {
                e = e5;
                fileInputStream = null;
            } catch (IOException e6) {
                e = e6;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream3 != null) {
                }
                throw th;
            }
            File file52222 = RECOVERY_DIR;
            copyFile(new File(file52222, "last_history"), new File("/data/log/recovery_history.log"));
            copyFile(new File(file52222, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
            copyFile(new File(file52222, "last_recovery"), new File("/data/log/recovery.log"));
            file = RECOVERY_RESCUEPARTY_FILE;
            if (file.exists()) {
                try {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                } catch (IOException e7) {
                    Log.e(TAG, "IOException with rescueparty_log :", e7);
                }
                try {
                    if (randomAccessFile.length() > 524288) {
                        randomAccessFile.setLength(524288L);
                    }
                    randomAccessFile.close();
                    randomAccessFile.close();
                    copyFile(new File(RECOVERY_DIR, "rescueparty_log"), new File("/data/log/rescueparty_log"));
                } finally {
                }
            }
            zExists = BLOCK_MAP_FILE.exists();
            if (!zExists) {
                File file7 = UNCRYPT_PACKAGE_FILE;
                if (file7.exists()) {
                    try {
                        textFile2 = FileUtils.readTextFile(file7, 0, null);
                    } catch (IOException e8) {
                        Log.e(TAG, "Error reading uncrypt file", e8);
                    }
                    if (textFile2 != null && textFile2.startsWith("/data")) {
                        if (UNCRYPT_PACKAGE_FILE.delete()) {
                            Log.i(TAG, "Deleted: " + textFile2);
                        } else {
                            Log.e(TAG, "Can't delete: " + textFile2);
                        }
                    }
                }
            }
            Log.i(TAG, "copy sudden_reset_log to /data/log/");
            File file62222 = RECOVERY_DIR;
            file2 = new File(file62222, SUDDEN_RESET_LAST_KMSG_NAME);
            if (file2.exists()) {
                copyFile(file2, new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
            }
            file3 = new File(TMP_RECOVERY_LOG_PATH);
            if (file3.exists()) {
                copyFile(file3, new File(LAST_CACHE_SUDDEN_RESET_LOG_PATH));
                copyFile(new File("/proc/last_kmsg"), new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
                if (file3.delete()) {
                    Log.i(TAG, "Deleted: /efs/recovery/tmp_recovery.log");
                } else {
                    Log.e(TAG, "Can't delete: /efs/recovery/tmp_recovery.log");
                }
            }
            list = file62222.list();
            for (i = 0; list != null && i < list.length; i++) {
                if (list[i].startsWith(LAST_PREFIX) && !list[i].equals(LAST_INSTALL_PATH) && ((!zExists || !list[i].equals(BLOCK_MAP_FILE.getName())) && ((!zExists || !list[i].equals(UNCRYPT_PACKAGE_FILE.getName())) && !list[i].equals(RECOVERY_RESCUEPARTY_FILE.getName()) && !list[i].equals(COMMAND_FILE.getName())))) {
                    recursiveDelete(new File(RECOVERY_DIR, list[i]));
                }
            }
            return textFile;
        }
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
            boolean zRequestLskf = this.mService.requestLskf(str, intentSender);
            Log.i(TAG, TextUtils.formatSimple("LSKF Request isValid = %b", Boolean.valueOf(zRequestLskf)));
            return zRequestLskf;
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

    private static void copyFile(File file, File file2) throws Throwable {
        Throwable th;
        FileChannel channel;
        FileChannel channel2;
        FileChannel fileChannel = null;
        try {
            try {
                try {
                    channel2 = new FileInputStream(file).getChannel();
                    try {
                        channel = new FileOutputStream(file2).getChannel();
                    } catch (ErrnoException e) {
                        e = e;
                        channel = null;
                    } catch (IOException e2) {
                        e = e2;
                        channel = null;
                    } catch (Throwable th2) {
                        th = th2;
                        channel = null;
                    }
                } catch (IOException e3) {
                    Log.e(TAG, "copyFile: Error close FileChannel ", e3);
                }
            } catch (ErrnoException e4) {
                e = e4;
                channel = null;
            } catch (IOException e5) {
                e = e5;
                channel = null;
            } catch (Throwable th3) {
                th = th3;
                channel = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
        try {
            channel.transferFrom(channel2, 0L, channel2.size());
            Os.chmod(file2.getPath(), 416);
            Os.chown(file2.getPath(), 1000, 1007);
            if (channel2 != null) {
                channel2.close();
            }
            if (channel != null) {
                channel.close();
            }
        } catch (ErrnoException e6) {
            e = e6;
            fileChannel = channel2;
            Log.e(TAG, "copyFile: Error chmod recovery logs", e);
            if (fileChannel != null) {
                fileChannel.close();
            }
            if (channel != null) {
                channel.close();
            }
            Log.i(TAG, "copyFile: " + file + " -> " + file2);
        } catch (IOException e7) {
            e = e7;
            fileChannel = channel2;
            Log.e(TAG, "copyFile: Error copy recovery logs", e);
            if (fileChannel != null) {
                fileChannel.close();
            }
            if (channel != null) {
                channel.close();
            }
            Log.i(TAG, "copyFile: " + file + " -> " + file2);
        } catch (Throwable th5) {
            th = th5;
            fileChannel = channel2;
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e8) {
                    Log.e(TAG, "copyFile: Error close FileChannel ", e8);
                    throw th;
                }
            }
            if (channel == null) {
                throw th;
            }
            channel.close();
            throw th;
        }
        Log.i(TAG, "copyFile: " + file + " -> " + file2);
    }
}
