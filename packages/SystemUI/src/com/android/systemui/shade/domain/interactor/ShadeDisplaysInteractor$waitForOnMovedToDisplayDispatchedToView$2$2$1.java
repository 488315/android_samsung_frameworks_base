package com.android.systemui.shade.domain.interactor;

import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.shade.ShadeTraceLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
final class ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $newDisplayId;
    int label;
    final /* synthetic */ ShadeDisplaysInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1(ShadeDisplaysInteractor shadeDisplaysInteractor, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeDisplaysInteractor;
        this.$newDisplayId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1(this.this$0, this.$newDisplayId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ReadonlyStateFlow readonlyStateFlow = ((ConfigurationRepositoryImpl) this.this$0.configurationRepository).onMovedToDisplay;
            final int i2 = this.$newDisplayId;
            Flow flow = new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1$invokeSuspend$$inlined$filter$1

                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ int $newDisplayId$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        Object L$1;
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

                    public AnonymousClass2(FlowCollector flowCollector, int i) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$newDisplayId$inlined = i;
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
                            if (((Number) obj).intValue() == this.$newDisplayId$inlined) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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
                    Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, i2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.first(flow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ShadeTraceLogger.INSTANCE.getClass();
        TrackTracer trackTracer = ShadeTraceLogger.t;
        int i3 = this.$newDisplayId;
        if (Trace.isEnabled()) {
            Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "onMovedToDisplay received with "));
        }
        return Unit.INSTANCE;
    }
}
