package android.service.voice;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.media.AudioSystem;
import android.media.MediaMetrics;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.ISandboxedDetectionService;
import android.speech.IRecognitionServiceManager;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.IContentCaptureManager;
import com.android.internal.infra.AndroidFuture;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.IntConsumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class VisualQueryDetectionService extends Service implements SandboxedDetectionInitializer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String KEY_INITIALIZATION_STATUS = "initialization_status";
    public static final String SERVICE_INTERFACE = "android.service.voice.VisualQueryDetectionService";
    private static final String TAG = VisualQueryDetectionService.class.getSimpleName();
    private static final long UPDATE_TIMEOUT_MILLIS = 20000;
    private ContentCaptureManager mContentCaptureManager;
    private IDetectorSessionStorageService mDetectorSessionStorageService;
    private IRecognitionServiceManager mIRecognitionServiceManager;
    private IDetectorSessionVisualQueryDetectionCallback mRemoteCallback = null;
    private final ISandboxedDetectionService mInterface = new ISandboxedDetectionService.Stub() { // from class: android.service.voice.VisualQueryDetectionService.1
        @Override // android.service.voice.ISandboxedDetectionService
        public void detectWithVisualSignals(IDetectorSessionVisualQueryDetectionCallback callback) {
            Log.v(VisualQueryDetectionService.TAG, "#detectWithVisualSignals");
            VisualQueryDetectionService.this.mRemoteCallback = callback;
            VisualQueryDetectionService.this.onStartDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void stopDetection() {
            Log.v(VisualQueryDetectionService.TAG, "#stopDetection");
            VisualQueryDetectionService.this.onStopDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateState(PersistableBundle options, SharedMemory sharedMemory, IRemoteCallback callback) throws RemoteException {
            Log.v(VisualQueryDetectionService.TAG, "#updateState" + (callback != null ? " with callback" : ""));
            VisualQueryDetectionService.this.onUpdateStateInternal(options, sharedMemory, callback);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void ping(IRemoteCallback callback) throws RemoteException {
            callback.sendResult(null);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromDspSource(SoundTrigger.KeyphraseRecognitionEvent event, AudioFormat audioFormat, long timeoutMillis, IDspHotwordDetectionCallback callback) {
            throw new UnsupportedOperationException("Not supported by VisualQueryDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromMicrophoneSource(ParcelFileDescriptor audioStream, int audioSource, AudioFormat audioFormat, PersistableBundle options, IDspHotwordDetectionCallback callback) {
            throw new UnsupportedOperationException("Not supported by VisualQueryDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateAudioFlinger(IBinder audioFlinger) {
            AudioSystem.setAudioFlingerBinder(audioFlinger);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateContentCaptureManager(IContentCaptureManager manager, ContentCaptureOptions options) {
            VisualQueryDetectionService.this.mContentCaptureManager = new ContentCaptureManager(VisualQueryDetectionService.this, manager, options);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateRecognitionServiceManager(IRecognitionServiceManager manager) {
            VisualQueryDetectionService.this.mIRecognitionServiceManager = manager;
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void registerRemoteStorageService(IDetectorSessionStorageService detectorSessionStorageService) {
            VisualQueryDetectionService.this.mDetectorSessionStorageService = detectorSessionStorageService;
        }
    };

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String name) {
        if (Context.CONTENT_CAPTURE_MANAGER_SERVICE.equals(name)) {
            return this.mContentCaptureManager;
        }
        if (Context.SPEECH_RECOGNITION_SERVICE.equals(name) && this.mIRecognitionServiceManager != null) {
            return this.mIRecognitionServiceManager.asBinder();
        }
        return super.getSystemService(name);
    }

    @Override // android.service.voice.SandboxedDetectionInitializer
    @SystemApi
    public void onUpdateState(PersistableBundle options, SharedMemory sharedMemory, long callbackTimeoutMillis, IntConsumer statusCallback) {
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.voice.VisualQueryDetectionService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateStateInternal(PersistableBundle options, SharedMemory sharedMemory, IRemoteCallback callback) {
        IntConsumer intConsumer = SandboxedDetectionInitializer.createInitializationStatusConsumer(callback);
        onUpdateState(options, sharedMemory, UPDATE_TIMEOUT_MILLIS, intConsumer);
    }

    public void onStartDetection() {
        throw new UnsupportedOperationException();
    }

    public void onStopDetection() {
    }

    public final void gainedAttention() {
        try {
            this.mRemoteCallback.onAttentionGained(null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void gainedAttention(VisualQueryAttentionResult attentionResult) {
        try {
            this.mRemoteCallback.onAttentionGained(attentionResult);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void lostAttention() {
        try {
            this.mRemoteCallback.onAttentionLost(0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void lostAttention(int interactionIntention) {
        try {
            this.mRemoteCallback.onAttentionLost(interactionIntention);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void streamQuery(String partialQuery) throws IllegalStateException {
        Objects.requireNonNull(partialQuery);
        try {
            this.mRemoteCallback.onQueryDetected(partialQuery);
        } catch (RemoteException e) {
            throw new IllegalStateException("#streamQuery must be only be triggered after calling #gainedAttention to be in the attention gained state.");
        }
    }

    public final void streamQuery(VisualQueryDetectedResult partialResult) {
        Objects.requireNonNull(partialResult);
        try {
            this.mRemoteCallback.onResultDetected(partialResult);
        } catch (RemoteException e) {
            throw new IllegalStateException("#streamQuery must be only be triggered after calling #gainedAttention to be in the attention gained state.");
        }
    }

    public final void rejectQuery() throws IllegalStateException {
        try {
            this.mRemoteCallback.onQueryRejected();
        } catch (RemoteException e) {
            throw new IllegalStateException("#rejectQuery must be only be triggered after calling #streamQuery to be in the query streaming state.");
        }
    }

    public final void finishQuery() throws IllegalStateException {
        try {
            this.mRemoteCallback.onQueryFinished();
        } catch (RemoteException e) {
            throw new IllegalStateException("#finishQuery must be only be triggered after calling #streamQuery to be in the query streaming state.");
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public FileInputStream openFileInput(String filename) throws FileNotFoundException {
        try {
            AndroidFuture<ParcelFileDescriptor> future = new AndroidFuture<>();
            this.mDetectorSessionStorageService.openFile(filename, future);
            ParcelFileDescriptor pfd = future.get();
            if (pfd == null) {
                throw new FileNotFoundException("File does not exist. Unable to open " + filename + MediaMetrics.SEPARATOR);
            }
            return new FileInputStream(pfd.getFileDescriptor());
        } catch (RemoteException | InterruptedException | ExecutionException e) {
            Log.w(TAG, "Cannot open file due to remote service failure");
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
