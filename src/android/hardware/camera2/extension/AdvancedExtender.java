package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SystemApi
/* loaded from: classes2.dex */
public abstract class AdvancedExtender {
    private static final String TAG = "AdvancedExtender";
    private final CameraManager mCameraManager;
    private CameraUsageTracker mCameraUsageTracker;
    private HashMap<String, Long> mMetadataVendorIdMap = new HashMap<>();

    public abstract List<CaptureRequest.Key> getAvailableCaptureRequestKeys(String str);

    public abstract List<CaptureResult.Key> getAvailableCaptureResultKeys(String str);

    public abstract List<Pair<CameraCharacteristics.Key, Object>> getAvailableCharacteristicsKeyValues();

    public abstract SessionProcessor getSessionProcessor();

    public abstract Map<Integer, List<android.util.Size>> getSupportedCaptureOutputResolutions(String str);

    public abstract Map<Integer, List<android.util.Size>> getSupportedPreviewOutputResolutions(String str);

    public abstract void initialize(String str, CharacteristicsMap characteristicsMap);

    public abstract boolean isExtensionAvailable(String str, CharacteristicsMap characteristicsMap);

    public AdvancedExtender(CameraManager cameraManager) {
        this.mCameraManager = cameraManager;
        try {
            String[] cameraIdListNoLazy = cameraManager.getCameraIdListNoLazy();
            if (cameraIdListNoLazy != null) {
                for (String str : cameraIdListNoLazy) {
                    ArrayList allVendorKeys = this.mCameraManager.getCameraCharacteristics(str).getNativeMetadata().getAllVendorKeys(CameraCharacteristics.Key.class);
                    if (allVendorKeys != null && !allVendorKeys.isEmpty()) {
                        this.mMetadataVendorIdMap.put(str, Long.valueOf(((CameraCharacteristics.Key) allVendorKeys.get(0)).getVendorId()));
                    }
                }
            }
        } catch (CameraAccessException unused) {
            Log.e(TAG, "Failed to query camera characteristics!");
        }
    }

    void setCameraUsageTracker(CameraUsageTracker cameraUsageTracker) {
        this.mCameraUsageTracker = cameraUsageTracker;
    }

    public long getMetadataVendorId(String str) {
        if (this.mMetadataVendorIdMap.containsKey(str)) {
            return this.mMetadataVendorIdMap.get(str).longValue();
        }
        return Long.MAX_VALUE;
    }

    private final class AdvancedExtenderImpl extends IAdvancedExtenderImpl.Stub {
        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public LatencyRange getEstimatedCaptureLatencyRange(String str, Size size, int i) {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPostviewResolutions(Size size) {
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

        private AdvancedExtenderImpl() {
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isExtensionAvailable(String str, Map<String, CameraMetadataNative> map) {
            return AdvancedExtender.this.isExtensionAvailable(str, new CharacteristicsMap(map));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public void init(String str, Map<String, CameraMetadataNative> map) {
            AdvancedExtender.this.initialize(str, new CharacteristicsMap(map));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPreviewOutputResolutions(String str) {
            return AdvancedExtender.initializeParcelable(AdvancedExtender.this.getSupportedPreviewOutputResolutions(str));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedCaptureOutputResolutions(String str) {
            return AdvancedExtender.initializeParcelable(AdvancedExtender.this.getSupportedCaptureOutputResolutions(str));
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public ISessionProcessorImpl getSessionProcessor() {
            SessionProcessor sessionProcessor = AdvancedExtender.this.getSessionProcessor();
            sessionProcessor.setCameraUsageTracker(AdvancedExtender.this.mCameraUsageTracker);
            return sessionProcessor.getSessionProcessorBinder();
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureRequestKeys(String str) {
            List<CaptureRequest.Key> availableCaptureRequestKeys = AdvancedExtender.this.getAvailableCaptureRequestKeys(str);
            if (availableCaptureRequestKeys.isEmpty()) {
                return null;
            }
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            long metadataVendorId = AdvancedExtender.this.getMetadataVendorId(str);
            cameraMetadataNative.setVendorId(metadataVendorId);
            int[] iArr = new int[availableCaptureRequestKeys.size()];
            Iterator<CaptureRequest.Key> it = availableCaptureRequestKeys.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = CameraMetadataNative.getTag(it.next().getName(), metadataVendorId);
                i++;
            }
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS, (CameraCharacteristics.Key<int[]>) iArr);
            return cameraMetadataNative;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureResultKeys(String str) {
            List<CaptureResult.Key> availableCaptureResultKeys = AdvancedExtender.this.getAvailableCaptureResultKeys(str);
            if (availableCaptureResultKeys.isEmpty()) {
                return null;
            }
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            long metadataVendorId = AdvancedExtender.this.getMetadataVendorId(str);
            cameraMetadataNative.setVendorId(metadataVendorId);
            int[] iArr = new int[availableCaptureResultKeys.size()];
            Iterator<CaptureResult.Key> it = availableCaptureResultKeys.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = CameraMetadataNative.getTag(it.next().getName(), metadataVendorId);
                i++;
            }
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS, (CameraCharacteristics.Key<int[]>) iArr);
            return cameraMetadataNative;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCharacteristicsKeyValues(String str) {
            List<Pair<CameraCharacteristics.Key, Object>> availableCharacteristicsKeyValues = AdvancedExtender.this.getAvailableCharacteristicsKeyValues();
            if (availableCharacteristicsKeyValues == null || availableCharacteristicsKeyValues.isEmpty()) {
                return null;
            }
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            long jLongValue = AdvancedExtender.this.mMetadataVendorIdMap.containsKey(str) ? ((Long) AdvancedExtender.this.mMetadataVendorIdMap.get(str)).longValue() : Long.MAX_VALUE;
            cameraMetadataNative.setVendorId(jLongValue);
            int[] iArr = new int[availableCharacteristicsKeyValues.size()];
            int i = 0;
            for (Pair<CameraCharacteristics.Key, Object> pair : availableCharacteristicsKeyValues) {
                iArr[i] = CameraMetadataNative.getTag(pair.first.getName(), jLongValue);
                cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key>) pair.first, (CameraCharacteristics.Key) pair.second);
                i++;
            }
            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<int[]>>) CameraCharacteristics.REQUEST_AVAILABLE_CHARACTERISTICS_KEYS, (CameraCharacteristics.Key<int[]>) iArr);
            return cameraMetadataNative;
        }
    }

    IAdvancedExtenderImpl getAdvancedExtenderBinder() {
        return new AdvancedExtenderImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<SizeList> initializeParcelable(Map<Integer, List<android.util.Size>> map) {
        if (map == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<Integer, List<android.util.Size>> entry : map.entrySet()) {
            SizeList sizeList = new SizeList();
            sizeList.format = entry.getKey().intValue();
            sizeList.sizes = new ArrayList();
            for (android.util.Size size : entry.getValue()) {
                Size size2 = new Size();
                size2.width = size.getWidth();
                size2.height = size.getHeight();
                sizeList.sizes.add(size2);
            }
            arrayList.add(sizeList);
        }
        return arrayList;
    }
}
