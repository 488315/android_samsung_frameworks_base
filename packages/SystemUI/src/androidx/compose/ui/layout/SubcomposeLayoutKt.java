package androidx.compose.ui.layout;

import androidx.collection.MutableScatterMap;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutNodeSubcompositionsState;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNode;
import com.samsung.android.knox.EnterpriseContainerCallback;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class SubcomposeLayoutKt {
    public static final SubcomposeLayoutKt$ReusedSlotId$1 ReusedSlotId = new Object() { // from class: androidx.compose.ui.layout.SubcomposeLayoutKt$ReusedSlotId$1
        public final String toString() {
            return "ReusedSlotId";
        }
    };

    public static final void SubcomposeLayout(final Modifier modifier, Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        final Function2 function22;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1298353104);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 19) != 18)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            Modifier modifier2 = modifier;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.layout.SubcomposeLayout (SubcomposeLayout.kt:84)");
            }
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new SubcomposeLayoutState();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            function22 = function2;
            SubcomposeLayout((SubcomposeLayoutState) objRememberedValue, modifier2, function22, composerImpl, (i3 << 3) & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier = modifier2;
        } else {
            function22 = function2;
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.layout.SubcomposeLayoutKt.SubcomposeLayout.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SubcomposeLayoutKt.SubcomposeLayout(modifier, function22, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SubcomposeLayout(final SubcomposeLayoutState subcomposeLayoutState, Modifier modifier, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-511989831);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(subcomposeLayoutState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 147) != 146)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.layout.SubcomposeLayout (SubcomposeLayout.kt:117)");
            }
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            LayoutNode.Companion.getClass();
            Function0 function0 = LayoutNode.Constructor;
            if (composerImpl.applier != null) {
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, subcomposeLayoutState, subcomposeLayoutState.setRoot);
                Updater.m337setimpl(composerImpl, compositionContextImplRememberCompositionContext, subcomposeLayoutState.setCompositionContext);
                Updater.m337setimpl(composerImpl, function2, subcomposeLayoutState.setMeasurePolicy);
                ComposeUiNode.Companion.getClass();
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
                }
                composerImpl.end(true);
                if (!composerImpl.getSkipping()) {
                    composerImpl.startReplaceGroup(-26243682);
                    boolean zChangedInstance = composerImpl.changedInstance(subcomposeLayoutState);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (!zChangedInstance) {
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new Function0() { // from class: androidx.compose.ui.layout.SubcomposeLayoutKt$SubcomposeLayout$4$1
                                {
                                    super(0);
                                }

                                /* JADX WARN: Removed duplicated region for block: B:16:0x0056  */
                                @Override // kotlin.jvm.functions.Function0
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke() {
                                    LayoutNodeSubcompositionsState state = subcomposeLayoutState.getState();
                                    LayoutNode layoutNode = state.root;
                                    if (state.reusableCount != layoutNode.getFoldedChildren$ui_release().size()) {
                                        MutableScatterMap mutableScatterMap = state.nodeToNodeState;
                                        Object[] objArr = mutableScatterMap.values;
                                        long[] jArr = mutableScatterMap.metadata;
                                        int length = jArr.length - 2;
                                        if (length >= 0) {
                                            int i5 = 0;
                                            while (true) {
                                                long j = jArr[i5];
                                                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                                    int i6 = 8 - ((~(i5 - length)) >>> 31);
                                                    for (int i7 = 0; i7 < i6; i7++) {
                                                        if ((255 & j) < 128) {
                                                            ((LayoutNodeSubcompositionsState.NodeState) objArr[(i5 << 3) + i7]).forceRecompose = true;
                                                        }
                                                        j >>= 8;
                                                    }
                                                    if (i6 != 8) {
                                                        break;
                                                    }
                                                    if (i5 == length) {
                                                        break;
                                                    }
                                                    i5++;
                                                }
                                            }
                                        }
                                        if (layoutNode.lookaheadRoot != null) {
                                            if (!layoutNode.layoutDelegate.lookaheadMeasurePending) {
                                                LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode, false, 7);
                                            }
                                        } else if (!layoutNode.getMeasurePending$ui_release()) {
                                            LayoutNode.requestRemeasure$ui_release$default(layoutNode, false, 7);
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        EffectsKt.SideEffect((Function0) objRememberedValue, composerImpl);
                        composerImpl.end(false);
                    }
                } else {
                    composerImpl.startReplaceGroup(-26185061);
                    composerImpl.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                ComposablesKt.invalidApplier();
                throw null;
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        final Modifier modifier2 = modifier;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.layout.SubcomposeLayoutKt.SubcomposeLayout.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SubcomposeLayoutKt.SubcomposeLayout(subcomposeLayoutState, modifier2, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
