package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.impl.CameraMetadataNative;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SystemApi
/* loaded from: classes2.dex */
public class CharacteristicsMap {
    private final HashMap<String, CameraCharacteristics> mCharMap = new HashMap<>();

    CharacteristicsMap(Map<String, CameraMetadataNative> map) {
        for (Map.Entry<String, CameraMetadataNative> entry : map.entrySet()) {
            this.mCharMap.put(entry.getKey(), new CameraCharacteristics(entry.getValue()));
        }
    }

    public Set<String> getCameraIds() {
        return this.mCharMap.keySet();
    }

    public CameraCharacteristics get(String str) {
        return this.mCharMap.get(str);
    }
}
