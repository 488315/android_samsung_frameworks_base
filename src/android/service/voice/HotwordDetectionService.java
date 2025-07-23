package android.service.voice;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.media.AudioSystem;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.AlwaysOnHotwordDetector;
import android.service.voice.ISandboxedDetectionService;
import android.speech.IRecognitionServiceManager;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.IContentCaptureManager;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.function.IntConsumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class HotwordDetectionService extends Service implements SandboxedDetectionInitializer {
    public static final int AUDIO_SOURCE_EXTERNAL = 2;
    public static final int AUDIO_SOURCE_MICROPHONE = 1;
    private static final boolean DBG = false;
    public static final boolean ENABLE_PROXIMITY_RESULT = true;

    @Deprecated
    public static final int INITIALIZATION_STATUS_SUCCESS = 0;

    @Deprecated
    public static final int INITIALIZATION_STATUS_UNKNOWN = 100;
    public static final String KEY_SYSTEM_WILL_CLOSE_AUDIO_STREAM_AFTER_CALLBACK = "android.service.voice.HotwordDetectionService.KEY_SYSTEM_WILL_CLOSE_AUDIO_STREAM_AFTER_CALLBACK";
    public static final String SERVICE_INTERFACE = "android.service.voice.HotwordDetectionService";
    private static final String TAG = "HotwordDetectionService";
    private static final long UPDATE_TIMEOUT_MILLIS = 20000;
    private ContentCaptureManager mContentCaptureManager;
    private IRecognitionServiceManager mIRecognitionServiceManager;
    private final ISandboxedDetectionService mInterface = new ISandboxedDetectionService.Stub() { // from class: android.service.voice.HotwordDetectionService.1
        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromDspSource(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, AudioFormat audioFormat, long j, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException {
            HotwordDetectionService.this.onDetect(new AlwaysOnHotwordDetector.EventPayload.Builder(keyphraseRecognitionEvent).build(), j, new Callback(iDspHotwordDetectionCallback));
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IRemoteCallback iRemoteCallback) throws RemoteException {
            Log.v(HotwordDetectionService.TAG, "#updateState".concat(iRemoteCallback != null ? " with callback" : ""));
            HotwordDetectionService.this.onUpdateStateInternal(persistableBundle, sharedMemory, iRemoteCallback);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromMicrophoneSource(ParcelFileDescriptor parcelFileDescriptor, int i, AudioFormat audioFormat, PersistableBundle persistableBundle, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException {
            if (i == 1) {
                HotwordDetectionService.this.onDetect(new Callback(iDspHotwordDetectionCallback));
            } else {
                if (i == 2) {
                    HotwordDetectionService.this.onDetect(parcelFileDescriptor, audioFormat, persistableBundle, new Callback(iDspHotwordDetectionCallback));
                    return;
                }
                Log.i(HotwordDetectionService.TAG, "Unsupported audio source " + i);
            }
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectWithVisualSignals(IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) {
            throw new UnsupportedOperationException("Not supported by HotwordDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateAudioFlinger(IBinder iBinder) {
            AudioSystem.setAudioFlingerBinder(iBinder);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateContentCaptureManager(IContentCaptureManager iContentCaptureManager, ContentCaptureOptions contentCaptureOptions) {
            HotwordDetectionService.this.mContentCaptureManager = new ContentCaptureManager(HotwordDetectionService.this, iContentCaptureManager, contentCaptureOptions);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateRecognitionServiceManager(IRecognitionServiceManager iRecognitionServiceManager) {
            HotwordDetectionService.this.mIRecognitionServiceManager = iRecognitionServiceManager;
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void ping(ISandboxedDetectionService.IPingMe iPingMe) throws RemoteException {
            iPingMe.onPing();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void stopDetection() {
            HotwordDetectionService.this.onStopDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void registerRemoteStorageService(IDetectorSessionStorageService iDetectorSessionStorageService) {
            throw new UnsupportedOperationException("Hotword cannot access files from the disk.");
        }
    };

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @interface AudioSource {
    }

    @SystemApi
    @Deprecated
    public static int getMaxCustomInitializationStatus() {
        return 2;
    }

    public void onStopDetection() {
    }

    @Override // android.service.voice.SandboxedDetectionInitializer
    @SystemApi
    public void onUpdateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, long j, IntConsumer intConsumer) {
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.voice.HotwordDetectionService: " + intent);
        return null;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        IRecognitionServiceManager iRecognitionServiceManager;
        if (Context.CONTENT_CAPTURE_MANAGER_SERVICE.equals(str)) {
            return this.mContentCaptureManager;
        }
        if (Context.SPEECH_RECOGNITION_SERVICE.equals(str) && (iRecognitionServiceManager = this.mIRecognitionServiceManager) != null) {
            return iRecognitionServiceManager.asBinder();
        }
        return super.getSystemService(str);
    }

    @SystemApi
    public void onDetect(AlwaysOnHotwordDetector.EventPayload eventPayload, long j, Callback callback) {
        throw new UnsupportedOperationException();
    }

    public void onDetect(Callback callback) {
        throw new UnsupportedOperationException();
    }

    public void onDetect(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, Callback callback) {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateStateInternal(PersistableBundle persistableBundle, SharedMemory sharedMemory, IRemoteCallback iRemoteCallback) {
        onUpdateState(persistableBundle, sharedMemory, UPDATE_TIMEOUT_MILLIS, SandboxedDetectionInitializer.createInitializationStatusConsumer(iRemoteCallback));
    }

    @SystemApi
    public static final class Callback {
        private final IDspHotwordDetectionCallback mRemoteCallback;

        private Callback(IDspHotwordDetectionCallback iDspHotwordDetectionCallback) {
            this.mRemoteCallback = iDspHotwordDetectionCallback;
        }

        public void onDetected(HotwordDetectedResult hotwordDetectedResult) {
            Objects.requireNonNull(hotwordDetectedResult);
            PersistableBundle extras = hotwordDetectedResult.getExtras();
            if (!extras.isEmpty() && HotwordDetectedResult.getParcelableSize(extras) > HotwordDetectedResult.getMaxBundleSize()) {
                throw new IllegalArgumentException("The bundle size of result is larger than max bundle size (" + HotwordDetectedResult.getMaxBundleSize() + ") of HotwordDetectedResult");
            }
            try {
                this.mRemoteCallback.onDetected(hotwordDetectedResult);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void onRejected(HotwordRejectedResult hotwordRejectedResult) {
            Objects.requireNonNull(hotwordRejectedResult);
            try {
                this.mRemoteCallback.onRejected(hotwordRejectedResult);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
