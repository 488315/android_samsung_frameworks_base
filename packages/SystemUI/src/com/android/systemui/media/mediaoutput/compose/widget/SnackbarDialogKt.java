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
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SnackbarData;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
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
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;

public abstract class SnackbarDialogKt {
    /* JADX WARN: Type inference failed for: r6v21, types: [com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$SnackbarDialog$2$2$1$2, kotlin.jvm.internal.Lambda] */
    public static final void SnackbarDialog(final DialogInterface dialogInterface, final SnackbarData snackbarData, Composer composer, final int i) {
        Function2 function2;
        Function2 function22;
        Function2 function23;
        boolean z;
        Function0 function0;
        SnackbarDialogImpl snackbarDialogImpl;
        Function2 function24;
        Modifier.Companion companion;
        ComposerImpl composerImpl;
        boolean z2;
        ComposerImpl composerImpl2;
        boolean z3;
        boolean z4;
        char c;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(835644400);
        int i2 = (i & 14) == 0 ? (composerImpl3.changed(dialogInterface) ? 4 : 2) | i : i;
        if ((i & 112) == 0) {
            i2 |= composerImpl3.changed(snackbarData) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            composerImpl2 = composerImpl3;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion2 = Modifier.Companion;
            Dp.Companion companion3 = Dp.Companion;
            float f = 24;
            Modifier m105paddingqDBjuR0 = PaddingKt.m105paddingqDBjuR0(BackgroundKt.m24backgroundbw27NRU(SizeKt.m119width3ABfNKs(ClickableKt.m30clickableO2vRcR0$default(companion2, null, null, false, null, new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$SnackbarDialog$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            }, 28), 340), ColorKt.Color(4294769919L), RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(26)), f, f, f, 20);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl3, 0);
            int i3 = composerImpl3.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, m105paddingqDBjuR0);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            boolean z5 = composerImpl3.applier instanceof Applier;
            if (!z5) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl3.startReusableNode();
            if (composerImpl3.inserting) {
                composerImpl3.createNode(function02);
            } else {
                composerImpl3.useNode();
            }
            Function2 function25 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m276setimpl(composerImpl3, columnMeasurePolicy, function25);
            Function2 function26 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m276setimpl(composerImpl3, currentCompositionLocalScope, function26);
            Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl3, i3, function27);
            }
            Function2 function28 = ComposeUiNode.Companion.SetModifier;
            Updater.m276setimpl(composerImpl3, materializeModifier, function28);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            SnackbarDialogImpl snackbarDialogImpl2 = (SnackbarDialogImpl) dialogInterface;
            composerImpl3.startReplaceGroup(-1913438392);
            String str = snackbarDialogImpl2.title;
            if (str == null) {
                function2 = function26;
                function22 = function27;
                function23 = function25;
                z = z5;
                function0 = function02;
                z2 = false;
                snackbarDialogImpl = snackbarDialogImpl2;
                function24 = function28;
                composerImpl = composerImpl3;
                companion = companion2;
            } else {
                composerImpl3.startReplaceGroup(-1937149718);
                TextStyle.Companion companion4 = TextStyle.Companion;
                TextStyle secSemiBold = TypeKt.getSecSemiBold();
                DarkThemeKt.isSystemInDarkTheme(composerImpl3);
                long Color = ColorKt.Color(4278255874L);
                TextUnitType.Companion.getClass();
                TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secSemiBold, Color, TextUnitKt.pack(17.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
                composerImpl3.end(false);
                function2 = function26;
                function22 = function27;
                function23 = function25;
                z = z5;
                function0 = function02;
                snackbarDialogImpl = snackbarDialogImpl2;
                function24 = function28;
                TextKt.m257Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m651copyp1EtxEg$default, composerImpl3, 0, 0, 65534);
                companion = companion2;
                composerImpl = composerImpl3;
                SpacerKt.Spacer(composerImpl, SizeKt.m108height3ABfNKs(companion, 12));
                z2 = false;
            }
            composerImpl.end(z2);
            composerImpl.startReplaceGroup(-450202428);
            TextStyle.Companion companion5 = TextStyle.Companion;
            TextStyle secRegular = TypeKt.getSecRegular();
            long Color2 = DarkThemeKt.isSystemInDarkTheme(composerImpl) ? ColorKt.Color(4278255874L) : ColorKt.Color(4280624424L);
            TextUnitType.Companion.getClass();
            TextStyle m651copyp1EtxEg$default2 = TextStyle.m651copyp1EtxEg$default(secRegular, Color2, TextUnitKt.pack(14.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
            composerImpl.end(z2);
            SnackbarDialogImpl snackbarDialogImpl3 = snackbarDialogImpl;
            ComposerImpl composerImpl4 = composerImpl;
            Modifier.Companion companion6 = companion;
            TextKt.m257Text4IGK_g(snackbarDialogImpl3.body, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m651copyp1EtxEg$default2, composerImpl4, 0, 0, 65534);
            ComposerImpl composerImpl5 = composerImpl4;
            composerImpl5.startReplaceGroup(2108926057);
            if (!snackbarDialogImpl3.actions.isEmpty()) {
                SpacerKt.Spacer(composerImpl5, SizeKt.m108height3ABfNKs(companion6, 17));
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.CenterVertically, composerImpl5, 48);
                int i4 = composerImpl5.compoundKeyHash;
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl5.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl5, companion6);
                if (!z) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl5.startReusableNode();
                if (composerImpl5.inserting) {
                    composerImpl5.createNode(function0);
                } else {
                    composerImpl5.useNode();
                }
                Updater.m276setimpl(composerImpl5, rowMeasurePolicy, function23);
                Updater.m276setimpl(composerImpl5, currentCompositionLocalScope2, function2);
                if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(i4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl5, i4, function22);
                }
                Updater.m276setimpl(composerImpl5, materializeModifier2, function24);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                final List reversed = CollectionsKt___CollectionsKt.reversed(snackbarDialogImpl3.actions);
                ButtonDefaults.INSTANCE.getClass();
                MaterialTheme.INSTANCE.getClass();
                ButtonColors defaultButtonColors$material3_release = ButtonDefaults.getDefaultButtonColors$material3_release(MaterialTheme.getColorScheme(composerImpl5));
                Color.Companion.getClass();
                ButtonColors m208copyjRlVdoo = defaultButtonColors$material3_release.m208copyjRlVdoo(Color.Transparent, ((Color) composerImpl5.consume(ContentColorKt.LocalContentColor)).value, defaultButtonColors$material3_release.disabledContainerColor, defaultButtonColors$material3_release.disabledContentColor);
                float f2 = 0;
                PaddingValuesImpl paddingValuesImpl = new PaddingValuesImpl(f2, f2, f2, f2, null);
                composerImpl5.startReplaceGroup(-1913437698);
                final int i5 = 0;
                for (Object obj : reversed) {
                    int i6 = i5 + 1;
                    if (i5 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    final String str2 = (String) obj;
                    composerImpl5.startReplaceGroup(-2131791787);
                    if (i5 > 0) {
                        c = 16;
                        DividerKt.m219VerticalDivider9IZ8Weo(PaddingKt.m104paddingVpY3zN4$default(SizeKt.m108height3ABfNKs(Modifier.Companion, 16), 6, 0.0f, 2), 0.0f, ColorKt.Color(4293519852L), composerImpl5, 390, 2);
                    } else {
                        c = 16;
                    }
                    composerImpl5.end(false);
                    ButtonKt.Button(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$SnackbarDialog$2$2$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            if (i5 == reversed.size() - 1) {
                                CancellableContinuation cancellableContinuation = ((SnackbarHostState.SnackbarDataImpl) snackbarData).continuation;
                                if (cancellableContinuation.isActive()) {
                                    int i7 = Result.$r8$clinit;
                                    cancellableContinuation.resumeWith(SnackbarResult.ActionPerformed);
                                }
                            } else {
                                ((SnackbarHostState.SnackbarDataImpl) snackbarData).dismiss();
                            }
                            return Unit.INSTANCE;
                        }
                    }, rowScopeInstance.weight(Modifier.Companion, 1.0f, true), false, null, m208copyjRlVdoo, null, null, paddingValuesImpl, null, ComposableLambdaKt.rememberComposableLambda(1140690427, composerImpl5, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$SnackbarDialog$2$2$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj2, Object obj3, Object obj4) {
                            Composer composer2 = (Composer) obj3;
                            if ((((Number) obj4).intValue() & 81) == 16) {
                                ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                                if (composerImpl6.getSkipping()) {
                                    composerImpl6.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                            String str3 = str2;
                            ComposerImpl composerImpl7 = (ComposerImpl) composer2;
                            composerImpl7.startReplaceGroup(785544752);
                            TextStyle.Companion companion7 = TextStyle.Companion;
                            TextStyle secSemiBold2 = TypeKt.getSecSemiBold();
                            DarkThemeKt.isSystemInDarkTheme(composerImpl7);
                            long Color3 = ColorKt.Color(4278255874L);
                            TextUnitType.Companion.getClass();
                            TextStyle m651copyp1EtxEg$default3 = TextStyle.m651copyp1EtxEg$default(secSemiBold2, Color3, TextUnitKt.pack(18.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
                            composerImpl7.end(false);
                            TextKt.m257Text4IGK_g(str3, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m651copyp1EtxEg$default3, composer2, 0, 0, 65534);
                            return Unit.INSTANCE;
                        }
                    }), composerImpl5, 817889280, 364);
                    i5 = i6;
                    rowScopeInstance = rowScopeInstance;
                    composerImpl5 = composerImpl5;
                }
                composerImpl2 = composerImpl5;
                z3 = false;
                composerImpl2.end(false);
                z4 = true;
                composerImpl2.end(true);
            } else {
                composerImpl2 = composerImpl5;
                z3 = false;
                z4 = true;
            }
            composerImpl2.end(z3);
            composerImpl2.end(z4);
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogKt$SnackbarDialog$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    SnackbarDialogKt.SnackbarDialog(DialogInterface.this, snackbarData, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
