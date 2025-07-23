package android.companion.virtual.audio;

import android.annotation.SystemApi;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;
import java.nio.ByteBuffer;

@SystemApi
/* loaded from: classes.dex */
public final class AudioCapture {
    private static final String TAG = "AudioCapture";
    private final AudioFormat mAudioFormat;
    private AudioRecord mAudioRecord;
    private final Object mLock = new Object();
    private int mRecordingState = 1;

    void setAudioRecord(AudioRecord audioRecord) {
        Log.d(TAG, "set AudioRecord with " + audioRecord);
        synchronized (this.mLock) {
            if (audioRecord != null) {
                if (audioRecord.getState() != 1) {
                    throw new IllegalStateException("set an uninitialized AudioRecord.");
                }
                if (this.mRecordingState == 3 && audioRecord.getRecordingState() != 3) {
                    audioRecord.startRecording();
                }
                if (this.mRecordingState == 1 && audioRecord.getRecordingState() != 1) {
                    audioRecord.stop();
                }
            }
            AudioRecord audioRecord2 = this.mAudioRecord;
            if (audioRecord2 != null) {
                audioRecord2.release();
            }
            this.mAudioRecord = audioRecord;
        }
    }

    AudioCapture(AudioFormat audioFormat) {
        this.mAudioFormat = audioFormat;
    }

    void close() {
        synchronized (this.mLock) {
            AudioRecord audioRecord = this.mAudioRecord;
            if (audioRecord != null) {
                audioRecord.release();
                this.mAudioRecord = null;
            }
        }
    }

    public AudioFormat getFormat() {
        return this.mAudioFormat;
    }

    public int read(byte[] bArr, int i, int i2) {
        return read(bArr, i, i2, 0);
    }

    public int read(byte[] bArr, int i, int i2, int i3) {
        int read;
        synchronized (this.mLock) {
            AudioRecord audioRecord = this.mAudioRecord;
            read = audioRecord != null ? audioRecord.read(bArr, i, i2, i3) : 0;
        }
        return read;
    }

    public int read(ByteBuffer byteBuffer, int i) {
        return read(byteBuffer, i, 0);
    }

    public int read(ByteBuffer byteBuffer, int i, int i2) {
        int read;
        synchronized (this.mLock) {
            AudioRecord audioRecord = this.mAudioRecord;
            read = audioRecord != null ? audioRecord.read(byteBuffer, i, i2) : 0;
        }
        return read;
    }

    public int read(float[] fArr, int i, int i2, int i3) {
        int read;
        synchronized (this.mLock) {
            AudioRecord audioRecord = this.mAudioRecord;
            read = audioRecord != null ? audioRecord.read(fArr, i, i2, i3) : 0;
        }
        return read;
    }

    public int read(short[] sArr, int i, int i2) {
        return read(sArr, i, i2, 0);
    }

    public int read(short[] sArr, int i, int i2, int i3) {
        int read;
        synchronized (this.mLock) {
            AudioRecord audioRecord = this.mAudioRecord;
            read = audioRecord != null ? audioRecord.read(sArr, i, i2, i3) : 0;
        }
        return read;
    }

    public void startRecording() {
        synchronized (this.mLock) {
            this.mRecordingState = 3;
            AudioRecord audioRecord = this.mAudioRecord;
            if (audioRecord != null && audioRecord.getRecordingState() != 3) {
                this.mAudioRecord.startRecording();
            }
        }
    }

    public void stop() {
        synchronized (this.mLock) {
            this.mRecordingState = 1;
            AudioRecord audioRecord = this.mAudioRecord;
            if (audioRecord != null && audioRecord.getRecordingState() != 1) {
                this.mAudioRecord.stop();
            }
        }
    }

    public int getRecordingState() {
        int i;
        synchronized (this.mLock) {
            i = this.mRecordingState;
        }
        return i;
    }
}
