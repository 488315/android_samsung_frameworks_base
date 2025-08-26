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
import android.view.SurfaceControl;
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
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
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
    protected static byte[] mCreationTime = new byte[4];
    protected static MediaFormat mInputAudioinfo;
    protected static MediaInfo.MediaFileInfo mInputFileinfo;
    protected static MediaFormat mInputVideoinfo;
    protected static volatile long sNAACHandle;
    protected static volatile long sSRCHandle;
    protected static volatile long sVSPHandle;
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

    private boolean checkLayerCondition(int i, int i2, int i3) {
        for (int i4 = 1; i4 <= i3; i4++) {
            if (i == i2 - i4) {
                return true;
            }
        }
        return false;
    }

    protected boolean isSlow120(int i, int i2) {
        if (i == 13 || i == 15) {
            return true;
        }
        return i == 21 && i2 == 120;
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
    protected void startEncoding() throws MediaCodec.CryptoException, IOException {
        if (this.mUserStop) {
            LogS.d("TranscodeLib", "Not starting encoding because it is stopped by user.");
            return;
        }
        LogS.i("TranscodeLib", "startEncoding");
        initialize_video();
        initialize_audio();
        long j = this.mTrimVideoStartUs;
        if (j != 0) {
            this.mVideoExtractor.seekTo(j, 0);
        }
        if (this.mCopyAudio) {
            long j2 = this.mTrimAudioStartUs;
            if (j2 != 0) {
                this.mAudioExtractor.seekTo(j2, 0);
                while (this.mAudioExtractor.getSampleTime() < this.mTrimAudioStartUs) {
                    if (this.mAudioExtractor.getSampleTime() == -1) {
                        throw new RuntimeException("Invalid File!");
                    }
                    this.mAudioExtractor.advance();
                }
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
        LogS.d("TranscodeLib", "Encoding abruptly stopped mUserStop ?" + this.mUserStop + " mCodecError? " + this.mCodecError);
    }

    protected static long unsignedIntToLong(byte[] bArr) {
        return ((((((bArr[0] & 255) << 8) | (bArr[1] & 255)) << 8) | (bArr[2] & 255)) << 8) | (bArr[3] & 255);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ad, code lost:
    
        if (r26 == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00af, code lost:
    
        r3.read(r9, 0, r6);
        r0 = com.samsung.android.transcode.core.EncodeBase.mCreationTime;
        r3.write(r0, 0, r0.length);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b9, code lost:
    
        r0 = com.samsung.android.transcode.core.EncodeBase.mCreationTime;
        r3.read(r0, 0, r0.length);
        r0 = com.samsung.android.transcode.core.EncodeBase.mCreationTime;
        r3.read(r0, 0, r0.length);
        r24.mUpdateCreationTime = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00c7, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ca, code lost:
    
        r1 = r0;
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f0, code lost:
    
        com.samsung.android.transcode.util.LogS.d("TranscodeLib", "filePointer does not go forward. Exit.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updateCreationTime(String str, boolean z) throws Throwable {
        RandomAccessFile randomAccessFile;
        byte[] bArr;
        LogS.d("TranscodeLib", "updateCreationTime mode : " + z + ", filepath : " + str);
        boolean z2 = false;
        if (!this.mUpdateCreationTime && z) {
            LogS.d("TranscodeLib", "Do not update CreationTime");
            return false;
        }
        File file = new File(str);
        int i = 4;
        byte[] bArr2 = new byte[4];
        byte[] bArr3 = new byte[4];
        byte[] bArr4 = new byte[4];
        long length = file.length();
        if (length <= 0) {
            LogS.e("TranscodeLib", "file size is same or less than 0");
            return false;
        }
        String[] strArr = {"mdia", "minf", "moov", "stbl", "trak"};
        try {
            if (z) {
                randomAccessFile = new RandomAccessFile(file, "rw");
            } else {
                randomAccessFile = new RandomAccessFile(file, "r");
            }
            RandomAccessFile randomAccessFile2 = randomAccessFile;
            long jLongValue = 0;
            while (true) {
                if (jLongValue >= length) {
                    break;
                }
                try {
                    try {
                        randomAccessFile2.seek(jLongValue);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    randomAccessFile2.read(bArr2, 0, i);
                    long jUnsignedIntToLong = unsignedIntToLong(bArr2);
                    randomAccessFile2.read(bArr3, 0, i);
                    String str2 = new String(bArr3, StandardCharsets.UTF_8);
                    if (Arrays.binarySearch(strArr, str2) >= 0) {
                        jLongValue += 8;
                        bArr = bArr2;
                    } else {
                        if (str2.equals("mvhd")) {
                            break;
                        }
                        if (jUnsignedIntToLong == 1) {
                            bArr = bArr2;
                            randomAccessFile2.seek(jLongValue + 8);
                            byte[] bArr5 = new byte[8];
                            randomAccessFile2.read(bArr5, 0, 8);
                            jLongValue += new BigInteger(bArr5).longValue();
                        } else {
                            bArr = bArr2;
                            if (jUnsignedIntToLong == 0) {
                                break;
                            }
                            jLongValue += jUnsignedIntToLong;
                        }
                    }
                    bArr2 = bArr;
                    i = 4;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    try {
                        randomAccessFile2.close();
                        throw th2;
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                        throw th2;
                    }
                }
            }
            randomAccessFile2.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return z2;
    }

    protected void checkMuxerStart() {
        if (this.mUserStop || this.mMuxerStarted || this.mVideoEncoderOutputMediaFormat == null) {
            return;
        }
        if (this.mCopyAudio && this.mAudioEncoderOutputMediaFormat == null) {
            return;
        }
        if (updateCreationTime(this.mUseUri ? FileHelper.getVEEditFilePath(this.mContext, this.mInputUri) : this.mInputFilePath, false)) {
            this.mVideoEncoderOutputMediaFormat.setInteger(KEY_MUXER_AUTHOR, 8);
            this.mVideoEncoderOutputMediaFormat.setInteger(KEY_MUXER_TRANSCODING, 1);
            int i = this.mExportRecordingMode;
            if (i != -1) {
                this.mVideoEncoderOutputMediaFormat.setInteger(KEY_MUXER_RECORDINGMODE, i);
                LogS.d("TranscodeLib", "set recording mode for NDE : " + this.mExportRecordingMode);
            }
        }
        if (!TextUtils.isEmpty(mInputFileinfo.Writer)) {
            this.mVideoEncoderOutputMediaFormat.setString(KEY_MUXER_MODEL_NAME, mInputFileinfo.Writer);
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

    private void getAudioTime(long j, int i) {
        double d;
        List<SEFHelper.Region> list = this.mRegionList;
        if (list == null || list.isEmpty()) {
            return;
        }
        int i2 = 0;
        long j2 = 0;
        if (i == 1 || i == 2 || i == 12 || i == 21 || i == 13 || i == 15 || i == 19) {
            while (true) {
                if (i2 < this.mRegionList.size()) {
                    if (j >= this.mRegionList.get(i2).mRegionStartTime * 1000 && j < this.mRegionList.get(i2).mRegionEndTime * 1000) {
                        j = (((j - (this.mRegionList.get(i2).mRegionStartTime * 1000)) * ((long) (SEFHelper.getTimeScale(this.mRegionList.get(i2).mRegionSpeedType) * 1000000.0f))) / 1000000) + (this.mRegionList.get(i2).mRegionStartTime * 1000);
                        break;
                    } else {
                        if (j >= this.mRegionList.get(i2).mRegionEndTime * 1000) {
                            double timeScale = SEFHelper.getTimeScale(this.mRegionList.get(i2).mRegionSpeedType);
                            if (timeScale > 1.0d) {
                                d = j2 + ((timeScale - 1.0d) * (this.mRegionList.get(i2).mRegionEndTime - this.mRegionList.get(i2).mRegionStartTime) * 1000.0d);
                            } else {
                                d = j2 - (((1.0d - timeScale) * 1000.0d) * (this.mRegionList.get(i2).mRegionEndTime - this.mRegionList.get(i2).mRegionStartTime));
                            }
                            j2 = (long) d;
                        }
                        i2++;
                    }
                } else {
                    break;
                }
            }
            this.mModifiedAudiotime = j + j2;
            return;
        }
        while (i2 < this.mRegionList.size() && j > this.mRegionList.get(i2).mRegionEndTime * 1000) {
            if (this.mRegionList.get(i2).mRegionSpeed == 9) {
                j2 += (this.mRegionList.get(i2).mRegionEndTime - this.mRegionList.get(i2).mRegionAudioEndTime) * 1000;
            }
            i2++;
        }
        this.mModifiedAudiotime = j - j2;
    }

    private int checkSilentRegion(long j) {
        LogS.d("TranscodeLib", "checkSilentRegion  TimeUs:" + j);
        List<SEFHelper.Region> list = this.mRegionList;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.mRegionList.size(); i++) {
            if (j >= this.mRegionList.get(i).mRegionAudioEndTime * 1000 && j <= this.mRegionList.get(i).mRegionEndTime * 1000 && this.mRegionList.get(i).mRegionSpeed == 9) {
                LogS.d("TranscodeLib", "checkSilentRegion_SuperSlow Cancel Region:" + i);
                return i;
            }
        }
        return -1;
    }

    private void sendAudioToDecoder() throws MediaCodec.CryptoException {
        if (this.mUserStop || this.mAudioExtractorDone) {
            return;
        }
        if (this.mAudioEncoderOutputMediaFormat == null || (this.mMuxerStarted && this.mAudioEncoderInputBufferCount <= 0 && !this.mAudioWaitFrame)) {
            long sampleTime = this.mAudioExtractor.getSampleTime();
            int iCheckSilentRegion = (this.mSEFVideo && isSuperSlow()) ? checkSilentRegion(sampleTime) : -1;
            if (iCheckSilentRegion != -1) {
                LogS.d("TranscodeLib", "Seekto region End time :" + (this.mRegionList.get(iCheckSilentRegion).mRegionEndTime * 1000));
                this.mAudioExtractor.seekTo(((long) this.mRegionList.get(iCheckSilentRegion).mRegionEndTime) * 1000, 0);
                while (this.mAudioExtractor.getSampleTime() < this.mRegionList.get(iCheckSilentRegion).mRegionEndTime * 1000) {
                    if (this.mAudioExtractor.getSampleTime() == -1) {
                        throw new RuntimeException("Invalid File!");
                    }
                    this.mAudioExtractor.advance();
                }
                return;
            }
            int iDequeueInputBuffer = this.mInputAudioDecoder.dequeueInputBuffer(10000L);
            if (iDequeueInputBuffer != -1) {
                int sampleData = this.mAudioExtractor.readSampleData(this.mAudioDecoderInputBuffers[iDequeueInputBuffer], 0);
                this.mModifiedAudiotime = sampleTime;
                if (this.mSEFVideo) {
                    if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                        getAudioTime(2 * sampleTime, this.mRecordingMode);
                    } else {
                        getAudioTime(sampleTime, this.mRecordingMode);
                    }
                }
                if (sampleTime <= this.mTrimAudioEndUs && sampleData >= 0) {
                    this.mInputAudioDecoder.queueInputBuffer(iDequeueInputBuffer, 0, sampleData, this.mModifiedAudiotime, this.mAudioExtractor.getSampleFlags());
                    this.mAudioExtractor.advance();
                } else {
                    this.mAudioExtractorDone = true;
                }
                if (this.mAudioExtractorDone) {
                    LogS.e("TranscodeLib", "audio decoder sending EOS");
                    this.mInputAudioDecoder.queueInputBuffer(iDequeueInputBuffer, 0, 0, 0L, 4);
                }
            }
        }
    }

    private void getAudioDecoderOutput() {
        if (this.mUserStop || this.mAudioDecoderDone || this.mPendingAudioDecoderOutputBufferIndex != -1 || this.mAudioWaitFrame) {
            return;
        }
        if ((this.mAudioEncoderOutputMediaFormat == null || this.mMuxerStarted) && this.mAudioEncoderInputBufferCount <= 0) {
            int iDequeueOutputBuffer = this.mInputAudioDecoder.dequeueOutputBuffer(this.mAudioDecoderOutputBufferInfo, 10000L);
            if (iDequeueOutputBuffer == -1) {
                LogS.d("TranscodeLib", "audio decoder output buffer try again later while decoding");
                return;
            }
            if (iDequeueOutputBuffer == -3) {
                LogS.e("TranscodeLib", "audio decoder: output buffers changed");
                this.mAudioDecoderOutputBuffers = this.mInputAudioDecoder.getOutputBuffers();
                return;
            }
            if (iDequeueOutputBuffer == -2) {
                LogS.e("TranscodeLib", "audio decoder: output format changed: ");
                return;
            }
            if (iDequeueOutputBuffer < 0) {
                LogS.e("TranscodeLib", "Unexpected result from audio decoder dequeue output format.");
            } else if ((this.mAudioDecoderOutputBufferInfo.flags & 2) != 0) {
                LogS.e("TranscodeLib", "audio decoder: codec config buffer");
                this.mInputAudioDecoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
            } else {
                this.mPendingAudioDecoderOutputBufferIndex = iDequeueOutputBuffer;
            }
        }
    }

    private void getAudioFormat() {
        if (this.mUserStop || this.mMuxerStarted || this.mAudioEncoderDone || this.mAudioEncoderOutputMediaFormat != null) {
            return;
        }
        LogS.d("TranscodeLib", "getAudioFormat");
        this.mAudioEncoderOutputMediaFormat = this.mAudioExtractor.getTrackFormat(CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor));
        LogS.d("TranscodeLib", "getAudioFormat : " + this.mAudioEncoderOutputMediaFormat);
    }

    private void getandsendAudioToMuxer() {
        if (this.mUserStop || !this.mCopyAudio || !this.mMuxerStarted || this.mAudioEncoderDone) {
            return;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(131072);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.size = this.mAudioExtractor.readSampleData(byteBufferAllocate, 0);
        LogS.d("TranscodeLib", "Audio rewirte bufferInfoA.size : " + bufferInfo.size);
        bufferInfo.offset = 0;
        bufferInfo.size = this.mAudioExtractor.readSampleData(byteBufferAllocate, 0);
        if (bufferInfo.size < 0) {
            LogS.d("TranscodeLib", "saw input EOS: Audio");
            this.mAudioEncoderDone = true;
            bufferInfo.size = 0;
            return;
        }
        bufferInfo.presentationTimeUs = this.mAudioExtractor.getSampleTime();
        bufferInfo.flags = this.mAudioExtractor.getSampleFlags();
        this.mMuxer.writeSampleData(this.mAudioTrackIndex, byteBufferAllocate, bufferInfo);
        LogS.d("TranscodeLib", "Audio writeSampleData bufferInfoA.size : " + bufferInfo.size + ", bufferInfoA.presentationTimeUs :" + bufferInfo.presentationTimeUs);
        updateProgress(bufferInfo.presentationTimeUs, true);
        this.mAudioExtractor.advance();
    }

    protected boolean isSlowFast() {
        int i = this.mRecordingMode;
        return i == 2 || i == 1 || i == 12 || i == 21 || i == 13 || i == 15 || i == 19;
    }

    private void initAudioSlowV2() {
        if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
            this.mAudio.SRCInit(sSRCHandle, this.mOutputAudioSampleRateHZ * 2, ((int) this.mTimescale) * 12000, this.mOutputAudioChannelCount, 16, 16);
        } else {
            this.mAudio.SRCInit(sSRCHandle, this.mOutputAudioSampleRateHZ, ((int) this.mTimescale) * 12000, this.mOutputAudioChannelCount, 16, 16);
        }
    }

    protected void audioVolume(ByteBuffer byteBuffer, int i) {
        LogS.d("TranscodeLib", "AudioVolume  fade_sampleRateConvFactor: 0.1, data_length; " + i);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i);
        byteBufferAllocateDirect.position(0);
        byteBufferAllocateDirect.limit(i);
        for (int i2 = i / 4; i2 > 0; i2--) {
            short s = (short) (((short) (((short) ((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8))) & 65535)) * 0.1f);
            byteBufferAllocateDirect.put((byte) (s & 255));
            byteBufferAllocateDirect.put((byte) ((s & 65280) >> 8));
            short s2 = (short) (((short) (((short) ((byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8))) & 65535)) * 0.1f);
            byteBufferAllocateDirect.put((byte) (s2 & 255));
            byteBufferAllocateDirect.put((byte) ((s2 & 65280) >> 8));
        }
        byteBufferAllocateDirect.position(0);
        byteBufferAllocateDirect.limit(i);
        byteBuffer.position(0);
        while (byteBufferAllocateDirect.hasRemaining()) {
            byteBuffer.put(byteBufferAllocateDirect.get());
        }
        byteBufferAllocateDirect.clear();
    }

    protected int getRegionNumber(long j) {
        LogS.d("TranscodeLib", "getRegionNumber timeUs:" + j);
        List<SEFHelper.Region> list = this.mRegionList;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.mRegionList.size(); i++) {
            if (j >= this.mRegionList.get(i).mRegionStartTime * 1000 && j <= this.mRegionList.get(i).mRegionEndTime * 1000) {
                LogS.d("TranscodeLib", "getRegionNumber number :" + i);
                return i;
            }
        }
        return -1;
    }

    private boolean checkAudioEncoderOutputBufferIndex() {
        int iDequeueOutputBuffer = this.mOutputAudioEncoder.dequeueOutputBuffer(this.mAudioEncoderOutputBufferInfo, 10000L);
        if (iDequeueOutputBuffer == -1) {
            LogS.d("TranscodeLib", "audio encoder output buffer try again later");
            return false;
        }
        if (iDequeueOutputBuffer == -3) {
            LogS.d("TranscodeLib", "audio encoder: output buffers changed");
            this.mAudioEncoderOutputBuffers = this.mOutputAudioEncoder.getOutputBuffers();
            return false;
        }
        if (iDequeueOutputBuffer == -2) {
            this.mAudioEncoderOutputMediaFormat = this.mOutputAudioEncoder.getOutputFormat();
            LogS.e("TranscodeLib", "audio encoder: output format changed " + this.mAudioEncoderOutputMediaFormat);
            return false;
        }
        if (iDequeueOutputBuffer < 0) {
            LogS.d("TranscodeLib", "Unexpected result from audio encoder dequeue output format.");
            return false;
        }
        ByteBuffer byteBuffer = this.mAudioEncoderOutputBuffers[iDequeueOutputBuffer];
        if ((this.mAudioEncoderOutputBufferInfo.flags & 2) != 0) {
            LogS.e("TranscodeLib", "audio encoder ignoring BUFFER_FLAG_CODEC_CONFIG");
            this.mOutputAudioEncoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
            return false;
        }
        if (this.mAudioEncoderOutputBufferInfo.size != 0) {
            LogS.e("TranscodeLib", "audio encoder writing sample data to muxer  time: " + this.mAudioEncoderOutputBufferInfo.presentationTimeUs);
            if (this.mLastAudioSampleWrittenTime > this.mAudioEncoderOutputBufferInfo.presentationTimeUs) {
                LogS.d("TranscodeLib", "Audio time stamps are not in increasing order.");
            } else {
                this.mLastAudioSampleWrittenTime = this.mAudioEncoderOutputBufferInfo.presentationTimeUs;
                this.mMuxer.writeSampleData(this.mAudioTrackIndex, byteBuffer, this.mAudioEncoderOutputBufferInfo);
                updateProgress(this.mLastAudioSampleWrittenTime, true);
                this.mPausedVideoUs = this.mLastAudioSampleWrittenTime;
            }
        }
        if ((this.mAudioEncoderOutputBufferInfo.flags & 4) != 0) {
            LogS.e("TranscodeLib", "saw input EOS: Audio");
            this.mAudioEncoderDone = true;
        }
        this.mOutputAudioEncoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
        this.mAudioEncoderInputBufferCount--;
        return false;
    }

    private void sendAudioToMuxer() {
        while (!this.mUserStop && !this.mAudioEncoderDone) {
            if ((this.mAudioEncoderOutputMediaFormat != null && !this.mMuxerStarted) || this.mAudioEncoderInputBufferCount < 0) {
                return;
            }
            if (sNAACHandle != 0) {
                int i = this.mAudioEncoderInputBufferCount;
                if (i > 0) {
                    this.mAudioEncoderInputBufferCount = i - 1;
                }
                if (this.mAudioDecoderDone) {
                    this.mAudioEncoderDone = true;
                    LogS.e("TranscodeLib", "saw input EOS: Audio audioEncoderDone: " + this.mAudioEncoderDone);
                    return;
                }
                return;
            }
            if (!checkAudioEncoderOutputBufferIndex()) {
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x01fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendAudioToMuxer(int i, long j, long j2) {
        if (this.mNaccTime == -1) {
            this.mNaccTime = j;
        }
        if (i >= 0) {
            ByteBuffer byteBufferDuplicate = this.mAudioDecoderOutputBuffers[this.mPendingAudioDecoderOutputBufferIndex].duplicate();
            byteBufferDuplicate.position(this.mAudioDecoderOutputBufferInfo.offset);
            byteBufferDuplicate.limit(this.mAudioDecoderOutputBufferInfo.offset + i);
            int i2 = this.mRecordingMode;
            if ((i2 == 2 || i2 == 1) && i > 0 && sVSPHandle != 0) {
                float f = this.mTimescale;
                if (f == 1.0f) {
                    if (isSlowV2() && i > 0 && sSRCHandle != 0) {
                        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(409600);
                        byteBufferAllocateDirect.position(0);
                        LogS.d("TranscodeLib", "SRCExe2 is called");
                        int iSRCExe2 = this.mAudio.SRCExe2(sSRCHandle, byteBufferDuplicate, byteBufferAllocateDirect, (i / this.mOutputAudioChannelCount) / 2);
                        byteBufferAllocateDirect.limit(this.mOutputAudioChannelCount * iSRCExe2 * 2);
                        if (this.mTimescale != 8.0f) {
                            audioVolume(byteBufferAllocateDirect, this.mOutputAudioChannelCount * iSRCExe2 * 2);
                        }
                        byteBufferAllocateDirect.position(0);
                        byteBufferAllocateDirect.get(this.mTempAudioBuffer, this.mTempAudioLength, this.mOutputAudioChannelCount * iSRCExe2 * 2);
                        this.mTempAudioLength += iSRCExe2 * this.mOutputAudioChannelCount * 2;
                        byteBufferAllocateDirect.clear();
                        while (true) {
                            int i3 = this.mTempAudioLength;
                            int i4 = this.mTempAudioEncSize;
                            if (i3 < i4) {
                                break;
                            }
                            ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(i4);
                            byteBufferAllocateDirect2.put(this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                            int i5 = this.mTempAudioOffset;
                            int i6 = this.mTempAudioEncSize;
                            int i7 = i5 + i6;
                            this.mTempAudioOffset = i7;
                            byte[] bArr = this.mTempAudioBuffer;
                            System.arraycopy(bArr, i7, bArr, 0, this.mTempAudioLength - i6);
                            this.mTempAudioOffset = 0;
                            this.mTempAudioLength -= this.mTempAudioEncSize;
                            this.mAudioEncoderInputBufferCount++;
                            ByteBuffer byteBufferAllocateDirect3 = ByteBuffer.allocateDirect(4096);
                            int iNAACEncoderExe = this.mAudio.NAACEncoderExe(sNAACHandle, byteBufferAllocateDirect2, byteBufferAllocateDirect3, this.mOutputAudioChannelCount);
                            LogS.d("TranscodeLib", " Enc NAACEncoderExe encoded_size: " + iNAACEncoderExe + " naac_time : " + this.mNaccTime);
                            this.mAudioEncoderOutputBufferInfo.size = iNAACEncoderExe;
                            this.mAudioEncoderOutputBufferInfo.presentationTimeUs = this.mNaccTime;
                            byteBufferAllocateDirect3.limit(iNAACEncoderExe);
                            this.mMuxer.writeSampleData(this.mAudioTrackIndex, byteBufferAllocateDirect3, this.mAudioEncoderOutputBufferInfo);
                            long j3 = this.mNaccTime;
                            this.mPausedVideoUs = j3;
                            this.mNaccTime = j3 + 21333;
                            this.mAudioEncoderInputBufferCount--;
                            byteBufferAllocateDirect2.clear();
                        }
                    } else {
                        ByteBuffer byteBufferAllocateDirect4 = ByteBuffer.allocateDirect(4096);
                        byteBufferAllocateDirect4.position(0);
                        byteBufferAllocateDirect4.put(byteBufferDuplicate);
                        byteBufferAllocateDirect4.limit(i);
                        this.mAudioEncoderInputBufferCount++;
                        ByteBuffer byteBufferAllocateDirect5 = ByteBuffer.allocateDirect(4096);
                        int iNAACEncoderExe2 = this.mAudio.NAACEncoderExe(sNAACHandle, byteBufferAllocateDirect4, byteBufferAllocateDirect5, this.mOutputAudioChannelCount);
                        LogS.d("TranscodeLib", " Enc NAACEncoderExe2 encoded_size: " + iNAACEncoderExe2 + " naac_time : " + this.mNaccTime);
                        this.mAudioEncoderOutputBufferInfo.size = iNAACEncoderExe2;
                        this.mAudioEncoderOutputBufferInfo.presentationTimeUs = this.mNaccTime;
                        byteBufferAllocateDirect5.limit(iNAACEncoderExe2);
                        this.mMuxer.writeSampleData(this.mAudioTrackIndex, byteBufferAllocateDirect5, this.mAudioEncoderOutputBufferInfo);
                        long j4 = this.mNaccTime;
                        this.mPausedVideoUs = j4;
                        this.mNaccTime = j4 + 21333;
                        this.mAudioEncoderInputBufferCount--;
                        byteBufferAllocateDirect4.clear();
                    }
                } else {
                    if (f > 8.0f) {
                        int regionNumber = getRegionNumber(j2);
                        LogS.d("TranscodeLib", "Seekto region : " + regionNumber + ", end time :" + (this.mRegionList.get(regionNumber).mRegionEndTime * 1000) + ", RegionList.size() : " + this.mRegionList.size());
                        if (regionNumber < this.mRegionList.size() - 1) {
                            this.mAudioExtractor.seekTo(this.mRegionList.get(regionNumber).mRegionEndTime * 1000, 0);
                            while (this.mAudioExtractor.getSampleTime() < this.mRegionList.get(regionNumber).mRegionEndTime * 1000) {
                                if (this.mAudioExtractor.getSampleTime() == -1) {
                                    throw new RuntimeException("Invalid File!");
                                }
                                this.mAudioExtractor.advance();
                            }
                        } else {
                            LogS.e("TranscodeLib", "audio decoder: EOS");
                            this.mAudioDecoderDone = true;
                        }
                        this.mInputAudioDecoder.releaseOutputBuffer(this.mPendingAudioDecoderOutputBufferIndex, false);
                        LogS.d("TranscodeLib", "releaseOutputBuffer : " + this.mPendingAudioDecoderOutputBufferIndex);
                        this.mPendingAudioDecoderOutputBufferIndex = -1;
                        if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                            LogS.e("TranscodeLib", "audio decoder: EOS");
                            this.mAudioDecoderDone = true;
                            this.mAudioEncoderInputBufferCount++;
                            return;
                        }
                        return;
                    }
                    ByteBuffer byteBufferAllocateDirect6 = ByteBuffer.allocateDirect(409600);
                    byteBufferAllocateDirect6.position(0);
                    LogS.d("TranscodeLib", "VSPExe2 is called");
                    int iVSPExe2 = this.mAudio.VSPExe2(sVSPHandle, byteBufferAllocateDirect6, byteBufferDuplicate, i / this.mOutputAudioChannelCount);
                    byteBufferAllocateDirect6.limit(this.mOutputAudioChannelCount * iVSPExe2 * 2);
                    byteBufferAllocateDirect6.get(this.mTempAudioBuffer, this.mTempAudioLength, this.mOutputAudioChannelCount * iVSPExe2 * 2);
                    this.mTempAudioLength += iVSPExe2 * this.mOutputAudioChannelCount * 2;
                    byteBufferAllocateDirect6.clear();
                    LogS.d("TranscodeLib", "VSPExe2 original size :" + i + ", mTempAudioLength :" + this.mTempAudioLength + ", mTempAudioEncSize :" + this.mTempAudioEncSize);
                    while (true) {
                        int i8 = this.mTempAudioLength;
                        int i9 = this.mTempAudioEncSize;
                        if (i8 < i9) {
                            break;
                        }
                        ByteBuffer byteBufferAllocateDirect7 = ByteBuffer.allocateDirect(i9);
                        byteBufferAllocateDirect7.put(this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                        int i10 = this.mTempAudioOffset;
                        int i11 = this.mTempAudioEncSize;
                        int i12 = i10 + i11;
                        this.mTempAudioOffset = i12;
                        byte[] bArr2 = this.mTempAudioBuffer;
                        System.arraycopy(bArr2, i12, bArr2, 0, this.mTempAudioLength - i11);
                        this.mTempAudioOffset = 0;
                        this.mTempAudioLength -= this.mTempAudioEncSize;
                        this.mAudioEncoderInputBufferCount++;
                        ByteBuffer byteBufferAllocateDirect8 = ByteBuffer.allocateDirect(4096);
                        int iNAACEncoderExe3 = this.mAudio.NAACEncoderExe(sNAACHandle, byteBufferAllocateDirect7, byteBufferAllocateDirect8, this.mOutputAudioChannelCount);
                        LogS.d("TranscodeLib", " Enc NAACEncoderExe encoded_size: " + iNAACEncoderExe3 + " naac_time : " + this.mNaccTime);
                        this.mAudioEncoderOutputBufferInfo.size = iNAACEncoderExe3;
                        this.mAudioEncoderOutputBufferInfo.presentationTimeUs = this.mNaccTime;
                        byteBufferAllocateDirect8.limit(iNAACEncoderExe3);
                        this.mMuxer.writeSampleData(this.mAudioTrackIndex, byteBufferAllocateDirect8, this.mAudioEncoderOutputBufferInfo);
                        long j5 = this.mNaccTime;
                        this.mPausedVideoUs = j5;
                        this.mNaccTime = j5 + 21333;
                        this.mAudioEncoderInputBufferCount--;
                        byteBufferAllocateDirect7.clear();
                    }
                }
            }
        }
        if (checkDecoderFinish()) {
            this.mAudioEncoderInputBufferCount++;
        }
        updateProgress(this.mPausedVideoUs, true);
    }

    protected int checkDecAudio(int i, boolean z) {
        ByteBuffer byteBufferDuplicate = this.mAudioDecoderOutputBuffers[this.mPendingAudioDecoderOutputBufferIndex].duplicate();
        byteBufferDuplicate.position(this.mAudioDecoderOutputBufferInfo.offset);
        byteBufferDuplicate.limit(this.mAudioDecoderOutputBufferInfo.offset + i);
        this.mDecAudio = ByteBuffer.allocateDirect(byteBufferDuplicate.capacity());
        if (this.mOriginalAudioChannelCount > 0) {
            int i2 = (i / this.mOriginalAudioChannelCount) * this.mOutputAudioChannelCount;
            int i3 = this.mOutputAudioChannelCount * 2;
            int i4 = this.mOriginalAudioChannelCount * 2;
            this.mDecAudio.position(0);
            this.mDecAudio.limit(i2);
            for (int i5 = 0; i5 < i / i4; i5++) {
                for (int i6 = 0; i6 < this.mOutputAudioChannelCount; i6++) {
                    int i7 = i6 * 2;
                    int i8 = (i5 * i3) + i7;
                    int i9 = (i5 * i4) + i7;
                    this.mDecAudio.put(i8, byteBufferDuplicate.get(i9));
                    this.mDecAudio.put(i8 + 1, byteBufferDuplicate.get(i9 + 1));
                }
            }
            i = i2;
        } else {
            this.mDecAudio.position(0);
            this.mDecAudio.limit(i);
            this.mDecAudio.put(byteBufferDuplicate);
        }
        if (!z) {
            this.mDecAudio.position(0);
            this.mDecAudio.get(this.mTempAudioBuffer, this.mTempAudioLength, i);
            this.mTempAudioLength += i;
        }
        return i;
    }

    private void checkAudioDecoderEOS(long j) {
        int regionNumber = getRegionNumber(j);
        LogS.d("TranscodeLib", "Seekto region : " + regionNumber + ", end time :" + (this.mRegionList.get(regionNumber).mRegionEndTime * 1000) + ", RegionList.size() : " + this.mRegionList.size());
        if (regionNumber < this.mRegionList.size() - 1) {
            this.mAudioExtractor.seekTo(this.mRegionList.get(regionNumber).mRegionEndTime * 1000, 0);
            while (this.mAudioExtractor.getSampleTime() < this.mRegionList.get(regionNumber).mRegionEndTime * 1000) {
                if (this.mAudioExtractor.getSampleTime() == -1) {
                    throw new RuntimeException("Invalid File!");
                }
                this.mAudioExtractor.advance();
            }
        } else {
            LogS.e("TranscodeLib", "audio decoder: EOS");
            this.mAudioDecoderDone = true;
        }
        this.mInputAudioDecoder.releaseOutputBuffer(this.mPendingAudioDecoderOutputBufferIndex, false);
        this.mPendingAudioDecoderOutputBufferIndex = -1;
        if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
            LogS.e("TranscodeLib", "audio decoder: EOS");
            this.mAudioDecoderDone = true;
            this.mAudioEncoderInputBufferCount++;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendAudioToEncoder_AudioSolution(int i, long j, long j2) throws MediaCodec.CryptoException {
        if (i >= 0) {
            int iCheckDecAudio = checkDecAudio(i, true);
            int i2 = this.mRecordingMode;
            if ((i2 == 2 || i2 == 1) && i > 0 && sVSPHandle != 0) {
                float f = this.mTimescale;
                if (f != 1.0f) {
                    if (f > 8.0f) {
                        checkAudioDecoderEOS(j2);
                        return;
                    }
                    ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(409600);
                    byteBufferAllocateDirect.position(0);
                    LogS.d("TranscodeLib", "VSPExe2 is called");
                    int iVSPExe2 = this.mAudio.VSPExe2(sVSPHandle, byteBufferAllocateDirect, this.mDecAudio, iCheckDecAudio / this.mOutputAudioChannelCount);
                    byteBufferAllocateDirect.limit(this.mOutputAudioChannelCount * iVSPExe2 * 2);
                    byteBufferAllocateDirect.get(this.mTempAudioBuffer, this.mTempAudioLength, this.mOutputAudioChannelCount * iVSPExe2 * 2);
                    this.mTempAudioLength += iVSPExe2 * this.mOutputAudioChannelCount * 2;
                    byteBufferAllocateDirect.clear();
                    LogS.d("TranscodeLib", "VSPExe2 original size :" + i + ", mTempAudioLength :" + this.mTempAudioLength);
                    long j3 = j;
                    while (this.mTempAudioLength >= this.mTempAudioEncSize) {
                        int iDequeueInputBuffer = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
                        if (iDequeueInputBuffer == -1) {
                            LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                            return;
                        }
                        this.mAudioEncoderInputBuffers[iDequeueInputBuffer].put(this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                        int i3 = this.mTempAudioOffset;
                        int i4 = this.mTempAudioEncSize;
                        int i5 = i3 + i4;
                        this.mTempAudioOffset = i5;
                        byte[] bArr = this.mTempAudioBuffer;
                        System.arraycopy(bArr, i5, bArr, 0, this.mTempAudioLength - i4);
                        this.mTempAudioOffset = 0;
                        this.mTempAudioLength -= this.mTempAudioEncSize;
                        this.mOutputAudioEncoder.queueInputBuffer(iDequeueInputBuffer, 0, this.mTempAudioEncSize, j3, this.mAudioDecoderOutputBufferInfo.flags);
                        this.mAudioEncoderInputBufferCount++;
                        j3 += 21333;
                    }
                } else if (isSlowV2() && i > 0 && sSRCHandle != 0) {
                    ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(409600);
                    byteBufferAllocateDirect2.position(0);
                    LogS.d("TranscodeLib", "SRCExe2 is called");
                    int iSRCExe2 = this.mAudio.SRCExe2(sSRCHandle, this.mDecAudio, byteBufferAllocateDirect2, (iCheckDecAudio / this.mOutputAudioChannelCount) / 2);
                    byteBufferAllocateDirect2.limit(this.mOutputAudioChannelCount * iSRCExe2 * 2);
                    if (this.mTimescale != 8.0f) {
                        audioVolume(byteBufferAllocateDirect2, this.mOutputAudioChannelCount * iSRCExe2 * 2);
                    }
                    byteBufferAllocateDirect2.position(0);
                    byteBufferAllocateDirect2.get(this.mTempAudioBuffer, this.mTempAudioLength, this.mOutputAudioChannelCount * iSRCExe2 * 2);
                    this.mTempAudioLength += iSRCExe2 * this.mOutputAudioChannelCount * 2;
                    byteBufferAllocateDirect2.clear();
                    long j4 = j;
                    while (this.mTempAudioLength >= this.mTempAudioEncSize) {
                        int iDequeueInputBuffer2 = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
                        if (iDequeueInputBuffer2 == -1) {
                            LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                            return;
                        }
                        this.mAudioEncoderInputBuffers[iDequeueInputBuffer2].put(this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                        int i6 = this.mTempAudioOffset;
                        int i7 = this.mTempAudioEncSize;
                        int i8 = i6 + i7;
                        this.mTempAudioOffset = i8;
                        byte[] bArr2 = this.mTempAudioBuffer;
                        System.arraycopy(bArr2, i8, bArr2, 0, this.mTempAudioLength - i7);
                        this.mTempAudioOffset = 0;
                        this.mTempAudioLength -= this.mTempAudioEncSize;
                        this.mOutputAudioEncoder.queueInputBuffer(iDequeueInputBuffer2, 0, this.mTempAudioEncSize, j4, this.mAudioDecoderOutputBufferInfo.flags);
                        this.mAudioEncoderInputBufferCount++;
                        j4 += 21333;
                    }
                } else {
                    int iDequeueInputBuffer3 = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
                    if (iDequeueInputBuffer3 == -1) {
                        LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                        return;
                    }
                    ByteBuffer byteBuffer = this.mAudioEncoderInputBuffers[iDequeueInputBuffer3];
                    byteBuffer.position(0);
                    byteBuffer.put(this.mDecAudio);
                    this.mOutputAudioEncoder.queueInputBuffer(iDequeueInputBuffer3, 0, iCheckDecAudio, j, this.mAudioDecoderOutputBufferInfo.flags);
                    this.mAudioEncoderInputBufferCount++;
                }
            }
        }
        checkDecoderFinish();
    }

    protected boolean checkDecoderFinish() {
        if (this.mTempAudioLength < this.mTempAudioEncSize) {
            this.mInputAudioDecoder.releaseOutputBuffer(this.mPendingAudioDecoderOutputBufferIndex, false);
            this.mPendingAudioDecoderOutputBufferIndex = -1;
            if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                LogS.e("TranscodeLib", "audio decoder: EOS  mTempAudioLength : " + this.mTempAudioLength);
                this.mAudioDecoderDone = true;
            }
            return true;
        }
        LogS.d("TranscodeLib", "Not finished yet");
        return false;
    }

    private boolean getAudioDrop(long j, int i) {
        List<SEFHelper.Region> list = this.mRegionList;
        if (list != null && !list.isEmpty()) {
            int i2 = 0;
            if (i == 1 || i == 2) {
                while (true) {
                    if (i2 < this.mRegionList.size()) {
                        if (j >= this.mRegionList.get(i2).mRegionStartTime * 1000 && j < this.mRegionList.get(i2).mRegionEndTime * 1000) {
                            float timeScale = SEFHelper.getTimeScale(this.mRegionList.get(i2).mRegionSpeedType);
                            int i3 = this.mRegionList.get(i2).mRegionStartTime;
                            long j2 = ((j - (this.mRegionList.get(i2).mRegionStartTime * 1000)) * ((long) (timeScale * 1000000.0f))) / 1000000;
                            break;
                        }
                        if (j >= this.mRegionList.get(i2).mRegionEndTime * 1000) {
                            SEFHelper.getTimeScale(this.mRegionList.get(i2).mRegionSpeedType);
                            if (i == 1) {
                                int i4 = this.mRegionList.get(i2).mRegionEndTime;
                                int i5 = this.mRegionList.get(i2).mRegionStartTime;
                            } else if (i == 2) {
                                int i6 = this.mRegionList.get(i2).mRegionEndTime;
                                int i7 = this.mRegionList.get(i2).mRegionStartTime;
                            }
                        }
                        i2++;
                    } else {
                        break;
                    }
                }
            } else {
                long j3 = 0;
                while (i2 < this.mRegionList.size() && j > this.mRegionList.get(i2).mRegionEndTime * 1000) {
                    if (this.mRegionList.get(i2).mRegionSpeed == 9) {
                        j3 += (this.mRegionList.get(i2).mRegionEndTime - this.mRegionList.get(i2).mRegionAudioEndTime) * 1000;
                    }
                    i2++;
                }
                if (j < j3) {
                    LogS.d("TranscodeLib", "[getAudioDrop]SampleTime error tempSampleTime = " + j + ",timeDelta :" + j3);
                } else {
                    LogS.d("TranscodeLib", "[getAudioDrop]SampleTime new tempSampleTime = " + (j - j3) + ",timeDelta :" + j3);
                }
            }
        }
        return this.mSefhelper.isSEFRegion(j, i);
    }

    private float getTimescale(long j, int i) {
        List<SEFHelper.Region> list = this.mRegionList;
        if (list != null && !list.isEmpty() && (i == 1 || i == 2 || i == 12 || i == 21 || i == 13 || i == 15 || i == 19)) {
            for (int i2 = 0; i2 < this.mRegionList.size(); i2++) {
                if (j >= this.mRegionList.get(i2).mRegionStartTime * 1000 && j < this.mRegionList.get(i2).mRegionEndTime * 1000) {
                    float timeScale = 1.0f / SEFHelper.getTimeScale(this.mRegionList.get(i2).mRegionSpeedType);
                    LogS.d("TranscodeLib", "[getTimescale]timescale = " + timeScale);
                    return timeScale;
                }
            }
        }
        return 1.0f;
    }

    private void checkAudioFollowHandle(long j) {
        float timescale = getTimescale(j, this.mRecordingMode);
        int i = this.mRecordingMode;
        if ((i == 2 || i == 1) && sVSPHandle != 0 && this.mTimescale != timescale) {
            this.mTimescale = timescale;
            this.mAudio.VSPSetPar(sVSPHandle, this.mTimescale);
        }
        if (!isSlowV2() || sSRCHandle == 0 || this.mTimescale == timescale || j < 0) {
            return;
        }
        this.mTimescale = timescale;
        initAudioSlowV2();
    }

    private void checkTempRadio(int i, int i2, long j) throws MediaCodec.CryptoException {
        ByteBuffer byteBuffer = this.mAudioEncoderInputBuffers[i];
        if (this.mOriginalAudioChannelCount > 0) {
            i2 /= this.mOriginalAudioChannelCount;
        }
        int i3 = i2;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i3);
        byteBufferAllocateDirect.position(0);
        byteBufferAllocateDirect.limit(i3);
        byteBuffer.position(0);
        byteBuffer.put(byteBufferAllocateDirect);
        this.mOutputAudioEncoder.queueInputBuffer(i, 0, i3, j, this.mAudioDecoderOutputBufferInfo.flags);
        this.mAudioEncoderInputBufferCount++;
        byteBufferAllocateDirect.clear();
        this.mAudioLoopCount++;
    }

    private boolean checkAudioDecoderEOSNotWaitFrameCase(long j) throws MediaCodec.CryptoException {
        LogS.e("TranscodeLib", "audio decoder: EOS  mTempAudioLength : " + this.mTempAudioLength);
        this.mAudioDecoderDone = true;
        if (this.mTempAudioLength > 0) {
            int iDequeueInputBuffer = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
            if (iDequeueInputBuffer == -1) {
                LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                return false;
            }
            this.mAudioEncoderInputBuffers[iDequeueInputBuffer].put(this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioLength);
            LogS.d("TranscodeLib", "Enc Last frame queueInputBuffer size:" + this.mTempAudioLength + ", presentationTime :" + j);
            this.mOutputAudioEncoder.queueInputBuffer(iDequeueInputBuffer, 0, this.mTempAudioLength, j, 0);
            this.mAudioEncoderInputBufferCount = this.mAudioEncoderInputBufferCount + 1;
        }
        int iDequeueInputBuffer2 = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
        if (iDequeueInputBuffer2 == -1) {
            LogS.d("TranscodeLib", "audio encoder input buffer try again later");
            return false;
        }
        this.mAudioEncoderInputBuffers[iDequeueInputBuffer2].put(this.mTempAudioBuffer, this.mTempAudioOffset, 0);
        LogS.d("TranscodeLib", "Enc EOS queueInputBuffer  time :" + this.mAudioDecoderOutputBufferInfo.presentationTimeUs + ", size : " + this.mAudioDecoderOutputBufferInfo.size);
        this.mOutputAudioEncoder.queueInputBuffer(iDequeueInputBuffer2, 0, this.mAudioDecoderOutputBufferInfo.size, this.mAudioDecoderOutputBufferInfo.presentationTimeUs, this.mAudioDecoderOutputBufferInfo.flags);
        this.mAudioEncoderInputBufferCount = this.mAudioEncoderInputBufferCount + 1;
        return true;
    }

    private void checkAudioLoopCount(long j) {
        if (this.mPendingAudioDecoderOutputBufferIndex != -1) {
            this.mInputAudioDecoder.releaseOutputBuffer(this.mPendingAudioDecoderOutputBufferIndex, false);
            this.mPendingAudioDecoderOutputBufferIndex = -1;
        }
        this.mInputAudioDecoder.flush();
        LogS.d("TranscodeLib", "seek to next frame\taudioLoopCount :" + this.mAudioLoopCount + ", seektime: " + j);
        this.mAudioWaitFrame = false;
        this.mAudioLoopCount = 0;
        this.mAudioExtractor.seekTo(j, 1);
        while (this.mAudioExtractor.getSampleTime() < j) {
            if (this.mAudioExtractor.getSampleTime() == -1) {
                throw new RuntimeException("Invalid File!");
            }
            this.mAudioExtractor.advance();
        }
    }

    private void sendAudioToEncoder(int i, long j, long j2, long j3) throws MediaCodec.CryptoException {
        long j4;
        if (this.mAudioWaitFrame) {
            int iDequeueInputBuffer = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
            if (iDequeueInputBuffer == -1) {
                LogS.d("TranscodeLib", "audio encoder input buffer try again later");
                return;
            }
            if (i >= 0) {
                checkTempRadio(iDequeueInputBuffer, i, j3);
            }
            if (j3 > this.mOriginTrimEndUs) {
                if (this.mPendingAudioDecoderOutputBufferIndex != -1) {
                    this.mInputAudioDecoder.releaseOutputBuffer(this.mPendingAudioDecoderOutputBufferIndex, false);
                    this.mPendingAudioDecoderOutputBufferIndex = -1;
                }
                LogS.d("TranscodeLib", "Forcely EOS  AudioLoopCount :" + this.mAudioLoopCount + ", seek time:" + j2 + ", temp_presentationTime :" + j3);
                this.mAudioWaitFrame = false;
                this.mAudioLoopCount = 0;
                this.mInputAudioDecoder.flush();
                this.mAudioExtractor.seekTo(j2, 1);
            }
            if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                LogS.e("TranscodeLib", "audio decoder: EOS");
                if (this.mPendingAudioDecoderOutputBufferIndex != -1) {
                    this.mInputAudioDecoder.releaseOutputBuffer(this.mPendingAudioDecoderOutputBufferIndex, false);
                    this.mPendingAudioDecoderOutputBufferIndex = -1;
                }
                this.mAudioWaitFrame = false;
                this.mAudioDecoderDone = true;
                this.mAudioLoopCount = 0;
                return;
            }
            return;
        }
        if (this.mSEFVideo && this.mAudioLoopCount > 0) {
            checkAudioLoopCount(j2);
            return;
        }
        if (i < 0 || j < 0) {
            j4 = j;
        } else {
            if (!this.mkeepAudioFrame) {
                checkDecAudio(i, false);
            }
            j4 = j;
            while (true) {
                if (this.mTempAudioLength < this.mTempAudioEncSize) {
                    break;
                }
                int iDequeueInputBuffer2 = this.mOutputAudioEncoder.dequeueInputBuffer(10000L);
                if (iDequeueInputBuffer2 == -1) {
                    LogS.d("TranscodeLib", " audio encoder input buffer try again later");
                    break;
                }
                this.mAudioEncoderInputBuffers[iDequeueInputBuffer2].put(this.mTempAudioBuffer, this.mTempAudioOffset, this.mTempAudioEncSize);
                int i2 = this.mTempAudioOffset;
                int i3 = this.mTempAudioEncSize;
                int i4 = i2 + i3;
                this.mTempAudioOffset = i4;
                byte[] bArr = this.mTempAudioBuffer;
                System.arraycopy(bArr, i4, bArr, 0, this.mTempAudioLength - i3);
                this.mTempAudioOffset = 0;
                this.mTempAudioLength -= this.mTempAudioEncSize;
                this.mOutputAudioEncoder.queueInputBuffer(iDequeueInputBuffer2, 0, this.mTempAudioEncSize, j4, this.mAudioDecoderOutputBufferInfo.flags);
                this.mAudioEncoderInputBufferCount++;
                j4 += 21333;
            }
        }
        if (this.mTempAudioLength < this.mTempAudioEncSize) {
            this.mInputAudioDecoder.releaseOutputBuffer(this.mPendingAudioDecoderOutputBufferIndex, false);
            this.mPendingAudioDecoderOutputBufferIndex = -1;
            this.mkeepAudioFrame = false;
            if ((this.mAudioDecoderOutputBufferInfo.flags & 4) != 0) {
                checkAudioDecoderEOSNotWaitFrameCase(j4);
                return;
            }
            return;
        }
        LogS.d("TranscodeLib", "Not finished yet");
        this.mkeepAudioFrame = true;
    }

    private void checkSendAudioFollowHandle(int i, long j, long j2, long j3) throws MediaCodec.CryptoException {
        if (sNAACHandle != 0) {
            sendAudioToMuxer(i, j, j2);
        } else if (sVSPHandle != 0 || sSRCHandle != 0) {
            sendAudioToEncoder_AudioSolution(i, j, j2);
        } else {
            sendAudioToEncoder(i, j, j2, j3);
        }
    }

    protected long getSlowfastSeektime(long j) {
        long j2 = 0;
        if (j < 0) {
            return -1L;
        }
        List<SEFHelper.Region> list = this.mRegionList;
        if (list == null || list.isEmpty()) {
            LogS.d("TranscodeLib", "There is no region info.");
            return j;
        }
        int i = 0;
        while (true) {
            if (i >= this.mRegionList.size()) {
                break;
            }
            float timeScale = SEFHelper.getTimeScale(this.mRegionList.get(i).mRegionSpeedType);
            long j3 = this.mRegionList.get(i).mRegionStartTime * 1000;
            long j4 = (this.mRegionList.get(i).mRegionEndTime * 1000) - j3;
            long j5 = (((long) (1000000.0f * timeScale)) * j4) / 1000000;
            long j6 = j3 + j2;
            if (j >= j6 && j <= j5 + j3 + j2) {
                return j3 + ((long) (((j - j3) - j2) / timeScale));
            }
            if (j > j3 + j5 + j2) {
                j2 += j5 - j4;
                if (i == this.mRegionList.size() - 1) {
                    return j - j2;
                }
            } else if (j < j6) {
                if (i != 0) {
                    return j - j2;
                }
            }
            i++;
        }
        return j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e6, code lost:
    
        r0 = r21 + r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected long getSuperslowSeektime(long j) {
        EncodeBase encodeBase;
        EncodeBase encodeBase2 = this;
        long j2 = 0;
        if (j < 0) {
            return -1L;
        }
        List<SEFHelper.Region> list = encodeBase2.mRegionList;
        String str = "TranscodeLib";
        if (list == null || list.isEmpty()) {
            LogS.d("TranscodeLib", "There is no region info.");
            return j;
        }
        long j3 = j;
        int i = 0;
        while (true) {
            if (i >= encodeBase2.mRegionList.size()) {
                break;
            }
            long j4 = encodeBase2.mRegionList.get(i).mRegionStartTime * 1000;
            long j5 = encodeBase2.mRegionList.get(i).mRegionEndTime * 1000;
            long j6 = encodeBase2.mRegionList.get(i).mRegionAudioEndTime * 1000;
            long j7 = j5 - j6;
            boolean z = encodeBase2.mRegionList.get(i).mRegionSpeed == 9;
            str = str;
            LogS.d(str, "[getSuperslowSeektime] regStartTime = " + j4 + ",regEndTime : " + j5 + ",regAudioEndTime: " + j6 + ", isCancel =" + z + ",tmpSeekTimeUs: " + j3 + ", timeDelta:" + j2 + ", seekTimeUs:" + j + ", i :" + i);
            if (j3 < j6 || j3 > j5) {
                encodeBase = this;
                if (j3 > j5) {
                    if (z) {
                        j2 += j7;
                        j3 += j7;
                    }
                    if (i == encodeBase.mRegionList.size() - 1) {
                        break;
                    }
                    i++;
                    encodeBase2 = encodeBase;
                } else if (j3 >= j6) {
                    i++;
                    encodeBase2 = encodeBase;
                } else if (i == 0) {
                    break;
                }
            } else {
                if (z) {
                    j2 += j7;
                    j3 += j7;
                }
                encodeBase = this;
                if (i == encodeBase.mRegionList.size() - 1) {
                    break;
                }
                i++;
                encodeBase2 = encodeBase;
            }
            LogS.d(str, "[getSuperslowSeektime] seekTimeUs= " + j);
            return j;
        }
        long j8 = j;
        LogS.d(str, "[getSuperslowSeektime] seekTimeUs= " + j8);
        return j8;
    }

    private void sendAudioDecoderOutput() throws MediaCodec.CryptoException {
        long j;
        long superslowSeektime;
        while (!this.mUserStop && !this.mAudioDecoderDone) {
            if ((this.mPendingAudioDecoderOutputBufferIndex == -1 && !this.mAudioWaitFrame) || this.mAudioEncoderInputBufferCount > 0) {
                return;
            }
            int i = this.mAudioDecoderOutputBufferInfo.size;
            long j2 = this.mAudioDecoderOutputBufferInfo.presentationTimeUs;
            long j3 = j2 + (this.mAudioLoopCount * 21333);
            if (this.mSEFVideo) {
                if (this.isSlowFast()) {
                    superslowSeektime = this.getSlowfastSeektime(j3);
                } else {
                    superslowSeektime = this.isSuperSlow() ? this.getSuperslowSeektime(j3) : j3;
                }
                if (this.mAudio == null) {
                    this.mAudioWaitFrame = this.getAudioDrop(superslowSeektime, this.mRecordingMode);
                }
                this.checkAudioFollowHandle(superslowSeektime);
                LogS.d("TranscodeLib", "presentationTime :" + j2 + ", temp_presentationTime: " + j3 + ", seektime :" + superslowSeektime + ", audioWaitFrame:" + this.mAudioWaitFrame + ", timescale : " + this.mTimescale);
                j = superslowSeektime;
            } else {
                j = j3;
            }
            EncodeBase encodeBase = this;
            encodeBase.checkSendAudioFollowHandle(i, j2, j, j3);
            ByteBuffer byteBuffer = encodeBase.mDecAudio;
            if (byteBuffer != null) {
                byteBuffer.clear();
                encodeBase.mDecAudio = null;
            }
            this = encodeBase;
        }
    }

    protected void startAudioEncoding() throws MediaCodec.CryptoException {
        sendAudioToDecoder();
        getAudioDecoderOutput();
        sendAudioDecoderOutput();
        sendAudioToMuxer();
    }

    protected void startAudioRewriting() {
        getAudioFormat();
        getandsendAudioToMuxer();
    }

    private boolean checkEncoderOutputBufferIndex(int i) throws InterruptedException {
        if (i == -1) {
            LogS.d("TranscodeLib", "no video encoder output buffer");
            try {
                Thread.sleep(10L);
            } catch (Exception e) {
                LogS.e("TranscodeLib", "sleep interrupted" + e);
            }
            return false;
        }
        if (i == -3) {
            LogS.d("TranscodeLib", "video encoder: output buffers changed");
            return false;
        }
        if (i != -2) {
            if (i >= 0) {
                return true;
            }
            LogS.d("TranscodeLib", "Unexpected result from video encoder dequeue output format.");
            return false;
        }
        LogS.e("TranscodeLib", "video encoder: output format changed " + this.mOutputVideoEncoder.getOutputFormat());
        if (this.mVideoTrackIndex >= 0) {
            throw new RuntimeException("Video encoder output format changed after muxer has started");
        }
        this.mVideoEncoderOutputMediaFormat = this.mOutputVideoEncoder.getOutputFormat();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendVideoFramesToMuxer(int i, MediaCodec.BufferInfo bufferInfo) {
        if (!checkEncoderOutputBufferIndex(i)) {
            LogS.e("TranscodeLib", "video encoder: sendVideoFramesToMuxer condition error");
            return;
        }
        ByteBuffer outputBuffer = this.mOutputVideoEncoder.getOutputBuffer(i);
        if ((bufferInfo.flags & 2) != 0) {
            LogS.e("TranscodeLib", "video encoder: codec config buffer");
            this.mOutputVideoEncoder.releaseOutputBuffer(i, false);
            return;
        }
        if (bufferInfo.size != 0) {
            LogS.e("TranscodeLib", "video encoder: writing sample data timestamp " + bufferInfo.presentationTimeUs);
            try {
                this.mMuxer.writeSampleData(this.mVideoTrackIndex, outputBuffer, bufferInfo);
            } catch (IllegalStateException unused) {
                LogS.e("TranscodeLib", "fail to writeSampleData videoEncoderDone? " + this.mVideoEncoderDone);
            }
            updateProgress(bufferInfo.presentationTimeUs, false);
        }
        if ((bufferInfo.flags & 4) != 0) {
            LogS.e("TranscodeLib", "video encoder: EOS");
            this.mVideoEncoderDone = true;
        }
        try {
            this.mOutputVideoEncoder.releaseOutputBuffer(i, false);
        } catch (IllegalStateException unused2) {
            LogS.e("TranscodeLib", "fail to release output buffer of encoder videoEncoderDone? " + this.mVideoEncoderDone);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isVideoDecoderAvailableCondition() {
        if (this.mCodecError || this.mUserStop || this.mVideoDecoderDone || !this.mPrepared || this.mInputVideoDecoder == null) {
            return false;
        }
        return this.mVideoEncoderOutputMediaFormat == null || this.mMuxerStarted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean isVideoEncoderAvailableCondition() {
        boolean z;
        if (this.mCodecError || this.mUserStop || this.mVideoEncoderDone || !this.mPrepared) {
            z = false;
        } else {
            if (this.mVideoEncoderOutputMediaFormat != null) {
                if (this.mMuxerStarted) {
                }
            }
            z = true;
        }
        return z;
    }

    private boolean checkDecoderOutputBufferIndex(int i, MediaCodec.BufferInfo bufferInfo) {
        if (i == -1) {
            LogS.d("TranscodeLib", "no video decoder output buffer");
            return false;
        }
        if (i == -3) {
            LogS.e("TranscodeLib", "video decoder: output buffers changed");
            return false;
        }
        if (i == -2) {
            LogS.e("TranscodeLib", "video decoder: codec info format changed" + this.mInputVideoDecoder.getOutputFormat());
            return false;
        }
        if ((bufferInfo.flags & 2) == 0) {
            return true;
        }
        LogS.e("TranscodeLib", "video decoder: codec config buffer");
        this.mInputVideoDecoder.releaseOutputBuffer(i, false);
        return false;
    }

    private void checkSkipFrames(long j) {
        if (this.mSkipFrames && this.mSkippedFramesCount % this.mFramesSkipInterval != 0) {
            LogS.d("TranscodeLib", "input surface: skip this frame: presentationTimeUs " + j);
        } else {
            this.mSkippedFramesCount = 0;
            this.mInputSurface.setPresentationTime(j * 1000);
            this.mInputSurface.swapBuffers();
            this.mVideoFramesWritten++;
        }
        this.mSkippedFramesCount++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getVideoDecoderOutput(int i, MediaCodec.BufferInfo bufferInfo) {
        if (checkDecoderOutputBufferIndex(i, bufferInfo)) {
            final DecodedFrame decodedFrame = new DecodedFrame(i, bufferInfo);
            Optional.ofNullable(this.mDecoderReleaseListener).ifPresent(new Consumer() { // from class: com.samsung.android.transcode.core.EncodeBase$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DecoderReleaseListener) obj).notifyFrameDecoded(decodedFrame);
                }
            });
        }
    }

    private boolean releaseOutputBufferOfVideoDecoder(DecodedFrame decodedFrame) {
        this.mInputVideoDecoder.releaseOutputBuffer(decodedFrame.bufferIndex, decodedFrame.size != 0);
        return decodedFrame.size != 0;
    }

    protected void sendFrametoEncoder() {
        DecodedFrame decodedFrameDequeueFrame;
        if (isVideoEncoderAvailableCondition()) {
            int i = 0;
            while (this.mDecoderFrameManager.queSize() > 0 && i < 3 && !this.mUserStop && (decodedFrameDequeueFrame = this.mDecoderFrameManager.dequeueFrame()) != null) {
                i++;
                if (releaseOutputBufferOfVideoDecoder(decodedFrameDequeueFrame)) {
                    try {
                        if (!this.mOutputSurface.checkForNewImage(1000)) {
                            LogS.e("TranscodeLib", "video decoder: checkForNewImage return false!!  mUserStop : " + this.mUserStop);
                        }
                        GLES20.glClear(16384);
                        this.mOutputSurface.drawImage();
                        if (decodedFrameDequeueFrame.presentationTimeUs >= this.mOriginTrimStartUs) {
                            checkSkipFrames(decodedFrameDequeueFrame.presentationTimeUs);
                        }
                    } catch (RuntimeException e) {
                        String message = e.getMessage();
                        if (!this.mUserStop || message == null || !message.equals(OutputSurface.EXCEPTION_FRAME_NOT_AVAILABLE)) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if ((decodedFrameDequeueFrame.flags & 4) != 0) {
                    LogS.e("TranscodeLib", "video decoder: EOS");
                    this.mVideoDecoderDone = true;
                    this.mOutputVideoEncoder.signalEndOfInputStream();
                    return;
                }
            }
        }
    }

    private long checkTimeDelta(long j, float f, boolean z, boolean z2, int i) {
        double d;
        if (z) {
            d = j + ((f - 1.0d) * (this.mRegionList.get(i).mRegionEndTime - this.mRegionList.get(i).mRegionStartTime) * 1000.0d);
        } else {
            if (!z2) {
                return j;
            }
            d = j - (((1.0d - f) * 1000.0d) * (this.mRegionList.get(i).mRegionEndTime - this.mRegionList.get(i).mRegionStartTime));
        }
        return (long) d;
    }

    /* renamed from: com.samsung.android.transcode.core.EncodeBase$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed;

        static {
            int[] iArr = new int[SEFHelper.Speed.values().length];
            $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed = iArr;
            try {
                iArr[SEFHelper.Speed.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.TWO_TIMES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.FOUR_TIMES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.EIGHT_TIMES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.SIXTEEN_TIMES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.THIRTY_TWO_TIMES.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.HALF.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.ONE_FOURTH.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[SEFHelper.Speed.ONE_EIGHTH.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean checkRetDropFastMotion(boolean z, int i, int i2, int i3, int i4, SEFHelper.Speed speed) {
        if (isSlow120(i, i2)) {
            int i5 = AnonymousClass3.$SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[speed.ordinal()];
            if (i5 != 1 && i5 != 2) {
                if (i5 != 3) {
                    if (i5 == 4) {
                        if (checkLayerCondition(i3, i4, 2)) {
                            return true;
                        }
                    } else {
                        LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!!!");
                        return z;
                    }
                } else if (i3 == i4 - 1) {
                    return true;
                }
            }
        } else {
            switch (AnonymousClass3.$SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[speed.ordinal()]) {
                case 1:
                    break;
                case 2:
                    if (i3 == i4 - 1) {
                        return true;
                    }
                    break;
                case 3:
                    if (checkLayerCondition(i3, i4, 2)) {
                        return true;
                    }
                    break;
                case 4:
                    if (checkLayerCondition(i3, i4, 3)) {
                        return true;
                    }
                    break;
                case 5:
                    if (checkLayerCondition(i3, i4, 4)) {
                        return true;
                    }
                    break;
                case 6:
                    if (checkLayerCondition(i3, i4, 5)) {
                        return true;
                    }
                    break;
                default:
                    LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!!!");
                    return z;
            }
        }
        return z;
    }

    private boolean checkRetDropSlowMotion(boolean z, int i, int i2, int i3, SEFHelper.Speed speed) {
        if (i < 230) {
            if (i >= 110) {
                if (i2 > 0 && checkLayerCondition(i2, i3, 2)) {
                    z = true;
                }
                int i4 = AnonymousClass3.$SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[speed.ordinal()];
                if (i4 != 1) {
                    if (i4 != 7) {
                        if (i4 != 8 && i4 != 9) {
                            LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!!");
                            return z;
                        }
                        if (checkLayerCondition(i2, i3, 2)) {
                            return false;
                        }
                    } else if (i2 == i3 - 2) {
                        return false;
                    }
                }
            }
            return z;
        }
        if (i2 > 0 && checkLayerCondition(i2, i3, 3)) {
            z = true;
        }
        int i5 = AnonymousClass3.$SwitchMap$com$samsung$android$transcode$util$SEFHelper$Speed[speed.ordinal()];
        if (i5 != 1) {
            if (i5 != 7) {
                if (i5 != 8) {
                    if (i5 != 9) {
                        LogS.d("TranscodeLib", "ProcSVCLayerDrop Should not be here!");
                        return z;
                    }
                    if (checkLayerCondition(i2, i3, 3)) {
                        return false;
                    }
                } else if (i2 == i3 - 3 || i2 == i3 - 2) {
                    return false;
                }
            } else if (i2 == i3 - 3) {
                return false;
            }
        }
        return z;
    }

    private boolean isRegionListExist() {
        List<SEFHelper.Region> list = this.mRegionList;
        return (list == null || list.isEmpty()) ? false : true;
    }

    private boolean keepPrevPFrameForSlowVideo(boolean z, int i, long j, int i2, int i3) {
        double d = i2;
        double d2 = (1.45d / d) * 1000000.0d;
        if ((this.mRegionList.get(i).mRegionStartTime * 1000) - j < ((long) (((1.0d / d) * 1000000.0d * 2.0d) + d2))) {
            if (i3 == 1) {
                return true;
            }
            if (i3 == 2 && (this.mRegionList.get(i).mRegionStartTime * 1000) - j < ((long) d2)) {
                return true;
            }
        }
        return z;
    }

    private boolean keepPrevPFrameForFastVideo(boolean z, int i, long j, int i2, int i3) {
        if ((this.mRegionList.get(i).mRegionEndTime * 1000) - j > 0) {
            double d = i2;
            double d2 = (1.45d / d) * 1000000.0d;
            double d3 = (1.0d / d) * 1000000.0d;
            if ((this.mRegionList.get(i).mRegionEndTime * 1000) - j < (14.0d * d3) + d2) {
                if (i3 == 1) {
                    return true;
                }
                if (i3 == 2 && (this.mRegionList.get(i).mRegionEndTime * 1000) - j < (6.0d * d3) + d2) {
                    return true;
                }
                if (i3 == 3 && (this.mRegionList.get(i).mRegionEndTime * 1000) - j < (d3 * 2.0d) + d2) {
                    return true;
                }
                if (i3 == 4 && (this.mRegionList.get(i).mRegionEndTime * 1000) - j < d2) {
                    return true;
                }
            }
        }
        return z;
    }

    private boolean procSuperSlowVideo(long j, int i, int i2) {
        boolean z;
        long timeScale;
        int i3;
        EncodeBase encodeBase;
        int i4;
        long j2 = j;
        LogS.d("TranscodeLib", "[procSuperSlowVideo]SampleTime = tempSampleTime = " + j2);
        boolean zKeepPrevPFrameForFastVideo = false;
        if (isRegionListExist()) {
            long timeScale2 = 0;
            int i5 = 0;
            while (true) {
                if (i5 >= this.mRegionList.size()) {
                    break;
                }
                if (j2 >= this.mRegionList.get(i5).mRegionStartTime * 1000 && j2 < this.mRegionList.get(i5).mRegionEndTime * 1000) {
                    if (this.mRegionList.get(i5).mRegionSpeed == 9) {
                        if (i2 == 0) {
                            i3 = 30;
                            i4 = i;
                            encodeBase = this;
                        } else {
                            i3 = i2;
                            encodeBase = this;
                            i4 = i;
                        }
                        zKeepPrevPFrameForFastVideo = encodeBase.keepPrevPFrameForFastVideo(false, i5, j2, i3, i4);
                        timeScale = (this.mRegionList.get(i5).mRegionStartTime * 1000) + (((j - (this.mRegionList.get(i5).mRegionStartTime * 1000)) * ((long) (SEFHelper.getTimeScale(this.mRegionList.get(i5).mRegionSpeedType) * 1000000.0f))) / 1000000);
                        z = true;
                    }
                } else {
                    if (j >= this.mRegionList.get(i5).mRegionEndTime * 1000 && this.mRegionList.get(i5).mRegionSpeed == 9) {
                        timeScale2 = (long) (timeScale2 - (((1.0d - SEFHelper.getTimeScale(this.mRegionList.get(i5).mRegionSpeedType)) * 1000.0d) * (this.mRegionList.get(i5).mRegionEndTime - this.mRegionList.get(i5).mRegionStartTime)));
                    }
                    i5++;
                    j2 = j;
                }
            }
            timeScale = j;
            z = false;
            this.mModifiedVideotime = timeScale + timeScale2;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        boolean z2 = i != 0;
        if (zKeepPrevPFrameForFastVideo) {
            return false;
        }
        return z2;
    }

    private boolean procSVCLayerDrop(long j, int i, int i2, int i3, int i4) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean zKeepPrevPFrameForSlowVideo;
        SEFHelper.Speed speed;
        long j2;
        long j3;
        int i5;
        boolean z5;
        int i6;
        int i7;
        long j4;
        boolean z6;
        int i8;
        int i9;
        EncodeBase encodeBase;
        int i10;
        long j5;
        EncodeBase encodeBase2 = this;
        SEFHelper.Speed speed2 = SEFHelper.Speed.NORMAL;
        int i11 = i4;
        long j6 = encodeBase2.isSlow120(i3, i11) ? 2 * j : j;
        StringBuilder sb = new StringBuilder("[procSVCLayerDrop]SampleTime = tempSampleTime = ");
        sb.append(j6);
        sb.append(",layernum :");
        int i12 = i;
        sb.append(i12);
        sb.append(", maxlayernum : ");
        sb.append(i2);
        LogS.d("TranscodeLib", sb.toString());
        if (encodeBase2.isRegionListExist()) {
            long jCheckTimeDelta = 0;
            z = true;
            boolean z7 = false;
            zKeepPrevPFrameForSlowVideo = false;
            SEFHelper.Speed speed3 = speed2;
            int i13 = i11;
            boolean z8 = false;
            int i14 = 0;
            while (true) {
                if (i14 >= encodeBase2.mRegionList.size()) {
                    z2 = false;
                    z4 = z8;
                    j2 = j6;
                    j3 = jCheckTimeDelta;
                    i5 = i13;
                    break;
                }
                if (SEFHelper.getTimeScale(encodeBase2.mRegionList.get(i14).mRegionSpeedType) > 1.0f) {
                    z8 = true;
                    z7 = false;
                }
                if (SEFHelper.getTimeScale(encodeBase2.mRegionList.get(i14).mRegionSpeedType) < 1.0f) {
                    z5 = true;
                    z4 = false;
                } else {
                    z4 = z8;
                    z5 = z7;
                }
                if (z4) {
                    i6 = i13;
                    z2 = false;
                    if (j6 < encodeBase2.mRegionList.get(i14).mRegionStartTime * 1000) {
                        int i15 = i6 == 0 ? 240 : i6;
                        zKeepPrevPFrameForSlowVideo = encodeBase2.keepPrevPFrameForSlowVideo(zKeepPrevPFrameForSlowVideo, i14, j6, i15, i12);
                        i7 = i14;
                        i8 = i15;
                        z7 = z5;
                        j4 = j6;
                        z6 = z4;
                    }
                    i14 = i7 + 1;
                    i13 = i8;
                    z8 = z6;
                    j6 = j4;
                    i12 = i;
                } else {
                    i6 = i13;
                    z2 = false;
                }
                boolean z9 = zKeepPrevPFrameForSlowVideo;
                if (j6 < encodeBase2.mRegionList.get(i14).mRegionStartTime * 1000 || j6 >= encodeBase2.mRegionList.get(i14).mRegionEndTime * 1000) {
                    zKeepPrevPFrameForSlowVideo = z9;
                    i7 = i14;
                    j4 = j6;
                    EncodeBase encodeBase3 = encodeBase2;
                    if (j4 >= encodeBase3.mRegionList.get(i7).mRegionEndTime * 1000) {
                        encodeBase2 = encodeBase3;
                        z6 = z4;
                        z7 = z5;
                        jCheckTimeDelta = encodeBase2.checkTimeDelta(jCheckTimeDelta, SEFHelper.getTimeScale(encodeBase3.mRegionList.get(i7).mRegionSpeedType), z6, z7, i7);
                        speed3 = SEFHelper.Speed.NORMAL;
                    } else {
                        encodeBase2 = encodeBase3;
                        z6 = z4;
                        z7 = z5;
                    }
                    i8 = i6;
                    i14 = i7 + 1;
                    i13 = i8;
                    z8 = z6;
                    j6 = j4;
                    i12 = i;
                } else {
                    if (z5) {
                        int i16 = i6 == 0 ? 30 : i6;
                        boolean zKeepPrevPFrameForFastVideo = encodeBase2.keepPrevPFrameForFastVideo(z9, i14, j6, i16, i);
                        i9 = i14;
                        encodeBase = encodeBase2;
                        i10 = i16;
                        j5 = j6;
                        zKeepPrevPFrameForSlowVideo = zKeepPrevPFrameForFastVideo;
                    } else {
                        i9 = i14;
                        encodeBase = encodeBase2;
                        i10 = i6;
                        zKeepPrevPFrameForSlowVideo = z9;
                        j5 = j6;
                    }
                    SEFHelper.Speed speed4 = encodeBase.mRegionList.get(i9).mRegionSpeedType;
                    float timeScale = SEFHelper.getTimeScale(speed4);
                    int i17 = i10;
                    speed3 = speed4;
                    encodeBase2 = encodeBase;
                    z7 = z5;
                    i5 = i17;
                    j2 = (encodeBase.mRegionList.get(i9).mRegionStartTime * 1000) + (((j5 - (encodeBase.mRegionList.get(i9).mRegionStartTime * 1000)) * ((long) (timeScale * 1000000.0f))) / 1000000);
                    j3 = jCheckTimeDelta;
                }
            }
            encodeBase2.mModifiedVideotime = j2 + j3;
            i11 = i5;
            z3 = z7;
            speed = speed3;
        } else {
            z = true;
            z2 = false;
            z3 = false;
            z4 = false;
            zKeepPrevPFrameForSlowVideo = false;
            speed = speed2;
        }
        boolean z10 = false;
        if (z4) {
            z10 = (zKeepPrevPFrameForSlowVideo || !encodeBase2.checkRetDropSlowMotion(false, i11, i, i2, speed)) ? z2 : z;
        }
        return z3 ? (zKeepPrevPFrameForSlowVideo || !checkRetDropFastMotion(z10, i3, i11, i, i2, speed)) ? z2 : z : z10;
    }

    private int getLayerNumber(byte[] bArr) {
        int i = 3;
        int i2 = 0;
        int i3 = 1;
        if (isNoneSVC()) {
            if (this.mRecordingMode == 18) {
                int i4 = this.mFramesCount;
                i = i4 % 16;
                this.mFramesCount = i4 + 1;
            } else {
                this.mNumOfSVCLayers = 4;
                int i5 = this.mFramesCount + 1;
                this.mFramesCount = i5;
                int i6 = this.mLayer2Count;
                if ((i6 * 4) + 3 == i5) {
                    this.mLayer2Count = i6 + 1;
                    i = 2;
                } else if (i5 % 8 == 1) {
                    i = 0;
                } else if (i5 % 2 != 0) {
                    i = 1;
                }
            }
            LogS.d("TranscodeLib", "get NONE SVC layerNumber: " + i);
            i3 = i;
        } else if (CodecsHelper.isHevcFormat(mInputVideoinfo)) {
            int i7 = (bArr[0] << 8) | (bArr[1] & 255);
            int i8 = (i7 >> 9) & 63;
            if (i8 >= 2 && i8 <= 5) {
                i2 = (i7 & 7) - 1;
            }
            LogS.d("TranscodeLib", "get SVC layerNumber of HEVC: " + i2);
            i3 = i2;
        } else if ((bArr[0] & SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN) == 14 && (bArr[1] & 128) == 128) {
            i3 = (bArr[3] & MidiConstants.STATUS_PITCH_BEND) >>> 5;
            LogS.d("TranscodeLib", "get SVC layerNumber: " + i3);
        }
        LogS.d("TranscodeLib", "[getlayernum] layerNumber = " + i3);
        return i3;
    }

    private boolean isNoneSVC() {
        int i = this.mRecordingMode;
        return i == 18 || i == 15 || i == 19;
    }

    private boolean isSlowFastExceptSlowV2120NoneSVC() {
        int i = this.mRecordingMode;
        return i == 2 || i == 1 || i == 12 || i == 21 || i == 13;
    }

    protected boolean isSuperSlow() {
        int i = this.mRecordingMode;
        return i == 8 || i == 7 || i == 9 || i == 22 || i == 18;
    }

    protected boolean calculateIsDrop(byte[] bArr, long j) {
        int i;
        int layerNumber = getLayerNumber(bArr);
        if (isSlowFastExceptSlowV2120NoneSVC() || (i = this.mRecordingMode) == 15 || i == 19) {
            boolean zProcSVCLayerDrop = procSVCLayerDrop(j, layerNumber, this.mNumOfSVCLayers, this.mRecordingMode, this.mRecordingFps);
            LogS.d("TranscodeLib", "layerNumber: " + layerNumber + ", isDrop: " + this.mIsDrop + ", mModifiedVideotime: " + this.mModifiedVideotime);
            return zProcSVCLayerDrop;
        }
        if (isSuperSlow()) {
            boolean zProcSuperSlowVideo = procSuperSlowVideo(j, layerNumber, this.mRecordingFps);
            LogS.d("TranscodeLib", "isDrop: " + this.mIsDrop + " ,mModifiedVideotime: " + this.mModifiedVideotime);
            return zProcSuperSlowVideo;
        }
        LogS.d("TranscodeLib", "Need to check recording mode and SEF data");
        return false;
    }

    protected void sendVideoToDecoder(int i) throws MediaCodec.CryptoException {
        ByteBuffer inputBuffer = this.mInputVideoDecoder.getInputBuffer(i);
        int sampleData = this.mVideoExtractor.readSampleData(inputBuffer, 0);
        long sampleTime = this.mVideoExtractor.getSampleTime();
        this.mIsDrop = false;
        this.mModifiedVideotime = sampleTime;
        if (this.mSEFVideo) {
            byte[] bArr = new byte[4];
            inputBuffer.position(4);
            inputBuffer.get(bArr, 0, 4);
            inputBuffer.position(0);
            calculateIsDrop(bArr, sampleTime);
        }
        if (this.mIsDrop) {
            inputBuffer.clear();
        }
        pushSampleDataToDecoderInputBuffer(i, sampleData, this.mModifiedVideotime, this.mIsDrop);
    }

    protected void pushSampleDataToDecoderInputBuffer(int i, int i2, long j, boolean z) throws MediaCodec.CryptoException {
        if (j <= this.mOriginTrimEndUs && i2 >= 0) {
            if (!z) {
                this.mInputVideoDecoder.queueInputBuffer(i, 0, i2, j, this.mVideoExtractor.getSampleFlags());
            } else {
                this.mInputVideoDecoder.queueInputBuffer(i, 0, 0, 0L, this.mVideoExtractor.getSampleFlags());
            }
            this.mVideoExtractor.advance();
            return;
        }
        LogS.e("TranscodeLib", "video extractor: EOS ");
        this.mInputVideoDecoder.queueInputBuffer(i, 0, 0, 0L, 4);
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
        this.mOutputVideoEncoder.setCallback(new MediaCodec.Callback() { // from class: com.samsung.android.transcode.core.EncodeBase.1
            @Override // android.media.MediaCodec.Callback
            public void onInputBufferAvailable(MediaCodec mediaCodec, int i) {
            }

            @Override // android.media.MediaCodec.Callback
            public void onOutputBufferAvailable(MediaCodec mediaCodec, int i, MediaCodec.BufferInfo bufferInfo) {
                if (EncodeBase.this.mAsyncCodecReleased[ASYNC_CODEC_TYPE.VIDEO_ENCODER.ordinal()]) {
                    return;
                }
                if (EncodeBase.this.isVideoEncoderAvailableCondition()) {
                    EncodeBase.this.sendVideoFramesToMuxer(i, bufferInfo);
                } else {
                    LogS.e("TranscodeLib", "video encoder: [onOutputBufferAvailable] condition error");
                }
            }

            @Override // android.media.MediaCodec.Callback
            public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
                LogS.e("TranscodeLib", "video encoder: has error");
                EncodeBase.this.mCodecError = true;
                synchronized (EncodeBase.this.mOutputVideoEncoder) {
                    EncodeBase.this.mOutputVideoEncoder.notifyAll();
                }
            }

            @Override // android.media.MediaCodec.Callback
            public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
                if (EncodeBase.this.mCodecError || EncodeBase.this.mAsyncCodecReleased[ASYNC_CODEC_TYPE.VIDEO_ENCODER.ordinal()]) {
                    return;
                }
                EncodeBase.this.mVideoEncoderOutputMediaFormat = mediaFormat;
                LogS.e("TranscodeLib", "video encoder: onOutputFormatChanged " + EncodeBase.this.mVideoEncoderOutputMediaFormat);
                EncodeBase.this.checkMuxerStart();
            }
        });
    }

    protected void setVideoDecoderAsyncCallback() {
        this.mInputVideoDecoder.setCallback(new MediaCodec.Callback() { // from class: com.samsung.android.transcode.core.EncodeBase.2
            @Override // android.media.MediaCodec.Callback
            public void onInputBufferAvailable(MediaCodec mediaCodec, int i) throws MediaCodec.CryptoException {
                if (EncodeBase.this.mAsyncCodecReleased[ASYNC_CODEC_TYPE.VIDEO_DECODER.ordinal()] || !EncodeBase.this.isVideoDecoderAvailableCondition()) {
                    return;
                }
                EncodeBase.this.sendVideoToDecoder(i);
            }

            @Override // android.media.MediaCodec.Callback
            public void onOutputBufferAvailable(MediaCodec mediaCodec, int i, MediaCodec.BufferInfo bufferInfo) {
                if (EncodeBase.this.mAsyncCodecReleased[ASYNC_CODEC_TYPE.VIDEO_DECODER.ordinal()] || !EncodeBase.this.isVideoDecoderAvailableCondition()) {
                    return;
                }
                EncodeBase.this.getVideoDecoderOutput(i, bufferInfo);
            }

            @Override // android.media.MediaCodec.Callback
            public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
                LogS.e("TranscodeLib", "video Decoder has error");
                EncodeBase.this.mCodecError = true;
                synchronized (EncodeBase.this.mInputVideoDecoder) {
                    EncodeBase.this.mInputVideoDecoder.notifyAll();
                }
            }

            @Override // android.media.MediaCodec.Callback
            public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
                LogS.e("TranscodeLib", "video decoder: onOutputFormatChanged " + mediaFormat);
            }
        });
    }

    protected void initialize_audio() {
        this.mAudioEncoderOutputBuffers = this.mCopyAudio ? this.mOutputAudioEncoder.getOutputBuffers() : null;
        this.mAudioEncoderInputBuffers = this.mCopyAudio ? this.mOutputAudioEncoder.getInputBuffers() : null;
        this.mAudioDecoderOutputBuffers = this.mCopyAudio ? this.mInputAudioDecoder.getOutputBuffers() : null;
        this.mAudioDecoderInputBuffers = this.mCopyAudio ? this.mInputAudioDecoder.getInputBuffers() : null;
        this.mAudioEncoderOutputBufferInfo = new MediaCodec.BufferInfo();
        this.mAudioDecoderOutputBufferInfo = new MediaCodec.BufferInfo();
        this.mAudioEncoderOutputMediaFormat = null;
        boolean z = this.mCopyAudio;
        this.mAudioExtractorDone = !z;
        this.mAudioDecoderDone = !z;
        this.mAudioEncoderDone = !z;
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
        LogS.i("TranscodeLib", "input video auth : " + this.mAuthor + ", recordingMode :" + this.mRecordingMode);
        if (this.mUseUri) {
            this.mVideoExtractor = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
        } else {
            this.mVideoExtractor = CodecsHelper.createExtractor(this.mInputFilePath);
        }
        MediaFormat trackFormat = this.mVideoExtractor.getTrackFormat(CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor));
        LogS.i("TranscodeLib", "input video format: " + trackFormat);
        if (this.mTrimVideoEndUs == 0) {
            long j = trackFormat.getLong(MediaFormat.KEY_DURATION);
            this.mTrimVideoEndUs = j;
            this.mOriginTrimEndUs = j;
            if (this.mSMConvert) {
                if (isSlowV2()) {
                    this.mOriginTrimEndUs = this.mSefhelper.getEditedDuration(mInputFileinfo.Duration * 1000);
                } else {
                    this.mOriginTrimEndUs = mInputFileinfo.EditedDuration * 1000;
                }
            }
            this.mTrimVideoStartUs = 0L;
            this.mOriginTrimStartUs = 0L;
            LogS.d("TranscodeLib", "mTrimVideoEndUs was 0 but updated  mTrimVideoEndUs : " + this.mTrimVideoEndUs + ", mOriginTrimEndUs : " + this.mOriginTrimEndUs);
        }
        this.mSourceFrameRate = 0;
        try {
            this.mSourceFrameRate = trackFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception unused) {
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

    private void setOrientation(int i) {
        int i2 = (i + this.mRotation) % 360;
        if (i2 == 90) {
            this.mInputOrientationDegrees = 90;
            return;
        }
        if (i2 == 180) {
            this.mInputOrientationDegrees = 180;
        } else if (i2 == 270) {
            this.mInputOrientationDegrees = 270;
        } else {
            this.mInputOrientationDegrees = 0;
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
        LogS.e("TranscodeLib", "mOutputVideoBitRate : " + this.mOutputVideoBitRate + ", mOutputAudioBitRate :" + this.mOutputAudioBitRate + ", mSourceFrameRate :" + this.mSourceFrameRate + ", mOutputVideoFrameRate :" + this.mOutputVideoFrameRate + ",mFramesSkipInterval: " + this.mFramesSkipInterval + ", mKeepSourceFrameRate : " + this.mKeepSourceFrameRate + ", mOutputVideoTargetFrameRate : " + this.mOutputVideoTargetFrameRate);
        MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(this.mOutputVideoMimeType, this.mOutputWidth, this.mOutputHeight);
        if (CodecsHelper.supportHierB() && this.mOutputVideoMimeType.equals("video/hevc") && isHLG()) {
            String lowerCase = SemSystemProperties.get("ro.hardware").toLowerCase();
            mediaFormatCreateVideoFormat.setString(MediaFormat.KEY_TEMPORAL_LAYERING, "android.generic.1+" + ((lowerCase == null || !lowerCase.equals("qcom")) ? 2 : 3));
            mediaFormatCreateVideoFormat.setInteger(MediaFormat.KEY_MAX_B_FRAMES, 1);
        }
        mediaFormatCreateVideoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        mediaFormatCreateVideoFormat.setInteger(MediaFormat.KEY_BIT_RATE, this.mOutputVideoBitRate);
        mediaFormatCreateVideoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, this.mOutputVideoFrameRate);
        mediaFormatCreateVideoFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, this.mOutputVideoIFrameInterval);
        mediaFormatCreateVideoFormat.setInteger("priority", 1);
        if (checkBitrateMode()) {
            mediaFormatCreateVideoFormat.setInteger(MediaFormat.KEY_BITRATE_MODE, 2);
        }
        if (!this.mMMSMode) {
            mediaFormatCreateVideoFormat.setInteger(MediaFormat.KEY_COLOR_STANDARD, 1);
        }
        LogS.e("TranscodeLib", "output video format " + mediaFormatCreateVideoFormat);
        this.mOutputVideoEncoder = MediaCodec.createEncoderByType(this.mOutputVideoMimeType);
        setVideoEncoderAsyncCallback();
        this.mOutputVideoEncoder.configure(mediaFormatCreateVideoFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mInputSurface = new InputSurface(this.mOutputVideoEncoder.createInputSurface());
        this.mOutputVideoEncoder.start();
        this.mInputSurface.makeCurrent();
    }

    protected void setOriginalTrimTime(long j) {
        this.mTrimVideoEndUs = j;
        this.mOriginTrimEndUs = j;
        if (this.mSEFVideo) {
            if (isSlowV2()) {
                this.mOriginTrimEndUs = this.mSefhelper.getEditedDuration(mInputFileinfo.Duration * 1000);
            } else {
                this.mOriginTrimEndUs = mInputFileinfo.EditedDuration * 1000;
            }
        }
        this.mTrimVideoStartUs = 0L;
        this.mOriginTrimStartUs = 0L;
        LogS.d("TranscodeLib", "mTrimVideoEndUs was 0 but updated  mTrimVideoEndUs : " + this.mTrimVideoEndUs + ", mOriginTrimEndUs : " + this.mOriginTrimEndUs);
    }

    private void checkOutputVideoFrameRate() {
        if (this.mOutputVideoTargetFrameRate > 0) {
            if (this.mOutputVideoTargetFrameRate >= this.mSourceFrameRate) {
                this.mOutputVideoFrameRate = this.mSourceFrameRate;
            } else {
                this.mFramesSkipInterval = (int) Math.ceil(this.mSourceFrameRate / this.mOutputVideoTargetFrameRate);
                if (this.mFramesSkipInterval > 1) {
                    this.mSkipFrames = true;
                }
                this.mOutputVideoFrameRate = this.mSourceFrameRate / this.mFramesSkipInterval;
            }
            LogS.d("TranscodeLib", "mSourceFrameRate : " + this.mSourceFrameRate + ", mOutputVideoTargetFrameRate : " + this.mOutputVideoTargetFrameRate + ", mOutputVideoFrameRate : " + this.mOutputVideoFrameRate);
            return;
        }
        if (this.mMMSMode) {
            this.mOutputVideoFrameRate = 10;
        }
        if (!this.mKeepSourceFrameRate && this.mSourceFrameRate >= this.mOutputVideoFrameRate * 2) {
            int i = this.mRecordingMode;
            if (i == 1 || i == 2) {
                if (i == 1 && this.mSourceFrameRate < 130) {
                    this.mFramesSkipInterval = 0;
                    this.mOutputVideoFrameRate = 15;
                } else {
                    this.mFramesSkipInterval = (int) Math.floor(30.0f / this.mOutputVideoFrameRate);
                    if (this.mFramesSkipInterval > 1) {
                        this.mOutputVideoFrameRate = 30 / this.mFramesSkipInterval;
                    }
                }
            } else {
                this.mFramesSkipInterval = (int) Math.floor(this.mSourceFrameRate / this.mOutputVideoFrameRate);
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
                LogS.d("TranscodeLib", "m2ndTimeEncoding case mOutputVideoFrameRate : " + this.mOutputVideoFrameRate);
                return;
            }
            this.mOutputVideoFrameRate = this.mSourceFrameRate;
        }
    }

    protected void checkSourceFrameRate(MediaFormat mediaFormat) {
        this.mSourceFrameRate = 0;
        try {
            this.mSourceFrameRate = mediaFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception unused) {
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
                this.mOutputVideoBitRate = CodecsHelper.getVideoEncodingBitRate(this.mSizeFraction, this.mOutputMaxSizeKB, (this.mOriginTrimEndUs - this.mOriginTrimStartUs) / 1000, this.mOutputAudioBitRate / 1000, this.mOutputWidth, this.mOutputHeight) * 1000;
                return;
            }
            return;
        }
        if (this.mOutputVideoBitRate == -1) {
            this.mOutputVideoBitRate = CodecsHelper.suggestBitrate(new ExportMediaInfo(this.mOutputWidth, this.mOutputHeight, this.mOutputVideoFrameRate, this.mOutputVideoMimeType, isHDR10Plus()), mInputFileinfo);
        }
    }

    protected void prepareVideoCodec() throws IOException {
        int i;
        int i2;
        int i3;
        int i4;
        createVideoExtractor();
        MediaFormat trackFormat = this.mVideoExtractor.getTrackFormat(CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor));
        if (supportConverter() && (this.mConvert || isHDR10() || isHLG())) {
            String lowerCase = SemSystemProperties.get("ro.hardware.chipname", SemSystemProperties.get("ro.soc.model").toLowerCase()).toLowerCase();
            if ("exynos2100".equals(lowerCase)) {
                trackFormat.setInteger("vendor.sec-dec-output.image-convert.value", 1);
                trackFormat.setInteger("vendor.sec-ext-imageformat-filter-enableInplace.value", 1);
                trackFormat.setInteger("vendor.sec-dec-output.buffers.usage.value", 1);
            } else if ("sm8350".equals(lowerCase)) {
                trackFormat.setInteger("vendor.qti-ext-dec-forceNonUBWC.value", 1);
                trackFormat.setInteger("vendor.qti-ext-imageformat-filter-enabled.value", 1);
                trackFormat.setInteger("vendor.qti-ext-imageformat-filter-enableInplace.value", 1);
            } else if ("sm8450".equals(lowerCase) || "s5e9925".equals(lowerCase)) {
                trackFormat.setInteger("vendor.renderengine-hdr-tonemap.value", 1);
            } else {
                trackFormat.setInteger(MediaFormat.KEY_COLOR_TRANSFER_REQUEST, 3);
            }
            setDecodeMaxInputSize(trackFormat);
        }
        LogS.e("TranscodeLib", "input video format: " + trackFormat);
        if (this.mTrimVideoEndUs == 0) {
            setOriginalTrimTime(trackFormat.getLong(MediaFormat.KEY_DURATION));
        }
        checkSourceFrameRate(trackFormat);
        createVideoEncoder();
        try {
            int integer = trackFormat.getInteger("width");
            int integer2 = trackFormat.getInteger("height");
            if (integer / integer2 > this.mOutputWidth / this.mOutputHeight) {
                i2 = this.mOutputWidth;
                i = (this.mOutputWidth * integer2) / integer;
                i4 = 0;
                i3 = (this.mOutputHeight - i) / 2;
            } else {
                i = this.mOutputHeight;
                i2 = (this.mOutputHeight * integer) / integer2;
                i3 = 0;
                i4 = (this.mOutputWidth - i2) / 2;
            }
            this.mOutputSurface = new OutputSurface(mInputFileinfo.Rotation, i4, i3, i2, i, integer, integer2, this.mMMSMode);
        } catch (Exception unused) {
            LogS.e("TranscodeLib", "Can't get input video resolution");
            this.mOutputSurface = new OutputSurface(mInputFileinfo.Rotation);
        }
        if (!this.mMMSMode) {
            trackFormat.setInteger("priority", 1);
        }
        this.mInputVideoDecoder = MediaCodec.createDecoderByType(trackFormat.getString("mime"));
        setVideoDecoderAsyncCallback();
        this.mInputVideoDecoder.configure(trackFormat, this.mOutputSurface.getSurface(), (MediaCrypto) null, 0);
    }

    private void setDecodeMaxInputSize(MediaFormat mediaFormat) {
        if (mediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE, -1) == -1) {
            mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, mediaFormat.getInteger("width") * mediaFormat.getInteger("height") > 8294400 ? SurfaceControl.NO_REMOTECONTROL : 8388608);
        }
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
        Uri uri;
        if (this.mUseUri) {
            Context context = this.mContext;
            if (context == null || (uri = this.mInputUri) == null) {
                throw new IOException("mInputUri or mContext  is NULL");
            }
            this.mAudioExtractor = CodecsHelper.createExtractor(context, uri);
            return;
        }
        String str = this.mInputFilePath;
        if (str == null) {
            throw new IOException("mInputFilePath is NULL");
        }
        this.mAudioExtractor = CodecsHelper.createExtractor(str);
    }

    private boolean checkCopyAudio(MediaFormat mediaFormat) {
        int integer;
        if ("audio/unknown".equals(mediaFormat.getString("mime"))) {
            LogS.d("TranscodeLib", "Audio mime type is unknown. Ignore audio track.");
            return false;
        }
        if (mediaFormat.containsKey("error-type") && (integer = mediaFormat.getInteger("error-type")) != 0) {
            LogS.d("TranscodeLib", "Audio codec error appear : " + integer);
            return false;
        }
        if (MediaInfoChecker.isSupportedCodecType(mediaFormat)) {
            return true;
        }
        LogS.d("TranscodeLib", "Audio codec type is unsupported. Ignore audio track.");
        return false;
    }

    protected void checkTrimAudioEndUs(MediaFormat mediaFormat) {
        if (this.mTrimAudioEndUs == 0) {
            if (mediaFormat != null) {
                this.mTrimAudioEndUs = mediaFormat.getLong(MediaFormat.KEY_DURATION);
            }
            LogS.d("TranscodeLib", "mTrimAudioEndUs was 0 but updated mTrimAudioEndUs :" + this.mTrimAudioEndUs + ", mOriginTrimEndUs:" + this.mOriginTrimEndUs);
        }
    }

    private boolean checkAudioDecoderBufferIndex(int i, ByteBuffer[] byteBufferArr) throws MediaCodec.CryptoException {
        if (i == -1) {
            LogS.d("TranscodeLib", "audio decoder input try again later while preparing audio codec");
            return false;
        }
        int sampleData = this.mAudioExtractor.readSampleData(byteBufferArr[i], 0);
        long sampleTime = this.mAudioExtractor.getSampleTime();
        if (sampleData > 0) {
            this.mInputAudioDecoder.queueInputBuffer(i, 0, sampleData, sampleTime, this.mAudioExtractor.getSampleFlags());
        } else if (sampleData == -1) {
            this.mCopyAudio = false;
            this.formatupdated = true;
            LogS.d("TranscodeLib", "Audio buffer is empty, size :" + sampleData);
        }
        return false;
    }

    private boolean checkPendingAudioDecoderBufferIndex(int i, String str) {
        if (i == -1) {
            LogS.d("TranscodeLib", "audio decoder output buffer try again later while preparing audio codec");
            return false;
        }
        if (i == -3) {
            LogS.d("TranscodeLib", "audio decoder: output buffers changed ");
            return false;
        }
        if (i != -2) {
            if (i >= 0) {
                return true;
            }
            LogS.d("TranscodeLib", "Unexpected result from audio decoder dequeue output format.");
            return false;
        }
        this.mOutputAudioSampleRateHZ = this.mInputAudioDecoder.getOutputFormat().getInteger(MediaFormat.KEY_SAMPLE_RATE);
        this.mOutputAudioChannelCount = this.mInputAudioDecoder.getOutputFormat().getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        if (isDolbyAudioCodec(str)) {
            setStereoAudioChannelForDolbyAudioCodec();
        }
        LogS.e("TranscodeLib", "audio decoder: output format changed: SampleRate" + this.mOutputAudioSampleRateHZ + ",ChannelCount" + this.mOutputAudioChannelCount);
        this.formatupdated = true;
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
        int andSelectAudioTrackIndex = CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor);
        if (andSelectAudioTrackIndex == -1 || this.mOutputAudioMute) {
            this.mCopyAudio = false;
            return;
        }
        MediaFormat trackFormat = this.mAudioExtractor.getTrackFormat(andSelectAudioTrackIndex);
        if (!checkCopyAudio(trackFormat)) {
            this.mCopyAudio = false;
            return;
        }
        this.mCopyAudio = true;
        checkTrimAudioEndUs(trackFormat);
        LogS.e("TranscodeLib", "Audio input format " + trackFormat);
        this.mOutputAudioSampleRateHZ = trackFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        this.mOutputAudioChannelCount = trackFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        String string = trackFormat.getString("mime");
        if ("audio/mp4a-latm".equals(string) || isDolbyAudioCodec(string)) {
            preprocessAudioOutputFormat(string, trackFormat);
        }
        int maxInputSize = getMaxInputSize(trackFormat);
        if (checkAudioChannelCount()) {
            createAudioHandle();
            MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(this.mOutputAudioMimeType, this.mOutputAudioSampleRateHZ, this.mOutputAudioChannelCount);
            if (maxInputSize != 0) {
                mediaFormatCreateAudioFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize);
            }
            mediaFormatCreateAudioFormat.setInteger(MediaFormat.KEY_BIT_RATE, this.mOutputAudioBitRate);
            mediaFormatCreateAudioFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, this.mOutputAudioAACProfile);
            LogS.e("TranscodeLib", "Audio output format " + mediaFormatCreateAudioFormat);
            this.mOutputAudioEncoder = CodecsHelper.createAudioEncoder(CodecsHelper.getEncoderCodec(this.mOutputAudioMimeType), mediaFormatCreateAudioFormat);
            createInputAudioDecoder(string, trackFormat);
        }
    }

    private void preprocessAudioOutputFormat(String str, MediaFormat mediaFormat) throws IOException {
        try {
            this.mInputAudioDecoder = CodecsHelper.createAudioDecoder(CodecsHelper.getDecoderCodec(str), mediaFormat);
            ByteBuffer[] inputBuffers = this.mCopyAudio ? this.mInputAudioDecoder.getInputBuffers() : null;
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            Runnable runnable = new Runnable() { // from class: com.samsung.android.transcode.core.EncodeBase$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m9629x8623e4ae();
                }
            };
            int i = -1;
            while (!this.formatupdated) {
                while (!this.formatupdated && checkAudioDecoderBufferIndex(this.mInputAudioDecoder.dequeueInputBuffer(10000L), inputBuffers)) {
                }
                CodecsHelper.scheduleAfter(3, runnable);
                if (!this.formatupdated && i == -1) {
                    int iDequeueOutputBuffer = this.mInputAudioDecoder.dequeueOutputBuffer(bufferInfo, 10000L);
                    if (checkPendingAudioDecoderBufferIndex(iDequeueOutputBuffer, str)) {
                        if ((bufferInfo.flags & 2) != 0) {
                            LogS.d("TranscodeLib", "audio decoder: codec config buffer");
                            this.mInputAudioDecoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
                        } else {
                            i = iDequeueOutputBuffer;
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
    /* synthetic */ void m9629x8623e4ae() {
        this.formatupdated = true;
    }

    private boolean isDolbyAudioCodec(String str) {
        return MediaFormat.MIMETYPE_AUDIO_AC3.equals(str) || MediaFormat.MIMETYPE_AUDIO_EAC3.equals(str) || MediaFormat.MIMETYPE_AUDIO_EAC3_JOC.equals(str) || MediaFormat.MIMETYPE_AUDIO_AC4.equals(str);
    }

    protected boolean isSlowV2() {
        int i = this.mRecordingMode;
        return i == 12 || i == 21 || i == 13 || i == 15 || i == 19;
    }

    private void createAudioHandle() {
        if (this.mAudio != null) {
            int i = this.mRecordingMode;
            if (i == 1 || i == 2) {
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
                sNAACHandle = this.mAudio.NAACEncoderInit(this.mOutputAudioChannelCount, this.mOutputAudioSampleRateHZ);
            }
        }
    }

    private int getMaxInputSize(MediaFormat mediaFormat) {
        try {
            return mediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
        } catch (NullPointerException unused) {
            LogS.e("TranscodeLib", "Audio max input size not defined");
            return 0;
        }
    }

    private void createInputAudioDecoder(String str, MediaFormat mediaFormat) throws IOException {
        if (mediaFormat.containsKey(MediaFormat.KEY_ENCODER_DELAY)) {
            mediaFormat.setInteger(MediaFormat.KEY_ENCODER_DELAY, 0);
        }
        if ("audio/mp4a-latm".equals(str)) {
            this.mInputAudioDecoder = CodecsHelper.createAudioDecoder(CodecsHelper.getDecoderCodec(str), mediaFormat);
        } else {
            this.mInputAudioDecoder = CodecsHelper.createAudioDecoder(mediaFormat);
        }
    }

    protected void updateProgress(long j, boolean z) {
        long jMin;
        if (j <= 0) {
            return;
        }
        if (z) {
            this.mAudioProgressTime = j;
        } else {
            this.mVidioProgressTime = j;
        }
        if (this.mCopyAudio) {
            jMin = Math.min(this.mAudioProgressTime, this.mVidioProgressTime);
        } else {
            jMin = this.mVidioProgressTime;
        }
        long j2 = this.mOriginTrimStartUs;
        int iMax = Math.max(0, Math.min(100, (int) (((jMin - j2) * 100) / (this.mOriginTrimEndUs - j2))));
        if (this.mEncodeProgressListener == null || iMax <= this.mProgress) {
            return;
        }
        LogS.d("TranscodeLib", "updateProgress: audioProgressTime: " + this.mAudioProgressTime + ", vidioProgressTime: " + this.mVidioProgressTime + ", time : " + jMin + ", progress: " + iMax);
        this.mEncodeProgressListener.onProgressChanged(iMax);
        this.mProgress = iMax;
    }

    protected void releaseFramemanager() {
        if (this.mDecoderFrameManager != null) {
            while (this.mDecoderFrameManager.queSize() > 0) {
                DecodedFrame decodedFrameDequeueFrame = this.mDecoderFrameManager.dequeueFrame();
                if (decodedFrameDequeueFrame == null) {
                    return;
                }
                if (this.mInputVideoDecoder != null) {
                    releaseOutputBufferOfVideoDecoder(decodedFrameDequeueFrame);
                }
            }
        }
        this.mDecoderFrameManager = null;
        this.mDecoderReleaseListener = null;
    }

    private boolean checkBitrateMode() {
        return this.mOutputMaxSizeKB > 0 && this.mOutputWidth * this.mOutputHeight > 76800 && this.mOutputWidth * this.mOutputHeight <= 307200 && !this.mKeepSourceFrameRate;
    }

    protected boolean supportConverter() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HDR2SDR");
    }
}
