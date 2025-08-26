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
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ScreenInternalAudioRecorder {
    public final AudioRecord mAudioRecord;
    public final AudioRecord mAudioRecordMic;
    public final MediaCodec mCodec;
    public final boolean mMic;
    public final MediaMuxer mMuxer;
    public long mPresentationTime;
    public boolean mStarted;
    public Thread mThread;
    public long mTotalBytes;
    public final Config mConfig = new Config();
    public int mTrackId = -1;

    public class Config {
        public final String toString() {
            return "channelMask=4\n   encoding=2\n sampleRate=44100\n bufferSize=131072\n privileged=true\n legacy app looback=false";
        }
    }

    public ScreenInternalAudioRecorder(String str, MediaProjection mediaProjection, boolean z) throws IOException {
        this.mMic = z;
        this.mMuxer = new MediaMuxer(str, 0);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("creating audio file ", str, "ScreenAudioRecorder");
        final int minBufferSize = AudioRecord.getMinBufferSize(44100, 16, 2) * 2;
        Log.d("ScreenAudioRecorder", "audio buffer size: " + minBufferSize);
        this.mAudioRecord = new AudioRecord.Builder().setAudioFormat(new AudioFormat.Builder().setEncoding(2).setSampleRate(44100).setChannelMask(4).build()).setAudioPlaybackCaptureConfig(new AudioPlaybackCaptureConfiguration.Builder(mediaProjection).addMatchingUsage(1).addMatchingUsage(0).addMatchingUsage(14).build()).build();
        if (z) {
            this.mAudioRecordMic = new AudioRecord(7, 44100, 16, 2, minBufferSize);
        }
        this.mCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", 44100, 1);
        mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
        mediaFormatCreateAudioFormat.setInteger("bitrate", 196000);
        mediaFormatCreateAudioFormat.setInteger("pcm-encoding", 2);
        this.mCodec.configure(mediaFormatCreateAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mThread = new Thread(new Runnable() { // from class: com.android.systemui.screenrecord.ScreenInternalAudioRecorder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws MediaCodec.CryptoException {
                short[] sArr;
                short[] sArr2;
                int i;
                int i2;
                boolean z2;
                short[] sArr3;
                int i3;
                ScreenInternalAudioRecorder screenInternalAudioRecorder = this.f$0;
                int i4 = minBufferSize;
                byte[] bArr = new byte[i4];
                boolean z3 = screenInternalAudioRecorder.mMic;
                if (z3) {
                    int i5 = i4 / 2;
                    sArr = new short[i5];
                    sArr2 = new short[i5];
                } else {
                    sArr = null;
                    sArr2 = null;
                }
                short s = 0;
                int i6 = 0;
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                while (true) {
                    if (z3) {
                        int i10 = screenInternalAudioRecorder.mAudioRecord.read(sArr, i6, sArr.length - i6);
                        int i11 = screenInternalAudioRecorder.mAudioRecordMic.read(sArr2, i7, sArr2.length - i7);
                        if (i10 < 0 && i11 < 0) {
                            break;
                        }
                        if (i10 < 0) {
                            Arrays.fill(sArr, s);
                            i6 = i7;
                            i10 = i11;
                        }
                        if (i11 < 0) {
                            Arrays.fill(sArr2, s);
                            i7 = i6;
                            i11 = i10;
                        }
                        i8 = i10 + i6;
                        i9 = i11 + i7;
                        int iMin = Math.min(i8, i9);
                        i2 = iMin * 2;
                        int i12 = s;
                        while (true) {
                            i3 = 32767;
                            if (i12 >= iMin) {
                                break;
                            }
                            sArr2[i12] = (short) MathUtils.constrain((int) (sArr2[i12] * 1.4f), -32768, 32767);
                            i12++;
                        }
                        int i13 = 0;
                        while (i13 < iMin) {
                            short sConstrain = (short) MathUtils.constrain(sArr[i13] + sArr2[i13], -32768, i3);
                            int i14 = i13 * 2;
                            bArr[i14] = (byte) (sConstrain & 255);
                            bArr[i14 + 1] = (byte) ((sConstrain >> 8) & 255);
                            i13++;
                            i3 = 32767;
                        }
                        for (int i15 = 0; i15 < i6 - iMin; i15++) {
                            sArr[i15] = sArr[iMin + i15];
                        }
                        for (int i16 = 0; i16 < i7 - iMin; i16++) {
                            sArr2[i16] = sArr2[iMin + i16];
                        }
                        i6 = i8 - iMin;
                        i7 = i9 - iMin;
                        i = 0;
                    } else {
                        i = 0;
                        i2 = screenInternalAudioRecorder.mAudioRecord.read(bArr, 0, i4);
                    }
                    if (i2 < 0) {
                        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i2, i8, "read error ", ", shorts internal: ", ", shorts mic: ");
                        sbM.append(i9);
                        Log.e("ScreenAudioRecorder", sbM.toString());
                        break;
                    }
                    int i17 = i;
                    while (true) {
                        if (i2 <= 0) {
                            z2 = z3;
                            sArr3 = sArr2;
                            break;
                        }
                        z2 = z3;
                        sArr3 = sArr2;
                        int iDequeueInputBuffer = screenInternalAudioRecorder.mCodec.dequeueInputBuffer(500L);
                        if (iDequeueInputBuffer < 0) {
                            screenInternalAudioRecorder.writeOutput();
                            break;
                        }
                        ByteBuffer inputBuffer = screenInternalAudioRecorder.mCodec.getInputBuffer(iDequeueInputBuffer);
                        inputBuffer.clear();
                        int iCapacity = inputBuffer.capacity();
                        if (i2 <= iCapacity) {
                            iCapacity = i2;
                        }
                        i2 -= iCapacity;
                        inputBuffer.put(bArr, i17, iCapacity);
                        i17 += iCapacity;
                        screenInternalAudioRecorder.mCodec.queueInputBuffer(iDequeueInputBuffer, 0, iCapacity, screenInternalAudioRecorder.mPresentationTime, 0);
                        long j = screenInternalAudioRecorder.mTotalBytes + iCapacity;
                        screenInternalAudioRecorder.mTotalBytes = j;
                        screenInternalAudioRecorder.mConfig.getClass();
                        screenInternalAudioRecorder.mPresentationTime = ((j / 2) * 1000000) / 44100;
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
            int iDequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(bufferInfo, 500L);
            if (iDequeueOutputBuffer == -2) {
                this.mTrackId = this.mMuxer.addTrack(this.mCodec.getOutputFormat());
                this.mMuxer.start();
            } else {
                if (iDequeueOutputBuffer == -1 || this.mTrackId < 0) {
                    return;
                }
                ByteBuffer outputBuffer = this.mCodec.getOutputBuffer(iDequeueOutputBuffer);
                if ((bufferInfo.flags & 2) == 0 || bufferInfo.size == 0) {
                    this.mMuxer.writeSampleData(this.mTrackId, outputBuffer, bufferInfo);
                }
                this.mCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
            }
        }
    }
}
