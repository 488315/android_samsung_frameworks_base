package com.android.systemui.media.controls.ui.controller;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.util.animation.MeasurementOutput;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaHostStatesManager implements Dumpable {
    public final Set callbacks = new LinkedHashSet();
    public final Set controllers = new LinkedHashSet();
    public final Map carouselSizes = new LinkedHashMap();
    public final Map mediaHostStates = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int intValue = ((Number) entry.getKey()).intValue();
            MeasurementOutput measurementOutput = (MeasurementOutput) entry.getValue();
            MagnificationImpl$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(intValue, measurementOutput.getMeasuredWidth(), "Size ", ": ", " x "), measurementOutput.getMeasuredHeight(), printWriter);
        }
        for (Map.Entry entry2 : ((LinkedHashMap) this.mediaHostStates).entrySet()) {
            printWriter.println("Host " + ((Number) entry2.getKey()).intValue() + ": visible " + ((MediaHostState) entry2.getValue()).getVisible());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0038, code lost:
    
        if (r7 != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        com.android.app.tracing.TraceUtilsKt.endSlice();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004f, code lost:
    
        if (r6 == null) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005a, code lost:
    
        if (r6.getMeasuredHeight() <= r1.getMeasuredHeight()) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005c, code lost:
    
        r1.setMeasuredHeight(r6.getMeasuredHeight());
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006f, code lost:
    
        if (r6.getMeasuredWidth() <= r1.getMeasuredWidth()) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0071, code lost:
    
        r1.setMeasuredWidth(r6.getMeasuredWidth());
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x004c, code lost:
    
        if (r7 != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.util.animation.MeasurementOutput updateCarouselDimensions(int r10, com.android.systemui.media.controls.ui.view.MediaHostState r11) {
        /*
            r9 = this;
            boolean r0 = android.os.Trace.isEnabled()
            if (r0 == 0) goto Lb
            java.lang.String r1 = "MediaHostStatesManager#updateCarouselDimensions"
            com.android.app.tracing.TraceUtilsKt.beginSlice(r1)
        Lb:
            com.android.systemui.util.animation.MeasurementOutput r1 = new com.android.systemui.util.animation.MeasurementOutput     // Catch: java.lang.Throwable -> L65
            r2 = 0
            r1.<init>(r2, r2)     // Catch: java.lang.Throwable -> L65
            java.util.Set r3 = r9.controllers     // Catch: java.lang.Throwable -> L65
            java.util.Iterator r3 = r3.iterator()     // Catch: java.lang.Throwable -> L65
            r4 = r2
        L18:
            boolean r5 = r3.hasNext()     // Catch: java.lang.Throwable -> L65
            if (r5 == 0) goto L81
            java.lang.Object r5 = r3.next()     // Catch: java.lang.Throwable -> L65
            com.android.systemui.media.controls.ui.controller.MediaViewController r5 = (com.android.systemui.media.controls.ui.controller.MediaViewController) r5     // Catch: java.lang.Throwable -> L65
            com.android.systemui.util.animation.MeasurementOutput r6 = r5.measurement     // Catch: java.lang.Throwable -> L65
            boolean r7 = android.os.Trace.isEnabled()     // Catch: java.lang.Throwable -> L65
            if (r7 == 0) goto L31
            java.lang.String r8 = "MediaViewController#getMeasurementsForState"
            com.android.app.tracing.TraceUtilsKt.beginSlice(r8)     // Catch: java.lang.Throwable -> L65
        L31:
            com.android.systemui.util.animation.TransitionViewState r5 = r5.obtainViewState(r11, r2)     // Catch: java.lang.Throwable -> L7a
            if (r5 != 0) goto L3e
            r6 = 0
            if (r7 == 0) goto L4f
        L3a:
            com.android.app.tracing.TraceUtilsKt.endSlice()     // Catch: java.lang.Throwable -> L65
            goto L4f
        L3e:
            int r8 = r5.getMeasureWidth()     // Catch: java.lang.Throwable -> L7a
            r6.setMeasuredWidth(r8)     // Catch: java.lang.Throwable -> L7a
            int r5 = r5.getMeasureHeight()     // Catch: java.lang.Throwable -> L7a
            r6.setMeasuredHeight(r5)     // Catch: java.lang.Throwable -> L7a
            if (r7 == 0) goto L4f
            goto L3a
        L4f:
            if (r6 == 0) goto L18
            int r5 = r6.getMeasuredHeight()     // Catch: java.lang.Throwable -> L65
            int r7 = r1.getMeasuredHeight()     // Catch: java.lang.Throwable -> L65
            r8 = 1
            if (r5 <= r7) goto L67
            int r4 = r6.getMeasuredHeight()     // Catch: java.lang.Throwable -> L65
            r1.setMeasuredHeight(r4)     // Catch: java.lang.Throwable -> L65
            r4 = r8
            goto L67
        L65:
            r9 = move-exception
            goto Lb0
        L67:
            int r5 = r6.getMeasuredWidth()     // Catch: java.lang.Throwable -> L65
            int r7 = r1.getMeasuredWidth()     // Catch: java.lang.Throwable -> L65
            if (r5 <= r7) goto L18
            int r4 = r6.getMeasuredWidth()     // Catch: java.lang.Throwable -> L65
            r1.setMeasuredWidth(r4)     // Catch: java.lang.Throwable -> L65
            r4 = r8
            goto L18
        L7a:
            r9 = move-exception
            if (r7 == 0) goto L80
            com.android.app.tracing.TraceUtilsKt.endSlice()     // Catch: java.lang.Throwable -> L65
        L80:
            throw r9     // Catch: java.lang.Throwable -> L65
        L81:
            java.lang.Integer r11 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L65
            java.util.Map r2 = r9.carouselSizes     // Catch: java.lang.Throwable -> L65
            boolean r11 = r2.containsKey(r11)     // Catch: java.lang.Throwable -> L65
            if (r11 == 0) goto L8f
            if (r4 == 0) goto L98
        L8f:
            java.lang.Integer r11 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L65
            java.util.Map r2 = r9.carouselSizes     // Catch: java.lang.Throwable -> L65
            r2.put(r11, r1)     // Catch: java.lang.Throwable -> L65
        L98:
            java.util.Map r9 = r9.carouselSizes     // Catch: java.lang.Throwable -> L65
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L65
            java.util.LinkedHashMap r9 = (java.util.LinkedHashMap) r9     // Catch: java.lang.Throwable -> L65
            java.lang.Object r9 = r9.get(r10)     // Catch: java.lang.Throwable -> L65
            com.android.systemui.util.animation.MeasurementOutput r9 = (com.android.systemui.util.animation.MeasurementOutput) r9     // Catch: java.lang.Throwable -> L65
            if (r9 != 0) goto La9
            goto Laa
        La9:
            r1 = r9
        Laa:
            if (r0 == 0) goto Laf
            com.android.app.tracing.TraceUtilsKt.endSlice()
        Laf:
            return r1
        Lb0:
            if (r0 == 0) goto Lb5
            com.android.app.tracing.TraceUtilsKt.endSlice()
        Lb5:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.updateCarouselDimensions(int, com.android.systemui.media.controls.ui.view.MediaHostState):com.android.systemui.util.animation.MeasurementOutput");
    }
}
