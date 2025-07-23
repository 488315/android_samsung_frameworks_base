package android.hardware.camera2.params;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class MandatoryStreamCombination {
    private static final long STREAM_USE_CASE_CROPPED_RAW = 6;
    private static final long STREAM_USE_CASE_PREVIEW = 1;
    private static final long STREAM_USE_CASE_PREVIEW_VIDEO_STILL = 4;
    private static final long STREAM_USE_CASE_RECORD = 3;
    private static final long STREAM_USE_CASE_STILL_CAPTURE = 2;
    private static final long STREAM_USE_CASE_VIDEO_CALL = 5;
    private static final String TAG = "MandatoryStreamCombination";
    private final String mDescription;
    private final boolean mIsReprocessable;
    private final ArrayList<MandatoryStreamInformation> mStreamsInformation;
    private static StreamCombinationTemplate[] sLegacyCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.MAXIMUM)}, "Simple preview, GPU video processing, or no-preview video recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "No-viewfinder still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "In-application video/image processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Standard still imaging"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "In-app processing plus still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Standard recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "Preview plus in-app processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Still capture plus in-app processing")};
    private static StreamCombinationTemplate[] sLimitedCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.RECORD)}, "High-resolution video recording with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "High-resolution in-app video processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "Two-input in-app video processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.RECORD), new StreamTemplate(256, SizeThreshold.RECORD)}, "High-resolution recording with video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD), new StreamTemplate(256, SizeThreshold.RECORD)}, "High-resolution in-app processing with video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Two-input in-app processing with still capture")};
    private static StreamCombinationTemplate[] sBurstCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.MAXIMUM)}, "Maximum-resolution GPU processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution in-app processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input in-app processsing")};
    private static StreamCombinationTemplate[] sFullCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.MAXIMUM), new StreamTemplate(34, SizeThreshold.MAXIMUM)}, "Maximum-resolution GPU processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.MAXIMUM), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution in-app processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.MAXIMUM), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input in-app processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "Video recording with maximum-size video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.VGA), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Standard video recording plus maximum-resolution in-app processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.VGA), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Preview plus two-input maximum-resolution in-app processing")};
    private static StreamCombinationTemplate[] sRawCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "No-preview DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Standard DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app processing plus DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Video recording with DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Preview with in-app processing and DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Two-input in-app processing plus DNG capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Still capture with simultaneous JPEG and DNG"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app processing with simultaneous JPEG and DNG")};
    private static StreamCombinationTemplate[] sLevel3Combinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.VGA), new StreamTemplate(35, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with dynamic selection of output format"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.VGA), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with dynamic selection of output format")};
    private static StreamCombinationTemplate[] sLimitedPrivateReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "No-viewfinder still image reprocessing", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL(Zero-Shutter-Lag) still imaging", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL still and in-app processing imaging", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL in-app processing with still capture", ReprocessType.PRIVATE)};
    private static StreamCombinationTemplate[] sLimitedYUVReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "No-viewfinder still image reprocessing", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL(Zero-Shutter-Lag) still imaging", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL still and in-app processing imaging", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL in-app processing with still capture", ReprocessType.YUV)};
    private static StreamCombinationTemplate[] sFullPrivateReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "High-resolution ZSL in-app video processing with regular preview", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution ZSL in-app processing with regular preview", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input ZSL in-app processing", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL still capture and in-app processing", ReprocessType.PRIVATE)};
    private static StreamCombinationTemplate[] sFullYUVReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Maximum-resolution multi-frame image fusion in-app processing with regular preview", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW)}, "Maximum-resolution multi-frame image fusion two-input in-app processing", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "High-resolution ZSL in-app video processing with regular preview", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "ZSL still capture and in-app processing", ReprocessType.YUV)};
    private static StreamCombinationTemplate[] sRAWPrivateReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and DNG capture", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and preview with DNG capture", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL two-input in-app processing and DNG capture", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL still capture and preview with DNG capture", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing with still capture and DNG capture", ReprocessType.PRIVATE)};
    private static StreamCombinationTemplate[] sRAWYUVReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and DNG capture", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and preview with DNG capture", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL two-input in-app processing and DNG capture", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL still capture and preview with DNG capture", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW), new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing with still capture and DNG capture", ReprocessType.YUV)};
    private static StreamCombinationTemplate[] sLevel3PrivateReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.VGA), new StreamTemplate(32, SizeThreshold.MAXIMUM), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with ZSL, RAW, and JPEG reprocessing output", ReprocessType.PRIVATE)};
    private static StreamCombinationTemplate[] sLevel3YUVReprocCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.VGA), new StreamTemplate(32, SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with ZSL and RAW", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.VGA), new StreamTemplate(32, SizeThreshold.MAXIMUM), new StreamTemplate(256, SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with ZSL, RAW, and JPEG reprocessing output", ReprocessType.YUV)};
    private static StreamCombinationTemplate[] sConcurrentStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p)}, "In-app video / image processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p)}, "preview / preview to GPU"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.s1440p)}, "No view-finder still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s720p), new StreamTemplate(35, SizeThreshold.s1440p)}, "Two-input in app video / image processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s720p), new StreamTemplate(34, SizeThreshold.s1440p)}, "High resolution video recording with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s720p), new StreamTemplate(35, SizeThreshold.s1440p)}, "In-app video / image processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s720p), new StreamTemplate(34, SizeThreshold.s1440p)}, "In-app video / image processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s720p), new StreamTemplate(256, SizeThreshold.s1440p)}, "Standard still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s720p), new StreamTemplate(256, SizeThreshold.s1440p)}, "Standard still image capture")};
    private static StreamCombinationTemplate[] sConcurrentDepthOnlyStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(ImageFormat.DEPTH16, SizeThreshold.VGA)}, "Depth capture for mesh based object rendering")};
    private static StreamCombinationTemplate[] sUltraHighResolutionStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Ultra high resolution YUV image capture with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Ultra high resolution RAW_SENSOR image capture with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Ultra high resolution JPEG image capture with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "No-viewfinder Ultra high resolution YUV image capture with image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "No-viewfinder Ultra high resolution RAW_SENSOR image capture with image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "No-viewfinder Ultra high resolution JPEG image capture with image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.RECORD)}, "Ultra high resolution YUV image capture with preview + app-based image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.RECORD)}, "Ultra high resolution RAW image capture with preview + app-based image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(34, SizeThreshold.RECORD)}, "Ultra high resolution JPEG image capture with preview + app-based image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "Ultra high resolution YUV image capture with preview + app-based image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "Ultra high resolution RAW image capture with preview + app-based image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.RECORD)}, "Ultra high resolution JPEG image capture with preview + app-based image analysis"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Ultra high resolution YUV image capture with preview + default", true), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Ultra high resolution RAW image capture with preview + default", true), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW), new StreamTemplate(35, SizeThreshold.MAXIMUM)}, "Ultra high resolution JPEG capture with preview + default", true)};
    private static StreamCombinationTemplate[] sUltraHighResolutionReprocStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW)}, "In-app RAW remosaic reprocessing with separate preview", ReprocessType.REMOSAIC), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW)}, "In-app RAW remosaic reprocessing with in-app image analysis", ReprocessType.REMOSAIC), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "In-app RAW -> JPEG reprocessing with separate preview", ReprocessType.REMOSAIC), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "In-app RAW -> YUV reprocessing with separate preview", ReprocessType.REMOSAIC), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "In-app RAW -> JPEG reprocessing with in-app image analysis", ReprocessType.REMOSAIC), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "In-app RAW -> YUV reprocessing with in-app image analysis", ReprocessType.REMOSAIC)};
    private static StreamCombinationTemplate[] sUltraHighResolutionYUVReprocStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> JPEG reprocessing with separate preview", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> JPEG reprocessing with in-app image analysis", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> YUV reprocessing with separate preview", ReprocessType.YUV), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> YUV reprocessing with in-app image analysis", ReprocessType.YUV)};
    private static StreamCombinationTemplate[] sUltraHighResolutionPRIVReprocStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Ultra high resolution PRIVATE -> JPEG reprocessing with separate preview", ReprocessType.PRIVATE), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.FULL_RES), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "Ultra high resolution PRIVATE -> JPEG reprocessing with in-app image analysis", ReprocessType.PRIVATE)};
    private static StreamCombinationTemplate[] s10BitOutputStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.MAXIMUM)}, "Simple preview, GPU video processing, or no-preview video recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(54, SizeThreshold.MAXIMUM)}, "In-application video/image processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Standard still imaging"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(54, SizeThreshold.MAXIMUM), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "Maximum-resolution in-app processing with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(54, SizeThreshold.MAXIMUM), new StreamTemplate(54, SizeThreshold.PREVIEW)}, "Maximum-resolution two-input in-app processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.RECORD), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "High-resolution video recording with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(54, SizeThreshold.RECORD), new StreamTemplate(34, SizeThreshold.RECORD), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "High-resolution recording with in-app snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.RECORD), new StreamTemplate(34, SizeThreshold.RECORD), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "High-resolution recording with video snapshot")};
    private static StreamCombinationTemplate[] sStreamUseCaseCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1)}, "Simple preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW, 1)}, "Simple in-application image processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.RECORD, 3)}, "Simple video recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.RECORD, 3)}, "Simple in-application video processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM, 2)}, "Simple JPEG still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.MAXIMUM, 2)}, "Simple YUV still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p, 4)}, "Multi-purpose stream for preview, video and still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p, 4)}, "Multi-purpose YUV stream for preview, video and still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p, 5)}, "Simple video call"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p, 5)}, "Simple YUV video call"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(256, SizeThreshold.MAXIMUM, 2)}, "Preview with JPEG still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.MAXIMUM, 2)}, "Preview with YUV still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(34, SizeThreshold.RECORD, 3)}, "Preview with video recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.RECORD, 3)}, "Preview with in-application video processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.PREVIEW, 1)}, "Preview with in-application image processing"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(34, SizeThreshold.s1440p, 5)}, "Preview with video call"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.s1440p, 5)}, "Preview with YUV video call"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p, 4), new StreamTemplate(256, SizeThreshold.MAXIMUM, 2)}, "Multi-purpose stream with JPEG still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p, 4), new StreamTemplate(35, SizeThreshold.MAXIMUM, 2)}, "Multi-purpose stream with YUV still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p, 4), new StreamTemplate(256, SizeThreshold.MAXIMUM, 2)}, "Multi-purpose YUV stream with JPEG still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p, 4), new StreamTemplate(35, SizeThreshold.MAXIMUM, 2)}, "Multi-purpose YUV stream with YUV still capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW, 2), new StreamTemplate(256, SizeThreshold.MAXIMUM, 2)}, "YUV and JPEG concurrent still image capture (for testing)"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(34, SizeThreshold.RECORD, 3), new StreamTemplate(256, SizeThreshold.RECORD, 2)}, "Preview, video record and JPEG video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.RECORD, 3), new StreamTemplate(256, SizeThreshold.RECORD, 2)}, "Preview, in-application video processing and JPEG video snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.PREVIEW, 1), new StreamTemplate(256, SizeThreshold.MAXIMUM, 2)}, "Preview, in-application image processing, and JPEG still image capture")};
    private static StreamCombinationTemplate[] sCroppedRawStreamUseCaseCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "Cropped RAW still image capture without preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "Cropped RAW still image capture with preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW, 1), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "In-app image processing with cropped RAW still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.MAXIMUM, 2), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "Preview with YUV and RAW still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.MAXIMUM, 2), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "In-app image processing with YUV and RAW still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(256, SizeThreshold.MAXIMUM, 2), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "Preview with JPEG and RAW still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW, 1), new StreamTemplate(256, SizeThreshold.MAXIMUM, 2), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "In-app image processing with JPEG and RAW still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(34, SizeThreshold.PREVIEW, 3), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "Preview with video recording and RAW snapshot"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.PREVIEW, 1), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "Preview with in-app image processing and RAW still image capture"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.PREVIEW, 1), new StreamTemplate(35, SizeThreshold.PREVIEW, 1), new StreamTemplate(32, SizeThreshold.MAXIMUM, 6)}, "Two input in-app processing and RAW still image capture")};
    private static StreamCombinationTemplate[] sPreviewStabilizedStreamCombinations = {new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p)}, "Stabilized preview, GPU video processing, or no-preview stabilized recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p)}, "Stabilized preview, GPU video processing, or no-preview stabilized recording"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(34, SizeThreshold.s1440p)}, "Standard JPEG still imaging with stabilized preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.MAXIMUM), new StreamTemplate(34, SizeThreshold.s1440p)}, "Standard YUV still imaging with stabilized preview"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.MAXIMUM), new StreamTemplate(35, SizeThreshold.s1440p)}, "Standard YUV still imaging with stabilized in-app image processing stream"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(256, SizeThreshold.MAXIMUM), new StreamTemplate(35, SizeThreshold.s1440p)}, "Standard JPEG still imaging with stabilized in-app image processing stream"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(34, SizeThreshold.s1440p), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p), new StreamTemplate(35, SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized"), new StreamCombinationTemplate(new StreamTemplate[]{new StreamTemplate(35, SizeThreshold.s1440p), new StreamTemplate(34, SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized")};

    private enum ReprocessType {
        NONE,
        PRIVATE,
        YUV,
        REMOSAIC
    }

    private enum SizeThreshold {
        VGA,
        PREVIEW,
        RECORD,
        MAXIMUM,
        s720p,
        s1440p,
        FULL_RES
    }

    public static final class MandatoryStreamInformation {
        private final ArrayList<Size> mAvailableSizes;
        private final int mFormat;
        private final boolean mIs10BitCapable;
        private final boolean mIsInput;
        private final boolean mIsMaximumSize;
        private final boolean mIsUltraHighResolution;
        private final long mStreamUseCase;

        public MandatoryStreamInformation(List<Size> list, int i, boolean z) {
            this(list, i, z, false, false);
        }

        public MandatoryStreamInformation(List<Size> list, int i, boolean z, boolean z2) {
            this(list, i, z, z2, false);
        }

        public MandatoryStreamInformation(List<Size> list, int i, boolean z, boolean z2, boolean z3) {
            this(list, i, z, z2, z3, false);
        }

        public MandatoryStreamInformation(List<Size> list, int i, boolean z, boolean z2, boolean z3, boolean z4) {
            this(list, i, z, z2, z3, z4, 0L);
        }

        public MandatoryStreamInformation(List<Size> list, int i, boolean z, boolean z2, boolean z3, boolean z4, long j) {
            ArrayList<Size> arrayList = new ArrayList<>();
            this.mAvailableSizes = arrayList;
            if (list.isEmpty()) {
                throw new IllegalArgumentException("No available sizes");
            }
            arrayList.addAll(list);
            this.mFormat = StreamConfigurationMap.checkArgumentFormat(i);
            this.mIsMaximumSize = z;
            this.mIsInput = z2;
            this.mIsUltraHighResolution = z3;
            this.mIs10BitCapable = z4;
            this.mStreamUseCase = j;
        }

        public boolean isInput() {
            return this.mIsInput;
        }

        public boolean isUltraHighResolution() {
            return this.mIsUltraHighResolution;
        }

        public boolean isMaximumSize() {
            return this.mIsMaximumSize;
        }

        public boolean is10BitCapable() {
            return this.mIs10BitCapable;
        }

        public List<Size> getAvailableSizes() {
            return Collections.unmodifiableList(this.mAvailableSizes);
        }

        public int getFormat() {
            if (this.mIs10BitCapable && this.mFormat == 54) {
                return 35;
            }
            return this.mFormat;
        }

        public int get10BitFormat() {
            if (!this.mIs10BitCapable) {
                throw new UnsupportedOperationException("10-bit output is not supported!");
            }
            return this.mFormat;
        }

        public long getStreamUseCase() {
            return this.mStreamUseCase;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj instanceof MandatoryStreamInformation) {
                MandatoryStreamInformation mandatoryStreamInformation = (MandatoryStreamInformation) obj;
                if (this.mFormat == mandatoryStreamInformation.mFormat && this.mIsInput == mandatoryStreamInformation.mIsInput && this.mIsUltraHighResolution == mandatoryStreamInformation.mIsUltraHighResolution && this.mStreamUseCase == mandatoryStreamInformation.mStreamUseCase && this.mAvailableSizes.size() == mandatoryStreamInformation.mAvailableSizes.size()) {
                    return this.mAvailableSizes.equals(mandatoryStreamInformation.mAvailableSizes);
                }
            }
            return false;
        }

        public int hashCode() {
            return HashCodeHelpers.hashCode(this.mFormat, Boolean.hashCode(this.mIsInput), Boolean.hashCode(this.mIsUltraHighResolution), this.mAvailableSizes.hashCode(), this.mStreamUseCase);
        }
    }

    public MandatoryStreamCombination(List<MandatoryStreamInformation> list, String str, boolean z) {
        ArrayList<MandatoryStreamInformation> arrayList = new ArrayList<>();
        this.mStreamsInformation = arrayList;
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Empty stream information");
        }
        arrayList.addAll(list);
        this.mDescription = str;
        this.mIsReprocessable = z;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public boolean isReprocessable() {
        return this.mIsReprocessable;
    }

    public List<MandatoryStreamInformation> getStreamsInformation() {
        return Collections.unmodifiableList(this.mStreamsInformation);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof MandatoryStreamCombination) {
            MandatoryStreamCombination mandatoryStreamCombination = (MandatoryStreamCombination) obj;
            if (this.mDescription == mandatoryStreamCombination.mDescription && this.mIsReprocessable == mandatoryStreamCombination.mIsReprocessable && this.mStreamsInformation.size() == mandatoryStreamCombination.mStreamsInformation.size()) {
                return this.mStreamsInformation.equals(mandatoryStreamCombination.mStreamsInformation);
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(Boolean.hashCode(this.mIsReprocessable), this.mDescription.hashCode(), this.mStreamsInformation.hashCode());
    }

    private static final class StreamTemplate {
        public int mFormat;
        public SizeThreshold mSizeThreshold;
        public long mStreamUseCase;

        public StreamTemplate(int i, SizeThreshold sizeThreshold) {
            this(i, sizeThreshold, 0L);
        }

        public StreamTemplate(int i, SizeThreshold sizeThreshold, long j) {
            this.mFormat = i;
            this.mSizeThreshold = sizeThreshold;
            this.mStreamUseCase = j;
        }
    }

    private static final class StreamCombinationTemplate {
        public String mDescription;
        public ReprocessType mReprocessType;
        public StreamTemplate[] mStreamTemplates;
        public boolean mSubstituteYUV;

        public StreamCombinationTemplate(StreamTemplate[] streamTemplateArr, String str) {
            this(streamTemplateArr, str, ReprocessType.NONE);
        }

        public StreamCombinationTemplate(StreamTemplate[] streamTemplateArr, String str, ReprocessType reprocessType) {
            this(streamTemplateArr, str, reprocessType, false);
        }

        public StreamCombinationTemplate(StreamTemplate[] streamTemplateArr, String str, boolean z) {
            this(streamTemplateArr, str, ReprocessType.NONE, z);
        }

        public StreamCombinationTemplate(StreamTemplate[] streamTemplateArr, String str, ReprocessType reprocessType, boolean z) {
            this.mStreamTemplates = streamTemplateArr;
            this.mReprocessType = reprocessType;
            this.mDescription = str;
            this.mSubstituteYUV = z;
        }
    }

    public static final class Builder {
        private final Size kPreviewSizeBound = new Size(1920, 1088);
        private int mCameraId;
        private List<Integer> mCapabilities;
        private Size mDisplaySize;
        private int mHwLevel;
        private boolean mIsCroppedRawSupported;
        private boolean mIsHiddenPhysicalCamera;
        private boolean mIsPreviewStabilizationSupported;
        private StreamConfigurationMap mStreamConfigMap;
        private StreamConfigurationMap mStreamConfigMapMaximumResolution;

        public Builder(int i, int i2, Size size, List<Integer> list, StreamConfigurationMap streamConfigurationMap, StreamConfigurationMap streamConfigurationMap2, boolean z, boolean z2) {
            this.mIsPreviewStabilizationSupported = false;
            this.mIsCroppedRawSupported = false;
            this.mCameraId = i;
            this.mDisplaySize = size;
            this.mCapabilities = list;
            this.mStreamConfigMap = streamConfigurationMap;
            this.mStreamConfigMapMaximumResolution = streamConfigurationMap2;
            this.mHwLevel = i2;
            this.mIsHiddenPhysicalCamera = CameraManager.isHiddenPhysicalCamera(Integer.toString(i));
            this.mIsPreviewStabilizationSupported = z;
            this.mIsCroppedRawSupported = z2;
        }

        private List<MandatoryStreamCombination> getAvailableMandatoryStreamCombinationsInternal(StreamCombinationTemplate[] streamCombinationTemplateArr, boolean z) {
            StreamCombinationTemplate[] streamCombinationTemplateArr2 = streamCombinationTemplateArr;
            HashMap<Pair<SizeThreshold, Integer>, List<Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            List<MandatoryStreamCombination> list = null;
            if (enumerateAvailableSizes == null) {
                Log.e(MandatoryStreamCombination.TAG, "Available size enumeration failed!");
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.ensureCapacity(streamCombinationTemplateArr2.length);
            int length = streamCombinationTemplateArr2.length;
            int i = 0;
            while (i < length) {
                StreamCombinationTemplate streamCombinationTemplate = streamCombinationTemplateArr2[i];
                ArrayList arrayList2 = new ArrayList();
                arrayList2.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
                StreamTemplate[] streamTemplateArr = streamCombinationTemplate.mStreamTemplates;
                int length2 = streamTemplateArr.length;
                int i2 = 0;
                while (i2 < length2) {
                    StreamTemplate streamTemplate = streamTemplateArr[i2];
                    List<MandatoryStreamCombination> list2 = list;
                    List<Size> list3 = enumerateAvailableSizes.get(new Pair(streamTemplate.mSizeThreshold, new Integer(streamTemplate.mFormat)));
                    if (z && streamTemplate.mFormat == 54 && !new HashSet(enumerateAvailableSizes.get(new Pair(streamTemplate.mSizeThreshold, new Integer(35)))).equals(new HashSet(list3))) {
                        Log.e(MandatoryStreamCombination.TAG, "The supported 10-bit YUV sizes are different from the supported 8-bit YUV sizes!");
                        return list2;
                    }
                    try {
                        arrayList2.add(new MandatoryStreamInformation(list3, streamTemplate.mFormat, streamTemplate.mSizeThreshold == SizeThreshold.MAXIMUM, false, false, z && streamTemplate.mFormat != 256));
                        i2++;
                        list = list2;
                    } catch (IllegalArgumentException unused) {
                        Log.e(MandatoryStreamCombination.TAG, "No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription);
                        return list2;
                    }
                }
                List<MandatoryStreamCombination> list4 = list;
                try {
                    arrayList.add(new MandatoryStreamCombination(arrayList2, streamCombinationTemplate.mDescription, false));
                    i++;
                    list = list4;
                    streamCombinationTemplateArr2 = streamCombinationTemplateArr;
                } catch (IllegalArgumentException unused2) {
                    Log.e(MandatoryStreamCombination.TAG, "No stream information for mandatory combination: " + streamCombinationTemplate.mDescription);
                    return list4;
                }
            }
            return Collections.unmodifiableList(arrayList);
        }

        public List<MandatoryStreamCombination> getAvailableMandatoryPreviewStabilizedStreamCombinations() {
            StreamCombinationTemplate[] streamCombinationTemplateArr = MandatoryStreamCombination.sPreviewStabilizedStreamCombinations;
            if (!this.mIsPreviewStabilizationSupported) {
                Log.v(MandatoryStreamCombination.TAG, "Device does not support preview stabilization");
                return null;
            }
            return getAvailableMandatoryStreamCombinationsInternal(streamCombinationTemplateArr, false);
        }

        public List<MandatoryStreamCombination> getAvailableMandatory10BitStreamCombinations() {
            StreamCombinationTemplate[] streamCombinationTemplateArr = MandatoryStreamCombination.s10BitOutputStreamCombinations;
            if (!is10BitOutputSupported()) {
                Log.v(MandatoryStreamCombination.TAG, "Device is not able to output 10-bit!");
                return null;
            }
            return getAvailableMandatoryStreamCombinationsInternal(streamCombinationTemplateArr, true);
        }

        public List<MandatoryStreamCombination> getAvailableMandatoryStreamUseCaseCombinations() {
            if (!isCapabilitySupported(19)) {
                return null;
            }
            HashMap<Pair<SizeThreshold, Integer>, List<Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            if (enumerateAvailableSizes == null) {
                Log.e(MandatoryStreamCombination.TAG, "Available size enumeration failed!");
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sStreamUseCaseCombinations));
            ArrayList arrayList2 = new ArrayList();
            int length = MandatoryStreamCombination.sStreamUseCaseCombinations.length;
            if (this.mIsCroppedRawSupported) {
                arrayList2.ensureCapacity(length + MandatoryStreamCombination.sCroppedRawStreamUseCaseCombinations.length);
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sCroppedRawStreamUseCaseCombinations));
            } else {
                arrayList2.ensureCapacity(length);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                StreamCombinationTemplate streamCombinationTemplate = (StreamCombinationTemplate) it.next();
                ArrayList arrayList3 = new ArrayList();
                arrayList3.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
                StreamTemplate[] streamTemplateArr = streamCombinationTemplate.mStreamTemplates;
                int length2 = streamTemplateArr.length;
                int i = 0;
                while (i < length2) {
                    StreamTemplate streamTemplate = streamTemplateArr[i];
                    try {
                        int i2 = i;
                        arrayList3.add(new MandatoryStreamInformation(enumerateAvailableSizes.get(new Pair(streamTemplate.mSizeThreshold, new Integer(streamTemplate.mFormat))), streamTemplate.mFormat, streamTemplate.mSizeThreshold == SizeThreshold.MAXIMUM, false, false, false, streamTemplate.mStreamUseCase));
                        i = i2 + 1;
                    } catch (IllegalArgumentException unused) {
                        Log.e(MandatoryStreamCombination.TAG, "No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription);
                        return null;
                    }
                }
                try {
                    arrayList2.add(new MandatoryStreamCombination(arrayList3, streamCombinationTemplate.mDescription, false));
                } catch (IllegalArgumentException unused2) {
                    Log.e(MandatoryStreamCombination.TAG, "No stream information for mandatory combination: " + streamCombinationTemplate.mDescription);
                    return null;
                }
            }
            return Collections.unmodifiableList(arrayList2);
        }

        public List<MandatoryStreamCombination> getAvailableMandatoryConcurrentStreamCombinations() {
            Builder builder;
            StreamCombinationTemplate[] streamCombinationTemplateArr;
            Size size;
            StreamCombinationTemplate[] streamCombinationTemplateArr2 = MandatoryStreamCombination.sConcurrentStreamCombinations;
            if (!isColorOutputSupported()) {
                Log.v(MandatoryStreamCombination.TAG, "Device is not backward compatible, depth streams are mandatory!");
                streamCombinationTemplateArr2 = MandatoryStreamCombination.sConcurrentDepthOnlyStreamCombinations;
            }
            Size size2 = new Size(640, 480);
            Size size3 = new Size(1280, 720);
            Size size4 = new Size(1920, 1440);
            ArrayList arrayList = new ArrayList();
            arrayList.ensureCapacity(streamCombinationTemplateArr2.length);
            int length = streamCombinationTemplateArr2.length;
            int i = 0;
            while (i < length) {
                StreamCombinationTemplate streamCombinationTemplate = streamCombinationTemplateArr2[i];
                ArrayList arrayList2 = new ArrayList();
                arrayList2.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
                StreamTemplate[] streamTemplateArr = streamCombinationTemplate.mStreamTemplates;
                int length2 = streamTemplateArr.length;
                int i2 = 0;
                while (i2 < length2) {
                    StreamTemplate streamTemplate = streamTemplateArr[i2];
                    ArrayList arrayList3 = new ArrayList();
                    int ordinal = streamTemplate.mSizeThreshold.ordinal();
                    if (ordinal == 0) {
                        builder = this;
                        streamCombinationTemplateArr = streamCombinationTemplateArr2;
                        size = size2;
                    } else if (ordinal != 5) {
                        builder = this;
                        streamCombinationTemplateArr = streamCombinationTemplateArr2;
                        size = size3;
                    } else {
                        builder = this;
                        streamCombinationTemplateArr = streamCombinationTemplateArr2;
                        size = size4;
                    }
                    Size size5 = size2;
                    arrayList3.add(getMinSize(size, getMaxSize(builder.mStreamConfigMap.getOutputSizes(streamTemplate.mFormat))));
                    try {
                        arrayList2.add(new MandatoryStreamInformation(arrayList3, streamTemplate.mFormat, false));
                        i2++;
                        streamCombinationTemplateArr2 = streamCombinationTemplateArr;
                        size2 = size5;
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription, e);
                    }
                }
                StreamCombinationTemplate[] streamCombinationTemplateArr3 = streamCombinationTemplateArr2;
                Size size6 = size2;
                try {
                    arrayList.add(new MandatoryStreamCombination(arrayList2, streamCombinationTemplate.mDescription, false));
                    i++;
                    streamCombinationTemplateArr2 = streamCombinationTemplateArr3;
                    size2 = size6;
                } catch (IllegalArgumentException e2) {
                    throw new RuntimeException("No stream information for mandatory combination: " + streamCombinationTemplate.mDescription, e2);
                }
            }
            return Collections.unmodifiableList(arrayList);
        }

        public List<MandatoryStreamCombination> getAvailableMandatoryMaximumResolutionStreamCombinations() {
            int i;
            if (!isColorOutputSupported()) {
                Log.v(MandatoryStreamCombination.TAG, "Device is not backward compatible!, no mandatory maximum res streams");
                return null;
            }
            ArrayList<StreamCombinationTemplate> arrayList = new ArrayList<>();
            arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sUltraHighResolutionStreamCombinations));
            ArrayList<MandatoryStreamCombination> arrayList2 = new ArrayList<>();
            boolean isRemosaicReprocessingSupported = isRemosaicReprocessingSupported();
            Size[] inputSizes = this.mStreamConfigMapMaximumResolution.getInputSizes(35);
            Size[] inputSizes2 = this.mStreamConfigMapMaximumResolution.getInputSizes(34);
            if (isRemosaicReprocessingSupported) {
                i = MandatoryStreamCombination.sUltraHighResolutionReprocStreamCombinations.length;
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sUltraHighResolutionReprocStreamCombinations));
            } else {
                i = 0;
            }
            if (inputSizes != null && inputSizes.length != 0) {
                i += MandatoryStreamCombination.sUltraHighResolutionYUVReprocStreamCombinations.length;
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sUltraHighResolutionYUVReprocStreamCombinations));
            }
            if (inputSizes2 != null && inputSizes2.length != 0) {
                i += MandatoryStreamCombination.sUltraHighResolutionPRIVReprocStreamCombinations.length;
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sUltraHighResolutionPRIVReprocStreamCombinations));
            }
            arrayList2.ensureCapacity(arrayList.size() + i);
            fillUHMandatoryStreamCombinations(arrayList2, arrayList);
            return Collections.unmodifiableList(arrayList2);
        }

        private MandatoryStreamCombination createUHSensorMandatoryStreamCombination(StreamCombinationTemplate streamCombinationTemplate, int i) {
            String str;
            List<Size> list;
            int i2;
            ArrayList arrayList = new ArrayList();
            arrayList.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
            boolean z = streamCombinationTemplate.mReprocessType != ReprocessType.NONE;
            int i3 = 32;
            if (z) {
                ArrayList arrayList2 = new ArrayList();
                if (streamCombinationTemplate.mReprocessType == ReprocessType.PRIVATE) {
                    i2 = 34;
                    arrayList2.add(getMaxSize(this.mStreamConfigMapMaximumResolution.getInputSizes(34)));
                } else if (streamCombinationTemplate.mReprocessType == ReprocessType.REMOSAIC) {
                    arrayList2.add(getMaxSize(this.mStreamConfigMapMaximumResolution.getInputSizes(32)));
                    i2 = 32;
                } else {
                    i2 = 35;
                    arrayList2.add(getMaxSize(this.mStreamConfigMapMaximumResolution.getInputSizes(35)));
                }
                arrayList.add(new MandatoryStreamInformation(arrayList2, i2, false, true, true));
                arrayList.add(new MandatoryStreamInformation(arrayList2, i2, false, false, true));
            }
            HashMap<Pair<SizeThreshold, Integer>, List<Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            if (enumerateAvailableSizes == null) {
                Log.e(MandatoryStreamCombination.TAG, "Available size enumeration failed");
                return null;
            }
            Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(32);
            ArrayList arrayList3 = new ArrayList();
            if (outputSizes != null) {
                arrayList3.ensureCapacity(outputSizes.length);
                arrayList3.addAll(Arrays.asList(outputSizes));
            }
            StreamTemplate[] streamTemplateArr = streamCombinationTemplate.mStreamTemplates;
            int length = streamTemplateArr.length;
            int i4 = 0;
            while (i4 < length) {
                StreamTemplate streamTemplate = streamTemplateArr[i4];
                List<Size> arrayList4 = new ArrayList<>();
                int i5 = streamTemplate.mFormat;
                boolean z2 = streamTemplate.mSizeThreshold == SizeThreshold.FULL_RES;
                StreamConfigurationMap streamConfigurationMap = z2 ? this.mStreamConfigMapMaximumResolution : this.mStreamConfigMap;
                boolean z3 = streamTemplate.mSizeThreshold == SizeThreshold.MAXIMUM;
                if (i != 0 && z3) {
                    i5 = i;
                }
                try {
                    if (z2) {
                        Size[] outputSizes2 = streamConfigurationMap.getOutputSizes(i5);
                        Size[] highResolutionOutputSizes = streamConfigurationMap.getHighResolutionOutputSizes(i5);
                        Size maxSizeOrNull = getMaxSizeOrNull(outputSizes2);
                        Size maxSizeOrNull2 = getMaxSizeOrNull(highResolutionOutputSizes);
                        Size size = maxSizeOrNull != null ? maxSizeOrNull : maxSizeOrNull2;
                        if (maxSizeOrNull != null && maxSizeOrNull2 != null) {
                            size = getMaxSize(maxSizeOrNull, maxSizeOrNull2);
                        }
                        arrayList4.add(size);
                    } else if (i5 != 32) {
                        arrayList4 = enumerateAvailableSizes.get(new Pair(streamTemplate.mSizeThreshold, new Integer(i5)));
                    } else {
                        list = arrayList3;
                        arrayList.add(new MandatoryStreamInformation(list, i5, z3, false, z2));
                        i4++;
                        i3 = 32;
                    }
                    arrayList.add(new MandatoryStreamInformation(list, i5, z3, false, z2));
                    i4++;
                    i3 = 32;
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription, e);
                }
                list = arrayList4;
            }
            if (i == i3) {
                str = "RAW_SENSOR";
            } else if (i == 256) {
                str = "JPEG";
            } else {
                str = "YUV";
            }
            try {
                return new MandatoryStreamCombination(arrayList, streamCombinationTemplate.mDescription + " " + str + " still-capture", z);
            } catch (IllegalArgumentException e2) {
                throw new RuntimeException("No stream information for mandatory combination: " + streamCombinationTemplate.mDescription, e2);
            }
        }

        private void fillUHMandatoryStreamCombinations(ArrayList<MandatoryStreamCombination> arrayList, ArrayList<StreamCombinationTemplate> arrayList2) {
            Iterator<StreamCombinationTemplate> it = arrayList2.iterator();
            while (it.hasNext()) {
                StreamCombinationTemplate next = it.next();
                arrayList.add(createUHSensorMandatoryStreamCombination(next, 0));
                if (next.mSubstituteYUV) {
                    arrayList.add(createUHSensorMandatoryStreamCombination(next, 32));
                    arrayList.add(createUHSensorMandatoryStreamCombination(next, 256));
                }
            }
        }

        public List<MandatoryStreamCombination> getAvailableMandatoryStreamCombinations() {
            if (!isColorOutputSupported()) {
                Log.v(MandatoryStreamCombination.TAG, "Device is not backward compatible!");
                return null;
            }
            if (this.mCameraId < 0 && !isExternalCamera()) {
                Log.i(MandatoryStreamCombination.TAG, "Invalid camera id");
                return null;
            }
            ArrayList<StreamCombinationTemplate> arrayList = new ArrayList<>();
            if (isHardwareLevelAtLeastLegacy()) {
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sLegacyCombinations));
            }
            if (isHardwareLevelAtLeastLimited() || isExternalCamera()) {
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sLimitedCombinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sLimitedPrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sLimitedYUVReprocCombinations));
                }
            }
            if (isCapabilitySupported(6)) {
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sBurstCombinations));
            }
            if (isHardwareLevelAtLeastFull()) {
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sFullCombinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sFullPrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sFullYUVReprocCombinations));
                }
            }
            if (isCapabilitySupported(3)) {
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sRawCombinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sRAWPrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sRAWYUVReprocCombinations));
                }
            }
            if (isHardwareLevelAtLeastLevel3()) {
                arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sLevel3Combinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sLevel3PrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(Arrays.asList(MandatoryStreamCombination.sLevel3YUVReprocCombinations));
                }
            }
            return generateAvailableCombinations(arrayList);
        }

        private List<MandatoryStreamCombination> generateAvailableCombinations(ArrayList<StreamCombinationTemplate> arrayList) {
            Size size;
            List<Size> list;
            List<Size> list2;
            int i;
            List<MandatoryStreamCombination> list3 = null;
            if (arrayList.isEmpty()) {
                Log.e(MandatoryStreamCombination.TAG, "No available stream templates!");
                return null;
            }
            HashMap<Pair<SizeThreshold, Integer>, List<Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            if (enumerateAvailableSizes == null) {
                Log.e(MandatoryStreamCombination.TAG, "Available size enumeration failed!");
                return null;
            }
            int i2 = 32;
            Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(32);
            ArrayList arrayList2 = new ArrayList();
            if (outputSizes != null) {
                arrayList2.ensureCapacity(outputSizes.length);
                arrayList2.addAll(Arrays.asList(outputSizes));
            }
            boolean z = false;
            Size size2 = new Size(0, 0);
            if (isPrivateReprocessingSupported()) {
                size2 = getMaxSize(this.mStreamConfigMap.getInputSizes(34));
            }
            Size size3 = new Size(0, 0);
            int i3 = 35;
            if (isYUVReprocessingSupported()) {
                size3 = getMaxSize(this.mStreamConfigMap.getInputSizes(35));
            }
            ArrayList arrayList3 = new ArrayList();
            arrayList3.ensureCapacity(arrayList.size());
            Iterator<StreamCombinationTemplate> it = arrayList.iterator();
            List<Size> list4 = arrayList2;
            while (it.hasNext()) {
                StreamCombinationTemplate next = it.next();
                ArrayList arrayList4 = new ArrayList();
                arrayList4.ensureCapacity(next.mStreamTemplates.length);
                List<MandatoryStreamCombination> list5 = list3;
                boolean z2 = next.mReprocessType != ReprocessType.NONE ? true : z;
                if (z2) {
                    ArrayList arrayList5 = new ArrayList();
                    if (next.mReprocessType == ReprocessType.PRIVATE) {
                        arrayList5.add(size2);
                        i = 34;
                    } else {
                        arrayList5.add(size3);
                        i = i3;
                    }
                    arrayList4.add(new MandatoryStreamInformation(arrayList5, i, true, true));
                    arrayList4.add(new MandatoryStreamInformation(arrayList5, i, true));
                }
                StreamTemplate[] streamTemplateArr = next.mStreamTemplates;
                int length = streamTemplateArr.length;
                int i4 = 0;
                List<Size> list6 = list4;
                while (i4 < length) {
                    StreamTemplate streamTemplate = streamTemplateArr[i4];
                    if (streamTemplate.mFormat == i2) {
                        size = size2;
                        list2 = list6;
                        list = list2;
                    } else {
                        size = size2;
                        list = list6;
                        list2 = enumerateAvailableSizes.get(new Pair(streamTemplate.mSizeThreshold, new Integer(streamTemplate.mFormat)));
                    }
                    try {
                        arrayList4.add(new MandatoryStreamInformation(list2, streamTemplate.mFormat, streamTemplate.mSizeThreshold == SizeThreshold.MAXIMUM));
                        i4++;
                        size2 = size;
                        list6 = list;
                        i2 = 32;
                    } catch (IllegalArgumentException unused) {
                        Log.e(MandatoryStreamCombination.TAG, "No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + next.mDescription);
                        return list5;
                    }
                }
                Size size4 = size2;
                List<Size> list7 = list6;
                try {
                    arrayList3.add(new MandatoryStreamCombination(arrayList4, next.mDescription, z2));
                    list3 = list5;
                    size2 = size4;
                    list4 = list7;
                    i2 = 32;
                    z = false;
                    i3 = 35;
                } catch (IllegalArgumentException unused2) {
                    Log.e(MandatoryStreamCombination.TAG, "No stream information for mandatory combination: " + next.mDescription);
                    return list5;
                }
            }
            return Collections.unmodifiableList(arrayList3);
        }

        private HashMap<Pair<SizeThreshold, Integer>, List<Size>> enumerateAvailableSizes() {
            Size maxCameraRecordingSize;
            int[] iArr = {32, 34, 35, 256, 54};
            new Size(0, 0);
            new Size(0, 0);
            Size size = new Size(640, 480);
            Size size2 = new Size(1280, 720);
            Size size3 = new Size(1920, 1440);
            if (isExternalCamera() || this.mIsHiddenPhysicalCamera) {
                maxCameraRecordingSize = getMaxCameraRecordingSize();
            } else {
                maxCameraRecordingSize = getMaxRecordingSize();
            }
            if (maxCameraRecordingSize == null) {
                Log.e(MandatoryStreamCombination.TAG, "Failed to find maximum recording size!");
                return null;
            }
            HashMap hashMap = new HashMap();
            for (int i = 0; i < 5; i++) {
                int i2 = iArr[i];
                Integer num = new Integer(i2);
                Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(i2);
                if (outputSizes == null) {
                    outputSizes = new Size[0];
                }
                hashMap.put(num, outputSizes);
            }
            List<Size> sizesWithinBound = getSizesWithinBound((Size[]) hashMap.get(new Integer(34)), this.kPreviewSizeBound);
            if (sizesWithinBound == null || sizesWithinBound.isEmpty()) {
                Log.e(MandatoryStreamCombination.TAG, "No preview sizes within preview size bound!");
                return null;
            }
            Size maxPreviewSize = getMaxPreviewSize(getAscendingOrderSizes(sizesWithinBound, false));
            HashMap<Pair<SizeThreshold, Integer>, List<Size>> hashMap2 = new HashMap<>();
            for (int i3 = 0; i3 < 5; i3++) {
                Integer num2 = new Integer(iArr[i3]);
                Size[] sizeArr = (Size[]) hashMap.get(num2);
                hashMap2.put(new Pair<>(SizeThreshold.VGA, num2), getSizesWithinBound(sizeArr, size));
                hashMap2.put(new Pair<>(SizeThreshold.PREVIEW, num2), getSizesWithinBound(sizeArr, maxPreviewSize));
                hashMap2.put(new Pair<>(SizeThreshold.RECORD, num2), getSizesWithinBound(sizeArr, maxCameraRecordingSize));
                hashMap2.put(new Pair<>(SizeThreshold.MAXIMUM, num2), Arrays.asList(sizeArr));
                hashMap2.put(new Pair<>(SizeThreshold.s720p, num2), getSizesWithinBound(sizeArr, size2));
                hashMap2.put(new Pair<>(SizeThreshold.s1440p, num2), getSizesWithinBound(sizeArr, size3));
            }
            return hashMap2;
        }

        private static List<Size> getSizesWithinBound(Size[] sizeArr, Size size) {
            ArrayList arrayList = new ArrayList();
            for (Size size2 : sizeArr) {
                if (size2.getWidth() <= size.getWidth() && size2.getHeight() <= size.getHeight()) {
                    arrayList.add(size2);
                }
            }
            return arrayList;
        }

        public static Size getMinSize(Size size, Size size2) {
            if (size == null || size2 == null) {
                throw new IllegalArgumentException("sizes was empty");
            }
            return size.getWidth() * size.getHeight() < size2.getHeight() * size2.getWidth() ? size : size2;
        }

        public static Size getMaxSize(Size... sizeArr) {
            if (sizeArr == null || sizeArr.length == 0) {
                throw new IllegalArgumentException("sizes was empty");
            }
            Size size = sizeArr[0];
            for (Size size2 : sizeArr) {
                if (size2.getWidth() * size2.getHeight() > size.getWidth() * size.getHeight()) {
                    size = size2;
                }
            }
            return size;
        }

        public static Size getMaxSizeOrNull(Size... sizeArr) {
            if (sizeArr == null || sizeArr.length == 0) {
                return null;
            }
            return getMaxSize(sizeArr);
        }

        private boolean isHardwareLevelAtLeast(int i) {
            int[] iArr = {2, 4, 0, 1, 3};
            if (i == this.mHwLevel) {
                return true;
            }
            for (int i2 = 0; i2 < 5; i2++) {
                int i3 = iArr[i2];
                if (i3 == i) {
                    return true;
                }
                if (i3 == this.mHwLevel) {
                    return false;
                }
            }
            return false;
        }

        private boolean isExternalCamera() {
            return this.mHwLevel == 4;
        }

        private boolean isHardwareLevelAtLeastLegacy() {
            return isHardwareLevelAtLeast(2);
        }

        private boolean isHardwareLevelAtLeastLimited() {
            return isHardwareLevelAtLeast(0);
        }

        private boolean isHardwareLevelAtLeastFull() {
            return isHardwareLevelAtLeast(1);
        }

        private boolean isHardwareLevelAtLeastLevel3() {
            return isHardwareLevelAtLeast(3);
        }

        private boolean isCapabilitySupported(int i) {
            return this.mCapabilities.contains(Integer.valueOf(i));
        }

        private boolean isColorOutputSupported() {
            return isCapabilitySupported(0);
        }

        private boolean is10BitOutputSupported() {
            return isCapabilitySupported(18);
        }

        private boolean isPrivateReprocessingSupported() {
            return isCapabilitySupported(4);
        }

        private boolean isYUVReprocessingSupported() {
            return isCapabilitySupported(7);
        }

        private boolean isRemosaicReprocessingSupported() {
            return isCapabilitySupported(17);
        }

        private Size getMaxRecordingSize() {
            int i = 8;
            if (!CamcorderProfile.hasProfile(this.mCameraId, 8)) {
                i = 6;
                if (!CamcorderProfile.hasProfile(this.mCameraId, 6)) {
                    i = 5;
                    if (!CamcorderProfile.hasProfile(this.mCameraId, 5)) {
                        i = 4;
                        if (!CamcorderProfile.hasProfile(this.mCameraId, 4)) {
                            i = 7;
                            if (!CamcorderProfile.hasProfile(this.mCameraId, 7)) {
                                i = 3;
                                if (!CamcorderProfile.hasProfile(this.mCameraId, 3)) {
                                    i = 2;
                                    if (!CamcorderProfile.hasProfile(this.mCameraId, 2)) {
                                        i = -1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (i < 0) {
                return null;
            }
            CamcorderProfile camcorderProfile = CamcorderProfile.get(this.mCameraId, i);
            return new Size(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        }

        private Size getMaxCameraRecordingSize() {
            Size size = new Size(1920, 1080);
            Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(MediaRecorder.class);
            ArrayList arrayList = new ArrayList();
            for (Size size2 : outputSizes) {
                if (size2.getWidth() <= size.getWidth() && size2.getHeight() <= size.getHeight()) {
                    arrayList.add(size2);
                }
            }
            for (Size size3 : getAscendingOrderSizes(arrayList, false)) {
                if (this.mStreamConfigMap.getOutputMinFrameDuration(MediaRecorder.class, size3) < 3.344481605351171E7d) {
                    Log.i(MandatoryStreamCombination.TAG, "External camera " + this.mCameraId + " has max video size:" + size3);
                    return size3;
                }
            }
            Log.w(MandatoryStreamCombination.TAG, "Camera " + this.mCameraId + " does not support any 30fps video output");
            return size;
        }

        private Size getMaxPreviewSize(List<Size> list) {
            if (list != null) {
                for (Size size : list) {
                    if (this.mDisplaySize.getWidth() >= size.getWidth() && this.mDisplaySize.getHeight() >= size.getHeight()) {
                        return size;
                    }
                }
            }
            Log.w(MandatoryStreamCombination.TAG, "Camera " + this.mCameraId + " maximum preview size search failed with display size " + this.mDisplaySize);
            return this.kPreviewSizeBound;
        }

        public static class SizeComparator implements Comparator<Size> {
            @Override // java.util.Comparator
            public int compare(Size size, Size size2) {
                return StreamConfigurationMap.compareSizes(size.getWidth(), size.getHeight(), size2.getWidth(), size2.getHeight());
            }
        }

        private static List<Size> getAscendingOrderSizes(List<Size> list, boolean z) {
            SizeComparator sizeComparator = new SizeComparator();
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(list);
            Collections.sort(arrayList, sizeComparator);
            if (!z) {
                Collections.reverse(arrayList);
            }
            return arrayList;
        }
    }
}
