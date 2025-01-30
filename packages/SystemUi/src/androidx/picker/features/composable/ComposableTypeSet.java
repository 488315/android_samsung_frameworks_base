package androidx.picker.features.composable;

import androidx.picker.features.composable.icon.IconFrame;
import androidx.picker.features.composable.left.LeftFrame;
import androidx.picker.features.composable.title.TitleFrame;
import androidx.picker.features.composable.widget.WidgetFrame;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'TextOnly' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComposableTypeSet implements ComposableType {
    public static final /* synthetic */ ComposableTypeSet[] $VALUES;
    public static final ComposableTypeSet AllSwitch;
    public static final ComposableTypeSet CheckBox;
    public static final ComposableTypeSet CheckBox_Action;
    public static final ComposableTypeSet CheckBox_Expander;
    public static final ComposableTypeSet Radio;
    public static final ComposableTypeSet Radio_Action;
    public static final ComposableTypeSet Switch;
    public static final ComposableTypeSet TextOnly;
    private final IconFrame iconFrame;
    private final LeftFrame leftFrame;
    private final TitleFrame titleFrame;
    private final WidgetFrame widgetFrame;

    static {
        IconFrame iconFrame = IconFrame.Icon;
        TitleFrame titleFrame = TitleFrame.Title;
        ComposableTypeSet composableTypeSet = new ComposableTypeSet("TextOnly", 0, null, iconFrame, titleFrame, null);
        TextOnly = composableTypeSet;
        ComposableTypeSet composableTypeSet2 = new ComposableTypeSet("Switch", 1, null, iconFrame, titleFrame, WidgetFrame.Switch);
        Switch = composableTypeSet2;
        ComposableTypeSet composableTypeSet3 = new ComposableTypeSet("AllSwitch", 2, null, null, titleFrame, WidgetFrame.AllAppsSwitch);
        AllSwitch = composableTypeSet3;
        LeftFrame leftFrame = LeftFrame.CheckBox;
        ComposableTypeSet composableTypeSet4 = new ComposableTypeSet("CheckBox", 3, leftFrame, iconFrame, titleFrame, null);
        CheckBox = composableTypeSet4;
        WidgetFrame widgetFrame = WidgetFrame.Action;
        ComposableTypeSet composableTypeSet5 = new ComposableTypeSet("CheckBox_Action", 4, leftFrame, iconFrame, titleFrame, widgetFrame);
        CheckBox_Action = composableTypeSet5;
        ComposableTypeSet composableTypeSet6 = new ComposableTypeSet("CheckBox_Expander", 5, leftFrame, iconFrame, titleFrame, WidgetFrame.Expander);
        CheckBox_Expander = composableTypeSet6;
        LeftFrame leftFrame2 = LeftFrame.Radio;
        ComposableTypeSet composableTypeSet7 = new ComposableTypeSet("Radio", 6, leftFrame2, iconFrame, titleFrame, null);
        Radio = composableTypeSet7;
        ComposableTypeSet composableTypeSet8 = new ComposableTypeSet("Radio_Action", 7, leftFrame2, iconFrame, titleFrame, widgetFrame);
        Radio_Action = composableTypeSet8;
        $VALUES = new ComposableTypeSet[]{composableTypeSet, composableTypeSet2, composableTypeSet3, composableTypeSet4, composableTypeSet5, composableTypeSet6, composableTypeSet7, composableTypeSet8};
    }

    private ComposableTypeSet(String str, int i, LeftFrame leftFrame, IconFrame iconFrame, TitleFrame titleFrame, WidgetFrame widgetFrame) {
        this.leftFrame = leftFrame;
        this.iconFrame = iconFrame;
        this.titleFrame = titleFrame;
        this.widgetFrame = widgetFrame;
    }

    public static ComposableTypeSet valueOf(String str) {
        return (ComposableTypeSet) Enum.valueOf(ComposableTypeSet.class, str);
    }

    public static ComposableTypeSet[] values() {
        return (ComposableTypeSet[]) $VALUES.clone();
    }

    @Override // androidx.picker.features.composable.ComposableType
    public final ComposableFrame getIconFrame() {
        return this.iconFrame;
    }

    @Override // androidx.picker.features.composable.ComposableType
    public final ComposableFrame getLeftFrame() {
        return this.leftFrame;
    }

    @Override // androidx.picker.features.composable.ComposableType
    public final ComposableFrame getTitleFrame() {
        return this.titleFrame;
    }

    @Override // androidx.picker.features.composable.ComposableType
    public final ComposableFrame getWidgetFrame() {
        return this.widgetFrame;
    }
}
