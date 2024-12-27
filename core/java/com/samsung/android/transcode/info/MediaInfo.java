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

public class MediaInfo {
    private static final String TAG = "MediaInfo";
    private static final int VIDEO_FPS_BUF_COUNT = 5;
    public static final MediaMetadataRetriever sMetadataRetriever = null;
    public static final MediaExtractor sMediaExtractor = null;
    private static int Framerate = 30;
    private static int FrameInterval = GLES30.GL_R32I;
    private static int Height = 0;
    private static int Width = 0;
    private static int iFrameInterval = -1;

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

    private MediaInfo() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    private static MediaMetadataRetriever newMetadataRetriever(
            String filepath, Context context, Uri uri) {
        MediaMetadataRetriever retriever =
                sMetadataRetriever != null ? sMetadataRetriever : new MediaMetadataRetriever();
        if (filepath != null) {
            retriever.setDataSource(filepath);
        } else {
            retriever.setDataSource(context, uri);
        }
        return retriever;
    }

    private static MediaExtractor newMediaExtractor(String filepath, Context context, Uri uri)
            throws IOException {
        MediaExtractor extractor = sMediaExtractor != null ? sMediaExtractor : new MediaExtractor();
        if (filepath != null) {
            extractor.setDataSource(filepath);
        } else {
            extractor.setDataSource(context, uri, (Map<String, String>) null);
        }
        return extractor;
    }

    public static MediaFileInfo getFileInfo(String filepath, Context context, Uri uri) {
        MediaMetadataRetriever retriever;
        MediaFileInfo info = new MediaFileInfo();
        if ((context == null || uri == null) && filepath == null) {
            LogS.d(
                    TAG,
                    "Can't get MediaInfo filepath : "
                            + filepath
                            + " or context : "
                            + context
                            + ", uri : "
                            + uri);
        } else {
            try {
                retriever = newMetadataRetriever(filepath, context, uri);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("invalid input file - can't get file info");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                String width = retriever.extractMetadata(18);
                String height = retriever.extractMetadata(19);
                String rotation = retriever.extractMetadata(24);
                int parseInt = Integer.parseInt((String) Optional.ofNullable(width).orElse("0"));
                Width = parseInt;
                info.Width = parseInt;
                int parseInt2 = Integer.parseInt((String) Optional.ofNullable(height).orElse("0"));
                Height = parseInt2;
                info.Height = parseInt2;
                info.Rotation =
                        Integer.parseInt((String) Optional.ofNullable(rotation).orElse("0"));
                String editedDuration = retriever.extractMetadata(1029);
                String duration = retriever.extractMetadata(9);
                String bitrate = retriever.extractMetadata(20);
                String transfer = retriever.extractMetadata(36);
                info.MimeType = retriever.extractMetadata(12);
                info.Writer = retriever.extractMetadata(11);
                info.EditedDuration =
                        Integer.parseInt((String) Optional.ofNullable(editedDuration).orElse("0"));
                info.Duration =
                        Integer.parseInt((String) Optional.ofNullable(duration).orElse("0"));
                info.Bitrate = Integer.parseInt((String) Optional.ofNullable(bitrate).orElse("0"));
                String auth = retriever.extractMetadata(1015);
                String recordingMode = retriever.extractMetadata(1022);
                String hdr10bit = retriever.extractMetadata(1027);
                String bitDepth = retriever.extractMetadata(1028);
                String is360 = retriever.extractMetadata(1021);
                info.Author = Integer.parseInt((String) Optional.ofNullable(auth).orElse("-1"));
                info.RecordingMode =
                        Integer.parseInt(
                                (String)
                                        Optional.ofNullable(recordingMode)
                                                .orElse(Integer.toString(0)));
                info.Bitdepth =
                        Integer.parseInt((String) Optional.ofNullable(bitDepth).orElse("8"));
                info.colorTransfer =
                        Integer.parseInt(
                                (String) Optional.ofNullable(transfer).orElse(String.valueOf(3)));
                info.HDR10 = "yes".equals(hdr10bit);
                info.Is360 = "1".equals(is360);
                getSEFSlowMotionInfo(info, retriever);
                getLocationInfo(info, retriever);
                if (retriever != null) {
                    retriever.close();
                }
                LogS.d(
                        TAG,
                        "Width : "
                                + info.Width
                                + ", Height : "
                                + info.Height
                                + ", RecordingMode : "
                                + info.RecordingMode
                                + ", Bitdepth :"
                                + info.Bitdepth
                                + ", ColorTransfer : "
                                + info.colorTransfer
                                + ", Author : "
                                + info.Author
                                + ",Is360 : "
                                + info.Is360
                                + ", HDR10 :"
                                + info.HDR10
                                + ", Duration : "
                                + info.Duration
                                + ", EditedDuration :"
                                + info.EditedDuration
                                + ", MimeType :"
                                + info.MimeType
                                + ", Rotation : "
                                + info.Rotation
                                + ",Bitrate : "
                                + info.Bitrate
                                + ", IsLocationAvailable : "
                                + info.IsLocationAvailable);
            } finally {
            }
        }
        return info;
    }

    private static void getSEFSlowMotionInfo(MediaFileInfo info, MediaMetadataRetriever retriever) {
        if (SEFHelper.isSEFVideoMode(info.RecordingMode)) {
            String sminfo = retriever.extractMetadata(1023);
            if (sminfo != null) {
                String[] splitData = sminfo.split("/");
                if (splitData.length > 0) {
                    info.NumOfSVCLayers = Integer.parseInt(splitData[0]);
                }
                if (splitData.length > 1) {
                    info.RecordingFramerate = Integer.parseInt(splitData[1]);
                }
            }
            if (info.RecordingFramerate == 0) {
                String fps = retriever.extractMetadata(25);
                info.RecordingFramerate =
                        Integer.parseInt((String) Optional.ofNullable(fps).orElse("0"));
            }
            LogS.d(
                    TAG,
                    "getSEFSlowMotionInfo  NumOfSVCLayers:"
                            + info.NumOfSVCLayers
                            + "RecordingFramerate:"
                            + info.RecordingFramerate);
        }
    }

    private static void getLocationInfo(MediaFileInfo info, MediaMetadataRetriever retriever) {
        String location = retriever.extractMetadata(23);
        if (location != null) {
            int lastIndex = location.lastIndexOf(47);
            if (lastIndex != -1) {
                location = location.substring(0, lastIndex);
            }
            int index = location.lastIndexOf(45);
            if (index == -1 || index == 0) {
                index = location.lastIndexOf(43);
            }
            info.latitude = Float.parseFloat(location.substring(0, index));
            info.longitude = Float.parseFloat(location.substring(index));
            if (info.latitude != 0.0f || info.longitude != 0.0f) {
                info.IsLocationAvailable = true;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x004c, code lost:

       if (r1 == null) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.media.MediaFormat getTrackInfo(
            java.lang.String r6, android.content.Context r7, android.net.Uri r8, boolean r9) {
        /*
            android.media.MediaFormat r0 = new android.media.MediaFormat
            r0.<init>()
            if (r7 == 0) goto L9
            if (r8 != 0) goto Lb
        L9:
            if (r6 == 0) goto L55
        Lb:
            r1 = 0
            android.media.MediaExtractor r2 = newMediaExtractor(r6, r7, r8)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            r1 = r2
            r2 = 0
        L12:
            int r3 = r1.getTrackCount()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r2 >= r3) goto L40
            android.media.MediaFormat r3 = r1.getTrackFormat(r2)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            java.lang.String r4 = "mime"
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r9 == 0) goto L34
            java.lang.String r5 = "video/"
            boolean r5 = r4.startsWith(r5)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r5 == 0) goto L3d
            r0 = r3
            setVideoFramerate(r1, r3)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            setIFrameInterval(r3)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            goto L3d
        L34:
            java.lang.String r5 = "audio/"
            boolean r5 = r4.startsWith(r5)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r5 == 0) goto L3d
            r0 = r3
        L3d:
            int r2 = r2 + 1
            goto L12
        L40:
            if (r1 == 0) goto L55
        L42:
            r1.release()
            goto L55
        L46:
            r2 = move-exception
            goto L4f
        L48:
            r2 = move-exception
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L46
            if (r1 == 0) goto L55
            goto L42
        L4f:
            if (r1 == 0) goto L54
            r1.release()
        L54:
            throw r2
        L55:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "trackinfo : "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "MediaInfo"
            com.samsung.android.transcode.util.LogS.d(r2, r1)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.transcode.info.MediaInfo.getTrackInfo(java.lang.String,"
                    + " android.content.Context, android.net.Uri,"
                    + " boolean):android.media.MediaFormat");
    }

    private static void setVideoFramerate(MediaExtractor extractor, MediaFormat videoFormat) {
        int frameRate = 0;
        try {
            frameRate = videoFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (frameRate == 0) {
            setFrameRateBySampleInterval(extractor);
        } else {
            Framerate = frameRate;
            FrameInterval = 1000000 / frameRate;
        }
        LogS.d(
                TAG,
                "setVideoFramerate Framerate: " + Framerate + ", FrameInterval : " + FrameInterval);
    }

    private static void setFrameRateBySampleInterval(MediaExtractor extractor) {
        long previousTime;
        long avgTime = 0;
        long previousTime2 = 0;
        int frameCount = 0;
        LogS.d(TAG, "Calculate Framerate");
        int bufferSizeV = Width * Height;
        if (bufferSizeV > 0) {
            ByteBuffer dstBufV = ByteBuffer.allocate(bufferSizeV);
            MediaCodec.BufferInfo bufferInfoV = new MediaCodec.BufferInfo();
            for (int i = 0; i <= 5; i++) {
                bufferInfoV.size = extractor.readSampleData(dstBufV, 0);
                long presentationTimeUs = extractor.getSampleTime();
                extractor.advance();
                if (i == 0) {
                    previousTime2 = presentationTimeUs;
                } else {
                    avgTime += presentationTimeUs - previousTime2;
                    previousTime2 = presentationTimeUs;
                    frameCount++;
                }
            }
            if (avgTime <= 0 || frameCount <= 0) {
                previousTime = previousTime2;
                LogS.d(
                        TAG,
                        "Fail to Calculate Framerate  avgTime :"
                                + avgTime
                                + ", frameCount : "
                                + frameCount);
            } else {
                FrameInterval =
                        ((int) (avgTime / ((long) frameCount))) > 0
                                ? (int) (avgTime / frameCount)
                                : GLES30.GL_R32I;
                previousTime = previousTime2;
                long previousTime3 = frameCount;
                int frameRate =
                        ((int) (1000 / ((avgTime / 1000) / previousTime3))) > 0
                                ? (int) (1000 / ((avgTime / 1000) / frameCount))
                                : 30;
                Framerate = frameRate;
            }
        }
    }

    public static int getVideoFramerate() {
        return Framerate;
    }

    public static int getVideoFrameInterval() {
        return FrameInterval;
    }

    private static void setIFrameInterval(MediaFormat videoFormat) {
        try {
            iFrameInterval = videoFormat.getInteger(MediaFormat.KEY_I_FRAME_INTERVAL, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogS.d(TAG, "setIFrameInterval iFrameInterval: " + iFrameInterval);
    }
}
