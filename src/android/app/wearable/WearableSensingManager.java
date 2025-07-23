package android.app.wearable;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.app.wearable.IWearableSensingCallback;
import android.app.wearable.WearableSensingManager;
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
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes.dex */
public class WearableSensingManager {
    static final long ALLOW_WEARABLE_SENSING_SERVICE_FILE_READ = 330701114;
    public static final int CONNECTION_ID_INVALID = -1;
    private static final int CONNECTION_ID_PLACEHOLDER = -2;
    public static final String EXTRA_WEARABLE_SENSING_DATA_REQUEST = "android.app.wearable.extra.WEARABLE_SENSING_DATA_REQUEST";
    public static final int STATUS_ACCESS_DENIED = 5;
    public static final int STATUS_CHANNEL_ERROR = 7;
    public static final int STATUS_MAX_CONCURRENT_CONNECTIONS_EXCEEDED = 9;
    public static final String STATUS_RESPONSE_BUNDLE_KEY = "android.app.wearable.WearableSensingStatusBundleKey";
    public static final int STATUS_SERVICE_UNAVAILABLE = 3;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;

    @Deprecated
    public static final int STATUS_UNSUPPORTED = 2;
    public static final int STATUS_UNSUPPORTED_DATA_TYPE = 8;
    public static final int STATUS_UNSUPPORTED_OPERATION = 6;
    public static final int STATUS_WEARABLE_UNAVAILABLE = 4;
    private static final String TAG = "WearableSensingManager";
    private final Context mContext;
    private final IWearableSensingManager mService;
    private final Map<WearableConnection, Integer> mWearableConnectionIdMap = new ConcurrentHashMap();

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusCode {
    }

    public static WearableSensingDataRequest getDataRequestFromIntent(Intent intent) {
        return (WearableSensingDataRequest) intent.getParcelableExtra(EXTRA_WEARABLE_SENSING_DATA_REQUEST, WearableSensingDataRequest.class);
    }

    public WearableSensingManager(Context context, IWearableSensingManager iWearableSensingManager) {
        this.mContext = context;
        this.mService = iWearableSensingManager;
    }

    public int getAvailableConnectionCount() {
        try {
            return this.mService.getAvailableConnectionCount();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void provideConnection(ParcelFileDescriptor parcelFileDescriptor, Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.provideConnection(parcelFileDescriptor, createWearableSensingCallback(executor), createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideConnection(final WearableConnection wearableConnection, Executor executor) {
        RemoteCallback createStatusCallback = createStatusCallback(executor, new Consumer() { // from class: android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WearableSensingManager.this.lambda$provideConnection$0(wearableConnection, (Integer) obj);
            }
        });
        try {
            this.mWearableConnectionIdMap.put(wearableConnection, -2);
            this.mWearableConnectionIdMap.put(wearableConnection, Integer.valueOf(this.mService.provideConcurrentConnection(wearableConnection.getConnection(), wearableConnection.getMetadata(), createWearableSensingCallback(executor), createStatusCallback)));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$provideConnection$0(WearableConnection wearableConnection, Integer num) {
        if (!this.mWearableConnectionIdMap.containsKey(wearableConnection)) {
            Slog.i(TAG, "Surpassed status callback for removed connection " + wearableConnection);
            return;
        }
        if (num.intValue() == 1) {
            wearableConnection.onConnectionAccepted();
        } else {
            this.mWearableConnectionIdMap.remove(wearableConnection);
            wearableConnection.onError(num.intValue());
        }
    }

    public void removeConnection(WearableConnection wearableConnection) {
        Integer remove = this.mWearableConnectionIdMap.remove(wearableConnection);
        if (remove == null || remove.intValue() == -1) {
            throw new NoSuchElementException("The provided connection was never provided or was already removed.");
        }
        if (remove.intValue() == -2) {
            throw new IllegalStateException("Attempt to remove connection before provideConnection returns. The connection will not be removed.");
        }
        try {
            if (this.mService.removeConnection(remove.intValue())) {
            } else {
                throw new NoSuchElementException("The provided connection was never provided or was already removed.");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllConnections() {
        this.mWearableConnectionIdMap.clear();
        try {
            this.mService.removeAllConnections();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.provideReadOnlyParcelFileDescriptor(parcelFileDescriptor, persistableBundle, createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.provideDataStream(parcelFileDescriptor, CompatChanges.isChangeEnabled(ALLOW_WEARABLE_SENSING_SERVICE_FILE_READ) ? createWearableSensingCallback(executor) : null, createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.provideData(persistableBundle, sharedMemory, createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerDataRequestObserver(int i, PendingIntent pendingIntent, Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.registerDataRequestObserver(i, pendingIntent, createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterDataRequestObserver(int i, PendingIntent pendingIntent, Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.unregisterDataRequestObserver(i, pendingIntent, createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startHotwordRecognition(ComponentName componentName, Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.startHotwordRecognition(componentName, createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopHotwordRecognition(Executor executor, Consumer<Integer> consumer) {
        try {
            this.mService.stopHotwordRecognition(createStatusCallback(executor, consumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static RemoteCallback createStatusCallback(final Executor executor, final Consumer<Integer> consumer) {
        return new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda0
            @Override // android.os.RemoteCallback.OnResultListener
            public final void onResult(Bundle bundle) {
                WearableSensingManager.lambda$createStatusCallback$2(executor, consumer, bundle);
            }
        });
    }

    static /* synthetic */ void lambda$createStatusCallback$2(Executor executor, final Consumer consumer, Bundle bundle) {
        final int i = bundle.getInt("android.app.wearable.WearableSensingStatusBundleKey");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            executor.execute(new Runnable() { // from class: android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(Integer.valueOf(i));
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: android.app.wearable.WearableSensingManager$1, reason: invalid class name */
    class AnonymousClass1 extends IWearableSensingCallback.Stub {
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(Executor executor) {
            this.val$executor = executor;
        }

        @Override // android.app.wearable.IWearableSensingCallback
        public void openFile(final String str, final AndroidFuture<ParcelFileDescriptor> androidFuture) {
            Slog.d(WearableSensingManager.TAG, "IWearableSensingCallback#openFile " + str);
            final Executor executor = this.val$executor;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.app.wearable.WearableSensingManager$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    WearableSensingManager.AnonymousClass1.this.lambda$openFile$1(executor, str, androidFuture);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$openFile$1(Executor executor, final String str, final AndroidFuture androidFuture) throws Exception {
            executor.execute(new Runnable() { // from class: android.app.wearable.WearableSensingManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    WearableSensingManager.AnonymousClass1.this.lambda$openFile$0(str, androidFuture);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$openFile$0(String str, AndroidFuture androidFuture) {
            File file = new File(WearableSensingManager.this.mContext.getFilesDir(), str);
            ParcelFileDescriptor parcelFileDescriptor = null;
            ParcelFileDescriptor parcelFileDescriptor2 = null;
            try {
                try {
                    try {
                        parcelFileDescriptor2 = ParcelFileDescriptor.open(file, 268435456);
                        Slog.d(WearableSensingManager.TAG, "Successfully opened a file with ParcelFileDescriptor.");
                        androidFuture.complete(parcelFileDescriptor2);
                        parcelFileDescriptor = parcelFileDescriptor2;
                        if (parcelFileDescriptor2 != null) {
                            parcelFileDescriptor2.close();
                        }
                    } catch (FileNotFoundException e) {
                        Slog.e(WearableSensingManager.TAG, "Cannot open file.", e);
                        androidFuture.complete(parcelFileDescriptor2);
                        parcelFileDescriptor = parcelFileDescriptor2;
                        if (parcelFileDescriptor2 != null) {
                            parcelFileDescriptor2.close();
                            parcelFileDescriptor = parcelFileDescriptor2;
                        }
                    }
                } catch (IOException e2) {
                    String str2 = WearableSensingManager.TAG;
                    Slog.e(str2, "Error closing ParcelFileDescriptor.", e2);
                    parcelFileDescriptor = str2;
                }
            } catch (Throwable th) {
                androidFuture.complete(parcelFileDescriptor);
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e3) {
                        Slog.e(WearableSensingManager.TAG, "Error closing ParcelFileDescriptor.", e3);
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
