package com.samsung.android.transcode.core;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.text.TextUtils;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.transcode.constants.EncodeConstants;
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

/* loaded from: classes6.dex */
public class EncodeVideo extends EncodeBase {
    private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    private static final String UNKNOWN_AUDIO = "audio/unknown";
    private final Object mStopLock = new Object();

    public void initialize(String str, int i, int i2, String str2) throws IOException {
        this.mUseUri = false;
        checkInitialize(str, i, i2, str2, null, null, false);
        createAudiosolution();
    }

    public void initialize(String str, int i, int i2, String str2, boolean z, boolean z2) throws IOException {
        this.mUseUri = false;
        checkInitialize(str, i, i2, str2, null, null, z);
    }

    public void initialize(String str, int i, int i2, String str2, boolean z, boolean z2, int i3) throws IOException {
        this.mUseUri = false;
        checkInitialize(str, i, i2, str2, null, null, z);
        if (!z2 && i3 > 0) {
            this.mSourceFrameRate = i3;
            this.mKeepSourceFrameRate = true;
        }
        if (this.mSMConvert && !z) {
            this.mSMConvert = false;
            this.mSMEncode = true;
            LogS.d("TranscodeLib", "Do not support rewrite for Photoring case mSMConvert : " + this.mSMConvert + ", mSMEncode: " + this.mSMEncode);
        }
        createAudiosolution();
    }

    public void initialize(String str, int i, int i2, Context context, Uri uri) throws IOException {
        this.mUseUri = true;
        checkInitialize(str, i, i2, null, context, uri, false);
        createAudiosolution();
    }

    public void initialize(String str, int i, int i2, Context context, Uri uri, boolean z, boolean z2) throws IOException {
        this.mUseUri = true;
        checkInitialize(str, i, i2, null, context, uri, z);
    }

    public void initialize(String str, int i, int i2, Context context, Uri uri, boolean z, boolean z2, int i3) throws IOException {
        this.mUseUri = true;
        checkInitialize(str, i, i2, null, context, uri, z);
        if (!z2 && i3 > 0) {
            this.mSourceFrameRate = i3;
            this.mKeepSourceFrameRate = true;
        }
        if (this.mSMConvert && !z) {
            this.mSMConvert = false;
            this.mSMEncode = true;
            LogS.d("TranscodeLib", "Do not support rewrite for Photoring case mSMConvert : " + this.mSMConvert + ", mSMEncode: " + this.mSMEncode);
        }
        createAudiosolution();
    }

    public void setTrimTime(long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("start time cannot be negative");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("end time cannot be negative");
        }
        if (j > j2) {
            throw new IllegalArgumentException("start time cannot be more than end time");
        }
        if (j == j2) {
            throw new IllegalArgumentException("endUs cannot be equal to startUs");
        }
        long j3 = j * 1000;
        this.mOriginTrimStartUs = j3;
        long j4 = j2 * 1000;
        this.mOriginTrimEndUs = j4;
        if (this.mSEFVideo) {
            if (this.mRecordingMode == 1 || this.mRecordingMode == 2 || this.mRecordingMode == 12 || this.mRecordingMode == 21 || this.mRecordingMode == 19) {
                long slowfastSeektime = getSlowfastSeektime(j3);
                this.mTrimAudioStartUs = slowfastSeektime;
                this.mTrimVideoStartUs = slowfastSeektime;
                long slowfastSeektime2 = getSlowfastSeektime(j4);
                this.mTrimAudioEndUs = slowfastSeektime2;
                this.mTrimVideoEndUs = slowfastSeektime2;
            } else if (isSuperSlow()) {
                long superslowSeektime = getSuperslowSeektime(j3);
                this.mTrimAudioStartUs = superslowSeektime;
                this.mTrimVideoStartUs = superslowSeektime;
                long superslowSeektime2 = getSuperslowSeektime(j4);
                this.mTrimAudioEndUs = superslowSeektime2;
                this.mTrimVideoEndUs = superslowSeektime2;
            } else if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                this.mOriginTrimStartUs = j3;
                this.mOriginTrimEndUs = j4;
                long slowfastSeektime3 = getSlowfastSeektime(j3) / 2;
                this.mTrimAudioStartUs = slowfastSeektime3;
                this.mTrimVideoStartUs = slowfastSeektime3;
                long slowfastSeektime4 = getSlowfastSeektime(j4) / 2;
                this.mTrimAudioEndUs = slowfastSeektime4;
                this.mTrimVideoEndUs = slowfastSeektime4;
            }
        } else {
            this.mTrimAudioStartUs = j3;
            this.mTrimVideoStartUs = j3;
            this.mTrimAudioEndUs = j4;
            this.mTrimVideoEndUs = j4;
        }
        LogS.e("TranscodeLib", "Trim startUS: " + this.mTrimVideoStartUs + ", endUS: " + this.mTrimVideoEndUs + ", OriginstartUS: " + this.mOriginTrimStartUs + ", OriginendUS :" + this.mOriginTrimEndUs);
    }

    public void setEncodingCodecs(int i, int i2) {
        setVideoCodecs(i);
        setAudioCodecs(i2);
    }

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public void setVideoOutputBitRate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("bitRate should be positive");
        }
        this.mOutputVideoBitRate = i;
    }

    public void setVideoTargetFrameRate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("fps should be positive");
        }
        this.mOutputVideoTargetFrameRate = i;
    }

    public void setOutputAudioMute(int i) {
        LogS.d("TranscodeLib", "set audio mute : " + i);
        this.mOutputAudioMute = i != 0;
    }

    public void setAudioCodecs(int i) {
        if (i == 1) {
            this.mOutputAudioMimeType = "audio/3gpp";
        } else {
            if (i == 2) {
                this.mOutputAudioMimeType = "audio/mp4a-latm";
                return;
            }
            throw new IllegalArgumentException("Invalid audio codec");
        }
    }

    public void setVideoCodecs(int i) {
        if (i == 3) {
            this.mOutputVideoMimeType = "video/3gpp";
            return;
        }
        if (i == 4) {
            this.mOutputVideoMimeType = "video/avc";
        } else {
            if (i == 5) {
                this.mOutputVideoMimeType = "video/hevc";
                return;
            }
            LogS.e("TranscodeLib", "videoCodecType is: " + i);
            throw new IllegalArgumentException("Invalid video codec");
        }
    }

    public void setMaxOutputSize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("size cannot be 0 or lesser");
        }
        LogS.e("TranscodeLib", "max output size is " + i);
        this.mOutputMaxSizeKB = (long) i;
        this.mMMSMode = this.mOutputMaxSizeKB < 1000 || (this.mOutputWidth < 200 && this.mOutputHeight < 200);
        LogS.e("TranscodeLib", "mMMSMode is " + this.mMMSMode);
    }

    public void setOutputConfig(int i, int i2) {
        switch (i) {
            case 1:
                setVideoCodecs(i2);
                return;
            case 2:
                setAudioCodecs(i2);
                return;
            case 3:
                setMaxOutputSize(i2);
                return;
            case 4:
                setOutputBitdepth(i2);
                return;
            case 5:
                setVideoOutputBitRate(i2);
                return;
            case 6:
                setVideoTargetFrameRate(i2);
                return;
            case 7:
                setOutputAudioMute(i2);
                return;
            default:
                LogS.e("TranscodeLib", "configType is: " + i);
                throw new IllegalArgumentException("Invalid config Type");
        }
    }

    private void checkTrimVideoStartPointChanged() {
        if (this.mTrimVideoStartUs != 0) {
            if (this.mRewritable) {
                long j = this.mTrimVideoStartUs;
                this.mVideoExtractor.seekTo(this.mTrimVideoStartUs, 0);
                this.mTrimVideoStartUs = this.mVideoExtractor.getSampleTime();
                this.mAudioExtractor.seekTo(this.mTrimVideoStartUs, 0);
                this.mTrimAudioStartUs = this.mAudioExtractor.getSampleTime();
                long j2 = this.mTrimVideoEndUs;
                this.mTrimVideoEndUs -= j - this.mTrimVideoStartUs;
                LogS.i("TranscodeLib", "change end time for rewrite mode prev : " + j2 + " new : " + this.mTrimVideoEndUs);
                this.mVideoEncoderDone = true;
                this.mAudioEncoderDone = true;
                LogS.d("TranscodeLib", "Abandon Rewrite. Switch to Rewrite mode.");
                return;
            }
            return;
        }
        this.mVideoEncoderDone = true;
        this.mAudioEncoderDone = true;
        LogS.d("TranscodeLib", "Start point has not been updated!");
    }

    private void checkAudioTranscodeSection() {
        if ((this.mVideoEncoderDone && this.mAudioEncoderDone) || !this.mCopyAudio || this.mTrimAudioStartUs == 0) {
            return;
        }
        this.mAudioExtractor.seekTo(this.mTrimAudioStartUs, 0);
        while (this.mAudioExtractor.getSampleTime() < this.mTrimAudioStartUs) {
            LogS.d("TranscodeLib", "Advance audio...");
            this.mAudioExtractor.advance();
        }
        LogS.d("TranscodeLib", "Audio Transcode section: Current position: " + this.mAudioExtractor.getSampleTime() + " mTrimAudioStartUs: " + this.mTrimAudioStartUs);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008b  */
    @Override // com.samsung.android.transcode.core.Encode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void startRewriting() throws IOException {
        NalUnitParser nalUnitParser;
        ByteBuffer byteBuffer;
        if (this.mUserStop) {
            LogS.d("TranscodeLib", "Not starting encoding because it is stopped by user.");
            return;
        }
        LogS.d("TranscodeLib", "startRewriting");
        this.mVideoEncoderDone = false;
        this.mAudioEncoderDone = !this.mCopyAudio;
        this.mPendingAudioDecoderOutputBufferIndex = -1;
        int andSelectVideoTrackIndex = CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor);
        if (andSelectVideoTrackIndex != -1) {
            MediaFormat trackFormat = this.mVideoExtractor.getTrackFormat(andSelectVideoTrackIndex);
            if (!isHDR10() || this.mTrimVideoStartUs == 0) {
                nalUnitParser = null;
            } else {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(getVideoSampleSize(trackFormat));
                if (this.mVideoExtractor.readSampleData(byteBufferAllocate, 0) > 0) {
                    nalUnitParser = new NalUnitParser(byteBufferAllocate);
                    if (nalUnitParser.findHDRStaticMeta() && nalUnitParser.getHdrStaticMeta() != null) {
                        LogS.i("TranscodeLib", "has hdr static meta : " + this.mVideoExtractor.getSampleTime());
                    } else {
                        LogS.i("TranscodeLib", "fail to find hdr static meta " + this.mVideoExtractor.getSampleTime());
                        nalUnitParser = null;
                    }
                }
            }
            checkTrimVideoStartPointChanged();
            checkAudioTranscodeSection();
            LogS.d("TranscodeLib", "Rewriting starts");
            this.mAudioProgressTime = 0L;
            this.mVidioProgressTime = 0L;
            int andSelectAudioTrackIndex = CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor);
            MediaFormat trackFormat2 = andSelectAudioTrackIndex != -1 ? this.mAudioExtractor.getTrackFormat(andSelectAudioTrackIndex) : null;
            if (!this.mMuxerStarted) {
                String vEEditFilePath = this.mUseUri ? FileHelper.getVEEditFilePath(this.mContext, this.mInputUri) : this.mInputFilePath;
                LogS.d("TranscodeLib", "filepath :" + vEEditFilePath);
                if (updateCreationTime(vEEditFilePath, false)) {
                    trackFormat.setInteger("param-meta-author", 8);
                    trackFormat.setInteger("param-meta-transcoding", 1);
                }
                if (!TextUtils.isEmpty(mInputFileinfo.Writer)) {
                    trackFormat.setString("param-meta-brand-model-name", mInputFileinfo.Writer);
                }
                if (this.mExportRecordingMode != -1) {
                    trackFormat.setInteger("param-meta-recording-mode", this.mExportRecordingMode);
                    LogS.d("TranscodeLib", "set recording mode for NDE : " + this.mExportRecordingMode);
                } else if (this.mRecordingMode == 10 || this.mRecordingMode == 25) {
                    trackFormat.setInteger("param-meta-recording-mode", this.mRecordingMode);
                    LogS.d("TranscodeLib", "set recording mode for HDR 10 PLUS : " + this.mRecordingMode);
                } else if (this.mRecordingMode == 26 || this.mRecordingMode == 27) {
                    trackFormat.setInteger("param-meta-recording-mode", this.mRecordingMode);
                    LogS.e("TranscodeLib", "set recording mode for Log video : " + this.mRecordingMode);
                } else if (this.mRecordingMode == 29) {
                    trackFormat.setInteger("param-meta-recording-mode", this.mRecordingMode);
                    LogS.e("TranscodeLib", "set recording mode for MV_HEVC : " + this.mRecordingMode);
                    if ("video/hevc".equals(trackFormat.getString("mime"))) {
                        trackFormat.setString("mime", EncodeConstants.CodecsMime.VIDEO_CODEC_MVHEVC);
                    }
                    if (trackFormat.containsKey("csd-mvhevc-ext") && (byteBuffer = trackFormat.getByteBuffer("csd-mvhevc-ext")) != null) {
                        int iRemaining = byteBuffer.remaining();
                        byte[] bArr = new byte[iRemaining];
                        byteBuffer.get(bArr, 0, iRemaining);
                        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(iRemaining);
                        byteBufferAllocate2.put(bArr, 0, iRemaining);
                        byteBufferAllocate2.flip();
                        trackFormat.setByteBuffer("csd-1", byteBufferAllocate2);
                        trackFormat.removeKey("csd-mvhevc-ext");
                    }
                }
                this.mVideoTrackIndex = this.mMuxer.addTrack(trackFormat);
                if (trackFormat2 == null || UNKNOWN_AUDIO.equals(trackFormat2.getString("mime"))) {
                    andSelectAudioTrackIndex = -1;
                } else {
                    this.mAudioTrackIndex = this.mMuxer.addTrack(trackFormat2);
                }
                this.mMuxer.setOrientationHint(this.mInputOrientationDegrees);
                if (mInputFileinfo.IsLocationAvailable) {
                    this.mMuxer.setLocation(mInputFileinfo.latitude, mInputFileinfo.longitude);
                }
                this.mMuxer.start();
                this.mMuxerStarted = true;
            }
            rewriteVideo(this.mTrimVideoEndUs, nalUnitParser, getVideoSampleSize(trackFormat));
            if (andSelectAudioTrackIndex == -1 || this.mOutputAudioMute) {
                this.mCopyAudio = false;
            } else {
                rewriteAudio(this.mTrimVideoEndUs);
            }
            if (this.mUserStop) {
                return;
            }
            LogS.d("TranscodeLib", "Rewriting finished");
            return;
        }
        throw new IOException("Absent valid video track");
    }

    private void rewriteAudio(long j) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(131072);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.size = this.mAudioExtractor.readSampleData(byteBufferAllocate, 0);
        boolean z = false;
        while (!this.mUserStop && !z) {
            bufferInfo.offset = 0;
            bufferInfo.size = this.mAudioExtractor.readSampleData(byteBufferAllocate, 0);
            if (bufferInfo.size < 0) {
                LogS.d("TranscodeLib", "saw input EOS: Audio");
                bufferInfo.size = 0;
            } else {
                bufferInfo.presentationTimeUs = this.mAudioExtractor.getSampleTime();
                if (j != -1 && bufferInfo.presentationTimeUs > j) {
                    LogS.d("TranscodeLib", "sawEOS: true: A");
                } else {
                    bufferInfo.flags = this.mAudioExtractor.getSampleFlags();
                    try {
                        this.mMuxer.writeSampleData(this.mAudioTrackIndex, byteBufferAllocate, bufferInfo);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        LogS.e("TranscodeLib", "fail to writeSampleData " + e);
                    }
                    updateProgress(bufferInfo.presentationTimeUs, true);
                    this.mAudioExtractor.advance();
                }
            }
            z = true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00d8 A[Catch: IllegalArgumentException | IllegalStateException -> 0x00e0, TRY_LEAVE, TryCatch #0 {IllegalArgumentException | IllegalStateException -> 0x00e0, blocks: (B:25:0x00a1, B:27:0x00a6, B:29:0x00b1, B:30:0x00ca, B:32:0x00d8), top: B:40:0x00a1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void rewriteVideo(long j, NalUnitParser nalUnitParser, int i) {
        long j2;
        long jMax;
        int i2;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int i3 = 0;
        bufferInfo.size = this.mVideoExtractor.readSampleData(byteBufferAllocate, 0);
        long sampleTime = this.mVideoExtractor.getSampleTime();
        NalUnitParser nalUnitParser2 = nalUnitParser;
        boolean z = false;
        while (!this.mUserStop && !z) {
            bufferInfo.offset = i3;
            bufferInfo.size = this.mVideoExtractor.readSampleData(byteBufferAllocate, i3);
            if (bufferInfo.size < 0) {
                LogS.d("TranscodeLib", "saw input EOS: Video");
                bufferInfo.size = i3;
                z = true;
            } else {
                bufferInfo.presentationTimeUs = this.mVideoExtractor.getSampleTime();
                bufferInfo.flags = this.mVideoExtractor.getSampleFlags();
                this.mVideoExtractor.advance();
                long sampleTime2 = this.mVideoExtractor.getSampleTime();
                if (sampleTime2 != -1) {
                    j2 = sampleTime;
                    jMax = Math.max(sampleTime2 - bufferInfo.presentationTimeUs, 0L);
                } else {
                    j2 = sampleTime;
                    jMax = Math.max(((this.mSEFVideo ? mInputFileinfo.EditedDuration : mInputFileinfo.Duration) * 1000) - bufferInfo.presentationTimeUs, 0L);
                }
                if (j == -1 || bufferInfo.presentationTimeUs + jMax < j) {
                    if (bufferInfo.presentationTimeUs >= j2) {
                        if (nalUnitParser2 != null) {
                            try {
                                if ((bufferInfo.flags & 1) != 0) {
                                    if (!new NalUnitParser(byteBufferAllocate).findHDRStaticMeta()) {
                                        ByteBuffer byteBufferInsertHDRStaticMeta = nalUnitParser2.insertHDRStaticMeta(byteBufferAllocate, bufferInfo.size, CodecsHelper.isHevcFormat(mInputVideoinfo));
                                        LogS.i("TranscodeLib", "add HDR static info");
                                        this.mMuxer.writeSampleData(this.mVideoTrackIndex, byteBufferInsertHDRStaticMeta, bufferInfo);
                                    } else {
                                        LogS.i("TranscodeLib", "has already static info");
                                        this.mMuxer.writeSampleData(this.mVideoTrackIndex, byteBufferAllocate, bufferInfo);
                                    }
                                    nalUnitParser2 = null;
                                } else {
                                    this.mMuxer.writeSampleData(this.mVideoTrackIndex, byteBufferAllocate, bufferInfo);
                                }
                            } catch (IllegalArgumentException | IllegalStateException e) {
                                LogS.e("TranscodeLib", "fail to writeSampleData " + e);
                            }
                            i2 = 0;
                            updateProgress(bufferInfo.presentationTimeUs, false);
                        }
                    }
                    i3 = i2;
                    sampleTime = j2;
                } else {
                    LogS.d("TranscodeLib", "sawEOS: true: V");
                    z = true;
                }
                i2 = 0;
                i3 = i2;
                sampleTime = j2;
            }
        }
    }

    @Override // com.samsung.android.transcode.core.Encode
    public void startSMEncoding() throws IOException {
        if (this.mUserStop) {
            LogS.d("TranscodeLib", "Not starting Slowmotion encoding because it is stopped by user.");
            return;
        }
        LogS.i("TranscodeLib", "startSMEncoding");
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
        LogS.d("TranscodeLib", "Transcode section - Audio : Current position: " + this.mAudioExtractor.getSampleTime() + " mTrimAudioStartUs: " + this.mTrimAudioStartUs + "Video: " + this.mVideoExtractor.getSampleTime() + " mTrimVideoStartUs: " + this.mTrimVideoStartUs);
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
        LogS.d("TranscodeLib", "Encoding abruptly stopped mUserStop ?" + this.mUserStop + " mCodecError? " + this.mCodecError);
    }

    private void checkTrimAudioStartPos() {
        long j = this.mTrimVideoStartUs;
        if (this.mTrimVideoStartUs != 0) {
            this.mVideoExtractor.seekTo(this.mTrimVideoStartUs, 0);
            this.mTrimVideoStartUs = this.mVideoExtractor.getSampleTime();
        }
        if (this.mSEFVideo && this.mTrimVideoStartUs != j && this.mTrimVideoEndUs > 0) {
            LogS.d("TranscodeLib", "checkTrimAudfioStartPos prev : " + j + " ~ " + this.mTrimVideoEndUs);
            long convertedTime = (this.mOriginTrimEndUs - (this.mSefhelper.getConvertedTime(j) - this.mSefhelper.getConvertedTime(this.mTrimVideoStartUs))) / 1000;
            if (this.mRecordingMode == 1 || this.mRecordingMode == 2 || this.mRecordingMode == 12 || this.mRecordingMode == 21 || this.mRecordingMode == 19) {
                long slowfastSeektime = getSlowfastSeektime(convertedTime * 1000);
                this.mTrimAudioEndUs = slowfastSeektime;
                this.mTrimVideoEndUs = slowfastSeektime;
            } else if (isSuperSlow()) {
                long superslowSeektime = getSuperslowSeektime(convertedTime * 1000);
                this.mTrimAudioEndUs = superslowSeektime;
                this.mTrimVideoEndUs = superslowSeektime;
            } else if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                long slowfastSeektime2 = getSlowfastSeektime(convertedTime * 1000) / 2;
                this.mTrimAudioEndUs = slowfastSeektime2;
                this.mTrimVideoEndUs = slowfastSeektime2;
            }
            LogS.d("TranscodeLib", "checkTrimAudfioStartPos after : " + this.mTrimVideoStartUs + " ~ " + this.mTrimVideoEndUs);
        } else {
            LogS.d("TranscodeLib", "Video  section: Current position: " + this.mTrimVideoStartUs);
        }
        if (!this.mCopyAudio || this.mTrimAudioStartUs == 0) {
            return;
        }
        this.mAudioExtractor.seekTo(this.mTrimVideoStartUs, 0);
        while (this.mAudioExtractor.getSampleTime() < this.mTrimVideoStartUs) {
            if (this.mAudioExtractor.getSampleTime() == -1) {
                throw new RuntimeException("Invalid File!");
            }
            this.mAudioExtractor.advance();
        }
        LogS.d("TranscodeLib", "Audio Transcode section: Current position: " + this.mAudioExtractor.getSampleTime() + " mTrimAudioStartUs: " + this.mTrimAudioStartUs);
    }

    private MediaFormat checkFormatV(MediaFormat mediaFormat) {
        ByteBuffer byteBuffer;
        if (mediaFormat == null) {
            return null;
        }
        if (updateCreationTime(this.mUseUri ? FileHelper.getVEEditFilePath(this.mContext, this.mInputUri) : this.mInputFilePath, false)) {
            mediaFormat.setInteger("param-meta-author", 8);
            mediaFormat.setInteger("param-meta-transcoding", 1);
            if (this.mExportRecordingMode != -1) {
                this.mVideoEncoderOutputMediaFormat.setInteger("param-meta-recording-mode", this.mExportRecordingMode);
                LogS.d("TranscodeLib", "set recording mode for NDE : " + this.mExportRecordingMode);
            }
        }
        if (!TextUtils.isEmpty(mInputFileinfo.Writer)) {
            mediaFormat.setString("param-meta-brand-model-name", mInputFileinfo.Writer);
        }
        if (CodecsHelper.isHevcFormat(mInputVideoinfo) && "video/hevc".equals(this.mOutputVideoMimeType)) {
            mediaFormat.setInteger("level", mInputVideoinfo.getInteger("level"));
            return mediaFormat;
        }
        mediaFormat.setInteger("level", this.mOutputWidth == 1280 ? 512 : 4096);
        if (mediaFormat.containsKey("csd-0") && (byteBuffer = mediaFormat.getByteBuffer("csd-0")) != null) {
            int iRemaining = byteBuffer.remaining();
            byte[] bArr = new byte[iRemaining];
            byteBuffer.get(bArr, 0, iRemaining);
            if (this.mOutputWidth == 1280) {
                bArr[7] = SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN;
            } else {
                bArr[7] = 41;
            }
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iRemaining);
            byteBufferAllocate.put(bArr, 0, iRemaining);
            byteBufferAllocate.flip();
            mediaFormat.setByteBuffer("csd-0", byteBufferAllocate);
        }
        return mediaFormat;
    }

    @Override // com.samsung.android.transcode.core.Encode
    public void startSMRewriting() throws IOException {
        long j;
        int i;
        long j2;
        char c;
        if (this.mUserStop) {
            LogS.d("TranscodeLib", "Not starting encoding because it is stopped by user.");
            return;
        }
        LogS.i("TranscodeLib", "startSMRewriting");
        initialize_audio();
        int i2 = 0;
        this.mIsDrop = false;
        long j3 = this.mOriginTrimEndUs;
        checkTrimAudioStartPos();
        LogS.d("TranscodeLib", "Rewriting starts");
        if (isSlowV2()) {
            this.mTimescale = 0.0f;
        }
        int andSelectVideoTrackIndex = CodecsHelper.getAndSelectVideoTrackIndex(this.mVideoExtractor);
        int andSelectAudioTrackIndex = this.mCopyAudio ? CodecsHelper.getAndSelectAudioTrackIndex(this.mAudioExtractor) : -1;
        long j4 = 0;
        this.mAudioProgressTime = 0L;
        this.mVidioProgressTime = 0L;
        if (andSelectVideoTrackIndex != -1) {
            MediaFormat trackFormat = this.mVideoExtractor.getTrackFormat(andSelectVideoTrackIndex);
            MediaFormat trackFormat2 = andSelectAudioTrackIndex != -1 ? this.mAudioExtractor.getTrackFormat(andSelectAudioTrackIndex) : null;
            boolean z = true;
            if (!this.mMuxerStarted) {
                trackFormat = checkFormatV(trackFormat);
                LogS.d("TranscodeLib", "video format " + trackFormat);
                this.mVideoTrackIndex = this.mMuxer.addTrack(trackFormat);
                if (trackFormat2 == null || UNKNOWN_AUDIO.equals(trackFormat2.getString("mime"))) {
                    andSelectAudioTrackIndex = -1;
                } else {
                    if (isSlowV2() && trackFormat2.containsKey("csd-0")) {
                        ByteBuffer byteBuffer = trackFormat2.getByteBuffer("csd-0");
                        int iRemaining = byteBuffer.remaining();
                        byte[] bArr = new byte[iRemaining];
                        byteBuffer.get(bArr, 0, iRemaining);
                        bArr[0] = 17;
                        bArr[1] = MidiConstants.STATUS_NOTE_ON;
                        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iRemaining);
                        byteBufferAllocate.put(bArr, 0, iRemaining);
                        byteBufferAllocate.flip();
                        trackFormat2.setByteBuffer("csd-0", byteBufferAllocate);
                    }
                    LogS.d("TranscodeLib", "audio format " + trackFormat2);
                    this.mAudioTrackIndex = this.mMuxer.addTrack(trackFormat2);
                }
                this.mMuxer.setOrientationHint(this.mInputOrientationDegrees);
                this.mMuxer.start();
                this.mMuxerStarted = true;
            }
            if (andSelectAudioTrackIndex == -1 || this.mOutputAudioMute) {
                this.mAudioEncoderDone = true;
                this.mAudioExtractorDone = true;
                this.mCopyAudio = false;
            }
            if (andSelectAudioTrackIndex != -1) {
                while (!this.mUserStop && !this.mAudioEncoderDone) {
                    startAudioEncoding();
                }
            }
            ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(getVideoSampleSize(trackFormat));
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.size = this.mVideoExtractor.readSampleData(byteBufferAllocate2, 0);
            boolean zIsHevcFormat = CodecsHelper.isHevcFormat(trackFormat);
            boolean z2 = false;
            long j5 = 0;
            while (!this.mUserStop && !z2) {
                bufferInfo.offset = i2;
                bufferInfo.size = this.mVideoExtractor.readSampleData(byteBufferAllocate2, i2);
                if (bufferInfo.size < 0) {
                    LogS.d("TranscodeLib", "saw input EOS: Video");
                    bufferInfo.size = i2;
                    z2 = z;
                } else {
                    long j6 = j4;
                    long sampleTime = this.mVideoExtractor.getSampleTime();
                    this.mModifiedVideotime = sampleTime;
                    LogS.d("TranscodeLib", "mModifiedVideotime = presentationTime = " + this.mModifiedVideotime);
                    if (this.mSEFVideo) {
                        byte[] bArr2 = new byte[4];
                        byteBufferAllocate2.position(4);
                        byteBufferAllocate2.get(bArr2, i2, 4);
                        byteBufferAllocate2.position(i2);
                        this.mIsDrop = calculateIsDrop(bArr2, sampleTime);
                    }
                    bufferInfo.presentationTimeUs = this.mVideoExtractor.getSampleTime();
                    long j7 = j5 != j6 ? this.mModifiedVideotime - j5 : j6;
                    boolean z3 = zIsHevcFormat;
                    if (j3 != -1 && bufferInfo.presentationTimeUs + j7 > this.mTrimVideoEndUs) {
                        LogS.d("TranscodeLib", "sawEOS: true: V");
                        j2 = j3;
                        j = j5;
                        c = 65535;
                        z2 = true;
                        i = i2;
                    } else {
                        bufferInfo.presentationTimeUs = this.mModifiedVideotime;
                        bufferInfo.flags = this.mVideoExtractor.getSampleFlags();
                        j = this.mModifiedVideotime;
                        if (this.mIsDrop) {
                            i = i2;
                            j2 = j3;
                            c = 65535;
                        } else {
                            int iRemaining2 = byteBufferAllocate2.remaining();
                            byte[] bArr3 = new byte[iRemaining2];
                            byteBufferAllocate2.get(bArr3, i2, iRemaining2);
                            StringBuilder sb = new StringBuilder("writeSampleData time:");
                            j2 = j3;
                            sb.append(bufferInfo.presentationTimeUs);
                            sb.append(" length=");
                            sb.append(iRemaining2);
                            LogS.d("TranscodeLib", sb.toString());
                            int i3 = 0;
                            if (!z3) {
                                while (true) {
                                    int iFindNalStartCode = findNalStartCode(bArr3, NAL_START_CODE.length + i3);
                                    LogS.d("TranscodeLib", "findNalStartCode. i: " + iFindNalStartCode + ", index: " + i3);
                                    if (iFindNalStartCode == -1) {
                                        break;
                                    } else {
                                        i3 = iFindNalStartCode;
                                    }
                                }
                            }
                            byteBufferAllocate2.position(i3);
                            bufferInfo.offset = i3;
                            try {
                                this.mMuxer.writeSampleData(this.mVideoTrackIndex, byteBufferAllocate2, bufferInfo);
                            } catch (IllegalArgumentException | IllegalStateException e) {
                                LogS.e("TranscodeLib", "fail to writeSampleData " + e);
                            }
                            c = 65535;
                            if (andSelectAudioTrackIndex == -1) {
                                this.mPausedVideoUs = bufferInfo.presentationTimeUs;
                            }
                            i = 0;
                            updateProgress(bufferInfo.presentationTimeUs, false);
                        }
                        this.mVideoExtractor.advance();
                    }
                    zIsHevcFormat = z3;
                    i2 = i;
                    j3 = j2;
                    j5 = j;
                    j4 = j6;
                    z = true;
                }
            }
            if (this.mUserStop) {
                return;
            }
            LogS.d("TranscodeLib", "Rewriting finished");
            return;
        }
        throw new IOException("Absent valid video track");
    }

    private void releaseVideoObjects() {
        try {
        } catch (Exception unused) {
            LogS.e("TranscodeLib", "Exception in releasing output video encoder.");
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
        } catch (Exception unused2) {
            LogS.e("TranscodeLib", "Exception in releasing input video decoder.");
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
            } catch (Exception unused3) {
                LogS.e("TranscodeLib", "Exception in releasing video extractor.");
            }
        }
    }

    private void releaseSurfaceObjects() {
        if (this.mOutputSurface != null) {
            try {
                this.mOutputSurface.release();
                this.mOutputSurface = null;
            } catch (Exception unused) {
                LogS.e("TranscodeLib", "Exception in releasing outputSurface.");
            }
        }
        if (this.mInputSurface != null) {
            try {
                this.mInputSurface.release();
                this.mInputSurface = null;
            } catch (Exception unused2) {
                LogS.e("TranscodeLib", "Exception in releasing input surface.");
            }
        }
    }

    private void releaseAudioObjects() {
        if (this.mOutputAudioEncoder != null) {
            try {
                this.mOutputAudioEncoder.stop();
                this.mOutputAudioEncoder.release();
                this.mOutputAudioEncoder = null;
            } catch (Exception unused) {
                LogS.e("TranscodeLib", "Exception in releasing output audio encoder.");
            }
        }
        if (this.mInputAudioDecoder != null) {
            try {
                this.mInputAudioDecoder.stop();
                this.mInputAudioDecoder.release();
                this.mInputAudioDecoder = null;
            } catch (Exception unused2) {
                LogS.e("TranscodeLib", "Exception in releasing input audio decoder.");
            }
        }
        if (this.mAudioExtractor != null) {
            try {
                this.mAudioExtractor.release();
                this.mAudioExtractor = null;
            } catch (Exception unused3) {
                LogS.e("TranscodeLib", "Exception in releasing audio extractor.");
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
            } catch (Exception unused) {
                LogS.e("TranscodeLib", "Exception in releasing muxer.");
            }
        }
    }

    private void releaseHandleObjects() {
        if (sSRCHandle != 0) {
            this.mAudio.SRCDestroy(sSRCHandle);
            sSRCHandle = 0L;
            LogS.d("TranscodeLib", " SRC release end ");
        }
        if (sVSPHandle != 0) {
            this.mAudio.VSPDestroy(sVSPHandle);
            sVSPHandle = 0L;
            LogS.d("TranscodeLib", " VSP release end ");
        }
        if (sNAACHandle != 0) {
            this.mAudio.NAACEncoderDeInit(sNAACHandle);
            sNAACHandle = 0L;
            LogS.d("TranscodeLib", " NAAC release end ");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0044 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.samsung.android.transcode.core.Encode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected synchronized void release() {
        try {
            LogS.e("TranscodeLib", "releasing encoder objects");
            releaseFramemanager();
            releaseVideoObjects();
            releaseSurfaceObjects();
            releaseAudioObjects();
            releaseMuxer();
            releaseHandleObjects();
            if (this.mEncoding && this.mUpdateCreationTime) {
                updateCreationTime(this.mOutputFilePath, true);
            }
            if (this.mIs360Video) {
                insertUuidFor360Video(this.mInputFilePath, this.mOutputFilePath);
            }
            if (this.mDecAudio != null) {
                this.mDecAudio.clear();
                this.mDecAudio = null;
                synchronized (this.mStopLock) {
                    this.mEncoding = false;
                    this.mPrepared = false;
                    this.mStopLock.notifyAll();
                }
            }
        } catch (Throwable th) {
            synchronized (this.mStopLock) {
                this.mEncoding = false;
                this.mPrepared = false;
                this.mStopLock.notifyAll();
                throw th;
            }
        }
    }

    @Override // com.samsung.android.transcode.core.Encode
    public void stop() {
        LogS.d("TranscodeLib", "Stop method called ");
        synchronized (this.mStopLock) {
            if (this.mOutputSurface != null) {
                this.mOutputSurface.notifyFrameSyncObject();
            }
            this.mUserStop = true;
            LogS.d("TranscodeLib", "mUserStop - true");
            if (this.mEncoding) {
                try {
                    try {
                        LogS.d("TranscodeLib", "Calling wait on stop lock.");
                        this.mStopLock.wait(5000L);
                        LogS.d("TranscodeLib", "Stop method finally  mEncoding :" + this.mEncoding);
                    } catch (InterruptedException e) {
                        LogS.d("TranscodeLib", "Stop lock interrupted.");
                        e.printStackTrace();
                        LogS.d("TranscodeLib", "Stop method finally  mEncoding :" + this.mEncoding);
                        if (this.mEncoding) {
                        }
                    }
                    if (this.mEncoding) {
                        release();
                    }
                } catch (Throwable th) {
                    LogS.d("TranscodeLib", "Stop method finally  mEncoding :" + this.mEncoding);
                    if (this.mEncoding) {
                        release();
                    }
                    throw th;
                }
            }
        }
    }

    public static int getMaxEncodingDuration(int i, int i2, int i3, int i4) {
        long j = (((int) (i * 0.7f)) * 8192) / ((i4 == 1 ? 8 : (i < 1000 || (i2 < 200 && i3 < 200)) ? 64 : 128) + r0);
        LogS.d("TranscodeLib", "Size " + i + " width " + i2 + " height " + i3 + " minBitRate : " + CodecsHelper.getVideoMinBitrate(i2, i3) + " audiocodec " + i4 + " maxdur " + j);
        return (int) Math.max(1000L, j);
    }

    public int getOutputFileSize() {
        MediaExtractor mediaExtractorCreateExtractor;
        int iSuggestBitrate;
        long j;
        try {
            if (this.mUseUri) {
                mediaExtractorCreateExtractor = CodecsHelper.createExtractor(this.mContext, this.mInputUri);
            } else {
                mediaExtractorCreateExtractor = CodecsHelper.createExtractor(this.mInputFilePath);
            }
            MediaFormat trackFormat = mediaExtractorCreateExtractor.getTrackFormat(CodecsHelper.getAndSelectVideoTrackIndex(mediaExtractorCreateExtractor));
            long j2 = this.mTrimVideoEndUs;
            if (j2 == 0) {
                if (this.mSEFVideo) {
                    j = mInputFileinfo.EditedDuration * 1000;
                } else {
                    j = trackFormat.getLong(MediaFormat.KEY_DURATION);
                }
                j2 = j;
                LogS.d("TranscodeLib", "getOutputFileSize  trimEndTime was 0 but updated trimEndTime : " + j2);
            }
            mediaExtractorCreateExtractor.release();
            if (this.mOutputMaxSizeKB >= 0) {
                if ("video/avc".equals(this.mOutputVideoMimeType)) {
                    this.mSizeFraction = 0.9f;
                }
                iSuggestBitrate = CodecsHelper.getVideoEncodingBitRate(this.mSizeFraction, this.mOutputMaxSizeKB, (j2 - this.mTrimVideoStartUs) / 1000, this.mOutputAudioBitRate / 1000, this.mOutputWidth, this.mOutputHeight) * 1000;
            } else {
                iSuggestBitrate = CodecsHelper.suggestBitrate(new ExportMediaInfo(this.mOutputWidth, this.mOutputHeight, this.mOutputVideoFrameRate, this.mOutputVideoMimeType, isHDR10Plus()), mInputFileinfo);
            }
            int i = (int) (((j2 - this.mTrimVideoStartUs) / 8000000.0d) * ((iSuggestBitrate + this.mOutputAudioBitRate) / 1000.0d));
            return this.mOutputMaxSizeKB == 0 ? (int) (i * 0.9d) : i;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    private boolean checkVideoCodec(int i, int i2, boolean z) {
        if (!z) {
            if (!MediaInfoChecker.isSupportedResolution(mInputVideoinfo, mInputFileinfo.Width, mInputFileinfo.Height, i, i2)) {
                LogS.d("TranscodeLib", "isSupportedResolution  Error appear : 0");
                return false;
            }
            if (!MediaInfoChecker.isSupportedCodecType(mInputVideoinfo)) {
                LogS.d("TranscodeLib", "isSupportedCodecType video  Error appear : 0");
                return false;
            }
        }
        if (mInputFileinfo.Width <= 0 || mInputFileinfo.Height <= 0) {
            LogS.d("TranscodeLib", "Resolution Error appear : width = " + mInputFileinfo.Width + ", height= " + mInputFileinfo.Height);
            return false;
        }
        this.mSMConvert = false;
        this.mSMEncode = false;
        if (this.mSEFVideo && isSlowFast() && mInputFileinfo.Width == i && mInputFileinfo.Height == i2 && this.mOutputVideoMimeType.equals(mInputVideoinfo.getString("mime"))) {
            this.mSMConvert = true;
            LogS.d("TranscodeLib", "Slowmotion Converting case  mSMConvert");
            return true;
        }
        if (this.mSEFVideo && isSlowV2()) {
            this.mSMEncode = true;
            LogS.d("TranscodeLib", "Slowmotion V2 transcoding case mSMEncode");
        }
        return true;
    }

    public static boolean findAtom(String str, String str2) throws IOException {
        boolean z;
        boolean z2 = false;
        if (str == null) {
            LogS.d("TranscodeLib", "findAtom : filepath is null");
            return false;
        }
        File file = new File(str);
        int i = 4;
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        long length = file.length();
        LogS.d("TranscodeLib", "file size: " + length);
        String[] strArr = {"mdia", "minf", "moov", "stbl", "trak"};
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        long j = 0;
        while (true) {
            if (j >= length) {
                Object[] objArr = z2 ? 1 : 0;
                break;
            }
            try {
                try {
                    LogS.d("TranscodeLib", "filePointer: " + j);
                    randomAccessFile.seek(j);
                } finally {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (randomAccessFile.read(bArr, z2 ? 1 : 0, i) < 0) {
                LogS.d("TranscodeLib", "file read is reached to end of the file");
            }
            long jUnsignedIntToLong = unsignedIntToLong(bArr);
            LogS.d("TranscodeLib", "Atom Size: " + jUnsignedIntToLong);
            if (randomAccessFile.read(bArr2, 0, i) < 0) {
                LogS.d("TranscodeLib", "file read is reached to end of the file");
            }
            String str3 = new String(bArr2, StandardCharsets.UTF_8);
            LogS.d("TranscodeLib", "Atom Box: " + str3);
            int iBinarySearch = Arrays.binarySearch(strArr, str3);
            if (iBinarySearch >= 0) {
                LogS.d("TranscodeLib", "Found parent: " + str3 + " move to : " + iBinarySearch);
                j += 8;
                z = false;
            } else {
                if (str3.equals(str2)) {
                    LogS.d("TranscodeLib", "Found: " + str2);
                    z2 = true;
                    break;
                }
                if (jUnsignedIntToLong == 1) {
                    randomAccessFile.seek(j + 8);
                    byte[] bArr3 = new byte[8];
                    z = false;
                    if (randomAccessFile.read(bArr3, 0, 8) < 0) {
                        LogS.d("TranscodeLib", "file read is reached to end of the file");
                    }
                    long jLongValue = new BigInteger(bArr3).longValue();
                    j += jLongValue;
                    LogS.d("TranscodeLib", "64bit: " + jLongValue);
                } else {
                    z = false;
                    if (jUnsignedIntToLong == 0) {
                        LogS.d("TranscodeLib", "filePointer does not go forward. Exit.");
                        z2 = false;
                        break;
                    }
                    j += jUnsignedIntToLong;
                    LogS.d("TranscodeLib", "move: " + j + " atomsize " + jUnsignedIntToLong);
                }
            }
            z2 = z;
            i = 4;
        }
        randomAccessFile.close();
        return z2;
    }

    public static boolean isSupportedFormat(String str) {
        return CodecsHelper.isSupportedFormat(str);
    }

    public static boolean isSupportedFormat(Context context, Uri uri) {
        return CodecsHelper.isSupportedFormat(context, uri);
    }

    public static void insertUuidFor360Video(String str, String str2) throws IOException {
        String str3;
        String str4;
        byte[] bArr;
        String str5;
        String str6;
        long j;
        String str7;
        String str8 = "uuid";
        String str9 = "trak";
        String str10 = "moov";
        LogS.d("TranscodeLib", "insertUuidFor360Video");
        File file = new File(str);
        File file2 = new File(str2);
        long length = file.length();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            try {
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file2, "rws");
                try {
                    String[] strArr = {"moov", "trak", "uuid"};
                    int i = 4;
                    byte[] bArr2 = new byte[4];
                    byte[] bArr3 = new byte[4];
                    long j2 = 0;
                    while (j2 < length) {
                        randomAccessFile.seek(j2);
                        if (randomAccessFile.read(bArr2, 0, i) < 0) {
                            LogS.d("TranscodeLib", "inputfile read is reached to end of the file");
                        }
                        File file3 = file2;
                        long j3 = length;
                        long jUnsignedIntToLong = unsignedIntToLong(bArr2);
                        if (randomAccessFile.read(bArr3, 0, 4) < 0) {
                            LogS.d("TranscodeLib", "inputfile read is reached to end of the file");
                        }
                        String str11 = new String(bArr3, StandardCharsets.UTF_8);
                        String[] strArr2 = strArr;
                        byte[] bArr4 = bArr2;
                        if (Arrays.binarySearch(strArr, str11) >= 0) {
                            if (str11.equals(str8)) {
                                long length2 = file3.length();
                                String[] strArr3 = {str10, str9};
                                int i2 = 4;
                                int i3 = 8;
                                byte[] bArr5 = new byte[4];
                                str3 = str8;
                                byte[] bArr6 = new byte[4];
                                String str12 = "64bit: ";
                                String str13 = "inputfile read is reached to end of the file";
                                long j4 = 0;
                                while (j4 < length2) {
                                    randomAccessFile2.seek(j4);
                                    str4 = str9;
                                    if (randomAccessFile2.read(bArr5, 0, i2) < 0) {
                                        LogS.d("TranscodeLib", "outputFile read is reached to end of the file");
                                    }
                                    long jUnsignedIntToLong2 = unsignedIntToLong(bArr5);
                                    byte[] bArr7 = bArr5;
                                    if (randomAccessFile2.read(bArr6, 0, 4) < 0) {
                                        LogS.d("TranscodeLib", "outputFile read is reached to end of the file");
                                    }
                                    String str14 = new String(bArr6, StandardCharsets.UTF_8);
                                    if (Arrays.binarySearch(strArr3, str14) >= 0) {
                                        int i4 = 3;
                                        if (str14.equals(str10)) {
                                            long j5 = jUnsignedIntToLong2 + jUnsignedIntToLong;
                                            byte[] bArr8 = new byte[4];
                                            while (i4 >= 0) {
                                                int i5 = i4;
                                                bArr8[i5] = (byte) (j5 & 255);
                                                j5 >>= i3;
                                                i4 = i5 - 1;
                                                bArr3 = bArr3;
                                            }
                                            bArr = bArr3;
                                            randomAccessFile2.seek(j4);
                                            randomAccessFile2.write(bArr8, 0, 4);
                                            randomAccessFile2.seek(j4);
                                            j4 += 8;
                                            str5 = str10;
                                            j = jUnsignedIntToLong;
                                            str6 = str13;
                                            str7 = str12;
                                            str13 = str6;
                                            str12 = str7;
                                            str10 = str5;
                                            str9 = str4;
                                            bArr5 = bArr7;
                                            jUnsignedIntToLong = j;
                                            bArr3 = bArr;
                                            i2 = 4;
                                            i3 = 8;
                                        } else {
                                            bArr = bArr3;
                                            long j6 = jUnsignedIntToLong2 + jUnsignedIntToLong;
                                            byte[] bArr9 = new byte[4];
                                            while (i4 >= 0) {
                                                bArr9[i4] = (byte) (r22 & 255);
                                                j6 >>= i3;
                                                i4--;
                                            }
                                            randomAccessFile2.seek(j4);
                                            randomAccessFile2.write(bArr9, 0, 4);
                                            long j7 = j4 + jUnsignedIntToLong2;
                                            randomAccessFile2.seek(j7);
                                            str5 = str10;
                                            int i6 = (int) (length2 - j7);
                                            byte[] bArr10 = new byte[i6];
                                            randomAccessFile2.seek(j7);
                                            if (randomAccessFile2.read(bArr10, 0, i6) < 0) {
                                                LogS.d("TranscodeLib", "outputfile read is reached to end of the file");
                                            }
                                            randomAccessFile2.seek(j7);
                                            int i7 = (int) jUnsignedIntToLong;
                                            byte[] bArr11 = new byte[i7];
                                            randomAccessFile.seek(j2);
                                            if (randomAccessFile.read(bArr11, 0, i7) < 0) {
                                                LogS.d("TranscodeLib", str13);
                                            }
                                            randomAccessFile2.write(bArr11, 0, i7);
                                            randomAccessFile2.write(bArr10, 0, i6);
                                        }
                                    } else {
                                        bArr = bArr3;
                                        str6 = str13;
                                        str5 = str10;
                                        if (jUnsignedIntToLong2 == 1) {
                                            j = jUnsignedIntToLong;
                                            randomAccessFile2.seek(j4 + 8);
                                            int i8 = i3;
                                            byte[] bArr12 = new byte[i8];
                                            if (randomAccessFile2.read(bArr12, 0, i8) < 0) {
                                                LogS.d("TranscodeLib", "outputfile read is reached to end of the file");
                                            }
                                            long jLongValue = new BigInteger(bArr12).longValue();
                                            j4 += jLongValue;
                                            StringBuilder sb = new StringBuilder();
                                            str7 = str12;
                                            sb.append(str7);
                                            sb.append(jLongValue);
                                            LogS.d("TranscodeLib", sb.toString());
                                        } else {
                                            j = jUnsignedIntToLong;
                                            str7 = str12;
                                            if (jUnsignedIntToLong2 == 0) {
                                                break;
                                            } else {
                                                j4 += jUnsignedIntToLong2;
                                            }
                                        }
                                        str13 = str6;
                                        str12 = str7;
                                        str10 = str5;
                                        str9 = str4;
                                        bArr5 = bArr7;
                                        jUnsignedIntToLong = j;
                                        bArr3 = bArr;
                                        i2 = 4;
                                        i3 = 8;
                                    }
                                }
                            } else {
                                str3 = str8;
                            }
                            str4 = str9;
                            bArr = bArr3;
                            str5 = str10;
                            j2 += 8;
                        } else {
                            str3 = str8;
                            str4 = str9;
                            bArr = bArr3;
                            str5 = str10;
                            if (jUnsignedIntToLong == 1) {
                                randomAccessFile.seek(j2 + 8);
                                byte[] bArr13 = new byte[8];
                                if (randomAccessFile.read(bArr13, 0, 8) < 0) {
                                    LogS.d("TranscodeLib", "inputfile read is reached to end of the file");
                                }
                                long jLongValue2 = new BigInteger(bArr13).longValue();
                                j2 += jLongValue2;
                                LogS.d("TranscodeLib", "64bit: " + jLongValue2);
                            } else if (jUnsignedIntToLong == 0) {
                                break;
                            } else {
                                j2 += jUnsignedIntToLong;
                            }
                        }
                        str10 = str5;
                        file2 = file3;
                        length = j3;
                        strArr = strArr2;
                        bArr2 = bArr4;
                        str8 = str3;
                        str9 = str4;
                        bArr3 = bArr;
                        i = 4;
                    }
                    randomAccessFile2.close();
                    randomAccessFile.close();
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean setOutputBitdepth(int i) {
        int i2 = mInputFileinfo.HDR10 ? 10 : 8;
        this.mConvert = false;
        LogS.d("TranscodeLib", "setOutputBitdepth  bitdepth : " + i + ", InputBitdepth : " + i2 + ", mHDRType : " + this.mHDRType + ", isHLG : " + isHLG());
        if (i == 8) {
            if (i2 == 8) {
                return true;
            }
            if (supportConverter() && isHDR10Plus()) {
                this.mConvert = true;
                return true;
            }
        }
        return false;
    }

    private static boolean isNalStartCode(byte[] bArr, int i) {
        if (bArr.length - i <= NAL_START_CODE.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = NAL_START_CODE;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i + i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private static int findNalStartCode(byte[] bArr, int i) {
        int length = bArr.length - NAL_START_CODE.length;
        while (i <= length) {
            if (isNalStartCode(bArr, i)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    void checkInitialize(String str, int i, int i2, String str2, Context context, Uri uri, boolean z) throws IOException {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("invalid output size width: " + i + "height:" + i2);
        }
        if (str == null) {
            throw new IllegalArgumentException("output file path cannot be null");
        }
        if (this.mUseUri) {
            if (uri == null || context == null) {
                throw new IllegalArgumentException("can't use uri uri: " + uri + " context: " + context);
            }
        } else if (str2 == null) {
            throw new IllegalArgumentException("input file path cannot be null");
        }
        try {
            mInputFileinfo = MediaInfo.getFileInfo(str2, context, uri);
            mInputVideoinfo = MediaInfo.getTrackInfo(str2, context, uri, true);
            mInputAudioinfo = MediaInfo.getTrackInfo(str2, context, uri, false);
            if (!MediaInfoChecker.isSupportedFileFormat(mInputFileinfo)) {
                throw new IOException("Not a valid video format.");
            }
            mInputFileinfo.Framerate = MediaInfo.getVideoFramerate();
            mInputFileinfo.VideoCodecType = mInputVideoinfo.getString("mime");
            this.mRecordingMode = mInputFileinfo.RecordingMode;
            this.mRecordingFps = mInputFileinfo.RecordingFramerate;
            if (mInputFileinfo.HDR10) {
                this.mHDRType = MediaInfoChecker.getHDRMode(mInputFileinfo);
            } else if (mInputFileinfo.colorTransfer == 7) {
                this.mIsHLG = true;
            }
            if (SEFHelper.isSEFVideoMode(this.mRecordingMode)) {
                this.mNumOfSVCLayers = mInputFileinfo.NumOfSVCLayers;
                this.mSefhelper = new SEFHelper();
                this.mSefhelper.initialize(str2, context, uri);
                this.mOriginalduration = mInputFileinfo.Duration;
                if (isSlow120(this.mRecordingMode, this.mRecordingFps)) {
                    this.mOriginalduration *= 2;
                }
                this.mSEFVideo = this.mSefhelper.checkSEFData(this.mRecordingMode, this.mRecordingFps, this.mOriginalduration);
                LogS.e("TranscodeLib", "checkSEFData mSEFVideo:" + this.mSEFVideo);
                if (this.mSEFVideo) {
                    this.mRegionList = this.mSefhelper.getRegionList();
                }
            }
            if (!checkVideoCodec(i, i2, z)) {
                throw new IOException("Not a valid video codec.");
            }
            this.mOutputFilePath = str;
            this.mOutputWidth = i;
            this.mOutputHeight = i2;
            if (this.mUseUri) {
                this.mInputUri = uri;
                this.mContext = context;
            } else {
                this.mInputFilePath = str2;
            }
        } catch (IllegalArgumentException unused) {
            throw new IllegalArgumentException("invalid input file - can't get file info");
        }
    }

    private void createAudiosolution() throws IOException {
        if (this.mSMEncode || this.mSMConvert) {
            try {
                this.mAudio = new AudioSolution();
            } catch (UnsatisfiedLinkError unused) {
                throw new IOException("Not a valid audio solution.");
            }
        }
    }

    private int getVideoSampleSize(MediaFormat mediaFormat) {
        if (!mediaFormat.getString("mime").startsWith(BnRConstants.VIDEO_DIR_PATH)) {
            return 0;
        }
        return (int) (mediaFormat.getInteger("width") * 1.2f * mediaFormat.getInteger("height"));
    }

    public void setExportRecordingMode(int i) {
        this.mExportRecordingMode = i;
        LogS.d("TranscodeLib", "setExportRecordingMode : " + this.mExportRecordingMode);
    }
}
