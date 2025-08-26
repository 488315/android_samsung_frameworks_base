package com.android.systemui.statusbar.pipeline.satellite.data.demo;

import android.os.Bundle;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import java.util.Locale;
import kotlin.ResultKt;
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

/* loaded from: classes3.dex */
public final class DemoDeviceBasedSatelliteDataSource {
    public static final DemoSatelliteEvent DEFAULT_VALUE;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 _satelliteCommands;
    public final ReadonlyStateFlow satelliteEvents;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        final Flow flowDemoFlowForCommand = demoModeController.demoFlowForCommand();
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteDataSource$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    DemoDeviceBasedSatelliteDataSource.DemoSatelliteEvent demoSatelliteEvent;
                    SatelliteConnectionState satelliteConnectionStateValueOf;
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
                        Bundle bundle = (Bundle) obj;
                        DemoDeviceBasedSatelliteDataSource.DemoSatelliteEvent demoSatelliteEvent2 = DemoDeviceBasedSatelliteDataSource.DEFAULT_VALUE;
                        this.this$0.getClass();
                        String string = bundle.getString("satellite");
                        if (string != null && string.equals("show")) {
                            String string2 = bundle.getString("connection");
                            if (string2 == null) {
                                satelliteConnectionStateValueOf = SatelliteConnectionState.Unknown;
                            } else {
                                try {
                                    if (string2.length() > 0) {
                                        string2 = ((Object) String.valueOf(string2.charAt(0)).toUpperCase(Locale.ROOT)) + string2.substring(1);
                                    }
                                    satelliteConnectionStateValueOf = SatelliteConnectionState.valueOf(string2);
                                } catch (IllegalArgumentException unused) {
                                    satelliteConnectionStateValueOf = SatelliteConnectionState.Unknown;
                                }
                            }
                            String string3 = bundle.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                            demoSatelliteEvent = new DemoDeviceBasedSatelliteDataSource.DemoSatelliteEvent(satelliteConnectionStateValueOf, string3 != null ? Integer.parseInt(string3) : 0);
                        } else {
                            demoSatelliteEvent = null;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(demoSatelliteEvent, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowDemoFlowForCommand.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this._satelliteCommands = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        this.satelliteEvents = FlowKt.stateIn(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), DEFAULT_VALUE);
    }
}
