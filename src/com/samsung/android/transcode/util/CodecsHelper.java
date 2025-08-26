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
import com.samsung.android.feature.SemFloatingFeature;
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

/* loaded from: classes6.dex */
public class CodecsHelper {
    private static final int AUTHOR_SAMSUNG_CAMERA = 0;
    private static final int AUTHOR_SAMSUNG_EDITOR = 8;
    private static final float BITRATE_FRACTION_FRAMERATE = 0.8f;
    private static final float BITRATE_FRACTION_HEVC = 0.85f;
    private static final float BITRATE_MARGIN_FACTOR = 1.25f;
    private static final String[] SEC_AAC_ENCODER_OMX_NAMES = {"OMX.SEC.naac.enc", "OMX.SEC.aac.enc", "c2.sec.aac.encoder"};
    private static final String[] SEC_AAC_DECODER_OMX_NAMES = {"OMX.SEC.aac.dec", "c2.sec.aac.decoder"};
    public static final MediaExtractor sMediaExtractor = null;
    public static final MediaMetadataRetriever sMetadataRetriever = null;

    private static int get360Bitrate(int i, int i2) {
        int i3 = i * i2;
        if (i3 <= 819200) {
            return 8000;
        }
        return i3 <= 1843200 ? EncodeConstants.BitRate.MM_AVG_FHD_DATARATE : i3 <= 3276800 ? EncodeConstants.BitRate.MM_AVG_QHD_DATARATE : i3 <= 4147200 ? 25000 : 35000;
    }

    private static int getCommonBitrate(int i, int i2) {
        int i3 = i * i2;
        if (i3 >= 35389440) {
            return 80000;
        }
        if (i3 >= 8294400) {
            return 35000;
        }
        if (i3 >= 3686400) {
            return EncodeConstants.BitRate.MM_AVG_QHD_DATARATE;
        }
        if (i3 >= 2073600) {
            return EncodeConstants.BitRate.MM_AVG_FHD_DATARATE;
        }
        if (i3 >= 921600) {
            return 8000;
        }
        if (i3 < 345600 && i3 < 76800) {
            return i3 >= 40000 ? 512 : 280;
        }
        return 5000;
    }

    private static int getHdrPlusBitrate(int i, int i2) {
        int i3 = i * i2;
        if (i3 <= 921600) {
            return EncodeConstants.BitRate.MM_BITRATE_10_HEVC_HD_30;
        }
        if (i3 <= 2073600) {
            return 25000;
        }
        if (i3 <= 2764800) {
            return 30000;
        }
        return EncodeConstants.BitRate.MM_BITRATE_10_HEVC_UHD_30;
    }

    private static int getSamsungVideoAvcBitrate(int i, int i2) {
        int i3 = i * i2;
        if (i3 <= 40000) {
            return 512;
        }
        if (i3 <= 76800 || i3 <= 307200) {
            return 5000;
        }
        if (i3 <= 345600) {
            return 8000;
        }
        if (i3 <= 921600) {
            return 12000;
        }
        if (i3 <= 2073600) {
            return EncodeConstants.BitRate.MM_BITRATE_AVC_FHD_30;
        }
        if (i3 <= 3686400) {
            return 25000;
        }
        return (i3 > 8294400 && i3 <= 35389440) ? 80000 : 48000;
    }

    static boolean isSamsungAuthor(int i) {
        return i == 0 || i == 8;
    }

    private CodecsHelper() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    private static MediaExtractor newMediaExtractor() {
        MediaExtractor mediaExtractor = sMediaExtractor;
        return mediaExtractor != null ? mediaExtractor : new MediaExtractor();
    }

    private static MediaMetadataRetriever newMetadataRetriever() {
        MediaMetadataRetriever mediaMetadataRetriever = sMetadataRetriever;
        return mediaMetadataRetriever != null ? mediaMetadataRetriever : new MediaMetadataRetriever();
    }

    public static MediaExtractor createExtractor(String str) throws IOException {
        MediaExtractor mediaExtractorNewMediaExtractor = newMediaExtractor();
        mediaExtractorNewMediaExtractor.semSetRunningMode(1);
        mediaExtractorNewMediaExtractor.setDataSource(str);
        return mediaExtractorNewMediaExtractor;
    }

    public static MediaExtractor createExtractor(FileDescriptor fileDescriptor, long j, long j2) throws IOException {
        MediaExtractor mediaExtractorNewMediaExtractor = newMediaExtractor();
        mediaExtractorNewMediaExtractor.semSetRunningMode(1);
        mediaExtractorNewMediaExtractor.setDataSource(fileDescriptor, j, j2);
        return mediaExtractorNewMediaExtractor;
    }

    public static MediaExtractor createExtractor(Context context, Uri uri) throws IOException {
        MediaExtractor mediaExtractorNewMediaExtractor = newMediaExtractor();
        mediaExtractorNewMediaExtractor.semSetRunningMode(1);
        mediaExtractorNewMediaExtractor.setDataSource(context, uri, (Map<String, String>) null);
        return mediaExtractorNewMediaExtractor;
    }

    public static int getAndSelectVideoTrackIndex(MediaExtractor mediaExtractor) {
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
            if (isVideoFormat(mediaExtractor.getTrackFormat(i))) {
                mediaExtractor.selectTrack(i);
                return i;
            }
        }
        return -1;
    }

    public static int getAndSelectAudioTrackIndex(MediaExtractor mediaExtractor) {
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
            if (isAudioFormat(mediaExtractor.getTrackFormat(i))) {
                mediaExtractor.selectTrack(i);
                return i;
            }
        }
        return -1;
    }

    public static MediaMetadataRetriever createMediaMetadataRetriever(String str) throws IOException, IllegalArgumentException {
        MediaMetadataRetriever mediaMetadataRetrieverNewMetadataRetriever = newMetadataRetriever();
        mediaMetadataRetrieverNewMetadataRetriever.setDataSource(str);
        return mediaMetadataRetrieverNewMetadataRetriever;
    }

    public static MediaMetadataRetriever createMediaMetadataRetriever(Context context, Uri uri) throws IOException, SecurityException, IllegalArgumentException {
        MediaMetadataRetriever mediaMetadataRetrieverNewMetadataRetriever = newMetadataRetriever();
        mediaMetadataRetrieverNewMetadataRetriever.setDataSource(context, uri);
        return mediaMetadataRetrieverNewMetadataRetriever;
    }

    private static boolean isVideoFormat(MediaFormat mediaFormat) {
        return getMimeTypeFor(mediaFormat).startsWith(BnRConstants.VIDEO_DIR_PATH);
    }

    private static boolean isAudioFormat(MediaFormat mediaFormat) {
        return getMimeTypeFor(mediaFormat).startsWith("audio/");
    }

    private static String getMimeTypeFor(MediaFormat mediaFormat) {
        return mediaFormat.getString("mime");
    }

    public static boolean isHevcFormat(MediaFormat mediaFormat) {
        return "video/hevc".equals(getMimeTypeFor(mediaFormat));
    }

    public static MediaCodecInfo getMediaCodec(String str, boolean z) {
        MediaCodecInfo mediaCodecInfoIsSecCodecAvailable = isSecCodecAvailable(str, z);
        if (mediaCodecInfoIsSecCodecAvailable == null) {
            int codecCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < codecCount; i++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
                if ((!z || codecInfoAt.isEncoder()) && ((z || !codecInfoAt.isEncoder()) && isSupportCodec(str, codecInfoAt))) {
                    return codecInfoAt;
                }
            }
        }
        if (mediaCodecInfoIsSecCodecAvailable != null) {
            LogS.d("TranscodeLib", "getMediaCodec : " + mediaCodecInfoIsSecCodecAvailable.getName());
        }
        return mediaCodecInfoIsSecCodecAvailable;
    }

    public static MediaCodecInfo getEncoderCodec(String str) {
        return getMediaCodec(str, true);
    }

    public static MediaCodecInfo getDecoderCodec(String str) {
        return getMediaCodec(str, false);
    }

    public static boolean isSupportedFormat(String str) {
        boolean zContains = false;
        if (str == null) {
            return false;
        }
        try {
            MediaMetadataRetriever mediaMetadataRetrieverNewMetadataRetriever = newMetadataRetriever();
            try {
                FileInputStream fileInputStream = new FileInputStream(str);
                try {
                    mediaMetadataRetrieverNewMetadataRetriever.setDataSource(fileInputStream.getFD());
                    zContains = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(12).contains("video/mp4");
                    fileInputStream.close();
                    if (mediaMetadataRetrieverNewMetadataRetriever != null) {
                        mediaMetadataRetrieverNewMetadataRetriever.close();
                    }
                    return zContains;
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return zContains;
        }
    }

    public static boolean isSupportedFormat(Context context, Uri uri) {
        boolean zContains = false;
        if (context != null && uri != null) {
            try {
                MediaMetadataRetriever mediaMetadataRetrieverNewMetadataRetriever = newMetadataRetriever();
                try {
                    mediaMetadataRetrieverNewMetadataRetriever.setDataSource(context, uri);
                    zContains = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(12).contains("video/mp4");
                    if (mediaMetadataRetrieverNewMetadataRetriever != null) {
                        mediaMetadataRetrieverNewMetadataRetriever.close();
                    }
                    return zContains;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return zContains;
    }

    private static MediaCodecInfo isSecCodecAvailable(String str, boolean z) {
        if (!"audio/mp4a-latm".equals(str)) {
            return null;
        }
        for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (isSamsungAACCodec(codecInfoAt, z) && isSupportCodec(str, codecInfoAt)) {
                return codecInfoAt;
            }
        }
        return null;
    }

    private static boolean isSamsungAACCodec(MediaCodecInfo mediaCodecInfo, boolean z) {
        String name = mediaCodecInfo.getName();
        if (z && mediaCodecInfo.isEncoder() && Arrays.asList(SEC_AAC_ENCODER_OMX_NAMES).contains(name)) {
            return true;
        }
        return (z || mediaCodecInfo.isEncoder() || !Arrays.asList(SEC_AAC_DECODER_OMX_NAMES).contains(name)) ? false : true;
    }

    static boolean isSupportCodec(final String str, MediaCodecInfo mediaCodecInfo) {
        return Arrays.stream(mediaCodecInfo.getSupportedTypes()).anyMatch(new Predicate() { // from class: com.samsung.android.transcode.util.CodecsHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((String) obj).equalsIgnoreCase(str);
            }
        });
    }

    public static MediaCodec createAudioEncoder(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat) throws IOException {
        MediaCodec mediaCodecCreateByCodecName = MediaCodec.createByCodecName(mediaCodecInfo.getName());
        mediaCodecCreateByCodecName.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 1);
        mediaCodecCreateByCodecName.start();
        return mediaCodecCreateByCodecName;
    }

    public static MediaCodec createAudioDecoder(MediaFormat mediaFormat) throws IOException {
        MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(getMimeTypeFor(mediaFormat));
        mediaCodecCreateDecoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        mediaCodecCreateDecoderByType.start();
        return mediaCodecCreateDecoderByType;
    }

    public static MediaCodec createAudioDecoder(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat) throws IOException {
        MediaCodec mediaCodecCreateByCodecName = MediaCodec.createByCodecName(mediaCodecInfo.getName());
        mediaCodecCreateByCodecName.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        mediaCodecCreateByCodecName.start();
        return mediaCodecCreateByCodecName;
    }

    public static int getVideoMinBitrate(int i, int i2) {
        int i3 = (i * i2) / 256;
        if (i3 < 100) {
            return 99;
        }
        if (i3 > 100 && i3 <= 1000) {
            return 150;
        }
        if (i3 > 1000 && i3 <= 1200) {
            return 350;
        }
        if (i3 > 1200 && i3 <= 1350) {
            return 400;
        }
        if (i3 <= 1350 || i3 > 3600) {
            return (i3 <= 3600 || i3 > 8100) ? 9500 : 2400;
        }
        return 1200;
    }

    public static int getVideoEncodingBitRate(float f, long j, long j2, int i, int i2, int i3) {
        String str;
        int i4 = ((int) ((((j * f) * 8.0f) * 1024.0f) / j2)) - (i + 2);
        LogS.i("TranscodeLib", "getVideoEncodingBitRate maxSizeKB: " + j + " sizeFraction :" + f + " bitatre :  " + i4);
        int videoMinBitrate = getVideoMinBitrate(i2, i3);
        int iSuggestBitRate = suggestBitRate(i2, i3);
        if (i4 < videoMinBitrate) {
            str = "bitrate(" + i4 + ") is under min bitrate : " + videoMinBitrate;
            i4 = videoMinBitrate;
        } else if (i4 > iSuggestBitRate) {
            i4 = iSuggestBitRate;
            str = "over max bitrate : " + iSuggestBitRate;
        } else {
            str = "selected bitrate : " + i4;
        }
        LogS.i("TranscodeLib", "getVideoEncodingBitRate " + str);
        return i4;
    }

    public static MediaCodec createVideoDecoder(MediaFormat mediaFormat, Surface surface, boolean z) throws IOException {
        MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(getMimeTypeFor(mediaFormat));
        LogS.d("TranscodeLib", "createVideoDecoder");
        try {
            mediaCodecCreateDecoderByType.configure(mediaFormat, surface, (MediaCrypto) null, 0);
            if (z) {
                mediaCodecCreateDecoderByType.start();
                LogS.d("TranscodeLib", "createVideoDecoder - start");
            }
            return mediaCodecCreateDecoderByType;
        } catch (IllegalStateException unused) {
            mediaCodecCreateDecoderByType.release();
            throw new IOException("createVideoDecode configure error");
        }
    }

    public static void scheduleAfter(int i, Runnable runnable) throws ExecutionException, InterruptedException {
        ((ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2)).schedule(runnable, i, TimeUnit.SECONDS);
    }

    public static int suggestBitRate(int i, int i2) {
        return getCommonBitrate(i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int suggestBitrate(ExportMediaInfo exportMediaInfo, MediaInfo.MediaFileInfo mediaFileInfo) {
        int commonBitrate;
        int samsungVideoAvcBitrate;
        int i;
        float f;
        int frameRate;
        if (exportMediaInfo == null || mediaFileInfo == null) {
            return -1;
        }
        int width = exportMediaInfo.getWidth();
        int height = exportMediaInfo.getHeight();
        boolean zIsHdr = exportMediaInfo.isHdr();
        boolean zIsHighBitrateMode = false;
        if (mediaFileInfo.Is360) {
            commonBitrate = get360Bitrate(width, height);
        } else if (zIsHdr) {
            commonBitrate = getHdrPlusBitrate(width, height);
        } else {
            if (isSamsungAuthor(mediaFileInfo.Author)) {
                if (keepOriginalBitrate(exportMediaInfo, mediaFileInfo)) {
                    if (mediaFileInfo.colorTransfer == 7) {
                        if (supportHierB()) {
                            f = mediaFileInfo.Bitrate * 0.8f;
                        } else {
                            f = mediaFileInfo.Bitrate * 1.2f;
                        }
                        i = (int) f;
                    } else {
                        i = mediaFileInfo.Bitrate;
                    }
                    LogS.i("TranscodeLib", "[final] keepOriginalBitrate: " + i);
                    return i;
                }
                samsungVideoAvcBitrate = getSamsungVideoAvcBitrate(width, height) * 1000;
                zIsHighBitrateMode = isHighBitrateMode(mediaFileInfo);
                if (zIsHighBitrateMode) {
                    samsungVideoAvcBitrate = ((int) (samsungVideoAvcBitrate / BITRATE_FRACTION_HEVC)) * 2;
                }
                LogS.d("TranscodeLib", "[1] get from table. bitrate: " + samsungVideoAvcBitrate + ", isHighBitrateMode: " + zIsHighBitrateMode);
                frameRate = exportMediaInfo.getFrameRate();
                if (frameRate >= 60) {
                    samsungVideoAvcBitrate = (int) (((samsungVideoAvcBitrate * 0.8f) * frameRate) / 30.0f);
                    LogS.d("TranscodeLib", "[2] over 60fps case. bitrate: " + samsungVideoAvcBitrate);
                }
                String videoCodecType = exportMediaInfo.getVideoCodecType();
                if (!zIsHdr && "video/hevc".equals(videoCodecType) && samsungVideoAvcBitrate != 80000000) {
                    samsungVideoAvcBitrate = (int) (samsungVideoAvcBitrate * BITRATE_FRACTION_HEVC);
                    LogS.d("TranscodeLib", "[3] normal hevc case. bitrate: " + samsungVideoAvcBitrate);
                }
                if (mediaFileInfo.Bitrate != 0) {
                    int i2 = mediaFileInfo.Bitrate;
                    if (isSamsungAuthor(mediaFileInfo.Author) && !videoCodecType.equals(mediaFileInfo.VideoCodecType)) {
                        i2 = (int) ("video/hevc".equals(videoCodecType) ? i2 * BITRATE_FRACTION_HEVC : i2 / BITRATE_FRACTION_HEVC);
                    }
                    LogS.d("TranscodeLib", "[4] sourceBitrate : " + mediaFileInfo.Bitrate + ", originalBitrate: " + i2);
                    samsungVideoAvcBitrate = Math.min(samsungVideoAvcBitrate, i2);
                }
                LogS.i("TranscodeLib", "suggestBitRate. bitrate: " + samsungVideoAvcBitrate);
                return samsungVideoAvcBitrate;
            }
            commonBitrate = getCommonBitrate(width, height);
        }
        samsungVideoAvcBitrate = commonBitrate * 1000;
        LogS.d("TranscodeLib", "[1] get from table. bitrate: " + samsungVideoAvcBitrate + ", isHighBitrateMode: " + zIsHighBitrateMode);
        frameRate = exportMediaInfo.getFrameRate();
        if (frameRate >= 60) {
        }
        String videoCodecType2 = exportMediaInfo.getVideoCodecType();
        if (!zIsHdr) {
            samsungVideoAvcBitrate = (int) (samsungVideoAvcBitrate * BITRATE_FRACTION_HEVC);
            LogS.d("TranscodeLib", "[3] normal hevc case. bitrate: " + samsungVideoAvcBitrate);
        }
        if (mediaFileInfo.Bitrate != 0) {
        }
        LogS.i("TranscodeLib", "suggestBitRate. bitrate: " + samsungVideoAvcBitrate);
        return samsungVideoAvcBitrate;
    }

    private static boolean keepOriginalBitrate(ExportMediaInfo exportMediaInfo, MediaInfo.MediaFileInfo mediaFileInfo) {
        LogS.d("TranscodeLib", "keepOriginalBitrate. exportInfo: [" + exportMediaInfo.getWidth() + "x" + exportMediaInfo.getHeight() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + exportMediaInfo.getVideoCodecType() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + exportMediaInfo.getFrameRate() + "], sourceInfo: [" + mediaFileInfo.Width + "x" + mediaFileInfo.Height + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + mediaFileInfo.VideoCodecType + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + mediaFileInfo.Framerate + NavigationBarInflaterView.SIZE_MOD_END);
        return exportMediaInfo.getVideoCodecType().equals(mediaFileInfo.VideoCodecType) && exportMediaInfo.getFrameRate() == mediaFileInfo.Framerate && exportMediaInfo.getWidth() == mediaFileInfo.Width && exportMediaInfo.getHeight() == mediaFileInfo.Height;
    }

    private static boolean isHighBitrateMode(MediaInfo.MediaFileInfo mediaFileInfo) {
        LogS.d("TranscodeLib", "isHighBitrateMode. codecType: " + mediaFileInfo.VideoCodecType + ", width: " + mediaFileInfo.Width + ", height: " + mediaFileInfo.Height + ", bitrate: " + mediaFileInfo.Bitrate + ", framerate: " + mediaFileInfo.Framerate);
        if (!"video/hevc".equals(mediaFileInfo.VideoCodecType)) {
            return false;
        }
        int samsungVideoAvcBitrate = getSamsungVideoAvcBitrate(mediaFileInfo.Width, mediaFileInfo.Height) * 1000;
        LogS.d("TranscodeLib", "isHighBitrateMode. [1] expected original bitrate: " + samsungVideoAvcBitrate);
        if (mediaFileInfo.Framerate >= 60) {
            samsungVideoAvcBitrate = (int) (((samsungVideoAvcBitrate * 0.8f) * mediaFileInfo.Framerate) / 30.0f);
            LogS.d("TranscodeLib", "isHighBitrateMode. [2] over 60fps case. bitrate: " + samsungVideoAvcBitrate);
        }
        int i = (int) (samsungVideoAvcBitrate * BITRATE_MARGIN_FACTOR);
        LogS.d("TranscodeLib", "isHighBitrateMode. [3] check condition. bitrate: " + i);
        return i < mediaFileInfo.Bitrate;
    }

    public static boolean is10bitVideo(MediaMetadataRetriever mediaMetadataRetriever) {
        try {
            return "10".equals(mediaMetadataRetriever.extractMetadata(1028));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSupportOMX() {
        MediaCodecInfo mediaCodec = getMediaCodec("video/avc", false, false);
        LogS.d("TranscodeLib", "isSupportOMX getMediaCodec : " + ((String) Optional.ofNullable(mediaCodec).map(new Function() { // from class: com.samsung.android.transcode.util.CodecsHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MediaCodecInfo) obj).getName();
            }
        }).orElse("none")));
        return ((Boolean) Optional.ofNullable(mediaCodec).map(new Function() { // from class: com.samsung.android.transcode.util.CodecsHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((MediaCodecInfo) obj).getName().toLowerCase().contains("omx"));
            }
        }).orElse(false)).booleanValue();
    }

    public static MediaCodecInfo getMediaCodec(String str, boolean z, boolean z2) {
        for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(0).getCodecInfos()) {
            if (mediaCodecInfo.isEncoder() == z && mediaCodecInfo.isSoftwareOnly() == z2 && isSupportCodec(str, mediaCodecInfo)) {
                return mediaCodecInfo;
            }
        }
        return null;
    }

    public static boolean supportHierB() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HIERARCHICAL_B_ENCODING");
    }
}
