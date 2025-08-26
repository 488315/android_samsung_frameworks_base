package com.android.systemui.keyguard.ui.composable.blueprint;

import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.layout.VerticalAlignmentLine;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final class BlueprintAlignmentLines$LockIcon {
    public static final HorizontalAlignmentLine Bottom;
    public static final BlueprintAlignmentLines$LockIcon INSTANCE = new BlueprintAlignmentLines$LockIcon();
    public static final VerticalAlignmentLine Left;
    public static final VerticalAlignmentLine Right;
    public static final HorizontalAlignmentLine Top;

    static {
        final int i = 0;
        Left = new VerticalAlignmentLine(new Function2() { // from class: com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Integer num = (Integer) obj;
                switch (i) {
                    case 0:
                        int iIntValue = num.intValue();
                        int iIntValue2 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue, iIntValue2));
                    case 1:
                        int iIntValue3 = num.intValue();
                        int iIntValue4 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon2 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue3, iIntValue4));
                    case 2:
                        int iIntValue5 = num.intValue();
                        int iIntValue6 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon3 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue5, iIntValue6));
                    default:
                        int iIntValue7 = num.intValue();
                        int iIntValue8 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon4 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue7, iIntValue8));
                }
            }
        });
        final int i2 = 1;
        Top = new HorizontalAlignmentLine(new Function2() { // from class: com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Integer num = (Integer) obj;
                switch (i2) {
                    case 0:
                        int iIntValue = num.intValue();
                        int iIntValue2 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue, iIntValue2));
                    case 1:
                        int iIntValue3 = num.intValue();
                        int iIntValue4 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon2 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue3, iIntValue4));
                    case 2:
                        int iIntValue5 = num.intValue();
                        int iIntValue6 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon3 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue5, iIntValue6));
                    default:
                        int iIntValue7 = num.intValue();
                        int iIntValue8 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon4 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue7, iIntValue8));
                }
            }
        });
        final int i3 = 2;
        Right = new VerticalAlignmentLine(new Function2() { // from class: com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Integer num = (Integer) obj;
                switch (i3) {
                    case 0:
                        int iIntValue = num.intValue();
                        int iIntValue2 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue, iIntValue2));
                    case 1:
                        int iIntValue3 = num.intValue();
                        int iIntValue4 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon2 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue3, iIntValue4));
                    case 2:
                        int iIntValue5 = num.intValue();
                        int iIntValue6 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon3 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue5, iIntValue6));
                    default:
                        int iIntValue7 = num.intValue();
                        int iIntValue8 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon4 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue7, iIntValue8));
                }
            }
        });
        final int i4 = 3;
        Bottom = new HorizontalAlignmentLine(new Function2() { // from class: com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Integer num = (Integer) obj;
                switch (i4) {
                    case 0:
                        int iIntValue = num.intValue();
                        int iIntValue2 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue, iIntValue2));
                    case 1:
                        int iIntValue3 = num.intValue();
                        int iIntValue4 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon2 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.min(iIntValue3, iIntValue4));
                    case 2:
                        int iIntValue5 = num.intValue();
                        int iIntValue6 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon3 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue5, iIntValue6));
                    default:
                        int iIntValue7 = num.intValue();
                        int iIntValue8 = ((Integer) obj2).intValue();
                        BlueprintAlignmentLines$LockIcon blueprintAlignmentLines$LockIcon4 = BlueprintAlignmentLines$LockIcon.INSTANCE;
                        return Integer.valueOf(Math.max(iIntValue7, iIntValue8));
                }
            }
        });
    }

    private BlueprintAlignmentLines$LockIcon() {
    }
}
