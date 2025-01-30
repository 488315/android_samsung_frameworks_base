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

/* loaded from: classes5.dex */
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
    }

    private MediaInfo() throws InstantiationException {
        throw new InstantiationException("do not instatiate");
    }

    private static MediaMetadataRetriever newMetadataRetriever(String filepath, Context context, Uri uri) {
        MediaMetadataRetriever retriever = sMetadataRetriever;
        if (retriever == null) {
            retriever = new MediaMetadataRetriever();
        }
        if (filepath != null) {
            retriever.setDataSource(filepath);
        } else {
            retriever.setDataSource(context, uri);
        }
        return retriever;
    }

    private static MediaExtractor newMediaExtractor(String filepath, Context context, Uri uri) throws IOException {
        MediaExtractor extractor = sMediaExtractor;
        if (extractor == null) {
            extractor = new MediaExtractor();
        }
        if (filepath != null) {
            extractor.setDataSource(filepath);
        } else {
            extractor.setDataSource(context, uri, (Map<String, String>) null);
        }
        return extractor;
    }

    public static MediaFileInfo getFileInfo(String filepath, Context context, Uri uri) {
        MediaFileInfo info = new MediaFileInfo();
        if ((context == null || uri == null) && filepath == null) {
            LogS.m473d(TAG, "Can't get MediaInfo filepath : " + filepath + " or context : " + context + ", uri : " + uri);
        } else {
            try {
                MediaMetadataRetriever retriever = newMetadataRetriever(filepath, context, uri);
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
                    info.Rotation = Integer.parseInt((String) Optional.ofNullable(rotation).orElse("0"));
                    String editedDuration = retriever.extractMetadata(1029);
                    String duration = retriever.extractMetadata(9);
                    String bitrate = retriever.extractMetadata(20);
                    info.MimeType = retriever.extractMetadata(12);
                    info.Writer = retriever.extractMetadata(11);
                    info.EditedDuration = Integer.parseInt((String) Optional.ofNullable(editedDuration).orElse("0"));
                    info.Duration = Integer.parseInt((String) Optional.ofNullable(duration).orElse("0"));
                    info.Bitrate = Integer.parseInt((String) Optional.ofNullable(bitrate).orElse("0"));
                    String auth = retriever.extractMetadata(1015);
                    String recordingMode = retriever.extractMetadata(1022);
                    String hdr10bit = retriever.extractMetadata(1027);
                    String bitDepth = retriever.extractMetadata(1028);
                    String is360 = retriever.extractMetadata(1021);
                    info.Author = Integer.parseInt((String) Optional.ofNullable(auth).orElse("-1"));
                    info.RecordingMode = Integer.parseInt((String) Optional.ofNullable(recordingMode).orElse(Integer.toString(0)));
                    info.Bitdepth = Integer.parseInt((String) Optional.ofNullable(bitDepth).orElse("8"));
                    info.HDR10 = "yes".equals(hdr10bit);
                    info.Is360 = "1".equals(is360);
                    getSEFSlowMotionInfo(info, retriever);
                    getLocationInfo(info, retriever);
                    if (retriever != null) {
                        retriever.close();
                    }
                } finally {
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("invalid input file - can't get file info");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            LogS.m473d(TAG, "Width : " + info.Width + ", Height : " + info.Height + ", RecordingMode : " + info.RecordingMode + ", Bitdepth :" + info.Bitdepth + ", Author : " + info.Author + ",Is360 : " + info.Is360 + ", HDR10 :" + info.HDR10 + ", Duration : " + info.Duration + ", EditedDuration :" + info.EditedDuration + ", MimeType :" + info.MimeType + ", Rotation : " + info.Rotation + ",Bitrate : " + info.Bitrate + ", IsLocationAvailable : " + info.IsLocationAvailable);
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
                info.RecordingFramerate = Integer.parseInt((String) Optional.ofNullable(fps).orElse("0"));
            }
            LogS.m473d(TAG, "getSEFSlowMotionInfo  NumOfSVCLayers:" + info.NumOfSVCLayers + "RecordingFramerate:" + info.RecordingFramerate);
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

    /* JADX WARN: Code restructure failed: missing block: B:31:0x004e, code lost:
    
        if (r1 == null) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MediaFormat getTrackInfo(String filepath, Context context, Uri uri, boolean isVideo) {
        MediaFormat trackinfo = new MediaFormat();
        if ((context != null && uri != null) || filepath != null) {
            MediaExtractor extractor = null;
            try {
                try {
                    extractor = newMediaExtractor(filepath, context, uri);
                    for (int index = 0; index < extractor.getTrackCount(); index++) {
                        MediaFormat format = extractor.getTrackFormat(index);
                        String mime = format.getString(MediaFormat.KEY_MIME);
                        if (isVideo) {
                            if (mime.startsWith(BnRConstants.VIDEO_DIR_PATH)) {
                                trackinfo = format;
                                setVideoFramerate(extractor, format);
                                setIFrameInterval(format);
                            }
                        } else if (mime.startsWith("audio/")) {
                            trackinfo = format;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                if (extractor != null) {
                    extractor.release();
                }
            }
        }
        LogS.m473d(TAG, "trackinfo : " + trackinfo);
        return trackinfo;
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
        LogS.m473d(TAG, "setVideoFramerate Framerate: " + Framerate + ", FrameInterval : " + FrameInterval);
    }

    private static void setFrameRateBySampleInterval(MediaExtractor extractor) {
        long previousTime;
        long avgTime = 0;
        long previousTime2 = 0;
        int frameCount = 0;
        LogS.m473d(TAG, "Calculate Framerate");
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
                LogS.m473d(TAG, "Fail to Calculate Framerate  avgTime :" + avgTime + ", frameCount : " + frameCount);
            } else {
                FrameInterval = ((int) (avgTime / ((long) frameCount))) > 0 ? (int) (avgTime / frameCount) : GLES30.GL_R32I;
                previousTime = previousTime2;
                long previousTime3 = frameCount;
                int frameRate = ((int) (1000 / ((avgTime / 1000) / previousTime3))) > 0 ? (int) (1000 / ((avgTime / 1000) / frameCount)) : 30;
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
        LogS.m473d(TAG, "setIFrameInterval iFrameInterval: " + iFrameInterval);
    }
}
