package com.samsung.sesl.compose.foundation;

import com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategyPreset;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslRecoilPreset {
    public static final /* synthetic */ SeslRecoilPreset[] $VALUES;
    public static final SeslRecoilPreset Button;
    public static final SeslRecoilPreset Card;
    public static final SeslRecoilPreset IconButton;
    public static final SeslRecoilPreset List;
    private final SeslRecoilParameter parameter;

    static {
        SeslRecoilDrawStrategyPreset.FeedbackNonScaling feedbackNonScaling = SeslRecoilDrawStrategyPreset.FeedbackNonScaling.INSTANCE;
        SeslRecoilPreset seslRecoilPreset = new SeslRecoilPreset("List", 0, new SeslRecoilParameter(0.98f, feedbackNonScaling));
        List = seslRecoilPreset;
        SeslRecoilDrawStrategyPreset.FeedbackScaling feedbackScaling = SeslRecoilDrawStrategyPreset.FeedbackScaling.INSTANCE;
        SeslRecoilPreset seslRecoilPreset2 = new SeslRecoilPreset("Card", 1, new SeslRecoilParameter(0.98f, feedbackScaling));
        Card = seslRecoilPreset2;
        SeslRecoilPreset seslRecoilPreset3 = new SeslRecoilPreset("Button", 2, new SeslRecoilParameter(0.96f, feedbackScaling));
        Button = seslRecoilPreset3;
        SeslRecoilPreset seslRecoilPreset4 = new SeslRecoilPreset("IconButton", 3, new SeslRecoilParameter(0.96f, feedbackNonScaling));
        IconButton = seslRecoilPreset4;
        SeslRecoilPreset[] seslRecoilPresetArr = {seslRecoilPreset, seslRecoilPreset2, seslRecoilPreset3, seslRecoilPreset4};
        $VALUES = seslRecoilPresetArr;
        EnumEntriesKt.enumEntries(seslRecoilPresetArr);
    }

    private SeslRecoilPreset(String str, int i, SeslRecoilParameter seslRecoilParameter) {
        this.parameter = seslRecoilParameter;
    }

    public static SeslRecoilPreset valueOf(String str) {
        return (SeslRecoilPreset) Enum.valueOf(SeslRecoilPreset.class, str);
    }

    public static SeslRecoilPreset[] values() {
        return (SeslRecoilPreset[]) $VALUES.clone();
    }

    public final SeslRecoilParameter getParameter$sesl8_compose_core_release() {
        return this.parameter;
    }
}
