package android.os.incremental;

import android.content.Context;
import android.content.pm.DataLoaderParams;
import android.content.pm.IDataLoaderStatusListener;
import android.content.pm.IPackageLoadingProgressCallback;
import android.content.pm.InstallationFileParcel;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class IncrementalFileStorages {
    private static final String SYSTEM_DATA_LOADER_PACKAGE = "android";
    private static final String TAG = "IncrementalFileStorages";
    private IncrementalStorage mDefaultStorage;
    private final IncrementalManager mIncrementalManager;
    private IncrementalStorage mInheritedStorage;
    private final File mStageDir;

    public static IncrementalFileStorages initialize(Context context, File file, File file2, DataLoaderParams dataLoaderParams, IDataLoaderStatusListener iDataLoaderStatusListener, StorageHealthCheckParams storageHealthCheckParams, IStorageHealthListener iStorageHealthListener, List<InstallationFileParcel> list, PerUidReadTimeouts[] perUidReadTimeoutsArr, IPackageLoadingProgressCallback iPackageLoadingProgressCallback) throws IOException {
        IncrementalManager incrementalManager = (IncrementalManager) context.getSystemService(Context.INCREMENTAL_SERVICE);
        if (incrementalManager == null) {
            throw new IOException("Failed to obtain incrementalManager.");
        }
        IncrementalFileStorages incrementalFileStorages = new IncrementalFileStorages(file, file2, incrementalManager, dataLoaderParams);
        for (InstallationFileParcel installationFileParcel : list) {
            if (installationFileParcel.location == 0) {
                try {
                    incrementalFileStorages.addApkFile(installationFileParcel);
                } catch (IOException e) {
                    throw new IOException("Failed to add file to IncFS: " + installationFileParcel.name + ", reason: ", e);
                }
            } else {
                throw new IOException("Unknown file location: " + installationFileParcel.location);
            }
        }
        if (iPackageLoadingProgressCallback != null) {
            incrementalManager.registerLoadingProgressCallback(file.getAbsolutePath(), iPackageLoadingProgressCallback);
        }
        incrementalFileStorages.startLoading(dataLoaderParams, iDataLoaderStatusListener, storageHealthCheckParams, iStorageHealthListener, perUidReadTimeoutsArr);
        return incrementalFileStorages;
    }

    private IncrementalFileStorages(File file, File file2, IncrementalManager incrementalManager, DataLoaderParams dataLoaderParams) throws IOException {
        try {
            this.mStageDir = file;
            this.mIncrementalManager = incrementalManager;
            if (file2 != null && IncrementalManager.isIncrementalPath(file2.getAbsolutePath())) {
                IncrementalStorage openStorage = incrementalManager.openStorage(file2.getAbsolutePath());
                this.mInheritedStorage = openStorage;
                if (openStorage != null) {
                    if ("android".equals(dataLoaderParams.getComponentName().getPackageName()) && !this.mInheritedStorage.isFullyLoaded()) {
                        throw new IOException("Inherited storage has missing pages.");
                    }
                    IncrementalStorage createStorage = incrementalManager.createStorage(file.getAbsolutePath(), this.mInheritedStorage, 5);
                    this.mDefaultStorage = createStorage;
                    if (createStorage != null) {
                        return;
                    }
                    throw new IOException("Couldn't create linked incremental storage at " + file);
                }
            }
            IncrementalStorage createStorage2 = incrementalManager.createStorage(file.getAbsolutePath(), dataLoaderParams, 5);
            this.mDefaultStorage = createStorage2;
            if (createStorage2 != null) {
                return;
            }
            throw new IOException("Couldn't create incremental storage at " + file);
        } catch (IOException e) {
            cleanUp();
            throw e;
        }
    }

    private void addApkFile(InstallationFileParcel installationFileParcel) throws IOException {
        String str = installationFileParcel.name;
        if (new File(this.mStageDir, str).exists()) {
            return;
        }
        this.mDefaultStorage.makeFile(str, installationFileParcel.size, 511, null, installationFileParcel.metadata, installationFileParcel.signature, null);
    }

    public void startLoading(DataLoaderParams dataLoaderParams, IDataLoaderStatusListener iDataLoaderStatusListener, StorageHealthCheckParams storageHealthCheckParams, IStorageHealthListener iStorageHealthListener, PerUidReadTimeouts[] perUidReadTimeoutsArr) throws IOException {
        if (!this.mDefaultStorage.startLoading(dataLoaderParams, iDataLoaderStatusListener, storageHealthCheckParams, iStorageHealthListener, perUidReadTimeoutsArr)) {
            throw new IOException("Failed to start or restart loading data for Incremental installation.");
        }
    }

    public void makeFile(String str, byte[] bArr, int i) throws IOException {
        this.mDefaultStorage.makeFile(str, bArr.length, i, UUID.randomUUID(), null, null, bArr);
    }

    public boolean makeLink(String str, String str2, String str3) throws IOException {
        if (this.mInheritedStorage == null) {
            return false;
        }
        this.mInheritedStorage.makeLink(new File(str2, str).getAbsolutePath(), this.mDefaultStorage, new File(str3, str).getAbsolutePath());
        return true;
    }

    public void disallowReadLogs() {
        this.mDefaultStorage.disallowReadLogs();
    }

    public void cleanUpAndMarkComplete() {
        IncrementalStorage cleanUp = cleanUp();
        if (cleanUp != null) {
            cleanUp.onInstallationComplete();
        }
    }

    private IncrementalStorage cleanUp() {
        IncrementalStorage incrementalStorage = this.mDefaultStorage;
        this.mInheritedStorage = null;
        this.mDefaultStorage = null;
        if (incrementalStorage == null) {
            return null;
        }
        try {
            this.mIncrementalManager.unregisterLoadingProgressCallbacks(this.mStageDir.getAbsolutePath());
            incrementalStorage.unBind(this.mStageDir.getAbsolutePath());
        } catch (IOException unused) {
        }
        return incrementalStorage;
    }
}
