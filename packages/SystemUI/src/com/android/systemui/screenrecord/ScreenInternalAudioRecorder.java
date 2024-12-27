package com.android.systemui.screenrecord;

import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.MathUtils;
import android.view.Surface;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ScreenInternalAudioRecorder {
    public final AudioRecord mAudioRecord;
    public final AudioRecord mAudioRecordMic;
    public final MediaCodec mCodec;
    public final Config mConfig;
    public final boolean mMic;
    public final MediaMuxer mMuxer;
    public long mPresentationTime;
    public boolean mStarted;
    public Thread mThread;
    public long mTotalBytes;
    public int mTrackId;

    public final class Config {
        public final int channelOutMask = 4;
        public final int channelInMask = 16;
        public final int encoding = 2;
        public final int sampleRate = 44100;
        public final int bitRate = 196000;
        public final int bufferSizeBytes = 131072;
        public final boolean privileged = true;

        public final String toString() {
            StringBuilder sb = new StringBuilder("channelMask=");
            sb.append(this.channelOutMask);
            sb.append("\n   encoding=");
            sb.append(this.encoding);
            sb.append("\n sampleRate=");
            sb.append(this.sampleRate);
            sb.append("\n bufferSize=");
            sb.append(this.bufferSizeBytes);
            sb.append("\n privileged=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.privileged, "\n legacy app looback=false");
        }
    }

    public ScreenInternalAudioRecorder(String str, MediaProjection mediaProjection, boolean z) throws IOException {
        Config config = new Config();
        this.mConfig = config;
        this.mTrackId = -1;
        this.mMic = z;
        this.mMuxer = new MediaMuxer(str, 0);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("creating audio file ", str, "ScreenAudioRecorder");
        final int minBufferSize = AudioRecord.getMinBufferSize(config.sampleRate, config.channelInMask, config.encoding) * 2;
        Log.d("ScreenAudioRecorder", "audio buffer size: " + minBufferSize);
        this.mAudioRecord = new AudioRecord.Builder().setAudioFormat(new AudioFormat.Builder().setEncoding(config.encoding).setSampleRate(config.sampleRate).setChannelMask(config.channelOutMask).build()).setAudioPlaybackCaptureConfig(new AudioPlaybackCaptureConfiguration.Builder(mediaProjection).addMatchingUsage(1).addMatchingUsage(0).addMatchingUsage(14).build()).build();
        if (z) {
            this.mAudioRecordMic = new AudioRecord(7, config.sampleRate, 16, config.encoding, minBufferSize);
        }
        this.mCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", config.sampleRate, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("bitrate", config.bitRate);
        createAudioFormat.setInteger("pcm-encoding", config.encoding);
        this.mCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mThread = new Thread(new Runnable() { // from class: com.android.systemui.screenrecord.ScreenInternalAudioRecorder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                short[] sArr;
                short[] sArr2;
                int i;
                int read;
                boolean z2;
                short[] sArr3;
                int i2;
                ScreenInternalAudioRecorder screenInternalAudioRecorder = ScreenInternalAudioRecorder.this;
                int i3 = minBufferSize;
                screenInternalAudioRecorder.getClass();
                byte[] bArr = new byte[i3];
                boolean z3 = screenInternalAudioRecorder.mMic;
                if (z3) {
                    int i4 = i3 / 2;
                    sArr = new short[i4];
                    sArr2 = new short[i4];
                } else {
                    sArr = null;
                    sArr2 = null;
                }
                short s = 0;
                int i5 = 0;
                int i6 = 0;
                int i7 = 0;
                int i8 = 0;
                while (true) {
                    if (z3) {
                        int read2 = screenInternalAudioRecorder.mAudioRecord.read(sArr, i5, sArr.length - i5);
                        int read3 = screenInternalAudioRecorder.mAudioRecordMic.read(sArr2, i6, sArr2.length - i6);
                        if (read2 < 0 && read3 < 0) {
                            break;
                        }
                        if (read2 < 0) {
                            Arrays.fill(sArr, s);
                            i5 = i6;
                            read2 = read3;
                        }
                        if (read3 < 0) {
                            Arrays.fill(sArr2, s);
                            i6 = i5;
                            read3 = read2;
                        }
                        i7 = read2 + i5;
                        i8 = read3 + i6;
                        int min = Math.min(i7, i8);
                        read = min * 2;
                        int i9 = s;
                        while (true) {
                            i2 = 32767;
                            if (i9 >= min) {
                                break;
                            }
                            sArr2[i9] = (short) MathUtils.constrain((int) (sArr2[i9] * 1.4f), -32768, 32767);
                            i9++;
                        }
                        int i10 = 0;
                        while (i10 < min) {
                            short constrain = (short) MathUtils.constrain(sArr[i10] + sArr2[i10], -32768, i2);
                            int i11 = i10 * 2;
                            bArr[i11] = (byte) (constrain & 255);
                            bArr[i11 + 1] = (byte) ((constrain >> 8) & 255);
                            i10++;
                            i2 = 32767;
                        }
                        for (int i12 = 0; i12 < i5 - min; i12++) {
                            sArr[i12] = sArr[min + i12];
                        }
                        for (int i13 = 0; i13 < i6 - min; i13++) {
                            sArr2[i13] = sArr2[min + i13];
                        }
                        i5 = i7 - min;
                        i6 = i8 - min;
                        i = 0;
                    } else {
                        i = 0;
                        read = screenInternalAudioRecorder.mAudioRecord.read(bArr, 0, i3);
                    }
                    if (read < 0) {
                        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(read, i7, "read error ", ", shorts internal: ", ", shorts mic: ");
                        m.append(i8);
                        Log.e("ScreenAudioRecorder", m.toString());
                        break;
                    }
                    int i14 = i;
                    while (true) {
                        if (read <= 0) {
                            z2 = z3;
                            sArr3 = sArr2;
                            break;
                        }
                        z2 = z3;
                        sArr3 = sArr2;
                        int dequeueInputBuffer = screenInternalAudioRecorder.mCodec.dequeueInputBuffer(500L);
                        if (dequeueInputBuffer < 0) {
                            screenInternalAudioRecorder.writeOutput();
                            break;
                        }
                        ByteBuffer inputBuffer = screenInternalAudioRecorder.mCodec.getInputBuffer(dequeueInputBuffer);
                        inputBuffer.clear();
                        int capacity = inputBuffer.capacity();
                        if (read <= capacity) {
                            capacity = read;
                        }
                        read -= capacity;
                        inputBuffer.put(bArr, i14, capacity);
                        i14 += capacity;
                        screenInternalAudioRecorder.mCodec.queueInputBuffer(dequeueInputBuffer, 0, capacity, screenInternalAudioRecorder.mPresentationTime, 0);
                        long j = screenInternalAudioRecorder.mTotalBytes + capacity;
                        screenInternalAudioRecorder.mTotalBytes = j;
                        screenInternalAudioRecorder.mPresentationTime = ((j / 2) * 1000000) / screenInternalAudioRecorder.mConfig.sampleRate;
                        screenInternalAudioRecorder.writeOutput();
                        z3 = z2;
                        sArr2 = sArr3;
                    }
                    z3 = z2;
                    sArr2 = sArr3;
                    s = 0;
                }
                screenInternalAudioRecorder.mCodec.queueInputBuffer(screenInternalAudioRecorder.mCodec.dequeueInputBuffer(500L), 0, 0, screenInternalAudioRecorder.mPresentationTime, 4);
                screenInternalAudioRecorder.writeOutput();
            }
        });
    }

    public final void writeOutput() {
        while (true) {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int dequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(bufferInfo, 500L);
            if (dequeueOutputBuffer == -2) {
                this.mTrackId = this.mMuxer.addTrack(this.mCodec.getOutputFormat());
                this.mMuxer.start();
            } else {
                if (dequeueOutputBuffer == -1 || this.mTrackId < 0) {
                    return;
                }
                ByteBuffer outputBuffer = this.mCodec.getOutputBuffer(dequeueOutputBuffer);
                if ((bufferInfo.flags & 2) == 0 || bufferInfo.size == 0) {
                    this.mMuxer.writeSampleData(this.mTrackId, outputBuffer, bufferInfo);
                }
                this.mCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
            }
        }
    }
}
