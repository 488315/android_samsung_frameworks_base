package android.speech.tts;

import android.media.AudioFormat;
import android.media.AudioTrack;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

/* loaded from: classes3.dex */
class BlockingAudioTrack {
    private static final boolean DBG = false;
    private static final long MAX_PROGRESS_WAIT_MS = 2500;
    private static final long MAX_SLEEP_TIME_MS = 2500;
    private static final int MIN_AUDIO_BUFFER_SIZE = 8192;
    private static final long MIN_SLEEP_TIME_MS = 20;
    private static final String TAG = "TTS.BlockingAudioTrack";
    private final int mAudioFormat;
    private final TextToSpeechService.AudioOutputParams mAudioParams;
    private final int mBytesPerFrame;
    private int mBytesWritten;
    private final int mChannelCount;
    private final int mSampleRateInHz;
    private int mSessionId;
    private Object mAudioTrackLock = new Object();
    private boolean mIsShortUtterance = false;
    private int mAudioBufferSize = 0;
    private AudioTrack mAudioTrack = null;
    private volatile boolean mStopped = false;

    private static final float clip(float f, float f2, float f3) {
        return f < f2 ? f2 : f < f3 ? f : f3;
    }

    private static final long clip(long j, long j2, long j3) {
        return j < j2 ? j2 : j < j3 ? j : j3;
    }

    static int getChannelConfig(int i) {
        if (i == 1) {
            return 4;
        }
        return i == 2 ? 12 : 0;
    }

    BlockingAudioTrack(TextToSpeechService.AudioOutputParams audioOutputParams, int i, int i2, int i3) {
        this.mBytesWritten = 0;
        this.mAudioParams = audioOutputParams;
        this.mSampleRateInHz = i;
        this.mAudioFormat = i2;
        this.mChannelCount = i3;
        this.mBytesPerFrame = AudioFormat.getBytesPerSample(i2) * i3;
        this.mBytesWritten = 0;
    }

    public boolean init() {
        AudioTrack createStreamingAudioTrack = createStreamingAudioTrack();
        synchronized (this.mAudioTrackLock) {
            this.mAudioTrack = createStreamingAudioTrack;
        }
        return createStreamingAudioTrack != null;
    }

    public void stop() {
        synchronized (this.mAudioTrackLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack != null) {
                audioTrack.stop();
            }
            this.mStopped = true;
        }
    }

    public int write(byte[] bArr) {
        AudioTrack audioTrack;
        synchronized (this.mAudioTrackLock) {
            audioTrack = this.mAudioTrack;
        }
        if (audioTrack == null || this.mStopped) {
            return -1;
        }
        int writeToAudioTrack = writeToAudioTrack(audioTrack, bArr);
        this.mBytesWritten += writeToAudioTrack;
        return writeToAudioTrack;
    }

    public void waitAndRelease() {
        AudioTrack audioTrack;
        synchronized (this.mAudioTrackLock) {
            audioTrack = this.mAudioTrack;
        }
        if (audioTrack == null) {
            return;
        }
        if (this.mBytesWritten < this.mAudioBufferSize && !this.mStopped) {
            this.mIsShortUtterance = true;
            audioTrack.stop();
        }
        if (!this.mStopped) {
            blockUntilDone(this.mAudioTrack);
        }
        synchronized (this.mAudioTrackLock) {
            this.mAudioTrack = null;
        }
        audioTrack.release();
    }

    long getAudioLengthMs(int i) {
        return ((i / this.mBytesPerFrame) * 1000) / this.mSampleRateInHz;
    }

    private static int writeToAudioTrack(AudioTrack audioTrack, byte[] bArr) {
        if (audioTrack.getPlayState() != 3) {
            audioTrack.play();
        }
        int i = 0;
        while (true) {
            if (i >= bArr.length) {
                break;
            }
            int write = audioTrack.write(bArr, i, bArr.length - i);
            if (write > 0) {
                i += write;
            } else if (write < 0) {
                Log.e(TAG, "An error occurred while writing to audio track: " + write);
                return i;
            }
        }
        return i;
    }

    private AudioTrack createStreamingAudioTrack() {
        int channelConfig = getChannelConfig(this.mChannelCount);
        int max = Math.max(8192, AudioTrack.getMinBufferSize(this.mSampleRateInHz, channelConfig, this.mAudioFormat));
        AudioTrack audioTrack = new AudioTrack(this.mAudioParams.mAudioAttributes, new AudioFormat.Builder().setChannelMask(channelConfig).setEncoding(this.mAudioFormat).setSampleRate(this.mSampleRateInHz).build(), max, 1, this.mAudioParams.mSessionId);
        if (audioTrack.getState() != 1) {
            Log.w(TAG, "Unable to create audio track.");
            audioTrack.release();
            return null;
        }
        this.mAudioBufferSize = max;
        setupVolume(audioTrack, this.mAudioParams.mVolume, this.mAudioParams.mPan);
        return audioTrack;
    }

    private void blockUntilDone(AudioTrack audioTrack) {
        if (this.mBytesWritten <= 0) {
            return;
        }
        if (this.mIsShortUtterance) {
            blockUntilEstimatedCompletion();
        } else {
            blockUntilCompletion(audioTrack);
        }
    }

    private void blockUntilEstimatedCompletion() {
        try {
            Thread.sleep(((this.mBytesWritten / this.mBytesPerFrame) * 1000) / this.mSampleRateInHz);
        } catch (InterruptedException unused) {
        }
    }

    private void blockUntilCompletion(AudioTrack audioTrack) {
        int i = this.mBytesWritten / this.mBytesPerFrame;
        int i2 = -1;
        long j = 0;
        while (true) {
            int playbackHeadPosition = audioTrack.getPlaybackHeadPosition();
            if (playbackHeadPosition >= i || audioTrack.getPlayState() != 3 || this.mStopped) {
                return;
            }
            long clip = clip(((i - playbackHeadPosition) * 1000) / audioTrack.getSampleRate(), 20L, 2500L);
            if (playbackHeadPosition == i2) {
                j += clip;
                if (j > 2500) {
                    Log.w(TAG, "Waited unsuccessfully for 2500ms for AudioTrack to make progress, Aborting");
                    return;
                }
            } else {
                j = 0;
            }
            try {
                Thread.sleep(clip);
                i2 = playbackHeadPosition;
            } catch (InterruptedException unused) {
                return;
            }
        }
    }

    private static void setupVolume(AudioTrack audioTrack, float f, float f2) {
        float f3;
        float clip = clip(f, 0.0f, 1.0f);
        float clip2 = clip(f2, -1.0f, 1.0f);
        if (clip2 > 0.0f) {
            float f4 = (1.0f - clip2) * clip;
            f3 = clip;
            clip = f4;
        } else {
            f3 = clip2 < 0.0f ? (clip2 + 1.0f) * clip : clip;
        }
        if (audioTrack.setStereoVolume(clip, f3) != 0) {
            Log.e(TAG, "Failed to set volume");
        }
    }

    public void setPlaybackPositionUpdateListener(AudioTrack.OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        synchronized (this.mAudioTrackLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack != null) {
                audioTrack.setPlaybackPositionUpdateListener(onPlaybackPositionUpdateListener);
            }
        }
    }

    public void setNotificationMarkerPosition(int i) {
        synchronized (this.mAudioTrackLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack != null) {
                audioTrack.setNotificationMarkerPosition(i);
            }
        }
    }
}
