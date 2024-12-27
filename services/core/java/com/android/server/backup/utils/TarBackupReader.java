package com.android.server.backup.utils;

import android.app.backup.IBackupManagerMonitor;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.backup.FileMetadata;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.restore.FullRestoreEngine$$ExternalSyntheticLambda0;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TarBackupReader {
    public final BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    public final FullRestoreEngine$$ExternalSyntheticLambda0 mBytesReadListener;
    public final InputStream mInputStream;
    public byte[] mWidgetData = null;

    public TarBackupReader(
            InputStream inputStream,
            FullRestoreEngine$$ExternalSyntheticLambda0 fullRestoreEngine$$ExternalSyntheticLambda0,
            IBackupManagerMonitor iBackupManagerMonitor) {
        this.mInputStream = inputStream;
        this.mBytesReadListener = fullRestoreEngine$$ExternalSyntheticLambda0;
        this.mBackupManagerMonitorEventSender =
                new BackupManagerMonitorEventSender(iBackupManagerMonitor);
    }

    public static int extractLine(byte[] bArr, int i, String[] strArr) {
        int length = bArr.length;
        if (i >= length) {
            throw new IOException("Incomplete data");
        }
        int i2 = i;
        while (i2 < length && bArr[i2] != 10) {
            i2++;
        }
        strArr[0] = new String(bArr, i, i2 - i);
        return i2 + 1;
    }

    public static long extractRadix(int i, int i2, int i3, byte[] bArr) {
        int i4 = i2 + i;
        long j = 0;
        while (i < i4) {
            byte b = bArr[i];
            if (b == 0 || b == 32) {
                break;
            }
            if (b < 48 || b > i3 + 47) {
                throw new IOException(
                        "Invalid number in header: '" + ((char) b) + "' for radix " + i3);
            }
            j = (b - 48) + (i3 * j);
            i++;
        }
        return j;
    }

    public static int readExactly(InputStream inputStream, byte[] bArr, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read <= 0) {
                break;
            }
            i2 += read;
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x01bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.backup.restore.RestorePolicy chooseRestorePolicy(
            android.content.pm.PackageManager r19,
            boolean r20,
            com.android.server.backup.FileMetadata r21,
            android.content.pm.Signature[] r22,
            int r23,
            com.android.server.backup.utils.BackupEligibilityRules r24,
            android.content.Context r25,
            boolean r26) {
        /*
            Method dump skipped, instructions count: 477
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.utils.TarBackupReader.chooseRestorePolicy(android.content.pm.PackageManager,"
                    + " boolean, com.android.server.backup.FileMetadata,"
                    + " android.content.pm.Signature[], int,"
                    + " com.android.server.backup.utils.BackupEligibilityRules,"
                    + " android.content.Context,"
                    + " boolean):com.android.server.backup.restore.RestorePolicy");
    }

    public final void getMonitor() {
        IBackupManagerMonitor iBackupManagerMonitor =
                this.mBackupManagerMonitorEventSender.mMonitor;
    }

    public final byte[] getWidgetData() {
        return this.mWidgetData;
    }

    public final Signature[] readAppManifestAndReturnSignatures(FileMetadata fileMetadata) {
        BackupManagerMonitorEventSender backupManagerMonitorEventSender =
                this.mBackupManagerMonitorEventSender;
        long j = fileMetadata.size;
        if (j > 65536) {
            throw new IOException("Restore manifest too big; corrupt? size=" + fileMetadata.size);
        }
        byte[] bArr = new byte[(int) j];
        if (readExactly(this.mInputStream, bArr, r6) != fileMetadata.size) {
            throw new IOException("Unexpected EOF in manifest");
        }
        getClass();
        String[] strArr = new String[1];
        try {
            int extractLine = extractLine(bArr, 0, strArr);
            int parseInt = Integer.parseInt(strArr[0]);
            if (parseInt == 1) {
                int extractLine2 = extractLine(bArr, extractLine, strArr);
                String str = strArr[0];
                if (str.equals(fileMetadata.packageName)) {
                    int extractLine3 = extractLine(bArr, extractLine2, strArr);
                    fileMetadata.version = Integer.parseInt(strArr[0]);
                    int extractLine4 = extractLine(bArr, extractLine3, strArr);
                    Integer.parseInt(strArr[0]);
                    int extractLine5 = extractLine(bArr, extractLine4, strArr);
                    fileMetadata.installerPackageName = strArr[0].length() > 0 ? strArr[0] : null;
                    int extractLine6 = extractLine(bArr, extractLine5, strArr);
                    fileMetadata.hasApk = strArr[0].equals("1");
                    if (UserBackupManagerService.mSplitRestoreFlag.booleanValue()) {
                        extractLine6 = extractLine(bArr, extractLine6, strArr);
                        fileMetadata.splitCount = Integer.parseInt(strArr[0]);
                    }
                    int extractLine7 = extractLine(bArr, extractLine6, strArr);
                    int parseInt2 = Integer.parseInt(strArr[0]);
                    if (parseInt2 > 0) {
                        Signature[] signatureArr = new Signature[parseInt2];
                        for (int i = 0; i < parseInt2; i++) {
                            extractLine7 = extractLine(bArr, extractLine7, strArr);
                            signatureArr[i] = new Signature(strArr[0]);
                        }
                        return signatureArr;
                    }
                    Slog.i(
                            "BackupManagerService",
                            "Missing signature on backed-up package " + fileMetadata.packageName);
                    backupManagerMonitorEventSender.monitorEvent(
                            42,
                            null,
                            3,
                            BackupManagerMonitorEventSender.putMonitoringExtra(
                                    "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME",
                                    fileMetadata.packageName));
                } else {
                    Slog.i(
                            "BackupManagerService",
                            "Expected package "
                                    + fileMetadata.packageName
                                    + " but restore manifest claims "
                                    + str);
                    Bundle putMonitoringExtra =
                            BackupManagerMonitorEventSender.putMonitoringExtra(
                                    "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME",
                                    fileMetadata.packageName);
                    putMonitoringExtra.putString(
                            "android.app.backup.extra.LOG_MANIFEST_PACKAGE_NAME", str);
                    backupManagerMonitorEventSender.monitorEvent(43, null, 3, putMonitoringExtra);
                }
            } else {
                Slog.i(
                        "BackupManagerService",
                        "Unknown restore manifest version "
                                + parseInt
                                + " for package "
                                + fileMetadata.packageName);
                Bundle putMonitoringExtra2 =
                        BackupManagerMonitorEventSender.putMonitoringExtra(
                                "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME",
                                fileMetadata.packageName);
                putMonitoringExtra2.putInt(
                        "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", parseInt);
                backupManagerMonitorEventSender.monitorEvent(44, null, 3, putMonitoringExtra2);
            }
        } catch (NumberFormatException unused) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Corrupt restore manifest for package "),
                    fileMetadata.packageName,
                    "BackupManagerService");
            backupManagerMonitorEventSender.monitorEvent(
                    46,
                    null,
                    3,
                    BackupManagerMonitorEventSender.putMonitoringExtra(
                            "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME",
                            fileMetadata.packageName));
        } catch (IllegalArgumentException e) {
            Slog.w("BackupManagerService", e.getMessage());
        }
        return null;
    }

    public final void readMetadata(FileMetadata fileMetadata) {
        long j = fileMetadata.size;
        if (j > 65536) {
            throw new IOException("Metadata too big; corrupt? size=" + fileMetadata.size);
        }
        int i = (int) j;
        byte[] bArr = new byte[i];
        if (readExactly(this.mInputStream, bArr, i) != fileMetadata.size) {
            throw new IOException("Unexpected EOF in widget data");
        }
        getClass();
        String[] strArr = new String[1];
        int extractLine = extractLine(bArr, 0, strArr);
        int parseInt = Integer.parseInt(strArr[0]);
        BackupManagerMonitorEventSender backupManagerMonitorEventSender =
                this.mBackupManagerMonitorEventSender;
        if (parseInt != 1) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    parseInt, "Unsupported metadata version ", "BackupManagerService");
            Bundle putMonitoringExtra =
                    BackupManagerMonitorEventSender.putMonitoringExtra(
                            "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME",
                            fileMetadata.packageName);
            putMonitoringExtra.putInt(
                    "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", parseInt);
            backupManagerMonitorEventSender.monitorEvent(48, null, 3, putMonitoringExtra);
            return;
        }
        int extractLine2 = extractLine(bArr, extractLine, strArr);
        String str = strArr[0];
        if (!fileMetadata.packageName.equals(str)) {
            Slog.w(
                    "BackupManagerService",
                    "Metadata mismatch: package "
                            + fileMetadata.packageName
                            + " but widget data for "
                            + str);
            Bundle putMonitoringExtra2 =
                    BackupManagerMonitorEventSender.putMonitoringExtra(
                            "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME",
                            fileMetadata.packageName);
            putMonitoringExtra2.putString("android.app.backup.extra.LOG_WIDGET_PACKAGE_NAME", str);
            backupManagerMonitorEventSender.monitorEvent(47, null, 3, putMonitoringExtra2);
            return;
        }
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(bArr, extractLine2, i - extractLine2);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        while (byteArrayInputStream.available() > 0) {
            int readInt = dataInputStream.readInt();
            int readInt2 = dataInputStream.readInt();
            if (readInt2 > 65536) {
                StringBuilder sb = new StringBuilder("Datum ");
                BatteryService$$ExternalSyntheticOutline0.m(
                        readInt, sb, " too big; corrupt? size=");
                sb.append(fileMetadata.size);
                throw new IOException(sb.toString());
            }
            if (readInt != 33549569) {
                StringBuilder sb2 = new StringBuilder("Ignoring metadata blob ");
                BatteryService$$ExternalSyntheticOutline0.m(readInt, sb2, " for ");
                sb2.append(fileMetadata.packageName);
                Slog.i("BackupManagerService", sb2.toString());
                dataInputStream.skipBytes(readInt2);
            } else {
                byte[] bArr2 = new byte[readInt2];
                this.mWidgetData = bArr2;
                dataInputStream.read(bArr2);
            }
        }
    }

    public final void readPaxExtendedHeader(FileMetadata fileMetadata) {
        long j = fileMetadata.size;
        if (j > 32768) {
            Slog.w(
                    "BackupManagerService",
                    "Suspiciously large pax header size " + fileMetadata.size + " - aborting");
            throw new IOException("Sanity failure: pax header size " + fileMetadata.size);
        }
        int i = ((int) ((j + 511) >> 9)) * 512;
        byte[] bArr = new byte[i];
        if (readExactly(this.mInputStream, bArr, i) < i) {
            throw new IOException("Unable to read full pax header");
        }
        getClass();
        int i2 = (int) fileMetadata.size;
        int i3 = 0;
        do {
            int i4 = i3 + 1;
            while (i4 < i2 && bArr[i4] != 32) {
                i4++;
            }
            if (i4 >= i2) {
                throw new IOException("Invalid pax data");
            }
            int i5 = i4 + 1;
            i3 += (int) extractRadix(i3, i4 - i3, 10, bArr);
            int i6 = i3 - 1;
            int i7 = i4 + 2;
            while (bArr[i7] != 61 && i7 <= i6) {
                i7++;
            }
            if (i7 > i6) {
                throw new IOException("Invalid pax declaration");
            }
            String str = new String(bArr, i5, i7 - i5, "UTF-8");
            String str2 = new String(bArr, i7 + 1, (i6 - i7) - 1, "UTF-8");
            if ("path".equals(str)) {
                fileMetadata.path = str2;
            } else if ("size".equals(str)) {
                fileMetadata.size = Long.parseLong(str2);
            } else {
                HermesService$3$$ExternalSyntheticOutline0.m(
                        i5, "Unhandled pax key: ", "BackupManagerService");
            }
        } while (i3 < i2);
    }

    public final FileMetadata readTarHeaders() {
        boolean z;
        byte[] bArr = new byte[512];
        int readExactly = readExactly(this.mInputStream, bArr, 512);
        FullRestoreEngine$$ExternalSyntheticLambda0 fullRestoreEngine$$ExternalSyntheticLambda0 =
                this.mBytesReadListener;
        if (readExactly == 0) {
            z = false;
        } else {
            if (readExactly < 512) {
                throw new IOException("Unable to read full block header");
            }
            fullRestoreEngine$$ExternalSyntheticLambda0.getClass();
            z = true;
        }
        if (!z) {
            return null;
        }
        try {
            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.size = extractRadix(124, 12, 8, bArr);
            fileMetadata.mtime = extractRadix(136, 12, 8, bArr);
            fileMetadata.mode = extractRadix(100, 8, 8, bArr);
            int i = 345;
            while (i < 500 && bArr[i] != 0) {
                i++;
            }
            fileMetadata.path =
                    new String(
                            bArr,
                            FrameworkStatsLog.MAGNIFICATION_USAGE_REPORTED,
                            i - FrameworkStatsLog.MAGNIFICATION_USAGE_REPORTED,
                            "US-ASCII");
            int i2 = 0;
            while (i2 < 100 && bArr[i2] != 0) {
                i2++;
            }
            String str = new String(bArr, 0, i2, "US-ASCII");
            if (str.length() > 0) {
                if (fileMetadata.path.length() > 0) {
                    fileMetadata.path += '/';
                }
                fileMetadata.path += str;
            }
            byte b = bArr[156];
            if (b == 120) {
                readPaxExtendedHeader(fileMetadata);
                int readExactly2 = readExactly(this.mInputStream, bArr, 512);
                if (readExactly2 == 0) {
                    throw new IOException("Bad or missing pax header");
                }
                if (readExactly2 < 512) {
                    throw new IOException("Unable to read full block header");
                }
                fullRestoreEngine$$ExternalSyntheticLambda0.getClass();
                b = bArr[156];
            }
            if (b == 0) {
                return null;
            }
            if (b == 48) {
                fileMetadata.type = 1;
            } else {
                if (b != 53) {
                    Slog.e("BackupManagerService", "Unknown tar entity type: " + ((int) b));
                    throw new IOException("Unknown entity type " + ((int) b));
                }
                fileMetadata.type = 2;
                if (fileMetadata.size != 0) {
                    Slog.w("BackupManagerService", "Directory entry with nonzero size in header");
                    fileMetadata.size = 0L;
                }
            }
            if ("shared/".regionMatches(0, fileMetadata.path, 0, 7)) {
                fileMetadata.path = fileMetadata.path.substring(7);
                fileMetadata.packageName = "com.android.sharedstoragebackup";
                fileMetadata.domain = "shared";
                Slog.i("BackupManagerService", "File in shared storage: " + fileMetadata.path);
            } else if ("apps/".regionMatches(0, fileMetadata.path, 0, 5)) {
                String substring = fileMetadata.path.substring(5);
                fileMetadata.path = substring;
                int indexOf = substring.indexOf(47);
                if (indexOf < 0) {
                    throw new IOException("Illegal semantic path in " + fileMetadata.path);
                }
                fileMetadata.packageName = fileMetadata.path.substring(0, indexOf);
                String substring2 = fileMetadata.path.substring(indexOf + 1);
                fileMetadata.path = substring2;
                if (!substring2.equals("_manifest") && !fileMetadata.path.equals("_meta")) {
                    int indexOf2 = fileMetadata.path.indexOf(47);
                    if (indexOf2 < 0) {
                        throw new IOException(
                                "Illegal semantic path in non-manifest " + fileMetadata.path);
                    }
                    fileMetadata.domain = fileMetadata.path.substring(0, indexOf2);
                    fileMetadata.path = fileMetadata.path.substring(indexOf2 + 1);
                }
            }
            return fileMetadata;
        } catch (IOException e) {
            Slog.e("BackupManagerService", "Parse error in header: " + e.getMessage());
            throw e;
        }
    }

    public final void skipTarPadding(long j) {
        long j2 = (j + 512) % 512;
        if (j2 > 0) {
            int i = 512 - ((int) j2);
            if (readExactly(this.mInputStream, new byte[i], i) != i) {
                throw new IOException("Unexpected EOF in padding");
            }
            getClass();
        }
    }
}
