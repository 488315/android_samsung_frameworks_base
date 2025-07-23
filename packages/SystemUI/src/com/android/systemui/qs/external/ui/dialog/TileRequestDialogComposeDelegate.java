package com.android.systemui.qs.external.ui.dialog;

import android.content.DialogInterface;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.dialog.ui.composable.AlertDialogContentKt;
import com.android.systemui.qs.external.TileData;
import com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileRequestDialogComposeDelegate implements SystemUIDialog.Delegate {
    public final DialogInterface.OnClickListener dialogListener;
    public final SystemUIDialogFactory sysuiDialogFactory;
    public final TileData tileData;
    public final TileRequestDialogViewModel.Factory tileRequestDialogViewModelFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(1079894543, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.<anonymous> (TileRequestDialogComposeDelegate.kt:64)");
                    }
                    ComposableSingletons$TileRequestDialogComposeDelegateKt.INSTANCE.getClass();
                    ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$TileRequestDialogComposeDelegateKt.f93lambda1;
                    final TileRequestDialogComposeDelegate tileRequestDialogComposeDelegate = TileRequestDialogComposeDelegate.this;
                    final SystemUIDialog systemUIDialog2 = systemUIDialog;
                    AlertDialogContentKt.AlertDialogContent(composableLambdaImpl, ComposableLambdaKt.rememberComposableLambda(-1188039156, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1.1
                        /* JADX WARN: Code restructure failed: missing block: B:24:0x00bd, code lost:
                        
                            if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L26;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r31, java.lang.Object r32) {
                            /*
                                Method dump skipped, instructions count: 362
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2), null, ComposableLambdaKt.rememberComposableLambda(-90655063, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1.2
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
                                goto L76
                            L1b:
                                boolean r12 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r12 == 0) goto L26
                                java.lang.String r12 = "com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.<anonymous>.<anonymous> (TileRequestDialogComposeDelegate.kt:98)"
                                androidx.compose.runtime.ComposerKt.traceEventStart(r12)
                            L26:
                                r7 = r11
                                androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
                                r11 = 754677184(0x2cfb75c0, float:7.146922E-12)
                                r7.startReplaceGroup(r11)
                                com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate r11 = com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.this
                                boolean r12 = r7.changedInstance(r11)
                                com.android.systemui.statusbar.phone.SystemUIDialog r10 = r2
                                boolean r0 = r7.changedInstance(r10)
                                r12 = r12 | r0
                                java.lang.Object r0 = r7.rememberedValue()
                                if (r12 != 0) goto L4b
                                androidx.compose.runtime.Composer$Companion r12 = androidx.compose.runtime.Composer.Companion
                                r12.getClass()
                                androidx.compose.runtime.Composer$Companion$Empty$1 r12 = androidx.compose.runtime.Composer.Companion.Empty
                                if (r0 != r12) goto L54
                            L4b:
                                com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0 r0 = new com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0
                                r12 = 1
                                r0.<init>(r11, r10, r12)
                                r7.updateRememberedValue(r0)
                            L54:
                                kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                r10 = 0
                                r7.end(r10)
                                com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt r10 = com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt.INSTANCE
                                r10.getClass()
                                androidx.compose.runtime.internal.ComposableLambdaImpl r6 = com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt.f94lambda2
                                r8 = 1572864(0x180000, float:2.204052E-39)
                                r9 = 62
                                r1 = 0
                                r2 = 0
                                r3 = 0
                                r4 = 0
                                r5 = 0
                                com.android.compose.PlatformButtonsKt.PlatformButton(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
                                boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r10 == 0) goto L76
                                androidx.compose.runtime.ComposerKt.traceEventEnd()
                            L76:
                                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                                return r10
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1.AnonymousClass2.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2), ComposableLambdaKt.rememberComposableLambda(1706795400, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1.3
                        /* JADX WARN: Code restructure failed: missing block: B:15:0x0049, code lost:
                        
                            if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r9, java.lang.Object r10) {
                            /*
                                r8 = this;
                                androidx.compose.runtime.Composer r9 = (androidx.compose.runtime.Composer) r9
                                java.lang.Number r10 = (java.lang.Number) r10
                                int r10 = r10.intValue()
                                r10 = r10 & 3
                                r0 = 2
                                if (r10 != r0) goto L1b
                                r10 = r9
                                androidx.compose.runtime.ComposerImpl r10 = (androidx.compose.runtime.ComposerImpl) r10
                                boolean r0 = r10.getSkipping()
                                if (r0 != 0) goto L17
                                goto L1b
                            L17:
                                r10.skipToGroupEnd()
                                goto L73
                            L1b:
                                boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r10 == 0) goto L26
                                java.lang.String r10 = "com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.TileRequestDialogContent.<anonymous>.<anonymous> (TileRequestDialogComposeDelegate.kt:108)"
                                androidx.compose.runtime.ComposerKt.traceEventStart(r10)
                            L26:
                                r6 = r9
                                androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
                                r9 = 754690272(0x2cfba8e0, float:7.152598E-12)
                                r6.startReplaceGroup(r9)
                                com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate r9 = com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.this
                                boolean r10 = r6.changedInstance(r9)
                                com.android.systemui.statusbar.phone.SystemUIDialog r8 = r2
                                boolean r0 = r6.changedInstance(r8)
                                r10 = r10 | r0
                                java.lang.Object r0 = r6.rememberedValue()
                                if (r10 != 0) goto L4b
                                androidx.compose.runtime.Composer$Companion r10 = androidx.compose.runtime.Composer.Companion
                                r10.getClass()
                                androidx.compose.runtime.Composer$Companion$Empty$1 r10 = androidx.compose.runtime.Composer.Companion.Empty
                                if (r0 != r10) goto L54
                            L4b:
                                com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0 r0 = new com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0
                                r10 = 2
                                r0.<init>(r9, r8, r10)
                                r6.updateRememberedValue(r0)
                            L54:
                                kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                r8 = 0
                                r6.end(r8)
                                com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt r8 = com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt.INSTANCE
                                r8.getClass()
                                androidx.compose.runtime.internal.ComposableLambdaImpl r5 = com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt.f95lambda3
                                r2 = 0
                                r7 = 196608(0x30000, float:2.75506E-40)
                                r1 = 0
                                r3 = 0
                                r4 = 0
                                com.android.compose.PlatformButtonsKt.PlatformOutlinedButton(r0, r1, r2, r3, r4, r5, r6, r7)
                                boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r8 == 0) goto L73
                                androidx.compose.runtime.ComposerKt.traceEventEnd()
                            L73:
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                return r8
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$TileRequestDialogContent$1.AnonymousClass3.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2), null, composer2, 221238, 76);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    TileRequestDialogComposeDelegate.this.TileRequestDialogContent(systemUIDialog, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        return SystemUIDialogFactoryExtKt.create$default(this.sysuiDialogFactory, null, null, null, new ComposableLambdaImpl(1233252354, true, new Function3() { // from class: com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate$createDialog$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate.createDialog.<anonymous> (TileRequestDialogComposeDelegate.kt:58)");
                }
                TileRequestDialogComposeDelegate.this.TileRequestDialogContent(systemUIDialog, composer, intValue & 14);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 31);
    }
}
