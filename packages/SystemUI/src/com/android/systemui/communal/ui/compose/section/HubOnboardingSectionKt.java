package com.android.systemui.communal.ui.compose.section;

import android.content.DialogInterface;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.ChargingStationKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class HubOnboardingSectionKt {
    public static final void HubOnboardingBottomSheet(final SystemUIDialogFactory systemUIDialogFactory, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1353420450);
        int i2 = (composerImpl.changedInstance(systemUIDialogFactory) ? 32 : 16) | i | (composerImpl.changedInstance(function0) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.HubOnboardingBottomSheet (HubOnboardingSection.kt:98)");
            }
            composerImpl.startReplaceGroup(1067172741);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1067174886);
            if (m == composer$Companion$Empty$1) {
                m = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl.updateRememberedValue(m);
            }
            final MutableState mutableState2 = (MutableState) m;
            composerImpl.end(false);
            Boolean bool = Boolean.TRUE;
            composerImpl.startReplaceGroup(1067178738);
            boolean changedInstance = ((i2 & 896) == 256) | composerImpl.changedInstance(systemUIDialogFactory);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new Function1() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        final MutableState mutableState3 = mutableState;
                        Dp.Companion companion = Dp.Companion;
                        ComponentSystemUIDialog m3078createBottomSheet6ZxE2Lo$default = SystemUIDialogFactoryExtKt.m3078createBottomSheet6ZxE2Lo$default(SystemUIDialogFactory.this, new ComposableLambdaImpl(19620036, true, new Function3() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$HubOnboardingBottomSheet$1$1$1
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                ((Number) obj4).intValue();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.HubOnboardingBottomSheet.<anonymous>.<anonymous>.<anonymous> (HubOnboardingSection.kt:107)");
                                }
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                composerImpl2.startReplaceGroup(2112610070);
                                Object rememberedValue3 = composerImpl2.rememberedValue();
                                Composer.Companion.getClass();
                                if (rememberedValue3 == Composer.Companion.Empty) {
                                    final MutableState mutableState4 = mutableState3;
                                    rememberedValue3 = new Function0() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$HubOnboardingBottomSheet$1$1$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            ComponentSystemUIDialog componentSystemUIDialog = (ComponentSystemUIDialog) MutableState.this.getValue();
                                            if (componentSystemUIDialog != null) {
                                                componentSystemUIDialog.dismiss();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl2.updateRememberedValue(rememberedValue3);
                                }
                                composerImpl2.end(false);
                                HubOnboardingSectionKt.HubOnboardingBottomSheetContent((Function0) rememberedValue3, composerImpl2, 6);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }), true, 627, 7);
                        final Function0 function02 = function0;
                        final MutableState mutableState4 = mutableState2;
                        m3078createBottomSheet6ZxE2Lo$default.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$HubOnboardingBottomSheet$1$1$2$1
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                if (((Boolean) mutableState4.getValue()).booleanValue()) {
                                    return;
                                }
                                Function0.this.invoke();
                            }
                        });
                        m3078createBottomSheet6ZxE2Lo$default.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$HubOnboardingBottomSheet$1$1$2$2
                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                mutableState4.setValue(Boolean.TRUE);
                            }
                        });
                        m3078createBottomSheet6ZxE2Lo$default.show();
                        mutableState3.setValue(m3078createBottomSheet6ZxE2Lo$default);
                        return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$HubOnboardingBottomSheet$lambda$9$lambda$8$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                MutableState mutableState5 = MutableState.this;
                                ComponentSystemUIDialog componentSystemUIDialog = (ComponentSystemUIDialog) mutableState5.getValue();
                                if (componentSystemUIDialog != null) {
                                    componentSystemUIDialog.cancel();
                                }
                                mutableState5.setValue(null);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.DisposableEffect(bool, (Function1) rememberedValue2, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(function0, i) { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$$ExternalSyntheticLambda2
                public final /* synthetic */ Function0 f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    HubOnboardingSectionKt.HubOnboardingBottomSheet(SystemUIDialogFactory.this, this.f$2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void HubOnboardingBottomSheetContent(final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-242266728);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.HubOnboardingBottomSheetContent (HubOnboardingSection.kt:136)");
            }
            MaterialTheme.INSTANCE.getClass();
            ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(SizeKt.fillMaxWidth(companion, 1.0f), 48);
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterHorizontally, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m124padding3ABfNKs);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
            ImageVector imageVector = ChargingStationKt._chargingStation;
            if (imageVector == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Outlined.ChargingStation", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i2 = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(14.5f, 11.0f);
                pathBuilder.lineToRelative(-3.0f, 6.0f);
                pathBuilder.verticalLineToRelative(-4.0f);
                pathBuilder.horizontalLineToRelative(-2.0f);
                pathBuilder.lineToRelative(3.0f, -6.0f);
                pathBuilder.verticalLineToRelative(4.0f);
                pathBuilder.horizontalLineTo(14.5f);
                pathBuilder.close();
                pathBuilder.moveTo(17.0f, 3.0f);
                pathBuilder.horizontalLineTo(7.0f);
                pathBuilder.verticalLineToRelative(1.0f);
                pathBuilder.horizontalLineToRelative(10.0f);
                pathBuilder.verticalLineTo(3.0f);
                pathBuilder.moveTo(17.0f, 20.0f);
                pathBuilder.horizontalLineTo(7.0f);
                pathBuilder.verticalLineToRelative(1.0f);
                pathBuilder.horizontalLineToRelative(10.0f);
                pathBuilder.verticalLineTo(20.0f);
                pathBuilder.moveTo(17.0f, 1.0f);
                pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f);
                pathBuilder.verticalLineToRelative(18.0f);
                pathBuilder.curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f);
                pathBuilder.horizontalLineTo(7.0f);
                pathBuilder.curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f);
                pathBuilder.verticalLineTo(3.0f);
                pathBuilder.curveToRelative(0.0f, -1.1f, 0.9f, -2.0f, 2.0f, -2.0f);
                pathBuilder.horizontalLineTo(17.0f);
                pathBuilder.lineTo(17.0f, 1.0f);
                pathBuilder.close();
                pathBuilder.moveTo(7.0f, 18.0f);
                pathBuilder.horizontalLineToRelative(10.0f);
                pathBuilder.verticalLineTo(6.0f);
                pathBuilder.horizontalLineTo(7.0f);
                pathBuilder.verticalLineTo(18.0f);
                pathBuilder.lineTo(7.0f, 18.0f);
                pathBuilder.close();
                builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i2, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                ChargingStationKt._chargingStation = imageVector;
            }
            float f = 32;
            IconKt.m270Iconww6aTOc(imageVector, (String) null, SizeKt.m139size3ABfNKs(companion, f), 0L, composerImpl, 432, 8);
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion, 16));
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.hub_onboarding_bottom_sheet_title, composerImpl), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).headlineMedium, composerImpl, 0, 0, 65534);
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion, f));
            ImageKt.Image(PainterResources_androidKt.painterResource(R.drawable.hub_onboarding_bg, composerImpl, 0), null, null, null, null, 0.0f, null, composerImpl, 48, 124);
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion, f));
            Modifier m143width3ABfNKs = SizeKt.m143width3ABfNKs(companion, 300);
            String stringResource = StringResources_androidKt.stringResource(R.string.hub_onboarding_bottom_sheet_text, composerImpl);
            TextAlign.Companion.getClass();
            TextKt.m316Text4IGK_g(stringResource, m143width3ABfNKs, 0L, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(TextAlign.Center), 0L, 0, false, 0, 0, null, null, composerImpl, 48, 0, 130556);
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion, f));
            Modifier align = columnScopeInstance.align(companion, Alignment.Companion.End);
            ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
            long j = colorScheme.primary;
            buttonDefaults.getClass();
            ButtonColors m251buttonColorsro_MJ88 = ButtonDefaults.m251buttonColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
            ComposableSingletons$HubOnboardingSectionKt.INSTANCE.getClass();
            ButtonKt.Button(function0, align, false, null, m251buttonColorsro_MJ88, null, null, null, null, ComposableSingletons$HubOnboardingSectionKt.f39lambda1, composerImpl, 805306374, 492);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(i, function0) { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSectionKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Function0 f$0;

                {
                    this.f$0 = function0;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    HubOnboardingSectionKt.HubOnboardingBottomSheetContent(this.f$0, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
