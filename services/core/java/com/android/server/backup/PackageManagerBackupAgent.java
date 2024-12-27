package com.android.server.backup;

import android.app.backup.BackupAgent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Slog;

import com.android.server.backup.utils.BackupEligibilityRules;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class PackageManagerBackupAgent extends BackupAgent {
    static final String ANCESTRAL_RECORD_KEY = "@ancestral_record@";
    static final int ANCESTRAL_RECORD_VERSION = 1;
    static final String GLOBAL_METADATA_KEY = "@meta@";
    static final String STATE_FILE_HEADER = "=state=";
    static final int STATE_FILE_VERSION = 2;
    public final List mAllPackages;
    public final HashSet mExisting;
    public boolean mHasMetadata;
    public final PackageManager mPackageManager;
    public HashMap mRestoredSignatures;
    public final HashMap mStateVersions;
    public int mStoredAncestralRecordVersion;
    public String mStoredIncrementalVersion;
    public int mStoredSdkVersion;
    public final int mUserId;

    public final class LegacyRestoreDataConsumer {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PackageManagerBackupAgent this$0;

        public /* synthetic */ LegacyRestoreDataConsumer(
                PackageManagerBackupAgent packageManagerBackupAgent, int i) {
            this.$r8$classId = i;
            this.this$0 = packageManagerBackupAgent;
        }
    }

    public final class Metadata {
        public final ArrayList sigHashes;
        public final long versionCode;

        public Metadata(long j, ArrayList arrayList) {
            this.versionCode = j;
            this.sigHashes = arrayList;
        }
    }

    public PackageManagerBackupAgent(PackageManager packageManager, int i) {
        this.mStateVersions = new HashMap();
        this.mExisting = new HashSet();
        this.mPackageManager = packageManager;
        this.mAllPackages = null;
        this.mRestoredSignatures = null;
        this.mHasMetadata = false;
        this.mStoredSdkVersion = Build.VERSION.SDK_INT;
        this.mStoredIncrementalVersion = Build.VERSION.INCREMENTAL;
        this.mUserId = i;
    }

    public PackageManagerBackupAgent(
            PackageManager packageManager, int i, BackupEligibilityRules backupEligibilityRules) {
        this.mStateVersions = new HashMap();
        this.mExisting = new HashSet();
        this.mPackageManager = packageManager;
        this.mAllPackages = null;
        this.mRestoredSignatures = null;
        this.mHasMetadata = false;
        this.mStoredSdkVersion = Build.VERSION.SDK_INT;
        this.mStoredIncrementalVersion = Build.VERSION.INCREMENTAL;
        this.mUserId = i;
        this.mAllPackages = getStorableApplications(packageManager, i, backupEligibilityRules);
    }

    public static List getStorableApplications(
            PackageManager packageManager, int i, BackupEligibilityRules backupEligibilityRules) {
        List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(134217728, i);
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            if (!backupEligibilityRules.appIsEligibleForBackup(
                    ((PackageInfo) installedPackagesAsUser.get(size)).applicationInfo)) {
                installedPackagesAsUser.remove(size);
            }
        }
        return installedPackagesAsUser;
    }

    public static ArrayList readSignatureHashArray(DataInputStream dataInputStream) {
        byte[] bArr;
        try {
            try {
                int readInt = dataInputStream.readInt();
                if (readInt > 20) {
                    Slog.e("PMBA", "Suspiciously large sig count in restore data; aborting");
                    throw new IllegalStateException("Bad restore state");
                }
                ArrayList arrayList = new ArrayList(readInt);
                boolean z = false;
                for (int i = 0; i < readInt; i++) {
                    int readInt2 = dataInputStream.readInt();
                    byte[] bArr2 = new byte[readInt2];
                    dataInputStream.read(bArr2);
                    arrayList.add(bArr2);
                    if (readInt2 != 32) {
                        z = true;
                    }
                }
                if (!z) {
                    return arrayList;
                }
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    byte[] bArr3 = (byte[]) it.next();
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                        messageDigest.update(bArr3);
                        bArr = messageDigest.digest();
                    } catch (NoSuchAlgorithmException unused) {
                        Slog.w("BackupUtils", "No SHA-256 algorithm found!");
                        bArr = null;
                    }
                    arrayList2.add(bArr);
                }
                return arrayList2;
            } catch (EOFException unused2) {
                Slog.w("PMBA", "Read empty signature block");
                return null;
            }
        } catch (IOException unused3) {
            Slog.e("PMBA", "Unable to read signatures");
            return null;
        }
    }

    public static void writeStateFile(List list, ParcelFileDescriptor parcelFileDescriptor) {
        DataOutputStream dataOutputStream =
                new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(parcelFileDescriptor.getFileDescriptor())));
        try {
            dataOutputStream.writeUTF(STATE_FILE_HEADER);
            dataOutputStream.writeInt(2);
            dataOutputStream.writeUTF(ANCESTRAL_RECORD_KEY);
            dataOutputStream.writeInt(1);
            dataOutputStream.writeUTF(GLOBAL_METADATA_KEY);
            dataOutputStream.writeInt(Build.VERSION.SDK_INT);
            dataOutputStream.writeUTF(Build.VERSION.INCREMENTAL);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                PackageInfo packageInfo = (PackageInfo) it.next();
                dataOutputStream.writeUTF(packageInfo.packageName);
                if (packageInfo.versionCodeMajor != 0) {
                    dataOutputStream.writeInt(Integer.MIN_VALUE);
                    dataOutputStream.writeLong(packageInfo.getLongVersionCode());
                } else {
                    dataOutputStream.writeInt(packageInfo.versionCode);
                }
            }
            dataOutputStream.flush();
        } catch (IOException unused) {
            Slog.e("PMBA", "Unable to write package manager state file!");
        }
    }

    @Override // android.app.backup.BackupAgent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBackup(
            android.os.ParcelFileDescriptor r18,
            android.app.backup.BackupDataOutput r19,
            android.os.ParcelFileDescriptor r20) {
        /*
            Method dump skipped, instructions count: 659
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.PackageManagerBackupAgent.onBackup(android.os.ParcelFileDescriptor,"
                    + " android.app.backup.BackupDataOutput,"
                    + " android.os.ParcelFileDescriptor):void");
    }

    @Override // android.app.backup.BackupAgent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onRestore(
            android.app.backup.BackupDataInput r7, int r8, android.os.ParcelFileDescriptor r9) {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.PackageManagerBackupAgent.onRestore(android.app.backup.BackupDataInput,"
                    + " int, android.os.ParcelFileDescriptor):void");
    }
}
