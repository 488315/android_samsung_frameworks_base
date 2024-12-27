package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.custom.SystemManager;

public final class KnoxCustomManagerWrapper {
    public final SystemManager mKnoxCustomManager = SystemManager.getInstance();

    public KnoxCustomManagerWrapper(Context context) {
    }
}
