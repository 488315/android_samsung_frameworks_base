package android.media;

import android.hardware.scontext.SContextConstants;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public final class MediaSync {
    private static final int CB_RETURN_AUDIO_BUFFER = 1;
    private static final int EVENT_CALLBACK = 1;
    private static final int EVENT_SET_CALLBACK = 2;
    public static final int MEDIASYNC_ERROR_AUDIOTRACK_FAIL = 1;
    public static final int MEDIASYNC_ERROR_SURFACE_FAIL = 2;
    private static final String TAG = "MediaSync";
    private long mNativeContext;
    private final Object mCallbackLock = new Object();
    private Handler mCallbackHandler = null;
    private Callback mCallback = null;
    private final Object mOnErrorListenerLock = new Object();
    private Handler mOnErrorListenerHandler = null;
    private OnErrorListener mOnErrorListener = null;
    private Thread mAudioThread = null;
    private Handler mAudioHandler = null;
    private Looper mAudioLooper = null;
    private final Object mAudioLock = new Object();
    private AudioTrack mAudioTrack = null;
    private List<AudioBuffer> mAudioBuffers = new LinkedList();
    private float mPlaybackRate = 0.0f;

    public static abstract class Callback {
        public abstract void onAudioBufferConsumed(MediaSync mediaSync, ByteBuffer byteBuffer, int i);
    }

    public interface OnErrorListener {
        void onError(MediaSync mediaSync, int i, int i2);
    }

    private final native void native_finalize();

    private final native void native_flush();

    /* JADX INFO: Access modifiers changed from: private */
    public final native long native_getPlayTimeForPendingAudioFrames();

    private final native boolean native_getTimestamp(MediaTimestamp mediaTimestamp);

    private static final native void native_init();

    private final native void native_release();

    private final native void native_setAudioTrack(AudioTrack audioTrack);

    private native float native_setPlaybackParams(PlaybackParams playbackParams);

    private final native void native_setSurface(Surface surface);

    private native float native_setSyncParams(SyncParams syncParams);

    private final native void native_setup();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_updateQueuedAudioData(int i, long j);

    public final native Surface createInputSurface();

    public native PlaybackParams getPlaybackParams();

    public native SyncParams getSyncParams();

    private static class AudioBuffer {
        public int mBufferIndex;
        public ByteBuffer mByteBuffer;
        long mPresentationTimeUs;

        public AudioBuffer(ByteBuffer byteBuffer, int i, long j) {
            this.mByteBuffer = byteBuffer;
            this.mBufferIndex = i;
            this.mPresentationTimeUs = j;
        }
    }

    public MediaSync() {
        native_setup();
    }

    protected void finalize() {
        native_finalize();
    }

    public final void release() {
        Looper looper;
        returnAudioBuffers();
        if (this.mAudioThread != null && (looper = this.mAudioLooper) != null) {
            looper.quit();
        }
        setCallback(null, null);
        native_release();
    }

    public void setCallback(Callback callback, Handler handler) {
        synchronized (this.mCallbackLock) {
            if (handler != null) {
                this.mCallbackHandler = handler;
            } else {
                Looper myLooper = Looper.myLooper();
                if (myLooper == null) {
                    myLooper = Looper.getMainLooper();
                }
                if (myLooper == null) {
                    this.mCallbackHandler = null;
                } else {
                    this.mCallbackHandler = new Handler(myLooper);
                }
            }
            this.mCallback = callback;
        }
    }

    public void setOnErrorListener(OnErrorListener onErrorListener, Handler handler) {
        synchronized (this.mOnErrorListenerLock) {
            if (handler != null) {
                this.mOnErrorListenerHandler = handler;
            } else {
                Looper myLooper = Looper.myLooper();
                if (myLooper == null) {
                    myLooper = Looper.getMainLooper();
                }
                if (myLooper == null) {
                    this.mOnErrorListenerHandler = null;
                } else {
                    this.mOnErrorListenerHandler = new Handler(myLooper);
                }
            }
            this.mOnErrorListener = onErrorListener;
        }
    }

    public void setSurface(Surface surface) {
        native_setSurface(surface);
    }

    public void setAudioTrack(AudioTrack audioTrack) {
        native_setAudioTrack(audioTrack);
        this.mAudioTrack = audioTrack;
        if (audioTrack == null || this.mAudioThread != null) {
            return;
        }
        createAudioThread();
    }

    public void setPlaybackParams(PlaybackParams playbackParams) {
        float native_setPlaybackParams;
        synchronized (this.mAudioLock) {
            native_setPlaybackParams = native_setPlaybackParams(playbackParams);
            this.mPlaybackRate = native_setPlaybackParams;
        }
        if (native_setPlaybackParams == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || this.mAudioThread == null) {
            return;
        }
        postRenderAudio(0L);
    }

    public void setSyncParams(SyncParams syncParams) {
        float native_setSyncParams;
        synchronized (this.mAudioLock) {
            native_setSyncParams = native_setSyncParams(syncParams);
            this.mPlaybackRate = native_setSyncParams;
        }
        if (native_setSyncParams == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || this.mAudioThread == null) {
            return;
        }
        postRenderAudio(0L);
    }

    public void flush() {
        synchronized (this.mAudioLock) {
            this.mAudioBuffers.clear();
            this.mCallbackHandler.removeCallbacksAndMessages(null);
        }
        AudioTrack audioTrack = this.mAudioTrack;
        if (audioTrack != null) {
            audioTrack.pause();
            this.mAudioTrack.flush();
            this.mAudioTrack.stop();
        }
        native_flush();
    }

    public MediaTimestamp getTimestamp() {
        MediaTimestamp mediaTimestamp;
        try {
            mediaTimestamp = new MediaTimestamp();
        } catch (IllegalStateException unused) {
        }
        if (native_getTimestamp(mediaTimestamp)) {
            return mediaTimestamp;
        }
        return null;
    }

    public void queueAudio(ByteBuffer byteBuffer, int i, long j) {
        if (this.mAudioTrack == null || this.mAudioThread == null) {
            throw new IllegalStateException("AudioTrack is NOT set or audio thread is not created");
        }
        synchronized (this.mAudioLock) {
            this.mAudioBuffers.add(new AudioBuffer(byteBuffer, i, j));
        }
        if (this.mPlaybackRate != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            postRenderAudio(0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postRenderAudio(long j) {
        this.mAudioHandler.postDelayed(new Runnable() { // from class: android.media.MediaSync.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (MediaSync.this.mAudioLock) {
                    if (MediaSync.this.mPlaybackRate == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        return;
                    }
                    if (MediaSync.this.mAudioBuffers.isEmpty()) {
                        return;
                    }
                    AudioBuffer audioBuffer = (AudioBuffer) MediaSync.this.mAudioBuffers.get(0);
                    int remaining = audioBuffer.mByteBuffer.remaining();
                    if (remaining > 0 && MediaSync.this.mAudioTrack.getPlayState() != 3) {
                        try {
                            MediaSync.this.mAudioTrack.play();
                        } catch (IllegalStateException unused) {
                            Log.w(MediaSync.TAG, "could not start audio track");
                        }
                    }
                    int write = MediaSync.this.mAudioTrack.write(audioBuffer.mByteBuffer, remaining, 1);
                    if (write > 0) {
                        if (audioBuffer.mPresentationTimeUs != -1) {
                            MediaSync.this.native_updateQueuedAudioData(remaining, audioBuffer.mPresentationTimeUs);
                            audioBuffer.mPresentationTimeUs = -1L;
                        }
                        if (write == remaining) {
                            MediaSync.this.postReturnByteBuffer(audioBuffer);
                            MediaSync.this.mAudioBuffers.remove(0);
                            if (!MediaSync.this.mAudioBuffers.isEmpty()) {
                                MediaSync.this.postRenderAudio(0L);
                            }
                            return;
                        }
                    }
                    MediaSync.this.postRenderAudio(TimeUnit.MICROSECONDS.toMillis(MediaSync.this.native_getPlayTimeForPendingAudioFrames()) / 2);
                }
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void postReturnByteBuffer(final AudioBuffer audioBuffer) {
        synchronized (this.mCallbackLock) {
            Handler handler = this.mCallbackHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.media.MediaSync.2
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (MediaSync.this.mCallbackLock) {
                            Callback callback = MediaSync.this.mCallback;
                            if (MediaSync.this.mCallbackHandler != null && MediaSync.this.mCallbackHandler.getLooper().getThread() == Thread.currentThread()) {
                                if (callback != null) {
                                    callback.onAudioBufferConsumed(this, audioBuffer.mByteBuffer, audioBuffer.mBufferIndex);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private final void returnAudioBuffers() {
        synchronized (this.mAudioLock) {
            Iterator<AudioBuffer> it = this.mAudioBuffers.iterator();
            while (it.hasNext()) {
                postReturnByteBuffer(it.next());
            }
            this.mAudioBuffers.clear();
        }
    }

    private void createAudioThread() {
        Thread thread = new Thread() { // from class: android.media.MediaSync.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                synchronized (MediaSync.this.mAudioLock) {
                    MediaSync.this.mAudioLooper = Looper.myLooper();
                    MediaSync.this.mAudioHandler = new Handler();
                    MediaSync.this.mAudioLock.notify();
                }
                Looper.loop();
            }
        };
        this.mAudioThread = thread;
        thread.start();
        synchronized (this.mAudioLock) {
            try {
                this.mAudioLock.wait();
            } catch (InterruptedException unused) {
            }
        }
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }
}
