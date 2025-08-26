package com.android.systemui.common.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.systemui.log.TouchHandlingViewLogger;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: classes.dex */
public final class TouchHandlingViewInteractionHandler {
    public final int allowedTouchSlop;
    public final Function0 isAttachedToWindow;
    public boolean isLongPressHandlingEnabled;
    public final Point lastEventDownCoordinate;
    public final TouchHandlingViewLogger logger;
    public Function0 longPressDuration;
    public final Function2 onLongPressDetected;
    public final Function2 onSingleTapDetected;
    public final Function2 postDelayed;
    public DisposableHandle scheduledLongPressHandle;

    public abstract class MotionEventModel {

        public final class Cancel extends MotionEventModel {
            public static final Cancel INSTANCE = new Cancel();

            private Cancel() {
                super(null);
            }
        }

        public final class Down extends MotionEventModel {
            public final int x;
            public final int y;

            public Down(int i, int i2) {
                super(null);
                this.x = i;
                this.y = i2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Down)) {
                    return false;
                }
                Down down = (Down) obj;
                return this.x == down.x && this.y == down.y;
            }

            public final int hashCode() {
                return Integer.hashCode(this.y) + (Integer.hashCode(this.x) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Down(x=");
                sb.append(this.x);
                sb.append(", y=");
                return ReorderTile$$ExternalSyntheticOutline0.m(this.y, ")", sb);
            }
        }

        public final class Move extends MotionEventModel {
            public final float distanceMoved;

            public Move(float f) {
                super(null);
                this.distanceMoved = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Move) && Float.compare(this.distanceMoved, ((Move) obj).distanceMoved) == 0;
            }

            public final int hashCode() {
                return Float.hashCode(this.distanceMoved);
            }

            public final String toString() {
                return "Move(distanceMoved=" + this.distanceMoved + ")";
            }
        }

        public final class Other extends MotionEventModel {
            public static final Other INSTANCE = new Other();

            private Other() {
                super(null);
            }
        }

        public final class Up extends MotionEventModel {
            public final float distanceMoved;
            public final long gestureDuration;

            public Up(float f, long j) {
                super(null);
                this.distanceMoved = f;
                this.gestureDuration = j;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Up)) {
                    return false;
                }
                Up up = (Up) obj;
                return Float.compare(this.distanceMoved, up.distanceMoved) == 0 && this.gestureDuration == up.gestureDuration;
            }

            public final int hashCode() {
                return Long.hashCode(this.gestureDuration) + (Float.hashCode(this.distanceMoved) * 31);
            }

            public final String toString() {
                return "Up(distanceMoved=" + this.distanceMoved + ", gestureDuration=" + this.gestureDuration + ")";
            }
        }

        public /* synthetic */ MotionEventModel(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private MotionEventModel() {
        }
    }

    public TouchHandlingViewInteractionHandler(Context context, Function2 function2, Function0 function0, Function2 function22, Function2 function23, Function0 function02, Function0 function03, int i, TouchHandlingViewLogger touchHandlingViewLogger) {
        this.postDelayed = function2;
        this.isAttachedToWindow = function0;
        this.onLongPressDetected = function22;
        this.onSingleTapDetected = function23;
        this.longPressDuration = function03;
        this.allowedTouchSlop = i;
        this.logger = touchHandlingViewLogger;
        this.lastEventDownCoordinate = new Point(-1, -1);
        new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.common.ui.view.TouchHandlingViewInteractionHandler$gestureDetector$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onDoubleTap(MotionEvent motionEvent) {
                this.this$0.getClass();
                return false;
            }
        });
    }

    public static float distanceMoved(MotionEvent motionEvent) {
        if (motionEvent.getHistorySize() <= 0) {
            return 0.0f;
        }
        double d = 2;
        return (float) Math.sqrt(((float) Math.pow(motionEvent.getX() - motionEvent.getHistoricalX(0), d)) + ((float) Math.pow(motionEvent.getY() - motionEvent.getHistoricalY(0), d)));
    }

    public /* synthetic */ TouchHandlingViewInteractionHandler(Context context, Function2 function2, Function0 function0, Function2 function22, Function2 function23, Function0 function02, Function0 function03, int i, TouchHandlingViewLogger touchHandlingViewLogger, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, function2, function0, function22, function23, function02, function03, i, (i2 & 256) != 0 ? null : touchHandlingViewLogger);
    }
}
