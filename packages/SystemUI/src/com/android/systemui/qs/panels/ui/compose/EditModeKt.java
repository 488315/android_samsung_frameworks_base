package com.android.systemui.qs.panels.ui.compose;

import androidx.activity.compose.BackHandlerKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* loaded from: classes2.dex */
public abstract class EditModeKt {
    /* JADX WARN: Removed duplicated region for block: B:23:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0197  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void EditMode(final EditModeViewModel editModeViewModel, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1011548058);
        if ((((composerImpl.changedInstance(editModeViewModel) ? 4 : 2) | i | (composerImpl.changed(modifier) ? 32 : 16)) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.EditMode (EditMode.kt:28)");
            }
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(editModeViewModel.gridLayout, composerImpl);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(editModeViewModel.tiles, EmptyList.INSTANCE, composerImpl, 48);
            composerImpl.startReplaceGroup(1118085932);
            boolean zChangedInstance = composerImpl.changedInstance(editModeViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.EditModeKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            editModeViewModel.stopEditing();
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                BackHandlerKt.BackHandler(false, (Function0) objRememberedValue, composerImpl, 0, 1);
                Unit unit = Unit.INSTANCE;
                composerImpl.startReplaceGroup(1118087738);
                boolean zChangedInstance2 = composerImpl.changedInstance(editModeViewModel);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.EditModeKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                final EditModeViewModel editModeViewModel2 = editModeViewModel;
                                return new DisposableEffectResult() { // from class: com.android.systemui.qs.panels.ui.compose.EditModeKt$EditMode$lambda$6$lambda$5$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        editModeViewModel2.stopEditing();
                                    }
                                };
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue2, composerImpl);
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
                    Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    GridLayout gridLayout = (GridLayout) mutableStateCollectAsStateWithLifecycle.getValue();
                    List list = (List) mutableStateCollectAsStateWithLifecycle2.getValue();
                    Modifier.Companion companion2 = Modifier.Companion;
                    composerImpl.startReplaceGroup(1939160482);
                    boolean zChangedInstance3 = composerImpl.changedInstance(editModeViewModel);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance3) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new EditModeKt$EditMode$3$1$1(editModeViewModel);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl.end(false);
                        Function2 function22 = (Function2) ((KFunction) objRememberedValue3);
                        composerImpl.startReplaceGroup(1939161509);
                        boolean zChangedInstance4 = composerImpl.changedInstance(editModeViewModel);
                        Object objRememberedValue4 = composerImpl.rememberedValue();
                        if (!zChangedInstance4) {
                            companion.getClass();
                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                objRememberedValue4 = new EditModeKt$EditMode$3$2$1(editModeViewModel);
                                composerImpl.updateRememberedValue(objRememberedValue4);
                            }
                            composerImpl.end(false);
                            Function1 function1 = (Function1) ((KFunction) objRememberedValue4);
                            composerImpl.startReplaceGroup(1939162627);
                            boolean zChangedInstance5 = composerImpl.changedInstance(editModeViewModel);
                            Object objRememberedValue5 = composerImpl.rememberedValue();
                            if (!zChangedInstance5) {
                                companion.getClass();
                                if (objRememberedValue5 == Composer.Companion.Empty) {
                                    objRememberedValue5 = new EditModeKt$EditMode$3$3$1(editModeViewModel);
                                    composerImpl.updateRememberedValue(objRememberedValue5);
                                }
                                composerImpl.end(false);
                                Function1 function12 = (Function1) ((KFunction) objRememberedValue5);
                                composerImpl.startReplaceGroup(1939163686);
                                boolean zChangedInstance6 = composerImpl.changedInstance(editModeViewModel);
                                Object objRememberedValue6 = composerImpl.rememberedValue();
                                if (!zChangedInstance6) {
                                    companion.getClass();
                                    if (objRememberedValue6 == Composer.Companion.Empty) {
                                        objRememberedValue6 = new EditModeKt$EditMode$3$4$1(editModeViewModel);
                                        composerImpl.updateRememberedValue(objRememberedValue6);
                                    }
                                    composerImpl.end(false);
                                    gridLayout.EditTileGrid(list, companion2, function22, function1, function12, (Function0) ((KFunction) objRememberedValue6), composerImpl);
                                    composerImpl.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier, i) { // from class: com.android.systemui.qs.panels.ui.compose.EditModeKt$$ExternalSyntheticLambda2
                public final /* synthetic */ Modifier f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    EditModeKt.EditMode(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
