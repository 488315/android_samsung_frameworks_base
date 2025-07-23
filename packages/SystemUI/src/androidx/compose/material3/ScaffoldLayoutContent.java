package androidx.compose.material3;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScaffoldLayoutContent {
    public static final /* synthetic */ ScaffoldLayoutContent[] $VALUES;
    public static final ScaffoldLayoutContent BottomBar;
    public static final ScaffoldLayoutContent Fab;
    public static final ScaffoldLayoutContent MainContent;
    public static final ScaffoldLayoutContent Snackbar;
    public static final ScaffoldLayoutContent TopBar;

    static {
        ScaffoldLayoutContent scaffoldLayoutContent = new ScaffoldLayoutContent("TopBar", 0);
        TopBar = scaffoldLayoutContent;
        ScaffoldLayoutContent scaffoldLayoutContent2 = new ScaffoldLayoutContent("MainContent", 1);
        MainContent = scaffoldLayoutContent2;
        ScaffoldLayoutContent scaffoldLayoutContent3 = new ScaffoldLayoutContent("Snackbar", 2);
        Snackbar = scaffoldLayoutContent3;
        ScaffoldLayoutContent scaffoldLayoutContent4 = new ScaffoldLayoutContent("Fab", 3);
        Fab = scaffoldLayoutContent4;
        ScaffoldLayoutContent scaffoldLayoutContent5 = new ScaffoldLayoutContent("BottomBar", 4);
        BottomBar = scaffoldLayoutContent5;
        ScaffoldLayoutContent[] scaffoldLayoutContentArr = {scaffoldLayoutContent, scaffoldLayoutContent2, scaffoldLayoutContent3, scaffoldLayoutContent4, scaffoldLayoutContent5};
        $VALUES = scaffoldLayoutContentArr;
        EnumEntriesKt.enumEntries(scaffoldLayoutContentArr);
    }

    private ScaffoldLayoutContent(String str, int i) {
    }

    public static ScaffoldLayoutContent valueOf(String str) {
        return (ScaffoldLayoutContent) Enum.valueOf(ScaffoldLayoutContent.class, str);
    }

    public static ScaffoldLayoutContent[] values() {
        return (ScaffoldLayoutContent[]) $VALUES.clone();
    }
}
