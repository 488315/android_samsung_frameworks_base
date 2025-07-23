package android.speech.tts;

import android.speech.tts.TextToSpeechService;
import android.util.Log;

/* loaded from: classes3.dex */
class PlaybackSynthesisCallback extends AbstractSynthesisCallback {
    private static final boolean DBG = false;
    private static final int MIN_AUDIO_BUFFER_SIZE = 8192;
    private static final String TAG = "PlaybackSynthesisRequest";
    private final TextToSpeechService.AudioOutputParams mAudioParams;
    private final AudioPlaybackHandler mAudioTrackHandler;
    private final Object mCallerIdentity;
    private final TextToSpeechService.UtteranceProgressDispatcher mDispatcher;
    private volatile boolean mDone;
    private SynthesisPlaybackQueueItem mItem;
    private final AbstractEventLogger mLogger;
    private final Object mStateLock;
    protected int mStatusCode;

    @Override // android.speech.tts.SynthesisCallback
    public int getMaxBufferSize() {
        return 8192;
    }

    PlaybackSynthesisCallback(TextToSpeechService.AudioOutputParams audioOutputParams, AudioPlaybackHandler audioPlaybackHandler, TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object obj, AbstractEventLogger abstractEventLogger, boolean z) {
        super(z);
        this.mStateLock = new Object();
        this.mItem = null;
        this.mDone = false;
        this.mAudioParams = audioOutputParams;
        this.mAudioTrackHandler = audioPlaybackHandler;
        this.mDispatcher = utteranceProgressDispatcher;
        this.mCallerIdentity = obj;
        this.mLogger = abstractEventLogger;
        this.mStatusCode = 0;
    }

    @Override // android.speech.tts.AbstractSynthesisCallback
    void stop() {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                return;
            }
            if (this.mStatusCode == -2) {
                Log.w(TAG, "stop() called twice");
                return;
            }
            SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
            this.mStatusCode = -2;
            if (synthesisPlaybackQueueItem != null) {
                synthesisPlaybackQueueItem.stop(-2);
            } else {
                this.mLogger.onCompleted(-2);
                this.mDispatcher.dispatchOnStop();
            }
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public boolean hasStarted() {
        boolean z;
        synchronized (this.mStateLock) {
            z = this.mItem != null;
        }
        return z;
    }

    @Override // android.speech.tts.SynthesisCallback
    public boolean hasFinished() {
        boolean z;
        synchronized (this.mStateLock) {
            z = this.mDone;
        }
        return z;
    }

    @Override // android.speech.tts.SynthesisCallback
    public int start(int i, int i2, int i3) {
        if (i2 != 3 && i2 != 2 && i2 != 4) {
            Log.w(TAG, "Audio format encoding " + i2 + " not supported. Please use one of AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT or AudioFormat.ENCODING_PCM_FLOAT");
        }
        this.mDispatcher.dispatchOnBeginSynthesis(i, i2, i3);
        int channelConfig = BlockingAudioTrack.getChannelConfig(i3);
        synchronized (this.mStateLock) {
            if (channelConfig == 0) {
                Log.e(TAG, "Unsupported number of channels :" + i3);
                this.mStatusCode = -5;
                return -1;
            }
            int i4 = this.mStatusCode;
            if (i4 == -2) {
                return errorCodeOnStop();
            }
            if (i4 != 0) {
                return -1;
            }
            if (this.mItem != null) {
                Log.e(TAG, "Start called twice");
                return -1;
            }
            SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = new SynthesisPlaybackQueueItem(this.mAudioParams, i, i2, i3, this.mDispatcher, this.mCallerIdentity, this.mLogger);
            this.mAudioTrackHandler.enqueue(synthesisPlaybackQueueItem);
            this.mItem = synthesisPlaybackQueueItem;
            return 0;
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public int audioAvailable(byte[] bArr, int i, int i2) {
        if (i2 > getMaxBufferSize() || i2 <= 0) {
            throw new IllegalArgumentException("buffer is too large or of zero length (" + i2 + " bytes)");
        }
        synchronized (this.mStateLock) {
            SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
            if (synthesisPlaybackQueueItem == null) {
                this.mStatusCode = -5;
                return -1;
            }
            int i3 = this.mStatusCode;
            if (i3 != 0) {
                return -1;
            }
            if (i3 == -2) {
                return errorCodeOnStop();
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            this.mDispatcher.dispatchOnAudioAvailable(bArr2);
            try {
                synthesisPlaybackQueueItem.put(bArr2);
                this.mLogger.onEngineDataReceived();
                return 0;
            } catch (InterruptedException unused) {
                synchronized (this.mStateLock) {
                    this.mStatusCode = -5;
                    return -1;
                }
            }
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public int done() {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                Log.w(TAG, "Duplicate call to done()");
                return -1;
            }
            if (this.mStatusCode == -2) {
                return errorCodeOnStop();
            }
            this.mDone = true;
            SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
            if (synthesisPlaybackQueueItem == null) {
                Log.w(TAG, "done() was called before start() call");
                int i = this.mStatusCode;
                if (i == 0) {
                    this.mDispatcher.dispatchOnSuccess();
                } else {
                    this.mDispatcher.dispatchOnError(i);
                }
                this.mLogger.onEngineComplete();
                return -1;
            }
            int i2 = this.mStatusCode;
            if (i2 == 0) {
                synthesisPlaybackQueueItem.done();
            } else {
                synthesisPlaybackQueueItem.stop(i2);
            }
            this.mLogger.onEngineComplete();
            return 0;
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public void error() {
        error(-3);
    }

    @Override // android.speech.tts.SynthesisCallback
    public void error(int i) {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                return;
            }
            this.mStatusCode = i;
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public void rangeStart(int i, int i2, int i3) {
        SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
        if (synthesisPlaybackQueueItem == null) {
            Log.e(TAG, "mItem is null");
        } else {
            synthesisPlaybackQueueItem.rangeStart(i, i2, i3);
        }
    }
}
