package com.android.systemui.pluginlock;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isDefaultInstance(int i) {
        boolean z;
        Integer num = this.mCategoryMap.get(Integer.valueOf(getBaseNumber(i)));
        if (num != null) {
            z = (num.intValue() & 1) == 1;
        }
        KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("isDefaultInstance() allowedNumber:", i, ", ret:", z, TAG);
        return z;
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
        ActionBarContextView$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "isSameInstance() submitNum:", ", matchedNum:", ", ret:"), z, TAG);
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
