package com.samsung.sesl.compose.component;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class SeslScaffoldLayoutContent {
    public static final /* synthetic */ SeslScaffoldLayoutContent[] $VALUES;
    public static final SeslScaffoldLayoutContent Background;
    public static final SeslScaffoldLayoutContent BottomBar;
    public static final SeslScaffoldLayoutContent Fab;
    public static final SeslScaffoldLayoutContent MainContent;
    public static final SeslScaffoldLayoutContent Snackbar;
    public static final SeslScaffoldLayoutContent TopBar;

    static {
        SeslScaffoldLayoutContent seslScaffoldLayoutContent = new SeslScaffoldLayoutContent("TopBar", 0);
        TopBar = seslScaffoldLayoutContent;
        SeslScaffoldLayoutContent seslScaffoldLayoutContent2 = new SeslScaffoldLayoutContent("MainContent", 1);
        MainContent = seslScaffoldLayoutContent2;
        SeslScaffoldLayoutContent seslScaffoldLayoutContent3 = new SeslScaffoldLayoutContent("Snackbar", 2);
        Snackbar = seslScaffoldLayoutContent3;
        SeslScaffoldLayoutContent seslScaffoldLayoutContent4 = new SeslScaffoldLayoutContent("Fab", 3);
        Fab = seslScaffoldLayoutContent4;
        SeslScaffoldLayoutContent seslScaffoldLayoutContent5 = new SeslScaffoldLayoutContent("BottomBar", 4);
        BottomBar = seslScaffoldLayoutContent5;
        SeslScaffoldLayoutContent seslScaffoldLayoutContent6 = new SeslScaffoldLayoutContent("Background", 5);
        Background = seslScaffoldLayoutContent6;
        SeslScaffoldLayoutContent[] seslScaffoldLayoutContentArr = {seslScaffoldLayoutContent, seslScaffoldLayoutContent2, seslScaffoldLayoutContent3, seslScaffoldLayoutContent4, seslScaffoldLayoutContent5, seslScaffoldLayoutContent6};
        $VALUES = seslScaffoldLayoutContentArr;
        EnumEntriesKt.enumEntries(seslScaffoldLayoutContentArr);
    }

    private SeslScaffoldLayoutContent(String str, int i) {
    }

    public static SeslScaffoldLayoutContent valueOf(String str) {
        return (SeslScaffoldLayoutContent) Enum.valueOf(SeslScaffoldLayoutContent.class, str);
    }

    public static SeslScaffoldLayoutContent[] values() {
        return (SeslScaffoldLayoutContent[]) $VALUES.clone();
    }
}
