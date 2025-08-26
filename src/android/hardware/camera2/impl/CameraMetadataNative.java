package android.hardware.camera2.impl;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.MarshalRegistry;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.marshal.impl.MarshalQueryableArray;
import android.hardware.camera2.marshal.impl.MarshalQueryableBlackLevelPattern;
import android.hardware.camera2.marshal.impl.MarshalQueryableBoolean;
import android.hardware.camera2.marshal.impl.MarshalQueryableColorSpaceTransform;
import android.hardware.camera2.marshal.impl.MarshalQueryableEnum;
import android.hardware.camera2.marshal.impl.MarshalQueryableHighSpeedVideoConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableMeteringRectangle;
import android.hardware.camera2.marshal.impl.MarshalQueryableNativeByteToInteger;
import android.hardware.camera2.marshal.impl.MarshalQueryablePair;
import android.hardware.camera2.marshal.impl.MarshalQueryableParcelable;
import android.hardware.camera2.marshal.impl.MarshalQueryablePrimitive;
import android.hardware.camera2.marshal.impl.MarshalQueryableRange;
import android.hardware.camera2.marshal.impl.MarshalQueryableRecommendedStreamConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableRect;
import android.hardware.camera2.marshal.impl.MarshalQueryableReprocessFormatsMap;
import android.hardware.camera2.marshal.impl.MarshalQueryableRggbChannelVector;
import android.hardware.camera2.marshal.impl.MarshalQueryableSize;
import android.hardware.camera2.marshal.impl.MarshalQueryableSizeF;
import android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfigurationDuration;
import android.hardware.camera2.marshal.impl.MarshalQueryableString;
import android.hardware.camera2.params.Capability;
import android.hardware.camera2.params.ColorSpaceProfiles;
import android.hardware.camera2.params.DeviceStateSensorOrientationMap;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.params.LensIntrinsicsSample;
import android.hardware.camera2.params.LensShadingMap;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.MultiResolutionStreamConfigurationMap;
import android.hardware.camera2.params.OisSample;
import android.hardware.camera2.params.RecommendedStreamConfiguration;
import android.hardware.camera2.params.RecommendedStreamConfigurationMap;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.SharedSessionConfiguration;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.params.TonemapCurve;
import android.hardware.camera2.utils.ArrayUtils;
import android.hardware.camera2.utils.TypeReference;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ServiceSpecificException;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import com.android.internal.camera.flags.Flags;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public class CameraMetadataNative implements Parcelable {
    private static final String CELLID_PROCESS = "CELLID";
    public static final Parcelable.Creator<CameraMetadataNative> CREATOR = new Parcelable.Creator<CameraMetadataNative>() { // from class: android.hardware.camera2.impl.CameraMetadataNative.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraMetadataNative createFromParcel(Parcel parcel) {
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            cameraMetadataNative.readFromParcel(parcel);
            return cameraMetadataNative;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraMetadataNative[] newArray(int i) {
            return new CameraMetadataNative[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final int FACE_LANDMARK_SIZE = 6;
    private static final String GPS_PROCESS = "GPS";
    private static final int MANDATORY_STREAM_CONFIGURATIONS_10BIT = 3;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_CONCURRENT = 2;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_DEFAULT = 0;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_MAX_RESOLUTION = 1;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_PREVIEW_STABILIZATION = 5;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_USE_CASE = 4;
    public static final int NATIVE_JPEG_FORMAT = 33;
    public static final int NUM_TYPES = 6;
    private static final String TAG = "CameraMetadataJV";
    public static final int TYPE_BYTE = 0;
    public static final int TYPE_DOUBLE = 4;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_INT32 = 1;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_RATIONAL = 5;
    private static final HashMap<Key<?>, GetCommand> sGetCommandMap;
    private static final HashMap<Key<?>, SetCommand> sSetCommandMap;
    private long mMetadataPtr;
    private int mCameraId = -1;
    private boolean mHasMandatoryConcurrentStreams = false;
    private Size mDisplaySize = new Size(0, 0);
    private long mBufferSize = 0;
    private MultiResolutionStreamConfigurationMap mMultiResolutionStreamConfigurationMap = null;

    @FastNative
    private static native long nativeAllocate();

    @FastNative
    private static native long nativeAllocateCopy(long j) throws NullPointerException;

    @FastNative
    private static native void nativeClose(long j);

    @FastNative
    private static native void nativeDump(long j) throws IOException;

    @FastNative
    private static native ArrayList nativeGetAllVendorKeys(long j, Class cls);

    @FastNative
    private static native long nativeGetBufferSize(long j);

    @FastNative
    private static native int nativeGetEntryCount(long j);

    @FastNative
    private static native int nativeGetTagFromKey(String str, long j) throws IllegalArgumentException;

    @FastNative
    private static native int nativeGetTagFromKeyLocal(long j, String str) throws IllegalArgumentException;

    @FastNative
    private static native int nativeGetTypeFromTag(int i, long j) throws IllegalArgumentException;

    @FastNative
    private static native int nativeGetTypeFromTagLocal(long j, int i) throws IllegalArgumentException;

    @FastNative
    private static native boolean nativeIsEmpty(long j);

    @FastNative
    private static native void nativeReadFromParcel(Parcel parcel, long j);

    @FastNative
    private static native byte[] nativeReadValues(int i, long j);

    @FastNative
    private static native void nativeSetVendorId(long j, long j2);

    private static native int nativeSetupGlobalVendorTagDescriptor();

    @FastNative
    private static native void nativeSwap(long j, long j2) throws NullPointerException;

    @FastNative
    private static native void nativeUpdate(long j, long j2);

    @FastNative
    private static native void nativeWriteToParcel(Parcel parcel, long j);

    @FastNative
    private static native void nativeWriteValues(int i, byte[] bArr, long j);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Key<T> {
        private final String mFallbackName;
        private boolean mHasTag;
        private final int mHash;
        private final String mName;
        private int mTag;
        private final Class<T> mType;
        private final TypeReference<T> mTypeReference;
        private long mVendorId;

        public Key(String str, Class<T> cls, long j) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new NullPointerException("Key needs a valid name");
            }
            if (cls == null) {
                throw new NullPointerException("Type needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = null;
            this.mType = cls;
            this.mVendorId = j;
            TypeReference<T> typeReferenceCreateSpecializedTypeReference = TypeReference.createSpecializedTypeReference((Class) cls);
            this.mTypeReference = typeReferenceCreateSpecializedTypeReference;
            this.mHash = str.hashCode() ^ typeReferenceCreateSpecializedTypeReference.hashCode();
        }

        public Key(String str, String str2, Class<T> cls) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new NullPointerException("Key needs a valid name");
            }
            if (cls == null) {
                throw new NullPointerException("Type needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = str2;
            this.mType = cls;
            TypeReference<T> typeReferenceCreateSpecializedTypeReference = TypeReference.createSpecializedTypeReference((Class) cls);
            this.mTypeReference = typeReferenceCreateSpecializedTypeReference;
            this.mHash = str.hashCode() ^ typeReferenceCreateSpecializedTypeReference.hashCode();
        }

        public Key(String str, Class<T> cls) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new NullPointerException("Key needs a valid name");
            }
            if (cls == null) {
                throw new NullPointerException("Type needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = null;
            this.mType = cls;
            TypeReference<T> typeReferenceCreateSpecializedTypeReference = TypeReference.createSpecializedTypeReference((Class) cls);
            this.mTypeReference = typeReferenceCreateSpecializedTypeReference;
            this.mHash = str.hashCode() ^ typeReferenceCreateSpecializedTypeReference.hashCode();
        }

        public Key(String str, TypeReference<T> typeReference) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new NullPointerException("Key needs a valid name");
            }
            if (typeReference == null) {
                throw new NullPointerException("TypeReference needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = null;
            this.mType = typeReference.getRawType();
            this.mTypeReference = typeReference;
            this.mHash = str.hashCode() ^ typeReference.hashCode();
        }

        public final String getName() {
            return this.mName;
        }

        public final int hashCode() {
            return this.mHash;
        }

        public final boolean equals(Object obj) {
            Key<T> nativeKey;
            if (this == obj) {
                return true;
            }
            if (obj != null && hashCode() == obj.hashCode()) {
                if (obj instanceof CaptureResult.Key) {
                    nativeKey = ((CaptureResult.Key) obj).getNativeKey();
                } else if (obj instanceof CaptureRequest.Key) {
                    nativeKey = ((CaptureRequest.Key) obj).getNativeKey();
                } else if (obj instanceof CameraCharacteristics.Key) {
                    nativeKey = ((CameraCharacteristics.Key) obj).getNativeKey();
                } else if (obj instanceof Key) {
                    nativeKey = (Key) obj;
                }
                if (this.mName.equals(nativeKey.mName) && this.mTypeReference.equals(nativeKey.mTypeReference)) {
                    return true;
                }
            }
            return false;
        }

        public final int getTag() {
            if (!this.mHasTag) {
                this.mTag = CameraMetadataNative.getTag(this.mName, this.mVendorId);
                this.mHasTag = true;
            }
            return this.mTag;
        }

        public final boolean hasTag() {
            return this.mHasTag;
        }

        public final void cacheTag(int i) {
            this.mHasTag = true;
            this.mTag = i;
        }

        public final Class<T> getType() {
            return this.mType;
        }

        public final long getVendorId() {
            return this.mVendorId;
        }

        public final TypeReference<T> getTypeReference() {
            return this.mTypeReference;
        }
    }

    private static String translateLocationProviderToProcess(String str) {
        if (str == null) {
            return null;
        }
        str.hashCode();
        if (str.equals("gps")) {
            return GPS_PROCESS;
        }
        if (str.equals("network")) {
            return CELLID_PROCESS;
        }
        return null;
    }

    private static String translateProcessToLocationProvider(String str) {
        if (str == null) {
            return null;
        }
        str.hashCode();
        if (str.equals(GPS_PROCESS)) {
            return "gps";
        }
        if (str.equals(CELLID_PROCESS)) {
            return "network";
        }
        return null;
    }

    public CameraMetadataNative() {
        long jNativeAllocate = nativeAllocate();
        this.mMetadataPtr = jNativeAllocate;
        if (jNativeAllocate == 0) {
            throw new OutOfMemoryError("Failed to allocate native CameraMetadata");
        }
        updateNativeAllocation();
    }

    public CameraMetadataNative(CameraMetadataNative cameraMetadataNative) throws NullPointerException {
        long jNativeAllocateCopy = nativeAllocateCopy(cameraMetadataNative.mMetadataPtr);
        this.mMetadataPtr = jNativeAllocateCopy;
        if (jNativeAllocateCopy == 0) {
            throw new OutOfMemoryError("Failed to allocate native CameraMetadata");
        }
        updateNativeAllocation();
    }

    public CameraMetadataNative(long j) {
        this.mMetadataPtr = j;
        if (j == 0) {
            throw new OutOfMemoryError("Failed to allocate native CameraMetadata");
        }
        updateNativeAllocation();
    }

    public static CameraMetadataNative move(CameraMetadataNative cameraMetadataNative) {
        CameraMetadataNative cameraMetadataNative2 = new CameraMetadataNative();
        cameraMetadataNative2.swap(cameraMetadataNative);
        return cameraMetadataNative2;
    }

    public static void update(CameraMetadataNative cameraMetadataNative, CameraMetadataNative cameraMetadataNative2) {
        nativeUpdate(cameraMetadataNative.mMetadataPtr, cameraMetadataNative2.mMetadataPtr);
    }

    static {
        HashMap<Key<?>, GetCommand> map = new HashMap<>();
        sGetCommandMap = map;
        map.put(CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getAvailableFormats();
            }
        });
        map.put(CaptureResult.STATISTICS_FACES.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getFaces();
            }
        });
        map.put(CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getFaceRectangles();
            }
        });
        map.put(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.5
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getStreamConfigurationMap();
            }
        });
        map.put(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.6
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getStreamConfigurationMapMaximumResolution();
            }
        });
        map.put(CameraCharacteristics.SCALER_MANDATORY_STREAM_COMBINATIONS.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryStreamCombinations();
            }
        });
        map.put(CameraCharacteristics.SCALER_MANDATORY_CONCURRENT_STREAM_COMBINATIONS.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.8
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryConcurrentStreamCombinations();
            }
        });
        map.put(CameraCharacteristics.SCALER_MANDATORY_TEN_BIT_OUTPUT_STREAM_COMBINATIONS.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.9
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMandatory10BitStreamCombinations();
            }
        });
        map.put(CameraCharacteristics.SCALER_MANDATORY_MAXIMUM_RESOLUTION_STREAM_COMBINATIONS.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.10
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryMaximumResolutionStreamCombinations();
            }
        });
        map.put(CameraCharacteristics.SCALER_MANDATORY_USE_CASE_STREAM_COMBINATIONS.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.11
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryUseCaseStreamCombinations();
            }
        });
        map.put(CameraCharacteristics.SCALER_MANDATORY_PREVIEW_STABILIZATION_OUTPUT_STREAM_COMBINATIONS.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.12
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryPreviewStabilizationStreamCombinations();
            }
        });
        map.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AE.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.13
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMaxRegions(key);
            }
        });
        map.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.14
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMaxRegions(key);
            }
        });
        map.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AF.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.15
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMaxRegions(key);
            }
        });
        map.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.16
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        map.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.17
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        map.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.18
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        map.put(CaptureRequest.TONEMAP_CURVE.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.19
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getTonemapCurve();
            }
        });
        map.put(CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.20
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getGpsLocation();
            }
        });
        map.put(CaptureResult.STATISTICS_LENS_SHADING_CORRECTION_MAP.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.21
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getLensShadingMap();
            }
        });
        map.put(CameraCharacteristics.INFO_DEVICE_STATE_SENSOR_ORIENTATION_MAP.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.22
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getDeviceStateOrientationMap();
            }
        });
        map.put(CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.23
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getDynamicRangeProfiles();
            }
        });
        map.put(CameraCharacteristics.REQUEST_AVAILABLE_COLOR_SPACE_PROFILES.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.24
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getColorSpaceProfiles();
            }
        });
        map.put(CaptureResult.STATISTICS_OIS_SAMPLES.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.25
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getOisSamples();
            }
        });
        map.put(CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_CAPABILITIES.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.26
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getExtendedSceneModeCapabilities();
            }
        });
        map.put(CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.27
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getMultiResolutionStreamConfigurationMap();
            }
        });
        map.put(CaptureResult.STATISTICS_LENS_INTRINSICS_SAMPLES.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.28
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getLensIntrinsicSamples();
            }
        });
        map.put(CameraCharacteristics.SHARED_SESSION_CONFIGURATION.getNativeKey(), new GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.29
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T) cameraMetadataNative.getSharedSessionConfiguration();
            }
        });
        HashMap<Key<?>, SetCommand> map2 = new HashMap<>();
        sSetCommandMap = map2;
        map2.put(CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.30
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAvailableFormats((int[]) t);
            }
        });
        map2.put(CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.31
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setFaceRectangles((Rect[]) t);
            }
        });
        map2.put(CaptureResult.STATISTICS_FACES.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.32
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setFaces((Face[]) t);
            }
        });
        map2.put(CaptureRequest.TONEMAP_CURVE.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.33
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setTonemapCurve((TonemapCurve) t);
            }
        });
        map2.put(CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.34
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setGpsLocation((Location) t);
            }
        });
        map2.put(CaptureRequest.SCALER_CROP_REGION.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.35
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setScalerCropRegion((Rect) t);
            }
        });
        map2.put(CaptureRequest.CONTROL_AWB_REGIONS.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.36
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAWBRegions(t);
            }
        });
        map2.put(CaptureRequest.CONTROL_AF_REGIONS.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.37
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAFRegions(t);
            }
        });
        map2.put(CaptureRequest.CONTROL_AE_REGIONS.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.38
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAERegions(t);
            }
        });
        map2.put(CaptureResult.STATISTICS_LENS_SHADING_CORRECTION_MAP.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.39
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setLensShadingMap((LensShadingMap) t);
            }
        });
        map2.put(CaptureResult.STATISTICS_LENS_INTRINSICS_SAMPLES.getNativeKey(), new SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.40
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setLensIntrinsicsSamples((LensIntrinsicsSample[]) t);
            }
        });
        registerAllMarshalers();
    }

    @Override // android.os.Parcelable
    public synchronized void writeToParcel(Parcel parcel, int i) {
        nativeWriteToParcel(parcel, this.mMetadataPtr);
    }

    public <T> T get(CameraCharacteristics.Key<T> key) {
        return (T) get(key.getNativeKey());
    }

    public <T> T get(CaptureResult.Key<T> key) {
        return (T) get(key.getNativeKey());
    }

    public <T> T get(CaptureRequest.Key<T> key) {
        return (T) get(key.getNativeKey());
    }

    public <T> T get(Key<T> key) {
        Objects.requireNonNull(key, "key must not be null");
        GetCommand getCommand = sGetCommandMap.get(key);
        if (getCommand != null) {
            return (T) getCommand.getValue(this, key);
        }
        return (T) getBase(key);
    }

    public synchronized void readFromParcel(Parcel parcel) {
        nativeReadFromParcel(parcel, this.mMetadataPtr);
        updateNativeAllocation();
    }

    public static void setupGlobalVendorTagDescriptor() throws ServiceSpecificException {
        int iNativeSetupGlobalVendorTagDescriptor = nativeSetupGlobalVendorTagDescriptor();
        if (iNativeSetupGlobalVendorTagDescriptor != 0) {
            throw new ServiceSpecificException(iNativeSetupGlobalVendorTagDescriptor, "Failure to set up global vendor tags");
        }
    }

    public <T> void set(Key<T> key, T t) {
        SetCommand setCommand = sSetCommandMap.get(key);
        if (setCommand != null) {
            setCommand.setValue(this, t);
        } else {
            setBase((Key<Key<T>>) key, (Key<T>) t);
        }
    }

    public <T> void set(CaptureRequest.Key<T> key, T t) {
        set((Key<Key<T>>) key.getNativeKey(), (Key<T>) t);
    }

    public <T> void set(CaptureResult.Key<T> key, T t) {
        set((Key<Key<T>>) key.getNativeKey(), (Key<T>) t);
    }

    public <T> void set(CameraCharacteristics.Key<T> key, T t) {
        set((Key<Key<T>>) key.getNativeKey(), (Key<T>) t);
    }

    private void close() {
        nativeClose(this.mMetadataPtr);
        this.mMetadataPtr = 0L;
        if (this.mBufferSize > 0) {
            VMRuntime.getRuntime().registerNativeFree(this.mBufferSize);
        }
        this.mBufferSize = 0L;
    }

    private <T> T getBase(CameraCharacteristics.Key<T> key) {
        return (T) getBase(key.getNativeKey());
    }

    private <T> T getBase(CaptureResult.Key<T> key) {
        return (T) getBase(key.getNativeKey());
    }

    private <T> T getBase(CaptureRequest.Key<T> key) {
        return (T) getBase(key.getNativeKey());
    }

    private <T> T getBase(Key<T> key) {
        int iNativeGetTagFromKeyLocal;
        synchronized (this) {
            if (key.hasTag()) {
                iNativeGetTagFromKeyLocal = key.getTag();
            } else {
                iNativeGetTagFromKeyLocal = nativeGetTagFromKeyLocal(this.mMetadataPtr, key.getName());
                key.cacheTag(iNativeGetTagFromKeyLocal);
            }
            byte[] values = readValues(iNativeGetTagFromKeyLocal);
            if (values == null) {
                if (((Key) key).mFallbackName == null) {
                    return null;
                }
                iNativeGetTagFromKeyLocal = nativeGetTagFromKeyLocal(this.mMetadataPtr, ((Key) key).mFallbackName);
                byte[] values2 = readValues(iNativeGetTagFromKeyLocal);
                if (values2 == null) {
                    return null;
                }
                values = values2;
            }
            return (T) getMarshalerForKey(key, nativeGetTypeFromTagLocal(this.mMetadataPtr, iNativeGetTagFromKeyLocal)).unmarshal(ByteBuffer.wrap(values).order(ByteOrder.nativeOrder()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getAvailableFormats() {
        int[] iArr = (int[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_FORMATS);
        if (iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                if (iArr[i] == 33) {
                    iArr[i] = 256;
                }
            }
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setFaces(Face[] faceArr) {
        int[] iArr;
        int[] iArr2;
        if (faceArr == null) {
            return false;
        }
        int length = faceArr.length;
        boolean z = true;
        for (Face face : faceArr) {
            if (face == null) {
                length--;
                Log.w(TAG, "setFaces - null face detected, skipping");
            } else if (face.getId() == -1) {
                z = false;
            }
        }
        Rect[] rectArr = new Rect[length];
        byte[] bArr = new byte[length];
        if (z) {
            iArr = new int[length];
            iArr2 = new int[length * 6];
        } else {
            iArr = null;
            iArr2 = null;
        }
        int i = 0;
        for (Face face2 : faceArr) {
            if (face2 != null) {
                rectArr[i] = face2.getBounds();
                bArr[i] = (byte) face2.getScore();
                if (z) {
                    iArr[i] = face2.getId();
                    int i2 = i * 6;
                    iArr2[i2] = face2.getLeftEyePosition().x;
                    iArr2[i2 + 1] = face2.getLeftEyePosition().y;
                    iArr2[i2 + 2] = face2.getRightEyePosition().x;
                    iArr2[i2 + 3] = face2.getRightEyePosition().y;
                    iArr2[i2 + 4] = face2.getMouthPosition().x;
                    iArr2[i2 + 5] = face2.getMouthPosition().y;
                }
                i++;
            }
        }
        set((CaptureResult.Key<CaptureResult.Key<Rect[]>>) CaptureResult.STATISTICS_FACE_RECTANGLES, (CaptureResult.Key<Rect[]>) rectArr);
        set((CaptureResult.Key<CaptureResult.Key<int[]>>) CaptureResult.STATISTICS_FACE_IDS, (CaptureResult.Key<int[]>) iArr);
        set((CaptureResult.Key<CaptureResult.Key<int[]>>) CaptureResult.STATISTICS_FACE_LANDMARKS, (CaptureResult.Key<int[]>) iArr2);
        set((CaptureResult.Key<CaptureResult.Key<byte[]>>) CaptureResult.STATISTICS_FACE_SCORES, (CaptureResult.Key<byte[]>) bArr);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Face[] getFaces() {
        Integer num = (Integer) get(CaptureResult.STATISTICS_FACE_DETECT_MODE);
        byte[] bArr = (byte[]) get(CaptureResult.STATISTICS_FACE_SCORES);
        Rect[] rectArr = (Rect[]) get(CaptureResult.STATISTICS_FACE_RECTANGLES);
        int[] iArr = (int[]) get(CaptureResult.STATISTICS_FACE_IDS);
        int[] iArr2 = (int[]) get(CaptureResult.STATISTICS_FACE_LANDMARKS);
        if (areValuesAllNull(num, bArr, rectArr, iArr, iArr2)) {
            return null;
        }
        int i = 0;
        if (num == null) {
            Log.w(TAG, "Face detect mode metadata is null, assuming the mode is SIMPLE");
            num = 1;
        } else if (num.intValue() == 101) {
            num = 101;
        } else if (num.intValue() > 2) {
            num = 2;
        } else {
            if (num.intValue() == 0) {
                return new Face[0];
            }
            if (num.intValue() != 1 && num.intValue() != 2) {
                Log.w(TAG, "Unknown face detect mode: " + num);
                return new Face[0];
            }
        }
        if (bArr == null || rectArr == null) {
            Log.w(TAG, "Expect face scores and rectangles to be non-null");
            return new Face[0];
        }
        if (bArr.length != rectArr.length) {
            Log.w(TAG, String.format("Face score size(%d) doesn match face rectangle size(%d)!", Integer.valueOf(bArr.length), Integer.valueOf(rectArr.length)));
        }
        int iMin = Math.min(bArr.length, rectArr.length);
        if (num.intValue() == 2) {
            if (iArr == null || iArr2 == null) {
                Log.w(TAG, "Expect face ids and landmarks to be non-null for FULL mode,fallback to SIMPLE mode");
                num = 1;
            } else {
                if (iArr.length != iMin || iArr2.length != iMin * 6) {
                    Log.w(TAG, String.format("Face id size(%d), or face landmark size(%d) don'tmatch face number(%d)!", Integer.valueOf(iArr.length), Integer.valueOf(iArr2.length * 6), Integer.valueOf(iMin)));
                }
                iMin = Math.min(Math.min(iMin, iArr.length), iArr2.length / 6);
            }
        } else if (num.intValue() == 101) {
            if (iArr == null) {
                Log.w(TAG, "Expect face ids to be non-null for TRACKING mode,fallback to SIMPLE mode");
                num = 1;
            } else {
                if (iArr.length != iMin) {
                    Log.w(TAG, String.format("Face id size(%d) don't match face number(%d)!", Integer.valueOf(iArr.length), Integer.valueOf(iMin)));
                }
                iMin = Math.min(iMin, iArr.length);
            }
        }
        ArrayList arrayList = new ArrayList();
        if (num.intValue() == 1) {
            while (i < iMin) {
                byte b = bArr[i];
                if (b <= 100 && b >= 1) {
                    arrayList.add(new Face(rectArr[i], bArr[i]));
                }
                i++;
            }
        } else if (num.intValue() == 101) {
            while (i < iMin) {
                byte b2 = bArr[i];
                if (b2 <= 100 && b2 >= 1 && iArr[i] >= 0) {
                    arrayList.add(new Face(rectArr[i], bArr[i], iArr[i], null, null, null));
                }
                i++;
            }
        } else {
            while (i < iMin) {
                byte b3 = bArr[i];
                if (b3 <= 100 && b3 >= 1 && iArr[i] >= 0) {
                    int i2 = i * 6;
                    arrayList.add(new Face(rectArr[i], bArr[i], iArr[i], new Point(iArr2[i2], iArr2[i2 + 1]), new Point(iArr2[i2 + 2], iArr2[i2 + 3]), new Point(iArr2[i2 + 4], iArr2[i2 + 5])));
                }
                i++;
            }
        }
        Face[] faceArr = new Face[arrayList.size()];
        arrayList.toArray(faceArr);
        return faceArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect[] getFaceRectangles() {
        Rect[] rectArr = (Rect[]) getBase(CaptureResult.STATISTICS_FACE_RECTANGLES);
        if (rectArr == null) {
            return null;
        }
        Rect[] rectArr2 = new Rect[rectArr.length];
        for (int i = 0; i < rectArr.length; i++) {
            rectArr2[i] = new Rect(rectArr[i].left, rectArr[i].top, rectArr[i].right - rectArr[i].left, rectArr[i].bottom - rectArr[i].top);
        }
        return rectArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setLensShadingMap(LensShadingMap lensShadingMap) {
        if (lensShadingMap == null) {
            return false;
        }
        float[] fArr = new float[lensShadingMap.getGainFactorCount()];
        lensShadingMap.copyGainFactors(fArr, 0);
        setBase((CaptureResult.Key<CaptureResult.Key<float[]>>) CaptureResult.STATISTICS_LENS_SHADING_MAP, (CaptureResult.Key<float[]>) fArr);
        setBase((CameraCharacteristics.Key<CameraCharacteristics.Key<Size>>) CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, (CameraCharacteristics.Key<Size>) new Size(lensShadingMap.getRowCount(), lensShadingMap.getColumnCount()));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LensShadingMap getLensShadingMap() {
        float[] fArr = (float[]) getBase(CaptureResult.STATISTICS_LENS_SHADING_MAP);
        Size size = (Size) get(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE);
        if (fArr == null) {
            return null;
        }
        if (size == null) {
            Log.w(TAG, "getLensShadingMap - Lens shading map size was null.");
            return null;
        }
        return new LensShadingMap(fArr, size.getHeight(), size.getWidth());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DeviceStateSensorOrientationMap getDeviceStateOrientationMap() {
        long[] jArr = (long[]) getBase(CameraCharacteristics.INFO_DEVICE_STATE_ORIENTATIONS);
        if (jArr == null) {
            return null;
        }
        return new DeviceStateSensorOrientationMap(jArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DynamicRangeProfiles getDynamicRangeProfiles() {
        long[] jArr = (long[]) getBase(CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES_MAP);
        if (jArr == null) {
            return null;
        }
        return new DynamicRangeProfiles(jArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ColorSpaceProfiles getColorSpaceProfiles() {
        long[] jArr = (long[]) getBase(CameraCharacteristics.REQUEST_AVAILABLE_COLOR_SPACE_PROFILES_MAP);
        if (jArr == null) {
            return null;
        }
        return new ColorSpaceProfiles(jArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Location getGpsLocation() {
        String str = (String) get(CaptureResult.JPEG_GPS_PROCESSING_METHOD);
        double[] dArr = (double[]) get(CaptureResult.JPEG_GPS_COORDINATES);
        Long l = (Long) get(CaptureResult.JPEG_GPS_TIMESTAMP);
        if (areValuesAllNull(str, dArr, l)) {
            return null;
        }
        Location location = new Location(translateProcessToLocationProvider(str));
        if (l == null) {
            Log.w(TAG, "getGpsLocation - No timestamp for GPS location.");
        } else {
            location.setTime(l.longValue() * 1000);
        }
        if (dArr == null) {
            Log.w(TAG, "getGpsLocation - No coordinates for GPS location");
            return location;
        }
        location.setLatitude(dArr[0]);
        location.setLongitude(dArr[1]);
        location.setAltitude(dArr[2]);
        return location;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setGpsLocation(Location location) {
        if (location == null) {
            setBase((CaptureRequest.Key<CaptureRequest.Key<Long>>) CaptureRequest.JPEG_GPS_TIMESTAMP, (CaptureRequest.Key<Long>) null);
            setBase((CaptureRequest.Key<CaptureRequest.Key<double[]>>) CaptureRequest.JPEG_GPS_COORDINATES, (CaptureRequest.Key<double[]>) null);
            setBase((CaptureRequest.Key<CaptureRequest.Key<String>>) CaptureRequest.JPEG_GPS_PROCESSING_METHOD, (CaptureRequest.Key<String>) null);
            return false;
        }
        double[] dArr = {location.getLatitude(), location.getLongitude(), location.getAltitude()};
        String strTranslateLocationProviderToProcess = translateLocationProviderToProcess(location.getProvider());
        set((CaptureRequest.Key<CaptureRequest.Key<Long>>) CaptureRequest.JPEG_GPS_TIMESTAMP, (CaptureRequest.Key<Long>) Long.valueOf(location.getTime() / 1000));
        set((CaptureRequest.Key<CaptureRequest.Key<double[]>>) CaptureRequest.JPEG_GPS_COORDINATES, (CaptureRequest.Key<double[]>) dArr);
        if (strTranslateLocationProviderToProcess == null) {
            Log.w(TAG, "setGpsLocation - No process method, Location is not from a GPS or NETWORKprovider");
        } else {
            setBase((CaptureRequest.Key<CaptureRequest.Key<String>>) CaptureRequest.JPEG_GPS_PROCESSING_METHOD, (CaptureRequest.Key<String>) strTranslateLocationProviderToProcess);
        }
        return true;
    }

    private void parseRecommendedConfigurations(RecommendedStreamConfiguration[] recommendedStreamConfigurationArr, StreamConfigurationMap streamConfigurationMap, boolean z, ArrayList<ArrayList<StreamConfiguration>> arrayList, ArrayList<ArrayList<StreamConfigurationDuration>> arrayList2, ArrayList<ArrayList<StreamConfigurationDuration>> arrayList3, boolean[] zArr) {
        int iImageFormatToPublic;
        char c;
        Size size;
        int i;
        int i2;
        boolean z2;
        StreamConfigurationDuration streamConfigurationDuration;
        StreamConfigurationDuration streamConfigurationDuration2;
        StreamConfigurationDuration streamConfigurationDuration3;
        RecommendedStreamConfiguration[] recommendedStreamConfigurationArr2 = recommendedStreamConfigurationArr;
        StreamConfigurationMap streamConfigurationMap2 = streamConfigurationMap;
        arrayList.ensureCapacity(32);
        arrayList2.ensureCapacity(32);
        arrayList3.ensureCapacity(32);
        boolean z3 = false;
        for (int i3 = 0; i3 < 32; i3++) {
            arrayList.add(new ArrayList<>());
            arrayList2.add(new ArrayList<>());
            arrayList3.add(new ArrayList<>());
        }
        int length = recommendedStreamConfigurationArr2.length;
        int i4 = 0;
        while (i4 < length) {
            RecommendedStreamConfiguration recommendedStreamConfiguration = recommendedStreamConfigurationArr2[i4];
            int width = recommendedStreamConfiguration.getWidth();
            int height = recommendedStreamConfiguration.getHeight();
            int format = recommendedStreamConfiguration.getFormat();
            if (z) {
                iImageFormatToPublic = StreamConfigurationMap.depthFormatToPublic(format);
            } else {
                iImageFormatToPublic = StreamConfigurationMap.imageFormatToPublic(format);
            }
            Size size2 = new Size(width, height);
            int usecaseBitmap = recommendedStreamConfiguration.getUsecaseBitmap();
            if (!recommendedStreamConfiguration.isInput()) {
                StreamConfiguration streamConfiguration = new StreamConfiguration(format, width, height, z3);
                long outputMinFrameDuration = streamConfigurationMap2.getOutputMinFrameDuration(iImageFormatToPublic, size2);
                if (outputMinFrameDuration > 0) {
                    size = size2;
                    i2 = usecaseBitmap;
                    i = iImageFormatToPublic;
                    z2 = true;
                    streamConfigurationDuration = new StreamConfigurationDuration(format, width, height, outputMinFrameDuration);
                } else {
                    size = size2;
                    i = iImageFormatToPublic;
                    i2 = usecaseBitmap;
                    z2 = true;
                    streamConfigurationDuration = null;
                }
                long outputStallDuration = streamConfigurationMap2.getOutputStallDuration(i, size);
                if (outputStallDuration > 0) {
                    streamConfigurationDuration2 = streamConfigurationDuration;
                    streamConfigurationDuration3 = new StreamConfigurationDuration(format, width, height, outputStallDuration);
                } else {
                    streamConfigurationDuration2 = streamConfigurationDuration;
                    streamConfigurationDuration3 = null;
                }
                c = ' ';
                for (int i5 = 0; i5 < 32; i5++) {
                    if ((((z2 ? 1 : 0) << i5) & i2) != 0) {
                        arrayList.get(i5).add(streamConfiguration);
                        if (outputMinFrameDuration > 0) {
                            arrayList2.get(i5).add(streamConfigurationDuration2);
                        }
                        if (outputStallDuration > 0) {
                            arrayList3.get(i5).add(streamConfigurationDuration3);
                        }
                        if (zArr != null && !zArr[i5] && i == 34) {
                            zArr[i5] = z2;
                        }
                    }
                }
            } else {
                c = ' ';
                if (usecaseBitmap != 16) {
                    throw new IllegalArgumentException("Recommended input stream configurations should only be advertised in the ZSL use case!");
                }
                arrayList.get(4).add(new StreamConfiguration(format, width, height, true));
            }
            i4++;
            recommendedStreamConfigurationArr2 = recommendedStreamConfigurationArr;
            streamConfigurationMap2 = streamConfigurationMap;
            z3 = false;
        }
    }

    private class StreamConfigurationData {
        StreamConfigurationDuration[] minDurationArray;
        StreamConfigurationDuration[] stallDurationArray;
        StreamConfiguration[] streamConfigurationArray;

        private StreamConfigurationData(CameraMetadataNative cameraMetadataNative) {
            this.streamConfigurationArray = null;
            this.minDurationArray = null;
            this.stallDurationArray = null;
        }
    }

    public void initializeStreamConfigurationData(ArrayList<StreamConfiguration> arrayList, ArrayList<StreamConfigurationDuration> arrayList2, ArrayList<StreamConfigurationDuration> arrayList3, StreamConfigurationData streamConfigurationData) {
        if (streamConfigurationData == null || arrayList == null) {
            return;
        }
        streamConfigurationData.streamConfigurationArray = new StreamConfiguration[arrayList.size()];
        streamConfigurationData.streamConfigurationArray = (StreamConfiguration[]) arrayList.toArray(streamConfigurationData.streamConfigurationArray);
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            streamConfigurationData.minDurationArray = new StreamConfigurationDuration[arrayList2.size()];
            streamConfigurationData.minDurationArray = (StreamConfigurationDuration[]) arrayList2.toArray(streamConfigurationData.minDurationArray);
        } else {
            streamConfigurationData.minDurationArray = new StreamConfigurationDuration[0];
        }
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            streamConfigurationData.stallDurationArray = new StreamConfigurationDuration[arrayList3.size()];
            streamConfigurationData.stallDurationArray = (StreamConfigurationDuration[]) arrayList3.toArray(streamConfigurationData.stallDurationArray);
        } else {
            streamConfigurationData.stallDurationArray = new StreamConfigurationDuration[0];
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x01a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ArrayList<RecommendedStreamConfigurationMap> getRecommendedStreamConfigurations() {
        RecommendedStreamConfiguration[] recommendedStreamConfigurationArr;
        ArrayList<ArrayList<StreamConfiguration>> arrayList;
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList2;
        boolean[] zArr;
        RecommendedStreamConfiguration[] recommendedStreamConfigurationArr2;
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList3;
        CameraMetadataNative cameraMetadataNative;
        CameraMetadataNativeIA cameraMetadataNativeIA;
        StreamConfigurationMap streamConfigurationMap;
        StreamConfigurationMap streamConfigurationMap2;
        RecommendedStreamConfiguration[] recommendedStreamConfigurationArr3 = (RecommendedStreamConfiguration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS);
        RecommendedStreamConfiguration[] recommendedStreamConfigurationArr4 = (RecommendedStreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS);
        CameraMetadataNativeIA cameraMetadataNativeIA2 = null;
        if (recommendedStreamConfigurationArr3 == null && recommendedStreamConfigurationArr4 == null) {
            return null;
        }
        StreamConfigurationMap streamConfigurationMap3 = getStreamConfigurationMap();
        ArrayList<RecommendedStreamConfigurationMap> arrayList4 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfiguration>> arrayList5 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList6 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList7 = new ArrayList<>();
        int i = 32;
        boolean[] zArr2 = new boolean[32];
        if (recommendedStreamConfigurationArr3 != null) {
            try {
                parseRecommendedConfigurations(recommendedStreamConfigurationArr3, streamConfigurationMap3, false, arrayList5, arrayList6, arrayList7, zArr2);
                recommendedStreamConfigurationArr = recommendedStreamConfigurationArr3;
                arrayList = arrayList5;
                arrayList2 = arrayList6;
                zArr = zArr2;
            } catch (IllegalArgumentException unused) {
                Log.e(TAG, "Failed parsing the recommended stream configurations!");
                return null;
            }
        } else {
            recommendedStreamConfigurationArr = recommendedStreamConfigurationArr3;
            arrayList = arrayList5;
            arrayList2 = arrayList6;
            zArr = zArr2;
        }
        ArrayList<ArrayList<StreamConfiguration>> arrayList8 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList9 = new ArrayList<>();
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList10 = new ArrayList<>();
        if (recommendedStreamConfigurationArr4 != null) {
            recommendedStreamConfigurationArr2 = recommendedStreamConfigurationArr4;
            arrayList3 = arrayList7;
            cameraMetadataNative = this;
            try {
                cameraMetadataNative.parseRecommendedConfigurations(recommendedStreamConfigurationArr2, streamConfigurationMap3, true, arrayList8, arrayList9, arrayList10, null);
            } catch (IllegalArgumentException unused2) {
                Log.e(TAG, "Failed parsing the recommended depth stream configurations!");
                return null;
            }
        } else {
            recommendedStreamConfigurationArr2 = recommendedStreamConfigurationArr4;
            arrayList3 = arrayList7;
            cameraMetadataNative = this;
        }
        ReprocessFormatsMap reprocessFormatsMap = (ReprocessFormatsMap) cameraMetadataNative.getBase(CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP);
        HighSpeedVideoConfiguration[] highSpeedVideoConfigurationArr = (HighSpeedVideoConfiguration[]) cameraMetadataNative.getBase(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS);
        boolean zIsBurstSupported = cameraMetadataNative.isBurstSupported();
        arrayList4.ensureCapacity(32);
        int i2 = 0;
        while (i2 < i) {
            StreamConfigurationData streamConfigurationData = new StreamConfigurationData();
            if (recommendedStreamConfigurationArr != null) {
                cameraMetadataNative.initializeStreamConfigurationData(arrayList.get(i2), arrayList2.get(i2), arrayList3.get(i2), streamConfigurationData);
            }
            StreamConfigurationData streamConfigurationData2 = new StreamConfigurationData();
            if (recommendedStreamConfigurationArr2 != null) {
                cameraMetadataNative.initializeStreamConfigurationData(arrayList8.get(i2), arrayList9.get(i2), arrayList10.get(i2), streamConfigurationData2);
            }
            if ((streamConfigurationData.streamConfigurationArray == null || streamConfigurationData.streamConfigurationArray.length == 0) && (streamConfigurationData2.streamConfigurationArray == null || streamConfigurationData2.streamConfigurationArray.length == 0)) {
                cameraMetadataNativeIA = null;
                arrayList4.add(null);
            } else if (i2 == 0) {
                streamConfigurationMap = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, zIsBurstSupported, zArr[i2]);
                streamConfigurationMap2 = streamConfigurationMap;
                arrayList4.add(new RecommendedStreamConfigurationMap(streamConfigurationMap2, i2, zArr[i2]));
                cameraMetadataNativeIA = null;
            } else {
                if (i2 == 1) {
                    streamConfigurationMap2 = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, highSpeedVideoConfigurationArr, null, zIsBurstSupported, zArr[i2]);
                } else if (i2 != 2) {
                    if (i2 != 4) {
                        if (i2 != 5 && i2 != 6) {
                            streamConfigurationMap = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, streamConfigurationData2.streamConfigurationArray, streamConfigurationData2.minDurationArray, streamConfigurationData2.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, null, null, null, zIsBurstSupported, zArr[i2]);
                        }
                        streamConfigurationMap2 = streamConfigurationMap;
                    } else {
                        streamConfigurationMap2 = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, streamConfigurationData2.streamConfigurationArray, streamConfigurationData2.minDurationArray, streamConfigurationData2.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, null, null, reprocessFormatsMap, zIsBurstSupported, zArr[i2]);
                    }
                }
                arrayList4.add(new RecommendedStreamConfigurationMap(streamConfigurationMap2, i2, zArr[i2]));
                cameraMetadataNativeIA = null;
            }
            i2++;
            cameraMetadataNativeIA2 = cameraMetadataNativeIA;
            i = 32;
            cameraMetadataNative = this;
        }
        return arrayList4;
    }

    private boolean isCapabilitySupported(int i) {
        for (int i2 : (int[]) getBase(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    public boolean isUltraHighResolutionSensor() {
        return isCapabilitySupported(16);
    }

    private boolean isBurstSupported() {
        return isCapabilitySupported(6);
    }

    private boolean isPreviewStabilizationSupported() {
        int[] iArr = (int[]) getBase(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
        if (iArr == null) {
            return false;
        }
        for (int i : iArr) {
            if (i == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isCroppedRawSupported() {
        long[] jArr = (long[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_STREAM_USE_CASES);
        if (jArr == null) {
            return false;
        }
        for (long j : jArr) {
            if (j == 6) {
                return true;
            }
        }
        return false;
    }

    private MandatoryStreamCombination[] getMandatoryStreamCombinationsHelper(int i) {
        List<MandatoryStreamCombination> availableMandatoryMaximumResolutionStreamCombinations;
        int[] iArr = (int[]) getBase(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        ArrayList arrayList = new ArrayList();
        arrayList.ensureCapacity(iArr.length);
        for (int i2 : iArr) {
            arrayList.add(new Integer(i2));
        }
        MandatoryStreamCombination.Builder builder = new MandatoryStreamCombination.Builder(this.mCameraId, ((Integer) getBase(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue(), this.mDisplaySize, arrayList, getStreamConfigurationMap(), getStreamConfigurationMapMaximumResolution(), isPreviewStabilizationSupported(), isCroppedRawSupported());
        if (i == 1) {
            availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryMaximumResolutionStreamCombinations();
        } else if (i == 2) {
            availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryConcurrentStreamCombinations();
        } else if (i == 3) {
            availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatory10BitStreamCombinations();
        } else if (i == 4) {
            availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryStreamUseCaseCombinations();
        } else if (i == 5) {
            availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryPreviewStabilizedStreamCombinations();
        } else {
            availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryStreamCombinations();
        }
        if (availableMandatoryMaximumResolutionStreamCombinations == null || availableMandatoryMaximumResolutionStreamCombinations.isEmpty()) {
            return null;
        }
        return (MandatoryStreamCombination[]) availableMandatoryMaximumResolutionStreamCombinations.toArray(new MandatoryStreamCombination[availableMandatoryMaximumResolutionStreamCombinations.size()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MandatoryStreamCombination[] getMandatory10BitStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MandatoryStreamCombination[] getMandatoryConcurrentStreamCombinations() {
        if (this.mHasMandatoryConcurrentStreams) {
            return getMandatoryStreamCombinationsHelper(2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MandatoryStreamCombination[] getMandatoryMaximumResolutionStreamCombinations() {
        if (isUltraHighResolutionSensor()) {
            return getMandatoryStreamCombinationsHelper(1);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MandatoryStreamCombination[] getMandatoryStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MandatoryStreamCombination[] getMandatoryUseCaseStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MandatoryStreamCombination[] getMandatoryPreviewStabilizationStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public StreamConfigurationMap getStreamConfigurationMap() {
        StreamConfiguration[] streamConfigurationArr;
        StreamConfigurationDuration[] streamConfigurationDurationArr;
        StreamConfigurationDuration[] streamConfigurationDurationArr2;
        StreamConfiguration[] streamConfigurationArr2 = (StreamConfiguration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr3 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr4 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS);
        StreamConfiguration[] streamConfigurationArr3 = (StreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr5 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr6 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS);
        StreamConfiguration[] streamConfigurationArr4 = (StreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr7 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr8 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS);
        StreamConfiguration[] streamConfigurationArr5 = (StreamConfiguration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr9 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS);
        StreamConfigurationDuration[] streamConfigurationDurationArr10 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STALL_DURATIONS);
        if (Flags.cameraHeifGainmap()) {
            streamConfigurationArr = (StreamConfiguration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_ULTRA_HDR_STREAM_CONFIGURATIONS);
            streamConfigurationDurationArr = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_ULTRA_HDR_MIN_FRAME_DURATIONS);
            streamConfigurationDurationArr2 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_ULTRA_HDR_STALL_DURATIONS);
        } else {
            streamConfigurationArr = null;
            streamConfigurationDurationArr = null;
            streamConfigurationDurationArr2 = null;
        }
        return new StreamConfigurationMap(streamConfigurationArr2, streamConfigurationDurationArr3, streamConfigurationDurationArr4, streamConfigurationArr3, streamConfigurationDurationArr5, streamConfigurationDurationArr6, streamConfigurationArr4, streamConfigurationDurationArr7, streamConfigurationDurationArr8, streamConfigurationArr5, streamConfigurationDurationArr9, streamConfigurationDurationArr10, (StreamConfiguration[]) getBase(CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS), streamConfigurationArr, streamConfigurationDurationArr, streamConfigurationDurationArr2, (HighSpeedVideoConfiguration[]) getBase(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS), (ReprocessFormatsMap) getBase(CameraCharacteristics.SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP), isBurstSupported());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SharedSessionConfiguration getSharedSessionConfiguration() {
        if (!Flags.cameraMultiClient()) {
            return null;
        }
        Integer num = (Integer) getBase(CameraCharacteristics.SHARED_SESSION_COLOR_SPACE);
        long[] jArr = (long[]) getBase(CameraCharacteristics.SHARED_SESSION_OUTPUT_CONFIGURATIONS);
        if (num == null || jArr == null) {
            return null;
        }
        return new SharedSessionConfiguration(num.intValue(), jArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public StreamConfigurationMap getStreamConfigurationMapMaximumResolution() {
        StreamConfiguration[] streamConfigurationArr;
        StreamConfigurationDuration[] streamConfigurationDurationArr;
        StreamConfigurationDuration[] streamConfigurationDurationArr2;
        StreamConfiguration[] streamConfigurationArr2 = (StreamConfiguration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr3 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr4 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS_MAXIMUM_RESOLUTION);
        if (streamConfigurationArr2 == null || streamConfigurationDurationArr3 == null || streamConfigurationDurationArr4 == null) {
            return null;
        }
        StreamConfiguration[] streamConfigurationArr3 = (StreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr5 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr6 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION);
        StreamConfiguration[] streamConfigurationArr4 = (StreamConfiguration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr7 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr8 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION);
        StreamConfiguration[] streamConfigurationArr5 = (StreamConfiguration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr9 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION);
        StreamConfigurationDuration[] streamConfigurationDurationArr10 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STALL_DURATIONS_MAXIMUM_RESOLUTION);
        if (Flags.cameraHeifGainmap()) {
            streamConfigurationArr = (StreamConfiguration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_ULTRA_HDR_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION);
            streamConfigurationDurationArr = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_ULTRA_HDR_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION);
            streamConfigurationDurationArr2 = (StreamConfigurationDuration[]) getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_ULTRA_HDR_STALL_DURATIONS_MAXIMUM_RESOLUTION);
        } else {
            streamConfigurationArr = null;
            streamConfigurationDurationArr = null;
            streamConfigurationDurationArr2 = null;
        }
        return new StreamConfigurationMap(streamConfigurationArr2, streamConfigurationDurationArr3, streamConfigurationDurationArr4, streamConfigurationArr3, streamConfigurationDurationArr5, streamConfigurationDurationArr6, streamConfigurationArr4, streamConfigurationDurationArr7, streamConfigurationDurationArr8, streamConfigurationArr5, streamConfigurationDurationArr9, streamConfigurationDurationArr10, (StreamConfiguration[]) getBase(CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION), (StreamConfigurationDuration[]) getBase(CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS_MAXIMUM_RESOLUTION), streamConfigurationArr, streamConfigurationDurationArr, streamConfigurationDurationArr2, (HighSpeedVideoConfiguration[]) getBase(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS_MAXIMUM_RESOLUTION), (ReprocessFormatsMap) getBase(CameraCharacteristics.SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP_MAXIMUM_RESOLUTION), isBurstSupported(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> Integer getMaxRegions(Key<T> key) {
        int[] iArr = (int[]) getBase(CameraCharacteristics.CONTROL_MAX_REGIONS);
        if (iArr == null) {
            return null;
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AE)) {
            return Integer.valueOf(iArr[0]);
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB)) {
            return Integer.valueOf(iArr[1]);
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)) {
            return Integer.valueOf(iArr[2]);
        }
        throw new AssertionError("Invalid key " + key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> Integer getMaxNumOutputs(Key<T> key) {
        int[] iArr = (int[]) getBase(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_STREAMS);
        if (iArr == null) {
            return null;
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW)) {
            return Integer.valueOf(iArr[0]);
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC)) {
            return Integer.valueOf(iArr[1]);
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING)) {
            return Integer.valueOf(iArr[2]);
        }
        throw new AssertionError("Invalid key " + key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> TonemapCurve getTonemapCurve() {
        float[] fArr = (float[]) getBase(CaptureRequest.TONEMAP_CURVE_RED);
        float[] fArr2 = (float[]) getBase(CaptureRequest.TONEMAP_CURVE_GREEN);
        float[] fArr3 = (float[]) getBase(CaptureRequest.TONEMAP_CURVE_BLUE);
        if (areValuesAllNull(fArr, fArr2, fArr3)) {
            return null;
        }
        if (fArr == null || fArr2 == null || fArr3 == null) {
            Log.w(TAG, "getTonemapCurve - missing tone curve components");
            return null;
        }
        return new TonemapCurve(fArr, fArr2, fArr3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public OisSample[] getOisSamples() {
        long[] jArr = (long[]) getBase(CaptureResult.STATISTICS_OIS_TIMESTAMPS);
        float[] fArr = (float[]) getBase(CaptureResult.STATISTICS_OIS_X_SHIFTS);
        float[] fArr2 = (float[]) getBase(CaptureResult.STATISTICS_OIS_Y_SHIFTS);
        if (jArr == null) {
            if (fArr != null) {
                throw new AssertionError("timestamps is null but xShifts is not");
            }
            if (fArr2 == null) {
                return null;
            }
            throw new AssertionError("timestamps is null but yShifts is not");
        }
        if (fArr == null) {
            throw new AssertionError("timestamps is not null but xShifts is");
        }
        if (fArr2 == null) {
            throw new AssertionError("timestamps is not null but yShifts is");
        }
        if (fArr.length != jArr.length) {
            throw new AssertionError(String.format("timestamps has %d entries but xShifts has %d", Integer.valueOf(jArr.length), Integer.valueOf(fArr.length)));
        }
        if (fArr2.length != jArr.length) {
            throw new AssertionError(String.format("timestamps has %d entries but yShifts has %d", Integer.valueOf(jArr.length), Integer.valueOf(fArr2.length)));
        }
        OisSample[] oisSampleArr = new OisSample[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            oisSampleArr[i] = new OisSample(jArr[i], fArr[i], fArr2[i]);
        }
        return oisSampleArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setLensIntrinsicsSamples(LensIntrinsicsSample[] lensIntrinsicsSampleArr) {
        if (lensIntrinsicsSampleArr == null) {
            return false;
        }
        long[] jArr = new long[lensIntrinsicsSampleArr.length];
        float[] fArr = new float[lensIntrinsicsSampleArr.length * 5];
        for (int i = 0; i < lensIntrinsicsSampleArr.length; i++) {
            jArr[i] = lensIntrinsicsSampleArr[i].getTimestampNanos();
            System.arraycopy(lensIntrinsicsSampleArr[i].getLensIntrinsics(), 0, fArr, i * 5, 5);
        }
        setBase((CaptureResult.Key<CaptureResult.Key<float[]>>) CaptureResult.STATISTICS_LENS_INTRINSIC_SAMPLES, (CaptureResult.Key<float[]>) fArr);
        setBase((CaptureResult.Key<CaptureResult.Key<long[]>>) CaptureResult.STATISTICS_LENS_INTRINSIC_TIMESTAMPS, (CaptureResult.Key<long[]>) jArr);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LensIntrinsicsSample[] getLensIntrinsicSamples() {
        long[] jArr = (long[]) getBase(CaptureResult.STATISTICS_LENS_INTRINSIC_TIMESTAMPS);
        float[] fArr = (float[]) getBase(CaptureResult.STATISTICS_LENS_INTRINSIC_SAMPLES);
        if (jArr == null) {
            if (fArr == null) {
                return null;
            }
            throw new AssertionError("timestamps is null but intrinsics is not");
        }
        if (fArr == null) {
            throw new AssertionError("timestamps is not null but intrinsics is");
        }
        if (fArr.length % 5 != 0) {
            throw new AssertionError("intrinsics are not multiple of 5");
        }
        if (fArr.length / 5 != jArr.length) {
            throw new AssertionError(String.format("timestamps has %d entries but intrinsics has %d", Integer.valueOf(jArr.length), Integer.valueOf(fArr.length / 5)));
        }
        LensIntrinsicsSample[] lensIntrinsicsSampleArr = new LensIntrinsicsSample[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            int i2 = i * 5;
            lensIntrinsicsSampleArr[i] = new LensIntrinsicsSample(jArr[i], Arrays.copyOfRange(fArr, i2, i2 + 5));
        }
        return lensIntrinsicsSampleArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Capability[] getExtendedSceneModeCapabilities() {
        int length;
        float fFloatValue;
        float fFloatValue2;
        int[] iArr = (int[]) getBase(CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_MAX_SIZES);
        float[] fArr = (float[]) getBase(CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_ZOOM_RATIO_RANGES);
        Range range = (Range) getBase(CameraCharacteristics.CONTROL_ZOOM_RATIO_RANGE);
        float fFloatValue3 = ((Float) getBase(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue();
        if (iArr == null) {
            return null;
        }
        if (iArr.length % 3 != 0) {
            throw new AssertionError("availableExtendedSceneModeMaxSizes must be tuples of [mode, width, height]");
        }
        int length2 = iArr.length / 3;
        if (fArr == null) {
            length = 0;
        } else {
            if (fArr.length % 2 != 0) {
                throw new AssertionError("availableExtendedSceneModeZoomRanges must be tuples of [minZoom, maxZoom]");
            }
            length = fArr.length / 2;
            if (length2 - length != 1) {
                throw new AssertionError("Number of extended scene mode zoom ranges must be 1 less than number of supported modes");
            }
        }
        if (range != null) {
            fFloatValue2 = ((Float) range.getLower()).floatValue();
            fFloatValue = ((Float) range.getUpper()).floatValue();
        } else {
            fFloatValue = fFloatValue3;
            fFloatValue2 = 1.0f;
        }
        Capability[] capabilityArr = new Capability[length2];
        int i = 0;
        for (int i2 = 0; i2 < length2; i2++) {
            int i3 = i2 * 3;
            int i4 = iArr[i3];
            int i5 = iArr[i3 + 1];
            int i6 = iArr[i3 + 2];
            if (i4 != 0 && i < length) {
                Size size = new Size(i5, i6);
                int i7 = i * 2;
                capabilityArr[i2] = new Capability(i4, size, new Range(Float.valueOf(fArr[i7]), Float.valueOf(fArr[i7 + 1])));
                i++;
            } else {
                capabilityArr[i2] = new Capability(i4, new Size(i5, i6), new Range(Float.valueOf(fFloatValue2), Float.valueOf(fFloatValue)));
            }
        }
        return capabilityArr;
    }

    private <T> void setBase(CameraCharacteristics.Key<T> key, T t) {
        setBase((Key<Key<T>>) key.getNativeKey(), (Key<T>) t);
    }

    private <T> void setBase(CaptureResult.Key<T> key, T t) {
        setBase((Key<Key<T>>) key.getNativeKey(), (Key<T>) t);
    }

    private <T> void setBase(CaptureRequest.Key<T> key, T t) {
        setBase((Key<Key<T>>) key.getNativeKey(), (Key<T>) t);
    }

    private synchronized <T> void setBase(Key<T> key, T t) {
        int iNativeGetTagFromKeyLocal;
        if (key.hasTag()) {
            iNativeGetTagFromKeyLocal = key.getTag();
        } else {
            iNativeGetTagFromKeyLocal = nativeGetTagFromKeyLocal(this.mMetadataPtr, key.getName());
            key.cacheTag(iNativeGetTagFromKeyLocal);
        }
        if (t == null) {
            writeValues(iNativeGetTagFromKeyLocal, null);
            return;
        }
        Marshaler marshalerForKey = getMarshalerForKey(key, nativeGetTypeFromTagLocal(this.mMetadataPtr, iNativeGetTagFromKeyLocal));
        byte[] bArr = new byte[marshalerForKey.calculateMarshalSize(t)];
        marshalerForKey.marshal(t, ByteBuffer.wrap(bArr).order(ByteOrder.nativeOrder()));
        writeValues(iNativeGetTagFromKeyLocal, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setAvailableFormats(int[] iArr) {
        if (iArr == null) {
            return false;
        }
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = iArr[i];
            if (iArr[i] == 256) {
                iArr2[i] = 33;
            }
        }
        setBase((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.SCALER_AVAILABLE_FORMATS, (CameraCharacteristics.Key<int[]>) iArr2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setFaceRectangles(Rect[] rectArr) {
        if (rectArr == null) {
            return false;
        }
        int length = rectArr.length;
        Rect[] rectArr2 = new Rect[length];
        for (int i = 0; i < length; i++) {
            rectArr2[i] = new Rect(rectArr[i].left, rectArr[i].top, rectArr[i].right + rectArr[i].left, rectArr[i].bottom + rectArr[i].top);
        }
        setBase((CaptureResult.Key<CaptureResult.Key<Rect[]>>) CaptureResult.STATISTICS_FACE_RECTANGLES, (CaptureResult.Key<Rect[]>) rectArr2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setTonemapCurve(TonemapCurve tonemapCurve) {
        if (tonemapCurve == null) {
            return false;
        }
        float[][] fArr = new float[3][];
        for (int i = 0; i <= 2; i++) {
            float[] fArr2 = new float[tonemapCurve.getPointCount(i) * 2];
            fArr[i] = fArr2;
            tonemapCurve.copyColorCurve(i, fArr2, 0);
        }
        setBase((CaptureRequest.Key<CaptureRequest.Key<float[]>>) CaptureRequest.TONEMAP_CURVE_RED, (CaptureRequest.Key<float[]>) fArr[0]);
        setBase((CaptureRequest.Key<CaptureRequest.Key<float[]>>) CaptureRequest.TONEMAP_CURVE_GREEN, (CaptureRequest.Key<float[]>) fArr[1]);
        setBase((CaptureRequest.Key<CaptureRequest.Key<float[]>>) CaptureRequest.TONEMAP_CURVE_BLUE, (CaptureRequest.Key<float[]>) fArr[2]);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setScalerCropRegion(Rect rect) {
        if (rect == null) {
            return false;
        }
        setBase((CaptureRequest.Key<CaptureRequest.Key<Boolean>>) CaptureRequest.SCALER_CROP_REGION_SET, (CaptureRequest.Key<Boolean>) true);
        setBase((CaptureRequest.Key<CaptureRequest.Key<Rect>>) CaptureRequest.SCALER_CROP_REGION, (CaptureRequest.Key<Rect>) rect);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setAFRegions(T t) {
        if (t == null) {
            return false;
        }
        setBase((CaptureRequest.Key<CaptureRequest.Key<Boolean>>) CaptureRequest.CONTROL_AF_REGIONS_SET, (CaptureRequest.Key<Boolean>) true);
        setBase((CaptureRequest.Key<CaptureRequest.Key<MeteringRectangle[]>>) CaptureRequest.CONTROL_AF_REGIONS, (CaptureRequest.Key<MeteringRectangle[]>) t);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setAERegions(T t) {
        if (t == null) {
            return false;
        }
        setBase((CaptureRequest.Key<CaptureRequest.Key<Boolean>>) CaptureRequest.CONTROL_AE_REGIONS_SET, (CaptureRequest.Key<Boolean>) true);
        setBase((CaptureRequest.Key<CaptureRequest.Key<MeteringRectangle[]>>) CaptureRequest.CONTROL_AE_REGIONS, (CaptureRequest.Key<MeteringRectangle[]>) t);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setAWBRegions(T t) {
        if (t == null) {
            return false;
        }
        setBase((CaptureRequest.Key<CaptureRequest.Key<Boolean>>) CaptureRequest.CONTROL_AWB_REGIONS_SET, (CaptureRequest.Key<Boolean>) true);
        setBase((CaptureRequest.Key<CaptureRequest.Key<MeteringRectangle[]>>) CaptureRequest.CONTROL_AWB_REGIONS, (CaptureRequest.Key<MeteringRectangle[]>) t);
        return true;
    }

    private synchronized void updateNativeAllocation() {
        long jNativeGetBufferSize = nativeGetBufferSize(this.mMetadataPtr);
        long j = this.mBufferSize;
        if (jNativeGetBufferSize != j) {
            if (j > 0) {
                VMRuntime.getRuntime().registerNativeFree(this.mBufferSize);
            }
            this.mBufferSize = jNativeGetBufferSize;
            if (jNativeGetBufferSize > 0) {
                VMRuntime.getRuntime().registerNativeAllocation(this.mBufferSize);
            }
        }
    }

    public void setCameraId(int i) {
        this.mCameraId = i;
    }

    public void setHasMandatoryConcurrentStreams(boolean z) {
        this.mHasMandatoryConcurrentStreams = z;
    }

    public void setDisplaySize(Size size) {
        this.mDisplaySize = size;
    }

    public void setMultiResolutionStreamConfigurationMap(Map<String, StreamConfiguration[]> map) {
        this.mMultiResolutionStreamConfigurationMap = new MultiResolutionStreamConfigurationMap(map);
    }

    public MultiResolutionStreamConfigurationMap getMultiResolutionStreamConfigurationMap() {
        return this.mMultiResolutionStreamConfigurationMap;
    }

    public synchronized void swap(CameraMetadataNative cameraMetadataNative) {
        nativeSwap(this.mMetadataPtr, cameraMetadataNative.mMetadataPtr);
        this.mCameraId = cameraMetadataNative.mCameraId;
        this.mHasMandatoryConcurrentStreams = cameraMetadataNative.mHasMandatoryConcurrentStreams;
        this.mDisplaySize = cameraMetadataNative.mDisplaySize;
        this.mMultiResolutionStreamConfigurationMap = cameraMetadataNative.mMultiResolutionStreamConfigurationMap;
        updateNativeAllocation();
        cameraMetadataNative.updateNativeAllocation();
    }

    public synchronized void setVendorId(long j) {
        nativeSetVendorId(this.mMetadataPtr, j);
    }

    public synchronized int getEntryCount() {
        return nativeGetEntryCount(this.mMetadataPtr);
    }

    public synchronized boolean isEmpty() {
        return nativeIsEmpty(this.mMetadataPtr);
    }

    public long getMetadataPtr() {
        return this.mMetadataPtr;
    }

    public synchronized <K> ArrayList<K> getAllVendorKeys(Class<K> cls) {
        if (cls == null) {
            throw new NullPointerException();
        }
        return nativeGetAllVendorKeys(this.mMetadataPtr, cls);
    }

    public static int getTag(String str) {
        return nativeGetTagFromKey(str, Long.MAX_VALUE);
    }

    public static int getTag(String str, long j) {
        return nativeGetTagFromKey(str, j);
    }

    public static int getNativeType(int i, long j) {
        return nativeGetTypeFromTag(i, j);
    }

    public synchronized void writeValues(int i, byte[] bArr) {
        nativeWriteValues(i, bArr, this.mMetadataPtr);
    }

    public synchronized byte[] readValues(int i) {
        return nativeReadValues(i, this.mMetadataPtr);
    }

    public synchronized void dumpToLog() {
        try {
            nativeDump(this.mMetadataPtr);
        } catch (IOException e) {
            Log.wtf(TAG, "Dump logging failed", e);
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private static <T> Marshaler<T> getMarshalerForKey(Key<T> key, int i) {
        return MarshalRegistry.getMarshaler(key.getTypeReference(), i);
    }

    private static void registerAllMarshalers() {
        MarshalQueryable[] marshalQueryableArr = {new MarshalQueryablePrimitive(), new MarshalQueryableEnum(), new MarshalQueryableArray(), new MarshalQueryableBoolean(), new MarshalQueryableNativeByteToInteger(), new MarshalQueryableRect(), new MarshalQueryableSize(), new MarshalQueryableSizeF(), new MarshalQueryableString(), new MarshalQueryableReprocessFormatsMap(), new MarshalQueryableRange(), new MarshalQueryablePair(), new MarshalQueryableMeteringRectangle(), new MarshalQueryableColorSpaceTransform(), new MarshalQueryableStreamConfiguration(), new MarshalQueryableStreamConfigurationDuration(), new MarshalQueryableRggbChannelVector(), new MarshalQueryableBlackLevelPattern(), new MarshalQueryableHighSpeedVideoConfiguration(), new MarshalQueryableRecommendedStreamConfiguration(), new MarshalQueryableParcelable()};
        for (int i = 0; i < 21; i++) {
            MarshalRegistry.registerMarshalQueryable(marshalQueryableArr[i]);
        }
    }

    private static boolean areValuesAllNull(Object... objArr) {
        for (Object obj : objArr) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    public Set<String> getPhysicalCameraIds() {
        int[] iArr = (int[]) get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        if (iArr == null) {
            throw new AssertionError("android.request.availableCapabilities must be non-null in the characteristics");
        }
        if (!ArrayUtils.contains(iArr, 11)) {
            return Collections.EMPTY_SET;
        }
        try {
            return Collections.unmodifiableSet(new HashSet(Arrays.asList(new String((byte[]) get(CameraCharacteristics.LOGICAL_MULTI_CAMERA_PHYSICAL_IDS), "UTF-8").split("\u0000"))));
        } catch (UnsupportedEncodingException unused) {
            throw new AssertionError("android.logicalCam.physicalIds must be UTF-8 string");
        }
    }
}
