package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.SettingsClockSize;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardClockRepository$getClockSize$2", m277f = "KeyguardClockRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardClockRepository$getClockSize$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardClockRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardClockRepository$getClockSize$2(KeyguardClockRepository keyguardClockRepository, Continuation<? super KeyguardClockRepository$getClockSize$2> continuation) {
        super(2, continuation);
        this.this$0 = keyguardClockRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardClockRepository$getClockSize$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardClockRepository$getClockSize$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.secureSettings.getIntForUser(1, -2, "lockscreen_use_double_line_clock") == 1 ? SettingsClockSize.DYNAMIC : SettingsClockSize.SMALL;
    }
}
