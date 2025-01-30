package com.google.android.mms;

import com.android.internal.widget.MessagingMessage;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ContentType {
    public static final String APP_DRM_CONTENT = "application/vnd.oma.drm.content";
    public static final String APP_DRM_MESSAGE = "application/vnd.oma.drm.message";
    public static final String APP_DRM_RIGHTS_WBXML = "application/vnd.oma.drm.rights+wbxml";
    public static final String APP_OGG = "application/ogg";
    public static final String APP_SMIL = "application/smil";
    public static final String APP_WAP_XHTML = "application/vnd.wap.xhtml+xml";
    public static final String APP_XHTML = "application/xhtml+xml";
    public static final String AUDIO_3GPP = "audio/3gpp";
    public static final String AUDIO_AAC = "audio/aac";
    public static final String AUDIO_AAC_ADTS = "audio/aac-adts";
    public static final String AUDIO_AAC_MP4 = "audio/aac_mp4";
    public static final String AUDIO_AMR = "audio/amr";
    public static final String AUDIO_AMR_WB = "audio/amr-wb";
    public static final String AUDIO_ASF = "audio/x-ms-asf";
    public static final String AUDIO_EVRC = "audio/evrc";
    public static final String AUDIO_FLAC = "audio/flac";
    public static final String AUDIO_IMELODY = "audio/imelody";
    public static final String AUDIO_M4A = "audio/m4a";
    public static final String AUDIO_MID = "audio/mid";
    public static final String AUDIO_MIDI = "audio/midi";
    public static final String AUDIO_MMF = "application/vnd.smaf";
    public static final String AUDIO_MP3 = "audio/mp3";
    public static final String AUDIO_MP4 = "audio/mp4";
    public static final String AUDIO_MP4A_LATM = "audio/mp4a-latm";
    public static final String AUDIO_MPEG = "audio/mpeg";
    public static final String AUDIO_MPEG3 = "audio/mpeg3";
    public static final String AUDIO_MPG = "audio/mpg";
    public static final String AUDIO_MXMF = "audio/mobile-xmf";
    public static final String AUDIO_OGG = "application/ogg";
    public static final String AUDIO_OGG2 = "audio/ogg";
    public static final String AUDIO_QCELP = "audio/qcelp";
    public static final String AUDIO_QCELP_VND = "audio/vnd.qcelp";
    public static final String AUDIO_QCP = "audio/qcp";
    public static final String AUDIO_SP_MIDI = "audio/sp-midi";
    public static final String AUDIO_TEXT_X_IMY = "text/x-imelody";
    public static final String AUDIO_TEXT_X_IMY_C = "text/x-iMelody";
    public static final String AUDIO_UNSPECIFIED = "audio/*";
    public static final String AUDIO_WAV = "audio/wav";
    public static final String AUDIO_WAVE = "audio/wave";
    public static final String AUDIO_WMA = "audio/x-ms-wma";
    public static final String AUDIO_XMF = "audio/xmf";
    public static final String AUDIO_X_AAC = "audio/x-aac";
    public static final String AUDIO_X_FLAC = "application/x-flac";
    public static final String AUDIO_X_MID = "audio/x-mid";
    public static final String AUDIO_X_MIDI = "audio/x-midi";
    public static final String AUDIO_X_MP3 = "audio/x-mp3";
    public static final String AUDIO_X_MPEG = "audio/x-mpeg";
    public static final String AUDIO_X_MPEG3 = "audio/x-mpeg3";
    public static final String AUDIO_X_MPG = "audio/x-mpg";
    public static final String AUDIO_X_WAV = "audio/x-wav";
    public static final String AUDIO_X_WAVE = "audio/x-wave";
    public static final String AUDIO_X_XMF = "audio/x-xmf";
    public static final String IMAGE_BMP = "image/bmp";
    public static final String IMAGE_GIF = "image/gif";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final String IMAGE_WBMP = "image/vnd.wap.wbmp";
    public static final String IMAGE_XBMP = "image/x-bmp";
    public static final String IMAGE_X_MS_BMP = "image/x-ms-bmp";
    public static final String MMS_GENERIC = "application/vnd.wap.mms-generic";
    public static final String MMS_MESSAGE = "application/vnd.wap.mms-message";
    public static final String MULTIPART_ALTERNATIVE = "application/vnd.wap.multipart.alternative";
    public static final String MULTIPART_MIXED = "application/vnd.wap.multipart.mixed";
    public static final String MULTIPART_RELATED = "application/vnd.wap.multipart.related";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_VCALENDAR = "text/x-vCalendar";
    public static final String TEXT_VCARD = "text/x-vCard";
    public static final String TEXT_VNOTE = "text/x-vNote";
    public static final String TEXT_VTASK = "text/x-vtodo";
    public static final String VIDEO_3G2 = "video/3gpp2";
    public static final String VIDEO_3GP = "video/3gp";
    public static final String VIDEO_3GPP = "video/3gpp";
    public static final String VIDEO_ASF = "video/x-ms-asf";
    public static final String VIDEO_AVI = "video/avi";
    public static final String VIDEO_DIVX = "video/divx";
    public static final String VIDEO_H263 = "video/h263";
    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_MP4V_ES = "video/mp4v-es";
    public static final String VIDEO_MPEG = "video/mpeg";
    public static final String VIDEO_UNSPECIFIED = "video/*";
    public static final String VIDEO_WMV = "video/x-ms-wmv";
    private static final ArrayList<String> sIsAudioTypes;
    private static final ArrayList<String> sIsImageTypes;
    private static final ArrayList<String> sIsVideoTypes;
    private static final ArrayList<String> sSupportedAudioTypes;
    private static final ArrayList<String> sSupportedContentTypes;
    private static final ArrayList<String> sSupportedImageTypes;
    private static final ArrayList<String> sSupportedVideoTypes;

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        sSupportedContentTypes = arrayList;
        ArrayList<String> arrayList2 = new ArrayList<>();
        sSupportedImageTypes = arrayList2;
        ArrayList<String> arrayList3 = new ArrayList<>();
        sSupportedAudioTypes = arrayList3;
        ArrayList<String> arrayList4 = new ArrayList<>();
        sSupportedVideoTypes = arrayList4;
        ArrayList<String> arrayList5 = new ArrayList<>();
        sIsImageTypes = arrayList5;
        ArrayList<String> arrayList6 = new ArrayList<>();
        sIsAudioTypes = arrayList6;
        ArrayList<String> arrayList7 = new ArrayList<>();
        sIsVideoTypes = arrayList7;
        arrayList.add("text/plain");
        arrayList.add("text/html");
        arrayList.add(TEXT_VCALENDAR);
        arrayList.add(TEXT_VCARD);
        arrayList.add(TEXT_VNOTE);
        arrayList.add(TEXT_VTASK);
        arrayList.add(IMAGE_JPEG);
        arrayList.add(IMAGE_GIF);
        arrayList.add(IMAGE_WBMP);
        arrayList.add(IMAGE_PNG);
        arrayList.add(IMAGE_JPG);
        arrayList.add(IMAGE_X_MS_BMP);
        arrayList.add(IMAGE_BMP);
        arrayList.add(IMAGE_XBMP);
        arrayList.add(AUDIO_AAC);
        arrayList.add(AUDIO_AMR);
        arrayList.add(AUDIO_IMELODY);
        arrayList.add(AUDIO_MID);
        arrayList.add(AUDIO_MIDI);
        arrayList.add(AUDIO_MP3);
        arrayList.add(AUDIO_MP4);
        arrayList.add(AUDIO_MPEG3);
        arrayList.add("audio/mpeg");
        arrayList.add(AUDIO_MPG);
        arrayList.add(AUDIO_X_MID);
        arrayList.add(AUDIO_X_MIDI);
        arrayList.add(AUDIO_X_MP3);
        arrayList.add(AUDIO_X_MPEG3);
        arrayList.add(AUDIO_X_MPEG);
        arrayList.add(AUDIO_X_MPG);
        arrayList.add(AUDIO_X_WAV);
        arrayList.add("audio/3gpp");
        arrayList.add("application/ogg");
        arrayList.add(AUDIO_OGG2);
        arrayList.add(AUDIO_TEXT_X_IMY);
        arrayList.add(AUDIO_TEXT_X_IMY_C);
        arrayList.add(AUDIO_SP_MIDI);
        arrayList.add("audio/mp4a-latm");
        arrayList.add(AUDIO_WAV);
        arrayList.add(AUDIO_WAVE);
        arrayList.add(AUDIO_X_WAV);
        arrayList.add(AUDIO_X_WAVE);
        arrayList.add("audio/flac");
        arrayList.add(AUDIO_X_FLAC);
        arrayList.add(AUDIO_WMA);
        arrayList.add(AUDIO_M4A);
        arrayList.add(AUDIO_MXMF);
        arrayList.add(AUDIO_XMF);
        arrayList.add(AUDIO_X_XMF);
        arrayList.add(AUDIO_ASF);
        arrayList.add("audio/amr-wb");
        arrayList.add("audio/qcelp");
        arrayList.add(AUDIO_QCELP_VND);
        arrayList.add(AUDIO_QCP);
        arrayList.add(AUDIO_EVRC);
        arrayList.add("video/3gpp");
        arrayList.add("video/3gpp2");
        arrayList.add(VIDEO_H263);
        arrayList.add("video/mp4");
        arrayList.add("video/mp4v-es");
        arrayList.add("video/3gp");
        arrayList.add("video/avi");
        arrayList.add("video/x-ms-wmv");
        arrayList.add("video/x-ms-asf");
        arrayList.add("video/divx");
        arrayList.add("video/mpeg");
        arrayList.add(APP_SMIL);
        arrayList.add(APP_WAP_XHTML);
        arrayList.add(APP_XHTML);
        arrayList.add(APP_DRM_CONTENT);
        arrayList.add("application/vnd.oma.drm.message");
        arrayList.add(APP_DRM_RIGHTS_WBXML);
        arrayList2.add(IMAGE_JPEG);
        arrayList2.add(IMAGE_GIF);
        arrayList2.add(IMAGE_WBMP);
        arrayList2.add(IMAGE_PNG);
        arrayList2.add(IMAGE_JPG);
        arrayList2.add(IMAGE_X_MS_BMP);
        arrayList2.add(IMAGE_BMP);
        arrayList2.add(IMAGE_XBMP);
        arrayList3.add(AUDIO_AAC);
        arrayList3.add(AUDIO_AMR);
        arrayList3.add(AUDIO_IMELODY);
        arrayList3.add(AUDIO_MID);
        arrayList3.add(AUDIO_MIDI);
        arrayList3.add(AUDIO_MP3);
        arrayList3.add(AUDIO_MPEG3);
        arrayList3.add("audio/mpeg");
        arrayList3.add(AUDIO_MPG);
        arrayList3.add(AUDIO_MP4);
        arrayList3.add(AUDIO_X_MID);
        arrayList3.add(AUDIO_X_MIDI);
        arrayList3.add(AUDIO_X_MP3);
        arrayList3.add(AUDIO_X_MPEG3);
        arrayList3.add(AUDIO_X_MPEG);
        arrayList3.add(AUDIO_X_MPG);
        arrayList3.add(AUDIO_X_WAV);
        arrayList3.add("audio/3gpp");
        arrayList3.add("application/ogg");
        arrayList3.add(AUDIO_OGG2);
        arrayList3.add(AUDIO_AAC_MP4);
        arrayList3.add("audio/qcelp");
        arrayList3.add(AUDIO_EVRC);
        arrayList3.add(AUDIO_X_AAC);
        arrayList3.add(AUDIO_AAC_ADTS);
        arrayList3.add(AUDIO_TEXT_X_IMY);
        arrayList3.add(AUDIO_TEXT_X_IMY_C);
        arrayList3.add(AUDIO_SP_MIDI);
        arrayList3.add("audio/mp4a-latm");
        arrayList3.add(AUDIO_WAV);
        arrayList3.add(AUDIO_WAVE);
        arrayList3.add(AUDIO_X_WAV);
        arrayList3.add(AUDIO_X_WAVE);
        arrayList3.add("audio/flac");
        arrayList3.add(AUDIO_X_FLAC);
        arrayList3.add(AUDIO_WMA);
        arrayList3.add(AUDIO_M4A);
        arrayList3.add(AUDIO_MXMF);
        arrayList3.add(AUDIO_XMF);
        arrayList3.add(AUDIO_X_XMF);
        arrayList3.add(AUDIO_ASF);
        arrayList3.add("audio/amr-wb");
        arrayList3.add("audio/qcelp");
        arrayList3.add(AUDIO_QCELP_VND);
        arrayList3.add(AUDIO_QCP);
        arrayList3.add(AUDIO_EVRC);
        arrayList4.add("video/3gpp");
        arrayList4.add("video/3gpp2");
        arrayList4.add(VIDEO_H263);
        arrayList4.add("video/mp4");
        arrayList4.add("video/mp4v-es");
        arrayList4.add("video/3gp");
        arrayList4.add("video/avi");
        arrayList4.add("video/x-ms-wmv");
        arrayList4.add("video/x-ms-asf");
        arrayList4.add("video/divx");
        arrayList5.add(IMAGE_JPEG);
        arrayList5.add(IMAGE_JPG);
        arrayList5.add(IMAGE_GIF);
        arrayList5.add(IMAGE_WBMP);
        arrayList5.add(IMAGE_PNG);
        arrayList5.add(IMAGE_BMP);
        arrayList5.add(IMAGE_XBMP);
        arrayList5.add(IMAGE_X_MS_BMP);
        arrayList6.add(AUDIO_AAC);
        arrayList6.add(AUDIO_X_AAC);
        arrayList6.add(AUDIO_AAC_ADTS);
        arrayList6.add(AUDIO_AMR);
        arrayList6.add(AUDIO_IMELODY);
        arrayList6.add(AUDIO_TEXT_X_IMY);
        arrayList6.add(AUDIO_TEXT_X_IMY_C);
        arrayList6.add(AUDIO_MID);
        arrayList6.add(AUDIO_MIDI);
        arrayList6.add(AUDIO_SP_MIDI);
        arrayList6.add(AUDIO_MP3);
        arrayList6.add(AUDIO_MPEG3);
        arrayList6.add("audio/mpeg");
        arrayList6.add(AUDIO_MPG);
        arrayList6.add(AUDIO_MP4);
        arrayList6.add("audio/mp4a-latm");
        arrayList6.add(AUDIO_X_MID);
        arrayList6.add(AUDIO_X_MIDI);
        arrayList6.add(AUDIO_X_MP3);
        arrayList6.add(AUDIO_X_MPEG3);
        arrayList6.add(AUDIO_X_MPEG);
        arrayList6.add(AUDIO_X_MPG);
        arrayList6.add("audio/3gpp");
        arrayList6.add("application/ogg");
        arrayList6.add(AUDIO_MMF);
        arrayList6.add(AUDIO_WAV);
        arrayList6.add(AUDIO_WAVE);
        arrayList6.add(AUDIO_X_WAV);
        arrayList6.add(AUDIO_X_WAVE);
        arrayList6.add("audio/flac");
        arrayList6.add(AUDIO_X_FLAC);
        arrayList6.add(AUDIO_WMA);
        arrayList6.add(AUDIO_M4A);
        arrayList6.add(AUDIO_MXMF);
        arrayList6.add(AUDIO_XMF);
        arrayList6.add(AUDIO_X_XMF);
        arrayList6.add(AUDIO_ASF);
        arrayList6.add("audio/amr-wb");
        arrayList6.add("audio/qcelp");
        arrayList6.add(AUDIO_QCELP_VND);
        arrayList6.add(AUDIO_QCP);
        arrayList6.add(AUDIO_EVRC);
        arrayList7.add("video/3gpp");
        arrayList7.add("video/3gpp2");
        arrayList7.add(VIDEO_H263);
        arrayList7.add("video/mp4");
        arrayList7.add("video/mp4v-es");
        arrayList7.add("video/3gp");
        arrayList7.add("video/avi");
        arrayList7.add("video/x-ms-wmv");
        arrayList7.add("video/x-ms-asf");
        arrayList7.add("video/divx");
    }

    private ContentType() {
    }

    public static boolean isSupportedType(String contentType) {
        return contentType != null && sSupportedContentTypes.contains(contentType);
    }

    public static boolean isSupportedImageType(String contentType) {
        return isImageType(contentType) && isSupportedType(contentType);
    }

    public static boolean isSupportedAudioType(String contentType) {
        return isAudioType(contentType) && isSupportedType(contentType);
    }

    public static boolean isSupportedVideoType(String contentType) {
        return isVideoType(contentType) && isSupportedType(contentType);
    }

    public static boolean isTextType(String contentType) {
        return contentType != null && contentType.startsWith("text/");
    }

    public static boolean isImageType(String contentType) {
        return contentType != null && (contentType.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX) || sSupportedImageTypes.contains(contentType));
    }

    public static boolean isAudioType(String contentType) {
        return contentType != null && (contentType.startsWith("audio/") || sSupportedAudioTypes.contains(contentType));
    }

    public static boolean isVideoType(String contentType) {
        return contentType != null && (contentType.startsWith(BnRConstants.VIDEO_DIR_PATH) || sSupportedVideoTypes.contains(contentType));
    }

    public static boolean isDrmType(String contentType) {
        return contentType != null && (contentType.equals(APP_DRM_CONTENT) || contentType.equals("application/vnd.oma.drm.message") || contentType.equals(APP_DRM_RIGHTS_WBXML));
    }

    public static boolean isUnspecified(String contentType) {
        return contentType != null && contentType.endsWith("*");
    }

    public static ArrayList<String> getImageTypes() {
        return (ArrayList) sSupportedImageTypes.clone();
    }

    public static ArrayList<String> getAudioTypes() {
        return (ArrayList) sSupportedAudioTypes.clone();
    }

    public static ArrayList<String> getVideoTypes() {
        return (ArrayList) sSupportedVideoTypes.clone();
    }

    public static ArrayList<String> getSupportedTypes() {
        return (ArrayList) sSupportedContentTypes.clone();
    }
}
