package androidx.compose.animation;

import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class AnimatedVisibilityKt {
    /* JADX WARN: Removed duplicated region for block: B:117:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x025a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedEnterExitImpl(Transition transition, Function1 function1, Modifier modifier, EnterTransition enterTransition, ExitTransition exitTransition, Function2 function2, Function3 function3, Composer composer, int i, int i2) {
        int i3;
        EnterTransition enterTransition2;
        ExitTransition exitTransition2;
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-891967166);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(transition) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 8) != 0) {
            i3 |= 3072;
            enterTransition2 = enterTransition;
        } else {
            enterTransition2 = enterTransition;
            if ((i & 3072) == 0) {
                i3 |= composerImpl.changed(enterTransition2) ? 2048 : 1024;
            }
        }
        if ((i2 & 16) != 0) {
            i3 |= 24576;
            exitTransition2 = exitTransition;
        } else {
            exitTransition2 = exitTransition;
            if ((i & 24576) == 0) {
                i3 |= composerImpl.changed(exitTransition2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
        }
        if ((i2 & 32) != 0) {
            i3 |= 196608;
        } else if ((i & 196608) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 131072 : 65536;
        }
        if ((i2 & 64) != 0) {
            i3 |= 1572864;
        } else if ((i & 1572864) == 0) {
            i3 |= (2097152 & i) == 0 ? composerImpl.changed((Object) null) : composerImpl.changedInstance(null) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((i2 & 128) != 0) {
            i3 |= 12582912;
        } else if ((i & 12582912) == 0) {
            i3 |= composerImpl.changedInstance(function3) ? 8388608 : 4194304;
        }
        if (composerImpl.shouldExecute(i3 & 1, (4793491 & i3) != 4793490)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.animation.AnimatedEnterExitImpl (AnimatedVisibility.kt:718)");
            }
            boolean zBooleanValue = ((Boolean) function1.mo781invoke(((SnapshotMutableStateImpl) transition.targetState$delegate).getValue())).booleanValue();
            TransitionState transitionState = transition.transitionState;
            if (zBooleanValue || ((Boolean) function1.mo781invoke(transitionState.getCurrentState())).booleanValue() || transition.isSeeking() || transition.getHasInitialValueAnimations()) {
                composerImpl.startReplaceGroup(1788522886);
                int i4 = i3 & 14;
                int i5 = i4 | 48;
                int i6 = i5 & 14;
                boolean z2 = ((i6 ^ 6) > 4 && composerImpl.changed(transition)) || (i5 & 6) == 4;
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion companion = Composer.Companion;
                if (!z2) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = transitionState.getCurrentState();
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    if (transition.isSeeking()) {
                        objRememberedValue = transitionState.getCurrentState();
                    }
                    composerImpl.startReplaceGroup(-466616829);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.animation.AnimatedEnterExitImpl.<anonymous> (AnimatedVisibility.kt:727)");
                    }
                    EnterExitState enterExitStateTargetEnterExit = targetEnterExit(transition, function1, objRememberedValue, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    Object value = ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue();
                    composerImpl.startReplaceGroup(-466616829);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.animation.AnimatedEnterExitImpl.<anonymous> (AnimatedVisibility.kt:727)");
                    }
                    EnterExitState enterExitStateTargetEnterExit2 = targetEnterExit(transition, function1, value, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    Transition transitionCreateChildTransitionInternal = TransitionKt.createChildTransitionInternal(transition, enterExitStateTargetEnterExit, enterExitStateTargetEnterExit2, composerImpl, i6 | 3072);
                    MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function2, composerImpl);
                    Object currentState = transitionCreateChildTransitionInternal.transitionState.getCurrentState();
                    MutableState mutableState = transitionCreateChildTransitionInternal.targetState$delegate;
                    int i7 = i3;
                    Object objInvoke = function2.invoke(currentState, ((SnapshotMutableStateImpl) mutableState).getValue());
                    boolean zChanged = composerImpl.changed(transitionCreateChildTransitionInternal) | composerImpl.changed(mutableStateRememberUpdatedState);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChanged) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new AnimatedVisibilityKt$AnimatedEnterExitImpl$shouldDisposeAfterExit$2$1(transitionCreateChildTransitionInternal, mutableStateRememberUpdatedState, null);
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        MutableState mutableStateProduceState = SnapshotStateKt.produceState(composerImpl, objInvoke, (Function2) objRememberedValue2);
                        Object currentState2 = transitionCreateChildTransitionInternal.transitionState.getCurrentState();
                        EnterExitState enterExitState = EnterExitState.PostExit;
                        if ((currentState2 == enterExitState && ((SnapshotMutableStateImpl) mutableState).getValue() == enterExitState) && ((Boolean) mutableStateProduceState.getValue()).booleanValue()) {
                            composerImpl.startReplaceGroup(1790688794);
                            composerImpl.end(false);
                            z = false;
                        } else {
                            composerImpl.startReplaceGroup(1789551931);
                            boolean z3 = i4 == 4;
                            Object objRememberedValue3 = composerImpl.rememberedValue();
                            if (!z3) {
                                companion.getClass();
                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                    objRememberedValue3 = new AnimatedVisibilityScopeImpl(transitionCreateChildTransitionInternal);
                                    composerImpl.updateRememberedValue(objRememberedValue3);
                                }
                                AnimatedVisibilityScopeImpl animatedVisibilityScopeImpl = (AnimatedVisibilityScopeImpl) objRememberedValue3;
                                int i8 = i7 >> 6;
                                int i9 = (i8 & 112) | 24576 | (i8 & 896);
                                z = false;
                                Modifier modifierCreateModifier = EnterExitTransitionKt.createModifier(transitionCreateChildTransitionInternal, enterTransition2, exitTransition2, "Built-in", composerImpl, i9);
                                composerImpl.startReplaceGroup(1581779440);
                                composerImpl.end(false);
                                Modifier modifierThen = modifier.then(modifierCreateModifier.then(Modifier.Companion));
                                Object objRememberedValue4 = composerImpl.rememberedValue();
                                companion.getClass();
                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                    objRememberedValue4 = new AnimatedEnterExitMeasurePolicy(animatedVisibilityScopeImpl);
                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                }
                                AnimatedEnterExitMeasurePolicy animatedEnterExitMeasurePolicy = (AnimatedEnterExitMeasurePolicy) objRememberedValue4;
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
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
                                Updater.m337setimpl(composerImpl, animatedEnterExitMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                function3.invoke(animatedVisibilityScopeImpl, composerImpl, Integer.valueOf((i7 >> 18) & 112));
                                composerImpl.end(true);
                                composerImpl.end(false);
                            }
                        }
                        composerImpl.end(z);
                    }
                }
            } else {
                composerImpl.startReplaceGroup(1790694746);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(transition, function1, modifier, enterTransition, exitTransition, function2, null, function3, i, i2) { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedEnterExitImpl.4
                final /* synthetic */ int $$changed;
                final /* synthetic */ int $$default;
                final /* synthetic */ Function3 $content;
                final /* synthetic */ EnterTransition $enter;
                final /* synthetic */ ExitTransition $exit;
                final /* synthetic */ Modifier $modifier;
                final /* synthetic */ OnLookaheadMeasured $onLookaheadMeasured;
                final /* synthetic */ Function2 $shouldDisposeBlock;
                final /* synthetic */ Transition<Object> $transition;
                final /* synthetic */ Function1 $visible;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                    this.$content = function3;
                    this.$$changed = i;
                    this.$$default = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AnimatedVisibilityKt.AnimatedEnterExitImpl(this.$transition, this.$visible, this.$modifier, this.$enter, this.$exit, this.$shouldDisposeBlock, this.$content, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(this.$$changed | 1), this.$$default);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:99:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedVisibility(boolean z, Modifier modifier, EnterTransition enterTransition, ExitTransition exitTransition, String str, final Function3 function3, Composer composer, final int i, final int i2) {
        boolean z2;
        int i3;
        Modifier modifier2;
        int i4;
        int i5;
        ExitTransition exitTransitionPlus;
        int i6;
        String str2;
        Function3 function32;
        final EnterTransition enterTransition2;
        final Modifier modifier3;
        final String str3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i7;
        EnterTransition enterTransitionPlus;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2088733774);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            z2 = z;
        } else if ((i & 6) == 0) {
            z2 = z;
            i3 = (composerImpl.changed(z2) ? 4 : 2) | i;
        } else {
            z2 = z;
            i3 = i;
        }
        int i8 = i2 & 2;
        if (i8 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    i3 |= composerImpl.changed(enterTransition) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        exitTransitionPlus = exitTransition;
                        i3 |= composerImpl.changed(exitTransitionPlus) ? 2048 : 1024;
                    }
                    i6 = i2 & 16;
                    if (i6 == 0) {
                        i3 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            str2 = str;
                            i3 |= composerImpl.changed(str2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if ((i2 & 32) == 0) {
                            if ((i & 196608) == 0) {
                                function32 = function3;
                                i3 |= composerImpl.changedInstance(function32) ? 131072 : 65536;
                            }
                            if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                                composerImpl.skipToGroupEnd();
                                enterTransition2 = enterTransition;
                                modifier3 = modifier2;
                                str3 = str2;
                            } else {
                                Modifier modifier4 = i8 != 0 ? Modifier.Companion : modifier2;
                                if (i4 != 0) {
                                    EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
                                    IntSize.Companion companion = IntSize.Companion;
                                    SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
                                    Alignment.Companion.getClass();
                                    enterTransitionPlus = enterTransitionFadeIn$default.plus(EnterExitTransitionKt.expandIn(springSpecSpring$default, Alignment.Companion.BottomEnd, true, EnterExitTransitionKt.C06751.INSTANCE));
                                    i7 = i5;
                                } else {
                                    i7 = i5;
                                    enterTransitionPlus = enterTransition;
                                }
                                if (i7 != 0) {
                                    exitTransitionPlus = EnterExitTransitionKt.shrinkOut$default().plus(EnterExitTransitionKt.fadeOut$default(null, 3));
                                }
                                String str4 = i6 != 0 ? "AnimatedVisibility" : str2;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.animation.AnimatedVisibility (AnimatedVisibility.kt:130)");
                                }
                                int i9 = i3 << 3;
                                AnimatedVisibilityImpl(TransitionKt.updateTransition(Boolean.valueOf(z2), str4, composerImpl, (i3 & 14) | ((i3 >> 9) & 112), 0), new Function1() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility.1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        Boolean bool = (Boolean) obj;
                                        bool.booleanValue();
                                        return bool;
                                    }
                                }, modifier4, enterTransitionPlus, exitTransitionPlus, function32, composerImpl, (i9 & 57344) | (i9 & 896) | 48 | (i9 & 7168) | (458752 & i3));
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                str3 = str4;
                                modifier3 = modifier4;
                                enterTransition2 = enterTransitionPlus;
                            }
                            final ExitTransition exitTransition2 = exitTransitionPlus;
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                final boolean z3 = z2;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility.2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        AnimatedVisibilityKt.AnimatedVisibility(z3, modifier3, enterTransition2, exitTransition2, str3, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 196608;
                        function32 = function3;
                        if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                        }
                        final ExitTransition exitTransition22 = exitTransitionPlus;
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    str2 = str;
                    if ((i2 & 32) == 0) {
                    }
                    function32 = function3;
                    if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                    }
                    final ExitTransition exitTransition222 = exitTransitionPlus;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                exitTransitionPlus = exitTransition;
                i6 = i2 & 16;
                if (i6 == 0) {
                }
                str2 = str;
                if ((i2 & 32) == 0) {
                }
                function32 = function3;
                if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                }
                final ExitTransition exitTransition2222 = exitTransitionPlus;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            exitTransitionPlus = exitTransition;
            i6 = i2 & 16;
            if (i6 == 0) {
            }
            str2 = str;
            if ((i2 & 32) == 0) {
            }
            function32 = function3;
            if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
            }
            final ExitTransition exitTransition22222 = exitTransitionPlus;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        exitTransitionPlus = exitTransition;
        i6 = i2 & 16;
        if (i6 == 0) {
        }
        str2 = str;
        if ((i2 & 32) == 0) {
        }
        function32 = function3;
        if (composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
        }
        final ExitTransition exitTransition222222 = exitTransitionPlus;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedVisibilityImpl(final Transition transition, final Function1 function1, final Modifier modifier, final EnterTransition enterTransition, final ExitTransition exitTransition, final Function3 function3, Composer composer, final int i) {
        int i2;
        EnterTransition enterTransition2;
        ExitTransition exitTransition2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(429978603);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(transition) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            enterTransition2 = enterTransition;
            i2 |= composerImpl.changed(enterTransition2) ? 2048 : 1024;
        } else {
            enterTransition2 = enterTransition;
        }
        if ((i & 24576) == 0) {
            exitTransition2 = exitTransition;
            i2 |= composerImpl.changed(exitTransition2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            exitTransition2 = exitTransition;
        }
        if ((i & 196608) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 131072 : 65536;
        }
        if (composerImpl.shouldExecute(i2 & 1, (74899 & i2) != 74898)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.animation.AnimatedVisibilityImpl (AnimatedVisibility.kt:677)");
            }
            int i3 = i2 & 112;
            int i4 = i2 & 14;
            boolean z = (i3 == 32) | (i4 == 4);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function3() { // from class: androidx.compose.animation.AnimatedVisibilityKt$AnimatedVisibilityImpl$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            long j;
                            MeasureScope measureScope = (MeasureScope) obj;
                            final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj2).mo610measureBRTryo0(((Constraints) obj3).value);
                            if (!measureScope.isLookingAhead() || ((Boolean) function1.mo781invoke(((SnapshotMutableStateImpl) transition.targetState$delegate).getValue())).booleanValue()) {
                                j = (placeableMo610measureBRTryo0.width << 32) | (placeableMo610measureBRTryo0.height & 4294967295L);
                                IntSize.Companion companion = IntSize.Companion;
                            } else {
                                IntSize.Companion.getClass();
                                j = 0;
                            }
                            return measureScope.layout$1((int) (j >> 32), (int) (4294967295L & j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.AnimatedVisibilityKt$AnimatedVisibilityImpl$1$1.1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                                    return Unit.INSTANCE;
                                }
                            });
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                AnimatedEnterExitImpl(transition, function1, LayoutModifierKt.layout(modifier, (Function3) objRememberedValue), enterTransition2, exitTransition2, new Function2() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibilityImpl.2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        EnterExitState enterExitState = (EnterExitState) obj2;
                        return Boolean.valueOf(((EnterExitState) obj) == enterExitState && enterExitState == EnterExitState.PostExit);
                    }
                }, function3, composerImpl, 196608 | i4 | i3 | (i2 & 7168) | (57344 & i2) | ((i2 << 6) & 29360128), 64);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibilityImpl.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AnimatedVisibilityKt.AnimatedVisibilityImpl(transition, function1, modifier, enterTransition, exitTransition, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final EnterExitState targetEnterExit(Transition transition, Function1 function1, Object obj, Composer composer) {
        EnterExitState enterExitState;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.targetEnterExit (AnimatedVisibility.kt:836)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startMovableGroup(-902032957, transition);
        boolean zIsSeeking = transition.isSeeking();
        TransitionState transitionState = transition.transitionState;
        if (zIsSeeking) {
            composerImpl.startReplaceGroup(2101770115);
            composerImpl.end(false);
            enterExitState = ((Boolean) function1.mo781invoke(obj)).booleanValue() ? EnterExitState.Visible : ((Boolean) function1.mo781invoke(transitionState.getCurrentState())).booleanValue() ? EnterExitState.PostExit : EnterExitState.PreEnter;
        } else {
            composerImpl.startReplaceGroup(2102044248);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            if (((Boolean) function1.mo781invoke(transitionState.getCurrentState())).booleanValue()) {
                mutableState.setValue(Boolean.TRUE);
            }
            enterExitState = ((Boolean) function1.mo781invoke(obj)).booleanValue() ? EnterExitState.Visible : ((Boolean) mutableState.getValue()).booleanValue() ? EnterExitState.PostExit : EnterExitState.PreEnter;
            composerImpl.end(false);
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return enterExitState;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedVisibility(final RowScope rowScope, final boolean z, Modifier modifier, EnterTransition enterTransition, ExitTransition exitTransition, String str, final Function3 function3, Composer composer, final int i, final int i2) {
        boolean z2;
        int i3;
        Modifier modifier2;
        int i4;
        EnterTransition enterTransition2;
        int i5;
        int i6;
        Function3 function32;
        final ExitTransition exitTransition2;
        final String str2;
        final Modifier modifier3;
        final EnterTransition enterTransition3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i7;
        Modifier modifier4;
        int i8;
        EnterTransition enterTransitionPlus;
        ExitTransition exitTransitionPlus;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1741346906);
        if ((i2 & 1) != 0) {
            i3 = i | 48;
            z2 = z;
        } else if ((i & 48) == 0) {
            z2 = z;
            i3 = (composerImpl.changed(z2) ? 32 : 16) | i;
        } else {
            z2 = z;
            i3 = i;
        }
        int i9 = i2 & 2;
        if (i9 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    enterTransition2 = enterTransition;
                    i3 |= composerImpl.changed(enterTransition2) ? 2048 : 1024;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        i3 |= composerImpl.changed(exitTransition) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i6 = i2 & 16;
                    if (i6 != 0) {
                        if ((196608 & i) == 0) {
                            i3 |= composerImpl.changed(str) ? 131072 : 65536;
                        }
                        if ((i2 & 32) != 0) {
                            i3 |= 1572864;
                            function32 = function3;
                        } else {
                            function32 = function3;
                            if ((i & 1572864) == 0) {
                                i3 |= composerImpl.changedInstance(function32) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                        }
                        if (composerImpl.shouldExecute(i3 & 1, (599185 & i3) != 599184)) {
                            if (i9 != 0) {
                                modifier4 = Modifier.Companion;
                                i7 = i5;
                            } else {
                                i7 = i5;
                                modifier4 = modifier2;
                            }
                            if (i4 != 0) {
                                EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
                                IntSize.Companion companion = IntSize.Companion;
                                SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
                                Alignment.Companion.getClass();
                                BiasAlignment.Horizontal horizontal = Alignment.Companion.End;
                                final EnterExitTransitionKt$expandHorizontally$1 enterExitTransitionKt$expandHorizontally$1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$expandHorizontally$1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                        ((Number) obj).intValue();
                                        return 0;
                                    }
                                };
                                BiasAlignment alignment = EnterExitTransitionKt.toAlignment(horizontal);
                                Function1 function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$expandHorizontally$2
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        long j = ((IntSize) obj).packedValue;
                                        return IntSize.m861boximpl((((Number) enterExitTransitionKt$expandHorizontally$1.mo781invoke(Integer.valueOf((int) (j >> 32)))).intValue() << 32) | (((int) (j & 4294967295L)) & 4294967295L));
                                    }
                                };
                                i8 = 1;
                                enterTransitionPlus = enterTransitionFadeIn$default.plus(EnterExitTransitionKt.expandIn(springSpecSpring$default, alignment, true, function1));
                            } else {
                                i8 = 1;
                                enterTransitionPlus = enterTransition2;
                            }
                            if (i7 != 0) {
                                ExitTransition exitTransitionFadeOut$default = EnterExitTransitionKt.fadeOut$default(null, 3);
                                IntSize.Companion companion2 = IntSize.Companion;
                                SpringSpec springSpecSpring$default2 = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), i8);
                                Alignment.Companion.getClass();
                                BiasAlignment.Horizontal horizontal2 = Alignment.Companion.End;
                                final EnterExitTransitionKt$shrinkHorizontally$1 enterExitTransitionKt$shrinkHorizontally$1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$shrinkHorizontally$1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                        ((Number) obj).intValue();
                                        return 0;
                                    }
                                };
                                exitTransitionPlus = exitTransitionFadeOut$default.plus(EnterExitTransitionKt.shrinkOut(springSpecSpring$default2, EnterExitTransitionKt.toAlignment(horizontal2), true, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$shrinkHorizontally$2
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        long j = ((IntSize) obj).packedValue;
                                        return IntSize.m861boximpl((((Number) enterExitTransitionKt$shrinkHorizontally$1.mo781invoke(Integer.valueOf((int) (j >> 32)))).intValue() << 32) | (((int) (j & 4294967295L)) & 4294967295L));
                                    }
                                }));
                            } else {
                                exitTransitionPlus = exitTransition;
                            }
                            String str3 = i6 != 0 ? "AnimatedVisibility" : str;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.animation.AnimatedVisibility (AnimatedVisibility.kt:204)");
                            }
                            int i10 = i3 >> 3;
                            AnimatedVisibilityImpl(TransitionKt.updateTransition(Boolean.valueOf(z2), str3, composerImpl, (i10 & 14) | ((i3 >> 12) & 112), 0), new Function1() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility.3
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    Boolean bool = (Boolean) obj;
                                    bool.booleanValue();
                                    return bool;
                                }
                            }, modifier4, enterTransitionPlus, exitTransitionPlus, function32, composerImpl, (i3 & 896) | 48 | (i3 & 7168) | (57344 & i3) | (i10 & 458752));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            str2 = str3;
                            modifier3 = modifier4;
                            enterTransition3 = enterTransitionPlus;
                            exitTransition2 = exitTransitionPlus;
                        } else {
                            composerImpl.skipToGroupEnd();
                            exitTransition2 = exitTransition;
                            str2 = str;
                            modifier3 = modifier2;
                            enterTransition3 = enterTransition2;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility.4
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    AnimatedVisibilityKt.AnimatedVisibility(rowScope, z, modifier3, enterTransition3, exitTransition2, str2, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 196608;
                    if ((i2 & 32) != 0) {
                    }
                    if (composerImpl.shouldExecute(i3 & 1, (599185 & i3) != 599184)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                i6 = i2 & 16;
                if (i6 != 0) {
                }
                if ((i2 & 32) != 0) {
                }
                if (composerImpl.shouldExecute(i3 & 1, (599185 & i3) != 599184)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            enterTransition2 = enterTransition;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            i6 = i2 & 16;
            if (i6 != 0) {
            }
            if ((i2 & 32) != 0) {
            }
            if (composerImpl.shouldExecute(i3 & 1, (599185 & i3) != 599184)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        enterTransition2 = enterTransition;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        i6 = i2 & 16;
        if (i6 != 0) {
        }
        if ((i2 & 32) != 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (599185 & i3) != 599184)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedVisibility(final ColumnScope columnScope, boolean z, Modifier modifier, EnterTransition enterTransition, ExitTransition exitTransition, String str, final Function3 function3, Composer composer, final int i, final int i2) {
        boolean z2;
        int i3;
        Modifier modifier2;
        int i4;
        final EnterTransition enterTransition2;
        int i5;
        ExitTransition exitTransition2;
        int i6;
        String str2;
        Function3 function32;
        final ExitTransition exitTransition3;
        final Modifier modifier3;
        ComposerImpl composerImpl;
        final String str3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1766503102);
        if ((i2 & 1) != 0) {
            i3 = i | 48;
            z2 = z;
        } else {
            z2 = z;
            if ((i & 48) == 0) {
                i3 = (composerImpl2.changed(z2) ? 32 : 16) | i;
            } else {
                i3 = i;
            }
        }
        int i7 = i2 & 2;
        if (i7 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 256 : 128;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    enterTransition2 = enterTransition;
                    i3 |= composerImpl2.changed(enterTransition2) ? 2048 : 1024;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        exitTransition2 = exitTransition;
                        i3 |= composerImpl2.changed(exitTransition2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i6 = i2 & 16;
                    if (i6 == 0) {
                        i3 |= 196608;
                    } else {
                        if ((196608 & i) == 0) {
                            str2 = str;
                            i3 |= composerImpl2.changed(str2) ? 131072 : 65536;
                        }
                        if ((i2 & 32) == 0) {
                            if ((i & 1572864) == 0) {
                                function32 = function3;
                                i3 |= composerImpl2.changedInstance(function32) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                            if (!composerImpl2.shouldExecute(i3 & 1, (599185 & i3) == 599184)) {
                                if (i7 != 0) {
                                    modifier2 = Modifier.Companion;
                                }
                                EnterTransition enterTransitionPlus = i4 != 0 ? EnterExitTransitionKt.fadeIn$default(null, 3).plus(EnterExitTransitionKt.expandVertically$default(null, null, null, 15)) : enterTransition2;
                                ExitTransition exitTransitionPlus = i5 != 0 ? EnterExitTransitionKt.fadeOut$default(null, 3).plus(EnterExitTransitionKt.shrinkVertically$default(null, null, null, 15)) : exitTransition2;
                                if (i6 != 0) {
                                    str2 = "AnimatedVisibility";
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.animation.AnimatedVisibility (AnimatedVisibility.kt:277)");
                                }
                                int i8 = i3 >> 3;
                                AnimatedVisibilityImpl(TransitionKt.updateTransition(Boolean.valueOf(z2), str2, composerImpl2, (i8 & 14) | ((i3 >> 12) & 112), 0), new Function1() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility.5
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        Boolean bool = (Boolean) obj;
                                        bool.booleanValue();
                                        return bool;
                                    }
                                }, modifier2, enterTransitionPlus, exitTransitionPlus, function32, composerImpl2, (i3 & 57344) | (i3 & 896) | 48 | (i3 & 7168) | (i8 & 458752));
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                exitTransition3 = exitTransitionPlus;
                                enterTransition2 = enterTransitionPlus;
                                composerImpl = composerImpl2;
                                str3 = str2;
                                modifier3 = modifier2;
                            } else {
                                composerImpl2.skipToGroupEnd();
                                exitTransition3 = exitTransition2;
                                modifier3 = modifier2;
                                composerImpl = composerImpl2;
                                str3 = str2;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                final boolean z3 = z2;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility.6
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        AnimatedVisibilityKt.AnimatedVisibility(columnScope, z3, modifier3, enterTransition2, exitTransition3, str3, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 1572864;
                        function32 = function3;
                        if (!composerImpl2.shouldExecute(i3 & 1, (599185 & i3) == 599184)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    str2 = str;
                    if ((i2 & 32) == 0) {
                    }
                    function32 = function3;
                    if (!composerImpl2.shouldExecute(i3 & 1, (599185 & i3) == 599184)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                exitTransition2 = exitTransition;
                i6 = i2 & 16;
                if (i6 == 0) {
                }
                str2 = str;
                if ((i2 & 32) == 0) {
                }
                function32 = function3;
                if (!composerImpl2.shouldExecute(i3 & 1, (599185 & i3) == 599184)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            enterTransition2 = enterTransition;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            exitTransition2 = exitTransition;
            i6 = i2 & 16;
            if (i6 == 0) {
            }
            str2 = str;
            if ((i2 & 32) == 0) {
            }
            function32 = function3;
            if (!composerImpl2.shouldExecute(i3 & 1, (599185 & i3) == 599184)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        enterTransition2 = enterTransition;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        exitTransition2 = exitTransition;
        i6 = i2 & 16;
        if (i6 == 0) {
        }
        str2 = str;
        if ((i2 & 32) == 0) {
        }
        function32 = function3;
        if (!composerImpl2.shouldExecute(i3 & 1, (599185 & i3) == 599184)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
