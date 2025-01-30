package com.android.systemui.statusbar.phone;

import android.content.Intent;
import android.telephony.ServiceState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CarrierHomeLogoViewController$special$$inlined$map$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ CarrierHomeLogoViewController this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$special$$inlined$map$1$2 */
    public final class C30002 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ CarrierHomeLogoViewController this$0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$special$$inlined$map$1$2", m277f = "CarrierHomeLogoViewController.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
        /* renamed from: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$special$$inlined$map$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                return C30002.this.emit(null, this);
            }
        }

        public C30002(FlowCollector flowCollector, CarrierHomeLogoViewController carrierHomeLogoViewController) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = carrierHomeLogoViewController;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            int i;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i2 = anonymousClass1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        Intent intent = (Intent) obj;
                        int intExtra = intent.getIntExtra("slot", 0);
                        ServiceState newFromBundle = ServiceState.newFromBundle(intent.getExtras());
                        if (newFromBundle != null) {
                            this.this$0.carrierLogoVisibilityManager.serviceStateHash.put(new Integer(intExtra), new ServiceStateModel(newFromBundle.getState() == 0, newFromBundle.getRoaming()));
                        }
                        Unit unit = Unit.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }
            anonymousClass1 = new AnonymousClass1(continuation);
            Object obj22 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            i = anonymousClass1.label;
            if (i != 0) {
            }
            return Unit.INSTANCE;
        }
    }

    public CarrierHomeLogoViewController$special$$inlined$map$1(Flow flow, CarrierHomeLogoViewController carrierHomeLogoViewController) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = carrierHomeLogoViewController;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new C30002(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
