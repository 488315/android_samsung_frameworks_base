package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActionTutorialContentKt$$ExternalSyntheticLambda2 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TutorialActionState f$0;
    public final /* synthetic */ TutorialScreenConfig f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ Modifier f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ ActionTutorialContentKt$$ExternalSyntheticLambda2(TutorialActionState tutorialActionState, TutorialScreenConfig tutorialScreenConfig, boolean z, Modifier modifier, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = tutorialActionState;
        this.f$1 = tutorialScreenConfig;
        this.f$2 = z;
        this.f$3 = modifier;
        this.f$4 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$4 | 1);
                boolean z = this.f$2;
                Modifier modifier = this.f$3;
                ActionTutorialContentKt.VerticalDescriptionAndAnimation(this.f$0, this.f$1, z, modifier, (Composer) obj, updateChangedFlags);
                break;
            case 1:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$4 | 1);
                boolean z2 = this.f$2;
                Modifier modifier2 = this.f$3;
                ActionTutorialContentKt.HorizontalDescriptionAndAnimation(this.f$0, this.f$1, z2, modifier2, (Composer) obj, updateChangedFlags2);
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags3 = RecomposeScopeImplKt.updateChangedFlags(this.f$4 | 1);
                boolean z3 = this.f$2;
                Modifier modifier3 = this.f$3;
                ActionTutorialContentKt.TutorialDescription(this.f$0, this.f$1, z3, modifier3, (Composer) obj, updateChangedFlags3);
                break;
        }
        return Unit.INSTANCE;
    }
}
