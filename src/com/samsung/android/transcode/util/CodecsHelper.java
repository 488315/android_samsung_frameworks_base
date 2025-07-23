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
        MediaExtractor newMediaExtractor = newMediaExtractor();
        newMediaExtractor.semSetRunningMode(1);
        newMediaExtractor.setDataSource(str);
        return newMediaExtractor;
    }

    public static MediaExtractor createExtractor(FileDescriptor fileDescriptor, long j, long j2) throws IOException {
        MediaExtractor newMediaExtractor = newMediaExtractor();
        newMediaExtractor.semSetRunningMode(1);
        newMediaExtractor.setDataSource(fileDescriptor, j, j2);
        return newMediaExtractor;
    }

    public static MediaExtractor createExtractor(Context context, Uri uri) throws IOException {
        MediaExtractor newMediaExtractor = newMediaExtractor();
        newMediaExtractor.semSetRunningMode(1);
        newMediaExtractor.setDataSource(context, uri, (Map<String, String>) null);
        return newMediaExtractor;
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

    public static MediaMetadataRetriever createMediaMetadataRetriever(String str) throws IllegalArgumentException {
        MediaMetadataRetriever newMetadataRetriever = newMetadataRetriever();
        newMetadataRetriever.setDataSource(str);
        return newMetadataRetriever;
    }

    public static MediaMetadataRetriever createMediaMetadataRetriever(Context context, Uri uri) throws IllegalArgumentException, SecurityException {
        MediaMetadataRetriever newMetadataRetriever = newMetadataRetriever();
        newMetadataRetriever.setDataSource(context, uri);
        return newMetadataRetriever;
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
        MediaCodecInfo isSecCodecAvailable = isSecCodecAvailable(str, z);
        if (isSecCodecAvailable == null) {
            int codecCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < codecCount; i++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
                if ((!z || codecInfoAt.isEncoder()) && ((z || !codecInfoAt.isEncoder()) && isSupportCodec(str, codecInfoAt))) {
                    return codecInfoAt;
                }
            }
        }
        if (isSecCodecAvailable != null) {
            LogS.d("TranscodeLib", "getMediaCodec : " + isSecCodecAvailable.getName());
        }
        return isSecCodecAvailable;
    }

    public static MediaCodecInfo getEncoderCodec(String str) {
        return getMediaCodec(str, true);
    }

    public static MediaCodecInfo getDecoderCodec(String str) {
        return getMediaCodec(str, false);
    }

    public static boolean isSupportedFormat(String str) {
        boolean z = false;
        if (str == null) {
            return false;
        }
        try {
            MediaMetadataRetriever newMetadataRetriever = newMetadataRetriever();
            try {
                FileInputStream fileInputStream = new FileInputStream(str);
                try {
                    newMetadataRetriever.setDataSource(fileInputStream.getFD());
                    z = newMetadataRetriever.extractMetadata(12).contains("video/mp4");
                    fileInputStream.close();
                    if (newMetadataRetriever != null) {
                        newMetadataRetriever.close();
                    }
                    return z;
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }

    public static boolean isSupportedFormat(Context context, Uri uri) {
        boolean z = false;
        if (context != null && uri != null) {
            try {
                MediaMetadataRetriever newMetadataRetriever = newMetadataRetriever();
                try {
                    newMetadataRetriever.setDataSource(context, uri);
                    z = newMetadataRetriever.extractMetadata(12).contains("video/mp4");
                    if (newMetadataRetriever != null) {
                        newMetadataRetriever.close();
                    }
                    return z;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return z;
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
                boolean equalsIgnoreCase;
                equalsIgnoreCase = ((String) obj).equalsIgnoreCase(str);
                return equalsIgnoreCase;
            }
        });
    }

    public static MediaCodec createAudioEncoder(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat) throws IOException {
        MediaCodec createByCodecName = MediaCodec.createByCodecName(mediaCodecInfo.getName());
        createByCodecName.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 1);
        createByCodecName.start();
        return createByCodecName;
    }

    public static MediaCodec createAudioDecoder(MediaFormat mediaFormat) throws IOException {
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType(getMimeTypeFor(mediaFormat));
        createDecoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        createDecoderByType.start();
        return createDecoderByType;
    }

    public static MediaCodec createAudioDecoder(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat) throws IOException {
        MediaCodec createByCodecName = MediaCodec.createByCodecName(mediaCodecInfo.getName());
        createByCodecName.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        createByCodecName.start();
        return createByCodecName;
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
        int suggestBitRate = suggestBitRate(i2, i3);
        if (i4 < videoMinBitrate) {
            str = "bitrate(" + i4 + ") is under min bitrate : " + videoMinBitrate;
            i4 = videoMinBitrate;
        } else if (i4 > suggestBitRate) {
            i4 = suggestBitRate;
            str = "over max bitrate : " + suggestBitRate;
        } else {
            str = "selected bitrate : " + i4;
        }
        LogS.i("TranscodeLib", "getVideoEncodingBitRate " + str);
        return i4;
    }

    public static MediaCodec createVideoDecoder(MediaFormat mediaFormat, Surface surface, boolean z) throws IOException {
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType(getMimeTypeFor(mediaFormat));
        LogS.d("TranscodeLib", "createVideoDecoder");
        try {
            createDecoderByType.configure(mediaFormat, surface, (MediaCrypto) null, 0);
            if (z) {
                createDecoderByType.start();
                LogS.d("TranscodeLib", "createVideoDecoder - start");
            }
            return createDecoderByType;
        } catch (IllegalStateException unused) {
            createDecoderByType.release();
            throw new IOException("createVideoDecode configure error");
        }
    }

    public static void scheduleAfter(int i, Runnable runnable) throws InterruptedException, ExecutionException {
        ((ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2)).schedule(runnable, i, TimeUnit.SECONDS);
    }

    public static int suggestBitRate(int i, int i2) {
        return getCommonBitrate(i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int suggestBitrate(com.samsung.android.transcode.info.ExportMediaInfo r8, com.samsung.android.transcode.info.MediaInfo.MediaFileInfo r9) {
        /*
            Method dump skipped, instructions count: 311
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.transcode.util.CodecsHelper.suggestBitrate(com.samsung.android.transcode.info.ExportMediaInfo, com.samsung.android.transcode.info.MediaInfo$MediaFileInfo):int");
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
                Boolean valueOf;
                valueOf = Boolean.valueOf(((MediaCodecInfo) obj).getName().toLowerCase().contains("omx"));
                return valueOf;
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
