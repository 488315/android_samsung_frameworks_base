package com.samsung.android.transcode.core;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.text.TextUtils;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.transcode.core.EncodeBase;
import com.samsung.android.transcode.info.ExportMediaInfo;
import com.samsung.android.transcode.info.MediaInfo;
import com.samsung.android.transcode.info.MediaInfoChecker;
import com.samsung.android.transcode.util.AudioSolution;
import com.samsung.android.transcode.util.CodecsHelper;
import com.samsung.android.transcode.util.FileHelper;
import com.samsung.android.transcode.util.LogS;
import com.samsung.android.transcode.util.NalUnitParser;
import com.samsung.android.transcode.util.SEFHelper;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class EncodeVideo extends EncodeBase {
    private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    private static final String UNKNOWN_AUDIO = "audio/unknown";
    private final Object mStopLock = new Object();

    public void initialize(String outputFilePath, int outputWidth, int outputHeight, String inputFilePath) throws IOException {
        this.mUseUri = false;
        checkInitialize(outputFilePath, outputWidth, outputHeight, inputFilePath, null, null, false);
        createAudiosolution();
    }

    public void initialize(String outputFilePath, int outputWidth, int outputHeight, String inputFilePath, boolean isRewrite, boolean isSEFVideo) throws IOException {
        this.mUseUri = false;
        checkInitialize(outputFilePath, outputWidth, outputHeight, inputFilePath, null, null, isRewrite);
    }

    public void initialize(String outputFilePath, int outputWidth, int outputHeight, String inputFilePath, boolean isRewrite, boolean isSEFVideo, int inputFPS) throws IOException {
        this.mUseUri = false;
        checkInitialize(outputFilePath, outputWidth, outputHeight, inputFilePath, null, null, isRewrite);
        if (!isSEFVideo && inputFPS > 0) {
            this.mSourceFrameRate = inputFPS;
            this.mKeepSourceFrameRate = true;
        }
        if (this.mSMConvert && !isRewrite) {
            this.mSMConvert = false;
            this.mSMEncode = true;
            LogS.m473d("TranscodeLib", "Do not support rewrite for Photoring case mSMConvert : " + this.mSMConvert + ", mSMEncode: " + this.mSMEncode);
        }
        createAudiosolution();
    }

    public void initialize(String outputFilePath, int outputWidth, int outputHeight, Context context, Uri inputUri) throws IOException {
        this.mUseUri = true;
        checkInitialize(outputFilePath, outputWidth, outputHeight, null, context, inputUri, false);
        createAudiosolution();
    }

    public void initialize(String outputFilePath, int outputWidth, int outputHeight, Context context, Uri inputUri, boolean isRewrite, boolean isSEFVideo) throws IOException {
        this.mUseUri = true;
        checkInitialize(outputFilePath, outputWidth, outputHeight, null, context, inputUri, isRewrite);
    }

    public void initialize(String outputFilePath, int outputWidth, int outputHeight, Context context, Uri inputUri, boolean isRewrite, boolean isSEFVideo, int inputFPS) throws IOException {
        this.mUseUri = true;
        checkInitialize(outputFilePath, outputWidth, outputHeight, null, context, inputUri, isRewrite);
        if (!isSEFVideo && inputFPS > 0) {
            this.mSourceFrameRate = inputFPS;
            this.mKeepSourceFrameRate = true;
        }
        if (this.mSMConvert && !isRewrite) {
            this.mSMConvert = false;
            this.mSMEncode = true;
            LogS.m473d("TranscodeLib", "Do not support rewrite for Photoring case mSMConvert : " + this.mSMConvert + ", mSMEncode: " + this.mSMEncode);
        }
        createAudiosolution();
    }

    public void setTrimTime(long startMs, long endMs) {
        if (startMs < 0) {
            throw new IllegalArgumentException("start time cannot be negative");
        }
        if (endMs < 0) {
            throw new IllegalArgumentException("end time cannot be negative");
        }
        if (startMs > endMs) {
            throw new IllegalArgumentException("start time cannot be more than end time");
        }
        if (startMs == endMs) {
            throw new IllegalArgumentException("endUs cannot be equal to startUs");
        }
        this.mOriginTrimStartUs = startMs * 1000;
        this.mOriginTrimEndUs = endMs * 1000;
        if (this.mSEFVideo) {
            if (this.mRecordingMode == 1 || this.mRecordingMode == 2 || this.mRecordingMode == 12 || this.mRecordingMode == 21 || this.mRecordingMode == 19) {
                long slowfastSeektime = getSlowfastSeektime(startMs * 1000);
                this.mTrimAudioStartUs = slowfastSeektime;
                this.mTrimVideoStartUs = slowfastSeektime;
                long slowfastSeektime2 = getSlowfastSeektime(1000 * endMs);
                this.mTrimAudioEndUs = slowfastSeektime2;
                this.mTrimVideoEndUs = slowfastSeektime2;
            } else if (isSuperSlow()) {
                long superslowSeektime = getSuperslowSeektime(startMs * 1000);
                this.mTrimAudioStartUs = superslowSeektime;
                this.mTrimVideoStartUs = superslowSeektime;
                long superslowSeektime2 = getSuperslowSeektime(1000 * endMs);
                this.mTrimAudioEndUs = superslowSeektime2;
                this.mTrimVideoEndUs = superslowSeektime2;
            } else if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                this.mOriginTrimStartUs = startMs * 1000;
                this.mOriginTrimEndUs = endMs * 1000;
                long slowfastSeektime3 = getSlowfastSeektime(startMs * 1000) / 2;
                this.mTrimAudioStartUs = slowfastSeektime3;
                this.mTrimVideoStartUs = slowfastSeektime3;
                long slowfastSeektime4 = getSlowfastSeektime(1000 * endMs) / 2;
                this.mTrimAudioEndUs = slowfastSeektime4;
                this.mTrimVideoEndUs = slowfastSeektime4;
            }
        } else {
            long j = startMs * 1000;
            this.mTrimAudioStartUs = j;
            this.mTrimVideoStartUs = j;
            long j2 = 1000 * endMs;
            this.mTrimAudioEndUs = j2;
            this.mTrimVideoEndUs = j2;
        }
        LogS.m474e("TranscodeLib", "Trim startUS: " + this.mTrimVideoStartUs + ", endUS: " + this.mTrimVideoEndUs + ", OriginstartUS: " + this.mOriginTrimStartUs + ", OriginendUS :" + this.mOriginTrimEndUs);
    }

    public void setEncodingCodecs(int videoCodecType, int audioCodecType) {
        setVideoCodecs(videoCodecType);
        setAudioCodecs(audioCodecType);
    }

    public void setRotation(int rotation) {
        this.mRotation = rotation;
    }

    public void setVideoOutputBitRate(int bitRate) {
        if (bitRate <= 0) {
            throw new IllegalArgumentException("bitRate should be positive");
        }
        this.mOutputVideoBitRate = bitRate;
    }

    public void setVideoTargetFrameRate(int fps) {
        if (fps <= 0) {
            throw new IllegalArgumentException("fps should be positive");
        }
        this.mOutputVideoTargetFrameRate = fps;
    }

    public void setOutputAudioMute(int value) {
        LogS.m473d("TranscodeLib", "set audio mute : " + value);
        this.mOutputAudioMute = value != 0;
    }

    public void setAudioCodecs(int audioCodecType) {
        switch (audioCodecType) {
            case 1:
                this.mOutputAudioMimeType = "audio/3gpp";
                return;
            case 2:
                this.mOutputAudioMimeType = "audio/mp4a-latm";
                return;
            default:
                throw new IllegalArgumentException("Invalid audio codec");
        }
    }

    public void setVideoCodecs(int videoCodecType) {
        switch (videoCodecType) {
            case 3:
                this.mOutputVideoMimeType = "video/3gpp";
                return;
            case 4:
                this.mOutputVideoMimeType = "video/avc";
                return;
            case 5:
                this.mOutputVideoMimeType = "video/hevc";
                return;
            default:
                LogS.m474e("TranscodeLib", "videoCodecType is: " + videoCodecType);
                throw new IllegalArgumentException("Invalid video codec");
        }
    }

    public void setMaxOutputSize(int kilobytes) {
        if (kilobytes <= 0) {
            throw new IllegalArgumentException("size cannot be 0 or lesser");
        }
        LogS.m474e("TranscodeLib", "max output size is " + kilobytes);
        this.mOutputMaxSizeKB = kilobytes;
        this.mMMSMode = this.mOutputMaxSizeKB < 1000 || (this.mOutputWidth < 200 && this.mOutputHeight < 200);
        LogS.m474e("TranscodeLib", "mMMSMode is " + this.mMMSMode);
    }

    public void setOutputConfig(int configType, int value) {
        switch (configType) {
            case 1:
                setVideoCodecs(value);
                return;
            case 2:
                setAudioCodecs(value);
                return;
            case 3:
                setMaxOutputSize(value);
                return;
            case 4:
                setOutputBitdepth(value);
                return;
            case 5:
                setVideoOutputBitRate(value);
                return;
            case 6:
                setVideoTargetFrameRate(value);
                return;
            case 7:
                setOutputAudioMute(value);
                return;
            default:
                LogS.m474e("TranscodeLib", "configType is: " + configType);
                throw new IllegalArgumentException("Invalid config Type");
        }
    }

    private void checkTrimVideoStartPointChanged() {
        if (this.mTrimVideoStartUs != 0) {
            if (this.mRewritable) {
                long prevVideoStartTime = this.mTrimVideoStartUs;
                this.mVideoExtractor.seekTo(this.mTrimVideoStartUs, 0);
                this.mTrimVideoStartUs = this.mVideoExtractor.getSampleTime();
                this.mAudioExtractor.seekTo(this.mTrimVideoStartUs, 0);
                this.mTrimAudioStartUs = this.mAudioExtractor.getSampleTime();
                long prevEndTime = this.mTrimVideoEndUs;
                this.mTrimVideoEndUs -= prevVideoStartTime - this.mTrimVideoStartUs;
                LogS.m475i("TranscodeLib", "change end time for rewrite mode prev : " + prevEndTime + " new : " + this.mTrimVideoEndUs);
                this.mVideoEncoderDone = true;
                this.mAudioEncoderDone = true;
                LogS.m473d("TranscodeLib", "Abandon Rewrite. Switch to Rewrite mode.");
                return;
            }
            return;
        }
        this.mVideoEncoderDone = true;
        this.mAudioEncoderDone = true;
        LogS.m473d("TranscodeLib", "Start point has not been updated!");
    }

    private void checkAudioTranscodeSection() {
        if ((!this.mVideoEncoderDone || !this.mAudioEncoderDone) && this.mCopyAudio && this.mTrimAudioStartUs != 0) {
            this.mAudioExtractor.seekTo(this.mTrimAudioStartUs, 0);
            while (this.mAudioExtractor.getSampleTime() < this.mTrimAudioStartUs) {
                LogS.m473d("TranscodeLib", "Advance audio...");
                this.mAudioExtractor.advance();
            }
            LogS.m473d("TranscodeLib", "Audio Transcode section: Current position: " + this.mAudioExtractor.getSampleTime() + " mTrimAudioStartUs: " + this.mTrimAudioStartUs);
        }
    }

    @Override // com.samsung.android.transcode.core.Encode
    public void startRewriting() throws IOException {
        if (this.mUserStop) {
            LogS.m473d("TranscodeLib", "Not starting encoding because it is stopped by user.");
            return;
        }
        LogS.m473d("TranscodeLib", "startRewriting");
        this.mVideoEncoderDone = false;
        this.mAudioEncoderDone = !this.mCopyAudio;
        this.mPendingAudioDecoderOutputBufferIndex = -1;
        int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor);
        if (videoTrack != -1) {
            MediaFormat formatV = this.mVideoExtractor.getTrackFormat(videoTrack);
            NalUnitParser firstNalUnit = null;
            if (isHDR10() && this.mTrimVideoStartUs != 0) {
                int bufferSizeV = getVideoSampleSize(formatV);
                ByteBuffer tempBuffer = ByteBuffer.allocate(bufferSizeV);
                if (this.mVideoExtractor.readSampleData(tempBuffer, 0) > 0) {
                    firstNalUnit = new NalUnitParser(tempBuffer);
                    if (firstNalUnit.findHDRStaticMeta() && firstNalUnit.getHdrStaticMeta() != null) {
                        LogS.m475i("TranscodeLib", "has hdr static meta : " + this.mVideoExtractor.getSampleTime());
                    } else {
                        LogS.m475i("TranscodeLib", "fail to find hdr static meta " + this.mVideoExtractor.getSampleTime());
                        firstNalUnit = null;
                    }
                }
            }
            checkTrimVideoStartPointChanged();
            checkAudioTranscodeSection();
            LogS.m473d("TranscodeLib", "Rewriting starts");
            this.mAudioProgressTime = 0L;
            this.mVidioProgressTime = 0L;
            int audioTrack = CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor);
            MediaFormat formatA = null;
            if (audioTrack != -1) {
                formatA = this.mAudioExtractor.getTrackFormat(audioTrack);
            }
            if (!this.mMuxerStarted) {
                String filepath = this.mUseUri ? FileHelper.getVEEditFilePath(this.mContext, this.mInputUri) : this.mInputFilePath;
                LogS.m473d("TranscodeLib", "filepath :" + filepath);
                if (updateCreationTime(filepath, false)) {
                    formatV.setInteger("param-meta-author", 8);
                    formatV.setInteger("param-meta-transcoding", 1);
                }
                if (!TextUtils.isEmpty(mInputFileinfo.Writer)) {
                    formatV.setString("param-meta-brand-model-name", mInputFileinfo.Writer);
                }
                if (this.mExportRecordingMode != -1) {
                    formatV.setInteger("param-meta-recording-mode", this.mExportRecordingMode);
                    LogS.m473d("TranscodeLib", "set recording mode for NDE : " + this.mExportRecordingMode);
                } else if (this.mRecordingMode == 10 || this.mRecordingMode == 25) {
                    formatV.setInteger("param-meta-recording-mode", this.mRecordingMode);
                    LogS.m473d("TranscodeLib", "set recording mode for HDR 10 PLUS : " + this.mRecordingMode);
                }
                this.mVideoTrackIndex = this.mMuxer.addTrack(formatV);
                if (formatA != null && !UNKNOWN_AUDIO.equals(formatA.getString(MediaFormat.KEY_MIME))) {
                    this.mAudioTrackIndex = this.mMuxer.addTrack(formatA);
                } else {
                    audioTrack = -1;
                }
                this.mMuxer.setOrientationHint(this.mInputOrientationDegrees);
                if (mInputFileinfo.IsLocationAvailable) {
                    this.mMuxer.setLocation(mInputFileinfo.latitude, mInputFileinfo.longitude);
                }
                this.mMuxer.start();
                this.mMuxerStarted = true;
            }
            int bufferSizeV2 = getVideoSampleSize(formatV);
            rewriteVideo(this.mTrimVideoEndUs, firstNalUnit, bufferSizeV2);
            if (audioTrack == -1 || this.mOutputAudioMute) {
                this.mCopyAudio = false;
            } else {
                rewriteAudio(this.mTrimVideoEndUs);
            }
            if (!this.mUserStop) {
                LogS.m473d("TranscodeLib", "Rewriting finished");
                return;
            }
            return;
        }
        throw new IOException("Absent valid video track");
    }

    private void rewriteAudio(long endTime) {
        ByteBuffer dstBufA = ByteBuffer.allocate(131072);
        MediaCodec.BufferInfo bufferInfoA = new MediaCodec.BufferInfo();
        bufferInfoA.size = this.mAudioExtractor.readSampleData(dstBufA, 0);
        boolean sawEOSA = false;
        while (!this.mUserStop && !sawEOSA) {
            bufferInfoA.offset = 0;
            bufferInfoA.size = this.mAudioExtractor.readSampleData(dstBufA, 0);
            if (bufferInfoA.size < 0) {
                LogS.m473d("TranscodeLib", "saw input EOS: Audio");
                sawEOSA = true;
                bufferInfoA.size = 0;
            } else {
                bufferInfoA.presentationTimeUs = this.mAudioExtractor.getSampleTime();
                if (endTime != -1 && bufferInfoA.presentationTimeUs > endTime) {
                    sawEOSA = true;
                    LogS.m473d("TranscodeLib", "sawEOS: true: A");
                } else {
                    bufferInfoA.flags = this.mAudioExtractor.getSampleFlags();
                    try {
                        this.mMuxer.writeSampleData(this.mAudioTrackIndex, dstBufA, bufferInfoA);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        LogS.m474e("TranscodeLib", "fail to writeSampleData " + e);
                    }
                    updateProgress(bufferInfoA.presentationTimeUs, true);
                    this.mAudioExtractor.advance();
                }
            }
        }
    }

    private void rewriteVideo(long endTime, NalUnitParser firstNalUnit, int bufferSizeV) {
        long startPtsTimeUs;
        long lastPtsUs;
        int i;
        ByteBuffer dstBufV = ByteBuffer.allocate(bufferSizeV);
        MediaCodec.BufferInfo bufferInfoV = new MediaCodec.BufferInfo();
        int i2 = 0;
        bufferInfoV.size = this.mVideoExtractor.readSampleData(dstBufV, 0);
        long startPtsTimeUs2 = this.mVideoExtractor.getSampleTime();
        NalUnitParser firstNalUnit2 = firstNalUnit;
        boolean sawEOSV = false;
        while (!this.mUserStop && !sawEOSV) {
            bufferInfoV.offset = i2;
            bufferInfoV.size = this.mVideoExtractor.readSampleData(dstBufV, i2);
            if (bufferInfoV.size < 0) {
                LogS.m473d("TranscodeLib", "saw input EOS: Video");
                sawEOSV = true;
                bufferInfoV.size = i2;
            } else {
                bufferInfoV.presentationTimeUs = this.mVideoExtractor.getSampleTime();
                bufferInfoV.flags = this.mVideoExtractor.getSampleFlags();
                this.mVideoExtractor.advance();
                long nextPresentationTimeUs = this.mVideoExtractor.getSampleTime();
                if (nextPresentationTimeUs != -1) {
                    startPtsTimeUs = startPtsTimeUs2;
                    lastPtsUs = Math.max(nextPresentationTimeUs - bufferInfoV.presentationTimeUs, 0L);
                } else {
                    startPtsTimeUs = startPtsTimeUs2;
                    long lastPtsUs2 = (this.mSEFVideo ? mInputFileinfo.EditedDuration : mInputFileinfo.Duration) * 1000;
                    lastPtsUs = Math.max(lastPtsUs2 - bufferInfoV.presentationTimeUs, 0L);
                }
                if (endTime != -1 && bufferInfoV.presentationTimeUs + lastPtsUs >= endTime) {
                    LogS.m473d("TranscodeLib", "sawEOS: true: V");
                    sawEOSV = true;
                    i = 0;
                } else if (bufferInfoV.presentationTimeUs < startPtsTimeUs) {
                    i = 0;
                } else {
                    if (firstNalUnit2 != null) {
                        try {
                        } catch (IllegalArgumentException | IllegalStateException e) {
                            LogS.m474e("TranscodeLib", "fail to writeSampleData " + e);
                        }
                        if ((bufferInfoV.flags & 1) != 0) {
                            if (!new NalUnitParser(dstBufV).findHDRStaticMeta()) {
                                ByteBuffer newBuf = firstNalUnit2.insertHDRStaticMeta(dstBufV, bufferInfoV.size, CodecsHelper.isHevcFormat(mInputVideoinfo));
                                LogS.m475i("TranscodeLib", "add HDR static info");
                                this.mMuxer.writeSampleData(this.mVideoTrackIndex, newBuf, bufferInfoV);
                            } else {
                                LogS.m475i("TranscodeLib", "has already static info");
                                this.mMuxer.writeSampleData(this.mVideoTrackIndex, dstBufV, bufferInfoV);
                            }
                            firstNalUnit2 = null;
                            i = 0;
                            updateProgress(bufferInfoV.presentationTimeUs, false);
                        }
                    }
                    this.mMuxer.writeSampleData(this.mVideoTrackIndex, dstBufV, bufferInfoV);
                    i = 0;
                    updateProgress(bufferInfoV.presentationTimeUs, false);
                }
                i2 = i;
                startPtsTimeUs2 = startPtsTimeUs;
            }
        }
    }

    @Override // com.samsung.android.transcode.core.Encode
    public void startSMEncoding() throws IOException {
        if (this.mUserStop) {
            LogS.m473d("TranscodeLib", "Not starting Slowmotion encoding because it is stopped by user.");
            return;
        }
        LogS.m475i("TranscodeLib", "startSMEncoding");
        initialize_video();
        initialize_audio();
        if (this.mTrimVideoStartUs != 0) {
            this.mVideoExtractor.seekTo(this.mTrimVideoStartUs, 0);
            this.mTrimVideoStartUs = this.mVideoExtractor.getSampleTime();
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
        LogS.m473d("TranscodeLib", "Transcode section - Audio : Current position: " + this.mAudioExtractor.getSampleTime() + " mTrimAudioStartUs: " + this.mTrimAudioStartUs + "Video: " + this.mVideoExtractor.getSampleTime() + " mTrimVideoStartUs: " + this.mTrimVideoStartUs);
        if (isSlowV2()) {
            this.mTimescale = 0.0f;
        }
        do {
            if (!this.mVideoEncoderDone || !this.mAudioEncoderDone) {
                if (this.mCopyAudio) {
                    startAudioEncoding();
                }
                if (!this.mPrepared) {
                    startVideoDecoding();
                    this.mPrepared = true;
                }
                sendFrametoEncoder();
                if (this.mUserStop) {
                    break;
                }
            } else {
                return;
            }
        } while (!this.mCodecError);
        LogS.m473d("TranscodeLib", "Encoding abruptly stopped mUserStop ?" + this.mUserStop + " mCodecError? " + this.mCodecError);
    }

    private void checkTrimAudioStartPos() {
        long prevVideoStartTime = this.mTrimVideoStartUs;
        if (this.mTrimVideoStartUs != 0) {
            this.mVideoExtractor.seekTo(this.mTrimVideoStartUs, 0);
            this.mTrimVideoStartUs = this.mVideoExtractor.getSampleTime();
        }
        if (!this.mSEFVideo || this.mTrimVideoStartUs == prevVideoStartTime || this.mTrimVideoEndUs <= 0) {
            LogS.m473d("TranscodeLib", "Video  section: Current position: " + this.mTrimVideoStartUs);
        } else {
            LogS.m473d("TranscodeLib", "checkTrimAudfioStartPos prev : " + prevVideoStartTime + " ~ " + this.mTrimVideoEndUs);
            long diffUs = this.mSefhelper.getConvertedTime(prevVideoStartTime) - this.mSefhelper.getConvertedTime(this.mTrimVideoStartUs);
            long endMs = (this.mOriginTrimEndUs - diffUs) / 1000;
            if (this.mRecordingMode == 1 || this.mRecordingMode == 2 || this.mRecordingMode == 12 || this.mRecordingMode == 21 || this.mRecordingMode == 19) {
                long slowfastSeektime = getSlowfastSeektime(1000 * endMs);
                this.mTrimAudioEndUs = slowfastSeektime;
                this.mTrimVideoEndUs = slowfastSeektime;
            } else if (isSuperSlow()) {
                long superslowSeektime = getSuperslowSeektime(1000 * endMs);
                this.mTrimAudioEndUs = superslowSeektime;
                this.mTrimVideoEndUs = superslowSeektime;
            } else if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                long slowfastSeektime2 = getSlowfastSeektime(1000 * endMs) / 2;
                this.mTrimAudioEndUs = slowfastSeektime2;
                this.mTrimVideoEndUs = slowfastSeektime2;
            }
            LogS.m473d("TranscodeLib", "checkTrimAudfioStartPos after : " + this.mTrimVideoStartUs + " ~ " + this.mTrimVideoEndUs);
        }
        if (this.mCopyAudio && this.mTrimAudioStartUs != 0) {
            this.mAudioExtractor.seekTo(this.mTrimVideoStartUs, 0);
            while (this.mAudioExtractor.getSampleTime() < this.mTrimVideoStartUs) {
                if (this.mAudioExtractor.getSampleTime() == -1) {
                    throw new RuntimeException("Invalid File!");
                }
                this.mAudioExtractor.advance();
            }
            LogS.m473d("TranscodeLib", "Audio Transcode section: Current position: " + this.mAudioExtractor.getSampleTime() + " mTrimAudioStartUs: " + this.mTrimAudioStartUs);
        }
    }

    private MediaFormat checkFormatV(MediaFormat formatV) {
        ByteBuffer csd;
        if (formatV == null) {
            return null;
        }
        String filepath = this.mUseUri ? FileHelper.getVEEditFilePath(this.mContext, this.mInputUri) : this.mInputFilePath;
        if (updateCreationTime(filepath, false)) {
            formatV.setInteger("param-meta-author", 8);
            formatV.setInteger("param-meta-transcoding", 1);
            if (this.mExportRecordingMode != -1) {
                this.mVideoEncoderOutputMediaFormat.setInteger("param-meta-recording-mode", this.mExportRecordingMode);
                LogS.m473d("TranscodeLib", "set recording mode for NDE : " + this.mExportRecordingMode);
            }
        }
        if (!TextUtils.isEmpty(mInputFileinfo.Writer)) {
            formatV.setString("param-meta-brand-model-name", mInputFileinfo.Writer);
        }
        if (CodecsHelper.isHevcFormat(mInputVideoinfo) && "video/hevc".equals(this.mOutputVideoMimeType)) {
            formatV.setInteger("level", mInputVideoinfo.getInteger("level"));
        } else {
            formatV.setInteger("level", this.mOutputWidth == 1280 ? 512 : 4096);
            if (formatV.containsKey("csd-0") && (csd = formatV.getByteBuffer("csd-0")) != null) {
                byte[] buffer = new byte[csd.remaining()];
                csd.get(buffer, 0, buffer.length);
                if (this.mOutputWidth == 1280) {
                    buffer[7] = SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN;
                } else {
                    buffer[7] = 41;
                }
                ByteBuffer csdTemp = ByteBuffer.allocate(buffer.length);
                csdTemp.put(buffer, 0, buffer.length);
                csdTemp.flip();
                formatV.setByteBuffer("csd-0", csdTemp);
            }
        }
        return formatV;
    }

    /* JADX WARN: Incorrect condition in loop: B:46:0x012c */
    @Override // com.samsung.android.transcode.core.Encode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void startSMRewriting() throws IOException {
        long prevModifiedVideotime;
        int index;
        if (this.mUserStop) {
            LogS.m473d("TranscodeLib", "Not starting encoding because it is stopped by user.");
            return;
        }
        LogS.m475i("TranscodeLib", "startSMRewriting");
        initialize_audio();
        int i = 0;
        this.mIsDrop = false;
        long trimRewriteEndTime = this.mOriginTrimEndUs;
        checkTrimAudioStartPos();
        LogS.m473d("TranscodeLib", "Rewriting starts");
        if (isSlowV2()) {
            this.mTimescale = 0.0f;
        }
        int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor);
        int audioTrack = -1;
        if (this.mCopyAudio) {
            audioTrack = CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor);
        }
        MediaFormat formatA = null;
        this.mAudioProgressTime = 0L;
        this.mVidioProgressTime = 0L;
        if (videoTrack != -1) {
            MediaFormat formatV = this.mVideoExtractor.getTrackFormat(videoTrack);
            if (audioTrack != -1) {
                formatA = this.mAudioExtractor.getTrackFormat(audioTrack);
            }
            if (!this.mMuxerStarted) {
                formatV = checkFormatV(formatV);
                LogS.m473d("TranscodeLib", "video format " + formatV);
                this.mVideoTrackIndex = this.mMuxer.addTrack(formatV);
                if (formatA != null && !UNKNOWN_AUDIO.equals(formatA.getString(MediaFormat.KEY_MIME))) {
                    if (isSlowV2() && formatA.containsKey("csd-0")) {
                        ByteBuffer csd = formatA.getByteBuffer("csd-0");
                        byte[] buffer = new byte[csd.remaining()];
                        csd.get(buffer, 0, buffer.length);
                        buffer[0] = 17;
                        buffer[1] = MidiConstants.STATUS_NOTE_ON;
                        ByteBuffer csdTemp = ByteBuffer.allocate(buffer.length);
                        csdTemp.put(buffer, 0, buffer.length);
                        csdTemp.flip();
                        formatA.setByteBuffer("csd-0", csdTemp);
                    }
                    LogS.m473d("TranscodeLib", "audio format " + formatA);
                    this.mAudioTrackIndex = this.mMuxer.addTrack(formatA);
                } else {
                    audioTrack = -1;
                }
                this.mMuxer.setOrientationHint(this.mInputOrientationDegrees);
                this.mMuxer.start();
                this.mMuxerStarted = true;
            }
            if (audioTrack == -1 || this.mOutputAudioMute) {
                this.mAudioEncoderDone = true;
                this.mAudioExtractorDone = true;
                this.mCopyAudio = false;
            }
            if (audioTrack != -1) {
                while (!this.mUserStop && !this.mAudioEncoderDone) {
                    startAudioEncoding();
                }
            }
            int bufferSizeV = getVideoSampleSize(formatV);
            ByteBuffer dstBufV = ByteBuffer.allocate(bufferSizeV);
            MediaCodec.BufferInfo bufferInfoV = new MediaCodec.BufferInfo();
            bufferInfoV.size = this.mVideoExtractor.readSampleData(dstBufV, 0);
            boolean isHevc = CodecsHelper.isHevcFormat(formatV);
            long prevModifiedVideotime2 = 0;
            boolean sawEOSV = false;
            while (!sawEOSV && !sawEOSV) {
                bufferInfoV.offset = 0;
                bufferInfoV.size = this.mVideoExtractor.readSampleData(dstBufV, 0);
                if (bufferInfoV.size < 0) {
                    LogS.m473d("TranscodeLib", "saw input EOS: Video");
                    sawEOSV = true;
                    bufferInfoV.size = i;
                } else {
                    long trimRewriteEndTime2 = trimRewriteEndTime;
                    long presentationTimeUs = this.mVideoExtractor.getSampleTime();
                    this.mModifiedVideotime = presentationTimeUs;
                    int videoTrack2 = videoTrack;
                    LogS.m473d("TranscodeLib", "mModifiedVideotime = presentationTime = " + this.mModifiedVideotime);
                    if (this.mSEFVideo) {
                        byte[] a = new byte[4];
                        dstBufV.position(4);
                        dstBufV.get(a, 0, a.length);
                        dstBufV.position(0);
                        this.mIsDrop = calculateIsDrop(a, presentationTimeUs);
                    }
                    bufferInfoV.presentationTimeUs = this.mVideoExtractor.getSampleTime();
                    long frameDuration = prevModifiedVideotime2 != 0 ? this.mModifiedVideotime - prevModifiedVideotime2 : 0L;
                    if (trimRewriteEndTime2 != -1) {
                        long j = bufferInfoV.presentationTimeUs + frameDuration;
                        long frameDuration2 = this.mTrimVideoEndUs;
                        if (j > frameDuration2) {
                            LogS.m473d("TranscodeLib", "sawEOS: true: V");
                            sawEOSV = true;
                            index = 0;
                            i = index;
                            trimRewriteEndTime = trimRewriteEndTime2;
                            videoTrack = videoTrack2;
                        }
                    }
                    bufferInfoV.presentationTimeUs = this.mModifiedVideotime;
                    bufferInfoV.flags = this.mVideoExtractor.getSampleFlags();
                    long prevModifiedVideotime3 = this.mModifiedVideotime;
                    if (this.mIsDrop) {
                        prevModifiedVideotime = prevModifiedVideotime3;
                        index = 0;
                    } else {
                        int index2 = 0;
                        byte[] encodedStream = new byte[dstBufV.remaining()];
                        int i2 = encodedStream.length;
                        prevModifiedVideotime = prevModifiedVideotime3;
                        dstBufV.get(encodedStream, 0, i2);
                        LogS.m473d("TranscodeLib", "writeSampleData time:" + bufferInfoV.presentationTimeUs + " length=" + encodedStream.length);
                        if (!isHevc) {
                            int i3 = 0;
                            do {
                                index2 = i3;
                                i3 = findNalStartCode(encodedStream, NAL_START_CODE.length + i3);
                                LogS.m473d("TranscodeLib", "findNalStartCode. i: " + i3 + ", index: " + index2);
                            } while (i3 != -1);
                        }
                        dstBufV.position(index2);
                        bufferInfoV.offset = index2;
                        try {
                            this.mMuxer.writeSampleData(this.mVideoTrackIndex, dstBufV, bufferInfoV);
                        } catch (IllegalArgumentException | IllegalStateException e) {
                            LogS.m474e("TranscodeLib", "fail to writeSampleData " + e);
                        }
                        if (audioTrack == -1) {
                            this.mPausedVideoUs = bufferInfoV.presentationTimeUs;
                        }
                        index = 0;
                        updateProgress(bufferInfoV.presentationTimeUs, false);
                    }
                    this.mVideoExtractor.advance();
                    prevModifiedVideotime2 = prevModifiedVideotime;
                    i = index;
                    trimRewriteEndTime = trimRewriteEndTime2;
                    videoTrack = videoTrack2;
                }
            }
            if (!this.mUserStop) {
                LogS.m473d("TranscodeLib", "Rewriting finished");
                return;
            }
            return;
        }
        throw new IOException("Absent valid video track");
    }

    private void releaseVideoObjects() {
        try {
        } catch (Exception e) {
            LogS.m474e("TranscodeLib", "Exception in releasing output video encoder.");
        } finally {
            this.mAsyncCodecReleased[EncodeBase.ASYNC_CODEC_TYPE.VIDEO_ENCODER.ordinal()] = true;
        }
        if (this.mOutputVideoEncoder != null) {
            this.mOutputVideoEncoder.stop();
            this.mOutputVideoEncoder.setCallback(null);
            this.mOutputVideoEncoder.release();
            this.mOutputVideoEncoder = null;
        }
        try {
        } catch (Exception e2) {
            LogS.m474e("TranscodeLib", "Exception in releasing input video decoder.");
        } finally {
            this.mAsyncCodecReleased[EncodeBase.ASYNC_CODEC_TYPE.VIDEO_DECODER.ordinal()] = true;
        }
        if (this.mInputVideoDecoder != null) {
            this.mInputVideoDecoder.stop();
            this.mInputVideoDecoder.setCallback(null);
            this.mInputVideoDecoder.release();
            this.mInputVideoDecoder = null;
        }
        if (this.mVideoExtractor != null) {
            try {
                this.mVideoExtractor.release();
                this.mVideoExtractor = null;
            } catch (Exception e3) {
                LogS.m474e("TranscodeLib", "Exception in releasing video extractor.");
            }
        }
    }

    private void releaseSurfaceObjects() {
        if (this.mOutputSurface != null) {
            try {
                this.mOutputSurface.release();
                this.mOutputSurface = null;
            } catch (Exception e) {
                LogS.m474e("TranscodeLib", "Exception in releasing outputSurface.");
            }
        }
        if (this.mInputSurface != null) {
            try {
                this.mInputSurface.release();
                this.mInputSurface = null;
            } catch (Exception e2) {
                LogS.m474e("TranscodeLib", "Exception in releasing input surface.");
            }
        }
    }

    private void releaseAudioObjects() {
        if (this.mOutputAudioEncoder != null) {
            try {
                this.mOutputAudioEncoder.stop();
                this.mOutputAudioEncoder.release();
                this.mOutputAudioEncoder = null;
            } catch (Exception e) {
                LogS.m474e("TranscodeLib", "Exception in releasing output audio encoder.");
            }
        }
        if (this.mInputAudioDecoder != null) {
            try {
                this.mInputAudioDecoder.stop();
                this.mInputAudioDecoder.release();
                this.mInputAudioDecoder = null;
            } catch (Exception e2) {
                LogS.m474e("TranscodeLib", "Exception in releasing input audio decoder.");
            }
        }
        if (this.mAudioExtractor != null) {
            try {
                this.mAudioExtractor.release();
                this.mAudioExtractor = null;
            } catch (Exception e3) {
                LogS.m474e("TranscodeLib", "Exception in releasing audio extractor.");
            }
        }
    }

    private void releaseMuxer() {
        if (this.mMuxer != null) {
            try {
                if (this.mMuxerStarted) {
                    this.mMuxer.stop();
                }
                this.mMuxer.release();
                this.mMuxer = null;
            } catch (Exception e) {
                LogS.m474e("TranscodeLib", "Exception in releasing muxer.");
            }
        }
    }

    private void releaseHandleObjects() {
        if (sSRCHandle != 0) {
            this.mAudio.SRCDestroy(sSRCHandle);
            sSRCHandle = 0L;
            LogS.m473d("TranscodeLib", " SRC release end ");
        }
        if (sVSPHandle != 0) {
            this.mAudio.VSPDestroy(sVSPHandle);
            sVSPHandle = 0L;
            LogS.m473d("TranscodeLib", " VSP release end ");
        }
        if (sNAACHandle != 0) {
            this.mAudio.NAACEncoderDeInit(sNAACHandle);
            sNAACHandle = 0L;
            LogS.m473d("TranscodeLib", " NAAC release end ");
        }
    }

    @Override // com.samsung.android.transcode.core.Encode
    protected synchronized void release() {
        try {
            LogS.m474e("TranscodeLib", "releasing encoder objects");
            releaseFramemanager();
            releaseVideoObjects();
            releaseSurfaceObjects();
            releaseAudioObjects();
            releaseMuxer();
            releaseHandleObjects();
            if (this.mEncoding) {
                try {
                    if (this.mUpdateCreationTime) {
                        updateCreationTime(this.mOutputFilePath, true);
                    }
                } catch (Throwable th) {
                    th = th;
                    synchronized (this.mStopLock) {
                        this.mEncoding = false;
                        this.mPrepared = false;
                        this.mStopLock.notifyAll();
                    }
                    throw th;
                }
            }
            if (this.mIs360Video) {
                insertUuidFor360Video(this.mInputFilePath, this.mOutputFilePath);
            }
            if (this.mDecAudio != null) {
                this.mDecAudio.clear();
                this.mDecAudio = null;
            }
            synchronized (this.mStopLock) {
                try {
                    this.mEncoding = false;
                    this.mPrepared = false;
                    this.mStopLock.notifyAll();
                } catch (Throwable th2) {
                    th = th2;
                    while (true) {
                        try {
                            throw th;
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    @Override // com.samsung.android.transcode.core.Encode
    public void stop() {
        LogS.m473d("TranscodeLib", "Stop method called ");
        synchronized (this.mStopLock) {
            if (this.mOutputSurface != null) {
                this.mOutputSurface.notifyFrameSyncObject();
            }
            this.mUserStop = true;
            LogS.m473d("TranscodeLib", "mUserStop - true");
            if (!this.mEncoding) {
                return;
            }
            try {
                try {
                    LogS.m473d("TranscodeLib", "Calling wait on stop lock.");
                    this.mStopLock.wait(5000L);
                } catch (InterruptedException e) {
                    LogS.m473d("TranscodeLib", "Stop lock interrupted.");
                    e.printStackTrace();
                    LogS.m473d("TranscodeLib", "Stop method finally  mEncoding :" + this.mEncoding);
                    if (this.mEncoding) {
                    }
                }
            } finally {
                LogS.m473d("TranscodeLib", "Stop method finally  mEncoding :" + this.mEncoding);
                if (this.mEncoding) {
                    release();
                }
            }
        }
    }

    public static int getMaxEncodingDuration(int maxSizeKB, int width, int height, int audioCodecType) {
        int audioBitRate;
        int minBitRate = CodecsHelper.getVideoMinBitrate(width, height);
        long outFileSize = (int) (maxSizeKB * 0.7f);
        if (audioCodecType == 1) {
            audioBitRate = 8;
        } else if (maxSizeKB < 1000 || (width < 200 && height < 200)) {
            audioBitRate = 64;
        } else {
            audioBitRate = 128;
        }
        long expectedDurationInMs = ((8 * outFileSize) * 1024) / (minBitRate + audioBitRate);
        LogS.m473d("TranscodeLib", "Size " + maxSizeKB + " width " + width + " height " + height + " minBitRate : " + minBitRate + " audiocodec " + audioCodecType + " maxdur " + expectedDurationInMs);
        return (int) Math.max(1000L, expectedDurationInMs);
    }

    public int getOutputFileSize() {
        MediaExtractor extractor;
        int outputVideBitRate;
        try {
            if (this.mUseUri) {
                extractor = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
            } else {
                extractor = CodecsHelper.createExtractor(this.mInputFilePath);
            }
            int videoTrack = CodecsHelper.getAndSelectVideoTrackIndex(extractor);
            MediaFormat inputFormat = extractor.getTrackFormat(videoTrack);
            long trimEndTime = this.mTrimVideoEndUs;
            if (trimEndTime == 0) {
                if (this.mSEFVideo) {
                    trimEndTime = mInputFileinfo.EditedDuration * 1000;
                } else {
                    trimEndTime = inputFormat.getLong(MediaFormat.KEY_DURATION);
                }
                LogS.m473d("TranscodeLib", "getOutputFileSize  trimEndTime was 0 but updated trimEndTime : " + trimEndTime);
            }
            extractor.release();
            if (this.mOutputMaxSizeKB >= 0) {
                if ("video/avc".equals(this.mOutputVideoMimeType)) {
                    this.mSizeFraction = 0.9f;
                }
                outputVideBitRate = CodecsHelper.getVideoEncodingBitRate(this.mSizeFraction, this.mOutputMaxSizeKB, (trimEndTime - this.mTrimVideoStartUs) / 1000, this.mOutputAudioBitRate / 1000, this.mOutputWidth, this.mOutputHeight) * 1000;
            } else {
                outputVideBitRate = CodecsHelper.suggestBitrate(new ExportMediaInfo(this.mOutputWidth, this.mOutputHeight, this.mOutputVideoFrameRate, this.mOutputVideoMimeType, isHDR10Plus()), mInputFileinfo);
            }
            int size = (int) (((trimEndTime - this.mTrimVideoStartUs) / 8000000.0d) * ((this.mOutputAudioBitRate + outputVideBitRate) / 1000.0d));
            if (this.mOutputMaxSizeKB == 0) {
                return (int) (size * 0.9d);
            }
            return size;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    private boolean checkVideoCodec(int outWidth, int outHeight, boolean isRewrite) {
        if (!isRewrite) {
            if (!MediaInfoChecker.isSupportedResolution(mInputVideoinfo, mInputFileinfo.Width, mInputFileinfo.Height, outWidth, outHeight)) {
                LogS.m473d("TranscodeLib", "isSupportedResolution  Error appear : 0");
                return false;
            }
            if (!MediaInfoChecker.isSupportedCodecType(mInputVideoinfo)) {
                LogS.m473d("TranscodeLib", "isSupportedCodecType video  Error appear : 0");
                return false;
            }
        }
        if (mInputFileinfo.Width <= 0 || mInputFileinfo.Height <= 0) {
            LogS.m473d("TranscodeLib", "Resolution Error appear : width = " + mInputFileinfo.Width + ", height= " + mInputFileinfo.Height);
            return false;
        }
        this.mSMConvert = false;
        this.mSMEncode = false;
        if (this.mSEFVideo && isSlowFast() && mInputFileinfo.Width == outWidth && mInputFileinfo.Height == outHeight && this.mOutputVideoMimeType.equals(mInputVideoinfo.getString(MediaFormat.KEY_MIME))) {
            this.mSMConvert = true;
            LogS.m473d("TranscodeLib", "Slowmotion Converting case  mSMConvert");
            return true;
        }
        if (this.mSEFVideo && isSlowV2()) {
            this.mSMEncode = true;
            LogS.m473d("TranscodeLib", "Slowmotion V2 transcoding case mSMEncode");
        }
        return true;
    }

    public static boolean findAtom(String srcMediaPath, String atomToHunt) throws IOException {
        Throwable th;
        String str = atomToHunt;
        boolean ret = false;
        int i = 0;
        if (srcMediaPath == null) {
            LogS.m473d("TranscodeLib", "findAtom : filepath is null");
            return false;
        }
        File file = new File(srcMediaPath);
        byte[] atomSizeBuf = new byte[4];
        byte[] atomNameBuf = new byte[4];
        long fileSize = file.length();
        LogS.m473d("TranscodeLib", "file size: " + fileSize);
        String[] parentContainer = {"mdia", "minf", "moov", "stbl", "trak"};
        RandomAccessFile fileObj = new RandomAccessFile(file, "r");
        long filePointer = 0;
        while (true) {
            if (filePointer >= fileSize) {
                break;
            }
            try {
                try {
                    LogS.m473d("TranscodeLib", "filePointer: " + filePointer);
                    fileObj.seek(filePointer);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (fileObj.read(atomSizeBuf, i, atomSizeBuf.length) < 0) {
                    LogS.m473d("TranscodeLib", "file read is reached to end of the file");
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    fileObj.close();
                    throw th;
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                    throw th;
                }
            }
            try {
                long atomSize = unsignedIntToLong(atomSizeBuf);
                boolean ret2 = ret;
                try {
                    LogS.m473d("TranscodeLib", "Atom Size: " + atomSize);
                    if (fileObj.read(atomNameBuf, 0, atomNameBuf.length) < 0) {
                        try {
                            LogS.m473d("TranscodeLib", "file read is reached to end of the file");
                        } catch (Throwable th4) {
                            th = th4;
                            fileObj.close();
                            throw th;
                        }
                    }
                    String atomName = new String(atomNameBuf, StandardCharsets.UTF_8);
                    File file2 = file;
                    try {
                        LogS.m473d("TranscodeLib", "Atom Box: " + atomName);
                        int tmpAtomPosition = Arrays.binarySearch(parentContainer, atomName);
                        if (tmpAtomPosition >= 0) {
                            LogS.m473d("TranscodeLib", "Found parent: " + atomName + " move to : " + tmpAtomPosition);
                            filePointer += 8;
                        } else {
                            if (atomName.equals(str)) {
                                LogS.m473d("TranscodeLib", "Found: " + str);
                                ret = true;
                                break;
                            }
                            if (atomSize == 1) {
                                fileObj.seek(filePointer + 8);
                                byte[] atomLargeSizeBuf = new byte[8];
                                if (fileObj.read(atomLargeSizeBuf, 0, atomLargeSizeBuf.length) < 0) {
                                    LogS.m473d("TranscodeLib", "file read is reached to end of the file");
                                }
                                BigInteger atomTmpLargeSize = new BigInteger(atomLargeSizeBuf);
                                long atomLargeSize = atomTmpLargeSize.longValue();
                                filePointer += atomLargeSize;
                                LogS.m473d("TranscodeLib", "64bit: " + atomLargeSize);
                            } else {
                                if (atomSize == 0) {
                                    LogS.m473d("TranscodeLib", "filePointer does not go forward. Exit.");
                                    ret = false;
                                    break;
                                }
                                filePointer += atomSize;
                                LogS.m473d("TranscodeLib", "move: " + filePointer + " atomsize " + atomSize);
                            }
                        }
                        str = atomToHunt;
                        ret = ret2;
                        file = file2;
                        i = 0;
                    } catch (Throwable th5) {
                        th = th5;
                        fileObj.close();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                }
            } catch (Throwable th7) {
                th = th7;
                fileObj.close();
                throw th;
            }
        }
        fileObj.close();
        return ret;
    }

    public static boolean isSupportedFormat(String filePath) {
        return CodecsHelper.isSupportedFormat(filePath);
    }

    public static boolean isSupportedFormat(Context context, Uri uri) {
        return CodecsHelper.isSupportedFormat(context, uri);
    }

    public static void insertUuidFor360Video(String inputFilePath, String outputFilePath) {
        Throwable th;
        Throwable th2;
        String str;
        String str2;
        String str3;
        File outputFile;
        String[] parentContainer;
        byte[] outputAtomNameBuf;
        String[] outParentContainer;
        long atomSize;
        String str4 = "uuid";
        String str5 = "trak";
        String str6 = "moov";
        LogS.m473d("TranscodeLib", "insertUuidFor360Video");
        File inputFile = new File(inputFilePath);
        File outputFile2 = new File(outputFilePath);
        long size = inputFile.length();
        try {
            try {
                RandomAccessFile inputRAF = new RandomAccessFile(inputFile, "r");
                try {
                    RandomAccessFile outputRAF = new RandomAccessFile(outputFile2, "rws");
                    long filePointer = 0;
                    try {
                        try {
                            String[] parentContainer2 = {"moov", "trak", "uuid"};
                            try {
                                byte[] atomSizeBuf = new byte[4];
                                byte[] atomNameBuf = new byte[4];
                                while (filePointer < size) {
                                    inputRAF.seek(filePointer);
                                    if (inputRAF.read(atomSizeBuf, 0, atomSizeBuf.length) < 0) {
                                        try {
                                            LogS.m473d("TranscodeLib", "inputfile read is reached to end of the file");
                                        } catch (Throwable th3) {
                                            th2 = th3;
                                            try {
                                                outputRAF.close();
                                                throw th2;
                                            } catch (Throwable th4) {
                                                th2.addSuppressed(th4);
                                                throw th2;
                                            }
                                        }
                                    }
                                    long atomSize2 = unsignedIntToLong(atomSizeBuf);
                                    byte[] atomSizeBuf2 = atomSizeBuf;
                                    if (inputRAF.read(atomNameBuf, 0, atomNameBuf.length) < 0) {
                                        LogS.m473d("TranscodeLib", "inputfile read is reached to end of the file");
                                    }
                                    String atomName = new String(atomNameBuf, StandardCharsets.UTF_8);
                                    int tmpAtomPosition = Arrays.binarySearch(parentContainer2, atomName);
                                    byte[] atomNameBuf2 = atomNameBuf;
                                    long size2 = size;
                                    if (tmpAtomPosition >= 0) {
                                        try {
                                            if (atomName.equals(str4)) {
                                                long outputFileSize = outputFile2.length();
                                                String[] outParentContainer2 = {str6, str5};
                                                str = str4;
                                                byte[] outputAtomSizeBuf = new byte[4];
                                                str2 = str5;
                                                byte[] outputAtomNameBuf2 = new byte[4];
                                                String[] parentContainer3 = parentContainer2;
                                                long outputFilePointer = 0;
                                                while (true) {
                                                    if (outputFilePointer >= outputFileSize) {
                                                        str3 = str6;
                                                        outputFile = outputFile2;
                                                        parentContainer = parentContainer3;
                                                        break;
                                                    }
                                                    outputRAF.seek(outputFilePointer);
                                                    String atomName2 = atomName;
                                                    outputFile = outputFile2;
                                                    try {
                                                        if (outputRAF.read(outputAtomSizeBuf, 0, outputAtomSizeBuf.length) < 0) {
                                                            LogS.m473d("TranscodeLib", "outputFile read is reached to end of the file");
                                                        }
                                                        long outputAtomSize = unsignedIntToLong(outputAtomSizeBuf);
                                                        byte[] outputAtomSizeBuf2 = outputAtomSizeBuf;
                                                        if (outputRAF.read(outputAtomNameBuf2, 0, outputAtomNameBuf2.length) < 0) {
                                                            LogS.m473d("TranscodeLib", "outputFile read is reached to end of the file");
                                                        }
                                                        String outputAtomName = new String(outputAtomNameBuf2, StandardCharsets.UTF_8);
                                                        int tmp = Arrays.binarySearch(outParentContainer2, outputAtomName);
                                                        if (tmp < 0) {
                                                            outputAtomNameBuf = outputAtomNameBuf2;
                                                            str3 = str6;
                                                            outParentContainer = outParentContainer2;
                                                            parentContainer = parentContainer3;
                                                            atomSize = atomSize2;
                                                            if (outputAtomSize == 1) {
                                                                outputRAF.seek(outputFilePointer + 8);
                                                                byte[] outputAtomLargeSizeBuf = new byte[8];
                                                                if (outputRAF.read(outputAtomLargeSizeBuf, 0, outputAtomLargeSizeBuf.length) < 0) {
                                                                    LogS.m473d("TranscodeLib", "outputfile read is reached to end of the file");
                                                                }
                                                                BigInteger outputAtomTmpLargeSize = new BigInteger(outputAtomLargeSizeBuf);
                                                                long outputAtomLargeSize = outputAtomTmpLargeSize.longValue();
                                                                outputFilePointer += outputAtomLargeSize;
                                                                LogS.m473d("TranscodeLib", "64bit: " + outputAtomLargeSize);
                                                            } else if (outputAtomSize == 0) {
                                                                break;
                                                            } else {
                                                                outputFilePointer += outputAtomSize;
                                                            }
                                                            atomSize2 = atomSize;
                                                            atomName = atomName2;
                                                            outputFile2 = outputFile;
                                                            outputAtomSizeBuf = outputAtomSizeBuf2;
                                                            outputAtomNameBuf2 = outputAtomNameBuf;
                                                            str6 = str3;
                                                            outParentContainer2 = outParentContainer;
                                                            parentContainer3 = parentContainer;
                                                        } else if (outputAtomName.equals(str6)) {
                                                            outputAtomNameBuf = outputAtomNameBuf2;
                                                            atomSize = atomSize2;
                                                            long l = outputAtomSize + atomSize;
                                                            str3 = str6;
                                                            byte[] b = new byte[4];
                                                            int i = 3;
                                                            while (i >= 0) {
                                                                b[i] = (byte) (l & 255);
                                                                l >>= 8;
                                                                i--;
                                                                outParentContainer2 = outParentContainer2;
                                                                parentContainer3 = parentContainer3;
                                                            }
                                                            outParentContainer = outParentContainer2;
                                                            parentContainer = parentContainer3;
                                                            long position = outputFilePointer;
                                                            outputRAF.seek(outputFilePointer);
                                                            outputRAF.write(b, 0, b.length);
                                                            outputRAF.seek(position);
                                                            outputFilePointer += 8;
                                                            atomSize2 = atomSize;
                                                            atomName = atomName2;
                                                            outputFile2 = outputFile;
                                                            outputAtomSizeBuf = outputAtomSizeBuf2;
                                                            outputAtomNameBuf2 = outputAtomNameBuf;
                                                            str6 = str3;
                                                            outParentContainer2 = outParentContainer;
                                                            parentContainer3 = parentContainer;
                                                        } else {
                                                            str3 = str6;
                                                            parentContainer = parentContainer3;
                                                            long atomSize3 = atomSize2;
                                                            long l2 = outputAtomSize + atomSize3;
                                                            byte[] b2 = new byte[4];
                                                            for (int i2 = 3; i2 >= 0; i2--) {
                                                                b2[i2] = (byte) (l2 & 255);
                                                                l2 >>= 8;
                                                            }
                                                            outputRAF.seek(outputFilePointer);
                                                            outputRAF.write(b2, 0, b2.length);
                                                            long positionAudio = outputFilePointer + outputAtomSize;
                                                            outputRAF.seek(positionAudio);
                                                            long audioSize = outputFileSize - positionAudio;
                                                            byte[] dataAudio = new byte[(int) audioSize];
                                                            outputRAF.seek(positionAudio);
                                                            if (outputRAF.read(dataAudio, 0, dataAudio.length) < 0) {
                                                                LogS.m473d("TranscodeLib", "outputfile read is reached to end of the file");
                                                            }
                                                            outputRAF.seek(positionAudio);
                                                            byte[] dataUuid = new byte[(int) atomSize3];
                                                            inputRAF.seek(filePointer);
                                                            if (inputRAF.read(dataUuid, 0, dataUuid.length) < 0) {
                                                                LogS.m473d("TranscodeLib", "inputfile read is reached to end of the file");
                                                            }
                                                            outputRAF.write(dataUuid, 0, dataUuid.length);
                                                            outputRAF.write(dataAudio, 0, dataAudio.length);
                                                        }
                                                    } catch (Throwable th5) {
                                                        th2 = th5;
                                                        outputRAF.close();
                                                        throw th2;
                                                    }
                                                }
                                            } else {
                                                str = str4;
                                                str2 = str5;
                                                str3 = str6;
                                                outputFile = outputFile2;
                                                parentContainer = parentContainer2;
                                            }
                                            filePointer += 8;
                                        } catch (Throwable th6) {
                                            th2 = th6;
                                        }
                                    } else {
                                        str = str4;
                                        str2 = str5;
                                        str3 = str6;
                                        outputFile = outputFile2;
                                        parentContainer = parentContainer2;
                                        if (atomSize2 == 1) {
                                            inputRAF.seek(filePointer + 8);
                                            byte[] atomLargeSizeBuf = new byte[8];
                                            if (inputRAF.read(atomLargeSizeBuf, 0, atomLargeSizeBuf.length) < 0) {
                                                LogS.m473d("TranscodeLib", "inputfile read is reached to end of the file");
                                            }
                                            BigInteger atomTmpLargeSize = new BigInteger(atomLargeSizeBuf);
                                            long atomLargeSize = atomTmpLargeSize.longValue();
                                            filePointer += atomLargeSize;
                                            LogS.m473d("TranscodeLib", "64bit: " + atomLargeSize);
                                        } else if (atomSize2 == 0) {
                                            break;
                                        } else {
                                            filePointer += atomSize2;
                                        }
                                    }
                                    atomSizeBuf = atomSizeBuf2;
                                    atomNameBuf = atomNameBuf2;
                                    size = size2;
                                    outputFile2 = outputFile;
                                    str4 = str;
                                    str5 = str2;
                                    str6 = str3;
                                    parentContainer2 = parentContainer;
                                }
                                outputRAF.close();
                                inputRAF.close();
                            } catch (Throwable th7) {
                                th2 = th7;
                            }
                        } catch (Throwable th8) {
                            th2 = th8;
                        }
                    } catch (Throwable th9) {
                        th = th9;
                        try {
                            inputRAF.close();
                            throw th;
                        } catch (Throwable th10) {
                            th.addSuppressed(th10);
                            throw th;
                        }
                    }
                } catch (Throwable th11) {
                    th = th11;
                }
            } catch (IOException e) {
                e = e;
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
        }
    }

    public boolean setOutputBitdepth(int bitDepth) {
        int inputBitDepth = mInputFileinfo.HDR10 ? 10 : 8;
        this.mConvert = false;
        LogS.m473d("TranscodeLib", "setOutputBitdepth  bitdepth : " + bitDepth + ", InputBitdepth : " + inputBitDepth + ", mHDRType : " + this.mHDRType);
        if (bitDepth != 8) {
            return false;
        }
        if (inputBitDepth == 8) {
            return true;
        }
        if (!isHDR10Plus() || !supportConverter()) {
            return false;
        }
        this.mConvert = true;
        return true;
    }

    private boolean supportConverter() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HDR2SDR");
    }

    private static boolean isNalStartCode(byte[] data, int index) {
        if (data.length - index <= NAL_START_CODE.length) {
            return false;
        }
        int j = 0;
        while (true) {
            byte[] bArr = NAL_START_CODE;
            if (j < bArr.length) {
                if (data[index + j] != bArr[j]) {
                    return false;
                }
                j++;
            } else {
                return true;
            }
        }
    }

    private static int findNalStartCode(byte[] data, int index) {
        int endIndex = data.length - NAL_START_CODE.length;
        for (int i = index; i <= endIndex; i++) {
            if (isNalStartCode(data, i)) {
                return i;
            }
        }
        return -1;
    }

    void checkInitialize(String outputFilePath, int outputWidth, int outputHeight, String inputFilePath, Context context, Uri inputUri, boolean rewrite) throws IOException {
        if (outputWidth <= 0 || outputHeight <= 0) {
            throw new IllegalArgumentException("invalid output size width: " + outputWidth + "height:" + outputHeight);
        }
        if (outputFilePath == null) {
            throw new IllegalArgumentException("output file path cannot be null");
        }
        if (this.mUseUri) {
            if (inputUri == null || context == null) {
                throw new IllegalArgumentException("can't use uri uri: " + inputUri + " context: " + context);
            }
        } else if (inputFilePath == null) {
            throw new IllegalArgumentException("input file path cannot be null");
        }
        try {
            mInputFileinfo = MediaInfo.getFileInfo(inputFilePath, context, inputUri);
            mInputVideoinfo = MediaInfo.getTrackInfo(inputFilePath, context, inputUri, true);
            mInputAudioinfo = MediaInfo.getTrackInfo(inputFilePath, context, inputUri, false);
            if (!MediaInfoChecker.isSupportedFileFormat(mInputFileinfo)) {
                throw new IOException("Not a valid video format.");
            }
            mInputFileinfo.Framerate = MediaInfo.getVideoFramerate();
            mInputFileinfo.VideoCodecType = mInputVideoinfo.getString(MediaFormat.KEY_MIME);
            this.mRecordingMode = mInputFileinfo.RecordingMode;
            this.mRecordingFps = mInputFileinfo.RecordingFramerate;
            if (mInputFileinfo.HDR10) {
                this.mHDRType = MediaInfoChecker.getHDRMode(mInputFileinfo);
            }
            if (SEFHelper.isSEFVideoMode(this.mRecordingMode)) {
                this.mNumOfSVCLayers = mInputFileinfo.NumOfSVCLayers;
                this.mSefhelper = new SEFHelper();
                this.mSefhelper.initialize(inputFilePath, context, inputUri);
                this.mOriginalduration = mInputFileinfo.Duration;
                if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                    this.mOriginalduration *= 2;
                }
                this.mSEFVideo = this.mSefhelper.checkSEFData(this.mRecordingMode, this.mRecordingFps, this.mOriginalduration);
                LogS.m474e("TranscodeLib", "checkSEFData mSEFVideo:" + this.mSEFVideo);
                if (this.mSEFVideo) {
                    this.mRegionList = this.mSefhelper.getRegionList();
                }
            }
            if (!checkVideoCodec(outputWidth, outputHeight, rewrite)) {
                throw new IOException("Not a valid video codec.");
            }
            this.mOutputFilePath = outputFilePath;
            this.mOutputWidth = outputWidth;
            this.mOutputHeight = outputHeight;
            if (this.mUseUri) {
                this.mInputUri = inputUri;
                this.mContext = context;
            } else {
                this.mInputFilePath = inputFilePath;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid input file - can't get file info");
        }
    }

    private void createAudiosolution() throws IOException {
        if (this.mSMEncode || this.mSMConvert) {
            try {
                this.mAudio = new AudioSolution();
            } catch (UnsatisfiedLinkError e) {
                throw new IOException("Not a valid audio solution.");
            }
        }
    }

    private int getVideoSampleSize(MediaFormat format) {
        if (format.getString(MediaFormat.KEY_MIME).startsWith(BnRConstants.VIDEO_DIR_PATH)) {
            int width = format.getInteger("width");
            int height = format.getInteger("height");
            return (int) (width * 1.2f * height);
        }
        return 0;
    }

    public void setExportRecordingMode(int exportrecordingmode) {
        this.mExportRecordingMode = exportrecordingmode;
        LogS.m473d("TranscodeLib", "setExportRecordingMode : " + this.mExportRecordingMode);
    }
}
