package com.android.systemui.shade;

import com.android.systemui.unfold.SysUIUnfoldComponent;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda23 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda23(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((SysUIUnfoldComponent) obj).getKeyguardUnfoldTransition();
            default:
                return Float.valueOf(((NotificationPanelView) obj).mCurrentPanelAlpha);
        }
    }
}
