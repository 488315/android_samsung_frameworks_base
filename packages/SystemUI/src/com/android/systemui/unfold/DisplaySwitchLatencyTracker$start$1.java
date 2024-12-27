package com.android.systemui.unfold;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DisplaySwitchLatencyTracker$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplaySwitchLatencyTracker$start$1(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displaySwitchLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DisplaySwitchLatencyTracker$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplaySwitchLatencyTracker$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Flow pairwise = FlowKt.pairwise(((DeviceStateRepositoryImpl) this.this$0.deviceStateRepository).state);
            ChannelFlowTransformLatest transformLatest = kotlinx.coroutines.flow.FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
                            com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1$2$1
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L4e
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            r7 = r6
                            com.android.systemui.util.kotlin.WithPrev r7 = (com.android.systemui.util.kotlin.WithPrev) r7
                            java.lang.Object r2 = r7.getPreviousValue()
                            com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r4 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.FOLDED
                            if (r2 == r4) goto L43
                            java.lang.Object r7 = r7.getNewValue()
                            if (r7 != r4) goto L4e
                        L43:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                            java.lang.Object r5 = r5.emit(r6, r0)
                            if (r5 != r1) goto L4e
                            return r1
                        L4e:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, new DisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$flatMapLatest$1(null, this.this$0));
            final DisplaySwitchLatencyTracker displaySwitchLatencyTracker = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent = (DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent) obj2;
                    DisplaySwitchLatencyTracker.this.displaySwitchLatencyLogger.getClass();
                    int[] intArray = CollectionsKt___CollectionsKt.toIntArray(displaySwitchLatencyEvent.fromVisibleAppsUid);
                    int[] intArray2 = CollectionsKt___CollectionsKt.toIntArray(displaySwitchLatencyEvent.toVisibleAppsUid);
                    StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                    newBuilder.setAtomId(753);
                    newBuilder.writeInt(displaySwitchLatencyEvent.latencyMs);
                    newBuilder.writeInt(displaySwitchLatencyEvent.fromFoldableDeviceState);
                    newBuilder.writeInt(displaySwitchLatencyEvent.fromState);
                    newBuilder.writeInt(displaySwitchLatencyEvent.fromFocusedAppUid);
                    newBuilder.addBooleanAnnotation((byte) 1, true);
                    newBuilder.writeInt(displaySwitchLatencyEvent.fromPipAppUid);
                    newBuilder.addBooleanAnnotation((byte) 1, true);
                    newBuilder.writeIntArray(intArray);
                    newBuilder.addBooleanAnnotation((byte) 1, true);
                    newBuilder.writeInt(displaySwitchLatencyEvent.fromDensityDpi);
                    newBuilder.writeInt(displaySwitchLatencyEvent.toState);
                    newBuilder.writeInt(displaySwitchLatencyEvent.toFoldableDeviceState);
                    newBuilder.writeInt(displaySwitchLatencyEvent.toFocusedAppUid);
                    newBuilder.addBooleanAnnotation((byte) 1, true);
                    newBuilder.writeInt(displaySwitchLatencyEvent.toPipAppUid);
                    newBuilder.addBooleanAnnotation((byte) 1, true);
                    newBuilder.writeIntArray(intArray2);
                    newBuilder.addBooleanAnnotation((byte) 1, true);
                    newBuilder.writeInt(displaySwitchLatencyEvent.toDensityDpi);
                    newBuilder.writeInt(displaySwitchLatencyEvent.notificationCount);
                    newBuilder.writeInt(displaySwitchLatencyEvent.externalDisplayCount);
                    newBuilder.writeInt(displaySwitchLatencyEvent.throttlingLevel);
                    newBuilder.writeInt(displaySwitchLatencyEvent.vskinTemperatureC);
                    newBuilder.writeInt(displaySwitchLatencyEvent.hallSensorToFirstHingeAngleChangeMs);
                    newBuilder.writeInt(displaySwitchLatencyEvent.hallSensorToDeviceStateChangeMs);
                    newBuilder.writeInt(displaySwitchLatencyEvent.onScreenTurningOnToOnDrawnMs);
                    newBuilder.writeInt(displaySwitchLatencyEvent.onDrawnToOnScreenTurnedOnMs);
                    newBuilder.usePooledBuffer();
                    StatsLog.write(newBuilder.build());
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transformLatest.collect(flowCollector, this) == coroutineSingletons) {
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
