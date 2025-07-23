package com.android.wm.shell.windowdecor.common;

import android.graphics.Region;
import android.window.DesktopExperienceFlags;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowDecorationGestureExclusionTracker$exclusionRegions$1 extends HashMap<Integer, Region> {
    final /* synthetic */ WindowDecorationGestureExclusionTracker this$0;

    public WindowDecorationGestureExclusionTracker$exclusionRegions$1(WindowDecorationGestureExclusionTracker windowDecorationGestureExclusionTracker) {
        this.this$0 = windowDecorationGestureExclusionTracker;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof Integer) {
            return super.containsKey((Integer) obj);
        }
        return false;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof Region) {
            return super.containsValue((Region) obj);
        }
        return false;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        if (!(obj instanceof Integer)) {
            return null;
        }
        int intValue = ((Number) obj).intValue();
        if (!DesktopExperienceFlags.ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY.isTrue()) {
            return this.this$0.exclusionRegion;
        }
        Region region = (Region) super.get(Integer.valueOf(intValue));
        return region == null ? new Region() : region;
    }

    @Override // java.util.HashMap, java.util.Map
    public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof Integer) ? obj2 : (Region) super.getOrDefault((Integer) obj, (Region) obj2);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Object remove(Object obj) {
        if (obj instanceof Integer) {
            return (Region) super.remove((Integer) obj);
        }
        return null;
    }

    @Override // java.util.HashMap, java.util.Map
    public final /* bridge */ boolean remove(Object obj, Object obj2) {
        if ((obj instanceof Integer) && (obj2 instanceof Region)) {
            return super.remove((Integer) obj, (Region) obj2);
        }
        return false;
    }
}
