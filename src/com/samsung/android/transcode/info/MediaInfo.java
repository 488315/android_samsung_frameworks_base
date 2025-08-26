package com.samsung.android.transcode.info;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.opengl.GLES30;
import com.samsung.android.transcode.util.LogS;
import com.samsung.android.transcode.util.SEFHelper;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;

/* loaded from: classes6.dex */
public class MediaInfo {
    private static int FrameInterval = 33333;
    private static int Framerate = 30;
    private static int Height = 0;
    private static final String TAG = "MediaInfo";
    private static final int VIDEO_FPS_BUF_COUNT = 5;
    private static int Width = 0;
    private static int iFrameInterval = -1;
    public static final MediaExtractor sMediaExtractor = null;
    public static final MediaMetadataRetriever sMetadataRetriever = null;

    public static class MediaFileInfo {
        public int Height = 0;
        public int Width = 0;
        public int RecordingMode = 0;
        public int Bitdepth = 8;
        public int Author = -1;
        public boolean Is360 = false;
        public boolean HDR10 = false;
        public long Duration = 0;
        public long EditedDuration = 0;
        public String MimeType = "";
        public int Rotation = 0;
        public int Bitrate = 0;
        public int NumOfSVCLayers = 0;
        public float longitude = 0.0f;
        public float latitude = 0.0f;
        public boolean IsLocationAvailable = false;
        public int RecordingFramerate = 0;
        public String Writer = "";
        public int Framerate = 0;
        public String VideoCodecType = "";
        public int colorTransfer = 0;
    }

    protected static boolean isSlow120(int i, int i2) {
        if (i == 13 || i == 15) {
            return true;
        }
        return i == 21 && i2 == 120;
    }

    private MediaInfo() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    private static MediaMetadataRetriever newMetadataRetriever(String str, Context context, Uri uri) throws IOException, SecurityException, IllegalArgumentException {
        MediaMetadataRetriever mediaMetadataRetriever = sMetadataRetriever;
        if (mediaMetadataRetriever == null) {
            mediaMetadataRetriever = new MediaMetadataRetriever();
        }
        if (str != null) {
            mediaMetadataRetriever.setDataSource(str);
            return mediaMetadataRetriever;
        }
        mediaMetadataRetriever.setDataSource(context, uri);
        return mediaMetadataRetriever;
    }

    private static MediaExtractor newMediaExtractor(String str, Context context, Uri uri) throws IOException {
        MediaExtractor mediaExtractor = sMediaExtractor;
        if (mediaExtractor == null) {
            mediaExtractor = new MediaExtractor();
        }
        if (str != null) {
            mediaExtractor.setDataSource(str);
            return mediaExtractor;
        }
        mediaExtractor.setDataSource(context, uri, (Map<String, String>) null);
        return mediaExtractor;
    }

    public static MediaFileInfo getFileInfo(String str, Context context, Uri uri) {
        MediaFileInfo mediaFileInfo = new MediaFileInfo();
        if ((context != null && uri != null) || str != null) {
            try {
                MediaMetadataRetriever mediaMetadataRetrieverNewMetadataRetriever = newMetadataRetriever(str, context, uri);
                try {
                    String strExtractMetadata = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(18);
                    String strExtractMetadata2 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(19);
                    String strExtractMetadata3 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(24);
                    int i = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata).orElse("0"));
                    Width = i;
                    mediaFileInfo.Width = i;
                    int i2 = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata2).orElse("0"));
                    Height = i2;
                    mediaFileInfo.Height = i2;
                    mediaFileInfo.Rotation = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata3).orElse("0"));
                    String strExtractMetadata4 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(1029);
                    String strExtractMetadata5 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(9);
                    String strExtractMetadata6 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(20);
                    String strExtractMetadata7 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(36);
                    mediaFileInfo.MimeType = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(12);
                    mediaFileInfo.Writer = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(11);
                    mediaFileInfo.EditedDuration = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata4).orElse("0"));
                    mediaFileInfo.Duration = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata5).orElse("0"));
                    mediaFileInfo.Bitrate = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata6).orElse("0"));
                    String strExtractMetadata8 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(1015);
                    String strExtractMetadata9 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(1022);
                    String strExtractMetadata10 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(1027);
                    String strExtractMetadata11 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(1028);
                    String strExtractMetadata12 = mediaMetadataRetrieverNewMetadataRetriever.extractMetadata(1021);
                    mediaFileInfo.Author = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata8).orElse("-1"));
                    mediaFileInfo.RecordingMode = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata9).orElse(Integer.toString(0)));
                    mediaFileInfo.Bitdepth = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata11).orElse("8"));
                    mediaFileInfo.colorTransfer = Integer.parseInt((String) Optional.ofNullable(strExtractMetadata7).orElse(String.valueOf(3)));
                    mediaFileInfo.HDR10 = "yes".equals(strExtractMetadata10);
                    mediaFileInfo.Is360 = "1".equals(strExtractMetadata12);
                    getSEFSlowMotionInfo(mediaFileInfo, mediaMetadataRetrieverNewMetadataRetriever);
                    updateSEFSlowMotionDuration(context, mediaFileInfo, str, uri);
                    getLocationInfo(mediaFileInfo, mediaMetadataRetrieverNewMetadataRetriever);
                    if (mediaMetadataRetrieverNewMetadataRetriever != null) {
                        mediaMetadataRetrieverNewMetadataRetriever.close();
                    }
                } catch (Throwable th) {
                    if (mediaMetadataRetrieverNewMetadataRetriever != null) {
                        try {
                            mediaMetadataRetrieverNewMetadataRetriever.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IllegalArgumentException unused) {
                throw new IllegalArgumentException("invalid input file - can't get file info");
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogS.d(TAG, "Width : " + mediaFileInfo.Width + ", Height : " + mediaFileInfo.Height + ", RecordingMode : " + mediaFileInfo.RecordingMode + ", Bitdepth :" + mediaFileInfo.Bitdepth + ", ColorTransfer : " + mediaFileInfo.colorTransfer + ", Author : " + mediaFileInfo.Author + ",Is360 : " + mediaFileInfo.Is360 + ", HDR10 :" + mediaFileInfo.HDR10 + ", Duration : " + mediaFileInfo.Duration + ", EditedDuration :" + mediaFileInfo.EditedDuration + ", MimeType :" + mediaFileInfo.MimeType + ", Rotation : " + mediaFileInfo.Rotation + ",Bitrate : " + mediaFileInfo.Bitrate + ", IsLocationAvailable : " + mediaFileInfo.IsLocationAvailable);
            return mediaFileInfo;
        }
        LogS.d(TAG, "Can't get MediaInfo filepath : " + str + " or context : " + context + ", uri : " + uri);
        return mediaFileInfo;
    }

    private static void getSEFSlowMotionInfo(MediaFileInfo mediaFileInfo, MediaMetadataRetriever mediaMetadataRetriever) {
        if (SEFHelper.isSEFVideoMode(mediaFileInfo.RecordingMode)) {
            String strExtractMetadata = mediaMetadataRetriever.extractMetadata(1023);
            if (strExtractMetadata != null) {
                String[] strArrSplit = strExtractMetadata.split("/");
                if (strArrSplit.length > 0) {
                    mediaFileInfo.NumOfSVCLayers = Integer.parseInt(strArrSplit[0]);
                }
                if (strArrSplit.length > 1) {
                    mediaFileInfo.RecordingFramerate = Integer.parseInt(strArrSplit[1]);
                }
            }
            if (mediaFileInfo.RecordingFramerate == 0) {
                mediaFileInfo.RecordingFramerate = Integer.parseInt((String) Optional.ofNullable(mediaMetadataRetriever.extractMetadata(25)).orElse("0"));
            }
            LogS.d(TAG, "getSEFSlowMotionInfo  NumOfSVCLayers:" + mediaFileInfo.NumOfSVCLayers + "RecordingFramerate:" + mediaFileInfo.RecordingFramerate);
        }
    }

    private static void updateSEFSlowMotionDuration(Context context, MediaFileInfo mediaFileInfo, String str, Uri uri) throws IOException {
        if (SEFHelper.isSEFVideoMode(mediaFileInfo.RecordingMode)) {
            SEFHelper sEFHelper = new SEFHelper();
            sEFHelper.initialize(str, context, uri);
            if (sEFHelper.extractSEFData(mediaFileInfo.RecordingMode, mediaFileInfo.Duration) == null) {
                long j = mediaFileInfo.Duration;
                if (isSlow120(mediaFileInfo.RecordingMode, mediaFileInfo.RecordingFramerate)) {
                    j *= 2;
                }
                if (sEFHelper.checkSEFData(mediaFileInfo.RecordingMode, mediaFileInfo.RecordingFramerate, j)) {
                    mediaFileInfo.EditedDuration = sEFHelper.getConvertedTime(mediaFileInfo.Duration * 1000) / 1000;
                    LogS.d(TAG, "updateSEFSlowMotionDuration EditedDuration : " + mediaFileInfo.EditedDuration);
                }
            }
        }
    }

    private static void getLocationInfo(MediaFileInfo mediaFileInfo, MediaMetadataRetriever mediaMetadataRetriever) {
        String strExtractMetadata = mediaMetadataRetriever.extractMetadata(23);
        if (strExtractMetadata != null) {
            int iLastIndexOf = strExtractMetadata.lastIndexOf(47);
            if (iLastIndexOf != -1) {
                strExtractMetadata = strExtractMetadata.substring(0, iLastIndexOf);
            }
            int iLastIndexOf2 = strExtractMetadata.lastIndexOf(45);
            if (iLastIndexOf2 == -1 || iLastIndexOf2 == 0) {
                iLastIndexOf2 = strExtractMetadata.lastIndexOf(43);
            }
            mediaFileInfo.latitude = Float.parseFloat(strExtractMetadata.substring(0, iLastIndexOf2));
            mediaFileInfo.longitude = Float.parseFloat(strExtractMetadata.substring(iLastIndexOf2));
            if (mediaFileInfo.latitude == 0.0f && mediaFileInfo.longitude == 0.0f) {
                return;
            }
            mediaFileInfo.IsLocationAvailable = true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0045 A[DONT_GENERATE, PHI: r0 r1
      0x0045: PHI (r0v3 android.media.MediaFormat) = (r0v4 android.media.MediaFormat), (r0v5 android.media.MediaFormat) binds: [B:28:0x004f, B:22:0x0043] A[DONT_GENERATE, DONT_INLINE]
      0x0045: PHI (r1v3 android.media.MediaExtractor) = (r1v4 android.media.MediaExtractor), (r1v5 android.media.MediaExtractor) binds: [B:28:0x004f, B:22:0x0043] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MediaFormat getTrackInfo(String str, Context context, Uri uri, boolean z) {
        MediaFormat mediaFormat = new MediaFormat();
        if ((context != null && uri != null) || str != null) {
            MediaExtractor mediaExtractorNewMediaExtractor = null;
            try {
                try {
                    mediaExtractorNewMediaExtractor = newMediaExtractor(str, context, uri);
                    for (int i = 0; i < mediaExtractorNewMediaExtractor.getTrackCount(); i++) {
                        MediaFormat trackFormat = mediaExtractorNewMediaExtractor.getTrackFormat(i);
                        String string = trackFormat.getString("mime");
                        if (z) {
                            if (string.startsWith(BnRConstants.VIDEO_DIR_PATH)) {
                                try {
                                    setVideoFramerate(mediaExtractorNewMediaExtractor, trackFormat);
                                    setIFrameInterval(trackFormat);
                                    mediaFormat = trackFormat;
                                } catch (Exception e) {
                                    e = e;
                                    mediaFormat = trackFormat;
                                    e.printStackTrace();
                                    if (mediaExtractorNewMediaExtractor != null) {
                                    }
                                    LogS.d(TAG, "trackinfo : " + mediaFormat);
                                    return mediaFormat;
                                }
                            } else {
                                continue;
                            }
                        } else if (string.startsWith("audio/")) {
                            mediaFormat = trackFormat;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } finally {
                if (mediaExtractorNewMediaExtractor != null) {
                    mediaExtractorNewMediaExtractor.release();
                }
            }
        }
        LogS.d(TAG, "trackinfo : " + mediaFormat);
        return mediaFormat;
    }

    private static void setVideoFramerate(MediaExtractor mediaExtractor, MediaFormat mediaFormat) {
        int integer;
        try {
            integer = mediaFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception e) {
            e.printStackTrace();
            integer = 0;
        }
        if (integer == 0) {
            setFrameRateBySampleInterval(mediaExtractor);
        } else {
            Framerate = integer;
            FrameInterval = 1000000 / integer;
        }
        LogS.d(TAG, "setVideoFramerate Framerate: " + Framerate + ", FrameInterval : " + FrameInterval);
    }

    private static void setFrameRateBySampleInterval(MediaExtractor mediaExtractor) {
        LogS.d(TAG, "Calculate Framerate");
        int i = Width * Height;
        if (i > 0) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int i2 = 0;
            int i3 = 0;
            long j = 0;
            long j2 = 0;
            while (i2 <= 5) {
                bufferInfo.size = mediaExtractor.readSampleData(byteBufferAllocate, 0);
                long sampleTime = mediaExtractor.getSampleTime();
                mediaExtractor.advance();
                if (i2 != 0) {
                    j += sampleTime - j2;
                    i3++;
                }
                i2++;
                j2 = sampleTime;
            }
            if (j > 0 && i3 > 0) {
                long j3 = i3;
                int i4 = (int) (j / j3);
                if (i4 <= 0) {
                    i4 = GLES30.GL_R32I;
                }
                FrameInterval = i4;
                int i5 = (int) (1000 / ((j / 1000) / j3));
                if (i5 <= 0) {
                    i5 = 30;
                }
                Framerate = i5;
                return;
            }
            LogS.d(TAG, "Fail to Calculate Framerate  avgTime :" + j + ", frameCount : " + i3);
        }
    }

    public static int getVideoFramerate() {
        return Framerate;
    }

    public static int getVideoFrameInterval() {
        return FrameInterval;
    }

    private static void setIFrameInterval(MediaFormat mediaFormat) {
        try {
            iFrameInterval = mediaFormat.getInteger(MediaFormat.KEY_I_FRAME_INTERVAL, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogS.d(TAG, "setIFrameInterval iFrameInterval: " + iFrameInterval);
    }
}
