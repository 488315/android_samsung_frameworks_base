package com.android.systemui.topwindoweffects.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.keyevent.domain.interactor.KeyEventInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
public final class SqueezeEffectViewModel extends ExclusiveActivatable {
    public final Hydrator hydrator;
    public final State isPowerButtonLongPressed$delegate;
    public final State isPowerButtonPressed$delegate;

    public interface Factory {
        SqueezeEffectViewModel create();
    }

    /* renamed from: com.android.systemui.topwindoweffects.ui.viewmodel.SqueezeEffectViewModel$onActivated$1, reason: invalid class name */
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
            return SqueezeEffectViewModel.this.onActivated(this);
        }
    }

    public SqueezeEffectViewModel(KeyEventInteractor keyEventInteractor) {
        Hydrator hydrator = new Hydrator("SqueezeEffectViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        Boolean bool = Boolean.FALSE;
        this.isPowerButtonPressed$delegate = hydrator.hydratedStateOf("isPowerButtonPressed", bool, keyEventInteractor.isPowerButtonDown);
        this.isPowerButtonLongPressed$delegate = hydrator.hydratedStateOf("isPowerButtonLongPressed", bool, keyEventInteractor.isPowerButtonLongPressed);
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
            anonymousClass1.label = 1;
            if (this.hydrator.activate(anonymousClass1) == coroutineSingletons) {
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
