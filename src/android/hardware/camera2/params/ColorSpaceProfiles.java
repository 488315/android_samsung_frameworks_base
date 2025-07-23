package android.hardware.camera2.params;

import android.graphics.ColorSpace;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class ColorSpaceProfiles {
    public static final int UNSPECIFIED = -1;
    private final Map<ColorSpace.Named, Map<Integer, Set<Long>>> mProfileMap = new ArrayMap();

    public ColorSpaceProfiles(long[] jArr) {
        if (jArr.length % 3 != 0) {
            throw new IllegalArgumentException("Color space profile map length " + jArr.length + " is not divisible by 3!");
        }
        for (int i = 0; i < jArr.length; i += 3) {
            int i2 = (int) jArr[i];
            checkProfileValue(i2);
            ColorSpace.Named named = ColorSpace.Named.values()[i2];
            int i3 = (int) jArr[i + 1];
            long j = jArr[i + 2];
            if (!this.mProfileMap.containsKey(named)) {
                this.mProfileMap.put(named, new ArrayMap());
            }
            if (!this.mProfileMap.get(named).containsKey(Integer.valueOf(i3))) {
                this.mProfileMap.get(named).put(Integer.valueOf(i3), new ArraySet());
            }
            if (j != 0) {
                for (long j2 = 1; j2 < 4096; j2 <<= 1) {
                    if ((j & j2) != 0) {
                        this.mProfileMap.get(named).get(Integer.valueOf(i3)).add(Long.valueOf(j2));
                    }
                }
            }
        }
    }

    public static void checkProfileValue(int i) {
        for (ColorSpace.Named named : ColorSpace.Named.values()) {
            if (i == named.ordinal()) {
                return;
            }
        }
        throw new IllegalArgumentException("Unknown ColorSpace " + i);
    }

    public Map<ColorSpace.Named, Map<Integer, Set<Long>>> getProfileMap() {
        return this.mProfileMap;
    }

    public Set<ColorSpace.Named> getSupportedColorSpaces(int i) {
        ArraySet arraySet = new ArraySet();
        for (ColorSpace.Named named : this.mProfileMap.keySet()) {
            if (i == 0) {
                arraySet.add(named);
            } else if (this.mProfileMap.get(named).containsKey(Integer.valueOf(i))) {
                arraySet.add(named);
            }
        }
        return arraySet;
    }

    public Set<Integer> getSupportedImageFormatsForColorSpace(ColorSpace.Named named) {
        Map<Integer, Set<Long>> map = this.mProfileMap.get(named);
        if (map == null) {
            return new ArraySet();
        }
        return map.keySet();
    }

    public Set<Long> getSupportedDynamicRangeProfiles(ColorSpace.Named named, int i) {
        Map<Integer, Set<Long>> map = this.mProfileMap.get(named);
        if (map == null) {
            return new ArraySet();
        }
        if (i == 0) {
            ArraySet arraySet = new ArraySet();
            for (Integer num : map.keySet()) {
                num.intValue();
                Iterator<Long> it = map.get(num).iterator();
                while (it.hasNext()) {
                    arraySet.add(it.next());
                }
            }
            return arraySet;
        }
        Set<Long> set = map.get(Integer.valueOf(i));
        return set == null ? new ArraySet() : set;
    }

    public Set<ColorSpace.Named> getSupportedColorSpacesForDynamicRange(int i, long j) {
        ArraySet arraySet = new ArraySet();
        for (ColorSpace.Named named : this.mProfileMap.keySet()) {
            Map<Integer, Set<Long>> map = this.mProfileMap.get(named);
            if (i == 0) {
                for (Integer num : map.keySet()) {
                    num.intValue();
                    if (map.get(num).contains(Long.valueOf(j))) {
                        arraySet.add(named);
                    }
                }
            } else if (map.containsKey(Integer.valueOf(i)) && map.get(Integer.valueOf(i)).contains(Long.valueOf(j))) {
                arraySet.add(named);
            }
        }
        return arraySet;
    }
}
