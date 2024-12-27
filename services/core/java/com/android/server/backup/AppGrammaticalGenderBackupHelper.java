package com.android.server.backup;

import android.app.backup.BackupDataOutput;
import android.app.backup.BlobBackupHelper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.server.LocalServices;
import com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper;
import com.android.server.grammaticalinflection.GrammaticalInflectionService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppGrammaticalGenderBackupHelper extends BlobBackupHelper {
    public final GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl mGrammarInflectionManagerInternal;
    public final int mUserId;

    public AppGrammaticalGenderBackupHelper(int i) {
        super(1, new String[]{"app_gender"});
        this.mUserId = i;
        this.mGrammarInflectionManagerInternal = (GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl) LocalServices.getService(GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl.class);
    }

    public final void applyRestoredPayload(String str, byte[] bArr) {
        GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl grammaticalInflectionManagerInternalImpl;
        PackageInfo packageInfo;
        Integer num;
        if (!"app_gender".equals(str) || (grammaticalInflectionManagerInternalImpl = this.mGrammarInflectionManagerInternal) == null) {
            return;
        }
        int i = this.mUserId;
        GrammaticalInflectionBackupHelper grammaticalInflectionBackupHelper = GrammaticalInflectionService.this.mBackupHelper;
        synchronized (grammaticalInflectionBackupHelper.mCacheLock) {
            try {
                grammaticalInflectionBackupHelper.cleanStagedDataForOldEntries();
                HashMap readFromByteArray = GrammaticalInflectionBackupHelper.readFromByteArray(bArr);
                if (readFromByteArray.isEmpty()) {
                    return;
                }
                GrammaticalInflectionBackupHelper.StagedData stagedData = new GrammaticalInflectionBackupHelper.StagedData(grammaticalInflectionBackupHelper.mClock.millis());
                for (Map.Entry entry : readFromByteArray.entrySet()) {
                    int i2 = 0;
                    try {
                        packageInfo = grammaticalInflectionBackupHelper.mPackageManager.getPackageInfoAsUser((String) entry.getKey(), 0, i);
                    } catch (PackageManager.NameNotFoundException unused) {
                        packageInfo = null;
                    }
                    if (packageInfo != null) {
                        ActivityTaskManagerInternal.PackageConfig findPackageConfiguration = ActivityTaskManagerService.this.mPackageConfigPersister.findPackageConfiguration(i, (String) entry.getKey());
                        if (findPackageConfiguration != null && (num = findPackageConfiguration.mGrammaticalGender) != null) {
                            i2 = num.intValue();
                        }
                        grammaticalInflectionBackupHelper.mGrammaticalGenderService.setRequestedApplicationGrammaticalGender((String) entry.getKey(), i, ((Integer) entry.getValue()).intValue());
                    } else if (((Integer) entry.getValue()).intValue() != 0) {
                        stagedData.mPackageStates.put((String) entry.getKey(), (Integer) entry.getValue());
                    }
                }
                grammaticalInflectionBackupHelper.mCache.append(i, stagedData);
            } finally {
            }
        }
    }

    public final byte[] getBackupPayload(String str) {
        GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl grammaticalInflectionManagerInternalImpl;
        Integer num;
        if (!"app_gender".equals(str) || (grammaticalInflectionManagerInternalImpl = this.mGrammarInflectionManagerInternal) == null) {
            return null;
        }
        int i = this.mUserId;
        GrammaticalInflectionService.m569$$Nest$menforceCallerPermissions(GrammaticalInflectionService.this);
        GrammaticalInflectionBackupHelper grammaticalInflectionBackupHelper = GrammaticalInflectionService.this.mBackupHelper;
        synchronized (grammaticalInflectionBackupHelper.mCacheLock) {
            grammaticalInflectionBackupHelper.cleanStagedDataForOldEntries();
        }
        HashMap hashMap = new HashMap();
        for (ApplicationInfo applicationInfo : grammaticalInflectionBackupHelper.mPackageManager.getInstalledApplicationsAsUser(PackageManager.ApplicationInfoFlags.of(0L), i)) {
            ActivityTaskManagerInternal.PackageConfig findPackageConfiguration = ActivityTaskManagerService.this.mPackageConfigPersister.findPackageConfiguration(i, applicationInfo.packageName);
            int intValue = (findPackageConfiguration == null || (num = findPackageConfiguration.mGrammaticalGender) == null) ? 0 : num.intValue();
            if (intValue != 0) {
                hashMap.put(applicationInfo.packageName, Integer.valueOf(intValue));
            }
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(hashMap);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e("GrammaticalInflectionBackupHelper", "cannot convert payload to byte array.", e);
            return null;
        }
    }

    public final void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        if ((backupDataOutput.getTransportFlags() & 1) == 0) {
            return;
        }
        super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }
}
