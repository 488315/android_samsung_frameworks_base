package com.android.systemui.scene.ui.composable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel;
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
public final class GoneScene extends ExclusiveActivatable implements Scene {
    public final Lazy actionsViewModel$delegate;
    public final SceneKey key = Scenes.Gone;
    public final dagger.Lazy notificationStackScrolLView;
    public final NotificationsPlaceholderViewModel.Factory notificationsPlaceholderViewModelFactory;
    public final ReadonlyStateFlow userActions;
    public final GoneUserActionsViewModel.Factory viewModelFactory;

    /* renamed from: com.android.systemui.scene.ui.composable.GoneScene$onActivated$1, reason: invalid class name */
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
            return GoneScene.this.onActivated(this);
        }
    }

    public GoneScene(dagger.Lazy lazy, NotificationsPlaceholderViewModel.Factory factory, GoneUserActionsViewModel.Factory factory2) {
        this.notificationStackScrolLView = lazy;
        this.notificationsPlaceholderViewModelFactory = factory;
        this.viewModelFactory = factory2;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.scene.ui.composable.GoneScene$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.viewModelFactory.create();
            }
        });
        this.actionsViewModel$delegate = lazy2;
        this.userActions = ((GoneUserActionsViewModel) lazy2.getValue()).actions;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
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
            GoneUserActionsViewModel goneUserActionsViewModel = (GoneUserActionsViewModel) this.actionsViewModel$delegate.getValue();
            anonymousClass1.label = 1;
            if (goneUserActionsViewModel.activate(anonymousClass1) == coroutineSingletons) {
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
