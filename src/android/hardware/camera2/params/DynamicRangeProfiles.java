package android.hardware.camera2.params;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public final class DynamicRangeProfiles {
    public static final long DOLBY_VISION_10B_HDR_OEM = 64;
    public static final long DOLBY_VISION_10B_HDR_OEM_PO = 128;
    public static final long DOLBY_VISION_10B_HDR_REF = 16;
    public static final long DOLBY_VISION_10B_HDR_REF_PO = 32;
    public static final long DOLBY_VISION_8B_HDR_OEM = 1024;
    public static final long DOLBY_VISION_8B_HDR_OEM_PO = 2048;
    public static final long DOLBY_VISION_8B_HDR_REF = 256;
    public static final long DOLBY_VISION_8B_HDR_REF_PO = 512;
    public static final long HDR10 = 4;
    public static final long HDR10_PLUS = 8;
    public static final long HLG10 = 2;
    public static final long PUBLIC_MAX = 4096;
    public static final long STANDARD = 1;
    private final HashMap<Long, Set<Long>> mProfileMap = new HashMap<>();
    private final HashMap<Long, Boolean> mLookahedLatencyMap = new HashMap<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface Profile {
    }

    public DynamicRangeProfiles(long[] jArr) {
        if (jArr.length % 3 != 0) {
            throw new IllegalArgumentException("Dynamic range profile map length " + jArr.length + " is not even!");
        }
        int i = 0;
        while (true) {
            if (i < jArr.length) {
                checkProfileValue(jArr[i]);
                if (jArr[i] == 1) {
                    throw new IllegalArgumentException("Dynamic range profile map must not include a STANDARD profile entry!");
                }
                HashSet hashSet = new HashSet();
                int i2 = i + 1;
                boolean z = true;
                if (jArr[i2] != 0) {
                    for (long j = 1; j < 4096; j <<= 1) {
                        if ((jArr[i2] & j) != 0) {
                            hashSet.add(Long.valueOf(j));
                        }
                    }
                }
                this.mProfileMap.put(Long.valueOf(jArr[i]), hashSet);
                HashMap<Long, Boolean> map = this.mLookahedLatencyMap;
                Long lValueOf = Long.valueOf(jArr[i]);
                if (jArr[i + 2] == 0) {
                    z = false;
                }
                map.put(lValueOf, Boolean.valueOf(z));
                i += 3;
            } else {
                HashSet hashSet2 = new HashSet();
                hashSet2.add(1L);
                for (Long l : this.mProfileMap.keySet()) {
                    if (this.mProfileMap.get(l).isEmpty() || this.mProfileMap.get(l).contains(1L)) {
                        hashSet2.add(l);
                    }
                }
                this.mProfileMap.put(1L, hashSet2);
                this.mLookahedLatencyMap.put(1L, false);
                return;
            }
        }
    }

    public static void checkProfileValue(long j) {
        if (j == 1 || j == 2 || j == 4 || j == 8 || j == 16 || j == 32 || j == 64 || j == 128 || j == 256 || j == 512 || j == 1024 || j == 2048) {
            return;
        }
        throw new IllegalArgumentException("Unknown profile " + j);
    }

    public Set<Long> getSupportedProfiles() {
        return Collections.unmodifiableSet(this.mProfileMap.keySet());
    }

    public Set<Long> getProfileCaptureRequestConstraints(long j) {
        Set<Long> set = this.mProfileMap.get(Long.valueOf(j));
        if (set == null) {
            throw new IllegalArgumentException("Unsupported profile!");
        }
        return Collections.unmodifiableSet(set);
    }

    public boolean isExtraLatencyPresent(long j) {
        Boolean bool = this.mLookahedLatencyMap.get(Long.valueOf(j));
        if (bool == null) {
            throw new IllegalArgumentException("Unsupported profile!");
        }
        return bool.booleanValue();
    }
}
