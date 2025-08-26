package com.samsung.sesl.compose.foundation;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class RecoilType {
    public static final /* synthetic */ RecoilType[] $VALUES;
    public static final RecoilType Button;
    public static final RecoilType Card;
    public static final RecoilType IconButton;
    public static final RecoilType List;
    private final SeslRecoilDrawStrategy drawStrategy;
    private final float scaleRatio;

    static {
        SeslRecoilPreset seslRecoilPreset = SeslRecoilPreset.List;
        RecoilType recoilType = new RecoilType("List", 0, seslRecoilPreset.getParameter$sesl8_compose_core_release().scaleRatio, seslRecoilPreset.getParameter$sesl8_compose_core_release().drawStrategy);
        List = recoilType;
        SeslRecoilPreset seslRecoilPreset2 = SeslRecoilPreset.Card;
        RecoilType recoilType2 = new RecoilType("Card", 1, seslRecoilPreset2.getParameter$sesl8_compose_core_release().scaleRatio, seslRecoilPreset2.getParameter$sesl8_compose_core_release().drawStrategy);
        Card = recoilType2;
        SeslRecoilPreset seslRecoilPreset3 = SeslRecoilPreset.Button;
        RecoilType recoilType3 = new RecoilType("Button", 2, seslRecoilPreset3.getParameter$sesl8_compose_core_release().scaleRatio, seslRecoilPreset3.getParameter$sesl8_compose_core_release().drawStrategy);
        Button = recoilType3;
        SeslRecoilPreset seslRecoilPreset4 = SeslRecoilPreset.IconButton;
        RecoilType recoilType4 = new RecoilType("IconButton", 3, seslRecoilPreset4.getParameter$sesl8_compose_core_release().scaleRatio, seslRecoilPreset4.getParameter$sesl8_compose_core_release().drawStrategy);
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
}
