package androidx.compose.material3;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
final class SliderComponents {
    public static final /* synthetic */ SliderComponents[] $VALUES;
    public static final SliderComponents THUMB;
    public static final SliderComponents TRACK;

    static {
        SliderComponents sliderComponents = new SliderComponents("THUMB", 0);
        THUMB = sliderComponents;
        SliderComponents sliderComponents2 = new SliderComponents("TRACK", 1);
        TRACK = sliderComponents2;
        SliderComponents[] sliderComponentsArr = {sliderComponents, sliderComponents2};
        $VALUES = sliderComponentsArr;
        EnumEntriesKt.enumEntries(sliderComponentsArr);
    }

    private SliderComponents(String str, int i) {
    }

    public static SliderComponents valueOf(String str) {
        return (SliderComponents) Enum.valueOf(SliderComponents.class, str);
    }

    public static SliderComponents[] values() {
        return (SliderComponents[]) $VALUES.clone();
    }
}
