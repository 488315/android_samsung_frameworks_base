package com.android.systemui.keyboard.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$keyboardsChange$1", m277f = "KeyboardRepository.kt", m278l = {106}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyboardRepositoryImpl$keyboardsChange$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyboardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardRepositoryImpl$keyboardsChange$1(KeyboardRepositoryImpl keyboardRepositoryImpl, Continuation<? super KeyboardRepositoryImpl$keyboardsChange$1> continuation) {
        super(2, continuation);
        this.this$0 = keyboardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyboardRepositoryImpl$keyboardsChange$1 keyboardRepositoryImpl$keyboardsChange$1 = new KeyboardRepositoryImpl$keyboardsChange$1(this.this$0, continuation);
        keyboardRepositoryImpl$keyboardsChange$1.L$0 = obj;
        return keyboardRepositoryImpl$keyboardsChange$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardRepositoryImpl$keyboardsChange$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, java.util.Set] */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.hardware.input.InputManager$InputDeviceListener, com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$keyboardsChange$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = ArraysKt___ArraysKt.toSet(this.this$0.inputManager.getInputDeviceIds());
            final KeyboardRepositoryImpl keyboardRepositoryImpl = this.this$0;
            final ?? r3 = new InputManager.InputDeviceListener() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$keyboardsChange$1$listener$1
                /* JADX WARN: Type inference failed for: r3v0, types: [T, java.util.LinkedHashSet] */
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceAdded(int i2) {
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    Set set = (Set) ref$ObjectRef2.element;
                    Integer valueOf = Integer.valueOf(i2);
                    ?? linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(set.size() + 1));
                    linkedHashSet.addAll(set);
                    linkedHashSet.add(valueOf);
                    ref$ObjectRef2.element = linkedHashSet;
                    KeyboardRepositoryImpl.access$sendWithLogging(keyboardRepositoryImpl, producerScope, new Pair(ref$ObjectRef.element, new KeyboardRepositoryImpl.DeviceAdded(i2)));
                }

                /* JADX WARN: Type inference failed for: r2v0, types: [T, java.util.Collection, java.util.LinkedHashSet] */
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceRemoved(int i2) {
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    Set set = (Set) ref$ObjectRef2.element;
                    Integer valueOf = Integer.valueOf(i2);
                    ?? linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(set.size()));
                    boolean z = false;
                    for (Object obj2 : set) {
                        boolean z2 = true;
                        if (!z && Intrinsics.areEqual(obj2, valueOf)) {
                            z = true;
                            z2 = false;
                        }
                        if (z2) {
                            linkedHashSet.add(obj2);
                        }
                    }
                    ref$ObjectRef2.element = linkedHashSet;
                    KeyboardRepositoryImpl.access$sendWithLogging(keyboardRepositoryImpl, producerScope, new Pair(ref$ObjectRef.element, KeyboardRepositoryImpl.DeviceRemoved.INSTANCE));
                }

                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceChanged(int i2) {
                }
            };
            KeyboardRepositoryImpl.access$sendWithLogging(this.this$0, producerScope, new Pair(ref$ObjectRef.element, KeyboardRepositoryImpl.FreshStart.INSTANCE));
            this.this$0.inputManager.registerInputDeviceListener(r3, null);
            final KeyboardRepositoryImpl keyboardRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$keyboardsChange$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyboardRepositoryImpl.this.inputManager.unregisterInputDeviceListener(r3);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
