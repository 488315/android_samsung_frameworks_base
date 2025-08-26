package com.samsung.android.media;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SemExtendedFormat {
    private static final boolean DEBUG = false;
    private static final String SEF_VERSION = "1.19";
    private static final String TAG = "SemExtendedFormat";

    public static class DataPosition {
        public long length;
        public long offset;
    }

    public static final class DataType {
        public static final int ANIMATED_GIF_INFO = 2400;
        public static final int AUTO_ENHANCE_FOOD_INFO = 2480;
        public static final int AUTO_ENHANCE_INFO = 2240;
        public static final int BACKUP_RESTORE_DATA = 2625;
        public static final int BEAUTY_FACE_INFO = 2368;
        public static final int BURST_SHOT_BEST_PHOTO_INFO = 2529;
        public static final int BURST_SHOT_INFO = 2528;
        public static final int CAMERA_CAPTURE_MODE_INFO = 3169;
        public static final int CAMERA_STICKER_INFO = 2864;
        public static final int CLIP_MOVIE_INFO = 2496;
        public static final int COLOR_DISPLAY_P3 = 3265;
        public static final int COPY_AVAILABLE_EDIT_INFO = 2978;
        public static final int DIRECTOR_VIEW_INFO = 3088;
        public static final int DIRECTOR_VIEW_INFO_BACK_0 = 3091;
        public static final int DIRECTOR_VIEW_INFO_FRONT_0 = 3090;
        public static final int DIRECTOR_VIEW_INFO_GROUP_ID = 3089;
        public static final int DISPLAY_HDR_INFO = 3281;
        public static final int DOCUMENT_SCAN_INFO = 2960;
        public static final int DUAL_CAMERA_INFO = 2352;
        public static final int DUAL_RECORDING_INFO = 3312;
        public static final int DUAL_RELIGHT_BOKEH_INFO = 3024;
        public static final int DUAL_SHOT_BOKEH_INFO = 2784;
        public static final int DUAL_SHOT_CORE_INFO = 2740;
        public static final int DUAL_SHOT_DEPTHMAP = 2737;
        public static final int DUAL_SHOT_EXTRA_INFO = 2739;
        public static final int DUAL_SHOT_INFO = 2736;
        public static final int DUAL_SHOT_ONLY = 2768;
        public static final int DUAL_SHOT_ZOOMINOUT = 2738;
        public static final int DUAL_SHOT_ZOOMINOUT_INFO = 2752;
        public static final int DUBBING_SHOT_FACIAL_FEATURE_DATA = 2082;
        public static final int DUBBING_SHOT_FACIAL_FEATURE_NEUTRAL = 2081;
        public static final int DUBBING_SHOT_INFO = 2080;
        public static final int DYNAMIC_SHOT_INFO = 3073;
        public static final int DYNAMIC_VIEWING_DATA = 2992;
        public static final int EASY_360_INFO = 2512;
        public static final int EXTRA_A = 1030;
        public static final int EXTRA_DLL = 1029;
        public static final int EXTRA_EXE = 1031;
        public static final int EXTRA_HTML = 1026;
        public static final int EXTRA_LIB = 1028;
        public static final int EXTRA_SO = 1027;
        public static final int EXTRA_SWF = 1025;
        public static final int EXTRA_XML = 1024;
        public static final int FACE_DATA = 2177;
        public static final int FACE_DATA_INFO = 2577;
        public static final int FACE_TAG_DATA = 2178;
        public static final int FAST_MOTION_DATA = 2208;
        public static final int FLIP_PHOTO_INFO = 2592;
        public static final int FOCUS_SHOT_INFO = 2112;
        public static final int FOCUS_SHOT_MAP = 2113;
        public static final int FOOD_SHOT_INFO = 2336;
        public static final int FRONT_CAMERA_SELFIE_AUTO_ENHANCE_INFO = 2321;
        public static final int FRONT_CAMERA_SELFIE_INFO = 2320;
        public static final int GALLERY_DC_DATA = 3297;
        public static final int GOLF_SHOT_INFO = 2064;
        public static final int HIGHLIGHT_VIDEO_DATA = 2224;
        public static final int IMAGE_BMP = 3;
        public static final int IMAGE_GIF = 4;
        public static final int IMAGE_JPEG = 1;
        public static final int IMAGE_NV21 = 9;
        public static final int IMAGE_NV22 = 10;
        public static final int IMAGE_PNG = 2;
        public static final int IMAGE_RAW_BGRA = 12;
        public static final int IMAGE_RAW_RGB = 13;
        public static final int IMAGE_RAW_RGB565 = 14;
        public static final int IMAGE_RAW_RGBA = 11;
        public static final int IMAGE_TIFF = 5;
        public static final int IMAGE_UTC_DATA = 2561;
        public static final int IMAGE_YUV420 = 8;
        public static final int IMAGE_YUV422 = 7;
        public static final int IMAGE_YUV444 = 6;
        public static final int INTELLIGENT_PHOTOEDITOR_DATA = 2897;
        public static final int INTERACTIVE_PANORAMA_DEBUG_DATA = 2257;
        public static final int INTERACTIVE_PANORAMA_INFO = 2256;
        public static final int INTERVAL_SHOT_INFO = 2432;
        public static final int INVALID_DATA = 32766;
        public static final int INVALID_TYPE = -1;
        public static final int JPEG_180_2D = 2832;
        public static final int JPEG_180_HDR = 2672;
        public static final int JPEG_360_2D_INFO = 2640;
        public static final int JPEG_360_2D_NOTSTITCHED = 2656;
        public static final int JPEG_360_HDR_NOTSTITCHED = 2704;
        public static final int JPEG_360_HDR_STITCHED = 2688;
        public static final int LIVEFOCUS_JDM_INFO = 3040;
        public static final int MAGIC_SHOT_BEST_FACE_INFO = 2098;
        public static final int MAGIC_SHOT_BEST_PHOTO_INFO = 2097;
        public static final int MAGIC_SHOT_DRAMA_SHOT_INFO = 2100;
        public static final int MAGIC_SHOT_ERASER_INFO = 2099;
        public static final int MAGIC_SHOT_INFO = 2096;
        public static final int MAGIC_SHOT_PICTURE_MOTION_INFO = 2101;
        public static final int MESSAGE_STICKER_INFO = 2800;
        public static final int MESSAGE_STICKER_INFO_2 = 5217;
        public static final int MOBILE_COUNTRY_CODE_DATA = 2721;
        public static final int MOTION_PHOTO_DATA = 2608;
        public static final int MOVIE_AVI = 512;
        public static final int MOVIE_MOV = 515;
        public static final int MOVIE_MP4 = 513;
        public static final int MOVIE_QUICK_TIME = 514;
        public static final int MULTI_SHOT_REFOCUS_DATA = 2144;
        public static final int NON_DESTRUCTIVE_EDIT_TYPE = 2977;
        public static final int PANORAMA_MOTION_DEBUG_DATA = 2274;
        public static final int PANORAMA_MOTION_INFO = 2273;
        public static final int PANORAMA_SHOT_INFO = 2272;
        public static final int PHOTO_HDR_INFO = 3282;
        public static final int PICTURE_POST_PROCESSING_INFO = 2928;
        public static final int PORTRAIT_EFFECT_INFO = 3105;
        public static final int PRO_MODE_INFO = 2544;
        public static final int PRO_WHITE_BALANCE_INFO = 3185;
        public static final int REAR_CAMERA_SELFIE_AUTO_ENHANCE_INFO = 2305;
        public static final int REAR_CAMERA_SELFIE_INFO = 2304;
        public static final int RECORDING_START_TIME = 3137;
        public static final int REMASTER_INFO = 3056;
        public static final int SAMSUNG_CAPTURE_INFO = 3153;
        public static final int SAMSUNG_SPECIFIC_DATA_TYPE_END = 16384;
        public static final int SAMSUNG_SPECIFIC_DATA_TYPE_START = 2048;
        public static final int SAMSUNG_THEMES_INFO = 2848;
        public static final int SCENEOPTIMIZER_SCENE_INFO = 3249;
        public static final int SEQUENCE_SHOT_DATA = 2160;
        public static final int SINGLE_RELIGHT_BOKEH_INFO = 3008;
        public static final int SINGLE_RELIGHT_BOKEH_REAR_INFO = 3216;
        public static final int SINGLE_SHOT_BOKEH_INFO = 2880;
        public static final int SINGLE_SHOT_BOKEH_REAR_INFO = 3232;
        public static final int SINGLE_SHOT_DEPTHMAP = 2881;
        public static final int SINGLE_TAKE_CAMERA_DRAFT_INFO = 2947;
        public static final int SINGLE_TAKE_CAMERA_INFO = 2945;
        public static final int SINGLE_TAKE_CAMERA_REPRESENTIVE_INFO = 2946;
        public static final int SINGLE_TAKE_IMAGE_BLACKWHITE = 2955;
        public static final int SINGLE_TAKE_IMAGE_COLLAGE = 2956;
        public static final int SINGLE_TAKE_IMAGE_COLORPICKER = 2956;
        public static final int SINGLE_TAKE_IMAGE_FILTER = 2954;
        public static final int SINGLE_TAKE_IMAGE_PANORAMA = 2952;
        public static final int SINGLE_TAKE_IMAGE_SMARTCROP = 2953;
        public static final int SINGLE_TAKE_ORIGINAL_BM1 = 3121;
        public static final int SINGLE_TAKE_ORIGINAL_BM2 = 3122;
        public static final int SINGLE_TAKE_ORIGINAL_BM3 = 3123;
        public static final int SINGLE_TAKE_ORIGINAL_BM4 = 3124;
        public static final int SINGLE_TAKE_ORIGINAL_BM5 = 3125;
        public static final int SINGLE_TAKE_VIDEO_BOOMERANG = 2950;
        public static final int SINGLE_TAKE_VIDEO_DV = 2958;
        public static final int SINGLE_TAKE_VIDEO_FF = 2949;
        public static final int SINGLE_TAKE_VIDEO_FIRSTFRAME_TIMESTAMP_INFO = 3135;
        public static final int SINGLE_TAKE_VIDEO_HIDT = 2959;
        public static final int SINGLE_TAKE_VIDEO_HV = 2957;
        public static final int SINGLE_TAKE_VIDEO_ORIGINAL = 2948;
        public static final int SINGLE_TAKE_VIDEO_REVERSE = 2951;
        public static final int SLOW_MOTION_DATA = 2192;
        public static final int SOUND_AAC = 260;
        public static final int SOUND_FLAC = 259;
        public static final int SOUND_MP3 = 257;
        public static final int SOUND_OGG = 258;
        public static final int SOUND_PCM_WAV = 256;
        public static final int SOUND_SHOT_INFO = 2048;
        public static final int SPORTS_SHOT_INFO = 2288;
        public static final int SUPER_SLOW_MOTION_BGM = 2817;
        public static final int SUPER_SLOW_MOTION_DATA = 2816;
        public static final int SUPER_SLOW_MOTION_DEFLICKERING_INFO = 2818;
        public static final int SUPER_SLOW_MOTION_DEFLICKERING_ON = 2820;
        public static final int SUPER_SLOW_MOTION_EDIT_DATA = 2819;
        public static final int SURROUND_SHOT_INFO = 2384;
        public static final int TAG_SHOT_INFO = 2448;
        public static final int ULTRA_WIDE_PHOTOEDITOR_DATA = 2912;
        public static final int USER_DATA = 0;
        public static final int UserData = 0;
        public static final int VIDEO_VIEW_MODE = 2465;
        public static final int VIRTUAL_TOUR_INFO = 2128;
        public static final int WATERMARK_INFO = 3201;
        public static final int WIDE_SELFIE_INFO = 2416;
        public static final int WIDE_SELFIE_MOTION_INFO = 2417;
    }

    public static final class KeyName {
        public static final String ANIMATED_GIF_INFO = "Animated_Gif_Info";
        public static final String AUTO_ENHANCE_FOOD_INFO = "Auto_Enhance_Food_Info";
        public static final String AUTO_ENHANCE_IMAGE_PROCESSED = "Auto_Enhance_Processed";
        public static final String AUTO_ENHANCE_IMAGE_UNPROCESSED = "Auto_Enhance_Unprocessed";
        public static final String AUTO_ENHANCE_INFO = "Auto_Enhance_Info";
        public static final String BACKUP_RESTORE_DATA = "BackupRestore_Data";
        public static final String BEAUTY_FACE_INFO = "Beauty_Face_Info";
        public static final String BURST_SHOT_BEST_PHOTO_INFO = "BurstShot_Best_Photo_Info";
        public static final String BURST_SHOT_INFO = "Burst_Shot_Info";
        public static final String CAMERA_CAPTURE_MODE_INFO = "Camera_Capture_Mode_Info";
        public static final String CAMERA_STICKER_BGM_TEMPLATE = "Camera_Sticker_BGM_%03d";
        public static final String CAMERA_STICKER_INFO = "Camera_Sticker_Info";
        public static final String CLIP_MOVIE_INFO = "Clip_Movie_Info";
        public static final String COLOR_DISPLAY_P3 = "Color_Display_P3";
        public static final String COPY_AVAILABLE_EDIT_INFO = "Copy_Available_Edit_Info";
        public static final String DIRECTOR_VIEW_INFO = "Directors_View_Info";
        public static final String DIRECTOR_VIEW_INFO_BACK_0 = "Directors_View_Info_Back_0";
        public static final String DIRECTOR_VIEW_INFO_FRONT_0 = "Directors_View_Info_Front_0";
        public static final String DIRECTOR_VIEW_INFO_GROUP_ID = "Directors_View_Group_Id";
        public static final String DISPLAY_HDR_INFO = "Display_HDR_Info";
        public static final String DOCUMENT_SCAN_INFO = "Document_Scan_Info";
        public static final String DUAL_CAMERA_INFO = "Dual_Camera_Info";
        public static final String DUAL_RECORDING_INFO = "Dual_Recording_Info";
        public static final String DUAL_RELIGHT_BOKEH_INFO = "Dual_Relighting_Bokeh_Info";
        public static final String DUAL_SHOT_BOKEH_INFO = "DualShot_Bokeh_Info";
        public static final String DUAL_SHOT_CORE_INFO = "DualShot_Core_Info";
        public static final String DUAL_SHOT_DEPTHMAP = "DualShot_DepthMap_%d";
        public static final String DUAL_SHOT_EXTRA_INFO = "DualShot_Extra_Info";
        public static final String DUAL_SHOT_INFO = "DualShot_Meta_Info";
        public static final String DUAL_SHOT_JPEG_TEMPLATE = "DualShot_%d";
        public static final String DUAL_SHOT_ONLY = "DualShot_Only_Info";
        public static final String DUAL_SHOT_ZOOMINOUT = "ZoomInOut_Info";
        public static final String DYNAMIC_SHOT_INFO = "Dynamic_Shot_Info";
        public static final String DYNAMIC_VIEWING_DATA = "Dynamic_Viewing_Data";
        public static final String EASY_360_INFO = "Easy_360_Info";
        public static final String EFFECT_PHOTOEDITOR_DATA = "PhotoEditor_Effect_Data";
        public static final String FACE_DATA = "Face_Data_%03d";
        public static final String FACE_DATA_INFO = "Face_Data_Info";
        public static final String FACE_TAG_DATA = "Face_Tag_Data_%03d";
        public static final String FAST_MOTION_DATA = "FastMotion_Data";
        public static final String FLIP_PHOTO_INFO = "Flip_Photo_Info";
        public static final String FLIP_PHOTO_JEPG_TEMPLATE = "FlipPhoto_%03d";
        public static final String FOCUS_SHOT_INFO = "FocusShot_Meta_Info";
        public static final String FOCUS_SHOT_JEPG_TEMPLATE = "FocusShot_%d";
        public static final String FOCUS_SHOT_MAP = "FocusShot_Map";
        public static final String FOOD_SHOT_INFO = "Food_Shot_Info";
        public static final String FRONT_CAMERA_SELFIE_INFO = "Front_Cam_Selfie_Info";
        public static final String GALLERY_DC_DATA = "Gallery_DC_Data";
        public static final String HIGHLIGHT_VIDEO_DATA = "HighlightVideo_Data";
        public static final String IMAGE_UTC_DATA = "Image_UTC_Data";
        public static final String INTELLIGENT_PHOTOEDITOR_DATA = "Intelligent_PhotoEditor_Data";
        public static final String INTERACTIVE_PANORAMA_DEBUG_DATA = "Interactive_Panorama_Debug_Data";
        public static final String INTERACTIVE_PANORAMA_INFO = "Interactive_Panorama_Info";
        public static final String INTERACTIVE_PANORAMA_MP4_TEMPLATE = "Interactive_Panorama_%03d";
        public static final String INTERVAL_SHOT_INFO = "Interval_Shot_Info";
        public static final String INVALID_DATA = "Invalid_Data";
        public static final String JPEG_180_2D = "Jpeg180_2D";
        public static final String JPEG_180_HDR = "Jpeg180_HDR";
        public static final String JPEG_360_2D_INFO = "Jpeg360_2D_Info";
        public static final String JPEG_360_2D_NOTSTITCHED = "Jpeg360_2D_NotStitched";
        public static final String JPEG_360_HDR_NOTSTITCHED = "Jpeg360_HDR_NotStitched";
        public static final String JPEG_360_HDR_STITCHED = "Jpeg360_HDR_Stitched";
        public static final String LIVEFOCUS_JDM_INFO = "Livefocus_JDM_Info";
        public static final String MAGIC_SHOT_BEST_FACE_INFO = "MagicShot_Best_Face_Info";
        public static final String MAGIC_SHOT_BEST_FACE_JEPG = "MagicShot_Best_Face_JPG";
        public static final String MAGIC_SHOT_BEST_PHOTO_INFO = "MagicShot_Best_Photo_Info";
        public static final String MAGIC_SHOT_BEST_PHOTO_JEPG = "MagicShot_Best_Photo_JPG";
        public static final String MAGIC_SHOT_DRAMA_SHOT_INFO = "MagicShot_Drama_Shot_Info";
        public static final String MAGIC_SHOT_DRAMA_SHOT_JEPG = "MagicShot_Drama_Shot_JPG";
        public static final String MAGIC_SHOT_ERASER_INFO = "MagicShot_Eraser_Info";
        public static final String MAGIC_SHOT_ERASER_JEPG = "MagicShot_Eraser_JPG";
        public static final String MAGIC_SHOT_INFO = "MagicShot_Info";
        public static final String MAGIC_SHOT_JEPG_TEMPLATE = "MagicShot_%03d";
        public static final String MAGIC_SHOT_PICTURE_MOTION_INFO = "MagicShot_Pic_Motion_Info";
        public static final String MAGIC_SHOT_PICTURE_MOTION_JEPG = "MagicShot_Pic_Motion_JPG";
        public static final String MESSAGE_STICKER_BGM_TEMPLATE = "Message_Sticker_BGM_%03d";
        public static final String MESSAGE_STICKER_INFO = "Message_Sticker_Info";
        public static final String MOBILE_COUNTRY_CODE_DATA = "MCC_Data";
        public static final String MOTION_PHOTO_DATA = "MotionPhoto_Data";
        public static final String ORIGINAL_PATH_HASH_KEY = "Original_Path_Hash_Key";
        public static final String ORIGINAL_PHOTOEDITOR_PATH = "PhotoEditor_Original_Path";
        public static final String PANORAMA_MOTION_DEBUG_DATA = "Motion_Panorama_Debug_Data";
        public static final String PANORAMA_MOTION_INFO = "Motion_Panorama_Info";
        public static final String PANORAMA_MOTION_MP4_TEMPLATE = "Motion_Panorama_MP4_%03d";
        public static final String PANORAMA_MOTION_SOUND_TEMPLATE = "Motion_Panorama_Sound_%03d";
        public static final String PANORAMA_SHOT_INFO = "Panorama_Shot_Info";
        public static final String PHOTO_HDR_INFO = "Photo_HDR_Info";
        public static final String PICTURE_POST_PROCESSING_INFO = "PostProcess_Status";
        public static final String PORTRAIT_EFFECT_INFO = "Portrait_Effect_Info";
        public static final String PRO_MODE_INFO = "Pro_Mode_Info";
        public static final String PRO_WHITE_BALANCE_INFO = "Pro_White_Balance_Info";
        public static final String REAR_CAMERA_SELFIE_INFO = "Rear_Cam_Selfie_Info";
        public static final String RECORDING_START_TIME = "Recording_Start_Time";
        public static final String REMASTER_INFO = "Remaster_Info";
        public static final String RE_EDIT_PHOTOEDITOR_DATA = "PhotoEditor_Re_Edit_Data";
        public static final String SAMSUNG_CAPTURE_INFO = "Samsung_Capture_Info";
        public static final String SAMSUNG_THEMES_INFO = "Samsung_Themes_Info";
        public static final String SCENEOPTIMIZER_SCENE_INFO = "SceneOptimizer_Scene_Info";
        public static final String SEQUENCE_SHOT_DATA = "SequenceShot_Data";
        public static final String SINGLE_RELIGHT_BOKEH_INFO = "Single_Relighting_Bokeh_Info";
        public static final String SINGLE_RELIGHT_BOKEH_REAR_INFO = "Single_Relighting_Bokeh_Rear_Info";
        public static final String SINGLE_SHOT_BOKEH_INFO = "SingleShot_Meta_Info";
        public static final String SINGLE_SHOT_BOKEH_REAR_INFO = "SingleShot_Meta_Rear_Info";
        public static final String SINGLE_SHOT_DEPTHMAP = "SingeShot_DepthMap_%d";
        public static final String SINGLE_SHOT_JPEG_TEMPLATE = "SingleShot";
        public static final String SINGLE_TAKE_CAMERA_DRAFT_INFO = "Single_Take_Camera_Draft_Info";
        public static final String SINGLE_TAKE_CAMERA_INFO = "Single_Take_Camera_Info";
        public static final String SINGLE_TAKE_CAMERA_REPRESENTIVE_INFO = "Single_Take_Camera_Representive_Info";
        public static final String SINGLE_TAKE_VIDEO_FIRSTFRAME_TIMESTAMP_INFO = "Single_Take_Video_Firstframe_Timestamp_Info";
        public static final String SINGLE_TAKE_VIDEO_TYPE_INFO = "Single_Take_Video_Type_Info";
        public static final String SLOW_MOTION_DATA = "SlowMotion_Data";
        public static final String SOUND_SHOT_INFO = "SoundShot_Meta_Info";
        public static final String SOUND_SHOT_WAVE = "SoundShot_000";
        public static final String SPORTS_SHOT_INFO = "Sports_Shot_Info";
        public static final String SUPER_SLOW_MOTION_BGM = "Super_SlowMotion_BGM";
        public static final String SUPER_SLOW_MOTION_DATA = "Super_SlowMotion_Data";
        public static final String SUPER_SLOW_MOTION_DEFLICKERING_INFO = "Super_SlowMotion_Deflickering_Info";
        public static final String SUPER_SLOW_MOTION_DEFLICKERING_ON = "Super_SlowMotion_Deflickering_On";
        public static final String SUPER_SLOW_MOTION_EDIT_DATA = "Super_SlowMotion_Edit_Data";
        public static final String SURROUND_SHOT_INFO = "Surround_Shot_Info";
        public static final String TAG_SHOT_INFO = "Tag_Shot_Info";
        public static final String ULTRA_WIDE_PHOTOEDITOR_DATA = "UltraWide_PhotoEditor_Data";
        public static final String VIDEO_VIEW_MODE = "Video_View_Mode";
        public static final String VIRTUAL_TOUR_INFO = "VirtualTour_Info";
        public static final String VIRTUAL_TOUR_JEPG_TEMPLATE = "VirtualTour_%03d";
        public static final String WATERMARK_INFO = "Watermark_Info";
        public static final String WIDE_SELFIE_INFO = "Wide_Selfie_Info";
        public static final String WIDE_SELFIE_MOTION_INFO = "Wide_Selfie_Motion_Info";
        public static final String WIDE_SELFIE_MOTION_MP4_TEMPLATE = "Wide_Selfie_Motion_MP4_%03d";
    }

    public static final class Options {

        @Deprecated(forRemoval = true, since = "15.5")
        public static final int OVERWRITE_IF_EXISTS = 1;

        @Deprecated(forRemoval = true, since = "15.5")
        public static final int OVERWRITE_IF_EXISTS_MP4 = 4096;

        @Deprecated(forRemoval = true, since = "15.5")
        public static final int SKIP_IF_EXISTS = 0;

        @Deprecated(forRemoval = true, since = "15.5")
        public static final int SKIP_IF_EXISTS_MP4 = 256;
        public static final int SUBSTITUTE_IF_EXIST = 16;
        public static final int TYPE_MP4 = 4;
        public static final int TYPE_OVERWRITE_IF_EXISTS = 1;
        public static final int TYPE_SKIP_IF_EXISTS = 0;
        public static final int TYPE_WITH_BOX_TAG = 2;
    }

    public static class SEFDataPosition {
        public long length;
        public long offset;
    }

    private static final class SEFViewerPackageName {
        private static final String INTERACTIVESHOT_PACKAGE_NAME = "com.samsung.android.app.interactivepanoramaviewer";
        private static final String MOTIONPANORAMA_PACKAGE_NAME = "com.samsung.android.app.motionpanoramaviewer";
        private static final String SELFMOTIONPANORAMA_PACKAGE_NAME = "com.samsung.android.app.selfmotionpanoramaviewer";

        private SEFViewerPackageName() {
        }
    }

    public static boolean isValidFile(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath != null && canonicalPath.length() > 0) {
            return SEFJNI.isSEFFile(canonicalPath) != 0;
        }
        Log.e(TAG, "Invalid file name: " + canonicalPath);
        return false;
    }

    public static boolean isValidFile(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        return SEFJNI.isSEFfileDescriptor(parcelFileDescriptor) != 0;
    }

    public static boolean hasData(File file, int i) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0 || i == -1) {
            Log.e(TAG, "Invalid file name: " + canonicalPath + ", Data Type: " + i);
            return false;
        }
        if (SEFJNI.isSEFFile(canonicalPath) == 0) {
            return false;
        }
        int[] iArrListSEFDataTypes = listSEFDataTypes(file);
        if (iArrListSEFDataTypes == null) {
            Log.e(TAG, "Invalid file : " + canonicalPath);
            return false;
        }
        for (int length = iArrListSEFDataTypes.length - 1; length > -1; length--) {
            if (i == iArrListSEFDataTypes[length]) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasData(File file, String str) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0 || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath + ", keyName: " + str);
            return false;
        }
        if (SEFJNI.isSEFFile(canonicalPath) == 0) {
            return false;
        }
        String[] strArrListKeyNames = listKeyNames(file);
        if (strArrListKeyNames == null) {
            Log.e(TAG, "Invalid file : " + canonicalPath);
            return false;
        }
        if (strArrListKeyNames.length <= 0) {
            Log.e(TAG, "Invalid file : " + canonicalPath);
            return false;
        }
        for (int length = strArrListKeyNames.length - 1; length > -1; length--) {
            if (str.equals(strArrListKeyNames[length])) {
                return true;
            }
        }
        return false;
    }

    public static String getVersion() {
        return "1.19_" + SEFJNI.getNativeVersion();
    }

    public static boolean isSEFFile(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath != null && canonicalPath.length() > 0) {
            return SEFJNI.isSEFFile(canonicalPath) != 0;
        }
        Log.e(TAG, "Invalid file name: " + canonicalPath);
        return false;
    }

    public static boolean isSEFFile(String str) {
        if (str != null && str.length() > 0) {
            return SEFJNI.isSEFFile(str) != 0;
        }
        Log.e(TAG, "Invalid file name: " + str);
        return false;
    }

    public static boolean hasSEFData(File file, int i) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0 || i == -1) {
            Log.e(TAG, "Invalid file name: " + canonicalPath + ", Data Type: " + i);
            return false;
        }
        if (SEFJNI.isSEFFile(canonicalPath) == 0) {
            return false;
        }
        int[] iArrListSEFDataTypes = listSEFDataTypes(file);
        if (iArrListSEFDataTypes == null) {
            Log.e(TAG, "Invalid file : " + canonicalPath);
            return false;
        }
        for (int length = iArrListSEFDataTypes.length - 1; length > -1; length--) {
            if (i == iArrListSEFDataTypes[length]) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSEFData(File file, String str) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0 || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath + ", keyName: " + str);
            return false;
        }
        if (SEFJNI.isSEFFile(canonicalPath) == 0) {
            return false;
        }
        String[] strArrListKeyNames = listKeyNames(file);
        if (strArrListKeyNames == null) {
            Log.e(TAG, "Invalid file : " + canonicalPath);
            return false;
        }
        if (strArrListKeyNames.length <= 0) {
            Log.e(TAG, "Invalid file : " + canonicalPath);
            return false;
        }
        for (int length = strArrListKeyNames.length - 1; length > -1; length--) {
            if (str.equals(strArrListKeyNames[length])) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDataType(String str, int i) {
        if (str == null || str.length() <= 0 || i == -1) {
            Log.e(TAG, "Invalid file name: " + str + ", Data Type: " + i);
            return false;
        }
        if (SEFJNI.isSEFFile(str) == 0) {
            return false;
        }
        int[] iArrListSEFDataTypes = listSEFDataTypes(str);
        if (iArrListSEFDataTypes == null) {
            Log.e(TAG, "Invalid file : " + str);
            return false;
        }
        for (int length = iArrListSEFDataTypes.length - 1; length > -1; length--) {
            if (i == iArrListSEFDataTypes[length]) {
                return true;
            }
        }
        return false;
    }

    public static int addData(File file, String str, byte[] bArr, int i, int i2) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return 0;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return 0;
        }
        if (bArr == null || bArr.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0;
        }
        if (i2 == 256 || i2 == 4) {
            return SEFJNI.addSEFDataToMP4(canonicalPath, str, str.length(), null, 0, bArr, bArr.length, i, 0);
        }
        if (i2 == 4096 || i2 == 5) {
            return SEFJNI.addSEFDataToMP4(canonicalPath, str, str.length(), null, 0, bArr, bArr.length, i, 1);
        }
        if (i2 == 2) {
            return SEFJNI.addSEFDataAddTag(canonicalPath, str, str.length(), null, 0, bArr, bArr.length, i, 0);
        }
        if (i2 == 3) {
            return SEFJNI.addSEFDataAddTag(canonicalPath, str, str.length(), null, 0, bArr, bArr.length, i, 1);
        }
        if (i2 != 0 && i2 != 1) {
            Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
            return 0;
        }
        return SEFJNI.addSEFData(canonicalPath, str, str.length(), null, 0, bArr, bArr.length, i, i2);
    }

    public static int addData(File file, String str, File file2, int i, int i2) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        String canonicalPath2 = file2.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return 0;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return 0;
        }
        if (canonicalPath2 == null || canonicalPath2.length() <= 0) {
            Log.e(TAG, "Invalid SEF Data File name: " + canonicalPath2);
            return 0;
        }
        if (i2 == 256 || i2 == 4) {
            return SEFJNI.addSEFDataFileToMP4(canonicalPath, str, str.length(), null, 0, canonicalPath2, i, 0);
        }
        if (i2 == 4096 || i2 == 5) {
            return SEFJNI.addSEFDataFileToMP4(canonicalPath, str, str.length(), null, 0, canonicalPath2, i, 1);
        }
        if (i2 == 2) {
            return SEFJNI.addSEFDataFileAddTag(canonicalPath, str, str.length(), null, 0, canonicalPath2, i, 0);
        }
        if (i2 == 3) {
            return SEFJNI.addSEFDataFileAddTag(canonicalPath, str, str.length(), null, 0, canonicalPath2, i, 1);
        }
        if (i2 != 0 && i2 != 1) {
            Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
            return 0;
        }
        return SEFJNI.addSEFDataFile(canonicalPath, str, str.length(), null, 0, canonicalPath2, i, i2);
    }

    public static long addSEFDataByteBufferAddTag(ByteBuffer byteBuffer, String str, int i, byte[] bArr, int i2, byte[] bArr2, int i3, int i4, int i5) {
        byte[] bArrArray = byteBuffer.array();
        long jPosition = byteBuffer.position();
        long jCapacity = byteBuffer.capacity();
        long jArrayOffset = byteBuffer.arrayOffset();
        if (jCapacity <= 0) {
            return 0L;
        }
        long jAddSEFDataBufferAddTag = SEFJNI.addSEFDataBufferAddTag(bArrArray, jCapacity, jPosition, jArrayOffset, str, i, bArr, i2, bArr2, i3, i4, i5);
        byteBuffer.position((int) jAddSEFDataBufferAddTag);
        return jAddSEFDataBufferAddTag;
    }

    public static long addData(ByteBuffer byteBuffer, String str, byte[] bArr, int i, int i2) {
        if (byteBuffer == null) {
            Log.e(TAG, "buffer is null");
            return 0L;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return 0L;
        }
        if (bArr == null || bArr.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0L;
        }
        if (i2 != 0 && i2 != 1) {
            Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
            Log.e(TAG, "You can use only one of two - TYPE_SKIP_IF_EXISTS, TYPE_OVERWRITE_IF_EXISTS");
            return 0L;
        }
        return addSEFDataByteBufferAddTag(byteBuffer, str, str.length(), null, 0, bArr, bArr.length, i, i2);
    }

    public static long getRequiredBufferSize(long j, int i, long j2, long j3) {
        if (j <= 0) {
            Log.e(TAG, "invalid orgDataSize : " + j);
            return 0L;
        }
        if (i <= 0) {
            Log.e(TAG, "invalid dataCount : " + i);
            return 0L;
        }
        if (j2 <= 0) {
            Log.e(TAG, "invalid totalDataSize : " + j2);
            return 0L;
        }
        if (j3 <= 0) {
            Log.e(TAG, "invalid totalkeyNameSize : " + j3);
            return 0L;
        }
        return SEFJNI.getSEFBufferAllocSize(j, i, j2, 0L, j3);
    }

    public static long addData(ParcelFileDescriptor parcelFileDescriptor, String str, byte[] bArr, int i, int i2) {
        if (parcelFileDescriptor == null) {
            Log.e(TAG, "pfd is null");
            return 0L;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return 0L;
        }
        if (bArr == null || bArr.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0L;
        }
        if (i2 == 256 || i2 == 4) {
            return addSEFDataFileDescriptorToMP4(parcelFileDescriptor, str, str.length(), null, 0, bArr, bArr.length, i, 0);
        }
        if (i2 == 4096 || i2 == 5) {
            return addSEFDataFileDescriptorToMP4(parcelFileDescriptor, str, str.length(), null, 0, bArr, bArr.length, i, 1);
        }
        if (i2 == 2) {
            return addSEFDataFileDescriptorAddTag(parcelFileDescriptor, str, str.length(), null, 0, bArr, bArr.length, i, 0);
        }
        if (i2 == 3) {
            return addSEFDataFileDescriptorAddTag(parcelFileDescriptor, str, str.length(), null, 0, bArr, bArr.length, i, 1);
        }
        if (i2 != 0 && i2 != 1) {
            Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
            return 0L;
        }
        return addSEFDataFilSEFeDescriptor(parcelFileDescriptor, str, str.length(), null, 0, bArr, bArr.length, i, i2);
    }

    public static long addSEFDataFilSEFeDescriptor(ParcelFileDescriptor parcelFileDescriptor, String str, int i, byte[] bArr, int i2, byte[] bArr2, int i3, int i4, int i5) {
        return SEFJNI.addSEFDataFd(parcelFileDescriptor.getFd(), str, i, bArr, i2, bArr2, i3, i4, i5);
    }

    public static long addSEFDataFileDescriptorAddTag(ParcelFileDescriptor parcelFileDescriptor, String str, int i, byte[] bArr, int i2, byte[] bArr2, int i3, int i4, int i5) {
        return SEFJNI.addSEFDataFdAddTag(parcelFileDescriptor.getFd(), str, i, bArr, i2, bArr2, i3, i4, i5);
    }

    public static long addSEFDataFileDescriptorToMP4(ParcelFileDescriptor parcelFileDescriptor, String str, int i, byte[] bArr, int i2, byte[] bArr2, int i3, int i4, int i5) {
        return SEFJNI.addSEFDataFdToMP4(parcelFileDescriptor.getFd(), str, i, bArr, i2, bArr2, i3, i4, i5);
    }

    public static int addSEFData(File file, String str, byte[] bArr, int i, int i2) throws IOException {
        return addSEFData(file, str, bArr, (byte[]) null, i, i2);
    }

    public static int addSEFData(File file, String str, byte[] bArr, byte[] bArr2, int i, int i2) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return 0;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return 0;
        }
        if (bArr == null || bArr.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0;
        }
        if (i2 == 16) {
            return SEFJNI.addFastSEFData(canonicalPath, str, str.length(), bArr2, bArr2 != null ? bArr2.length : 0, bArr, bArr.length, i, i2);
        }
        if (i2 == 256) {
            return SEFJNI.addSEFDataToMP4(canonicalPath, str, str.length(), bArr2, bArr2 == null ? 0 : bArr2.length, bArr, bArr.length, i, 0);
        }
        if (i2 == 4096) {
            return SEFJNI.addSEFDataToMP4(canonicalPath, str, str.length(), bArr2, bArr2 == null ? 0 : bArr2.length, bArr, bArr.length, i, 1);
        }
        return SEFJNI.addSEFData(canonicalPath, str, str.length(), bArr2, bArr2 == null ? 0 : bArr2.length, bArr, bArr.length, i, i2);
    }

    public static int addSEFData(File file, String str, File file2, int i, int i2) throws IOException {
        return addSEFData(file, str, file2, (byte[]) null, i, i2);
    }

    public static int addSEFData(File file, String str, File file2, byte[] bArr, int i, int i2) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        String canonicalPath2 = file2.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return 0;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return 0;
        }
        if (canonicalPath2 == null || canonicalPath2.length() <= 0) {
            Log.e(TAG, "Invalid SEF Data File name: " + canonicalPath2);
            return 0;
        }
        if (i2 == 16) {
            return SEFJNI.addFastSEFDataFile(canonicalPath, str, str.length(), bArr, bArr != null ? bArr.length : 0, canonicalPath2, i, i2);
        }
        if (i2 == 256) {
            return SEFJNI.addSEFDataFileToMP4(canonicalPath, str, str.length(), bArr, bArr != null ? bArr.length : 0, canonicalPath2, i, 0);
        }
        if (i2 == 4096) {
            return SEFJNI.addSEFDataFileToMP4(canonicalPath, str, str.length(), bArr, bArr != null ? bArr.length : 0, canonicalPath2, i, 1);
        }
        return SEFJNI.addSEFDataFile(canonicalPath, str, str.length(), bArr, bArr != null ? bArr.length : 0, canonicalPath2, i, i2);
    }

    public static int addSEFData(String str, String str2, byte[] bArr, int i, int i2) {
        return addSEFData(str, str2, bArr, (byte[]) null, i, i2);
    }

    public static int addSEFData(String str, String str2, byte[] bArr, byte[] bArr2, int i, int i2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return 0;
        }
        if (bArr == null || bArr.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0;
        }
        if (i2 == 16) {
            return SEFJNI.addFastSEFData(str, str2, str2.length(), bArr2, bArr2 != null ? bArr2.length : 0, bArr, bArr.length, i, i2);
        }
        return SEFJNI.addSEFData(str, str2, str2.length(), bArr2, bArr2 != null ? bArr2.length : 0, bArr, bArr.length, i, i2);
    }

    public static int addSEFDataFile(String str, String str2, String str3, int i, int i2) {
        return addSEFDataFile(str, str2, str3, null, i, i2);
    }

    public static int addSEFDataFile(String str, String str2, String str3, byte[] bArr, int i, int i2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return 0;
        }
        if (str3 != null && str3.length() > 0) {
            return SEFJNI.addSEFDataFile(str, str2, str2.length(), bArr, bArr != null ? bArr.length : 0, str3, i, i2);
        }
        Log.e(TAG, "Invalid SEF Data File name: " + str3);
        return 0;
    }

    public static int addFastSEFData(String str, String str2, byte[] bArr, int i, int i2) {
        return addFastSEFData(str, str2, bArr, null, i, i2);
    }

    public static int addFastSEFData(String str, String str2, byte[] bArr, byte[] bArr2, int i, int i2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return 0;
        }
        if (bArr != null && bArr.length > 0) {
            return SEFJNI.addFastSEFData(str, str2, str2.length(), bArr2, bArr2 != null ? bArr2.length : 0, bArr, bArr.length, i, i2);
        }
        Log.e(TAG, "Invalid data");
        return 0;
    }

    public static int addFastSEFDataFile(String str, String str2, String str3, int i, int i2) {
        return addFastSEFDataFile(str, str2, str3, null, i, i2);
    }

    public static int addFastSEFDataFile(String str, String str2, String str3, byte[] bArr, int i, int i2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return 0;
        }
        if (str3 != null && str3.length() > 0) {
            return SEFJNI.addFastSEFDataFile(str, str2, str2.length(), bArr, bArr != null ? bArr.length : 0, str3, i, i2);
        }
        Log.e(TAG, "Invalid SEF Data File name: " + str3);
        return 0;
    }

    public static int addSEFDataFiles(String str, String[] strArr, String[] strArr2, int[] iArr, int i) {
        int length = strArr.length;
        if (length != strArr2.length) {
            Log.e(TAG, "Data Count is different. ( keyNames data count= " + length + ", dataFileNames data count= " + strArr2.length + " )");
        } else if (length != iArr.length) {
            Log.e(TAG, "Data Count is different. ( keyNames data count= " + length + ", dataTypes data count= " + iArr.length + " )");
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        int[] iArr2 = new int[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            iArr2[i2] = strArr[i2].length();
        }
        return SEFJNI.addSEFDataFiles(str, strArr, iArr2, strArr2, iArr, i, length);
    }

    public static boolean deleteData(File file, String str) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return false;
        }
        if (str != null && str.length() > 0) {
            return SEFJNI.deleteSEFData(canonicalPath, str, str.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + str);
        return false;
    }

    public static boolean deleteData(ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException {
        if (str != null && str.length() > 0) {
            return SEFJNI.deleteSEFDataFileDescriptor(parcelFileDescriptor, str, str.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + str);
        return false;
    }

    public static boolean deleteAllData(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath != null && canonicalPath.length() > 0) {
            return SEFJNI.clearSEFData(canonicalPath) == 1;
        }
        Log.e(TAG, "Invalid file name: " + canonicalPath);
        return false;
    }

    public static boolean deleteAllData(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        return SEFJNI.clearSEFDataFileDescriptor(parcelFileDescriptor) == 1;
    }

    public static boolean deleteSEFData(File file, String str) throws IOException {
        return deleteSEFData(file, str, 1);
    }

    public static boolean deleteSEFData(File file, String str, int i) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return false;
        }
        if (str != null && str.length() > 0) {
            return i == 16 ? SEFJNI.fastDeleteSEFData(canonicalPath, str, str.length()) == 1 : SEFJNI.deleteSEFData(canonicalPath, str, str.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + str);
        return false;
    }

    public static int deleteSEFData(String str, String str2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return 0;
        }
        return SEFJNI.deleteSEFData(str, str2, str2.length());
    }

    public static boolean deleteFastSEFData(String str, String str2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return false;
        }
        if (str2 != null && str2.length() > 0) {
            return SEFJNI.fastDeleteSEFData(str, str2, str2.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + str2);
        return false;
    }

    public static boolean deleteAllSEFData(File file) throws IOException {
        return deleteAllSEFData(file, 1);
    }

    public static boolean deleteAllSEFData(File file, int i) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath != null && canonicalPath.length() > 0) {
            return i == 16 ? SEFJNI.fastClearSEFData(canonicalPath) == 1 : SEFJNI.clearSEFData(canonicalPath) == 1;
        }
        Log.e(TAG, "Invalid file name: " + canonicalPath);
        return false;
    }

    public static int clearSEFData(String str) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        return SEFJNI.clearSEFData(str);
    }

    public static int clearFastSEFData(String str) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return 0;
        }
        return SEFJNI.fastClearSEFData(str);
    }

    public static boolean compact(File file) throws IOException {
        return deleteSEFData(file, KeyName.INVALID_DATA);
    }

    public static String[] getKeyNameArray(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        return SEFJNI.listKeyNames(canonicalPath);
    }

    public static String[] getKeyNameArray(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        return SEFJNI.listKeyNamesFileDescriptor(parcelFileDescriptor);
    }

    @Deprecated(forRemoval = true, since = "15.5")
    public static String[] getKeyNameArray(File file, int i) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0 || i == -1) {
            Log.e(TAG, "Invalid file name: " + canonicalPath + ", Data Type: " + i);
            return null;
        }
        return SEFJNI.listKeyNamesByDataType(canonicalPath, i);
    }

    public static byte[] getData(File file, String str) throws Throwable {
        byte[] bArr;
        FileInputStream fileInputStream;
        String canonicalPath = file.getCanonicalPath();
        FileInputStream fileInputStream2 = null;
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return null;
        }
        try {
            try {
                fileInputStream = new FileInputStream(canonicalPath);
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    DataPosition dataPosition = getDataPosition(file, str);
                    if (dataPosition == null) {
                        fileInputStream.close();
                        fileInputStream.close();
                        return null;
                    }
                    long j = dataPosition.offset;
                    byte[] bArr2 = new byte[(int) dataPosition.length];
                    if (j < 0) {
                        fileInputStream.close();
                        return null;
                    }
                    try {
                        if (fileInputStream.skip(j) == 0) {
                            fileInputStream.close();
                            return null;
                        }
                        if (fileInputStream.read(bArr2) == 0) {
                            fileInputStream.close();
                            return null;
                        }
                        fileInputStream.close();
                        return bArr2;
                    } catch (IOException e) {
                        bArr = bArr2;
                        e = e;
                        fileInputStream2 = fileInputStream;
                        e.printStackTrace();
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        return bArr;
                    }
                } catch (IOException e2) {
                    e = e2;
                    bArr = null;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            bArr = null;
        }
    }

    public static byte[] getData(ParcelFileDescriptor parcelFileDescriptor, String str) throws Throwable {
        byte[] bArr;
        FileInputStream fileInputStream = null;
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return null;
        }
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    try {
                        DataPosition dataPosition = getDataPosition(parcelFileDescriptor, str);
                        if (dataPosition == null) {
                            fileInputStream2.close();
                            fileInputStream2.close();
                            return null;
                        }
                        long j = dataPosition.offset;
                        byte[] bArr2 = new byte[(int) dataPosition.length];
                        if (j < 0) {
                            fileInputStream2.close();
                            return null;
                        }
                        try {
                            if (fileInputStream2.skip(j) == 0) {
                                fileInputStream2.close();
                                return null;
                            }
                            if (fileInputStream2.read(bArr2) == 0) {
                                fileInputStream2.close();
                                return null;
                            }
                            fileInputStream2.close();
                            return bArr2;
                        } catch (IOException e) {
                            bArr = bArr2;
                            e = e;
                            fileInputStream = fileInputStream2;
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            return bArr;
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    bArr = null;
                }
            } catch (IOException e3) {
                e = e3;
                bArr = null;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static int getDataCount(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return -1;
        }
        return SEFJNI.getSEFDataCount(canonicalPath);
    }

    public static int getDataType(File file, String str) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return -1;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return -1;
        }
        return SEFJNI.getSEFDataType(canonicalPath, str);
    }

    public static int[] getDataTypeArray(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        return SEFJNI.listSEFDataTypes(canonicalPath);
    }

    public static int[] getDataTypeArray(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        return SEFJNI.listSEFDataTypesFileDescriptor(parcelFileDescriptor);
    }

    public static DataPosition getDataPosition(File file, String str) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return null;
        }
        DataPosition dataPosition = new DataPosition();
        long[] sEFDataPosition = SEFJNI.getSEFDataPosition(canonicalPath, str);
        if (sEFDataPosition == null) {
            Log.w(TAG, "No SEF data is found in file.");
            return null;
        }
        dataPosition.offset = sEFDataPosition[0];
        dataPosition.length = sEFDataPosition[1];
        return dataPosition;
    }

    public static DataPosition getDataPosition(ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return null;
        }
        DataPosition dataPosition = new DataPosition();
        long[] sEFDataPositionFileDescriptor = SEFJNI.getSEFDataPositionFileDescriptor(parcelFileDescriptor, str);
        if (sEFDataPositionFileDescriptor == null) {
            Log.w(TAG, "No SEF data is found in file.");
            return null;
        }
        dataPosition.offset = sEFDataPositionFileDescriptor[0];
        dataPosition.length = sEFDataPositionFileDescriptor[1];
        return dataPosition;
    }

    public static long[] getDataPositionArray(File file, String str) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return null;
        }
        long[] sEFDataPosition = SEFJNI.getSEFDataPosition(canonicalPath, str);
        if (sEFDataPosition != null) {
            return sEFDataPosition;
        }
        Log.w(TAG, "No SEF data matching to given keyName is found in file.");
        return null;
    }

    public static String[] listKeyNames(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        return SEFJNI.listKeyNames(canonicalPath);
    }

    public static String[] listKeyNames(File file, int i) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0 || i == -1) {
            Log.e(TAG, "Invalid file name: " + canonicalPath + ", Data Type: " + i);
            return null;
        }
        return SEFJNI.listKeyNamesByDataType(canonicalPath, i);
    }

    public static String[] listKeyNames(String str) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return null;
        }
        return SEFJNI.listKeyNames(str);
    }

    public static String[] listKeyNamesByDataType(String str, int i) {
        if (str == null || str.length() <= 0 || i == -1) {
            Log.e(TAG, "Invalid file name: " + str + ", Data Type: " + i);
            return null;
        }
        return SEFJNI.listKeyNamesByDataType(str, i);
    }

    public static SEFDataPosition getSEFDataPosition(String str, String str2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return null;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return null;
        }
        long[] sEFDataPosition = SEFJNI.getSEFDataPosition(str, str2);
        if (sEFDataPosition == null) {
            Log.w(TAG, "No SEF data is found in file.");
            return null;
        }
        SEFDataPosition sEFDataPosition2 = new SEFDataPosition();
        sEFDataPosition2.offset = sEFDataPosition[0];
        sEFDataPosition2.length = sEFDataPosition[1];
        return sEFDataPosition2;
    }

    public static long[] getSEFDataPositionArray(String str, String str2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return null;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return null;
        }
        long[] sEFDataPosition = SEFJNI.getSEFDataPosition(str, str2);
        if (sEFDataPosition != null) {
            return sEFDataPosition;
        }
        Log.w(TAG, "No SEF data is found in file.");
        return null;
    }

    public static byte[] getSEFData(File file, String str) throws Throwable {
        byte[] bArr;
        FileInputStream fileInputStream;
        String canonicalPath = file.getCanonicalPath();
        FileInputStream fileInputStream2 = null;
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return null;
        }
        try {
            try {
                fileInputStream = new FileInputStream(canonicalPath);
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    SEFDataPosition sEFDataPosition = getSEFDataPosition(canonicalPath, str);
                    if (sEFDataPosition == null) {
                        fileInputStream.close();
                        fileInputStream.close();
                        return null;
                    }
                    long j = sEFDataPosition.offset;
                    byte[] bArr2 = new byte[(int) sEFDataPosition.length];
                    if (j < 0) {
                        fileInputStream.close();
                        return null;
                    }
                    try {
                        if (fileInputStream.skip(j) == 0) {
                            fileInputStream.close();
                            return null;
                        }
                        if (fileInputStream.read(bArr2) == 0) {
                            fileInputStream.close();
                            return null;
                        }
                        fileInputStream.close();
                        return bArr2;
                    } catch (IOException e) {
                        bArr = bArr2;
                        e = e;
                        fileInputStream2 = fileInputStream;
                        e.printStackTrace();
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        return bArr;
                    }
                } catch (IOException e2) {
                    e = e2;
                    bArr = null;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            bArr = null;
        }
    }

    public static byte[] getSEFData(String str, String str2) throws Throwable {
        byte[] bArr;
        FileInputStream fileInputStream = null;
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return null;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return null;
        }
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(str);
                try {
                    try {
                        SEFDataPosition sEFDataPosition = getSEFDataPosition(str, str2);
                        if (sEFDataPosition == null) {
                            fileInputStream2.close();
                            fileInputStream2.close();
                            return null;
                        }
                        long j = sEFDataPosition.offset;
                        byte[] bArr2 = new byte[(int) sEFDataPosition.length];
                        if (j < 0) {
                            fileInputStream2.close();
                            return null;
                        }
                        try {
                            if (fileInputStream2.skip(j) == 0) {
                                fileInputStream2.close();
                                return null;
                            }
                            if (fileInputStream2.read(bArr2) == 0) {
                                fileInputStream2.close();
                                return null;
                            }
                            fileInputStream2.close();
                            return bArr2;
                        } catch (IOException e) {
                            bArr = bArr2;
                            e = e;
                            fileInputStream = fileInputStream2;
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            return bArr;
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    bArr = null;
                }
            } catch (IOException e3) {
                e = e3;
                bArr = null;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static int getSEFDataCount(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return -1;
        }
        return SEFJNI.getSEFDataCount(canonicalPath);
    }

    public static int getSEFDataCount(String str) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return -1;
        }
        return SEFJNI.getSEFDataCount(str);
    }

    public static int getSEFDataType(File file, String str) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return -1;
        }
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str);
            return -1;
        }
        return SEFJNI.getSEFDataType(canonicalPath, str);
    }

    public static int getSEFDataType(String str, String str2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return -1;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + str2);
            return -1;
        }
        return SEFJNI.getSEFDataType(str, str2);
    }

    public static int[] listSEFDataTypes(File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + canonicalPath);
            return null;
        }
        return SEFJNI.listSEFDataTypes(canonicalPath);
    }

    public static int[] listSEFDataTypes(String str) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return null;
        }
        return SEFJNI.listSEFDataTypes(str);
    }

    public static int getMajorDataType(String str) {
        int[] iArrListSEFDataTypes;
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + str);
            return -1;
        }
        if (SEFJNI.isSEFFile(str) == 0) {
            return -1;
        }
        try {
            iArrListSEFDataTypes = listSEFDataTypes(new File(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (iArrListSEFDataTypes == null) {
            Log.e(TAG, "No data type has been found : " + str);
            return -1;
        }
        for (int length = iArrListSEFDataTypes.length - 1; length > -1; length--) {
            int i = iArrListSEFDataTypes[length];
            if (i >= 2048 && i <= 16384 && (i & 15) == 0) {
                return i;
            }
        }
        Log.e(TAG, "No major data type has been found : " + str);
        return -1;
    }

    public static int copyAllData(File file, File file2) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        String canonicalPath2 = file2.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid src file name: " + canonicalPath);
            return 0;
        }
        if (canonicalPath2 == null || canonicalPath2.length() <= 0) {
            Log.e(TAG, "Invalid dst file name: " + canonicalPath2);
            return 0;
        }
        return SEFJNI.copyAllSEFData(canonicalPath, canonicalPath2);
    }

    public static int copyAllData(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws IOException {
        return SEFJNI.copyAllSEFDataFileDescriptor(parcelFileDescriptor, parcelFileDescriptor2);
    }

    public static int copyAllSEFData(File file, File file2) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        String canonicalPath2 = file2.getCanonicalPath();
        if (canonicalPath == null || canonicalPath.length() <= 0) {
            Log.e(TAG, "Invalid src file name: " + canonicalPath);
            return 0;
        }
        if (canonicalPath2 == null || canonicalPath2.length() <= 0) {
            Log.e(TAG, "Invalid dst file name: " + canonicalPath2);
            return 0;
        }
        return SEFJNI.copyAllSEFData(canonicalPath, canonicalPath2);
    }

    public static int copyAllSEFData(String str, String str2) {
        if (str == null || str.length() <= 0) {
            Log.e(TAG, "Invalid src file name: " + str);
            return 0;
        }
        if (str2 == null || str2.length() <= 0) {
            Log.e(TAG, "Invalid dst file name: " + str2);
            return 0;
        }
        return SEFJNI.copyAllSEFData(str, str2);
    }

    public static void convertImageToMP4(String str, String str2) {
        if (new File(str).exists()) {
            int majorDataType = getMajorDataType(str);
            if (majorDataType == 2608) {
                MotionPhotoConverter.getInstance().convertToMp4(str, str2);
                return;
            }
            Log.e(TAG, "This type of file is not yet supported. type=" + majorDataType);
        }
    }

    public static boolean isMp4ConversionSupported(Context context, String str) {
        int majorDataType = getMajorDataType(str);
        if (majorDataType == 2048) {
            Log.i(TAG, "SoundNShot is not supported from P OS. So, MP4 Conversion for SoundNShot is removed from Q OS");
            return false;
        }
        if (majorDataType == 2256) {
            Log.i(TAG, "VirtualShot is not supported from R OS. So, MP4 Conversion for VirtualShot is removed from R OS");
            return false;
        }
        if (majorDataType == 2272) {
            Log.i(TAG, "MotionPanoramaShot is not supported from R OS. So, MP4 Conversion for MotionPanoramaShot is removed from R OS");
            return false;
        }
        if (majorDataType == 2416) {
            Log.i(TAG, "MotionWideSelfie is not supported from R OS. So, MP4 Conversion for MotionWideSelfie is removed from R OS");
            return false;
        }
        if (majorDataType == 2608) {
            return true;
        }
        Log.e(TAG, "This type of file is not yet supported. type=" + majorDataType);
        return false;
    }

    private static boolean isViewerInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str.toString(), 1);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
