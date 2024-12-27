package com.android.server.wm;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;

public abstract class MultiTaskingAppCompatUtils {
    public static int getAdjustedUserId(int i, int i2, String str) {
        if ((i2 & 1) != 0 && SemDualAppManager.isDualAppId(i)) {
            return 0;
        }
        if ((i2 & 2) != 0 && SemPersonaManager.isSecureFolderId(i)) {
            return 0;
        }
        if ((i2 & 4) == 0 || !"com.samsung.knox.securefolder".equals(str)) {
            return i;
        }
        return 0;
    }
}
