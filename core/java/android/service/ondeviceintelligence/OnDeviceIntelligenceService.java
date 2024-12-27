package android.service.ondeviceintelligence;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.ondeviceintelligence.DownloadCallback;
import android.app.ondeviceintelligence.Feature;
import android.app.ondeviceintelligence.FeatureDetails;
import android.app.ondeviceintelligence.IDownloadCallback;
import android.app.ondeviceintelligence.IFeatureCallback;
import android.app.ondeviceintelligence.IFeatureDetailsCallback;
import android.app.ondeviceintelligence.IListFeaturesCallback;
import android.app.ondeviceintelligence.OnDeviceIntelligenceException;
import android.app.ondeviceintelligence.OnDeviceIntelligenceManager;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.ondeviceintelligence.IOnDeviceIntelligenceService;
import android.service.ondeviceintelligence.IProcessingUpdateStatusCallback;
import android.service.ondeviceintelligence.OnDeviceIntelligenceService;
import android.util.Log;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class OnDeviceIntelligenceService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.ondeviceintelligence.OnDeviceIntelligenceService";
    private static final String TAG = OnDeviceIntelligenceService.class.getSimpleName();
    private Handler mHandler;
    private volatile IRemoteProcessingService mRemoteProcessingService;

    public abstract void onDownloadFeature(int i, Feature feature, CancellationSignal cancellationSignal, DownloadCallback downloadCallback);

    public abstract void onGetFeature(int i, int i2, OutcomeReceiver<Feature, OnDeviceIntelligenceException> outcomeReceiver);

    public abstract void onGetFeatureDetails(int i, Feature feature, OutcomeReceiver<FeatureDetails, OnDeviceIntelligenceException> outcomeReceiver);

    public abstract void onGetReadOnlyFeatureFileDescriptorMap(Feature feature, Consumer<Map<String, ParcelFileDescriptor>> consumer);

    public abstract void onGetVersion(LongConsumer longConsumer);

    public abstract void onInferenceServiceConnected();

    public abstract void onInferenceServiceDisconnected();

    public abstract void onListFeatures(int i, OutcomeReceiver<List<Feature>, OnDeviceIntelligenceException> outcomeReceiver);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    /* renamed from: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1, reason: invalid class name */
    class AnonymousClass1 extends IOnDeviceIntelligenceService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void ready() {
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((OnDeviceIntelligenceService) obj).onReady();
                }
            }, OnDeviceIntelligenceService.this));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getVersion(final RemoteCallback remoteCallback) {
            Objects.requireNonNull(remoteCallback);
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((OnDeviceIntelligenceService) obj).onGetVersion((LongConsumer) obj2);
                }
            }, OnDeviceIntelligenceService.this, new LongConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda6
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    OnDeviceIntelligenceService.AnonymousClass1.lambda$getVersion$0(RemoteCallback.this, j);
                }
            }));
        }

        static /* synthetic */ void lambda$getVersion$0(RemoteCallback remoteCallback, long l) {
            Bundle b = new Bundle();
            b.putLong(OnDeviceIntelligenceManager.API_VERSION_BUNDLE_KEY, l);
            remoteCallback.sendResult(b);
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void listFeatures(int callerUid, IListFeaturesCallback listFeaturesCallback) {
            Objects.requireNonNull(listFeaturesCallback);
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda12
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((OnDeviceIntelligenceService) obj).onListFeatures(((Integer) obj2).intValue(), (OutcomeReceiver) obj3);
                }
            }, OnDeviceIntelligenceService.this, Integer.valueOf(callerUid), OnDeviceIntelligenceService.this.wrapListFeaturesCallback(listFeaturesCallback)));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeature(int callerUid, int id, IFeatureCallback featureCallback) {
            Objects.requireNonNull(featureCallback);
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda9
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((OnDeviceIntelligenceService) obj).onGetFeature(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (OutcomeReceiver) obj4);
                }
            }, OnDeviceIntelligenceService.this, Integer.valueOf(callerUid), Integer.valueOf(id), OnDeviceIntelligenceService.this.wrapFeatureCallback(featureCallback)));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeatureDetails(int callerUid, Feature feature, IFeatureDetailsCallback featureDetailsCallback) {
            Objects.requireNonNull(feature);
            Objects.requireNonNull(featureDetailsCallback);
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda11
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((OnDeviceIntelligenceService) obj).onGetFeatureDetails(((Integer) obj2).intValue(), (Feature) obj3, (OutcomeReceiver) obj4);
                }
            }, OnDeviceIntelligenceService.this, Integer.valueOf(callerUid), feature, OnDeviceIntelligenceService.this.wrapFeatureDetailsCallback(featureDetailsCallback)));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void requestFeatureDownload(int callerUid, Feature feature, AndroidFuture cancellationSignalFuture, IDownloadCallback downloadCallback) {
            Objects.requireNonNull(feature);
            Objects.requireNonNull(downloadCallback);
            ICancellationSignal transport = null;
            if (cancellationSignalFuture != null) {
                transport = CancellationSignal.createTransport();
                cancellationSignalFuture.complete(transport);
            }
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda8
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((OnDeviceIntelligenceService) obj).onDownloadFeature(((Integer) obj2).intValue(), (Feature) obj3, (CancellationSignal) obj4, (DownloadCallback) obj5);
                }
            }, OnDeviceIntelligenceService.this, Integer.valueOf(callerUid), feature, CancellationSignal.fromTransport(transport), OnDeviceIntelligenceService.this.wrapDownloadCallback(downloadCallback)));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFileDescriptor(String fileName, AndroidFuture<ParcelFileDescriptor> future) {
            Objects.requireNonNull(fileName);
            Objects.requireNonNull(future);
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((OnDeviceIntelligenceService) obj).onGetReadOnlyFileDescriptor((String) obj2, (AndroidFuture) obj3);
                }
            }, OnDeviceIntelligenceService.this, fileName, future));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFeatureFileDescriptorMap(Feature feature, final RemoteCallback remoteCallback) {
            Objects.requireNonNull(feature);
            Objects.requireNonNull(remoteCallback);
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((OnDeviceIntelligenceService) obj).onGetReadOnlyFeatureFileDescriptorMap((Feature) obj2, (Consumer) obj3);
                }
            }, OnDeviceIntelligenceService.this, feature, new Consumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OnDeviceIntelligenceService.AnonymousClass1.lambda$getReadOnlyFeatureFileDescriptorMap$1(RemoteCallback.this, (Map) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$getReadOnlyFeatureFileDescriptorMap$1(RemoteCallback remoteCallback, Map parcelFileDescriptorMap) {
            final Bundle bundle = new Bundle();
            Objects.requireNonNull(bundle);
            parcelFileDescriptorMap.forEach(new BiConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda10
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    Bundle.this.putParcelable((String) obj, (ParcelFileDescriptor) obj2);
                }
            });
            remoteCallback.sendResult(bundle);
            OnDeviceIntelligenceService.tryClosePfds(parcelFileDescriptorMap.values());
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void registerRemoteServices(IRemoteProcessingService remoteProcessingService) {
            OnDeviceIntelligenceService.this.mRemoteProcessingService = remoteProcessingService;
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void notifyInferenceServiceConnected() {
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((OnDeviceIntelligenceService) obj).onInferenceServiceConnected();
                }
            }, OnDeviceIntelligenceService.this));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void notifyInferenceServiceDisconnected() {
            OnDeviceIntelligenceService.this.mHandler.executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((OnDeviceIntelligenceService) obj).onInferenceServiceDisconnected();
                }
            }, OnDeviceIntelligenceService.this));
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new AnonymousClass1();
        }
        Slog.w(TAG, "Incorrect service interface, returning null.");
        return null;
    }

    public void onReady() {
    }

    public final void updateProcessingState(Bundle processingState, Executor callbackExecutor, OutcomeReceiver<PersistableBundle, OnDeviceIntelligenceException> statusReceiver) {
        Objects.requireNonNull(callbackExecutor);
        if (this.mRemoteProcessingService == null) {
            throw new IllegalStateException("Remote processing service is unavailable.");
        }
        try {
            this.mRemoteProcessingService.updateProcessingState(processingState, new AnonymousClass2(callbackExecutor, statusReceiver));
        } catch (RemoteException e) {
            Slog.e(TAG, "Error in updateProcessingState: " + e);
            throw new RuntimeException(e);
        }
    }

    /* renamed from: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2, reason: invalid class name */
    class AnonymousClass2 extends IProcessingUpdateStatusCallback.Stub {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ OutcomeReceiver val$statusReceiver;

        AnonymousClass2(Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$statusReceiver = outcomeReceiver;
        }

        @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
        public void onSuccess(final PersistableBundle result) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$statusReceiver;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
        public void onFailure(final int errorCode, final String errorMessage) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$statusReceiver;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onError(new OnDeviceIntelligenceException(r2, r3));
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public OutcomeReceiver<Feature, OnDeviceIntelligenceException> wrapFeatureCallback(final IFeatureCallback featureCallback) {
        return new OutcomeReceiver<Feature, OnDeviceIntelligenceException>() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.3
            @Override // android.os.OutcomeReceiver
            public void onResult(Feature feature) {
                try {
                    featureCallback.onSuccess(feature);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending feature: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(OnDeviceIntelligenceException exception) {
                try {
                    featureCallback.onFailure(exception.getErrorCode(), exception.getMessage(), exception.getErrorParams());
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending download feature: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public OutcomeReceiver<List<Feature>, OnDeviceIntelligenceException> wrapListFeaturesCallback(final IListFeaturesCallback listFeaturesCallback) {
        return new OutcomeReceiver<List<Feature>, OnDeviceIntelligenceException>() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.4
            @Override // android.os.OutcomeReceiver
            public void onResult(List<Feature> features) {
                try {
                    listFeaturesCallback.onSuccess(features);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending feature: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(OnDeviceIntelligenceException exception) {
                try {
                    listFeaturesCallback.onFailure(exception.getErrorCode(), exception.getMessage(), exception.getErrorParams());
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending download feature: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public OutcomeReceiver<FeatureDetails, OnDeviceIntelligenceException> wrapFeatureDetailsCallback(final IFeatureDetailsCallback featureStatusCallback) {
        return new OutcomeReceiver<FeatureDetails, OnDeviceIntelligenceException>() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.5
            @Override // android.os.OutcomeReceiver
            public void onResult(FeatureDetails result) {
                try {
                    featureStatusCallback.onSuccess(result);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending feature status: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(OnDeviceIntelligenceException exception) {
                try {
                    featureStatusCallback.onFailure(exception.getErrorCode(), exception.getMessage(), exception.getErrorParams());
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending feature status: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DownloadCallback wrapDownloadCallback(final IDownloadCallback downloadCallback) {
        return new DownloadCallback() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.6
            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadStarted(long bytesToDownload) {
                try {
                    downloadCallback.onDownloadStarted(bytesToDownload);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadFailed(int failureStatus, String errorMessage, PersistableBundle errorParams) {
                try {
                    downloadCallback.onDownloadFailed(failureStatus, errorMessage, errorParams);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadProgress(long totalBytesDownloaded) {
                try {
                    downloadCallback.onDownloadProgress(totalBytesDownloaded);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadCompleted(PersistableBundle persistableBundle) {
                try {
                    downloadCallback.onDownloadCompleted(persistableBundle);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void tryClosePfds(Collection<ParcelFileDescriptor> pfds) {
        pfds.forEach(new Consumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                OnDeviceIntelligenceService.lambda$tryClosePfds$0((ParcelFileDescriptor) obj);
            }
        });
    }

    static /* synthetic */ void lambda$tryClosePfds$0(ParcelFileDescriptor pfd) {
        try {
            pfd.close();
        } catch (Exception e) {
            Log.w(TAG, "Error closing FD", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetReadOnlyFileDescriptor(final String fileName, final AndroidFuture<ParcelFileDescriptor> future) {
        Slog.v(TAG, "onGetReadOnlyFileDescriptor " + fileName);
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
                OnDeviceIntelligenceService.this.lambda$onGetReadOnlyFileDescriptor$1(fileName, future);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGetReadOnlyFileDescriptor$1(String fileName, AndroidFuture future) throws Exception {
        Slog.v(TAG, "onGetReadOnlyFileDescriptor: " + fileName + " under internal app storage.");
        File f = new File(getBaseContext().getFilesDir(), fileName);
        if (!f.exists()) {
            f = new File(fileName);
        }
        ParcelFileDescriptor pfd = null;
        try {
            try {
                pfd = ParcelFileDescriptor.open(f, 268435456);
                Slog.d(TAG, "Successfully opened a file with ParcelFileDescriptor.");
                future.complete(pfd);
                if (pfd == null) {
                    return;
                }
            } catch (FileNotFoundException e) {
                Slog.e(TAG, "Cannot open file. No ParcelFileDescriptor returned.");
                future.completeExceptionally(e);
                future.complete(pfd);
                if (pfd == null) {
                    return;
                }
            }
            pfd.close();
        } catch (Throwable th) {
            future.complete(pfd);
            if (pfd != null) {
                pfd.close();
            }
            throw th;
        }
    }
}
