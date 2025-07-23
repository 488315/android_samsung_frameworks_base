package com.android.systemui.statusbar.policy.ui.dialog;

import android.content.Intent;
import android.util.Log;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.util.Assert;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ModesDialogDelegate$launchFromDialog$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent;
    int label;
    final /* synthetic */ ModesDialogDelegate this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$launchFromDialog$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        int label;
        final /* synthetic */ ModesDialogDelegate this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate$launchFromDialog$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C03931 extends SuspendLambda implements Function2 {
            final /* synthetic */ Intent $intent;
            int label;
            final /* synthetic */ ModesDialogDelegate this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03931(ModesDialogDelegate modesDialogDelegate, Intent intent, Continuation continuation) {
                super(2, continuation);
                this.this$0 = modesDialogDelegate;
                this.$intent = intent;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03931(this.this$0, this.$intent, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03931) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default = componentSystemUIDialog2 != null ? DialogTransitionAnimator.createActivityTransitionController$default(componentSystemUIDialog2, modesDialogDelegate.dialogTransitionAnimator) : null;
                if (createActivityTransitionController$default == null && (componentSystemUIDialog = modesDialogDelegate.currentDialog) != null) {
                    componentSystemUIDialog.dismiss();
                }
                modesDialogDelegate.activityStarter.startActivity(intent, true, (ActivityTransitionAnimator.Controller) createActivityTransitionController$default);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ModesDialogDelegate modesDialogDelegate, Intent intent, Continuation continuation) {
            super(2, continuation);
            this.this$0 = modesDialogDelegate;
            this.$intent = intent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$intent, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ModesDialogDelegate modesDialogDelegate = this.this$0;
                CoroutineContext coroutineContext = modesDialogDelegate.mainCoroutineContext;
                C03931 c03931 = new C03931(modesDialogDelegate, this.$intent, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineContext, c03931, this) == coroutineSingletons) {
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
    public ModesDialogDelegate$launchFromDialog$1(ModesDialogDelegate modesDialogDelegate, Intent intent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = modesDialogDelegate;
        this.$intent = intent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ModesDialogDelegate$launchFromDialog$1(this.this$0, this.$intent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ModesDialogDelegate$launchFromDialog$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ModesDialogDelegate modesDialogDelegate = this.this$0;
            CoroutineContext coroutineContext = modesDialogDelegate.bgContext;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(modesDialogDelegate, this.$intent, null);
            this.label = 1;
            if (BuildersKt.withContext(coroutineContext, anonymousClass1, this) == coroutineSingletons) {
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
