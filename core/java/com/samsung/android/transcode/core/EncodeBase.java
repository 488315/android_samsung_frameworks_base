package com.samsung.android.transcode.core;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.SemSystemProperties;
import android.text.TextUtils;
import android.view.Surface;

import com.android.internal.midi.MidiConstants;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.transcode.info.ExportMediaInfo;
import com.samsung.android.transcode.info.MediaInfo;
import com.samsung.android.transcode.info.MediaInfoChecker;
import com.samsung.android.transcode.surfaces.InputSurface;
import com.samsung.android.transcode.surfaces.OutputSurface;
import com.samsung.android.transcode.unit.decoder.DecodedFrame;
import com.samsung.android.transcode.unit.decoder.DecoderFrameManager;
import com.samsung.android.transcode.unit.decoder.DecoderReleaseListener;
import com.samsung.android.transcode.util.AudioSolution;
import com.samsung.android.transcode.util.CodecsHelper;
import com.samsung.android.transcode.util.FileHelper;
import com.samsung.android.transcode.util.LogS;
import com.samsung.android.transcode.util.SEFHelper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public abstract class EncodeBase extends Encode {
    protected static final int ENCODER_LOOP_COUNT = 3;
    protected static final int HAL_PIXEL_FORMAT_EXYNOS_YCbCr_420_SP_M = 261;
    protected static final int IMAGE_WAIT_TIMEOUT_MS = 1000;
    private static final String KEY_ERROR_TYPE = "error-type";
    protected static final String KEY_MUXER_AUTHOR = "param-meta-author";
    protected static final String KEY_MUXER_MODEL_NAME = "param-meta-brand-model-name";
    protected static final String KEY_MUXER_RECORDINGMODE = "param-meta-recording-mode";
    protected static final String KEY_MUXER_TRANSCODING = "param-meta-transcoding";
    protected static final int OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar32m = 2141391876;
    protected static final int REWRITE_AUDIO_BUFFER_SIZE = 131072;
    protected static final int TEMP_AUDIO_BUF_SIZE = 4096;
    protected static final long TIMEOUT_USEC = 10000;
    protected static MediaFormat mInputAudioinfo;
    protected static MediaInfo.MediaFileInfo mInputFileinfo;
    protected static MediaFormat mInputVideoinfo;
    protected boolean mAudioDecoderDone;
    protected ByteBuffer[] mAudioDecoderInputBuffers;
    protected MediaCodec.BufferInfo mAudioDecoderOutputBufferInfo;
    protected ByteBuffer[] mAudioDecoderOutputBuffers;
    protected boolean mAudioEncoderDone;
    protected int mAudioEncoderInputBufferCount;
    protected ByteBuffer[] mAudioEncoderInputBuffers;
    protected MediaCodec.BufferInfo mAudioEncoderOutputBufferInfo;
    protected ByteBuffer[] mAudioEncoderOutputBuffers;
    protected MediaFormat mAudioEncoderOutputMediaFormat;
    protected MediaExtractor mAudioExtractor;
    protected boolean mAudioExtractorDone;
    protected boolean mAudioWaitFrame;
    protected Context mContext;
    protected boolean mCopyAudio;
    protected DecoderFrameManager mDecoderFrameManager;
    protected DecoderReleaseListener mDecoderReleaseListener;
    protected int mFramesCount;
    protected String mInputFilePath;
    protected InputSurface mInputSurface;
    protected Uri mInputUri;
    protected boolean mIsDrop;
    protected int mLayer2Count;
    protected int mNumOfSVCLayers;
    protected long mOriginTrimEndUs;
    protected long mOriginTrimStartUs;
    protected long mOriginalduration;
    protected OutputSurface mOutputSurface;
    protected int mPendingAudioDecoderOutputBufferIndex;
    protected int mRecordingFps;
    protected SEFHelper mSefhelper;
    protected int mSkippedFramesCount;
    protected byte[] mTempAudioBuffer;
    protected int mTempAudioEncSize;
    protected float mTimescale;
    protected long mTrimAudioEndUs;
    protected long mTrimAudioStartUs;
    protected long mTrimVideoEndUs;
    protected long mTrimVideoStartUs;
    protected boolean mVideoDecoderDone;
    protected boolean mVideoEncoderDone;
    protected MediaFormat mVideoEncoderOutputMediaFormat;
    protected MediaExtractor mVideoExtractor;
    protected int mVideoFramesWritten;
    protected boolean mkeepAudioFrame;
    protected static volatile long sSRCHandle = 0;
    protected static volatile long sVSPHandle = 0;
    protected static volatile long sNAACHandle = 0;
    protected static byte[] mCreationTime = new byte[4];
    protected volatile boolean mEncoding = false;
    protected AudioSolution mAudio = null;
    protected int mRecordingMode = 0;
    protected boolean mUseUri = false;
    protected long mPausedVideoUs = -1;
    protected int mRotation = 0;
    protected boolean formatupdated = false;
    protected int mInputOrientationDegrees = 0;
    protected int mAuthor = -1;
    protected boolean mSEFVideo = false;
    protected boolean mIs360Video = false;
    protected long mLastAudioSampleWrittenTime = -1;
    protected int mAudioLoopCount = 0;
    protected ByteBuffer mDecAudio = null;
    protected int mTempAudioLength = 0;
    protected int mTempAudioOffset = 0;
    protected long mNaccTime = -1;
    protected long mModifiedVideotime = -1;
    protected List<SEFHelper.Region> mRegionList = new Vector();
    protected boolean mUpdateCreationTime = false;
    protected long mModifiedAudiotime = -1;
    protected int mExportRecordingMode = -1;
    protected long mAudioProgressTime = 0;
    protected long mVidioProgressTime = 0;
    protected boolean[] mAsyncCodecReleased = {false, false};

    enum ASYNC_CODEC_TYPE {
        VIDEO_DECODER,
        VIDEO_ENCODER
    }

    @Override // com.samsung.android.transcode.core.Encode
    protected void prepare() throws IOException {
        LogS.d("TranscodeLib", "prepare video and audio codec");
        this.mAsyncCodecReleased[ASYNC_CODEC_TYPE.VIDEO_DECODER.ordinal()] = false;
        this.mAsyncCodecReleased[ASYNC_CODEC_TYPE.VIDEO_ENCODER.ordinal()] = false;
        this.mEncoding = true;
        prepareVideoCodec();
        prepareAudioCodec();
    }

    @Override // com.samsung.android.transcode.core.Encode
    protected void prepareForRewrite() throws IOException {
        this.mEncoding = true;
        this.mRewritable = false;
        prepareVideoCodecNeo();
        prepareAudioCodec();
    }

    @Override // com.samsung.android.transcode.core.Encode
    protected void startEncoding() throws IOException {
        if (this.mUserStop) {
            LogS.d("TranscodeLib", "Not starting encoding because it is stopped by user.");
            return;
        }
        LogS.i("TranscodeLib", "startEncoding");
        initialize_video();
        initialize_audio();
        if (this.mTrimVideoStartUs != 0) {
            this.mVideoExtractor.seekTo(this.mTrimVideoStartUs, 0);
        }
        if (this.mCopyAudio && this.mTrimAudioStartUs != 0) {
            this.mAudioExtractor.seekTo(this.mTrimAudioStartUs, 0);
            while (this.mAudioExtractor.getSampleTime() < this.mTrimAudioStartUs) {
                if (this.mAudioExtractor.getSampleTime() == -1) {
                    throw new RuntimeException("Invalid File!");
                }
                this.mAudioExtractor.advance();
            }
        }
        do {
            if (!this.mVideoEncoderDone || !this.mAudioEncoderDone) {
                if (this.mCopyAudio) {
                    if (this.mConvert) {
                        startAudioRewriting();
                    } else {
                        startAudioEncoding();
                    }
                }
                if (!this.mPrepared) {
                    startVideoDecoding();
                }
                sendFrametoEncoder();
                if (this.mUserStop) {
                    break;
                }
            } else {
                return;
            }
        } while (!this.mCodecError);
        LogS.d(
                "TranscodeLib",
                "Encoding abruptly stopped mUserStop ?"
                        + this.mUserStop
                        + " mCodecError? "
                        + this.mCodecError);
    }

    protected static long unsignedIntToLong(byte[] b) {
        long l = 0 | (b[0] & 255);
        return (((((l << 8) | (b[1] & 255)) << 8) | (b[2] & 255)) << 8) | (b[3] & 255);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0119, code lost:

       com.samsung.android.transcode.util.LogS.d("TranscodeLib", "filePointer does not go forward. Exit.");
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x011e, code lost:

       r13 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00c1, code lost:

       if (r28 == false) goto L36;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00c3, code lost:

       r14.read(r9, 0, r9.length);
       r14.write(com.samsung.android.transcode.core.EncodeBase.mCreationTime, 0, com.samsung.android.transcode.core.EncodeBase.mCreationTime.length);
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e8, code lost:

       r13 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d2, code lost:

       r14.read(com.samsung.android.transcode.core.EncodeBase.mCreationTime, 0, com.samsung.android.transcode.core.EncodeBase.mCreationTime.length);
       r14.read(com.samsung.android.transcode.core.EncodeBase.mCreationTime, 0, com.samsung.android.transcode.core.EncodeBase.mCreationTime.length);
       r1.mUpdateCreationTime = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ea, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00eb, code lost:

       r1 = r0;
       r13 = true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean updateCreationTime(java.lang.String r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.transcode.core.EncodeBase.updateCreationTime(java.lang.String,"
                    + " boolean):boolean");
    }

    protected void checkMuxerStart() {
        if (this.mUserStop || this.mMuxerStarted || this.mVideoEncoderOutputMediaFormat == null) {
            return;
        }
        if (!this.mCopyAudio || this.mAudioEncoderOutputMediaFormat != null) {
            String filepath =
                    this.mUseUri
                            ? FileHelper.getVEEditFilePath(this.mContext, this.mInputUri)
                            : this.mInputFilePath;
            if (updateCreationTime(filepath, false)) {
                this.mVideoEncoderOutputMediaFormat.setInteger(KEY_MUXER_AUTHOR, 8);
                this.mVideoEncoderOutputMediaFormat.setInteger(KEY_MUXER_TRANSCODING, 1);
                if (this.mExportRecordingMode != -1) {
                    this.mVideoEncoderOutputMediaFormat.setInteger(
                            KEY_MUXER_RECORDINGMODE, this.mExportRecordingMode);
                    LogS.d(
                            "TranscodeLib",
                            "set recording mode for NDE : " + this.mExportRecordingMode);
                }
            }
            if (!TextUtils.isEmpty(mInputFileinfo.Writer)) {
                this.mVideoEncoderOutputMediaFormat.setString(
                        KEY_MUXER_MODEL_NAME, mInputFileinfo.Writer);
            }
            this.mVideoTrackIndex = this.mMuxer.addTrack(this.mVideoEncoderOutputMediaFormat);
            if (this.mCopyAudio) {
                this.mAudioTrackIndex = this.mMuxer.addTrack(this.mAudioEncoderOutputMediaFormat);
            }
            this.mMuxer.setOrientationHint(this.mInputOrientationDegrees);
            if (mInputFileinfo.IsLocationAvailable) {
                this.mMuxer.setLocation(mInputFileinfo.latitude, mInputFileinfo.longitude);
            }
            this.mMuxer.start();
            this.mMuxerStarted = true;
        }
    }

    private void getAudioTime(long SampleTime, int recordingmode) {
        long tempSampleTime = SampleTime;
        long timedelta = 0;
        if (this.mRegionList != null && !this.mRegionList.isEmpty()) {
            long j = 1000;
            if (recordingmode == 1
                    || recordingmode == 2
                    || recordingmode == 12
                    || recordingmode == 21
                    || recordingmode == 13
                    || recordingmode == 15
                    || recordingmode == 19) {
                int i = 0;
                while (true) {
                    if (i >= this.mRegionList.size()) {
                        break;
                    }
                    if (SampleTime < this.mRegionList.get(i).mRegionStartTime * j
                            || SampleTime >= this.mRegionList.get(i).mRegionEndTime * j) {
                        if (SampleTime >= this.mRegionList.get(i).mRegionEndTime * j) {
                            SEFHelper.Speed playSpeed = this.mRegionList.get(i).mRegionSpeedType;
                            float timescale = SEFHelper.getTimeScale(playSpeed);
                            timedelta =
                                    ((double) timescale) > 1.0d
                                            ? (long)
                                                    (timedelta
                                                            + ((timescale - 1.0d)
                                                                    * (this.mRegionList.get(i)
                                                                                    .mRegionEndTime
                                                                            - this.mRegionList.get(
                                                                                            i)
                                                                                    .mRegionStartTime)
                                                                    * 1000.0d))
                                            : (long)
                                                    (timedelta
                                                            - (((1.0d - timescale) * 1000.0d)
                                                                    * (this.mRegionList.get(i)
                                                                                    .mRegionEndTime
                                                                            - this.mRegionList.get(
                                                                                            i)
                                                                                    .mRegionStartTime)));
                        }
                        i++;
                        j = 1000;
                    } else {
                        SEFHelper.Speed playSpeed2 = this.mRegionList.get(i).mRegionSpeedType;
                        tempSampleTime =
                                (this.mRegionList.get(i).mRegionStartTime * j)
                                        + (((SampleTime
                                                                - (this.mRegionList.get(i)
                                                                                .mRegionStartTime
                                                                        * j))
                                                        * ((long)
                                                                (1000000.0f
                                                                        * SEFHelper.getTimeScale(
                                                                                playSpeed2))))
                                                / 1000000);
                        break;
                    }
                }
                this.mModifiedAudiotime = tempSampleTime + timedelta;
                return;
            }
            for (int i2 = 0;
                    i2 < this.mRegionList.size()
                            && SampleTime > this.mRegionList.get(i2).mRegionEndTime * 1000;
                    i2++) {
                if (this.mRegionList.get(i2).mRegionSpeed == 9) {
                    timedelta +=
                            (this.mRegionList.get(i2).mRegionEndTime
                                            - this.mRegionList.get(i2).mRegionAudioEndTime)
                                    * 1000;
                }
            }
            this.mModifiedAudiotime = tempSampleTime - timedelta;
        }
    }

    private int checkSilentRegion(long timeUs) {
        LogS.d("TranscodeLib", "checkSilentRegion  TimeUs:" + timeUs);
        if (this.mRegionList != null && !this.mRegionList.isEmpty()) {
            for (int i = 0; i < this.mRegionList.size(); i++) {
                if (timeUs >= this.mRegionList.get(i).mRegionAudioEndTime * 1000
                        && timeUs <= this.mRegionList.get(i).mRegionEndTime * 1000
                        && this.mRegionList.get(i).mRegionSpeed == 9) {
                    LogS.d("TranscodeLib", "checkSilentRegion_SuperSlow Cancel Region:" + i);
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    private void sendAudioToDecoder() {
        if (!this.mUserStop && !this.mAudioExtractorDone) {
            if (this.mAudioEncoderOutputMediaFormat == null
                    || (this.mMuxerStarted
                            && this.mAudioEncoderInputBufferCount <= 0
                            && !this.mAudioWaitFrame)) {
                long presentationTimeUs = this.mAudioExtractor.getSampleTime();
                int jumpRegion = -1;
                if (this.mSEFVideo && isSuperSlow()) {
                    jumpRegion = checkSilentRegion(presentationTimeUs);
                }
                if (jumpRegion != -1) {
                    LogS.d(
                            "TranscodeLib",
                            "Seekto region End time :"
                                    + (this.mRegionList.get(jumpRegion).mRegionEndTime * 1000));
                    this.mAudioExtractor.seekTo(
                            this.mRegionList.get(jumpRegion).mRegionEndTime * 1000, 0);
                    while (this.mAudioExtractor.getSampleTime()
                            < this.mRegionList.get(jumpRegion).mRegionEndTime * 1000) {
                        if (this.mAudioExtractor.getSampleTime() == -1) {
                            throw new RuntimeException("Invalid File!");
                        }
                        this.mAudioExtractor.advance();
                    }
                    return;
                }
                int audioDecoderInputBufferIndex =
                        this.mInputAudioDecoder.dequeueInputBuffer(10000L);
                if (audioDecoderInputBufferIndex != -1) {
                    ByteBuffer audioDecoderInputBuffer =
                            this.mAudioDecoderInputBuffers[audioDecoderInputBufferIndex];
                    int size = this.mAudioExtractor.readSampleData(audioDecoderInputBuffer, 0);
                    this.mModifiedAudiotime = presentationTimeUs;
                    if (this.mSEFVideo) {
                        if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                            getAudioTime(2 * presentationTimeUs, this.mRecordingMode);
                        } else {
                            getAudioTime(presentationTimeUs, this.mRecordingMode);
                        }
                    }
                    if (presentationTimeUs > this.mTrimAudioEndUs || size < 0) {
                        this.mAudioExtractorDone = true;
                    } else {
                        this.mInputAudioDecoder.queueInputBuffer(
                                audioDecoderInputBufferIndex,
                                0,
                                size,
                                this.mModifiedAudiotime,
                                this.mAudioExtractor.getSampleFlags());
                        this.mAudioExtractor.advance();
                    }
                    if (this.mAudioExtractorDone) {
                        LogS.e("TranscodeLib", "audio decoder sending EOS");
                        this.mInputAudioDecoder.queueInputBuffer(
                                audioDecoderInputBufferIndex, 0, 0, 0L, 4);
                    }
                }
            }
        }
    }

    private void getAudioDecoderOutput() {
        if (this.mUserStop
                || this.mAudioDecoderDone
                || this.mPendingAudioDecoderOutputBufferIndex != -1
                || this.mAudioWaitFrame) {
            return;
        }
        if ((this.mAudioEncoderOutputMediaFormat == null || this.mMuxerStarted)
                && this.mAudioEncoderInputBufferCount <= 0) {
            int audioDecoderOutputBufferIndex =
                    this.mInputAudioDecoder.dequeueOutputBuffer(
                            this.mAudioDecoderOutputBufferInfo, 10000L);
            if (audioDecoderOutputBufferIndex == -1) {
                LogS.d(
                        "TranscodeLib",
                        "audio decoder output buffer try again later while decoding");
                return;
            }
            if (audioDecoderOutputBufferIndex == -3) {
                LogS.e("TranscodeLib", "audio decoder: output buffers changed");
                this.mAudioDecoderOutputBuffers = this.mInputAudioDecoder.getOutputBuffers();
                return;
            }
            if (audioDecoderOutputBufferIndex == -2) {
                LogS.e("TranscodeLib", "audio decoder: output format changed: ");
                return;
            }
            if (audioDecoderOutputBufferIndex < 0) {
                LogS.e(
                        "TranscodeLib",
                        "Unexpected result from audio decoder dequeue output format.");
            } else if ((this.mAudioDecoderOutputBufferInfo.flags & 2) != 0) {
                LogS.e("TranscodeLib", "audio decoder: codec config buffer");
                this.mInputAudioDecoder.releaseOutputBuffer(audioDecoderOutputBufferIndex, false);
            } else {
                this.mPendingAudioDecoderOutputBufferIndex = audioDecoderOutputBufferIndex;
            }
        }
    }

    private void getAudioFormat() {
        if (!this.mUserStop
                && !this.mMuxerStarted
                && !this.mAudioEncoderDone
                && this.mAudioEncoderOutputMediaFormat == null) {
            LogS.d("TranscodeLib", "getAudioFormat");
            int audioTrack = CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor);
            this.mAudioEncoderOutputMediaFormat = this.mAudioExtractor.getTrackFormat(audioTrack);
            LogS.d("TranscodeLib", "getAudioFormat : " + this.mAudioEncoderOutputMediaFormat);
        }
    }

    private void getandsendAudioToMuxer() {
        if (!this.mUserStop && this.mCopyAudio && this.mMuxerStarted && !this.mAudioEncoderDone) {
            ByteBuffer dstBufA = ByteBuffer.allocate(131072);
            MediaCodec.BufferInfo bufferInfoA = new MediaCodec.BufferInfo();
            bufferInfoA.size = this.mAudioExtractor.readSampleData(dstBufA, 0);
            LogS.d("TranscodeLib", "Audio rewirte bufferInfoA.size : " + bufferInfoA.size);
            bufferInfoA.offset = 0;
            bufferInfoA.size = this.mAudioExtractor.readSampleData(dstBufA, 0);
            if (bufferInfoA.size < 0) {
                LogS.d("TranscodeLib", "saw input EOS: Audio");
                this.mAudioEncoderDone = true;
                bufferInfoA.size = 0;
            } else {
                bufferInfoA.presentationTimeUs = this.mAudioExtractor.getSampleTime();
                bufferInfoA.flags = this.mAudioExtractor.getSampleFlags();
                this.mMuxer.writeSampleData(this.mAudioTrackIndex, dstBufA, bufferInfoA);
                LogS.d(
                        "TranscodeLib",
                        "Audio writeSampleData bufferInfoA.size : "
                                + bufferInfoA.size
                                + ", bufferInfoA.presentationTimeUs :"
                                + bufferInfoA.presentationTimeUs);
                updateProgress(bufferInfoA.presentationTimeUs, true);
                this.mAudioExtractor.advance();
            }
        }
    }

    protected boolean isSlowFast() {
        return this.mRecordingMode == 2
                || this.mRecordingMode == 1
                || this.mRecordingMode == 12
                || this.mRecordingMode == 21
                || this.mRecordingMode == 13
                || this.mRecordingMode == 15
                || this.mRecordingMode == 19;
    }

    private void initAudioSlowV2() {
        if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
            this.mAudio.SRCInit(
                    sSRCHandle,
                    this.mOutputAudioSampleRateHZ * 2,
                    ((int) this.mTimescale) * 12000,
                    this.mOutputAudioChannelCount,
                    16,
                    16);
        } else {
            this.mAudio.SRCInit(
                    sSRCHandle,
                    this.mOutputAudioSampleRateHZ,
                    ((int) this.mTimescale) * 12000,
                    this.mOutputAudioChannelCount,
                    16,
                    16);
        }
    }

    protected void audioVolume(ByteBuffer pcmData, int dataLength) {
        int cnt = 0;
        LogS.d(
                "TranscodeLib",
                "AudioVolume  fade_sampleRateConvFactor: 0.1, data_length; " + dataLength);
        ByteBuffer outBuffer = ByteBuffer.allocateDirect(dataLength);
        outBuffer.position(0);
        outBuffer.limit(dataLength);
        for (int buflen = dataLength / 4; buflen > 0; buflen--) {
            short temp = (short) ((pcmData.get() & 255) | ((pcmData.get() & 255) << 8));
            short temp2 = (short) (((short) (temp & 65535)) * 0.1f);
            outBuffer.put((byte) (temp2 & 255));
            outBuffer.put((byte) ((temp2 & 65280) >> 8));
            short temp3 = (short) ((pcmData.get() & 255) | ((pcmData.get() & 255) << 8));
            short temp4 = (short) (((short) (65535 & temp3)) * 0.1f);
            outBuffer.put((byte) (temp4 & 255));
            outBuffer.put((byte) ((temp4 & 65280) >> 8));
            cnt = cnt + 2 + 2;
        }
        outBuffer.position(0);
        outBuffer.limit(dataLength);
        pcmData.position(0);
        while (outBuffer.hasRemaining()) {
            pcmData.put(outBuffer.get());
        }
        outBuffer.clear();
    }

    protected int getRegionNumber(long timeUs) {
        LogS.d("TranscodeLib", "getRegionNumber timeUs:" + timeUs);
        if (this.mRegionList != null && !this.mRegionList.isEmpty()) {
            for (int i = 0; i < this.mRegionList.size(); i++) {
                if (timeUs >= this.mRegionList.get(i).mRegionStartTime * 1000
                        && timeUs <= this.mRegionList.get(i).mRegionEndTime * 1000) {
                    LogS.d("TranscodeLib", "getRegionNumber number :" + i);
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    private boolean checkAudioEncoderOutputBufferIndex() {
        int audioEncoderOutputBufferIndex =
                this.mOutputAudioEncoder.dequeueOutputBuffer(
                        this.mAudioEncoderOutputBufferInfo, 10000L);
        if (audioEncoderOutputBufferIndex == -1) {
            LogS.d("TranscodeLib", "audio encoder output buffer try again later");
            return false;
        }
        if (audioEncoderOutputBufferIndex == -3) {
            LogS.d("TranscodeLib", "audio encoder: output buffers changed");
            this.mAudioEncoderOutputBuffers = this.mOutputAudioEncoder.getOutputBuffers();
            return false;
        }
        if (audioEncoderOutputBufferIndex == -2) {
            this.mAudioEncoderOutputMediaFormat = this.mOutputAudioEncoder.getOutputFormat();
            LogS.e(
                    "TranscodeLib",
                    "audio encoder: output format changed " + this.mAudioEncoderOutputMediaFormat);
            return false;
        }
        if (audioEncoderOutputBufferIndex < 0) {
            LogS.d("TranscodeLib", "Unexpected result from audio encoder dequeue output format.");
            return false;
        }
        ByteBuffer audioEncoderOutputBuffer =
                this.mAudioEncoderOutputBuffers[audioEncoderOutputBufferIndex];
        if ((this.mAudioEncoderOutputBufferInfo.flags & 2) != 0) {
            LogS.e("TranscodeLib", "audio encoder ignoring BUFFER_FLAG_CODEC_CONFIG");
            this.mOutputAudioEncoder.releaseOutputBuffer(audioEncoderOutputBufferIndex, false);
            return false;
        }
        if (this.mAudioEncoderOutputBufferInfo.size != 0) {
            LogS.e(
                    "TranscodeLib",
                    "audio encoder writing sample data to muxer  time: "
                            + this.mAudioEncoderOutputBufferInfo.presentationTimeUs);
            if (this.mLastAudioSampleWrittenTime
                    <= this.mAudioEncoderOutputBufferInfo.presentationTimeUs) {
                this.mLastAudioSampleWrittenTime =
                        this.mAudioEncoderOutputBufferInfo.presentationTimeUs;
                this.mMuxer.writeSampleData(
                        this.mAudioTrackIndex,
                        audioEncoderOutputBuffer,
                        this.mAudioEncoderOutputBufferInfo);
                updateProgress(this.mLastAudioSampleWrittenTime, true);
                this.mPausedVideoUs = this.mLastAudioSampleWrittenTime;
            } else {
                LogS.d("TranscodeLib", "Audio time stamps are not in increasing order.");
            }
        }
        if ((this.mAudioEncoderOutputBufferInfo.flags & 4) != 0) {
            LogS.e("TranscodeLib", "saw input EOS: Audio");
            this.mAudioEncoderDone = true;
        }
        this.mOutputAudioEncoder.releaseOutputBuffer(audioEncoderOutputBufferIndex, false);
        this.mAudioEncoderInputBufferCount--;
        return false;
    }

    private void sendAudioToMuxer() {
        while (!this.mUserStop && !this.mAudioEncoderDone) {
            if ((this.mAudioEncoderOutputMediaFormat != null && !this.mMuxerStarted)
                    || this.mAudioEncoderInputBufferCount < 0) {
                return;
            }
            if (sNAACHandle != 0) {
                if (this.mAudioEncoderInputBufferCount > 0) {
                    this.mAudioEncoderInputBufferCount--;
                }
                if (this.mAudioDecoderDone) {
                    this.mAudioEncoderDone = true;
                    LogS.e(
                            "TranscodeLib",
                            "saw input EOS: Audio audioEncoderDone: " + this.mAudioEncoderDone);
                    return;
                }
                return;
            }
            if (!checkAudioEncoderOutputBufferIndex()) {
                return;
            }
        }
    }

    private void sendAudioToMuxer(int size, long presentationTime, long seektime) {
        long presentationTime2;
        boolean z;
        ByteBuffer audioDecoderOutputBuffer;
        String str;
        int i;
        String str2;
        if (this.mNaccTime == -1) {
            presentationTime2 = presentationTime;
            this.mNaccTime = presentationTime2;
        } else {
            presentationTime2 = presentationTime;
        }
        if (size >= 0) {
            ByteBuffer audioDecoderOutputBuffer2 =
                    this.mAudioDecoderOutputBuffers[this.mPendingAudioDecoderOutputBufferIndex]
                            .duplicate();
            audioDecoderOutputBuffer2.position(this.mAudioDecoderOutputBufferInfo.offset);
            audioDecoderOutputBuffer2.limit(this.mAudioDecoderOutputBufferInfo.offset + size);
            if (this.mRecordingMode != 2 && this.mRecordingMode != 1) {
                audioDecoderOutputBuffer = audioDecoderOutputBuffer2;
                str = "TranscodeLib";
                i = 2;
                str2 = " naac_time : ";
            } else if (size <= 0 || sVSPHandle == 0 || this.mTimescale == 1.0f) {
                audioDecoderOutputBuffer = audioDecoderOutputBuffer2;
                str = "TranscodeLib";
                i = 2;
                str2 = " naac_time : ";
            } else {
                if (this.mTimescale > 8.0f) {
                    int i2 = getRegionNumber(seektime);
                    LogS.d(
                            "TranscodeLib",
                            "Seekto region : "
                                    + i2
                                    + ", end time :"
                                    + (this.mRegionList.get(i2).mRegionEndTime * 1000)
                                    + ", RegionList.size() : "
                                    + this.mRegionList.size());
                    if (i2 < this.mRegionList.size() - 1) {
                        this.mAudioExtractor.seekTo(
                                this.mRegionList.get(i2).mRegionEndTime * 1000, 0);
                        while (true) {
                            int i3 = i2;
                            if (this.mAudioExtractor.getSampleTime()
                                    >= this.mRegionList.get(i2).mRegionEndTime * 1000) {
                                break;
                            }
                            if (this.mAudioExtractor.getSampleTime() == -1) {
                                throw new RuntimeException("Invalid File!");
                            }
                            this.mAudioExtractor.advance();
                            i2 = i3;
                        }
                    } else {
                        LogS.e("TranscodeLib", "audio decoder: EOS");
                        this.mAudioDecoderDone = true;
                    }
                    this.mInputAudioDecoder.releaseOutputBuffer(
                            this.mPendingAudioDecoderOutputBufferIndex, false);
                    LogS.d(
                            "TranscodeLib",
                            "releaseOutputBuffer : " + this.mPendingAudioDecoderOutputBufferIndex);
                    this.mPendingAudioDecoderOutputBufferIndex = -1;
                    if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                        LogS.e("TranscodeLib", "audio decoder: EOS");
                        this.mAudioDecoderDone = true;
                        this.mAudioEncoderInputBufferCount++;
                        return;
                    }
                    return;
                }
                ByteBuffer tempAudio = ByteBuffer.allocateDirect(409600);
                tempAudio.position(0);
                LogS.d("TranscodeLib", "VSPExe2 is called");
                int newSize =
                        this.mAudio.VSPExe2(
                                sVSPHandle,
                                tempAudio,
                                audioDecoderOutputBuffer2,
                                size / this.mOutputAudioChannelCount);
                tempAudio.limit(this.mOutputAudioChannelCount * newSize * 2);
                tempAudio.get(
                        this.mTempAudioBuffer,
                        this.mTempAudioLength,
                        this.mOutputAudioChannelCount * newSize * 2);
                this.mTempAudioLength += this.mOutputAudioChannelCount * newSize * 2;
                tempAudio.clear();
                LogS.d(
                        "TranscodeLib",
                        "VSPExe2 original size :"
                                + size
                                + ", mTempAudioLength :"
                                + this.mTempAudioLength
                                + ", mTempAudioEncSize :"
                                + this.mTempAudioEncSize);
                while (this.mTempAudioLength >= this.mTempAudioEncSize) {
                    ByteBuffer encoderInputBuffer =
                            ByteBuffer.allocateDirect(this.mTempAudioEncSize);
                    encoderInputBuffer.put(
                            this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                    this.mTempAudioOffset += this.mTempAudioEncSize;
                    System.arraycopy(
                            this.mTempAudioBuffer,
                            this.mTempAudioOffset,
                            this.mTempAudioBuffer,
                            0,
                            this.mTempAudioLength - this.mTempAudioEncSize);
                    this.mTempAudioOffset = 0;
                    this.mTempAudioLength -= this.mTempAudioEncSize;
                    this.mAudioEncoderInputBufferCount++;
                    ByteBuffer encoderOutputBuffer = ByteBuffer.allocateDirect(4096);
                    int encodedSize =
                            this.mAudio.NAACEncoderExe(
                                    sNAACHandle,
                                    encoderInputBuffer,
                                    encoderOutputBuffer,
                                    this.mOutputAudioChannelCount);
                    LogS.d(
                            "TranscodeLib",
                            " Enc NAACEncoderExe encoded_size: "
                                    + encodedSize
                                    + " naac_time : "
                                    + this.mNaccTime);
                    this.mAudioEncoderOutputBufferInfo.size = encodedSize;
                    this.mAudioEncoderOutputBufferInfo.presentationTimeUs = this.mNaccTime;
                    encoderOutputBuffer.limit(encodedSize);
                    this.mMuxer.writeSampleData(
                            this.mAudioTrackIndex,
                            encoderOutputBuffer,
                            this.mAudioEncoderOutputBufferInfo);
                    this.mPausedVideoUs = this.mNaccTime;
                    this.mNaccTime += 21333;
                    this.mAudioEncoderInputBufferCount--;
                    encoderInputBuffer.clear();
                    presentationTime2 += 21333;
                }
            }
            if (isSlowV2() && size > 0 && sSRCHandle != 0) {
                ByteBuffer tempAudio2 = ByteBuffer.allocateDirect(409600);
                tempAudio2.position(0);
                LogS.d(str, "SRCExe2 is called");
                int newSize2 =
                        this.mAudio.SRCExe2(
                                sSRCHandle,
                                audioDecoderOutputBuffer,
                                tempAudio2,
                                (size / this.mOutputAudioChannelCount) / 2);
                tempAudio2.limit(this.mOutputAudioChannelCount * newSize2 * i);
                if (this.mTimescale != 8.0f) {
                    audioVolume(tempAudio2, this.mOutputAudioChannelCount * newSize2 * i);
                }
                tempAudio2.position(0);
                tempAudio2.get(
                        this.mTempAudioBuffer,
                        this.mTempAudioLength,
                        this.mOutputAudioChannelCount * newSize2 * i);
                this.mTempAudioLength += this.mOutputAudioChannelCount * newSize2 * i;
                tempAudio2.clear();
                while (this.mTempAudioLength >= this.mTempAudioEncSize) {
                    ByteBuffer encoderInputBuffer2 =
                            ByteBuffer.allocateDirect(this.mTempAudioEncSize);
                    encoderInputBuffer2.put(
                            this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                    this.mTempAudioOffset += this.mTempAudioEncSize;
                    System.arraycopy(
                            this.mTempAudioBuffer,
                            this.mTempAudioOffset,
                            this.mTempAudioBuffer,
                            0,
                            this.mTempAudioLength - this.mTempAudioEncSize);
                    this.mTempAudioOffset = 0;
                    this.mTempAudioLength -= this.mTempAudioEncSize;
                    this.mAudioEncoderInputBufferCount++;
                    presentationTime2 += 21333;
                    ByteBuffer encoderOutputBuffer2 = ByteBuffer.allocateDirect(4096);
                    int encodedSize2 =
                            this.mAudio.NAACEncoderExe(
                                    sNAACHandle,
                                    encoderInputBuffer2,
                                    encoderOutputBuffer2,
                                    this.mOutputAudioChannelCount);
                    LogS.d(
                            str,
                            " Enc NAACEncoderExe encoded_size: "
                                    + encodedSize2
                                    + str2
                                    + this.mNaccTime);
                    this.mAudioEncoderOutputBufferInfo.size = encodedSize2;
                    this.mAudioEncoderOutputBufferInfo.presentationTimeUs = this.mNaccTime;
                    encoderOutputBuffer2.limit(encodedSize2);
                    this.mMuxer.writeSampleData(
                            this.mAudioTrackIndex,
                            encoderOutputBuffer2,
                            this.mAudioEncoderOutputBufferInfo);
                    this.mPausedVideoUs = this.mNaccTime;
                    this.mNaccTime += 21333;
                    this.mAudioEncoderInputBufferCount--;
                    encoderInputBuffer2.clear();
                }
            } else {
                ByteBuffer encoderInputBuffer3 = ByteBuffer.allocateDirect(4096);
                encoderInputBuffer3.position(0);
                encoderInputBuffer3.put(audioDecoderOutputBuffer);
                encoderInputBuffer3.limit(size);
                this.mAudioEncoderInputBufferCount++;
                ByteBuffer encoderOutputBuffer3 = ByteBuffer.allocateDirect(4096);
                int encodedSize3 =
                        this.mAudio.NAACEncoderExe(
                                sNAACHandle,
                                encoderInputBuffer3,
                                encoderOutputBuffer3,
                                this.mOutputAudioChannelCount);
                LogS.d(
                        str,
                        " Enc NAACEncoderExe2 encoded_size: "
                                + encodedSize3
                                + str2
                                + this.mNaccTime);
                this.mAudioEncoderOutputBufferInfo.size = encodedSize3;
                this.mAudioEncoderOutputBufferInfo.presentationTimeUs = this.mNaccTime;
                encoderOutputBuffer3.limit(encodedSize3);
                this.mMuxer.writeSampleData(
                        this.mAudioTrackIndex,
                        encoderOutputBuffer3,
                        this.mAudioEncoderOutputBufferInfo);
                this.mPausedVideoUs = this.mNaccTime;
                this.mNaccTime += 21333;
                this.mAudioEncoderInputBufferCount--;
                encoderInputBuffer3.clear();
            }
        }
        if (checkDecoderFinish()) {
            z = true;
            this.mAudioEncoderInputBufferCount++;
        } else {
            z = true;
        }
        updateProgress(this.mPausedVideoUs, z);
    }

    protected int checkDecAudio(int size, boolean isAudioSolution) {
        int encoderInputBufferSize;
        ByteBuffer audioDecoderOutputBuffer =
                this.mAudioDecoderOutputBuffers[this.mPendingAudioDecoderOutputBufferIndex]
                        .duplicate();
        audioDecoderOutputBuffer.position(this.mAudioDecoderOutputBufferInfo.offset);
        audioDecoderOutputBuffer.limit(this.mAudioDecoderOutputBufferInfo.offset + size);
        this.mDecAudio = ByteBuffer.allocateDirect(audioDecoderOutputBuffer.capacity());
        if (this.mOriginalAudioChannelCount > 0) {
            encoderInputBufferSize =
                    (size / this.mOriginalAudioChannelCount) * this.mOutputAudioChannelCount;
            int outputBitStreamSize = this.mOutputAudioChannelCount * 2;
            int inputBitStreamSize = this.mOriginalAudioChannelCount * 2;
            this.mDecAudio.position(0);
            this.mDecAudio.limit(encoderInputBufferSize);
            for (int i = 0; i < size / inputBitStreamSize; i++) {
                for (int j = 0; j < this.mOutputAudioChannelCount; j++) {
                    this.mDecAudio.put(
                            (i * outputBitStreamSize) + (j * 2),
                            audioDecoderOutputBuffer.get((i * inputBitStreamSize) + (j * 2)));
                    this.mDecAudio.put(
                            (i * outputBitStreamSize) + (j * 2) + 1,
                            audioDecoderOutputBuffer.get((i * inputBitStreamSize) + (j * 2) + 1));
                }
            }
        } else {
            encoderInputBufferSize = size;
            this.mDecAudio.position(0);
            this.mDecAudio.limit(encoderInputBufferSize);
            this.mDecAudio.put(audioDecoderOutputBuffer);
        }
        if (!isAudioSolution) {
            this.mDecAudio.position(0);
            this.mDecAudio.get(
                    this.mTempAudioBuffer, this.mTempAudioLength, encoderInputBufferSize);
            this.mTempAudioLength += encoderInputBufferSize;
        }
        return encoderInputBufferSize;
    }

    private void checkAudioDecoderEOS(long seektime) {
        int i = getRegionNumber(seektime);
        LogS.d(
                "TranscodeLib",
                "Seekto region : "
                        + i
                        + ", end time :"
                        + (this.mRegionList.get(i).mRegionEndTime * 1000)
                        + ", RegionList.size() : "
                        + this.mRegionList.size());
        if (i < this.mRegionList.size() - 1) {
            this.mAudioExtractor.seekTo(this.mRegionList.get(i).mRegionEndTime * 1000, 0);
            while (this.mAudioExtractor.getSampleTime()
                    < this.mRegionList.get(i).mRegionEndTime * 1000) {
                if (this.mAudioExtractor.getSampleTime() == -1) {
                    throw new RuntimeException("Invalid File!");
                }
                this.mAudioExtractor.advance();
            }
        } else {
            LogS.e("TranscodeLib", "audio decoder: EOS");
            this.mAudioDecoderDone = true;
        }
        this.mInputAudioDecoder.releaseOutputBuffer(
                this.mPendingAudioDecoderOutputBufferIndex, false);
        this.mPendingAudioDecoderOutputBufferIndex = -1;
        if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
            LogS.e("TranscodeLib", "audio decoder: EOS");
            this.mAudioDecoderDone = true;
            this.mAudioEncoderInputBufferCount++;
        }
    }

    private void sendAudioToEncoder_AudioSolution(int size, long presentationTime, long seektime) {
        if (size >= 0) {
            int encoderInputBufferSize = checkDecAudio(size, true);
            int i = -1;
            long j = 10000;
            if ((this.mRecordingMode == 2 || this.mRecordingMode == 1)
                    && size > 0
                    && sVSPHandle != 0
                    && this.mTimescale != 1.0f) {
                if (this.mTimescale > 8.0f) {
                    checkAudioDecoderEOS(seektime);
                    return;
                }
                ByteBuffer tempAudio = ByteBuffer.allocateDirect(409600);
                tempAudio.position(0);
                LogS.d("TranscodeLib", "VSPExe2 is called");
                int newSize =
                        this.mAudio.VSPExe2(
                                sVSPHandle,
                                tempAudio,
                                this.mDecAudio,
                                encoderInputBufferSize / this.mOutputAudioChannelCount);
                tempAudio.limit(this.mOutputAudioChannelCount * newSize * 2);
                tempAudio.get(
                        this.mTempAudioBuffer,
                        this.mTempAudioLength,
                        this.mOutputAudioChannelCount * newSize * 2);
                this.mTempAudioLength += this.mOutputAudioChannelCount * newSize * 2;
                tempAudio.clear();
                LogS.d(
                        "TranscodeLib",
                        "VSPExe2 original size :"
                                + size
                                + ", mTempAudioLength :"
                                + this.mTempAudioLength);
                long presentationTime2 = presentationTime;
                while (this.mTempAudioLength >= this.mTempAudioEncSize) {
                    int audioEncoderInputBufferIndex =
                            this.mOutputAudioEncoder.dequeueInputBuffer(j);
                    if (audioEncoderInputBufferIndex != i) {
                        this.mAudioEncoderInputBuffers[audioEncoderInputBufferIndex].put(
                                this.mTempAudioBuffer,
                                this.mTempAudioOffset,
                                this.mTempAudioEncSize);
                        this.mTempAudioOffset += this.mTempAudioEncSize;
                        System.arraycopy(
                                this.mTempAudioBuffer,
                                this.mTempAudioOffset,
                                this.mTempAudioBuffer,
                                0,
                                this.mTempAudioLength - this.mTempAudioEncSize);
                        this.mTempAudioOffset = 0;
                        this.mTempAudioLength -= this.mTempAudioEncSize;
                        this.mOutputAudioEncoder.queueInputBuffer(
                                audioEncoderInputBufferIndex,
                                0,
                                this.mTempAudioEncSize,
                                presentationTime2,
                                this.mAudioDecoderOutputBufferInfo.flags);
                        this.mAudioEncoderInputBufferCount++;
                        presentationTime2 += 21333;
                        newSize = newSize;
                        tempAudio = tempAudio;
                        i = -1;
                        j = 10000;
                    } else {
                        LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                        return;
                    }
                }
            } else if (isSlowV2() && size > 0 && sSRCHandle != 0) {
                ByteBuffer tempAudio2 = ByteBuffer.allocateDirect(409600);
                tempAudio2.position(0);
                LogS.d("TranscodeLib", "SRCExe2 is called");
                int newSize2 =
                        this.mAudio.SRCExe2(
                                sSRCHandle,
                                this.mDecAudio,
                                tempAudio2,
                                (encoderInputBufferSize / this.mOutputAudioChannelCount) / 2);
                tempAudio2.limit(this.mOutputAudioChannelCount * newSize2 * 2);
                if (this.mTimescale != 8.0f) {
                    audioVolume(tempAudio2, this.mOutputAudioChannelCount * newSize2 * 2);
                }
                tempAudio2.position(0);
                tempAudio2.get(
                        this.mTempAudioBuffer,
                        this.mTempAudioLength,
                        this.mOutputAudioChannelCount * newSize2 * 2);
                this.mTempAudioLength += this.mOutputAudioChannelCount * newSize2 * 2;
                tempAudio2.clear();
                long presentationTime3 = presentationTime;
                while (this.mTempAudioLength >= this.mTempAudioEncSize) {
                    int audioEncoderInputBufferIndex2 =
                            this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
                    if (audioEncoderInputBufferIndex2 == -1) {
                        LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                        return;
                    }
                    this.mAudioEncoderInputBuffers[audioEncoderInputBufferIndex2].put(
                            this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                    this.mTempAudioOffset += this.mTempAudioEncSize;
                    System.arraycopy(
                            this.mTempAudioBuffer,
                            this.mTempAudioOffset,
                            this.mTempAudioBuffer,
                            0,
                            this.mTempAudioLength - this.mTempAudioEncSize);
                    this.mTempAudioOffset = 0;
                    this.mTempAudioLength -= this.mTempAudioEncSize;
                    this.mOutputAudioEncoder.queueInputBuffer(
                            audioEncoderInputBufferIndex2,
                            0,
                            this.mTempAudioEncSize,
                            presentationTime3,
                            this.mAudioDecoderOutputBufferInfo.flags);
                    this.mAudioEncoderInputBufferCount++;
                    presentationTime3 += 21333;
                }
            } else {
                int audioEncoderInputBufferIndex3 =
                        this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
                if (audioEncoderInputBufferIndex3 == -1) {
                    LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                    return;
                }
                ByteBuffer encoderInputBuffer =
                        this.mAudioEncoderInputBuffers[audioEncoderInputBufferIndex3];
                encoderInputBuffer.position(0);
                encoderInputBuffer.put(this.mDecAudio);
                this.mOutputAudioEncoder.queueInputBuffer(
                        audioEncoderInputBufferIndex3,
                        0,
                        encoderInputBufferSize,
                        presentationTime,
                        this.mAudioDecoderOutputBufferInfo.flags);
                this.mAudioEncoderInputBufferCount++;
            }
            checkDecoderFinish();
        }
        checkDecoderFinish();
    }

    protected boolean checkDecoderFinish() {
        if (this.mTempAudioLength < this.mTempAudioEncSize) {
            this.mInputAudioDecoder.releaseOutputBuffer(
                    this.mPendingAudioDecoderOutputBufferIndex, false);
            this.mPendingAudioDecoderOutputBufferIndex = -1;
            if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                LogS.e(
                        "TranscodeLib",
                        "audio decoder: EOS  mTempAudioLength : " + this.mTempAudioLength);
                this.mAudioDecoderDone = true;
            }
            return true;
        }
        LogS.d("TranscodeLib", "Not finished yet");
        return false;
    }

    private boolean getAudioDrop(long sampleTime, int recordingMode) {
        long tempSampleTime;
        long tempSampleTime2 = sampleTime;
        long timedelta = 0;
        if (this.mRegionList != null && !this.mRegionList.isEmpty()) {
            int i = 1;
            long j = 1000;
            if (recordingMode == 1 || recordingMode == 2) {
                int i2 = 0;
                while (true) {
                    if (i2 >= this.mRegionList.size()) {
                        break;
                    }
                    if (sampleTime >= this.mRegionList.get(i2).mRegionStartTime * j) {
                        if (sampleTime >= this.mRegionList.get(i2).mRegionEndTime * j) {
                            tempSampleTime = tempSampleTime2;
                        } else {
                            SEFHelper.Speed playSpeed = this.mRegionList.get(i2).mRegionSpeedType;
                            long timeScale =
                                    (this.mRegionList.get(i2).mRegionStartTime * j)
                                            + (((sampleTime
                                                                    - (this.mRegionList.get(i2)
                                                                                    .mRegionStartTime
                                                                            * j))
                                                            * ((long)
                                                                    (1000000.0f
                                                                            * SEFHelper
                                                                                    .getTimeScale(
                                                                                            playSpeed))))
                                                    / 1000000);
                            break;
                        }
                    } else {
                        tempSampleTime = tempSampleTime2;
                    }
                    if (sampleTime >= this.mRegionList.get(i2).mRegionEndTime * j) {
                        SEFHelper.Speed playSpeed2 = this.mRegionList.get(i2).mRegionSpeedType;
                        float timescale = SEFHelper.getTimeScale(playSpeed2);
                        if (recordingMode == i) {
                            timedelta =
                                    (long)
                                            (timedelta
                                                    + ((timescale - 1.0d)
                                                            * (this.mRegionList.get(i2)
                                                                            .mRegionEndTime
                                                                    - this.mRegionList.get(i2)
                                                                            .mRegionStartTime)
                                                            * 1000.0d));
                        } else if (recordingMode == 2) {
                            timedelta =
                                    (long)
                                            (timedelta
                                                    - (((1.0d - timescale) * 1000.0d)
                                                            * (this.mRegionList.get(i2)
                                                                            .mRegionEndTime
                                                                    - this.mRegionList.get(i2)
                                                                            .mRegionStartTime)));
                        }
                    }
                    i2++;
                    tempSampleTime2 = tempSampleTime;
                    i = 1;
                    j = 1000;
                }
            } else {
                for (int i3 = 0;
                        i3 < this.mRegionList.size()
                                && sampleTime > this.mRegionList.get(i3).mRegionEndTime * 1000;
                        i3++) {
                    if (this.mRegionList.get(i3).mRegionSpeed == 9) {
                        timedelta +=
                                (this.mRegionList.get(i3).mRegionEndTime
                                                - this.mRegionList.get(i3).mRegionAudioEndTime)
                                        * 1000;
                    }
                }
                if (tempSampleTime2 < timedelta) {
                    LogS.d(
                            "TranscodeLib",
                            "[getAudioDrop]SampleTime error tempSampleTime = "
                                    + tempSampleTime2
                                    + ",timeDelta :"
                                    + timedelta);
                } else {
                    LogS.d(
                            "TranscodeLib",
                            "[getAudioDrop]SampleTime new tempSampleTime = "
                                    + (tempSampleTime2 - timedelta)
                                    + ",timeDelta :"
                                    + timedelta);
                }
            }
        }
        return this.mSefhelper.isSEFRegion(sampleTime, recordingMode);
    }

    private float getTimescale(long sampleTime, int recordingmode) {
        if (this.mRegionList == null || this.mRegionList.isEmpty()) {
            return 1.0f;
        }
        if (recordingmode != 1
                && recordingmode != 2
                && recordingmode != 12
                && recordingmode != 21
                && recordingmode != 13
                && recordingmode != 15
                && recordingmode != 19) {
            return 1.0f;
        }
        for (int i = 0; i < this.mRegionList.size(); i++) {
            if (sampleTime >= this.mRegionList.get(i).mRegionStartTime * 1000
                    && sampleTime < this.mRegionList.get(i).mRegionEndTime * 1000) {
                SEFHelper.Speed playSpeed = this.mRegionList.get(i).mRegionSpeedType;
                float timescale = 1.0f / SEFHelper.getTimeScale(playSpeed);
                LogS.d("TranscodeLib", "[getTimescale]timescale = " + timescale);
                return timescale;
            }
        }
        return 1.0f;
    }

    private void checkAudioFollowHandle(long seektime) {
        float temptimescale = getTimescale(seektime, this.mRecordingMode);
        if ((this.mRecordingMode == 2 || this.mRecordingMode == 1)
                && sVSPHandle != 0
                && this.mTimescale != temptimescale) {
            this.mTimescale = temptimescale;
            this.mAudio.VSPSetPar(sVSPHandle, this.mTimescale);
        }
        if (isSlowV2() && sSRCHandle != 0 && this.mTimescale != temptimescale && seektime >= 0) {
            this.mTimescale = temptimescale;
            initAudioSlowV2();
        }
    }

    private void checkTempRadio(int audioEncInputBufIndex, int size, long tempPresentationTime) {
        ByteBuffer encoderInputBuffer = this.mAudioEncoderInputBuffers[audioEncInputBufIndex];
        if (this.mOriginalAudioChannelCount > 0) {
            size /= this.mOriginalAudioChannelCount;
        }
        ByteBuffer tempAudio = ByteBuffer.allocateDirect(size);
        tempAudio.position(0);
        tempAudio.limit(size);
        encoderInputBuffer.position(0);
        encoderInputBuffer.put(tempAudio);
        this.mOutputAudioEncoder.queueInputBuffer(
                audioEncInputBufIndex,
                0,
                size,
                tempPresentationTime,
                this.mAudioDecoderOutputBufferInfo.flags);
        this.mAudioEncoderInputBufferCount++;
        tempAudio.clear();
        this.mAudioLoopCount++;
    }

    private boolean checkAudioDecoderEOSNotWaitFrameCase(long presentationTime) {
        LogS.e("TranscodeLib", "audio decoder: EOS  mTempAudioLength : " + this.mTempAudioLength);
        this.mAudioDecoderDone = true;
        if (this.mTempAudioLength > 0) {
            int audioEncoderInputBufferIndex = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
            if (audioEncoderInputBufferIndex == -1) {
                LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                return false;
            }
            ByteBuffer encoderInputBuffer =
                    this.mAudioEncoderInputBuffers[audioEncoderInputBufferIndex];
            encoderInputBuffer.put(
                    this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioLength);
            LogS.d(
                    "TranscodeLib",
                    "Enc Last frame queueInputBuffer size:"
                            + this.mTempAudioLength
                            + ", presentationTime :"
                            + presentationTime);
            this.mOutputAudioEncoder.queueInputBuffer(
                    audioEncoderInputBufferIndex, 0, this.mTempAudioLength, presentationTime, 0);
            this.mAudioEncoderInputBufferCount++;
        }
        int audioEncoderInputBufferIndex2 = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
        if (audioEncoderInputBufferIndex2 == -1) {
            LogS.d("TranscodeLib", "audio encoder input buffer try again later");
            return false;
        }
        ByteBuffer encoderInputBuffer2 =
                this.mAudioEncoderInputBuffers[audioEncoderInputBufferIndex2];
        encoderInputBuffer2.put(this.mTempAudioBuffer, this.mTempAudioOffset, 0);
        LogS.d(
                "TranscodeLib",
                "Enc EOS queueInputBuffer  time :"
                        + this.mAudioDecoderOutputBufferInfo.presentationTimeUs
                        + ", size : "
                        + this.mAudioDecoderOutputBufferInfo.size);
        this.mOutputAudioEncoder.queueInputBuffer(
                audioEncoderInputBufferIndex2,
                0,
                this.mAudioDecoderOutputBufferInfo.size,
                this.mAudioDecoderOutputBufferInfo.presentationTimeUs,
                this.mAudioDecoderOutputBufferInfo.flags);
        this.mAudioEncoderInputBufferCount++;
        return true;
    }

    private void checkAudioLoopCount(long seektime) {
        if (this.mPendingAudioDecoderOutputBufferIndex != -1) {
            this.mInputAudioDecoder.releaseOutputBuffer(
                    this.mPendingAudioDecoderOutputBufferIndex, false);
            this.mPendingAudioDecoderOutputBufferIndex = -1;
        }
        this.mInputAudioDecoder.flush();
        LogS.d(
                "TranscodeLib",
                "seek to next frame\taudioLoopCount :"
                        + this.mAudioLoopCount
                        + ", seektime: "
                        + seektime);
        this.mAudioWaitFrame = false;
        this.mAudioLoopCount = 0;
        this.mAudioExtractor.seekTo(seektime, 1);
        while (this.mAudioExtractor.getSampleTime() < seektime) {
            if (this.mAudioExtractor.getSampleTime() == -1) {
                throw new RuntimeException("Invalid File!");
            }
            this.mAudioExtractor.advance();
        }
    }

    private void sendAudioToEncoder(
            int size, long presentationTime, long seektime, long tempPresentationTime) {
        long presentationTime2;
        long j = 10000;
        int i = -1;
        if (this.mAudioWaitFrame) {
            int audioEncoderInputBufferIndex = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
            if (audioEncoderInputBufferIndex == -1) {
                LogS.d("TranscodeLib", "audio encoder input buffer try again later");
            } else {
                if (size >= 0) {
                    checkTempRadio(audioEncoderInputBufferIndex, size, tempPresentationTime);
                }
                if (tempPresentationTime > this.mOriginTrimEndUs) {
                    if (this.mPendingAudioDecoderOutputBufferIndex != -1) {
                        this.mInputAudioDecoder.releaseOutputBuffer(
                                this.mPendingAudioDecoderOutputBufferIndex, false);
                        this.mPendingAudioDecoderOutputBufferIndex = -1;
                    }
                    LogS.d(
                            "TranscodeLib",
                            "Forcely EOS  AudioLoopCount :"
                                    + this.mAudioLoopCount
                                    + ", seek time:"
                                    + seektime
                                    + ", temp_presentationTime :"
                                    + tempPresentationTime);
                    this.mAudioWaitFrame = false;
                    this.mAudioLoopCount = 0;
                    this.mInputAudioDecoder.flush();
                    this.mAudioExtractor.seekTo(seektime, 1);
                }
                if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                    LogS.e("TranscodeLib", "audio decoder: EOS");
                    if (this.mPendingAudioDecoderOutputBufferIndex != -1) {
                        this.mInputAudioDecoder.releaseOutputBuffer(
                                this.mPendingAudioDecoderOutputBufferIndex, false);
                        this.mPendingAudioDecoderOutputBufferIndex = -1;
                    }
                    this.mAudioWaitFrame = false;
                    this.mAudioDecoderDone = true;
                    this.mAudioLoopCount = 0;
                }
            }
            return;
        }
        if (this.mSEFVideo && this.mAudioLoopCount > 0) {
            checkAudioLoopCount(seektime);
            return;
        }
        if (size < 0 || presentationTime < 0) {
            presentationTime2 = presentationTime;
        } else {
            if (!this.mkeepAudioFrame) {
                checkDecAudio(size, false);
            }
            long presentationTime3 = presentationTime;
            while (true) {
                if (this.mTempAudioLength < this.mTempAudioEncSize) {
                    break;
                }
                int audioEncoderInputBufferIndex2 = this.mOutputAudioEncoder.dequeueInputBuffer(j);
                if (audioEncoderInputBufferIndex2 == i) {
                    LogS.d("TranscodeLib", " audio encoder input buffer try again later");
                    break;
                }
                ByteBuffer encoderInputBuffer =
                        this.mAudioEncoderInputBuffers[audioEncoderInputBufferIndex2];
                encoderInputBuffer.put(
                        this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                this.mTempAudioOffset += this.mTempAudioEncSize;
                System.arraycopy(
                        this.mTempAudioBuffer,
                        this.mTempAudioOffset,
                        this.mTempAudioBuffer,
                        0,
                        this.mTempAudioLength - this.mTempAudioEncSize);
                this.mTempAudioOffset = 0;
                this.mTempAudioLength -= this.mTempAudioEncSize;
                this.mOutputAudioEncoder.queueInputBuffer(
                        audioEncoderInputBufferIndex2,
                        0,
                        this.mTempAudioEncSize,
                        presentationTime3,
                        this.mAudioDecoderOutputBufferInfo.flags);
                this.mAudioEncoderInputBufferCount++;
                presentationTime3 += 21333;
                j = 10000;
                i = -1;
            }
            presentationTime2 = presentationTime3;
        }
        if (this.mTempAudioLength < this.mTempAudioEncSize) {
            this.mInputAudioDecoder.releaseOutputBuffer(
                    this.mPendingAudioDecoderOutputBufferIndex, false);
            this.mPendingAudioDecoderOutputBufferIndex = -1;
            this.mkeepAudioFrame = false;
            if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                checkAudioDecoderEOSNotWaitFrameCase(presentationTime2);
                return;
            }
            return;
        }
        LogS.d("TranscodeLib", "Not finished yet");
        this.mkeepAudioFrame = true;
    }

    private void checkSendAudioFollowHandle(
            int size, long presentationTime, long seektime, long temp_presentationTime) {
        if (sNAACHandle != 0) {
            sendAudioToMuxer(size, presentationTime, seektime);
        } else if (sVSPHandle != 0 || sSRCHandle != 0) {
            sendAudioToEncoder_AudioSolution(size, presentationTime, seektime);
        } else {
            sendAudioToEncoder(size, presentationTime, seektime, temp_presentationTime);
        }
    }

    protected long getSlowfastSeektime(long seekTimeUs) {
        if (seekTimeUs < 0) {
            return -1L;
        }
        if (this.mRegionList != null && !this.mRegionList.isEmpty()) {
            long timeDelta = 0;
            int i = 0;
            while (true) {
                if (i >= this.mRegionList.size()) {
                    break;
                }
                float timescale = SEFHelper.getTimeScale(this.mRegionList.get(i).mRegionSpeedType);
                long regStartTime = this.mRegionList.get(i).mRegionStartTime * 1000;
                long regEndTime = this.mRegionList.get(i).mRegionEndTime * 1000;
                long regDuration = regEndTime - regStartTime;
                long regScaledDuration =
                        (((long) (1000000.0f * timescale)) * regDuration) / 1000000;
                if (seekTimeUs < regStartTime + timeDelta
                        || seekTimeUs > regScaledDuration + regStartTime + timeDelta) {
                    if (seekTimeUs > regScaledDuration + regStartTime + timeDelta) {
                        timeDelta += regScaledDuration - regDuration;
                        if (i == this.mRegionList.size() - 1) {
                            return seekTimeUs - timeDelta;
                        }
                    } else if (seekTimeUs < regStartTime + timeDelta) {
                        if (i != 0) {
                            return seekTimeUs - timeDelta;
                        }
                    }
                    i++;
                } else {
                    return ((long) (((seekTimeUs - regStartTime) - timeDelta) / timescale))
                            + regStartTime;
                }
            }
            return seekTimeUs;
        }
        LogS.d("TranscodeLib", "There is no region info.");
        return seekTimeUs;
    }

    protected long getSuperslowSeektime(long seekTimeUs) {
        long j;
        long seekTimeUs2;
        boolean isCancel;
        EncodeBase encodeBase = this;
        long regAudioEndTime = seekTimeUs;
        if (regAudioEndTime < 0) {
            return -1L;
        }
        if (encodeBase.mRegionList != null && !encodeBase.mRegionList.isEmpty()) {
            long timeDelta = 0;
            long tmpSeekTimeUs = seekTimeUs;
            int i = 0;
            while (true) {
                if (i >= encodeBase.mRegionList.size()) {
                    j = regAudioEndTime;
                    break;
                }
                long regStartTime = encodeBase.mRegionList.get(i).mRegionStartTime * 1000;
                long regEndTime = encodeBase.mRegionList.get(i).mRegionEndTime * 1000;
                long regAudioEndTime2 = encodeBase.mRegionList.get(i).mRegionAudioEndTime * 1000;
                long regDuration = regEndTime - regAudioEndTime2;
                if (encodeBase.mRegionList.get(i).mRegionSpeed != 9) {
                    isCancel = false;
                } else {
                    isCancel = true;
                }
                j = seekTimeUs;
                LogS.d(
                        "TranscodeLib",
                        "[getSuperslowSeektime] regStartTime = "
                                + regStartTime
                                + ",regEndTime : "
                                + regEndTime
                                + ",regAudioEndTime: "
                                + regAudioEndTime2
                                + ", isCancel ="
                                + isCancel
                                + ",tmpSeekTimeUs: "
                                + tmpSeekTimeUs
                                + ", timeDelta:"
                                + timeDelta
                                + ", seekTimeUs:"
                                + j
                                + ", i :"
                                + i);
                if (tmpSeekTimeUs < regAudioEndTime2 || tmpSeekTimeUs > regEndTime) {
                    encodeBase = this;
                    if (tmpSeekTimeUs > regEndTime) {
                        if (isCancel) {
                            timeDelta += regDuration;
                            tmpSeekTimeUs += regDuration;
                        }
                        if (i != encodeBase.mRegionList.size() - 1) {
                            i++;
                            regAudioEndTime = j;
                        } else {
                            seekTimeUs2 = j + timeDelta;
                            break;
                        }
                    } else if (tmpSeekTimeUs >= regAudioEndTime2) {
                        i++;
                        regAudioEndTime = j;
                    } else if (i != 0) {
                        seekTimeUs2 = j + timeDelta;
                    }
                } else {
                    if (isCancel) {
                        timeDelta += regDuration;
                        tmpSeekTimeUs += regDuration;
                    }
                    encodeBase = this;
                    if (i != encodeBase.mRegionList.size() - 1) {
                        i++;
                        regAudioEndTime = j;
                    } else {
                        seekTimeUs2 = j + timeDelta;
                        break;
                    }
                }
            }
            seekTimeUs2 = j;
            LogS.d("TranscodeLib", "[getSuperslowSeektime] seekTimeUs= " + seekTimeUs2);
            return seekTimeUs2;
        }
        LogS.d("TranscodeLib", "There is no region info.");
        return regAudioEndTime;
    }

    private void sendAudioDecoderOutput() {
        long seektime;
        while (!this.mUserStop && !this.mAudioDecoderDone) {
            if ((this.mPendingAudioDecoderOutputBufferIndex != -1 || this.mAudioWaitFrame)
                    && this.mAudioEncoderInputBufferCount <= 0) {
                int size = this.mAudioDecoderOutputBufferInfo.size;
                long presentationTime = this.mAudioDecoderOutputBufferInfo.presentationTimeUs;
                long tempPresentationTime = presentationTime + (this.mAudioLoopCount * 21333);
                long seektime2 = tempPresentationTime;
                if (!this.mSEFVideo) {
                    seektime = seektime2;
                } else {
                    if (isSlowFast()) {
                        seektime2 = getSlowfastSeektime(tempPresentationTime);
                    } else if (isSuperSlow()) {
                        seektime2 = getSuperslowSeektime(tempPresentationTime);
                    }
                    if (this.mAudio == null) {
                        this.mAudioWaitFrame = getAudioDrop(seektime2, this.mRecordingMode);
                    }
                    checkAudioFollowHandle(seektime2);
                    LogS.d(
                            "TranscodeLib",
                            "presentationTime :"
                                    + presentationTime
                                    + ", temp_presentationTime: "
                                    + tempPresentationTime
                                    + ", seektime :"
                                    + seektime2
                                    + ", audioWaitFrame:"
                                    + this.mAudioWaitFrame
                                    + ", timescale : "
                                    + this.mTimescale);
                    seektime = seektime2;
                }
                checkSendAudioFollowHandle(size, presentationTime, seektime, tempPresentationTime);
                if (this.mDecAudio != null) {
                    this.mDecAudio.clear();
                    this.mDecAudio = null;
                }
            } else {
                return;
            }
        }
    }

    protected void startAudioEncoding() {
        sendAudioToDecoder();
        getAudioDecoderOutput();
        sendAudioDecoderOutput();
        sendAudioToMuxer();
    }

    protected void startAudioRewriting() {
        getAudioFormat();
        getandsendAudioToMuxer();
    }

    private boolean checkEncoderOutputBufferIndex(int encoderOutputBufferIndex) {
        if (encoderOutputBufferIndex == -1) {
            LogS.d("TranscodeLib", "no video encoder output buffer");
            try {
                Thread.sleep(10L);
            } catch (Exception e) {
                LogS.e("TranscodeLib", "sleep interrupted" + e);
            }
            return false;
        }
        if (encoderOutputBufferIndex == -3) {
            LogS.d("TranscodeLib", "video encoder: output buffers changed");
            return false;
        }
        if (encoderOutputBufferIndex == -2) {
            LogS.e(
                    "TranscodeLib",
                    "video encoder: output format changed "
                            + this.mOutputVideoEncoder.getOutputFormat());
            if (this.mVideoTrackIndex >= 0) {
                throw new RuntimeException(
                        "Video encoder output format changed after muxer has started");
            }
            this.mVideoEncoderOutputMediaFormat = this.mOutputVideoEncoder.getOutputFormat();
            return false;
        }
        if (encoderOutputBufferIndex < 0) {
            LogS.d("TranscodeLib", "Unexpected result from video encoder dequeue output format.");
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendVideoFramesToMuxer(
            int encoderOutputBufferIndex, MediaCodec.BufferInfo videoEncoderOutputBufferInfo) {
        if (!checkEncoderOutputBufferIndex(encoderOutputBufferIndex)) {
            LogS.e("TranscodeLib", "video encoder: sendVideoFramesToMuxer condition error");
            return;
        }
        ByteBuffer encoderOutputBuffer =
                this.mOutputVideoEncoder.getOutputBuffer(encoderOutputBufferIndex);
        if ((videoEncoderOutputBufferInfo.flags & 2) != 0) {
            LogS.e("TranscodeLib", "video encoder: codec config buffer");
            this.mOutputVideoEncoder.releaseOutputBuffer(encoderOutputBufferIndex, false);
            return;
        }
        if (videoEncoderOutputBufferInfo.size != 0) {
            LogS.e(
                    "TranscodeLib",
                    "video encoder: writing sample data timestamp "
                            + videoEncoderOutputBufferInfo.presentationTimeUs);
            try {
                this.mMuxer.writeSampleData(
                        this.mVideoTrackIndex, encoderOutputBuffer, videoEncoderOutputBufferInfo);
            } catch (IllegalStateException e) {
                LogS.e(
                        "TranscodeLib",
                        "fail to writeSampleData videoEncoderDone? " + this.mVideoEncoderDone);
            }
            updateProgress(videoEncoderOutputBufferInfo.presentationTimeUs, false);
        }
        if ((videoEncoderOutputBufferInfo.flags & 4) != 0) {
            LogS.e("TranscodeLib", "video encoder: EOS");
            this.mVideoEncoderDone = true;
        }
        try {
            this.mOutputVideoEncoder.releaseOutputBuffer(encoderOutputBufferIndex, false);
        } catch (IllegalStateException e2) {
            LogS.e(
                    "TranscodeLib",
                    "fail to release output buffer of encoder videoEncoderDone? "
                            + this.mVideoEncoderDone);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isVideoDecoderAvailableCondition() {
        return (this.mCodecError
                        || this.mUserStop
                        || this.mVideoDecoderDone
                        || !this.mPrepared
                        || this.mInputVideoDecoder == null
                        || (this.mVideoEncoderOutputMediaFormat != null && !this.mMuxerStarted))
                ? false
                : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0017, code lost:

       if (r1.mMuxerStarted != false) goto L15;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isVideoEncoderAvailableCondition() {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.mCodecError     // Catch: java.lang.Throwable -> L1e
            if (r0 != 0) goto L1b
            boolean r0 = r1.mUserStop     // Catch: java.lang.Throwable -> L1e
            if (r0 != 0) goto L1b
            boolean r0 = r1.mVideoEncoderDone     // Catch: java.lang.Throwable -> L1e
            if (r0 != 0) goto L1b
            boolean r0 = r1.mPrepared     // Catch: java.lang.Throwable -> L1e
            if (r0 == 0) goto L1b
            android.media.MediaFormat r0 = r1.mVideoEncoderOutputMediaFormat     // Catch: java.lang.Throwable -> L1e
            if (r0 == 0) goto L19
            boolean r0 = r1.mMuxerStarted     // Catch: java.lang.Throwable -> L1e
            if (r0 == 0) goto L1b
        L19:
            r0 = 1
            goto L1c
        L1b:
            r0 = 0
        L1c:
            monitor-exit(r1)
            return r0
        L1e:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.transcode.core.EncodeBase.isVideoEncoderAvailableCondition():boolean");
    }

    private boolean checkDecoderOutputBufferIndex(
            int decoderOutputBufferIndex, MediaCodec.BufferInfo videoDecoderOutputBufferInfo) {
        if (decoderOutputBufferIndex == -1) {
            LogS.d("TranscodeLib", "no video decoder output buffer");
            return false;
        }
        if (decoderOutputBufferIndex == -3) {
            LogS.e("TranscodeLib", "video decoder: output buffers changed");
            return false;
        }
        if (decoderOutputBufferIndex == -2) {
            LogS.e(
                    "TranscodeLib",
                    "video decoder: codec info format changed"
                            + this.mInputVideoDecoder.getOutputFormat());
            return false;
        }
        if ((videoDecoderOutputBufferInfo.flags & 2) != 0) {
            LogS.e("TranscodeLib", "video decoder: codec config buffer");
            this.mInputVideoDecoder.releaseOutputBuffer(decoderOutputBufferIndex, false);
            return false;
        }
        return true;
    }

    private void checkSkipFrames(long presentationTimeUs) {
        if (this.mSkipFrames && this.mSkippedFramesCount % this.mFramesSkipInterval != 0) {
            LogS.d(
                    "TranscodeLib",
                    "input surface: skip this frame: presentationTimeUs " + presentationTimeUs);
        } else {
            this.mSkippedFramesCount = 0;
            this.mInputSurface.setPresentationTime(1000 * presentationTimeUs);
            this.mInputSurface.swapBuffers();
            this.mVideoFramesWritten++;
        }
        this.mSkippedFramesCount++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getVideoDecoderOutput(
            int decoderOutputBufferIndex, MediaCodec.BufferInfo videoDecoderOutputBufferInfo) {
        if (!checkDecoderOutputBufferIndex(
                decoderOutputBufferIndex, videoDecoderOutputBufferInfo)) {
            return;
        }
        final DecodedFrame decodedFrame =
                new DecodedFrame(decoderOutputBufferIndex, videoDecoderOutputBufferInfo);
        Optional.ofNullable(this.mDecoderReleaseListener)
                .ifPresent(
                        new Consumer() { // from class:
                                         // com.samsung.android.transcode.core.EncodeBase$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((DecoderReleaseListener) obj)
                                        .notifyFrameDecoded(DecodedFrame.this);
                            }
                        });
    }

    private boolean releaseOutputBufferOfVideoDecoder(DecodedFrame releaseFrame) {
        this.mInputVideoDecoder.releaseOutputBuffer(
                releaseFrame.bufferIndex, releaseFrame.size != 0);
        return releaseFrame.size != 0;
    }

    protected void sendFrametoEncoder() {
        DecodedFrame frame;
        if (!isVideoEncoderAvailableCondition()) {
            return;
        }
        int count = 0;
        while (this.mDecoderFrameManager.queSize() > 0
                && count < 3
                && !this.mUserStop
                && (frame = this.mDecoderFrameManager.dequeueFrame()) != null) {
            count++;
            if (releaseOutputBufferOfVideoDecoder(frame)) {
                try {
                    if (!this.mOutputSurface.checkForNewImage(1000)) {
                        LogS.e(
                                "TranscodeLib",
                                "video decoder: checkForNewImage return false!!  mUserStop : "
                                        + this.mUserStop);
                    }
                    GLES20.glClear(16384);
                    this.mOutputSurface.drawImage();
                    if (frame.presentationTimeUs >= this.mOriginTrimStartUs) {
                        checkSkipFrames(frame.presentationTimeUs);
                    }
                } catch (RuntimeException r) {
                    String msg = r.getMessage();
                    if (!this.mUserStop
                            || msg == null
                            || !msg.equals(OutputSurface.EXCEPTION_FRAME_NOT_AVAILABLE)) {
                        throw new RuntimeException(r);
                    }
                }
            }
            if ((frame.flags & 4) != 0) {
                LogS.e("TranscodeLib", "video decoder: EOS");
                this.mVideoDecoderDone = true;
                this.mOutputVideoEncoder.signalEndOfInputStream();
                return;
            }
        }
    }

    private boolean checkLayerCondition(int layerNum, int maxLayerNum, int conditionNum) {
        for (int i = 1; i <= conditionNum; i++) {
            if (layerNum == maxLayerNum - i) {
                return true;
            }
        }
        return false;
    }

    private long checkTimeDelta(
            long timedelta,
            float timescale,
            boolean isSEFSlowMotion,
            boolean isSEFFastMotion,
            int i) {
        if (isSEFSlowMotion) {
            return (long)
                    (timedelta
                            + ((timescale - 1.0d)
                                    * (this.mRegionList.get(i).mRegionEndTime
                                            - this.mRegionList.get(i).mRegionStartTime)
                                    * 1000.0d));
        }
        if (isSEFFastMotion) {
            return (long)
                    (timedelta
                            - (((1.0d - timescale) * 1000.0d)
                                    * (this.mRegionList.get(i).mRegionEndTime
                                            - this.mRegionList.get(i).mRegionStartTime)));
        }
        return timedelta;
    }

    private boolean checkRetDropFastMotion(
            boolean retDrop,
            int recordingmode,
            int recordingFps,
            int layernum,
            int maxlayernum,
            SEFHelper.Speed playSpeed) {
        if (isSlow120(recordingmode, recordingFps)) {
            switch (playSpeed) {
                case NORMAL:
                case TWO_TIMES:
                    break;
                case FOUR_TIMES:
                    if (layernum == maxlayernum - 1) {}
                    break;
                case EIGHT_TIMES:
                    if (checkLayerCondition(layernum, maxlayernum, 2)) {}
                    break;
                default:
                    LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!!!");
                    break;
            }
            return retDrop;
        }
        switch (playSpeed) {
            case NORMAL:
                break;
            case TWO_TIMES:
                if (layernum == maxlayernum - 1) {}
                break;
            case FOUR_TIMES:
                if (checkLayerCondition(layernum, maxlayernum, 2)) {}
                break;
            case EIGHT_TIMES:
                if (checkLayerCondition(layernum, maxlayernum, 3)) {}
                break;
            case SIXTEEN_TIMES:
                if (checkLayerCondition(layernum, maxlayernum, 4)) {}
                break;
            case THIRTY_TWO_TIMES:
                if (checkLayerCondition(layernum, maxlayernum, 5)) {}
                break;
            default:
                LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!!!");
                break;
        }
        return retDrop;
    }

    private boolean checkRetDropSlowMotion(
            boolean retDrop,
            int recordingframerate,
            int layernum,
            int maxlayernum,
            SEFHelper.Speed playSpeed) {
        if (recordingframerate >= 230) {
            if (layernum > 0 && checkLayerCondition(layernum, maxlayernum, 3)) {
                retDrop = true;
            }
            switch (playSpeed) {
                case NORMAL:
                    break;
                case HALF:
                    if (layernum == maxlayernum - 3) {}
                    break;
                case ONE_FOURTH:
                    if (layernum == maxlayernum - 3 || layernum == maxlayernum - 2) {}
                    break;
                case ONE_EIGHTH:
                    if (checkLayerCondition(layernum, maxlayernum, 3)) {}
                    break;
                default:
                    LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!");
                    break;
            }
            return retDrop;
        }
        if (recordingframerate >= 110) {
            if (layernum > 0 && checkLayerCondition(layernum, maxlayernum, 2)) {
                retDrop = true;
            }
            switch (playSpeed) {
                case NORMAL:
                    break;
                case HALF:
                    if (layernum == maxlayernum - 2) {}
                    break;
                case ONE_FOURTH:
                case ONE_EIGHTH:
                    if (checkLayerCondition(layernum, maxlayernum, 2)) {}
                    break;
                default:
                    LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!!");
                    break;
            }
            return retDrop;
        }
        return retDrop;
    }

    protected boolean isSlow120(int recordingMode, int recordingFps) {
        return recordingMode == 13
                || recordingMode == 15
                || (recordingMode == 21 && recordingFps == 120);
    }

    private boolean isRegionListExist() {
        return (this.mRegionList == null || this.mRegionList.isEmpty()) ? false : true;
    }

    private boolean keepPrevPFrameForSlowVideo(
            boolean keepFrame, int i, long sampleTime, int recordingFramerate, int layerNum) {
        if ((this.mRegionList.get(i).mRegionStartTime * 1000) - sampleTime
                < ((long)
                        (((1.45d / recordingFramerate) * 1000000.0d)
                                + ((1.0d / recordingFramerate) * 1000000.0d * 2.0d)))) {
            if (layerNum == 1) {
                return true;
            }
            if (layerNum == 2
                    && (this.mRegionList.get(i).mRegionStartTime * 1000) - sampleTime
                            < ((long) ((1.45d / recordingFramerate) * 1000000.0d))) {
                return true;
            }
        }
        return keepFrame;
    }

    private boolean keepPrevPFrameForFastVideo(
            boolean keepFrame, int i, long sampleTime, int recordingFramerate, int layerNum) {
        if ((this.mRegionList.get(i).mRegionEndTime * 1000) - sampleTime > 0
                && (this.mRegionList.get(i).mRegionEndTime * 1000) - sampleTime
                        < ((1.45d / recordingFramerate) * 1000000.0d)
                                + ((1.0d / recordingFramerate) * 1000000.0d * 14.0d)) {
            if (layerNum == 1) {
                return true;
            }
            if (layerNum == 2
                    && (this.mRegionList.get(i).mRegionEndTime * 1000) - sampleTime
                            < ((1.45d / recordingFramerate) * 1000000.0d)
                                    + ((1.0d / recordingFramerate) * 1000000.0d * 6.0d)) {
                return true;
            }
            if (layerNum == 3
                    && (this.mRegionList.get(i).mRegionEndTime * 1000) - sampleTime
                            < ((1.45d / recordingFramerate) * 1000000.0d)
                                    + ((1.0d / recordingFramerate) * 1000000.0d * 2.0d)) {
                return true;
            }
            if (layerNum == 4
                    && (this.mRegionList.get(i).mRegionEndTime * 1000) - sampleTime
                            < (1.45d / recordingFramerate) * 1000000.0d) {
                return true;
            }
        }
        return keepFrame;
    }

    private boolean procSuperSlowVideo(long sampleTime, int layernum, int recordingframerate) {
        boolean retDrop;
        boolean retDrop2;
        long timedelta;
        boolean isSEFSuperSlowCancel;
        boolean isSEFSuperSlowCancel2;
        boolean retDrop3;
        long timedelta2;
        long j = sampleTime;
        boolean isSEFSuperSlowCancel3 = false;
        boolean retDrop4 = false;
        boolean keepframe = false;
        long tempSampleTime = sampleTime;
        LogS.d("TranscodeLib", "[procSuperSlowVideo]SampleTime = tempSampleTime = " + j);
        if (!isRegionListExist()) {
            retDrop = false;
        } else {
            long timedelta3 = 0;
            int i = 0;
            while (true) {
                if (i >= this.mRegionList.size()) {
                    boolean isSEFSuperSlowCancel4 = isSEFSuperSlowCancel3;
                    retDrop = retDrop4;
                    timedelta = timedelta3;
                    isSEFSuperSlowCancel = isSEFSuperSlowCancel4;
                    break;
                }
                if (j < this.mRegionList.get(i).mRegionStartTime * 1000) {
                    isSEFSuperSlowCancel2 = isSEFSuperSlowCancel3;
                    retDrop3 = retDrop4;
                    timedelta2 = timedelta3;
                } else if (j >= this.mRegionList.get(i).mRegionEndTime * 1000) {
                    isSEFSuperSlowCancel2 = isSEFSuperSlowCancel3;
                    retDrop3 = retDrop4;
                    timedelta2 = timedelta3;
                } else if (this.mRegionList.get(i).mRegionSpeed == 9) {
                    retDrop = retDrop4;
                    timedelta = timedelta3;
                    boolean keepframe2 =
                            keepPrevPFrameForFastVideo(
                                    false,
                                    i,
                                    sampleTime,
                                    recordingframerate == 0 ? 30 : recordingframerate,
                                    layernum);
                    SEFHelper.Speed playSpeed = this.mRegionList.get(i).mRegionSpeedType;
                    float timescale = SEFHelper.getTimeScale(playSpeed);
                    isSEFSuperSlowCancel = true;
                    keepframe = keepframe2;
                    tempSampleTime =
                            (this.mRegionList.get(i).mRegionStartTime * 1000)
                                    + (((j - (this.mRegionList.get(i).mRegionStartTime * 1000))
                                                    * ((long) (1000000.0f * timescale)))
                                            / 1000000);
                } else {
                    retDrop = retDrop4;
                    timedelta = timedelta3;
                    isSEFSuperSlowCancel = false;
                }
                if (j < this.mRegionList.get(i).mRegionEndTime * 1000
                        || this.mRegionList.get(i).mRegionSpeed != 9) {
                    timedelta3 = timedelta2;
                } else {
                    SEFHelper.Speed playSpeed2 = this.mRegionList.get(i).mRegionSpeedType;
                    float timescale2 = SEFHelper.getTimeScale(playSpeed2);
                    timedelta3 =
                            (long)
                                    (timedelta2
                                            - (((1.0d - timescale2) * 1000.0d)
                                                    * (this.mRegionList.get(i).mRegionEndTime
                                                            - this.mRegionList.get(i)
                                                                    .mRegionStartTime)));
                }
                i++;
                j = sampleTime;
                isSEFSuperSlowCancel3 = isSEFSuperSlowCancel2;
                retDrop4 = retDrop3;
            }
            this.mModifiedVideotime = tempSampleTime + timedelta;
            isSEFSuperSlowCancel3 = isSEFSuperSlowCancel;
        }
        if (!isSEFSuperSlowCancel3) {
            return retDrop;
        }
        if (layernum == 0) {
            retDrop2 = retDrop;
        } else {
            retDrop2 = true;
        }
        if (keepframe) {
            return false;
        }
        return retDrop2;
    }

    private boolean procSVCLayerDrop(
            long sampleTime, int layerNum, int maxLayerNum, int recordingMode, int recordingFps) {
        long sampleTime2;
        boolean isSEFSlowMotion;
        boolean keepframe;
        SEFHelper.Speed playSpeed;
        int recordingFps2;
        boolean keepframe2;
        SEFHelper.Speed playSpeed2;
        boolean isSEFSlowMotion2;
        boolean isSEFFastMotion;
        int i;
        boolean retDrop = false;
        SEFHelper.Speed playSpeed3 = SEFHelper.Speed.NORMAL;
        if (!isSlow120(recordingMode, recordingFps)) {
            sampleTime2 = sampleTime;
        } else {
            sampleTime2 = 2 * sampleTime;
        }
        long tempSampleTime = sampleTime2;
        boolean isSEFFastMotion2 = false;
        LogS.d(
                "TranscodeLib",
                "[procSVCLayerDrop]SampleTime = tempSampleTime = "
                        + sampleTime2
                        + ",layernum :"
                        + layerNum
                        + ", maxlayernum : "
                        + maxLayerNum);
        if (!isRegionListExist()) {
            isSEFSlowMotion = false;
            keepframe = false;
            playSpeed = playSpeed3;
            recordingFps2 = recordingFps;
        } else {
            long timedelta = 0;
            SEFHelper.Speed playSpeed4 = playSpeed3;
            boolean isSEFFastMotion3 = false;
            boolean keepframe3 = false;
            int i2 = 0;
            boolean isSEFSlowMotion3 = false;
            recordingFps2 = recordingFps;
            while (true) {
                if (i2 >= this.mRegionList.size()) {
                    keepframe2 = keepframe3;
                    playSpeed2 = playSpeed4;
                    break;
                }
                if (SEFHelper.getTimeScale(this.mRegionList.get(i2).mRegionSpeedType) > 1.0f) {
                    isSEFSlowMotion3 = true;
                    isSEFFastMotion3 = false;
                }
                if (SEFHelper.getTimeScale(this.mRegionList.get(i2).mRegionSpeedType) >= 1.0f) {
                    isSEFSlowMotion2 = isSEFSlowMotion3;
                    isSEFFastMotion = isSEFFastMotion3;
                } else {
                    isSEFSlowMotion2 = false;
                    isSEFFastMotion = true;
                }
                if (!isSEFSlowMotion2
                        || sampleTime2 >= this.mRegionList.get(i2).mRegionStartTime * 1000) {
                    i = i2;
                    if (sampleTime2 >= this.mRegionList.get(i).mRegionStartTime * 1000
                            && sampleTime2 < this.mRegionList.get(i).mRegionEndTime * 1000) {
                        if (isSEFFastMotion) {
                            if (recordingFps2 == 0) {
                                recordingFps2 = 30;
                            }
                            keepframe3 =
                                    keepPrevPFrameForFastVideo(
                                            keepframe3, i, sampleTime2, recordingFps2, layerNum);
                        }
                        SEFHelper.Speed playSpeed5 = this.mRegionList.get(i).mRegionSpeedType;
                        float timescale = SEFHelper.getTimeScale(playSpeed5);
                        long tempSampleTime2 =
                                (this.mRegionList.get(i).mRegionStartTime * 1000)
                                        + (((sampleTime2
                                                                - (this.mRegionList.get(i)
                                                                                .mRegionStartTime
                                                                        * 1000))
                                                        * ((long) (1000000.0f * timescale)))
                                                / 1000000);
                        tempSampleTime = tempSampleTime2;
                        keepframe2 = keepframe3;
                        playSpeed2 = playSpeed5;
                        isSEFSlowMotion3 = isSEFSlowMotion2;
                        isSEFFastMotion3 = isSEFFastMotion;
                    } else if (sampleTime2 >= this.mRegionList.get(i).mRegionEndTime * 1000) {
                        float timescale2 =
                                SEFHelper.getTimeScale(this.mRegionList.get(i).mRegionSpeedType);
                        timedelta =
                                checkTimeDelta(
                                        timedelta,
                                        timescale2,
                                        isSEFSlowMotion2,
                                        isSEFFastMotion,
                                        i);
                        playSpeed4 = SEFHelper.Speed.NORMAL;
                    }
                } else {
                    if (recordingFps2 == 0) {
                        recordingFps2 = 240;
                    }
                    i = i2;
                    keepframe3 =
                            keepPrevPFrameForSlowVideo(
                                    keepframe3, i2, sampleTime2, recordingFps2, layerNum);
                }
                i2 = i + 1;
                isSEFSlowMotion3 = isSEFSlowMotion2;
                isSEFFastMotion3 = isSEFFastMotion;
            }
            this.mModifiedVideotime = tempSampleTime + timedelta;
            isSEFSlowMotion = isSEFSlowMotion3;
            isSEFFastMotion2 = isSEFFastMotion3;
            keepframe = keepframe2;
            playSpeed = playSpeed2;
        }
        boolean z = false;
        if (isSEFSlowMotion) {
            retDrop =
                    !keepframe
                            && checkRetDropSlowMotion(
                                    false, recordingFps2, layerNum, maxLayerNum, playSpeed);
        }
        if (isSEFFastMotion2) {
            if (!keepframe
                    && checkRetDropFastMotion(
                            retDrop,
                            recordingMode,
                            recordingFps2,
                            layerNum,
                            maxLayerNum,
                            playSpeed)) {
                z = true;
            }
            boolean retDrop2 = z;
            return retDrop2;
        }
        return retDrop;
    }

    private int getLayerNumber(byte[] a) {
        int layerNumber = 1;
        if (isNoneSVC()) {
            if (this.mRecordingMode == 18) {
                int layerNumber2 = this.mFramesCount % 16;
                this.mFramesCount++;
                layerNumber = layerNumber2;
            } else {
                this.mNumOfSVCLayers = 4;
                this.mFramesCount++;
                if ((this.mLayer2Count * 4) + 3 == this.mFramesCount) {
                    layerNumber = 2;
                    this.mLayer2Count++;
                } else if (this.mFramesCount % 8 == 1) {
                    layerNumber = 0;
                } else if (this.mFramesCount % 2 == 0) {
                    layerNumber = 3;
                }
            }
            LogS.d("TranscodeLib", "get NONE SVC layerNumber: " + layerNumber);
        } else if (CodecsHelper.isHevcFormat(mInputVideoinfo)) {
            int nalHeader = (a[0] << 8) | (a[1] & 255);
            int nalType = (nalHeader >> 9) & 63;
            if (nalType >= 2 && nalType <= 5) {
                layerNumber = (nalHeader & 7) - 1;
            } else {
                layerNumber = 0;
            }
            LogS.d("TranscodeLib", "get SVC layerNumber of HEVC: " + layerNumber);
        } else if ((a[0] & SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN) == 14
                && (a[1] & 128) == 128) {
            layerNumber = (a[3] & MidiConstants.STATUS_PITCH_BEND) >>> 5;
            LogS.d("TranscodeLib", "get SVC layerNumber: " + layerNumber);
        }
        LogS.d("TranscodeLib", "[getlayernum] layerNumber = " + layerNumber);
        return layerNumber;
    }

    private boolean isNoneSVC() {
        return this.mRecordingMode == 18 || this.mRecordingMode == 15 || this.mRecordingMode == 19;
    }

    private boolean isSlowFastExceptSlowV2120NoneSVC() {
        return this.mRecordingMode == 2
                || this.mRecordingMode == 1
                || this.mRecordingMode == 12
                || this.mRecordingMode == 21
                || this.mRecordingMode == 13;
    }

    protected boolean isSuperSlow() {
        return this.mRecordingMode == 8
                || this.mRecordingMode == 7
                || this.mRecordingMode == 9
                || this.mRecordingMode == 22
                || this.mRecordingMode == 18;
    }

    protected boolean calculateIsDrop(byte[] a, long presentationTimeUs) {
        int layerNumber = getLayerNumber(a);
        if (isSlowFastExceptSlowV2120NoneSVC()
                || this.mRecordingMode == 15
                || this.mRecordingMode == 19) {
            boolean isDrop =
                    procSVCLayerDrop(
                            presentationTimeUs,
                            layerNumber,
                            this.mNumOfSVCLayers,
                            this.mRecordingMode,
                            this.mRecordingFps);
            LogS.d(
                    "TranscodeLib",
                    "layerNumber: "
                            + layerNumber
                            + ", isDrop: "
                            + this.mIsDrop
                            + ", mModifiedVideotime: "
                            + this.mModifiedVideotime);
            return isDrop;
        }
        if (isSuperSlow()) {
            boolean isDrop2 =
                    procSuperSlowVideo(presentationTimeUs, layerNumber, this.mRecordingFps);
            LogS.d(
                    "TranscodeLib",
                    "isDrop: " + this.mIsDrop + " ,mModifiedVideotime: " + this.mModifiedVideotime);
            return isDrop2;
        }
        LogS.d("TranscodeLib", "Need to check recording mode and SEF data");
        return false;
    }

    protected void sendVideoToDecoder(int decoderInputBufferIndex) {
        ByteBuffer decoderInputBuffer =
                this.mInputVideoDecoder.getInputBuffer(decoderInputBufferIndex);
        int size = this.mVideoExtractor.readSampleData(decoderInputBuffer, 0);
        long presentationTime = this.mVideoExtractor.getSampleTime();
        this.mIsDrop = false;
        this.mModifiedVideotime = presentationTime;
        if (this.mSEFVideo) {
            byte[] a = new byte[4];
            decoderInputBuffer.position(4);
            decoderInputBuffer.get(a, 0, a.length);
            decoderInputBuffer.position(0);
            calculateIsDrop(a, presentationTime);
        }
        if (this.mIsDrop) {
            decoderInputBuffer.clear();
        }
        pushSampleDataToDecoderInputBuffer(
                decoderInputBufferIndex, size, this.mModifiedVideotime, this.mIsDrop);
    }

    protected void pushSampleDataToDecoderInputBuffer(
            int inputBufferIndex, int size, long time, boolean isDrop) {
        if (time <= this.mOriginTrimEndUs && size >= 0) {
            if (!isDrop) {
                this.mInputVideoDecoder.queueInputBuffer(
                        inputBufferIndex, 0, size, time, this.mVideoExtractor.getSampleFlags());
            } else {
                this.mInputVideoDecoder.queueInputBuffer(
                        inputBufferIndex, 0, 0, 0L, this.mVideoExtractor.getSampleFlags());
            }
            this.mVideoExtractor.advance();
            return;
        }
        LogS.e("TranscodeLib", "video extractor: EOS ");
        this.mInputVideoDecoder.queueInputBuffer(inputBufferIndex, 0, 0, 0L, 4);
    }

    protected void initialize_video() {
        this.mVideoEncoderOutputMediaFormat = null;
        this.mVideoEncoderDone = false;
        this.mVideoDecoderDone = false;
        this.mVideoFramesWritten = 0;
        this.mSkippedFramesCount = 0;
        this.mIsDrop = false;
        this.mNaccTime = -1L;
        this.mFramesCount = 0;
        this.mLayer2Count = 0;
        this.mVidioProgressTime = 0L;
        this.mDecoderFrameManager = new DecoderFrameManager();
    }

    protected void setVideoEncoderAsyncCallback() {
        this.mOutputVideoEncoder.setCallback(
                new MediaCodec
                        .Callback() { // from class: com.samsung.android.transcode.core.EncodeBase.1
                    @Override // android.media.MediaCodec.Callback
                    public void onInputBufferAvailable(MediaCodec codec, int index) {}

                    @Override // android.media.MediaCodec.Callback
                    public void onOutputBufferAvailable(
                            MediaCodec codec, int index, MediaCodec.BufferInfo bufferInfo) {
                        if (!EncodeBase.this
                                .mAsyncCodecReleased[ASYNC_CODEC_TYPE.VIDEO_ENCODER.ordinal()]) {
                            if (EncodeBase.this.isVideoEncoderAvailableCondition()) {
                                EncodeBase.this.sendVideoFramesToMuxer(index, bufferInfo);
                            } else {
                                LogS.e(
                                        "TranscodeLib",
                                        "video encoder: [onOutputBufferAvailable] condition error");
                            }
                        }
                    }

                    @Override // android.media.MediaCodec.Callback
                    public void onError(MediaCodec codec, MediaCodec.CodecException e) {
                        LogS.e("TranscodeLib", "video encoder: has error");
                        EncodeBase.this.mCodecError = true;
                        synchronized (EncodeBase.this.mOutputVideoEncoder) {
                            EncodeBase.this.mOutputVideoEncoder.notifyAll();
                        }
                    }

                    @Override // android.media.MediaCodec.Callback
                    public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {
                        if (!EncodeBase.this.mCodecError
                                && !EncodeBase.this
                                        .mAsyncCodecReleased[
                                        ASYNC_CODEC_TYPE.VIDEO_ENCODER.ordinal()]) {
                            EncodeBase.this.mVideoEncoderOutputMediaFormat = format;
                            LogS.e(
                                    "TranscodeLib",
                                    "video encoder: onOutputFormatChanged "
                                            + EncodeBase.this.mVideoEncoderOutputMediaFormat);
                            EncodeBase.this.checkMuxerStart();
                        }
                    }
                });
    }

    protected void setVideoDecoderAsyncCallback() {
        this.mInputVideoDecoder.setCallback(
                new MediaCodec
                        .Callback() { // from class: com.samsung.android.transcode.core.EncodeBase.2
                    @Override // android.media.MediaCodec.Callback
                    public void onInputBufferAvailable(MediaCodec codec, int index) {
                        if (EncodeBase.this
                                        .mAsyncCodecReleased[
                                        ASYNC_CODEC_TYPE.VIDEO_DECODER.ordinal()]
                                || !EncodeBase.this.isVideoDecoderAvailableCondition()) {
                            return;
                        }
                        EncodeBase.this.sendVideoToDecoder(index);
                    }

                    @Override // android.media.MediaCodec.Callback
                    public void onOutputBufferAvailable(
                            MediaCodec codec, int index, MediaCodec.BufferInfo bufferInfo) {
                        if (!EncodeBase.this
                                        .mAsyncCodecReleased[
                                        ASYNC_CODEC_TYPE.VIDEO_DECODER.ordinal()]
                                && EncodeBase.this.isVideoDecoderAvailableCondition()) {
                            EncodeBase.this.getVideoDecoderOutput(index, bufferInfo);
                        }
                    }

                    @Override // android.media.MediaCodec.Callback
                    public void onError(MediaCodec codec, MediaCodec.CodecException e) {
                        LogS.e("TranscodeLib", "video Decoder has error");
                        EncodeBase.this.mCodecError = true;
                        synchronized (EncodeBase.this.mInputVideoDecoder) {
                            EncodeBase.this.mInputVideoDecoder.notifyAll();
                        }
                    }

                    @Override // android.media.MediaCodec.Callback
                    public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {
                        LogS.e("TranscodeLib", "video decoder: onOutputFormatChanged " + format);
                    }
                });
    }

    protected void initialize_audio() {
        this.mAudioEncoderOutputBuffers =
                this.mCopyAudio ? this.mOutputAudioEncoder.getOutputBuffers() : null;
        this.mAudioEncoderInputBuffers =
                this.mCopyAudio ? this.mOutputAudioEncoder.getInputBuffers() : null;
        this.mAudioDecoderOutputBuffers =
                this.mCopyAudio ? this.mInputAudioDecoder.getOutputBuffers() : null;
        this.mAudioDecoderInputBuffers =
                this.mCopyAudio ? this.mInputAudioDecoder.getInputBuffers() : null;
        this.mAudioEncoderOutputBufferInfo = new MediaCodec.BufferInfo();
        this.mAudioDecoderOutputBufferInfo = new MediaCodec.BufferInfo();
        this.mAudioEncoderOutputMediaFormat = null;
        this.mAudioExtractorDone = !this.mCopyAudio;
        this.mAudioDecoderDone = !this.mCopyAudio;
        this.mAudioEncoderDone = !this.mCopyAudio;
        this.mLastAudioSampleWrittenTime = -1L;
        this.mPendingAudioDecoderOutputBufferIndex = -1;
        this.mAudioWaitFrame = false;
        this.mAudioLoopCount = 0;
        this.mTimescale = 1.0f;
        this.mAudioEncoderInputBufferCount = 0;
        this.mTempAudioBuffer = new byte[409600];
        this.mTempAudioLength = 0;
        this.mTempAudioOffset = 0;
        this.mTempAudioEncSize = this.mOutputAudioChannelCount * 2048;
        this.mDecAudio = null;
        this.mkeepAudioFrame = false;
        this.mAudioProgressTime = 0L;
    }

    protected void startVideoDecoding() {
        LogS.d("TranscodeLib", " starts transcoding");
        this.mDecoderReleaseListener = this.mDecoderFrameManager;
        this.mPrepared = true;
        this.mInputVideoDecoder.start();
    }

    protected void prepareVideoCodecNeo() throws IOException {
        if (this.mUseUri) {
            if (this.mContext == null || this.mInputUri == null) {
                throw new IOException("mInputUri or mContext  is NULL");
            }
        } else if (this.mInputFilePath == null) {
            throw new IOException("mInputFilePath is NULL");
        }
        if (this.mOutputVideoBitRate == -1) {
            this.mOutputVideoBitRate = mInputFileinfo.Bitrate;
        }
        this.mIs360Video = mInputFileinfo.Is360;
        setOrientation(mInputFileinfo.Rotation);
        this.mAuthor = mInputFileinfo.Author;
        this.mRecordingMode = mInputFileinfo.RecordingMode;
        this.mRecordingFps = mInputFileinfo.RecordingFramerate;
        LogS.i(
                "TranscodeLib",
                "input video auth : " + this.mAuthor + ", recordingMode :" + this.mRecordingMode);
        if (this.mUseUri) {
            this.mVideoExtractor = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
        } else {
            this.mVideoExtractor = CodecsHelper.createExtractor(this.mInputFilePath);
        }
        int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor);
        MediaFormat inputFormat = this.mVideoExtractor.getTrackFormat(videoTrack);
        LogS.i("TranscodeLib", "input video format: " + inputFormat);
        if (this.mTrimVideoEndUs == 0) {
            long j = inputFormat.getLong(MediaFormat.KEY_DURATION);
            this.mTrimVideoEndUs = j;
            this.mOriginTrimEndUs = j;
            if (this.mSMConvert) {
                if (isSlowV2()) {
                    this.mOriginTrimEndUs =
                            this.mSefhelper.getEditedDuration(mInputFileinfo.Duration * 1000);
                } else {
                    this.mOriginTrimEndUs = mInputFileinfo.EditedDuration * 1000;
                }
            }
            this.mTrimVideoStartUs = 0L;
            this.mOriginTrimStartUs = 0L;
            LogS.d(
                    "TranscodeLib",
                    "mTrimVideoEndUs was 0 but updated  mTrimVideoEndUs : "
                            + this.mTrimVideoEndUs
                            + ", mOriginTrimEndUs : "
                            + this.mOriginTrimEndUs);
        }
        this.mSourceFrameRate = 0;
        try {
            this.mSourceFrameRate = inputFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception e) {
        }
        if (this.mSourceFrameRate > 0) {
            this.mOutputVideoFrameRate = this.mSourceFrameRate;
        }
        LogS.d("TranscodeLib", "mOutputVideoFrameRate: " + this.mOutputVideoFrameRate);
        this.mRewritable = MediaInfoChecker.isRewritable(mInputAudioinfo, mInputVideoinfo);
        LogS.i("TranscodeLib", "askRewritable: " + this.mRewritable);
        if (!this.mRewritable) {
            throw new IOException("Unable to handle input file");
        }
    }

    private void setOrientation(int degree) {
        switch ((degree + this.mRotation) % 360) {
            case 90:
                this.mInputOrientationDegrees = 90;
                break;
            case 180:
                this.mInputOrientationDegrees = 180;
                break;
            case 270:
                this.mInputOrientationDegrees = 270;
                break;
            default:
                this.mInputOrientationDegrees = 0;
                break;
        }
    }

    protected void createVideoExtractor() throws IOException {
        if (this.mUseUri) {
            if (this.mContext == null || this.mInputUri == null) {
                throw new IOException("mInputUri or mContext  is NULL");
            }
        } else if (this.mInputFilePath == null) {
            throw new IOException("mInputFilePath is NULL");
        }
        setOrientation(mInputFileinfo.Rotation);
        this.mAuthor = mInputFileinfo.Author;
        if (this.mUseUri) {
            this.mVideoExtractor = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
        } else {
            this.mVideoExtractor = CodecsHelper.createExtractor(this.mInputFilePath);
        }
    }

    protected void createVideoEncoder() throws IOException {
        checkOutputVideoFrameRate();
        checkOutputVideoBitRate();
        LogS.e(
                "TranscodeLib",
                "mOutputVideoBitRate : "
                        + this.mOutputVideoBitRate
                        + ", mOutputAudioBitRate :"
                        + this.mOutputAudioBitRate
                        + ", mSourceFrameRate :"
                        + this.mSourceFrameRate
                        + ", mOutputVideoFrameRate :"
                        + this.mOutputVideoFrameRate
                        + ",mFramesSkipInterval: "
                        + this.mFramesSkipInterval
                        + ", mKeepSourceFrameRate : "
                        + this.mKeepSourceFrameRate
                        + ", mOutputVideoTargetFrameRate : "
                        + this.mOutputVideoTargetFrameRate);
        MediaFormat outputVideoFormat =
                MediaFormat.createVideoFormat(
                        this.mOutputVideoMimeType, this.mOutputWidth, this.mOutputHeight);
        outputVideoFormat.setInteger(
                MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        outputVideoFormat.setInteger(MediaFormat.KEY_BIT_RATE, this.mOutputVideoBitRate);
        outputVideoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, this.mOutputVideoFrameRate);
        outputVideoFormat.setInteger(
                MediaFormat.KEY_I_FRAME_INTERVAL, this.mOutputVideoIFrameInterval);
        outputVideoFormat.setInteger("priority", 1);
        if (checkBitrateMode()) {
            outputVideoFormat.setInteger(MediaFormat.KEY_BITRATE_MODE, 2);
        }
        if (!this.mMMSMode) {
            outputVideoFormat.setInteger(MediaFormat.KEY_COLOR_STANDARD, 1);
        }
        LogS.e("TranscodeLib", "output video format " + outputVideoFormat);
        this.mOutputVideoEncoder = MediaCodec.createEncoderByType(this.mOutputVideoMimeType);
        setVideoEncoderAsyncCallback();
        this.mOutputVideoEncoder.configure(
                outputVideoFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mInputSurface = new InputSurface(this.mOutputVideoEncoder.createInputSurface());
        this.mOutputVideoEncoder.start();
        this.mInputSurface.makeCurrent();
    }

    protected void setOriginalTrimTime(long duration) {
        this.mTrimVideoEndUs = duration;
        this.mOriginTrimEndUs = duration;
        if (this.mSEFVideo) {
            if (isSlowV2()) {
                this.mOriginTrimEndUs =
                        this.mSefhelper.getEditedDuration(mInputFileinfo.Duration * 1000);
            } else {
                this.mOriginTrimEndUs = mInputFileinfo.EditedDuration * 1000;
            }
        }
        this.mTrimVideoStartUs = 0L;
        this.mOriginTrimStartUs = 0L;
        LogS.d(
                "TranscodeLib",
                "mTrimVideoEndUs was 0 but updated  mTrimVideoEndUs : "
                        + this.mTrimVideoEndUs
                        + ", mOriginTrimEndUs : "
                        + this.mOriginTrimEndUs);
    }

    private void checkOutputVideoFrameRate() {
        if (this.mOutputVideoTargetFrameRate > 0) {
            if (this.mOutputVideoTargetFrameRate >= this.mSourceFrameRate) {
                this.mOutputVideoFrameRate = this.mSourceFrameRate;
            } else {
                this.mFramesSkipInterval =
                        (int) Math.ceil(this.mSourceFrameRate / this.mOutputVideoTargetFrameRate);
                if (this.mFramesSkipInterval > 1) {
                    this.mSkipFrames = true;
                }
                this.mOutputVideoFrameRate = this.mSourceFrameRate / this.mFramesSkipInterval;
            }
            LogS.d(
                    "TranscodeLib",
                    "mSourceFrameRate : "
                            + this.mSourceFrameRate
                            + ", mOutputVideoTargetFrameRate : "
                            + this.mOutputVideoTargetFrameRate
                            + ", mOutputVideoFrameRate : "
                            + this.mOutputVideoFrameRate);
            return;
        }
        if (this.mMMSMode) {
            this.mOutputVideoFrameRate = 10;
        }
        if (!this.mKeepSourceFrameRate && this.mSourceFrameRate >= this.mOutputVideoFrameRate * 2) {
            if (this.mRecordingMode == 1 || this.mRecordingMode == 2) {
                if (this.mRecordingMode == 1 && this.mSourceFrameRate < 130) {
                    this.mFramesSkipInterval = 0;
                    this.mOutputVideoFrameRate = 15;
                } else {
                    this.mFramesSkipInterval = (int) Math.floor(30.0f / this.mOutputVideoFrameRate);
                    if (this.mFramesSkipInterval > 1) {
                        this.mOutputVideoFrameRate = 30 / this.mFramesSkipInterval;
                    }
                }
            } else {
                this.mFramesSkipInterval =
                        (int) Math.floor(this.mSourceFrameRate / this.mOutputVideoFrameRate);
                if (this.mFramesSkipInterval > 1) {
                    this.mOutputVideoFrameRate = this.mSourceFrameRate / this.mFramesSkipInterval;
                }
            }
            if (this.mFramesSkipInterval > 1) {
                this.mSkipFrames = true;
                return;
            }
            return;
        }
        if (this.mSourceFrameRate > 0) {
            if (this.m2ndTimeEncoding) {
                this.mOutputVideoFrameRate = this.mSourceFrameRate / this.mFramesSkipInterval;
                LogS.d(
                        "TranscodeLib",
                        "m2ndTimeEncoding case mOutputVideoFrameRate : "
                                + this.mOutputVideoFrameRate);
            } else {
                this.mOutputVideoFrameRate = this.mSourceFrameRate;
            }
        }
    }

    protected void checkSourceFrameRate(MediaFormat inputFormat) {
        this.mSourceFrameRate = 0;
        try {
            this.mSourceFrameRate = inputFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception e) {
        }
        if (this.mSourceFrameRate == 0 || this.mSourceFrameRate > 250) {
            this.mSourceFrameRate = MediaInfo.getVideoFramerate();
        }
        mInputFileinfo.Framerate = this.mSourceFrameRate;
    }

    protected void checkOutputVideoBitRate() {
        if (this.mOutputMaxSizeKB >= 0) {
            if (!this.m2ndTimeEncoding && "video/avc".equals(this.mOutputVideoMimeType)) {
                this.mSizeFraction = 0.9f;
            }
            if (this.mMMSMode) {
                this.mOutputAudioBitRate = 32000;
            }
            if (this.mOutputVideoBitRate == -1) {
                this.mOutputVideoBitRate =
                        CodecsHelper.getVideoEncodingBitRate(
                                        this.mSizeFraction,
                                        this.mOutputMaxSizeKB,
                                        (this.mOriginTrimEndUs - this.mOriginTrimStartUs) / 1000,
                                        this.mOutputAudioBitRate / 1000,
                                        this.mOutputWidth,
                                        this.mOutputHeight)
                                * 1000;
                return;
            }
            return;
        }
        if (this.mOutputVideoBitRate == -1) {
            this.mOutputVideoBitRate =
                    CodecsHelper.suggestBitrate(
                            new ExportMediaInfo(
                                    this.mOutputWidth,
                                    this.mOutputHeight,
                                    this.mOutputVideoFrameRate,
                                    this.mOutputVideoMimeType,
                                    isHDR10Plus()),
                            mInputFileinfo);
        }
    }

    protected void prepareVideoCodec() throws IOException {
        int width;
        int height;
        int x;
        int y;
        createVideoExtractor();
        int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor);
        MediaFormat inputFormat = this.mVideoExtractor.getTrackFormat(videoTrack);
        String chipName = SemSystemProperties.get("ro.hardware").toLowerCase();
        if (supportConverter() && (this.mConvert || isHDR10() || isHLG())) {
            if (chipName != null && chipName.startsWith("mt")) {
                inputFormat.setInteger(MediaFormat.KEY_COLOR_TRANSFER_REQUEST, 3);
            } else if (chipName == null) {
                inputFormat.setInteger(MediaFormat.KEY_COLOR_TRANSFER_REQUEST, 128);
            } else {
                inputFormat.setInteger("vendor.renderengine-hdr-tonemap.value", 1);
            }
        }
        LogS.e("TranscodeLib", "input video format: " + inputFormat);
        if (this.mTrimVideoEndUs == 0) {
            long duration = inputFormat.getLong(MediaFormat.KEY_DURATION);
            setOriginalTrimTime(duration);
        }
        checkSourceFrameRate(inputFormat);
        createVideoEncoder();
        try {
            int originWidth = inputFormat.getInteger("width");
            int originHeight = inputFormat.getInteger("height");
            float originAspectRatio = originWidth / originHeight;
            float targetAspectRatio = this.mOutputWidth / this.mOutputHeight;
            if (originAspectRatio > targetAspectRatio) {
                int width2 = this.mOutputWidth;
                int height2 = (this.mOutputWidth * originHeight) / originWidth;
                width = width2;
                height = height2;
                x = 0;
                y = (this.mOutputHeight - height2) / 2;
            } else {
                int width3 = this.mOutputHeight;
                int width4 = (this.mOutputHeight * originWidth) / originHeight;
                int x2 = (this.mOutputWidth - width4) / 2;
                width = width4;
                height = width3;
                x = x2;
                y = 0;
            }
            this.mOutputSurface =
                    new OutputSurface(
                            mInputFileinfo.Rotation,
                            x,
                            y,
                            width,
                            height,
                            originWidth,
                            originHeight,
                            this.mMMSMode);
        } catch (Exception e) {
            LogS.e("TranscodeLib", "Can't get input video resolution");
            this.mOutputSurface = new OutputSurface(mInputFileinfo.Rotation);
        }
        if (!this.mMMSMode) {
            inputFormat.setInteger("priority", 1);
        }
        this.mInputVideoDecoder = MediaCodec.createDecoderByType(inputFormat.getString("mime"));
        setVideoDecoderAsyncCallback();
        this.mInputVideoDecoder.configure(
                inputFormat, this.mOutputSurface.getSurface(), (MediaCrypto) null, 0);
    }

    private boolean checkAudioChannelCount() {
        if (this.mMMSMode && this.mOutputAudioChannelCount >= 2) {
            this.mOriginalAudioChannelCount = this.mOutputAudioChannelCount;
            this.mOutputAudioChannelCount = 1;
            return true;
        }
        if (this.mOutputAudioChannelCount <= 2) {
            return true;
        }
        if (this.mOutputAudioChannelCount == 6) {
            this.mOriginalAudioChannelCount = this.mOutputAudioChannelCount;
            this.mOutputAudioChannelCount = 2;
            LogS.d("TranscodeLib", "Audio need down mixing ");
            return true;
        }
        LogS.e("TranscodeLib", "Can't support " + this.mOutputAudioChannelCount + " channel ");
        this.mCopyAudio = false;
        return false;
    }

    protected void createAudioExtractor() throws IOException {
        if (this.mUseUri) {
            if (this.mContext == null || this.mInputUri == null) {
                throw new IOException("mInputUri or mContext  is NULL");
            }
            this.mAudioExtractor = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
            return;
        }
        if (this.mInputFilePath == null) {
            throw new IOException("mInputFilePath is NULL");
        }
        this.mAudioExtractor = CodecsHelper.createExtractor(this.mInputFilePath);
    }

    private boolean checkCopyAudio(MediaFormat inputAudioFormat) {
        int error;
        if ("audio/unknown".equals(inputAudioFormat.getString("mime"))) {
            LogS.d("TranscodeLib", "Audio mime type is unknown. Ignore audio track.");
            return false;
        }
        if (inputAudioFormat.containsKey("error-type")
                && (error = inputAudioFormat.getInteger("error-type")) != 0) {
            LogS.d("TranscodeLib", "Audio codec error appear : " + error);
            return false;
        }
        if (!MediaInfoChecker.isSupportedCodecType(inputAudioFormat)) {
            LogS.d("TranscodeLib", "Audio codec type is unsupported. Ignore audio track.");
            return false;
        }
        return true;
    }

    protected void checkTrimAudioEndUs(MediaFormat inputAudioFormat) {
        if (this.mTrimAudioEndUs == 0) {
            if (inputAudioFormat != null) {
                this.mTrimAudioEndUs = inputAudioFormat.getLong(MediaFormat.KEY_DURATION);
            }
            LogS.d(
                    "TranscodeLib",
                    "mTrimAudioEndUs was 0 but updated mTrimAudioEndUs :"
                            + this.mTrimAudioEndUs
                            + ", mOriginTrimEndUs:"
                            + this.mOriginTrimEndUs);
        }
    }

    private boolean checkAudioDecoderBufferIndex(
            int audioDecoderInputBufferIndex, ByteBuffer[] audioDecoderInputBuffers) {
        if (audioDecoderInputBufferIndex == -1) {
            LogS.d(
                    "TranscodeLib",
                    "audio decoder input try again later while preparing audio codec");
            return false;
        }
        ByteBuffer audioDecoderInputBuffer = audioDecoderInputBuffers[audioDecoderInputBufferIndex];
        int size = this.mAudioExtractor.readSampleData(audioDecoderInputBuffer, 0);
        long presentationTimeUs = this.mAudioExtractor.getSampleTime();
        if (size > 0) {
            this.mInputAudioDecoder.queueInputBuffer(
                    audioDecoderInputBufferIndex,
                    0,
                    size,
                    presentationTimeUs,
                    this.mAudioExtractor.getSampleFlags());
        } else if (size == -1) {
            this.mCopyAudio = false;
            this.formatupdated = true;
            LogS.d("TranscodeLib", "Audio buffer is empty, size :" + size);
            return false;
        }
        return false;
    }

    private boolean checkPendingAudioDecoderBufferIndex(
            int audioDecoderOutputBufferIndex, String mimeType) {
        if (audioDecoderOutputBufferIndex == -1) {
            LogS.d(
                    "TranscodeLib",
                    "audio decoder output buffer try again later while preparing audio codec");
            return false;
        }
        if (audioDecoderOutputBufferIndex == -3) {
            LogS.d("TranscodeLib", "audio decoder: output buffers changed ");
            return false;
        }
        if (audioDecoderOutputBufferIndex == -2) {
            this.mOutputAudioSampleRateHZ =
                    this.mInputAudioDecoder
                            .getOutputFormat()
                            .getInteger(MediaFormat.KEY_SAMPLE_RATE);
            this.mOutputAudioChannelCount =
                    this.mInputAudioDecoder
                            .getOutputFormat()
                            .getInteger(MediaFormat.KEY_CHANNEL_COUNT);
            if (isDolbyAudioCodec(mimeType)) {
                setStereoAudioChannelForDolbyAudioCodec();
            }
            LogS.e(
                    "TranscodeLib",
                    "audio decoder: output format changed: SampleRate"
                            + this.mOutputAudioSampleRateHZ
                            + ",ChannelCount"
                            + this.mOutputAudioChannelCount);
            this.formatupdated = true;
            return false;
        }
        if (audioDecoderOutputBufferIndex >= 0) {
            return true;
        }
        LogS.d("TranscodeLib", "Unexpected result from audio decoder dequeue output format.");
        return false;
    }

    private void setStereoAudioChannelForDolbyAudioCodec() {
        this.mOutputAudioChannelCount = Math.min(2, this.mOutputAudioChannelCount);
        LogS.d("TranscodeLib", "Audio ac3 type :  mOutputAudioChannelCount is changed.");
    }

    private void releaseInputAudioDecoder() {
        if (this.mInputAudioDecoder != null) {
            try {
                this.mInputAudioDecoder.stop();
                this.mInputAudioDecoder.release();
                this.mInputAudioDecoder = null;
            } catch (Exception e) {
                LogS.d("TranscodeLib", "Exception in releasing input audio decoder.");
                e.printStackTrace();
            }
        }
    }

    protected void prepareAudioCodec() throws IOException {
        createAudioExtractor();
        int audioInputTrack = CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor);
        if (audioInputTrack == -1 || this.mOutputAudioMute) {
            this.mCopyAudio = false;
            return;
        }
        MediaFormat inputAudioFormat = this.mAudioExtractor.getTrackFormat(audioInputTrack);
        if (!checkCopyAudio(inputAudioFormat)) {
            this.mCopyAudio = false;
            return;
        }
        this.mCopyAudio = true;
        checkTrimAudioEndUs(inputAudioFormat);
        LogS.e("TranscodeLib", "Audio input format " + inputAudioFormat);
        this.mOutputAudioSampleRateHZ = inputAudioFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        this.mOutputAudioChannelCount = inputAudioFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        String mimeType = inputAudioFormat.getString("mime");
        if ("audio/mp4a-latm".equals(mimeType) || isDolbyAudioCodec(mimeType)) {
            preprocessAudioOutputFormat(mimeType, inputAudioFormat);
        }
        int maxInputSize = getMaxInputSize(inputAudioFormat);
        if (!checkAudioChannelCount()) {
            return;
        }
        createAudioHandle();
        MediaFormat outputAudioFormat =
                MediaFormat.createAudioFormat(
                        this.mOutputAudioMimeType,
                        this.mOutputAudioSampleRateHZ,
                        this.mOutputAudioChannelCount);
        if (maxInputSize != 0) {
            outputAudioFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize);
        }
        outputAudioFormat.setInteger(MediaFormat.KEY_BIT_RATE, this.mOutputAudioBitRate);
        outputAudioFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, this.mOutputAudioAACProfile);
        LogS.e("TranscodeLib", "Audio output format " + outputAudioFormat);
        MediaCodecInfo audioCodecInfo = CodecsHelper.getEncoderCodec(this.mOutputAudioMimeType);
        this.mOutputAudioEncoder =
                CodecsHelper.createAudioEncoder(audioCodecInfo, outputAudioFormat);
        createInputAudioDecoder(mimeType, inputAudioFormat);
    }

    private void preprocessAudioOutputFormat(String mimeType, MediaFormat inputAudioFormat)
            throws IOException {
        try {
            MediaCodecInfo audioinputCodecInfo = CodecsHelper.getDecoderCodec(mimeType);
            this.mInputAudioDecoder =
                    CodecsHelper.createAudioDecoder(audioinputCodecInfo, inputAudioFormat);
            ByteBuffer[] inputBuffers =
                    this.mCopyAudio ? this.mInputAudioDecoder.getInputBuffers() : null;
            MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
            int pendingAudioDecoderOutputBufferIndex = -1;
            Runnable schedulerCallback =
                    new Runnable() { // from class:
                                     // com.samsung.android.transcode.core.EncodeBase$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            EncodeBase.this.m9215x8623e4ae();
                        }
                    };
            while (!this.formatupdated) {
                while (!this.formatupdated) {
                    int audioDecoderInputBufferIndex =
                            this.mInputAudioDecoder.dequeueInputBuffer(10000L);
                    if (!checkAudioDecoderBufferIndex(audioDecoderInputBufferIndex, inputBuffers)) {
                        break;
                    }
                }
                CodecsHelper.scheduleAfter(3, schedulerCallback);
                if (!this.formatupdated && pendingAudioDecoderOutputBufferIndex == -1) {
                    int outputBufferIndex =
                            this.mInputAudioDecoder.dequeueOutputBuffer(outputBufferInfo, 10000L);
                    if (checkPendingAudioDecoderBufferIndex(outputBufferIndex, mimeType)) {
                        if ((outputBufferInfo.flags & 2) != 0) {
                            LogS.d("TranscodeLib", "audio decoder: codec config buffer");
                            this.mInputAudioDecoder.releaseOutputBuffer(outputBufferIndex, false);
                        } else {
                            pendingAudioDecoderOutputBufferIndex = outputBufferIndex;
                        }
                    }
                }
            }
            releaseInputAudioDecoder();
            if (this.mCopyAudio) {
                this.mAudioExtractor.seekTo(0L, 0);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$preprocessAudioOutputFormat$1$com-samsung-android-transcode-core-EncodeBase, reason: not valid java name */
    /* synthetic */ void m9215x8623e4ae() {
        this.formatupdated = true;
    }

    private boolean isDolbyAudioCodec(String mimeType) {
        return MediaFormat.MIMETYPE_AUDIO_AC3.equals(mimeType)
                || MediaFormat.MIMETYPE_AUDIO_EAC3.equals(mimeType)
                || MediaFormat.MIMETYPE_AUDIO_EAC3_JOC.equals(mimeType)
                || MediaFormat.MIMETYPE_AUDIO_AC4.equals(mimeType);
    }

    protected boolean isSlowV2() {
        return this.mRecordingMode == 12
                || this.mRecordingMode == 21
                || this.mRecordingMode == 13
                || this.mRecordingMode == 15
                || this.mRecordingMode == 19;
    }

    private void createAudioHandle() {
        if (this.mAudio != null) {
            if (this.mRecordingMode == 1 || this.mRecordingMode == 2) {
                if (sVSPHandle == 0) {
                    sVSPHandle = this.mAudio.VSPCreate();
                }
                this.mAudio.VSPInit(sVSPHandle, this.mOutputAudioSampleRateHZ, 16);
            }
            if (isSlowV2()) {
                this.mOutputAudioSampleRateHZ = 48000;
                if (sSRCHandle == 0) {
                    sSRCHandle = this.mAudio.SRCCreate();
                }
            }
            if (this.mSMConvert && sNAACHandle == 0) {
                sNAACHandle =
                        this.mAudio.NAACEncoderInit(
                                this.mOutputAudioChannelCount, this.mOutputAudioSampleRateHZ);
            }
        }
    }

    private int getMaxInputSize(MediaFormat inputAudioFormat) {
        try {
            return inputAudioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
        } catch (NullPointerException e) {
            LogS.e("TranscodeLib", "Audio max input size not defined");
            return 0;
        }
    }

    private void createInputAudioDecoder(String mimeType, MediaFormat inputAudioFormat)
            throws IOException {
        if (inputAudioFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {
            inputAudioFormat.setInteger(MediaFormat.KEY_ENCODER_DELAY, 0);
        }
        if ("audio/mp4a-latm".equals(mimeType)) {
            MediaCodecInfo audioInfo = CodecsHelper.getDecoderCodec(mimeType);
            this.mInputAudioDecoder = CodecsHelper.createAudioDecoder(audioInfo, inputAudioFormat);
        } else {
            this.mInputAudioDecoder = CodecsHelper.createAudioDecoder(inputAudioFormat);
        }
    }

    protected void updateProgress(long frametime, boolean isAudio) {
        long time;
        if (frametime <= 0) {
            return;
        }
        if (isAudio) {
            this.mAudioProgressTime = frametime;
        } else {
            this.mVidioProgressTime = frametime;
        }
        if (this.mCopyAudio) {
            time = Math.min(this.mAudioProgressTime, this.mVidioProgressTime);
        } else {
            time = this.mVidioProgressTime;
        }
        int progress =
                Math.max(
                        0,
                        Math.min(
                                100,
                                (int)
                                        (((time - this.mOriginTrimStartUs) * 100)
                                                / (this.mOriginTrimEndUs
                                                        - this.mOriginTrimStartUs))));
        if (this.mEncodeProgressListener != null && progress > this.mProgress) {
            LogS.d(
                    "TranscodeLib",
                    "updateProgress: audioProgressTime: "
                            + this.mAudioProgressTime
                            + ", vidioProgressTime: "
                            + this.mVidioProgressTime
                            + ", time : "
                            + time
                            + ", progress: "
                            + progress);
            this.mEncodeProgressListener.onProgressChanged(progress);
            this.mProgress = progress;
        }
    }

    protected void releaseFramemanager() {
        if (this.mDecoderFrameManager != null) {
            while (this.mDecoderFrameManager.queSize() > 0) {
                DecodedFrame frame = this.mDecoderFrameManager.dequeueFrame();
                if (frame == null) {
                    return;
                }
                if (this.mInputVideoDecoder != null) {
                    releaseOutputBufferOfVideoDecoder(frame);
                }
            }
        }
        this.mDecoderFrameManager = null;
        this.mDecoderReleaseListener = null;
    }

    private boolean checkBitrateMode() {
        return this.mOutputMaxSizeKB > 0
                && this.mOutputWidth * this.mOutputHeight > 76800
                && this.mOutputWidth * this.mOutputHeight <= 307200
                && !this.mKeepSourceFrameRate;
    }

    protected boolean supportConverter() {
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HDR2SDR");
    }
}
