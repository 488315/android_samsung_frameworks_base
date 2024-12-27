package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ActionButtonAppearance {
    public static final /* synthetic */ ActionButtonAppearance[] $VALUES;
    public static final ActionButtonAppearance Hidden;
    public static final ActionButtonAppearance Shown;
    public static final ActionButtonAppearance Subtle;

    static {
        ActionButtonAppearance actionButtonAppearance = new ActionButtonAppearance("Hidden", 0);
        Hidden = actionButtonAppearance;
        ActionButtonAppearance actionButtonAppearance2 = new ActionButtonAppearance("Subtle", 1);
        Subtle = actionButtonAppearance2;
        ActionButtonAppearance actionButtonAppearance3 = new ActionButtonAppearance("Shown", 2);
        Shown = actionButtonAppearance3;
        ActionButtonAppearance[] actionButtonAppearanceArr = {actionButtonAppearance, actionButtonAppearance2, actionButtonAppearance3};
        $VALUES = actionButtonAppearanceArr;
        EnumEntriesKt.enumEntries(actionButtonAppearanceArr);
    }

    private ActionButtonAppearance(String str, int i) {
    }

    public static ActionButtonAppearance valueOf(String str) {
        return (ActionButtonAppearance) Enum.valueOf(ActionButtonAppearance.class, str);
    }

    public static ActionButtonAppearance[] values() {
        return (ActionButtonAppearance[]) $VALUES.clone();
    }
}
