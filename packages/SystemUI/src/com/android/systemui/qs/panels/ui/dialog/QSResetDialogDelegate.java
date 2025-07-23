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
import com.android.systemui.dialog.ui.composable.AlertDialogContentKt;
import com.android.systemui.qs.panels.domain.interactor.EditTilesResetInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.util.Assert;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSResetDialogDelegate implements SystemUIDialog.Delegate {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ComponentSystemUIDialog currentDialog;
    public final EditTilesResetInteractor resetInteractor;
    public final ShadeDialogContextInteractor shadeDialogContextInteractor;
    public final SystemUIDialogFactory sysuiDialogFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            AlertDialogContentKt.AlertDialogContent(ComposableSingletons$QSResetDialogDelegateKt.f100lambda1, ComposableSingletons$QSResetDialogDelegateKt.f101lambda2, null, ComposableLambdaKt.rememberComposableLambda(-1065843285, new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0049, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r11, java.lang.Object r12) {
                    /*
                        r10 = this;
                        androidx.compose.runtime.Composer r11 = (androidx.compose.runtime.Composer) r11
                        java.lang.Number r12 = (java.lang.Number) r12
                        int r12 = r12.intValue()
                        r12 = r12 & 3
                        r0 = 2
                        if (r12 != r0) goto L1b
                        r12 = r11
                        androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
                        boolean r0 = r12.getSkipping()
                        if (r0 != 0) goto L17
                        goto L1b
                    L17:
                        r12.skipToGroupEnd()
                        goto L75
                    L1b:
                        boolean r12 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r12 == 0) goto L26
                        java.lang.String r12 = "com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.ResetConfirmationDialog.<anonymous> (QSResetDialogDelegate.kt:81)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r12)
                    L26:
                        r7 = r11
                        androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
                        r11 = -1303510231(0xffffffffb24e0329, float:-1.1991497E-8)
                        r7.startReplaceGroup(r11)
                        com.android.systemui.statusbar.phone.SystemUIDialog r11 = com.android.systemui.statusbar.phone.SystemUIDialog.this
                        boolean r12 = r7.changedInstance(r11)
                        com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate r10 = r2
                        boolean r0 = r7.changedInstance(r10)
                        r12 = r12 | r0
                        java.lang.Object r0 = r7.rememberedValue()
                        if (r12 != 0) goto L4b
                        androidx.compose.runtime.Composer$Companion r12 = androidx.compose.runtime.Composer.Companion
                        r12.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r12 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r0 != r12) goto L53
                    L4b:
                        com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$1$$ExternalSyntheticLambda0 r0 = new com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$1$$ExternalSyntheticLambda0
                        r0.<init>()
                        r7.updateRememberedValue(r0)
                    L53:
                        kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                        r10 = 0
                        r7.end(r10)
                        com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt r10 = com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.INSTANCE
                        r10.getClass()
                        androidx.compose.runtime.internal.ComposableLambdaImpl r6 = com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.f102lambda3
                        r8 = 1572864(0x180000, float:2.204052E-39)
                        r9 = 62
                        r1 = 0
                        r2 = 0
                        r3 = 0
                        r4 = 0
                        r5 = 0
                        com.android.compose.PlatformButtonsKt.PlatformButton(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
                        boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r10 == 0) goto L75
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L75:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(-762955894, new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$2
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0042, code lost:
                
                    if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r8, java.lang.Object r9) {
                    /*
                        r7 = this;
                        androidx.compose.runtime.Composer r8 = (androidx.compose.runtime.Composer) r8
                        java.lang.Number r9 = (java.lang.Number) r9
                        int r9 = r9.intValue()
                        r9 = r9 & 3
                        r0 = 2
                        if (r9 != r0) goto L1b
                        r9 = r8
                        androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                        boolean r0 = r9.getSkipping()
                        if (r0 != 0) goto L17
                        goto L1b
                    L17:
                        r9.skipToGroupEnd()
                        goto L6b
                    L1b:
                        boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r9 == 0) goto L26
                        java.lang.String r9 = "com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.ResetConfirmationDialog.<anonymous> (QSResetDialogDelegate.kt:91)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r9)
                    L26:
                        r5 = r8
                        androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
                        r8 = -1303500435(0xffffffffb24e296d, float:-1.2000197E-8)
                        r5.startReplaceGroup(r8)
                        com.android.systemui.statusbar.phone.SystemUIDialog r7 = com.android.systemui.statusbar.phone.SystemUIDialog.this
                        boolean r8 = r5.changedInstance(r7)
                        java.lang.Object r9 = r5.rememberedValue()
                        if (r8 != 0) goto L44
                        androidx.compose.runtime.Composer$Companion r8 = androidx.compose.runtime.Composer.Companion
                        r8.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r8 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r9 != r8) goto L4c
                    L44:
                        com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$2$$ExternalSyntheticLambda0 r9 = new com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$2$$ExternalSyntheticLambda0
                        r9.<init>()
                        r5.updateRememberedValue(r9)
                    L4c:
                        r0 = r9
                        kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                        r7 = 0
                        r5.end(r7)
                        com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt r7 = com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.INSTANCE
                        r7.getClass()
                        androidx.compose.runtime.internal.ComposableLambdaImpl r4 = com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.f103lambda4
                        r1 = 0
                        r6 = 24576(0x6000, float:3.4438E-41)
                        r2 = 0
                        r3 = 0
                        com.android.compose.PlatformButtonsKt.PlatformTextButton(r0, r1, r2, r3, r4, r5, r6)
                        boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r7 == 0) goto L6b
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L6b:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$ResetConfirmationDialog$2.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), null, composerImpl, 221238, 76);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int i3 = QSResetDialogDelegate.$r8$clinit;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    QSResetDialogDelegate.this.ResetConfirmationDialog(systemUIDialog, (Composer) obj, updateChangedFlags);
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
        ComponentSystemUIDialog create$default = SystemUIDialogFactoryExtKt.create$default(this.sysuiDialogFactory, ((ShadeDialogContextInteractorImpl) this.shadeDialogContextInteractor).getContext(), null, null, new ComposableLambdaImpl(423726146, true, new Function3() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$createDialog$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate.createDialog.<anonymous> (QSResetDialogDelegate.kt:58)");
                }
                int i = QSResetDialogDelegate.$r8$clinit;
                QSResetDialogDelegate.this.ResetConfirmationDialog(systemUIDialog, composer, intValue & 14);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 30);
        create$default.getLifecycleRegistry$1().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate$createDialog$2$1
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public final void onStop$1() {
                Assert.isMainThread();
                QSResetDialogDelegate.this.currentDialog = null;
            }
        });
        this.currentDialog = create$default;
        return create$default;
    }
}
