package com.samsung.android.media.codec;

import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import com.samsung.android.media.codec.SemVideoTranscodingService;
import com.samsung.android.transcode.core.Encode;
import com.samsung.android.transcode.core.EncodeVideo;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SemVideoTranscoder {
    private EncodeVideo mEncodeVideo = new EncodeVideo();
    private ProgressEventListener mProgressEventListener;
    private ProgressListener mProgressListener;
    private IVideoTranscodingServiceCallback mVideoTranscodingServiceCallback;

    public interface ProgressEventListener {
        void onCompleted();

        void onStarted();
    }

    public interface ProgressListener {
        void onCompleted();

        void onProgressChanged(int i);

        void onStarted();
    }

    public static final class CodecType {
        public static final int AUDIO_CODEC_AAC = 2;
        public static final int AUDIO_CODEC_AMR = 1;
        public static final int VIDEO_CODEC_H263 = 3;
        public static final int VIDEO_CODEC_H264 = 4;
        public static final int VIDEO_CODEC_H265 = 5;

        private CodecType() {
        }
    }

    public static final class ConfigType {
        public static final int audioCodec = 2;
        public static final int audioMute = 7;
        public static final int bitDepth = 4;
        public static final int bitRate = 5;
        public static final int frameRate = 6;
        public static final int maxSize = 3;
        public static final int videoCodec = 1;

        private ConfigType() {
        }
    }

    public void encode() throws IOException {
        this.mEncodeVideo.encode();
    }

    public void rewrite() throws IOException {
        this.mEncodeVideo.rewrite();
    }

    public void stop() {
        this.mEncodeVideo.stop();
    }

    public void initialize(String str, int i, int i2, String str2) throws IOException {
        this.mEncodeVideo.initialize(str, i, i2, str2);
    }

    public void initialize(String str, int i, int i2, Context context, Uri uri) throws IOException {
        this.mEncodeVideo.initialize(str, i, i2, context, uri);
    }

    public void setTrimTime(long j, long j2) {
        this.mEncodeVideo.setTrimTime(j, j2);
    }

    public void setEncodingCodecs(int i, int i2) {
        this.mEncodeVideo.setEncodingCodecs(i, i2);
    }

    public void setMaxOutputSize(int i) {
        this.mEncodeVideo.setMaxOutputSize(i);
    }

    public int getOutputFileSize() {
        return this.mEncodeVideo.getOutputFileSize();
    }

    public void setProgressEventListener(ProgressEventListener progressEventListener) {
        this.mProgressEventListener = progressEventListener;
        this.mEncodeVideo.setProgressUpdateListener(new Encode.EncodeEventListener() { // from class: com.samsung.android.media.codec.SemVideoTranscoder.1
            @Override // com.samsung.android.transcode.core.Encode.EncodeEventListener
            public void onStarted() {
                SemVideoTranscoder.this.mProgressEventListener.onStarted();
            }

            @Override // com.samsung.android.transcode.core.Encode.EncodeEventListener
            public void onCompleted() {
                SemVideoTranscoder.this.mProgressEventListener.onCompleted();
            }
        });
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.mProgressListener = progressListener;
        this.mEncodeVideo.setEncodeProgressListener(new Encode.EncodeProgressListener() { // from class: com.samsung.android.media.codec.SemVideoTranscoder.2
            @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
            public void onStarted() {
                SemVideoTranscoder.this.mProgressListener.onStarted();
            }

            @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
            public void onProgressChanged(int i) {
                SemVideoTranscoder.this.mProgressListener.onProgressChanged(i);
            }

            @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
            public void onCompleted() {
                SemVideoTranscoder.this.mProgressListener.onCompleted();
            }
        });
    }

    public void setVideoTranscodingServiceCallback(SemVideoTranscodingService.ProgressCallback progressCallback) {
        this.mVideoTranscodingServiceCallback = progressCallback;
        this.mEncodeVideo.setEncodeProgressListener(new Encode.EncodeProgressListener() { // from class: com.samsung.android.media.codec.SemVideoTranscoder.3
            @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
            public void onStarted() {
                try {
                    SemVideoTranscoder.this.mVideoTranscodingServiceCallback.onStarted();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
            public void onProgressChanged(int i) {
                try {
                    SemVideoTranscoder.this.mVideoTranscodingServiceCallback.onProgressChanged(i);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // com.samsung.android.transcode.core.Encode.EncodeProgressListener
            public void onCompleted() {
                try {
                    SemVideoTranscoder.this.mVideoTranscodingServiceCallback.onCompleted();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static int getMaxEncodingDuration(int i, int i2, int i3, int i4) {
        return EncodeVideo.getMaxEncodingDuration(i, i2, i3, i4);
    }

    public boolean setOutputBitdepth(int i) {
        return this.mEncodeVideo.setOutputBitdepth(i);
    }

    public void setOutputConfig(int i, int i2) {
        this.mEncodeVideo.setOutputConfig(i, i2);
    }
}
