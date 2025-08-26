package android.service.storage;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.service.storage.IExternalStorageService;
import com.android.internal.os.BackgroundThread;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

@SystemApi
/* loaded from: classes3.dex */
public abstract class ExternalStorageService extends Service {
    public static final String EXTRA_ERROR = "android.service.storage.extra.error";
    public static final String EXTRA_PACKAGE_NAME = "android.service.storage.extra.package_name";
    public static final String EXTRA_SESSION_ID = "android.service.storage.extra.session_id";
    public static final int FLAG_SESSION_ATTRIBUTE_INDEXABLE = 2;
    public static final int FLAG_SESSION_TYPE_FUSE = 1;
    public static final String SERVICE_INTERFACE = "android.service.storage.ExternalStorageService";
    private final ExternalStorageServiceWrapper mWrapper = new ExternalStorageServiceWrapper();
    private final Handler mHandler = BackgroundThread.getHandler();

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionFlag {
    }

    public abstract void onEndSession(String str) throws IOException;

    public abstract void onStartSession(String str, int i, ParcelFileDescriptor parcelFileDescriptor, File file, File file2) throws IOException;

    public abstract void onVolumeStateChanged(StorageVolume storageVolume) throws IOException;

    public void onFreeCache(UUID uuid, long j) throws IOException {
        throw new UnsupportedOperationException("onFreeCacheRequested not implemented");
    }

    public void onAnrDelayStarted(String str, int i, int i2, int i3) {
        throw new UnsupportedOperationException("onAnrDelayStarted not implemented");
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ExternalStorageServiceWrapper extends IExternalStorageService.Stub {
        private ExternalStorageServiceWrapper() {
        }

        @Override // android.service.storage.IExternalStorageService
        public void startSession(final String str, final int i, final ParcelFileDescriptor parcelFileDescriptor, final String str2, final String str3, final RemoteCallback remoteCallback) throws RemoteException {
            ExternalStorageService.this.mHandler.post(new Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$startSession$0(str, i, parcelFileDescriptor, str2, str3, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSession$0(String str, int i, ParcelFileDescriptor parcelFileDescriptor, String str2, String str3, RemoteCallback remoteCallback) {
            String str4;
            try {
                str4 = str;
                try {
                    ExternalStorageService.this.onStartSession(str4, i, parcelFileDescriptor, new File(str2), new File(str3));
                    sendResult(str4, null, remoteCallback);
                } catch (Throwable th) {
                    th = th;
                    sendResult(str4, th, remoteCallback);
                }
            } catch (Throwable th2) {
                th = th2;
                str4 = str;
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyVolumeStateChanged(final String str, final StorageVolume storageVolume, final RemoteCallback remoteCallback) {
            ExternalStorageService.this.mHandler.post(new Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyVolumeStateChanged$1(storageVolume, str, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyVolumeStateChanged$1(StorageVolume storageVolume, String str, RemoteCallback remoteCallback) {
            try {
                ExternalStorageService.this.onVolumeStateChanged(storageVolume);
                sendResult(str, null, remoteCallback);
            } catch (Throwable th) {
                sendResult(str, th, remoteCallback);
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void freeCache(final String str, final String str2, final long j, final RemoteCallback remoteCallback) {
            ExternalStorageService.this.mHandler.post(new Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$freeCache$2(str2, j, str, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freeCache$2(String str, long j, String str2, RemoteCallback remoteCallback) {
            try {
                ExternalStorageService.this.onFreeCache(StorageManager.convert(str), j);
                sendResult(str2, null, remoteCallback);
            } catch (Throwable th) {
                sendResult(str2, th, remoteCallback);
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void endSession(final String str, final RemoteCallback remoteCallback) throws RemoteException {
            ExternalStorageService.this.mHandler.post(new Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$endSession$3(str, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$endSession$3(String str, RemoteCallback remoteCallback) {
            try {
                ExternalStorageService.this.onEndSession(str);
                sendResult(str, null, remoteCallback);
            } catch (Throwable th) {
                sendResult(str, th, remoteCallback);
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyAnrDelayStarted(final String str, final int i, final int i2, final int i3) throws RemoteException {
            ExternalStorageService.this.mHandler.post(new Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyAnrDelayStarted$4(str, i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAnrDelayStarted$4(String str, int i, int i2, int i3) {
            try {
                ExternalStorageService.this.onAnrDelayStarted(str, i, i2, i3);
            } catch (Throwable unused) {
            }
        }

        private void sendResult(String str, Throwable th, RemoteCallback remoteCallback) {
            Bundle bundle = new Bundle();
            bundle.putString(ExternalStorageService.EXTRA_SESSION_ID, str);
            if (th != null) {
                bundle.putParcelable(ExternalStorageService.EXTRA_ERROR, new ParcelableException(th));
            }
            remoteCallback.sendResult(bundle);
        }
    }
}
