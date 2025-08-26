package com.android.systemui.shade.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.TransitionKey;
import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes3.dex */
public final class ShadeLockscreenInteractorImpl implements ShadeLockscreenInteractor {
    public final CoroutineScope backgroundScope;
    public final CoroutineDispatcher mainDispatcher;
    public final SceneInteractor sceneInteractor;
    public final ShadeInteractor shadeInteractor;

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl$transitionToExpandedShade$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ long $delay;
        int label;
        final /* synthetic */ ShadeLockscreenInteractorImpl this$0;

        /* renamed from: com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl$transitionToExpandedShade$1$1, reason: invalid class name and collision with other inner class name */
        final class C04811 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ShadeLockscreenInteractorImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04811(ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shadeLockscreenInteractorImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04811(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04811) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ((ShadeInteractorImpl) this.this$0.shadeInteractor).expandNotificationsShade("ShadeLockscreenInteractorImpl.transitionToExpandedShade");
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(long j, ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.$delay = j;
            this.this$0 = shadeLockscreenInteractorImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$delay, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r1, r3, r6) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = this.$delay;
                this.label = 1;
                if (DelayKt.delay(j, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl = this.this$0;
            CoroutineDispatcher coroutineDispatcher = shadeLockscreenInteractorImpl.mainDispatcher;
            C04811 c04811 = new C04811(shadeLockscreenInteractorImpl, null);
            this.label = 2;
        }
    }

    public ShadeLockscreenInteractorImpl(CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, ShadeInteractor shadeInteractor, SceneInteractor sceneInteractor, SecLockIconViewController secLockIconViewController, ShadeRepository shadeRepository) {
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundScope = coroutineScope;
        this.shadeInteractor = shadeInteractor;
        this.sceneInteractor = sceneInteractor;
        shadeRepository.getClass();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void expandToNotifications() {
        ((ShadeInteractorImpl) this.shadeInteractor).expandNotificationsShade("ShadeLockscreenInteractorImpl.expandToNotifications");
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final boolean isExpanded() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor, com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyCollapsed() {
        return true;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z) {
        TransitionKeys.INSTANCE.getClass();
        TransitionKey transitionKey = TransitionKeys.Instant;
        if (z) {
            transitionKey = null;
        }
        BaseShadeInteractor.collapseQuickSettingsShade$default(this.shadeInteractor, "ShadeLockscreenInteractorImpl.resetViews", transitionKey, 4);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void showAodUi() {
        SceneInteractor.changeScene$default(this.sceneInteractor, Scenes.Lockscreen, "showAodUi", null, KeyguardState.AOD, false, 20);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void transitionToExpandedShade(long j, boolean z) {
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new AnonymousClass1(j, this, null), 7);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setOverStretchAmount(float f) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setPulsing(boolean z) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void blockExpansionForCurrentTouch() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViewGroupFade() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardStatusBarAlpha() {
    }
}
