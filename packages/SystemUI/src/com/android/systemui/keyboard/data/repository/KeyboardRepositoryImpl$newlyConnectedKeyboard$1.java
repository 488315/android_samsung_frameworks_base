package com.android.systemui.keyboard.data.repository;

import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import java.util.Collection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

final class KeyboardRepositoryImpl$newlyConnectedKeyboard$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public KeyboardRepositoryImpl$newlyConnectedKeyboard$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyboardRepositoryImpl$newlyConnectedKeyboard$1 keyboardRepositoryImpl$newlyConnectedKeyboard$1 = new KeyboardRepositoryImpl$newlyConnectedKeyboard$1(continuation);
        keyboardRepositoryImpl$newlyConnectedKeyboard$1.L$0 = obj;
        return keyboardRepositoryImpl$newlyConnectedKeyboard$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardRepositoryImpl$newlyConnectedKeyboard$1) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Pair pair = (Pair) this.L$0;
        Collection collection = (Collection) pair.component1();
        KeyboardRepositoryImpl.DeviceChange deviceChange = (KeyboardRepositoryImpl.DeviceChange) pair.component2();
        if (Intrinsics.areEqual(deviceChange, KeyboardRepositoryImpl.FreshStart.INSTANCE)) {
            return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3(collection);
        }
        if (deviceChange instanceof KeyboardRepositoryImpl.DeviceAdded) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Integer(((KeyboardRepositoryImpl.DeviceAdded) deviceChange).deviceId));
        }
        if (deviceChange instanceof KeyboardRepositoryImpl.DeviceRemoved) {
            return EmptyFlow.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }
}
