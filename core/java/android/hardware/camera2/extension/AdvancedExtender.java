package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SystemApi
public abstract class AdvancedExtender {
    private static final String TAG = "AdvancedExtender";
    private final CameraManager mCameraManager;
    private CameraUsageTracker mCameraUsageTracker;
    private HashMap<String, Long> mMetadataVendorIdMap = new HashMap<>();

    public abstract List<CaptureRequest.Key> getAvailableCaptureRequestKeys(String str);

    public abstract List<CaptureResult.Key> getAvailableCaptureResultKeys(String str);

    public abstract List<Pair<CameraCharacteristics.Key, Object>>
            getAvailableCharacteristicsKeyValues();

    public abstract SessionProcessor getSessionProcessor();

    public abstract Map<Integer, List<android.util.Size>> getSupportedCaptureOutputResolutions(
            String str);

    public abstract Map<Integer, List<android.util.Size>> getSupportedPreviewOutputResolutions(
            String str);

    public abstract void initialize(String str, CharacteristicsMap characteristicsMap);

    public abstract boolean isExtensionAvailable(String str, CharacteristicsMap characteristicsMap);

    public AdvancedExtender(CameraManager cameraManager) {
        this.mCameraManager = cameraManager;
        try {
            String[] cameraIds = this.mCameraManager.getCameraIdListNoLazy();
            if (cameraIds != null) {
                for (String cameraId : cameraIds) {
                    CameraCharacteristics chars =
                            this.mCameraManager.getCameraCharacteristics(cameraId);
                    ArrayList<CameraCharacteristics.Key<?>> vendorKeys =
                            chars.getNativeMetadata().getAllVendorKeys(keyClass);
                    if (vendorKeys != null && !vendorKeys.isEmpty()) {
                        this.mMetadataVendorIdMap.put(
                                cameraId, Long.valueOf(vendorKeys.get(0).getVendorId()));
                    }
                }
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to query camera characteristics!");
        }
    }

    void setCameraUsageTracker(CameraUsageTracker tracker) {
        this.mCameraUsageTracker = tracker;
    }

    public long getMetadataVendorId(String cameraId) {
        if (!this.mMetadataVendorIdMap.containsKey(cameraId)) {
            return Long.MAX_VALUE;
        }
        long vendorId = this.mMetadataVendorIdMap.get(cameraId).longValue();
        return vendorId;
    }

    private final class AdvancedExtenderImpl extends IAdvancedExtenderImpl.Stub {
        private AdvancedExtenderImpl() {}

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isExtensionAvailable(
                String cameraId, Map<String, CameraMetadataNative> charsMapNative) {
            return AdvancedExtender.this.isExtensionAvailable(
                    cameraId, new CharacteristicsMap(charsMapNative));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public void init(String cameraId, Map<String, CameraMetadataNative> charsMapNative) {
            AdvancedExtender.this.initialize(cameraId, new CharacteristicsMap(charsMapNative));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPostviewResolutions(Size captureSize) {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPreviewOutputResolutions(String cameraId) {
            return AdvancedExtender.initializeParcelable(
                    AdvancedExtender.this.getSupportedPreviewOutputResolutions(cameraId));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedCaptureOutputResolutions(String cameraId) {
            return AdvancedExtender.initializeParcelable(
                    AdvancedExtender.this.getSupportedCaptureOutputResolutions(cameraId));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public LatencyRange getEstimatedCaptureLatencyRange(
                String cameraId, Size outputSize, int format) {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public ISessionProcessorImpl getSessionProcessor() {
            SessionProcessor processor = AdvancedExtender.this.getSessionProcessor();
            processor.setCameraUsageTracker(AdvancedExtender.this.mCameraUsageTracker);
            return processor.getSessionProcessorBinder();
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureRequestKeys(String cameraId) {
            List<CaptureRequest.Key> supportedCaptureKeys =
                    AdvancedExtender.this.getAvailableCaptureRequestKeys(cameraId);
            if (!supportedCaptureKeys.isEmpty()) {
                CameraMetadataNative ret = new CameraMetadataNative();
                long vendorId = AdvancedExtender.this.getMetadataVendorId(cameraId);
                ret.setVendorId(vendorId);
                int[] requestKeyTags = new int[supportedCaptureKeys.size()];
                int i = 0;
                for (CaptureRequest.Key key : supportedCaptureKeys) {
                    requestKeyTags[i] = CameraMetadataNative.getTag(key.getName(), vendorId);
                    i++;
                }
                ret.set(
                        (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                                CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS,
                        (CameraCharacteristics.Key<int[]>) requestKeyTags);
                return ret;
            }
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureResultKeys(String cameraId) {
            List<CaptureResult.Key> supportedResultKeys =
                    AdvancedExtender.this.getAvailableCaptureResultKeys(cameraId);
            if (!supportedResultKeys.isEmpty()) {
                CameraMetadataNative ret = new CameraMetadataNative();
                long vendorId = AdvancedExtender.this.getMetadataVendorId(cameraId);
                ret.setVendorId(vendorId);
                int[] resultKeyTags = new int[supportedResultKeys.size()];
                int i = 0;
                for (CaptureResult.Key key : supportedResultKeys) {
                    resultKeyTags[i] = CameraMetadataNative.getTag(key.getName(), vendorId);
                    i++;
                }
                ret.set(
                        (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                                CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS,
                        (CameraCharacteristics.Key<int[]>) resultKeyTags);
                return ret;
            }
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isCaptureProcessProgressAvailable() {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isPostviewAvailable() {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCharacteristicsKeyValues(String cameraId) {
            List<Pair<CameraCharacteristics.Key, Object>> entries =
                    AdvancedExtender.this.getAvailableCharacteristicsKeyValues();
            if (entries != null && !entries.isEmpty()) {
                CameraMetadataNative ret = new CameraMetadataNative();
                long vendorId =
                        AdvancedExtender.this.mMetadataVendorIdMap.containsKey(cameraId)
                                ? ((Long) AdvancedExtender.this.mMetadataVendorIdMap.get(cameraId))
                                        .longValue()
                                : Long.MAX_VALUE;
                ret.setVendorId(vendorId);
                int[] characteristicsKeyTags = new int[entries.size()];
                int i = 0;
                for (Pair<CameraCharacteristics.Key, Object> entry : entries) {
                    int tag = CameraMetadataNative.getTag(entry.first.getName(), vendorId);
                    characteristicsKeyTags[i] = tag;
                    ret.set(
                            (CameraCharacteristics.Key<CameraCharacteristics.Key>) entry.first,
                            (CameraCharacteristics.Key) entry.second);
                    i++;
                }
                ret.set(
                        (CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>)
                                CameraCharacteristics.REQUEST_AVAILABLE_CHARACTERISTICS_KEYS,
                        (CameraCharacteristics.Key<int[]>) characteristicsKeyTags);
                return ret;
            }
            return null;
        }
    }

    IAdvancedExtenderImpl getAdvancedExtenderBinder() {
        return new AdvancedExtenderImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<SizeList> initializeParcelable(Map<Integer, List<android.util.Size>> sizes) {
        if (sizes == null) {
            return null;
        }
        ArrayList<SizeList> ret = new ArrayList<>(sizes.size());
        for (Map.Entry<Integer, List<android.util.Size>> entry : sizes.entrySet()) {
            SizeList sizeList = new SizeList();
            sizeList.format = entry.getKey().intValue();
            sizeList.sizes = new ArrayList();
            for (android.util.Size size : entry.getValue()) {
                Size sz = new Size();
                sz.width = size.getWidth();
                sz.height = size.getHeight();
                sizeList.sizes.add(sz);
            }
            ret.add(sizeList);
        }
        return ret;
    }
}
