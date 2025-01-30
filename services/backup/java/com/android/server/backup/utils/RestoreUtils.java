package com.android.server.backup.utils;

import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.backup.FileMetadata;
import com.android.server.backup.restore.RestoreDeleteObserver;
import com.android.server.backup.restore.RestorePolicy;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class RestoreUtils {
  public static boolean mPrivilegeApp = false;

  public static void setPrivilegeApp(boolean z) {
    mPrivilegeApp = z;
  }

  public static int createSession(Context context, String str) {
    PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
    PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
    sessionParams.setInstallerPackageName(str);
    try {
      return packageInstaller.createSession(sessionParams);
    } catch (Exception e) {
      Slog.d("BackupManagerService", "Exception in session id created" + e);
      return 0;
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r7v2, types: [android.content.pm.PackageInstaller] */
  /* JADX WARN: Type inference failed for: r7v6, types: [android.content.pm.PackageInstaller$Session] */
  public static boolean writeSession(
      Context context,
      InputStream inputStream,
      FileMetadata fileMetadata,
      String str,
      BytesReadListener bytesReadListener,
      int i) {
    OutputStream openWrite;
    PackageInstaller.Session packageInstaller = context.getPackageManager().getPackageInstaller();
    try {
      try {
        packageInstaller = packageInstaller.openSession(i);
        try {
          openWrite = packageInstaller.openWrite(fileMetadata.path, 0L, fileMetadata.size);
        } catch (InterruptedIOException e) {
          Slog.e("BackupManagerService", " InterruptedIOException in apkStream.close()" + e);
        }
        try {
          byte[] bArr = new byte[32768];
          long j = fileMetadata.size;
          while (j > 0) {
            long j2 = 32768;
            if (j2 >= j) {
              j2 = j;
            }
            int read = inputStream.read(bArr, 0, (int) j2);
            if (read >= 0) {
              bytesReadListener.onBytesRead(read);
            }
            openWrite.write(bArr, 0, read);
            j -= read;
          }
          packageInstaller.fsync(openWrite);
          if (openWrite != null) {
            openWrite.close();
          }
          if (packageInstaller != 0) {
            packageInstaller.close();
          }
          return true;
        } catch (Throwable th) {
          if (openWrite != null) {
            try {
              openWrite.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
          }
          throw th;
        }
      } finally {
      }
    } catch (Exception e2) {
      Slog.e("BackupManagerService", " Exception in writeSession " + e2);
      e2.printStackTrace();
      return false;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:23:0x013b A[Catch: IOException -> 0x0157, TRY_LEAVE, TryCatch #0 {IOException -> 0x0157, blocks: (B:3:0x0019, B:9:0x003a, B:11:0x0047, B:18:0x0056, B:20:0x0064, B:23:0x013b, B:27:0x0085, B:29:0x0097, B:31:0x009b, B:33:0x00b8, B:36:0x00d4, B:38:0x00de, B:40:0x00e4, B:42:0x0101, B:43:0x011c, B:54:0x0153, B:55:0x0156, B:5:0x0027, B:8:0x0037, B:47:0x0151, B:52:0x014e), top: B:2:0x0019, inners: #3, #4 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static boolean installApkSplitSupport(
      InputStream inputStream,
      Context context,
      RestoreDeleteObserver restoreDeleteObserver,
      HashMap hashMap,
      HashMap hashMap2,
      FileMetadata fileMetadata,
      String str,
      BytesReadListener bytesReadListener,
      int i,
      int i2) {
    boolean z;
    PackageInfo packageInfoAsUser;
    Slog.d("BackupManagerService", "Installing from backup: " + fileMetadata.packageName);
    try {
      LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
      PackageManager packageManager = context.getPackageManager();
      PackageInstaller packageInstaller = packageManager.getPackageInstaller();
      try {
        PackageInstaller.Session openSession = packageInstaller.openSession(i2);
        try {
          Slog.e("BackupManagerService", "Entering Session Commit");
          openSession.commit(localIntentReceiver.getIntentSender());
          openSession.close();
          Intent result = localIntentReceiver.getResult();
          boolean z2 = true;
          if (result.getIntExtra("android.content.pm.extra.STATUS", 1) != 0) {
            return hashMap2.get(fileMetadata.packageName) == RestorePolicy.ACCEPT;
          }
          String stringExtra = result.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
          if (!stringExtra.equals(fileMetadata.packageName)) {
            Slog.w(
                "BackupManagerService",
                "Restore stream claimed to include apk for "
                    + fileMetadata.packageName
                    + " but apk was really "
                    + stringExtra);
          } else {
            try {
              packageInfoAsUser =
                  packageManager.getPackageInfoAsUser(fileMetadata.packageName, 134217728, i);
            } catch (PackageManager.NameNotFoundException unused) {
              Slog.w(
                  "BackupManagerService",
                  "Install of package "
                      + fileMetadata.packageName
                      + " succeeded but now not found");
            }
            if ((packageInfoAsUser.applicationInfo.flags & 32768) == 0 && !mPrivilegeApp) {
              Slog.w(
                  "BackupManagerService",
                  "Restore stream contains apk of package "
                      + fileMetadata.packageName
                      + " but it disallows backup/restore");
            } else {
              if (BackupEligibilityRules.forBackup(
                      packageManager,
                      (PackageManagerInternal)
                          LocalServices.getService(PackageManagerInternal.class),
                      i,
                      context)
                  .signaturesMatch(
                      (Signature[]) hashMap.get(fileMetadata.packageName), packageInfoAsUser)) {
                if (!UserHandle.isCore(packageInfoAsUser.applicationInfo.uid)
                    || packageInfoAsUser.applicationInfo.backupAgentName != null) {
                  z = true;
                  z2 = false;
                  if (z2) {
                    restoreDeleteObserver.reset();
                    packageManager.deletePackage(stringExtra, restoreDeleteObserver, 0);
                    restoreDeleteObserver.waitForCompletion();
                  }
                  return z;
                }
                Slog.w(
                    "BackupManagerService",
                    "Installed app "
                        + fileMetadata.packageName
                        + " has restricted uid and no agent");
              } else {
                Slog.w(
                    "BackupManagerService",
                    "Installed app "
                        + fileMetadata.packageName
                        + " signatures do not match restore manifest");
              }
            }
            z = false;
            z2 = false;
            if (z2) {}
            return z;
          }
          z = false;
          if (z2) {}
          return z;
        } finally {
        }
      } catch (Exception e) {
        packageInstaller.abandonSession(i2);
        throw e;
      }
    } catch (IOException e2) {
      Slog.e("BackupManagerService", "Unable to transcribe restored apk for install" + e2);
      return false;
    }
  }

  /* JADX WARN: Can't wrap try/catch for region: R(10:4|5|6|(3:7|8|9)|(5:(10:10|11|12|13|14|15|16|(7:19|(1:21)(1:28)|22|(1:24)(1:27)|25|26|17)|30|(1:32))|38|39|40|(2:42|(2:44|45)(2:47|48))(6:49|(2:51|52)(9:60|61|(3:69|70|(7:72|(1:76)|67|68|(3:55|56|57)|58|59)(6:77|78|68|(0)|58|59))(1:65)|66|67|68|(0)|58|59)|53|(0)|58|59))|33|34|35|36|(2:(0)|(1:98))) */
  /* JADX WARN: Code restructure failed: missing block: B:86:0x01eb, code lost:

     r0 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:91:0x01f4, code lost:

     r4.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:93:?, code lost:

     throw r1;
  */
  /* JADX WARN: Code restructure failed: missing block: B:94:0x01f8, code lost:

     r0 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:96:0x01fa, code lost:

     r1.addSuppressed(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:97:0x01fd, code lost:

     throw r1;
  */
  /* JADX WARN: Code restructure failed: missing block: B:99:?, code lost:

     throw r1;
  */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Removed duplicated region for block: B:42:0x00dc A[Catch: IOException -> 0x020b, TryCatch #5 {IOException -> 0x020b, blocks: (B:3:0x001c, B:40:0x00cf, B:42:0x00dc, B:49:0x00ee, B:51:0x00fc, B:55:0x01da, B:61:0x011e, B:63:0x0130, B:65:0x0134, B:69:0x0151, B:72:0x0171, B:74:0x017b, B:76:0x0181, B:77:0x019d, B:79:0x01ba), top: B:2:0x001c, inners: #6 }] */
  /* JADX WARN: Removed duplicated region for block: B:49:0x00ee A[Catch: IOException -> 0x020b, TryCatch #5 {IOException -> 0x020b, blocks: (B:3:0x001c, B:40:0x00cf, B:42:0x00dc, B:49:0x00ee, B:51:0x00fc, B:55:0x01da, B:61:0x011e, B:63:0x0130, B:65:0x0134, B:69:0x0151, B:72:0x0171, B:74:0x017b, B:76:0x0181, B:77:0x019d, B:79:0x01ba), top: B:2:0x001c, inners: #6 }] */
  /* JADX WARN: Removed duplicated region for block: B:55:0x01da A[Catch: IOException -> 0x020b, TRY_LEAVE, TryCatch #5 {IOException -> 0x020b, blocks: (B:3:0x001c, B:40:0x00cf, B:42:0x00dc, B:49:0x00ee, B:51:0x00fc, B:55:0x01da, B:61:0x011e, B:63:0x0130, B:65:0x0134, B:69:0x0151, B:72:0x0171, B:74:0x017b, B:76:0x0181, B:77:0x019d, B:79:0x01ba), top: B:2:0x001c, inners: #6 }] */
  /* JADX WARN: Removed duplicated region for block: B:90:0x01f4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:99:? A[Catch: Exception -> 0x01fe, SYNTHETIC, TRY_LEAVE, TryCatch #8 {Exception -> 0x01fe, blocks: (B:97:0x01fd, B:96:0x01fa, B:91:0x01f4), top: B:88:0x01f2, inners: #11 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static boolean installApk(
      InputStream inputStream,
      Context context,
      RestoreDeleteObserver restoreDeleteObserver,
      HashMap hashMap,
      HashMap hashMap2,
      FileMetadata fileMetadata,
      String str,
      BytesReadListener bytesReadListener,
      int i) {
    int i2;
    int i3;
    Throwable th;
    PackageInstaller.Session session;
    PackageInstaller.Session session2;
    Intent result;
    boolean z;
    PackageInfo packageInfoAsUser;
    boolean z2;
    int i4 = i;
    Slog.d("BackupManagerService", "Installing from backup: " + fileMetadata.packageName);
    try {
      try {
        LocalIntentReceiver localIntentReceiver = new LocalIntentReceiver();
        PackageManager packageManager = context.getPackageManager();
        PackageInstaller packageInstaller = packageManager.getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setInstallerPackageName(str);
        int createSession = packageInstaller.createSession(sessionParams);
        try {
          PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
          try {
            i3 = createSession;
          } catch (InterruptedIOException e) {
            e = e;
            i3 = createSession;
          } catch (Throwable th2) {
            th = th2;
            i3 = createSession;
          }
          try {
            try {
              session2 = openSession;
              int i5 = 32768;
              try {
                OutputStream openWrite =
                    openSession.openWrite(fileMetadata.packageName, 0L, fileMetadata.size);
                try {
                  byte[] bArr = new byte[32768];
                  long j = fileMetadata.size;
                  while (j > 0) {
                    long j2 = i5;
                    if (j2 >= j) {
                      j2 = j;
                    }
                    int read = inputStream.read(bArr, 0, (int) j2);
                    if (read >= 0) {
                      bytesReadListener.onBytesRead(read);
                    }
                    openWrite.write(bArr, 0, read);
                    j -= read;
                    i5 = 32768;
                  }
                  if (openWrite != null) {
                    openWrite.close();
                  }
                } finally {
                }
              } catch (InterruptedIOException e2) {
                e = e2;
                try {
                  Slog.e(
                      "BackupManagerService", " InterruptedIOException in apkStream.close()" + e);
                  Slog.e("BackupManagerService", "Entering Session Commit");
                  session = session2;
                  session.commit(localIntentReceiver.getIntentSender());
                  session.close();
                  result = localIntentReceiver.getResult();
                  boolean z3 = true;
                  if (result.getIntExtra("android.content.pm.extra.STATUS", 1) != 0) {}
                } catch (Throwable th3) {
                  th = th3;
                  session = session2;
                  th = th;
                  try {
                    if (session == null) {}
                  } catch (Exception e3) {
                    e = e3;
                    i2 = i3;
                    packageInstaller.abandonSession(i2);
                    throw e;
                  }
                }
              } catch (Throwable th4) {
                th = th4;
                session = session2;
                if (session == null) {}
              }
            } catch (InterruptedIOException e4) {
              e = e4;
              session2 = openSession;
              Slog.e("BackupManagerService", " InterruptedIOException in apkStream.close()" + e);
              Slog.e("BackupManagerService", "Entering Session Commit");
              session = session2;
              session.commit(localIntentReceiver.getIntentSender());
              session.close();
              result = localIntentReceiver.getResult();
              boolean z32 = true;
              if (result.getIntExtra("android.content.pm.extra.STATUS", 1) != 0) {}
            } catch (Throwable th5) {
              th = th5;
              th = th;
              session = openSession;
              if (session == null) {}
            }
            session.close();
            result = localIntentReceiver.getResult();
            boolean z322 = true;
            if (result.getIntExtra("android.content.pm.extra.STATUS", 1) != 0) {
              return hashMap2.get(fileMetadata.packageName) == RestorePolicy.ACCEPT;
            }
            String stringExtra = result.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
            if (!stringExtra.equals(fileMetadata.packageName)) {
              Slog.w(
                  "BackupManagerService",
                  "Restore stream claimed to include apk for "
                      + fileMetadata.packageName
                      + " but apk was really "
                      + stringExtra);
              z = true;
            } else {
              try {
                packageInfoAsUser =
                    packageManager.getPackageInfoAsUser(fileMetadata.packageName, 134217728, i4);
              } catch (PackageManager.NameNotFoundException unused) {
                Slog.w(
                    "BackupManagerService",
                    "Install of package "
                        + fileMetadata.packageName
                        + " succeeded but now not found");
                z = false;
              }
              if ((packageInfoAsUser.applicationInfo.flags & 32768) == 0 && !mPrivilegeApp) {
                Slog.w(
                    "BackupManagerService",
                    "Restore stream contains apk of package "
                        + fileMetadata.packageName
                        + " but it disallows backup/restore");
              } else {
                if (BackupEligibilityRules.forBackup(
                        packageManager,
                        (PackageManagerInternal)
                            LocalServices.getService(PackageManagerInternal.class),
                        i4,
                        context)
                    .signaturesMatch(
                        (Signature[]) hashMap.get(fileMetadata.packageName), packageInfoAsUser)) {
                  if (UserHandle.isCore(packageInfoAsUser.applicationInfo.uid)
                      && packageInfoAsUser.applicationInfo.backupAgentName == null) {
                    Slog.w(
                        "BackupManagerService",
                        "Installed app "
                            + fileMetadata.packageName
                            + " has restricted uid and no agent");
                  }
                  z = false;
                  z2 = z322;
                  if (z) {
                    restoreDeleteObserver.reset();
                    packageManager.deletePackage(stringExtra, restoreDeleteObserver, 0);
                    restoreDeleteObserver.waitForCompletion();
                  }
                  return z2;
                }
                Slog.w(
                    "BackupManagerService",
                    "Installed app "
                        + fileMetadata.packageName
                        + " signatures do not match restore manifest");
                z = true;
                z322 = false;
                z2 = z322;
                if (z) {}
                return z2;
              }
              z322 = false;
              z = false;
              z2 = z322;
              if (z) {}
              return z2;
            }
            z2 = false;
            if (z) {}
            return z2;
          } catch (Exception e5) {
            e = e5;
            i2 = i3;
            packageInstaller.abandonSession(i2);
            throw e;
          }
          Slog.e("BackupManagerService", "Entering Session Commit");
          session = session2;
          session.commit(localIntentReceiver.getIntentSender());
        } catch (Exception e6) {
          e = e6;
          i2 = createSession;
        }
      } catch (IOException e7) {
        e = e7;
        i4 = 0;
        Slog.e("BackupManagerService", "Unable to transcribe restored apk for install" + e);
        return i4;
      }
    } catch (IOException e8) {
      e = e8;
      Slog.e("BackupManagerService", "Unable to transcribe restored apk for install" + e);
      return i4;
    }
  }

  public class LocalIntentReceiver {
    public IIntentSender.Stub mLocalSender;
    public final Object mLock;
    public Intent mResult;

    public LocalIntentReceiver() {
      this.mLock = new Object();
      this.mResult = null;
      this.mLocalSender =
          new IIntentSender
              .Stub() { // from class:
                        // com.android.server.backup.utils.RestoreUtils.LocalIntentReceiver.1
            public void send(
                int i,
                Intent intent,
                String str,
                IBinder iBinder,
                IIntentReceiver iIntentReceiver,
                String str2,
                Bundle bundle) {
              synchronized (LocalIntentReceiver.this.mLock) {
                LocalIntentReceiver.this.mResult = intent;
                LocalIntentReceiver.this.mLock.notifyAll();
              }
            }
          };
    }

    public IntentSender getIntentSender() {
      return new IntentSender(this.mLocalSender);
    }

    public Intent getResult() {
      Intent intent;
      synchronized (this.mLock) {
        while (true) {
          intent = this.mResult;
          if (intent == null) {
            try {
              this.mLock.wait();
            } catch (InterruptedException unused) {
            }
          }
        }
      }
      return intent;
    }
  }
}
