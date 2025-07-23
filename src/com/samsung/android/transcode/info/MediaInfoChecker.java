package com.samsung.android.transcode.info;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import com.samsung.android.media.SemMediaResourceHelper;
import com.samsung.android.transcode.constants.EncodeConstants;
import com.samsung.android.transcode.info.MediaInfo;
import com.samsung.android.transcode.util.LogS;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class MediaInfoChecker {
    private static final int FOUR_K_VIDEO_RESOULTION_SIZE = 8847360;
    static final int NOT_SUPPORT_VC = 1234567890;
    private static int mp4v_esds_size = 105;
    public static final MediaCodecList sMediaCodecList = null;

    private MediaInfoChecker() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    public static boolean isSupportedFileFormat(MediaInfo.MediaFileInfo mediaFileInfo) {
        return EncodeConstants.ContentType.sSupportedVideoTypes.contains(mediaFileInfo.MimeType);
    }

    public static boolean isRewriteSupportedFileFormat(MediaInfo.MediaFileInfo mediaFileInfo) {
        return mediaFileInfo.MimeType.contains("video/mp4");
    }

    public static boolean isRewritable(MediaFormat mediaFormat, MediaFormat mediaFormat2) {
        boolean z;
        boolean z2;
        String string = mediaFormat.getString("mime");
        String string2 = mediaFormat2.getString("mime");
        if (string2 == null || !(string2.contains("video/avc") || string2.contains("video/mp4v-es") || string2.contains("video/3gpp") || string2.contains("video/hevc"))) {
            LogS.d("TranscodeLib", "Unsupported mime type: video");
            z = false;
        } else {
            z = true;
        }
        if (string == null || !(string.contains("audio/mp4a-latm") || string.contains("audio/3gpp") || string.contains("audio/amr-wb"))) {
            LogS.d("TranscodeLib", "Unsuppported mime type: audio");
            z2 = false;
        } else {
            z2 = true;
        }
        if (string2 != null && string2.contains("video/mp4v-es") && mediaFormat2.containsKey("csd-0")) {
            ByteBuffer byteBuffer = mediaFormat2.getByteBuffer("csd-0");
            LogS.d("TranscodeLib", "mime : " + string2 + ", csd.capacity(): " + byteBuffer.capacity() + ", csd.limit()" + byteBuffer.limit());
            if (byteBuffer.limit() > mp4v_esds_size) {
                z = false;
            }
        }
        if (string == null) {
            LogS.d("TranscodeLib", "audio track is null - skip audio");
            z2 = true;
        }
        return z && z2;
    }

    public static int getHDRMode(MediaInfo.MediaFileInfo mediaFileInfo) {
        if ((mediaFileInfo.Author == 0 || mediaFileInfo.Author == 8) && (mediaFileInfo.RecordingMode == 10 || mediaFileInfo.RecordingMode == 25)) {
            return 2;
        }
        return mediaFileInfo.HDR10 ? 1 : 0;
    }

    public static boolean isSupportedResolution(MediaFormat mediaFormat, int i, int i2, int i3, int i4) {
        LogS.d("TranscodeLib", "isSupportedResolution\tinputwidth: " + i + ", inputheight: " + i2 + ", outputwidth: " + i3 + ", outputheight : " + i4);
        if (i < 0 || i2 < 0 || i3 < 0 || i4 < 0) {
            return false;
        }
        SemMediaResourceHelper createInstance = SemMediaResourceHelper.createInstance(2, false);
        int remainedVideoCapacity = createInstance.getRemainedVideoCapacity();
        int i5 = (i * i2) + (i3 * i4);
        if (remainedVideoCapacity == NOT_SUPPORT_VC && (remainedVideoCapacity = createInstance.getMaxVideoCapacity()) <= i5) {
            remainedVideoCapacity = remainedVideoCapacity > FOUR_K_VIDEO_RESOULTION_SIZE ? remainedVideoCapacity + FOUR_K_VIDEO_RESOULTION_SIZE : remainedVideoCapacity * 2;
        }
        return remainedVideoCapacity >= i5;
    }

    public static boolean isSupportedCodecType(MediaFormat mediaFormat) {
        String string = mediaFormat.getString("mime");
        if (string == null) {
            LogS.e("TranscodeLib", "isSupportedCodecType mime is null");
            return false;
        }
        MediaCodecList allCodecList = getAllCodecList();
        String findDecoderForFormat = allCodecList.findDecoderForFormat(mediaFormat);
        if (findDecoderForFormat == null) {
            for (MediaCodecInfo mediaCodecInfo : allCodecList.getCodecInfos()) {
                if (!mediaCodecInfo.isEncoder()) {
                    String[] supportedTypes = mediaCodecInfo.getSupportedTypes();
                    int length = supportedTypes.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (supportedTypes[i].equalsIgnoreCase(string)) {
                            findDecoderForFormat = mediaCodecInfo.getName();
                            break;
                        }
                        i++;
                    }
                }
            }
            if (findDecoderForFormat == null) {
                LogS.e("TranscodeLib", "isSupportedCodecType not support mime : " + string);
                return false;
            }
        }
        LogS.e("TranscodeLib", "isSupportedCodecType support codec  : " + findDecoderForFormat + ", mime : " + string);
        return true;
    }

    private static MediaCodecList getAllCodecList() {
        MediaCodecList mediaCodecList = sMediaCodecList;
        return mediaCodecList != null ? mediaCodecList : new MediaCodecList(1);
    }
}
