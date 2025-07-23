package com.android.systemui.shade.domain.interactor;

import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.data.repository.ShadeHeaderClockRepository;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeHeaderClockInteractor {
    public final ActivityStarter activityStarter;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ShadeHeaderClockInteractor$special$$inlined$map$1 currentTime;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 onTimezoneOrLocaleChanged = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), broadcastFlowForActions$default(this, new String[]{"android.intent.action.TIMEZONE_CHANGED", "android.intent.action.LOCALE_CHANGED"}));
    public final ShadeHeaderClockRepository repository;
    public final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1] */
    public ShadeHeaderClockInteractor(ShadeHeaderClockRepository shadeHeaderClockRepository, ActivityStarter activityStarter, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock) {
        this.repository = shadeHeaderClockRepository;
        this.activityStarter = activityStarter;
        this.broadcastDispatcher = broadcastDispatcher;
        this.systemClock = systemClock;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), broadcastFlowForActions$default(this, new String[]{"android.intent.action.TIME_TICK", "android.intent.action.TIME_SET"}));
        this.currentTime = new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ShadeHeaderClockInteractor this$0;

                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ShadeHeaderClockInteractor shadeHeaderClockInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = shadeHeaderClockInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlin.Unit r7 = (kotlin.Unit) r7
                        java.util.Date r7 = new java.util.Date
                        com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor r8 = r6.this$0
                        com.android.systemui.util.time.SystemClock r8 = r8.systemClock
                        long r4 = r8.currentTimeMillis()
                        r7.<init>(r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    public static Flow broadcastFlowForActions$default(ShadeHeaderClockInteractor shadeHeaderClockInteractor, String[] strArr) {
        UserHandle userHandle = UserHandle.SYSTEM;
        IntentFilter intentFilter = new IntentFilter();
        for (String str : strArr) {
            intentFilter.addAction(str);
        }
        return BroadcastDispatcher.broadcastFlow$default(shadeHeaderClockInteractor.broadcastDispatcher, intentFilter, userHandle, 12);
    }
}
