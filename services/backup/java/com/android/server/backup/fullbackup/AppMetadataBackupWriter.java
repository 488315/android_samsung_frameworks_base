package com.android.server.backup.fullbackup;

import android.app.backup.FullBackup;
import android.app.backup.FullBackupDataOutput;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import android.os.Environment;
import android.os.IInstalld;
import android.util.StringBuilderPrinter;
import com.android.internal.util.Preconditions;
import com.android.server.backup.UserBackupManagerService;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/* loaded from: classes.dex */
public class AppMetadataBackupWriter {
  public final FullBackupDataOutput mOutput;
  public final PackageManager mPackageManager;

  public AppMetadataBackupWriter(
      FullBackupDataOutput fullBackupDataOutput, PackageManager packageManager) {
    this.mOutput = fullBackupDataOutput;
    this.mPackageManager = packageManager;
  }

  public void backupManifest(PackageInfo packageInfo, File file, File file2, boolean z) {
    backupManifest(packageInfo, file, file2, null, null, z);
  }

  public void backupManifest(
      PackageInfo packageInfo, File file, File file2, String str, String str2, boolean z) {
    byte[] manifestBytes = getManifestBytes(packageInfo, z);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write(manifestBytes);
    fileOutputStream.close();
    file.setLastModified(0L);
    FullBackup.backupToTar(
        packageInfo.packageName,
        str,
        str2,
        file2.getAbsolutePath(),
        file.getAbsolutePath(),
        this.mOutput);
  }

  public final byte[] getManifestBytes(PackageInfo packageInfo, boolean z) {
    String[] strArr;
    String str = packageInfo.packageName;
    StringBuilder sb = new StringBuilder(IInstalld.FLAG_USE_QUOTA);
    StringBuilderPrinter stringBuilderPrinter = new StringBuilderPrinter(sb);
    stringBuilderPrinter.println(Integer.toString(1));
    stringBuilderPrinter.println(str);
    stringBuilderPrinter.println(Long.toString(packageInfo.getLongVersionCode()));
    stringBuilderPrinter.println(Integer.toString(Build.VERSION.SDK_INT));
    String installerPackageName = this.mPackageManager.getInstallerPackageName(str);
    if (installerPackageName == null) {
      installerPackageName = "";
    }
    stringBuilderPrinter.println(installerPackageName);
    stringBuilderPrinter.println(z ? "1" : "0");
    if (UserBackupManagerService.mSplitBackupFlag.booleanValue()) {
      stringBuilderPrinter.println(
          (!z || (strArr = packageInfo.splitNames) == null)
              ? "0"
              : Integer.toString(strArr.length));
    }
    SigningInfo signingInfo = packageInfo.signingInfo;
    if (signingInfo == null) {
      stringBuilderPrinter.println("0");
    } else {
      Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
      stringBuilderPrinter.println(Integer.toString(apkContentsSigners.length));
      for (Signature signature : apkContentsSigners) {
        stringBuilderPrinter.println(signature.toCharsString());
      }
    }
    return sb.toString().getBytes();
  }

  public void backupWidget(PackageInfo packageInfo, File file, File file2, byte[] bArr) {
    Preconditions.checkArgument(bArr.length > 0, "Can't backup widget with no data.");
    String str = packageInfo.packageName;
    BufferedOutputStream bufferedOutputStream =
        new BufferedOutputStream(new FileOutputStream(file));
    DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
    bufferedOutputStream.write(getMetadataBytes(str));
    writeWidgetData(dataOutputStream, bArr);
    bufferedOutputStream.flush();
    dataOutputStream.close();
    file.setLastModified(0L);
    FullBackup.backupToTar(
        str,
        (String) null,
        (String) null,
        file2.getAbsolutePath(),
        file.getAbsolutePath(),
        this.mOutput);
  }

  public final byte[] getMetadataBytes(String str) {
    StringBuilder sb = new StringBuilder(512);
    StringBuilderPrinter stringBuilderPrinter = new StringBuilderPrinter(sb);
    stringBuilderPrinter.println(Integer.toString(1));
    stringBuilderPrinter.println(str);
    return sb.toString().getBytes();
  }

  public final void writeWidgetData(DataOutputStream dataOutputStream, byte[] bArr) {
    dataOutputStream.writeInt(33549569);
    dataOutputStream.writeInt(bArr.length);
    dataOutputStream.write(bArr);
  }

  public void backupApk(PackageInfo packageInfo) {
    String[] splitCodePaths;
    String baseCodePath = packageInfo.applicationInfo.getBaseCodePath();
    String parent = new File(baseCodePath).getParent();
    FullBackup.backupToTar(
        packageInfo.packageName, "a", (String) null, parent, baseCodePath, this.mOutput);
    if (!UserBackupManagerService.mSplitBackupFlag.booleanValue()
        || (splitCodePaths = packageInfo.applicationInfo.getSplitCodePaths()) == null) {
      return;
    }
    for (String str : splitCodePaths) {
      FullBackup.backupToTar(
          packageInfo.packageName, "a", (String) null, parent, str, this.mOutput);
    }
  }

  public void backupObb(int i, PackageInfo packageInfo) {
    File[] listFiles;
    File file =
        new Environment.UserEnvironment(i)
            .buildExternalStorageAppObbDirs(packageInfo.packageName)[0];
    if (file == null || (listFiles = file.listFiles()) == null) {
      return;
    }
    String absolutePath = file.getAbsolutePath();
    for (File file2 : listFiles) {
      FullBackup.backupToTar(
          packageInfo.packageName,
          "obb",
          (String) null,
          absolutePath,
          file2.getAbsolutePath(),
          this.mOutput);
    }
  }
}
