package com.android.systemui.statusbar.pipeline.mobile.ui.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IconLocation {
    public static final /* synthetic */ IconLocation[] $VALUES;
    public static final IconLocation DATA_ICON;
    public static final IconLocation ROAMING_ICON;

    static {
        IconLocation iconLocation = new IconLocation("DATA_ICON", 0);
        DATA_ICON = iconLocation;
        IconLocation iconLocation2 = new IconLocation("ROAMING_ICON", 1);
        ROAMING_ICON = iconLocation2;
        IconLocation[] iconLocationArr = {iconLocation, iconLocation2};
        $VALUES = iconLocationArr;
        EnumEntriesKt.enumEntries(iconLocationArr);
    }

    private IconLocation(String str, int i) {
    }

    public static IconLocation valueOf(String str) {
        return (IconLocation) Enum.valueOf(IconLocation.class, str);
    }

    public static IconLocation[] values() {
        return (IconLocation[]) $VALUES.clone();
    }
}
