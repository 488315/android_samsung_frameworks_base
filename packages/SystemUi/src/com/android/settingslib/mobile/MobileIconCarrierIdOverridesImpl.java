package com.android.settingslib.mobile;

import android.content.res.TypedArray;
import android.util.Log;
import com.android.systemui.R;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MobileIconCarrierIdOverridesImpl implements MobileIconCarrierIdOverrides {
    public static final Companion Companion = new Companion(null);
    public static final Map MAPPING = MapsKt__MapsJVMKt.mapOf(new Pair(2032, Integer.valueOf(R.array.carrierId_2032_iconOverrides)));

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Map<String, Integer> parseNetworkIconOverrideTypedArray(TypedArray typedArray) {
            if (typedArray.length() % 2 != 0) {
                Log.w("MobileIconOverrides", "override must contain an even number of (key, value) entries. skipping");
                return MapsKt__MapsKt.emptyMap();
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            IntRange until = RangesKt___RangesKt.until(0, typedArray.length());
            IntProgression.Companion companion = IntProgression.Companion;
            int i = until.first;
            int i2 = until.last;
            int i3 = until.step <= 0 ? -2 : 2;
            companion.getClass();
            IntProgression intProgression = new IntProgression(i, i2, i3);
            int i4 = intProgression.first;
            int i5 = intProgression.last;
            int i6 = intProgression.step;
            if ((i6 > 0 && i4 <= i5) || (i6 < 0 && i5 <= i4)) {
                while (true) {
                    String string = typedArray.getString(i4);
                    int resourceId = typedArray.getResourceId(i4 + 1, 0);
                    if (string == null || resourceId == 0) {
                        Log.w("MobileIconOverrides", "Invalid override found. Skipping");
                    } else {
                        linkedHashMap.put(string, Integer.valueOf(resourceId));
                    }
                    if (i4 == i5) {
                        break;
                    }
                    i4 += i6;
                }
            }
            return linkedHashMap;
        }
    }

    public static final Map<String, Integer> parseNetworkIconOverrideTypedArray(TypedArray typedArray) {
        return Companion.parseNetworkIconOverrideTypedArray(typedArray);
    }
}
