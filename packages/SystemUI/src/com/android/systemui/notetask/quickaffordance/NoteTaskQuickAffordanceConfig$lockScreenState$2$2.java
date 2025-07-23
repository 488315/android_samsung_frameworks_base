package com.android.systemui.notetask.quickaffordance;

import android.os.Build;
import android.util.Log;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class NoteTaskQuickAffordanceConfig$lockScreenState$2$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NoteTaskQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskQuickAffordanceConfig$lockScreenState$2$2(NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noteTaskQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoteTaskQuickAffordanceConfig$lockScreenState$2$2 noteTaskQuickAffordanceConfig$lockScreenState$2$2 = new NoteTaskQuickAffordanceConfig$lockScreenState$2$2(this.this$0, continuation);
        noteTaskQuickAffordanceConfig$lockScreenState$2$2.L$0 = obj;
        return noteTaskQuickAffordanceConfig$lockScreenState$2$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoteTaskQuickAffordanceConfig$lockScreenState$2$2) create((KeyguardQuickAffordanceConfig.LockScreenState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardQuickAffordanceConfig.LockScreenState lockScreenState = (KeyguardQuickAffordanceConfig.LockScreenState) this.L$0;
        NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig = this.this$0;
        if (Build.IS_DEBUGGABLE) {
            Log.d(noteTaskQuickAffordanceConfig.getClass().getSimpleName(), "lockScreenState=" + lockScreenState);
        }
        return Unit.INSTANCE;
    }
}
