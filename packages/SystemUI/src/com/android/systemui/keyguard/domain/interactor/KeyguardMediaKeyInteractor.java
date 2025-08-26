package com.android.systemui.keyguard.domain.interactor;

import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class KeyguardMediaKeyInteractor extends ExclusiveActivatable {
    public final TelephonyInteractor telephonyInteractor;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor$onActivated$1, reason: invalid class name */
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
            return KeyguardMediaKeyInteractor.this.onActivated(this);
        }
    }

    public KeyguardMediaKeyInteractor(TelephonyInteractor telephonyInteractor, AudioRepository audioRepository) {
        this.telephonyInteractor = telephonyInteractor;
    }

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
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow readonlyStateFlow = this.telephonyInteractor.isInCall;
            AnonymousClass2 anonymousClass2 = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor.onActivated.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation2) {
                    ((Boolean) obj2).booleanValue();
                    return Unit.INSTANCE;
                }
            };
            anonymousClass1.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(anonymousClass2, anonymousClass1) == coroutineSingletons) {
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
