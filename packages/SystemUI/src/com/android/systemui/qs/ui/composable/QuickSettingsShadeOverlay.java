package com.android.systemui.qs.ui.composable;

import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayContentViewModel;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.ui.composable.Overlay;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
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
public final class QuickSettingsShadeOverlay implements Overlay {
    public final Lazy actionsViewModel$delegate;
    public final QuickSettingsShadeOverlayActionsViewModel.Factory actionsViewModelFactory;
    public final QuickSettingsShadeOverlayContentViewModel.Factory contentViewModelFactory;
    public final OverlayKey key = Overlays.QuickSettingsShade;
    public final dagger.Lazy notificationStackScrollView;
    public final NotificationsPlaceholderViewModel.Factory notificationsPlaceholderViewModelFactory;
    public final QuickSettingsContainerViewModel.Factory quickSettingsContainerViewModelFactory;
    public final ReadonlyStateFlow userActions;

    /* renamed from: com.android.systemui.qs.ui.composable.QuickSettingsShadeOverlay$activate$1, reason: invalid class name */
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
            return QuickSettingsShadeOverlay.this.activate(this);
        }
    }

    public QuickSettingsShadeOverlay(QuickSettingsShadeOverlayActionsViewModel.Factory factory, QuickSettingsShadeOverlayContentViewModel.Factory factory2, QuickSettingsContainerViewModel.Factory factory3, dagger.Lazy lazy, NotificationsPlaceholderViewModel.Factory factory4) {
        this.actionsViewModelFactory = factory;
        this.contentViewModelFactory = factory2;
        this.quickSettingsContainerViewModelFactory = factory3;
        this.notificationStackScrollView = lazy;
        this.notificationsPlaceholderViewModelFactory = factory4;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.ui.composable.QuickSettingsShadeOverlay$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.actionsViewModelFactory.create();
            }
        });
        this.actionsViewModel$delegate = lazy2;
        this.userActions = ((QuickSettingsShadeOverlayActionsViewModel) lazy2.getValue()).actions;
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
            QuickSettingsShadeOverlayActionsViewModel quickSettingsShadeOverlayActionsViewModel = (QuickSettingsShadeOverlayActionsViewModel) this.actionsViewModel$delegate.getValue();
            anonymousClass1.label = 1;
            if (quickSettingsShadeOverlayActionsViewModel.activate(anonymousClass1) == coroutineSingletons) {
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
