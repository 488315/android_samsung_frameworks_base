package androidx.constraintlayout.core.motion.utils;

import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyCache {
    public final HashMap mMap = new HashMap();

    public final float getFloatValue(Object obj, String str) {
        HashMap map;
        float[] fArr;
        if (this.mMap.containsKey(obj) && (map = (HashMap) this.mMap.get(obj)) != null && map.containsKey(str) && (fArr = (float[]) map.get(str)) != null && fArr.length > 0) {
            return fArr[0];
        }
        return Float.NaN;
    }
}
