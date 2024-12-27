package android.service.ondeviceintelligence;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.ondeviceintelligence.Feature;
import android.app.ondeviceintelligence.IProcessingSignal;
import android.app.ondeviceintelligence.IResponseCallback;
import android.app.ondeviceintelligence.IStreamingResponseCallback;
import android.app.ondeviceintelligence.ITokenInfoCallback;
import android.app.ondeviceintelligence.OnDeviceIntelligenceException;
import android.app.ondeviceintelligence.OnDeviceIntelligenceManager;
import android.app.ondeviceintelligence.ProcessingCallback;
import android.app.ondeviceintelligence.ProcessingSignal;
import android.app.ondeviceintelligence.StreamingProcessingCallback;
import android.app.ondeviceintelligence.TokenInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
public abstract class OnDeviceSandboxedInferenceService extends Service {
    public static final String DEVICE_CONFIG_UPDATE_BUNDLE_KEY = "device_config_update";
    public static final String INFERENCE_INFO_BUNDLE_KEY = "inference_info";
    public static final String MODEL_LOADED_BUNDLE_KEY = "model_loaded";
    public static final String MODEL_UNLOADED_BUNDLE_KEY = "model_unloaded";
    public static final String REGISTER_MODEL_UPDATE_CALLBACK_BUNDLE_KEY =
            "register_model_update_callback";
    public static final String SERVICE_INTERFACE =
            "android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService";
    private static final String TAG = OnDeviceSandboxedInferenceService.class.getSimpleName();
    private Handler mHandler;
    private IRemoteStorageService mRemoteStorageService;

    public abstract void onProcessRequest(
            int i,
            Feature feature,
            Bundle bundle,
            int i2,
            CancellationSignal cancellationSignal,
            ProcessingSignal processingSignal,
            ProcessingCallback processingCallback);

    public abstract void onProcessRequestStreaming(
            int i,
            Feature feature,
            Bundle bundle,
            int i2,
            CancellationSignal cancellationSignal,
            ProcessingSignal processingSignal,
            StreamingProcessingCallback streamingProcessingCallback);

    public abstract void onTokenInfoRequest(
            int i,
            Feature feature,
            Bundle bundle,
            CancellationSignal cancellationSignal,
            OutcomeReceiver<TokenInfo, OnDeviceIntelligenceException> outcomeReceiver);

    public abstract void onUpdateProcessingState(
            Bundle bundle,
            OutcomeReceiver<PersistableBundle, OnDeviceIntelligenceException> outcomeReceiver);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new IOnDeviceSandboxedInferenceService
                    .Stub() { // from class:
                              // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService.1
                @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
                public void registerRemoteStorageService(
                        IRemoteStorageService storageService, IRemoteCallback remoteCallback)
                        throws RemoteException {
                    Objects.requireNonNull(storageService);
                    OnDeviceSandboxedInferenceService.this.mRemoteStorageService = storageService;
                    remoteCallback.sendResult(Bundle.EMPTY);
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
                public void requestTokenInfo(
                        int callerUid,
                        Feature feature,
                        Bundle request,
                        AndroidFuture cancellationSignalFuture,
                        ITokenInfoCallback tokenInfoCallback) {
                    Objects.requireNonNull(feature);
                    Objects.requireNonNull(tokenInfoCallback);
                    ICancellationSignal transport = null;
                    if (cancellationSignalFuture != null) {
                        transport = CancellationSignal.createTransport();
                        cancellationSignalFuture.complete(transport);
                    }
                    OnDeviceSandboxedInferenceService.this.mHandler.executeOrSendMessage(
                            PooledLambda.obtainMessage(
                                    new HexConsumer() { // from class:
                                                        // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$1$$ExternalSyntheticLambda2
                                        @Override // com.android.internal.util.function.HexConsumer
                                        public final void accept(
                                                Object obj,
                                                Object obj2,
                                                Object obj3,
                                                Object obj4,
                                                Object obj5,
                                                Object obj6) {
                                            ((OnDeviceSandboxedInferenceService) obj)
                                                    .onTokenInfoRequest(
                                                            ((Integer) obj2).intValue(),
                                                            (Feature) obj3,
                                                            (Bundle) obj4,
                                                            (CancellationSignal) obj5,
                                                            (OutcomeReceiver) obj6);
                                        }
                                    },
                                    OnDeviceSandboxedInferenceService.this,
                                    Integer.valueOf(callerUid),
                                    feature,
                                    request,
                                    CancellationSignal.fromTransport(transport),
                                    OnDeviceSandboxedInferenceService.this.wrapTokenInfoCallback(
                                            tokenInfoCallback)));
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
                public void processRequestStreaming(
                        int callerUid,
                        Feature feature,
                        Bundle request,
                        int requestType,
                        AndroidFuture cancellationSignalFuture,
                        AndroidFuture processingSignalFuture,
                        IStreamingResponseCallback callback) {
                    Objects.requireNonNull(feature);
                    Objects.requireNonNull(callback);
                    ICancellationSignal transport = null;
                    if (cancellationSignalFuture != null) {
                        transport = CancellationSignal.createTransport();
                        cancellationSignalFuture.complete(transport);
                    }
                    IProcessingSignal processingSignalTransport = null;
                    if (processingSignalFuture != null) {
                        processingSignalTransport = ProcessingSignal.createTransport();
                        processingSignalFuture.complete(processingSignalTransport);
                    }
                    OnDeviceSandboxedInferenceService.this.mHandler.executeOrSendMessage(
                            PooledLambda.obtainMessage(
                                    new OctConsumer() { // from class:
                                                        // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$1$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.function.OctConsumer
                                        public final void accept(
                                                Object obj,
                                                Object obj2,
                                                Object obj3,
                                                Object obj4,
                                                Object obj5,
                                                Object obj6,
                                                Object obj7,
                                                Object obj8) {
                                            ((OnDeviceSandboxedInferenceService) obj)
                                                    .onProcessRequestStreaming(
                                                            ((Integer) obj2).intValue(),
                                                            (Feature) obj3,
                                                            (Bundle) obj4,
                                                            ((Integer) obj5).intValue(),
                                                            (CancellationSignal) obj6,
                                                            (ProcessingSignal) obj7,
                                                            (StreamingProcessingCallback) obj8);
                                        }
                                    },
                                    OnDeviceSandboxedInferenceService.this,
                                    Integer.valueOf(callerUid),
                                    feature,
                                    request,
                                    Integer.valueOf(requestType),
                                    CancellationSignal.fromTransport(transport),
                                    ProcessingSignal.fromTransport(processingSignalTransport),
                                    OnDeviceSandboxedInferenceService.this
                                            .wrapStreamingResponseCallback(callback)));
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
                public void processRequest(
                        int callerUid,
                        Feature feature,
                        Bundle request,
                        int requestType,
                        AndroidFuture cancellationSignalFuture,
                        AndroidFuture processingSignalFuture,
                        IResponseCallback callback) {
                    Objects.requireNonNull(feature);
                    Objects.requireNonNull(callback);
                    ICancellationSignal transport = null;
                    if (cancellationSignalFuture != null) {
                        transport = CancellationSignal.createTransport();
                        cancellationSignalFuture.complete(transport);
                    }
                    IProcessingSignal processingSignalTransport = null;
                    if (processingSignalFuture != null) {
                        processingSignalTransport = ProcessingSignal.createTransport();
                        processingSignalFuture.complete(processingSignalTransport);
                    }
                    OnDeviceSandboxedInferenceService.this.mHandler.executeOrSendMessage(
                            PooledLambda.obtainMessage(
                                    new OctConsumer() { // from class:
                                                        // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$1$$ExternalSyntheticLambda3
                                        @Override // com.android.internal.util.function.OctConsumer
                                        public final void accept(
                                                Object obj,
                                                Object obj2,
                                                Object obj3,
                                                Object obj4,
                                                Object obj5,
                                                Object obj6,
                                                Object obj7,
                                                Object obj8) {
                                            ((OnDeviceSandboxedInferenceService) obj)
                                                    .onProcessRequest(
                                                            ((Integer) obj2).intValue(),
                                                            (Feature) obj3,
                                                            (Bundle) obj4,
                                                            ((Integer) obj5).intValue(),
                                                            (CancellationSignal) obj6,
                                                            (ProcessingSignal) obj7,
                                                            (ProcessingCallback) obj8);
                                        }
                                    },
                                    OnDeviceSandboxedInferenceService.this,
                                    Integer.valueOf(callerUid),
                                    feature,
                                    request,
                                    Integer.valueOf(requestType),
                                    CancellationSignal.fromTransport(transport),
                                    ProcessingSignal.fromTransport(processingSignalTransport),
                                    OnDeviceSandboxedInferenceService.this.wrapResponseCallback(
                                            callback)));
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
                public void updateProcessingState(
                        Bundle processingState, IProcessingUpdateStatusCallback callback) {
                    Objects.requireNonNull(processingState);
                    Objects.requireNonNull(callback);
                    OnDeviceSandboxedInferenceService.this.mHandler.executeOrSendMessage(
                            PooledLambda.obtainMessage(
                                    new TriConsumer() { // from class:
                                                        // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$1$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.function.TriConsumer
                                        public final void accept(
                                                Object obj, Object obj2, Object obj3) {
                                            ((OnDeviceSandboxedInferenceService) obj)
                                                    .onUpdateProcessingState(
                                                            (Bundle) obj2, (OutcomeReceiver) obj3);
                                        }
                                    },
                                    OnDeviceSandboxedInferenceService.this,
                                    processingState,
                                    OnDeviceSandboxedInferenceService.wrapOutcomeReceiver(
                                            callback)));
                }
            };
        }
        Slog.w(TAG, "Incorrect service interface, returning null.");
        return null;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final FileInputStream openFileInput(String filename) throws FileNotFoundException {
        try {
            AndroidFuture<ParcelFileDescriptor> future = new AndroidFuture<>();
            this.mRemoteStorageService.getReadOnlyFileDescriptor(filename, future);
            ParcelFileDescriptor pfd = future.get();
            return new FileInputStream(pfd.getFileDescriptor());
        } catch (RemoteException | InterruptedException | ExecutionException e) {
            Log.w(TAG, "Cannot open file due to remote service failure");
            throw new FileNotFoundException(e.getMessage());
        }
    }

    public final void getReadOnlyFileDescriptor(
            final String fileName,
            final Executor executor,
            final Consumer<ParcelFileDescriptor> resultConsumer)
            throws FileNotFoundException {
        AndroidFuture<ParcelFileDescriptor> future = new AndroidFuture<>();
        try {
            this.mRemoteStorageService.getReadOnlyFileDescriptor(fileName, future);
            future.whenCompleteAsync(
                    new BiConsumer() { // from class:
                                       // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda9
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            OnDeviceSandboxedInferenceService.lambda$getReadOnlyFileDescriptor$2(
                                    fileName,
                                    executor,
                                    resultConsumer,
                                    (ParcelFileDescriptor) obj,
                                    (Throwable) obj2);
                        }
                    },
                    executor);
        } catch (RemoteException e) {
            Log.w(TAG, "Cannot open file due to remote service failure");
            throw new FileNotFoundException(e.getMessage());
        }
    }

    static /* synthetic */ void lambda$getReadOnlyFileDescriptor$2(
            String fileName,
            Executor executor,
            final Consumer resultConsumer,
            final ParcelFileDescriptor pfd,
            Throwable err) {
        if (err != null) {
            Log.e(TAG, "Failure when reading file: " + fileName + err);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            resultConsumer.accept(null);
                        }
                    });
        } else {
            executor.execute(
                    new Runnable() { // from class:
                                     // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            resultConsumer.accept(pfd);
                        }
                    });
        }
    }

    public final void fetchFeatureFileDescriptorMap(
            Feature feature,
            Executor executor,
            Consumer<Map<String, ParcelFileDescriptor>> resultConsumer) {
        try {
            this.mRemoteStorageService.getReadOnlyFeatureFileDescriptorMap(
                    feature, wrapAsRemoteCallback(resultConsumer, executor));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Executor getCallbackExecutor() {
        return new HandlerExecutor(Handler.createAsync(getMainLooper()));
    }

    private RemoteCallback wrapAsRemoteCallback(
            final Consumer<Map<String, ParcelFileDescriptor>> resultConsumer,
            final Executor executor) {
        return new RemoteCallback(
                new RemoteCallback
                        .OnResultListener() { // from class:
                                              // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda4
                    @Override // android.os.RemoteCallback.OnResultListener
                    public final void onResult(Bundle bundle) {
                        OnDeviceSandboxedInferenceService.lambda$wrapAsRemoteCallback$6(
                                executor, resultConsumer, bundle);
                    }
                });
    }

    static /* synthetic */ void lambda$wrapAsRemoteCallback$6(
            Executor executor, final Consumer resultConsumer, final Bundle result) {
        if (result == null) {
            executor.execute(
                    new Runnable() { // from class:
                                     // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            resultConsumer.accept(new HashMap());
                        }
                    });
            return;
        }
        final Map<String, ParcelFileDescriptor> pfdMap = new HashMap<>();
        result.keySet()
                .forEach(
                        new Consumer() { // from class:
                                         // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                pfdMap.put(
                                        r3,
                                        (ParcelFileDescriptor)
                                                result.getParcelable(
                                                        (String) obj, ParcelFileDescriptor.class));
                            }
                        });
        executor.execute(
                new Runnable() { // from class:
                                 // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        resultConsumer.accept(pfdMap);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ProcessingCallback wrapResponseCallback(final IResponseCallback callback) {
        return new ProcessingCallback() { // from class:
                                          // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService.2
            @Override // android.app.ondeviceintelligence.ProcessingCallback
            public void onResult(Bundle result) {
                try {
                    callback.onSuccess(result);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.ProcessingCallback
            public void onError(OnDeviceIntelligenceException exception) {
                try {
                    callback.onFailure(
                            exception.getErrorCode(),
                            exception.getMessage(),
                            exception.getErrorParams());
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.ProcessingCallback
            public void onDataAugmentRequest(Bundle content, Consumer<Bundle> contentCallback) {
                try {
                    callback.onDataAugmentRequest(
                            content,
                            OnDeviceSandboxedInferenceService.this.wrapRemoteCallback(
                                    contentCallback));
                } catch (RemoteException e) {
                    Slog.e(
                            OnDeviceSandboxedInferenceService.TAG,
                            "Error sending augment request: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public StreamingProcessingCallback wrapStreamingResponseCallback(
            final IStreamingResponseCallback callback) {
        return new StreamingProcessingCallback() { // from class:
                                                   // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService.3
            @Override // android.app.ondeviceintelligence.StreamingProcessingCallback
            public void onPartialResult(Bundle partialResult) {
                try {
                    callback.onNewContent(partialResult);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.ProcessingCallback
            public void onResult(Bundle result) {
                try {
                    callback.onSuccess(result);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.ProcessingCallback
            public void onError(OnDeviceIntelligenceException exception) {
                try {
                    callback.onFailure(
                            exception.getErrorCode(),
                            exception.getMessage(),
                            exception.getErrorParams());
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.ProcessingCallback
            public void onDataAugmentRequest(Bundle content, Consumer<Bundle> contentCallback) {
                try {
                    callback.onDataAugmentRequest(
                            content,
                            OnDeviceSandboxedInferenceService.this.wrapRemoteCallback(
                                    contentCallback));
                } catch (RemoteException e) {
                    Slog.e(
                            OnDeviceSandboxedInferenceService.TAG,
                            "Error sending augment request: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RemoteCallback wrapRemoteCallback(final Consumer<Bundle> contentCallback) {
        return new RemoteCallback(
                new RemoteCallback
                        .OnResultListener() { // from class:
                                              // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda3
                    @Override // android.os.RemoteCallback.OnResultListener
                    public final void onResult(Bundle bundle) {
                        OnDeviceSandboxedInferenceService.this.lambda$wrapRemoteCallback$9(
                                contentCallback, bundle);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$wrapRemoteCallback$9(
            final Consumer contentCallback, final Bundle result) {
        if (result != null) {
            getCallbackExecutor()
                    .execute(
                            new Runnable() { // from class:
                                             // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    contentCallback.accept(
                                            (Bundle)
                                                    result.getParcelable(
                                                            OnDeviceIntelligenceManager
                                                                    .AUGMENT_REQUEST_CONTENT_BUNDLE_KEY,
                                                            Bundle.class));
                                }
                            });
        } else {
            getCallbackExecutor()
                    .execute(
                            new Runnable() { // from class:
                                             // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    contentCallback.accept(null);
                                }
                            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public OutcomeReceiver<TokenInfo, OnDeviceIntelligenceException> wrapTokenInfoCallback(
            final ITokenInfoCallback tokenInfoCallback) {
        return new OutcomeReceiver<
                TokenInfo,
                OnDeviceIntelligenceException>() { // from class:
                                                   // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService.4
            @Override // android.os.OutcomeReceiver
            public void onResult(TokenInfo tokenInfo) {
                try {
                    tokenInfoCallback.onSuccess(tokenInfo);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(OnDeviceIntelligenceException exception) {
                try {
                    tokenInfoCallback.onFailure(
                            exception.getErrorCode(),
                            exception.getMessage(),
                            exception.getErrorParams());
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending failure: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static OutcomeReceiver<PersistableBundle, OnDeviceIntelligenceException>
            wrapOutcomeReceiver(final IProcessingUpdateStatusCallback callback) {
        return new OutcomeReceiver<
                PersistableBundle,
                OnDeviceIntelligenceException>() { // from class:
                                                   // android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService.5
            @Override // android.os.OutcomeReceiver
            public void onResult(PersistableBundle result) {
                try {
                    IProcessingUpdateStatusCallback.this.onSuccess(result);
                } catch (RemoteException e) {
                    Slog.e(OnDeviceSandboxedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(OnDeviceIntelligenceException error) {
                try {
                    IProcessingUpdateStatusCallback.this.onFailure(
                            error.getErrorCode(), error.getMessage());
                } catch (RemoteException e) {
                    Slog.e(
                            OnDeviceSandboxedInferenceService.TAG,
                            "Error sending exception details: " + e);
                }
            }
        };
    }
}
