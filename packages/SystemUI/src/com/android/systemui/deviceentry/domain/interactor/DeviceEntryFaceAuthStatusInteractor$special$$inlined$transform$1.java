package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.biometrics.FaceHelpMessageDebouncer;
import com.android.systemui.deviceentry.shared.model.AcquiredFaceAuthenticationStatus;
import com.android.systemui.deviceentry.shared.model.FaceAuthenticationStatus;
import com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthStatusInteractor this$0;

    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ DeviceEntryFaceAuthStatusInteractor this$0;

        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C01791 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C01791(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector, DeviceEntryFaceAuthStatusInteractor deviceEntryFaceAuthStatusInteractor) {
            this.this$0 = deviceEntryFaceAuthStatusInteractor;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x007f, code lost:
        
            if (r8.emit(r9, r0) == r1) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0088, code lost:
        
            if (r8.emit(r9, r0) == r1) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x008a, code lost:
        
            return r1;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            C01791 c01791;
            if (continuation instanceof C01791) {
                c01791 = (C01791) continuation;
                int i = c01791.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    c01791.label = i - Integer.MIN_VALUE;
                } else {
                    c01791 = new C01791(continuation);
                }
            }
            Object obj2 = c01791.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = c01791.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                FaceAuthenticationStatus faceAuthenticationStatus = (FaceAuthenticationStatus) obj;
                boolean z = faceAuthenticationStatus instanceof AcquiredFaceAuthenticationStatus;
                DeviceEntryFaceAuthStatusInteractor deviceEntryFaceAuthStatusInteractor = this.this$0;
                if (z) {
                    AcquiredFaceAuthenticationStatus acquiredFaceAuthenticationStatus = (AcquiredFaceAuthenticationStatus) faceAuthenticationStatus;
                    if (acquiredFaceAuthenticationStatus.acquiredInfo == 20) {
                        deviceEntryFaceAuthStatusInteractor.faceHelpMessageDebouncer.startNewFaceAuthSession(acquiredFaceAuthenticationStatus.createdAt);
                    }
                }
                boolean z2 = faceAuthenticationStatus instanceof HelpFaceAuthenticationStatus;
                FlowCollector flowCollector = this.$$this$flow;
                if (z2) {
                    HelpFaceAuthenticationStatus helpFaceAuthenticationStatus = (HelpFaceAuthenticationStatus) faceAuthenticationStatus;
                    boolean zContains = deviceEntryFaceAuthStatusInteractor.faceAcquiredInfoIgnoreList.contains(new Integer(helpFaceAuthenticationStatus.msgId));
                    FaceHelpMessageDebouncer faceHelpMessageDebouncer = deviceEntryFaceAuthStatusInteractor.faceHelpMessageDebouncer;
                    if (!zContains) {
                        ((ArrayList) faceHelpMessageDebouncer.helpFaceAuthStatuses).add(helpFaceAuthenticationStatus);
                        Objects.toString(helpFaceAuthenticationStatus);
                    }
                    HelpFaceAuthenticationStatus messageToShow = faceHelpMessageDebouncer.getMessageToShow(helpFaceAuthenticationStatus.createdAt);
                    if (messageToShow != null) {
                        c01791.label = 1;
                    }
                } else {
                    c01791.label = 2;
                }
            } else {
                if (i2 != 1 && i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1(Flow flow, Continuation continuation, DeviceEntryFaceAuthStatusInteractor deviceEntryFaceAuthStatusInteractor) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = deviceEntryFaceAuthStatusInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1 deviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1 = new DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1(this.$this_transform, continuation, this.this$0);
        deviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1.L$0 = obj;
        return deviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
