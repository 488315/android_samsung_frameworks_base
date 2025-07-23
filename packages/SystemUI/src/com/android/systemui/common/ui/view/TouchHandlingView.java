package com.android.systemui.common.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$1;
import com.android.systemui.log.TouchHandlingViewLogger;
import com.android.systemui.shade.TouchLogger;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "long_press", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    public final TouchHandlingViewInteractionHandler getInteractionHandler() {
        return (TouchHandlingViewInteractionHandler) this.interactionHandler$delegate.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x009b  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 356
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.view.TouchHandlingView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public TouchHandlingView(final Context context, AttributeSet attributeSet, final Function0 function0, final int i, final TouchHandlingViewLogger touchHandlingViewLogger) {
        super(context, attributeSet);
        setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.common.ui.view.TouchHandlingView$setupAccessibilityDelegate$1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                TouchHandlingView touchHandlingView = TouchHandlingView.this;
                int i2 = TouchHandlingView.$r8$clinit;
                if (touchHandlingView.getInteractionHandler().isLongPressHandlingEnabled) {
                    TouchHandlingView.this.getClass();
                }
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                TouchHandlingView touchHandlingView = TouchHandlingView.this;
                int i3 = TouchHandlingView.$r8$clinit;
                if (!touchHandlingView.getInteractionHandler().isLongPressHandlingEnabled || i2 != 32) {
                    return super.performAccessibilityAction(view, i2, bundle);
                }
                TouchHandlingView touchHandlingView2 = view instanceof TouchHandlingView ? (TouchHandlingView) view : null;
                if (touchHandlingView2 == null) {
                    return false;
                }
                DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$1 = TouchHandlingView.this.listener;
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
                                long longValue = ((Long) obj2).longValue();
                                int i6 = TouchHandlingView.$r8$clinit;
                                final Object obj3 = new Object();
                                touchHandlingView2.getHandler().postDelayed((Runnable) obj, obj3, longValue);
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
                                long longValue = ((Long) obj2).longValue();
                                int i6 = TouchHandlingView.$r8$clinit;
                                final Object obj3 = new Object();
                                touchHandlingView2.getHandler().postDelayed((Runnable) obj, obj3, longValue);
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
                                long longValue = ((Long) obj2).longValue();
                                int i6 = TouchHandlingView.$r8$clinit;
                                final Object obj3 = new Object();
                                touchHandlingView2.getHandler().postDelayed((Runnable) obj, obj3, longValue);
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
