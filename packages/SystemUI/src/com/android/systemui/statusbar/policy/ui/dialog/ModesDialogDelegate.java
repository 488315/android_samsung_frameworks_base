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
import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.lifecycle.DefaultLifecycleObserver;
import com.android.compose.PlatformButtonsKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.dialog.ui.composable.AlertDialogContentKt;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSModesEvent;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt;
import com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel;
import com.android.systemui.util.Assert;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$launchFromDialog$1, reason: invalid class name and case insensitive filesystem */
    final class C11131 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        int label;

        /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$launchFromDialog$1$1, reason: invalid class name and collision with other inner class name */
        final class C06011 extends SuspendLambda implements Function2 {
            final /* synthetic */ Intent $intent;
            int label;
            final /* synthetic */ ModesDialogDelegate this$0;

            /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$launchFromDialog$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C06021 extends SuspendLambda implements Function2 {
                final /* synthetic */ Intent $intent;
                int label;
                final /* synthetic */ ModesDialogDelegate this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C06021(ModesDialogDelegate modesDialogDelegate, Intent intent, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = modesDialogDelegate;
                    this.$intent = intent;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C06021(this.this$0, this.$intent, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C06021) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    ComponentSystemUIDialog componentSystemUIDialog;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    ModesDialogDelegate modesDialogDelegate = this.this$0;
                    Intent intent = this.$intent;
                    Intent intent2 = ModesDialogDelegate.ZEN_MODE_SETTINGS_INTENT;
                    modesDialogDelegate.getClass();
                    Assert.isMainThread();
                    if (modesDialogDelegate.currentDialog == null) {
                        Log.w("ModesDialogDelegate", "Cannot launch from dialog, the dialog is not present. Will launch activity without animating.");
                    }
                    ComponentSystemUIDialog componentSystemUIDialog2 = modesDialogDelegate.currentDialog;
                    DialogTransitionAnimator.AnonymousClass1 anonymousClass1CreateActivityTransitionController$default = componentSystemUIDialog2 != null ? DialogTransitionAnimator.createActivityTransitionController$default(componentSystemUIDialog2, modesDialogDelegate.dialogTransitionAnimator) : null;
                    if (anonymousClass1CreateActivityTransitionController$default == null && (componentSystemUIDialog = modesDialogDelegate.currentDialog) != null) {
                        componentSystemUIDialog.dismiss();
                    }
                    modesDialogDelegate.activityStarter.startActivity(intent, true, (ActivityTransitionAnimator.Controller) anonymousClass1CreateActivityTransitionController$default);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06011(ModesDialogDelegate modesDialogDelegate, Intent intent, Continuation continuation) {
                super(2, continuation);
                this.this$0 = modesDialogDelegate;
                this.$intent = intent;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C06011(this.this$0, this.$intent, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06011) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ModesDialogDelegate modesDialogDelegate = this.this$0;
                    CoroutineContext coroutineContext = modesDialogDelegate.mainCoroutineContext;
                    C06021 c06021 = new C06021(modesDialogDelegate, this.$intent, null);
                    this.label = 1;
                    if (BuildersKt.withContext(coroutineContext, c06021, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11131(Intent intent, Continuation continuation) {
            super(2, continuation);
            this.$intent = intent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ModesDialogDelegate.this.new C11131(this.$intent, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11131) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ModesDialogDelegate modesDialogDelegate = ModesDialogDelegate.this;
                CoroutineContext coroutineContext = modesDialogDelegate.bgContext;
                C06011 c06011 = new C06011(modesDialogDelegate, this.$intent, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineContext, c06011, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$1, reason: invalid class name and case insensitive filesystem */
    final class C11141 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C11141(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ModesDialogDelegate.this.showDialog(null, this);
        }
    }

    /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$showDialog$2, reason: invalid class name and case insensitive filesystem */
    final class C11152 extends SuspendLambda implements Function2 {
        final /* synthetic */ Expandable $expandable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11152(Expandable expandable, Continuation continuation) {
            super(2, continuation);
            this.$expandable = expandable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ModesDialogDelegate.this.new C11152(this.$expandable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11152) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            DialogTransitionAnimator.Controller controllerDialogTransitionController;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ModesDialogDelegate modesDialogDelegate = ModesDialogDelegate.this;
            if (modesDialogDelegate.currentDialog == null) {
                modesDialogDelegate.createDialog();
            }
            Expandable expandable = this.$expandable;
            if (expandable == null || (controllerDialogTransitionController = expandable.dialogTransitionController(new DialogCuj(58, "configure_priority_modes"))) == null) {
                ComponentSystemUIDialog componentSystemUIDialog = ModesDialogDelegate.this.currentDialog;
                componentSystemUIDialog.getClass();
                componentSystemUIDialog.show();
            } else {
                ModesDialogDelegate modesDialogDelegate2 = ModesDialogDelegate.this;
                DialogTransitionAnimator dialogTransitionAnimator = modesDialogDelegate2.dialogTransitionAnimator;
                ComponentSystemUIDialog componentSystemUIDialog2 = modesDialogDelegate2.currentDialog;
                componentSystemUIDialog2.getClass();
                TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                dialogTransitionAnimator.show(componentSystemUIDialog2, controllerDialogTransitionController, false);
            }
            return Unit.INSTANCE;
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
            boolean zIsSystemInDarkTheme = DarkThemeKt.isSystemInDarkTheme(composerImpl);
            composerImpl.startReplaceGroup(-234256559);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = Boolean.valueOf(zIsSystemInDarkTheme);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            boolean zBooleanValue = ((Boolean) objRememberedValue).booleanValue();
            composerImpl.end(false);
            PlatformThemeKt.PlatformTheme(zBooleanValue, ComposableLambdaKt.rememberComposableLambda(-1782855818, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
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
                                ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.<anonymous> (ModesDialogDelegate.kt:122)");
                            }
                            Modifier.Companion companion = Modifier.Companion;
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1489003334);
                            final SystemUIDialog systemUIDialog2 = systemUIDialog;
                            boolean zChangedInstance = composerImpl3.changedInstance(systemUIDialog2);
                            Object objRememberedValue2 = composerImpl3.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    objRememberedValue2 = new Function1() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj3) {
                                            SemanticsPropertiesKt.setPaneTitle((SemanticsPropertyReceiver) obj3, systemUIDialog2.getContext().getString(R.string.accessibility_desc_quick_settings));
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue2);
                                }
                                composerImpl3.end(false);
                                Modifier modifierSemantics = SemanticsModifierKt.semantics(companion, false, (Function1) objRememberedValue2);
                                ComposableSingletons$ModesDialogDelegateKt.INSTANCE.getClass();
                                ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$ModesDialogDelegateKt.f109lambda1;
                                final ModesDialogDelegate modesDialogDelegate = this;
                                AlertDialogContentKt.AlertDialogContent(composableLambdaImpl, ComposableLambdaKt.rememberComposableLambda(129037721, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.1.2
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        Composer composer3 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.<anonymous>.<anonymous> (ModesDialogDelegate.kt:135)");
                                                }
                                                ModeTileGridKt.ModeTileGrid((ModesDialogViewModel) modesDialogDelegate.viewModel.get(), composer3, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl3), modifierSemantics, ComposableLambdaKt.rememberComposableLambda(-854074596, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.1.3
                                    /* JADX WARN: Removed duplicated region for block: B:15:0x0044  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        Composer composer3 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.<anonymous>.<anonymous> (ModesDialogDelegate.kt:142)");
                                                }
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                composerImpl5.startReplaceGroup(-1008763488);
                                                SystemUIDialog systemUIDialog3 = systemUIDialog2;
                                                boolean zChangedInstance2 = composerImpl5.changedInstance(systemUIDialog3);
                                                Object objRememberedValue3 = composerImpl5.rememberedValue();
                                                if (!zChangedInstance2) {
                                                    Composer.Companion.getClass();
                                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                                        objRememberedValue3 = new ModesDialogDelegate$$ExternalSyntheticLambda0(systemUIDialog3, 1);
                                                        composerImpl5.updateRememberedValue(objRememberedValue3);
                                                    }
                                                    composerImpl5.end(false);
                                                    ComposableSingletons$ModesDialogDelegateKt.INSTANCE.getClass();
                                                    PlatformButtonsKt.PlatformButton((Function0) objRememberedValue3, null, false, null, null, null, ComposableSingletons$ModesDialogDelegateKt.f110lambda2, composerImpl5, 1572864, 62);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl3), null, ComposableLambdaKt.rememberComposableLambda(-77827042, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.1.4
                                    /* JADX WARN: Removed duplicated region for block: B:15:0x004b  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        Composer composer3 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.ModesDialogContent.<anonymous>.<anonymous> (ModesDialogDelegate.kt:137)");
                                                }
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                composerImpl5.startReplaceGroup(-1008770844);
                                                final ModesDialogDelegate modesDialogDelegate2 = modesDialogDelegate;
                                                boolean zChangedInstance2 = composerImpl5.changedInstance(modesDialogDelegate2);
                                                final SystemUIDialog systemUIDialog3 = systemUIDialog2;
                                                boolean zChangedInstance3 = zChangedInstance2 | composerImpl5.changedInstance(systemUIDialog3);
                                                Object objRememberedValue3 = composerImpl5.rememberedValue();
                                                if (!zChangedInstance3) {
                                                    Composer.Companion.getClass();
                                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                                        objRememberedValue3 = new Function0() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$ModesDialogContent$1$4$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                modesDialogDelegate2.openSettings(systemUIDialog3);
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl5.updateRememberedValue(objRememberedValue3);
                                                    }
                                                    composerImpl5.end(false);
                                                    ComposableSingletons$ModesDialogDelegateKt.INSTANCE.getClass();
                                                    PlatformButtonsKt.PlatformOutlinedButton((Function0) objRememberedValue3, null, false, null, null, ComposableSingletons$ModesDialogDelegateKt.f111lambda3, composerImpl5, 196608);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl3), composerImpl3, 1597494, 40);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 54, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    Intent intent = ModesDialogDelegate.ZEN_MODE_SETTINGS_INTENT;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.ModesDialogContent(systemUIDialog, (Composer) obj, iUpdateChangedFlags);
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
        ComponentSystemUIDialog componentSystemUIDialogCreate$default = SystemUIDialogFactoryExtKt.create$default(this.sysuiDialogFactory, ((ShadeDialogContextInteractorImpl) this.shadeDisplayContextRepository).getContext(), null, null, new ComposableLambdaImpl(-1413719250, true, new Function3() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.createDialog.1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int iIntValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.createDialog.<anonymous> (ModesDialogDelegate.kt:97)");
                }
                Intent intent = ModesDialogDelegate.ZEN_MODE_SETTINGS_INTENT;
                ModesDialogDelegate.this.ModesDialogContent(systemUIDialog, composer, iIntValue & 14);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 30);
        this.currentDialog = componentSystemUIDialogCreate$default;
        componentSystemUIDialogCreate$default.getLifecycleRegistry$1().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate.createDialog.2
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
        BuildersKt.launch$default(this.applicationCoroutineScope, null, null, new C11131(intent, null), 3);
    }

    public final void openSettings(SystemUIDialog systemUIDialog) {
        this.dialogEventLogger.uiEventLogger.log(QSModesEvent.QS_MODES_SETTINGS);
        DialogTransitionAnimator.AnonymousClass1 anonymousClass1CreateActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(systemUIDialog, this.dialogTransitionAnimator);
        if (anonymousClass1CreateActivityTransitionController$default == null) {
            systemUIDialog.dismiss();
        }
        this.activityStarter.startActivity(ZEN_MODE_SETTINGS_INTENT, true, (ActivityTransitionAnimator.Controller) anonymousClass1CreateActivityTransitionController$default);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object showDialog(Expandable expandable, ContinuationImpl continuationImpl) {
        C11141 c11141;
        if (continuationImpl instanceof C11141) {
            c11141 = (C11141) continuationImpl;
            int i = c11141.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c11141.label = i - Integer.MIN_VALUE;
            } else {
                c11141 = new C11141(continuationImpl);
            }
        }
        Object obj = c11141.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c11141.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            C11152 c11152 = new C11152(expandable, null);
            c11141.L$0 = this;
            c11141.label = 1;
            if (BuildersKt.withContext(this.mainCoroutineContext, c11152, c11141) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (ModesDialogDelegate) c11141.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ComponentSystemUIDialog componentSystemUIDialog = this.currentDialog;
        componentSystemUIDialog.getClass();
        return componentSystemUIDialog;
    }

    public static /* synthetic */ void getCurrentDialog$annotations() {
    }
}
