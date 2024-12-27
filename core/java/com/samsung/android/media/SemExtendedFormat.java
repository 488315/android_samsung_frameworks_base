package com.samsung.android.media;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
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
        public static final String INTERACTIVE_PANORAMA_DEBUG_DATA =
                "Interactive_Panorama_Debug_Data";
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
        public static final String SINGLE_RELIGHT_BOKEH_REAR_INFO =
                "Single_Relighting_Bokeh_Rear_Info";
        public static final String SINGLE_SHOT_BOKEH_INFO = "SingleShot_Meta_Info";
        public static final String SINGLE_SHOT_BOKEH_REAR_INFO = "SingleShot_Meta_Rear_Info";
        public static final String SINGLE_SHOT_DEPTHMAP = "SingeShot_DepthMap_%d";
        public static final String SINGLE_SHOT_JPEG_TEMPLATE = "SingleShot";
        public static final String SINGLE_TAKE_CAMERA_DRAFT_INFO = "Single_Take_Camera_Draft_Info";
        public static final String SINGLE_TAKE_CAMERA_INFO = "Single_Take_Camera_Info";
        public static final String SINGLE_TAKE_CAMERA_REPRESENTIVE_INFO =
                "Single_Take_Camera_Representive_Info";
        public static final String SINGLE_TAKE_VIDEO_FIRSTFRAME_TIMESTAMP_INFO =
                "Single_Take_Video_Firstframe_Timestamp_Info";
        public static final String SINGLE_TAKE_VIDEO_TYPE_INFO = "Single_Take_Video_Type_Info";
        public static final String SLOW_MOTION_DATA = "SlowMotion_Data";
        public static final String SOUND_SHOT_INFO = "SoundShot_Meta_Info";
        public static final String SOUND_SHOT_WAVE = "SoundShot_000";
        public static final String SPORTS_SHOT_INFO = "Sports_Shot_Info";
        public static final String SUPER_SLOW_MOTION_BGM = "Super_SlowMotion_BGM";
        public static final String SUPER_SLOW_MOTION_DATA = "Super_SlowMotion_Data";
        public static final String SUPER_SLOW_MOTION_DEFLICKERING_INFO =
                "Super_SlowMotion_Deflickering_Info";
        public static final String SUPER_SLOW_MOTION_DEFLICKERING_ON =
                "Super_SlowMotion_Deflickering_On";
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
        private static final String INTERACTIVESHOT_PACKAGE_NAME =
                "com.samsung.android.app.interactivepanoramaviewer";
        private static final String MOTIONPANORAMA_PACKAGE_NAME =
                "com.samsung.android.app.motionpanoramaviewer";
        private static final String SELFMOTIONPANORAMA_PACKAGE_NAME =
                "com.samsung.android.app.selfmotionpanoramaviewer";

        private SEFViewerPackageName() {}
    }

    public static boolean isValidFile(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidFile(ParcelFileDescriptor pfd) throws IOException {
        if (SEFJNI.isSEFfileDescriptor(pfd) == 0) {
            return false;
        }
        return true;
    }

    public static boolean hasData(File sefFile, int dataType) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0 || dataType == -1) {
            Log.e(TAG, "Invalid file name: " + fileName + ", Data Type: " + dataType);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        int[] currentTypes = listSEFDataTypes(sefFile);
        if (currentTypes == null) {
            Log.e(TAG, "Invalid file : " + fileName);
            return false;
        }
        for (int i = currentTypes.length - 1; i > -1; i--) {
            if (dataType == currentTypes[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasData(File sefFile, String keyName) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0 || keyName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName + ", keyName: " + keyName);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        String[] currentTypes = listKeyNames(sefFile);
        if (currentTypes == null) {
            Log.e(TAG, "Invalid file : " + fileName);
            return false;
        }
        if (currentTypes.length <= 0) {
            Log.e(TAG, "Invalid file : " + fileName);
            return false;
        }
        for (int i = currentTypes.length - 1; i > -1; i--) {
            if (keyName.equals(currentTypes[i])) {
                return true;
            }
        }
        return false;
    }

    public static String getVersion() {
        int native_version = SEFJNI.getNativeVersion();
        String version = "1.19_" + native_version;
        return version;
    }

    public static boolean isSEFFile(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        return true;
    }

    public static boolean isSEFFile(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        return true;
    }

    public static boolean hasSEFData(File sefFile, int dataType) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0 || dataType == -1) {
            Log.e(TAG, "Invalid file name: " + fileName + ", Data Type: " + dataType);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        int[] currentTypes = listSEFDataTypes(sefFile);
        if (currentTypes == null) {
            Log.e(TAG, "Invalid file : " + fileName);
            return false;
        }
        for (int i = currentTypes.length - 1; i > -1; i--) {
            if (dataType == currentTypes[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSEFData(File sefFile, String keyName) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0 || keyName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName + ", keyName: " + keyName);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        String[] currentTypes = listKeyNames(sefFile);
        if (currentTypes == null) {
            Log.e(TAG, "Invalid file : " + fileName);
            return false;
        }
        if (currentTypes.length <= 0) {
            Log.e(TAG, "Invalid file : " + fileName);
            return false;
        }
        for (int i = currentTypes.length - 1; i > -1; i--) {
            if (keyName.equals(currentTypes[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDataType(String fileName, int dataType) {
        if (fileName == null || fileName.length() <= 0 || dataType == -1) {
            Log.e(TAG, "Invalid file name: " + fileName + ", Data Type: " + dataType);
            return false;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return false;
        }
        int[] currentTypes = listSEFDataTypes(fileName);
        if (currentTypes == null) {
            Log.e(TAG, "Invalid file : " + fileName);
            return false;
        }
        for (int i = currentTypes.length - 1; i > -1; i--) {
            if (dataType == currentTypes[i]) {
                return true;
            }
        }
        return false;
    }

    public static int addData(File sefFile, String keyName, byte[] data, int dataType, int option)
            throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        if (data == null || data.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0;
        }
        if (option == 256 || option == 4) {
            return SEFJNI.addSEFDataToMP4(
                    fileName, keyName, keyName.length(), null, 0, data, data.length, dataType, 0);
        }
        if (option == 4096 || option == 5) {
            return SEFJNI.addSEFDataToMP4(
                    fileName, keyName, keyName.length(), null, 0, data, data.length, dataType, 1);
        }
        if (option == 2) {
            return SEFJNI.addSEFDataAddTag(
                    fileName, keyName, keyName.length(), null, 0, data, data.length, dataType, 0);
        }
        if (option == 3) {
            return SEFJNI.addSEFDataAddTag(
                    fileName, keyName, keyName.length(), null, 0, data, data.length, dataType, 1);
        }
        if (option == 0 || option == 1) {
            return SEFJNI.addSEFData(
                    fileName,
                    keyName,
                    keyName.length(),
                    null,
                    0,
                    data,
                    data.length,
                    dataType,
                    option);
        }
        Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
        return 0;
    }

    public static int addData(File sefFile, String keyName, File dataFile, int dataType, int option)
            throws IOException {
        String fileName = sefFile.getCanonicalPath();
        String dataFileName = dataFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        if (dataFileName == null || dataFileName.length() <= 0) {
            Log.e(TAG, "Invalid SEF Data File name: " + dataFileName);
            return 0;
        }
        if (option == 256 || option == 4) {
            return SEFJNI.addSEFDataFileToMP4(
                    fileName, keyName, keyName.length(), null, 0, dataFileName, dataType, 0);
        }
        if (option == 4096 || option == 5) {
            return SEFJNI.addSEFDataFileToMP4(
                    fileName, keyName, keyName.length(), null, 0, dataFileName, dataType, 1);
        }
        if (option == 2) {
            return SEFJNI.addSEFDataFileAddTag(
                    fileName, keyName, keyName.length(), null, 0, dataFileName, dataType, 0);
        }
        if (option == 3) {
            return SEFJNI.addSEFDataFileAddTag(
                    fileName, keyName, keyName.length(), null, 0, dataFileName, dataType, 1);
        }
        if (option == 0 || option == 1) {
            return SEFJNI.addSEFDataFile(
                    fileName, keyName, keyName.length(), null, 0, dataFileName, dataType, option);
        }
        Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
        return 0;
    }

    public static long addSEFDataByteBufferAddTag(
            ByteBuffer Buffer,
            String SEFname,
            int SEFname_len,
            byte[] data_sub_info,
            int data_sub_info_len,
            byte[] data,
            int data_len,
            int data_type,
            int option) {
        byte[] BufferArray = Buffer.array();
        long offset = Buffer.position();
        long AllocSize = Buffer.capacity();
        long StartOffset = Buffer.arrayOffset();
        if (AllocSize <= 0) {
            return 0L;
        }
        long Ret =
                SEFJNI.addSEFDataBufferAddTag(
                        BufferArray,
                        AllocSize,
                        offset,
                        StartOffset,
                        SEFname,
                        SEFname_len,
                        data_sub_info,
                        data_sub_info_len,
                        data,
                        data_len,
                        data_type,
                        option);
        Buffer.position((int) Ret);
        return Ret;
    }

    public static long addData(
            ByteBuffer buffer, String keyName, byte[] data, int dataType, int option) {
        if (buffer == null) {
            Log.e(TAG, "buffer is null");
            return 0L;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0L;
        }
        if (data == null || data.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0L;
        }
        if (option == 0 || option == 1) {
            return addSEFDataByteBufferAddTag(
                    buffer,
                    keyName,
                    keyName.length(),
                    null,
                    0,
                    data,
                    data.length,
                    dataType,
                    option);
        }
        Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
        Log.e(TAG, "You can use only one of two - TYPE_SKIP_IF_EXISTS, TYPE_OVERWRITE_IF_EXISTS");
        return 0L;
    }

    public static long getRequiredBufferSize(
            long orgDataSize, int dataCount, long totalDataSize, long totalkeyNameSize) {
        if (orgDataSize <= 0) {
            Log.e(TAG, "invalid orgDataSize : " + orgDataSize);
            return 0L;
        }
        if (dataCount <= 0) {
            Log.e(TAG, "invalid dataCount : " + dataCount);
            return 0L;
        }
        if (totalDataSize <= 0) {
            Log.e(TAG, "invalid totalDataSize : " + totalDataSize);
            return 0L;
        }
        if (totalkeyNameSize <= 0) {
            Log.e(TAG, "invalid totalkeyNameSize : " + totalkeyNameSize);
            return 0L;
        }
        return SEFJNI.getSEFBufferAllocSize(
                orgDataSize, dataCount, totalDataSize, 0L, totalkeyNameSize);
    }

    public static long addData(
            ParcelFileDescriptor pfd, String keyName, byte[] data, int dataType, int option) {
        if (pfd == null) {
            Log.e(TAG, "pfd is null");
            return 0L;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0L;
        }
        if (data == null || data.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0L;
        }
        if (option == 256 || option == 4) {
            return addSEFDataFileDescriptorToMP4(
                    pfd, keyName, keyName.length(), null, 0, data, data.length, dataType, 0);
        }
        if (option == 4096 || option == 5) {
            return addSEFDataFileDescriptorToMP4(
                    pfd, keyName, keyName.length(), null, 0, data, data.length, dataType, 1);
        }
        if (option == 2) {
            return addSEFDataFileDescriptorAddTag(
                    pfd, keyName, keyName.length(), null, 0, data, data.length, dataType, 0);
        }
        if (option == 3) {
            return addSEFDataFileDescriptorAddTag(
                    pfd, keyName, keyName.length(), null, 0, data, data.length, dataType, 1);
        }
        if (option == 0 || option == 1) {
            return addSEFDataFilSEFeDescriptor(
                    pfd, keyName, keyName.length(), null, 0, data, data.length, dataType, option);
        }
        Log.e(TAG, "Unsupported Option Combination. Please check the option !!!!!");
        return 0L;
    }

    public static long addSEFDataFilSEFeDescriptor(
            ParcelFileDescriptor Pfd,
            String SEFname,
            int SEFname_len,
            byte[] data_sub_info,
            int data_sub_info_len,
            byte[] data,
            int data_len,
            int data_type,
            int option) {
        return SEFJNI.addSEFDataFd(
                Pfd.getFd(),
                SEFname,
                SEFname_len,
                data_sub_info,
                data_sub_info_len,
                data,
                data_len,
                data_type,
                option);
    }

    public static long addSEFDataFileDescriptorAddTag(
            ParcelFileDescriptor Pfd,
            String SEFname,
            int SEFname_len,
            byte[] data_sub_info,
            int data_sub_info_len,
            byte[] data,
            int data_len,
            int data_type,
            int option) {
        return SEFJNI.addSEFDataFdAddTag(
                Pfd.getFd(),
                SEFname,
                SEFname_len,
                data_sub_info,
                data_sub_info_len,
                data,
                data_len,
                data_type,
                option);
    }

    public static long addSEFDataFileDescriptorToMP4(
            ParcelFileDescriptor Pfd,
            String SEFname,
            int SEFname_len,
            byte[] data_sub_info,
            int data_sub_info_len,
            byte[] data,
            int data_len,
            int data_type,
            int option) {
        return SEFJNI.addSEFDataFdToMP4(
                Pfd.getFd(),
                SEFname,
                SEFname_len,
                data_sub_info,
                data_sub_info_len,
                data,
                data_len,
                data_type,
                option);
    }

    public static int addSEFData(
            File sefFile, String keyName, byte[] data, int dataType, int option)
            throws IOException {
        return addSEFData(sefFile, keyName, data, (byte[]) null, dataType, option);
    }

    public static int addSEFData(
            File sefFile, String keyName, byte[] data, byte[] subdataInfo, int dataType, int option)
            throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        if (data == null || data.length <= 0) {
            Log.e(TAG, "Invalid data");
            return 0;
        }
        if (option == 16) {
            return SEFJNI.addFastSEFData(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    data,
                    data.length,
                    dataType,
                    option);
        }
        if (option == 256) {
            return SEFJNI.addSEFDataToMP4(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    data,
                    data.length,
                    dataType,
                    0);
        }
        if (option == 4096) {
            return SEFJNI.addSEFDataToMP4(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    data,
                    data.length,
                    dataType,
                    1);
        }
        return SEFJNI.addSEFData(
                fileName,
                keyName,
                keyName.length(),
                subdataInfo,
                subdataInfo == null ? 0 : subdataInfo.length,
                data,
                data.length,
                dataType,
                option);
    }

    public static int addSEFData(
            File sefFile, String keyName, File dataFile, int dataType, int option)
            throws IOException {
        return addSEFData(sefFile, keyName, dataFile, (byte[]) null, dataType, option);
    }

    public static int addSEFData(
            File sefFile,
            String keyName,
            File dataFile,
            byte[] subdataInfo,
            int dataType,
            int option)
            throws IOException {
        String fileName = sefFile.getCanonicalPath();
        String dataFileName = dataFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        if (dataFileName == null || dataFileName.length() <= 0) {
            Log.e(TAG, "Invalid SEF Data File name: " + dataFileName);
            return 0;
        }
        if (option == 16) {
            return SEFJNI.addFastSEFDataFile(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    dataFileName,
                    dataType,
                    option);
        }
        if (option == 256) {
            return SEFJNI.addSEFDataFileToMP4(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    dataFileName,
                    dataType,
                    0);
        }
        if (option == 4096) {
            return SEFJNI.addSEFDataFileToMP4(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    dataFileName,
                    dataType,
                    1);
        }
        return SEFJNI.addSEFDataFile(
                fileName,
                keyName,
                keyName.length(),
                subdataInfo,
                subdataInfo == null ? 0 : subdataInfo.length,
                dataFileName,
                dataType,
                option);
    }

    public static int addSEFData(
            String fileName, String keyName, byte[] data, int dataType, int option) {
        return addSEFData(fileName, keyName, data, (byte[]) null, dataType, option);
    }

    public static int addSEFData(
            String fileName,
            String keyName,
            byte[] data,
            byte[] subdataInfo,
            int dataType,
            int option) {
        if (fileName != null && fileName.length() > 0) {
            if (keyName != null && keyName.length() > 0) {
                if (data != null && data.length > 0) {
                    if (option == 16) {
                        return SEFJNI.addFastSEFData(
                                fileName,
                                keyName,
                                keyName.length(),
                                subdataInfo,
                                subdataInfo == null ? 0 : subdataInfo.length,
                                data,
                                data.length,
                                dataType,
                                option);
                    }
                    return SEFJNI.addSEFData(
                            fileName,
                            keyName,
                            keyName.length(),
                            subdataInfo,
                            subdataInfo == null ? 0 : subdataInfo.length,
                            data,
                            data.length,
                            dataType,
                            option);
                }
                Log.e(TAG, "Invalid data");
                return 0;
            }
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        Log.e(TAG, "Invalid file name: " + fileName);
        return 0;
    }

    public static int addSEFDataFile(
            String fileName, String keyName, String dataFileName, int dataType, int option) {
        return addSEFDataFile(fileName, keyName, dataFileName, null, dataType, option);
    }

    public static int addSEFDataFile(
            String fileName,
            String keyName,
            String dataFileName,
            byte[] subdataInfo,
            int dataType,
            int option) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        if (dataFileName != null && dataFileName.length() > 0) {
            return SEFJNI.addSEFDataFile(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    dataFileName,
                    dataType,
                    option);
        }
        Log.e(TAG, "Invalid SEF Data File name: " + dataFileName);
        return 0;
    }

    public static int addFastSEFData(
            String fileName, String keyName, byte[] data, int dataType, int option) {
        return addFastSEFData(fileName, keyName, data, null, dataType, option);
    }

    public static int addFastSEFData(
            String fileName,
            String keyName,
            byte[] data,
            byte[] subdataInfo,
            int dataType,
            int option) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        if (data != null && data.length > 0) {
            return SEFJNI.addFastSEFData(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    data,
                    data.length,
                    dataType,
                    option);
        }
        Log.e(TAG, "Invalid data");
        return 0;
    }

    public static int addFastSEFDataFile(
            String fileName, String keyName, String dataFileName, int dataType, int option) {
        return addFastSEFDataFile(fileName, keyName, dataFileName, null, dataType, option);
    }

    public static int addFastSEFDataFile(
            String fileName,
            String keyName,
            String dataFileName,
            byte[] subdataInfo,
            int dataType,
            int option) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        if (dataFileName != null && dataFileName.length() > 0) {
            return SEFJNI.addFastSEFDataFile(
                    fileName,
                    keyName,
                    keyName.length(),
                    subdataInfo,
                    subdataInfo == null ? 0 : subdataInfo.length,
                    dataFileName,
                    dataType,
                    option);
        }
        Log.e(TAG, "Invalid SEF Data File name: " + dataFileName);
        return 0;
    }

    public static int addSEFDataFiles(
            String fileName,
            String[] keyNames,
            String[] dataFileNames,
            int[] dataTypes,
            int option) {
        int dataCount = keyNames.length;
        if (dataCount != dataFileNames.length) {
            Log.e(
                    TAG,
                    "Data Count is different. ( keyNames data count= "
                            + dataCount
                            + ", dataFileNames data count= "
                            + dataFileNames.length
                            + " )");
        } else if (dataCount != dataTypes.length) {
            Log.e(
                    TAG,
                    "Data Count is different. ( keyNames data count= "
                            + dataCount
                            + ", dataTypes data count= "
                            + dataTypes.length
                            + " )");
        }
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        int[] keynamesLength = new int[keyNames.length];
        for (int i = 0; i < keyNames.length; i++) {
            keynamesLength[i] = keyNames[i].length();
        }
        return SEFJNI.addSEFDataFiles(
                fileName, keyNames, keynamesLength, dataFileNames, dataTypes, option, dataCount);
    }

    public static boolean deleteData(File sefFile, String keyName) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return false;
        }
        if (keyName != null && keyName.length() > 0) {
            return SEFJNI.deleteSEFData(fileName, keyName, keyName.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + keyName);
        return false;
    }

    public static boolean deleteData(ParcelFileDescriptor pfd, String keyName) throws IOException {
        if (keyName != null && keyName.length() > 0) {
            return SEFJNI.deleteSEFDataFileDescriptor(pfd, keyName, keyName.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + keyName);
        return false;
    }

    public static boolean deleteAllData(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName != null && fileName.length() > 0) {
            return SEFJNI.clearSEFData(fileName) == 1;
        }
        Log.e(TAG, "Invalid file name: " + fileName);
        return false;
    }

    public static boolean deleteAllData(ParcelFileDescriptor pfd) throws IOException {
        return SEFJNI.clearSEFDataFileDescriptor(pfd) == 1;
    }

    public static boolean deleteSEFData(File sefFile, String keyName) throws IOException {
        return deleteSEFData(sefFile, keyName, 1);
    }

    public static boolean deleteSEFData(File sefFile, String keyName, int option)
            throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return false;
        }
        if (keyName != null && keyName.length() > 0) {
            return option == 16
                    ? SEFJNI.fastDeleteSEFData(fileName, keyName, keyName.length()) == 1
                    : SEFJNI.deleteSEFData(fileName, keyName, keyName.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + keyName);
        return false;
    }

    public static int deleteSEFData(String fileName, String keyName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return 0;
        }
        return SEFJNI.deleteSEFData(fileName, keyName, keyName.length());
    }

    public static boolean deleteFastSEFData(String fileName, String keyName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return false;
        }
        if (keyName != null && keyName.length() > 0) {
            return SEFJNI.fastDeleteSEFData(fileName, keyName, keyName.length()) == 1;
        }
        Log.e(TAG, "Invalid key name: " + keyName);
        return false;
    }

    public static boolean deleteAllSEFData(File sefFile) throws IOException {
        return deleteAllSEFData(sefFile, 1);
    }

    public static boolean deleteAllSEFData(File sefFile, int option) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName != null && fileName.length() > 0) {
            return option == 16
                    ? SEFJNI.fastClearSEFData(fileName) == 1
                    : SEFJNI.clearSEFData(fileName) == 1;
        }
        Log.e(TAG, "Invalid file name: " + fileName);
        return false;
    }

    public static int clearSEFData(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        return SEFJNI.clearSEFData(fileName);
    }

    public static int clearFastSEFData(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return 0;
        }
        return SEFJNI.fastClearSEFData(fileName);
    }

    public static boolean compact(File sefFile) throws IOException {
        if (deleteSEFData(sefFile, KeyName.INVALID_DATA)) {
            return true;
        }
        return false;
    }

    public static String[] getKeyNameArray(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        return SEFJNI.listKeyNames(fileName);
    }

    public static String[] getKeyNameArray(ParcelFileDescriptor pfd) throws IOException {
        return SEFJNI.listKeyNamesFileDescriptor(pfd);
    }

    @Deprecated(forRemoval = true, since = "15.5")
    public static String[] getKeyNameArray(File sefFile, int dataType) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0 || dataType == -1) {
            Log.e(TAG, "Invalid file name: " + fileName + ", Data Type: " + dataType);
            return null;
        }
        return SEFJNI.listKeyNamesByDataType(fileName, dataType);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x006a, code lost:

       if (0 == 0) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getData(java.io.File r11, java.lang.String r12)
            throws java.io.IOException {
        /*
            java.lang.String r0 = r11.getCanonicalPath()
            java.lang.String r1 = "SemExtendedFormat"
            r2 = 0
            if (r0 == 0) goto L8b
            int r3 = r0.length()
            if (r3 > 0) goto L11
            goto L8b
        L11:
            if (r12 == 0) goto L74
            int r3 = r12.length()
            if (r3 > 0) goto L1a
            goto L74
        L1a:
            r1 = 0
            r3 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r3 = r4
            com.samsung.android.media.SemExtendedFormat$DataPosition r4 = getDataPosition(r11, r12)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            if (r4 != 0) goto L31
            r3.close()     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r3.close()
            return r2
        L31:
            long r5 = r4.offset     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            long r7 = r4.length     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            int r7 = (int) r7     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            byte[] r7 = new byte[r7]     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r1 = r7
            r7 = 0
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 >= 0) goto L45
        L41:
            r3.close()
            return r2
        L45:
            long r9 = r3.skip(r5)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            int r7 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r7 != 0) goto L53
        L4f:
            r3.close()
            return r2
        L53:
            int r7 = r3.read(r1)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            if (r7 != 0) goto L5f
        L5b:
            r3.close()
            return r2
        L5f:
        L60:
            r3.close()
            goto L6d
        L64:
            r2 = move-exception
            goto L6e
        L66:
            r2 = move-exception
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L64
            if (r3 == 0) goto L6d
            goto L60
        L6d:
            return r1
        L6e:
            if (r3 == 0) goto L73
            r3.close()
        L73:
            throw r2
        L74:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Invalid key name: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r12)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r1, r3)
            return r2
        L8b:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Invalid file name: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r1, r3)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.media.SemExtendedFormat.getData(java.io.File,"
                    + " java.lang.String):byte[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x005e, code lost:

       if (0 == 0) goto L35;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getData(android.os.ParcelFileDescriptor r10, java.lang.String r11)
            throws java.io.IOException {
        /*
            r0 = 0
            if (r11 == 0) goto L68
            int r1 = r11.length()
            if (r1 > 0) goto La
            goto L68
        La:
            r1 = 0
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            java.io.FileDescriptor r4 = r10.getFileDescriptor()     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r2 = r3
            com.samsung.android.media.SemExtendedFormat$DataPosition r3 = getDataPosition(r10, r11)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            if (r3 != 0) goto L25
            r2.close()     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r2.close()
            return r0
        L25:
            long r4 = r3.offset     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            long r6 = r3.length     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            int r6 = (int) r6     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r1 = r6
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L39
        L35:
            r2.close()
            return r0
        L39:
            long r8 = r2.skip(r4)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r6 != 0) goto L47
        L43:
            r2.close()
            return r0
        L47:
            int r6 = r2.read(r1)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            if (r6 != 0) goto L53
        L4f:
            r2.close()
            return r0
        L53:
        L54:
            r2.close()
            goto L61
        L58:
            r0 = move-exception
            goto L62
        L5a:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L58
            if (r2 == 0) goto L61
            goto L54
        L61:
            return r1
        L62:
            if (r2 == 0) goto L67
            r2.close()
        L67:
            throw r0
        L68:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Invalid key name: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "SemExtendedFormat"
            android.util.Log.e(r2, r1)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.media.SemExtendedFormat.getData(android.os.ParcelFileDescriptor,"
                    + " java.lang.String):byte[]");
    }

    public static int getDataCount(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return -1;
        }
        int ret = SEFJNI.getSEFDataCount(fileName);
        return ret;
    }

    public static int getDataType(File sefFile, String keyName) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return -1;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return -1;
        }
        return SEFJNI.getSEFDataType(fileName, keyName);
    }

    public static int[] getDataTypeArray(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        return SEFJNI.listSEFDataTypes(fileName);
    }

    public static int[] getDataTypeArray(ParcelFileDescriptor pfd) throws IOException {
        return SEFJNI.listSEFDataTypesFileDescriptor(pfd);
    }

    public static DataPosition getDataPosition(File sefFile, String keyName) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return null;
        }
        DataPosition position = new DataPosition();
        long[] posArray = SEFJNI.getSEFDataPosition(fileName, keyName);
        if (posArray == null) {
            Log.w(TAG, "No SEF data is found in file.");
            return null;
        }
        position.offset = posArray[0];
        position.length = posArray[1];
        return position;
    }

    public static DataPosition getDataPosition(ParcelFileDescriptor pfd, String keyName)
            throws IOException {
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return null;
        }
        DataPosition position = new DataPosition();
        long[] posArray = SEFJNI.getSEFDataPositionFileDescriptor(pfd, keyName);
        if (posArray == null) {
            Log.w(TAG, "No SEF data is found in file.");
            return null;
        }
        position.offset = posArray[0];
        position.length = posArray[1];
        return position;
    }

    public static long[] getDataPositionArray(File sefFile, String keyName) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return null;
        }
        long[] PositionData = SEFJNI.getSEFDataPosition(fileName, keyName);
        if (PositionData == null) {
            Log.w(TAG, "No SEF data matching to given keyName is found in file.");
            return null;
        }
        return PositionData;
    }

    public static String[] listKeyNames(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        return SEFJNI.listKeyNames(fileName);
    }

    public static String[] listKeyNames(File sefFile, int dataType) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0 || dataType == -1) {
            Log.e(TAG, "Invalid file name: " + fileName + ", Data Type: " + dataType);
            return null;
        }
        return SEFJNI.listKeyNamesByDataType(fileName, dataType);
    }

    public static String[] listKeyNames(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        return SEFJNI.listKeyNames(fileName);
    }

    public static String[] listKeyNamesByDataType(String fileName, int dataType) {
        if (fileName == null || fileName.length() <= 0 || dataType == -1) {
            Log.e(TAG, "Invalid file name: " + fileName + ", Data Type: " + dataType);
            return null;
        }
        return SEFJNI.listKeyNamesByDataType(fileName, dataType);
    }

    public static SEFDataPosition getSEFDataPosition(String fileName, String keyName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return null;
        }
        long[] posArray = SEFJNI.getSEFDataPosition(fileName, keyName);
        if (posArray == null) {
            Log.w(TAG, "No SEF data is found in file.");
            return null;
        }
        SEFDataPosition position = new SEFDataPosition();
        position.offset = posArray[0];
        position.length = posArray[1];
        return position;
    }

    public static long[] getSEFDataPositionArray(String fileName, String keyName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return null;
        }
        long[] posArray = SEFJNI.getSEFDataPosition(fileName, keyName);
        if (posArray == null) {
            Log.w(TAG, "No SEF data is found in file.");
            return null;
        }
        return posArray;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x006a, code lost:

       if (0 == 0) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getSEFData(java.io.File r11, java.lang.String r12)
            throws java.io.IOException {
        /*
            java.lang.String r0 = r11.getCanonicalPath()
            java.lang.String r1 = "SemExtendedFormat"
            r2 = 0
            if (r0 == 0) goto L8b
            int r3 = r0.length()
            if (r3 > 0) goto L11
            goto L8b
        L11:
            if (r12 == 0) goto L74
            int r3 = r12.length()
            if (r3 > 0) goto L1a
            goto L74
        L1a:
            r1 = 0
            r3 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r3 = r4
            com.samsung.android.media.SemExtendedFormat$SEFDataPosition r4 = getSEFDataPosition(r0, r12)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            if (r4 != 0) goto L31
            r3.close()     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r3.close()
            return r2
        L31:
            long r5 = r4.offset     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            long r7 = r4.length     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            int r7 = (int) r7     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            byte[] r7 = new byte[r7]     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            r1 = r7
            r7 = 0
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 >= 0) goto L45
        L41:
            r3.close()
            return r2
        L45:
            long r9 = r3.skip(r5)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            int r7 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r7 != 0) goto L53
        L4f:
            r3.close()
            return r2
        L53:
            int r7 = r3.read(r1)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L66
            if (r7 != 0) goto L5f
        L5b:
            r3.close()
            return r2
        L5f:
        L60:
            r3.close()
            goto L6d
        L64:
            r2 = move-exception
            goto L6e
        L66:
            r2 = move-exception
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L64
            if (r3 == 0) goto L6d
            goto L60
        L6d:
            return r1
        L6e:
            if (r3 == 0) goto L73
            r3.close()
        L73:
            throw r2
        L74:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Invalid key name: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r12)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r1, r3)
            return r2
        L8b:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Invalid file name: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r1, r3)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.media.SemExtendedFormat.getSEFData(java.io.File,"
                    + " java.lang.String):byte[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0066, code lost:

       if (0 == 0) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getSEFData(java.lang.String r10, java.lang.String r11)
            throws java.io.IOException {
        /*
            java.lang.String r0 = "SemExtendedFormat"
            r1 = 0
            if (r10 == 0) goto L87
            int r2 = r10.length()
            if (r2 > 0) goto Ld
            goto L87
        Ld:
            if (r11 == 0) goto L70
            int r2 = r11.length()
            if (r2 > 0) goto L16
            goto L70
        L16:
            r0 = 0
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            r3.<init>(r10)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            r2 = r3
            com.samsung.android.media.SemExtendedFormat$SEFDataPosition r3 = getSEFDataPosition(r10, r11)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            if (r3 != 0) goto L2d
            r2.close()     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            r2.close()
            return r1
        L2d:
            long r4 = r3.offset     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            long r6 = r3.length     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            int r6 = (int) r6     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            r0 = r6
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L41
        L3d:
            r2.close()
            return r1
        L41:
            long r8 = r2.skip(r4)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r6 != 0) goto L4f
        L4b:
            r2.close()
            return r1
        L4f:
            int r6 = r2.read(r0)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            if (r6 != 0) goto L5b
        L57:
            r2.close()
            return r1
        L5b:
        L5c:
            r2.close()
            goto L69
        L60:
            r1 = move-exception
            goto L6a
        L62:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L60
            if (r2 == 0) goto L69
            goto L5c
        L69:
            return r0
        L6a:
            if (r2 == 0) goto L6f
            r2.close()
        L6f:
            throw r1
        L70:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Invalid key name: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r0, r2)
            return r1
        L87:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Invalid file name: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r10)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r0, r2)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.media.SemExtendedFormat.getSEFData(java.lang.String,"
                    + " java.lang.String):byte[]");
    }

    public static int getSEFDataCount(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return -1;
        }
        int ret = SEFJNI.getSEFDataCount(fileName);
        return ret;
    }

    public static int getSEFDataCount(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return -1;
        }
        int ret = SEFJNI.getSEFDataCount(fileName);
        return ret;
    }

    public static int getSEFDataType(File sefFile, String keyName) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return -1;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return -1;
        }
        return SEFJNI.getSEFDataType(fileName, keyName);
    }

    public static int getSEFDataType(String fileName, String keyName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return -1;
        }
        if (keyName == null || keyName.length() <= 0) {
            Log.e(TAG, "Invalid key name: " + keyName);
            return -1;
        }
        return SEFJNI.getSEFDataType(fileName, keyName);
    }

    public static int[] listSEFDataTypes(File sefFile) throws IOException {
        String fileName = sefFile.getCanonicalPath();
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        return SEFJNI.listSEFDataTypes(fileName);
    }

    public static int[] listSEFDataTypes(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return null;
        }
        return SEFJNI.listSEFDataTypes(fileName);
    }

    public static int getMajorDataType(String fileName) {
        int[] dataTypes;
        if (fileName == null || fileName.length() <= 0) {
            Log.e(TAG, "Invalid file name: " + fileName);
            return -1;
        }
        if (SEFJNI.isSEFFile(fileName) == 0) {
            return -1;
        }
        try {
            File sefFile = new File(fileName);
            dataTypes = listSEFDataTypes(sefFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dataTypes == null) {
            Log.e(TAG, "No data type has been found : " + fileName);
            return -1;
        }
        for (int i = dataTypes.length - 1; i > -1; i--) {
            if (dataTypes[i] >= 2048 && dataTypes[i] <= 16384 && (dataTypes[i] & 15) == 0) {
                return dataTypes[i];
            }
        }
        Log.e(TAG, "No major data type has been found : " + fileName);
        return -1;
    }

    public static int copyAllData(File srcFile, File dstFile) throws IOException {
        String srcFileName = srcFile.getCanonicalPath();
        String dstFileName = dstFile.getCanonicalPath();
        if (srcFileName == null || srcFileName.length() <= 0) {
            Log.e(TAG, "Invalid src file name: " + srcFileName);
            return 0;
        }
        if (dstFileName == null || dstFileName.length() <= 0) {
            Log.e(TAG, "Invalid dst file name: " + dstFileName);
            return 0;
        }
        return SEFJNI.copyAllSEFData(srcFileName, dstFileName);
    }

    public static int copyAllData(ParcelFileDescriptor srcPfd, ParcelFileDescriptor dstPfd)
            throws IOException {
        return SEFJNI.copyAllSEFDataFileDescriptor(srcPfd, dstPfd);
    }

    public static int copyAllSEFData(File srcFile, File dstFile) throws IOException {
        String srcFileName = srcFile.getCanonicalPath();
        String dstFileName = dstFile.getCanonicalPath();
        if (srcFileName == null || srcFileName.length() <= 0) {
            Log.e(TAG, "Invalid src file name: " + srcFileName);
            return 0;
        }
        if (dstFileName == null || dstFileName.length() <= 0) {
            Log.e(TAG, "Invalid dst file name: " + dstFileName);
            return 0;
        }
        return SEFJNI.copyAllSEFData(srcFileName, dstFileName);
    }

    public static int copyAllSEFData(String srcFileName, String dstFileName) {
        if (srcFileName == null || srcFileName.length() <= 0) {
            Log.e(TAG, "Invalid src file name: " + srcFileName);
            return 0;
        }
        if (dstFileName == null || dstFileName.length() <= 0) {
            Log.e(TAG, "Invalid dst file name: " + dstFileName);
            return 0;
        }
        return SEFJNI.copyAllSEFData(srcFileName, dstFileName);
    }

    public static void convertImageToMP4(String srcPath, String targetPath) {
        File sefFile = new File(srcPath);
        if (sefFile.exists()) {
            int type = getMajorDataType(srcPath);
            switch (type) {
                case 2608:
                    MotionPhotoConverter.getInstance().convertToMp4(srcPath, targetPath);
                    break;
                default:
                    Log.e(TAG, "This type of file is not yet supported. type=" + type);
                    break;
            }
        }
    }

    public static boolean isMp4ConversionSupported(Context context, String srcPath) {
        int type = getMajorDataType(srcPath);
        switch (type) {
            case 2048:
                Log.i(
                        TAG,
                        "SoundNShot is not supported from P OS. So, MP4 Conversion for SoundNShot"
                            + " is removed from Q OS");
                break;
            case 2256:
                Log.i(
                        TAG,
                        "VirtualShot is not supported from R OS. So, MP4 Conversion for VirtualShot"
                            + " is removed from R OS");
                break;
            case DataType.PANORAMA_SHOT_INFO /* 2272 */:
                Log.i(
                        TAG,
                        "MotionPanoramaShot is not supported from R OS. So, MP4 Conversion for"
                            + " MotionPanoramaShot is removed from R OS");
                break;
            case DataType.WIDE_SELFIE_INFO /* 2416 */:
                Log.i(
                        TAG,
                        "MotionWideSelfie is not supported from R OS. So, MP4 Conversion for"
                            + " MotionWideSelfie is removed from R OS");
                break;
            case 2608:
                break;
            default:
                Log.e(TAG, "This type of file is not yet supported. type=" + type);
                break;
        }
        return false;
    }

    private static boolean isViewerInstalled(Context mContext, String name) {
        PackageManager pm = mContext.getPackageManager();
        try {
            pm.getPackageInfo(name.toString(), 1);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
