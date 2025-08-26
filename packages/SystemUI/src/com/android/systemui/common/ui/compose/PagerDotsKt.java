package com.android.systemui.common.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.pager.PagerState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;
import platform.test.motion.compose.values.MotionTestValueKt;
import platform.test.motion.compose.values.MotionTestValuesNode;

/* loaded from: classes.dex */
public abstract class PagerDotsKt {
    /* JADX WARN: Removed duplicated region for block: B:57:0x00cc  */
    /* renamed from: PagerDots-LLiWm1Q, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m1075PagerDotsLLiWm1Q(final PagerState pagerState, final long j, final long j2, final Modifier modifier, float f, float f2, Composer composer, final int i) {
        int i2;
        final float f3;
        ComposerImpl composerImpl;
        final float f4;
        final float f5;
        final float f6;
        final float f7;
        final State state;
        final int i3;
        ComposerImpl composerImpl2;
        int i4;
        final int i5 = 0;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(2069291305);
        if ((i & 6) == 0) {
            i2 = (composerImpl3.changed(pagerState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl3.changed(j) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl3.changed(j2) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl3.changed(modifier) ? 2048 : 1024;
        }
        int i6 = i2 | 221184;
        if ((74899 & i6) == 74898 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            f3 = f;
            f4 = f2;
            composerImpl = composerImpl3;
        } else {
            final float f8 = 6;
            Dp.Companion companion = Dp.Companion;
            final float f9 = 4;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.PagerDots (PagerDots.kt:59)");
            }
            if (pagerState.getPageCount() < 2) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl3.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i7 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            switch (i7) {
                                case 0:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    PagerState pagerState2 = pagerState;
                                    float f10 = f8;
                                    float f11 = f9;
                                    PagerDotsKt.m1075PagerDotsLLiWm1Q(pagerState2, j, j2, modifier, f10, f11, (Composer) obj, iUpdateChangedFlags);
                                    break;
                                default:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    PagerState pagerState3 = pagerState;
                                    float f12 = f8;
                                    float f13 = f9;
                                    PagerDotsKt.m1075PagerDotsLLiWm1Q(pagerState3, j, j2, modifier, f12, f13, (Composer) obj, iUpdateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            final PagerState pagerState2 = pagerState;
            float f10 = f8;
            float f11 = f9;
            composerImpl3.startReplaceGroup(-1391463067);
            int i8 = i6 & 14;
            boolean z = i8 == 4;
            Object objRememberedValue = composerImpl3.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!z) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i5) {
                                case 0:
                                    PagerState pagerState3 = pagerState2;
                                    return Boolean.valueOf(((double) Math.abs(pagerState3.getCurrentPageOffsetFraction())) > 0.05d && !PagerDotsKt.isOverscrolling(pagerState3));
                                default:
                                    PagerState pagerState4 = pagerState2;
                                    return Boolean.valueOf(pagerState4.getCurrentPageOffsetFraction() <= 0.0f || PagerDotsKt.isOverscrolling(pagerState4));
                            }
                        }
                    });
                    composerImpl3.updateRememberedValue(objRememberedValue);
                }
                State state2 = (State) objRememberedValue;
                composerImpl3.end(false);
                Object objRememberedValue2 = composerImpl3.rememberedValue();
                companion2.getClass();
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl3);
                    composerImpl3.updateRememberedValue(objRememberedValue2);
                }
                final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue2;
                float f12 = 2;
                final State stateM8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(((Boolean) state2.getValue()).booleanValue() ? (f10 * f12) + f11 : f10, null, "PagerDotsTransitionAnimation", composerImpl3, 384, 10);
                ComposerImpl composerImpl4 = composerImpl3;
                float f13 = f10 / f12;
                composerImpl4.startReplaceGroup(-1391425764);
                boolean zChanged = composerImpl4.changed(stateM8animateDpAsStateAjpBEmI);
                Object objRememberedValue3 = composerImpl4.rememberedValue();
                if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                    objRememberedValue3 = new Function1() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            Dp dpM837boximpl = Dp.m837boximpl(((Dp) stateM8animateDpAsStateAjpBEmI.getValue()).value);
                            PagerDotsMotionKeys.INSTANCE.getClass();
                            ((MotionTestValuesNode.AnonymousClass1) obj).exportAs(dpM837boximpl, PagerDotsMotionKeys.indicatorWidth);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl4.updateRememberedValue(objRememberedValue3);
                }
                composerImpl4.end(false);
                Modifier modifierThen = SizeKt.wrapContentWidth$default(MotionTestValueKt.motionTestValues(modifier, (Function1) objRememberedValue3), null, 3).then(SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        final int i9 = 1;
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                        final PagerState pagerState3 = pagerState2;
                        final CoroutineScope coroutineScope2 = coroutineScope;
                        final int i10 = 0;
                        Function0 function0 = new Function0() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda7
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                boolean z2;
                                boolean z3;
                                switch (i10) {
                                    case 0:
                                        PagerState pagerState4 = pagerState3;
                                        if (pagerState4.getCanScrollBackward()) {
                                            CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new PagerDotsKt$pagerDotsSemantics$1$1$1(pagerState4, null), 7);
                                            z2 = true;
                                        } else {
                                            z2 = false;
                                        }
                                        return Boolean.valueOf(z2);
                                    default:
                                        PagerState pagerState5 = pagerState3;
                                        if (pagerState5.getCanScrollForward()) {
                                            CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new PagerDotsKt$pagerDotsSemantics$1$2$1(pagerState5, null), 7);
                                            z3 = true;
                                        } else {
                                            z3 = false;
                                        }
                                        return Boolean.valueOf(z3);
                                }
                            }
                        };
                        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                        SemanticsActions semanticsActions = SemanticsActions.INSTANCE;
                        semanticsActions.getClass();
                        SemanticsPropertyKey semanticsPropertyKey = SemanticsActions.PageLeft;
                        AccessibilityAction accessibilityAction = new AccessibilityAction(null, function0);
                        SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) semanticsPropertyReceiver;
                        semanticsConfiguration.set(semanticsPropertyKey, accessibilityAction);
                        Function0 function02 = new Function0() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda7
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                boolean z2;
                                boolean z3;
                                switch (i9) {
                                    case 0:
                                        PagerState pagerState4 = pagerState3;
                                        if (pagerState4.getCanScrollBackward()) {
                                            CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new PagerDotsKt$pagerDotsSemantics$1$1$1(pagerState4, null), 7);
                                            z2 = true;
                                        } else {
                                            z2 = false;
                                        }
                                        return Boolean.valueOf(z2);
                                    default:
                                        PagerState pagerState5 = pagerState3;
                                        if (pagerState5.getCanScrollForward()) {
                                            CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new PagerDotsKt$pagerDotsSemantics$1$2$1(pagerState5, null), 7);
                                            z3 = true;
                                        } else {
                                            z3 = false;
                                        }
                                        return Boolean.valueOf(z3);
                                }
                            }
                        };
                        semanticsActions.getClass();
                        semanticsConfiguration.set(SemanticsActions.PageRight, new AccessibilityAction(null, function02));
                        SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, "Page " + (((Number) pagerState3.settledPage$delegate.getValue()).intValue() + 1) + " of " + pagerState3.getPageCount());
                        return Unit.INSTANCE;
                    }
                }));
                Arrangement.INSTANCE.getClass();
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f11);
                Alignment.Companion.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.CenterVertically, composerImpl4, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierThen);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl4.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl4.startReusableNode();
                if (composerImpl4.inserting) {
                    composerImpl4.createNode(function0);
                } else {
                    composerImpl4.useNode();
                }
                Updater.m337setimpl(composerImpl4, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                composerImpl4.startReplaceGroup(1719847311);
                boolean z2 = i8 == 4;
                Object objRememberedValue4 = composerImpl4.rememberedValue();
                if (z2 || objRememberedValue4 == composer$Companion$Empty$1) {
                    final int i9 = 1;
                    objRememberedValue4 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i9) {
                                case 0:
                                    PagerState pagerState3 = pagerState2;
                                    return Boolean.valueOf(((double) Math.abs(pagerState3.getCurrentPageOffsetFraction())) > 0.05d && !PagerDotsKt.isOverscrolling(pagerState3));
                                default:
                                    PagerState pagerState4 = pagerState2;
                                    return Boolean.valueOf(pagerState4.getCurrentPageOffsetFraction() <= 0.0f || PagerDotsKt.isOverscrolling(pagerState4));
                            }
                        }
                    });
                    composerImpl4.updateRememberedValue(objRememberedValue4);
                }
                final State state3 = (State) objRememberedValue4;
                composerImpl4.end(false);
                composerImpl4.startReplaceGroup(1719854597);
                int pageCount = pagerState2.getPageCount();
                int i10 = 0;
                while (i10 < pageCount) {
                    Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, f10);
                    composerImpl4.startReplaceGroup(1526442728);
                    boolean zChanged2 = ((i6 & 896) == 256) | composerImpl4.changed(state3) | composerImpl4.changed(i10) | (i8 == 4) | ((i6 & 57344) == 16384) | ((458752 & i6) == 131072) | composerImpl4.changed(f13) | ((i6 & 112) == 32) | composerImpl4.changed(stateM8animateDpAsStateAjpBEmI);
                    Object objRememberedValue5 = composerImpl4.rememberedValue();
                    if (zChanged2 || objRememberedValue5 == Composer.Companion.Empty) {
                        f5 = f11;
                        f6 = f13;
                        f7 = f10;
                        int i11 = i10;
                        state = stateM8animateDpAsStateAjpBEmI;
                        i3 = i11;
                        composerImpl2 = composerImpl4;
                        i4 = i8;
                        final PagerState pagerState3 = pagerState2;
                        Function1 function1 = new Function1() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda4
                            /* JADX WARN: Removed duplicated region for block: B:21:0x00aa A[Catch: all -> 0x0092, TRY_LEAVE, TryCatch #0 {all -> 0x0092, blocks: (B:9:0x0078, B:12:0x008b, B:21:0x00aa, B:17:0x0096, B:19:0x00a2), top: B:28:0x0078 }] */
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object mo781invoke(Object obj) throws Throwable {
                                long j3;
                                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1;
                                long j4 = j2;
                                State state4 = state3;
                                State state5 = state;
                                float f14 = f7;
                                float f15 = f5;
                                float f16 = f6;
                                long j5 = j;
                                DrawScope drawScope = (DrawScope) obj;
                                float f17 = drawScope.getLayoutDirection() == LayoutDirection.Rtl ? -1.0f : 1.0f;
                                long jFloatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope.mo546getCenterF1C5BW0() & 4294967295L))) & 4294967295L);
                                Offset.Companion companion3 = Offset.Companion;
                                CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
                                long jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
                                drawContext.getCanvas().save();
                                try {
                                    drawContext.transform.m532scale0AR0LA0(f17, 1.0f, jFloatToRawIntBits);
                                    canvasDrawScope$drawContext$1 = drawContext;
                                    try {
                                        DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j4, 0.0f, 0L, 0.0f, null, 0, 126);
                                        boolean zBooleanValue = ((Boolean) state4.getValue()).booleanValue();
                                        PagerState pagerState4 = pagerState3;
                                        int i12 = i3;
                                        if (!zBooleanValue || i12 != pagerState4.getCurrentPage()) {
                                            if (!((Boolean) state4.getValue()).booleanValue() && i12 == pagerState4.getCurrentPage() + 1) {
                                                PagerDotsKt.PagerDots_LLiWm1Q$drawDoubleRect(drawScope, f14, f15, f16, j5, ((Boolean) state4.getValue()).booleanValue(), ((Dp) state5.getValue()).value);
                                            }
                                        }
                                        BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                                        return Unit.INSTANCE;
                                    } catch (Throwable th) {
                                        th = th;
                                        j3 = jM528getSizeNHjbRc;
                                        BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, j3);
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    j3 = jM528getSizeNHjbRc;
                                    canvasDrawScope$drawContext$1 = drawContext;
                                }
                            }
                        };
                        composerImpl2.updateRememberedValue(function1);
                        objRememberedValue5 = function1;
                    } else {
                        int i12 = i10;
                        state = stateM8animateDpAsStateAjpBEmI;
                        i3 = i12;
                        composerImpl2 = composerImpl4;
                        f5 = f11;
                        i4 = i8;
                        f6 = f13;
                        f7 = f10;
                    }
                    composerImpl2.end(false);
                    CanvasKt.Canvas(modifierM140size3ABfNKs, (Function1) objRememberedValue5, composerImpl2, 0);
                    int i13 = i3 + 1;
                    pagerState2 = pagerState;
                    stateM8animateDpAsStateAjpBEmI = state;
                    f10 = f7;
                    f13 = f6;
                    composerImpl4 = composerImpl2;
                    i8 = i4;
                    i10 = i13;
                    f11 = f5;
                }
                f3 = f10;
                composerImpl = composerImpl4;
                f4 = f11;
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i14 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.systemui.common.ui.compose.PagerDotsKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    switch (i14) {
                        case 0:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            PagerState pagerState22 = pagerState;
                            float f102 = f3;
                            float f112 = f4;
                            PagerDotsKt.m1075PagerDotsLLiWm1Q(pagerState22, j, j2, modifier, f102, f112, (Composer) obj, iUpdateChangedFlags);
                            break;
                        default:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            PagerState pagerState32 = pagerState;
                            float f122 = f3;
                            float f132 = f4;
                            PagerDotsKt.m1075PagerDotsLLiWm1Q(pagerState32, j, j2, modifier, f122, f132, (Composer) obj, iUpdateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void PagerDots_LLiWm1Q$drawDoubleRect(DrawScope drawScope, float f, float f2, float f3, long j, boolean z, float f4) {
        float fMo58toPx0680j_4;
        if (z) {
            fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(f) - drawScope.mo58toPx0680j_4(f4);
        } else {
            fMo58toPx0680j_4 = -(drawScope.mo58toPx0680j_4(f2) + drawScope.mo58toPx0680j_4(f));
        }
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        float fMo58toPx0680j_42 = drawScope.mo58toPx0680j_4(f4);
        float fMo58toPx0680j_43 = drawScope.mo58toPx0680j_4(f);
        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo58toPx0680j_43) & 4294967295L) | (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32);
        Size.Companion companion2 = Size.Companion;
        long jFloatToRawIntBits3 = (4294967295L & Float.floatToRawIntBits(r5)) | (Float.floatToRawIntBits(drawScope.mo58toPx0680j_4(f3)) << 32);
        CornerRadius.Companion companion3 = CornerRadius.Companion;
        DrawScope.m543drawRoundRectuAw5IA$default(drawScope, j, jFloatToRawIntBits, jFloatToRawIntBits2, jFloatToRawIntBits3, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
    }

    public static final boolean isOverscrolling(PagerState pagerState) {
        float currentPageOffsetFraction = pagerState.getCurrentPageOffsetFraction() + pagerState.getCurrentPage();
        return currentPageOffsetFraction < 0.0f || currentPageOffsetFraction > ((float) (pagerState.getPageCount() - 1));
    }
}
