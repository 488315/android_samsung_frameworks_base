package android.hardware.camera2;

import android.annotation.SystemApi;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.PublicKey;
import android.hardware.camera2.impl.SyntheticKey;
import android.hardware.camera2.params.BlackLevelPattern;
import android.hardware.camera2.params.Capability;
import android.hardware.camera2.params.ColorSpaceProfiles;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.params.DeviceStateSensorOrientationMap;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.hardware.camera2.params.MultiResolutionStreamConfigurationMap;
import android.hardware.camera2.params.RecommendedStreamConfiguration;
import android.hardware.camera2.params.RecommendedStreamConfigurationMap;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.SharedSessionConfiguration;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.TypeReference;
import android.util.Log;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.util.SizeF;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public final class CameraCharacteristics extends CameraMetadata<Key<?>> {

    @PublicKey
    public static final Key<int[]> AUTOMOTIVE_LENS_FACING;

    @PublicKey
    public static final Key<Integer> AUTOMOTIVE_LOCATION;
    private static final Map<Integer, Key<?>[]> AVAILABLE_SESSION_CHARACTERISTICS_KEYS_MAP;

    @PublicKey
    public static final Key<int[]> CONTROL_AE_AVAILABLE_PRIORITY_MODES;

    @PublicKey
    public static final Key<Boolean> CONTROL_AUTOFRAMING_AVAILABLE;
    public static final Key<HighSpeedVideoConfiguration[]> CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_SETTINGS_OVERRIDES;

    @PublicKey
    public static final Key<Range<Float>> CONTROL_LOW_LIGHT_BOOST_INFO_LUMINANCE_RANGE;

    @PublicKey
    public static final Key<Range<Float>> CONTROL_ZOOM_RATIO_RANGE;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfiguration[]> DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfiguration[]> DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfiguration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfiguration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<RecommendedStreamConfiguration[]> DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS;

    @PublicKey
    public static final Key<Boolean> DEPTH_DEPTH_IS_EXCLUSIVE;

    @PublicKey
    public static final Key<int[]> DISTORTION_CORRECTION_AVAILABLE_MODES;

    @PublicKey
    public static final Key<int[]> EDGE_AVAILABLE_EDGE_MODES;

    @PublicKey
    public static final Key<Boolean> FLASH_INFO_AVAILABLE;

    @PublicKey
    public static final Key<Integer> FLASH_INFO_STRENGTH_DEFAULT_LEVEL;

    @PublicKey
    public static final Key<Integer> FLASH_INFO_STRENGTH_MAXIMUM_LEVEL;

    @PublicKey
    public static final Key<Integer> FLASH_SINGLE_STRENGTH_DEFAULT_LEVEL;

    @PublicKey
    public static final Key<Integer> FLASH_SINGLE_STRENGTH_MAX_LEVEL;

    @PublicKey
    public static final Key<Integer> FLASH_TORCH_STRENGTH_DEFAULT_LEVEL;

    @PublicKey
    public static final Key<Integer> FLASH_TORCH_STRENGTH_MAX_LEVEL;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_STALL_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_STALL_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfiguration[]> HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfiguration[]> HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_ULTRA_HDR_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_ULTRA_HDR_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_ULTRA_HDR_STALL_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_ULTRA_HDR_STALL_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfiguration[]> HEIC_AVAILABLE_HEIC_ULTRA_HDR_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfiguration[]> HEIC_AVAILABLE_HEIC_ULTRA_HDR_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<int[]> HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES;
    public static final Key<Integer> INFO_DEVICE_ID;
    public static final Key<long[]> INFO_DEVICE_STATE_ORIENTATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<DeviceStateSensorOrientationMap> INFO_DEVICE_STATE_SENSOR_ORIENTATION_MAP;

    @PublicKey
    public static final Key<Integer> INFO_SESSION_CONFIGURATION_QUERY_VERSION;

    @PublicKey
    public static final Key<Integer> INFO_SUPPORTED_HARDWARE_LEVEL;

    @PublicKey
    public static final Key<String> INFO_VERSION;
    public static final Key<StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfiguration[]> JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfiguration[]> JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<Size[]> JPEG_AVAILABLE_THUMBNAIL_SIZES;
    public static final Key<int[]> LED_AVAILABLE_LEDS;

    @PublicKey
    public static final Key<float[]> LENS_DISTORTION;

    @PublicKey
    public static final Key<float[]> LENS_DISTORTION_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<Integer> LENS_FACING;

    @PublicKey
    public static final Key<float[]> LENS_INFO_AVAILABLE_APERTURES;

    @PublicKey
    public static final Key<float[]> LENS_INFO_AVAILABLE_FILTER_DENSITIES;

    @PublicKey
    public static final Key<float[]> LENS_INFO_AVAILABLE_FOCAL_LENGTHS;

    @PublicKey
    public static final Key<int[]> LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION;

    @PublicKey
    public static final Key<Integer> LENS_INFO_FOCUS_DISTANCE_CALIBRATION;

    @PublicKey
    public static final Key<Float> LENS_INFO_HYPERFOCAL_DISTANCE;

    @PublicKey
    public static final Key<Float> LENS_INFO_MINIMUM_FOCUS_DISTANCE;
    public static final Key<Size> LENS_INFO_SHADING_MAP_SIZE;

    @PublicKey
    public static final Key<float[]> LENS_INTRINSIC_CALIBRATION;

    @PublicKey
    public static final Key<float[]> LENS_INTRINSIC_CALIBRATION_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<Integer> LENS_POSE_REFERENCE;

    @PublicKey
    public static final Key<float[]> LENS_POSE_ROTATION;

    @PublicKey
    public static final Key<float[]> LENS_POSE_TRANSLATION;

    @PublicKey
    @Deprecated
    public static final Key<float[]> LENS_RADIAL_DISTORTION;
    public static final Key<byte[]> LOGICAL_MULTI_CAMERA_PHYSICAL_IDS;

    @PublicKey
    public static final Key<Integer> LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE;

    @PublicKey
    public static final Key<int[]> NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES;

    @Deprecated
    public static final Key<Byte> QUIRKS_USE_PARTIAL_RESULT;

    @PublicKey
    public static final Key<Integer> REPROCESS_MAX_CAPTURE_STALL;

    @PublicKey
    public static final Key<int[]> REQUEST_AVAILABLE_CAPABILITIES;
    public static final Key<int[]> REQUEST_AVAILABLE_CHARACTERISTICS_KEYS;

    @SyntheticKey
    @PublicKey
    public static final Key<ColorSpaceProfiles> REQUEST_AVAILABLE_COLOR_SPACE_PROFILES;
    public static final Key<long[]> REQUEST_AVAILABLE_COLOR_SPACE_PROFILES_MAP;

    @SyntheticKey
    @PublicKey
    public static final Key<DynamicRangeProfiles> REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES;
    public static final Key<long[]> REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES_MAP;
    public static final Key<int[]> REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS;
    public static final Key<int[]> REQUEST_AVAILABLE_REQUEST_KEYS;
    public static final Key<int[]> REQUEST_AVAILABLE_RESULT_KEYS;
    public static final Key<int[]> REQUEST_AVAILABLE_SESSION_KEYS;
    public static final Key<int[]> REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION;

    @PublicKey
    public static final Key<Integer> REQUEST_MAX_NUM_INPUT_STREAMS;

    @SyntheticKey
    @PublicKey
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_PROC;

    @SyntheticKey
    @PublicKey
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_PROC_STALLING;

    @SyntheticKey
    @PublicKey
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_RAW;
    public static final Key<int[]> REQUEST_MAX_NUM_OUTPUT_STREAMS;

    @PublicKey
    public static final Key<Integer> REQUEST_PARTIAL_RESULT_COUNT;

    @PublicKey
    public static final Key<Byte> REQUEST_PIPELINE_MAX_DEPTH;

    @PublicKey
    public static final Key<Long> REQUEST_RECOMMENDED_TEN_BIT_DYNAMIC_RANGE_PROFILE;

    @Deprecated
    public static final Key<int[]> SCALER_AVAILABLE_FORMATS;
    public static final Key<ReprocessFormatsMap> SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP;
    public static final Key<ReprocessFormatsMap> SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP_MAXIMUM_RESOLUTION;

    @Deprecated
    public static final Key<long[]> SCALER_AVAILABLE_JPEG_MIN_DURATIONS;

    @Deprecated
    public static final Key<Size[]> SCALER_AVAILABLE_JPEG_SIZES;

    @PublicKey
    public static final Key<Float> SCALER_AVAILABLE_MAX_DIGITAL_ZOOM;
    public static final Key<StreamConfigurationDuration[]> SCALER_AVAILABLE_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> SCALER_AVAILABLE_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION;

    @Deprecated
    public static final Key<long[]> SCALER_AVAILABLE_PROCESSED_MIN_DURATIONS;

    @Deprecated
    public static final Key<Size[]> SCALER_AVAILABLE_PROCESSED_SIZES;
    public static final Key<ReprocessFormatsMap> SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP;
    public static final Key<RecommendedStreamConfiguration[]> SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS;

    @PublicKey
    public static final Key<int[]> SCALER_AVAILABLE_ROTATE_AND_CROP_MODES;
    public static final Key<StreamConfigurationDuration[]> SCALER_AVAILABLE_STALL_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> SCALER_AVAILABLE_STALL_DURATIONS_MAXIMUM_RESOLUTION;
    public static final Key<StreamConfiguration[]> SCALER_AVAILABLE_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfiguration[]> SCALER_AVAILABLE_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<long[]> SCALER_AVAILABLE_STREAM_USE_CASES;

    @PublicKey
    public static final Key<Integer> SCALER_CROPPING_TYPE;

    @PublicKey
    public static final Key<Size> SCALER_DEFAULT_SECURE_IMAGE_SIZE;

    @SyntheticKey
    @PublicKey
    public static final Key<MandatoryStreamCombination[]> SCALER_MANDATORY_CONCURRENT_STREAM_COMBINATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<MandatoryStreamCombination[]> SCALER_MANDATORY_MAXIMUM_RESOLUTION_STREAM_COMBINATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<MandatoryStreamCombination[]> SCALER_MANDATORY_PREVIEW_STABILIZATION_OUTPUT_STREAM_COMBINATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<MandatoryStreamCombination[]> SCALER_MANDATORY_STREAM_COMBINATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<MandatoryStreamCombination[]> SCALER_MANDATORY_TEN_BIT_OUTPUT_STREAM_COMBINATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<MandatoryStreamCombination[]> SCALER_MANDATORY_USE_CASE_STREAM_COMBINATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<MultiResolutionStreamConfigurationMap> SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP;
    public static final Key<Boolean> SCALER_MULTI_RESOLUTION_STREAM_SUPPORTED;
    public static final Key<StreamConfiguration[]> SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS;

    @SyntheticKey
    @PublicKey
    public static final Key<StreamConfigurationMap> SCALER_STREAM_CONFIGURATION_MAP;

    @SyntheticKey
    @PublicKey
    public static final Key<StreamConfigurationMap> SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<int[]> SENSOR_AVAILABLE_TEST_PATTERN_MODES;

    @PublicKey
    public static final Key<BlackLevelPattern> SENSOR_BLACK_LEVEL_PATTERN;

    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM1;

    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM2;

    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_COLOR_TRANSFORM1;

    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_COLOR_TRANSFORM2;

    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_FORWARD_MATRIX1;

    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_FORWARD_MATRIX2;

    @PublicKey
    public static final Key<Rect> SENSOR_INFO_ACTIVE_ARRAY_SIZE;

    @PublicKey
    public static final Key<Rect> SENSOR_INFO_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<Size> SENSOR_INFO_BINNING_FACTOR;

    @PublicKey
    public static final Key<Integer> SENSOR_INFO_COLOR_FILTER_ARRANGEMENT;

    @PublicKey
    public static final Key<Range<Long>> SENSOR_INFO_EXPOSURE_TIME_RANGE;

    @PublicKey
    public static final Key<Boolean> SENSOR_INFO_LENS_SHADING_APPLIED;

    @PublicKey
    public static final Key<Long> SENSOR_INFO_MAX_FRAME_DURATION;

    @PublicKey
    public static final Key<SizeF> SENSOR_INFO_PHYSICAL_SIZE;

    @PublicKey
    public static final Key<Size> SENSOR_INFO_PIXEL_ARRAY_SIZE;

    @PublicKey
    public static final Key<Size> SENSOR_INFO_PIXEL_ARRAY_SIZE_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<Rect> SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE;

    @PublicKey
    public static final Key<Rect> SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION;

    @PublicKey
    public static final Key<Range<Integer>> SENSOR_INFO_SENSITIVITY_RANGE;

    @PublicKey
    public static final Key<Integer> SENSOR_INFO_TIMESTAMP_SOURCE;

    @PublicKey
    public static final Key<Integer> SENSOR_INFO_WHITE_LEVEL;

    @PublicKey
    public static final Key<Integer> SENSOR_MAX_ANALOG_SENSITIVITY;

    @PublicKey
    public static final Key<Rect[]> SENSOR_OPTICAL_BLACK_REGIONS;

    @PublicKey
    public static final Key<Integer> SENSOR_ORIENTATION;

    @PublicKey
    public static final Key<Integer> SENSOR_READOUT_TIMESTAMP;

    @PublicKey
    public static final Key<Integer> SENSOR_REFERENCE_ILLUMINANT1;

    @PublicKey
    public static final Key<Byte> SENSOR_REFERENCE_ILLUMINANT2;

    @PublicKey
    public static final Key<int[]> SHADING_AVAILABLE_MODES;
    public static final Key<Integer> SHARED_SESSION_COLOR_SPACE;

    @SyntheticKey
    @SystemApi
    public static final Key<SharedSessionConfiguration> SHARED_SESSION_CONFIGURATION;
    public static final Key<long[]> SHARED_SESSION_OUTPUT_CONFIGURATIONS;

    @PublicKey
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES;

    @PublicKey
    public static final Key<boolean[]> STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES;

    @PublicKey
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_LENS_SHADING_MAP_MODES;

    @PublicKey
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_OIS_DATA_MODES;

    @PublicKey
    public static final Key<Integer> STATISTICS_INFO_MAX_FACE_COUNT;

    @PublicKey
    public static final Key<Integer> SYNC_MAX_LATENCY;
    private static final String TAG = "CameraCharacteristics";

    @PublicKey
    public static final Key<int[]> TONEMAP_AVAILABLE_TONE_MAP_MODES;

    @PublicKey
    public static final Key<Integer> TONEMAP_MAX_CURVE_POINTS;
    private List<CaptureRequest.Key<?>> mAvailablePhysicalRequestKeys;
    private List<CaptureRequest.Key<?>> mAvailableRequestKeys;
    private List<CaptureResult.Key<?>> mAvailableResultKeys;
    private List<Key<?>> mAvailableSessionCharacteristicsKeys;
    private List<CaptureRequest.Key<?>> mAvailableSessionKeys;
    private CameraManager.DeviceStateListener mFoldStateListener;
    private boolean mFoldedDeviceState;
    private List<Key<?>> mKeys;
    private List<Key<?>> mKeysNeedingPermission;
    private final Object mLock = new Object();
    private final CameraMetadataNative mProperties;
    private ArrayList<RecommendedStreamConfigurationMap> mRecommendedConfigurations;

    @PublicKey
    public static final Key<int[]> COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES = new Key<>("android.colorCorrection.availableAberrationModes", int[].class);

    @PublicKey
    public static final Key<Range<Integer>> COLOR_CORRECTION_COLOR_TEMPERATURE_RANGE = new Key<>("android.colorCorrection.colorTemperatureRange", new TypeReference<Range<Integer>>() { // from class: android.hardware.camera2.CameraCharacteristics.2
    });

    @PublicKey
    public static final Key<int[]> COLOR_CORRECTION_AVAILABLE_MODES = new Key<>("android.colorCorrection.availableModes", int[].class);

    @PublicKey
    public static final Key<int[]> CONTROL_AE_AVAILABLE_ANTIBANDING_MODES = new Key<>("android.control.aeAvailableAntibandingModes", int[].class);

    @PublicKey
    public static final Key<int[]> CONTROL_AE_AVAILABLE_MODES = new Key<>("android.control.aeAvailableModes", int[].class);

    @PublicKey
    public static final Key<Range<Integer>[]> CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES = new Key<>("android.control.aeAvailableTargetFpsRanges", new TypeReference<Range<Integer>[]>() { // from class: android.hardware.camera2.CameraCharacteristics.3
    });

    @PublicKey
    public static final Key<Range<Integer>> CONTROL_AE_COMPENSATION_RANGE = new Key<>("android.control.aeCompensationRange", new TypeReference<Range<Integer>>() { // from class: android.hardware.camera2.CameraCharacteristics.4
    });

    @PublicKey
    public static final Key<Rational> CONTROL_AE_COMPENSATION_STEP = new Key<>("android.control.aeCompensationStep", Rational.class);

    @PublicKey
    public static final Key<int[]> CONTROL_AF_AVAILABLE_MODES = new Key<>("android.control.afAvailableModes", int[].class);

    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_EFFECTS = new Key<>("android.control.availableEffects", int[].class);

    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_SCENE_MODES = new Key<>("android.control.availableSceneModes", int[].class);

    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES = new Key<>("android.control.availableVideoStabilizationModes", int[].class);

    @PublicKey
    public static final Key<int[]> CONTROL_AWB_AVAILABLE_MODES = new Key<>("android.control.awbAvailableModes", int[].class);
    public static final Key<int[]> CONTROL_MAX_REGIONS = new Key<>("android.control.maxRegions", int[].class);

    @SyntheticKey
    @PublicKey
    public static final Key<Integer> CONTROL_MAX_REGIONS_AE = new Key<>("android.control.maxRegionsAe", Integer.TYPE);

    @SyntheticKey
    @PublicKey
    public static final Key<Integer> CONTROL_MAX_REGIONS_AWB = new Key<>("android.control.maxRegionsAwb", Integer.TYPE);

    @SyntheticKey
    @PublicKey
    public static final Key<Integer> CONTROL_MAX_REGIONS_AF = new Key<>("android.control.maxRegionsAf", Integer.TYPE);
    public static final Key<HighSpeedVideoConfiguration[]> CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS = new Key<>("android.control.availableHighSpeedVideoConfigurations", HighSpeedVideoConfiguration[].class);

    @PublicKey
    public static final Key<Boolean> CONTROL_AE_LOCK_AVAILABLE = new Key<>("android.control.aeLockAvailable", Boolean.TYPE);

    @PublicKey
    public static final Key<Boolean> CONTROL_AWB_LOCK_AVAILABLE = new Key<>("android.control.awbLockAvailable", Boolean.TYPE);

    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_MODES = new Key<>("android.control.availableModes", int[].class);

    @PublicKey
    public static final Key<Range<Integer>> CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE = new Key<>("android.control.postRawSensitivityBoostRange", new TypeReference<Range<Integer>>() { // from class: android.hardware.camera2.CameraCharacteristics.5
    });
    public static final Key<int[]> CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_MAX_SIZES = new Key<>("android.control.availableExtendedSceneModeMaxSizes", int[].class);
    public static final Key<float[]> CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_ZOOM_RATIO_RANGES = new Key<>("android.control.availableExtendedSceneModeZoomRatioRanges", float[].class);

    @SyntheticKey
    @PublicKey
    public static final Key<Capability[]> CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_CAPABILITIES = new Key<>("android.control.availableExtendedSceneModeCapabilities", Capability[].class);

    public static final class Key<T> {
        private final CameraMetadataNative.Key<T> mKey;

        public Key(String str, Class<T> cls, long j) {
            this.mKey = new CameraMetadataNative.Key<>(str, cls, j);
        }

        public Key(String str, String str2, Class<T> cls) {
            this.mKey = new CameraMetadataNative.Key<>(str, str2, cls);
        }

        public Key(String str, Class<T> cls) {
            this.mKey = new CameraMetadataNative.Key<>(str, cls);
        }

        public Key(String str, TypeReference<T> typeReference) {
            this.mKey = new CameraMetadataNative.Key<>(str, typeReference);
        }

        public String getName() {
            return this.mKey.getName();
        }

        public long getVendorId() {
            return this.mKey.getVendorId();
        }

        public final int hashCode() {
            return this.mKey.hashCode();
        }

        public final boolean equals(Object obj) {
            return (obj instanceof Key) && ((Key) obj).mKey.equals(this.mKey);
        }

        public String toString() {
            return String.format("CameraCharacteristics.Key(%s)", this.mKey.getName());
        }

        public CameraMetadataNative.Key<T> getNativeKey() {
            return this.mKey;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Key(CameraMetadataNative.Key<?> key) {
            this.mKey = key;
        }
    }

    public CameraCharacteristics(CameraMetadataNative cameraMetadataNative) {
        CameraMetadataNative move = CameraMetadataNative.move(cameraMetadataNative);
        this.mProperties = move;
        setNativeInstance(move);
    }

    public CameraMetadataNative getNativeCopy() {
        return new CameraMetadataNative(this.mProperties);
    }

    CameraManager.DeviceStateListener getDeviceStateListener() {
        if (this.mFoldStateListener == null) {
            this.mFoldStateListener = new CameraManager.DeviceStateListener() { // from class: android.hardware.camera2.CameraCharacteristics.1
                @Override // android.hardware.camera2.CameraManager.DeviceStateListener
                public final void onDeviceStateChanged(boolean z) {
                    synchronized (CameraCharacteristics.this.mLock) {
                        CameraCharacteristics.this.mFoldedDeviceState = z;
                    }
                }
            };
        }
        return this.mFoldStateListener;
    }

    private <T> T overrideProperty(Key<T> key) {
        if (!SENSOR_ORIENTATION.equals(key) || this.mFoldStateListener == null || this.mProperties.get(INFO_DEVICE_STATE_ORIENTATIONS) == null) {
            return null;
        }
        DeviceStateSensorOrientationMap deviceStateSensorOrientationMap = (DeviceStateSensorOrientationMap) this.mProperties.get(INFO_DEVICE_STATE_SENSOR_ORIENTATION_MAP);
        synchronized (this.mLock) {
            int sensorOrientation = deviceStateSensorOrientationMap.getSensorOrientation(this.mFoldedDeviceState ? 4L : 0L);
            T t = (T) Integer.valueOf(sensorOrientation);
            t.getClass();
            if (sensorOrientation >= 0) {
                return t;
            }
            Log.w(TAG, "No valid device state to orientation mapping! Using default!");
            return null;
        }
    }

    public <T> T get(Key<T> key) {
        T t = (T) overrideProperty(key);
        return t != null ? t : (T) this.mProperties.get(key);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.hardware.camera2.CameraMetadata
    public <T> T getProtected(Key<?> key) {
        return (T) this.mProperties.get(key);
    }

    @Override // android.hardware.camera2.CameraMetadata
    protected Class<Key<?>> getKeyClass() {
        return Key.class;
    }

    @Override // android.hardware.camera2.CameraMetadata
    public List<Key<?>> getKeys() {
        List<Key<?>> list = this.mKeys;
        if (list != null) {
            return list;
        }
        int[] iArr = (int[]) get(REQUEST_AVAILABLE_CHARACTERISTICS_KEYS);
        if (iArr == null) {
            throw new AssertionError("android.request.availableCharacteristicsKeys must be non-null in the characteristics");
        }
        List<Key<?>> unmodifiableList = Collections.unmodifiableList(getKeys(getClass(), getKeyClass(), this, iArr, true));
        this.mKeys = unmodifiableList;
        return unmodifiableList;
    }

    public List<Key<?>> getKeysNeedingPermission() {
        if (this.mKeysNeedingPermission == null) {
            int[] iArr = (int[]) get(REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION);
            if (iArr == null) {
                List<Key<?>> unmodifiableList = Collections.unmodifiableList(new ArrayList());
                this.mKeysNeedingPermission = unmodifiableList;
                return unmodifiableList;
            }
            this.mKeysNeedingPermission = getAvailableKeyList(CameraCharacteristics.class, Key.class, iArr, false);
        }
        return this.mKeysNeedingPermission;
    }

    public RecommendedStreamConfigurationMap getRecommendedStreamConfigurationMap(int i) {
        if ((i >= 0 && i <= 8) || (i >= 24 && i < 32)) {
            if (this.mRecommendedConfigurations == null) {
                ArrayList<RecommendedStreamConfigurationMap> recommendedStreamConfigurations = this.mProperties.getRecommendedStreamConfigurations();
                this.mRecommendedConfigurations = recommendedStreamConfigurations;
                if (recommendedStreamConfigurations == null) {
                    return null;
                }
            }
            return this.mRecommendedConfigurations.get(i);
        }
        throw new IllegalArgumentException(String.format("Invalid use case: %d", Integer.valueOf(i)));
    }

    public List<CaptureRequest.Key<?>> getAvailableSessionKeys() {
        if (this.mAvailableSessionKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_SESSION_KEYS);
            if (iArr == null) {
                return null;
            }
            this.mAvailableSessionKeys = getAvailableKeyList(CaptureRequest.class, CaptureRequest.Key.class, iArr, false);
        }
        return this.mAvailableSessionKeys;
    }

    public List<Key<?>> getAvailableSessionCharacteristicsKeys() {
        List<Key<?>> list = this.mAvailableSessionCharacteristicsKeys;
        if (list != null) {
            return list;
        }
        final Integer num = (Integer) get(INFO_SESSION_CONFIGURATION_QUERY_VERSION);
        if (num == null) {
            List<Key<?>> list2 = Collections.EMPTY_LIST;
            this.mAvailableSessionCharacteristicsKeys = list2;
            return list2;
        }
        List<Key<?>> list3 = (List) AVAILABLE_SESSION_CHARACTERISTICS_KEYS_MAP.entrySet().stream().filter(new Predicate() { // from class: android.hardware.camera2.CameraCharacteristics$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CameraCharacteristics.lambda$getAvailableSessionCharacteristicsKeys$0(num, (Map.Entry) obj);
            }
        }).map(new Function() { // from class: android.hardware.camera2.CameraCharacteristics$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (CameraCharacteristics.Key[]) ((Map.Entry) obj).getValue();
            }
        }).flatMap(new Function() { // from class: android.hardware.camera2.CameraCharacteristics$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Arrays.stream((CameraCharacteristics.Key[]) obj);
            }
        }).collect(Collectors.toUnmodifiableList());
        this.mAvailableSessionCharacteristicsKeys = list3;
        return list3;
    }

    static /* synthetic */ boolean lambda$getAvailableSessionCharacteristicsKeys$0(Integer num, Map.Entry entry) {
        return ((Integer) entry.getKey()).intValue() <= num.intValue();
    }

    public List<CaptureRequest.Key<?>> getAvailablePhysicalCameraRequestKeys() {
        if (this.mAvailablePhysicalRequestKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS);
            if (iArr == null) {
                return null;
            }
            this.mAvailablePhysicalRequestKeys = getAvailableKeyList(CaptureRequest.class, CaptureRequest.Key.class, iArr, false);
        }
        return this.mAvailablePhysicalRequestKeys;
    }

    public List<CaptureRequest.Key<?>> getAvailableCaptureRequestKeys() {
        if (this.mAvailableRequestKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_REQUEST_KEYS);
            if (iArr == null) {
                throw new AssertionError("android.request.availableRequestKeys must be non-null in the characteristics");
            }
            this.mAvailableRequestKeys = getAvailableKeyList(CaptureRequest.class, CaptureRequest.Key.class, iArr, true);
        }
        return this.mAvailableRequestKeys;
    }

    public List<CaptureResult.Key<?>> getAvailableCaptureResultKeys() {
        if (this.mAvailableResultKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_RESULT_KEYS);
            if (iArr != null) {
                this.mAvailableResultKeys = getAvailableKeyList(CaptureResult.class, CaptureResult.Key.class, iArr, true);
            } else {
                throw new AssertionError("android.request.availableResultKeys must be non-null in the characteristics");
            }
        }
        return this.mAvailableResultKeys;
    }

    public <TKey> List<TKey> semGetAvailableSamsungKeyList(Class<?> cls, Class<TKey> cls2, Key<int[]> key) {
        int[] iArr = (int[]) get(key);
        if (iArr == null) {
            return null;
        }
        return getAvailableKeyList(cls, cls2, iArr, false);
    }

    <TKey> List<TKey> getAvailableKeyList(Class<?> cls, Class<TKey> cls2, int[] iArr, boolean z) {
        if (cls.equals(CameraMetadata.class)) {
            throw new AssertionError("metadataClass must be a strict subclass of CameraMetadata");
        }
        if (!CameraMetadata.class.isAssignableFrom(cls)) {
            throw new AssertionError("metadataClass must be a subclass of CameraMetadata");
        }
        return Collections.unmodifiableList(getKeys(cls, cls2, null, iArr, z));
    }

    public Set<String> getPhysicalCameraIds() {
        return this.mProperties.getPhysicalCameraIds();
    }

    static {
        Key<Range<Float>> key = new Key<>("android.control.zoomRatioRange", new TypeReference<Range<Float>>() { // from class: android.hardware.camera2.CameraCharacteristics.6
        });
        CONTROL_ZOOM_RATIO_RANGE = key;
        CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.control.availableHighSpeedVideoConfigurationsMaximumResolution", HighSpeedVideoConfiguration[].class);
        CONTROL_AVAILABLE_SETTINGS_OVERRIDES = new Key<>("android.control.availableSettingsOverrides", int[].class);
        CONTROL_AUTOFRAMING_AVAILABLE = new Key<>("android.control.autoframingAvailable", Boolean.TYPE);
        CONTROL_LOW_LIGHT_BOOST_INFO_LUMINANCE_RANGE = new Key<>("android.control.lowLightBoostInfoLuminanceRange", new TypeReference<Range<Float>>() { // from class: android.hardware.camera2.CameraCharacteristics.7
        });
        CONTROL_AE_AVAILABLE_PRIORITY_MODES = new Key<>("android.control.aeAvailablePriorityModes", int[].class);
        EDGE_AVAILABLE_EDGE_MODES = new Key<>("android.edge.availableEdgeModes", int[].class);
        FLASH_INFO_AVAILABLE = new Key<>("android.flash.info.available", Boolean.TYPE);
        FLASH_INFO_STRENGTH_MAXIMUM_LEVEL = new Key<>("android.flash.info.strengthMaximumLevel", Integer.TYPE);
        FLASH_INFO_STRENGTH_DEFAULT_LEVEL = new Key<>("android.flash.info.strengthDefaultLevel", Integer.TYPE);
        FLASH_SINGLE_STRENGTH_MAX_LEVEL = new Key<>("android.flash.singleStrengthMaxLevel", Integer.TYPE);
        FLASH_SINGLE_STRENGTH_DEFAULT_LEVEL = new Key<>("android.flash.singleStrengthDefaultLevel", Integer.TYPE);
        FLASH_TORCH_STRENGTH_MAX_LEVEL = new Key<>("android.flash.torchStrengthMaxLevel", Integer.TYPE);
        FLASH_TORCH_STRENGTH_DEFAULT_LEVEL = new Key<>("android.flash.torchStrengthDefaultLevel", Integer.TYPE);
        HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES = new Key<>("android.hotPixel.availableHotPixelModes", int[].class);
        JPEG_AVAILABLE_THUMBNAIL_SIZES = new Key<>("android.jpeg.availableThumbnailSizes", Size[].class);
        LENS_INFO_AVAILABLE_APERTURES = new Key<>("android.lens.info.availableApertures", float[].class);
        LENS_INFO_AVAILABLE_FILTER_DENSITIES = new Key<>("android.lens.info.availableFilterDensities", float[].class);
        LENS_INFO_AVAILABLE_FOCAL_LENGTHS = new Key<>("android.lens.info.availableFocalLengths", float[].class);
        LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION = new Key<>("android.lens.info.availableOpticalStabilization", int[].class);
        LENS_INFO_HYPERFOCAL_DISTANCE = new Key<>("android.lens.info.hyperfocalDistance", Float.TYPE);
        LENS_INFO_MINIMUM_FOCUS_DISTANCE = new Key<>("android.lens.info.minimumFocusDistance", Float.TYPE);
        LENS_INFO_SHADING_MAP_SIZE = new Key<>("android.lens.info.shadingMapSize", Size.class);
        LENS_INFO_FOCUS_DISTANCE_CALIBRATION = new Key<>("android.lens.info.focusDistanceCalibration", Integer.TYPE);
        LENS_FACING = new Key<>("android.lens.facing", Integer.TYPE);
        LENS_POSE_ROTATION = new Key<>("android.lens.poseRotation", float[].class);
        LENS_POSE_TRANSLATION = new Key<>("android.lens.poseTranslation", float[].class);
        LENS_INTRINSIC_CALIBRATION = new Key<>("android.lens.intrinsicCalibration", float[].class);
        LENS_RADIAL_DISTORTION = new Key<>("android.lens.radialDistortion", float[].class);
        LENS_POSE_REFERENCE = new Key<>("android.lens.poseReference", Integer.TYPE);
        LENS_DISTORTION = new Key<>("android.lens.distortion", float[].class);
        LENS_DISTORTION_MAXIMUM_RESOLUTION = new Key<>("android.lens.distortionMaximumResolution", float[].class);
        LENS_INTRINSIC_CALIBRATION_MAXIMUM_RESOLUTION = new Key<>("android.lens.intrinsicCalibrationMaximumResolution", float[].class);
        NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES = new Key<>("android.noiseReduction.availableNoiseReductionModes", int[].class);
        QUIRKS_USE_PARTIAL_RESULT = new Key<>("android.quirks.usePartialResult", Byte.TYPE);
        REQUEST_MAX_NUM_OUTPUT_STREAMS = new Key<>("android.request.maxNumOutputStreams", int[].class);
        REQUEST_MAX_NUM_OUTPUT_RAW = new Key<>("android.request.maxNumOutputRaw", Integer.TYPE);
        REQUEST_MAX_NUM_OUTPUT_PROC = new Key<>("android.request.maxNumOutputProc", Integer.TYPE);
        REQUEST_MAX_NUM_OUTPUT_PROC_STALLING = new Key<>("android.request.maxNumOutputProcStalling", Integer.TYPE);
        REQUEST_MAX_NUM_INPUT_STREAMS = new Key<>("android.request.maxNumInputStreams", Integer.TYPE);
        REQUEST_PIPELINE_MAX_DEPTH = new Key<>("android.request.pipelineMaxDepth", Byte.TYPE);
        REQUEST_PARTIAL_RESULT_COUNT = new Key<>("android.request.partialResultCount", Integer.TYPE);
        REQUEST_AVAILABLE_CAPABILITIES = new Key<>("android.request.availableCapabilities", int[].class);
        REQUEST_AVAILABLE_REQUEST_KEYS = new Key<>("android.request.availableRequestKeys", int[].class);
        REQUEST_AVAILABLE_RESULT_KEYS = new Key<>("android.request.availableResultKeys", int[].class);
        REQUEST_AVAILABLE_CHARACTERISTICS_KEYS = new Key<>("android.request.availableCharacteristicsKeys", int[].class);
        REQUEST_AVAILABLE_SESSION_KEYS = new Key<>("android.request.availableSessionKeys", int[].class);
        REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS = new Key<>("android.request.availablePhysicalCameraRequestKeys", int[].class);
        REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION = new Key<>("android.request.characteristicKeysNeedingPermission", int[].class);
        REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES = new Key<>("android.request.availableDynamicRangeProfiles", DynamicRangeProfiles.class);
        REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES_MAP = new Key<>("android.request.availableDynamicRangeProfilesMap", long[].class);
        REQUEST_RECOMMENDED_TEN_BIT_DYNAMIC_RANGE_PROFILE = new Key<>("android.request.recommendedTenBitDynamicRangeProfile", Long.TYPE);
        REQUEST_AVAILABLE_COLOR_SPACE_PROFILES = new Key<>("android.request.availableColorSpaceProfiles", ColorSpaceProfiles.class);
        REQUEST_AVAILABLE_COLOR_SPACE_PROFILES_MAP = new Key<>("android.request.availableColorSpaceProfilesMap", long[].class);
        SCALER_AVAILABLE_FORMATS = new Key<>("android.scaler.availableFormats", int[].class);
        SCALER_AVAILABLE_JPEG_MIN_DURATIONS = new Key<>("android.scaler.availableJpegMinDurations", long[].class);
        SCALER_AVAILABLE_JPEG_SIZES = new Key<>("android.scaler.availableJpegSizes", Size[].class);
        Key<Float> key2 = new Key<>("android.scaler.availableMaxDigitalZoom", (Class<Float>) Float.TYPE);
        SCALER_AVAILABLE_MAX_DIGITAL_ZOOM = key2;
        SCALER_AVAILABLE_PROCESSED_MIN_DURATIONS = new Key<>("android.scaler.availableProcessedMinDurations", long[].class);
        SCALER_AVAILABLE_PROCESSED_SIZES = new Key<>("android.scaler.availableProcessedSizes", Size[].class);
        SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP = new Key<>("android.scaler.availableInputOutputFormatsMap", ReprocessFormatsMap.class);
        SCALER_AVAILABLE_STREAM_CONFIGURATIONS = new Key<>("android.scaler.availableStreamConfigurations", StreamConfiguration[].class);
        SCALER_AVAILABLE_MIN_FRAME_DURATIONS = new Key<>("android.scaler.availableMinFrameDurations", StreamConfigurationDuration[].class);
        SCALER_AVAILABLE_STALL_DURATIONS = new Key<>("android.scaler.availableStallDurations", StreamConfigurationDuration[].class);
        SCALER_STREAM_CONFIGURATION_MAP = new Key<>("android.scaler.streamConfigurationMap", StreamConfigurationMap.class);
        SCALER_CROPPING_TYPE = new Key<>("android.scaler.croppingType", Integer.TYPE);
        SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS = new Key<>("android.scaler.availableRecommendedStreamConfigurations", RecommendedStreamConfiguration[].class);
        SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP = new Key<>("android.scaler.availableRecommendedInputOutputFormatsMap", ReprocessFormatsMap.class);
        SCALER_MANDATORY_STREAM_COMBINATIONS = new Key<>("android.scaler.mandatoryStreamCombinations", MandatoryStreamCombination[].class);
        SCALER_MANDATORY_CONCURRENT_STREAM_COMBINATIONS = new Key<>("android.scaler.mandatoryConcurrentStreamCombinations", MandatoryStreamCombination[].class);
        SCALER_AVAILABLE_ROTATE_AND_CROP_MODES = new Key<>("android.scaler.availableRotateAndCropModes", int[].class);
        SCALER_DEFAULT_SECURE_IMAGE_SIZE = new Key<>("android.scaler.defaultSecureImageSize", Size.class);
        SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS = new Key<>("android.scaler.physicalCameraMultiResolutionStreamConfigurations", StreamConfiguration[].class);
        SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP = new Key<>("android.scaler.multiResolutionStreamConfigurationMap", MultiResolutionStreamConfigurationMap.class);
        SCALER_AVAILABLE_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.scaler.availableStreamConfigurationsMaximumResolution", StreamConfiguration[].class);
        SCALER_AVAILABLE_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.scaler.availableMinFrameDurationsMaximumResolution", StreamConfigurationDuration[].class);
        SCALER_AVAILABLE_STALL_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.scaler.availableStallDurationsMaximumResolution", StreamConfigurationDuration[].class);
        SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION = new Key<>("android.scaler.streamConfigurationMapMaximumResolution", StreamConfigurationMap.class);
        SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP_MAXIMUM_RESOLUTION = new Key<>("android.scaler.availableInputOutputFormatsMapMaximumResolution", ReprocessFormatsMap.class);
        SCALER_MANDATORY_MAXIMUM_RESOLUTION_STREAM_COMBINATIONS = new Key<>("android.scaler.mandatoryMaximumResolutionStreamCombinations", MandatoryStreamCombination[].class);
        SCALER_MANDATORY_TEN_BIT_OUTPUT_STREAM_COMBINATIONS = new Key<>("android.scaler.mandatoryTenBitOutputStreamCombinations", MandatoryStreamCombination[].class);
        SCALER_MANDATORY_PREVIEW_STABILIZATION_OUTPUT_STREAM_COMBINATIONS = new Key<>("android.scaler.mandatoryPreviewStabilizationOutputStreamCombinations", MandatoryStreamCombination[].class);
        SCALER_MULTI_RESOLUTION_STREAM_SUPPORTED = new Key<>("android.scaler.multiResolutionStreamSupported", Boolean.TYPE);
        SCALER_AVAILABLE_STREAM_USE_CASES = new Key<>("android.scaler.availableStreamUseCases", long[].class);
        SCALER_MANDATORY_USE_CASE_STREAM_COMBINATIONS = new Key<>("android.scaler.mandatoryUseCaseStreamCombinations", MandatoryStreamCombination[].class);
        SENSOR_INFO_ACTIVE_ARRAY_SIZE = new Key<>("android.sensor.info.activeArraySize", Rect.class);
        SENSOR_INFO_SENSITIVITY_RANGE = new Key<>("android.sensor.info.sensitivityRange", new TypeReference<Range<Integer>>() { // from class: android.hardware.camera2.CameraCharacteristics.8
        });
        SENSOR_INFO_COLOR_FILTER_ARRANGEMENT = new Key<>("android.sensor.info.colorFilterArrangement", Integer.TYPE);
        SENSOR_INFO_EXPOSURE_TIME_RANGE = new Key<>("android.sensor.info.exposureTimeRange", new TypeReference<Range<Long>>() { // from class: android.hardware.camera2.CameraCharacteristics.9
        });
        SENSOR_INFO_MAX_FRAME_DURATION = new Key<>("android.sensor.info.maxFrameDuration", Long.TYPE);
        SENSOR_INFO_PHYSICAL_SIZE = new Key<>("android.sensor.info.physicalSize", SizeF.class);
        SENSOR_INFO_PIXEL_ARRAY_SIZE = new Key<>("android.sensor.info.pixelArraySize", Size.class);
        SENSOR_INFO_WHITE_LEVEL = new Key<>("android.sensor.info.whiteLevel", Integer.TYPE);
        SENSOR_INFO_TIMESTAMP_SOURCE = new Key<>("android.sensor.info.timestampSource", Integer.TYPE);
        SENSOR_INFO_LENS_SHADING_APPLIED = new Key<>("android.sensor.info.lensShadingApplied", Boolean.TYPE);
        SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE = new Key<>("android.sensor.info.preCorrectionActiveArraySize", Rect.class);
        SENSOR_INFO_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION = new Key<>("android.sensor.info.activeArraySizeMaximumResolution", Rect.class);
        SENSOR_INFO_PIXEL_ARRAY_SIZE_MAXIMUM_RESOLUTION = new Key<>("android.sensor.info.pixelArraySizeMaximumResolution", Size.class);
        SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION = new Key<>("android.sensor.info.preCorrectionActiveArraySizeMaximumResolution", Rect.class);
        SENSOR_INFO_BINNING_FACTOR = new Key<>("android.sensor.info.binningFactor", Size.class);
        SENSOR_REFERENCE_ILLUMINANT1 = new Key<>("android.sensor.referenceIlluminant1", Integer.TYPE);
        SENSOR_REFERENCE_ILLUMINANT2 = new Key<>("android.sensor.referenceIlluminant2", Byte.TYPE);
        SENSOR_CALIBRATION_TRANSFORM1 = new Key<>("android.sensor.calibrationTransform1", ColorSpaceTransform.class);
        SENSOR_CALIBRATION_TRANSFORM2 = new Key<>("android.sensor.calibrationTransform2", ColorSpaceTransform.class);
        SENSOR_COLOR_TRANSFORM1 = new Key<>("android.sensor.colorTransform1", ColorSpaceTransform.class);
        SENSOR_COLOR_TRANSFORM2 = new Key<>("android.sensor.colorTransform2", ColorSpaceTransform.class);
        SENSOR_FORWARD_MATRIX1 = new Key<>("android.sensor.forwardMatrix1", ColorSpaceTransform.class);
        SENSOR_FORWARD_MATRIX2 = new Key<>("android.sensor.forwardMatrix2", ColorSpaceTransform.class);
        SENSOR_BLACK_LEVEL_PATTERN = new Key<>("android.sensor.blackLevelPattern", BlackLevelPattern.class);
        SENSOR_MAX_ANALOG_SENSITIVITY = new Key<>("android.sensor.maxAnalogSensitivity", Integer.TYPE);
        SENSOR_ORIENTATION = new Key<>(Sensor.STRING_TYPE_ORIENTATION, Integer.TYPE);
        SENSOR_AVAILABLE_TEST_PATTERN_MODES = new Key<>("android.sensor.availableTestPatternModes", int[].class);
        SENSOR_OPTICAL_BLACK_REGIONS = new Key<>("android.sensor.opticalBlackRegions", Rect[].class);
        SENSOR_READOUT_TIMESTAMP = new Key<>("android.sensor.readoutTimestamp", Integer.TYPE);
        SHADING_AVAILABLE_MODES = new Key<>("android.shading.availableModes", int[].class);
        STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES = new Key<>("android.statistics.info.availableFaceDetectModes", int[].class);
        STATISTICS_INFO_MAX_FACE_COUNT = new Key<>("android.statistics.info.maxFaceCount", Integer.TYPE);
        STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES = new Key<>("android.statistics.info.availableHotPixelMapModes", boolean[].class);
        STATISTICS_INFO_AVAILABLE_LENS_SHADING_MAP_MODES = new Key<>("android.statistics.info.availableLensShadingMapModes", int[].class);
        STATISTICS_INFO_AVAILABLE_OIS_DATA_MODES = new Key<>("android.statistics.info.availableOisDataModes", int[].class);
        TONEMAP_MAX_CURVE_POINTS = new Key<>("android.tonemap.maxCurvePoints", Integer.TYPE);
        TONEMAP_AVAILABLE_TONE_MAP_MODES = new Key<>("android.tonemap.availableToneMapModes", int[].class);
        LED_AVAILABLE_LEDS = new Key<>("android.led.availableLeds", int[].class);
        INFO_SUPPORTED_HARDWARE_LEVEL = new Key<>("android.info.supportedHardwareLevel", Integer.TYPE);
        INFO_VERSION = new Key<>("android.info.version", String.class);
        INFO_DEVICE_STATE_SENSOR_ORIENTATION_MAP = new Key<>("android.info.deviceStateSensorOrientationMap", DeviceStateSensorOrientationMap.class);
        INFO_DEVICE_STATE_ORIENTATIONS = new Key<>("android.info.deviceStateOrientations", long[].class);
        INFO_SESSION_CONFIGURATION_QUERY_VERSION = new Key<>("android.info.sessionConfigurationQueryVersion", Integer.TYPE);
        INFO_DEVICE_ID = new Key<>("android.info.deviceId", Integer.TYPE);
        SYNC_MAX_LATENCY = new Key<>("android.sync.maxLatency", Integer.TYPE);
        REPROCESS_MAX_CAPTURE_STALL = new Key<>("android.reprocess.maxCaptureStall", Integer.TYPE);
        DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS = new Key<>("android.depth.availableDepthStreamConfigurations", StreamConfiguration[].class);
        DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS = new Key<>("android.depth.availableDepthMinFrameDurations", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS = new Key<>("android.depth.availableDepthStallDurations", StreamConfigurationDuration[].class);
        DEPTH_DEPTH_IS_EXCLUSIVE = new Key<>("android.depth.depthIsExclusive", Boolean.TYPE);
        DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS = new Key<>("android.depth.availableRecommendedDepthStreamConfigurations", RecommendedStreamConfiguration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS = new Key<>("android.depth.availableDynamicDepthStreamConfigurations", StreamConfiguration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS = new Key<>("android.depth.availableDynamicDepthMinFrameDurations", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS = new Key<>("android.depth.availableDynamicDepthStallDurations", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.depth.availableDepthStreamConfigurationsMaximumResolution", StreamConfiguration[].class);
        DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.depth.availableDepthMinFrameDurationsMaximumResolution", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.depth.availableDepthStallDurationsMaximumResolution", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.depth.availableDynamicDepthStreamConfigurationsMaximumResolution", StreamConfiguration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.depth.availableDynamicDepthMinFrameDurationsMaximumResolution", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.depth.availableDynamicDepthStallDurationsMaximumResolution", StreamConfigurationDuration[].class);
        LOGICAL_MULTI_CAMERA_PHYSICAL_IDS = new Key<>("android.logicalMultiCamera.physicalIds", byte[].class);
        LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE = new Key<>("android.logicalMultiCamera.sensorSyncType", Integer.TYPE);
        DISTORTION_CORRECTION_AVAILABLE_MODES = new Key<>("android.distortionCorrection.availableModes", int[].class);
        HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS = new Key<>("android.heic.availableHeicStreamConfigurations", StreamConfiguration[].class);
        HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS = new Key<>("android.heic.availableHeicMinFrameDurations", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_STALL_DURATIONS = new Key<>("android.heic.availableHeicStallDurations", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.heic.availableHeicStreamConfigurationsMaximumResolution", StreamConfiguration[].class);
        HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.heic.availableHeicMinFrameDurationsMaximumResolution", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_STALL_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.heic.availableHeicStallDurationsMaximumResolution", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_ULTRA_HDR_STREAM_CONFIGURATIONS = new Key<>("android.heic.availableHeicUltraHdrStreamConfigurations", StreamConfiguration[].class);
        HEIC_AVAILABLE_HEIC_ULTRA_HDR_MIN_FRAME_DURATIONS = new Key<>("android.heic.availableHeicUltraHdrMinFrameDurations", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_ULTRA_HDR_STALL_DURATIONS = new Key<>("android.heic.availableHeicUltraHdrStallDurations", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_ULTRA_HDR_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.heic.availableHeicUltraHdrStreamConfigurationsMaximumResolution", StreamConfiguration[].class);
        HEIC_AVAILABLE_HEIC_ULTRA_HDR_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.heic.availableHeicUltraHdrMinFrameDurationsMaximumResolution", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_ULTRA_HDR_STALL_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.heic.availableHeicUltraHdrStallDurationsMaximumResolution", StreamConfigurationDuration[].class);
        AUTOMOTIVE_LENS_FACING = new Key<>("android.automotive.lens.facing", int[].class);
        AUTOMOTIVE_LOCATION = new Key<>("android.automotive.location", Integer.TYPE);
        JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS = new Key<>("android.jpegr.availableJpegRStreamConfigurations", StreamConfiguration[].class);
        JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS = new Key<>("android.jpegr.availableJpegRMinFrameDurations", StreamConfigurationDuration[].class);
        JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS = new Key<>("android.jpegr.availableJpegRStallDurations", StreamConfigurationDuration[].class);
        JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.jpegr.availableJpegRStreamConfigurationsMaximumResolution", StreamConfiguration[].class);
        JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.jpegr.availableJpegRMinFrameDurationsMaximumResolution", StreamConfigurationDuration[].class);
        JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS_MAXIMUM_RESOLUTION = new Key<>("android.jpegr.availableJpegRStallDurationsMaximumResolution", StreamConfigurationDuration[].class);
        SHARED_SESSION_COLOR_SPACE = new Key<>("android.sharedSession.colorSpace", Integer.TYPE);
        SHARED_SESSION_OUTPUT_CONFIGURATIONS = new Key<>("android.sharedSession.outputConfigurations", long[].class);
        SHARED_SESSION_CONFIGURATION = new Key<>("android.sharedSession.configuration", SharedSessionConfiguration.class);
        AVAILABLE_SESSION_CHARACTERISTICS_KEYS_MAP = Map.ofEntries(Map.entry(35, new Key[]{key, key2}));
    }
}
