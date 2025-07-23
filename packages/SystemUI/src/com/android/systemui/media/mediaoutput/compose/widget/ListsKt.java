package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ListsKt {
    public static final void EmptyListItem(String str, Composer composer, int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1067275542);
        int i2 = i | (composerImpl2.changed(str) ? 4 : 2);
        if ((i2 & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.EmptyListItem (Lists.kt:190)");
            }
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            composerImpl2.startReplaceGroup(337750070);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new ListsKt$$ExternalSyntheticLambda0(0);
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            composerImpl2.end(false);
            Dp.Companion companion = Dp.Companion;
            Modifier m132heightInVpY3zN4$default = SizeKt.m132heightInVpY3zN4$default(ClickableKt.m34clickableO2vRcR0$default(fillMaxWidth, null, null, false, null, null, (Function0) rememberedValue, 28), 56, 0.0f, 2);
            float f = 18;
            float f2 = 14;
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(m132heightInVpY3zN4$default, f, f2, f, f2);
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterVertically, composerImpl2, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, m127paddingqDBjuR0);
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
            Updater.m336setimpl(composerImpl2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            composerImpl2.startReplaceGroup(869356492);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.EmptyListTextStyle (Type.kt:112)");
            }
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secRegular = TypeKt.getSecRegular();
            long Color = ColorKt.Color(4291611854L);
            TextUnitType.Companion.getClass();
            TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secRegular, Color, TextUnitKt.pack(17.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            TextKt.m316Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m754copyp1EtxEg$default, composerImpl2, i2 & 14, 0, 65534);
            composerImpl = composerImpl2;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ListsKt$$ExternalSyntheticLambda1(str, i, 0);
        }
    }

    public static final void ListItemContainer(final ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-893202227);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ListItemContainer (Lists.kt:36)");
            }
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape m186RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(26);
            CardDefaults cardDefaults = CardDefaults.INSTANCE;
            long cardBackground = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.cardBackground(composerImpl);
            cardDefaults.getClass();
            CardKt.Card(fillMaxWidth, m186RoundedCornerShape0680j_4, CardDefaults.m254cardColorsro_MJ88(cardBackground, 0L, composerImpl, 14), null, null, ComposableLambdaKt.rememberComposableLambda(-1160263013, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$ListItemContainer$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ColumnScope columnScope = (ColumnScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(columnScope) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ListItemContainer.<anonymous> (Lists.kt:42)");
                    }
                    Function3.this.invoke(columnScope, composer2, Integer.valueOf(intValue & 14));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 196614, 24);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ListsKt$$ExternalSyntheticLambda1(composableLambdaImpl, i, 1);
        }
    }

    public static final void SecIconListItem(final String str, final Painter painter, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2074527927);
        int i2 = i | 6 | (composerImpl.changed(str) ? 32 : 16) | 384 | (composerImpl.changedInstance(painter) ? 2048 : 1024);
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecIconListItem (Lists.kt:154)");
            }
            composerImpl.startReplaceGroup(525189021);
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier m132heightInVpY3zN4$default = SizeKt.m132heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 56, 0.0f, 2);
            composerImpl.startReplaceGroup(-148627826);
            composerImpl.startReplaceGroup(-2083001966);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new ListsKt$$ExternalSyntheticLambda0(1);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            Modifier m34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(m132heightInVpY3zN4$default, null, null, false, null, null, (Function0) rememberedValue, 28);
            composerImpl.end(false);
            composerImpl.end(false);
            float f = 18;
            float f2 = 14;
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(m34clickableO2vRcR0$default, f, f2, f, f2);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m127paddingqDBjuR0);
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
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            ImageExtKt.ImageExt(painter, null, SizeKt.m139size3ABfNKs(companion, 36), composerImpl, ((i2 >> 9) & 14) | 432);
            SpacerKt.Spacer(composerImpl, SizeKt.m143width3ABfNKs(companion, 16));
            TwoLineListItem(str, null, null, composerImpl, ((i2 >> 3) & 14) | 384, 2);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(str, painter, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda4
                public final /* synthetic */ String f$1;
                public final /* synthetic */ Painter f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ListsKt.SecIconListItem(this.f$1, this.f$3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0092, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecListItem(kotlin.jvm.functions.Function0 r14, java.lang.String r15, java.lang.String r16, androidx.compose.runtime.Composer r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(kotlin.jvm.functions.Function0, java.lang.String, java.lang.String, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0091, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecRadioListItem(final kotlin.jvm.functions.Function0 r16, final java.lang.String r17, final java.lang.String r18, final java.lang.Boolean r19, androidx.compose.runtime.Composer r20, final int r21) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecRadioListItem(kotlin.jvm.functions.Function0, java.lang.String, java.lang.String, java.lang.Boolean, androidx.compose.runtime.Composer, int):void");
    }

    public static final void SecSubHeader(final String str, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1207641572);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changed(str) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecSubHeader (Lists.kt:178)");
            }
            Dp.Companion companion = Dp.Companion;
            float f = 18;
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(SizeKt.m132heightInVpY3zN4$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 35, 0.0f, 2), f, 13, f, 5);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m127paddingqDBjuR0);
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
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-777711954);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.SubHeaderTextStyle (Type.kt:106)");
            }
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secSemiBold = TypeKt.getSecSemiBold();
            long mediaPrimaryColor = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.mediaPrimaryColor(composerImpl);
            TextUnitType.Companion.getClass();
            TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secSemiBold, mediaPrimaryColor, TextUnitKt.pack(13.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            TextKt.m316Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m754copyp1EtxEg$default, composerImpl, i2 & 14, 0, 65534);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ListsKt.SecSubHeader(str, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b5, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0183, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01d1, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L86;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecSwitchListItem(final kotlin.jvm.functions.Function1 r21, final java.lang.String r22, java.lang.String r23, final java.lang.Boolean r24, androidx.compose.runtime.Composer r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecSwitchListItem(kotlin.jvm.functions.Function1, java.lang.String, java.lang.String, java.lang.Boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TwoLineListItem(java.lang.String r34, androidx.compose.ui.Modifier r35, java.lang.String r36, androidx.compose.runtime.Composer r37, int r38, int r39) {
        /*
            Method dump skipped, instructions count: 555
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.TwoLineListItem(java.lang.String, androidx.compose.ui.Modifier, java.lang.String, androidx.compose.runtime.Composer, int, int):void");
    }
}
