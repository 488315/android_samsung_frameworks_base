package android.app.wearable;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FunctionalUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes.dex */
public class WearableSensingManager {
    static final long ALLOW_WEARABLE_SENSING_SERVICE_FILE_READ = 330701114;
    public static final String EXTRA_WEARABLE_SENSING_DATA_REQUEST =
            "android.app.wearable.extra.WEARABLE_SENSING_DATA_REQUEST";
    public static final int STATUS_ACCESS_DENIED = 5;
    public static final int STATUS_CHANNEL_ERROR = 7;
    public static final String STATUS_RESPONSE_BUNDLE_KEY =
            "android.app.wearable.WearableSensingStatusBundleKey";
    public static final int STATUS_SERVICE_UNAVAILABLE = 3;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;

    @Deprecated public static final int STATUS_UNSUPPORTED = 2;
    public static final int STATUS_UNSUPPORTED_DATA_TYPE = 8;
    public static final int STATUS_UNSUPPORTED_OPERATION = 6;
    public static final int STATUS_WEARABLE_UNAVAILABLE = 4;
    private static final String TAG = WearableSensingManager.class.getSimpleName();
    private final Context mContext;
    private final IWearableSensingManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusCode {}

    public static WearableSensingDataRequest getDataRequestFromIntent(Intent intent) {
        return (WearableSensingDataRequest)
                intent.getParcelableExtra(
                        EXTRA_WEARABLE_SENSING_DATA_REQUEST, WearableSensingDataRequest.class);
    }

    public WearableSensingManager(Context context, IWearableSensingManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public void provideConnection(
            ParcelFileDescriptor wearableConnection,
            Executor executor,
            Consumer<Integer> statusConsumer) {
        RemoteCallback statusCallback = createStatusCallback(executor, statusConsumer);
        try {
            this.mService.provideConnection(
                    wearableConnection, createWearableSensingCallback(executor), statusCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void provideDataStream(
            ParcelFileDescriptor parcelFileDescriptor,
            Executor executor,
            Consumer<Integer> statusConsumer) {
        RemoteCallback statusCallback = createStatusCallback(executor, statusConsumer);
        IWearableSensingCallback wearableSensingCallback = null;
        if (CompatChanges.isChangeEnabled(ALLOW_WEARABLE_SENSING_SERVICE_FILE_READ)) {
            wearableSensingCallback = createWearableSensingCallback(executor);
        }
        try {
            this.mService.provideDataStream(
                    parcelFileDescriptor, wearableSensingCallback, statusCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideData(
            PersistableBundle data,
            SharedMemory sharedMemory,
            Executor executor,
            Consumer<Integer> statusConsumer) {
        try {
            RemoteCallback callback = createStatusCallback(executor, statusConsumer);
            this.mService.provideData(data, sharedMemory, callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerDataRequestObserver(
            int dataType,
            PendingIntent dataRequestPendingIntent,
            Executor executor,
            Consumer<Integer> statusConsumer) {
        try {
            RemoteCallback statusCallback = createStatusCallback(executor, statusConsumer);
            this.mService.registerDataRequestObserver(
                    dataType, dataRequestPendingIntent, statusCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterDataRequestObserver(
            int dataType,
            PendingIntent dataRequestPendingIntent,
            Executor executor,
            Consumer<Integer> statusConsumer) {
        try {
            RemoteCallback statusCallback = createStatusCallback(executor, statusConsumer);
            this.mService.unregisterDataRequestObserver(
                    dataType, dataRequestPendingIntent, statusCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startHotwordRecognition(
            ComponentName targetVisComponentName,
            Executor executor,
            Consumer<Integer> statusConsumer) {
        try {
            this.mService.startHotwordRecognition(
                    targetVisComponentName, createStatusCallback(executor, statusConsumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopHotwordRecognition(Executor executor, Consumer<Integer> statusConsumer) {
        try {
            this.mService.stopHotwordRecognition(createStatusCallback(executor, statusConsumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static RemoteCallback createStatusCallback(
            final Executor executor, final Consumer<Integer> statusConsumer) {
        return new RemoteCallback(
                new RemoteCallback.OnResultListener() { // from class:
                    // android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda0
                    @Override // android.os.RemoteCallback.OnResultListener
                    public final void onResult(Bundle bundle) {
                        WearableSensingManager.lambda$createStatusCallback$1(
                                executor, statusConsumer, bundle);
                    }
                });
    }

    static /* synthetic */ void lambda$createStatusCallback$1(
            Executor executor, final Consumer statusConsumer, Bundle result) {
        final int status = result.getInt("android.app.wearable.WearableSensingStatusBundleKey");
        long identity = Binder.clearCallingIdentity();
        try {
            executor.execute(
                    new Runnable() { // from class:
                        // android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            statusConsumer.accept(Integer.valueOf(status));
                        }
                    });
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    /* renamed from: android.app.wearable.WearableSensingManager$1, reason: invalid class name */
    class AnonymousClass1 extends IWearableSensingCallback.Stub {
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(Executor executor) {
            this.val$executor = executor;
        }

        @Override // android.app.wearable.IWearableSensingCallback
        public void openFile(
                final String filename, final AndroidFuture<ParcelFileDescriptor> future) {
            Slog.d(WearableSensingManager.TAG, "IWearableSensingCallback#openFile " + filename);
            final Executor executor = this.val$executor;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.wearable.WearableSensingManager$1$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            WearableSensingManager.AnonymousClass1.this.lambda$openFile$1(
                                    executor, filename, future);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$openFile$1(
                Executor executor, final String filename, final AndroidFuture future)
                throws Exception {
            executor.execute(
                    new Runnable() { // from class:
                        // android.app.wearable.WearableSensingManager$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WearableSensingManager.AnonymousClass1.this.lambda$openFile$0(
                                    filename, future);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x002d -> B:8:0x004b). Please report as a decompilation issue!!! */
        public /* synthetic */ void lambda$openFile$0(String filename, AndroidFuture future) {
            File file = new File(WearableSensingManager.this.mContext.getFilesDir(), filename);
            ParcelFileDescriptor pfd = null;
            try {
            } catch (IOException ex) {
                Slog.e(WearableSensingManager.TAG, "Error closing ParcelFileDescriptor.", ex);
            }
            try {
                try {
                    pfd = ParcelFileDescriptor.open(file, 268435456);
                    Slog.d(
                            WearableSensingManager.TAG,
                            "Successfully opened a file with ParcelFileDescriptor.");
                    future.complete(pfd);
                    if (pfd != null) {
                        pfd.close();
                    }
                } catch (FileNotFoundException e) {
                    Slog.e(WearableSensingManager.TAG, "Cannot open file.", e);
                    future.complete(pfd);
                    if (pfd != null) {
                        pfd.close();
                    }
                }
            } catch (Throwable th) {
                future.complete(pfd);
                if (pfd != null) {
                    try {
                        pfd.close();
                    } catch (IOException ex2) {
                        Slog.e(
                                WearableSensingManager.TAG,
                                "Error closing ParcelFileDescriptor.",
                                ex2);
                    }
                }
                throw th;
            }
        }
    }

    private IWearableSensingCallback createWearableSensingCallback(Executor executor) {
        return new AnonymousClass1(executor);
    }
}
