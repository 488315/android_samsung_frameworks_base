package com.android.systemui.statusbar.phone.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.systemui.R;

public interface StatusBarIconController {
    static ArraySet getIconHideList(Context context, String str) {
        ArraySet arraySet = new ArraySet();
        for (String str2 : str == null ? context.getResources().getStringArray(R.array.config_statusBarIconsToExclude) : str.split(",")) {
            if (!TextUtils.isEmpty(str2)) {
                arraySet.add(str2);
            }
        }
        return arraySet;
    }
}
