package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class ShortcutHelperStateRepository {
    public final StateFlowImpl _state;
    public final CoroutineDispatcher backgroundDispatcher;
    public final InputManager inputManager;
    public final ReadonlyStateFlow state;

    /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$show$1, reason: invalid class name */
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
            return ShortcutHelperStateRepository.this.show(null, this);
        }
    }

    public ShortcutHelperStateRepository(InputManager inputManager, CoroutineDispatcher coroutineDispatcher) {
        this.inputManager = inputManager;
        this.backgroundDispatcher = coroutineDispatcher;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(ShortcutHelperState.Inactive.INSTANCE);
        this._state = stateFlowImplMutableStateFlow;
        this.state = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r5v11, types: [kotlinx.coroutines.flow.MutableStateFlow] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object show(Integer num, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        StateFlowImpl stateFlowImpl;
        StateFlowImpl stateFlowImpl2;
        int iIntValue;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            stateFlowImpl = this._state;
            if (num != null) {
                iIntValue = num.intValue();
                stateFlowImpl.setValue(new ShortcutHelperState.Active(iIntValue));
                return Unit.INSTANCE;
            }
            anonymousClass1.L$0 = stateFlowImpl;
            anonymousClass1.label = 1;
            Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new ShortcutHelperStateRepository$findPhysicalKeyboardId$2(this, null), anonymousClass1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objWithContext;
            stateFlowImpl2 = stateFlowImpl;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r5 = (MutableStateFlow) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            stateFlowImpl2 = r5;
        }
        int iIntValue2 = ((Number) obj).intValue();
        stateFlowImpl = stateFlowImpl2;
        iIntValue = iIntValue2;
        stateFlowImpl.setValue(new ShortcutHelperState.Active(iIntValue));
        return Unit.INSTANCE;
    }
}
