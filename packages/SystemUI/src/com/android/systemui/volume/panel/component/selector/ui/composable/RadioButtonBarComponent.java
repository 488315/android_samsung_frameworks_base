package com.android.systemui.volume.panel.component.selector.ui.composable;

import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
