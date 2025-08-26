package com.android.systemui.scene.ui.viewmodel;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public abstract class UserActionsViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _actions;
    public final ReadonlyStateFlow actions;

    /* renamed from: com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return UserActionsViewModel.this.onActivated(this);
        }
    }

    public UserActionsViewModel() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(MapsKt__MapsKt.emptyMap());
        this._actions = stateFlowImplMutableStateFlow;
        this.actions = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    public abstract Object hydrateActions(UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation);

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005b, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L28;
     */
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
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0 = new UserActionsViewModel$$ExternalSyntheticLambda0(this);
                anonymousClass1.L$0 = this;
                anonymousClass1.label = 1;
                if (hydrateActions(userActionsViewModel$$ExternalSyntheticLambda0, anonymousClass1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (UserActionsViewModel) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            this = (UserActionsViewModel) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 2;
        } catch (Throwable th) {
            this._actions.setValue(MapsKt__MapsKt.emptyMap());
            throw th;
        }
    }
}
