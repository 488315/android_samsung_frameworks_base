package com.android.systemui.bouncer.ui.composable;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerOverlayLayout {
    public static final /* synthetic */ BouncerOverlayLayout[] $VALUES;
    public static final BouncerOverlayLayout BELOW_USER_SWITCHER;
    public static final BouncerOverlayLayout BESIDE_USER_SWITCHER;
    public static final BouncerOverlayLayout SPLIT_BOUNCER;
    public static final BouncerOverlayLayout STANDARD_BOUNCER;

    static {
        BouncerOverlayLayout bouncerOverlayLayout = new BouncerOverlayLayout("STANDARD_BOUNCER", 0);
        STANDARD_BOUNCER = bouncerOverlayLayout;
        BouncerOverlayLayout bouncerOverlayLayout2 = new BouncerOverlayLayout("BELOW_USER_SWITCHER", 1);
        BELOW_USER_SWITCHER = bouncerOverlayLayout2;
        BouncerOverlayLayout bouncerOverlayLayout3 = new BouncerOverlayLayout("BESIDE_USER_SWITCHER", 2);
        BESIDE_USER_SWITCHER = bouncerOverlayLayout3;
        BouncerOverlayLayout bouncerOverlayLayout4 = new BouncerOverlayLayout("SPLIT_BOUNCER", 3);
        SPLIT_BOUNCER = bouncerOverlayLayout4;
        BouncerOverlayLayout[] bouncerOverlayLayoutArr = {bouncerOverlayLayout, bouncerOverlayLayout2, bouncerOverlayLayout3, bouncerOverlayLayout4};
        $VALUES = bouncerOverlayLayoutArr;
        EnumEntriesKt.enumEntries(bouncerOverlayLayoutArr);
    }

    private BouncerOverlayLayout(String str, int i) {
    }

    public static BouncerOverlayLayout valueOf(String str) {
        return (BouncerOverlayLayout) Enum.valueOf(BouncerOverlayLayout.class, str);
    }

    public static BouncerOverlayLayout[] values() {
        return (BouncerOverlayLayout[]) $VALUES.clone();
    }
}
