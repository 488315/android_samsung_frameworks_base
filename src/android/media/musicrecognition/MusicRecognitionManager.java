package android.media.musicrecognition;

import android.annotation.SystemApi;
import android.media.MediaMetadata;
import android.media.musicrecognition.IMusicRecognitionManagerCallback;
import android.media.musicrecognition.MusicRecognitionManager;
import android.os.Bundle;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class MusicRecognitionManager {
    public static final int RECOGNITION_FAILED_AUDIO_UNAVAILABLE = 7;
    public static final int RECOGNITION_FAILED_NOT_FOUND = 1;
    public static final int RECOGNITION_FAILED_NO_CONNECTIVITY = 2;
    public static final int RECOGNITION_FAILED_SERVICE_KILLED = 5;
    public static final int RECOGNITION_FAILED_SERVICE_UNAVAILABLE = 3;
    public static final int RECOGNITION_FAILED_TIMEOUT = 6;
    public static final int RECOGNITION_FAILED_UNKNOWN = -1;
    private final IMusicRecognitionManager mService;

    public interface RecognitionCallback {
        void onAudioStreamClosed();

        void onRecognitionFailed(RecognitionRequest recognitionRequest, int i);

        void onRecognitionSucceeded(RecognitionRequest recognitionRequest, MediaMetadata mediaMetadata, Bundle bundle);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecognitionFailureCode {
    }

    public MusicRecognitionManager(IMusicRecognitionManager iMusicRecognitionManager) {
        this.mService = iMusicRecognitionManager;
    }

    @SystemApi
    public void beginStreamingSearch(RecognitionRequest recognitionRequest, Executor executor, RecognitionCallback recognitionCallback) {
        try {
            this.mService.beginRecognition((RecognitionRequest) Objects.requireNonNull(recognitionRequest), new MusicRecognitionCallbackWrapper(this, (RecognitionRequest) Objects.requireNonNull(recognitionRequest), (RecognitionCallback) Objects.requireNonNull(recognitionCallback), (Executor) Objects.requireNonNull(executor)));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MusicRecognitionCallbackWrapper extends IMusicRecognitionManagerCallback.Stub {
        private final RecognitionCallback mCallback;
        private final Executor mCallbackExecutor;
        private final RecognitionRequest mRecognitionRequest;

        MusicRecognitionCallbackWrapper(MusicRecognitionManager musicRecognitionManager, RecognitionRequest recognitionRequest, RecognitionCallback recognitionCallback, Executor executor) {
            this.mRecognitionRequest = recognitionRequest;
            this.mCallback = recognitionCallback;
            this.mCallbackExecutor = executor;
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionSucceeded(final MediaMetadata mediaMetadata, final Bundle bundle) {
            this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.musicrecognition.MusicRecognitionManager$MusicRecognitionCallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MusicRecognitionManager.MusicRecognitionCallbackWrapper.this.lambda$onRecognitionSucceeded$0(mediaMetadata, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecognitionSucceeded$0(MediaMetadata mediaMetadata, Bundle bundle) {
            this.mCallback.onRecognitionSucceeded(this.mRecognitionRequest, mediaMetadata, bundle);
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionFailed(final int i) {
            this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.musicrecognition.MusicRecognitionManager$MusicRecognitionCallbackWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MusicRecognitionManager.MusicRecognitionCallbackWrapper.this.lambda$onRecognitionFailed$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecognitionFailed$1(int i) {
            this.mCallback.onRecognitionFailed(this.mRecognitionRequest, i);
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onAudioStreamClosed() {
            Executor executor = this.mCallbackExecutor;
            final RecognitionCallback recognitionCallback = this.mCallback;
            Objects.requireNonNull(recognitionCallback);
            executor.execute(new Runnable() { // from class: android.media.musicrecognition.MusicRecognitionManager$MusicRecognitionCallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MusicRecognitionManager.RecognitionCallback.this.onAudioStreamClosed();
                }
            });
        }
    }
}
