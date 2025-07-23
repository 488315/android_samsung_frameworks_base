package com.android.settingslib.mobile;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import com.android.systemui.R;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MobileIconCarrierIdOverridesImpl implements MobileIconCarrierIdOverrides {
    public static final Companion Companion = new Companion(null);
    public static final Map MAPPING;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Map<String, Integer> parseNetworkIconOverrideTypedArray(TypedArray typedArray) {
            if (typedArray.length() % 2 != 0) {
                Log.w("MobileIconOverrides", "override must contain an even number of (key, value) entries. skipping");
                return MapsKt__MapsKt.emptyMap();
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            IntProgression step = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, typedArray.length()), 2);
            int i = step.first;
            int i2 = step.last;
            int i3 = step.step;
            if ((i3 > 0 && i <= i2) || (i3 < 0 && i2 <= i)) {
                while (true) {
                    String string = typedArray.getString(i);
                    int resourceId = typedArray.getResourceId(i + 1, 0);
                    if (string == null || resourceId == 0) {
                        Log.w("MobileIconOverrides", "Invalid override found. Skipping");
                    } else {
                        linkedHashMap.put(string, Integer.valueOf(resourceId));
                    }
                    if (i == i2) {
                        break;
                    }
                    i += i3;
                }
            }
            return linkedHashMap;
        }

        private Companion() {
        }
    }

    static {
        Pair pair = new Pair(2032, Integer.valueOf(R.array.carrierId_2032_iconOverrides));
        MAPPING = Collections.singletonMap(pair.getFirst(), pair.getSecond());
    }

    public static final Map<String, Integer> parseNetworkIconOverrideTypedArray(TypedArray typedArray) {
        return Companion.parseNetworkIconOverrideTypedArray(typedArray);
    }

    public final int getOverrideFor(int i, Resources resources, String str) {
        Integer num = (Integer) MAPPING.get(Integer.valueOf(i));
        if (num != null) {
            TypedArray obtainTypedArray = resources.obtainTypedArray(num.intValue());
            Map<String, Integer> parseNetworkIconOverrideTypedArray = Companion.parseNetworkIconOverrideTypedArray(obtainTypedArray);
            obtainTypedArray.recycle();
            Integer num2 = parseNetworkIconOverrideTypedArray.get(str);
            if (num2 != null) {
                return num2.intValue();
            }
        }
        return 0;
    }
}
