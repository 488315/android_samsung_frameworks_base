package com.android.systemui.communal.ui.compose;

import android.content.Context;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(741040877);
            boolean changedInstance = composerImpl.changedInstance(context) | composerImpl.changedInstance(communalViewModel);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == obj) {
                rememberedValue2 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        Context context2 = context;
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj2;
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, context2.getString(R.string.accessibility_content_description_for_communal_hub));
                        String string = context2.getString(R.string.accessibility_action_label_close_communal_hub);
                        final CommunalViewModel communalViewModel2 = communalViewModel;
                        final int i3 = 0;
                        CustomAccessibilityAction customAccessibilityAction = new CustomAccessibilityAction(string, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
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
                            public final Object invoke() {
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
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            Modifier semantics = SemanticsModifierKt.semantics(modifier, false, (Function1) rememberedValue2);
            composerImpl.startReplaceGroup(741079441);
            boolean changedInstance2 = composerImpl.changedInstance(communalViewModel);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changedInstance2 || rememberedValue3 == obj) {
                rememberedValue3 = new CommunalTouchableSurfaceKt$CommunalTouchableSurface$2$1(communalViewModel);
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            KFunction kFunction = (KFunction) rememberedValue3;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(741081165);
            boolean changedInstance3 = composerImpl.changedInstance(communalViewModel);
            Object rememberedValue4 = composerImpl.rememberedValue();
            if (changedInstance3 || rememberedValue4 == obj) {
                rememberedValue4 = new CommunalTouchableSurfaceKt$CommunalTouchableSurface$3$1(communalViewModel);
                composerImpl.updateRememberedValue(rememberedValue4);
            }
            composerImpl.end(false);
            Modifier m37combinedClickableauXiCPI$default = ClickableKt.m37combinedClickableauXiCPI$default(semantics, mutableInteractionSource, null, (Function0) kFunction, (Function0) ((KFunction) rememberedValue4), 444);
            composerImpl.startReplaceGroup(741086718);
            boolean changedInstance4 = composerImpl.changedInstance(communalViewModel);
            Object rememberedValue5 = composerImpl.rememberedValue();
            if (changedInstance4 || rememberedValue5 == obj) {
                rememberedValue5 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$CommunalTouchableSurface$4$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj2).nativeKeyEvent;
                        ((BaseCommunalViewModel) CommunalViewModel.this).communalInteractor._userActivity.tryEmit(Unit.INSTANCE);
                        return Boolean.FALSE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue5);
            }
            composerImpl.end(false);
            Modifier onPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(m37combinedClickableauXiCPI$default, (Function1) rememberedValue5);
            composerImpl.startReplaceGroup(741090880);
            boolean changedInstance5 = composerImpl.changedInstance(communalViewModel);
            Object rememberedValue6 = composerImpl.rememberedValue();
            if (changedInstance5 || rememberedValue6 == obj) {
                rememberedValue6 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        SharedFlowImpl sharedFlowImpl = ((BaseCommunalViewModel) CommunalViewModel.this).communalInteractor._userActivity;
                        Unit unit = Unit.INSTANCE;
                        sharedFlowImpl.tryEmit(unit);
                        return unit;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue6);
            }
            composerImpl.end(false);
            Modifier motionEventSpy = PointerInteropFilter_androidKt.motionEventSpy(onPreviewKeyEvent, (Function1) rememberedValue6);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, motionEventSpy);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, composerImpl, Integer.valueOf(((i2 >> 3) & 112) | 6));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalTouchableSurfaceKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    CommunalTouchableSurfaceKt.CommunalTouchableSurface(CommunalViewModel.this, modifier, composableLambdaImpl2, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
