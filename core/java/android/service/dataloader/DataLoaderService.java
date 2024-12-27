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

import libcore.io.IoUtils;

import java.io.IOException;
import java.util.Collection;

@SystemApi
public abstract class DataLoaderService extends Service {
    private static final String TAG = "DataLoaderService";
    private final DataLoaderBinderService mBinder = new DataLoaderBinderService();

    @SystemApi
    public interface DataLoader {
        boolean onCreate(
                DataLoaderParams dataLoaderParams, FileSystemConnector fileSystemConnector);

        boolean onPrepareImage(
                Collection<InstallationFile> collection, Collection<String> collection2);
    }

    public native boolean nativeCreateDataLoader(
            int i,
            FileSystemControlParcel fileSystemControlParcel,
            DataLoaderParamsParcel dataLoaderParamsParcel,
            IDataLoaderStatusListener iDataLoaderStatusListener);

    public native boolean nativeDestroyDataLoader(int i);

    public native boolean nativePrepareImage(
            int i, InstallationFileParcel[] installationFileParcelArr, String[] strArr);

    public native boolean nativeStartDataLoader(int i);

    public native boolean nativeStopDataLoader(int i);

    public static native void nativeWriteData(
            long j, String str, long j2, long j3, ParcelFileDescriptor parcelFileDescriptor);

    @SystemApi
    public DataLoader onCreateDataLoader(DataLoaderParams dataLoaderParams) {
        return null;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    private class DataLoaderBinderService extends IDataLoader.Stub {
        private DataLoaderBinderService() {}

        @Override // android.content.pm.IDataLoader
        public void create(
                int id,
                DataLoaderParamsParcel params,
                FileSystemControlParcel control,
                IDataLoaderStatusListener listener)
                throws RuntimeException {
            try {
                try {
                    DataLoaderService.this.nativeCreateDataLoader(id, control, params, listener);
                    if (control.incremental != null) {
                        IoUtils.closeQuietly(control.incremental.cmd);
                        IoUtils.closeQuietly(control.incremental.pendingReads);
                        IoUtils.closeQuietly(control.incremental.log);
                        IoUtils.closeQuietly(control.incremental.blocksWritten);
                    }
                } catch (Exception ex) {
                    Slog.e(DataLoaderService.TAG, "Failed to create native loader for " + id, ex);
                    destroy(id);
                    throw new RuntimeException(ex);
                }
            } catch (Throwable th) {
                if (control.incremental != null) {
                    IoUtils.closeQuietly(control.incremental.cmd);
                    IoUtils.closeQuietly(control.incremental.pendingReads);
                    IoUtils.closeQuietly(control.incremental.log);
                    IoUtils.closeQuietly(control.incremental.blocksWritten);
                }
                throw th;
            }
        }

        @Override // android.content.pm.IDataLoader
        public void start(int id) {
            if (!DataLoaderService.this.nativeStartDataLoader(id)) {
                Slog.e(DataLoaderService.TAG, "Failed to start loader: " + id);
            }
        }

        @Override // android.content.pm.IDataLoader
        public void stop(int id) {
            if (!DataLoaderService.this.nativeStopDataLoader(id)) {
                Slog.w(DataLoaderService.TAG, "Failed to stop loader: " + id);
            }
        }

        @Override // android.content.pm.IDataLoader
        public void destroy(int id) {
            if (!DataLoaderService.this.nativeDestroyDataLoader(id)) {
                Slog.w(DataLoaderService.TAG, "Failed to destroy loader: " + id);
            }
        }

        @Override // android.content.pm.IDataLoader
        public void prepareImage(
                int id, InstallationFileParcel[] addedFiles, String[] removedFiles) {
            if (!DataLoaderService.this.nativePrepareImage(id, addedFiles, removedFiles)) {
                Slog.w(DataLoaderService.TAG, "Failed to prepare image for data loader: " + id);
            }
        }
    }

    @SystemApi
    public static final class FileSystemConnector {
        private final long mNativeInstance;

        FileSystemConnector(long nativeInstance) {
            this.mNativeInstance = nativeInstance;
        }

        public void writeData(
                String name, long offsetBytes, long lengthBytes, ParcelFileDescriptor incomingFd)
                throws IOException {
            try {
                DataLoaderService.nativeWriteData(
                        this.mNativeInstance, name, offsetBytes, lengthBytes, incomingFd);
            } catch (RuntimeException e) {
                ExceptionUtils.maybeUnwrapIOException(e);
                throw e;
            }
        }
    }
}
