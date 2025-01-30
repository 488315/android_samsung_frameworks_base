package com.android.systemui.pluginlock;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0731x5bb8a836;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockInstancePolicy {
    public static final String[] DEFAULT_PACKAGES = {"com.samsung.android.dynamiclock:2"};
    public static final String[] DUAL_DISPLAY_PACKAGES = {"com.samsung.android.dynamiclock", "com.samsung.android.dynamiclock:2"};
    public static final String[] DUAL_DISPLAY_PACKAGES_FOLDER = {"com.samsung.android.dynamiclock:2"};
    public final Map mCategoryMap = new HashMap();

    public static boolean isEnable(int i) {
        boolean z = i >= 0 && i % 10 != 0;
        AbstractC0731x5bb8a836.m72m("isEnable() value:", i, ", ret:", z, "PluginLockInstancePolicy");
        return z;
    }

    public static boolean isSameInstance(int i, int i2) {
        boolean z = i / 10 == i2 / 10;
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("isSameInstance() submitNum:", i, ", matchedNum:", i2, ", ret:"), z, "PluginLockInstancePolicy");
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x001a, code lost:
    
        if ((r3.intValue() & 1) == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isDefaultInstance(int i) {
        Integer num = (Integer) ((HashMap) this.mCategoryMap).get(Integer.valueOf((i / 10) * 10));
        boolean z = num != null;
        AbstractC0731x5bb8a836.m72m("isDefaultInstance() allowedNumber:", i, ", ret:", z, "PluginLockInstancePolicy");
        return z;
    }
}
