package com.android.systemui.communal.ui.compose;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ButtonKt;
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
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class EnableWidgetDialogKt {
    public static final void DialogComposable(final String str, final String str2, final Function0 function0, final Function0 function02, Composer composer, final int i) {
        Function0 function03;
        Function2 function2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-906920275);
        int i2 = i | (composerImpl.changed(str) ? 4 : 2) | (composerImpl.changed(str2) ? 32 : 16) | (composerImpl.changedInstance(function0) ? 256 : 128) | (composerImpl.changedInstance(function02) ? 2048 : 1024);
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.DialogComposable (EnableWidgetDialog.kt:90)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SizeKt.fillMaxWidth(companion, 1.0f), 0.0f, 18, 0.0f, 8, 5);
            MaterialTheme.INSTANCE.getClass();
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifierM129paddingqDBjuR0$default, MaterialTheme.getColorScheme(composerImpl).surfaceBright, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(28));
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM26backgroundbw27NRU);
            ComposeUiNode.Companion.getClass();
            Function0 function04 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function04);
            } else {
                composerImpl.useNode();
            }
            Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function22);
            Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function23);
            Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function24);
            }
            Function2 function25 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function25);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            Arrangement.INSTANCE.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.m92spacedBy0680j_4(20), Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function04);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function22);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function23);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function24);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function25);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier modifierWrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(PaddingKt.m127paddingVpY3zN4$default(companion, 24, 0.0f, 2), 1.0f), 3);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function04);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function22);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function23);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function24);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function25);
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl).titleMedium;
            long j = MaterialTheme.getColorScheme(composerImpl).onSurface;
            TextAlign.Companion.getClass();
            TextKt.m317Text4IGK_g(str, null, j, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(TextAlign.Center), 0L, 0, false, 1, 0, null, textStyle, composerImpl, i2 & 14, 3072, 56826);
            composerImpl = composerImpl;
            composerImpl.end(true);
            Modifier modifierWrapContentHeight$default2 = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(PaddingKt.m129paddingqDBjuR0$default(companion, 0.0f, 0.0f, 12, 0.0f, 11), 1.0f), 3);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                function03 = function04;
                composerImpl.createNode(function03);
            } else {
                function03 = function04;
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function22);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function23);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                function2 = function24;
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function2);
            } else {
                function2 = function24;
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function25);
            Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(companion, 1.0f);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.End, Alignment.Companion.Top, composerImpl, 6);
            int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function03);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, function22);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope5, function23);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl, currentCompositeKeyHash5, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier5, function25);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            float f = 16;
            PaddingValuesImpl paddingValuesImplM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(f);
            ComposableSingletons$EnableWidgetDialogKt.INSTANCE.getClass();
            ButtonKt.TextButton(function02, null, false, null, null, null, null, paddingValuesImplM120PaddingValues0680j_4, null, ComposableSingletons$EnableWidgetDialogKt.f37lambda1, composerImpl, ((i2 >> 9) & 14) | 817889280, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
            ButtonKt.TextButton(function0, null, false, null, null, null, null, PaddingKt.m120PaddingValues0680j_4(f), null, ComposableLambdaKt.rememberComposableLambda(-1297407490, new Function3() { // from class: com.android.systemui.communal.ui.compose.EnableWidgetDialogKt$DialogComposable$1$1$2$1$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.DialogComposable.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (EnableWidgetDialog.kt:133)");
                            }
                            TextKt.m317Text4IGK_g(str2, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 6) & 14) | 817889280, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, str2, function0, function02, i) { // from class: com.android.systemui.communal.ui.compose.EnableWidgetDialogKt$$ExternalSyntheticLambda2
                public final /* synthetic */ String f$0;
                public final /* synthetic */ String f$1;
                public final /* synthetic */ Function0 f$2;
                public final /* synthetic */ Function0 f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function0 function05 = this.f$2;
                    Function0 function06 = this.f$3;
                    EnableWidgetDialogKt.DialogComposable(this.f$0, this.f$1, function05, function06, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void EnableWidgetDialog(final boolean z, final SystemUIDialogFactory systemUIDialogFactory, final String str, final String str2, final Function0 function0, final Function0 function02, Composer composer, final int i) {
        boolean z2;
        int i2;
        final String str3;
        String str4;
        Function0 function03;
        Function0 function04;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1900524164);
        if ((i & 6) == 0) {
            z2 = z;
            i2 = (composerImpl.changed(z2) ? 4 : 2) | i;
        } else {
            z2 = z;
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(systemUIDialogFactory) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            str3 = str;
            i2 |= composerImpl.changed(str3) ? 256 : 128;
        } else {
            str3 = str;
        }
        if ((i & 3072) == 0) {
            str4 = str2;
            i2 |= composerImpl.changed(str4) ? 2048 : 1024;
        } else {
            str4 = str2;
        }
        if ((i & 24576) == 0) {
            function03 = function0;
            i2 |= composerImpl.changedInstance(function03) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            function03 = function0;
        }
        if ((196608 & i) == 0) {
            function04 = function02;
            i2 |= composerImpl.changedInstance(function04) ? 131072 : 65536;
        } else {
            function04 = function02;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.EnableWidgetDialog (EnableWidgetDialog.kt:57)");
            }
            composerImpl.startReplaceGroup(-801121081);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl.end(false);
            final Context context = ((View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView)).getContext();
            Boolean boolValueOf = Boolean.valueOf(z2);
            composerImpl.startReplaceGroup(-801116418);
            boolean zChangedInstance = ((i2 & 14) == 4) | composerImpl.changedInstance(systemUIDialogFactory) | composerImpl.changedInstance(context) | ((i2 & 896) == 256) | ((i2 & 7168) == 2048) | ((57344 & i2) == 16384) | ((i2 & 458752) == 131072);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                final String str5 = str4;
                final Function0 function05 = function03;
                final Function0 function06 = function04;
                Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.EnableWidgetDialogKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        Context context2 = context;
                        boolean z3 = z;
                        final MutableState mutableState2 = mutableState;
                        if (z3) {
                            context2.getClass();
                            final String str6 = str3;
                            final String str7 = str5;
                            final Function0 function07 = function05;
                            final Function0 function08 = function06;
                            mutableState2.setValue(SystemUIDialogFactoryExtKt.create$default(systemUIDialogFactory, context2, null, null, new ComposableLambdaImpl(-841731401, true, new Function3() { // from class: com.android.systemui.communal.ui.compose.EnableWidgetDialogKt$EnableWidgetDialog$1$1$1
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                    Composer composer2 = (Composer) obj3;
                                    ((Number) obj4).intValue();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.EnableWidgetDialog.<anonymous>.<anonymous>.<anonymous> (EnableWidgetDialog.kt:67)");
                                    }
                                    EnableWidgetDialogKt.DialogComposable(str6, str7, function07, function08, composer2, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }), 30));
                            ComponentSystemUIDialog componentSystemUIDialog = (ComponentSystemUIDialog) mutableState2.getValue();
                            if (componentSystemUIDialog != null) {
                                componentSystemUIDialog.setCancelable(true);
                                componentSystemUIDialog.setCanceledOnTouchOutside(true);
                                componentSystemUIDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.communal.ui.compose.EnableWidgetDialogKt$EnableWidgetDialog$1$1$2$1
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        function08.invoke();
                                    }
                                });
                                componentSystemUIDialog.show();
                            }
                        }
                        return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.EnableWidgetDialogKt$EnableWidgetDialog$lambda$6$lambda$5$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                MutableState mutableState3 = mutableState2;
                                ComponentSystemUIDialog componentSystemUIDialog2 = (ComponentSystemUIDialog) mutableState3.getValue();
                                if (componentSystemUIDialog2 != null) {
                                    componentSystemUIDialog2.dismiss();
                                }
                                mutableState3.setValue(null);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(function1);
                objRememberedValue2 = function1;
            }
            composerImpl.end(false);
            EffectsKt.DisposableEffect(boolValueOf, (Function1) objRememberedValue2, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.EnableWidgetDialogKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    EnableWidgetDialogKt.EnableWidgetDialog(z, systemUIDialogFactory, str, str2, function0, function02, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
