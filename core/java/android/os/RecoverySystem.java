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
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audio.Enums;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.RestrictionPolicy;
import android.sec.enterprise.auditlog.AuditEvents;
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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
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
import java.util.zip.ZipInputStream;
import libcore.io.Streams;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

/* loaded from: classes3.dex */
public class RecoverySystem {
  private static final String ACTION_EUICC_FACTORY_RESET =
      "com.android.internal.action.EUICC_FACTORY_RESET";
  private static final String ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS =
      "com.android.internal.action.EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS";
  public static final File BLOCK_BACKUP_FILE;
  public static final File BLOCK_MAP_FILE;
  private static File COMMAND_FILE = null;
  private static final long DEFAULT_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 30000;
  private static final long DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 45000;
  private static final File DEFAULT_KEYSTORE = new File("/system/etc/security/otacerts.zip");
  private static final String LAST_CACHE_SUDDEN_RESET_LOG_PATH =
      "/data/log/recovery_last_sudden_reset.log";
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

  @SystemApi public static final int RESUME_ON_REBOOT_REBOOT_ERROR_INVALID_PACKAGE_NAME = 2000;

  @SystemApi public static final int RESUME_ON_REBOOT_REBOOT_ERROR_LSKF_NOT_CAPTURED = 3000;
  public static final int RESUME_ON_REBOOT_REBOOT_ERROR_NONE = 0;

  @SystemApi
  public static final int RESUME_ON_REBOOT_REBOOT_ERROR_PROVIDER_PREPARATION_FAILURE = 5000;

  @SystemApi public static final int RESUME_ON_REBOOT_REBOOT_ERROR_SLOT_MISMATCH = 4000;

  @SystemApi public static final int RESUME_ON_REBOOT_REBOOT_ERROR_UNSPECIFIED = 1000;
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

  public @interface ResumeOnRebootRebootErrorCode {}

  static {
    File file = new File("/cache/recovery");
    RECOVERY_DIR = file;
    LOG_FILE = new File(file, "log");
    COMMAND_FILE = new File(file, "command");
    BLOCK_BACKUP_FILE = new File(file, "corrupted_blocks");
    mShutdownIsInProgressLock = new Object();
    mShutdownIsInProgress = false;
    RECOVERY_RESCUEPARTY_FILE = new File(file, "rescueparty_log");
    BLOCK_MAP_FILE = new File(file, "block.map");
    UNCRYPT_PACKAGE_FILE = new File(file, "uncrypt_file");
    UNCRYPT_STATUS_FILE = new File(file, "uncrypt_status");
    sRequestLock = new Object();
  }

  private static HashSet<X509Certificate> getTrustedCerts(File keystore)
      throws IOException, GeneralSecurityException {
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
          Log.m96e(TAG, "!@RecoverySystem failed to get zipEntry");
        } else {
          InputStream inputStream = zip.getInputStream(entry);
          if (inputStream == null) {
            Log.m96e(TAG, "!@RecoverySystem failed to get inputStream");
          } else {
            byte[] bytes = new byte[8];
            int len = inputStream.read(bytes);
            if (len <= 0) {
              Log.m96e(TAG, "!@RecoverySystem failed to read super_used_size");
              inputStream.close();
            } else {
              String str = new String(bytes, StandardCharsets.UTF_8);
              Log.m98i(TAG, "!@RecoverySystem super_used_size: " + str);
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
      Log.m97e(TAG, "!@RecoverySystem IOException when reading package files", e);
      return 0L;
    }
  }

  public static void verifyPackage(
      File packageFile, ProgressListener listener, File deviceCertsZipFile)
      throws IOException, GeneralSecurityException {
    byte[] eocd;
    long fileLen = packageFile.length();
    RandomAccessFile raf = new RandomAccessFile(packageFile, "r");
    try {
      long startTimeMillis = System.currentTimeMillis();
      if (listener != null) {
        listener.onProgress(0);
      }
      raf.seek(fileLen - 6);
      byte[] footer = new byte[6];
      raf.readFully(footer);
      if (footer[2] != -1 || footer[3] != -1) {
        throw new SignatureException("no signature in file (no footer)");
      }
      int commentSize = (footer[4] & 255) | ((footer[5] & 255) << 8);
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
      PKCS7 block =
          new PKCS7(
              new ByteArrayInputStream(eocd2, (commentSize + 22) - signatureStart, signatureStart));
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
      HashSet<X509Certificate> trusted =
          getTrustedCerts(deviceCertsZipFile == null ? DEFAULT_KEYSTORE : deviceCertsZipFile);
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
      SignerInfo verifyResult =
          block.verify(
              signerInfo,
              new InputStream(
                  fileLen,
                  commentSize,
                  startTimeMillis,
                  raf,
                  listener) { // from class: android.os.RecoverySystem.1
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
                  this.val$fileLen = fileLen;
                  this.val$commentSize = commentSize;
                  this.val$startTimeMillis = startTimeMillis;
                  this.val$raf = raf;
                  this.val$listenerForInner = listener;
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
                  long j = this.soFar;
                  long j2 = size + j;
                  long j3 = this.toRead;
                  if (j2 > j3) {
                    size = (int) (j3 - j);
                  }
                  int read = this.val$raf.read(b2, off, size);
                  this.soFar += read;
                  if (this.val$listenerForInner != null) {
                    long now = System.currentTimeMillis();
                    int p = (int) ((this.soFar * 100) / this.toRead);
                    if (p > this.lastPercent
                        && now - this.lastPublishTime
                            > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                      this.lastPercent = p;
                      this.lastPublishTime = now;
                      this.val$listenerForInner.onProgress(p);
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
      raf.close();
      if (!readAndVerifyPackageCompatibilityEntry(packageFile)) {
        throw new SignatureException("package compatibility verification failed");
      }
    } catch (Throwable th) {
      raf.close();
      throw th;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:11:0x0052, code lost:

     throw new java.io.IOException("invalid entry size (" + r4 + ") in the compatibility file");
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private static boolean verifyPackageCompatibility(InputStream inputStream) throws IOException {
    ArrayList<String> list = new ArrayList<>();
    ZipInputStream zis = new ZipInputStream(inputStream);
    while (true) {
      ZipEntry entry = zis.getNextEntry();
      if (entry != null) {
        long entrySize = entry.getSize();
        if (entrySize > 2147483647L || entrySize < 0) {
          break;
        }
        byte[] bytes = new byte[(int) entrySize];
        Streams.readFully(zis, bytes);
        list.add(new String(bytes, StandardCharsets.UTF_8));
      } else {
        if (list.isEmpty()) {
          throw new IOException("no entries found in the compatibility file");
        }
        return VintfObject.verify((String[]) list.toArray(new String[list.size()])) == 0;
      }
    }
  }

  private static boolean readAndVerifyPackageCompatibilityEntry(File packageFile)
      throws IOException {
    ZipFile zip = new ZipFile(packageFile);
    try {
      ZipEntry entry = zip.getEntry("compatibility.zip");
      if (entry != null) {
        InputStream inputStream = zip.getInputStream(entry);
        boolean verifyPackageCompatibility = verifyPackageCompatibility(inputStream);
        zip.close();
        return verifyPackageCompatibility;
      }
      zip.close();
      return true;
    } catch (Throwable th) {
      try {
        zip.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }

  @SystemApi
  public static boolean verifyPackageCompatibility(File compatibilityFile) throws IOException {
    InputStream inputStream = new FileInputStream(compatibilityFile);
    try {
      boolean verifyPackageCompatibility = verifyPackageCompatibility(inputStream);
      inputStream.close();
      return verifyPackageCompatibility;
    } catch (Throwable th) {
      try {
        inputStream.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }

  @SystemApi
  public static void processPackage(
      Context context, File packageFile, ProgressListener listener, Handler handler)
      throws IOException {
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
      progressListener = new IRecoverySystemProgressListenerStubC23592(progressHandler, listener);
    }
    if (!rs.uncrypt(filename, progressListener)) {
      throw new IOException("process package failed");
    }
  }

  /* renamed from: android.os.RecoverySystem$2 */
  class IRecoverySystemProgressListenerStubC23592 extends IRecoverySystemProgressListener.Stub {
    int lastProgress = 0;
    long lastPublishTime = System.currentTimeMillis();
    final /* synthetic */ ProgressListener val$listener;
    final /* synthetic */ Handler val$progressHandler;

    IRecoverySystemProgressListenerStubC23592(Handler handler, ProgressListener progressListener) {
      this.val$progressHandler = handler;
      this.val$listener = progressListener;
    }

    @Override // android.os.IRecoverySystemProgressListener
    public void onProgress(final int progress) {
      final long now = System.currentTimeMillis();
      this.val$progressHandler.post(
          new Runnable() { // from class: android.os.RecoverySystem.2.1
            @Override // java.lang.Runnable
            public void run() {
              if (progress > IRecoverySystemProgressListenerStubC23592.this.lastProgress
                  && now - IRecoverySystemProgressListenerStubC23592.this.lastPublishTime
                      > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                IRecoverySystemProgressListenerStubC23592.this.lastProgress = progress;
                IRecoverySystemProgressListenerStubC23592.this.lastPublishTime = now;
                IRecoverySystemProgressListenerStubC23592.this.val$listener.onProgress(progress);
              }
            }
          });
    }
  }

  @SystemApi
  public static void processPackage(Context context, File packageFile, ProgressListener listener)
      throws IOException {
    processPackage(context, packageFile, listener, null);
  }

  public static void installPackage(Context context, File packageFile) throws IOException {
    installPackage(context, packageFile, false);
  }

  /* JADX WARN: Removed duplicated region for block: B:70:0x0290 A[Catch: all -> 0x0303, TryCatch #3 {, blocks: (B:4:0x0005, B:6:0x0060, B:8:0x006e, B:9:0x0079, B:10:0x0087, B:15:0x008e, B:19:0x0097, B:20:0x00a5, B:21:0x00a6, B:24:0x00c1, B:26:0x00cb, B:28:0x00e9, B:29:0x00d1, B:32:0x00f3, B:33:0x00f7, B:34:0x00f8, B:36:0x014a, B:37:0x015e, B:39:0x016a, B:41:0x01b6, B:42:0x01ca, B:45:0x01d1, B:49:0x01f4, B:51:0x01fa, B:100:0x0202, B:57:0x0224, B:59:0x022d, B:61:0x0234, B:66:0x0252, B:68:0x0278, B:70:0x0290, B:72:0x02a4, B:73:0x02b8, B:74:0x02c2, B:78:0x0271, B:91:0x026b, B:90:0x0268, B:97:0x02c3, B:98:0x02d3, B:53:0x020a, B:112:0x02ee, B:111:0x02eb, B:115:0x02f4, B:116:0x0302, B:121:0x017f, B:123:0x018b, B:124:0x01a0, B:23:0x00ab), top: B:3:0x0005, inners: #1 }] */
  @SystemApi
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static void installPackage(Context context, File packageFile, boolean processed)
      throws IOException {
    String command;
    String command2;
    Context context2 = context;
    synchronized (sRequestLock) {
      LOG_FILE.delete();
      File file = UNCRYPT_PACKAGE_FILE;
      file.delete();
      String filename = packageFile.getCanonicalPath();
      Log.m102w(TAG, "!!! REBOOTING TO INSTALL " + filename + " !!!");
      boolean securityUpdate = filename.endsWith("_s.zip");
      boolean fileInData = filename.startsWith("/data/");
      String orgFilenameArg = "--update_org_package=" + filename + "\n";
      long superUsedSectors = parseSuperUsedSize(packageFile);
      if (superUsedSectors > 0) {
        StorageManager storageManager =
            (StorageManager) context2.getSystemService(StorageManager.class);
        if (!storageManager.shrinkDataDdp(superUsedSectors)) {
          Log.m96e(TAG, "[DDP] Failed to shrink /data to expand super partition");
          throw new IOException("Failed to shrink /data to expand super partition");
        }
        SystemProperties.set("persist.sys.ddp.super_used_size", Long.toString(superUsedSectors));
      }
      if (fileInData) {
        if (processed) {
          if (!BLOCK_MAP_FILE.exists()) {
            Log.m96e(
                TAG,
                "Package claimed to have been processed but failed to find the block map file.");
            throw new IOException("Failed to find block map file");
          }
        } else {
          FileWriter uncryptFile = new FileWriter(file);
          try {
            uncryptFile.write(filename + "\n");
            uncryptFile.close();
            if (!file.setReadable(true, false) || !file.setWritable(true, false)) {
              Log.m96e(TAG, "Error setting permission for " + file);
            }
            BLOCK_MAP_FILE.delete();
          } catch (Throwable th) {
            uncryptFile.close();
            throw th;
          }
        }
        filename = "@/cache/recovery/block.map";
      }
      String filenameArg = "--update_package=" + filename + "\n";
      String localeArg = "--locale=" + Locale.getDefault().toLanguageTag() + "\n";
      String command3 = filenameArg + localeArg;
      if (securityUpdate) {
        command3 = command3 + "--security\n";
      }
      if ("com.ws.dm".equals(context.getPackageName())) {
        command = command3 + "--carry_out=att_fota\n";
      } else if ("com.samsung.sdm.sdmviewer".equals(context.getPackageName())) {
        command = command3 + "--carry_out=vzw_fota\n";
      } else {
        command = command3 + "--carry_out=open_fota\n";
      }
      if (!fileInData) {
        command2 = command;
      } else {
        command2 = command + orgFilenameArg;
      }
      COMMAND_FILE.delete();
      int retry_count = 3;
      while (true) {
        try {
          RandomAccessFile commandFile = new RandomAccessFile(COMMAND_FILE, "rwd");
          try {
            commandFile.writeBytes(command2);
            Log.m98i(TAG, "!@RecoverySystem before fsync syscall!!");
            commandFile.getFD().sync();
            Log.m98i(TAG, "!@RecoverySystem after fsync syscall!!");
            commandFile.close();
            retry_count--;
            if (COMMAND_FILE.exists()) {
              Log.m98i(TAG, "COMMAND_FILE is already exist!!");
              break;
            }
            Log.m98i(TAG, "Retry_count : " + retry_count);
            if (retry_count <= 0) {
              break;
            } else {
              context2 = context;
            }
          } catch (Throwable th2) {
            try {
              commandFile.close();
              throw th2;
            } catch (Throwable th3) {
              try {
                th2.addSuppressed(th3);
                throw th2;
              } catch (IOException e) {
                e = e;
                Log.m97e(TAG, "IOException when writing command cause:", e);
                throw new IOException("failed to create command file");
              }
            }
          }
        } catch (IOException e2) {
          e = e2;
        }
      }
      if (!COMMAND_FILE.exists()) {
        Log.m98i(TAG, "!@ command file absent, throw exception");
        throw new IOException("failed to create command file");
      }
      Log.m94d(TAG, "!@[reset tracking] installPackage write to recovery_cause");
      try {
        FileWriter rebootDescr = new FileWriter("/sys/class/sec/sec_debug/recovery_cause");
        try {
          rebootDescr.write("RecoverySystem installPackage: " + command2);
          try {
            rebootDescr.close();
          } catch (IOException e3) {
            e = e3;
            Log.m97e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e);
            PowerManager pm = (PowerManager) context2.getSystemService("power");
            String reason = PowerManager.REBOOT_RECOVERY_UPDATE;
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK)) {}
            pm.reboot(reason);
            throw new IOException("Reboot failed (no permissions?)");
          }
        } catch (Throwable th4) {
          try {
            rebootDescr.close();
            throw th4;
          } catch (Throwable th5) {
            try {
              th4.addSuppressed(th5);
              throw th4;
            } catch (IOException e4) {
              e = e4;
              Log.m97e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e);
              PowerManager pm2 = (PowerManager) context2.getSystemService("power");
              String reason2 = PowerManager.REBOOT_RECOVERY_UPDATE;
              if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK)) {}
              pm2.reboot(reason2);
              throw new IOException("Reboot failed (no permissions?)");
            }
          }
        }
      } catch (IOException e5) {
        e = e5;
      }
      PowerManager pm22 = (PowerManager) context2.getSystemService("power");
      String reason22 = PowerManager.REBOOT_RECOVERY_UPDATE;
      if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK)) {
        DisplayManager dm = (DisplayManager) context2.getSystemService(DisplayManager.class);
        if (dm.getDisplay(0).getState() != 2) {
          reason22 = PowerManager.REBOOT_RECOVERY_UPDATE + ",quiescent";
        }
      }
      pm22.reboot(reason22);
      throw new IOException("Reboot failed (no permissions?)");
    }
  }

  @SystemApi
  public static void prepareForUnattendedUpdate(
      Context context, String updateToken, IntentSender intentSender) throws IOException {
    if (updateToken == null) {
      throw new NullPointerException("updateToken == null");
    }
    KeyguardManager keyguardManager =
        (KeyguardManager) context.getSystemService(KeyguardManager.class);
    if (keyguardManager == null || !keyguardManager.isDeviceSecure()) {
      throw new IOException(
          "Failed to request LSKF because the device doesn't have a lock screen. ");
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
  public static void rebootAndApply(Context context, String updateToken, String reason)
      throws IOException {
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
  public static int rebootAndApply(Context context, String reason, boolean slotSwitch)
      throws IOException {
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

  public static void rebootWipeUserData(
      Context context, boolean shutdown, String reason, boolean force) throws IOException {
    rebootWipeUserData(context, shutdown, reason, force, false);
  }

  public static void rebootWipeUserData(
      Context context, boolean shutdown, String reason, boolean force, boolean wipeEuicc)
      throws IOException {
    rebootWipeUserData(context, shutdown, reason, force, wipeEuicc, null);
  }

  public static void rebootWipeUserData(
      Context context,
      boolean shutdown,
      String reason,
      boolean force,
      boolean wipeEuicc,
      String extraCmd)
      throws IOException {
    String shutdownArg;
    String timeStamp;
    String extraCmdArg;
    RestrictionPolicy restrPol;
    Log.m98i(TAG, "rebootWipeUserData++");
    if (force
        || (restrPol = EnterpriseDeviceManager.getInstance().getRestrictionPolicy()) == null
        || restrPol.isFactoryResetAllowed()) {
      UserManager um = (UserManager) context.getSystemService("user");
      if (!force && um.hasUserRestriction(UserManager.DISALLOW_FACTORY_RESET)) {
        AuditLog.log(
            5,
            1,
            false,
            Process.myPid(),
            TAG,
            AuditEvents.AUDIT_WIPING_DATA_IS_NOT_ALLOWED_FOR_THIS_USER);
        throw new SecurityException("Wiping data is not allowed for this user.");
      }
      final ConditionVariable condition = new ConditionVariable();
      HandlerThread hthread = new HandlerThread(TAG);
      Log.m98i(TAG, "rebootWipeUserData: run handler " + hthread);
      hthread.start();
      Log.m98i(TAG, "rebootWipeUserData: sendOrderedBroadcastAsUser");
      Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR_NOTIFICATION);
      intent.addFlags(285212672);
      context.sendOrderedBroadcastAsUser(
          intent,
          UserHandle.SYSTEM,
          Manifest.permission.MASTER_CLEAR,
          new BroadcastReceiver() { // from class: android.os.RecoverySystem.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent2) {
              Log.m98i(RecoverySystem.TAG, "rebootWipeUserData: onReceive");
              ConditionVariable.this.open();
            }
          },
          new Handler(hthread.getLooper()),
          0,
          null,
          null);
      Log.m98i(TAG, "rebootWipeUserData: wait intent to complete");
      condition.block();
      Log.m98i(TAG, "rebootWipeUserData: continue..");
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
        timeStamp = null;
      } else {
        String timeStamp2 =
            DateFormat.format("yyyy-MM-ddTHH:mm:ssZ", System.currentTimeMillis()).toString();
        String reasonArg = "--reason=" + sanitizeArg(reason + "," + timeStamp2);
        timeStamp = reasonArg;
      }
      String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
      if (TextUtils.isEmpty(extraCmd)) {
        extraCmdArg = "";
      } else {
        String extraCmdArg2 = "--" + sanitizeArg(extraCmd);
        extraCmdArg = extraCmdArg2;
      }
      try {
        AuditLog.log(5, 1, true, Process.myPid(), TAG, AuditEvents.AUDIT_STARTING_USER_DATA_WIPE);
        Log.m94d(
            TAG,
            "!@[RecoverySystem] rebootWipeUserData: wipeDataArg:["
                + RECOVERY_WIPE_DATA_COMMAND
                + "], extraCmdArg:["
                + extraCmdArg
                + NavigationBarInflaterView.SIZE_MOD_END);
        bootCommand(
            context, shutdownArg, RECOVERY_WIPE_DATA_COMMAND, extraCmdArg, timeStamp, localeArg);
        return;
      } catch (IOException ioE) {
        AuditLog.log(
            5,
            1,
            false,
            Process.myPid(),
            TAG,
            String.format(AuditEvents.AUDIT_FAILED_TO_WIPE_USER_DATA, ioE.getMessage()));
        throw ioE;
      }
    }
    AuditLog.log(
        5,
        1,
        false,
        Process.myPid(),
        TAG,
        AuditEvents.AUDIT_WIPING_DATA_IS_NOT_ALLOWED_FOR_THIS_USER);
    throw new SecurityException("Wiping data is not allowed due to restriction policy.");
  }

  public static boolean wipeEuiccData(Context context, String packageName) {
    ContentResolver cr = context.getContentResolver();
    if (Settings.Global.getInt(cr, Settings.Global.EUICC_PROVISIONED, 0) == 0) {
      Log.m94d(TAG, "Skipping eUICC wipe/retain as it is not provisioned");
      return true;
    }
    EuiccManager euiccManager = (EuiccManager) context.getSystemService(Context.EUICC_SERVICE);
    if (euiccManager == null || !euiccManager.isEnabled()) {
      return false;
    }
    final CountDownLatch euiccFactoryResetLatch = new CountDownLatch(1);
    final AtomicBoolean wipingSucceeded = new AtomicBoolean(false);
    BroadcastReceiver euiccWipeFinishReceiver =
        new BroadcastReceiver() { // from class: android.os.RecoverySystem.4
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if (RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
              if (getResultCode() != 0) {
                int detailedCode =
                    intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                Log.m96e(
                    RecoverySystem.TAG, "Error wiping euicc data, Detailed code = " + detailedCode);
              } else {
                Log.m94d(RecoverySystem.TAG, "Successfully wiped euicc data.");
                wipingSucceeded.set(true);
              }
              euiccFactoryResetLatch.countDown();
            }
          }
        };
    Intent intent = new Intent(ACTION_EUICC_FACTORY_RESET);
    intent.setPackage(packageName);
    PendingIntent callbackIntent =
        PendingIntent.getBroadcastAsUser(
            context, 0, intent, Enums.AUDIO_FORMAT_DTS_HD, UserHandle.SYSTEM);
    IntentFilter filterConsent = new IntentFilter();
    filterConsent.addAction(ACTION_EUICC_FACTORY_RESET);
    HandlerThread euiccHandlerThread = new HandlerThread("euiccWipeFinishReceiverThread");
    euiccHandlerThread.start();
    Handler euiccHandler = new Handler(euiccHandlerThread.getLooper());
    context
        .getApplicationContext()
        .registerReceiver(euiccWipeFinishReceiver, filterConsent, null, euiccHandler);
    euiccManager.eraseSubscriptions(callbackIntent);
    try {
      try {
        long waitingTimeMillis =
            Settings.Global.getLong(
                context.getContentResolver(),
                Settings.Global.EUICC_FACTORY_RESET_TIMEOUT_MILLIS,
                30000L);
        if (waitingTimeMillis < 5000) {
          waitingTimeMillis = 5000;
        } else if (waitingTimeMillis > 60000) {
          waitingTimeMillis = 60000;
        }
        try {
          try {
            if (euiccFactoryResetLatch.await(waitingTimeMillis, TimeUnit.MILLISECONDS)) {
              context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
              return wipingSucceeded.get();
            }
            Log.m96e(TAG, "Timeout wiping eUICC data.");
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            return false;
          } catch (InterruptedException e) {
            e = e;
            Thread.currentThread().interrupt();
            Log.m97e(TAG, "Wiping eUICC data interrupted", e);
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            return false;
          }
        } catch (Throwable th) {
          e = th;
          context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
          throw e;
        }
      } catch (InterruptedException e2) {
        e = e2;
      } catch (Throwable th2) {
        e = th2;
        context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
        throw e;
      }
    } catch (InterruptedException e3) {
      e = e3;
    } catch (Throwable th3) {
      e = th3;
    }
  }

  private static void removeEuiccInvisibleSubs(Context context, EuiccManager euiccManager) {
    ContentResolver cr = context.getContentResolver();
    if (Settings.Global.getInt(cr, Settings.Global.EUICC_PROVISIONED, 0) == 0) {
      Log.m98i(TAG, "Skip removing eUICC invisible profiles as it is not provisioned.");
      return;
    }
    if (euiccManager == null || !euiccManager.isEnabled()) {
      Log.m98i(TAG, "Skip removing eUICC invisible profiles as eUICC manager is not available.");
      return;
    }
    SubscriptionManager subscriptionManager =
        (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
    List<SubscriptionInfo> availableSubs = subscriptionManager.getAvailableSubscriptionInfoList();
    if (availableSubs == null || availableSubs.isEmpty()) {
      Log.m98i(TAG, "Skip removing eUICC invisible profiles as no available profiles found.");
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

  private static boolean removeEuiccInvisibleSubs(
      Context context, List<SubscriptionInfo> subscriptionInfos, EuiccManager euiccManager) {
    if (subscriptionInfos != null && !subscriptionInfos.isEmpty()) {
      final CountDownLatch removeSubsLatch = new CountDownLatch(subscriptionInfos.size());
      final AtomicInteger removedSubsCount = new AtomicInteger(0);
      BroadcastReceiver removeEuiccSubsReceiver =
          new BroadcastReceiver() { // from class: android.os.RecoverySystem.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
              if (RecoverySystem.ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS.equals(
                  intent.getAction())) {
                if (getResultCode() != 0) {
                  int detailedCode =
                      intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                  Log.m96e(
                      RecoverySystem.TAG,
                      "Error removing euicc opportunistic profile, Detailed code = "
                          + detailedCode);
                } else {
                  Log.m96e(RecoverySystem.TAG, "Successfully remove euicc opportunistic profile.");
                  removedSubsCount.incrementAndGet();
                }
                removeSubsLatch.countDown();
              }
            }
          };
      Intent intent = new Intent(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
      intent.setPackage("android");
      PendingIntent callbackIntent =
          PendingIntent.getBroadcastAsUser(
              context, 0, intent, Enums.AUDIO_FORMAT_DTS_HD, UserHandle.SYSTEM);
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
      HandlerThread euiccHandlerThread = new HandlerThread("euiccRemovingSubsReceiverThread");
      euiccHandlerThread.start();
      Handler euiccHandler = new Handler(euiccHandlerThread.getLooper());
      context
          .getApplicationContext()
          .registerReceiver(removeEuiccSubsReceiver, intentFilter, null, euiccHandler);
      for (SubscriptionInfo subscriptionInfo : subscriptionInfos) {
        Log.m98i(
            TAG,
            "Remove invisible subscription "
                + subscriptionInfo.getSubscriptionId()
                + " from card "
                + subscriptionInfo.getCardId());
        euiccManager
            .createForCardId(subscriptionInfo.getCardId())
            .deleteSubscription(subscriptionInfo.getSubscriptionId(), callbackIntent);
      }
      try {
        long waitingTimeMillis =
            Settings.Global.getLong(
                context.getContentResolver(),
                Settings.Global.EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS,
                DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS);
        if (waitingTimeMillis < MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
          waitingTimeMillis = MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
        } else if (waitingTimeMillis > MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
          waitingTimeMillis = MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
        }
        if (!removeSubsLatch.await(waitingTimeMillis, TimeUnit.MILLISECONDS)) {
          Log.m96e(TAG, "Timeout removing invisible euicc profiles.");
          return false;
        }
        context.getApplicationContext().unregisterReceiver(removeEuiccSubsReceiver);
        euiccHandlerThread.quit();
        return removedSubsCount.get() == subscriptionInfos.size();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        Log.m97e(TAG, "Removing invisible euicc profiles interrupted", e);
        return false;
      } finally {
        context.getApplicationContext().unregisterReceiver(removeEuiccSubsReceiver);
        euiccHandlerThread.quit();
      }
    }
    Log.m98i(TAG, "There are no eUICC invisible profiles needed to be removed.");
    return true;
  }

  public static void rebootPromptAndWipeUserData(Context context, String reason)
      throws IOException {
    boolean checkpointing = false;
    IVold vold = null;
    try {
      vold = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
      if (vold != null) {
        checkpointing = vold.needsCheckpoint();
      } else {
        Log.m102w(TAG, "Failed to get vold");
      }
    } catch (Exception e) {
      Log.m102w(TAG, "Failed to check for checkpointing");
    }
    if (checkpointing) {
      try {
        vold.abortChanges("rescueparty", false);
        Log.m98i(TAG, "Rescue Party requested wipe. Aborting update");
        return;
      } catch (Exception e2) {
        Log.m98i(TAG, "Rescue Party requested wipe. Rebooting instead.");
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
        Log.m102w(TAG, "Failed to get vold");
      }
    } catch (Exception e) {
      Log.m102w(TAG, "Failed to check for checkpointing");
    }
    if (checkpointing) {
      try {
        vold.abortChanges("rescueparty", false);
        Log.m98i(TAG, "Rescue Party requested wipe. Aborting update");
        return;
      } catch (Exception e2) {
        Log.m98i(TAG, "Rescue Party requested wipe. Rebooting instead.");
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
  public static void rebootWipeAb(Context context, File packageFile, String reason)
      throws IOException {
    String reasonArg = null;
    if (!TextUtils.isEmpty(reason)) {
      reasonArg = "--reason=" + sanitizeArg(reason);
    }
    String filename = packageFile.getCanonicalPath();
    String filenameArg = "--wipe_package=" + filename;
    String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
    bootCommand(context, "--wipe_ab", filenameArg, reasonArg, localeArg);
  }

  public static void rebootWipeCustomerPartition(Context context, String arg, String reason)
      throws IOException {
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
      Log.m97e(TAG, "StringIndexOutOfBoundsException when splitting recovery cause:", e);
      return null;
    }
  }

  private static void bootCommand(Context context, String... args) throws IOException {
    synchronized (mShutdownIsInProgressLock) {
      if (mShutdownIsInProgress.booleanValue()) {
        return;
      }
      mShutdownIsInProgress = true;
      Log.m98i(TAG, "!@[RecoverySystem] bootCommand: " + Arrays.toString(args));
      boolean isForcedWipe =
          Arrays.toString(args) != null
              && Arrays.toString(args).contains(RECOVERY_WIPE_DATA_COMMAND);
      synchronized (sRequestLock) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append("!@[RecoverySystem] ");
        for (StackTraceElement st : stackTraceElements) {
          sb.append(st.toString() + "\n");
        }
        Log.m98i(TAG, sb.toString());
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
            Log.m98i(TAG, "!@[RecoverySystem] bootCommand: before fsync syscall!!");
            command.getFD().sync();
            Log.m98i(TAG, "!@[RecoverySystem] bootCommand: after fsync syscall!!");
            command.close();
            retryCount--;
            if (COMMAND_FILE.exists()) {
              Log.m98i(TAG, "COMMAND_FILE is created!!");
              break;
            } else {
              Log.m98i(TAG, "retryCount : " + retryCount);
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
          Log.m98i(TAG, "!@[RecoverySystem] bootCommand: command file absent, throw exception");
          throw new IOException("Reboot failed (unable to create command file)");
        }
        if (isForcedWipe) {
          deleteSecrets();
        }
        PowerManager pm = (PowerManager) context.getSystemService("power");
        String reason = SystemProperties.get("persist.sys.reboot.reason");
        if ("nvrecovery".equals(reason)) {
          Log.m98i(TAG, "FactoryTest ->nvrecovery ");
          pm.reboot("nvrecovery");
        } else if (Context.DOWNLOAD_SERVICE.equals(reason)) {
          Log.m98i(TAG, "FactoryTest ->download ");
          pm.reboot(Context.DOWNLOAD_SERVICE);
        } else {
          Log.m94d(TAG, "calling pm.reboot");
          if (recovery_cause == null) {
            recovery_cause = "bootCommand()";
          }
          Log.m94d(
              TAG,
              "!@[RecoverySystem] bootCommand: [reset tracking] write to recovery_cause : "
                  + recovery_cause);
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
            Log.m97e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e);
          }
          pm.reboot("recovery");
        }
        throw new IOException("Reboot failed (no permissions?)");
      }
    }
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:118:0x008e -> B:26:0x00b5). Please report as a decompilation issue!!! */
  public static String handleAftermath(Context context) {
    synchronized (mShutdownIsInProgressLock) {
      if (mShutdownIsInProgress.booleanValue()) {
        Log.m98i(TAG, "!@[RecoverySystem] handleAftermath: disabled, as shutdown in progress");
        return null;
      }
      Log.m98i(TAG, "!@[RecoverySystem] handleAftermath");
      String log = null;
      try {
        log = FileUtils.readTextFile(LOG_FILE, -65536, "...\n");
      } catch (FileNotFoundException e) {
        Log.m98i(TAG, "No recovery log file");
      } catch (IOException e2) {
        Log.m97e(TAG, "Error reading recovery log", e2);
      }
      FileInputStream reFis = null;
      try {
        try {
          try {
            File reFile = new File("/cache/recovery/last_recovery_mode");
            reFis = new FileInputStream(reFile);
            byte[] mode = new byte[21];
            int bytes = reFis.read(mode);
            if (bytes > 0) {
              String lastRecoveryMode = new String(mode, 0, bytes, StandardCharsets.UTF_8);
              Log.m98i(TAG, "last_recovery_mode : " + lastRecoveryMode);
              SystemProperties.set(LAST_RECOVERY_MODE, lastRecoveryMode);
            }
            if (!reFile.delete()) {
              Log.m98i(TAG, "Failed to delete /cache/recovery/last_recovery_mode");
            }
            reFis.close();
          } catch (Throwable th) {
            if (reFis != null) {
              try {
                reFis.close();
              } catch (IOException e3) {
                Log.m97e(TAG, "IOException when close last_recovery_mode file:", e3);
              }
            }
            throw th;
          }
        } catch (FileNotFoundException e4) {
          Log.m97e(TAG, "FileNotFoundException when open /cache/recovery/last_recovery_mode:", e4);
          if (reFis != null) {
            reFis.close();
          }
        } catch (IOException e5) {
          Log.m97e(TAG, "IOException when read /cache/recovery/last_recovery_mode:", e5);
          if (reFis != null) {
            reFis.close();
          }
        }
      } catch (IOException e6) {
        Log.m97e(TAG, "IOException when close last_recovery_mode file:", e6);
      }
      File file = RECOVERY_DIR;
      copyFile(new File(file, "last_history"), new File("/data/log/recovery_history.log"));
      copyFile(
          new File(file, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
      copyFile(new File(file, "last_recovery"), new File("/data/log/recovery.log"));
      File file2 = RECOVERY_RESCUEPARTY_FILE;
      if (file2.exists()) {
        try {
          RandomAccessFile raf = new RandomAccessFile(file2, "rw");
          try {
            if (raf.length() > 524288) {
              raf.setLength(524288L);
            }
            raf.close();
            raf.close();
          } finally {
          }
        } catch (IOException e7) {
          Log.m97e(TAG, "IOException with rescueparty_log :", e7);
        }
        copyFile(new File(RECOVERY_DIR, "rescueparty_log"), new File("/data/log/rescueparty_log"));
      }
      boolean reservePackage = BLOCK_MAP_FILE.exists();
      if (!reservePackage) {
        File file3 = UNCRYPT_PACKAGE_FILE;
        if (file3.exists()) {
          String filename = null;
          try {
            filename = FileUtils.readTextFile(file3, 0, null);
          } catch (IOException e8) {
            Log.m97e(TAG, "Error reading uncrypt file", e8);
          }
          if (filename != null && filename.startsWith("/data")) {
            if (UNCRYPT_PACKAGE_FILE.delete()) {
              Log.m98i(TAG, "Deleted: " + filename);
            } else {
              Log.m96e(TAG, "Can't delete: " + filename);
            }
          }
        }
      }
      File file4 = BLOCK_BACKUP_FILE;
      if (file4.exists()) {
        copyFile(file4, new File("/data/log/corrupted_blocks"));
      }
      Log.m98i(TAG, "copy sudden_reset_log to /data/log/");
      File file5 = RECOVERY_DIR;
      File tmpSuddenResetLastKmsg = new File(file5, SUDDEN_RESET_LAST_KMSG_NAME);
      if (tmpSuddenResetLastKmsg.exists()) {
        copyFile(tmpSuddenResetLastKmsg, new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
      }
      File tmpRecoveryLogFile = new File(TMP_RECOVERY_LOG_PATH);
      if (tmpRecoveryLogFile.exists()) {
        copyFile(tmpRecoveryLogFile, new File(LAST_CACHE_SUDDEN_RESET_LOG_PATH));
        copyFile(new File("/proc/last_kmsg"), new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
        if (tmpRecoveryLogFile.delete()) {
          Log.m98i(TAG, "Deleted: /efs/recovery/tmp_recovery.log");
        } else {
          Log.m96e(TAG, "Can't delete: /efs/recovery/tmp_recovery.log");
        }
      }
      String[] names = file5.list();
      for (int i = 0; names != null && i < names.length; i++) {
        if (!names[i].startsWith(LAST_PREFIX)
            && !names[i].equals(LAST_INSTALL_PATH)
            && ((!reservePackage || !names[i].equals(BLOCK_MAP_FILE.getName()))
                && ((!reservePackage || !names[i].equals(UNCRYPT_PACKAGE_FILE.getName()))
                    && !names[i].equals(RECOVERY_RESCUEPARTY_FILE.getName())
                    && !names[i].equals(COMMAND_FILE.getName())))) {
          recursiveDelete(new File(RECOVERY_DIR, names[i]));
        }
      }
      return log;
    }
  }

  private static void deleteSecrets() {
    Log.m102w(TAG, "deleteSecrets");
    try {
      AndroidKeyStoreMaintenance.deleteAllKeys();
    } catch (KeyStoreException e) {
      Log.wtf(TAG, "Failed to delete all keys from keystore.", e);
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
      Log.m96e(TAG, "Can't delete: " + name);
    } else {
      Log.m98i(TAG, "Deleted: " + name);
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
    try {
      return this.mService.requestLskf(packageName, sender);
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

  private int rebootWithLskf(String packageName, String reason, boolean slotSwitch)
      throws IOException {
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
          } catch (IOException e) {
            Log.m97e(TAG, "copyFile: Error copy recovery logs", e);
            if (inputChannel != null) {
              inputChannel.close();
            }
            if (outputChannel != null) {
              outputChannel.close();
            }
          }
        } catch (ErrnoException e2) {
          Log.m97e(TAG, "copyFile: Error chmod recovery logs", e2);
          if (inputChannel != null) {
            inputChannel.close();
          }
          if (outputChannel != null) {
            outputChannel.close();
          }
        }
      } catch (IOException e3) {
        Log.m97e(TAG, "copyFile: Error close FileChannel ", e3);
      }
      str = "copyFile: " + source + " -> " + dest;
      Log.m98i(TAG, str);
    } catch (Throwable th) {
      if (inputChannel != null) {
        try {
          inputChannel.close();
        } catch (IOException e4) {
          Log.m97e(TAG, str, e4);
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
