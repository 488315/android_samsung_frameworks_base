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
    private static final Duration OPEN_FILE_TIMEOUT = Duration.ofSeconds(5);
    public static final String SERVICE_INTERFACE = "android.service.wearable.WearableSensingService";
    public static final String STATUS_RESPONSE_BUNDLE_KEY = "android.app.wearable.WearableSensingStatusBundleKey";
    private static final String TAG = "WearableSensingService";
    private final SparseArray<WearableSensingDataRequester> mDataRequestObserverIdToRequesterMap = new SparseArray<>();
    private IWearableSensingCallback mWearableSensingCallback;

    public abstract void onDataProvided(PersistableBundle persistableBundle, SharedMemory sharedMemory, Consumer<Integer> consumer);

    public abstract void onDataStreamProvided(ParcelFileDescriptor parcelFileDescriptor, Consumer<Integer> consumer);

    public abstract void onQueryServiceStatus(Set<Integer> set, String str, Consumer<AmbientContextDetectionServiceStatus> consumer);

    public abstract void onStartDetection(AmbientContextEventRequest ambientContextEventRequest, String str, Consumer<AmbientContextDetectionServiceStatus> consumer, Consumer<AmbientContextDetectionResult> consumer2);

    public abstract void onStopDetection(String str);

    public void onStopHotwordAudioStream() {
    }

    public void onValidatedByHotwordDetectionService() {
    }

    /* renamed from: android.service.wearable.WearableSensingService$1, reason: invalid class name */
    class AnonymousClass1 extends IWearableSensingService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) {
            Objects.requireNonNull(parcelFileDescriptor);
            if (iWearableSensingCallback != null) {
                WearableSensingService.this.mWearableSensingCallback = iWearableSensingCallback;
            }
            WearableSensingService.this.onSecureConnectionProvided(parcelFileDescriptor, WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideConcurrentSecureConnection(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) {
            Objects.requireNonNull(parcelFileDescriptor);
            Objects.requireNonNull(persistableBundle);
            if (iWearableSensingCallback != null) {
                WearableSensingService.this.mWearableSensingCallback = iWearableSensingCallback;
            }
            WearableSensingService.this.onSecureConnectionProvided(parcelFileDescriptor, persistableBundle, WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, RemoteCallback remoteCallback) {
            Objects.requireNonNull(parcelFileDescriptor);
            WearableSensingService.this.onReadOnlyParcelFileDescriptorProvided(parcelFileDescriptor, persistableBundle, WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) {
            Objects.requireNonNull(parcelFileDescriptor);
            if (iWearableSensingCallback != null) {
                WearableSensingService.this.mWearableSensingCallback = iWearableSensingCallback;
            }
            WearableSensingService.this.onDataStreamProvided(parcelFileDescriptor, WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) {
            Objects.requireNonNull(persistableBundle);
            WearableSensingService.this.onDataProvided(persistableBundle, sharedMemory, WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void registerDataRequestObserver(int i, RemoteCallback remoteCallback, int i2, String str, RemoteCallback remoteCallback2) {
            WearableSensingDataRequester wearableSensingDataRequester;
            Objects.requireNonNull(remoteCallback);
            Objects.requireNonNull(remoteCallback2);
            synchronized (WearableSensingService.this.mDataRequestObserverIdToRequesterMap) {
                wearableSensingDataRequester = (WearableSensingDataRequester) WearableSensingService.this.mDataRequestObserverIdToRequesterMap.get(i2);
                if (wearableSensingDataRequester == null) {
                    wearableSensingDataRequester = WearableSensingService.createDataRequester(remoteCallback);
                    WearableSensingService.this.mDataRequestObserverIdToRequesterMap.put(i2, wearableSensingDataRequester);
                }
            }
            WearableSensingService.this.onDataRequestObserverRegistered(i, str, wearableSensingDataRequester, WearableSensingService.createWearableStatusConsumer(remoteCallback2));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void unregisterDataRequestObserver(int i, int i2, String str, RemoteCallback remoteCallback) {
            synchronized (WearableSensingService.this.mDataRequestObserverIdToRequesterMap) {
                WearableSensingDataRequester wearableSensingDataRequester = (WearableSensingDataRequester) WearableSensingService.this.mDataRequestObserverIdToRequesterMap.get(i2);
                if (wearableSensingDataRequester == null) {
                    Slog.w(WearableSensingService.TAG, "dataRequestObserverId not found, cannot unregister data request observer.");
                    return;
                }
                WearableSensingService.this.mDataRequestObserverIdToRequesterMap.remove(i2);
                WearableSensingService.this.onDataRequestObserverUnregistered(i, str, wearableSensingDataRequester, WearableSensingService.createWearableStatusConsumer(remoteCallback));
            }
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startHotwordRecognition(final RemoteCallback remoteCallback, final RemoteCallback remoteCallback2) {
            WearableSensingService.this.onStartHotwordRecognition(new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startHotwordRecognition$0(RemoteCallback.this, (HotwordAudioStream) obj);
                }
            }, new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startHotwordRecognition$1(RemoteCallback.this, (Integer) obj);
                }
            });
        }

        static /* synthetic */ void lambda$startHotwordRecognition$0(RemoteCallback remoteCallback, HotwordAudioStream hotwordAudioStream) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(WearableSensingService.HOTWORD_AUDIO_STREAM_BUNDLE_KEY, hotwordAudioStream);
            remoteCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startHotwordRecognition$1(RemoteCallback remoteCallback, Integer num) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", num.intValue());
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopHotwordRecognition(final RemoteCallback remoteCallback) {
            WearableSensingService.this.onStopHotwordRecognition(new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$stopHotwordRecognition$2(RemoteCallback.this, (Integer) obj);
                }
            });
        }

        static /* synthetic */ void lambda$stopHotwordRecognition$2(RemoteCallback remoteCallback, Integer num) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", num.intValue());
            remoteCallback.sendResult(bundle);
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
        public void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, final RemoteCallback remoteCallback, final RemoteCallback remoteCallback2) {
            Objects.requireNonNull(ambientContextEventRequest);
            Objects.requireNonNull(str);
            Objects.requireNonNull(remoteCallback);
            Objects.requireNonNull(remoteCallback2);
            WearableSensingService.this.onStartDetection(ambientContextEventRequest, str, new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startDetection$4(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            }, new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startDetection$3(RemoteCallback.this, (AmbientContextDetectionResult) obj);
                }
            });
            Slog.d(WearableSensingService.TAG, "startDetection " + ambientContextEventRequest);
        }

        static /* synthetic */ void lambda$startDetection$3(RemoteCallback remoteCallback, AmbientContextDetectionResult ambientContextDetectionResult) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionResult.RESULT_RESPONSE_BUNDLE_KEY, ambientContextDetectionResult);
            remoteCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startDetection$4(RemoteCallback remoteCallback, AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopDetection(String str) {
            Objects.requireNonNull(str);
            WearableSensingService.this.onStopDetection(str);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void queryServiceStatus(int[] iArr, String str, final RemoteCallback remoteCallback) {
            Objects.requireNonNull(iArr);
            Objects.requireNonNull(str);
            Objects.requireNonNull(remoteCallback);
            WearableSensingService.this.onQueryServiceStatus(new HashSet(Arrays.asList(WearableSensingService.intArrayToIntegerArray(iArr))), str, new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$queryServiceStatus$5(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            });
        }

        static /* synthetic */ void lambda$queryServiceStatus$5(RemoteCallback remoteCallback, AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
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

    @Deprecated
    public void onSecureConnectionProvided(ParcelFileDescriptor parcelFileDescriptor, Consumer<Integer> consumer) {
        consumer.accept(6);
    }

    public void onSecureConnectionProvided(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, Consumer<Integer> consumer) {
        consumer.accept(6);
    }

    public void onReadOnlyParcelFileDescriptorProvided(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, Consumer<Integer> consumer) {
        consumer.accept(6);
    }

    public void onDataRequestObserverRegistered(int i, String str, WearableSensingDataRequester wearableSensingDataRequester, Consumer<Integer> consumer) {
        consumer.accept(6);
    }

    public void onDataRequestObserverUnregistered(int i, String str, WearableSensingDataRequester wearableSensingDataRequester, Consumer<Integer> consumer) {
        consumer.accept(6);
    }

    public void onStartHotwordRecognition(Consumer<HotwordAudioStream> consumer, Consumer<Integer> consumer2) {
        if (Flags.enableUnsupportedOperationStatusCode()) {
            consumer2.accept(6);
        }
    }

    public void onStopHotwordRecognition(Consumer<Integer> consumer) {
        if (Flags.enableUnsupportedOperationStatusCode()) {
            consumer.accept(6);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public FileInputStream openFileInput(String str) throws FileNotFoundException {
        if (str == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        try {
            if (this.mWearableSensingCallback == null) {
                throw new IllegalStateException("Cannot open file from WearableSensingService. WearableSensingCallback is not available.");
            }
            AndroidFuture<ParcelFileDescriptor> androidFuture = new AndroidFuture<>();
            this.mWearableSensingCallback.openFile(str, androidFuture);
            ParcelFileDescriptor parcelFileDescriptor = androidFuture.get(OPEN_FILE_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
            if (parcelFileDescriptor == null) {
                throw new FileNotFoundException(TextUtils.formatSimple("File %s not found or unable to be opened in read-only mode.", str));
            }
            return new FileInputStream(parcelFileDescriptor.getFileDescriptor());
        } catch (RemoteException | ExecutionException | TimeoutException e) {
            throw ((FileNotFoundException) new FileNotFoundException("Cannot open file due to remote service failure").initCause(e));
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw ((FileNotFoundException) new FileNotFoundException("Interrupted when opening a file.").initCause(e2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Integer[] intArrayToIntegerArray(int[] iArr) {
        Integer[] numArr = new Integer[iArr.length];
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            numArr[i2] = Integer.valueOf(iArr[i]);
            i++;
            i2++;
        }
        return numArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WearableSensingDataRequester createDataRequester(final RemoteCallback remoteCallback) {
        return new WearableSensingDataRequester() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda2
            @Override // android.service.wearable.WearableSensingDataRequester
            public final void requestData(WearableSensingDataRequest wearableSensingDataRequest, Consumer consumer) {
                WearableSensingService.lambda$createDataRequester$1(RemoteCallback.this, wearableSensingDataRequest, consumer);
            }
        };
    }

    static /* synthetic */ void lambda$createDataRequester$1(RemoteCallback remoteCallback, WearableSensingDataRequest wearableSensingDataRequest, final Consumer consumer) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(WearableSensingDataRequest.REQUEST_BUNDLE_KEY, wearableSensingDataRequest);
        bundle.putParcelable(WearableSensingDataRequest.REQUEST_STATUS_CALLBACK_BUNDLE_KEY, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda0
            @Override // android.os.RemoteCallback.OnResultListener
            public final void onResult(Bundle bundle2) {
                consumer.accept(Integer.valueOf(bundle2.getInt("android.app.wearable.WearableSensingStatusBundleKey")));
            }
        }));
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Consumer<Integer> createWearableStatusConsumer(final RemoteCallback remoteCallback) {
        return new Consumer() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WearableSensingService.lambda$createWearableStatusConsumer$2(RemoteCallback.this, (Integer) obj);
            }
        };
    }

    static /* synthetic */ void lambda$createWearableStatusConsumer$2(RemoteCallback remoteCallback, Integer num) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", num.intValue());
        remoteCallback.sendResult(bundle);
    }
}
