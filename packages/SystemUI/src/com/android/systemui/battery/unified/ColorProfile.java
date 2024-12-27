package com.android.systemui.battery.unified;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ColorProfile {
    public static final /* synthetic */ ColorProfile[] $VALUES;
    public static final ColorProfile Active = null;
    public static final ColorProfile Error = null;
    public static final ColorProfile None;
    public static final ColorProfile Warning = null;

    static {
        ColorProfile colorProfile = new ColorProfile("None", 0);
        None = colorProfile;
        ColorProfile[] colorProfileArr = {colorProfile, new ColorProfile("Active", 1), new ColorProfile("Warning", 2), new ColorProfile("Error", 3)};
        $VALUES = colorProfileArr;
        EnumEntriesKt.enumEntries(colorProfileArr);
    }

    private ColorProfile(String str, int i) {
    }

    public static ColorProfile valueOf(String str) {
        return (ColorProfile) Enum.valueOf(ColorProfile.class, str);
    }

    public static ColorProfile[] values() {
        return (ColorProfile[]) $VALUES.clone();
    }
}
