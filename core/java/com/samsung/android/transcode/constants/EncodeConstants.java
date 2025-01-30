package com.samsung.android.transcode.constants;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class EncodeConstants {
  public static final int Error_Codec_Config = -1003;
  public static final int Error_Invalid_File = -1001;
  public static final int Error_None = 0;
  public static final int Error_Resolution_limit = -1004;
  public static final int Error_Unsupported_Codec = -1002;
  public static final int FPS_120 = 120;

  public static final class BitRate {
    public static final int AUDIO_AAC_BITRATE = 128;
    public static final int MID_AUDIO_AAC_BITRATE = 64;
    public static final int MIN_AUDIO_AAC_BITRATE = 32;
    public static final int MIN_AUDIO_AMR_BITRATE = 8;
    public static final int MM_AVG_25K_DATARATE = 25000;
    public static final int MM_AVG_D1_DATARATE = 5000;
    public static final int MM_AVG_FHD_DATARATE = 13000;
    public static final int MM_AVG_FUHD_DATARATE = 80000;
    public static final int MM_AVG_HD_DATARATE = 8000;
    public static final int MM_AVG_QCIF_DATARATE = 280;
    public static final int MM_AVG_QHD_DATARATE = 18000;
    public static final int MM_AVG_QVGA_DATARATE = 512;
    public static final int MM_AVG_UHD_DATARATE = 35000;
    public static final int MM_AVG_VGA_DATARATE = 5000;
    public static final int MM_BITRATE_10_HEVC_FHD_30 = 25000;
    public static final int MM_BITRATE_10_HEVC_FHD_60 = 37000;
    public static final int MM_BITRATE_10_HEVC_FULL_30 = 30000;
    public static final int MM_BITRATE_10_HEVC_HD_30 = 15000;
    public static final int MM_BITRATE_10_HEVC_UHD_30 = 54000;
    public static final int MM_BITRATE_10_HEVC_UHD_60 = 75000;
    public static final int MM_BITRATE_AVC_D1_30 = 8000;
    public static final int MM_BITRATE_AVC_FHD_30 = 17000;
    public static final int MM_BITRATE_AVC_FHD_60 = 28000;
    public static final int MM_BITRATE_AVC_HD_30 = 12000;
    public static final int MM_BITRATE_AVC_QHD_30 = 25000;
    public static final int MM_BITRATE_AVC_UHD_30 = 48000;
    public static final int MM_BITRATE_AVC_UHD_60 = 72000;
    public static final int MM_BITRATE_HEVC_FUHD_24 = 80000;
  }

  public static final class CodecsMime {
    public static final String AUDIO_CODEC_AAC = "audio/mp4a-latm";
    public static final String AUDIO_CODEC_AMR = "audio/3gpp";
    public static final String AUDIO_CODEC_AMR_WB = "audio/amr-wb";
    public static final String VIDEO_CODEC_H263 = "video/3gpp";
    public static final String VIDEO_CODEC_H264 = "video/avc";
    public static final String VIDEO_CODEC_H265 = "video/hevc";
    public static final String VIDEO_CODEC_MPEG4 = "video/mp4v-es";
  }

  public static final class EncodeResolution {

    /* renamed from: D1 */
    public static final int f3071D1 = 4;
    public static final int FULL_HD = 6;

    /* renamed from: HD */
    public static final int f3072HD = 5;
    public static final int QCIF = 1;
    public static final int QHD = 7;
    public static final int QVGA = 2;
    public static final int UHD = 8;
    public static final int VGA = 3;
  }

  public static final class ExportRecordingMode {
    public static final int FAST_NORMAL = 121;
    public static final int HDR_10 = 11;
    public static final int HDR_10_PLUS = 10;
    public static final int HYPERLAPSE_NORMAL = 122;
    public static final int NORMAL = 0;
    public static final int SLOW_NORMAL = 101;
    public static final int SUPERSLOW_MULTI_NORMAL = 112;
    public static final int SUPERSLOW_NORMAL = 111;
    public static final int UNSUPPORT = -1;
  }

  public static final class HDRType {
    public static final int HDR_10 = 1;
    public static final int HDR_10_PLUS = 2;
    public static final int NONE_HDR = 0;
  }

  public static final class RecordingMode {
    public static final int FAST = 2;
    public static final int HDR_10 = 11;
    public static final int HDR_10_PLUS = 10;
    public static final int HYPERLAPSE = 5;
    public static final int NORMAL = 0;
    public static final int PRO_HDR_10_PLUS = 25;
    public static final int SLOW = 1;
    public static final int SLOW_V2 = 12;
    public static final int SLOW_V2_120 = 13;
    public static final int SLOW_V2_120_NONE_SVC = 15;
    public static final int SLOW_V2_HEVC = 21;
    public static final int SLOW_V2_NONE_SVC = 19;
    public static final int SUPER_SLOW = 8;
    public static final int SUPER_SLOW_FRC = 9;
    public static final int SUPER_SLOW_FRC_HEVC = 22;
    public static final int SUPER_SLOW_NONE_SVC = 18;
    public static final int SUPER_SLOW_SINGLE = 7;
  }

  public static final class Resolution {
    public static final int MM_360_EXPORT_HEIGHT_1280 = 1280;
    public static final int MM_360_EXPORT_HEIGHT_1440 = 1440;
    public static final int MM_360_EXPORT_HEIGHT_1920 = 1920;
    public static final int MM_360_EXPORT_HEIGHT_640 = 640;
    public static final int MM_360_EXPORT_HEIGHT_960 = 960;
    public static final int MM_360_EXPORT_WIDTH_1280 = 1280;
    public static final int MM_360_EXPORT_WIDTH_1920 = 1920;
    public static final int MM_360_EXPORT_WIDTH_2560 = 2560;
    public static final int MM_360_EXPORT_WIDTH_2880 = 2880;
    public static final int MM_360_EXPORT_WIDTH_3840 = 3840;
    public static final int MM_8K_HEIGHT = 4320;
    public static final int MM_8K_WIDTH = 8192;
    public static final int MM_D1_HEIGHT = 480;
    public static final int MM_D1_WIDTH = 720;
    public static final int MM_FULL_HD_HEIGHT = 1080;
    public static final int MM_FULL_HD_WIDTH = 1920;
    public static final int MM_HD_HEIGHT = 720;
    public static final int MM_HD_WIDTH = 1280;
    public static final int MM_QHD_HEIGHT = 1440;
    public static final int MM_QHD_WIDTH = 2560;
    public static final int MM_QVGA_HEIGHT = 240;
    public static final int MM_QVGA_WIDTH = 320;
    public static final int MM_UHD_HEIGHT = 2160;
    public static final int MM_UHD_WIDTH = 3840;
    public static final int MM_VGA_HEIGHT = 480;
    public static final int MM_VGA_WIDTH = 640;
  }

  public static final class ContentType {
    public static final String VIDEO_3G2 = "video/3gpp2";
    public static final String VIDEO_3GP = "video/3gp";
    public static final String VIDEO_3GPP = "video/3gpp";
    public static final String VIDEO_ASF = "video/x-ms-asf";
    public static final String VIDEO_AVI = "video/avi";
    public static final String VIDEO_DIVX = "video/divx";
    public static final String VIDEO_FLV = "video/flv";
    public static final String VIDEO_MKV = "video/x-matroska";
    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_MP4V_ES = "video/mp4v-es";
    public static final String VIDEO_MPEG = "video/mpeg";
    public static final String VIDEO_MPEG2TS = "video/mp2ts";
    public static final String VIDEO_WEBM = "video/webm";
    public static final String VIDEO_WMV = "video/x-ms-wmv";
    public static final ArrayList<String> sSupportedVideoTypes;

    static {
      ArrayList<String> arrayList = new ArrayList<>();
      sSupportedVideoTypes = arrayList;
      arrayList.add("video/3gpp");
      arrayList.add("video/3gpp2");
      arrayList.add("video/mp4");
      arrayList.add("video/mp4v-es");
      arrayList.add("video/3gp");
      arrayList.add("video/avi");
      arrayList.add("video/x-ms-wmv");
      arrayList.add("video/x-ms-asf");
      arrayList.add("video/divx");
      arrayList.add("video/mpeg");
      arrayList.add(VIDEO_MKV);
      arrayList.add(VIDEO_FLV);
      arrayList.add(VIDEO_MPEG2TS);
      arrayList.add(VIDEO_WEBM);
    }
  }
}
