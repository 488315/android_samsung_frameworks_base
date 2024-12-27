package com.android.systemui.volume.panel.component.selector.ui.composable;

import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import kotlin.enums.EnumEntriesKt;

final class RadioButtonBarComponent {
    public static final /* synthetic */ RadioButtonBarComponent[] $VALUES;
    public static final RadioButtonBarComponent Buttons;
    public static final RadioButtonBarComponent ButtonsBackground;
    public static final RadioButtonBarComponent Indicator;
    public static final RadioButtonBarComponent Labels;
    private final float zIndex;

    static {
        RadioButtonBarComponent radioButtonBarComponent = new RadioButtonBarComponent("ButtonsBackground", 0, 0.0f);
        ButtonsBackground = radioButtonBarComponent;
        RadioButtonBarComponent radioButtonBarComponent2 = new RadioButtonBarComponent(PluginLockStar.INDICATOR_TYPE, 1, 1.0f);
        Indicator = radioButtonBarComponent2;
        RadioButtonBarComponent radioButtonBarComponent3 = new RadioButtonBarComponent("Buttons", 2, 2.0f);
        Buttons = radioButtonBarComponent3;
        RadioButtonBarComponent radioButtonBarComponent4 = new RadioButtonBarComponent("Labels", 3, 2.0f);
        Labels = radioButtonBarComponent4;
        RadioButtonBarComponent[] radioButtonBarComponentArr = {radioButtonBarComponent, radioButtonBarComponent2, radioButtonBarComponent3, radioButtonBarComponent4};
        $VALUES = radioButtonBarComponentArr;
        EnumEntriesKt.enumEntries(radioButtonBarComponentArr);
    }

    private RadioButtonBarComponent(String str, int i, float f) {
        this.zIndex = f;
    }

    public static RadioButtonBarComponent valueOf(String str) {
        return (RadioButtonBarComponent) Enum.valueOf(RadioButtonBarComponent.class, str);
    }

    public static RadioButtonBarComponent[] values() {
        return (RadioButtonBarComponent[]) $VALUES.clone();
    }

    public final float getZIndex() {
        return this.zIndex;
    }
}
