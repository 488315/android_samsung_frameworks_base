package com.android.systemui.shade;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeHeaderController$chipVisibilityListener$1 {
    public final /* synthetic */ ShadeHeaderController this$0;

    public ShadeHeaderController$chipVisibilityListener$1(ShadeHeaderController shadeHeaderController) {
        this.this$0 = shadeHeaderController;
    }

    public final void onChipVisibilityRefreshed(boolean z) {
        ShadeHeaderController shadeHeaderController = this.this$0;
        ((CombinedShadeHeadersConstraintManagerImpl) shadeHeaderController.combinedShadeHeadersConstraintManager).getClass();
        final float f = z ? 0.0f : 1.0f;
        ConstraintsChanges constraintsChanges = new ConstraintsChanges(new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$privacyChipVisibilityConstraints$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((ConstraintSet) obj).setAlpha(R.id.shade_header_system_icons, f);
                return Unit.INSTANCE;
            }
        }, null, null, 6, null);
        MotionLayout motionLayout = shadeHeaderController.header;
        Function1 function1 = constraintsChanges.qqsConstraintsChanges;
        if (function1 != null) {
            int i = ShadeHeaderController.QQS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet = motionLayout.getConstraintSet(i);
            Intrinsics.checkNotNull(constraintSet);
            function1.invoke(constraintSet);
            motionLayout.updateState(i, constraintSet);
        }
        Function1 function12 = constraintsChanges.qsConstraintsChanges;
        if (function12 != null) {
            int i2 = ShadeHeaderController.QS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i2);
            Intrinsics.checkNotNull(constraintSet2);
            function12.invoke(constraintSet2);
            motionLayout.updateState(i2, constraintSet2);
        }
    }
}
