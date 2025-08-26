package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.DisplayTracker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public abstract class SwipeUpGestureHandler extends GenericGestureDetector {
    public final SwipeUpGestureLogger logger;
    public final String loggerTag;
    public boolean monitoringCurrentTouch;
    public long startTime;
    public float startY;
    public final int swipeDistanceThreshold;

    /* JADX WARN: Illegal instructions before constructor call */
    public SwipeUpGestureHandler(Context context, DisplayTracker displayTracker, SwipeUpGestureLogger swipeUpGestureLogger, String str) {
        String simpleName = Reflection.getOrCreateKotlinClass(SwipeUpGestureHandler.class).getSimpleName();
        simpleName.getClass();
        displayTracker.getClass();
        super(simpleName, 0);
        this.logger = swipeUpGestureLogger;
        this.loggerTag = str;
        this.swipeDistanceThreshold = context.getResources().getDimensionPixelSize(17106392);
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
                LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, swipeUpGestureLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).int1 = y;
                logBuffer.commit(logMessageObtain);
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
                        LogMessage logMessageObtain2 = logBuffer2.obtain(str, logLevel2, swipeUpGestureLogger$$ExternalSyntheticLambda02, null);
                        ((LogMessageImpl) logMessageObtain2).int1 = y3;
                        logBuffer2.commit(logMessageObtain2);
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
                LogMessage logMessageObtain3 = logBuffer3.obtain(str, logLevel3, swipeUpGestureLogger$$ExternalSyntheticLambda03, null);
                ((LogMessageImpl) logMessageObtain3).int1 = y4;
                logBuffer3.commit(logMessageObtain3);
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
