package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1 implements Flow {
    public final /* synthetic */ String $hostPackage$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ PromotedNotificationsInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ String $hostPackage$inlined;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ PromotedNotificationsInteractor this$0;

        /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1$2$1, reason: invalid class name */
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
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector, PromotedNotificationsInteractor promotedNotificationsInteractor, String str) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = promotedNotificationsInteractor;
            this.$hostPackage$inlined = str;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
            /*
                Method dump skipped, instructions count: 300
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1(Flow flow, PromotedNotificationsInteractor promotedNotificationsInteractor, String str) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = promotedNotificationsInteractor;
        this.$hostPackage$inlined = str;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0, this.$hostPackage$inlined), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
