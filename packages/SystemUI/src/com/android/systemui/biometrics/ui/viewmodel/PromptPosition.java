package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.enums.EnumEntriesKt;

public final class PromptPosition {
    public static final /* synthetic */ PromptPosition[] $VALUES;
    public static final PromptPosition Bottom;
    public static final PromptPosition Left;
    public static final PromptPosition Right;
    public static final PromptPosition Top;

    static {
        PromptPosition promptPosition = new PromptPosition("Top", 0);
        Top = promptPosition;
        PromptPosition promptPosition2 = new PromptPosition("Bottom", 1);
        Bottom = promptPosition2;
        PromptPosition promptPosition3 = new PromptPosition(SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT, 2);
        Left = promptPosition3;
        PromptPosition promptPosition4 = new PromptPosition(SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT, 3);
        Right = promptPosition4;
        PromptPosition[] promptPositionArr = {promptPosition, promptPosition2, promptPosition3, promptPosition4};
        $VALUES = promptPositionArr;
        EnumEntriesKt.enumEntries(promptPositionArr);
    }

    private PromptPosition(String str, int i) {
    }

    public static PromptPosition valueOf(String str) {
        return (PromptPosition) Enum.valueOf(PromptPosition.class, str);
    }

    public static PromptPosition[] values() {
        return (PromptPosition[]) $VALUES.clone();
    }
}
