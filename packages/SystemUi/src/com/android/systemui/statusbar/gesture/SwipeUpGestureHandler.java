package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.settings.DisplayTracker;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SwipeUpGestureHandler extends GenericGestureDetector {
    public final SwipeUpGestureLogger logger;
    public final String loggerTag;
    public boolean monitoringCurrentTouch;
    public long startTime;
    public float startY;
    public final int swipeDistanceThreshold;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SwipeUpGestureHandler(Context context, DisplayTracker displayTracker, SwipeUpGestureLogger swipeUpGestureLogger, String str) {
        super(r3, 0);
        String simpleName = Reflection.getOrCreateKotlinClass(SwipeUpGestureHandler.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.logger = swipeUpGestureLogger;
        this.loggerTag = str;
        this.swipeDistanceThreshold = context.getResources().getDimensionPixelSize(17106189);
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void onInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            int actionMasked = motionEvent.getActionMasked();
            String str = this.loggerTag;
            SwipeUpGestureLogger swipeUpGestureLogger = this.logger;
            if (actionMasked == 0) {
                if (!startOfGestureIsWithinBounds(motionEvent)) {
                    this.monitoringCurrentTouch = false;
                    return;
                }
                int y = (int) motionEvent.getY();
                swipeUpGestureLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                SwipeUpGestureLogger$logGestureDetectionStarted$2 swipeUpGestureLogger$logGestureDetectionStarted$2 = new Function1() { // from class: com.android.systemui.statusbar.gesture.SwipeUpGestureLogger$logGestureDetectionStarted$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AbstractC0000x2c234b15.m0m("Beginning gesture detection. y=", ((LogMessage) obj).getInt1());
                    }
                };
                LogBuffer logBuffer = swipeUpGestureLogger.buffer;
                LogMessage obtain = logBuffer.obtain(str, logLevel, swipeUpGestureLogger$logGestureDetectionStarted$2, null);
                obtain.setInt1(y);
                logBuffer.commit(obtain);
                this.startY = motionEvent.getY();
                this.startTime = motionEvent.getEventTime();
                this.monitoringCurrentTouch = true;
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (this.monitoringCurrentTouch) {
                        float y2 = motionEvent.getY();
                        float f = this.startY;
                        if (y2 >= f || f - motionEvent.getY() < this.swipeDistanceThreshold || motionEvent.getEventTime() - this.startTime >= 500) {
                            return;
                        }
                        this.monitoringCurrentTouch = false;
                        int y3 = (int) motionEvent.getY();
                        swipeUpGestureLogger.getClass();
                        LogLevel logLevel2 = LogLevel.INFO;
                        SwipeUpGestureLogger$logGestureDetected$2 swipeUpGestureLogger$logGestureDetected$2 = new Function1() { // from class: com.android.systemui.statusbar.gesture.SwipeUpGestureLogger$logGestureDetected$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return AbstractC0000x2c234b15.m0m("Gesture detected; notifying callbacks. y=", ((LogMessage) obj).getInt1());
                            }
                        };
                        LogBuffer logBuffer2 = swipeUpGestureLogger.buffer;
                        LogMessage obtain2 = logBuffer2.obtain(str, logLevel2, swipeUpGestureLogger$logGestureDetected$2, null);
                        obtain2.setInt1(y3);
                        logBuffer2.commit(obtain2);
                        Iterator it = ((LinkedHashMap) this.callbacks).values().iterator();
                        while (it.hasNext()) {
                            ((Function1) it.next()).invoke(motionEvent);
                        }
                        return;
                    }
                    return;
                }
                if (actionMasked != 3) {
                    return;
                }
            }
            if (this.monitoringCurrentTouch) {
                int y4 = (int) motionEvent.getY();
                swipeUpGestureLogger.getClass();
                LogLevel logLevel3 = LogLevel.DEBUG;
                SwipeUpGestureLogger$logGestureDetectionEndedWithoutTriggering$2 swipeUpGestureLogger$logGestureDetectionEndedWithoutTriggering$2 = new Function1() { // from class: com.android.systemui.statusbar.gesture.SwipeUpGestureLogger$logGestureDetectionEndedWithoutTriggering$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AbstractC0000x2c234b15.m0m("Gesture finished; no swipe up gesture detected. Final y=", ((LogMessage) obj).getInt1());
                    }
                };
                LogBuffer logBuffer3 = swipeUpGestureLogger.buffer;
                LogMessage obtain3 = logBuffer3.obtain(str, logLevel3, swipeUpGestureLogger$logGestureDetectionEndedWithoutTriggering$2, null);
                obtain3.setInt1(y4);
                logBuffer3.commit(obtain3);
            }
            this.monitoringCurrentTouch = false;
        }
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    /* renamed from: startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo200x8843713a() {
        super.mo200x8843713a();
        SwipeUpGestureLogger swipeUpGestureLogger = this.logger;
        swipeUpGestureLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        SwipeUpGestureLogger$logInputListeningStarted$2 swipeUpGestureLogger$logInputListeningStarted$2 = new Function1() { // from class: com.android.systemui.statusbar.gesture.SwipeUpGestureLogger$logInputListeningStarted$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Input listening started ";
            }
        };
        LogBuffer logBuffer = swipeUpGestureLogger.buffer;
        logBuffer.commit(logBuffer.obtain(this.loggerTag, logLevel, swipeUpGestureLogger$logInputListeningStarted$2, null));
    }

    public abstract boolean startOfGestureIsWithinBounds(MotionEvent motionEvent);

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    /* renamed from: stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo201xfadba4da() {
        super.mo201xfadba4da();
        SwipeUpGestureLogger swipeUpGestureLogger = this.logger;
        swipeUpGestureLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        SwipeUpGestureLogger$logInputListeningStopped$2 swipeUpGestureLogger$logInputListeningStopped$2 = new Function1() { // from class: com.android.systemui.statusbar.gesture.SwipeUpGestureLogger$logInputListeningStopped$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Input listening stopped ";
            }
        };
        LogBuffer logBuffer = swipeUpGestureLogger.buffer;
        logBuffer.commit(logBuffer.obtain(this.loggerTag, logLevel, swipeUpGestureLogger$logInputListeningStopped$2, null));
    }
}
