package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.impl.CameraMetadataNative;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SystemApi
public class CharacteristicsMap {
    private final HashMap<String, CameraCharacteristics> mCharMap = new HashMap<>();

    CharacteristicsMap(Map<String, CameraMetadataNative> charsMap) {
        for (Map.Entry<String, CameraMetadataNative> entry : charsMap.entrySet()) {
            this.mCharMap.put(entry.getKey(), new CameraCharacteristics(entry.getValue()));
        }
    }

    public Set<String> getCameraIds() {
        return this.mCharMap.keySet();
    }

    public CameraCharacteristics get(String cameraId) {
        return this.mCharMap.get(cameraId);
    }
}
