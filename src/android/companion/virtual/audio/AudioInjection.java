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

    void setSilent(boolean z) {
        synchronized (this.mLock) {
            this.mIsSilent = z;
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
            AudioTrack audioTrack2 = this.mAudioTrack;
            if (audioTrack2 != null) {
                audioTrack2.release();
            }
            this.mAudioTrack = audioTrack;
        }
    }

    AudioInjection(AudioFormat audioFormat) {
        this.mAudioFormat = audioFormat;
    }

    void close() {
        synchronized (this.mLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack != null) {
                audioTrack.release();
                this.mAudioTrack = null;
            }
        }
    }

    public AudioFormat getFormat() {
        return this.mAudioFormat;
    }

    public int write(byte[] bArr, int i, int i2) {
        return write(bArr, i, i2, 0);
    }

    public int write(byte[] bArr, int i, int i2, int i3) {
        int iWrite;
        synchronized (this.mLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            iWrite = (audioTrack == null || this.mIsSilent) ? 0 : audioTrack.write(bArr, i, i2, i3);
        }
        return iWrite;
    }

    public int write(ByteBuffer byteBuffer, int i, int i2) {
        int iWrite;
        synchronized (this.mLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            iWrite = (audioTrack == null || this.mIsSilent) ? 0 : audioTrack.write(byteBuffer, i, i2);
        }
        return iWrite;
    }

    public int write(ByteBuffer byteBuffer, int i, int i2, long j) {
        int iWrite;
        synchronized (this.mLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            iWrite = (audioTrack == null || this.mIsSilent) ? 0 : audioTrack.write(byteBuffer, i, i2, j);
        }
        return iWrite;
    }

    public int write(float[] fArr, int i, int i2, int i3) {
        int iWrite;
        synchronized (this.mLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            iWrite = (audioTrack == null || this.mIsSilent) ? 0 : audioTrack.write(fArr, i, i2, i3);
        }
        return iWrite;
    }

    public int write(short[] sArr, int i, int i2) {
        return write(sArr, i, i2, 0);
    }

    public int write(short[] sArr, int i, int i2, int i3) {
        int iWrite;
        synchronized (this.mLock) {
            AudioTrack audioTrack = this.mAudioTrack;
            iWrite = (audioTrack == null || this.mIsSilent) ? 0 : audioTrack.write(sArr, i, i2, i3);
        }
        return iWrite;
    }

    public void play() {
        synchronized (this.mLock) {
            this.mPlayState = 3;
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack != null && audioTrack.getPlayState() != 3) {
                this.mAudioTrack.play();
            }
        }
    }

    public void stop() {
        synchronized (this.mLock) {
            this.mPlayState = 1;
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack != null && audioTrack.getPlayState() != 1) {
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
