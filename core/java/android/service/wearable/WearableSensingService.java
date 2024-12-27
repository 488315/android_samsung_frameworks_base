package android.service.wearable;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.ambientcontext.AmbientContextEventRequest;
import android.app.wearable.IWearableSensingCallback;
import android.app.wearable.WearableSensingDataRequest;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.ambientcontext.AmbientContextDetectionResult;
import android.service.ambientcontext.AmbientContextDetectionServiceStatus;
import android.service.voice.HotwordAudioStream;
import android.service.wearable.IWearableSensingService;
import android.service.wearable.WearableSensingService;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.hidden_from_bootclasspath.android.app.wearable.Flags;
import com.android.internal.infra.AndroidFuture;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class WearableSensingService extends Service {
    public static final String HOTWORD_AUDIO_STREAM_BUNDLE_KEY = "android.app.wearable.HotwordAudioStreamBundleKey";
    public static final String SERVICE_INTERFACE = "android.service.wearable.WearableSensingService";
    public static final String STATUS_RESPONSE_BUNDLE_KEY = "android.app.wearable.WearableSensingStatusBundleKey";
    private final SparseArray<WearableSensingDataRequester> mDataRequestObserverIdToRequesterMap = new SparseArray<>();
    private IWearableSensingCallback mWearableSensingCallback;
    private static final String TAG = WearableSensingService.class.getSimpleName();
    private static final Duration OPEN_FILE_TIMEOUT = Duration.ofSeconds(5);

    public abstract void onDataProvided(PersistableBundle persistableBundle, SharedMemory sharedMemory, Consumer<Integer> consumer);

    public abstract void onDataStreamProvided(ParcelFileDescriptor parcelFileDescriptor, Consumer<Integer> consumer);

    public abstract void onQueryServiceStatus(Set<Integer> set, String str, Consumer<AmbientContextDetectionServiceStatus> consumer);

    public abstract void onStartDetection(AmbientContextEventRequest ambientContextEventRequest, String str, Consumer<AmbientContextDetectionServiceStatus> consumer, Consumer<AmbientContextDetectionResult> consumer2);

    public abstract void onStopDetection(String str);

    /* renamed from: android.service.wearable.WearableSensingService$1, reason: invalid class name */
    class AnonymousClass1 extends IWearableSensingService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideSecureConnection(ParcelFileDescriptor secureWearableConnection, IWearableSensingCallback wearableSensingCallback, RemoteCallback callback) {
            Objects.requireNonNull(secureWearableConnection);
            if (wearableSensingCallback != null) {
                WearableSensingService.this.mWearableSensingCallback = wearableSensingCallback;
            }
            Consumer<Integer> consumer = WearableSensingService.createWearableStatusConsumer(callback);
            WearableSensingService.this.onSecureConnectionProvided(secureWearableConnection, consumer);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback callback) {
            Objects.requireNonNull(parcelFileDescriptor);
            if (wearableSensingCallback != null) {
                WearableSensingService.this.mWearableSensingCallback = wearableSensingCallback;
            }
            Consumer<Integer> consumer = WearableSensingService.createWearableStatusConsumer(callback);
            WearableSensingService.this.onDataStreamProvided(parcelFileDescriptor, consumer);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideData(PersistableBundle data, SharedMemory sharedMemory, RemoteCallback callback) {
            Objects.requireNonNull(data);
            Consumer<Integer> consumer = WearableSensingService.createWearableStatusConsumer(callback);
            WearableSensingService.this.onDataProvided(data, sharedMemory, consumer);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void registerDataRequestObserver(int dataType, RemoteCallback dataRequestCallback, int dataRequestObserverId, String packageName, RemoteCallback statusCallback) {
            WearableSensingDataRequester dataRequester;
            Objects.requireNonNull(dataRequestCallback);
            Objects.requireNonNull(statusCallback);
            synchronized (WearableSensingService.this.mDataRequestObserverIdToRequesterMap) {
                dataRequester = (WearableSensingDataRequester) WearableSensingService.this.mDataRequestObserverIdToRequesterMap.get(dataRequestObserverId);
                if (dataRequester == null) {
                    dataRequester = WearableSensingService.createDataRequester(dataRequestCallback);
                    WearableSensingService.this.mDataRequestObserverIdToRequesterMap.put(dataRequestObserverId, dataRequester);
                }
            }
            Consumer<Integer> statusConsumer = WearableSensingService.createWearableStatusConsumer(statusCallback);
            WearableSensingService.this.onDataRequestObserverRegistered(dataType, packageName, dataRequester, statusConsumer);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void unregisterDataRequestObserver(int dataType, int dataRequestObserverId, String packageName, RemoteCallback statusCallback) {
            synchronized (WearableSensingService.this.mDataRequestObserverIdToRequesterMap) {
                WearableSensingDataRequester dataRequester = (WearableSensingDataRequester) WearableSensingService.this.mDataRequestObserverIdToRequesterMap.get(dataRequestObserverId);
                if (dataRequester == null) {
                    Slog.w(WearableSensingService.TAG, "dataRequestObserverId not found, cannot unregister data request observer.");
                    return;
                }
                WearableSensingService.this.mDataRequestObserverIdToRequesterMap.remove(dataRequestObserverId);
                Consumer<Integer> statusConsumer = WearableSensingService.createWearableStatusConsumer(statusCallback);
                WearableSensingService.this.onDataRequestObserverUnregistered(dataType, packageName, dataRequester, statusConsumer);
            }
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startHotwordRecognition(final RemoteCallback wearableHotwordCallback, final RemoteCallback statusCallback) {
            Consumer<HotwordAudioStream> hotwordAudioConsumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startHotwordRecognition$0(RemoteCallback.this, (HotwordAudioStream) obj);
                }
            };
            Consumer<Integer> statusConsumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startHotwordRecognition$1(RemoteCallback.this, (Integer) obj);
                }
            };
            WearableSensingService.this.onStartHotwordRecognition(hotwordAudioConsumer, statusConsumer);
        }

        static /* synthetic */ void lambda$startHotwordRecognition$0(RemoteCallback wearableHotwordCallback, HotwordAudioStream hotwordAudioStream) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(WearableSensingService.HOTWORD_AUDIO_STREAM_BUNDLE_KEY, hotwordAudioStream);
            wearableHotwordCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startHotwordRecognition$1(RemoteCallback statusCallback, Integer response) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", response.intValue());
            statusCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopHotwordRecognition(final RemoteCallback statusCallback) {
            Consumer<Integer> statusConsumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$stopHotwordRecognition$2(RemoteCallback.this, (Integer) obj);
                }
            };
            WearableSensingService.this.onStopHotwordRecognition(statusConsumer);
        }

        static /* synthetic */ void lambda$stopHotwordRecognition$2(RemoteCallback statusCallback, Integer response) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", response.intValue());
            statusCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void onValidatedByHotwordDetectionService() {
            WearableSensingService.this.onValidatedByHotwordDetectionService();
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopActiveHotwordAudio() {
            WearableSensingService.this.onStopHotwordAudioStream();
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startDetection(AmbientContextEventRequest request, String packageName, final RemoteCallback detectionResultCallback, final RemoteCallback statusCallback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(packageName);
            Objects.requireNonNull(detectionResultCallback);
            Objects.requireNonNull(statusCallback);
            Consumer<AmbientContextDetectionResult> detectionResultConsumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startDetection$3(RemoteCallback.this, (AmbientContextDetectionResult) obj);
                }
            };
            Consumer<AmbientContextDetectionServiceStatus> statusConsumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startDetection$4(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            };
            WearableSensingService.this.onStartDetection(request, packageName, statusConsumer, detectionResultConsumer);
            Slog.d(WearableSensingService.TAG, "startDetection " + request);
        }

        static /* synthetic */ void lambda$startDetection$3(RemoteCallback detectionResultCallback, AmbientContextDetectionResult result) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionResult.RESULT_RESPONSE_BUNDLE_KEY, result);
            detectionResultCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startDetection$4(RemoteCallback statusCallback, AmbientContextDetectionServiceStatus status) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, status);
            statusCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopDetection(String packageName) {
            Objects.requireNonNull(packageName);
            WearableSensingService.this.onStopDetection(packageName);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void queryServiceStatus(int[] eventTypes, String packageName, final RemoteCallback callback) {
            Objects.requireNonNull(eventTypes);
            Objects.requireNonNull(packageName);
            Objects.requireNonNull(callback);
            Consumer<AmbientContextDetectionServiceStatus> consumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$queryServiceStatus$5(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            };
            Integer[] events = WearableSensingService.intArrayToIntegerArray(eventTypes);
            WearableSensingService.this.onQueryServiceStatus(new HashSet(Arrays.asList(events)), packageName, consumer);
        }

        static /* synthetic */ void lambda$queryServiceStatus$5(RemoteCallback callback, AmbientContextDetectionServiceStatus response) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, response);
            callback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void killProcess() {
            Slog.d(WearableSensingService.TAG, "#killProcess");
            Process.killProcess(Process.myPid());
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

    public void onSecureConnectionProvided(ParcelFileDescriptor secureWearableConnection, Consumer<Integer> statusConsumer) {
        statusConsumer.accept(6);
    }

    public void onDataRequestObserverRegistered(int dataType, String packageName, WearableSensingDataRequester dataRequester, Consumer<Integer> statusConsumer) {
        statusConsumer.accept(6);
    }

    public void onDataRequestObserverUnregistered(int dataType, String packageName, WearableSensingDataRequester dataRequester, Consumer<Integer> statusConsumer) {
        statusConsumer.accept(6);
    }

    public void onStartHotwordRecognition(Consumer<HotwordAudioStream> hotwordAudioConsumer, Consumer<Integer> statusConsumer) {
        if (Flags.enableUnsupportedOperationStatusCode()) {
            statusConsumer.accept(6);
        }
    }

    public void onStopHotwordRecognition(Consumer<Integer> statusConsumer) {
        if (Flags.enableUnsupportedOperationStatusCode()) {
            statusConsumer.accept(6);
        }
    }

    public void onValidatedByHotwordDetectionService() {
    }

    public void onStopHotwordAudioStream() {
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public FileInputStream openFileInput(String fileName) throws FileNotFoundException {
        if (fileName == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        try {
            if (this.mWearableSensingCallback == null) {
                throw new IllegalStateException("Cannot open file from WearableSensingService. WearableSensingCallback is not available.");
            }
            AndroidFuture<ParcelFileDescriptor> future = new AndroidFuture<>();
            this.mWearableSensingCallback.openFile(fileName, future);
            ParcelFileDescriptor pfd = future.get(OPEN_FILE_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
            if (pfd == null) {
                throw new FileNotFoundException(TextUtils.formatSimple("File %s not found or unable to be opened in read-only mode.", fileName));
            }
            return new FileInputStream(pfd.getFileDescriptor());
        } catch (RemoteException | ExecutionException | TimeoutException e) {
            throw ((FileNotFoundException) new FileNotFoundException("Cannot open file due to remote service failure").initCause(e));
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw ((FileNotFoundException) new FileNotFoundException("Interrupted when opening a file.").initCause(e2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Integer[] intArrayToIntegerArray(int[] integerSet) {
        Integer[] intArray = new Integer[integerSet.length];
        int i = 0;
        int length = integerSet.length;
        int i2 = 0;
        while (i2 < length) {
            Integer type = Integer.valueOf(integerSet[i2]);
            intArray[i] = type;
            i2++;
            i++;
        }
        return intArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WearableSensingDataRequester createDataRequester(final RemoteCallback dataRequestCallback) {
        return new WearableSensingDataRequester() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda2
            @Override // android.service.wearable.WearableSensingDataRequester
            public final void requestData(WearableSensingDataRequest wearableSensingDataRequest, Consumer consumer) {
                WearableSensingService.lambda$createDataRequester$1(RemoteCallback.this, wearableSensingDataRequest, consumer);
            }
        };
    }

    static /* synthetic */ void lambda$createDataRequester$1(RemoteCallback dataRequestCallback, WearableSensingDataRequest request, final Consumer requestStatusConsumer) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(WearableSensingDataRequest.REQUEST_BUNDLE_KEY, request);
        RemoteCallback requestStatusCallback = new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda0
            @Override // android.os.RemoteCallback.OnResultListener
            public final void onResult(Bundle bundle2) {
                requestStatusConsumer.accept(Integer.valueOf(bundle2.getInt("android.app.wearable.WearableSensingStatusBundleKey")));
            }
        });
        bundle.putParcelable(WearableSensingDataRequest.REQUEST_STATUS_CALLBACK_BUNDLE_KEY, requestStatusCallback);
        dataRequestCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Consumer<Integer> createWearableStatusConsumer(final RemoteCallback statusCallback) {
        return new Consumer() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WearableSensingService.lambda$createWearableStatusConsumer$2(RemoteCallback.this, (Integer) obj);
            }
        };
    }

    static /* synthetic */ void lambda$createWearableStatusConsumer$2(RemoteCallback statusCallback, Integer response) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", response.intValue());
        statusCallback.sendResult(bundle);
    }
}
