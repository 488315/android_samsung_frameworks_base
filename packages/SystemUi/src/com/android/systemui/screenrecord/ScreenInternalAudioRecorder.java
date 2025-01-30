package com.android.systemui.screenrecord;

import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.MathUtils;
import android.view.Surface;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenInternalAudioRecorder {
    public AudioRecord mAudioRecord;
    public AudioRecord mAudioRecordMic;
    public MediaCodec mCodec;
    public final Config mConfig;
    public final boolean mMic;
    public final MediaMuxer mMuxer;
    public long mPresentationTime;
    public boolean mStarted;
    public Thread mThread;
    public long mTotalBytes;
    public int mTrackId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.privileged, "\n legacy app looback=false");
        }
    }

    public ScreenInternalAudioRecorder(String str, MediaProjection mediaProjection, boolean z) {
        Config config = new Config();
        this.mConfig = config;
        this.mTrackId = -1;
        this.mMic = z;
        this.mMuxer = new MediaMuxer(str, 0);
        AbstractC0000x2c234b15.m3m("creating audio file ", str, "ScreenAudioRecorder");
        int i = config.sampleRate;
        int i2 = config.channelInMask;
        int i3 = config.encoding;
        final int minBufferSize = AudioRecord.getMinBufferSize(i, i2, i3) * 2;
        Log.d("ScreenAudioRecorder", "audio buffer size: " + minBufferSize);
        this.mAudioRecord = new AudioRecord.Builder().setAudioFormat(new AudioFormat.Builder().setEncoding(i3).setSampleRate(i).setChannelMask(config.channelOutMask).build()).setAudioPlaybackCaptureConfig(new AudioPlaybackCaptureConfiguration.Builder(mediaProjection).addMatchingUsage(1).addMatchingUsage(0).addMatchingUsage(14).build()).build();
        if (z) {
            this.mAudioRecordMic = new AudioRecord(7, config.sampleRate, 16, config.encoding, minBufferSize);
        }
        this.mCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", i, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("bitrate", config.bitRate);
        createAudioFormat.setInteger("pcm-encoding", i3);
        this.mCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mThread = new Thread(new Runnable() { // from class: com.android.systemui.screenrecord.ScreenInternalAudioRecorder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                short[] sArr;
                short[] sArr2;
                int read;
                int i4;
                int i5;
                int i6;
                ScreenInternalAudioRecorder screenInternalAudioRecorder = ScreenInternalAudioRecorder.this;
                int i7 = minBufferSize;
                screenInternalAudioRecorder.getClass();
                byte[] bArr = new byte[i7];
                boolean z2 = screenInternalAudioRecorder.mMic;
                if (z2) {
                    int i8 = i7 / 2;
                    sArr = new short[i8];
                    sArr2 = new short[i8];
                } else {
                    sArr = null;
                    sArr2 = null;
                }
                int i9 = 0;
                int i10 = 0;
                short s = 0;
                int i11 = 0;
                int i12 = 0;
                while (true) {
                    if (z2) {
                        int read2 = screenInternalAudioRecorder.mAudioRecord.read(sArr, i9, sArr.length - i9);
                        int read3 = screenInternalAudioRecorder.mAudioRecordMic.read(sArr2, i10, sArr2.length - i10);
                        if (read2 < 0 && read3 < 0) {
                            break;
                        }
                        if (read2 < 0) {
                            Arrays.fill(sArr, s);
                            i9 = i10;
                            read2 = read3;
                        }
                        if (read3 < 0) {
                            Arrays.fill(sArr2, s);
                            i10 = i9;
                            read3 = read2;
                        }
                        i11 = read2 + i9;
                        i12 = read3 + i10;
                        int min = Math.min(i11, i12);
                        read = min * 2;
                        int i13 = s;
                        while (true) {
                            i5 = 32767;
                            i6 = -32768;
                            if (i13 >= min) {
                                break;
                            }
                            sArr2[i13] = (short) MathUtils.constrain((int) (sArr2[i13] * 1.4f), -32768, 32767);
                            i13++;
                        }
                        int i14 = 0;
                        while (i14 < min) {
                            short constrain = (short) MathUtils.constrain(sArr[i14] + sArr2[i14], i6, i5);
                            int i15 = i14 * 2;
                            bArr[i15] = (byte) (constrain & 255);
                            bArr[i15 + 1] = (byte) ((constrain >> 8) & 255);
                            i14++;
                            i5 = 32767;
                            i6 = -32768;
                        }
                        for (int i16 = 0; i16 < i9 - min; i16++) {
                            sArr[i16] = sArr[min + i16];
                        }
                        for (int i17 = 0; i17 < i10 - min; i17++) {
                            sArr2[i17] = sArr2[min + i17];
                        }
                        i9 = i11 - min;
                        i10 = i12 - min;
                        i4 = 0;
                    } else {
                        read = screenInternalAudioRecorder.mAudioRecord.read(bArr, 0, i7);
                        i4 = 0;
                    }
                    if (read < 0) {
                        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("read error ", read, ", shorts internal: ", i11, ", shorts mic: ");
                        m45m.append(i12);
                        Log.e("ScreenAudioRecorder", m45m.toString());
                        break;
                    }
                    while (true) {
                        if (read <= 0) {
                            break;
                        }
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
                        inputBuffer.put(bArr, i4, capacity);
                        i4 += capacity;
                        byte[] bArr2 = bArr;
                        screenInternalAudioRecorder.mCodec.queueInputBuffer(dequeueInputBuffer, 0, capacity, screenInternalAudioRecorder.mPresentationTime, 0);
                        long j = screenInternalAudioRecorder.mTotalBytes + capacity + 0;
                        screenInternalAudioRecorder.mTotalBytes = j;
                        screenInternalAudioRecorder.mPresentationTime = ((j / 2) * 1000000) / screenInternalAudioRecorder.mConfig.sampleRate;
                        screenInternalAudioRecorder.writeOutput();
                        bArr = bArr2;
                        z2 = z2;
                        i7 = i7;
                    }
                    s = 0;
                    bArr = bArr;
                    z2 = z2;
                    i7 = i7;
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
            MediaMuxer mediaMuxer = this.mMuxer;
            if (dequeueOutputBuffer == -2) {
                this.mTrackId = mediaMuxer.addTrack(this.mCodec.getOutputFormat());
                mediaMuxer.start();
            } else {
                if (dequeueOutputBuffer == -1 || this.mTrackId < 0) {
                    return;
                }
                ByteBuffer outputBuffer = this.mCodec.getOutputBuffer(dequeueOutputBuffer);
                if ((bufferInfo.flags & 2) == 0 || bufferInfo.size == 0) {
                    mediaMuxer.writeSampleData(this.mTrackId, outputBuffer, bufferInfo);
                }
                this.mCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
            }
        }
    }
}
