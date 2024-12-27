package com.android.systemui.shared.regionsampling;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RegionDarkness {
    public static final /* synthetic */ RegionDarkness[] $VALUES;
    public static final RegionDarkness DARK;
    public static final RegionDarkness DEFAULT;
    public static final RegionDarkness LIGHT;
    private final boolean isDark;

    static {
        RegionDarkness regionDarkness = new RegionDarkness("DEFAULT", 0, false);
        DEFAULT = regionDarkness;
        RegionDarkness regionDarkness2 = new RegionDarkness("DARK", 1, true);
        DARK = regionDarkness2;
        RegionDarkness regionDarkness3 = new RegionDarkness("LIGHT", 2, false);
        LIGHT = regionDarkness3;
        RegionDarkness[] regionDarknessArr = {regionDarkness, regionDarkness2, regionDarkness3};
        $VALUES = regionDarknessArr;
        EnumEntriesKt.enumEntries(regionDarknessArr);
    }

    private RegionDarkness(String str, int i, boolean z) {
        this.isDark = z;
    }

    public static RegionDarkness valueOf(String str) {
        return (RegionDarkness) Enum.valueOf(RegionDarkness.class, str);
    }

    public static RegionDarkness[] values() {
        return (RegionDarkness[]) $VALUES.clone();
    }
}
