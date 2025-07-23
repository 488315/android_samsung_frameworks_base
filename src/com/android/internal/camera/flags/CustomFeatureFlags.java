package com.android.internal.camera.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_AE_PRIORITY, Flags.FLAG_ANALYTICS_24Q3, Flags.FLAG_API1_RELEASE_BINDERLOCK_BEFORE_CAMERASERVICE_DISCONNECT, Flags.FLAG_BUMP_PREVIEW_FRAME_SPACE_PRIORITY, Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, Flags.FLAG_CAMERA_DEVICE_SETUP, Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, Flags.FLAG_CAMERA_HEIF_GAINMAP, Flags.FLAG_CAMERA_HSUM_PERMISSION, Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, Flags.FLAG_CAMERA_MULTI_CLIENT, Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, Flags.FLAG_COLOR_TEMPERATURE, Flags.FLAG_CONCERT_MODE, Flags.FLAG_DATA_DELIVERY_PERMISSION_CHECKS, Flags.FLAG_DEPTH_JPEG_EXTENSIONS, Flags.FLAG_DESKTOP_EFFECTS, Flags.FLAG_DUMPSYS_REQUEST_STREAM_IDS, Flags.FLAG_ENABLE_HAL_ABORT_FROM_CAMERASERVICEWATCHDOG, Flags.FLAG_ENABLE_STREAM_RECONFIGURATION_FOR_UNCHANGED_STREAMS, Flags.FLAG_EXTENSION_10_BIT, Flags.FLAG_FEATURE_COMBINATION_BAKLAVA, Flags.FLAG_FEATURE_COMBINATION_QUERY, Flags.FLAG_FMQ_METADATA, Flags.FLAG_INJECT_SESSION_PARAMS, Flags.FLAG_METADATA_RESIZE_FIX, Flags.FLAG_MIRROR_MODE_SHARED_SURFACES, Flags.FLAG_MULTI_RES_RAW_REPROCESSING, Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_PUBLIC, Flags.FLAG_NIGHT_MODE_INDICATOR, Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, Flags.FLAG_SINGLE_THREAD_EXECUTOR_NAMING, Flags.FLAG_ZOOM_METHOD, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean aePriority() {
        return getValue(Flags.FLAG_AE_PRIORITY, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aePriority();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean analytics24q3() {
        return getValue(Flags.FLAG_ANALYTICS_24Q3, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).analytics24q3();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean api1ReleaseBinderlockBeforeCameraserviceDisconnect() {
        return getValue(Flags.FLAG_API1_RELEASE_BINDERLOCK_BEFORE_CAMERASERVICE_DISCONNECT, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).api1ReleaseBinderlockBeforeCameraserviceDisconnect();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean bumpPreviewFrameSpacePriority() {
        return getValue(Flags.FLAG_BUMP_PREVIEW_FRAME_SPACE_PRIORITY, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bumpPreviewFrameSpacePriority();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraAeModeLowLightBoost() {
        return getValue(Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraAeModeLowLightBoost();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraDeviceSetup() {
        return getValue(Flags.FLAG_CAMERA_DEVICE_SETUP, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraDeviceSetup();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraExtensionsCharacteristicsGet() {
        return getValue(Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraExtensionsCharacteristicsGet();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraHeifGainmap() {
        return getValue(Flags.FLAG_CAMERA_HEIF_GAINMAP, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraHeifGainmap();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraHsumPermission() {
        return getValue(Flags.FLAG_CAMERA_HSUM_PERMISSION, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraHsumPermission();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraManualFlashStrengthControl() {
        return getValue(Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraManualFlashStrengthControl();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraMultiClient() {
        return getValue(Flags.FLAG_CAMERA_MULTI_CLIENT, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraMultiClient();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraPrivacyAllowlist() {
        return getValue(Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraPrivacyAllowlist();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean colorTemperature() {
        return getValue(Flags.FLAG_COLOR_TEMPERATURE, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).colorTemperature();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean concertMode() {
        return getValue(Flags.FLAG_CONCERT_MODE, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).concertMode();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean dataDeliveryPermissionChecks() {
        return getValue(Flags.FLAG_DATA_DELIVERY_PERMISSION_CHECKS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dataDeliveryPermissionChecks();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean depthJpegExtensions() {
        return getValue(Flags.FLAG_DEPTH_JPEG_EXTENSIONS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).depthJpegExtensions();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean desktopEffects() {
        return getValue(Flags.FLAG_DESKTOP_EFFECTS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).desktopEffects();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean dumpsysRequestStreamIds() {
        return getValue(Flags.FLAG_DUMPSYS_REQUEST_STREAM_IDS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dumpsysRequestStreamIds();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean enableHalAbortFromCameraservicewatchdog() {
        return getValue(Flags.FLAG_ENABLE_HAL_ABORT_FROM_CAMERASERVICEWATCHDOG, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableHalAbortFromCameraservicewatchdog();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean enableStreamReconfigurationForUnchangedStreams() {
        return getValue(Flags.FLAG_ENABLE_STREAM_RECONFIGURATION_FOR_UNCHANGED_STREAMS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableStreamReconfigurationForUnchangedStreams();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean extension10Bit() {
        return getValue(Flags.FLAG_EXTENSION_10_BIT, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).extension10Bit();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean featureCombinationBaklava() {
        return getValue(Flags.FLAG_FEATURE_COMBINATION_BAKLAVA, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).featureCombinationBaklava();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean featureCombinationQuery() {
        return getValue(Flags.FLAG_FEATURE_COMBINATION_QUERY, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).featureCombinationQuery();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean fmqMetadata() {
        return getValue(Flags.FLAG_FMQ_METADATA, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fmqMetadata();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean injectSessionParams() {
        return getValue(Flags.FLAG_INJECT_SESSION_PARAMS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).injectSessionParams();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean metadataResizeFix() {
        return getValue(Flags.FLAG_METADATA_RESIZE_FIX, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).metadataResizeFix();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean mirrorModeSharedSurfaces() {
        return getValue(Flags.FLAG_MIRROR_MODE_SHARED_SURFACES, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mirrorModeSharedSurfaces();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean multiResRawReprocessing() {
        return getValue(Flags.FLAG_MULTI_RES_RAW_REPROCESSING, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiResRawReprocessing();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean multiresolutionImagereaderUsageConfig() {
        return getValue(Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiresolutionImagereaderUsageConfig();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean multiresolutionImagereaderUsagePublic() {
        return getValue(Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_PUBLIC, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiresolutionImagereaderUsagePublic();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean nightModeIndicator() {
        return getValue(Flags.FLAG_NIGHT_MODE_INDICATOR, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nightModeIndicator();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean returnBuffersOutsideLocks() {
        return getValue(Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).returnBuffersOutsideLocks();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean singleThreadExecutorNaming() {
        return getValue(Flags.FLAG_SINGLE_THREAD_EXECUTOR_NAMING, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).singleThreadExecutorNaming();
            }
        });
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean zoomMethod() {
        return getValue(Flags.FLAG_ZOOM_METHOD, new Predicate() { // from class: com.android.internal.camera.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).zoomMethod();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_AE_PRIORITY, Flags.FLAG_ANALYTICS_24Q3, Flags.FLAG_API1_RELEASE_BINDERLOCK_BEFORE_CAMERASERVICE_DISCONNECT, Flags.FLAG_BUMP_PREVIEW_FRAME_SPACE_PRIORITY, Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, Flags.FLAG_CAMERA_DEVICE_SETUP, Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, Flags.FLAG_CAMERA_HEIF_GAINMAP, Flags.FLAG_CAMERA_HSUM_PERMISSION, Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, Flags.FLAG_CAMERA_MULTI_CLIENT, Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, Flags.FLAG_COLOR_TEMPERATURE, Flags.FLAG_CONCERT_MODE, Flags.FLAG_DATA_DELIVERY_PERMISSION_CHECKS, Flags.FLAG_DEPTH_JPEG_EXTENSIONS, Flags.FLAG_DESKTOP_EFFECTS, Flags.FLAG_DUMPSYS_REQUEST_STREAM_IDS, Flags.FLAG_ENABLE_HAL_ABORT_FROM_CAMERASERVICEWATCHDOG, Flags.FLAG_ENABLE_STREAM_RECONFIGURATION_FOR_UNCHANGED_STREAMS, Flags.FLAG_EXTENSION_10_BIT, Flags.FLAG_FEATURE_COMBINATION_BAKLAVA, Flags.FLAG_FEATURE_COMBINATION_QUERY, Flags.FLAG_FMQ_METADATA, Flags.FLAG_INJECT_SESSION_PARAMS, Flags.FLAG_METADATA_RESIZE_FIX, Flags.FLAG_MIRROR_MODE_SHARED_SURFACES, Flags.FLAG_MULTI_RES_RAW_REPROCESSING, Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_PUBLIC, Flags.FLAG_NIGHT_MODE_INDICATOR, Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, Flags.FLAG_SINGLE_THREAD_EXECUTOR_NAMING, Flags.FLAG_ZOOM_METHOD);
    }
}
