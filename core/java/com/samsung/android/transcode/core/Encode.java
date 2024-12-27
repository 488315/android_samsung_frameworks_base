package com.samsung.android.transcode.core;

import android.media.MediaCodec;
import android.media.MediaMuxer;

import com.samsung.android.transcode.util.LogS;

import java.io.File;
import java.io.IOException;

public abstract class Encode {
    protected static final int INVALID_OUTPUT_BIT_RATE = -1;
    private static final long ONE_BILLION = 1000000000;
    protected static final int ORIENTATION_0 = 0;
    protected static final int ORIENTATION_180 = 180;
    protected static final int ORIENTATION_270 = 270;
    protected static final int ORIENTATION_90 = 90;
    protected static final int SUPER_SLOW_SPEED_CANCEL = 9;
    private static final String VERSION = "4.67";
    protected EncodeEventListener mEncodeEventListener;
    protected EncodeProgressListener mEncodeProgressListener;
    protected int mFramesSkipInterval;
    protected MediaCodec mInputAudioDecoder;
    protected MediaCodec mInputVideoDecoder;
    protected MediaMuxer mMuxer;
    protected boolean mMuxerStarted;
    protected MediaCodec mOutputAudioEncoder;
    protected String mOutputFilePath;
    protected int mOutputHeight;
    protected MediaCodec mOutputVideoEncoder;
    protected int mOutputWidth;
    protected boolean mSkipFrames;
    protected int mOutputFormat = 0;
    protected String mOutputVideoMimeType = "video/avc";
    protected int mOutputVideoBitRate = -1;
    protected int mOutputVideoFrameRate = 30;
    protected int mOutputVideoIFrameInterval = 1;
    protected int mOutputVideoTargetFrameRate = 0;
    protected String mOutputAudioMimeType = "audio/mp4a-latm";
    protected int mOutputAudioChannelCount = 2;
    protected int mOutputAudioBitRate = 128000;
    protected int mOutputAudioAACProfile = 2;
    protected int mOutputAudioSampleRateHZ = 44100;
    protected boolean mOutputAudioMute = false;
    protected int mVideoTrackIndex = -1;
    protected int mAudioTrackIndex = -1;
    protected volatile boolean mUserStop = false;
    protected volatile boolean mPrepared = false;
    protected boolean mCodecError = false;
    protected float mSizeFraction = 0.8f;
    protected long mOutputMaxSizeKB = -1;
    protected boolean mRewritable = false;
    protected boolean m2ndTimeEncoding = false;
    protected int mOriginalAudioChannelCount = 0;
    protected boolean mMMSMode = false;
    protected boolean mKeepSourceFrameRate = false;
    protected int mSourceFrameRate = 30;
    protected boolean mConvert = false;
    protected int mHDRType = 0;
    protected boolean mIsHLG = false;
    protected boolean mSMConvert = false;
    protected boolean mSMEncode = false;
    protected int mProgress = 0;

    public static final class CodecType {
        public static final int AUDIO_CODEC_AAC = 2;
        public static final int AUDIO_CODEC_AMR = 1;
        public static final int VIDEO_CODEC_H263 = 3;
        public static final int VIDEO_CODEC_H264 = 4;
        public static final int VIDEO_CODEC_H265 = 5;
    }

    public static final class ConfigType {
        public static final int AudioCodec = 2;
        public static final int AudioMute = 7;
        public static final int BitRate = 5;
        public static final int Bitdepth = 4;
        public static final int Framerate = 6;
        public static final int MaxSize = 3;
        public static final int VideoCodec = 1;
    }

    public interface EncodeEventListener {
        void onCompleted();

        void onStarted();
    }

    public interface EncodeProgressListener {
        void onCompleted();

        void onProgressChanged(int i);

        void onStarted();
    }

    protected abstract void prepare() throws IOException;

    protected abstract void prepareForRewrite() throws IOException;

    protected abstract void release();

    protected abstract void startEncoding() throws IOException;

    public abstract void startRewriting() throws IOException;

    public abstract void startSMEncoding() throws IOException;

    public abstract void startSMRewriting() throws IOException;

    public abstract void stop();

    public void setupAndExecuteEncode() throws IOException {
        prepareListener();
        if (this.mSMConvert) {
            LogS.d("TranscodeLib", "starting encoder preparation  - SlowMo");
            prepareForRewrite();
        } else {
            LogS.e(
                    "TranscodeLib",
                    "starting encoder preparation  mSMEncode  : "
                            + this.mSMEncode
                            + " mConvert : "
                            + this.mConvert);
            prepare();
        }
        LogS.d("TranscodeLib", "encoder preparation done.");
        this.mCodecError = false;
        if (this.mSMConvert) {
            startSMRewriting();
        } else if (this.mSMEncode) {
            startSMEncoding();
        } else {
            startEncoding();
        }
        LogS.e("TranscodeLib", "encoding finished.");
    }

    public void encode() throws IOException {
        printVersionInfo();
        try {
            setupAndExecuteEncode();
            release();
            File file = new File(this.mOutputFilePath);
            long size = file.length();
            LogS.d("TranscodeLib", "generated output file size : " + size);
            if (!this.mSMConvert
                    && !this.mConvert
                    && !this.mUserStop
                    && this.mOutputMaxSizeKB != -1
                    && size / 1024.0d > this.mOutputMaxSizeKB
                    && (this instanceof EncodeVideo)) {
                if (!file.delete()) {
                    LogS.e("TranscodeLib", "Could not clean up file: " + file.getAbsolutePath());
                }
                this.mSizeFraction =
                        (((this.mSizeFraction * this.mOutputMaxSizeKB) * 1024.0f) / size) - 0.05f;
                LogS.d(
                        "TranscodeLib",
                        "file size("
                                + size
                                + ") exceeded the expected("
                                + (this.mOutputMaxSizeKB * 1024)
                                + ") size limit. new fraction :"
                                + this.mSizeFraction);
                this.mOutputVideoBitRate = -1;
                this.mSkipFrames = true;
                if (this.mFramesSkipInterval < 2) {
                    this.mFramesSkipInterval = 2;
                } else {
                    this.mFramesSkipInterval *= 2;
                }
                if (this.mOutputWidth == 176) {
                    this.mOutputWidth = 128;
                    this.mOutputHeight = 96;
                }
                try {
                    LogS.d("TranscodeLib", "2nd time starting encoder preparation");
                    this.m2ndTimeEncoding = true;
                    prepare();
                    LogS.d("TranscodeLib", "2nd time encoder preparation done.");
                    this.mMuxer = new MediaMuxer(this.mOutputFilePath, this.mOutputFormat);
                    this.mMuxerStarted = false;
                    this.mVideoTrackIndex = -1;
                    this.mAudioTrackIndex = -1;
                    LogS.d("TranscodeLib", "2nd time starting to encode");
                    if (this.mSMEncode) {
                        startSMEncoding();
                    } else {
                        startEncoding();
                    }
                    LogS.d("TranscodeLib", "2nd time encoding finished.");
                    release();
                    LogS.e(
                            "TranscodeLib",
                            "2nd generated output size : "
                                    + new File(this.mOutputFilePath).length());
                    this.m2ndTimeEncoding = false;
                } finally {
                }
            }
            releaseListener();
        } finally {
        }
    }

    public void rewrite() throws IOException {
        printVersionInfo();
        try {
            LogS.d("TranscodeLib", "starting Rewrite prepartion");
            prepareForRewrite();
            LogS.d("TranscodeLib", "Rewrite preparation done.");
            prepareListener();
            if (!this.mRewritable) {
                throw new IOException("Can't rewirte");
            }
            startRewriting();
            LogS.d("TranscodeLib", "Rewrite finished.");
            release();
            releaseListener();
        } catch (Throwable th) {
            release();
            throw th;
        }
    }

    public void setProgressUpdateListener(EncodeEventListener updateListener) {
        this.mEncodeEventListener = updateListener;
    }

    public void setEncodeProgressListener(EncodeProgressListener progressListener) {
        this.mEncodeProgressListener = progressListener;
    }

    protected long computePresentationTimeNsec(int frameIndex) {
        return (frameIndex * 1000000000) / this.mOutputVideoFrameRate;
    }

    private void prepareListener() throws IOException {
        this.mMuxer = new MediaMuxer(this.mOutputFilePath, this.mOutputFormat);
        this.mMuxerStarted = false;
        this.mVideoTrackIndex = -1;
        this.mAudioTrackIndex = -1;
        this.mProgress = 0;
        LogS.d("TranscodeLib", "starting to encode");
        if (this.mEncodeEventListener != null) {
            this.mEncodeEventListener.onStarted();
        }
        if (this.mEncodeProgressListener != null) {
            this.mEncodeProgressListener.onStarted();
        }
    }

    private void releaseListener() {
        if (this.mEncodeEventListener != null) {
            if (!this.mUserStop) {
                LogS.e("TranscodeLib", "calling onCompleted");
                this.mEncodeEventListener.onCompleted();
            } else {
                LogS.d("TranscodeLib", "user stopped. Not calling onCompleted");
            }
            this.mEncodeEventListener = null;
        }
        if (this.mEncodeProgressListener != null) {
            if (!this.mUserStop) {
                LogS.e("TranscodeLib", "calling onCompleted");
                this.mEncodeProgressListener.onCompleted();
            } else {
                LogS.d("TranscodeLib", "user stopped. Not calling onCompleted");
            }
            this.mEncodeProgressListener = null;
        }
    }

    private void printVersionInfo() {
        LogS.e("TranscodeLib", "Transcode Framework v.4.67");
    }

    public static String getLibraryVersion() {
        LogS.e("TranscodeLib", "getLibraryVersion  : Transcode Framework v.4.67");
        return VERSION;
    }

    protected boolean isHDR10() {
        return this.mHDRType == 1 || this.mHDRType == 2;
    }

    protected boolean isHDR10Plus() {
        return this.mHDRType == 2;
    }

    protected boolean isHLG() {
        return this.mIsHLG;
    }
}
