package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.DividerKt;
import androidx.compose.material3.SnackbarData;
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
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SnackbarDialogKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v3 */
    public static final void SnackbarDialog(final SnackbarDialogImpl snackbarDialogImpl, final SnackbarData snackbarData, Composer composer, final int i) {
        Modifier.Companion companion;
        ?? r3;
        Function2 function2;
        Function2 function22;
        Function2 function23;
        Function0 function0;
        Function2 function24;
        boolean z;
        ComposerImpl composerImpl;
        boolean z2;
        ComposerImpl composerImpl2;
        ButtonColors buttonColors;
        ComposerImpl composerImpl3;
        final String str;
        final int i2;
        ComposerImpl composerImpl4;
        ComposerImpl composerImpl5 = (ComposerImpl) composer;
        composerImpl5.startRestartGroup(835644400);
        int i3 = (i & 6) == 0 ? ((i & 8) == 0 ? composerImpl5.changed(snackbarDialogImpl) : composerImpl5.changedInstance(snackbarDialogImpl) ? 4 : 2) | i : i;
        if ((i & 48) == 0) {
            i3 |= composerImpl5.changed(snackbarData) ? 32 : 16;
        }
        int i4 = i3;
        if ((i4 & 19) == 18 && composerImpl5.getSkipping()) {
            composerImpl5.skipToGroupEnd();
            composerImpl4 = composerImpl5;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialog (SnackbarDialog.kt:55)");
            }
            Modifier.Companion companion2 = Modifier.Companion;
            composerImpl5.startReplaceGroup(952187852);
            Object rememberedValue = composerImpl5.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new SnackbarDialogKt$$ExternalSyntheticLambda0();
                composerImpl5.updateRememberedValue(rememberedValue);
            }
            composerImpl5.end(false);
            Dp.Companion companion3 = Dp.Companion;
            Modifier m143width3ABfNKs = SizeKt.m143width3ABfNKs(ClickableKt.m34clickableO2vRcR0$default(companion2, null, null, false, null, null, (Function0) rememberedValue, 28), 340);
            composerImpl5.startReplaceGroup(-387536149);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.BackgroundColor (SnackbarDialog.kt:107)");
            }
            long Color = DarkThemeKt.isSystemInDarkTheme(composerImpl5) ? ColorKt.Color(4280624424L) : ColorKt.Color(4294769919L);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl5.end(false);
            Modifier m26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(m143width3ABfNKs, Color, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(26));
            float f = 24;
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(m26backgroundbw27NRU, f, f, f, 20);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl5, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl5);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl5, m127paddingqDBjuR0);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl5.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl5.startReusableNode();
            if (composerImpl5.inserting) {
                composerImpl5.createNode(function02);
            } else {
                composerImpl5.useNode();
            }
            Function2 function25 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composerImpl5, columnMeasurePolicy, function25);
            Function2 function26 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl5, currentCompositionLocalScope, function26);
            Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function27);
            }
            Function2 function28 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl5, materializeModifier, function28);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            String str2 = snackbarDialogImpl.title;
            composerImpl5.startReplaceGroup(812955136);
            if (str2 == null) {
                r3 = 0;
                function22 = function28;
                function2 = function25;
                function23 = function26;
                function24 = function27;
                function0 = function02;
                companion = companion2;
                z = true;
                composerImpl = composerImpl5;
            } else {
                composerImpl5.startReplaceGroup(-1937149718);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.TitleStyle (SnackbarDialog.kt:113)");
                }
                TextStyle.Companion companion4 = TextStyle.Companion;
                TextStyle secSemiBold = TypeKt.getSecSemiBold();
                long Color2 = ColorKt.Color(DarkThemeKt.isSystemInDarkTheme(composerImpl5) ? 4294638335L : 4278255874L);
                TextUnitType.Companion.getClass();
                TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secSemiBold, Color2, TextUnitKt.pack(17.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl5.end(false);
                companion = companion2;
                r3 = 0;
                function2 = function25;
                function22 = function28;
                function23 = function26;
                function0 = function02;
                function24 = function27;
                z = true;
                TextKt.m316Text4IGK_g(str2, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m754copyp1EtxEg$default, composerImpl5, 0, 0, 65534);
                ComposerImpl composerImpl6 = composerImpl5;
                SpacerKt.Spacer(composerImpl6, SizeKt.m130height3ABfNKs(companion, 12));
                composerImpl = composerImpl6;
            }
            composerImpl.end(r3);
            composerImpl.startReplaceGroup(-450202428);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.BodyStyle (SnackbarDialog.kt:119)");
            }
            TextStyle.Companion companion5 = TextStyle.Companion;
            TextStyle secRegular = TypeKt.getSecRegular();
            long Color3 = DarkThemeKt.isSystemInDarkTheme(composerImpl) ? ColorKt.Color(4293585643L) : ColorKt.Color(4280624424L);
            TextUnitType.Companion.getClass();
            TextStyle m754copyp1EtxEg$default2 = TextStyle.m754copyp1EtxEg$default(secRegular, Color3, TextUnitKt.pack(14.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(r3);
            ComposerImpl composerImpl7 = composerImpl;
            TextKt.m316Text4IGK_g(snackbarDialogImpl.body, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m754copyp1EtxEg$default2, composerImpl7, 0, 0, 65534);
            ComposerImpl composerImpl8 = composerImpl7;
            composerImpl8.startReplaceGroup(812962697);
            if (((ArrayList) snackbarDialogImpl.actions).isEmpty()) {
                z2 = z;
                composerImpl2 = composerImpl8;
            } else {
                SpacerKt.Spacer(composerImpl8, SizeKt.m130height3ABfNKs(companion, 17));
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.CenterVertically, composerImpl8, 48);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl8);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl8.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl8, companion);
                composerImpl8.startReusableNode();
                if (composerImpl8.inserting) {
                    composerImpl8.createNode(function0);
                } else {
                    composerImpl8.useNode();
                }
                Updater.m336setimpl(composerImpl8, rowMeasurePolicy, function2);
                Updater.m336setimpl(composerImpl8, currentCompositionLocalScope2, function23);
                if (composerImpl8.inserting || !Intrinsics.areEqual(composerImpl8.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl8, currentCompositeKeyHash2, function24);
                }
                Updater.m336setimpl(composerImpl8, materializeModifier2, function22);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                final List reversed = CollectionsKt___CollectionsKt.reversed(snackbarDialogImpl.actions);
                ButtonDefaults.INSTANCE.getClass();
                ButtonColors buttonColors2 = ButtonDefaults.buttonColors(composerImpl8);
                Color.Companion.getClass();
                ButtonColors m250copyjRlVdoo = buttonColors2.m250copyjRlVdoo(Color.Transparent, ((Color) composerImpl8.consume(ContentColorKt.LocalContentColor)).value, buttonColors2.disabledContainerColor, buttonColors2.disabledContentColor);
                float f2 = (float) r3;
                PaddingValuesImpl m120PaddingValuesYgX7TsA = PaddingKt.m120PaddingValuesYgX7TsA(f2, f2);
                composerImpl8.startReplaceGroup(-1661032964);
                int i5 = r3;
                ComposerImpl composerImpl9 = composerImpl8;
                for (Object obj : reversed) {
                    int i6 = i5 + 1;
                    if (i5 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    String str3 = (String) obj;
                    composerImpl9.startReplaceGroup(-1661031901);
                    if (i5 > 0) {
                        buttonColors = m250copyjRlVdoo;
                        Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(SizeKt.m130height3ABfNKs(Modifier.Companion, 16), 6, 0.0f, 2);
                        composerImpl9.startReplaceGroup(42837756);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.DividerColor (SnackbarDialog.kt:110)");
                        }
                        long Color4 = ColorKt.Color(DarkThemeKt.isSystemInDarkTheme(composerImpl9) ? 4282861386L : 4293519852L);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl9.end(r3);
                        Composer composer2 = composerImpl9;
                        i2 = i5;
                        str = str3;
                        DividerKt.m263VerticalDivider9IZ8Weo(m126paddingVpY3zN4$default, 0.0f, Color4, composer2, 6, 2);
                        composerImpl3 = composer2;
                    } else {
                        buttonColors = m250copyjRlVdoo;
                        composerImpl3 = composerImpl9;
                        str = str3;
                        i2 = i5;
                    }
                    composerImpl3.end(r3);
                    composerImpl3.startReplaceGroup(1532257557);
                    boolean changed = composerImpl3.changed(i2) | composerImpl3.changedInstance(reversed) | ((i4 & 112) == 32 ? z : r3);
                    Object rememberedValue2 = composerImpl3.rememberedValue();
                    if (changed || rememberedValue2 == Composer.Companion.Empty) {
                        rememberedValue2 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int size = reversed.size() - 1;
                                int i7 = i2;
                                SnackbarData snackbarData2 = snackbarData;
                                if (i7 == size) {
                                    snackbarData2.performAction();
                                } else {
                                    snackbarData2.dismiss();
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl3.updateRememberedValue(rememberedValue2);
                    }
                    Function0 function03 = (Function0) rememberedValue2;
                    composerImpl3.end(r3);
                    Modifier weight = rowScopeInstance.weight(Modifier.Companion, 1.0f, z);
                    ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(1140690427, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$SnackbarDialog$2$2$1$2
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj2, Object obj3, Object obj4) {
                            Composer composer3 = (Composer) obj3;
                            if ((((Number) obj4).intValue() & 17) == 16) {
                                ComposerImpl composerImpl10 = (ComposerImpl) composer3;
                                if (composerImpl10.getSkipping()) {
                                    composerImpl10.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialog.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SnackbarDialog.kt:98)");
                            }
                            ComposerImpl composerImpl11 = (ComposerImpl) composer3;
                            composerImpl11.startReplaceGroup(785544752);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ActionStyle (SnackbarDialog.kt:125)");
                            }
                            TextStyle.Companion companion6 = TextStyle.Companion;
                            TextStyle secSemiBold2 = TypeKt.getSecSemiBold();
                            long Color5 = ColorKt.Color(DarkThemeKt.isSystemInDarkTheme(composerImpl11) ? 4294638335L : 4278255874L);
                            TextUnitType.Companion.getClass();
                            TextStyle m754copyp1EtxEg$default3 = TextStyle.m754copyp1EtxEg$default(secSemiBold2, Color5, TextUnitKt.pack(18.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl11.end(false);
                            TextKt.m316Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m754copyp1EtxEg$default3, composer3, 0, 0, 65534);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl3);
                    List list = reversed;
                    PaddingValuesImpl paddingValuesImpl = m120PaddingValuesYgX7TsA;
                    ComposerImpl composerImpl10 = composerImpl3;
                    ButtonColors buttonColors3 = buttonColors;
                    ButtonKt.Button(function03, weight, false, null, buttonColors3, null, null, paddingValuesImpl, null, rememberComposableLambda, composerImpl10, 817889280, 364);
                    m250copyjRlVdoo = buttonColors3;
                    m120PaddingValuesYgX7TsA = paddingValuesImpl;
                    i5 = i6;
                    reversed = list;
                    z = true;
                    composerImpl9 = composerImpl10;
                }
                composerImpl9.end(r3);
                z2 = true;
                composerImpl9.end(true);
                composerImpl2 = composerImpl9;
            }
            boolean m = AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, r3, z2);
            composerImpl4 = composerImpl2;
            if (m) {
                ComposerKt.traceEventEnd();
                composerImpl4 = composerImpl2;
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl4.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SnackbarDialogKt.SnackbarDialog(SnackbarDialogImpl.this, snackbarData, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
