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

    private static MediaMetadataRetriever newMetadataRetriever(String str, Context context, Uri uri) {
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
        MediaMetadataRetriever newMetadataRetriever;
        MediaFileInfo mediaFileInfo = new MediaFileInfo();
        if ((context != null && uri != null) || str != null) {
            try {
                newMetadataRetriever = newMetadataRetriever(str, context, uri);
            } catch (IllegalArgumentException unused) {
                throw new IllegalArgumentException("invalid input file - can't get file info");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String extractMetadata = newMetadataRetriever.extractMetadata(18);
                String extractMetadata2 = newMetadataRetriever.extractMetadata(19);
                String extractMetadata3 = newMetadataRetriever.extractMetadata(24);
                int parseInt = Integer.parseInt((String) Optional.ofNullable(extractMetadata).orElse("0"));
                Width = parseInt;
                mediaFileInfo.Width = parseInt;
                int parseInt2 = Integer.parseInt((String) Optional.ofNullable(extractMetadata2).orElse("0"));
                Height = parseInt2;
                mediaFileInfo.Height = parseInt2;
                mediaFileInfo.Rotation = Integer.parseInt((String) Optional.ofNullable(extractMetadata3).orElse("0"));
                String extractMetadata4 = newMetadataRetriever.extractMetadata(1029);
                String extractMetadata5 = newMetadataRetriever.extractMetadata(9);
                String extractMetadata6 = newMetadataRetriever.extractMetadata(20);
                String extractMetadata7 = newMetadataRetriever.extractMetadata(36);
                mediaFileInfo.MimeType = newMetadataRetriever.extractMetadata(12);
                mediaFileInfo.Writer = newMetadataRetriever.extractMetadata(11);
                mediaFileInfo.EditedDuration = Integer.parseInt((String) Optional.ofNullable(extractMetadata4).orElse("0"));
                mediaFileInfo.Duration = Integer.parseInt((String) Optional.ofNullable(extractMetadata5).orElse("0"));
                mediaFileInfo.Bitrate = Integer.parseInt((String) Optional.ofNullable(extractMetadata6).orElse("0"));
                String extractMetadata8 = newMetadataRetriever.extractMetadata(1015);
                String extractMetadata9 = newMetadataRetriever.extractMetadata(1022);
                String extractMetadata10 = newMetadataRetriever.extractMetadata(1027);
                String extractMetadata11 = newMetadataRetriever.extractMetadata(1028);
                String extractMetadata12 = newMetadataRetriever.extractMetadata(1021);
                mediaFileInfo.Author = Integer.parseInt((String) Optional.ofNullable(extractMetadata8).orElse("-1"));
                mediaFileInfo.RecordingMode = Integer.parseInt((String) Optional.ofNullable(extractMetadata9).orElse(Integer.toString(0)));
                mediaFileInfo.Bitdepth = Integer.parseInt((String) Optional.ofNullable(extractMetadata11).orElse("8"));
                mediaFileInfo.colorTransfer = Integer.parseInt((String) Optional.ofNullable(extractMetadata7).orElse(String.valueOf(3)));
                mediaFileInfo.HDR10 = "yes".equals(extractMetadata10);
                mediaFileInfo.Is360 = "1".equals(extractMetadata12);
                getSEFSlowMotionInfo(mediaFileInfo, newMetadataRetriever);
                updateSEFSlowMotionDuration(context, mediaFileInfo, str, uri);
                getLocationInfo(mediaFileInfo, newMetadataRetriever);
                if (newMetadataRetriever != null) {
                    newMetadataRetriever.close();
                }
                LogS.d(TAG, "Width : " + mediaFileInfo.Width + ", Height : " + mediaFileInfo.Height + ", RecordingMode : " + mediaFileInfo.RecordingMode + ", Bitdepth :" + mediaFileInfo.Bitdepth + ", ColorTransfer : " + mediaFileInfo.colorTransfer + ", Author : " + mediaFileInfo.Author + ",Is360 : " + mediaFileInfo.Is360 + ", HDR10 :" + mediaFileInfo.HDR10 + ", Duration : " + mediaFileInfo.Duration + ", EditedDuration :" + mediaFileInfo.EditedDuration + ", MimeType :" + mediaFileInfo.MimeType + ", Rotation : " + mediaFileInfo.Rotation + ",Bitrate : " + mediaFileInfo.Bitrate + ", IsLocationAvailable : " + mediaFileInfo.IsLocationAvailable);
                return mediaFileInfo;
            } catch (Throwable th) {
                if (newMetadataRetriever != null) {
                    try {
                        newMetadataRetriever.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        LogS.d(TAG, "Can't get MediaInfo filepath : " + str + " or context : " + context + ", uri : " + uri);
        return mediaFileInfo;
    }

    private static void getSEFSlowMotionInfo(MediaFileInfo mediaFileInfo, MediaMetadataRetriever mediaMetadataRetriever) {
        if (SEFHelper.isSEFVideoMode(mediaFileInfo.RecordingMode)) {
            String extractMetadata = mediaMetadataRetriever.extractMetadata(1023);
            if (extractMetadata != null) {
                String[] split = extractMetadata.split("/");
                if (split.length > 0) {
                    mediaFileInfo.NumOfSVCLayers = Integer.parseInt(split[0]);
                }
                if (split.length > 1) {
                    mediaFileInfo.RecordingFramerate = Integer.parseInt(split[1]);
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
        String extractMetadata = mediaMetadataRetriever.extractMetadata(23);
        if (extractMetadata != null) {
            int lastIndexOf = extractMetadata.lastIndexOf(47);
            if (lastIndexOf != -1) {
                extractMetadata = extractMetadata.substring(0, lastIndexOf);
            }
            int lastIndexOf2 = extractMetadata.lastIndexOf(45);
            if (lastIndexOf2 == -1 || lastIndexOf2 == 0) {
                lastIndexOf2 = extractMetadata.lastIndexOf(43);
            }
            mediaFileInfo.latitude = Float.parseFloat(extractMetadata.substring(0, lastIndexOf2));
            mediaFileInfo.longitude = Float.parseFloat(extractMetadata.substring(lastIndexOf2));
            if (mediaFileInfo.latitude == 0.0f && mediaFileInfo.longitude == 0.0f) {
                return;
            }
            mediaFileInfo.IsLocationAvailable = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x004f, code lost:
    
        if (r1 == null) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.media.MediaFormat getTrackInfo(java.lang.String r3, android.content.Context r4, android.net.Uri r5, boolean r6) {
        /*
            android.media.MediaFormat r0 = new android.media.MediaFormat
            r0.<init>()
            if (r4 == 0) goto L9
            if (r5 != 0) goto Lb
        L9:
            if (r3 == 0) goto L58
        Lb:
            r1 = 0
            android.media.MediaExtractor r1 = newMediaExtractor(r3, r4, r5)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4b
            r3 = 0
        L11:
            int r4 = r1.getTrackCount()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4b
            if (r3 >= r4) goto L43
            android.media.MediaFormat r4 = r1.getTrackFormat(r3)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4b
            java.lang.String r5 = "mime"
            java.lang.String r5 = r4.getString(r5)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4b
            if (r6 == 0) goto L37
            java.lang.String r2 = "video/"
            boolean r5 = r5.startsWith(r2)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4b
            if (r5 == 0) goto L40
            setVideoFramerate(r1, r4)     // Catch: java.lang.Exception -> L34 java.lang.Throwable -> L49
            setIFrameInterval(r4)     // Catch: java.lang.Exception -> L34 java.lang.Throwable -> L49
            goto L3f
        L34:
            r3 = move-exception
            r0 = r4
            goto L4c
        L37:
            java.lang.String r2 = "audio/"
            boolean r5 = r5.startsWith(r2)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4b
            if (r5 == 0) goto L40
        L3f:
            r0 = r4
        L40:
            int r3 = r3 + 1
            goto L11
        L43:
            if (r1 == 0) goto L58
        L45:
            r1.release()
            goto L58
        L49:
            r3 = move-exception
            goto L52
        L4b:
            r3 = move-exception
        L4c:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L49
            if (r1 == 0) goto L58
            goto L45
        L52:
            if (r1 == 0) goto L57
            r1.release()
        L57:
            throw r3
        L58:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "trackinfo : "
            r3.<init>(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "MediaInfo"
            com.samsung.android.transcode.util.LogS.d(r4, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.transcode.info.MediaInfo.getTrackInfo(java.lang.String, android.content.Context, android.net.Uri, boolean):android.media.MediaFormat");
    }

    private static void setVideoFramerate(MediaExtractor mediaExtractor, MediaFormat mediaFormat) {
        int i;
        try {
            i = mediaFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        if (i == 0) {
            setFrameRateBySampleInterval(mediaExtractor);
        } else {
            Framerate = i;
            FrameInterval = 1000000 / i;
        }
        LogS.d(TAG, "setVideoFramerate Framerate: " + Framerate + ", FrameInterval : " + FrameInterval);
    }

    private static void setFrameRateBySampleInterval(MediaExtractor mediaExtractor) {
        LogS.d(TAG, "Calculate Framerate");
        int i = Width * Height;
        if (i > 0) {
            ByteBuffer allocate = ByteBuffer.allocate(i);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int i2 = 0;
            int i3 = 0;
            long j = 0;
            long j2 = 0;
            while (i2 <= 5) {
                bufferInfo.size = mediaExtractor.readSampleData(allocate, 0);
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
