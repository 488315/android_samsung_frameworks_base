package com.samsung.sesl.compose.foundation;

import com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RecoilType {
    public static final /* synthetic */ RecoilType[] $VALUES;
    public static final RecoilType Button;
    public static final RecoilType IconButton;
    public static final RecoilType List;
    private final SeslRecoilDrawStrategy drawStrategy;
    private final float scaleRatio;

    static {
        SeslRecoilDrawStrategy.FeedbackNonScaling feedbackNonScaling = SeslRecoilDrawStrategy.FeedbackNonScaling.INSTANCE;
        RecoilType recoilType = new RecoilType("List", 0, 0.98f, feedbackNonScaling);
        List = recoilType;
        SeslRecoilDrawStrategy.FeedbackScaling feedbackScaling = SeslRecoilDrawStrategy.FeedbackScaling.INSTANCE;
        RecoilType recoilType2 = new RecoilType("Card", 1, 0.98f, feedbackScaling);
        RecoilType recoilType3 = new RecoilType("Button", 2, 0.96f, feedbackScaling);
        Button = recoilType3;
        RecoilType recoilType4 = new RecoilType("IconButton", 3, 0.96f, feedbackNonScaling);
        IconButton = recoilType4;
        RecoilType[] recoilTypeArr = {recoilType, recoilType2, recoilType3, recoilType4};
        $VALUES = recoilTypeArr;
        EnumEntriesKt.enumEntries(recoilTypeArr);
    }

    private RecoilType(String str, int i, float f, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        this.scaleRatio = f;
        this.drawStrategy = seslRecoilDrawStrategy;
    }

    public static RecoilType valueOf(String str) {
        return (RecoilType) Enum.valueOf(RecoilType.class, str);
    }

    public static RecoilType[] values() {
        return (RecoilType[]) $VALUES.clone();
    }

    public final SeslRecoilDrawStrategy getDrawStrategy() {
        return this.drawStrategy;
    }

    public final float getScaleRatio() {
        return this.scaleRatio;
    }
}
