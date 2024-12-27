package com.android.systemui.keyguard.ui.binder;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardPreviewSmartspaceViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Context $previewContext;
    final /* synthetic */ View $smartspace;
    final /* synthetic */ boolean $splitShadePreview;
    final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $previewContext;
        final /* synthetic */ View $smartspace;
        final /* synthetic */ boolean $splitShadePreview;
        final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, boolean z, Context context, View view, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardPreviewSmartspaceViewModel;
            this.$splitShadePreview = z;
            this.$previewContext = context;
            this.$smartspace = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$splitShadePreview, this.$previewContext, this.$smartspace, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel = this.$viewModel;
            boolean z = this.$splitShadePreview;
            Context context = this.$previewContext;
            View view = this.$smartspace;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1("KeyguardPreviewSmartspaceViewBinder#viewModel.selectedClockSize", null, keyguardPreviewSmartspaceViewModel, z, context, view), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardPreviewSmartspaceViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2("KeyguardPreviewSmartspaceViewBinder#viewModel.shouldHideSmartspace", null, this.$viewModel, this.$smartspace), 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardPreviewSmartspaceViewBinder$bind$1(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, boolean z, Context context, View view, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardPreviewSmartspaceViewModel;
        this.$splitShadePreview = z;
        this.$previewContext = context;
        this.$smartspace = view;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardPreviewSmartspaceViewBinder$bind$1 keyguardPreviewSmartspaceViewBinder$bind$1 = new KeyguardPreviewSmartspaceViewBinder$bind$1(this.$viewModel, this.$splitShadePreview, this.$previewContext, this.$smartspace, (Continuation) obj3);
        keyguardPreviewSmartspaceViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return keyguardPreviewSmartspaceViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$splitShadePreview, this.$previewContext, this.$smartspace, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
