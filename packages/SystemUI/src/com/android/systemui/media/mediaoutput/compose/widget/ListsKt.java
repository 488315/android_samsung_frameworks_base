package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
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
import com.samsung.sesl.compose.component.RadioButtonKt;
import com.samsung.sesl.compose.component.SeslSwitchDefaults;
import com.samsung.sesl.compose.component.SwitchKt;
import com.samsung.sesl.compose.component.tokens.SeslPaletteTokens;
import com.samsung.sesl.compose.theme.SeslColorScheme;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

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
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            composerImpl2.startReplaceGroup(337750070);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new ListsKt$$ExternalSyntheticLambda0(0);
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            composerImpl2.end(false);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(ClickableKt.m34clickableO2vRcR0$default(modifierFillMaxWidth, null, null, false, null, null, (Function0) objRememberedValue, 28), 56, 0.0f, 2);
            float f = 18;
            float f2 = 14;
            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(modifierM133heightInVpY3zN4$default, f, f2, f, f2);
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterVertically, composerImpl2, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM128paddingqDBjuR0);
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
            Updater.m337setimpl(composerImpl2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            composerImpl2.startReplaceGroup(869356492);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.EmptyListTextStyle (Type.kt:112)");
            }
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secRegular = TypeKt.getSecRegular();
            long jColor = ColorKt.Color(4291611854L);
            TextUnitType.Companion.getClass();
            TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secRegular, jColor, TextUnitKt.pack(17.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            TextKt.m317Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default, composerImpl2, i2 & 14, 0, 65534);
            composerImpl = composerImpl2;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ListsKt$$ExternalSyntheticLambda1(str, i, 0);
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
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(26);
            CardDefaults cardDefaults = CardDefaults.INSTANCE;
            long jCardBackground = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.cardBackground(composerImpl);
            cardDefaults.getClass();
            CardKt.Card(modifierFillMaxWidth, roundedCornerShapeM187RoundedCornerShape0680j_4, CardDefaults.m255cardColorsro_MJ88(jCardBackground, 0L, composerImpl, 14), null, null, ComposableLambdaKt.rememberComposableLambda(-1160263013, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.ListItemContainer.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ColumnScope columnScope = (ColumnScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(columnScope) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ListItemContainer.<anonymous> (Lists.kt:42)");
                            }
                            composableLambdaImpl.invoke(columnScope, composer2, Integer.valueOf(iIntValue & 14));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 196614, 24);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ListsKt$$ExternalSyntheticLambda1(composableLambdaImpl, i, 1);
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
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 56, 0.0f, 2);
            composerImpl.startReplaceGroup(-148627826);
            composerImpl.startReplaceGroup(-2083001966);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new ListsKt$$ExternalSyntheticLambda0(1);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(modifierM133heightInVpY3zN4$default, null, null, false, null, null, (Function0) objRememberedValue, 28);
            composerImpl.end(false);
            composerImpl.end(false);
            float f = 18;
            float f2 = 14;
            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(modifierM34clickableO2vRcR0$default, f, f2, f, f2);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            ImageExtKt.ImageExt(painter, null, SizeKt.m140size3ABfNKs(companion, 36), composerImpl, ((i2 >> 9) & 14) | 432);
            SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, 16));
            TwoLineListItem(str, null, null, composerImpl, ((i2 >> 3) & 14) | 384, 2);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, painter, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda4
                public final /* synthetic */ String f$1;
                public final /* synthetic */ Painter f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ListsKt.SecIconListItem(this.f$1, this.f$3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecListItem(Function0 function0, String str, String str2, Composer composer, int i, int i2) {
        String str3;
        int i3;
        String str4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2008429455);
        int i4 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        if ((i & 48) == 0) {
            i4 |= composerImpl.changed(str) ? 32 : 16;
        }
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 = i4 | 384;
            str3 = str2;
        } else {
            str3 = str2;
            i3 = i4 | (composerImpl.changed(str3) ? 256 : 128);
        }
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            str4 = str3;
        } else {
            if (i5 != 0) {
                str3 = null;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecListItem (Lists.kt:68)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 56, 0.0f, 2);
            composerImpl.startReplaceGroup(992453818);
            boolean z = (i3 & 14) == 4;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new ListsKt$$ExternalSyntheticLambda7(1, function0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierM133heightInVpY3zN4$default, false, null, (Function0) objRememberedValue, 7);
                float f = 18;
                float f2 = 14;
                Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(modifierM35clickableXHw0xAI$default, f, f2, f, f2);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                String str5 = str3;
                TwoLineListItem(str, null, str5, composerImpl, (14 & (i3 >> 3)) | (i3 & 896), 2);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                str4 = str5;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ListsKt$$ExternalSyntheticLambda6(function0, str, str4, i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecRadioListItem(final Function0 function0, final String str, final String str2, final Boolean bool, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2139303390);
        int i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2) | (composerImpl.changed(str) ? 32 : 16) | (composerImpl.changed(str2) ? 256 : 128) | (composerImpl.changed(bool) ? 2048 : 1024);
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecRadioListItem (Lists.kt:88)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 56, 0.0f, 2);
            composerImpl.startReplaceGroup(-545203061);
            boolean z = (i2 & 14) == 4;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new ListsKt$$ExternalSyntheticLambda7(0, function0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierM133heightInVpY3zN4$default, false, null, (Function0) objRememberedValue, 7);
                float f = 16;
                Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(modifierM35clickableXHw0xAI$default, f, f, 18, f);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                SeslTheme.INSTANCE.getClass();
                SeslColorScheme colorScheme = SeslTheme.getColorScheme(composerImpl);
                SeslPaletteTokens.INSTANCE.getClass();
                long j = SeslPaletteTokens.GRAYSCALE_L1;
                ThemeKt.SeslTheme(false, SeslColorScheme.m3357copyFD3wquc$default(colorScheme, j, j), ComposableLambdaKt.rememberComposableLambda(1164702771, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$SecRadioListItem$2$1
                    /* JADX WARN: Removed duplicated region for block: B:17:0x005a  */
                    /* JADX WARN: Removed duplicated region for block: B:23:0x0093  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecRadioListItem.<anonymous>.<anonymous> (Lists.kt:103)");
                                }
                                Composer.Companion companion3 = Composer.Companion;
                                Boolean bool2 = bool;
                                Function0 function03 = function0;
                                if (bool2 != null) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(-189622871);
                                    boolean zBooleanValue = bool2.booleanValue();
                                    composerImpl3.startReplaceGroup(-144662199);
                                    boolean zChanged = composerImpl3.changed(function03);
                                    Object objRememberedValue2 = composerImpl3.rememberedValue();
                                    if (!zChanged) {
                                        companion3.getClass();
                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                            objRememberedValue2 = new ListsKt$$ExternalSyntheticLambda7(2, function03);
                                            composerImpl3.updateRememberedValue(objRememberedValue2);
                                        }
                                        composerImpl3.end(false);
                                        RadioButtonKt.SeslRadioButton(zBooleanValue, (Function0) objRememberedValue2, null, false, null, composerImpl3, 0);
                                        composerImpl3.end(false);
                                    }
                                } else {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(-189517812);
                                    composerImpl4.startReplaceGroup(-144658903);
                                    boolean zChanged2 = composerImpl4.changed(function03);
                                    Object objRememberedValue3 = composerImpl4.rememberedValue();
                                    if (!zChanged2) {
                                        companion3.getClass();
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            objRememberedValue3 = new ListsKt$$ExternalSyntheticLambda7(3, function03);
                                            composerImpl4.updateRememberedValue(objRememberedValue3);
                                        }
                                        composerImpl4.end(false);
                                        RadioButtonKt.SeslRadioButton(false, (Function0) objRememberedValue3, null, false, null, composerImpl4, 6);
                                        composerImpl4.end(false);
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 384, 1);
                SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, 14));
                TwoLineListItem(str, null, str2, composerImpl, ((i2 >> 3) & 14) | (i2 & 896), 2);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, str2, bool, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda8
                public final /* synthetic */ String f$1;
                public final /* synthetic */ String f$2;
                public final /* synthetic */ Boolean f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    String str3 = this.f$2;
                    Boolean bool2 = this.f$3;
                    ListsKt.SecRadioListItem(this.f$0, this.f$1, str3, bool2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
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
            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 35, 0.0f, 2), f, 13, f, 5);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-777711954);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.SubHeaderTextStyle (Type.kt:106)");
            }
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secSemiBold = TypeKt.getSecSemiBold();
            long jMediaPrimaryColor = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.mediaPrimaryColor(composerImpl);
            TextUnitType.Companion.getClass();
            TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secSemiBold, jMediaPrimaryColor, TextUnitKt.pack(13.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            TextKt.m317Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default, composerImpl, i2 & 14, 0, 65534);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ListsKt.SecSubHeader(str, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecSwitchListItem(final Function1 function1, final String str, String str2, final Boolean bool, Composer composer, final int i, final int i2) {
        String str3;
        String str4;
        int i3;
        final String str5;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1621964162);
        int i4 = i | (composerImpl.changedInstance(function1) ? 4 : 2);
        if ((i & 48) == 0) {
            str3 = str;
            i4 |= composerImpl.changed(str3) ? 32 : 16;
        } else {
            str3 = str;
        }
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 = i4 | 384;
            str4 = str2;
        } else {
            str4 = str2;
            i3 = i4 | (composerImpl.changed(str4) ? 256 : 128);
        }
        int i6 = i3 | (composerImpl.changed(bool) ? 2048 : 1024);
        if ((i6 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            str5 = str4;
        } else {
            if (i5 != 0) {
                str4 = null;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecSwitchListItem (Lists.kt:123)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 56, 0.0f, 2);
            composerImpl.startReplaceGroup(-71503037);
            int i7 = i6 & 7168;
            int i8 = i6 & 14;
            boolean z = (i7 == 2048) | (i8 == 4);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion3 = Composer.Companion;
            if (!z) {
                companion3.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda11
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            if (bool != null) {
                                function1.mo781invoke(Boolean.valueOf(!r0.booleanValue()));
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierM133heightInVpY3zN4$default, false, null, (Function0) objRememberedValue, 7);
                float f = 18;
                float f2 = 14;
                Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(modifierM35clickableXHw0xAI$default, f, f2, f, f2);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
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
                Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                String str6 = str3;
                String str7 = str4;
                TwoLineListItem(str6, RowScopeInstance.INSTANCE.weight(companion, 1.0f, true), str7, composerImpl, ((i6 >> 3) & 14) | (i6 & 896), 0);
                SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, 16));
                if (bool != null) {
                    composerImpl.startReplaceGroup(700114395);
                    boolean zBooleanValue = bool.booleanValue();
                    composerImpl.startReplaceGroup(-254507499);
                    boolean z2 = (i7 == 2048) | (i8 == 4);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!z2) {
                        companion3.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda12
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    ((Boolean) obj).booleanValue();
                                    function1.mo781invoke(Boolean.valueOf(!bool.booleanValue()));
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        composerImpl.end(false);
                        SeslSwitchDefaults seslSwitchDefaults = SeslSwitchDefaults.INSTANCE;
                        long jColor = ColorKt.Color(4281891583L);
                        seslSwitchDefaults.getClass();
                        SwitchKt.SeslSwitch(zBooleanValue, (Function1) objRememberedValue2, null, false, SeslSwitchDefaults.m3343colorsoq7We08(jColor, composerImpl), null, composerImpl, (i6 >> 9) & 14, 44);
                        composerImpl.end(false);
                    }
                } else {
                    composerImpl.startReplaceGroup(700352010);
                    composerImpl.startReplaceGroup(-254500815);
                    boolean z3 = i8 == 4;
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!z3) {
                        companion3.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda13
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    ((Boolean) obj).booleanValue();
                                    function1.mo781invoke(Boolean.TRUE);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl.end(false);
                        SwitchKt.SeslSwitch(false, (Function1) objRememberedValue3, null, false, null, null, composerImpl, 6, 60);
                        composerImpl.end(false);
                    }
                }
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                str5 = str7;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Boolean bool2 = bool;
                    ListsKt.SecSwitchListItem(function1, str, str5, bool2, (Composer) obj, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TwoLineListItem(String str, Modifier modifier, String str2, Composer composer, int i, int i2) {
        int i3;
        Modifier modifier2;
        Modifier modifier3;
        Modifier modifier4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1791382115);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 == 0) {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            if ((i & 384) == 0) {
                i3 |= composerImpl.changed(str2) ? 256 : 128;
            }
            if ((i3 & 147) == 146 || !composerImpl.getSkipping()) {
                Modifier modifierFillMaxWidth = i4 == 0 ? SizeKt.fillMaxWidth(Modifier.Companion, 1.0f) : modifier2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.TwoLineListItem (Lists.kt:51)");
                }
                Arrangement.INSTANCE.getClass();
                Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.Start, composerImpl, 6);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
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
                composerImpl.startReplaceGroup(1710998868);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.ListMainTextStyle (Type.kt:88)");
                }
                TextStyle.Companion companion = TextStyle.Companion;
                TextStyle secRegular = TypeKt.getSecRegular();
                long jMediaPrimaryColor = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.mediaPrimaryColor(composerImpl);
                TextUnitType.Companion.getClass();
                long j = TextUnitType.Sp;
                TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secRegular, jMediaPrimaryColor, TextUnitKt.pack(17.0f, j), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                int i5 = i3;
                TextKt.m317Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default, composerImpl, i3 & 14, 0, 65534);
                composerImpl = composerImpl;
                composerImpl.startReplaceGroup(-762085099);
                if (str2 != null) {
                    composerImpl.startReplaceGroup(-1112696151);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.ListSummaryTextStyle (Type.kt:94)");
                    }
                    TextStyle textStyleM756copyp1EtxEg$default2 = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), ColorKt.Color(4291611854L), TextUnitKt.pack(13.0f, j), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    modifier3 = modifierFillMaxWidth;
                    TextKt.m317Text4IGK_g(str2, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default2, composerImpl, (i5 >> 6) & 14, 0, 65534);
                    composerImpl = composerImpl;
                } else {
                    modifier3 = modifierFillMaxWidth;
                }
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                    ComposerKt.traceEventEnd();
                }
                modifier4 = modifier3;
            } else {
                composerImpl.skipToGroupEnd();
                modifier4 = modifier2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new ListsKt$$ExternalSyntheticLambda6(str, modifier4, str2, i, i2);
                return;
            }
            return;
        }
        i3 |= 48;
        modifier2 = modifier;
        if ((i & 384) == 0) {
        }
        if ((i3 & 147) == 146) {
            if (i4 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$12 = Arrangement.Center;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Center$12, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier != null) {
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
