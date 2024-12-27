package com.android.server.usage;

import android.annotation.SystemApi;
import android.content.pm.PackageStats;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public interface StorageStatsManagerLocal {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface StorageStatsAugmenter {
        void augmentStatsForPackageForUser(
                PackageStats packageStats, String str, UserHandle userHandle, boolean z);

        void augmentStatsForUid(PackageStats packageStats, int i, boolean z);

        void augmentStatsForUser(PackageStats packageStats, UserHandle userHandle);
    }

    void registerStorageStatsAugmenter(StorageStatsAugmenter storageStatsAugmenter, String str);
}
