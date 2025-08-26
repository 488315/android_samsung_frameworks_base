package com.android.systemui.communal.ui.compose;

import android.content.Context;
import android.content.res.Resources;
import android.view.KeyEvent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.systemui.R;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public abstract class CommunalTouchableSurfaceKt {
    public static final void CommunalTouchableSurface(final CommunalViewModel communalViewModel, final Modifier modifier, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1445438354);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(communalViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalTouchableSurface (CommunalTouchableSurface.kt:44)");
            }
            final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(741036002);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(741040877);
            boolean zChangedInstance = composerImpl.changedInstance(context) | composerImpl.changedInstance(communalViewModel);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == obj) {
                objRememberedValue2 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        Context context2 = context;
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj2;
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, context2.getString(R.string.accessibility_content_description_for_communal_hub));
                        String string = context2.getString(R.string.accessibility_action_label_close_communal_hub);
                        final CommunalViewModel communalViewModel2 = communalViewModel;
                        final int i3 = 0;
                        CustomAccessibilityAction customAccessibilityAction = new CustomAccessibilityAction(string, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() throws Resources.NotFoundException {
                                switch (i3) {
                                    case 0:
                                        BaseCommunalViewModel.changeScene$default(communalViewModel2, CommunalScenes.Blank, "closed by accessibility", null, 12);
                                        break;
                                    default:
                                        communalViewModel2.onOpenWidgetEditor(false);
                                        break;
                                }
                                return Boolean.TRUE;
                            }
                        });
                        final int i4 = 1;
                        SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, Arrays.asList(customAccessibilityAction, new CustomAccessibilityAction(context2.getString(R.string.accessibility_action_label_edit_widgets), new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() throws Resources.NotFoundException {
                                switch (i4) {
                                    case 0:
                                        BaseCommunalViewModel.changeScene$default(communalViewModel2, CommunalScenes.Blank, "closed by accessibility", null, 12);
                                        break;
                                    default:
                                        communalViewModel2.onOpenWidgetEditor(false);
                                        break;
                                }
                                return Boolean.TRUE;
                            }
                        })));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue2);
            composerImpl.startReplaceGroup(741079441);
            boolean zChangedInstance2 = composerImpl.changedInstance(communalViewModel);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChangedInstance2 || objRememberedValue3 == obj) {
                objRememberedValue3 = new CommunalTouchableSurfaceKt$CommunalTouchableSurface$2$1(communalViewModel);
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            KFunction kFunction = (KFunction) objRememberedValue3;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(741081165);
            boolean zChangedInstance3 = composerImpl.changedInstance(communalViewModel);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChangedInstance3 || objRememberedValue4 == obj) {
                objRememberedValue4 = new CommunalTouchableSurfaceKt$CommunalTouchableSurface$3$1(communalViewModel);
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            composerImpl.end(false);
            Modifier modifierM37combinedClickableauXiCPI$default = ClickableKt.m37combinedClickableauXiCPI$default(modifierSemantics, mutableInteractionSource, null, (Function0) kFunction, (Function0) ((KFunction) objRememberedValue4), 444);
            composerImpl.startReplaceGroup(741086718);
            boolean zChangedInstance4 = composerImpl.changedInstance(communalViewModel);
            Object objRememberedValue5 = composerImpl.rememberedValue();
            if (zChangedInstance4 || objRememberedValue5 == obj) {
                objRememberedValue5 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$CommunalTouchableSurface$4$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj2).nativeKeyEvent;
                        ((BaseCommunalViewModel) communalViewModel).communalInteractor._userActivity.tryEmit(Unit.INSTANCE);
                        return Boolean.FALSE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
            composerImpl.end(false);
            Modifier modifierOnPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(modifierM37combinedClickableauXiCPI$default, (Function1) objRememberedValue5);
            composerImpl.startReplaceGroup(741090880);
            boolean zChangedInstance5 = composerImpl.changedInstance(communalViewModel);
            Object objRememberedValue6 = composerImpl.rememberedValue();
            if (zChangedInstance5 || objRememberedValue6 == obj) {
                objRememberedValue6 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        SharedFlowImpl sharedFlowImpl = ((BaseCommunalViewModel) communalViewModel).communalInteractor._userActivity;
                        Unit unit = Unit.INSTANCE;
                        sharedFlowImpl.tryEmit(unit);
                        return unit;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue6);
            }
            composerImpl.end(false);
            Modifier modifierMotionEventSpy = PointerInteropFilter_androidKt.motionEventSpy(modifierOnPreviewKeyEvent, (Function1) objRememberedValue6);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierMotionEventSpy);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, composerImpl, Integer.valueOf(((i2 >> 3) & 112) | 6));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    CommunalTouchableSurfaceKt.CommunalTouchableSurface(communalViewModel, modifier, composableLambdaImpl2, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
