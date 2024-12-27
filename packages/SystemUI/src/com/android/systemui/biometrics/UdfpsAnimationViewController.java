package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.HashSet;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public abstract class UdfpsAnimationViewController extends ViewController implements Dumpable {
    public ValueAnimator dialogAlphaAnimator;
    public final UdfpsAnimationViewController$dialogListener$1 dialogListener;
    public final SystemUIDialogManager dialogManager;
    public final DumpManager dumpManager;
    public final String dumpTag;
    public boolean notificationShadeVisible;
    public final ShadeInteractor shadeInteractor;
    public final StatusBarStateController statusBarStateController;
    public final UdfpsOverlayInteractor udfpsOverlayInteractor;

    /* renamed from: com.android.systemui.biometrics.UdfpsAnimationViewController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.biometrics.UdfpsAnimationViewController$1$1, reason: invalid class name and collision with other inner class name */
        final class C00261 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ UdfpsAnimationViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00261(UdfpsAnimationViewController udfpsAnimationViewController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = udfpsAnimationViewController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00261 c00261 = new C00261(this.this$0, continuation);
                c00261.L$0 = obj;
                return c00261;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00261) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    UdfpsAnimationViewController udfpsAnimationViewController = this.this$0;
                    this.label = 1;
                    if (udfpsAnimationViewController.listenForShadeExpansion(coroutineScope, this) == coroutineSingletons) {
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

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = UdfpsAnimationViewController.this.new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C00261 c00261 = new C00261(UdfpsAnimationViewController.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c00261, this) == coroutineSingletons) {
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

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.biometrics.UdfpsAnimationViewController$dialogListener$1] */
    public UdfpsAnimationViewController(UdfpsAnimationView udfpsAnimationView, StatusBarStateController statusBarStateController, ShadeInteractor shadeInteractor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        super(udfpsAnimationView);
        this.statusBarStateController = statusBarStateController;
        this.shadeInteractor = shadeInteractor;
        this.dialogManager = systemUIDialogManager;
        this.dumpManager = dumpManager;
        this.udfpsOverlayInteractor = udfpsOverlayInteractor;
        this.dialogListener = new SystemUIDialogManager.Listener() { // from class: com.android.systemui.biometrics.UdfpsAnimationViewController$dialogListener$1
            @Override // com.android.systemui.statusbar.phone.SystemUIDialogManager.Listener
            public final void shouldHideAffordances(boolean z) {
                final UdfpsAnimationViewController udfpsAnimationViewController = UdfpsAnimationViewController.this;
                boolean shouldHideAffordance = udfpsAnimationViewController.dialogManager.shouldHideAffordance();
                ValueAnimator valueAnimator = udfpsAnimationViewController.dialogAlphaAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(udfpsAnimationViewController.getView().calculateAlpha() / 255.0f, shouldHideAffordance ? 0.0f : 1.0f);
                ofFloat.setDuration(shouldHideAffordance ? 83L : 200L);
                ofFloat.setInterpolator(shouldHideAffordance ? Interpolators.LINEAR : Interpolators.ALPHA_IN);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsAnimationViewController$runDialogAlphaAnimator$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        UdfpsAnimationView view = UdfpsAnimationViewController.this.getView();
                        view.mDialogSuggestedAlpha = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        view.updateAlpha();
                        UdfpsAnimationViewController.this.updateAlpha();
                        UdfpsAnimationViewController.this.updatePauseAuth();
                    }
                });
                ofFloat.start();
                udfpsAnimationViewController.dialogAlphaAnimator = ofFloat;
            }
        };
        new PointF(0.0f, 0.0f);
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsAnimationView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
        this.dumpTag = getTag() + " (" + this + ")";
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mNotificationShadeVisible=" + this.notificationShadeVisible);
        printWriter.println("shouldPauseAuth()=" + shouldPauseAuth());
        printWriter.println("isPauseAuth=" + getView().mPauseAuth);
        printWriter.println("dialogSuggestedAlpha=" + getView().mDialogSuggestedAlpha);
    }

    public abstract String getTag();

    public final UdfpsAnimationView getView() {
        T t = this.mView;
        Intrinsics.checkNotNull(t);
        return (UdfpsAnimationView) t;
    }

    public final Object listenForShadeExpansion(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsAnimationViewController$listenForShadeExpansion$2(this, null), 3);
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        ((HashSet) this.dialogManager.mListeners).add(this.dialogListener);
        DumpManager.registerDumpable$default(this.dumpManager, this.dumpTag, this);
        this.udfpsOverlayInteractor.setHandleTouches(!shouldPauseAuth());
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        ((HashSet) this.dialogManager.mListeners).remove(this.dialogListener);
        this.dumpManager.unregisterDumpable(this.dumpTag);
        this.udfpsOverlayInteractor.setHandleTouches(!shouldPauseAuth());
    }

    public boolean shouldPauseAuth() {
        return this.notificationShadeVisible || this.dialogManager.shouldHideAffordance();
    }

    public void updateAlpha() {
        getView().updateAlpha();
    }

    public final void updatePauseAuth() {
        UdfpsAnimationView view = getView();
        boolean shouldPauseAuth = shouldPauseAuth();
        if (shouldPauseAuth != view.mPauseAuth) {
            view.mPauseAuth = shouldPauseAuth;
            view.updateAlpha();
            getView().postInvalidate();
            this.udfpsOverlayInteractor.setHandleTouches(!shouldPauseAuth());
        }
    }
}
