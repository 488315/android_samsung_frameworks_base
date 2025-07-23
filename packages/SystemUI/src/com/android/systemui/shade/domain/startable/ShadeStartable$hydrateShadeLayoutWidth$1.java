package com.android.systemui.shade.domain.startable;

import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeStartable$hydrateShadeLayoutWidth$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShadeStartable this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.domain.startable.ShadeStartable$hydrateShadeLayoutWidth$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Unit unit = Unit.INSTANCE;
                this.label = 1;
                if (flowCollector.emit(unit, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeStartable$hydrateShadeLayoutWidth$1(ShadeStartable shadeStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeStartable$hydrateShadeLayoutWidth$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeStartable$hydrateShadeLayoutWidth$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass1(null), ((ConfigurationRepositoryImpl) this.this$0.configurationRepository).onAnyConfigurationChange);
            final ShadeStartable shadeStartable = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.domain.startable.ShadeStartable$hydrateShadeLayoutWidth$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ShadeStartable shadeStartable2 = ShadeStartable.this;
                    shadeStartable2.context.getResources().getClass();
                    ((SplitShadeStateControllerImpl) shadeStartable2.splitShadeStateController).shouldUseSplitNotificationShade();
                    ((ShadeRepositoryImpl) shadeStartable2.shadeRepository)._isShadeLayoutWide.updateState(null, Boolean.FALSE);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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
