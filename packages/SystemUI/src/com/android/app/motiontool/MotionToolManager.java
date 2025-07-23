package com.android.app.motiontool;

import android.view.View;
import android.view.WindowManagerGlobal;
import com.android.app.viewcapture.SimpleViewCapture;
import com.android.app.viewcapture.ViewCapture;
import com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda4;
import com.android.app.viewcapture.data.FrameData;
import com.android.app.viewcapture.data.MotionWindowData;
import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MotionToolManager {
    public static final Companion Companion = new Companion(null);
    public static MotionToolManager INSTANCE;
    public int traceIdCounter;
    public final Map traces;
    public final SimpleViewCapture viewCapture;
    public final WindowManagerGlobal windowManagerGlobal;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ MotionToolManager(WindowManagerGlobal windowManagerGlobal, DefaultConstructorMarker defaultConstructorMarker) {
        this(windowManagerGlobal);
    }

    public final MotionWindowData getDataFromViewCapture(TraceMetadata traceMetadata) {
        MotionWindowData motionWindowData;
        WindowManagerGlobal windowManagerGlobal = this.windowManagerGlobal;
        String str = traceMetadata.windowId;
        final View rootView = windowManagerGlobal.getRootView(str);
        if (rootView == null) {
            throw new WindowNotFoundException(str);
        }
        SimpleViewCapture simpleViewCapture = this.viewCapture;
        simpleViewCapture.getClass();
        ArrayList arrayList = new ArrayList();
        Optional optional = (Optional) simpleViewCapture.getWindowData(rootView.getContext().getApplicationContext(), arrayList, new Predicate() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                View view = rootView;
                LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
                return ((ViewCapture.WindowListener) obj).mRoot.equals(view);
            }
        }).thenApply((Function) new ViewCapture$$ExternalSyntheticLambda4(arrayList, 0)).get();
        if (optional == null || (motionWindowData = (MotionWindowData) optional.orElse(null)) == null) {
            return (MotionWindowData) MotionWindowData.newBuilder().build();
        }
        Internal.ProtobufList frameDataList = motionWindowData.getFrameDataList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : frameDataList) {
            if (((FrameData) obj).getTimestamp() > traceMetadata.lastPolledTime) {
                arrayList2.add(obj);
            }
        }
        MotionWindowData.Builder builder = (MotionWindowData.Builder) motionWindowData.toBuilder();
        builder.copyOnWrite();
        MotionWindowData.access$500((MotionWindowData) builder.instance);
        builder.copyOnWrite();
        MotionWindowData.access$400((MotionWindowData) builder.instance, arrayList2);
        return (MotionWindowData) builder.build();
    }

    public final synchronized MotionWindowData pollTrace(int i) {
        MotionWindowData dataFromViewCapture;
        Object obj = ((LinkedHashMap) this.traces).get(Integer.valueOf(i));
        if (obj == null) {
            throw new UnknownTraceIdException(i);
        }
        TraceMetadata traceMetadata = (TraceMetadata) obj;
        dataFromViewCapture = getDataFromViewCapture(traceMetadata);
        traceMetadata.updateLastPolledTime(dataFromViewCapture);
        return dataFromViewCapture;
    }

    public final synchronized void reset() {
        try {
            Iterator it = ((LinkedHashMap) this.traces).values().iterator();
            while (it.hasNext()) {
                ((TraceMetadata) it.next()).stopTrace.invoke();
            }
            ((LinkedHashMap) this.traces).clear();
            this.traceIdCounter = 0;
        } catch (Throwable th) {
            throw th;
        }
    }

    private MotionToolManager(WindowManagerGlobal windowManagerGlobal) {
        this.windowManagerGlobal = windowManagerGlobal;
        this.viewCapture = new SimpleViewCapture("MTViewCapture");
        this.traces = new LinkedHashMap();
    }
}
