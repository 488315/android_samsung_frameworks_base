package com.android.app.motiontool;

import android.content.Context;
import android.view.View;
import android.view.WindowManagerGlobal;
import com.android.app.viewcapture.SimpleViewCapture;
import com.android.app.viewcapture.ViewCapture;
import com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda1;
import com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda5;
import com.android.app.viewcapture.data.FrameData;
import com.android.app.viewcapture.data.MotionWindowData;
import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionToolManager {
    public static final Companion Companion = new Companion(null);
    public static MotionToolManager INSTANCE;
    public int traceIdCounter;
    public final Map traces;
    public final SimpleViewCapture viewCapture;
    public final WindowManagerGlobal windowManagerGlobal;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ MotionToolManager(WindowManagerGlobal windowManagerGlobal, DefaultConstructorMarker defaultConstructorMarker) {
        this(windowManagerGlobal);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda0] */
    public final MotionWindowData getDataFromViewCapture(TraceMetadata traceMetadata) {
        WindowManagerGlobal windowManagerGlobal = this.windowManagerGlobal;
        String str = traceMetadata.windowId;
        final View rootView = windowManagerGlobal.getRootView(str);
        if (rootView == null) {
            throw new WindowNotFoundException(str);
        }
        final SimpleViewCapture simpleViewCapture = this.viewCapture;
        simpleViewCapture.getClass();
        ArrayList arrayList = new ArrayList();
        Context applicationContext = rootView.getContext().getApplicationContext();
        final ?? r3 = new Predicate() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ViewCapture.WindowListener) obj).mRoot.equals(rootView);
            }
        };
        int i = 0;
        Optional optional = (Optional) CompletableFuture.supplyAsync(new Supplier() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                ViewCapture viewCapture = simpleViewCapture;
                return viewCapture.mListeners.stream().filter(r3).toList();
            }
        }, ViewCapture.MAIN_EXECUTOR).thenApplyAsync((Function) new ViewCapture$$ExternalSyntheticLambda5(new ViewCapture.ViewIdProvider(applicationContext.getResources()), arrayList, i), simpleViewCapture.mBgExecutor).thenApply((Function) new ViewCapture$$ExternalSyntheticLambda1(arrayList, i)).get();
        MotionWindowData motionWindowData = optional != null ? (MotionWindowData) optional.orElse(null) : null;
        if (motionWindowData == null) {
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
        Iterator it = ((LinkedHashMap) this.traces).values().iterator();
        while (it.hasNext()) {
            ((TraceMetadata) it.next()).stopTrace.invoke();
        }
        ((LinkedHashMap) this.traces).clear();
        this.traceIdCounter = 0;
    }

    private MotionToolManager(WindowManagerGlobal windowManagerGlobal) {
        this.windowManagerGlobal = windowManagerGlobal;
        this.viewCapture = new SimpleViewCapture("MTViewCapture");
        this.traces = new LinkedHashMap();
    }
}
