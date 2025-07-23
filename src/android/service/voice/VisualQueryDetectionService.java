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
    private static final String TAG = "VisualQueryDetectionService";
    private static final long UPDATE_TIMEOUT_MILLIS = 20000;
    private ContentCaptureManager mContentCaptureManager;
    private IDetectorSessionStorageService mDetectorSessionStorageService;
    private IRecognitionServiceManager mIRecognitionServiceManager;
    private IDetectorSessionVisualQueryDetectionCallback mRemoteCallback = null;
    private final ISandboxedDetectionService mInterface = new ISandboxedDetectionService.Stub() { // from class: android.service.voice.VisualQueryDetectionService.1
        @Override // android.service.voice.ISandboxedDetectionService
        public void detectWithVisualSignals(IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) {
            Log.v(VisualQueryDetectionService.TAG, "#detectWithVisualSignals");
            VisualQueryDetectionService.this.mRemoteCallback = iDetectorSessionVisualQueryDetectionCallback;
            VisualQueryDetectionService.this.onStartDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void stopDetection() {
            Log.v(VisualQueryDetectionService.TAG, "#stopDetection");
            VisualQueryDetectionService.this.onStopDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IRemoteCallback iRemoteCallback) throws RemoteException {
            Log.v(VisualQueryDetectionService.TAG, "#updateState".concat(iRemoteCallback != null ? " with callback" : ""));
            VisualQueryDetectionService.this.onUpdateStateInternal(persistableBundle, sharedMemory, iRemoteCallback);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void ping(ISandboxedDetectionService.IPingMe iPingMe) throws RemoteException {
            iPingMe.onPing();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromDspSource(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, AudioFormat audioFormat, long j, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) {
            throw new UnsupportedOperationException("Not supported by VisualQueryDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromMicrophoneSource(ParcelFileDescriptor parcelFileDescriptor, int i, AudioFormat audioFormat, PersistableBundle persistableBundle, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) {
            throw new UnsupportedOperationException("Not supported by VisualQueryDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateAudioFlinger(IBinder iBinder) {
            AudioSystem.setAudioFlingerBinder(iBinder);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateContentCaptureManager(IContentCaptureManager iContentCaptureManager, ContentCaptureOptions contentCaptureOptions) {
            VisualQueryDetectionService.this.mContentCaptureManager = new ContentCaptureManager(VisualQueryDetectionService.this, iContentCaptureManager, contentCaptureOptions);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateRecognitionServiceManager(IRecognitionServiceManager iRecognitionServiceManager) {
            VisualQueryDetectionService.this.mIRecognitionServiceManager = iRecognitionServiceManager;
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void registerRemoteStorageService(IDetectorSessionStorageService iDetectorSessionStorageService) {
            VisualQueryDetectionService.this.mDetectorSessionStorageService = iDetectorSessionStorageService;
        }
    };

    public void onStopDetection() {
    }

    @Override // android.service.voice.SandboxedDetectionInitializer
    @SystemApi
    public void onUpdateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, long j, IntConsumer intConsumer) {
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

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.voice.VisualQueryDetectionService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateStateInternal(PersistableBundle persistableBundle, SharedMemory sharedMemory, IRemoteCallback iRemoteCallback) {
        onUpdateState(persistableBundle, sharedMemory, UPDATE_TIMEOUT_MILLIS, SandboxedDetectionInitializer.createInitializationStatusConsumer(iRemoteCallback));
    }

    public void onStartDetection() {
        throw new UnsupportedOperationException();
    }

    public final void gainedAttention() {
        try {
            this.mRemoteCallback.onAttentionGained(null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void gainedAttention(VisualQueryAttentionResult visualQueryAttentionResult) {
        try {
            this.mRemoteCallback.onAttentionGained(visualQueryAttentionResult);
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

    public final void lostAttention(int i) {
        try {
            this.mRemoteCallback.onAttentionLost(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void streamQuery(String str) throws IllegalStateException {
        Objects.requireNonNull(str);
        try {
            this.mRemoteCallback.onQueryDetected(str);
        } catch (RemoteException unused) {
            throw new IllegalStateException("#streamQuery must be only be triggered after calling #gainedAttention to be in the attention gained state.");
        }
    }

    public final void streamQuery(VisualQueryDetectedResult visualQueryDetectedResult) {
        Objects.requireNonNull(visualQueryDetectedResult);
        try {
            this.mRemoteCallback.onResultDetected(visualQueryDetectedResult);
        } catch (RemoteException unused) {
            throw new IllegalStateException("#streamQuery must be only be triggered after calling #gainedAttention to be in the attention gained state.");
        }
    }

    public final void rejectQuery() throws IllegalStateException {
        try {
            this.mRemoteCallback.onQueryRejected();
        } catch (RemoteException unused) {
            throw new IllegalStateException("#rejectQuery must be only be triggered after calling #streamQuery to be in the query streaming state.");
        }
    }

    public final void finishQuery() throws IllegalStateException {
        try {
            this.mRemoteCallback.onQueryFinished();
        } catch (RemoteException unused) {
            throw new IllegalStateException("#finishQuery must be only be triggered after calling #streamQuery to be in the query streaming state.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContextWrapper, android.content.Context
    public FileInputStream openFileInput(String str) throws FileNotFoundException {
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            this.mDetectorSessionStorageService.openFile(str, androidFuture);
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) androidFuture.get();
            if (parcelFileDescriptor == null) {
                throw new FileNotFoundException("File does not exist. Unable to open " + str + MediaMetrics.SEPARATOR);
            }
            return new FileInputStream(parcelFileDescriptor.getFileDescriptor());
        } catch (RemoteException | InterruptedException | ExecutionException e) {
            Log.w(TAG, "Cannot open file due to remote service failure");
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
