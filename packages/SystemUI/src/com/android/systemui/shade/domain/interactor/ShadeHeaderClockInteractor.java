package com.android.systemui.shade.domain.interactor;

import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.data.repository.ShadeHeaderClockRepository;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.time.SystemClock;
import java.util.Date;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class ShadeHeaderClockInteractor {
    public final ActivityStarter activityStarter;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ShadeHeaderClockInteractor$special$$inlined$map$1 currentTime;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 onTimezoneOrLocaleChanged = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), broadcastFlowForActions$default(this, new String[]{"android.intent.action.TIMEZONE_CHANGED", "android.intent.action.LOCALE_CHANGED"}));
    public final ShadeHeaderClockRepository repository;
    public final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1] */
    public ShadeHeaderClockInteractor(ShadeHeaderClockRepository shadeHeaderClockRepository, ActivityStarter activityStarter, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock) {
        this.repository = shadeHeaderClockRepository;
        this.activityStarter = activityStarter;
        this.broadcastDispatcher = broadcastDispatcher;
        this.systemClock = systemClock;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), broadcastFlowForActions$default(this, new String[]{"android.intent.action.TIME_TICK", "android.intent.action.TIME_SET"}));
        this.currentTime = new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeHeaderClockInteractor$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
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
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Date date = new Date(this.this$0.systemClock.currentTimeMillis());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(date, anonymousClass1) == coroutineSingletons) {
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
