package com.android.systemui.communal.ui.compose;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import com.android.compose.animation.scene.BaseTransitionBuilder;
import com.android.compose.animation.scene.BaseTransitionBuilderImpl;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.SceneTransitionsBuilderImpl;
import com.android.compose.animation.scene.TransitionBuilderImpl;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.transformation.Translate;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalContainerKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ CommunalContainerKt$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i = 2;
        int i2 = 5;
        int i3 = 4;
        int i4 = 6;
        switch (this.$r8$classId) {
            case 0:
                SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl = (SceneTransitionsBuilderImpl) obj;
                SceneKey sceneKey = CommunalScenes.Communal;
                sceneTransitionsBuilderImpl.transition(null, sceneKey, (r14 & 2) != 0 ? null : null, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(17));
                CommunalTransitionKeys.INSTANCE.getClass();
                TransitionKey transitionKey = CommunalTransitionKeys.Swipe;
                sceneTransitionsBuilderImpl.transition(null, sceneKey, (r14 & 2) != 0 ? null : transitionKey, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(19));
                SceneKey sceneKey2 = CommunalScenes.Blank;
                sceneTransitionsBuilderImpl.transition(null, sceneKey2, (r14 & 2) != 0 ? null : null, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(20));
                sceneTransitionsBuilderImpl.transition(null, sceneKey2, (r14 & 2) != 0 ? null : CommunalTransitionKeys.SwipeInLandscape, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(21));
                sceneTransitionsBuilderImpl.transition(null, sceneKey2, (r14 & 2) != 0 ? null : transitionKey, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(22));
                break;
            case 1:
                TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj;
                transitionBuilderImpl.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilder.translate$default(transitionBuilderImpl, Communal$Elements.Grid, Edge.End);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl, null, 167, new CommunalContainerKt$$ExternalSyntheticLambda0(i4), 5);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl, 167, 334, new CommunalContainerKt$$ExternalSyntheticLambda0(7), 4);
                break;
            case 2:
                TransitionBuilderImpl transitionBuilderImpl2 = (TransitionBuilderImpl) obj;
                transitionBuilderImpl2.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl2, null, Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend), new CommunalContainerKt$$ExternalSyntheticLambda0(12), 5);
                Communal$Elements.INSTANCE.getClass();
                transitionBuilderImpl2.fade(Communal$Elements.Scrim);
                break;
            case 3:
                TransitionBuilderImpl transitionBuilderImpl3 = (TransitionBuilderImpl) obj;
                transitionBuilderImpl3.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                Communal$Elements.INSTANCE.getClass();
                ElementKey elementKey = Communal$Elements.Grid;
                Dimensions.Companion.getClass();
                float f = Dimensions.SlideOffsetY;
                float f2 = 0.0f;
                if ((2 & 2) != 0) {
                    f2 = 0;
                    Dp.Companion companion = Dp.Companion;
                }
                if ((2 & 4) != 0) {
                    f = 0;
                    Dp.Companion companion2 = Dp.Companion;
                }
                TransitionBuilderImpl transitionBuilderImpl4 = transitionBuilderImpl3;
                transitionBuilderImpl4.getClass();
                transitionBuilderImpl4.addTransformation(elementKey, new Translate.Factory(f2, f, null));
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl3, null, 167, new CommunalContainerKt$$ExternalSyntheticLambda0(i3), 5);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl3, 167, 334, new CommunalContainerKt$$ExternalSyntheticLambda0(i2), 4);
                break;
            case 4:
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj);
                baseTransitionBuilderImpl.fade(Communal$Elements.IndicationArea);
                baseTransitionBuilderImpl.fade(Communal$Elements.LockIcon);
                baseTransitionBuilderImpl.fade(Communal$Elements.Scrim);
                break;
            case 5:
                Communal$Elements.INSTANCE.getClass();
                ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj)).fade(Communal$Elements.Grid);
                break;
            case 6:
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilderImpl baseTransitionBuilderImpl2 = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj);
                baseTransitionBuilderImpl2.fade(Communal$Elements.Grid);
                baseTransitionBuilderImpl2.fade(Communal$Elements.IndicationArea);
                baseTransitionBuilderImpl2.fade(Communal$Elements.LockIcon);
                baseTransitionBuilderImpl2.fade(Communal$Elements.StatusBar);
                break;
            case 7:
                Communal$Elements.INSTANCE.getClass();
                ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj)).fade(Communal$Elements.Scrim);
                break;
            case 8:
                ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj)).fade(AllElements.INSTANCE);
                break;
            case 9:
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilderImpl baseTransitionBuilderImpl3 = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj);
                baseTransitionBuilderImpl3.fade(Communal$Elements.Grid);
                baseTransitionBuilderImpl3.fade(Communal$Elements.IndicationArea);
                baseTransitionBuilderImpl3.fade(Communal$Elements.LockIcon);
                baseTransitionBuilderImpl3.fade(Communal$Elements.StatusBar);
                break;
            case 10:
                Communal$Elements.INSTANCE.getClass();
                ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj)).fade(Communal$Elements.Scrim);
                break;
            case 11:
                SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl2 = (SceneTransitionsBuilderImpl) obj;
                SceneKey sceneKey3 = CommunalScenes.Communal;
                CommunalTransitionKeys.INSTANCE.getClass();
                TransitionKey transitionKey2 = CommunalTransitionKeys.SimpleFade;
                sceneTransitionsBuilderImpl2.transition(null, sceneKey3, (r14 & 2) != 0 ? null : transitionKey2, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(23));
                SceneKey sceneKey4 = CommunalScenes.Blank;
                sceneTransitionsBuilderImpl2.transition(null, sceneKey4, (r14 & 2) != 0 ? null : transitionKey2, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(24));
                sceneTransitionsBuilderImpl2.transition(null, sceneKey3, (r14 & 2) != 0 ? null : null, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(25));
                sceneTransitionsBuilderImpl2.transition(null, sceneKey4, (r14 & 2) != 0 ? null : null, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(1));
                sceneTransitionsBuilderImpl2.transition(null, sceneKey4, (r14 & 2) != 0 ? null : CommunalTransitionKeys.ToEditMode, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(i));
                sceneTransitionsBuilderImpl2.transition(null, sceneKey3, (r14 & 2) != 0 ? null : CommunalTransitionKeys.FromEditMode, (r14 & 4) != 0 ? null : null, null, new CommunalContainerKt$$ExternalSyntheticLambda0(3));
                break;
            case 12:
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilderImpl baseTransitionBuilderImpl4 = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj);
                baseTransitionBuilderImpl4.fade(Communal$Elements.Grid);
                baseTransitionBuilderImpl4.fade(Communal$Elements.IndicationArea);
                baseTransitionBuilderImpl4.fade(Communal$Elements.LockIcon);
                break;
            case 13:
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilderImpl baseTransitionBuilderImpl5 = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj);
                baseTransitionBuilderImpl5.fade(Communal$Elements.Grid);
                baseTransitionBuilderImpl5.fade(Communal$Elements.IndicationArea);
                baseTransitionBuilderImpl5.fade(Communal$Elements.LockIcon);
                baseTransitionBuilderImpl5.fade(Communal$Elements.StatusBar);
                break;
            case 14:
                Communal$Elements.INSTANCE.getClass();
                ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj)).fade(Communal$Elements.Scrim);
                break;
            case 15:
                ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj)).fade(AllElements.INSTANCE);
                break;
            case 16:
                SemanticsPropertiesKt.disabled((SemanticsPropertyReceiver) obj);
                break;
            case 17:
                TransitionBuilderImpl transitionBuilderImpl5 = (TransitionBuilderImpl) obj;
                transitionBuilderImpl5.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                transitionBuilderImpl5.fade(AllElements.INSTANCE);
                break;
            case 18:
                break;
            case 19:
                TransitionBuilderImpl transitionBuilderImpl6 = (TransitionBuilderImpl) obj;
                transitionBuilderImpl6.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilder.translate$default(transitionBuilderImpl6, Communal$Elements.Grid, Edge.End);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl6, 167, 334, new CommunalContainerKt$$ExternalSyntheticLambda0(15), 4);
                break;
            case 20:
                TransitionBuilderImpl transitionBuilderImpl7 = (TransitionBuilderImpl) obj;
                FromPrimaryBouncerTransitionInteractor.Companion.getClass();
                transitionBuilderImpl7.spec = AnimationSpecKt.tween$default(Duration.m3444toIntimpl(FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION, DurationUnit.MILLISECONDS), 0, null, 6);
                transitionBuilderImpl7.fade(AllElements.INSTANCE);
                break;
            case 21:
                TransitionBuilderImpl transitionBuilderImpl8 = (TransitionBuilderImpl) obj;
                FromGlanceableHubTransitionInteractor.Companion.getClass();
                transitionBuilderImpl8.spec = AnimationSpecKt.tween$default(Duration.m3444toIntimpl(FromGlanceableHubTransitionInteractor.TO_LOCKSCREEN_DURATION, DurationUnit.MILLISECONDS), 0, null, 6);
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilder.translate$default(transitionBuilderImpl8, Communal$Elements.Grid, Edge.End);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl8, null, 167, new CommunalContainerKt$$ExternalSyntheticLambda0(9), 5);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl8, 167, 500, new CommunalContainerKt$$ExternalSyntheticLambda0(10), 4);
                break;
            case 22:
                TransitionBuilderImpl transitionBuilderImpl9 = (TransitionBuilderImpl) obj;
                transitionBuilderImpl9.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilder.translate$default(transitionBuilderImpl9, Communal$Elements.Grid, Edge.End);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl9, null, 167, new CommunalContainerKt$$ExternalSyntheticLambda0(13), 5);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl9, 167, 334, new CommunalContainerKt$$ExternalSyntheticLambda0(14), 4);
                break;
            case 23:
                TransitionBuilderImpl transitionBuilderImpl10 = (TransitionBuilderImpl) obj;
                transitionBuilderImpl10.spec = AnimationSpecKt.tween$default(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, 0, null, 6);
                transitionBuilderImpl10.fade(AllElements.INSTANCE);
                break;
            case 24:
                TransitionBuilderImpl transitionBuilderImpl11 = (TransitionBuilderImpl) obj;
                FromPrimaryBouncerTransitionInteractor.Companion.getClass();
                transitionBuilderImpl11.spec = AnimationSpecKt.tween$default(Duration.m3444toIntimpl(FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION, DurationUnit.MILLISECONDS), 0, null, 6);
                transitionBuilderImpl11.fade(AllElements.INSTANCE);
                break;
            default:
                TransitionBuilderImpl transitionBuilderImpl12 = (TransitionBuilderImpl) obj;
                transitionBuilderImpl12.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                Communal$Elements.INSTANCE.getClass();
                BaseTransitionBuilder.translate$default(transitionBuilderImpl12, Communal$Elements.Grid, Edge.End);
                TransitionBuilderImpl.timestampRange$default(transitionBuilderImpl12, 167, 334, new CommunalContainerKt$$ExternalSyntheticLambda0(8), 4);
                break;
        }
        return Unit.INSTANCE;
    }
}
