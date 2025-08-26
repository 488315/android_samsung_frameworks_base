package com.android.systemui.util.kotlin;

import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
public final class ActivatableFlowDumperImpl extends SimpleFlowDumper implements ActivatableFlowDumper {
    public static final int $stable = 8;
    private final DumpManager dumpManager;
    private final String dumpManagerName;
    private final ActivatableFlowDumperImpl$registration$1 registration = new ActivatableFlowDumperImpl$registration$1(this);

    /* renamed from: com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$activateFlowDumper$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ActivatableFlowDumperImpl.this.activateFlowDumper(this);
        }
    }

    public ActivatableFlowDumperImpl(DumpManager dumpManager, String str) {
        this.dumpManager = dumpManager;
        this.dumpManagerName = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("[", getIdString(this), "] ", str);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.util.kotlin.ActivatableFlowDumper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object activateFlowDumper(Continuation continuation) {
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
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ActivatableFlowDumperImpl$registration$1 activatableFlowDumperImpl$registration$1 = this.registration;
            anonymousClass1.label = 1;
            if (activatableFlowDumperImpl$registration$1.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
