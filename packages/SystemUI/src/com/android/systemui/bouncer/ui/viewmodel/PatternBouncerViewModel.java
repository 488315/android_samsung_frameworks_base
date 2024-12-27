package com.android.systemui.bouncer.ui.viewmodel;

import android.R;
import android.content.Context;
import android.util.TypedValue;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import java.util.LinkedHashSet;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class PatternBouncerViewModel extends AuthMethodBouncerViewModel {
    public final StateFlowImpl _currentDot;
    public final StateFlowImpl _dots;
    public final StateFlowImpl _selectedDots;
    public final Context applicationContext;
    public final AuthenticationMethodModel.Pattern authenticationMethod;
    public final int columnCount;
    public final ReadonlyStateFlow currentDot;
    public final ReadonlyStateFlow dots;
    public final Lazy hitFactor$delegate;
    public final ReadonlyStateFlow isPatternVisible;
    public final Function0 onIntentionalUserInput;
    public final int rowCount;
    public final ReadonlyStateFlow selectedDots;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public PatternBouncerViewModel(Context context, CoroutineScope coroutineScope, BouncerInteractor bouncerInteractor, StateFlow stateFlow, Function0 function0) {
        super(coroutineScope, bouncerInteractor, stateFlow, null);
        this.applicationContext = context;
        this.columnCount = 3;
        this.rowCount = 3;
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new LinkedHashSet());
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L43
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.LinkedHashSet r5 = (java.util.LinkedHashSet) r5
                        java.util.List r5 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L43
                        return r1
                    L43:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), EmptyList.INSTANCE);
        FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(null));
        ListBuilder listBuilder = new ListBuilder();
        IntProgressionIterator it = RangesKt___RangesKt.until(0, this.columnCount).iterator();
        while (it.hasNext) {
            int nextInt = it.nextInt();
            IntProgressionIterator it2 = RangesKt___RangesKt.until(0, this.rowCount).iterator();
            while (it2.hasNext) {
                listBuilder.add(new PatternDotViewModel(nextInt, it2.nextInt()));
            }
        }
        FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(listBuilder.build()));
        ReadonlyStateFlow readonlyStateFlow = bouncerInteractor.isPatternVisible;
        this.authenticationMethod = AuthenticationMethodModel.Pattern.INSTANCE;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$hitFactor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TypedValue typedValue = new TypedValue();
                PatternBouncerViewModel.this.applicationContext.getResources().getValue(R.dimen.notification_header_touchable_height, typedValue, true);
                return Float.valueOf(Math.max(Math.min(typedValue.getFloat(), 1.0f), 0.2f));
            }
        });
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }
}
