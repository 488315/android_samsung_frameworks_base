package com.android.compose.animation;

import android.view.View;
import android.view.ViewGroup;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathOperation;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ExpandableKt {
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01a1, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L70;
     */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0189  */
    /* renamed from: AnimatedContentInOverlay-CISuavA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m909AnimatedContentInOverlayCISuavA(final kotlin.jvm.functions.Function0 r22, final long r23, final android.view.ViewGroupOverlay r25, final com.android.compose.animation.ExpandableControllerImpl r26, final kotlin.jvm.functions.Function3 r27, final android.view.View r28, final kotlin.jvm.functions.Function1 r29, final androidx.compose.ui.unit.Density r30, androidx.compose.runtime.Composer r31, final int r32) {
        /*
            Method dump skipped, instructions count: 467
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.ExpandableKt.m909AnimatedContentInOverlayCISuavA(kotlin.jvm.functions.Function0, long, android.view.ViewGroupOverlay, com.android.compose.animation.ExpandableControllerImpl, kotlin.jvm.functions.Function3, android.view.View, kotlin.jvm.functions.Function1, androidx.compose.ui.unit.Density, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x0100, code lost:
    
        if (r14 == androidx.compose.runtime.Composer.Companion.Empty) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01fb, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x022a, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L126;
     */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0372  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0586  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03f7  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Expandable(com.android.compose.animation.ExpandableControllerImpl r21, final androidx.compose.ui.Modifier r22, kotlin.jvm.functions.Function1 r23, androidx.compose.foundation.interaction.MutableInteractionSource r24, final boolean r25, boolean r26, final androidx.compose.runtime.internal.ComposableLambdaImpl r27, androidx.compose.runtime.Composer r28, final int r29, final int r30) {
        /*
            Method dump skipped, instructions count: 1449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.ExpandableKt.Expandable(com.android.compose.animation.ExpandableControllerImpl, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function1, androidx.compose.foundation.interaction.MutableInteractionSource, boolean, boolean, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:88:0x01e4, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L151;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0246  */
    /* renamed from: Expandable-S04cQl8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m910ExpandableS04cQl8(final long r27, final androidx.compose.foundation.shape.RoundedCornerShape r29, final androidx.compose.ui.Modifier r30, long r31, androidx.compose.foundation.BorderStroke r33, final kotlin.jvm.functions.Function1 r34, androidx.compose.foundation.interaction.MutableInteractionSource r35, boolean r36, boolean r37, com.android.systemui.animation.ComposableControllerFactory r38, final androidx.compose.runtime.internal.ComposableLambdaImpl r39, androidx.compose.runtime.Composer r40, final int r41, final int r42) {
        /*
            Method dump skipped, instructions count: 622
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.ExpandableKt.m910ExpandableS04cQl8(long, androidx.compose.foundation.shape.RoundedCornerShape, androidx.compose.ui.Modifier, long, androidx.compose.foundation.BorderStroke, kotlin.jvm.functions.Function1, androidx.compose.foundation.interaction.MutableInteractionSource, boolean, boolean, com.android.systemui.animation.ComposableControllerFactory, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: WrappedContent-3IgeMak, reason: not valid java name */
    public static final void m911WrappedContent3IgeMak(final Expandable expandable, final long j, final boolean z, final Function3 function3, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-91621291);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(expandable) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(j) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.WrappedContent (Expandable.kt:322)");
            }
            ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-242023019, new Function2() { // from class: com.android.compose.animation.ExpandableKt$WrappedContent$minSizeContent$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.animation.WrappedContent.<anonymous> (Expandable.kt:325)");
                    }
                    boolean z2 = z;
                    Expandable expandable2 = expandable;
                    Function3 function32 = function3;
                    if (z2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        composerImpl3.startReplaceGroup(-605032501);
                        float f = 40;
                        Dp.Companion companion = Dp.Companion;
                        Modifier m129defaultMinSizeVpY3zN4 = SizeKt.m129defaultMinSizeVpY3zN4(Modifier.Companion, f, f);
                        Alignment.Companion.getClass();
                        MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, m129defaultMinSizeVpY3zN4);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl3.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function0);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m336setimpl(composerImpl3, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m336setimpl(composerImpl3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                        }
                        Updater.m336setimpl(composerImpl3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        function32.invoke(expandable2, composerImpl3, 0);
                        composerImpl3.end(true);
                        composerImpl3.end(false);
                    } else {
                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                        composerImpl4.startReplaceGroup(-604423382);
                        function32.invoke(expandable2, composerImpl4, 0);
                        composerImpl4.end(false);
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            if (j != 16) {
                composerImpl.startReplaceGroup(-1328291087);
                CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(j)), rememberComposableLambda, composerImpl, 56);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-1328181316);
                rememberComposableLambda.invoke((Object) composerImpl, (Object) 6);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    ExpandableKt.m911WrappedContent3IgeMak(Expandable.this, j, z, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Modifier clickModifier(final ExpandableControllerImpl expandableControllerImpl, final Function1 function1, MutableInteractionSource mutableInteractionSource) {
        if (function1 == null) {
            return Modifier.Companion;
        }
        if (mutableInteractionSource != null) {
            final int i = 0;
            return ClickableKt.m34clickableO2vRcR0$default(Modifier.Companion, mutableInteractionSource, null, false, null, null, new Function0() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i) {
                        case 0:
                            function1.mo779invoke(expandableControllerImpl.expandable);
                            break;
                        default:
                            function1.mo779invoke(expandableControllerImpl.expandable);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            }, 28);
        }
        final int i2 = 1;
        return ClickableKt.m35clickableXHw0xAI$default(ClipKt.clip(Modifier.Companion, expandableControllerImpl.shape), false, null, new Function0() { // from class: com.android.compose.animation.ExpandableKt$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        function1.mo779invoke(expandableControllerImpl.expandable);
                        break;
                    default:
                        function1.mo779invoke(expandableControllerImpl.expandable);
                        break;
                }
                return Unit.INSTANCE;
            }
        }, 7);
    }

    /* renamed from: drawBackground-HilfTbk, reason: not valid java name */
    public static final void m912drawBackgroundHilfTbk(ContentDrawScope contentDrawScope, TransitionAnimator.State state, long j, BorderStroke borderStroke, long j2) {
        float f = state.topCornerRadius;
        float f2 = state.bottomCornerRadius;
        if (f != f2) {
            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) contentDrawScope;
            Outline mo40createOutlinePq9zytI = RoundedCornerShapeKt.RoundedCornerShape(f, f, f2, f2).mo40createOutlinePq9zytI(j2, layoutNodeDrawScope.getLayoutDirection(), layoutNodeDrawScope);
            OutlineKt.m490drawOutlinewDX37Ww$default(layoutNodeDrawScope, mo40createOutlinePq9zytI, j, 0.0f, null, 60);
            if (borderStroke != null) {
                float mo57toPx0680j_4 = layoutNodeDrawScope.mo57toPx0680j_4(borderStroke.width);
                AndroidPath Path = AndroidPath_androidKt.Path();
                RoundRect roundRect = ((Outline.Rounded) mo40createOutlinePq9zytI).roundRect;
                Path.addRoundRect$default(Path, roundRect);
                Path Path2 = AndroidPath_androidKt.Path();
                Path.addRoundRect$default(Path2, new RoundRect(mo57toPx0680j_4, mo57toPx0680j_4, roundRect.getWidth() - mo57toPx0680j_4, roundRect.getHeight() - mo57toPx0680j_4, m913shrinkKibmq7A(mo57toPx0680j_4, roundRect.topLeftCornerRadius), m913shrinkKibmq7A(mo57toPx0680j_4, roundRect.topRightCornerRadius), m913shrinkKibmq7A(mo57toPx0680j_4, roundRect.bottomRightCornerRadius), m913shrinkKibmq7A(mo57toPx0680j_4, roundRect.bottomLeftCornerRadius), null));
                PathOperation.Companion.getClass();
                Path.m443opN5in7k0(Path, Path2, 0);
                DrawScope.m536drawPathGBMwjPU$default(layoutNodeDrawScope, Path, borderStroke.brush, 0.0f, null, 60);
                return;
            }
            return;
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        DrawScope.m541drawRoundRectuAw5IA$default(contentDrawScope, j, 0L, j2, floatToRawIntBits, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState);
        if (borderStroke != null) {
            LayoutNodeDrawScope layoutNodeDrawScope2 = (LayoutNodeDrawScope) contentDrawScope;
            float mo57toPx0680j_42 = layoutNodeDrawScope2.mo57toPx0680j_4(borderStroke.width);
            Stroke stroke = new Stroke(mo57toPx0680j_42, 0.0f, 0, 0, null, 30, null);
            long floatToRawIntBits2 = (Float.floatToRawIntBits(r3) << 32) | (Float.floatToRawIntBits(r3) & 4294967295L);
            Offset.Companion companion2 = Offset.Companion;
            float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) - mo57toPx0680j_42;
            float intBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) - mo57toPx0680j_42;
            long floatToRawIntBits3 = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
            Size.Companion companion3 = Size.Companion;
            DrawScope.m540drawRoundRectZuiqVtQ$default(layoutNodeDrawScope2, borderStroke.brush, floatToRawIntBits2, floatToRawIntBits3, m913shrinkKibmq7A(mo57toPx0680j_42 / 2, floatToRawIntBits), 0.0f, stroke, 208);
        }
    }

    public static final void measureAndLayoutComposeViewInOverlay(View view, TransitionAnimator.State state) {
        view.measure(View.MeasureSpec.makeSafeMeasureSpec(state.getWidth(), 1073741824), View.MeasureSpec.makeSafeMeasureSpec(state.getHeight(), 1073741824));
        int[] locationOnScreen = ((ViewGroup) view.getParent()).getLocationOnScreen();
        int i = locationOnScreen[0];
        int i2 = locationOnScreen[1];
        view.layout(state.left - i, state.top - i2, state.right - i, state.bottom - i2);
    }

    /* renamed from: shrink-Kibmq7A, reason: not valid java name */
    public static final long m913shrinkKibmq7A(float f, long j) {
        float max = Math.max(0.0f, Float.intBitsToFloat((int) (j >> 32)) - f);
        float max2 = Math.max(0.0f, Float.intBitsToFloat((int) (j & 4294967295L)) - f);
        long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max2) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        return floatToRawIntBits;
    }
}
