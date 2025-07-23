package androidx.compose.foundation.text.selection;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CrossStatus {
    public static final /* synthetic */ CrossStatus[] $VALUES;
    public static final CrossStatus COLLAPSED;
    public static final CrossStatus CROSSED;
    public static final CrossStatus NOT_CROSSED;

    static {
        CrossStatus crossStatus = new CrossStatus("CROSSED", 0);
        CROSSED = crossStatus;
        CrossStatus crossStatus2 = new CrossStatus("NOT_CROSSED", 1);
        NOT_CROSSED = crossStatus2;
        CrossStatus crossStatus3 = new CrossStatus("COLLAPSED", 2);
        COLLAPSED = crossStatus3;
        CrossStatus[] crossStatusArr = {crossStatus, crossStatus2, crossStatus3};
        $VALUES = crossStatusArr;
        EnumEntriesKt.enumEntries(crossStatusArr);
    }

    private CrossStatus(String str, int i) {
    }

    public static CrossStatus valueOf(String str) {
        return (CrossStatus) Enum.valueOf(CrossStatus.class, str);
    }

    public static CrossStatus[] values() {
        return (CrossStatus[]) $VALUES.clone();
    }
}
