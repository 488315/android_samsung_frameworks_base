package com.android.systemui.keyboard.shared.model;

import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutCustomizationRequestResult {
    public static final /* synthetic */ ShortcutCustomizationRequestResult[] $VALUES;
    public static final ShortcutCustomizationRequestResult ERROR_OTHER;
    public static final ShortcutCustomizationRequestResult ERROR_RESERVED_COMBINATION;
    public static final ShortcutCustomizationRequestResult SUCCESS;

    static {
        ShortcutCustomizationRequestResult shortcutCustomizationRequestResult = new ShortcutCustomizationRequestResult(KnoxForesight.SUCCESS, 0);
        SUCCESS = shortcutCustomizationRequestResult;
        ShortcutCustomizationRequestResult shortcutCustomizationRequestResult2 = new ShortcutCustomizationRequestResult("ERROR_RESERVED_COMBINATION", 1);
        ERROR_RESERVED_COMBINATION = shortcutCustomizationRequestResult2;
        ShortcutCustomizationRequestResult shortcutCustomizationRequestResult3 = new ShortcutCustomizationRequestResult("ERROR_OTHER", 2);
        ERROR_OTHER = shortcutCustomizationRequestResult3;
        ShortcutCustomizationRequestResult[] shortcutCustomizationRequestResultArr = {shortcutCustomizationRequestResult, shortcutCustomizationRequestResult2, shortcutCustomizationRequestResult3};
        $VALUES = shortcutCustomizationRequestResultArr;
        EnumEntriesKt.enumEntries(shortcutCustomizationRequestResultArr);
    }

    private ShortcutCustomizationRequestResult(String str, int i) {
    }

    public static ShortcutCustomizationRequestResult valueOf(String str) {
        return (ShortcutCustomizationRequestResult) Enum.valueOf(ShortcutCustomizationRequestResult.class, str);
    }

    public static ShortcutCustomizationRequestResult[] values() {
        return (ShortcutCustomizationRequestResult[]) $VALUES.clone();
    }
}
