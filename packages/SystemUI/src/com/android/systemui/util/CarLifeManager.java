package com.android.systemui.util;

import android.view.Display;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
            if ((((Display) ((Map.Entry) it.next()).getValue()).getFlags() & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0) {
                return true;
            }
        }
        return false;
    }
}
