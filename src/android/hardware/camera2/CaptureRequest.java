package android.hardware.camera2;

import android.content.RestrictionsManager;
import android.graphics.Rect;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.PublicKey;
import android.hardware.camera2.impl.SyntheticKey;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.RggbChannelVector;
import android.hardware.camera2.params.TonemapCurve;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.hardware.camera2.utils.SurfaceUtils;
import android.hardware.camera2.utils.TypeReference;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class CaptureRequest extends CameraMetadata<Key<?>> implements Parcelable {
    public static final int REQUEST_TYPE_COUNT = 3;
    public static final int REQUEST_TYPE_REGULAR = 0;
    public static final int REQUEST_TYPE_REPROCESS = 1;
    public static final int REQUEST_TYPE_ZSL_STILL = 2;
    private static final String SET_TAG_STRING_PREFIX = "android.hardware.camera2.CaptureRequest.setTag.";
    private final String TAG;
    private boolean mIsPartOfCHSRequestList;
    private boolean mIsReprocess;
    private String mLogicalCameraId;
    private CameraMetadataNative mLogicalCameraSettings;
    private final HashMap<String, CameraMetadataNative> mPhysicalCameraSettings;
    private boolean mReleaseSurfaces;
    private int mReprocessableSessionId;
    private int mRequestType;
    private int[] mStreamIdxArray;
    private boolean mSurfaceConverted;
    private int[] mSurfaceIdxArray;
    private final ArraySet<Surface> mSurfaceSet;
    private final Object mSurfacesLock;
    private Object mUserTag;
    private static final ArraySet<Surface> mEmptySurfaceSet = new ArraySet<>();
    public static final Parcelable.Creator<CaptureRequest> CREATOR = new Parcelable.Creator<CaptureRequest>() { // from class: android.hardware.camera2.CaptureRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureRequest createFromParcel(Parcel parcel) {
            CaptureRequest captureRequest = new CaptureRequest();
            captureRequest.readFromParcel(parcel);
            return captureRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CaptureRequest[] newArray(int i) {
            return new CaptureRequest[i];
        }
    };

    @PublicKey
    public static final Key<Integer> COLOR_CORRECTION_MODE = new Key<>("android.colorCorrection.mode", Integer.TYPE);

    @PublicKey
    public static final Key<ColorSpaceTransform> COLOR_CORRECTION_TRANSFORM = new Key<>("android.colorCorrection.transform", ColorSpaceTransform.class);

    @PublicKey
    public static final Key<RggbChannelVector> COLOR_CORRECTION_GAINS = new Key<>("android.colorCorrection.gains", RggbChannelVector.class);

    @PublicKey
    public static final Key<Integer> COLOR_CORRECTION_ABERRATION_MODE = new Key<>("android.colorCorrection.aberrationMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> COLOR_CORRECTION_COLOR_TEMPERATURE = new Key<>("android.colorCorrection.colorTemperature", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> COLOR_CORRECTION_COLOR_TINT = new Key<>("android.colorCorrection.colorTint", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_AE_ANTIBANDING_MODE = new Key<>("android.control.aeAntibandingMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_AE_EXPOSURE_COMPENSATION = new Key<>("android.control.aeExposureCompensation", Integer.TYPE);

    @PublicKey
    public static final Key<Boolean> CONTROL_AE_LOCK = new Key<>("android.control.aeLock", Boolean.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_AE_MODE = new Key<>("android.control.aeMode", Integer.TYPE);

    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AE_REGIONS = new Key<>("android.control.aeRegions", MeteringRectangle[].class);

    @PublicKey
    public static final Key<Range<Integer>> CONTROL_AE_TARGET_FPS_RANGE = new Key<>("android.control.aeTargetFpsRange", new TypeReference<Range<Integer>>() { // from class: android.hardware.camera2.CaptureRequest.2
    });

    @PublicKey
    public static final Key<Integer> CONTROL_AE_PRECAPTURE_TRIGGER = new Key<>("android.control.aePrecaptureTrigger", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_AF_MODE = new Key<>("android.control.afMode", Integer.TYPE);

    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AF_REGIONS = new Key<>("android.control.afRegions", MeteringRectangle[].class);

    @PublicKey
    public static final Key<Integer> CONTROL_AF_TRIGGER = new Key<>("android.control.afTrigger", Integer.TYPE);

    @PublicKey
    public static final Key<Boolean> CONTROL_AWB_LOCK = new Key<>("android.control.awbLock", Boolean.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_AWB_MODE = new Key<>("android.control.awbMode", Integer.TYPE);

    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AWB_REGIONS = new Key<>("android.control.awbRegions", MeteringRectangle[].class);

    @PublicKey
    public static final Key<Integer> CONTROL_CAPTURE_INTENT = new Key<>("android.control.captureIntent", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_EFFECT_MODE = new Key<>("android.control.effectMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_MODE = new Key<>("android.control.mode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_SCENE_MODE = new Key<>("android.control.sceneMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_VIDEO_STABILIZATION_MODE = new Key<>("android.control.videoStabilizationMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_POST_RAW_SENSITIVITY_BOOST = new Key<>("android.control.postRawSensitivityBoost", Integer.TYPE);

    @PublicKey
    public static final Key<Boolean> CONTROL_ENABLE_ZSL = new Key<>("android.control.enableZsl", Boolean.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_EXTENDED_SCENE_MODE = new Key<>("android.control.extendedSceneMode", Integer.TYPE);

    @PublicKey
    public static final Key<Float> CONTROL_ZOOM_RATIO = new Key<>("android.control.zoomRatio", Float.TYPE);
    public static final Key<Boolean> CONTROL_AF_REGIONS_SET = new Key<>("android.control.afRegionsSet", Boolean.TYPE);
    public static final Key<Boolean> CONTROL_AE_REGIONS_SET = new Key<>("android.control.aeRegionsSet", Boolean.TYPE);
    public static final Key<Boolean> CONTROL_AWB_REGIONS_SET = new Key<>("android.control.awbRegionsSet", Boolean.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_SETTINGS_OVERRIDE = new Key<>("android.control.settingsOverride", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_AUTOFRAMING = new Key<>("android.control.autoframing", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_ZOOM_METHOD = new Key<>("android.control.zoomMethod", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> CONTROL_AE_PRIORITY_MODE = new Key<>("android.control.aePriorityMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> EDGE_MODE = new Key<>("android.edge.mode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> FLASH_MODE = new Key<>("android.flash.mode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> FLASH_STRENGTH_LEVEL = new Key<>("android.flash.strengthLevel", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> HOT_PIXEL_MODE = new Key<>("android.hotPixel.mode", Integer.TYPE);

    @SyntheticKey
    @PublicKey
    public static final Key<Location> JPEG_GPS_LOCATION = new Key<>("android.jpeg.gpsLocation", Location.class);
    public static final Key<double[]> JPEG_GPS_COORDINATES = new Key<>("android.jpeg.gpsCoordinates", double[].class);
    public static final Key<String> JPEG_GPS_PROCESSING_METHOD = new Key<>("android.jpeg.gpsProcessingMethod", String.class);
    public static final Key<Long> JPEG_GPS_TIMESTAMP = new Key<>("android.jpeg.gpsTimestamp", Long.TYPE);

    @PublicKey
    public static final Key<Integer> JPEG_ORIENTATION = new Key<>("android.jpeg.orientation", Integer.TYPE);

    @PublicKey
    public static final Key<Byte> JPEG_QUALITY = new Key<>("android.jpeg.quality", Byte.TYPE);

    @PublicKey
    public static final Key<Byte> JPEG_THUMBNAIL_QUALITY = new Key<>("android.jpeg.thumbnailQuality", Byte.TYPE);

    @PublicKey
    public static final Key<Size> JPEG_THUMBNAIL_SIZE = new Key<>("android.jpeg.thumbnailSize", Size.class);

    @PublicKey
    public static final Key<Float> LENS_APERTURE = new Key<>("android.lens.aperture", Float.TYPE);

    @PublicKey
    public static final Key<Float> LENS_FILTER_DENSITY = new Key<>("android.lens.filterDensity", Float.TYPE);

    @PublicKey
    public static final Key<Float> LENS_FOCAL_LENGTH = new Key<>("android.lens.focalLength", Float.TYPE);

    @PublicKey
    public static final Key<Float> LENS_FOCUS_DISTANCE = new Key<>("android.lens.focusDistance", Float.TYPE);

    @PublicKey
    public static final Key<Integer> LENS_OPTICAL_STABILIZATION_MODE = new Key<>("android.lens.opticalStabilizationMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> NOISE_REDUCTION_MODE = new Key<>("android.noiseReduction.mode", Integer.TYPE);
    public static final Key<Integer> REQUEST_ID = new Key<>(RestrictionsManager.REQUEST_KEY_ID, Integer.TYPE);

    @PublicKey
    public static final Key<Rect> SCALER_CROP_REGION = new Key<>("android.scaler.cropRegion", Rect.class);

    @PublicKey
    public static final Key<Integer> SCALER_ROTATE_AND_CROP = new Key<>("android.scaler.rotateAndCrop", Integer.TYPE);
    public static final Key<Boolean> SCALER_CROP_REGION_SET = new Key<>("android.scaler.cropRegionSet", Boolean.TYPE);

    @PublicKey
    public static final Key<Long> SENSOR_EXPOSURE_TIME = new Key<>("android.sensor.exposureTime", Long.TYPE);

    @PublicKey
    public static final Key<Long> SENSOR_FRAME_DURATION = new Key<>("android.sensor.frameDuration", Long.TYPE);

    @PublicKey
    public static final Key<Integer> SENSOR_SENSITIVITY = new Key<>("android.sensor.sensitivity", Integer.TYPE);

    @PublicKey
    public static final Key<int[]> SENSOR_TEST_PATTERN_DATA = new Key<>("android.sensor.testPatternData", int[].class);

    @PublicKey
    public static final Key<Integer> SENSOR_TEST_PATTERN_MODE = new Key<>("android.sensor.testPatternMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> SENSOR_PIXEL_MODE = new Key<>("android.sensor.pixelMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> SHADING_MODE = new Key<>("android.shading.mode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> STATISTICS_FACE_DETECT_MODE = new Key<>("android.statistics.faceDetectMode", Integer.TYPE);

    @PublicKey
    public static final Key<Boolean> STATISTICS_HOT_PIXEL_MAP_MODE = new Key<>("android.statistics.hotPixelMapMode", Boolean.TYPE);

    @PublicKey
    public static final Key<Integer> STATISTICS_LENS_SHADING_MAP_MODE = new Key<>("android.statistics.lensShadingMapMode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> STATISTICS_OIS_DATA_MODE = new Key<>("android.statistics.oisDataMode", Integer.TYPE);
    public static final Key<float[]> TONEMAP_CURVE_BLUE = new Key<>("android.tonemap.curveBlue", float[].class);
    public static final Key<float[]> TONEMAP_CURVE_GREEN = new Key<>("android.tonemap.curveGreen", float[].class);
    public static final Key<float[]> TONEMAP_CURVE_RED = new Key<>("android.tonemap.curveRed", float[].class);

    @SyntheticKey
    @PublicKey
    public static final Key<TonemapCurve> TONEMAP_CURVE = new Key<>("android.tonemap.curve", TonemapCurve.class);

    @PublicKey
    public static final Key<Integer> TONEMAP_MODE = new Key<>("android.tonemap.mode", Integer.TYPE);

    @PublicKey
    public static final Key<Float> TONEMAP_GAMMA = new Key<>("android.tonemap.gamma", Float.TYPE);

    @PublicKey
    public static final Key<Integer> TONEMAP_PRESET_CURVE = new Key<>("android.tonemap.presetCurve", Integer.TYPE);
    public static final Key<Boolean> LED_TRANSMIT = new Key<>("android.led.transmit", Boolean.TYPE);

    @PublicKey
    public static final Key<Boolean> BLACK_LEVEL_LOCK = new Key<>("android.blackLevel.lock", Boolean.TYPE);

    @PublicKey
    public static final Key<Float> REPROCESS_EFFECTIVE_EXPOSURE_FACTOR = new Key<>("android.reprocess.effectiveExposureFactor", Float.TYPE);

    @PublicKey
    public static final Key<Integer> DISTORTION_CORRECTION_MODE = new Key<>("android.distortionCorrection.mode", Integer.TYPE);

    @PublicKey
    public static final Key<Integer> EXTENSION_STRENGTH = new Key<>("android.extension.strength", Integer.TYPE);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Key<T> {
        private final CameraMetadataNative.Key<T> mKey;

        public Key(String str, Class<T> cls, long j) {
            this.mKey = new CameraMetadataNative.Key<>(str, cls, j);
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
            return String.format("CaptureRequest.Key(%s)", this.mKey.getName());
        }

        public CameraMetadataNative.Key<T> getNativeKey() {
            return this.mKey;
        }

        /* JADX WARN: Multi-variable type inference failed */
        Key(CameraMetadataNative.Key<?> key) {
            this.mKey = key;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002e, code lost:
    
        if (((java.lang.Integer) r2.mLogicalCameraSettings.get(android.hardware.camera2.CaptureRequest.CONTROL_CAPTURE_INTENT)).intValue() == 2) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getRequestType() {
        /*
            r2 = this;
            int r0 = r2.mRequestType
            r1 = -1
            if (r0 != r1) goto L34
            boolean r0 = r2.mIsReprocess
            if (r0 == 0) goto Ld
            r0 = 1
            r2.mRequestType = r0
            goto L34
        Ld:
            android.hardware.camera2.impl.CameraMetadataNative r0 = r2.mLogicalCameraSettings
            android.hardware.camera2.CaptureRequest$Key<java.lang.Boolean> r1 = android.hardware.camera2.CaptureRequest.CONTROL_ENABLE_ZSL
            java.lang.Object r0 = r0.get(r1)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            if (r0 == 0) goto L31
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L31
            android.hardware.camera2.impl.CameraMetadataNative r0 = r2.mLogicalCameraSettings
            android.hardware.camera2.CaptureRequest$Key<java.lang.Integer> r1 = android.hardware.camera2.CaptureRequest.CONTROL_CAPTURE_INTENT
            java.lang.Object r0 = r0.get(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            r1 = 2
            if (r0 != r1) goto L31
            goto L32
        L31:
            r1 = 0
        L32:
            r2.mRequestType = r1
        L34:
            int r2 = r2.mRequestType
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.CaptureRequest.getRequestType():int");
    }

    public int[] getStreamIds() {
        return this.mStreamIdxArray;
    }

    public int[] getSurfaceIds() {
        return this.mSurfaceIdxArray;
    }

    private CaptureRequest() {
        this.TAG = "CaptureRequest-JV";
        this.mSurfaceSet = new ArraySet<>();
        this.mSurfacesLock = new Object();
        this.mSurfaceConverted = false;
        this.mPhysicalCameraSettings = new HashMap<>();
        this.mRequestType = -1;
        this.mIsPartOfCHSRequestList = false;
        this.mReleaseSurfaces = false;
        this.mIsReprocess = false;
        this.mReprocessableSessionId = -1;
    }

    private CaptureRequest(CaptureRequest captureRequest) {
        this.TAG = "CaptureRequest-JV";
        this.mSurfaceSet = new ArraySet<>();
        this.mSurfacesLock = new Object();
        this.mSurfaceConverted = false;
        this.mPhysicalCameraSettings = new HashMap<>();
        this.mRequestType = -1;
        this.mIsPartOfCHSRequestList = false;
        this.mReleaseSurfaces = false;
        this.mLogicalCameraId = new String(captureRequest.mLogicalCameraId);
        for (Map.Entry<String, CameraMetadataNative> entry : captureRequest.mPhysicalCameraSettings.entrySet()) {
            this.mPhysicalCameraSettings.put(new String(entry.getKey()), new CameraMetadataNative(entry.getValue()));
        }
        CameraMetadataNative cameraMetadataNative = this.mPhysicalCameraSettings.get(this.mLogicalCameraId);
        this.mLogicalCameraSettings = cameraMetadataNative;
        setNativeInstance(cameraMetadataNative);
        this.mSurfaceSet.addAll((ArraySet<? extends Surface>) captureRequest.mSurfaceSet);
        this.mIsReprocess = captureRequest.mIsReprocess;
        this.mIsPartOfCHSRequestList = captureRequest.mIsPartOfCHSRequestList;
        this.mReprocessableSessionId = captureRequest.mReprocessableSessionId;
        this.mUserTag = captureRequest.mUserTag;
    }

    private CaptureRequest(CameraMetadataNative cameraMetadataNative, boolean z, int i, String str, Set<String> set) {
        this.TAG = "CaptureRequest-JV";
        this.mSurfaceSet = new ArraySet<>();
        this.mSurfacesLock = new Object();
        this.mSurfaceConverted = false;
        HashMap<String, CameraMetadataNative> hashMap = new HashMap<>();
        this.mPhysicalCameraSettings = hashMap;
        this.mRequestType = -1;
        this.mIsPartOfCHSRequestList = false;
        this.mReleaseSurfaces = false;
        if (set != null && z) {
            throw new IllegalArgumentException("Create a reprocess capture request with with more than one physical camera is not supported!");
        }
        this.mLogicalCameraId = str;
        CameraMetadataNative move = CameraMetadataNative.move(cameraMetadataNative);
        this.mLogicalCameraSettings = move;
        hashMap.put(this.mLogicalCameraId, move);
        if (set != null) {
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                this.mPhysicalCameraSettings.put(it.next(), new CameraMetadataNative(this.mLogicalCameraSettings));
            }
        }
        setNativeInstance(this.mLogicalCameraSettings);
        this.mIsReprocess = z;
        if (!z) {
            this.mReprocessableSessionId = -1;
        } else {
            if (i == -1) {
                throw new IllegalArgumentException("Create a reprocess capture request with an invalid session ID: " + i);
            }
            this.mReprocessableSessionId = i;
        }
    }

    public <T> T get(Key<T> key) {
        return (T) this.mLogicalCameraSettings.get(key);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.hardware.camera2.CameraMetadata
    public <T> T getProtected(Key<?> key) {
        return (T) this.mLogicalCameraSettings.get(key);
    }

    @Override // android.hardware.camera2.CameraMetadata
    protected Class<Key<?>> getKeyClass() {
        return Key.class;
    }

    @Override // android.hardware.camera2.CameraMetadata
    public List<Key<?>> getKeys() {
        return super.getKeys();
    }

    public Object getTag() {
        return this.mUserTag;
    }

    public boolean isReprocess() {
        return this.mIsReprocess;
    }

    public boolean isPartOfCRequestList() {
        return this.mIsPartOfCHSRequestList;
    }

    public CameraMetadataNative getNativeCopy() {
        return new CameraMetadataNative(this.mLogicalCameraSettings);
    }

    public int getReprocessableSessionId() {
        int i;
        if (!this.mIsReprocess || (i = this.mReprocessableSessionId) == -1) {
            throw new IllegalStateException("Getting the reprocessable session ID for a non-reprocess capture request is illegal.");
        }
        return i;
    }

    public boolean equals(Object obj) {
        return (obj instanceof CaptureRequest) && equals((CaptureRequest) obj);
    }

    private boolean equals(CaptureRequest captureRequest) {
        return captureRequest != null && Objects.equals(this.mUserTag, captureRequest.mUserTag) && this.mSurfaceSet.equals(captureRequest.mSurfaceSet) && this.mPhysicalCameraSettings.equals(captureRequest.mPhysicalCameraSettings) && this.mLogicalCameraId.equals(captureRequest.mLogicalCameraId) && this.mLogicalCameraSettings.equals(captureRequest.mLogicalCameraSettings) && this.mIsReprocess == captureRequest.mIsReprocess && this.mReprocessableSessionId == captureRequest.mReprocessableSessionId;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCodeGeneric(this.mPhysicalCameraSettings, this.mSurfaceSet, this.mUserTag);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt <= 0) {
            throw new RuntimeException("Physical camera count" + readInt + " should always be positive");
        }
        this.mLogicalCameraId = parcel.readString();
        CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
        this.mLogicalCameraSettings = cameraMetadataNative;
        cameraMetadataNative.readFromParcel(parcel);
        setNativeInstance(this.mLogicalCameraSettings);
        this.mPhysicalCameraSettings.put(this.mLogicalCameraId, this.mLogicalCameraSettings);
        for (int i = 1; i < readInt; i++) {
            String readString = parcel.readString();
            CameraMetadataNative cameraMetadataNative2 = new CameraMetadataNative();
            cameraMetadataNative2.readFromParcel(parcel);
            this.mPhysicalCameraSettings.put(readString, cameraMetadataNative2);
        }
        this.mIsReprocess = parcel.readInt() != 0;
        this.mReprocessableSessionId = -1;
        synchronized (this.mSurfacesLock) {
            this.mSurfaceSet.clear();
            Parcelable[] parcelableArr = (Parcelable[]) parcel.readParcelableArray(Surface.class.getClassLoader(), Surface.class);
            if (parcelableArr != null) {
                this.mReleaseSurfaces = true;
                for (Parcelable parcelable : parcelableArr) {
                    this.mSurfaceSet.add((Surface) parcelable);
                }
            }
            if (parcel.readInt() != 0) {
                throw new RuntimeException("Reading cached CaptureRequest is not supported");
            }
        }
        if (parcel.readInt() == 1) {
            this.mUserTag = parcel.readString();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (!this.mPhysicalCameraSettings.containsKey(this.mLogicalCameraId)) {
            throw new IllegalStateException("Physical camera settings map must contain a key for the logical camera id.");
        }
        parcel.writeInt(this.mPhysicalCameraSettings.size());
        parcel.writeString(this.mLogicalCameraId);
        this.mLogicalCameraSettings.writeToParcel(parcel, i);
        for (Map.Entry<String, CameraMetadataNative> entry : this.mPhysicalCameraSettings.entrySet()) {
            if (!entry.getKey().equals(this.mLogicalCameraId)) {
                parcel.writeString(entry.getKey());
                entry.getValue().writeToParcel(parcel, i);
            }
        }
        parcel.writeInt(this.mIsReprocess ? 1 : 0);
        synchronized (this.mSurfacesLock) {
            ArraySet<Surface> arraySet = this.mSurfaceConverted ? mEmptySurfaceSet : this.mSurfaceSet;
            parcel.writeParcelableArray((Surface[]) arraySet.toArray(new Surface[arraySet.size()]), i);
            if (this.mSurfaceConverted) {
                parcel.writeInt(this.mStreamIdxArray.length);
                int i2 = 0;
                while (true) {
                    int[] iArr = this.mStreamIdxArray;
                    if (i2 >= iArr.length) {
                        break;
                    }
                    parcel.writeInt(iArr[i2]);
                    parcel.writeInt(this.mSurfaceIdxArray[i2]);
                    i2++;
                }
            } else {
                parcel.writeInt(0);
            }
        }
        Object obj = this.mUserTag;
        if (obj != null) {
            String obj2 = obj.toString();
            if (obj2 != null && obj2.startsWith(SET_TAG_STRING_PREFIX)) {
                parcel.writeInt(1);
                parcel.writeString(obj2.substring(47));
                return;
            } else {
                parcel.writeInt(0);
                return;
            }
        }
        parcel.writeInt(0);
    }

    public boolean containsTarget(Surface surface) {
        return this.mSurfaceSet.contains(surface);
    }

    public Collection<Surface> getTargets() {
        return Collections.unmodifiableCollection(this.mSurfaceSet);
    }

    public String getLogicalCameraId() {
        return this.mLogicalCameraId;
    }

    public void convertSurfaceToStreamId(SparseArray<OutputConfiguration> sparseArray) {
        synchronized (this.mSurfacesLock) {
            if (this.mSurfaceConverted) {
                Log.v("CaptureRequest-JV", "Cannot convert already converted surfaces!");
                return;
            }
            this.mStreamIdxArray = new int[this.mSurfaceSet.size()];
            this.mSurfaceIdxArray = new int[this.mSurfaceSet.size()];
            Iterator<Surface> it = this.mSurfaceSet.iterator();
            int i = 0;
            while (it.hasNext()) {
                Surface next = it.next();
                boolean z = false;
                for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                    int keyAt = sparseArray.keyAt(i2);
                    Iterator<Surface> it2 = sparseArray.valueAt(i2).getSurfaces().iterator();
                    int i3 = 0;
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (next == it2.next()) {
                            this.mStreamIdxArray[i] = keyAt;
                            this.mSurfaceIdxArray[i] = i3;
                            i++;
                            z = true;
                            break;
                        }
                        i3++;
                    }
                    if (z) {
                        break;
                    }
                }
                if (!z) {
                    long surfaceId = SurfaceUtils.getSurfaceId(next);
                    for (int i4 = 0; i4 < sparseArray.size(); i4++) {
                        int keyAt2 = sparseArray.keyAt(i4);
                        Iterator<Surface> it3 = sparseArray.valueAt(i4).getSurfaces().iterator();
                        int i5 = 0;
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            if (surfaceId == SurfaceUtils.getSurfaceId(it3.next())) {
                                this.mStreamIdxArray[i] = keyAt2;
                                this.mSurfaceIdxArray[i] = i5;
                                i++;
                                z = true;
                                break;
                            }
                            i5++;
                        }
                        if (z) {
                            break;
                        }
                    }
                }
                if (!z) {
                    this.mStreamIdxArray = null;
                    this.mSurfaceIdxArray = null;
                    throw new IllegalArgumentException("CaptureRequest contains unconfigured Input/Output Surface!");
                }
            }
            this.mSurfaceConverted = true;
        }
    }

    public void recoverStreamIdToSurface() {
        synchronized (this.mSurfacesLock) {
            if (!this.mSurfaceConverted) {
                Log.v("CaptureRequest-JV", "Cannot convert already converted surfaces!");
                return;
            }
            this.mStreamIdxArray = null;
            this.mSurfaceIdxArray = null;
            this.mSurfaceConverted = false;
        }
    }

    protected void finalize() {
        if (this.mReleaseSurfaces) {
            Iterator<Surface> it = this.mSurfaceSet.iterator();
            while (it.hasNext()) {
                it.next().release();
            }
        }
    }

    public static final class Builder {
        private final CaptureRequest mRequest;

        public Builder(CameraMetadataNative cameraMetadataNative, boolean z, int i, String str, Set<String> set) {
            this.mRequest = new CaptureRequest(cameraMetadataNative, z, i, str, set);
        }

        public void addTarget(Surface surface) {
            this.mRequest.mSurfaceSet.add(surface);
        }

        public void removeTarget(Surface surface) {
            this.mRequest.mSurfaceSet.remove(surface);
        }

        public <T> void set(Key<T> key, T t) {
            this.mRequest.mLogicalCameraSettings.set((Key<Key<T>>) key, (Key<T>) t);
        }

        public <T> T get(Key<T> key) {
            return (T) this.mRequest.mLogicalCameraSettings.get(key);
        }

        public <T> Builder setPhysicalCameraKey(Key<T> key, T t, String str) {
            if (!this.mRequest.mPhysicalCameraSettings.containsKey(str)) {
                throw new IllegalArgumentException("Physical camera id: " + str + " is not valid!");
            }
            ((CameraMetadataNative) this.mRequest.mPhysicalCameraSettings.get(str)).set((Key<Key<T>>) key, (Key<T>) t);
            return this;
        }

        public <T> T getPhysicalCameraKey(Key<T> key, String str) {
            if (!this.mRequest.mPhysicalCameraSettings.containsKey(str)) {
                throw new IllegalArgumentException("Physical camera id: " + str + " is not valid!");
            }
            return (T) ((CameraMetadataNative) this.mRequest.mPhysicalCameraSettings.get(str)).get(key);
        }

        public void setTag(Object obj) {
            this.mRequest.mUserTag = obj;
        }

        public void setPartOfCHSRequestList(boolean z) {
            this.mRequest.mIsPartOfCHSRequestList = z;
        }

        public CaptureRequest build() {
            return new CaptureRequest();
        }

        public boolean isEmpty() {
            return this.mRequest.mLogicalCameraSettings.isEmpty();
        }
    }
}
