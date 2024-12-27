package com.samsung.android.transcode.info;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;

import com.samsung.android.media.SemMediaResourceHelper;
import com.samsung.android.transcode.constants.EncodeConstants;
import com.samsung.android.transcode.util.LogS;

import java.nio.ByteBuffer;

public class MediaInfoChecker {
    private static final int FOUR_K_VIDEO_RESOULTION_SIZE = 8847360;
    static final int NOT_SUPPORT_VC = 1234567890;
    public static final MediaCodecList sMediaCodecList = null;
    private static int mp4v_esds_size = 105;

    private MediaInfoChecker() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    public static boolean isSupportedFileFormat(MediaInfo.MediaFileInfo info) {
        return EncodeConstants.ContentType.sSupportedVideoTypes.contains(info.MimeType);
    }

    public static boolean isRewriteSupportedFileFormat(MediaInfo.MediaFileInfo info) {
        return info.MimeType.contains("video/mp4");
    }

    public static boolean isRewritable(MediaFormat audioformat, MediaFormat videoformat) {
        boolean bSupportedVideoType = false;
        boolean bSupportedAudioType = false;
        String mimeAudio = audioformat.getString("mime");
        String mimeVideo = videoformat.getString("mime");
        if (mimeVideo != null
                && (mimeVideo.contains("video/avc")
                        || mimeVideo.contains("video/mp4v-es")
                        || mimeVideo.contains("video/3gpp")
                        || mimeVideo.contains("video/hevc"))) {
            bSupportedVideoType = true;
        } else {
            LogS.d("TranscodeLib", "Unsupported mime type: video");
        }
        if (mimeAudio != null
                && (mimeAudio.contains("audio/mp4a-latm")
                        || mimeAudio.contains("audio/3gpp")
                        || mimeAudio.contains("audio/amr-wb"))) {
            bSupportedAudioType = true;
        } else {
            LogS.d("TranscodeLib", "Unsuppported mime type: audio");
        }
        if (mimeVideo != null
                && mimeVideo.contains("video/mp4v-es")
                && videoformat.containsKey("csd-0")) {
            ByteBuffer csd = videoformat.getByteBuffer("csd-0");
            LogS.d(
                    "TranscodeLib",
                    "mime : "
                            + mimeVideo
                            + ", csd.capacity(): "
                            + csd.capacity()
                            + ", csd.limit()"
                            + csd.limit());
            if (csd.limit() > mp4v_esds_size) {
                bSupportedVideoType = false;
            }
        }
        if (mimeAudio == null) {
            LogS.d("TranscodeLib", "audio track is null - skip audio");
            bSupportedAudioType = true;
        }
        return bSupportedVideoType && bSupportedAudioType;
    }

    public static int getHDRMode(MediaInfo.MediaFileInfo info) {
        if ((info.Author == 0 || info.Author == 8)
                && (info.RecordingMode == 10 || info.RecordingMode == 25)) {
            return 2;
        }
        if (!info.HDR10) {
            return 0;
        }
        return 1;
    }

    public static boolean isSupportedResolution(
            MediaFormat inputformat,
            int inputwidth,
            int inputheight,
            int outputwidth,
            int outputheight) {
        int codecSize;
        LogS.d(
                "TranscodeLib",
                "isSupportedResolution\tinputwidth: "
                        + inputwidth
                        + ", inputheight: "
                        + inputheight
                        + ", outputwidth: "
                        + outputwidth
                        + ", outputheight : "
                        + outputheight);
        if (inputwidth < 0 || inputheight < 0 || outputwidth < 0 || outputheight < 0) {
            return false;
        }
        SemMediaResourceHelper resourceHelper = SemMediaResourceHelper.createInstance(2, false);
        int remainedCapacity = resourceHelper.getRemainedVideoCapacity();
        int requiredResolutionSizeWithInputAndOutput =
                (inputwidth * inputheight) + (outputwidth * outputheight);
        if (remainedCapacity != NOT_SUPPORT_VC) {
            codecSize = remainedCapacity;
        } else {
            codecSize = resourceHelper.getMaxVideoCapacity();
            if (codecSize <= requiredResolutionSizeWithInputAndOutput) {
                codecSize =
                        codecSize > FOUR_K_VIDEO_RESOULTION_SIZE
                                ? FOUR_K_VIDEO_RESOULTION_SIZE + codecSize
                                : codecSize * 2;
            }
        }
        return codecSize >= requiredResolutionSizeWithInputAndOutput;
    }

    public static boolean isSupportedCodecType(MediaFormat inputFormat) {
        String mime = inputFormat.getString("mime");
        if (mime == null) {
            LogS.e("TranscodeLib", "isSupportedCodecType mime is null");
            return false;
        }
        MediaCodecList codecList = getAllCodecList();
        String codec = codecList.findDecoderForFormat(inputFormat);
        if (codec == null) {
            MediaCodecInfo[] infos = codecList.getCodecInfos();
            for (MediaCodecInfo info : infos) {
                if (!info.isEncoder()) {
                    String[] types = info.getSupportedTypes();
                    int length = types.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            String type = types[i];
                            if (!type.equalsIgnoreCase(mime)) {
                                i++;
                            } else {
                                codec = info.getName();
                                break;
                            }
                        }
                    }
                }
            }
            if (codec == null) {
                LogS.e("TranscodeLib", "isSupportedCodecType not support mime : " + mime);
                return false;
            }
        }
        LogS.e(
                "TranscodeLib",
                "isSupportedCodecType support codec  : " + codec + ", mime : " + mime);
        return true;
    }

    private static MediaCodecList getAllCodecList() {
        return sMediaCodecList != null ? sMediaCodecList : new MediaCodecList(1);
    }
}
