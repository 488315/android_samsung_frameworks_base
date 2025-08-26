package com.android.systemui.qs.external.ui.dialog;

import android.content.DialogInterface;
import android.content.res.Resources;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
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
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import com.android.compose.PlatformButtonsKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.dialog.ui.composable.AlertDialogContentKt;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.qs.external.TileData;
import com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel;
import com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt;
import com.android.systemui.qs.panels.ui.viewmodel.IconProvider;
import com.android.systemui.qs.panels.ui.viewmodel.TileUiState;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class TileRequestDialogComposeDelegate implements SystemUIDialog.Delegate {
    public final DialogInterface.OnClickListener dialogListener;
    public final SystemUIDialogFactory sysuiDialogFactory;
    public final TileData tileData;
    public final TileRequestDialogViewModel.Factory tileRequestDialogViewModelFactory;

    public interface Factory {
    }

    public TileRequestDialogComposeDelegate(SystemUIDialogFactory systemUIDialogFactory, TileRequestDialogViewModel.Factory factory, TileData tileData, DialogInterface.OnClickListener onClickListener) {
        this.sysuiDialogFactory = systemUIDialogFactory;
        this.tileRequestDialogViewModelFactory = factory;
        this.tileData = tileData;
        this.dialogListener = onClickListener;
    }

    public final void TileRequestDialogContent(final SystemUIDialog systemUIDialog, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2069960901);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(systemUIDialog) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent (TileRequestDialogComposeDelegate.kt:62)");
            }
            PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(1079894543, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.<anonymous> (TileRequestDialogComposeDelegate.kt:64)");
                            }
                            ComposableSingletons$TileRequestDialogComposeDelegateKt.INSTANCE.getClass();
                            ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$TileRequestDialogComposeDelegateKt.f93lambda1;
                            final TileRequestDialogComposeDelegate tileRequestDialogComposeDelegate = TileRequestDialogComposeDelegate.this;
                            final SystemUIDialog systemUIDialog2 = systemUIDialog;
                            AlertDialogContentKt.AlertDialogContent(composableLambdaImpl, ComposableLambdaKt.rememberComposableLambda(-1188039156, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.1.1
                                /* JADX WARN: Removed duplicated region for block: B:26:0x00bf  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4) throws Resources.NotFoundException {
                                    Composer composer3 = (Composer) obj3;
                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.<anonymous>.<anonymous> (TileRequestDialogComposeDelegate.kt:67)");
                                            }
                                            Alignment.Companion.getClass();
                                            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                            Arrangement arrangement = Arrangement.INSTANCE;
                                            Dp.Companion companion = Dp.Companion;
                                            arrangement.getClass();
                                            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(16);
                                            Modifier.Companion companion2 = Modifier.Companion;
                                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, horizontal, composer3, 54);
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, companion2);
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
                                            Updater.m337setimpl(composer3, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                            Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                            }
                                            Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                            TileRequestDialogComposeDelegate tileRequestDialogComposeDelegate2 = tileRequestDialogComposeDelegate;
                                            TileData tileData = tileRequestDialogComposeDelegate2.tileData;
                                            composerImpl4.startReplaceGroup(1505794093);
                                            boolean zChangedInstance = composerImpl4.changedInstance(tileRequestDialogComposeDelegate2);
                                            SystemUIDialog systemUIDialog3 = systemUIDialog2;
                                            boolean zChangedInstance2 = zChangedInstance | composerImpl4.changedInstance(systemUIDialog3);
                                            Object objRememberedValue = composerImpl4.rememberedValue();
                                            if (!zChangedInstance2) {
                                                Composer.Companion.getClass();
                                                if (objRememberedValue == Composer.Companion.Empty) {
                                                    objRememberedValue = new TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0(tileRequestDialogComposeDelegate2, systemUIDialog3, 0);
                                                    composerImpl4.updateRememberedValue(objRememberedValue);
                                                }
                                                composerImpl4.end(false);
                                                TileRequestDialogViewModel tileRequestDialogViewModel = (TileRequestDialogViewModel) SysUiViewModelKt.rememberViewModel("TileRequestDialog", tileData, (Function0) objRememberedValue, composer3, 6, 0);
                                                String strStringResource = StringResources_androidKt.stringResource(R.string.qs_tile_request_dialog_text, new Object[]{tileRequestDialogComposeDelegate2.tileData.appName}, composer3);
                                                TextAlign.Companion.getClass();
                                                TextKt.m317Text4IGK_g(strStringResource, null, 0L, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(TextAlign.Start), 0L, 0, false, 0, 0, null, null, composer3, 0, 0, 130558);
                                                TileKt.LargeStaticTile((TileUiState) tileRequestDialogViewModel.uiState$delegate.getValue(), (IconProvider) tileRequestDialogViewModel.iconProvider$delegate.getValue(), SizeKt.m144width3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_service_request_tile_width, composer3)), composer3, 0);
                                                composerImpl4.end(true);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), null, ComposableLambdaKt.rememberComposableLambda(-90655063, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.1.2
                                /* JADX WARN: Removed duplicated region for block: B:15:0x004b  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4) {
                                    Composer composer3 = (Composer) obj3;
                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.<anonymous>.<anonymous> (TileRequestDialogComposeDelegate.kt:98)");
                                            }
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            composerImpl4.startReplaceGroup(754677184);
                                            TileRequestDialogComposeDelegate tileRequestDialogComposeDelegate2 = tileRequestDialogComposeDelegate;
                                            boolean zChangedInstance = composerImpl4.changedInstance(tileRequestDialogComposeDelegate2);
                                            SystemUIDialog systemUIDialog3 = systemUIDialog2;
                                            boolean zChangedInstance2 = zChangedInstance | composerImpl4.changedInstance(systemUIDialog3);
                                            Object objRememberedValue = composerImpl4.rememberedValue();
                                            if (!zChangedInstance2) {
                                                Composer.Companion.getClass();
                                                if (objRememberedValue == Composer.Companion.Empty) {
                                                    objRememberedValue = new TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0(tileRequestDialogComposeDelegate2, systemUIDialog3, 1);
                                                    composerImpl4.updateRememberedValue(objRememberedValue);
                                                }
                                                composerImpl4.end(false);
                                                ComposableSingletons$TileRequestDialogComposeDelegateKt.INSTANCE.getClass();
                                                PlatformButtonsKt.PlatformButton((Function0) objRememberedValue, null, false, null, null, null, ComposableSingletons$TileRequestDialogComposeDelegateKt.f94lambda2, composerImpl4, 1572864, 62);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), ComposableLambdaKt.rememberComposableLambda(1706795400, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.1.3
                                /* JADX WARN: Removed duplicated region for block: B:15:0x004b  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4) {
                                    Composer composer3 = (Composer) obj3;
                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.<anonymous>.<anonymous> (TileRequestDialogComposeDelegate.kt:108)");
                                            }
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            composerImpl4.startReplaceGroup(754690272);
                                            TileRequestDialogComposeDelegate tileRequestDialogComposeDelegate2 = tileRequestDialogComposeDelegate;
                                            boolean zChangedInstance = composerImpl4.changedInstance(tileRequestDialogComposeDelegate2);
                                            SystemUIDialog systemUIDialog3 = systemUIDialog2;
                                            boolean zChangedInstance2 = zChangedInstance | composerImpl4.changedInstance(systemUIDialog3);
                                            Object objRememberedValue = composerImpl4.rememberedValue();
                                            if (!zChangedInstance2) {
                                                Composer.Companion.getClass();
                                                if (objRememberedValue == Composer.Companion.Empty) {
                                                    objRememberedValue = new TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0(tileRequestDialogComposeDelegate2, systemUIDialog3, 2);
                                                    composerImpl4.updateRememberedValue(objRememberedValue);
                                                }
                                                composerImpl4.end(false);
                                                ComposableSingletons$TileRequestDialogComposeDelegateKt.INSTANCE.getClass();
                                                PlatformButtonsKt.PlatformOutlinedButton((Function0) objRememberedValue, null, false, null, null, ComposableSingletons$TileRequestDialogComposeDelegateKt.f95lambda3, composerImpl4, 196608);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), null, composer2, 221238, 76);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.TileRequestDialogContent(systemUIDialog, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        return SystemUIDialogFactoryExtKt.create$default(this.sysuiDialogFactory, null, null, null, new ComposableLambdaImpl(1233252354, true, new Function3() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.createDialog.1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int iIntValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.createDialog.<anonymous> (TileRequestDialogComposeDelegate.kt:58)");
                }
                TileRequestDialogComposeDelegate.this.TileRequestDialogContent(systemUIDialog, composer, iIntValue & 14);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 31);
    }
}
