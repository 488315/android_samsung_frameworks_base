package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogSettingsButtonViewModel this$0;

    /* renamed from: com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ VolumeDialogSettingsButtonViewModel this$0;

        /* renamed from: com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C06481 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C06481(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector, VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel) {
            this.this$0 = volumeDialogSettingsButtonViewModel;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            C06481 c06481;
            if (continuation instanceof C06481) {
                c06481 = (C06481) continuation;
                int i = c06481.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    c06481.label = i - Integer.MIN_VALUE;
                } else {
                    c06481 = new C06481(continuation);
                }
            }
            Object obj2 = c06481.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = c06481.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                c06481.label = 1;
                if (VolumeDialogSettingsButtonViewModel.access$emitDrawables(this.this$0, this.$$this$flow, (VolumeDialogSettingsButtonViewModel.PlaybackStates) obj, c06481) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1(Flow flow, Continuation continuation, VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = volumeDialogSettingsButtonViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1 volumeDialogSettingsButtonViewModel$special$$inlined$transform$1 = new VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1(this.$this_transform, continuation, this.this$0);
        volumeDialogSettingsButtonViewModel$special$$inlined$transform$1.L$0 = obj;
        return volumeDialogSettingsButtonViewModel$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.this$0);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
