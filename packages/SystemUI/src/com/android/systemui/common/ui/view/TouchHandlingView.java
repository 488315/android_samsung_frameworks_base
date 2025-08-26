package com.android.systemui.common.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.common.ui.view.TouchHandlingViewInteractionHandler;
import com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$1;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.TouchHandlingViewLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shade.TouchLogger;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: classes.dex */
public final class TouchHandlingView extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy interactionHandler$delegate;
    public DeviceEntryIconViewBinder$bind$1 listener;

    public /* synthetic */ TouchHandlingView(Context context, AttributeSet attributeSet, Function0 function0, int i, TouchHandlingViewLogger touchHandlingViewLogger, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, function0, (i2 & 8) != 0 ? ViewConfiguration.getTouchSlop() : i, (i2 & 16) != 0 ? null : touchHandlingViewLogger);
    }

    @Override // android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        TouchLogger.Companion companion = TouchLogger.Companion;
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "long_press", zDispatchTouchEvent);
        return zDispatchTouchEvent;
    }

    public final TouchHandlingViewInteractionHandler getInteractionHandler() {
        return (TouchHandlingViewInteractionHandler) this.interactionHandler$delegate.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x009b  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Object down;
        Object obj;
        boolean z;
        final TouchHandlingViewInteractionHandler interactionHandler = getInteractionHandler();
        if (interactionHandler.isLongPressHandlingEnabled) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                down = new TouchHandlingViewInteractionHandler.MotionEventModel.Down((int) motionEvent.getX(), (int) motionEvent.getY());
            } else if (actionMasked == 1) {
                down = new TouchHandlingViewInteractionHandler.MotionEventModel.Up(TouchHandlingViewInteractionHandler.distanceMoved(motionEvent), motionEvent.getEventTime() - motionEvent.getDownTime());
            } else if (actionMasked != 2) {
                obj = actionMasked != 3 ? TouchHandlingViewInteractionHandler.MotionEventModel.Other.INSTANCE : TouchHandlingViewInteractionHandler.MotionEventModel.Cancel.INSTANCE;
                z = obj instanceof TouchHandlingViewInteractionHandler.MotionEventModel.Down;
                TouchHandlingViewLogger touchHandlingViewLogger = interactionHandler.logger;
                if (!z) {
                    TouchHandlingViewInteractionHandler.MotionEventModel.Down down2 = (TouchHandlingViewInteractionHandler.MotionEventModel.Down) obj;
                    final int i = down2.x;
                    long jLongValue = ((Number) interactionHandler.longPressDuration.invoke()).longValue();
                    if (touchHandlingViewLogger != null) {
                        LogLevel logLevel = LogLevel.DEBUG;
                        final int i2 = 1;
                        Function1 function1 = new Function1() { // from class: com.android.systemui.log.TouchHandlingViewLogger$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                LogMessage logMessage = (LogMessage) obj2;
                                switch (i2) {
                                    case 0:
                                        return "on MotionEvent.Up: distanceMoved: " + logMessage.getDouble1() + ", allowedTouchSlop: " + logMessage.getInt1() + ", eventDuration: " + logMessage.getLong1();
                                    case 1:
                                        return "on MotionEvent.Down: scheduling long press activation after " + logMessage.getLong1() + " ms";
                                    default:
                                        return "on MotionEvent.Motion: May cancel long press due to movement: distanceMoved: " + logMessage.getDouble1() + ", allowedTouchSlop: " + logMessage.getInt1() + " ";
                                }
                            }
                        };
                        LogBuffer logBuffer = touchHandlingViewLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain(touchHandlingViewLogger.tag, logLevel, function1, null);
                        ((LogMessageImpl) logMessageObtain).long1 = jLongValue;
                        logBuffer.commit(logMessageObtain);
                    }
                    final int i3 = down2.y;
                    interactionHandler.scheduledLongPressHandle = (DisposableHandle) interactionHandler.postDelayed.invoke(new Runnable() { // from class: com.android.systemui.common.ui.view.TouchHandlingViewInteractionHandler$scheduleLongPress$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TouchHandlingViewLogger touchHandlingViewLogger2 = interactionHandler.logger;
                            if (touchHandlingViewLogger2 != null) {
                                LogBuffer.log$default(touchHandlingViewLogger2.logBuffer, touchHandlingViewLogger2.tag, LogLevel.DEBUG, "long press event detected and dispatched");
                            }
                            TouchHandlingViewInteractionHandler touchHandlingViewInteractionHandler = interactionHandler;
                            int i4 = i;
                            int i5 = i3;
                            if (((Boolean) touchHandlingViewInteractionHandler.isAttachedToWindow.invoke()).booleanValue()) {
                                touchHandlingViewInteractionHandler.onLongPressDetected.invoke(Integer.valueOf(i4), Integer.valueOf(i5));
                            }
                        }
                    }, Long.valueOf(jLongValue));
                    Point point = interactionHandler.lastEventDownCoordinate;
                    point.x = down2.x;
                    point.y = i3;
                    return true;
                }
                boolean z2 = obj instanceof TouchHandlingViewInteractionHandler.MotionEventModel.Move;
                int i4 = interactionHandler.allowedTouchSlop;
                if (z2) {
                    float f = ((TouchHandlingViewInteractionHandler.MotionEventModel.Move) obj).distanceMoved;
                    if (f > i4) {
                        if (touchHandlingViewLogger != null) {
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            final int i5 = 2;
                            Function1 function12 = new Function1() { // from class: com.android.systemui.log.TouchHandlingViewLogger$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    LogMessage logMessage = (LogMessage) obj2;
                                    switch (i5) {
                                        case 0:
                                            return "on MotionEvent.Up: distanceMoved: " + logMessage.getDouble1() + ", allowedTouchSlop: " + logMessage.getInt1() + ", eventDuration: " + logMessage.getLong1();
                                        case 1:
                                            return "on MotionEvent.Down: scheduling long press activation after " + logMessage.getLong1() + " ms";
                                        default:
                                            return "on MotionEvent.Motion: May cancel long press due to movement: distanceMoved: " + logMessage.getDouble1() + ", allowedTouchSlop: " + logMessage.getInt1() + " ";
                                    }
                                }
                            };
                            String str = touchHandlingViewLogger.tag;
                            LogBuffer logBuffer2 = touchHandlingViewLogger.logBuffer;
                            LogMessage logMessageObtain2 = logBuffer2.obtain(str, logLevel2, function12, null);
                            double d = f;
                            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
                            logMessageImpl.double1 = d;
                            logMessageImpl.int1 = i4;
                            logBuffer2.commit(logMessageObtain2);
                        }
                        DisposableHandle disposableHandle = interactionHandler.scheduledLongPressHandle;
                        if (disposableHandle != null) {
                            disposableHandle.dispose();
                            return false;
                        }
                    }
                } else if (obj instanceof TouchHandlingViewInteractionHandler.MotionEventModel.Up) {
                    if (touchHandlingViewLogger != null) {
                        TouchHandlingViewInteractionHandler.MotionEventModel.Up up = (TouchHandlingViewInteractionHandler.MotionEventModel.Up) obj;
                        float f2 = up.distanceMoved;
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        final int i6 = 0;
                        Function1 function13 = new Function1() { // from class: com.android.systemui.log.TouchHandlingViewLogger$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                LogMessage logMessage = (LogMessage) obj2;
                                switch (i6) {
                                    case 0:
                                        return "on MotionEvent.Up: distanceMoved: " + logMessage.getDouble1() + ", allowedTouchSlop: " + logMessage.getInt1() + ", eventDuration: " + logMessage.getLong1();
                                    case 1:
                                        return "on MotionEvent.Down: scheduling long press activation after " + logMessage.getLong1() + " ms";
                                    default:
                                        return "on MotionEvent.Motion: May cancel long press due to movement: distanceMoved: " + logMessage.getDouble1() + ", allowedTouchSlop: " + logMessage.getInt1() + " ";
                                }
                            }
                        };
                        String str2 = touchHandlingViewLogger.tag;
                        LogBuffer logBuffer3 = touchHandlingViewLogger.logBuffer;
                        LogMessage logMessageObtain3 = logBuffer3.obtain(str2, logLevel3, function13, null);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
                        logMessageImpl2.double1 = f2;
                        logMessageImpl2.int1 = i4;
                        logMessageImpl2.long1 = up.gestureDuration;
                        logBuffer3.commit(logMessageObtain3);
                    }
                    DisposableHandle disposableHandle2 = interactionHandler.scheduledLongPressHandle;
                    if (disposableHandle2 != null) {
                        disposableHandle2.dispose();
                    }
                    TouchHandlingViewInteractionHandler.MotionEventModel.Up up2 = (TouchHandlingViewInteractionHandler.MotionEventModel.Up) obj;
                    if (up2.distanceMoved <= i4) {
                        if (up2.gestureDuration < ((Number) interactionHandler.longPressDuration.invoke()).longValue()) {
                            if (touchHandlingViewLogger != null) {
                                LogBuffer.log$default(touchHandlingViewLogger.logBuffer, touchHandlingViewLogger.tag, LogLevel.DEBUG, "Dispatching single tap instead of long press");
                            }
                            Point point2 = interactionHandler.lastEventDownCoordinate;
                            int i7 = point2.x;
                            int i8 = point2.y;
                            if (((Boolean) interactionHandler.isAttachedToWindow.invoke()).booleanValue()) {
                                interactionHandler.onSingleTapDetected.invoke(Integer.valueOf(i7), Integer.valueOf(i8));
                                return false;
                            }
                        }
                    }
                } else if (obj instanceof TouchHandlingViewInteractionHandler.MotionEventModel.Cancel) {
                    if (touchHandlingViewLogger != null) {
                        LogBuffer.log$default(touchHandlingViewLogger.logBuffer, touchHandlingViewLogger.tag, LogLevel.DEBUG, "Long press may be cancelled due to MotionEventModel.Cancel");
                    }
                    DisposableHandle disposableHandle3 = interactionHandler.scheduledLongPressHandle;
                    if (disposableHandle3 != null) {
                        disposableHandle3.dispose();
                    }
                }
            } else {
                down = new TouchHandlingViewInteractionHandler.MotionEventModel.Move(TouchHandlingViewInteractionHandler.distanceMoved(motionEvent));
            }
            obj = down;
            z = obj instanceof TouchHandlingViewInteractionHandler.MotionEventModel.Down;
            TouchHandlingViewLogger touchHandlingViewLogger2 = interactionHandler.logger;
            if (!z) {
            }
        }
        return false;
    }

    public TouchHandlingView(final Context context, AttributeSet attributeSet, final Function0 function0, final int i, final TouchHandlingViewLogger touchHandlingViewLogger) {
        super(context, attributeSet);
        setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.common.ui.view.TouchHandlingView$setupAccessibilityDelegate$1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                TouchHandlingView touchHandlingView = this.this$0;
                int i2 = TouchHandlingView.$r8$clinit;
                if (touchHandlingView.getInteractionHandler().isLongPressHandlingEnabled) {
                    this.this$0.getClass();
                }
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                TouchHandlingView touchHandlingView = this.this$0;
                int i3 = TouchHandlingView.$r8$clinit;
                if (!touchHandlingView.getInteractionHandler().isLongPressHandlingEnabled || i2 != 32) {
                    return super.performAccessibilityAction(view, i2, bundle);
                }
                TouchHandlingView touchHandlingView2 = view instanceof TouchHandlingView ? (TouchHandlingView) view : null;
                if (touchHandlingView2 == null) {
                    return false;
                }
                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$1 = this.this$0.listener;
                if (deviceEntryIconViewBinder$bind$1 != null) {
                    deviceEntryIconViewBinder$bind$1.onLongPressDetected(touchHandlingView2, true);
                }
                return true;
            }
        });
        this.interactionHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.common.ui.view.TouchHandlingView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final int i2 = 1;
                Context context2 = context;
                int i3 = TouchHandlingView.$r8$clinit;
                final TouchHandlingView touchHandlingView = this;
                final int i4 = 0;
                final int i5 = 2;
                return new TouchHandlingViewInteractionHandler(context2, new Function2() { // from class: com.android.systemui.common.ui.view.TouchHandlingView$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        final TouchHandlingView touchHandlingView2 = touchHandlingView;
                        switch (i4) {
                            case 0:
                                long jLongValue = ((Long) obj2).longValue();
                                int i6 = TouchHandlingView.$r8$clinit;
                                final Object obj3 = new Object();
                                touchHandlingView2.getHandler().postDelayed((Runnable) obj, obj3, jLongValue);
                                break;
                            case 1:
                                ((Integer) obj).getClass();
                                ((Integer) obj2).getClass();
                                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$1 = touchHandlingView2.listener;
                                if (deviceEntryIconViewBinder$bind$1 != null) {
                                    deviceEntryIconViewBinder$bind$1.onLongPressDetected(touchHandlingView2, false);
                                }
                                break;
                            default:
                                ((Integer) obj).intValue();
                                ((Integer) obj2).intValue();
                                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$12 = touchHandlingView2.listener;
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                }, new TouchHandlingView$interactionHandler$2$2(touchHandlingView), new Function2() { // from class: com.android.systemui.common.ui.view.TouchHandlingView$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        final TouchHandlingView touchHandlingView2 = touchHandlingView;
                        switch (i2) {
                            case 0:
                                long jLongValue = ((Long) obj2).longValue();
                                int i6 = TouchHandlingView.$r8$clinit;
                                final Object obj3 = new Object();
                                touchHandlingView2.getHandler().postDelayed((Runnable) obj, obj3, jLongValue);
                                break;
                            case 1:
                                ((Integer) obj).getClass();
                                ((Integer) obj2).getClass();
                                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$1 = touchHandlingView2.listener;
                                if (deviceEntryIconViewBinder$bind$1 != null) {
                                    deviceEntryIconViewBinder$bind$1.onLongPressDetected(touchHandlingView2, false);
                                }
                                break;
                            default:
                                ((Integer) obj).intValue();
                                ((Integer) obj2).intValue();
                                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$12 = touchHandlingView2.listener;
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                }, new Function2() { // from class: com.android.systemui.common.ui.view.TouchHandlingView$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        final TouchHandlingView touchHandlingView2 = touchHandlingView;
                        switch (i5) {
                            case 0:
                                long jLongValue = ((Long) obj2).longValue();
                                int i6 = TouchHandlingView.$r8$clinit;
                                final Object obj3 = new Object();
                                touchHandlingView2.getHandler().postDelayed((Runnable) obj, obj3, jLongValue);
                                break;
                            case 1:
                                ((Integer) obj).getClass();
                                ((Integer) obj2).getClass();
                                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$1 = touchHandlingView2.listener;
                                if (deviceEntryIconViewBinder$bind$1 != null) {
                                    deviceEntryIconViewBinder$bind$1.onLongPressDetected(touchHandlingView2, false);
                                }
                                break;
                            default:
                                ((Integer) obj).intValue();
                                ((Integer) obj2).intValue();
                                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$12 = touchHandlingView2.listener;
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                }, new TouchHandlingView$$ExternalSyntheticLambda1(1), function0, i, touchHandlingViewLogger);
            }
        });
    }

    public TouchHandlingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, new TouchHandlingView$$ExternalSyntheticLambda1(0), 0, null, 24, null);
    }
}
