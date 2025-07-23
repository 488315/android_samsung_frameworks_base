package android.media.soundtrigger;

import android.annotation.SystemApi;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Slog;
import com.android.internal.app.ISoundTriggerSession;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class SoundTriggerDetector {
    private static final boolean DBG = false;
    private static final int MSG_AVAILABILITY_CHANGED = 1;
    private static final int MSG_DETECTION_ERROR = 3;
    private static final int MSG_DETECTION_PAUSE = 4;
    private static final int MSG_DETECTION_RESUME = 5;
    private static final int MSG_SOUND_TRIGGER_DETECTED = 2;
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_ECHO_CANCELLATION = 4;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_NOISE_SUPPRESSION = 8;
    public static final int RECOGNITION_FLAG_NONE = 0;
    public static final int RECOGNITION_FLAG_RUN_IN_BATTERY_SAVER = 16;
    private static final String TAG = "SoundTriggerDetector";
    private final Callback mCallback;
    private final Handler mHandler;
    private final Object mLock = new Object();
    private final RecognitionCallback mRecognitionCallback;
    private final SoundTrigger.GenericSoundModel mSoundModel;
    private final ISoundTriggerSession mSoundTriggerSession;

    public static abstract class Callback {
        public abstract void onAvailabilityChanged(int i);

        public abstract void onDetected(EventPayload eventPayload);

        public abstract void onError();

        public abstract void onRecognitionPaused();

        public abstract void onRecognitionResumed();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecognitionFlags {
    }

    public static class EventPayload {
        private final AudioFormat mAudioFormat;
        private final boolean mCaptureAvailable;
        private final int mCaptureSession;
        private final byte[] mData;
        private final boolean mTriggerAvailable;

        private EventPayload(boolean z, boolean z2, AudioFormat audioFormat, int i, byte[] bArr) {
            this.mTriggerAvailable = z;
            this.mCaptureAvailable = z2;
            this.mCaptureSession = i;
            this.mAudioFormat = audioFormat;
            this.mData = bArr;
        }

        public AudioFormat getCaptureAudioFormat() {
            return this.mAudioFormat;
        }

        public byte[] getTriggerAudio() {
            if (this.mTriggerAvailable) {
                return this.mData;
            }
            return null;
        }

        public byte[] getData() {
            if (this.mTriggerAvailable) {
                return null;
            }
            return this.mData;
        }

        public Integer getCaptureSession() {
            if (this.mCaptureAvailable) {
                return Integer.valueOf(this.mCaptureSession);
            }
            return null;
        }
    }

    SoundTriggerDetector(ISoundTriggerSession iSoundTriggerSession, SoundTrigger.GenericSoundModel genericSoundModel, Callback callback, Handler handler) {
        this.mSoundTriggerSession = iSoundTriggerSession;
        this.mSoundModel = genericSoundModel;
        this.mCallback = callback;
        if (handler == null) {
            this.mHandler = new MyHandler();
        } else {
            this.mHandler = new MyHandler(handler.getLooper());
        }
        this.mRecognitionCallback = new RecognitionCallback();
    }

    @Deprecated
    public boolean startRecognition(int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 16) != 0;
        int i2 = (i & 4) != 0 ? 1 : 0;
        if ((i & 8) != 0) {
            i2 |= 2;
        }
        return this.mSoundTriggerSession.startRecognition(this.mSoundModel, this.mRecognitionCallback, new SoundTrigger.RecognitionConfig.Builder().setCaptureRequested(z).setMultipleTriggersAllowed(z2).setAudioCapabilities(i2).build(), z3) == 0;
    }

    @Deprecated
    public boolean stopRecognition() {
        return this.mSoundTriggerSession.stopRecognition(new ParcelUuid(this.mSoundModel.getUuid()), this.mRecognitionCallback) == 0;
    }

    public void dump(String str, PrintWriter printWriter) {
        synchronized (this.mLock) {
        }
    }

    private class RecognitionCallback extends IRecognitionStatusCallback.Stub {
        private RecognitionCallback() {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
            Slog.d(SoundTriggerDetector.TAG, "onGenericSoundTriggerDetected()" + genericRecognitionEvent);
            Message.obtain(SoundTriggerDetector.this.mHandler, 2, new EventPayload(genericRecognitionEvent.triggerInData, genericRecognitionEvent.captureAvailable, genericRecognitionEvent.captureFormat, genericRecognitionEvent.captureSession, genericRecognitionEvent.data)).sendToTarget();
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
            Slog.e(SoundTriggerDetector.TAG, "Ignoring onKeyphraseDetected() called for " + keyphraseRecognitionEvent);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionPaused() {
            Slog.d(SoundTriggerDetector.TAG, "onRecognitionPaused()");
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(4);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionResumed() {
            Slog.d(SoundTriggerDetector.TAG, "onRecognitionResumed()");
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(5);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPreempted() {
            Slog.d(SoundTriggerDetector.TAG, "onPreempted()");
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onModuleDied() {
            Slog.d(SoundTriggerDetector.TAG, "onModuleDied()");
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onResumeFailed(int i) {
            Slog.d(SoundTriggerDetector.TAG, "onResumeFailed()" + i);
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPauseFailed(int i) {
            Slog.d(SoundTriggerDetector.TAG, "onPauseFailed()" + i);
            SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }
    }

    private class MyHandler extends Handler {
        MyHandler() {
        }

        MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (SoundTriggerDetector.this.mCallback == null) {
                Slog.w(SoundTriggerDetector.TAG, "Received message: " + message.what + " for NULL callback.");
                return;
            }
            int i = message.what;
            if (i == 2) {
                SoundTriggerDetector.this.mCallback.onDetected((EventPayload) message.obj);
                return;
            }
            if (i == 3) {
                SoundTriggerDetector.this.mCallback.onError();
                return;
            }
            if (i == 4) {
                SoundTriggerDetector.this.mCallback.onRecognitionPaused();
            } else if (i == 5) {
                SoundTriggerDetector.this.mCallback.onRecognitionResumed();
            } else {
                super.handleMessage(message);
            }
        }
    }
}
