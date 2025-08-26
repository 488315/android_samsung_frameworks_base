package com.android.systemui.notifications.ui.composable;

import com.android.compose.animation.scene.OverlayKey;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.keyguard.ui.composable.section.DefaultClockSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayActionsViewModel;
import com.android.systemui.notifications.ui.viewmodel.NotificationsShadeOverlayContentViewModel;
import com.android.systemui.scene.session.ui.composable.SaveableSession;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.ui.composable.Overlay;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class NotificationsShadeOverlay implements Overlay {
    public final Lazy actionsViewModel$delegate;
    public final NotificationsShadeOverlayActionsViewModel.Factory actionsViewModelFactory;
    public final DefaultClockSection clockSection;
    public final NotificationsShadeOverlayContentViewModel.Factory contentViewModelFactory;
    public final InteractionJankMonitor jankMonitor;
    public final OverlayKey key = Overlays.NotificationsShade;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final MediaCarouselController mediaCarouselController;
    public final dagger.Lazy mediaHost;
    public final SaveableSession shadeSession;
    public final dagger.Lazy stackScrollView;
    public final ReadonlyStateFlow userActions;

    /* renamed from: com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$activate$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return NotificationsShadeOverlay.this.activate(this);
        }
    }

    public NotificationsShadeOverlay(NotificationsShadeOverlayActionsViewModel.Factory factory, NotificationsShadeOverlayContentViewModel.Factory factory2, SaveableSession saveableSession, dagger.Lazy lazy, DefaultClockSection defaultClockSection, KeyguardClockViewModel keyguardClockViewModel, MediaCarouselController mediaCarouselController, dagger.Lazy lazy2, InteractionJankMonitor interactionJankMonitor) {
        this.actionsViewModelFactory = factory;
        this.contentViewModelFactory = factory2;
        this.shadeSession = saveableSession;
        this.stackScrollView = lazy;
        this.clockSection = defaultClockSection;
        this.keyguardClockViewModel = keyguardClockViewModel;
        this.mediaCarouselController = mediaCarouselController;
        this.mediaHost = lazy2;
        this.jankMonitor = interactionJankMonitor;
        Lazy lazy3 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.notifications.ui.composable.NotificationsShadeOverlay$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.actionsViewModelFactory.create();
            }
        });
        this.actionsViewModel$delegate = lazy3;
        this.userActions = ((NotificationsShadeOverlayActionsViewModel) lazy3.getValue()).actions;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.Activatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object activate(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationsShadeOverlayActionsViewModel notificationsShadeOverlayActionsViewModel = (NotificationsShadeOverlayActionsViewModel) this.actionsViewModel$delegate.getValue();
            anonymousClass1.label = 1;
            if (notificationsShadeOverlayActionsViewModel.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
