package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class KeyCache {
    HashMap<Object, HashMap<String, float[]>> mMap = new HashMap<>();

    public final float getFloatValue(Object obj, String str) {
        HashMap<String, float[]> hashMap;
        float[] fArr;
        if (this.mMap.containsKey(obj) && (hashMap = this.mMap.get(obj)) != null && hashMap.containsKey(str) && (fArr = hashMap.get(str)) != null && fArr.length > 0) {
            return fArr[0];
        }
        return Float.NaN;
    }

    public final void setFloatValue(Object obj, String str, float f) {
        if (!this.mMap.containsKey(obj)) {
            HashMap<String, float[]> hashMap = new HashMap<>();
            hashMap.put(str, new float[]{f});
            this.mMap.put(obj, hashMap);
            return;
        }
        HashMap<String, float[]> hashMap2 = this.mMap.get(obj);
        if (hashMap2 == null) {
            hashMap2 = new HashMap<>();
        }
        if (!hashMap2.containsKey(str)) {
            hashMap2.put(str, new float[]{f});
            this.mMap.put(obj, hashMap2);
            return;
        }
        float[] fArr = hashMap2.get(str);
        if (fArr == null) {
            fArr = new float[0];
        }
        if (fArr.length <= 0) {
            fArr = Arrays.copyOf(fArr, 1);
        }
        fArr[0] = f;
        hashMap2.put(str, fArr);
    }
}
