package com.android.systemui.util.kotlin;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes3.dex */
public final class ActivatableFlowDumperImpl$registration$1 extends ExclusiveActivatable {
    final /* synthetic */ ActivatableFlowDumperImpl this$0;

    public ActivatableFlowDumperImpl$registration$1(ActivatableFlowDumperImpl activatableFlowDumperImpl) {
        this.this$0 = activatableFlowDumperImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object onActivated(Continuation continuation) {
        ActivatableFlowDumperImpl$registration$1$onActivated$1 activatableFlowDumperImpl$registration$1$onActivated$1;
        if (continuation instanceof ActivatableFlowDumperImpl$registration$1$onActivated$1) {
            activatableFlowDumperImpl$registration$1$onActivated$1 = (ActivatableFlowDumperImpl$registration$1$onActivated$1) continuation;
            int i = activatableFlowDumperImpl$registration$1$onActivated$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                activatableFlowDumperImpl$registration$1$onActivated$1.label = i - Integer.MIN_VALUE;
            } else {
                activatableFlowDumperImpl$registration$1$onActivated$1 = new ActivatableFlowDumperImpl$registration$1$onActivated$1(this, continuation);
            }
        }
        Object obj = activatableFlowDumperImpl$registration$1$onActivated$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = activatableFlowDumperImpl$registration$1$onActivated$1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                this.this$0.dumpManager.registerCriticalDumpable(this.this$0.dumpManagerName, this.this$0);
                activatableFlowDumperImpl$registration$1$onActivated$1.L$0 = this;
                activatableFlowDumperImpl$registration$1$onActivated$1.label = 1;
                if (DelayKt.awaitCancellation(activatableFlowDumperImpl$registration$1$onActivated$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (ActivatableFlowDumperImpl$registration$1) activatableFlowDumperImpl$registration$1$onActivated$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            this.this$0.dumpManager.unregisterDumpable(this.this$0.dumpManagerName);
            throw th;
        }
    }
}
