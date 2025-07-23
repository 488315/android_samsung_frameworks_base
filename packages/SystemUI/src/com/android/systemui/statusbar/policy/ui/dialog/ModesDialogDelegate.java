package com.android.systemui.statusbar.policy.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.DefaultLifecycleObserver;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSModesEvent;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.util.Assert;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ModesDialogDelegate implements SystemUIDialog.Delegate {
    public static final Intent ZEN_MODE_SETTINGS_INTENT;
    public final ActivityStarter activityStarter;
    public final CoroutineScope applicationCoroutineScope;
    public final CoroutineContext bgContext;
    public final Context context;
    public ComponentSystemUIDialog currentDialog;
    public final ModesDialogEventLogger dialogEventLogger;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final Lazy dndDurationDialogLogger$delegate = LazyKt__LazyJVMKt.lazy(new ModesDialogDelegate$$ExternalSyntheticLambda0(this, 0));
    public final CoroutineContext mainCoroutineContext;
    public final ShadeDialogContextInteractor shadeDisplayContextRepository;
    public final SystemUIDialogFactory sysuiDialogFactory;
    public final Provider viewModel;

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
        ZEN_MODE_SETTINGS_INTENT = new Intent("android.settings.ZEN_MODE_SETTINGS");
    }

    public ModesDialogDelegate(Context context, SystemUIDialogFactory systemUIDialogFactory, DialogTransitionAnimator dialogTransitionAnimator, ActivityStarter activityStarter, Provider provider, ModesDialogEventLogger modesDialogEventLogger, CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineContext coroutineContext2, ShadeDialogContextInteractor shadeDialogContextInteractor) {
        this.context = context;
        this.sysuiDialogFactory = systemUIDialogFactory;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.activityStarter = activityStarter;
        this.viewModel = provider;
        this.dialogEventLogger = modesDialogEventLogger;
        this.applicationCoroutineScope = coroutineScope;
        this.mainCoroutineContext = coroutineContext;
        this.bgContext = coroutineContext2;
        this.shadeDisplayContextRepository = shadeDialogContextInteractor;
    }

    public final void ModesDialogContent(final SystemUIDialog systemUIDialog, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-134546816);
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
                ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent (ModesDialogDelegate.kt:114)");
            }
            boolean isSystemInDarkTheme = DarkThemeKt.isSystemInDarkTheme(composerImpl);
            composerImpl.startReplaceGroup(-234256559);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = Boolean.valueOf(isSystemInDarkTheme);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            boolean booleanValue = ((Boolean) rememberedValue).booleanValue();
            composerImpl.end(false);
            PlatformThemeKt.PlatformTheme(booleanValue, ComposableLambdaKt.rememberComposableLambda(-1782855818, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0045, code lost:
                
                    if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r10, java.lang.Object r11) {
                    /*
                        r9 = this;
                        androidx.compose.runtime.Composer r10 = (androidx.compose.runtime.Composer) r10
                        java.lang.Number r11 = (java.lang.Number) r11
                        int r11 = r11.intValue()
                        r11 = r11 & 3
                        r0 = 2
                        if (r11 != r0) goto L1c
                        r11 = r10
                        androidx.compose.runtime.ComposerImpl r11 = (androidx.compose.runtime.ComposerImpl) r11
                        boolean r0 = r11.getSkipping()
                        if (r0 != 0) goto L17
                        goto L1c
                    L17:
                        r11.skipToGroupEnd()
                        goto L98
                    L1c:
                        boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r11 == 0) goto L27
                        java.lang.String r11 = "com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.<anonymous> (ModesDialogDelegate.kt:122)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r11)
                    L27:
                        androidx.compose.ui.Modifier$Companion r11 = androidx.compose.ui.Modifier.Companion
                        r6 = r10
                        androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
                        r10 = -1489003334(0xffffffffa73f9cba, float:-2.6591536E-15)
                        r6.startReplaceGroup(r10)
                        com.android.systemui.statusbar.phone.SystemUIDialog r10 = com.android.systemui.statusbar.phone.SystemUIDialog.this
                        boolean r0 = r6.changedInstance(r10)
                        java.lang.Object r1 = r6.rememberedValue()
                        if (r0 != 0) goto L47
                        androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                        r0.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r1 != r0) goto L4f
                    L47:
                        com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$$ExternalSyntheticLambda0 r1 = new com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$$ExternalSyntheticLambda0
                        r1.<init>()
                        r6.updateRememberedValue(r1)
                    L4f:
                        kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
                        r0 = 0
                        r6.end(r0)
                        androidx.compose.ui.Modifier r2 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r11, r0, r1)
                        com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt r11 = com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt.INSTANCE
                        r11.getClass()
                        androidx.compose.runtime.internal.ComposableLambdaImpl r0 = com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt.f109lambda1
                        com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$2 r11 = new com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$2
                        com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate r9 = r2
                        r11.<init>()
                        r1 = 129037721(0x7b0f599, float:2.6625886E-34)
                        androidx.compose.runtime.internal.ComposableLambdaImpl r1 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r1, r11, r6)
                        com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$3 r11 = new com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$3
                        r11.<init>()
                        r3 = -854074596(0xffffffffcd17db1c, float:-1.5923245E8)
                        androidx.compose.runtime.internal.ComposableLambdaImpl r3 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r3, r11, r6)
                        com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$4 r11 = new com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$4
                        r11.<init>()
                        r9 = -77827042(0xfffffffffb5c741e, float:-1.14466045E36)
                        androidx.compose.runtime.internal.ComposableLambdaImpl r5 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r9, r11, r6)
                        r7 = 1597494(0x186036, float:2.238566E-39)
                        r8 = 40
                        r4 = 0
                        com.android.systemui.dialog.ui.composable.AlertDialogContentKt.AlertDialogContent(r0, r1, r2, r3, r4, r5, r6, r7, r8)
                        boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r9 == 0) goto L98
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L98:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 54, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    Intent intent = ModesDialogDelegate.ZEN_MODE_SETTINGS_INTENT;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ModesDialogDelegate.this.ModesDialogContent(systemUIDialog, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        Assert.isMainThread();
        if (this.currentDialog != null) {
            Log.w("ModesDialogDelegate", "Dialog is already open, dismissing it and creating a new one.");
            ComponentSystemUIDialog componentSystemUIDialog = this.currentDialog;
            if (componentSystemUIDialog != null) {
                componentSystemUIDialog.dismiss();
            }
        }
        ComponentSystemUIDialog create$default = SystemUIDialogFactoryExtKt.create$default(this.sysuiDialogFactory, ((ShadeDialogContextInteractorImpl) this.shadeDisplayContextRepository).getContext(), null, null, new ComposableLambdaImpl(-1413719250, true, new Function3() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$createDialog$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.createDialog.<anonymous> (ModesDialogDelegate.kt:97)");
                }
                Intent intent = ModesDialogDelegate.ZEN_MODE_SETTINGS_INTENT;
                ModesDialogDelegate.this.ModesDialogContent(systemUIDialog, composer, intValue & 14);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 30);
        this.currentDialog = create$default;
        create$default.getLifecycleRegistry$1().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$createDialog$2
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public final void onStop$1() {
                Assert.isMainThread();
                ModesDialogDelegate.this.currentDialog = null;
            }
        });
        ComponentSystemUIDialog componentSystemUIDialog2 = this.currentDialog;
        componentSystemUIDialog2.getClass();
        return componentSystemUIDialog2;
    }

    public final void launchFromDialog(Intent intent) {
        BuildersKt.launch$default(this.applicationCoroutineScope, null, null, new ModesDialogDelegate$launchFromDialog$1(this, intent, null), 3);
    }

    public final void openSettings(SystemUIDialog systemUIDialog) {
        this.dialogEventLogger.uiEventLogger.log(QSModesEvent.QS_MODES_SETTINGS);
        DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(systemUIDialog, this.dialogTransitionAnimator);
        if (createActivityTransitionController$default == null) {
            systemUIDialog.dismiss();
        }
        this.activityStarter.startActivity(ZEN_MODE_SETTINGS_INTENT, true, (ActivityTransitionAnimator.Controller) createActivityTransitionController$default);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object showDialog(com.android.systemui.animation.Expandable r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$1 r0 = (com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$1 r0 = new com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate r4 = (com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L49
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$2 r6 = new com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$2
            r2 = 0
            r6.<init>(r4, r5, r2)
            r0.L$0 = r4
            r0.label = r3
            kotlin.coroutines.CoroutineContext r5 = r4.mainCoroutineContext
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r0)
            if (r5 != r1) goto L49
            return r1
        L49:
            com.android.systemui.statusbar.phone.ComponentSystemUIDialog r4 = r4.currentDialog
            r4.getClass()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.showDialog(com.android.systemui.animation.Expandable, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static /* synthetic */ void getCurrentDialog$annotations() {
    }
}
