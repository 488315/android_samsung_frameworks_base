package android.companion.virtual.audio;

import android.annotation.SystemApi;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;

@SystemApi
/* loaded from: classes.dex */
public final class AudioInjection {
    private static final String TAG = "AudioInjection";
    private final AudioFormat mAudioFormat;
    private AudioTrack mAudioTrack;
    private boolean mIsSilent;
    private final Object mLock = new Object();
    private int mPlayState = 1;

    void setSilent(boolean isSilent) {
        synchronized (this.mLock) {
            this.mIsSilent = isSilent;
        }
    }

    void setAudioTrack(AudioTrack audioTrack) {
        Log.d(TAG, "set AudioTrack with " + audioTrack);
        synchronized (this.mLock) {
            if (audioTrack != null) {
                if (audioTrack.getState() != 1) {
                    throw new IllegalStateException("set an uninitialized AudioTrack.");
                }
                if (this.mPlayState == 3 && audioTrack.getPlayState() != 3) {
                    audioTrack.play();
                }
                if (this.mPlayState == 1 && audioTrack.getPlayState() != 1) {
                    audioTrack.stop();
                }
            }
            if (this.mAudioTrack != null) {
                this.mAudioTrack.release();
            }
            this.mAudioTrack = audioTrack;
        }
    }

    AudioInjection(AudioFormat audioFormat) {
        this.mAudioFormat = audioFormat;
    }

    void close() {
        synchronized (this.mLock) {
            if (this.mAudioTrack != null) {
                this.mAudioTrack.release();
                this.mAudioTrack = null;
            }
        }
    }

    public AudioFormat getFormat() {
        return this.mAudioFormat;
    }

    public int write(byte[] audioData, int offsetInBytes, int sizeInBytes) {
        return write(audioData, offsetInBytes, sizeInBytes, 0);
    }

    public int write(byte[] audioData, int offsetInBytes, int sizeInBytes, int writeMode) {
        int sizeWrite;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                sizeWrite = this.mAudioTrack.write(audioData, offsetInBytes, sizeInBytes, writeMode);
            } else {
                sizeWrite = 0;
            }
        }
        return sizeWrite;
    }

    public int write(ByteBuffer audioBuffer, int sizeInBytes, int writeMode) {
        int sizeWrite;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                sizeWrite = this.mAudioTrack.write(audioBuffer, sizeInBytes, writeMode);
            } else {
                sizeWrite = 0;
            }
        }
        return sizeWrite;
    }

    public int write(ByteBuffer audioBuffer, int sizeInBytes, int writeMode, long timestamp) {
        int sizeWrite;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                sizeWrite = this.mAudioTrack.write(audioBuffer, sizeInBytes, writeMode, timestamp);
            } else {
                sizeWrite = 0;
            }
        }
        return sizeWrite;
    }

    public int write(float[] audioData, int offsetInFloats, int sizeInFloats, int writeMode) {
        int sizeWrite;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                sizeWrite = this.mAudioTrack.write(audioData, offsetInFloats, sizeInFloats, writeMode);
            } else {
                sizeWrite = 0;
            }
        }
        return sizeWrite;
    }

    public int write(short[] audioData, int offsetInShorts, int sizeInShorts) {
        return write(audioData, offsetInShorts, sizeInShorts, 0);
    }

    public int write(short[] audioData, int offsetInShorts, int sizeInShorts, int writeMode) {
        int sizeWrite;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                sizeWrite = this.mAudioTrack.write(audioData, offsetInShorts, sizeInShorts, writeMode);
            } else {
                sizeWrite = 0;
            }
        }
        return sizeWrite;
    }

    public void play() {
        synchronized (this.mLock) {
            this.mPlayState = 3;
            if (this.mAudioTrack != null && this.mAudioTrack.getPlayState() != 3) {
                this.mAudioTrack.play();
            }
        }
    }

    public void stop() {
        synchronized (this.mLock) {
            this.mPlayState = 1;
            if (this.mAudioTrack != null && this.mAudioTrack.getPlayState() != 1) {
                this.mAudioTrack.stop();
            }
        }
    }

    public int getPlayState() {
        int i;
        synchronized (this.mLock) {
            i = this.mPlayState;
        }
        return i;
    }
}
