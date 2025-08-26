package androidx.compose.animation;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class CrossfadeKt {
    /* JADX WARN: Removed duplicated region for block: B:29:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Crossfade(final Object obj, Modifier modifier, FiniteAnimationSpec finiteAnimationSpec, String str, final Function3 function3, Composer composer, final int i, final int i2) {
        int i3;
        final Modifier modifier2;
        int i4;
        FiniteAnimationSpec finiteAnimationSpec2;
        int i5;
        String str2;
        Function3 function32;
        final FiniteAnimationSpec finiteAnimationSpec3;
        final String str3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-310686752);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = ((i & 8) == 0 ? composerImpl.changed(obj) : composerImpl.changedInstance(obj) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i6 = i2 & 2;
        if (i6 != 0) {
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
                    finiteAnimationSpec2 = finiteAnimationSpec;
                    i3 |= composerImpl.changedInstance(finiteAnimationSpec2) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 == 0) {
                    if ((i & 3072) == 0) {
                        str2 = str;
                        i3 |= composerImpl.changed(str2) ? 2048 : 1024;
                    }
                    if ((i2 & 16) == 0) {
                        i3 |= 24576;
                        function32 = function3;
                    } else {
                        function32 = function3;
                        if ((i & 24576) == 0) {
                            i3 |= composerImpl.changedInstance(function32) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                    }
                    if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
                        composerImpl.skipToGroupEnd();
                        finiteAnimationSpec3 = finiteAnimationSpec2;
                        str3 = str2;
                    } else {
                        Modifier modifier3 = i6 != 0 ? Modifier.Companion : modifier2;
                        FiniteAnimationSpec finiteAnimationSpecTween$default = i4 != 0 ? AnimationSpecKt.tween$default(0, 0, null, 7) : finiteAnimationSpec2;
                        String str4 = i5 != 0 ? "Crossfade" : str2;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.animation.Crossfade (Crossfade.kt:55)");
                        }
                        Crossfade(TransitionKt.updateTransition(obj, str4, composerImpl, (i3 & 14) | ((i3 >> 6) & 112), 0), modifier3, finiteAnimationSpecTween$default, (Function1) null, function32, composerImpl, i3 & 58352, 4);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        str3 = str4;
                        modifier2 = modifier3;
                        finiteAnimationSpec3 = finiteAnimationSpecTween$default;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.CrossfadeKt.Crossfade.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                ((Number) obj3).intValue();
                                CrossfadeKt.Crossfade(obj, modifier2, finiteAnimationSpec3, str3, function3, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 3072;
                str2 = str;
                if ((i2 & 16) == 0) {
                }
                if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            finiteAnimationSpec2 = finiteAnimationSpec;
            i5 = i2 & 8;
            if (i5 == 0) {
            }
            str2 = str;
            if ((i2 & 16) == 0) {
            }
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        finiteAnimationSpec2 = finiteAnimationSpec;
        i5 = i2 & 8;
        if (i5 == 0) {
        }
        str2 = str;
        if ((i2 & 16) == 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:142:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:151:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Crossfade(final Transition transition, Modifier modifier, FiniteAnimationSpec finiteAnimationSpec, Function1 function1, final Function3 function3, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        int i4;
        final FiniteAnimationSpec finiteAnimationSpecTween$default;
        int i5;
        Function1 function12;
        final Modifier modifier3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        boolean z;
        boolean z2 = true;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(679005231);
        if ((i2 & Integer.MIN_VALUE) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(transition) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i6 = i2 & 1;
        if (i6 != 0) {
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
                    finiteAnimationSpecTween$default = finiteAnimationSpec;
                    i3 |= composerImpl.changedInstance(finiteAnimationSpecTween$default) ? 256 : 128;
                }
                i5 = i2 & 4;
                if (i5 == 0) {
                    if ((i & 3072) == 0) {
                        function12 = function1;
                        i3 |= composerImpl.changedInstance(function12) ? 2048 : 1024;
                    }
                    if ((i2 & 8) == 0) {
                        i3 |= 24576;
                    } else if ((i & 24576) == 0) {
                        i3 |= composerImpl.changedInstance(function3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    if (!composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
                        Modifier modifier4 = i6 != 0 ? Modifier.Companion : modifier2;
                        if (i4 != 0) {
                            finiteAnimationSpecTween$default = AnimationSpecKt.tween$default(0, 0, null, 7);
                        }
                        if (i5 != 0) {
                            function12 = new Function1() { // from class: androidx.compose.animation.CrossfadeKt.Crossfade.3
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    return obj;
                                }
                            };
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.animation.Crossfade (Crossfade.kt:102)");
                        }
                        Object objRememberedValue = composerImpl.rememberedValue();
                        Composer.Companion.getClass();
                        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                        Object obj = objRememberedValue;
                        if (objRememberedValue == composer$Companion$Empty$1) {
                            SnapshotStateList snapshotStateList = new SnapshotStateList();
                            snapshotStateList.add(transition.transitionState.getCurrentState());
                            composerImpl.updateRememberedValue(snapshotStateList);
                            obj = snapshotStateList;
                        }
                        SnapshotStateList snapshotStateList2 = (SnapshotStateList) obj;
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (objRememberedValue2 == composer$Companion$Empty$1) {
                            objRememberedValue2 = ScatterMapKt.mutableScatterMapOf();
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        MutableScatterMap mutableScatterMap = (MutableScatterMap) objRememberedValue2;
                        Object currentState = transition.transitionState.getCurrentState();
                        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) transition.targetState$delegate;
                        if (Intrinsics.areEqual(currentState, snapshotMutableStateImpl.getValue())) {
                            composerImpl.startReplaceGroup(860925177);
                            if (snapshotStateList2.size() == 1 && Intrinsics.areEqual(snapshotStateList2.get(0), snapshotMutableStateImpl.getValue())) {
                                composerImpl.startReplaceGroup(861249809);
                                composerImpl.end(false);
                            } else {
                                composerImpl.startReplaceGroup(861059531);
                                boolean z3 = (i3 & 14) == 4;
                                Object objRememberedValue3 = composerImpl.rememberedValue();
                                if (z3 || objRememberedValue3 == composer$Companion$Empty$1) {
                                    objRememberedValue3 = new Function1() { // from class: androidx.compose.animation.CrossfadeKt$Crossfade$4$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj2) {
                                            return Boolean.valueOf(!Intrinsics.areEqual(obj2, ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue()));
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue3);
                                }
                                CollectionsKt__MutableCollectionsKt.removeAll(snapshotStateList2, (Function1) objRememberedValue3);
                                mutableScatterMap.clear();
                                composerImpl.end(false);
                            }
                            composerImpl.end(false);
                        } else {
                            composerImpl.startReplaceGroup(861255761);
                            composerImpl.end(false);
                        }
                        if (!mutableScatterMap.contains(snapshotMutableStateImpl.getValue())) {
                            composerImpl.startReplaceGroup(861316428);
                            ListIterator listIterator = snapshotStateList2.listIterator();
                            int i7 = 0;
                            while (true) {
                                if (!listIterator.hasNext()) {
                                    z = z2;
                                    i7 = -1;
                                    break;
                                } else {
                                    z = z2;
                                    if (Intrinsics.areEqual(function12.mo781invoke(listIterator.next()), function12.mo781invoke(snapshotMutableStateImpl.getValue()))) {
                                        break;
                                    }
                                    i7++;
                                    z2 = z;
                                }
                            }
                            if (i7 == -1) {
                                snapshotStateList2.add(snapshotMutableStateImpl.getValue());
                            } else {
                                snapshotStateList2.set(i7, snapshotMutableStateImpl.getValue());
                            }
                            mutableScatterMap.clear();
                            int size = snapshotStateList2.size();
                            for (int i8 = 0; i8 < size; i8++) {
                                final Object obj2 = snapshotStateList2.get(i8);
                                mutableScatterMap.set(obj2, ComposableLambdaKt.rememberComposableLambda(-1426421288, new Function2() { // from class: androidx.compose.animation.CrossfadeKt$Crossfade$5$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:34:0x00c0  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        Composer composer2 = (Composer) obj3;
                                        int iIntValue = ((Number) obj4).intValue();
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.animation.Crossfade.<anonymous>.<anonymous> (Crossfade.kt:125)");
                                            }
                                            Transition<Object> transition2 = transition;
                                            final FiniteAnimationSpec<Float> finiteAnimationSpec2 = finiteAnimationSpecTween$default;
                                            Function3 function32 = new Function3() { // from class: androidx.compose.animation.CrossfadeKt$Crossfade$5$1$alpha$2
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                                    ((Number) obj7).intValue();
                                                    ComposerImpl composerImpl3 = (ComposerImpl) ((Composer) obj6);
                                                    composerImpl3.startReplaceGroup(438406499);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.animation.Crossfade.<anonymous>.<anonymous>.<anonymous> (Crossfade.kt:126)");
                                                    }
                                                    FiniteAnimationSpec<Float> finiteAnimationSpec3 = finiteAnimationSpec2;
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    composerImpl3.end(false);
                                                    return finiteAnimationSpec3;
                                                }
                                            };
                                            Object obj5 = obj2;
                                            FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                                            TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
                                            Object currentState2 = transition2.transitionState.getCurrentState();
                                            composerImpl2.startReplaceGroup(-438678252);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.animation.Crossfade.<anonymous>.<anonymous>.<anonymous> (Crossfade.kt:127)");
                                            }
                                            float f = Intrinsics.areEqual(currentState2, obj5) ? 1.0f : 0.0f;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl2.end(false);
                                            Float fValueOf = Float.valueOf(f);
                                            Object value = ((SnapshotMutableStateImpl) transition2.targetState$delegate).getValue();
                                            composerImpl2.startReplaceGroup(-438678252);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.animation.Crossfade.<anonymous>.<anonymous>.<anonymous> (Crossfade.kt:127)");
                                            }
                                            float f2 = Intrinsics.areEqual(value, obj5) ? 1.0f : 0.0f;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl2.end(false);
                                            final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition2, fValueOf, Float.valueOf(f2), (FiniteAnimationSpec) function32.invoke(transition2.getSegment(), composerImpl2, 0), twoWayConverter, "FloatAnimation", composerImpl2, 0);
                                            Modifier.Companion companion = Modifier.Companion;
                                            boolean zChanged = composerImpl2.changed(transitionAnimationStateCreateTransitionAnimation);
                                            Object objRememberedValue4 = composerImpl2.rememberedValue();
                                            if (!zChanged) {
                                                Composer.Companion.getClass();
                                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                                    objRememberedValue4 = new Function1() { // from class: androidx.compose.animation.CrossfadeKt$Crossfade$5$1$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj6) {
                                                            ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj6)).setAlpha(((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue());
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl2.updateRememberedValue(objRememberedValue4);
                                                }
                                                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(companion, (Function1) objRememberedValue4);
                                                Function3 function33 = function3;
                                                Object obj6 = obj2;
                                                Alignment.Companion.getClass();
                                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierGraphicsLayer);
                                                ComposeUiNode.Companion.getClass();
                                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                if (composerImpl2.applier == null) {
                                                    ComposablesKt.invalidApplier();
                                                    throw null;
                                                }
                                                composerImpl2.startReusableNode();
                                                if (composerImpl2.inserting) {
                                                    composerImpl2.createNode(function0);
                                                } else {
                                                    composerImpl2.useNode();
                                                }
                                                Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                                                }
                                                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                function33.invoke(obj6, composerImpl2, 0);
                                                composerImpl2.end(true);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        } else {
                                            composerImpl2.skipToGroupEnd();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl));
                            }
                            composerImpl.end(false);
                        } else {
                            z = true;
                            composerImpl.startReplaceGroup(862059281);
                            composerImpl.end(false);
                        }
                        Alignment.Companion.getClass();
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier4);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl.applier != null) {
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
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            composerImpl.startReplaceGroup(-187474512);
                            int size2 = snapshotStateList2.size();
                            for (int i9 = 0; i9 < size2; i9++) {
                                Object obj3 = snapshotStateList2.get(i9);
                                composerImpl.startMovableGroup(-1081865889, function12.mo781invoke(obj3));
                                Function2 function22 = (Function2) mutableScatterMap.get(obj3);
                                if (function22 == null) {
                                    composerImpl.startReplaceGroup(821932266);
                                } else {
                                    composerImpl.startReplaceGroup(-1081864713);
                                    function22.invoke(composerImpl, 0);
                                }
                                composerImpl.end(false);
                                composerImpl.end(false);
                            }
                            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, z)) {
                                ComposerKt.traceEventEnd();
                            }
                            modifier3 = modifier4;
                        } else {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                    } else {
                        composerImpl.skipToGroupEnd();
                        modifier3 = modifier2;
                    }
                    final FiniteAnimationSpec finiteAnimationSpec2 = finiteAnimationSpecTween$default;
                    final Function1 function13 = function12;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.CrossfadeKt.Crossfade.7
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj4, Object obj5) {
                                ((Number) obj5).intValue();
                                CrossfadeKt.Crossfade(transition, modifier3, finiteAnimationSpec2, function13, function3, (Composer) obj4, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 3072;
                function12 = function1;
                if ((i2 & 8) == 0) {
                }
                if (!composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
                }
                final FiniteAnimationSpec<Float> finiteAnimationSpec22 = finiteAnimationSpecTween$default;
                final Function1 function132 = function12;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            finiteAnimationSpecTween$default = finiteAnimationSpec;
            i5 = i2 & 4;
            if (i5 == 0) {
            }
            function12 = function1;
            if ((i2 & 8) == 0) {
            }
            if (!composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
            }
            final FiniteAnimationSpec<Float> finiteAnimationSpec222 = finiteAnimationSpecTween$default;
            final Function1 function1322 = function12;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 2;
        if (i4 == 0) {
        }
        finiteAnimationSpecTween$default = finiteAnimationSpec;
        i5 = i2 & 4;
        if (i5 == 0) {
        }
        function12 = function1;
        if ((i2 & 8) == 0) {
        }
        if (!composerImpl.shouldExecute(i3 & 1, (i3 & 9363) == 9362)) {
        }
        final FiniteAnimationSpec<Float> finiteAnimationSpec2222 = finiteAnimationSpecTween$default;
        final Function1 function13222 = function12;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
