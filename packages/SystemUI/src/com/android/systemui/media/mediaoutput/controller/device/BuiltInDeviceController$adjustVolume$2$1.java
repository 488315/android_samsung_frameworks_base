package com.android.systemui.media.mediaoutput.controller.device;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes2.dex */
final class BuiltInDeviceController$adjustVolume$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BuiltInDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuiltInDeviceController$adjustVolume$2$1(BuiltInDeviceController builtInDeviceController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = builtInDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BuiltInDeviceController$adjustVolume$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuiltInDeviceController$adjustVolume$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x006b, code lost:
    
        if (r9.updateDevices$1(r1, false, r8) != r0) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0058  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(300L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                this.label = 3;
                if (DelayKt.delay(200L, this) != coroutineSingletons) {
                    BuiltInDeviceController builtInDeviceController = this.this$0;
                    List list = ArraysKt___ArraysKt.toList(builtInDeviceController.audioManager.getDevices(2));
                    this.label = 4;
                }
                return coroutineSingletons;
            }
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.updateJob = null;
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            BuiltInDeviceController builtInDeviceController2 = this.this$0;
            List list2 = ArraysKt___ArraysKt.toList(builtInDeviceController2.audioManager.getDevices(2));
            this.label = 4;
        }
        BuiltInDeviceController builtInDeviceController3 = this.this$0;
        List list3 = ArraysKt___ArraysKt.toList(builtInDeviceController3.audioManager.getDevices(2));
        this.label = 2;
        if (builtInDeviceController3.updateDevices$1(list3, true, this) != coroutineSingletons) {
            this.label = 3;
            if (DelayKt.delay(200L, this) != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
