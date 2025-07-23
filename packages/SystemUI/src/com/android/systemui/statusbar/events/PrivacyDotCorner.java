package com.android.systemui.statusbar.events;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PrivacyDotCorner {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ PrivacyDotCorner[] $VALUES;
    public static final PrivacyDotCorner BottomLeft;
    public static final PrivacyDotCorner BottomRight;
    public static final PrivacyDotCorner TopLeft;
    public static final PrivacyDotCorner TopRight;
    private final int gravity;
    private final int index;
    private final int innerGravity;
    private final String title;

    static {
        PrivacyDotCorner privacyDotCorner = new PrivacyDotCorner("TopLeft", 0, 0, 51, 21, "TopLeft");
        TopLeft = privacyDotCorner;
        PrivacyDotCorner privacyDotCorner2 = new PrivacyDotCorner("TopRight", 1, 1, 53, 19, "TopRight");
        TopRight = privacyDotCorner2;
        PrivacyDotCorner privacyDotCorner3 = new PrivacyDotCorner("BottomRight", 2, 2, 85, 21, "BottomRight");
        BottomRight = privacyDotCorner3;
        PrivacyDotCorner privacyDotCorner4 = new PrivacyDotCorner("BottomLeft", 3, 3, 83, 19, "BottomLeft");
        BottomLeft = privacyDotCorner4;
        PrivacyDotCorner[] privacyDotCornerArr = {privacyDotCorner, privacyDotCorner2, privacyDotCorner3, privacyDotCorner4};
        $VALUES = privacyDotCornerArr;
        $ENTRIES = EnumEntriesKt.enumEntries(privacyDotCornerArr);
    }

    private PrivacyDotCorner(String str, int i, int i2, int i3, int i4, String str2) {
        this.index = i2;
        this.gravity = i3;
        this.innerGravity = i4;
        this.title = str2;
    }

    public static PrivacyDotCorner valueOf(String str) {
        return (PrivacyDotCorner) Enum.valueOf(PrivacyDotCorner.class, str);
    }

    public static PrivacyDotCorner[] values() {
        return (PrivacyDotCorner[]) $VALUES.clone();
    }

    public final int getGravity() {
        return this.gravity;
    }

    public final int getIndex() {
        return this.index;
    }

    public final int getInnerGravity() {
        return this.innerGravity;
    }

    public final String getTitle() {
        return this.title;
    }
}
