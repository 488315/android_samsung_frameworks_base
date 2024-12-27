package com.android.systemui.statusbar.phone.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
