package com.android.systemui.pluginlock;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginLockInstancePolicy {
    private static final int CATEGORY_DEFAULT = 1;
    private static final int CATEGORY_DUAL = 2;
    public static final int DISABLED_ALL = 0;
    public static final int DISABLED_BY_MODE = 20000;
    public static final int DISABLED_BY_SUB_USER = 30000;
    public static final int PLUGIN_IDENTITY_DIVIDER = 10;
    public static final String TAG = "PluginLockInstancePolicy";
    private final Map<Integer, Integer> mCategoryMap = new HashMap();
    private static final String[] DEFAULT_PACKAGES = {"com.samsung.android.dynamiclock:2"};
    private static final String[] DUAL_DISPLAY_PACKAGES = {"com.samsung.android.dynamiclock", "com.samsung.android.dynamiclock:2"};
    private static final String[] DUAL_DISPLAY_PACKAGES_FOLDER = {"com.samsung.android.dynamiclock:2"};

    private int getBaseNumber(int i) {
        return (i / 10) * 10;
    }

    private String[] getDualDisplayPackages() {
        return (!LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) ? DUAL_DISPLAY_PACKAGES : DUAL_DISPLAY_PACKAGES_FOLDER;
    }

    private void setDualDisplayCategory(PluginLockInstanceState pluginLockInstanceState) {
        for (String str : getDualDisplayPackages()) {
            if (str.equals(pluginLockInstanceState.getPackageName())) {
                Integer num = this.mCategoryMap.get(Integer.valueOf(pluginLockInstanceState.getAllowedNumber()));
                if (num == null) {
                    num = 0;
                }
                this.mCategoryMap.put(Integer.valueOf(pluginLockInstanceState.getAllowedNumber()), Integer.valueOf(num.intValue() | 2));
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0018, code lost:
    
        if ((r3.intValue() & 1) == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isDefaultInstance(int r4) {
        /*
            r3 = this;
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r3.mCategoryMap
            int r3 = r3.getBaseNumber(r4)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r3 = r0.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            if (r3 == 0) goto L1b
            int r3 = r3.intValue()
            r0 = 1
            r3 = r3 & r0
            if (r3 != r0) goto L1b
            goto L1c
        L1b:
            r0 = 0
        L1c:
            java.lang.String r3 = "isDefaultInstance() allowedNumber:"
            java.lang.String r1 = ", ret:"
            java.lang.String r2 = "PluginLockInstancePolicy"
            com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m(r3, r4, r1, r0, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.PluginLockInstancePolicy.isDefaultInstance(int):boolean");
    }

    public boolean isDualDisplayInstance(int i) {
        Integer num = this.mCategoryMap.get(Integer.valueOf(getBaseNumber(i)));
        boolean z = num != null && (num.intValue() & 2) == 2;
        KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("isDualDisplayInstance() allowedNumber:", i, ", ret:", z, TAG);
        return z || LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION;
    }

    public boolean isEnable(int i) {
        boolean z = i >= 0 && i % 10 != 0;
        KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("isEnable() value:", i, ", ret:", z, TAG);
        return z;
    }

    public boolean isSameInstance(int i, int i2) {
        boolean z = i / 10 == i2 / 10;
        ActionBarContextView$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "isSameInstance() submitNum:", ", matchedNum:", ", ret:"), z, TAG);
        return z;
    }

    public void setCategory(PluginLockInstanceState pluginLockInstanceState) {
        setDefaultCategory(pluginLockInstanceState);
        setDualDisplayCategory(pluginLockInstanceState);
    }

    public void setDefaultCategory(PluginLockInstanceState pluginLockInstanceState) {
        for (String str : DEFAULT_PACKAGES) {
            if (str.equals(pluginLockInstanceState.getPackageName())) {
                Integer num = this.mCategoryMap.get(Integer.valueOf(pluginLockInstanceState.getAllowedNumber()));
                if (num == null) {
                    num = 0;
                }
                this.mCategoryMap.put(Integer.valueOf(pluginLockInstanceState.getAllowedNumber()), Integer.valueOf(num.intValue() | 1));
            }
        }
    }
}
