package com.android.systemui.qs.panels.ui.dialog;

import android.util.Log;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.DefaultLifecycleObserver;
import com.android.compose.PlatformButtonsKt;
import com.android.systemui.dialog.ui.composable.AlertDialogContentKt;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepositoryImpl;
import com.android.systemui.qs.panels.domain.interactor.EditTilesResetInteractor;
import com.android.systemui.qs.panels.domain.interactor.IconTilesInteractor;
import com.android.systemui.qs.panels.domain.interactor.SizedTilesResetInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.util.Assert;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class QSResetDialogDelegate implements SystemUIDialog.Delegate {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ComponentSystemUIDialog currentDialog;
    public final EditTilesResetInteractor resetInteractor;
    public final ShadeDialogContextInteractor shadeDialogContextInteractor;
    public final SystemUIDialogFactory sysuiDialogFactory;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public QSResetDialogDelegate(SystemUIDialogFactory systemUIDialogFactory, ShadeDialogContextInteractor shadeDialogContextInteractor, EditTilesResetInteractor editTilesResetInteractor) {
        this.sysuiDialogFactory = systemUIDialogFactory;
        this.shadeDialogContextInteractor = shadeDialogContextInteractor;
        this.resetInteractor = editTilesResetInteractor;
    }

    public final void ResetConfirmationDialog(final SystemUIDialog systemUIDialog, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1857595153);
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.ResetConfirmationDialog (QSResetDialogDelegate.kt:74)");
            }
            ComposableSingletons$QSResetDialogDelegateKt.INSTANCE.getClass();
            AlertDialogContentKt.AlertDialogContent(ComposableSingletons$QSResetDialogDelegateKt.f100lambda1, ComposableSingletons$QSResetDialogDelegateKt.f101lambda2, null, ComposableLambdaKt.rememberComposableLambda(-1065843285, new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.ResetConfirmationDialog.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x004b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.ResetConfirmationDialog.<anonymous> (QSResetDialogDelegate.kt:81)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1303510231);
                            final SystemUIDialog systemUIDialog2 = systemUIDialog;
                            boolean zChangedInstance = composerImpl3.changedInstance(systemUIDialog2);
                            final QSResetDialogDelegate qSResetDialogDelegate = this;
                            boolean zChangedInstance2 = zChangedInstance | composerImpl3.changedInstance(qSResetDialogDelegate);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChangedInstance2) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new Function0() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            systemUIDialog2.dismiss();
                                            SizedTilesResetInteractor sizedTilesResetInteractor = (SizedTilesResetInteractor) qSResetDialogDelegate.resetInteractor;
                                            sizedTilesResetInteractor.uiEventLogger.log(QSEditEvent.QS_EDIT_RESET);
                                            sizedTilesResetInteractor.currentTilesInteractor.resetTiles();
                                            IconTilesInteractor iconTilesInteractor = sizedTilesResetInteractor.iconTilesInteractor;
                                            iconTilesInteractor.preferencesInteractor.setLargeTilesSpecs(((DefaultLargeTilesRepositoryImpl) iconTilesInteractor.repo).defaultLargeTiles);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ComposableSingletons$QSResetDialogDelegateKt.INSTANCE.getClass();
                                PlatformButtonsKt.PlatformButton((Function0) objRememberedValue, null, false, null, null, null, ComposableSingletons$QSResetDialogDelegateKt.f102lambda3, composerImpl3, 1572864, 62);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(-762955894, new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.ResetConfirmationDialog.2
                /* JADX WARN: Removed duplicated region for block: B:15:0x0044  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.ResetConfirmationDialog.<anonymous> (QSResetDialogDelegate.kt:91)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1303500435);
                            final SystemUIDialog systemUIDialog2 = systemUIDialog;
                            boolean zChangedInstance = composerImpl3.changedInstance(systemUIDialog2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new Function0() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$2$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            systemUIDialog2.dismiss();
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                ComposableSingletons$QSResetDialogDelegateKt.INSTANCE.getClass();
                                PlatformButtonsKt.PlatformTextButton((Function0) objRememberedValue, null, false, null, ComposableSingletons$QSResetDialogDelegateKt.f103lambda4, composerImpl3, 24576);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), null, composerImpl, 221238, 76);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int i3 = QSResetDialogDelegate.$r8$clinit;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.ResetConfirmationDialog(systemUIDialog, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        Assert.isMainThread();
        if (this.currentDialog != null) {
            Log.d("ResetDialogDelegate", "Dialog is already open, dismissing it and creating a new one.");
            ComponentSystemUIDialog componentSystemUIDialog = this.currentDialog;
            if (componentSystemUIDialog != null) {
                componentSystemUIDialog.dismiss();
            }
        }
        ComponentSystemUIDialog componentSystemUIDialogCreate$default = SystemUIDialogFactoryExtKt.create$default(this.sysuiDialogFactory, ((ShadeDialogContextInteractorImpl) this.shadeDialogContextInteractor).getContext(), null, null, new ComposableLambdaImpl(423726146, true, new Function3() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.createDialog.1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int iIntValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.createDialog.<anonymous> (QSResetDialogDelegate.kt:58)");
                }
                int i = QSResetDialogDelegate.$r8$clinit;
                QSResetDialogDelegate.this.ResetConfirmationDialog(systemUIDialog, composer, iIntValue & 14);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 30);
        componentSystemUIDialogCreate$default.getLifecycleRegistry$1().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$createDialog$2$1
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public final void onStop$1() {
                Assert.isMainThread();
                this.this$0.currentDialog = null;
            }
        });
        this.currentDialog = componentSystemUIDialogCreate$default;
        return componentSystemUIDialogCreate$default;
    }
}
