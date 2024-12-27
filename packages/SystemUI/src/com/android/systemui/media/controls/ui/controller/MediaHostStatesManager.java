package com.android.systemui.media.controls.ui.controller;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.TransitionViewState;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class MediaHostStatesManager {
    public final Set callbacks = new LinkedHashSet();
    public final Set controllers = new LinkedHashSet();
    public final Map carouselSizes = new LinkedHashMap();
    public final Map mediaHostStates = new LinkedHashMap();

    public interface Callback {
        void onHostStateChanged(int i, MediaHostState mediaHostState);
    }

    public final MeasurementOutput updateCarouselDimensions(int i, MediaHostState mediaHostState) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaHostStatesManager#updateCarouselDimensions");
        }
        try {
            MeasurementOutput measurementOutput = new MeasurementOutput(0, 0);
            for (MediaViewController mediaViewController : this.controllers) {
                MeasurementOutput measurementOutput2 = mediaViewController.measurement;
                isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice("MediaViewController#getMeasurementsForState");
                }
                try {
                    TransitionViewState obtainViewState = mediaViewController.obtainViewState(mediaHostState, false);
                    if (obtainViewState == null) {
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                        measurementOutput2 = null;
                    } else {
                        measurementOutput2.setMeasuredWidth(obtainViewState.getMeasureWidth());
                        measurementOutput2.setMeasuredHeight(obtainViewState.getMeasureHeight());
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                    if (measurementOutput2 != null) {
                        if (measurementOutput2.getMeasuredHeight() > measurementOutput.getMeasuredHeight()) {
                            measurementOutput.setMeasuredHeight(measurementOutput2.getMeasuredHeight());
                        }
                        if (measurementOutput2.getMeasuredWidth() > measurementOutput.getMeasuredWidth()) {
                            measurementOutput.setMeasuredWidth(measurementOutput2.getMeasuredWidth());
                        }
                    }
                } finally {
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
            this.carouselSizes.put(Integer.valueOf(i), measurementOutput);
            return measurementOutput;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
