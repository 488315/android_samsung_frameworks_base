package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel$special$$inlined$map$2;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$2", m277f = "KeyguardPreviewClockViewBinder.kt", m278l = {43}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardPreviewClockViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $smallClockHostView;
    final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$2$1", m277f = "KeyguardPreviewClockViewBinder.kt", m278l = {44}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$2$1 */
    final class C17411 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $smallClockHostView;
        final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C17411(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, View view, Continuation<? super C17411> continuation) {
            super(2, continuation);
            this.$viewModel = keyguardPreviewClockViewModel;
            this.$smallClockHostView = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C17411(this.$viewModel, this.$smallClockHostView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C17411) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyguardPreviewClockViewModel$special$$inlined$map$2 keyguardPreviewClockViewModel$special$$inlined$map$2 = this.$viewModel.isSmallClockVisible;
                final View view = this.$smallClockHostView;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder.bind.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        view.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (keyguardPreviewClockViewModel$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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
    public KeyguardPreviewClockViewBinder$bind$2(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, View view, Continuation<? super KeyguardPreviewClockViewBinder$bind$2> continuation) {
        super(3, continuation);
        this.$viewModel = keyguardPreviewClockViewModel;
        this.$smallClockHostView = view;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardPreviewClockViewBinder$bind$2 keyguardPreviewClockViewBinder$bind$2 = new KeyguardPreviewClockViewBinder$bind$2(this.$viewModel, this.$smallClockHostView, (Continuation) obj3);
        keyguardPreviewClockViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return keyguardPreviewClockViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C17411 c17411 = new C17411(this.$viewModel, this.$smallClockHostView, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c17411, this) == coroutineSingletons) {
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
