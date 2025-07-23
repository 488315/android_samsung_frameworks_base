package com.android.app.displaylib;

import android.hardware.display.DisplayManager;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.TraceUtilsKt;
import java.util.Iterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DisplayRepositoryImpl implements DisplayRepository {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("DisplayRepository", 3);
    public final StateFlowImpl _ignoredDisplayIds;
    public final Flow allDisplayEvents;
    public final ReadonlyStateFlow connectedDisplayIds;
    public final Lazy defaultDisplay$delegate;
    public final Flow defaultDisplayOff;
    public final ChannelFlowTransformLatest displayAdditionEvent;
    public final DisplayRepositoryImpl$special$$inlined$map$1 displayChangeEvent;
    public final ReadonlyStateFlow displayIds;
    public final DisplayManager displayManager;
    public final DisplayRepositoryImpl$special$$inlined$map$2 displayRemovalEvent;
    public final ReadonlyStateFlow displays;
    public final ReadonlyStateFlow enabledDisplayIds;
    public final ReadonlyStateFlow enabledDisplays;
    public final Set initialDisplayIds;
    public final Set initialDisplays;
    public final Flow pendingDisplay;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x007e A[LOOP:0: B:13:0x0078->B:15:0x007e, LOOP_END] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DisplayRepositoryImpl(android.hardware.display.DisplayManager r10, android.os.Handler r11, kotlinx.coroutines.CoroutineScope r12, kotlinx.coroutines.CoroutineDispatcher r13) {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.app.displaylib.DisplayRepositoryImpl.<init>(android.hardware.display.DisplayManager, android.os.Handler, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineDispatcher):void");
    }

    public static Flow debugLog(Flow flow, String str) {
        if (!DEBUG) {
            return flow;
        }
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowTracing.traceEmissionCount$default(FlowTracing.INSTANCE, flow, str), new DisplayRepositoryImpl$debugLog$$inlined$traceEach$default$1(new TraceStateLogger(str, false, false, true, 6, null), null));
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDefaultDisplayOff() {
        return this.defaultDisplayOff;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Display getDisplay(int i) {
        Object obj;
        Iterator it = ((Iterable) this.displays.$$delegate_0.getValue()).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((Display) obj).getDisplayId() == i) {
                break;
            }
        }
        return (Display) obj;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayAdditionEvent() {
        return this.displayAdditionEvent;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayChangeEvent() {
        return this.displayChangeEvent;
    }

    public final Display getDisplayFromDisplayManager(int i) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("DisplayRepository#getDisplay");
        }
        try {
            return this.displayManager.getDisplay(i);
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final StateFlow getDisplayIds() {
        return this.displayIds;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayRemovalEvent() {
        return this.displayRemovalEvent;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final StateFlow getDisplays() {
        return this.displays;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getPendingDisplay() {
        return this.pendingDisplay;
    }
}
