package com.android.systemui.people.p012ui.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.people.p012ui.viewmodel.PeopleViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$1", m277f = "PeopleViewBinder.kt", m278l = {79}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PeopleViewBinder$bind$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LifecycleOwner $lifecycleOwner;
    final /* synthetic */ Function1 $onResult;
    final /* synthetic */ PeopleViewModel $viewModel;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$1$1", m277f = "PeopleViewBinder.kt", m278l = {80}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$1$1 */
    final class C19091 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $onResult;
        final /* synthetic */ PeopleViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C19091(PeopleViewModel peopleViewModel, Function1 function1, Continuation<? super C19091> continuation) {
            super(2, continuation);
            this.$viewModel = peopleViewModel;
            this.$onResult = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C19091(this.$viewModel, this.$onResult, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C19091) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final PeopleViewModel peopleViewModel = this.$viewModel;
                ReadonlyStateFlow readonlyStateFlow = peopleViewModel.result;
                final Function1 function1 = this.$onResult;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder.bind.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        PeopleViewModel.Result result = (PeopleViewModel.Result) obj2;
                        if (result != null) {
                            PeopleViewModel.this._result.setValue(null);
                            function1.invoke(result);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewBinder$bind$1(LifecycleOwner lifecycleOwner, PeopleViewModel peopleViewModel, Function1 function1, Continuation<? super PeopleViewBinder$bind$1> continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.$viewModel = peopleViewModel;
        this.$onResult = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PeopleViewBinder$bind$1(this.$lifecycleOwner, this.$viewModel, this.$onResult, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PeopleViewBinder$bind$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = this.$lifecycleOwner;
            Lifecycle.State state = Lifecycle.State.CREATED;
            C19091 c19091 = new C19091(this.$viewModel, this.$onResult, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c19091, this) == coroutineSingletons) {
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
