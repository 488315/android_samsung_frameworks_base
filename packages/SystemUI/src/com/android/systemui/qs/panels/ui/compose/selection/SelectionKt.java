package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.SystemGestureExclusionKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SelectionKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TileState.values().length];
            try {
                iArr[TileState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TileState.GreyedOut.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TileState.Removable.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TileState.Selected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TileState.Placeable.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x02de, code lost:
    
        if (r5.changed(r4) == false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0134, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L57;
     */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0343  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03f4  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x047d  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x04b0  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x04c6  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0524  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0541  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x05ae  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x05da  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x05fa  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0601  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0611  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0630  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0635  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0645  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0689  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x068e  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x06b8  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x06c8  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0708  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x070f  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x071a  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0739  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x073e  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0749  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0794  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x079b  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0818 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0857  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0924  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x07af  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x077c  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0741  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0712  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x06c0  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0583  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x04fc  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x042b  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x03a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void InteractiveTileContainer(final com.android.systemui.qs.panels.ui.compose.selection.TileState r36, final com.android.systemui.qs.panels.ui.compose.selection.ResizingState r37, final androidx.compose.ui.Modifier r38, final kotlin.jvm.functions.Function0 r39, final java.lang.String r40, kotlin.jvm.functions.Function3 r41, androidx.compose.runtime.Composer r42, final int r43) {
        /*
            Method dump skipped, instructions count: 2344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt.InteractiveTileContainer(com.android.systemui.qs.panels.ui.compose.selection.TileState, com.android.systemui.qs.panels.ui.compose.selection.ResizingState, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, java.lang.String, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }

    public static final void MinimumInteractiveSizeComponent(final Function0 function0, final Function0 function02, Modifier modifier, Function3 function3, Composer composer, final int i) {
        int i2;
        Function3 function32;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1659916325);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        } else {
            i2 = i;
        }
        int i3 = i2 | (composerImpl.changedInstance(function02) ? 32 : 16) | 384;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
            function32 = function3;
        } else {
            Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.MinimumInteractiveSizeComponent (Selection.kt:249)");
            }
            final float f = ((Dp) composerImpl.consume(InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value;
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.Center;
            Modifier zIndex = ZIndexModifierKt.zIndex(companion, 2.0f);
            composerImpl.startReplaceGroup(-774422871);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new SelectionKt$$ExternalSyntheticLambda7();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            Modifier systemGestureExclusion = SystemGestureExclusionKt.systemGestureExclusion(zIndex, (Function1) rememberedValue);
            composerImpl.startReplaceGroup(-774420111);
            boolean changed = ((i3 & 112) == 32) | ((i3 & 14) == 4) | composerImpl.changed(f);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        MeasureResult layout$1;
                        MeasureScope measureScope = (MeasureScope) obj;
                        final Constraints constraints = (Constraints) obj3;
                        int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(f);
                        Constraints.Companion.getClass();
                        final Placeable mo608measureBRTryo0 = ((Measurable) obj2).mo608measureBRTryo0(Constraints.Companion.m827fixedJhjzzOo(mo51roundToPx0680j_4, mo51roundToPx0680j_4));
                        int i4 = mo608measureBRTryo0.width;
                        int i5 = mo608measureBRTryo0.height;
                        final Function0 function03 = function0;
                        final Function0 function04 = function02;
                        layout$1 = measureScope.layout$1(i4, i5, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda12
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj4) {
                                Constraints constraints2 = Constraints.this;
                                float m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(constraints2.value) / 2.0f;
                                long floatToRawIntBits = (Float.floatToRawIntBits(Constraints.m821getMaxWidthimpl(constraints2.value) - m820getMaxHeightimpl) << 32) | (Float.floatToRawIntBits(m820getMaxHeightimpl) & 4294967295L);
                                Offset.Companion companion2 = Offset.Companion;
                                double floatValue = ((Number) function03.invoke()).floatValue();
                                float intBitsToFloat = Float.intBitsToFloat((int) (floatToRawIntBits >> 32)) + (((float) Math.cos(floatValue)) * m820getMaxHeightimpl);
                                float intBitsToFloat2 = Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)) + (m820getMaxHeightimpl * ((float) Math.sin(floatValue)));
                                long m401plusMKHz9U = Offset.m401plusMKHz9U((Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32), ((Offset) function04.invoke()).packedValue);
                                int roundToInt = MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (m401plusMKHz9U >> 32)));
                                Placeable placeable = mo608measureBRTryo0;
                                ((Placeable.PlacementScope) obj4).place(placeable, roundToInt - (placeable.width / 2), MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (m401plusMKHz9U & 4294967295L))) - (placeable.height / 2), 0.0f);
                                return Unit.INSTANCE;
                            }
                        });
                        return layout$1;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            Modifier layout = LayoutModifierKt.layout(systemGestureExclusion, (Function3) rememberedValue2);
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, layout);
            ComposeUiNode.Companion.getClass();
            Function0 function03 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function03);
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
            function32 = function3;
            function32.invoke(BoxScopeInstance.INSTANCE, composerImpl, 54);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = companion;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Function3 function33 = function32;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function3 function34 = function33;
                    SelectionKt.MinimumInteractiveSizeComponent(Function0.this, function02, modifier2, function34, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void StaticTileBadge(final ImageVector imageVector, final String str, final boolean z, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(282664700);
        if (((i | (composerImpl.changed(imageVector) ? 4 : 2) | (composerImpl.changed(str) ? 32 : 16) | (composerImpl.changed(z) ? 256 : 128) | (composerImpl.changedInstance(function0) ? 2048 : 1024)) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.StaticTileBadge (Selection.kt:212)");
            }
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            SelectionDefaults.INSTANCE.getClass();
            final long floatToRawIntBits = (Float.floatToRawIntBits(density.mo57toPx0680j_4(SelectionDefaults.BadgeXOffset)) << 32) | (Float.floatToRawIntBits(density.mo57toPx0680j_4(SelectionDefaults.BadgeYOffset)) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            final State animateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, null, null, null, composerImpl, 0, 30);
            composerImpl.startReplaceGroup(-566674700);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new SelectionKt$$ExternalSyntheticLambda0();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            Function0 function02 = (Function0) rememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-566673749);
            boolean changed = composerImpl.changed(floatToRawIntBits);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Offset.m393boximpl(floatToRawIntBits);
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            MinimumInteractiveSizeComponent(function02, (Function0) rememberedValue2, null, ComposableLambdaKt.rememberComposableLambda(-1542411233, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$StaticTileBadge$3
                /* JADX WARN: Code restructure failed: missing block: B:15:0x004d, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:31:0x0113, code lost:
                
                    if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L34;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r10, java.lang.Object r11, java.lang.Object r12) {
                    /*
                        Method dump skipped, instructions count: 325
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$StaticTileBadge$3.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 3078);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(str, z, function0, i) { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda2
                public final /* synthetic */ String f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Function0 f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ImageVector imageVector2 = ImageVector.this;
                    boolean z2 = this.f$2;
                    Function0 function03 = this.f$3;
                    SelectionKt.StaticTileBadge(imageVector2, this.f$1, z2, function03, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
