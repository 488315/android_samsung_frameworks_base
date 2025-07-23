package com.android.systemui.power.utils;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DisplayUtils {
    private DisplayUtils() {
    }

    public static Context getSubDisplayContext(Context context) {
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        RecyclerView$$ExternalSyntheticOutline0.m(displays.length, "PowerUI.DisplayUtils", new StringBuilder("Displays : "));
        Display display = displays[1];
        Log.d("PowerUI.DisplayUtils", "SubDisplay id : " + display.getDisplayId());
        return context.createDisplayContext(display);
    }

    public static boolean isFlipSubDisplayOn(boolean z) {
        return BasicRune.BASIC_FOLDABLE_TYPE_FLIP && z;
    }

    public static boolean isViewCoverClosed() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        if (!keyguardUpdateMonitor.isCoverClosed() || keyguardUpdateMonitor.getCoverState() == null) {
            return false;
        }
        int type = keyguardUpdateMonitor.getCoverState().getType();
        ListPopupWindow$$ExternalSyntheticOutline0.m(type, "View Cover is covered and closed, cover type : ", "PowerUI.DisplayUtils");
        if (type != 15) {
            return false;
        }
        Log.i("PowerUI.DisplayUtils", "S view cover is enabled, so we do not show this hv enable popup");
        return true;
    }
}
