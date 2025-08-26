package com.android.systemui.media.controls.ui.controller;

import android.os.Trace;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.TransitionViewState;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class MediaHostStatesManager implements Dumpable {
    public final Set callbacks = new LinkedHashSet();
    public final Set controllers = new LinkedHashSet();
    public final Map carouselSizes = new LinkedHashMap();
    public final Map mediaHostStates = new LinkedHashMap();

    public interface Callback {
        void onHostStateChanged(int i, MediaHostState mediaHostState);
    }

    public MediaHostStatesManager(DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("MediaHostStatesManager", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Controllers: " + this.controllers);
        printWriter.println("Callbacks: " + this.callbacks);
        for (Map.Entry entry : ((LinkedHashMap) this.carouselSizes).entrySet()) {
            int iIntValue = ((Number) entry.getKey()).intValue();
            MeasurementOutput measurementOutput = (MeasurementOutput) entry.getValue();
            MagnificationImpl$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(iIntValue, measurementOutput.getMeasuredWidth(), "Size ", ": ", " x "), measurementOutput.getMeasuredHeight(), printWriter);
        }
        for (Map.Entry entry2 : ((LinkedHashMap) this.mediaHostStates).entrySet()) {
            printWriter.println("Host " + ((Number) entry2.getKey()).intValue() + ": visible " + ((MediaHostState) entry2.getValue()).getVisible());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003a A[Catch: all -> 0x0065, PHI: r6
      0x003a: PHI (r6v2 com.android.systemui.util.animation.MeasurementOutput) = 
      (r6v0 com.android.systemui.util.animation.MeasurementOutput)
      (r6v3 com.android.systemui.util.animation.MeasurementOutput)
     binds: [B:18:0x004c, B:14:0x0038] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0065, blocks: (B:5:0x000b, B:6:0x0018, B:8:0x001e, B:10:0x002c, B:15:0x003a, B:21:0x0051, B:23:0x005c, B:26:0x0067, B:28:0x0071, B:31:0x007d, B:32:0x0080, B:33:0x0081, B:37:0x0098, B:36:0x008f, B:11:0x0031, B:17:0x003e), top: B:47:0x000b, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final MeasurementOutput updateCarouselDimensions(int i, MediaHostState mediaHostState) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaHostStatesManager#updateCarouselDimensions");
        }
        try {
            MeasurementOutput measurementOutput = new MeasurementOutput(0, 0);
            boolean z = false;
            for (MediaViewController mediaViewController : this.controllers) {
                MeasurementOutput measurementOutput2 = mediaViewController.measurement;
                zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice("MediaViewController#getMeasurementsForState");
                }
                try {
                    TransitionViewState transitionViewStateObtainViewState = mediaViewController.obtainViewState(mediaHostState, false);
                    if (transitionViewStateObtainViewState == null) {
                        measurementOutput2 = null;
                        if (zIsEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    } else {
                        measurementOutput2.setMeasuredWidth(transitionViewStateObtainViewState.getMeasureWidth());
                        measurementOutput2.setMeasuredHeight(transitionViewStateObtainViewState.getMeasureHeight());
                        if (zIsEnabled) {
                        }
                    }
                    if (measurementOutput2 != null) {
                        if (measurementOutput2.getMeasuredHeight() > measurementOutput.getMeasuredHeight()) {
                            measurementOutput.setMeasuredHeight(measurementOutput2.getMeasuredHeight());
                            z = true;
                        }
                        if (measurementOutput2.getMeasuredWidth() > measurementOutput.getMeasuredWidth()) {
                            measurementOutput.setMeasuredWidth(measurementOutput2.getMeasuredWidth());
                            z = true;
                        }
                    }
                } finally {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
            if (!this.carouselSizes.containsKey(Integer.valueOf(i)) || z) {
                this.carouselSizes.put(Integer.valueOf(i), measurementOutput);
            }
            MeasurementOutput measurementOutput3 = (MeasurementOutput) ((LinkedHashMap) this.carouselSizes).get(Integer.valueOf(i));
            if (measurementOutput3 != null) {
                measurementOutput = measurementOutput3;
            }
            return measurementOutput;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
