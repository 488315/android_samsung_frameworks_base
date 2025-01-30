package androidx.constraintlayout.core.motion.utils;

import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyCache {
    public final HashMap map = new HashMap();

    public final float getFloatValue(String str, Object obj) {
        HashMap hashMap;
        float[] fArr;
        HashMap hashMap2 = this.map;
        if (hashMap2.containsKey(obj) && (hashMap = (HashMap) hashMap2.get(obj)) != null && hashMap.containsKey(str) && (fArr = (float[]) hashMap.get(str)) != null && fArr.length > 0) {
            return fArr[0];
        }
        return Float.NaN;
    }
}
