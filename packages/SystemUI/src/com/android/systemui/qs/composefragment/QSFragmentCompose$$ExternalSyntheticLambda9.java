package com.android.systemui.qs.composefragment;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.ui.unit.Density;
import com.android.compose.animation.scene.TransitionBuilderImpl;
import com.android.compose.animation.scene.transformation.TransformationRange;
import com.android.systemui.qs.composefragment.ui.ToEditModeKt$$ExternalSyntheticLambda0;
import com.android.systemui.qs.ui.composable.QuickSettingsShade;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda9 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = QSFragmentCompose.$r8$clinit;
                QuickSettingsShade.Dimensions.INSTANCE.getClass();
                return Integer.valueOf(((Density) obj).mo51roundToPx0680j_4(QuickSettingsShade.Dimensions.Padding));
            default:
                TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj;
                int i2 = QSFragmentCompose.$r8$clinit;
                transitionBuilderImpl.spec = AnimationSpecKt.tween$default(500, 0, null, 6);
                Float valueOf = Float.valueOf(0.5f);
                Float f = (6 & 1) != 0 ? null : valueOf;
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = (6 & 4) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl2 = transitionBuilderImpl;
                transitionBuilderImpl2.getClass();
                transitionBuilderImpl2.range = new TransformationRange(f, (Float) null, easingKt$$ExternalSyntheticLambda0);
                SceneKeys.INSTANCE.getClass();
                transitionBuilderImpl2.fade(SceneKeys.EditMode.rootElementKey);
                Unit unit = Unit.INSTANCE;
                transitionBuilderImpl2.range = null;
                ToEditModeKt$$ExternalSyntheticLambda0 toEditModeKt$$ExternalSyntheticLambda0 = new ToEditModeKt$$ExternalSyntheticLambda0();
                if ((5 & 2) != 0) {
                    valueOf = null;
                }
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda02 = (5 & 4) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl3 = transitionBuilderImpl;
                transitionBuilderImpl3.getClass();
                transitionBuilderImpl3.range = new TransformationRange((Float) null, valueOf, easingKt$$ExternalSyntheticLambda02);
                toEditModeKt$$ExternalSyntheticLambda0.mo779invoke(transitionBuilderImpl3);
                transitionBuilderImpl3.range = null;
                return Unit.INSTANCE;
        }
    }
}
