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

public final class IncrementalFileStorages {
    private static final String SYSTEM_DATA_LOADER_PACKAGE = "android";
    private static final String TAG = "IncrementalFileStorages";
    private IncrementalStorage mDefaultStorage;
    private final IncrementalManager mIncrementalManager;
    private IncrementalStorage mInheritedStorage;
    private final File mStageDir;

    public static IncrementalFileStorages initialize(
            Context context,
            File stageDir,
            File inheritedDir,
            DataLoaderParams dataLoaderParams,
            IDataLoaderStatusListener statusListener,
            StorageHealthCheckParams healthCheckParams,
            IStorageHealthListener healthListener,
            List<InstallationFileParcel> addedFiles,
            PerUidReadTimeouts[] perUidReadTimeouts,
            IPackageLoadingProgressCallback progressCallback)
            throws IOException {
        IncrementalManager incrementalManager =
                (IncrementalManager) context.getSystemService(Context.INCREMENTAL_SERVICE);
        if (incrementalManager == null) {
            throw new IOException("Failed to obtain incrementalManager.");
        }
        IncrementalFileStorages result =
                new IncrementalFileStorages(
                        stageDir, inheritedDir, incrementalManager, dataLoaderParams);
        for (InstallationFileParcel file : addedFiles) {
            if (file.location == 0) {
                try {
                    result.addApkFile(file);
                } catch (IOException e) {
                    throw new IOException(
                            "Failed to add file to IncFS: " + file.name + ", reason: ", e);
                }
            } else {
                throw new IOException("Unknown file location: " + file.location);
            }
        }
        if (progressCallback != null) {
            incrementalManager.registerLoadingProgressCallback(
                    stageDir.getAbsolutePath(), progressCallback);
        }
        result.startLoading(
                dataLoaderParams,
                statusListener,
                healthCheckParams,
                healthListener,
                perUidReadTimeouts);
        return result;
    }

    private IncrementalFileStorages(
            File stageDir,
            File inheritedDir,
            IncrementalManager incrementalManager,
            DataLoaderParams dataLoaderParams)
            throws IOException {
        try {
            this.mStageDir = stageDir;
            this.mIncrementalManager = incrementalManager;
            if (inheritedDir != null
                    && IncrementalManager.isIncrementalPath(inheritedDir.getAbsolutePath())) {
                this.mInheritedStorage =
                        this.mIncrementalManager.openStorage(inheritedDir.getAbsolutePath());
                if (this.mInheritedStorage != null) {
                    boolean systemDataLoader =
                            "android".equals(dataLoaderParams.getComponentName().getPackageName());
                    if (systemDataLoader && !this.mInheritedStorage.isFullyLoaded()) {
                        throw new IOException("Inherited storage has missing pages.");
                    }
                    this.mDefaultStorage =
                            this.mIncrementalManager.createStorage(
                                    stageDir.getAbsolutePath(), this.mInheritedStorage, 5);
                    if (this.mDefaultStorage == null) {
                        throw new IOException(
                                "Couldn't create linked incremental storage at " + stageDir);
                    }
                    return;
                }
            }
            this.mDefaultStorage =
                    this.mIncrementalManager.createStorage(
                            stageDir.getAbsolutePath(), dataLoaderParams, 5);
            if (this.mDefaultStorage == null) {
                throw new IOException("Couldn't create incremental storage at " + stageDir);
            }
        } catch (IOException e) {
            cleanUp();
            throw e;
        }
    }

    private void addApkFile(InstallationFileParcel apk) throws IOException {
        String apkName = apk.name;
        File targetFile = new File(this.mStageDir, apkName);
        if (!targetFile.exists()) {
            this.mDefaultStorage.makeFile(
                    apkName, apk.size, 511, null, apk.metadata, apk.signature, null);
        }
    }

    public void startLoading(
            DataLoaderParams dataLoaderParams,
            IDataLoaderStatusListener statusListener,
            StorageHealthCheckParams healthCheckParams,
            IStorageHealthListener healthListener,
            PerUidReadTimeouts[] perUidReadTimeouts)
            throws IOException {
        if (!this.mDefaultStorage.startLoading(
                dataLoaderParams,
                statusListener,
                healthCheckParams,
                healthListener,
                perUidReadTimeouts)) {
            throw new IOException(
                    "Failed to start or restart loading data for Incremental installation.");
        }
    }

    public void makeFile(String name, byte[] content, int mode) throws IOException {
        this.mDefaultStorage.makeFile(
                name, content.length, mode, UUID.randomUUID(), null, null, content);
    }

    public boolean makeLink(String relativePath, String fromBase, String toBase)
            throws IOException {
        if (this.mInheritedStorage == null) {
            return false;
        }
        File sourcePath = new File(fromBase, relativePath);
        File destPath = new File(toBase, relativePath);
        this.mInheritedStorage.makeLink(
                sourcePath.getAbsolutePath(), this.mDefaultStorage, destPath.getAbsolutePath());
        return true;
    }

    public void disallowReadLogs() {
        this.mDefaultStorage.disallowReadLogs();
    }

    public void cleanUpAndMarkComplete() {
        IncrementalStorage defaultStorage = cleanUp();
        if (defaultStorage != null) {
            defaultStorage.onInstallationComplete();
        }
    }

    private IncrementalStorage cleanUp() {
        IncrementalStorage defaultStorage = this.mDefaultStorage;
        this.mInheritedStorage = null;
        this.mDefaultStorage = null;
        if (defaultStorage == null) {
            return null;
        }
        try {
            this.mIncrementalManager.unregisterLoadingProgressCallbacks(
                    this.mStageDir.getAbsolutePath());
            defaultStorage.unBind(this.mStageDir.getAbsolutePath());
        } catch (IOException e) {
        }
        return defaultStorage;
    }
}
