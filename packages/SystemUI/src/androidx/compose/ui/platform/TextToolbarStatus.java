package androidx.compose.ui.platform;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextToolbarStatus {
    public static final /* synthetic */ TextToolbarStatus[] $VALUES;
    public static final TextToolbarStatus Hidden;
    public static final TextToolbarStatus Shown;

    static {
        TextToolbarStatus textToolbarStatus = new TextToolbarStatus("Shown", 0);
        Shown = textToolbarStatus;
        TextToolbarStatus textToolbarStatus2 = new TextToolbarStatus("Hidden", 1);
        Hidden = textToolbarStatus2;
        TextToolbarStatus[] textToolbarStatusArr = {textToolbarStatus, textToolbarStatus2};
        $VALUES = textToolbarStatusArr;
        EnumEntriesKt.enumEntries(textToolbarStatusArr);
    }

    private TextToolbarStatus(String str, int i) {
    }

    public static TextToolbarStatus valueOf(String str) {
        return (TextToolbarStatus) Enum.valueOf(TextToolbarStatus.class, str);
    }

    public static TextToolbarStatus[] values() {
        return (TextToolbarStatus[]) $VALUES.clone();
    }
}
