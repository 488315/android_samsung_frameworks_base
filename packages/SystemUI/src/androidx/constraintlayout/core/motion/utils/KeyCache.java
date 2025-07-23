package androidx.constraintlayout.core.motion.utils;

import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyCache {
    public final HashMap mMap = new HashMap();

    public final float getFloatValue(Object obj, String str) {
        HashMap hashMap;
        float[] fArr;
        if (this.mMap.containsKey(obj) && (hashMap = (HashMap) this.mMap.get(obj)) != null && hashMap.containsKey(str) && (fArr = (float[]) hashMap.get(str)) != null && fArr.length > 0) {
            return fArr[0];
        }
        return Float.NaN;
    }
}
