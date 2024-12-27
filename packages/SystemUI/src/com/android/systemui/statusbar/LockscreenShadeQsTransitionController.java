package com.android.systemui.statusbar;

import android.content.Context;
import android.util.IndentingPrintWriter;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenShadeQsTransitionController extends AbstractLockscreenShadeTransitionController {
    public boolean isTransitioningToFullShade;
    public float qsDragDownAmount;
    public final Function0 qsProvider;
    public float qsSquishStartFraction;
    public int qsSquishTransitionDistance;
    public float qsSquishTransitionFraction;
    public int qsTransitionDistance;
    public float qsTransitionFraction;
    public int qsTransitionStartDelay;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        LockscreenShadeQsTransitionController create(Function0 function0);
    }

    public LockscreenShadeQsTransitionController(Context context, ConfigurationController configurationController, DumpManager dumpManager, Function0 function0, SplitShadeStateController splitShadeStateController) {
        super(context, configurationController, dumpManager, splitShadeStateController);
        this.qsProvider = function0;
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        int i = this.qsTransitionDistance;
        int i2 = this.qsTransitionStartDelay;
        int i3 = this.qsSquishTransitionDistance;
        float f = this.qsSquishStartFraction;
        float f2 = this.dragDownAmount;
        float f3 = this.qsDragDownAmount;
        float f4 = this.qsTransitionFraction;
        float f5 = this.qsSquishTransitionFraction;
        boolean z = this.isTransitioningToFullShade;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "\n            Resources:\n              qsTransitionDistance: ", "\n              qsTransitionStartDelay: ", "\n              qsSquishTransitionDistance: ");
        m.append(i3);
        m.append("\n              qsSquishStartFraction: ");
        m.append(f);
        m.append("\n            State:\n              dragDownAmount: ");
        m.append(f2);
        m.append("\n              qsDragDownAmount: ");
        m.append(f3);
        m.append("\n              qsDragFraction: ");
        m.append(f4);
        m.append("\n              qsSquishFraction: ");
        m.append(f5);
        m.append("\n              isTransitioningToFullShade: ");
        m.append(z);
        m.append("\n        ");
        indentingPrintWriter.println(StringsKt__IndentKt.trimIndent(m.toString()));
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void updateResources$1() {
        this.qsTransitionDistance = this.context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_distance);
        this.qsTransitionStartDelay = this.context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_delay);
        this.qsSquishTransitionDistance = this.context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_qs_squish_transition_distance);
        float f = this.context.getResources().getFloat(R.dimen.lockscreen_shade_qs_squish_start_fraction);
        this.qsSquishStartFraction = f;
        this.qsSquishTransitionFraction = Math.max(this.qsSquishTransitionFraction, f);
    }
}
