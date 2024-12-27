package com.samsung.android.transcode.util;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.NtpTrustedTime;
import android.view.Surface;

import com.samsung.android.transcode.constants.EncodeConstants;
import com.samsung.android.transcode.info.ExportMediaInfo;
import com.samsung.android.transcode.info.MediaInfo;
import com.samsung.android.wallpaperbackup.BnRConstants;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

public class CodecsHelper {
    private static final int AUTHOR_SAMSUNG_CAMERA = 0;
    private static final int AUTHOR_SAMSUNG_EDITOR = 8;
    private static final float BITRATE_FRACTION_FRAMERATE = 0.8f;
    private static final float BITRATE_FRACTION_HEVC = 0.85f;
    private static final float BITRATE_MARGIN_FACTOR = 1.25f;
    private static final String[] SEC_AAC_ENCODER_OMX_NAMES = {
        "OMX.SEC.naac.enc", "OMX.SEC.aac.enc", "c2.sec.aac.encoder"
    };
    private static final String[] SEC_AAC_DECODER_OMX_NAMES = {
        "OMX.SEC.aac.dec", "c2.sec.aac.decoder"
    };
    public static final MediaExtractor sMediaExtractor = null;
    public static final MediaMetadataRetriever sMetadataRetriever = null;

    private CodecsHelper() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    private static MediaExtractor newMediaExtractor() {
        return sMediaExtractor != null ? sMediaExtractor : new MediaExtractor();
    }

    private static MediaMetadataRetriever newMetadataRetriever() {
        return sMetadataRetriever != null ? sMetadataRetriever : new MediaMetadataRetriever();
    }

    public static MediaExtractor createExtractor(String inputFilePath) throws IOException {
        MediaExtractor extractor = newMediaExtractor();
        extractor.semSetRunningMode(1);
        extractor.setDataSource(inputFilePath);
        return extractor;
    }

    public static MediaExtractor createExtractor(
            FileDescriptor descriptor, long offset, long length) throws IOException {
        MediaExtractor extractor = newMediaExtractor();
        extractor.semSetRunningMode(1);
        extractor.setDataSource(descriptor, offset, length);
        return extractor;
    }

    public static MediaExtractor createExtractor(Context context, Uri uri) throws IOException {
        MediaExtractor extractor = newMediaExtractor();
        extractor.semSetRunningMode(1);
        extractor.setDataSource(context, uri, (Map<String, String>) null);
        return extractor;
    }

    public static int getAndSelectVideoTrackIndex(MediaExtractor extractor) {
        for (int index = 0; index < extractor.getTrackCount(); index++) {
            if (isVideoFormat(extractor.getTrackFormat(index))) {
                extractor.selectTrack(index);
                return index;
            }
        }
        return -1;
    }

    public static int getAndSelectAudioTrackIndex(MediaExtractor extractor) {
        for (int index = 0; index < extractor.getTrackCount(); index++) {
            if (isAudioFormat(extractor.getTrackFormat(index))) {
                extractor.selectTrack(index);
                return index;
            }
        }
        return -1;
    }

    public static MediaMetadataRetriever createMediaMetadataRetriever(String inputFilePath)
            throws IllegalArgumentException {
        MediaMetadataRetriever retriever = newMetadataRetriever();
        retriever.setDataSource(inputFilePath);
        return retriever;
    }

    public static MediaMetadataRetriever createMediaMetadataRetriever(Context context, Uri uri)
            throws IllegalArgumentException, SecurityException {
        MediaMetadataRetriever retriever = newMetadataRetriever();
        retriever.setDataSource(context, uri);
        return retriever;
    }

    private static boolean isVideoFormat(MediaFormat format) {
        return getMimeTypeFor(format).startsWith(BnRConstants.VIDEO_DIR_PATH);
    }

    private static boolean isAudioFormat(MediaFormat format) {
        return getMimeTypeFor(format).startsWith("audio/");
    }

    private static String getMimeTypeFor(MediaFormat format) {
        return format.getString("mime");
    }

    public static boolean isHevcFormat(MediaFormat format) {
        return "video/hevc".equals(getMimeTypeFor(format));
    }

    public static MediaCodecInfo getMediaCodec(String mimeType, boolean isEncoder) {
        MediaCodecInfo codec = isSecCodecAvailable(mimeType, isEncoder);
        if (codec == null) {
            int numCodecs = MediaCodecList.getCodecCount();
            for (int i = 0; i < numCodecs; i++) {
                MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
                if ((!isEncoder || codecInfo.isEncoder())
                        && ((isEncoder || !codecInfo.isEncoder())
                                && isSupportCodec(mimeType, codecInfo))) {
                    return codecInfo;
                }
            }
        }
        if (codec != null) {
            LogS.d("TranscodeLib", "getMediaCodec : " + codec.getName());
        }
        return codec;
    }

    public static MediaCodecInfo getEncoderCodec(String mimeType) {
        return getMediaCodec(mimeType, true);
    }

    public static MediaCodecInfo getDecoderCodec(String mimeType) {
        return getMediaCodec(mimeType, false);
    }

    public static boolean isSupportedFormat(String filePath) {
        MediaMetadataRetriever retriever;
        if (filePath == null) {
            return false;
        }
        boolean support = false;
        try {
            retriever = newMetadataRetriever();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            try {
                retriever.setDataSource(fis.getFD());
                String format = retriever.extractMetadata(12);
                if (format.contains("video/mp4")) {
                    support = true;
                }
                fis.close();
                if (retriever != null) {
                    retriever.close();
                }
                return support;
            } finally {
            }
        } finally {
        }
    }

    public static boolean isSupportedFormat(Context context, Uri uri) {
        if (context == null || uri == null) {
            return false;
        }
        boolean support = false;
        try {
            MediaMetadataRetriever retriever = newMetadataRetriever();
            try {
                retriever.setDataSource(context, uri);
                String format = retriever.extractMetadata(12);
                if (format.contains("video/mp4")) {
                    support = true;
                }
                if (retriever != null) {
                    retriever.close();
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return support;
    }

    private static MediaCodecInfo isSecCodecAvailable(String mimeType, boolean isEncoder) {
        if ("audio/mp4a-latm".equals(mimeType)) {
            for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
                MediaCodecInfo codec = MediaCodecList.getCodecInfoAt(i);
                if (isSamsungAACCodec(codec, isEncoder) && isSupportCodec(mimeType, codec)) {
                    return codec;
                }
            }
            return null;
        }
        return null;
    }

    private static boolean isSamsungAACCodec(MediaCodecInfo codec, boolean isEncoder) {
        String name = codec.getName();
        return (isEncoder
                        && codec.isEncoder()
                        && Arrays.asList(SEC_AAC_ENCODER_OMX_NAMES).contains(name))
                || !(isEncoder
                        || codec.isEncoder()
                        || !Arrays.asList(SEC_AAC_DECODER_OMX_NAMES).contains(name));
    }

    static boolean isSupportCodec(final String mimeType, MediaCodecInfo codecInfo) {
        String[] types = codecInfo.getSupportedTypes();
        return Arrays.stream(types)
                .anyMatch(
                        new Predicate() { // from class:
                                          // com.samsung.android.transcode.util.CodecsHelper$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean equalsIgnoreCase;
                                equalsIgnoreCase = ((String) obj).equalsIgnoreCase(mimeType);
                                return equalsIgnoreCase;
                            }
                        });
    }

    public static MediaCodec createAudioEncoder(MediaCodecInfo codecInfo, MediaFormat format)
            throws IOException {
        MediaCodec encoder = MediaCodec.createByCodecName(codecInfo.getName());
        encoder.configure(format, (Surface) null, (MediaCrypto) null, 1);
        encoder.start();
        return encoder;
    }

    public static MediaCodec createAudioDecoder(MediaFormat inputFormat) throws IOException {
        MediaCodec decoder = MediaCodec.createDecoderByType(getMimeTypeFor(inputFormat));
        decoder.configure(inputFormat, (Surface) null, (MediaCrypto) null, 0);
        decoder.start();
        return decoder;
    }

    public static MediaCodec createAudioDecoder(MediaCodecInfo codecInfo, MediaFormat inputFormat)
            throws IOException {
        MediaCodec decoder = MediaCodec.createByCodecName(codecInfo.getName());
        decoder.configure(inputFormat, (Surface) null, (MediaCrypto) null, 0);
        decoder.start();
        return decoder;
    }

    public static int getVideoMinBitrate(int width, int height) {
        int numberOfMBs = (width * height) / 256;
        if (numberOfMBs >= 100) {
            if (numberOfMBs <= 100 || numberOfMBs > 1000) {
                if (numberOfMBs <= 1000 || numberOfMBs > 1200) {
                    if (numberOfMBs <= 1200 || numberOfMBs > 1350) {
                        if (numberOfMBs > 1350 && numberOfMBs <= 3600) {
                            return 1200;
                        }
                        if (numberOfMBs > 3600 && numberOfMBs <= 8100) {
                            return 2400;
                        }
                        return 9500;
                    }
                    return 400;
                }
                return 350;
            }
            return 150;
        }
        return 99;
    }

    public static int getVideoEncodingBitRate(
            float sizeFraction,
            long maxSizeKB,
            long timeDurationMs,
            int audioBitRate,
            int width,
            int height) {
        String log;
        int bitRate =
                ((int) ((((maxSizeKB * sizeFraction) * 8.0f) * 1024.0f) / timeDurationMs))
                        - (audioBitRate + 2);
        LogS.i(
                "TranscodeLib",
                "getVideoEncodingBitRate maxSizeKB: "
                        + maxSizeKB
                        + " sizeFraction :"
                        + sizeFraction
                        + " bitatre :  "
                        + bitRate);
        int minBitRate = getVideoMinBitrate(width, height);
        int maxBitRate = suggestBitRate(width, height);
        if (bitRate < minBitRate) {
            log = "bitrate(" + bitRate + ") is under min bitrate : " + minBitRate;
            bitRate = minBitRate;
        } else if (bitRate > maxBitRate) {
            log = "over max bitrate : " + maxBitRate;
            bitRate = maxBitRate;
        } else {
            log = "selected bitrate : " + bitRate;
        }
        LogS.i("TranscodeLib", "getVideoEncodingBitRate " + log);
        return bitRate;
    }

    public static MediaCodec createVideoDecoder(
            MediaFormat inputFormat, Surface surface, boolean startFlag) throws IOException {
        MediaCodec decoder = MediaCodec.createDecoderByType(getMimeTypeFor(inputFormat));
        LogS.d("TranscodeLib", "createVideoDecoder");
        try {
            decoder.configure(inputFormat, surface, (MediaCrypto) null, 0);
            if (startFlag) {
                decoder.start();
                LogS.d("TranscodeLib", "createVideoDecoder - start");
            }
            return decoder;
        } catch (IllegalStateException e) {
            decoder.release();
            throw new IOException("createVideoDecode configure error");
        }
    }

    public static void scheduleAfter(int ms, Runnable schedulerCallback)
            throws InterruptedException, ExecutionException {
        ScheduledThreadPoolExecutor sch =
                (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
        sch.schedule(schedulerCallback, ms, TimeUnit.SECONDS);
    }

    public static int suggestBitRate(int width, int height) {
        return getCommonBitrate(width, height);
    }

    public static int suggestBitrate(
            ExportMediaInfo outputInfo, MediaInfo.MediaFileInfo sourceInfo) {
        int bitrate;
        if (outputInfo == null || sourceInfo == null) {
            return -1;
        }
        boolean isHighBitrateMode = false;
        int outputWidth = outputInfo.getWidth();
        int outputHeight = outputInfo.getHeight();
        boolean isHdr = outputInfo.isHdr();
        if (sourceInfo.Is360) {
            bitrate = get360Bitrate(outputWidth, outputHeight) * 1000;
        } else if (isHdr) {
            bitrate = getHdrPlusBitrate(outputWidth, outputHeight) * 1000;
        } else {
            int bitrate2 = sourceInfo.Author;
            if (isSamsungAuthor(bitrate2)) {
                if (keepOriginalBitrate(outputInfo, sourceInfo)) {
                    int bitrate3 = sourceInfo.Bitrate;
                    LogS.i("TranscodeLib", "[final] keepOriginalBitrate: " + bitrate3);
                    return bitrate3;
                }
                int bitrate4 = getSamsungVideoAvcBitrate(outputWidth, outputHeight);
                bitrate = bitrate4 * 1000;
                isHighBitrateMode = isHighBitrateMode(sourceInfo);
                if (isHighBitrateMode) {
                    bitrate = ((int) (bitrate / BITRATE_FRACTION_HEVC)) * 2;
                }
            } else {
                int bitrate5 = getCommonBitrate(outputWidth, outputHeight);
                bitrate = bitrate5 * 1000;
            }
        }
        LogS.d(
                "TranscodeLib",
                "[1] get from table. bitrate: "
                        + bitrate
                        + ", isHighBitrateMode: "
                        + isHighBitrateMode);
        int outputFramerate = outputInfo.getFrameRate();
        if (outputFramerate >= 60) {
            bitrate = (int) (((bitrate * 0.8f) * outputFramerate) / 30.0f);
            LogS.d("TranscodeLib", "[2] over 60fps case. bitrate: " + bitrate);
        }
        String outputVideoCodecType = outputInfo.getVideoCodecType();
        if (!isHdr && "video/hevc".equals(outputVideoCodecType) && bitrate != 80000000) {
            bitrate = (int) (bitrate * BITRATE_FRACTION_HEVC);
            LogS.d("TranscodeLib", "[3] normal hevc case. bitrate: " + bitrate);
        }
        if (sourceInfo.Bitrate != 0) {
            int originalBitrate = sourceInfo.Bitrate;
            if (isSamsungAuthor(sourceInfo.Author)
                    && !outputVideoCodecType.equals(sourceInfo.VideoCodecType)) {
                originalBitrate =
                        "video/hevc".equals(outputVideoCodecType)
                                ? (int) (originalBitrate * BITRATE_FRACTION_HEVC)
                                : (int) (originalBitrate / BITRATE_FRACTION_HEVC);
            }
            LogS.d(
                    "TranscodeLib",
                    "[4] sourceBitrate : "
                            + sourceInfo.Bitrate
                            + ", originalBitrate: "
                            + originalBitrate);
            bitrate = Math.min(bitrate, originalBitrate);
        }
        LogS.i("TranscodeLib", "suggestBitRate. bitrate: " + bitrate);
        return bitrate;
    }

    static boolean isSamsungAuthor(int author) {
        return author == 0 || author == 8;
    }

    private static int getCommonBitrate(int width, int height) {
        int frameSize = width * height;
        if (frameSize >= 35389440) {
            return 80000;
        }
        if (frameSize >= 8294400) {
            return 35000;
        }
        if (frameSize >= 3686400) {
            return EncodeConstants.BitRate.MM_AVG_QHD_DATARATE;
        }
        if (frameSize >= 2073600) {
            return EncodeConstants.BitRate.MM_AVG_FHD_DATARATE;
        }
        if (frameSize >= 921600) {
            return 8000;
        }
        if (frameSize >= 345600 || frameSize >= 76800) {
            return 5000;
        }
        if (frameSize >= 40000) {
            return 512;
        }
        return 280;
    }

    private static boolean keepOriginalBitrate(
            ExportMediaInfo outputInfo, MediaInfo.MediaFileInfo sourceInfo) {
        LogS.d(
                "TranscodeLib",
                "keepOriginalBitrate. exportInfo: ["
                        + outputInfo.getWidth()
                        + "x"
                        + outputInfo.getHeight()
                        + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER
                        + outputInfo.getVideoCodecType()
                        + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER
                        + outputInfo.getFrameRate()
                        + "], sourceInfo: ["
                        + sourceInfo.Width
                        + "x"
                        + sourceInfo.Height
                        + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER
                        + sourceInfo.VideoCodecType
                        + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER
                        + sourceInfo.Framerate
                        + NavigationBarInflaterView.SIZE_MOD_END);
        return outputInfo.getVideoCodecType().equals(sourceInfo.VideoCodecType)
                && outputInfo.getFrameRate() == sourceInfo.Framerate
                && outputInfo.getWidth() == sourceInfo.Width
                && outputInfo.getHeight() == sourceInfo.Height;
    }

    private static boolean isHighBitrateMode(MediaInfo.MediaFileInfo sourceInfo) {
        LogS.d(
                "TranscodeLib",
                "isHighBitrateMode. codecType: "
                        + sourceInfo.VideoCodecType
                        + ", width: "
                        + sourceInfo.Width
                        + ", height: "
                        + sourceInfo.Height
                        + ", bitrate: "
                        + sourceInfo.Bitrate
                        + ", framerate: "
                        + sourceInfo.Framerate);
        if (!"video/hevc".equals(sourceInfo.VideoCodecType)) {
            return false;
        }
        int bitrate = getSamsungVideoAvcBitrate(sourceInfo.Width, sourceInfo.Height) * 1000;
        LogS.d("TranscodeLib", "isHighBitrateMode. [1] expected original bitrate: " + bitrate);
        if (sourceInfo.Framerate >= 60) {
            bitrate = (int) (((bitrate * 0.8f) * sourceInfo.Framerate) / 30.0f);
            LogS.d("TranscodeLib", "isHighBitrateMode. [2] over 60fps case. bitrate: " + bitrate);
        }
        int bitrate2 = (int) (bitrate * BITRATE_MARGIN_FACTOR);
        LogS.d("TranscodeLib", "isHighBitrateMode. [3] check condition. bitrate: " + bitrate2);
        return bitrate2 < sourceInfo.Bitrate;
    }

    private static int getSamsungVideoAvcBitrate(int width, int height) {
        int frameSize = width * height;
        if (frameSize <= 40000) {
            return 512;
        }
        if (frameSize <= 76800 || frameSize <= 307200) {
            return 5000;
        }
        if (frameSize <= 345600) {
            return 8000;
        }
        if (frameSize <= 921600) {
            return 12000;
        }
        if (frameSize <= 2073600) {
            return EncodeConstants.BitRate.MM_BITRATE_AVC_FHD_30;
        }
        if (frameSize <= 3686400) {
            return 25000;
        }
        return (frameSize > 8294400 && frameSize <= 35389440) ? 80000 : 48000;
    }

    private static int getHdrPlusBitrate(int width, int height) {
        int frameSize = width * height;
        if (frameSize <= 921600) {
            return EncodeConstants.BitRate.MM_BITRATE_10_HEVC_HD_30;
        }
        if (frameSize <= 2073600) {
            return 25000;
        }
        if (frameSize <= 2764800) {
            return 30000;
        }
        return EncodeConstants.BitRate.MM_BITRATE_10_HEVC_UHD_30;
    }

    private static int get360Bitrate(int width, int height) {
        int frameSize = width * height;
        if (frameSize <= 819200) {
            return 8000;
        }
        if (frameSize <= 1843200) {
            return EncodeConstants.BitRate.MM_AVG_FHD_DATARATE;
        }
        if (frameSize <= 3276800) {
            return EncodeConstants.BitRate.MM_AVG_QHD_DATARATE;
        }
        if (frameSize <= 4147200) {
            return 25000;
        }
        return 35000;
    }

    public static boolean is10bitVideo(MediaMetadataRetriever retriever) {
        try {
            return "10".equals(retriever.extractMetadata(1028));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSupportOMX() {
        MediaCodecInfo codecInfo = getMediaCodec("video/avc", false, false);
        LogS.d(
                "TranscodeLib",
                "isSupportOMX getMediaCodec : "
                        + ((String)
                                Optional.ofNullable(codecInfo)
                                        .map(
                                                new Function() { // from class:
                                                                 // com.samsung.android.transcode.util.CodecsHelper$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return ((MediaCodecInfo) obj).getName();
                                                    }
                                                })
                                        .orElse("none")));
        return ((Boolean)
                        Optional.ofNullable(codecInfo)
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.transcode.util.CodecsHelper$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                Boolean valueOf;
                                                valueOf =
                                                        Boolean.valueOf(
                                                                ((MediaCodecInfo) obj)
                                                                        .getName()
                                                                        .toLowerCase()
                                                                        .contains("omx"));
                                                return valueOf;
                                            }
                                        })
                                .orElse(false))
                .booleanValue();
    }

    public static MediaCodecInfo getMediaCodec(
            String mimeType, boolean isEncoder, boolean preferSw) {
        MediaCodecList mcl = new MediaCodecList(0);
        MediaCodecInfo[] infos = mcl.getCodecInfos();
        for (MediaCodecInfo info : infos) {
            if (info.isEncoder() == isEncoder
                    && info.isSoftwareOnly() == preferSw
                    && isSupportCodec(mimeType, info)) {
                return info;
            }
        }
        return null;
    }
}
