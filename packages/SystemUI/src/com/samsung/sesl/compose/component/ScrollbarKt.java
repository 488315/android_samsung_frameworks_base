package com.samsung.sesl.compose.component;

import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.interaction.DragInteractionKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteractionKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslListColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.scroll.SeslScrollableState;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ScrollbarKt {
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008d, code lost:
    
        if (r0 == r2) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SeslScrollbar(final androidx.compose.foundation.lazy.LazyListState r7, androidx.compose.ui.Modifier r8, final androidx.compose.foundation.interaction.MutableInteractionSource r9, androidx.compose.runtime.Composer r10, final int r11) {
        /*
            r4 = r10
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            r10 = -131542391(0xfffffffff828d289, float:-1.36965E34)
            r4.startRestartGroup(r10)
            boolean r10 = r4.changed(r7)
            r0 = 4
            if (r10 == 0) goto L12
            r10 = r0
            goto L13
        L12:
            r10 = 2
        L13:
            r10 = r10 | r11
            r10 = r10 | 384(0x180, float:5.38E-43)
            r1 = r10 & 147(0x93, float:2.06E-43)
            r2 = 146(0x92, float:2.05E-43)
            if (r1 != r2) goto L29
            boolean r1 = r4.getSkipping()
            if (r1 != 0) goto L23
            goto L29
        L23:
            r4.skipToGroupEnd()
            r1 = r8
            goto Lc0
        L29:
            r9 = -587997261(0xffffffffdcf3dfb3, float:-5.4915503E17)
            r4.startReplaceGroup(r9)
            java.lang.Object r9 = r4.rememberedValue()
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r9 != r2) goto L43
            androidx.compose.foundation.interaction.MutableInteractionSource r9 = androidx.compose.foundation.interaction.InteractionSourceKt.MutableInteractionSource()
            r4.updateRememberedValue(r9)
        L43:
            androidx.compose.foundation.interaction.MutableInteractionSource r9 = (androidx.compose.foundation.interaction.MutableInteractionSource) r9
            r3 = 0
            r4.end(r3)
            boolean r5 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r5 == 0) goto L54
            java.lang.String r5 = "com.samsung.sesl.compose.component.SeslScrollbar (Scrollbar.kt:126)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r5)
        L54:
            com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index r5 = new java.lang.Object() { // from class: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index
                static {
                    /*
                        com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index r0 = new com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index) com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index.INSTANCE com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index.<clinit>():void");
                }

                {
                    /*
                        r0 = this;
                        r0.<init>()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index.<init>():void");
                }

                public final boolean equals(java.lang.Object r2) {
                    /*
                        r1 = this;
                        r0 = 1
                        if (r1 != r2) goto L4
                        return r0
                    L4:
                        boolean r1 = r2 instanceof com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index
                        if (r1 != 0) goto La
                        r1 = 0
                        return r1
                    La:
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index.equals(java.lang.Object):boolean");
                }

                public final int hashCode() {
                    /*
                        r0 = this;
                        r0 = -617289827(0xffffffffdb34e79d, float:-5.0920157E16)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index.hashCode():int");
                }

                public final java.lang.String toString() {
                    /*
                        r0 = this;
                        java.lang.String r0 = "Index"
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.scroll.SeslScrollingStrategy$Index.toString():java.lang.String");
                }
            }
            r10 = r10 & 14
            r10 = r10 | 48
            r6 = -342579925(0xffffffffeb94a52b, float:-3.59402E26)
            r4.startReplaceGroup(r6)
            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r6 == 0) goto L6b
            java.lang.String r6 = "com.samsung.sesl.compose.foundation.scroll.rememberSeslScrollbarState (SeslScrollState.kt:64)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r6)
        L6b:
            r6 = 1443869793(0x560fb461, float:3.950122E13)
            r4.startReplaceGroup(r6)
            r6 = r10 & 14
            r6 = r6 ^ 6
            if (r6 <= r0) goto L7d
            boolean r6 = r4.changed(r7)
            if (r6 != 0) goto L81
        L7d:
            r10 = r10 & 6
            if (r10 != r0) goto L83
        L81:
            r10 = 1
            goto L84
        L83:
            r10 = r3
        L84:
            java.lang.Object r0 = r4.rememberedValue()
            if (r10 != 0) goto L8f
            r1.getClass()
            if (r0 != r2) goto L9d
        L8f:
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r5)
            if (r10 == 0) goto Lce
            com.samsung.sesl.compose.foundation.scroll.SeslLazyListState r0 = new com.samsung.sesl.compose.foundation.scroll.SeslLazyListState
            r0.<init>(r7)
            r4.updateRememberedValue(r0)
        L9d:
            com.samsung.sesl.compose.foundation.scroll.SeslScrollableState r0 = (com.samsung.sesl.compose.foundation.scroll.SeslScrollableState) r0
            r4.end(r3)
            boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r10 == 0) goto Lab
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lab:
            r4.end(r3)
            r5 = 440(0x1b8, float:6.17E-43)
            r3 = 0
            r1 = r8
            r2 = r9
            SeslScrollbar(r0, r1, r2, r3, r4, r5)
            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r8 == 0) goto Lbf
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lbf:
            r9 = r2
        Lc0:
            androidx.compose.runtime.RecomposeScopeImpl r8 = r4.endRestartGroup()
            if (r8 == 0) goto Lcd
            com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda0 r10 = new com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda0
            r10.<init>(r1, r9, r11)
            r8.block = r10
        Lcd:
            return
        Lce:
            kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.component.ScrollbarKt.SeslScrollbar(androidx.compose.foundation.lazy.LazyListState, androidx.compose.ui.Modifier, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.runtime.Composer, int):void");
    }

    public static final void SeslScrollbar(SeslScrollableState seslScrollableState, Modifier modifier, final MutableInteractionSource mutableInteractionSource, boolean z, Composer composer, final int i) {
        int i2;
        final SeslScrollableState seslScrollableState2;
        final Modifier modifier2;
        final MutableInteractionSource mutableInteractionSource2;
        final boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1594911195);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(seslScrollableState) : composerImpl.changedInstance(seslScrollableState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 256 : 128;
        }
        int i3 = i2 | 3072;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z2 = z;
            mutableInteractionSource2 = mutableInteractionSource;
            modifier2 = modifier;
            seslScrollableState2 = seslScrollableState;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbar (Scrollbar.kt:222)");
            }
            int i4 = (i3 & 14) | 56;
            int i5 = i3 << 3;
            SeslScrollbar(seslScrollableState, ComposableLambdaKt.rememberComposableLambda(1433112073, new Function3() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$SeslScrollbar$17
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    SeslScrollbarThumbScope seslScrollbarThumbScope = (SeslScrollbarThumbScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= (intValue & 8) == 0 ? ((ComposerImpl) composer2).changed(seslScrollbarThumbScope) : ((ComposerImpl) composer2).changedInstance(seslScrollbarThumbScope) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbar.<anonymous> (Scrollbar.kt:229)");
                    }
                    MutableInteractionSource mutableInteractionSource3 = MutableInteractionSource.this;
                    boolean z3 = ((Boolean) DragInteractionKt.collectIsDraggedAsState(mutableInteractionSource3, composer2).getValue()).booleanValue() || ((Boolean) PressInteractionKt.collectIsPressedAsState(mutableInteractionSource3, composer2, 0).getValue()).booleanValue();
                    Modifier.Companion companion = Modifier.Companion;
                    ((SeslScrollbarThumbScopeImpl) seslScrollbarThumbScope).getClass();
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(-1453257121);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbarThumbScopeImpl.FastScrollbar (Scrollbar.kt:461)");
                    }
                    float f = z3 ? 5 : 3;
                    Dp.Companion companion2 = Dp.Companion;
                    State m8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(f, null, "", composerImpl3, 384, 10);
                    SeslScrollbarDefaults.INSTANCE.getClass();
                    composerImpl3.startReplaceGroup(-1294381766);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScrollbarDefaults.scrollbarThumbColor (Scrollbar.kt:494)");
                    }
                    long color = BasicColorSchemeKt.toColor(SeslListColorSchemeKeyTokens.ScrollbarThumbActivate, composerImpl3);
                    long color2 = BasicColorSchemeKt.toColor(SeslListColorSchemeKeyTokens.ScrollbarThumbInActivate, composerImpl3);
                    if (!z3) {
                        color = color2;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl3.end(false);
                    BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(SizeKt.m138requiredWidth3ABfNKs(companion, ((Dp) m8animateDpAsStateAjpBEmI.getValue()).value).then(SizeKt.FillWholeMaxHeight), ((Color) SingleValueAnimationKt.m7animateColorAsStateeuL9pac(color, null, "", composerImpl3, 384, 10).getValue()).value, RoundedCornerShapeKt.CircleShape), composerImpl3, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl3.end(false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), modifier, mutableInteractionSource, composerImpl, i4 | (i5 & 896) | (i5 & 7168) | (i5 & 57344));
            seslScrollableState2 = seslScrollableState;
            modifier2 = modifier;
            mutableInteractionSource2 = mutableInteractionSource;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            z2 = true;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.ScrollbarKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource2;
                    boolean z3 = z2;
                    ScrollbarKt.SeslScrollbar(SeslScrollableState.this, modifier2, mutableInteractionSource3, z3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:84:0x01c0, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r15.rememberedValue(), java.lang.Integer.valueOf(r7)) == false) goto L91;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.runtime.internal.ComposableLambdaImpl] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SeslScrollbar(final com.samsung.sesl.compose.foundation.scroll.SeslScrollableState r23, androidx.compose.runtime.internal.ComposableLambdaImpl r24, final androidx.compose.ui.Modifier r25, final androidx.compose.foundation.interaction.MutableInteractionSource r26, androidx.compose.runtime.Composer r27, final int r28) {
        /*
            Method dump skipped, instructions count: 802
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.component.ScrollbarKt.SeslScrollbar(com.samsung.sesl.compose.foundation.scroll.SeslScrollableState, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.ui.Modifier, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.runtime.Composer, int):void");
    }
}
