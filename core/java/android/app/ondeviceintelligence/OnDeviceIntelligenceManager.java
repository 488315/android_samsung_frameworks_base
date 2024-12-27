package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FunctionalUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

@SystemApi
public final class OnDeviceIntelligenceManager {
    public static final String API_VERSION_BUNDLE_KEY = "ApiVersionBundleKey";
    public static final String AUGMENT_REQUEST_CONTENT_BUNDLE_KEY =
            "AugmentRequestContentBundleKey";
    public static final int REQUEST_TYPE_EMBEDDINGS = 2;
    public static final int REQUEST_TYPE_INFERENCE = 0;
    public static final int REQUEST_TYPE_PREPARE = 1;
    private static final String TAG = "OnDeviceIntelligence";
    private final Context mContext;
    private final IOnDeviceIntelligenceManager mService;

    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
    public @interface InferenceParams {}

    @Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestType {}

    @Target({ElementType.PARAMETER, ElementType.FIELD})
    public @interface ResponseParams {}

    @Target({ElementType.PARAMETER, ElementType.FIELD})
    public @interface StateParams {}

    public OnDeviceIntelligenceManager(Context context, IOnDeviceIntelligenceManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public void getVersion(final Executor callbackExecutor, final LongConsumer versionConsumer) {
        try {
            RemoteCallback callback =
                    new RemoteCallback(
                            new RemoteCallback.OnResultListener() { // from class:
                                // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda1
                                @Override // android.os.RemoteCallback.OnResultListener
                                public final void onResult(Bundle bundle) {
                                    OnDeviceIntelligenceManager.lambda$getVersion$4(
                                            callbackExecutor, versionConsumer, bundle);
                                }
                            });
            this.mService.getVersion(callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$getVersion$4(
            final Executor callbackExecutor, final LongConsumer versionConsumer, Bundle result) {
        if (result == null) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            callbackExecutor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            r1.accept(0L);
                                        }
                                    });
                        }
                    });
        }
        final long version = result.getLong(API_VERSION_BUNDLE_KEY);
        Binder.withCleanCallingIdentity(
                new FunctionalUtils.ThrowingRunnable() { // from class:
                    // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda6
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        callbackExecutor.execute(
                                new Runnable() { // from class:
                                    // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        r1.accept(r2);
                                    }
                                });
                    }
                });
    }

    public String getRemoteServicePackageName() {
        try {
            String result = this.mService.getRemoteServicePackageName();
            return result;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1, reason: invalid class name */
    class AnonymousClass1 extends IFeatureCallback.Stub {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ OutcomeReceiver val$featureReceiver;

        AnonymousClass1(Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$featureReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onSuccess(final Feature result) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$featureReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onResult(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onFailure(
                final int errorCode,
                final String errorMessage,
                final PersistableBundle errorParams) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$featureReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onError(
                                                    new OnDeviceIntelligenceException(r2, r3, r4));
                                        }
                                    });
                        }
                    });
        }
    }

    public void getFeature(
            int featureId,
            Executor callbackExecutor,
            OutcomeReceiver<Feature, OnDeviceIntelligenceException> featureReceiver) {
        try {
            IFeatureCallback callback = new AnonymousClass1(callbackExecutor, featureReceiver);
            this.mService.getFeature(featureId, callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2, reason: invalid class name */
    class AnonymousClass2 extends IListFeaturesCallback.Stub {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ OutcomeReceiver val$featureListReceiver;

        AnonymousClass2(Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$featureListReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.IListFeaturesCallback
        public void onSuccess(final List<Feature> result) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$featureListReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onResult(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IListFeaturesCallback
        public void onFailure(
                final int errorCode,
                final String errorMessage,
                final PersistableBundle errorParams) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$featureListReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onError(
                                                    new OnDeviceIntelligenceException(r2, r3, r4));
                                        }
                                    });
                        }
                    });
        }
    }

    public void listFeatures(
            Executor callbackExecutor,
            OutcomeReceiver<List<Feature>, OnDeviceIntelligenceException> featureListReceiver) {
        try {
            IListFeaturesCallback callback =
                    new AnonymousClass2(callbackExecutor, featureListReceiver);
            this.mService.listFeatures(callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3, reason: invalid class name */
    class AnonymousClass3 extends IFeatureDetailsCallback.Stub {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ OutcomeReceiver val$featureDetailsReceiver;

        AnonymousClass3(Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$featureDetailsReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.IFeatureDetailsCallback
        public void onSuccess(final FeatureDetails result) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$featureDetailsReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onResult(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IFeatureDetailsCallback
        public void onFailure(
                final int errorCode,
                final String errorMessage,
                final PersistableBundle errorParams) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$featureDetailsReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onError(
                                                    new OnDeviceIntelligenceException(r2, r3, r4));
                                        }
                                    });
                        }
                    });
        }
    }

    public void getFeatureDetails(
            Feature feature,
            Executor callbackExecutor,
            OutcomeReceiver<FeatureDetails, OnDeviceIntelligenceException> featureDetailsReceiver) {
        try {
            IFeatureDetailsCallback callback =
                    new AnonymousClass3(callbackExecutor, featureDetailsReceiver);
            this.mService.getFeatureDetails(feature, callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4, reason: invalid class name */
    class AnonymousClass4 extends IDownloadCallback.Stub {
        final /* synthetic */ DownloadCallback val$callback;
        final /* synthetic */ Executor val$callbackExecutor;

        AnonymousClass4(Executor executor, DownloadCallback downloadCallback) {
            this.val$callbackExecutor = executor;
            this.val$callback = downloadCallback;
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadStarted(final long bytesToDownload) {
            final Executor executor = this.val$callbackExecutor;
            final DownloadCallback downloadCallback = this.val$callback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda6
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda5
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            DownloadCallback.this.onDownloadStarted(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadProgress(final long bytesDownloaded) {
            final Executor executor = this.val$callbackExecutor;
            final DownloadCallback downloadCallback = this.val$callback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda7
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda4
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            DownloadCallback.this.onDownloadProgress(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadFailed(
                final int failureStatus,
                final String errorMessage,
                final PersistableBundle errorParams) {
            final Executor executor = this.val$callbackExecutor;
            final DownloadCallback downloadCallback = this.val$callback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            DownloadCallback.this.onDownloadFailed(r2, r3, r4);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadCompleted(final PersistableBundle downloadParams) {
            final Executor executor = this.val$callbackExecutor;
            final DownloadCallback downloadCallback = this.val$callback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            DownloadCallback.this.onDownloadCompleted(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestFeatureDownload(
            Feature feature,
            CancellationSignal cancellationSignal,
            Executor callbackExecutor,
            DownloadCallback callback) {
        try {
            IDownloadCallback downloadCallback = new AnonymousClass4(callbackExecutor, callback);
            this.mService.requestFeatureDownload(
                    feature,
                    configureRemoteCancellationFuture(cancellationSignal, callbackExecutor),
                    downloadCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5, reason: invalid class name */
    class AnonymousClass5 extends ITokenInfoCallback.Stub {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ OutcomeReceiver val$outcomeReceiver;

        AnonymousClass5(Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$outcomeReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.ITokenInfoCallback
        public void onSuccess(final TokenInfo tokenInfo) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$outcomeReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onResult(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.ITokenInfoCallback
        public void onFailure(
                final int errorCode,
                final String errorMessage,
                final PersistableBundle errorParams) {
            final Executor executor = this.val$callbackExecutor;
            final OutcomeReceiver outcomeReceiver = this.val$outcomeReceiver;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OutcomeReceiver.this.onError(
                                                    new OnDeviceIntelligenceException(r2, r3, r4));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestTokenInfo(
            Feature feature,
            Bundle request,
            CancellationSignal cancellationSignal,
            Executor callbackExecutor,
            OutcomeReceiver<TokenInfo, OnDeviceIntelligenceException> outcomeReceiver) {
        try {
            ITokenInfoCallback callback = new AnonymousClass5(callbackExecutor, outcomeReceiver);
            this.mService.requestTokenInfo(
                    feature,
                    request,
                    configureRemoteCancellationFuture(cancellationSignal, callbackExecutor),
                    callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6, reason: invalid class name */
    class AnonymousClass6 extends IResponseCallback.Stub {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ ProcessingCallback val$processingCallback;

        AnonymousClass6(Executor executor, ProcessingCallback processingCallback) {
            this.val$callbackExecutor = executor;
            this.val$processingCallback = processingCallback;
        }

        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onSuccess(final Bundle result) {
            final Executor executor = this.val$callbackExecutor;
            final ProcessingCallback processingCallback = this.val$processingCallback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda7
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ProcessingCallback.this.onResult(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onFailure(
                final int errorCode,
                final String errorMessage,
                final PersistableBundle errorParams) {
            final Executor executor = this.val$callbackExecutor;
            final ProcessingCallback processingCallback = this.val$processingCallback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ProcessingCallback.this.onError(
                                                    new OnDeviceIntelligenceException(r2, r3, r4));
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onDataAugmentRequest(
                final Bundle request, final RemoteCallback contentCallback) {
            final Executor executor = this.val$callbackExecutor;
            final ProcessingCallback processingCallback = this.val$processingCallback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r0.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda4
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ProcessingCallback.this.onDataAugmentRequest(
                                                    r2,
                                                    new Consumer() { // from class:
                                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda2
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj) {
                                                            OnDeviceIntelligenceManager
                                                                    .AnonymousClass6
                                                                    .lambda$onDataAugmentRequest$5(
                                                                            r1, r2, (Bundle) obj);
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }

        static /* synthetic */ void lambda$onDataAugmentRequest$5(
                Executor callbackExecutor, final RemoteCallback contentCallback, Bundle result) {
            final Bundle bundle = new Bundle();
            bundle.putParcelable(
                    OnDeviceIntelligenceManager.AUGMENT_REQUEST_CONTENT_BUNDLE_KEY, result);
            callbackExecutor.execute(
                    new Runnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteCallback.this.sendResult(bundle);
                        }
                    });
        }
    }

    public void processRequest(
            Feature feature,
            Bundle request,
            int requestType,
            CancellationSignal cancellationSignal,
            ProcessingSignal processingSignal,
            Executor callbackExecutor,
            ProcessingCallback processingCallback) {
        try {
            IResponseCallback callback = new AnonymousClass6(callbackExecutor, processingCallback);
            this.mService.processRequest(
                    feature,
                    request,
                    requestType,
                    configureRemoteCancellationFuture(cancellationSignal, callbackExecutor),
                    configureRemoteProcessingSignalFuture(processingSignal, callbackExecutor),
                    callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7, reason: invalid class name */
    class AnonymousClass7 extends IStreamingResponseCallback.Stub {
        final /* synthetic */ Executor val$callbackExecutor;
        final /* synthetic */ StreamingProcessingCallback val$streamingProcessingCallback;

        AnonymousClass7(
                Executor executor, StreamingProcessingCallback streamingProcessingCallback) {
            this.val$callbackExecutor = executor;
            this.val$streamingProcessingCallback = streamingProcessingCallback;
        }

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onNewContent(final Bundle result) {
            final Executor executor = this.val$callbackExecutor;
            final StreamingProcessingCallback streamingProcessingCallback =
                    this.val$streamingProcessingCallback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda5
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            StreamingProcessingCallback.this.onPartialResult(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onSuccess(final Bundle result) {
            final Executor executor = this.val$callbackExecutor;
            final StreamingProcessingCallback streamingProcessingCallback =
                    this.val$streamingProcessingCallback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            StreamingProcessingCallback.this.onResult(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onFailure(
                final int errorCode,
                final String errorMessage,
                final PersistableBundle errorParams) {
            final Executor executor = this.val$callbackExecutor;
            final StreamingProcessingCallback streamingProcessingCallback =
                    this.val$streamingProcessingCallback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda8
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            executor.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda6
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            StreamingProcessingCallback.this.onError(
                                                    new OnDeviceIntelligenceException(r2, r3, r4));
                                        }
                                    });
                        }
                    });
        }

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onDataAugmentRequest(
                final Bundle content, final RemoteCallback contentCallback) {
            final Executor executor = this.val$callbackExecutor;
            final StreamingProcessingCallback streamingProcessingCallback =
                    this.val$streamingProcessingCallback;
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r0.execute(
                                    new Runnable() { // from class:
                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda9
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            StreamingProcessingCallback.this.onDataAugmentRequest(
                                                    r2,
                                                    new Consumer() { // from class:
                                                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda7
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj) {
                                                            OnDeviceIntelligenceManager
                                                                    .AnonymousClass7
                                                                    .lambda$onDataAugmentRequest$7(
                                                                            r1, r2, (Bundle) obj);
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }

        static /* synthetic */ void lambda$onDataAugmentRequest$7(
                Executor callbackExecutor,
                final RemoteCallback contentCallback,
                Bundle contentResponse) {
            final Bundle bundle = new Bundle();
            bundle.putParcelable(
                    OnDeviceIntelligenceManager.AUGMENT_REQUEST_CONTENT_BUNDLE_KEY,
                    contentResponse);
            callbackExecutor.execute(
                    new Runnable() { // from class:
                        // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteCallback.this.sendResult(bundle);
                        }
                    });
        }
    }

    public void processRequestStreaming(
            Feature feature,
            Bundle request,
            int requestType,
            CancellationSignal cancellationSignal,
            ProcessingSignal processingSignal,
            Executor callbackExecutor,
            StreamingProcessingCallback streamingProcessingCallback) {
        try {
            IStreamingResponseCallback callback =
                    new AnonymousClass7(callbackExecutor, streamingProcessingCallback);
            this.mService.processRequestStreaming(
                    feature,
                    request,
                    requestType,
                    configureRemoteCancellationFuture(cancellationSignal, callbackExecutor),
                    configureRemoteProcessingSignalFuture(processingSignal, callbackExecutor),
                    callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<InferenceInfo> getLatestInferenceInfo(long startTimeEpochMillis) {
        try {
            return this.mService.getLatestInferenceInfo(startTimeEpochMillis);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static AndroidFuture<IBinder> configureRemoteCancellationFuture(
            final CancellationSignal cancellationSignal, Executor callbackExecutor) {
        if (cancellationSignal == null) {
            return null;
        }
        AndroidFuture<IBinder> cancellationFuture = new AndroidFuture<>();
        cancellationFuture.whenCompleteAsync(
                new BiConsumer() { // from class:
                    // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        OnDeviceIntelligenceManager.lambda$configureRemoteCancellationFuture$5(
                                CancellationSignal.this, (IBinder) obj, (Throwable) obj2);
                    }
                },
                callbackExecutor);
        return cancellationFuture;
    }

    static /* synthetic */ void lambda$configureRemoteCancellationFuture$5(
            CancellationSignal cancellationSignal, IBinder cancellationTransport, Throwable error) {
        if (error != null || cancellationTransport == null) {
            Log.e(TAG, "Unable to receive the remote cancellation signal.", error);
        } else {
            cancellationSignal.setRemote(
                    ICancellationSignal.Stub.asInterface(cancellationTransport));
        }
    }

    private static AndroidFuture<IBinder> configureRemoteProcessingSignalFuture(
            final ProcessingSignal processingSignal, Executor executor) {
        if (processingSignal == null) {
            return null;
        }
        AndroidFuture<IBinder> processingSignalFuture = new AndroidFuture<>();
        processingSignalFuture.whenCompleteAsync(
                new BiConsumer() { // from class:
                    // android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        OnDeviceIntelligenceManager.lambda$configureRemoteProcessingSignalFuture$6(
                                ProcessingSignal.this, (IBinder) obj, (Throwable) obj2);
                    }
                },
                executor);
        return processingSignalFuture;
    }

    static /* synthetic */ void lambda$configureRemoteProcessingSignalFuture$6(
            ProcessingSignal processingSignal, IBinder transport, Throwable error) {
        if (error != null || transport == null) {
            Log.e(TAG, "Unable to receive the remote processing signal.", error);
        } else {
            processingSignal.setRemote(IProcessingSignal.Stub.asInterface(transport));
        }
    }
}
