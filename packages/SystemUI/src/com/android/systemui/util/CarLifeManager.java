package com.android.systemui.util;

import android.view.Display;
import com.android.systemui.keyguard.DisplayLifecycle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CarLifeManager {
    private static final String CATEGORY_CARLIFE = "com.samsung.android.hardware.display.category.CARLIFE_DISPLAY";
    private static final String TAG = "CarLifeManager";
    private final DisplayLifecycle mDisplayLifeCycle;

    public CarLifeManager(DisplayLifecycle displayLifecycle) {
        this.mDisplayLifeCycle = displayLifecycle;
    }

    public boolean isCarLifeMode() {
        Iterator it = ((HashMap) this.mDisplayLifeCycle.mDisplayHash).entrySet().iterator();
        while (it.hasNext()) {
            if ((((Display) ((Map.Entry) it.next()).getValue()).getFlags() & 1048576) != 0) {
                return true;
            }
        }
        return false;
    }
}
