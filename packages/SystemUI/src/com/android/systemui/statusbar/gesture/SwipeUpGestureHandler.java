package com.android.systemui.statusbar.gesture;

import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SwipeUpGestureHandler(android.content.Context r2, com.android.systemui.settings.DisplayTracker r3, com.android.systemui.statusbar.gesture.SwipeUpGestureLogger r4, java.lang.String r5) {
        /*
            r1 = this;
            java.lang.Class<com.android.systemui.statusbar.gesture.SwipeUpGestureHandler> r0 = com.android.systemui.statusbar.gesture.SwipeUpGestureHandler.class
            kotlin.jvm.internal.ClassReference r0 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r0)
            java.lang.String r0 = r0.getSimpleName()
            r0.getClass()
            r3.getClass()
            r3 = 0
            r1.<init>(r0, r3)
            r1.logger = r4
            r1.loggerTag = r5
            android.content.res.Resources r2 = r2.getResources()
            r3 = 17106391(0x10505d7, float:2.4432432E-38)
            int r2 = r2.getDimensionPixelSize(r3)
            r1.swipeDistanceThreshold = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.gesture.SwipeUpGestureHandler.<init>(android.content.Context, com.android.systemui.settings.DisplayTracker, com.android.systemui.statusbar.gesture.SwipeUpGestureLogger, java.lang.String):void");
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
                SwipeUpGestureLogger$$ExternalSyntheticLambda0 swipeUpGestureLogger$$ExternalSyntheticLambda0 = new SwipeUpGestureLogger$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer = swipeUpGestureLogger.buffer;
                LogMessage obtain = logBuffer.obtain(str, logLevel, swipeUpGestureLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).int1 = y;
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
                        SwipeUpGestureLogger$$ExternalSyntheticLambda0 swipeUpGestureLogger$$ExternalSyntheticLambda02 = new SwipeUpGestureLogger$$ExternalSyntheticLambda0(3);
                        LogBuffer logBuffer2 = swipeUpGestureLogger.buffer;
                        LogMessage obtain2 = logBuffer2.obtain(str, logLevel2, swipeUpGestureLogger$$ExternalSyntheticLambda02, null);
                        ((LogMessageImpl) obtain2).int1 = y3;
                        logBuffer2.commit(obtain2);
                        onGestureDetected$frameworks__base__packages__SystemUI__android_common__SystemUI_core(motionEvent);
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
                SwipeUpGestureLogger$$ExternalSyntheticLambda0 swipeUpGestureLogger$$ExternalSyntheticLambda03 = new SwipeUpGestureLogger$$ExternalSyntheticLambda0(0);
                LogBuffer logBuffer3 = swipeUpGestureLogger.buffer;
                LogMessage obtain3 = logBuffer3.obtain(str, logLevel3, swipeUpGestureLogger$$ExternalSyntheticLambda03, null);
                ((LogMessageImpl) obtain3).int1 = y4;
                logBuffer3.commit(obtain3);
            }
            this.monitoringCurrentTouch = false;
        }
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        super.startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        SwipeUpGestureLogger swipeUpGestureLogger = this.logger;
        swipeUpGestureLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        SwipeUpGestureLogger$$ExternalSyntheticLambda0 swipeUpGestureLogger$$ExternalSyntheticLambda0 = new SwipeUpGestureLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = swipeUpGestureLogger.buffer;
        logBuffer.commit(logBuffer.obtain(this.loggerTag, logLevel, swipeUpGestureLogger$$ExternalSyntheticLambda0, null));
    }

    public abstract boolean startOfGestureIsWithinBounds(MotionEvent motionEvent);

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        super.stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        SwipeUpGestureLogger swipeUpGestureLogger = this.logger;
        swipeUpGestureLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        SwipeUpGestureLogger$$ExternalSyntheticLambda0 swipeUpGestureLogger$$ExternalSyntheticLambda0 = new SwipeUpGestureLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = swipeUpGestureLogger.buffer;
        logBuffer.commit(logBuffer.obtain(this.loggerTag, logLevel, swipeUpGestureLogger$$ExternalSyntheticLambda0, null));
    }
}
