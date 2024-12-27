package com.android.systemui.statusbar.pipeline.satellite.data.demo;

import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class DemoDeviceBasedSatelliteDataSource {
    public static final DemoSatelliteEvent DEFAULT_VALUE;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 _satelliteCommands;
    public final ReadonlyStateFlow satelliteEvents;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class DemoSatelliteEvent {
        public final SatelliteConnectionState connectionState;
        public final int signalStrength;

        public DemoSatelliteEvent(SatelliteConnectionState satelliteConnectionState, int i) {
            this.connectionState = satelliteConnectionState;
            this.signalStrength = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DemoSatelliteEvent)) {
                return false;
            }
            DemoSatelliteEvent demoSatelliteEvent = (DemoSatelliteEvent) obj;
            return this.connectionState == demoSatelliteEvent.connectionState && this.signalStrength == demoSatelliteEvent.signalStrength;
        }

        public final int hashCode() {
            return Integer.hashCode(this.signalStrength) + (this.connectionState.hashCode() * 31);
        }

        public final String toString() {
            return "DemoSatelliteEvent(connectionState=" + this.connectionState + ", signalStrength=" + this.signalStrength + ")";
        }
    }

    static {
        new Companion(null);
        DEFAULT_VALUE = new DemoSatelliteEvent(SatelliteConnectionState.Unknown, 0);
    }

    public DemoDeviceBasedSatelliteDataSource(DemoModeController demoModeController, CoroutineScope coroutineScope) {
        final Flow demoFlowForCommand = demoModeController.demoFlowForCommand();
        FlowKt.stateIn(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoDeviceBasedSatelliteDataSource this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DemoDeviceBasedSatelliteDataSource demoDeviceBasedSatelliteDataSource) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoDeviceBasedSatelliteDataSource;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto La7
                    L28:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L30:
                        kotlin.ResultKt.throwOnFailure(r10)
                        android.os.Bundle r9 = (android.os.Bundle) r9
                        com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$DemoSatelliteEvent r10 = com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource.DEFAULT_VALUE
                        com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource r10 = r8.this$0
                        r10.getClass()
                        java.lang.String r10 = "satellite"
                        java.lang.String r10 = r9.getString(r10)
                        r2 = 0
                        if (r10 != 0) goto L47
                        goto L9c
                    L47:
                        java.lang.String r4 = "show"
                        boolean r10 = r10.equals(r4)
                        if (r10 != 0) goto L51
                        goto L9c
                    L51:
                        com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$DemoSatelliteEvent r2 = new com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$DemoSatelliteEvent
                        java.lang.String r10 = "connection"
                        java.lang.String r10 = r9.getString(r10)
                        r4 = 0
                        if (r10 != 0) goto L5f
                        com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState r10 = com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState.Unknown
                        goto L8d
                    L5f:
                        int r5 = r10.length()     // Catch: java.lang.IllegalArgumentException -> L8b
                        if (r5 <= 0) goto L86
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.IllegalArgumentException -> L8b
                        r5.<init>()     // Catch: java.lang.IllegalArgumentException -> L8b
                        char r6 = r10.charAt(r4)     // Catch: java.lang.IllegalArgumentException -> L8b
                        java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch: java.lang.IllegalArgumentException -> L8b
                        java.util.Locale r7 = java.util.Locale.ROOT     // Catch: java.lang.IllegalArgumentException -> L8b
                        java.lang.String r6 = r6.toUpperCase(r7)     // Catch: java.lang.IllegalArgumentException -> L8b
                        r5.append(r6)     // Catch: java.lang.IllegalArgumentException -> L8b
                        java.lang.String r10 = r10.substring(r3)     // Catch: java.lang.IllegalArgumentException -> L8b
                        r5.append(r10)     // Catch: java.lang.IllegalArgumentException -> L8b
                        java.lang.String r10 = r5.toString()     // Catch: java.lang.IllegalArgumentException -> L8b
                    L86:
                        com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState r10 = com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState.valueOf(r10)     // Catch: java.lang.IllegalArgumentException -> L8b
                        goto L8d
                    L8b:
                        com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState r10 = com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState.Unknown
                    L8d:
                        java.lang.String r5 = "level"
                        java.lang.String r9 = r9.getString(r5)
                        if (r9 == 0) goto L99
                        int r4 = java.lang.Integer.parseInt(r9)
                    L99:
                        r2.<init>(r10, r4)
                    L9c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r2, r0)
                        if (r8 != r1) goto La7
                        return r1
                    La7:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), DEFAULT_VALUE);
    }
}
