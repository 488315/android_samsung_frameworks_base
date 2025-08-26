package androidx.compose.animation;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.VectorConvertersKt;
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
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class AnimatedContentKt {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long UnspecifiedSize;

    static {
        long j = Integer.MIN_VALUE;
        IntSize.Companion companion = IntSize.Companion;
        UnspecifiedSize = (j & 4294967295L) | (j << 32);
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:114:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedContent(final Object obj, Modifier modifier, Function1 function1, Alignment alignment, String str, Function1 function12, final Function4 function4, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        int i4;
        Function1 function13;
        int i5;
        Alignment alignment2;
        int i6;
        int i7;
        Function4 function42;
        final Modifier modifier2;
        final Function1 function14;
        final Function1 function15;
        final Alignment alignment3;
        final String str2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i8;
        Function1 function16;
        Alignment alignment4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2132720749);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = ((i & 8) == 0 ? composerImpl.changed(obj) : composerImpl.changedInstance(obj) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i9 = i2 & 2;
        if (i9 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                i3 |= composerImpl.changed(modifier) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function13 = function1;
                    i3 |= composerImpl.changedInstance(function13) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        alignment2 = alignment;
                        i3 |= composerImpl.changed(alignment2) ? 2048 : 1024;
                    }
                    i6 = i2 & 16;
                    if (i6 == 0) {
                        i3 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            i3 |= composerImpl.changed(str) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i7 = i2 & 32;
                        if (i7 == 0) {
                            if ((196608 & i) == 0) {
                                i3 |= composerImpl.changedInstance(function12) ? 131072 : 65536;
                            }
                            if ((i2 & 64) == 0) {
                                i3 |= 1572864;
                                function42 = function4;
                            } else {
                                function42 = function4;
                                if ((i & 1572864) == 0) {
                                    i3 |= composerImpl.changedInstance(function42) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                                }
                            }
                            if (composerImpl.shouldExecute(i3 & 1, (599187 & i3) == 599186)) {
                                composerImpl.skipToGroupEnd();
                                modifier2 = modifier;
                                function14 = function12;
                                function15 = function13;
                                alignment3 = alignment2;
                                str2 = str;
                            } else {
                                Modifier modifier3 = i9 != 0 ? Modifier.Companion : modifier;
                                if (i4 != 0) {
                                    function16 = new Function1() { // from class: androidx.compose.animation.AnimatedContentKt.AnimatedContent.1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj2) {
                                            return AnimatedContentKt.togetherWith(EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(220, 90, null, 4), 2).plus(EnterExitTransitionKt.m5scaleInL8ZKhE$default(AnimationSpecKt.tween$default(220, 90, null, 4), 0.92f, 4)), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(90, 0, null, 6), 2));
                                        }
                                    };
                                    i8 = i7;
                                } else {
                                    i8 = i7;
                                    function16 = function13;
                                }
                                if (i5 != 0) {
                                    Alignment.Companion.getClass();
                                    alignment4 = Alignment.Companion.TopStart;
                                } else {
                                    alignment4 = alignment2;
                                }
                                String str3 = i6 != 0 ? "AnimatedContent" : str;
                                Function1 function17 = i8 != 0 ? new Function1() { // from class: androidx.compose.animation.AnimatedContentKt.AnimatedContent.2
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        return obj2;
                                    }
                                } : function12;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.animation.AnimatedContent (AnimatedContent.kt:140)");
                                }
                                Transition transitionUpdateTransition = TransitionKt.updateTransition(obj, str3, composerImpl, (i3 & 14) | ((i3 >> 9) & 112), 0);
                                int i10 = i3 & 8176;
                                int i11 = i3 >> 3;
                                AnimatedContent(transitionUpdateTransition, modifier3, function16, alignment4, function17, function42, composerImpl, i10 | (57344 & i11) | (i11 & 458752), 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                str2 = str3;
                                modifier2 = modifier3;
                                function15 = function16;
                                alignment3 = alignment4;
                                function14 = function17;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.AnimatedContentKt.AnimatedContent.3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj2, Object obj3) throws Throwable {
                                        ((Number) obj3).intValue();
                                        AnimatedContentKt.AnimatedContent(obj, modifier2, function15, alignment3, str2, function14, function4, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 196608;
                        if ((i2 & 64) == 0) {
                        }
                        if (composerImpl.shouldExecute(i3 & 1, (599187 & i3) == 599186)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    i7 = i2 & 32;
                    if (i7 == 0) {
                    }
                    if ((i2 & 64) == 0) {
                    }
                    if (composerImpl.shouldExecute(i3 & 1, (599187 & i3) == 599186)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                alignment2 = alignment;
                i6 = i2 & 16;
                if (i6 == 0) {
                }
                i7 = i2 & 32;
                if (i7 == 0) {
                }
                if ((i2 & 64) == 0) {
                }
                if (composerImpl.shouldExecute(i3 & 1, (599187 & i3) == 599186)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            function13 = function1;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            alignment2 = alignment;
            i6 = i2 & 16;
            if (i6 == 0) {
            }
            i7 = i2 & 32;
            if (i7 == 0) {
            }
            if ((i2 & 64) == 0) {
            }
            if (composerImpl.shouldExecute(i3 & 1, (599187 & i3) == 599186)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        function13 = function1;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        alignment2 = alignment;
        i6 = i2 & 16;
        if (i6 == 0) {
        }
        i7 = i2 & 32;
        if (i7 == 0) {
        }
        if ((i2 & 64) == 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (599187 & i3) == 599186)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final ContentTransform togetherWith(EnterTransition enterTransition, ExitTransition exitTransition) {
        return new ContentTransform(enterTransition, exitTransition, 0.0f, null, 12, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x043c  */
    /* JADX WARN: Removed duplicated region for block: B:232:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x011c  */
    /* JADX WARN: Type inference failed for: r0v11, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedContent(final Transition transition, Modifier modifier, Function1 function1, Alignment alignment, Function1 function12, final Function4 function4, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        Modifier modifier2;
        int i4;
        Function1 function13;
        int i5;
        int i6;
        Function1 function14;
        final Alignment alignment2;
        final Modifier modifier3;
        final Function1 function15;
        final Function1 function16;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i7;
        Alignment alignment3;
        AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl;
        ?? r0;
        Function1 function17;
        MutableScatterMap mutableScatterMap;
        Throwable th;
        Modifier modifier4;
        Transition.DeferredAnimation deferredAnimationCreateDeferredAnimation;
        final Transition transition2 = transition;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-114689412);
        if ((i2 & Integer.MIN_VALUE) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(transition2) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i8 = i2 & 1;
        if (i8 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 2;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function13 = function1;
                    i3 |= composerImpl.changedInstance(function13) ? 256 : 128;
                }
                i5 = i2 & 4;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        i3 |= composerImpl.changed(alignment) ? 2048 : 1024;
                    }
                    i6 = i2 & 8;
                    if (i6 == 0) {
                        i3 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            function14 = function12;
                            i3 |= composerImpl.changedInstance(function14) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if ((i2 & 16) == 0) {
                            if ((i & 196608) == 0) {
                                i3 |= composerImpl.changedInstance(function4) ? 131072 : 65536;
                            }
                            if (!composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                                Modifier modifier5 = i8 != 0 ? Modifier.Companion : modifier2;
                                if (i4 != 0) {
                                    int i9 = i3;
                                    function15 = new Function1() { // from class: androidx.compose.animation.AnimatedContentKt.AnimatedContent.4
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            return AnimatedContentKt.togetherWith(EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(220, 90, null, 4), 2).plus(EnterExitTransitionKt.m5scaleInL8ZKhE$default(AnimationSpecKt.tween$default(220, 90, null, 4), 0.92f, 4)), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(90, 0, null, 6), 2));
                                        }
                                    };
                                    i7 = i9;
                                } else {
                                    i7 = i3;
                                    function15 = function13;
                                }
                                if (i5 != 0) {
                                    Alignment.Companion.getClass();
                                    alignment3 = Alignment.Companion.TopStart;
                                } else {
                                    alignment3 = alignment;
                                }
                                Function1 function18 = i6 != 0 ? new Function1() { // from class: androidx.compose.animation.AnimatedContentKt.AnimatedContent.5
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        return obj;
                                    }
                                } : function14;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.animation.AnimatedContent (AnimatedContent.kt:772)");
                                }
                                LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
                                int i10 = i7 & 14;
                                boolean z = i10 == 4;
                                Object objRememberedValue = composerImpl.rememberedValue();
                                Composer.Companion companion = Composer.Companion;
                                if (!z) {
                                    companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new AnimatedContentTransitionScopeImpl(transition2, alignment3, layoutDirection);
                                        composerImpl.updateRememberedValue(objRememberedValue);
                                    }
                                    final AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl2 = (AnimatedContentTransitionScopeImpl) objRememberedValue;
                                    boolean z2 = i10 == 4;
                                    Object objRememberedValue2 = composerImpl.rememberedValue();
                                    if (!z2) {
                                        companion.getClass();
                                        Object obj = objRememberedValue2;
                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                            Object[] objArr = {transition2.transitionState.getCurrentState()};
                                            SnapshotStateList snapshotStateList = new SnapshotStateList();
                                            snapshotStateList.addAll(ArraysKt___ArraysKt.toList(objArr));
                                            composerImpl.updateRememberedValue(snapshotStateList);
                                            obj = snapshotStateList;
                                        }
                                        final SnapshotStateList snapshotStateList2 = (SnapshotStateList) obj;
                                        boolean z3 = i10 == 4;
                                        Object objRememberedValue3 = composerImpl.rememberedValue();
                                        if (!z3) {
                                            companion.getClass();
                                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                                objRememberedValue3 = ScatterMapKt.mutableScatterMapOf();
                                                composerImpl.updateRememberedValue(objRememberedValue3);
                                            }
                                            MutableScatterMap mutableScatterMap2 = (MutableScatterMap) objRememberedValue3;
                                            boolean zContains = snapshotStateList2.contains(transition2.transitionState.getCurrentState());
                                            TransitionState transitionState = transition2.transitionState;
                                            if (!zContains) {
                                                snapshotStateList2.clear();
                                                snapshotStateList2.add(transitionState.getCurrentState());
                                            }
                                            Object currentState = transitionState.getCurrentState();
                                            SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) transition2.targetState$delegate;
                                            if (Intrinsics.areEqual(currentState, snapshotMutableStateImpl.getValue())) {
                                                if (snapshotStateList2.size() != 1 || !Intrinsics.areEqual(snapshotStateList2.get(0), transitionState.getCurrentState())) {
                                                    snapshotStateList2.clear();
                                                    snapshotStateList2.add(transitionState.getCurrentState());
                                                }
                                                if (mutableScatterMap2._size != 1 || mutableScatterMap2.containsKey(transitionState.getCurrentState())) {
                                                    mutableScatterMap2.clear();
                                                }
                                                animatedContentTransitionScopeImpl2.contentAlignment = alignment3;
                                            }
                                            if (!Intrinsics.areEqual(transitionState.getCurrentState(), snapshotMutableStateImpl.getValue()) && !snapshotStateList2.contains(snapshotMutableStateImpl.getValue())) {
                                                ListIterator listIterator = snapshotStateList2.listIterator();
                                                int i11 = 0;
                                                while (true) {
                                                    if (!listIterator.hasNext()) {
                                                        i11 = -1;
                                                        break;
                                                    } else if (Intrinsics.areEqual(function18.mo781invoke(listIterator.next()), function18.mo781invoke(snapshotMutableStateImpl.getValue()))) {
                                                        break;
                                                    } else {
                                                        i11++;
                                                    }
                                                }
                                                if (i11 == -1) {
                                                    snapshotStateList2.add(snapshotMutableStateImpl.getValue());
                                                } else {
                                                    snapshotStateList2.set(i11, snapshotMutableStateImpl.getValue());
                                                }
                                            }
                                            if (mutableScatterMap2.containsKey(snapshotMutableStateImpl.getValue()) && mutableScatterMap2.containsKey(transitionState.getCurrentState())) {
                                                composerImpl.startReplaceGroup(919489879);
                                                composerImpl.end(false);
                                                animatedContentTransitionScopeImpl = animatedContentTransitionScopeImpl2;
                                                r0 = 0;
                                            } else {
                                                composerImpl.startReplaceGroup(916905750);
                                                mutableScatterMap2.clear();
                                                int size = snapshotStateList2.size();
                                                int i12 = 0;
                                                while (i12 < size) {
                                                    final Object obj2 = snapshotStateList2.get(i12);
                                                    mutableScatterMap2.set(obj2, ComposableLambdaKt.rememberComposableLambda(885640742, new Function2() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function2
                                                        public final Object invoke(Object obj3, Object obj4) {
                                                            Object obj5;
                                                            Composer composer2 = (Composer) obj3;
                                                            int iIntValue = ((Number) obj4).intValue();
                                                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                            if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventStart("androidx.compose.animation.AnimatedContent.<anonymous>.<anonymous> (AnimatedContent.kt:817)");
                                                                }
                                                                Function1 function19 = function15;
                                                                AnimatedContentTransitionScopeImpl<Object> animatedContentTransitionScopeImpl3 = animatedContentTransitionScopeImpl2;
                                                                Object objRememberedValue4 = composerImpl2.rememberedValue();
                                                                Composer.Companion.getClass();
                                                                Object obj6 = Composer.Companion.Empty;
                                                                if (objRememberedValue4 == obj6) {
                                                                    objRememberedValue4 = (ContentTransform) function19.mo781invoke(animatedContentTransitionScopeImpl3);
                                                                    composerImpl2.updateRememberedValue(objRememberedValue4);
                                                                }
                                                                final ContentTransform contentTransform = (ContentTransform) objRememberedValue4;
                                                                boolean zChanged = composerImpl2.changed(Intrinsics.areEqual(transition2.getSegment().getTargetState(), obj2));
                                                                Transition<Object> transition3 = transition2;
                                                                Object obj7 = obj2;
                                                                Function1 function110 = function15;
                                                                AnimatedContentTransitionScopeImpl<Object> animatedContentTransitionScopeImpl4 = animatedContentTransitionScopeImpl2;
                                                                Object objRememberedValue5 = composerImpl2.rememberedValue();
                                                                if (zChanged || objRememberedValue5 == obj6) {
                                                                    if (Intrinsics.areEqual(transition3.getSegment().getTargetState(), obj7)) {
                                                                        ExitTransition.Companion.getClass();
                                                                        obj5 = ExitTransition.None;
                                                                    } else {
                                                                        obj5 = ((ContentTransform) function110.mo781invoke(animatedContentTransitionScopeImpl4)).initialContentExit;
                                                                    }
                                                                    objRememberedValue5 = obj5;
                                                                    composerImpl2.updateRememberedValue(objRememberedValue5);
                                                                }
                                                                final ExitTransition exitTransition = (ExitTransition) objRememberedValue5;
                                                                Object obj8 = obj2;
                                                                Transition<Object> transition4 = transition2;
                                                                Object objRememberedValue6 = composerImpl2.rememberedValue();
                                                                if (objRememberedValue6 == obj6) {
                                                                    objRememberedValue6 = new AnimatedContentTransitionScopeImpl.ChildData(Intrinsics.areEqual(obj8, ((SnapshotMutableStateImpl) transition4.targetState$delegate).getValue()));
                                                                    composerImpl2.updateRememberedValue(objRememberedValue6);
                                                                }
                                                                AnimatedContentTransitionScopeImpl.ChildData childData = (AnimatedContentTransitionScopeImpl.ChildData) objRememberedValue6;
                                                                EnterTransition enterTransition = contentTransform.targetContentEnter;
                                                                Modifier.Companion companion2 = Modifier.Companion;
                                                                boolean zChangedInstance = composerImpl2.changedInstance(contentTransform);
                                                                Object objRememberedValue7 = composerImpl2.rememberedValue();
                                                                if (zChangedInstance || objRememberedValue7 == obj6) {
                                                                    objRememberedValue7 = new Function3() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1$1$1
                                                                        {
                                                                            super(3);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function3
                                                                        public final Object invoke(Object obj9, Object obj10, Object obj11) {
                                                                            final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj10).mo610measureBRTryo0(((Constraints) obj11).value);
                                                                            int i13 = placeableMo610measureBRTryo0.width;
                                                                            int i14 = placeableMo610measureBRTryo0.height;
                                                                            final ContentTransform contentTransform2 = contentTransform;
                                                                            return ((MeasureScope) obj9).layout$1(i13, i14, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1$1$1.1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj12) {
                                                                                    ((Placeable.PlacementScope) obj12).place(placeableMo610measureBRTryo0, 0, 0, ((SnapshotMutableFloatStateImpl) contentTransform2.targetContentZIndex$delegate).getFloatValue());
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            });
                                                                        }
                                                                    };
                                                                    composerImpl2.updateRememberedValue(objRememberedValue7);
                                                                }
                                                                Modifier modifierLayout = LayoutModifierKt.layout(companion2, (Function3) objRememberedValue7);
                                                                ((SnapshotMutableStateImpl) childData.isTarget$delegate).setValue(Boolean.valueOf(Intrinsics.areEqual(obj2, ((SnapshotMutableStateImpl) transition2.targetState$delegate).getValue())));
                                                                Modifier modifierThen = modifierLayout.then(childData);
                                                                Transition<Object> transition5 = transition2;
                                                                boolean zChangedInstance2 = composerImpl2.changedInstance(obj2);
                                                                final Object obj9 = obj2;
                                                                Object objRememberedValue8 = composerImpl2.rememberedValue();
                                                                if (zChangedInstance2 || objRememberedValue8 == obj6) {
                                                                    objRememberedValue8 = new Function1() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1$3$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final Object mo781invoke(Object obj10) {
                                                                            return Boolean.valueOf(Intrinsics.areEqual(obj10, obj9));
                                                                        }
                                                                    };
                                                                    composerImpl2.updateRememberedValue(objRememberedValue8);
                                                                }
                                                                Function1 function111 = (Function1) objRememberedValue8;
                                                                boolean zChanged2 = composerImpl2.changed(exitTransition);
                                                                Object objRememberedValue9 = composerImpl2.rememberedValue();
                                                                if (zChanged2 || objRememberedValue9 == obj6) {
                                                                    objRememberedValue9 = new Function2() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1$4$1
                                                                        {
                                                                            super(2);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function2
                                                                        public final Object invoke(Object obj10, Object obj11) {
                                                                            EnterExitState enterExitState = (EnterExitState) obj10;
                                                                            EnterExitState enterExitState2 = (EnterExitState) obj11;
                                                                            EnterExitState enterExitState3 = EnterExitState.PostExit;
                                                                            return Boolean.valueOf(enterExitState == enterExitState3 && enterExitState2 == enterExitState3 && !exitTransition.getData$animation().hold);
                                                                        }
                                                                    };
                                                                    composerImpl2.updateRememberedValue(objRememberedValue9);
                                                                }
                                                                Function2 function2 = (Function2) objRememberedValue9;
                                                                final SnapshotStateList<Object> snapshotStateList3 = snapshotStateList2;
                                                                final Object obj10 = obj2;
                                                                final AnimatedContentTransitionScopeImpl<Object> animatedContentTransitionScopeImpl5 = animatedContentTransitionScopeImpl2;
                                                                final Function4 function42 = function4;
                                                                AnimatedVisibilityKt.AnimatedEnterExitImpl(transition5, function111, modifierThen, enterTransition, exitTransition, function2, ComposableLambdaKt.rememberComposableLambda(-616195562, new Function3() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1.5
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(3);
                                                                    }

                                                                    /* JADX WARN: Removed duplicated region for block: B:25:0x006f  */
                                                                    @Override // kotlin.jvm.functions.Function3
                                                                    /*
                                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                                    */
                                                                    public final Object invoke(Object obj11, Object obj12, Object obj13) {
                                                                        AnimatedVisibilityScope animatedVisibilityScope = (AnimatedVisibilityScope) obj11;
                                                                        Composer composer3 = (Composer) obj12;
                                                                        int iIntValue2 = ((Number) obj13).intValue();
                                                                        if ((iIntValue2 & 6) == 0) {
                                                                            iIntValue2 |= (iIntValue2 & 8) == 0 ? ((ComposerImpl) composer3).changed(animatedVisibilityScope) : ((ComposerImpl) composer3).changedInstance(animatedVisibilityScope) ? 4 : 2;
                                                                        }
                                                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                                        if (composerImpl3.shouldExecute(iIntValue2 & 1, (iIntValue2 & 19) != 18)) {
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventStart("androidx.compose.animation.AnimatedContent.<anonymous>.<anonymous>.<anonymous> (AnimatedContent.kt:853)");
                                                                            }
                                                                            boolean zChanged3 = composerImpl3.changed(snapshotStateList3) | composerImpl3.changedInstance(obj10) | composerImpl3.changedInstance(animatedContentTransitionScopeImpl5);
                                                                            final SnapshotStateList<Object> snapshotStateList4 = snapshotStateList3;
                                                                            final Object obj14 = obj10;
                                                                            final AnimatedContentTransitionScopeImpl<Object> animatedContentTransitionScopeImpl6 = animatedContentTransitionScopeImpl5;
                                                                            Object objRememberedValue10 = composerImpl3.rememberedValue();
                                                                            Composer.Companion companion3 = Composer.Companion;
                                                                            if (!zChanged3) {
                                                                                companion3.getClass();
                                                                                if (objRememberedValue10 == Composer.Companion.Empty) {
                                                                                    objRememberedValue10 = new Function1() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1$5$1$1
                                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                        {
                                                                                            super(1);
                                                                                        }

                                                                                        @Override // kotlin.jvm.functions.Function1
                                                                                        /* renamed from: invoke */
                                                                                        public final Object mo781invoke(Object obj15) {
                                                                                            final SnapshotStateList<Object> snapshotStateList5 = snapshotStateList4;
                                                                                            final Object obj16 = obj14;
                                                                                            final AnimatedContentTransitionScopeImpl<Object> animatedContentTransitionScopeImpl7 = animatedContentTransitionScopeImpl6;
                                                                                            return new DisposableEffectResult() { // from class: androidx.compose.animation.AnimatedContentKt$AnimatedContent$6$1$5$1$1$invoke$$inlined$onDispose$1
                                                                                                @Override // androidx.compose.runtime.DisposableEffectResult
                                                                                                public final void dispose() {
                                                                                                    SnapshotStateList snapshotStateList6 = snapshotStateList5;
                                                                                                    Object obj17 = obj16;
                                                                                                    snapshotStateList6.remove(obj17);
                                                                                                    animatedContentTransitionScopeImpl7.targetSizeMap.remove(obj17);
                                                                                                }
                                                                                            };
                                                                                        }
                                                                                    };
                                                                                    composerImpl3.updateRememberedValue(objRememberedValue10);
                                                                                }
                                                                                EffectsKt.DisposableEffect(animatedVisibilityScope, (Function1) objRememberedValue10, composerImpl3);
                                                                                animatedContentTransitionScopeImpl5.targetSizeMap.set(obj10, ((AnimatedVisibilityScopeImpl) animatedVisibilityScope).targetSize);
                                                                                Object objRememberedValue11 = composerImpl3.rememberedValue();
                                                                                companion3.getClass();
                                                                                if (objRememberedValue11 == Composer.Companion.Empty) {
                                                                                    objRememberedValue11 = new AnimatedContentScopeImpl(animatedVisibilityScope);
                                                                                    composerImpl3.updateRememberedValue(objRememberedValue11);
                                                                                }
                                                                                function42.invoke((AnimatedContentScopeImpl) objRememberedValue11, obj10, composerImpl3, 0);
                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                    ComposerKt.traceEventEnd();
                                                                                }
                                                                            }
                                                                        } else {
                                                                            composerImpl3.skipToGroupEnd();
                                                                        }
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                }, composerImpl2), composerImpl2, 12582912, 64);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                            } else {
                                                                composerImpl2.skipToGroupEnd();
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl));
                                                    i12++;
                                                    transition2 = transition;
                                                }
                                                animatedContentTransitionScopeImpl = animatedContentTransitionScopeImpl2;
                                                r0 = 0;
                                                composerImpl.end(false);
                                            }
                                            boolean zChanged = composerImpl.changed(transition.getSegment()) | composerImpl.changed(animatedContentTransitionScopeImpl);
                                            Object objRememberedValue4 = composerImpl.rememberedValue();
                                            if (!zChanged) {
                                                companion.getClass();
                                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                                    objRememberedValue4 = (ContentTransform) function15.mo781invoke(animatedContentTransitionScopeImpl);
                                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                                }
                                                ContentTransform contentTransform = (ContentTransform) objRememberedValue4;
                                                animatedContentTransitionScopeImpl.getClass();
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.animation.AnimatedContentTransitionScopeImpl.createSizeAnimationModifier (AnimatedContent.kt:556)");
                                                }
                                                boolean zChanged2 = composerImpl.changed(animatedContentTransitionScopeImpl);
                                                Object objRememberedValue5 = composerImpl.rememberedValue();
                                                if (!zChanged2) {
                                                    companion.getClass();
                                                    if (objRememberedValue5 == Composer.Companion.Empty) {
                                                        objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                                                        composerImpl.updateRememberedValue(objRememberedValue5);
                                                    }
                                                    MutableState mutableState = (MutableState) objRememberedValue5;
                                                    MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(contentTransform.sizeTransform, composerImpl);
                                                    Transition transition3 = animatedContentTransitionScopeImpl.transition;
                                                    if (Intrinsics.areEqual(transition3.transitionState.getCurrentState(), ((SnapshotMutableStateImpl) transition3.targetState$delegate).getValue())) {
                                                        mutableState.setValue(Boolean.FALSE);
                                                    } else if (mutableStateRememberUpdatedState.getValue() != null) {
                                                        mutableState.setValue(Boolean.TRUE);
                                                    }
                                                    if (((Boolean) mutableState.getValue()).booleanValue()) {
                                                        composerImpl.startReplaceGroup(249676467);
                                                        IntSize.Companion companion2 = IntSize.Companion;
                                                        mutableScatterMap = mutableScatterMap2;
                                                        function17 = function18;
                                                        th = null;
                                                        deferredAnimationCreateDeferredAnimation = TransitionKt.createDeferredAnimation(animatedContentTransitionScopeImpl.transition, VectorConvertersKt.IntSizeToVector, null, composerImpl, 0, 2);
                                                        boolean zChanged3 = composerImpl.changed(deferredAnimationCreateDeferredAnimation);
                                                        Object objRememberedValue6 = composerImpl.rememberedValue();
                                                        if (!zChanged3) {
                                                            companion.getClass();
                                                            if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                SizeTransform sizeTransform = (SizeTransform) mutableStateRememberUpdatedState.getValue();
                                                                objRememberedValue6 = (sizeTransform == null || ((SizeTransformImpl) sizeTransform).clip) ? ClipKt.clipToBounds(Modifier.Companion) : Modifier.Companion;
                                                                composerImpl.updateRememberedValue(objRememberedValue6);
                                                            }
                                                            modifier4 = (Modifier) objRememberedValue6;
                                                            composerImpl.end(r0);
                                                        }
                                                    } else {
                                                        function17 = function18;
                                                        mutableScatterMap = mutableScatterMap2;
                                                        th = null;
                                                        composerImpl.startReplaceGroup(249942509);
                                                        composerImpl.end(r0);
                                                        modifier4 = Modifier.Companion;
                                                        deferredAnimationCreateDeferredAnimation = null;
                                                    }
                                                    Modifier modifierThen = modifier4.then(new AnimatedContentTransitionScopeImpl.SizeModifierElement(deferredAnimationCreateDeferredAnimation, mutableStateRememberUpdatedState, animatedContentTransitionScopeImpl));
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    Modifier modifierThen2 = modifier5.then(modifierThen);
                                                    Object objRememberedValue7 = composerImpl.rememberedValue();
                                                    companion.getClass();
                                                    if (objRememberedValue7 == Composer.Companion.Empty) {
                                                        objRememberedValue7 = new AnimatedContentMeasurePolicy(animatedContentTransitionScopeImpl);
                                                        composerImpl.updateRememberedValue(objRememberedValue7);
                                                    }
                                                    AnimatedContentMeasurePolicy animatedContentMeasurePolicy = (AnimatedContentMeasurePolicy) objRememberedValue7;
                                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen2);
                                                    ComposeUiNode.Companion.getClass();
                                                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                    if (composerImpl.applier != null) {
                                                        composerImpl.startReusableNode();
                                                        if (composerImpl.inserting) {
                                                            composerImpl.createNode(function0);
                                                        } else {
                                                            composerImpl.useNode();
                                                        }
                                                        Updater.m337setimpl(composerImpl, animatedContentMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                                                        }
                                                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                        composerImpl.startReplaceGroup(-1490874326);
                                                        int size2 = snapshotStateList2.size();
                                                        for (int i13 = r0; i13 < size2; i13++) {
                                                            Object obj3 = snapshotStateList2.get(i13);
                                                            composerImpl.startMovableGroup(1908442329, function17.mo781invoke(obj3));
                                                            Function2 function22 = (Function2) mutableScatterMap.get(obj3);
                                                            if (function22 == null) {
                                                                composerImpl.startReplaceGroup(-967793488);
                                                            } else {
                                                                composerImpl.startReplaceGroup(1908443505);
                                                                function22.invoke(composerImpl, Integer.valueOf((int) r0));
                                                            }
                                                            composerImpl.end(r0);
                                                            composerImpl.end(r0);
                                                        }
                                                        if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, r0, true)) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                        function16 = function17;
                                                        modifier3 = modifier5;
                                                        alignment2 = alignment3;
                                                    } else {
                                                        ComposablesKt.invalidApplier();
                                                        throw th;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                composerImpl.skipToGroupEnd();
                                alignment2 = alignment;
                                modifier3 = modifier2;
                                function15 = function13;
                                function16 = function14;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.AnimatedContentKt.AnimatedContent.9
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj4, Object obj5) throws Throwable {
                                        ((Number) obj5).intValue();
                                        AnimatedContentKt.AnimatedContent(transition, modifier3, function15, alignment2, function16, function4, (Composer) obj4, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 196608;
                        if (!composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    function14 = function12;
                    if ((i2 & 16) == 0) {
                    }
                    if (!composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                i6 = i2 & 8;
                if (i6 == 0) {
                }
                function14 = function12;
                if ((i2 & 16) == 0) {
                }
                if (!composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            function13 = function1;
            i5 = i2 & 4;
            if (i5 != 0) {
            }
            i6 = i2 & 8;
            if (i6 == 0) {
            }
            function14 = function12;
            if ((i2 & 16) == 0) {
            }
            if (!composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 2;
        if (i4 == 0) {
        }
        function13 = function1;
        i5 = i2 & 4;
        if (i5 != 0) {
        }
        i6 = i2 & 8;
        if (i6 == 0) {
        }
        function14 = function12;
        if ((i2 & 16) == 0) {
        }
        if (!composerImpl.shouldExecute(i3 & 1, (74899 & i3) == 74898)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
