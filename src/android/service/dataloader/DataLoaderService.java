package android.service.dataloader;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.DataLoaderParams;
import android.content.pm.DataLoaderParamsParcel;
import android.content.pm.FileSystemControlParcel;
import android.content.pm.IDataLoader;
import android.content.pm.IDataLoaderStatusListener;
import android.content.pm.InstallationFile;
import android.content.pm.InstallationFileParcel;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.ExceptionUtils;
import android.util.Slog;
import java.io.IOException;
import java.util.Collection;
import libcore.io.IoUtils;

@SystemApi
/* loaded from: classes3.dex */
public abstract class DataLoaderService extends Service {
    private static final String TAG = "DataLoaderService";
    private final DataLoaderBinderService mBinder = new DataLoaderBinderService();

    @SystemApi
    public interface DataLoader {
        boolean onCreate(DataLoaderParams dataLoaderParams, FileSystemConnector fileSystemConnector);

        boolean onPrepareImage(Collection<InstallationFile> collection, Collection<String> collection2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeCreateDataLoader(int i, FileSystemControlParcel fileSystemControlParcel, DataLoaderParamsParcel dataLoaderParamsParcel, IDataLoaderStatusListener iDataLoaderStatusListener);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeDestroyDataLoader(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativePrepareImage(int i, InstallationFileParcel[] installationFileParcelArr, String[] strArr);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeStartDataLoader(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeStopDataLoader(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeWriteData(long j, String str, long j2, long j3, ParcelFileDescriptor parcelFileDescriptor);

    @SystemApi
    public DataLoader onCreateDataLoader(DataLoaderParams dataLoaderParams) {
        return null;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    private class DataLoaderBinderService extends IDataLoader.Stub {
        private DataLoaderBinderService() {
        }

        /* JADX WARN: Finally extract failed */
        @Override // android.content.pm.IDataLoader
        public void create(int i, DataLoaderParamsParcel dataLoaderParamsParcel, FileSystemControlParcel fileSystemControlParcel, IDataLoaderStatusListener iDataLoaderStatusListener) throws RuntimeException {
            try {
                try {
                    DataLoaderService.this.nativeCreateDataLoader(i, fileSystemControlParcel, dataLoaderParamsParcel, iDataLoaderStatusListener);
                    if (fileSystemControlParcel.incremental != null) {
                        IoUtils.closeQuietly(fileSystemControlParcel.incremental.cmd);
                        IoUtils.closeQuietly(fileSystemControlParcel.incremental.pendingReads);
                        IoUtils.closeQuietly(fileSystemControlParcel.incremental.log);
                        IoUtils.closeQuietly(fileSystemControlParcel.incremental.blocksWritten);
                    }
                } catch (Exception e) {
                    Slog.e(DataLoaderService.TAG, "Failed to create native loader for " + i, e);
                    destroy(i);
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                if (fileSystemControlParcel.incremental != null) {
                    IoUtils.closeQuietly(fileSystemControlParcel.incremental.cmd);
                    IoUtils.closeQuietly(fileSystemControlParcel.incremental.pendingReads);
                    IoUtils.closeQuietly(fileSystemControlParcel.incremental.log);
                    IoUtils.closeQuietly(fileSystemControlParcel.incremental.blocksWritten);
                }
                throw th;
            }
        }

        @Override // android.content.pm.IDataLoader
        public void start(int i) {
            if (DataLoaderService.this.nativeStartDataLoader(i)) {
                return;
            }
            Slog.e(DataLoaderService.TAG, "Failed to start loader: " + i);
        }

        @Override // android.content.pm.IDataLoader
        public void stop(int i) {
            if (DataLoaderService.this.nativeStopDataLoader(i)) {
                return;
            }
            Slog.w(DataLoaderService.TAG, "Failed to stop loader: " + i);
        }

        @Override // android.content.pm.IDataLoader
        public void destroy(int i) {
            if (DataLoaderService.this.nativeDestroyDataLoader(i)) {
                return;
            }
            Slog.w(DataLoaderService.TAG, "Failed to destroy loader: " + i);
        }

        @Override // android.content.pm.IDataLoader
        public void prepareImage(int i, InstallationFileParcel[] installationFileParcelArr, String[] strArr) {
            if (DataLoaderService.this.nativePrepareImage(i, installationFileParcelArr, strArr)) {
                return;
            }
            Slog.w(DataLoaderService.TAG, "Failed to prepare image for data loader: " + i);
        }
    }

    @SystemApi
    public static final class FileSystemConnector {
        private final long mNativeInstance;

        FileSystemConnector(long j) {
            this.mNativeInstance = j;
        }

        public void writeData(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
            try {
                DataLoaderService.nativeWriteData(this.mNativeInstance, str, j, j2, parcelFileDescriptor);
            } catch (RuntimeException e) {
                ExceptionUtils.maybeUnwrapIOException(e);
                throw e;
            }
        }
    }
}
