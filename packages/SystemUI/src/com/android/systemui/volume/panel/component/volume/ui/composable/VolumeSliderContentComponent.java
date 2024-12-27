package com.android.systemui.volume.panel.component.volume.ui.composable;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
final class VolumeSliderContentComponent {
    public static final /* synthetic */ VolumeSliderContentComponent[] $VALUES;
    public static final VolumeSliderContentComponent DisabledMessage;
    public static final VolumeSliderContentComponent Label;

    static {
        VolumeSliderContentComponent volumeSliderContentComponent = new VolumeSliderContentComponent("Label", 0);
        Label = volumeSliderContentComponent;
        VolumeSliderContentComponent volumeSliderContentComponent2 = new VolumeSliderContentComponent("DisabledMessage", 1);
        DisabledMessage = volumeSliderContentComponent2;
        VolumeSliderContentComponent[] volumeSliderContentComponentArr = {volumeSliderContentComponent, volumeSliderContentComponent2};
        $VALUES = volumeSliderContentComponentArr;
        EnumEntriesKt.enumEntries(volumeSliderContentComponentArr);
    }

    private VolumeSliderContentComponent(String str, int i) {
    }

    public static VolumeSliderContentComponent valueOf(String str) {
        return (VolumeSliderContentComponent) Enum.valueOf(VolumeSliderContentComponent.class, str);
    }

    public static VolumeSliderContentComponent[] values() {
        return (VolumeSliderContentComponent[]) $VALUES.clone();
    }
}
