package com.android.server.backup;

import android.app.IBackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FullBackup;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.backup.keyvalue.KeyValueBackupTask;

import libcore.io.IoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KeyValueAdbRestoreEngine implements Runnable {
    public final IBackupAgent mAgent;
    public final UserBackupManagerService mBackupManagerService;
    public final File mDataDir;
    public final ParcelFileDescriptor mInFD;
    public final FileMetadata mInfo;
    public final int mToken;

    public KeyValueAdbRestoreEngine(
            UserBackupManagerService userBackupManagerService,
            File file,
            FileMetadata fileMetadata,
            ParcelFileDescriptor parcelFileDescriptor,
            IBackupAgent iBackupAgent,
            int i) {
        this.mBackupManagerService = userBackupManagerService;
        this.mDataDir = file;
        this.mInfo = fileMetadata;
        this.mInFD = parcelFileDescriptor;
        this.mAgent = iBackupAgent;
        this.mToken = i;
    }

    public static void copyKeysInLexicalOrder(
            BackupDataInput backupDataInput, BackupDataOutput backupDataOutput) {
        HashMap hashMap = new HashMap();
        while (backupDataInput.readNextHeader()) {
            String key = backupDataInput.getKey();
            int dataSize = backupDataInput.getDataSize();
            if (dataSize < 0) {
                backupDataInput.skipEntityData();
            } else {
                byte[] bArr = new byte[dataSize];
                backupDataInput.readEntityData(bArr, 0, dataSize);
                hashMap.put(key, bArr);
            }
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet());
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            byte[] bArr2 = (byte[]) hashMap.get(str);
            backupDataOutput.writeEntityHeader(str, bArr2.length);
            backupDataOutput.writeEntityData(bArr2, bArr2.length);
        }
    }

    public final void invokeAgentForAdbRestore(
            IBackupAgent iBackupAgent, FileMetadata fileMetadata, File file) {
        File file2 =
                new File(
                        this.mDataDir,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                fileMetadata.packageName,
                                KeyValueBackupTask.NEW_STATE_FILE_SUFFIX));
        try {
            iBackupAgent.doRestore(
                    ParcelFileDescriptor.open(file, 268435456),
                    fileMetadata.version,
                    ParcelFileDescriptor.open(file2, 1006632960),
                    this.mToken,
                    this.mBackupManagerService.mBackupManagerBinder);
        } catch (RemoteException e) {
            Slog.e("KeyValueAdbRestoreEngine", "Exception calling doRestore on agent: " + e);
        } catch (IOException e2) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Exception opening file. ", e2, "KeyValueAdbRestoreEngine");
        }
    }

    public final File prepareRestoreData(
            FileMetadata fileMetadata, ParcelFileDescriptor parcelFileDescriptor) {
        FileOutputStream fileOutputStream;
        Throwable th;
        FileInputStream fileInputStream;
        String str = fileMetadata.packageName;
        File file =
                new File(
                        this.mDataDir,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                str, ".restore"));
        File file2 =
                new File(
                        this.mDataDir,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ".sorted"));
        FullBackup.restoreFile(
                parcelFileDescriptor,
                fileMetadata.size,
                fileMetadata.type,
                fileMetadata.mode,
                fileMetadata.mtime,
                file);
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (Throwable th2) {
                fileOutputStream = null;
                th = th2;
            }
        } catch (Throwable th3) {
            fileOutputStream = null;
            th = th3;
            fileInputStream = null;
        }
        try {
            copyKeysInLexicalOrder(
                    new BackupDataInput(fileInputStream.getFD()),
                    new BackupDataOutput(fileOutputStream.getFD()));
            IoUtils.closeQuietly(fileInputStream);
            IoUtils.closeQuietly(fileOutputStream);
            return file2;
        } catch (Throwable th4) {
            th = th4;
            if (fileInputStream != null) {
                IoUtils.closeQuietly(fileInputStream);
            }
            if (fileOutputStream != null) {
                IoUtils.closeQuietly(fileOutputStream);
            }
            throw th;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            invokeAgentForAdbRestore(
                    this.mAgent, this.mInfo, prepareRestoreData(this.mInfo, this.mInFD));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
